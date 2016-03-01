package com.ynyes.cslm.touch;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.cslm.entity.TdAdType;
import com.ynyes.cslm.service.TdAdService;
import com.ynyes.cslm.service.TdAdTypeService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.util.ClientConstant;

@Controller
@RequestMapping("/touch")
public class TdTouchIndexController {
	@Autowired
    private TdCommonService tdCommonService;

    @Autowired
    private TdGoodsService tdGoodsService;

    @Autowired
    private TdAdTypeService tdAdTypeService;

    @Autowired
    private TdAdService tdAdService;

    @RequestMapping
    public String index(HttpServletRequest req, ModelMap map, String username, Integer app) {
    	
    	tdCommonService.setHeader(map, req);
    	if (null != username) {
    		req.getSession().setAttribute("username", username);
		}        
           
        // 首页顶部轮播广告
        TdAdType tdAdType = tdAdTypeService.findByTitle("触屏首页轮播广告");

//        if (null != tdAdType) {
//            map.addAttribute("banner_ad_list",
//                    tdAdService.findByTypeIdAndEndtimeAfter(tdAdType.getId()));
//        }
        
        // 顶部广告1
        tdAdType = tdAdTypeService.findByTitle("触屏首页顶部广告");
        
//        if (null != tdAdType) {
//            map.addAttribute("top_ad_list",
//                    tdAdService.findByTypeIdAndEndtimeAfter(tdAdType.getId()));
//        }                      
        
        // 热卖推荐商品
//        map.addAttribute("hot_recommend_list", tdGoodsService.findByIshotTrueAndIsOnSaleTrueOrderBySortIdAsc(0,ClientConstant.pageSize).getContent());
//        
//        // 热卖排行商品
//        map.addAttribute("hot_sale_list", tdGoodsService.findTop10ByIsOnSaleTrueOrderBySoldNumberDesc());
        
        // 商品推荐广告位

        tdAdType = tdAdTypeService.findByTitle("触屏商品推荐广告");
        
//        if (null != tdAdType) {
//            map.addAttribute("goodsRecommend_ad_list",
//                    tdAdService.findByTypeIdAndEndtimeAfter(tdAdType.getId()));
//        }                 
        
        // 商品推荐广告位

        tdAdType = tdAdTypeService.findByTitle("触屏商品分类广告");
        
//        if (null != tdAdType) {
//            map.addAttribute("goodsCategory_ad_list",
//                    tdAdService.findByTypeIdAndEndtimeAfter(tdAdType.getId()));
//        } 
        
        // 精选分类广告位
        tdAdType = tdAdTypeService.findByTitle("触屏精选分类广告");
        
//        if (null != tdAdType) {
//            map.addAttribute("selection_ad_list",
//                    tdAdService.findByTypeIdAndEndtimeAfter(tdAdType.getId()));
//        }   
        
        // app标志位
        if (null != app) {
        	map.addAttribute("app", app);
        	req.getSession().setAttribute("app", app);
		}
        
        return "/touch/index";
    }
    
    @RequestMapping("/category/list")
    public String product(
            ModelMap map, HttpServletRequest req) {

        tdCommonService.setHeader(map, req);
        
        return "/touch/category_list";
    }
}
