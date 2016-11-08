package com.ynyes.cslm.controller.management;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdPointGoods;
import com.ynyes.cslm.service.TdManagerLogService;
import com.ynyes.cslm.service.TdPointGoodsService;
import com.ynyes.cslm.util.SiteMagConstant;

/**
 * 
 * @author Max
 * 后台积分商品管理
 */

@Controller
@RequestMapping(value="/Verwalter/pointGoods")
public class TdManagerPointGoodsController {

	@Autowired
	private TdPointGoodsService tdPointGoodsService;
	
	@Autowired
	private TdManagerLogService tdManagerLogService;
	
	
	@RequestMapping("/list")
	public String list(Integer page,
            Integer size,
            String __EVENTTARGET,
            String __EVENTARGUMENT,
            String __VIEWSTATE,
            Long[] listId,
            Boolean isEnable,String keywords,
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
            if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
            {
                btnDelete(listId, listChkId);
                tdManagerLogService.addLog("delete", "用户删除标签", req);
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
        
        
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("inEnable",isEnable);
        map.addAttribute("keywords", keywords);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "onSaleTime"));
        
        map.addAttribute("goodPage", tdPointGoodsService.findAll(keywords, isEnable, pageRequest));
		
		return "/site_mag/point_goods_list";
	}
	

	@RequestMapping(value="/edit")
	public String tagEdit(Long id,
			String _VIEWSTATE,
			ModelMap map,
			HttpServletRequest req
			){
		String username = (String) req.getSession().getAttribute("manager");
	    if (null == username)
	    {
	        return "redirect:/Verwalter/login";
	    }
	    map.addAttribute("_VIEWSTATE",_VIEWSTATE);
	    if(null !=id){
	    	map.addAttribute("goods",tdPointGoodsService.findOne(id));
	    }
	    
		return "/site_mag/point_goods_edit";
	}
	
	@RequestMapping("/save")
	public String save(TdPointGoods tdPointGoods,
			String __VIEWSTATE,
			ModelMap map,
			HttpServletRequest req){
		String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
       
        if(null == tdPointGoods.getId())
	     {
	    	 tdManagerLogService.addLog("add", "用户修改兑换商品", req);
	     }
	     else
	     {
	    	 tdManagerLogService.addLog("edit", "用户修改兑换商品", req);
	     }
		
        tdPointGoodsService.save(tdPointGoods);
        
        return "redirect:/Verwalter/pointGoods/list";
	}
	
	
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> checkGoods(String param,Long id){
		Map<String, String> res = new HashMap<String, String>();
        
        res.put("status", "n");
        
        if (null == id)
        {
            if (null != tdPointGoodsService.findByCode(param))
            {
                res.put("info", "已存在同编号商品");
                return res;
            }
        }
        else
        {
            if (null != tdPointGoodsService.findByCodeAndIdNot(param, id))
            {
                res.put("info", "已存在同编号商品");
                return res;
            }
        }
        
        
        res.put("status", "y");
        
        return res;
		
	}
	
	
	@ModelAttribute
    public void getModel(@RequestParam(value = "id", required = false) Long id,
                        Model model) {
        if (null != id) {
            model.addAttribute("tdPointGoods", tdPointGoodsService.findOne(id));
        }
    }
	
	
	private void btnDelete(Long[] ids, Integer[] chkIds) {
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
                
                tdPointGoodsService.delete(id);
            }
        }
	}
	
}
