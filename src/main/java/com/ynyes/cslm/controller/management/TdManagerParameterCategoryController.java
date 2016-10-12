package com.ynyes.cslm.controller.management;

import java.util.HashMap;
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

import com.ynyes.cslm.entity.TdParameterCategory;
import com.ynyes.cslm.service.TdManagerLogService;
import com.ynyes.cslm.service.TdParameterCategoryService;

/**
 * 后台广告位管理控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value="/Verwalter/parameter/category")
public class TdManagerParameterCategoryController {
    
    @Autowired
    TdParameterCategoryService tdParameterCategoryService;
    
    @Autowired
    TdManagerLogService tdManagerLogService;
    
    @RequestMapping(value="/check", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> validateForm(Long parentId, Long id, String param) {
        Map<String, String> res = new HashMap<String, String>();
        
        res.put("status", "n");
        
        if (null == param || param.isEmpty())
        {
            res.put("info", "该字段不能为空");
            return res;
        }
        
        if (null == id)
        {
            if (null == parentId)
            {
                if (null != tdParameterCategoryService.findByTitleAndParentIdIsNull(param))
                {
                    res.put("info", "已存在同名参数分类");
                    return res;
                }
            }
            else
            {
                if (null != tdParameterCategoryService.findByTitleAndParentId(param, parentId))
                {
                    res.put("info", "已存在同名的兄弟参数分类");
                    return res;
                }
            }
        }
        else
        {
            if (null == parentId)
            {
                if (null != tdParameterCategoryService.findByTitleAndParentIdIsNullAndIdNot(param, id))
                {
                    res.put("info", "已存在同名参数分类");
                    return res;
                }
            }
            else
            {
                if (null != tdParameterCategoryService.findByTitleAndParentIdAndIdNot(param, parentId, id))
                {
                    res.put("info", "已存在同名的兄弟参数分类");
                    return res;
                }
            }
        }
        
        res.put("status", "y");
        
        return res;
    }
    
    @RequestMapping(value="/list")
    public String setting(String __EVENTTARGET,
                          String __EVENTARGUMENT,
                          String __VIEWSTATE,
                          Long parentId,
                          Long[] listId,Long id,
                          Integer[] listChkId,
                          Long[] listSortId,
                          String keywords,
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
                tdManagerLogService.addLog("delete", "用户删除参数分类", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnSave"))
            {
                btnSave(listId, listSortId);
                tdManagerLogService.addLog("edit", "用户修改参数分类", req);
            }
        }
        
        map.addAttribute("category_list", tdParameterCategoryService.findByParentIdIsNullOrderBySortIdAsc());
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        map.addAttribute("id", id);
        map.addAttribute("keywords", keywords);
        map.addAttribute("parentid", parentId);
        
        if(null == keywords || keywords.isEmpty()){
        	if(null != parentId){
        		map.addAttribute("parameter", tdParameterCategoryService.findOne(parentId));
        		map.addAttribute("parameter_category_list", tdParameterCategoryService.findByParentId(parentId));
        	}else{
        		map.addAttribute("parameter_category_list", tdParameterCategoryService.findAll());
        	}
        }else{
        	map.addAttribute("parameter_category_list", tdParameterCategoryService.searchAll(keywords));
        }
        
        
        return "/site_mag/parameter_category_list";
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
        map.addAttribute("category_list", tdParameterCategoryService.findAll());

        if (null != id)
        {
            if (null != sub) // 添加子类
            {
                map.addAttribute("fatherCat", tdParameterCategoryService.findOne(id));
            }
            else
            {
                map.addAttribute("cat", tdParameterCategoryService.findOne(id));
            }
        }
        
        return "/site_mag/parameter_category_edit";
    }
    
    @RequestMapping(value="/save")
    public String orderEdit(TdParameterCategory tdParameterCategory,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        if (null == tdParameterCategory.getId())
        {
        	tdManagerLogService.addLog("add", "用户新增参数类型"+tdParameterCategory.getTitle(), req);
        }
        else
        {
        	tdManagerLogService.addLog("edit", "用户修改参数类型"+tdParameterCategory.getTitle(), req);
        }
        
        tdParameterCategory = tdParameterCategoryService.save(tdParameterCategory);
        
        return "redirect:/Verwalter/parameter/category/list?id="+tdParameterCategory.getId();
    }

    @ModelAttribute
    public void getModel(@RequestParam(value = "id", required = false) Long id,
                        Model model) {
        if (null != id) {
            model.addAttribute("tdParameterCategory", tdParameterCategoryService.findOne(id));
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
            
            TdParameterCategory e = tdParameterCategoryService.findOne(id);
            
            if (null != e)
            {
                if (sortIds.length > i)
                {
                    e.setSortId(sortIds[i]);
                    tdParameterCategoryService.save(e);
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
                
                tdParameterCategoryService.delete(id);
            }
        }
    }
}
