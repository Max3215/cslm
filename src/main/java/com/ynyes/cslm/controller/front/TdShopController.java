package com.ynyes.cslm.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.service.TdArticleCategoryService;
import com.ynyes.cslm.service.TdArticleService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdOrderService;
import com.ynyes.cslm.service.TdUserRecentVisitService;
import com.ynyes.cslm.util.ClientConstant;

/**
 * 
 * 同盟店
 *
 */
@Controller
@RequestMapping("/shop")
public class TdShopController {
	@Autowired 
	private TdArticleService tdArticleService;
	
	@Autowired 
    private TdArticleCategoryService tdArticleCategoryService;
	
	@Autowired 
    private TdDistributorService TdDistributorService;
	
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
        
        String username = (String) req.getSession().getAttribute("username");
        
        // 读取浏览记录
        if (null == username)
        {
            map.addAttribute("recent_page", tdUserRecentVisitService.findByUsernameOrderByVisitTimeDesc(req.getSession().getId(), 0, ClientConstant.pageSize));
        }
        else
        {
            map.addAttribute("recent_page", tdUserRecentVisitService.findByUsernameOrderByVisitTimeDesc(username, 0, ClientConstant.pageSize));
        }
        
        if (null == cid)
        {
            cid = 0;
        }
        
        String[] cityArray = {"昆明", "曲靖", "大理"};
        
        map.addAttribute("city_list", cityArray);
        map.addAttribute("cid", cid);
        
        
        List<TdDistributor> TdDistributorlist;
        switch (cid)
        {
        case 1:
            map.addAttribute("shop_list", TdDistributorService.findByCityAndIsEnableTrueOrderBySortIdAsc("曲靖"));
            
            TdDistributorlist  = TdDistributorService.findByCityAndIsEnableTrueOrderBySortIdAsc("曲靖");
            if (null != TdDistributorlist) {
            	 for (TdDistributor diySite : TdDistributorlist) 
                 {
                     List<TdOrder> tdOrders = tdOrderService.findByshopIdAndstatusId(diySite.getId(), 5L);
                     List<TdOrder> tdOrders1 = tdOrderService.findByshopIdAndstatusId(diySite.getId(), 6L);
                     map.addAttribute("shop_orderFinish_"+diySite.getId(), tdOrders.size()+tdOrders1.size());
//                     map.addAttribute("shop_orderComment_"+diySite.getId(), TdDistributorService.ContdiysiteComment(diySite.getId()));
//                     map.addAttribute("shop_serviceStars"+diySite.getId(), TdDistributorService.diysiteServiceStars(diySite.getId()));
                 }
			}
                   
            break;
            
        case 2:
            map.addAttribute("shop_list", TdDistributorService.findByCityAndIsEnableTrueOrderBySortIdAsc("大理"));
            
            TdDistributorlist  = TdDistributorService.findByCityAndIsEnableTrueOrderBySortIdAsc("大理");
            if (null != TdDistributorlist) {
            	 for (TdDistributor diySite : TdDistributorlist) 
                 {
                     List<TdOrder> tdOrders = tdOrderService.findByshopIdAndstatusId(diySite.getId(), 5L);
                     List<TdOrder> tdOrders1 = tdOrderService.findByshopIdAndstatusId(diySite.getId(), 6L);
                     
                     map.addAttribute("shop_orderFinish_"+diySite.getId(), tdOrders.size()+tdOrders1.size());
//                     map.addAttribute("shop_orderComment_"+diySite.getId(), TdDistributorService.ContdiysiteComment(diySite.getId()));
//                     map.addAttribute("shop_serviceStars"+diySite.getId(), TdDistributorService.diysiteServiceStars(diySite.getId()));
                 }
			}
            break;
            
        default:
          
            map.addAttribute("shop_list", TdDistributorService.findByCityAndIsEnableTrueOrderBySortIdAsc("昆明"));
            TdDistributorlist  = TdDistributorService.findByCityAndIsEnableTrueOrderBySortIdAsc("昆明");
            if (null != TdDistributorlist) {
            	 for (TdDistributor diySite : TdDistributorlist) 
                 {
                     List<TdOrder> tdOrders = tdOrderService.findByshopIdAndstatusId(diySite.getId(), 5L);
                     List<TdOrder> tdOrders1 = tdOrderService.findByshopIdAndstatusId(diySite.getId(), 6L);
                     map.addAttribute("shop_orderFinish_"+diySite.getId(), tdOrders.size()+tdOrders1.size());
//                     map.addAttribute("shop_orderComment_"+diySite.getId(), TdDistributorService.ContdiysiteComment(diySite.getId()));
////                     int a  = TdDistributorService.ContdiysiteComment(diySite.getId());
//                     map.addAttribute("shop_serviceStars"+diySite.getId(), TdDistributorService.diysiteServiceStars(diySite.getId()));
                 }
			}
        }
        
        return "/client/shop_list";
    }
	
	@RequestMapping("/{id}")
    public String shop(@PathVariable Long id, ModelMap map, HttpServletRequest req){
	    tdCommonService.setHeader(map, req);
	    
	    map.addAttribute("shop", TdDistributorService.findOne(id));
	    
        return "/client/shop_detail";
    }
}
