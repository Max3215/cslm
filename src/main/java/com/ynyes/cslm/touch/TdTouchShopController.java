package com.ynyes.cslm.touch;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdOrderService;
import com.ynyes.cslm.service.TdUserRecentVisitService;
import com.ynyes.cslm.util.SiteMagConstant;

@Controller
@RequestMapping("/touch/shop")
public class TdTouchShopController {
	
//	@Autowired 
//    private TdDiySiteService tdDiySiteService;
	
	@Autowired 
    private TdOrderService tdOrderService;
		
	@Autowired
    private TdCommonService tdCommonService;
	
	@Autowired
    private TdUserRecentVisitService tdUserRecentVisitService;
    
	@RequestMapping("/list")
    public String infoList(Integer page, 
                            ModelMap map,
                            Integer cid,
                            HttpServletRequest req){
	    
	    tdCommonService.setHeader(map, req);
        
        if (null == cid)
        {
            cid = 0;
        }
        
        if (null == page) {
			page = 0;
		}
        
        map.addAttribute("cid", cid);        
        
//        map.addAttribute("shop_list", tdDiySiteService.findAllOrderBySortIdAsc(page, SiteMagConstant.pageSize));
       
      //判断是否为app链接
        Integer isApp = (Integer) req.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
        
        return "/touch/shop_list";
    }
	
	@RequestMapping("/{id}")
    public String shop(@PathVariable Long id, ModelMap map, HttpServletRequest req){
	    tdCommonService.setHeader(map, req);
	    
//	    map.addAttribute("shop", tdDiySiteService.findOne(id));
	    
	  //判断是否为app链接
        Integer isApp = (Integer) req.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
	    
        return "/touch/shop_detail";
    }
}
