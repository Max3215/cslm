package com.ynyes.cslm.touch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.cslm.entity.TdAdType;
import com.ynyes.cslm.entity.TdArticleCategory;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.service.TdAdService;
import com.ynyes.cslm.service.TdAdTypeService;
import com.ynyes.cslm.service.TdArticleCategoryService;
import com.ynyes.cslm.service.TdArticleService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdProductCategoryService;
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
    
    @Autowired
    private TdDistributorService tdDistributorService;
    
    @Autowired
    private TdDistributorGoodsService tdDistributorGoodsService;
    
    @Autowired
    private TdProductCategoryService tdProductCategoryService;
    
    @Autowired
    private TdArticleCategoryService tdArticleCategoryService;
    
    @Autowired
    private TdArticleService tdArticleService;

    @RequestMapping
    public String index(HttpServletRequest req, ModelMap map, String username, Integer app) {
    	
    	tdCommonService.setHeader(map, req);
    	if (null != username) {
    		req.getSession().setAttribute("username", username);
		}
    	
    	// 超市快讯
        List<TdArticleCategory> catList = tdArticleCategoryService
                .findByMenuId(10L);
        TdAdType adType = new TdAdType();
    	
    	 if(null != req.getSession().getAttribute("DISTRIBUTOR_ID"))
         {
         	Long distributorId= (Long)req.getSession().getAttribute("DISTRIBUTOR_ID");
         	if (null != catList && catList.size() > 0) {
                 for (TdArticleCategory tdCat : catList)
                 {
                     if (null != tdCat.getTitle() && tdCat.getTitle().equals("超市快讯"))
                     {
                         map.addAttribute("news_page", tdArticleService
                                 .findByMenuIdAndCategoryIdAndDistributorIdAndIsEnableOrderByIdDesc(10L,
                                         tdCat.getId(),distributorId, 0, ClientConstant.pageSize));
                         break;
                     }
                     
                 }
             }
         	
         	 map.addAttribute("recommed_index_page",tdDistributorGoodsService.findByDistribuorIdAndIsRecommendIndexTrueOrderByOnSaleTime(distributorId, 0, 10));
         	// 一级分类
             List<TdProductCategory> topCatList = tdProductCategoryService
                     .findByParentIdIsNullOrderBySortIdAsc();
             if (null != topCatList && topCatList.size() > 0) {
                 map.addAttribute("top_category_list", topCatList);

                 for (int i = 0; i < topCatList.size(); i++) {
                     TdProductCategory topCat = topCatList.get(i);

                     if (null != topCat) {
                         map.addAttribute( "top_cat_goods_page" + i,
                         		tdDistributorGoodsService.findByDistributorIdAndCategoryIdAndIsOnSale(distributorId, topCat.getId(), true, 0, 10));
                     }
                 }
             }
             
             // 首页新品广告
             adType = tdAdTypeService.findByTitle("触屏新品推荐右侧广告");

             if (null != adType) {
                 map.addAttribute("recommend_right_ad_list", tdAdService
                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),distributorId));
             }
             adType = tdAdTypeService.findByTitle("触屏新品推荐上侧广告");

             if (null != adType) {
                 map.addAttribute("recommend_top_ad_list", tdAdService
                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),distributorId));
             }
             adType = tdAdTypeService.findByTitle("触屏新品推荐下侧广告");

             if (null != adType) {
                 map.addAttribute("recommend_bot_ad_list", tdAdService
                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),distributorId));
             }
             
          // 超市快讯广告
             adType = tdAdTypeService.findByTitle("触屏超市快讯图");

             if (null != adType) {
                 map.addAttribute("news_ad_list", tdAdService
                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),distributorId));
             }
             
             // 触屏首页轮播广告
             adType = tdAdTypeService.findByTitle("触屏首页轮播广告");

             if (null != adType) {
                 map.addAttribute("banner_ad_list", tdAdService
                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),distributorId));
             }
         	
         }else{ // 为选择超市
         	if (null != catList && catList.size() > 0) {
         		for (TdArticleCategory tdCat : catList)
         		{
         			if (null != tdCat.getTitle() && tdCat.getTitle().equals("超市快讯"))
         			{
         				map.addAttribute("news_page", tdArticleService
                                 .findByMenuIdAndCategoryIdAndDistributorIdAndIsEnableOrderByIdDesc(10L,
                                         tdCat.getId(),null, 0, ClientConstant.pageSize));
         				break;
         			}
         			
         		}
         	}
         	 map.addAttribute("recommed_index_page",tdDistributorGoodsService.findAllByIsRecommendIndexTrueOrderByOnSaleTime(0, 10));
         	
         	// 一级分类
             List<TdProductCategory> topCatList = tdProductCategoryService
                     .findByParentIdIsNullOrderBySortIdAsc();
             if (null != topCatList && topCatList.size() > 0) {
                 map.addAttribute("top_category_list", topCatList);

                 for (int i = 0; i < topCatList.size(); i++) {
                     TdProductCategory topCat = topCatList.get(i);

                     if (null != topCat) {
                         map.addAttribute( "top_cat_goods_page" + i,
                         		tdDistributorGoodsService.findByCategoryIdAndIsOnSale(topCat.getId(), true, 0, 10));
                     }
                 }
             }
         	
          // 新品推荐
          // 首页新品广告
             adType = tdAdTypeService.findByTitle("触屏新品推荐右侧广告");

             if (null != adType) {
                 map.addAttribute("recommend_right_ad_list", tdAdService
                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),null));
             }
             adType = tdAdTypeService.findByTitle("触屏新品推荐上侧广告");

             if (null != adType) {
                 map.addAttribute("recommend_top_ad_list", tdAdService
                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),null));
             }
             adType = tdAdTypeService.findByTitle("触屏新品推荐下侧广告");

             if (null != adType) {
                 map.addAttribute("recommend_bot_ad_list", tdAdService
                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),null));
             }
             
          // 超市快讯广告
             adType = tdAdTypeService.findByTitle("触屏超市快讯图");

             if (null != adType) {
                 map.addAttribute("news_ad_list", tdAdService
                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),null));
             }
             
             // 新品推荐广告
             adType = tdAdTypeService.findByTitle("触屏首页轮播广告");

             if (null != adType) {
                 map.addAttribute("banner_ad_list", tdAdService
                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),null));
             }
             
             map.addAttribute("recommed_index_page",tdDistributorGoodsService.findAllByIsRecommendIndexTrueOrderByOnSaleTime(0, 10));
         	
         }
    	
           
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
