package com.ynyes.cslm.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.ynyes.cslm.entity.TdAdType;
import com.ynyes.cslm.entity.TdArticleCategory;
import com.ynyes.cslm.entity.TdDemand;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.entity.TdSetting;

@Service
public class TdCommonService {

    @Autowired
    private TdSettingService tdSettingService;

    @Autowired
    private TdKeywordsService tdKeywordsService;

    @Autowired
    private TdCartGoodsService tdCartGoodsService;

    @Autowired
    private TdNaviBarItemService tdNaviBarItemService;

    @Autowired
    private TdArticleCategoryService tdArticleCategoryService;

    @Autowired
    private TdArticleService tdArticleService;

    @Autowired
    private TdProductCategoryService tdProductCategoryService;

    @Autowired
    private TdSiteLinkService tdSiteLinkService;

    @Autowired
    private TdUserService tdUserService;
    
    @Autowired
    private TdServiceItemService tdServiceItemService;
    
    @Autowired
    private TdAdTypeService tdAdTypeService;
    
    @Autowired
    private TdAdService tdAdService;
    
//    //团购 zhangji
//    @Autowired
//    private TdDemandService tdDemandService;
    
    @Autowired
    private TdDistributorService tdDistributorService;

    public void setHeader(ModelMap map, HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("username");

        // 用户名，购物车
        if (null != username) {
            map.addAttribute("username", username);
            map.addAttribute("user",
                    tdUserService.findByUsernameAndIsEnabled(username));
            map.addAttribute("cart_goods_list",
                    tdCartGoodsService.updateGoodsInfo(tdCartGoodsService.findByUsername(username)));
        } else {
            map.addAttribute("cart_goods_list",
                    tdCartGoodsService.updateGoodsInfo(tdCartGoodsService.findByUsername(req.getSession().getId())));
        }
        
        // 顶部小图广告
        TdAdType adType = tdAdTypeService.findByTitle("搜索框左侧小图广告");
        if (null != adType) {
            map.addAttribute("top_small_ad_list", tdAdService
                    .findByTypeIdAndIsValidTrueOrderBySortIdAsc(adType.getId()));
        }
        
        // 网站基本信息
        TdSetting setting = tdSettingService.findTopBy();
        
        // 统计访问量
        if (null != setting && null == req.getSession().getAttribute("countedTotalVisits"))
        {
            req.getSession().setAttribute("countedTotalVisits", "yes");
            if (null == setting.getTotalVisits())
            {
                setting.setTotalVisits(1L);
            }
            else
            {
                setting.setTotalVisits(setting.getTotalVisits() + 1L);
            }
            setting = tdSettingService.save(setting);
        }
        
        // 统计在线人数
        if (null != setting && null == req.getSession().getAttribute("countedTotalOnlines"))
        {
            req.getSession().setAttribute("countedTotalOnlines", "yes");
            if (null == setting.getTotalOnlines())
            {
                setting.setTotalOnlines(1L);
            }
            else
            {
                setting.setTotalOnlines(setting.getTotalOnlines() + 1L);
            }
            setting = tdSettingService.save(setting);
        }

        map.addAttribute("site", setting);
        map.addAttribute("keywords_list",
                tdKeywordsService.findByIsEnableTrueOrderBySortIdAsc());

        // 全部商品分类，取三级
        List<TdProductCategory> topCatList = tdProductCategoryService
                .findByParentIdIsNullOrderBySortIdAsc();
        map.addAttribute("top_cat_list", topCatList);

        if (null != topCatList && topCatList.size() > 0) 
        {
            for (int i = 0; i < topCatList.size(); i++) 
            {
                TdProductCategory topCat = topCatList.get(i);
                List<TdProductCategory> secondLevelList = tdProductCategoryService
                        .findByParentIdOrderBySortIdAsc(topCat.getId());
                map.addAttribute("second_level_" + i + "_cat_list", secondLevelList);

                if (null != secondLevelList && secondLevelList.size() > 0) 
                {
                    for (int j=0; j<secondLevelList.size(); j++)
                    {
                        TdProductCategory secondLevelCat = secondLevelList.get(j);
                        List<TdProductCategory> thirdLevelList = tdProductCategoryService
                                .findByParentIdOrderBySortIdAsc(secondLevelCat.getId());
                        map.addAttribute("third_level_" + i + j + "_cat_list", thirdLevelList);
                    }
                }
            }
        }

        // 导航菜单
        map.addAttribute("navi_item_list",
                tdNaviBarItemService.findByIsEnableTrueOrderBySortIdAsc());
        
        // 商城服务
        map.addAttribute("service_item_list", tdServiceItemService.findByIsEnableTrueOrderBySortIdAsc());

        // 帮助中心
        Long helpId = 12L;

        map.addAttribute("help_id", helpId);
        
        List<TdArticleCategory> articleCategoryList = tdArticleCategoryService.findByMenuId(helpId);
	    map.addAttribute("td_art_list",articleCategoryList);
	    
	    if(null != articleCategoryList && articleCategoryList.size() > 0)
	    {
	    	for (int i = 0; i < articleCategoryList.size()&& i<4 ; i++)
	    	{
	    		//分类文章
				TdArticleCategory category = articleCategoryList.get(i);
				map.addAttribute("second_level_"+i+"_category_list",
						tdArticleService.findByCategoryId(category.getId()));
			}
	    }
        

//        List<TdArticleCategory> level0HelpList = tdArticleCategoryService
//                .findByMenuIdAndParentId(helpId, 0L);
//
//        map.addAttribute("help_level0_cat_list", level0HelpList);
//
//        if (null != level0HelpList) {
//
//            for (int i = 0; i < level0HelpList.size() && i < 4; i++) {
//                TdArticleCategory articleCat = level0HelpList.get(i);
//                map.addAttribute("help_" + i + "_cat_list",
//                        tdArticleCategoryService.findByMenuIdAndParentId(
//                                helpId, articleCat.getId()));
//            }
//        }

        // 友情链接
        map.addAttribute("site_link_list",
                tdSiteLinkService.findByIsEnableTrue());
        
//        map.addAttribute("dis_list", tdDistributorService.findByProvince("云南"));
//        
//        List<TdDistributor> list = tdDistributorService.findByProvince("云南");
//        
//        for (int i = 0; i < list.size(); i++) {
//			System.err.println(list.get(i).getProvince());
//			System.err.println(list.get(i).getCity());
//			System.err.println(list.get(i).getDisctrict());
//			System.err.println("---------------");
//		}
        
//        //团购留言     
//        List<TdDemand> tdDemand = tdDemandService.findByStatusIdAndIsShowable();
//        map.addAttribute("demand_list",tdDemand);
        
        //全部超市
        List<TdDistributor> dis_list = tdDistributorService.findByIsEnableTrue();
        
        map.addAttribute("dis_list",dis_list);
        
        
        if(null != dis_list && dis_list.size() > 0)
        {
        	ArrayList<String> citylist = new ArrayList<>();			//存市的集合
        	for (int i = 0; i < dis_list.size(); i++) 
        	{
        		//判断集合是否已经存入市
        		if(!citylist.contains(dis_list.get(i).getCity()))		
        		{
        			citylist.add(dis_list.get(i).getCity());		//存入市
        		}
        	}
        	map.addAttribute("city_list", citylist);
        	
        	for (int j = 0; j < citylist.size(); j++)
        	{
        		List<TdDistributor> dis_discaric_list = tdDistributorService.findByCityAndIsEnableTrueOrderBySortIdAsc(citylist.get(j));
        		//查询该市所有超市
    			if(null != dis_discaric_list && dis_discaric_list.size() > 0)
    			{
    				ArrayList<String> disctrictList = new ArrayList<>();	//存区的集合
    				for(int l = 0 ; l< dis_discaric_list.size();l++)
    				{
    					//判断是否存入该区
    					if(!disctrictList.contains(dis_discaric_list.get(l).getDisctrict()))
    					{
    						disctrictList.add(dis_discaric_list.get(l).getDisctrict()); 	//存入区
    					}
    				}
    				map.addAttribute("disc_"+j+"_list", disctrictList);
    				
    				//查询内所有超市
    				for (int k = 0; k < disctrictList.size(); k++) 
    				{
    					map.addAttribute("distributor_"+j+k+"_list", tdDistributorService.findBydisctrict(disctrictList.get(k)));
						
					}
    			}
        	}
        
        }
    }
}
