package com.ynyes.cslm.controller.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdParameter;
import com.ynyes.cslm.entity.TdParameterCategory;
import com.ynyes.cslm.service.TdManagerLogService;
import com.ynyes.cslm.service.TdParameterCategoryService;
import com.ynyes.cslm.service.TdParameterService;
import com.ynyes.cslm.util.SiteMagConstant;

/**
 * 后台广告位管理控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value="/Verwalter/parameter")
public class TdManagerParameterController {
    
    @Autowired
    TdParameterService tdParameterService;
    
    @Autowired
    TdParameterCategoryService tdParameterCategoryService;
    
    @Autowired
    TdManagerLogService tdManagerLogService;
    
    @RequestMapping(value="/check", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> validateForm(Long categoryId, Long id, String param) {
        Map<String, String> res = new HashMap<String, String>();
        
        res.put("status", "n");
        
        if (null == param || param.isEmpty())
        {
            res.put("info", "该字段不能为空");
            return res;
        }
        
        if (null == categoryId)
        {
            res.put("info", "请先选择参数分类");
            return res;
        }
        
        if (null == id)
        {
            if (null != tdParameterService.findByTitleAndCategoryId(param, categoryId))
            {
                res.put("info", "已存在同名参数");
                return res;
            }
        }
        else
        {
            if (null != tdParameterService.findByTitleAndCategoryIdAndIdNot(param, categoryId, id))
            {
                res.put("info", "已存在同名的兄弟参数");
                return res;
            }
        }
        
        res.put("status", "y");
        
        return res;
    }
    
    @RequestMapping(value="/list")
    public String setting(String __EVENTTARGET,
                          String __EVENTARGUMENT,
                          String __VIEWSTATE,
                          Long categoryId,
                          String keywords,
                          Integer page,
                          Integer size,
                          Long[] listId,
                          Integer[] listChkId,
                          Long[] listSortId,
                          ModelMap map,
                          HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        if (null != __EVENTTARGET)
        {
            if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
            {
                btnDelete(listId, listChkId);
                tdManagerLogService.addLog("delete", "用户删除参数", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnSave"))
            {
                btnSave(listId, listSortId);
                tdManagerLogService.addLog("edit", "用户修改参数", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnPage"))
            {
                if (null != __EVENTARGUMENT)
                {
                    page = Integer.parseInt(__EVENTARGUMENT);
                } 
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
        
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        map.addAttribute("categoryId", categoryId);
        map.addAttribute("keywords", keywords);
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        
//        map.addAttribute("parameter_category_list", tdParameterCategoryService.findAll());
        List<TdParameterCategory> parameCategoryList = tdParameterCategoryService.findByParentIdIsNullOrderBySortIdAsc();
        map.addAttribute("parameter_category_list", parameCategoryList);
        
        
        if (null != keywords && !keywords.isEmpty())
        {
            map.addAttribute("parameter_page", tdParameterService.searchOrderBySortIdAsc(keywords, page, size));
        }
        else
        {
            if (null == categoryId)
            {
                map.addAttribute("parameter_page", tdParameterService.findAllOrderBySortIdAsc(page, size));
            }
            else
            {
                map.addAttribute("parameter_page", tdParameterService.findByCategoryTreeContainingOrderBySortIdAsc(categoryId, page, size));
                
                TdParameterCategory category = tdParameterCategoryService.findOne(categoryId);
                if(null != parameCategoryList && parameCategoryList.size() > 0)
                {
                	for (TdParameterCategory tdParameterCategory : parameCategoryList) {
                		if(null != category && category.getParentTree().contains("["+tdParameterCategory.getId()+"]"))
                		{
                			List<TdParameterCategory> cateList = tdParameterCategoryService.findByParentIdOyderBySortIdAsc(tdParameterCategory.getId());
                			map.addAttribute("category_list", cateList);
                			
                		}
                	}
                }
                map.addAttribute("category", category);
            }
        }
        
        return "/site_mag/parameter_list";
    }
    
    @RequestMapping(value="/edit")
    public String paramCategoryEdit(Long id,
                        Long sub,       
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
//        map.addAttribute("category_list", tdParameterCategoryService.findAll());
        List<TdParameterCategory> categoryList = tdParameterCategoryService.findByParentIdIsNullOrderBySortIdAsc();
        map.addAttribute("category_list", categoryList);

        if (null != id)
        {
        	TdParameter parameter = tdParameterService.findOne(id);
            map.addAttribute("parameter", parameter);
            
            for (TdParameterCategory tdParameterCategory : categoryList) {
				if(null != parameter.getCategoryTree() && parameter.getCategoryTree().contains("["+tdParameterCategory.getId()+"]"))
				{
					List<TdParameterCategory> cateList = tdParameterCategoryService.findByParentIdAll(tdParameterCategory.getId());
					if(null != cateList && cateList.size() > 0)
					{
						map.addAttribute("cateList", cateList);
//						for (TdParameterCategory tdParameterCategory2 : cateList)
//						{	
//							if(null != parameter.getCategoryTree() && parameter.getCategoryTree().contains("["+tdParameterCategory2.getId()+"]"))
//							{
//								map.addAttribute("categoryList", tdParameterCategoryService.findByParentIdOyderBySortIdAsc(tdParameterCategory2.getId()));
//							}
//						}
					}
				}
			}
        }
        
        return "/site_mag/parameter_edit";
    }
    
    @RequestMapping(value="/category",method=RequestMethod.POST)
    public String category(Long categoryId,String type,HttpServletRequest req,ModelMap map)
    {
    	if(null != categoryId)
    	{
			map.addAttribute("cateList", tdParameterCategoryService.findByParentIdAll(categoryId));
    	}
    	return "/site_mag/paramter_two_cat";
    }
    
    @RequestMapping(value="/save")
    public String orderEdit(TdParameter tdParameter,
    					Long category,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        if (null == tdParameter.getId())
        {
        	tdManagerLogService.addLog("add", "用户修改参数"+tdParameter.getTitle(), req);
        }
        else
        {
        	tdManagerLogService.addLog("edit", "用户修改参数"+tdParameter.getTitle(), req);
        }
        if(null != category){
        	tdParameter.setCategoryId(category);
        }
        
        tdParameterService.save(tdParameter);
        
        return "redirect:/Verwalter/parameter/list";
    }

    @ModelAttribute
    public void getModel(@RequestParam(value = "id", required = false) Long id,
                        Model model) {
        if (null != id) {
            model.addAttribute("tdParameter", tdParameterService.findOne(id));
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
            
            TdParameter e = tdParameterService.findOne(id);
            
            if (null != e)
            {
                if (sortIds.length > i)
                {
                    e.setSortId(sortIds[i]);
                    tdParameterService.save(e);
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
                
                tdParameterService.delete(id);
            }
        }
    }
}
