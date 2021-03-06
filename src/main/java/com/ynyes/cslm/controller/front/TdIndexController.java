package com.ynyes.cslm.controller.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.cslm.entity.TdAdType;
import com.ynyes.cslm.entity.TdArticleCategory;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.service.TdAdService;
import com.ynyes.cslm.service.TdAdTypeService;
import com.ynyes.cslm.service.TdArticleCategoryService;
import com.ynyes.cslm.service.TdArticleService;
import com.ynyes.cslm.service.TdBrandService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.service.TdSettingService;
import com.ynyes.cslm.service.TdSiteLinkService;
import com.ynyes.cslm.util.ClientConstant;

/**
 * 前端首页控制
 *
 */
@Controller
@RequestMapping("/")
public class TdIndexController {

    @Autowired
    private TdCommonService tdCommonService;

    @Autowired
    private TdGoodsService tdGoodsService;

    @Autowired
    private TdArticleService tdArticleService;

    @Autowired
    private TdArticleCategoryService tdArticleCategoryService;

    @Autowired
    private TdProductCategoryService tdProductCategoryService;

    @Autowired
    private TdSiteLinkService tdSiteLinkService;

    @Autowired
    private TdAdTypeService tdAdTypeService;

    @Autowired
    private TdAdService tdAdService;

    @Autowired
    private TdBrandService tdBrandService;
    
    @Autowired
    private TdDistributorGoodsService tdDistributorGoodsService;
    
    @Autowired
    private TdDistributorService tdDistributorService;
    

    @RequestMapping
    public String index(HttpServletRequest req, Device device, ModelMap map) {
        
//        // 触屏
        if (device.isMobile() || device.isTablet()) {
            return "redirect:/touch/";
        }
        
        tdCommonService.setHeader(map, req);

        // 超市快讯
        List<TdArticleCategory> catList = tdArticleCategoryService
                .findByMenuId(10L);
        TdAdType adType = new TdAdType();
        
        // 是否选择超市
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
        	
        	map.addAttribute("recommed_index_page",tdDistributorGoodsService.findAll(distributorId, "isRecommendIndex", null, 0, 10));
        	
        	// 一级分类
            List<TdProductCategory> topCatList = tdProductCategoryService
                    .findByParentIdIsNullOrderBySortIdAsc();
            if (null != topCatList && topCatList.size() > 0) {
                map.addAttribute("top_category_list", topCatList);

                for (int i = 0; i < topCatList.size(); i++) {
                    TdProductCategory topCat = topCatList.get(i);

                    if (null != topCat) {
                        map.addAttribute( "top_cat_goods_page" + i,tdDistributorGoodsService.findAll(distributorId, "isRecommendType", topCat.getId(), 0, 10));
                    }
                }
            }
            
            // 首页大图轮播广告
            adType = tdAdTypeService.findByTitle("首页轮播大图广告");

            if (null != adType) {
                map.addAttribute("big_scroll_ad_list", tdAdService
                        .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),distributorId));
            }
            
         // 新品推荐广告
            adType = tdAdTypeService.findByTitle("新品推荐广告");

            if (null != adType) {
                map.addAttribute("new_goods_ad_list", tdAdService
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
        	map.addAttribute("recommed_index_page",tdDistributorGoodsService.findAll(null, "isSetRecommend", null, 0, 10));
        	
        	// 一级分类
            List<TdProductCategory> topCatList = tdProductCategoryService
                    .findByParentIdIsNullOrderBySortIdAsc();
            if (null != topCatList && topCatList.size() > 0) {
                map.addAttribute("top_category_list", topCatList);

                for (int i = 0; i < topCatList.size(); i++) {
                    TdProductCategory topCat = topCatList.get(i);

                    if (null != topCat) {
                    	map.addAttribute( "top_cat_goods_page" + i,tdDistributorGoodsService.findAll(null, "isRecommendCategory", topCat.getId(), 0, 10));
                    }
                }
            }
        	
         // 首页大图轮播广告
            adType = tdAdTypeService.findByTitle("首页轮播大图广告");

            if (null != adType) {
                map.addAttribute("big_scroll_ad_list", tdAdService
                        .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),0L));
            }
            
         // 新品推荐广告
            adType = tdAdTypeService.findByTitle("新品推荐广告");

            if (null != adType) {
                map.addAttribute("new_goods_ad_list", tdAdService
                        .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),0L));
            }
        	
        }
        

        // 
        if (null != catList && catList.size() > 0) {

            map.addAttribute("curing_page", tdArticleService
                    .findByMenuIdAndIsEnableOrderByIdDesc(11L, 0, ClientConstant.pageSize));
        }

        // 一楼商品中部广告
        adType = tdAdTypeService.findByTitle("一楼分类底部广告");

        if (null != adType) {
            map.addAttribute("index_1F_ad_list", tdAdService
                    .findByTypeIdAndIsValidTrueOrderBySortIdAsc(adType.getId()));
        }

        // 二楼商品中部广告
        adType = tdAdTypeService.findByTitle("二楼分类底部广告");

        if (null != adType) {
            map.addAttribute("index_2F_ad_list", tdAdService
                    .findByTypeIdAndIsValidTrueOrderBySortIdAsc(adType.getId()));
        }

        // 三楼商品中部广告
        adType = tdAdTypeService.findByTitle("三楼分类底部广告");

        if (null != adType) {
            map.addAttribute("index_3F_ad_list", tdAdService
                    .findByTypeIdAndIsValidTrueOrderBySortIdAsc(adType.getId()));
        }

        // 四楼商品中部广告
        adType = tdAdTypeService.findByTitle("四楼分类底部广告");

        if (null != adType) {
            map.addAttribute("index_4F_ad_list", tdAdService
                    .findByTypeIdAndIsValidTrueOrderBySortIdAsc(adType.getId()));
        }

        // 五楼商品中部广告
        adType = tdAdTypeService.findByTitle("五楼分类底部广告");

        if (null != adType) {
            map.addAttribute("index_5F_ad_list", tdAdService
                    .findByTypeIdAndIsValidTrueOrderBySortIdAsc(adType.getId()));
        }

        // 六楼商品中部广告
        adType = tdAdTypeService.findByTitle("六楼分类底部广告");

        if (null != adType) {
            map.addAttribute("index_6F_ad_list", tdAdService
                    .findByTypeIdAndIsValidTrueOrderBySortIdAsc(adType.getId()));
        }

        // 七楼商品中部广告
        adType = tdAdTypeService.findByTitle("七楼分类底部广告");

        if (null != adType) {
            map.addAttribute("index_7F_ad_list", tdAdService
                    .findByTypeIdAndIsValidTrueOrderBySortIdAsc(adType.getId()));
        }

        // 八楼商品中部广告
        adType = tdAdTypeService.findByTitle("八楼分类底部广告");

        if (null != adType) {
            map.addAttribute("index_8F_ad_list", tdAdService
                    .findByTypeIdAndIsValidTrueOrderBySortIdAsc(adType.getId()));
        }
        
        
        return "/client/index";
    }
    
    @RequestMapping(value="/index")
    public String index(Long id,HttpServletRequest req,ModelMap map){
    	if(null != id){
    		TdDistributor distributor = tdDistributorService.findOne(id);
    		if(null != distributor)
    		{
    			req.getSession().setAttribute("DISTRIBUTOR_ID", distributor.getId());
    			req.getSession().setAttribute("distributorTitle", distributor.getTitle());
    		}
    	}
    	return "redirect:/";
    }
    
}
