package com.ynyes.cslm.controller.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdParameterCategory;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.service.TdManagerLogService;
import com.ynyes.cslm.service.TdParameterCategoryService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.service.TdTagService;

/**
 * 后台产品控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value = "/Verwalter/product/category")
public class TdManagerProductCategoryController {

    @Autowired
    TdProductCategoryService tdProductCategoryService;

    @Autowired
    TdManagerLogService tdManagerLogService;
    
    @Autowired
    TdParameterCategoryService tdParameterCategoryService;
    
    @Autowired
    TdTagService tdTagService;

    @RequestMapping(value = "/list")
    public String categoryList(String __EVENTTARGET, String __EVENTARGUMENT,
            String __VIEWSTATE, Long[] listId, Integer[] listChkId,
            Long[] listSortId, ModelMap map, HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }

        if (null != __EVENTTARGET) {
            switch (__EVENTTARGET) {
            case "btnSave":
                productCategoryBtnSave(listId, listSortId);

                break;

            case "btnDelete":
                productCategoryBtnDelete(listId, listChkId);

                break;
            }
        }

        map.addAttribute("category_list", tdProductCategoryService.findAll());

        // 参数注回
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);

        return "/site_mag/product_category_list";
    }

    @RequestMapping(value = "/edit")
    public String categoryEditDialog(Long id, Long sub, String __EVENTTARGET,
            String __EVENTARGUMENT, String __VIEWSTATE, ModelMap map,
            HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }

        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);

//        List<TdProductCategory> categoryList = tdProductCategoryService.findByParentIdIsNullOrderBySortIdAsc();
//        map.addAttribute("category_list", categoryList);
        map.addAttribute("category_list", tdProductCategoryService.findAll());
        
        map.addAttribute("tag_list",tdTagService.findByTypeId(1L));

        // 参数类型表
        List<TdParameterCategory> parameList = tdParameterCategoryService.findByParentIdIsNullOrderBySortIdAsc();
        
        map.addAttribute("param_category_list",
                parameList);

        if (null != sub) // 添加子类
        {
            if (null != id) {
            	TdProductCategory category = tdProductCategoryService.findOne(id);
                map.addAttribute("fatherCat",
                      category  );
              if(null != category.getParamCategoryId())
              {
            	  if(null != parameList)
            	  {
            		  for (TdParameterCategory tdParameterCategory : parameList) {
						if(tdParameterCategory.getParentTree().contains("["+category.getParamCategoryId()+"]"))
						{
							map.addAttribute("subParamList", tdParameterCategoryService.findByParentIdOyderBySortIdAsc(category.getParamCategoryId()));
						}
					}
            	  }
              }  
                
            }
        } else {
            if (null != id) {
            	TdProductCategory category = tdProductCategoryService.findOne(id);
                map.addAttribute("cat",category);
                if(null != category.getParamCategoryId())
                {
            	  TdParameterCategory parameterCategory = tdParameterCategoryService.findOne(category.getParamCategoryId());
            	  map.addAttribute("par", parameterCategory);
              	  if(null != parameList && null != parameterCategory)
              	  {
              		  for (TdParameterCategory tdParameterCategory : parameList) {
              			  
  						if(parameterCategory.getParentTree().contains("["+tdParameterCategory.getId()+"],"))
  						{
  							map.addAttribute("subParamList", tdParameterCategoryService.findByParentIdOyderBySortIdAsc(tdParameterCategory.getId()));
  						}
  					}
              	  }
                } 
            }
        }

        return "/site_mag/product_category_edit";

    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(TdProductCategory cat, Long paramCategory,
    		String[] tagList,
    		String __EVENTTARGET,
            String __EVENTARGUMENT, String __VIEWSTATE, ModelMap map,
            HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }

        if (null == cat.getId()) {
            tdManagerLogService.addLog("add", "用户修改产品分类", req);
        } else {
            tdManagerLogService.addLog("edit", "用户修改产品分类", req);
        }
        
        String tagValueCollect = "";
        
        if (null != tagList)
        {
        	for (String tag : tagList)
        	{
        		if (null != tag && !"".equals(tag))
        		{
        			tagValueCollect += "[" + tag + "],";
        		}
        	}
        	
        	cat.setTagValueCollect(tagValueCollect);
        }
        
        if(null != paramCategory && !paramCategory.equals(0L))
        {
        	cat.setParamCategoryId(paramCategory);
        }
        
        tdProductCategoryService.save(cat);

        return "redirect:/Verwalter/product/category/list";
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> validateForm(String param, Long id) {
        Map<String, String> res = new HashMap<String, String>();

        res.put("status", "n");

        if (null == param || param.isEmpty()) {
            res.put("info", "该字段不能为空");
            return res;
        }

//        if (null == id) // 新增分类，查找所有
//        {
//            if (null != tdProductCategoryService.findByTitle(param)) {
//                res.put("info", "已存在同名分类");
//                return res;
//            }
//        } else // 修改，查找除当前ID的所有
//        {
//            if (null != tdProductCategoryService.findByTitleAndIdNot(param, id)) {
//                res.put("info", "已存在同名分类");
//                return res;
//            }
//        }

        res.put("status", "y");

        return res;
    }
    
    @RequestMapping(value="/paramCheck",method =RequestMethod.POST)
    public String findParam(Long paramId,HttpServletRequest req,ModelMap map)
    {
    	System.err.println(paramId);
    	if(null != paramId)
    	{
    		map.addAttribute("paramLlist", tdParameterCategoryService.findByParentIdOyderBySortIdAsc(paramId));
    	}
    	
    	return "/site_mag/product_category_param";
    }

    private void productCategoryBtnSave(Long[] ids, Long[] sortIds) {
        if (null == ids || null == sortIds || ids.length < 1
                || sortIds.length < 1) {
            return;
        }

        for (int i = 0; i < ids.length; i++) {
            Long id = ids[i];
            TdProductCategory category = tdProductCategoryService.findOne(id);

            if (sortIds.length > i) {
                category.setSortId(sortIds[i]);
                tdProductCategoryService.save(category);
            }
        }
    }

    private void productCategoryBtnDelete(Long[] ids, Integer[] chkIds) {
        if (null == ids || null == chkIds || ids.length < 1
                || chkIds.length < 1) {
            return;
        }

        for (int chkId : chkIds) {
            if (chkId >= 0 && ids.length > chkId) {
                Long id = ids[chkId];

                tdProductCategoryService.delete(id);
            }
        }
    }
}
