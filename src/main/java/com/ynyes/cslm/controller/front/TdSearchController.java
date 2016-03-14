package com.ynyes.cslm.controller.front;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ynyes.cslm.entity.TdAdType;
import com.ynyes.cslm.entity.TdArticleCategory;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdKeywords;
import com.ynyes.cslm.service.TdAdService;
import com.ynyes.cslm.service.TdAdTypeService;
import com.ynyes.cslm.service.TdArticleCategoryService;
import com.ynyes.cslm.service.TdArticleService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdKeywordsService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.util.ClientConstant;

/**
 * 商品检索
 * @author Sharon
 *
 */
@Controller
public class TdSearchController {
    
    @Autowired
    private TdCommonService tdCommonService;
    
    @Autowired
    private TdGoodsService tdGoodsService;
    
    @Autowired
    private TdKeywordsService tdKeywordsService;
    
    @Autowired
    private TdProductCategoryService tdProductCategoryService;
    
    @Autowired
    private TdArticleCategoryService tdArticleCategoryService;
    
    @Autowired
    private TdArticleService tdArticleService;
    
    @Autowired
    private TdAdTypeService tdAdTypeService;

    @Autowired
    private TdAdService tdAdService;
    
    @Autowired
    private TdDistributorGoodsService tdDistributorGoodsService;
    
    @RequestMapping(value="/search", method = RequestMethod.GET)
    public String list(String keywords, Integer page, HttpServletRequest req, ModelMap map){
        
        tdCommonService.setHeader(map, req);
        
        if (null == page || page < 0)
        {
            page = 0;
        }
        
        if (null != keywords)
        {
            TdKeywords key = tdKeywordsService.findByTitle(keywords);
            
            if (null != key)
            {
                if (null == key.getTotalSearch())
                {
                    key.setTotalSearch(1L);
                }
                else
                {
                    key.setTotalSearch(key.getTotalSearch() + 1L);
                }
                
                key.setLastSearchTime(new Date());
                
                tdKeywordsService.save(key);
            }
            else
            {
            	key =new TdKeywords();
            	key.setTotalSearch(1L);
            	key.setTitle(keywords);
            	key.setIsEnable(false);
            	key.setLastSearchTime(new Date());
            	key.setCreateTime(new Date());
            	tdKeywordsService.save(key);
            }
            
            
            if(null != req.getSession().getAttribute("DISTRIBUTOR_ID"))
            {
              Long distributorId = (Long)req.getSession().getAttribute("DISTRIBUTOR_ID");
              map.addAttribute("goods_page",tdDistributorGoodsService.searchAndDistributorIdAndIsOnSale(distributorId, keywords, true, page, ClientConstant.pageSize));
              
              map.addAttribute("hot_sale_list",tdDistributorGoodsService.findByDistributorIdAndIsOnSaleTrueBySoldNumberDesc(distributorId,0,10).getContent());
            }else{
            	map.addAttribute("goods_page", tdDistributorGoodsService.searchGoodsAndIsOnSale(keywords, true, page, ClientConstant.pageSize));
            	
            	// 热卖
            	map.addAttribute("hot_sale_list",tdDistributorGoodsService.findByIsOnSaleTrueBySoldNumberDesc(0,10).getContent());
            }
        }
        
        // 商城资讯
        List<TdArticleCategory> articleCatList = tdArticleCategoryService
                .findByMenuId(10L);

        if (null != articleCatList && articleCatList.size() > 0) {
            Long articleCatId = articleCatList.get(0).getId();

            map.addAttribute("news_page", tdArticleService
                    .findByMenuIdAndCategoryIdAndIsEnableOrderByIdDesc(10L,
                            articleCatId, 0, ClientConstant.pageSize));
        }
        
        map.addAttribute("pageId", page);
        map.addAttribute("keywords", keywords);
        
        // 列表页轮播广告
        TdAdType adType = tdAdTypeService.findByTitle("列表页轮播广告");

        if (null != adType) {
            map.addAttribute("list_scroll_ad_list", tdAdService
                    .findByTypeIdAndIsValidTrueOrderBySortIdAsc(adType.getId()));
        }
        
//        // 热卖推荐
//        map.addAttribute("hot_sale_list", tdGoodsService.findByIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(0, 10).getContent());   
        
        // 销量排行
        map.addAttribute("most_sold_list", tdGoodsService.findByIsOnSaleTrueOrderBySoldNumberDesc(0, 10).getContent());   
        
        return "/client/search_list";
    }
}
