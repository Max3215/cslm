package com.ynyes.cslm.controller.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdBrand;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.service.TdBrandService;
import com.ynyes.cslm.service.TdManagerLogService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.util.SiteMagConstant;

/**
 * 后台用户管理控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value="/Verwalter/brand")
public class TdManagerBrandController {
    
    @Autowired
    TdBrandService tdBrandService;
    
    @Autowired
    TdProductCategoryService tdProductCategoryService;
    
    @Autowired
    TdManagerLogService tdManagerLogService;
    
    
    @RequestMapping(value="/check", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> validateForm(Long catId, Long id, String param) {
        Map<String, String> res = new HashMap<String, String>();
        
        res.put("status", "n");
        
        if (null == param || param.isEmpty())
        {
            res.put("info", "该字段不能为空");
            return res;
        }
        
        if (null == id)
        {
            if (null == catId)
            {
                if (null != tdBrandService.findByTitle(param))
                {
                    res.put("info", "该商品分类下已存在同名品牌");
                    return res;
                }
            }
            else
            {
                if (null != tdBrandService.findByProductCategoryTreeContainingAndTitle(catId, param))
                {
                    res.put("info", "该商品分类下已存在同名品牌");
                    return res;
                }
            }
        }
        else
        {
            if (null == catId)
            {
                if (null != tdBrandService.findByTitleAndIdNot(param, id))
                {
                    res.put("info", "该商品分类下已存在同名品牌");
                    return res;
                }
            }
            else
            {
                if (null != tdBrandService.findByProductCategoryTreeContainingAndTitleAndIdNot(catId, param, id))
                {
                    res.put("info", "该商品分类下已存在同名品牌");
                    return res;
                }
            }
        }
        
        res.put("status", "y");
        
        return res;
    }
    
    @RequestMapping(value="/list")
    public String setting(Integer page,
                          Integer size,
                          String keywords,
                          String __EVENTTARGET,
                          String __EVENTARGUMENT,
                          String __VIEWSTATE,
                          Long[] listId,
                          Integer[] listChkId,
                          Long[] listSortId,
                          ModelMap map,
                          HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        if (null != __EVENTTARGET)
        {
            if (__EVENTTARGET.equalsIgnoreCase("btnPage"))
            {
                if (null != __EVENTARGUMENT)
                {
                    page = Integer.parseInt(__EVENTARGUMENT);
                } 
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
            {
                btnDelete(listId, listChkId);
                tdManagerLogService.addLog("delete", "删除品牌", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnSave"))
            {
                btnSave(listId, listSortId);
                tdManagerLogService.addLog("edit", "修改品牌", req);
            }
        }
        
        if (null == page || page < 0)
        {
            page = 0;
        }
        
        if (null == size || size <= 0)
        {
            size = SiteMagConstant.pageSize;;
        }
        
        if (null != keywords)
        {
            keywords = keywords.trim();
        }
        
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("keywords", keywords);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);

        Page<TdBrand> brandPage = null;
        
        if (null == keywords || "".equalsIgnoreCase(keywords))
        {
            brandPage = tdBrandService.findAllOrderBySortIdAsc(page, size);
        }
        else
        {
            brandPage = tdBrandService.searchAndOrderBySortIdAsc(keywords, page, size);
        }
        
        map.addAttribute("brand_page", brandPage);
        
        return "/site_mag/brand_list";
    }
    
    @RequestMapping(value="/edit")
    public String orderEdit(Long id,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
//        map.addAttribute("category_list", tdProductCategoryService.findAll());
        List<TdProductCategory> categortList = tdProductCategoryService.findByParentIdIsNullOrderBySortIdAsc();
        map.addAttribute("category_list", categortList);
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);

        if (null != id)
        {
        	TdBrand brand = tdBrandService.findOne(id);
            map.addAttribute("brand",brand);
            for (TdProductCategory tdProductCategory : categortList) {
				if(brand.getProductCategoryTree().contains("["+tdProductCategory.getId()+"]"))
				{
					List<TdProductCategory> cateList = tdProductCategoryService.findByParentIdOrderBySortIdAsc(tdProductCategory.getId());
					map.addAttribute("cateList", cateList);
					
					for (TdProductCategory productCategory : cateList) {
						if(brand.getProductCategoryTree().contains("["+productCategory.getId()+"]"))
						{
							map.addAttribute("categoryList", tdProductCategoryService.findByParentIdOrderBySortIdAsc(productCategory.getId()));
						}
					}
					
				}
			}
        }
        return "/site_mag/brand_edit";
    }
    
    @RequestMapping(value="/save")
    public String orderEdit(TdBrand tdBrand,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        if (null == tdBrand.getId())
        {
            tdManagerLogService.addLog("add", "用户修改品牌", req);
        }
        else
        {
            tdManagerLogService.addLog("edit", "用户修改品牌", req);
        }
        
        tdBrandService.save(tdBrand);
        
        return "redirect:/Verwalter/brand/list";
    }
    
    @RequestMapping(value="/category",method=RequestMethod.POST)
    public String category(Long categoryId,String type,HttpServletRequest req,ModelMap map)
    {
    	if(null != categoryId)
    	{
    		if("two".equals(type))
    		{
    			map.addAttribute("cateList", tdProductCategoryService.findByParentIdOrderBySortIdAsc(categoryId));
    			return "/site_mag/brand_two_cat";
    		}
    		else if("three".equals(type))
    		{
    			map.addAttribute("categoeyList", tdProductCategoryService.findByParentIdOrderBySortIdAsc(categoryId));
    			return "/site_mag/brand_three_cat";
    		}
    	}
    	return "/site_mag/brand_two_cat";
    }

    @ModelAttribute
    public void getModel(@RequestParam(value = "id", required = false) Long id,
                        Model model) {
        if (null != id) {
            model.addAttribute("tdBrand", tdBrandService.findOne(id));
        }
    }
    
    private void btnSave(Long[] ids, Long[] sortIds)
    {
        if (null == ids || null == sortIds
                || ids.length < 1 || sortIds.length < 1)
        {
            return;
        }
        
        for (int i = 0; i < ids.length; i++)
        {
            Long id = ids[i];
            
            TdBrand e = tdBrandService.findOne(id);
            
            if (null != e)
            {
                if (sortIds.length > i)
                {
                    e.setSortId(sortIds[i]);
                    tdBrandService.save(e);
                }
            }
        }
    }
    
    private void btnDelete(Long[] ids, Integer[] chkIds)
    {
        if (null == ids || null == chkIds
                || ids.length < 1 || chkIds.length < 1)
        {
            return;
        }
        
        for (int chkId : chkIds)
        {
            if (chkId >=0 && ids.length > chkId)
            {
                Long id = ids[chkId];
                
                tdBrandService.delete(id);
            }
        }
    }
}
