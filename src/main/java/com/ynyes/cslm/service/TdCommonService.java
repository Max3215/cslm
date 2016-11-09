package com.ynyes.cslm.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.ynyes.cslm.entity.TdAdType;
import com.ynyes.cslm.entity.TdArticleCategory;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.entity.TdSetting;
import com.ynyes.cslm.util.Cnvter;

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
    
    @Autowired
    private TdDistributorService tdDistributorService;
    
    @Autowired
    private TdDistributorGoodsService tdDistributorGoodsService;

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
        

        map.addAttribute("site", setting);
        map.addAttribute("keywords_list",
                tdKeywordsService.findByIsEnableTrueOrderBySortIdAsc());

        // 全部商品分类，取三级
        Long disId =(Long)req.getSession().getAttribute("DISTRIBUTOR_ID");
        if(null == disId){
        	List<TdProductCategory> topCatList = tdProductCategoryService .findByParentIdIsNullAndIsEnableTrueOrderBySortIdAsc();
            map.addAttribute("top_cat_list", topCatList);

            if (null != topCatList && topCatList.size() > 0) 
            {
                for (int i = 0; i < topCatList.size(); i++) 
                {
                    TdProductCategory topCat = topCatList.get(i);
                    List<TdProductCategory> secondLevelList = tdProductCategoryService
                            .findByParentIdAndIsEnableTrueOrderBySortIdAsc(topCat.getId());
                    map.addAttribute("second_level_" + i + "_cat_list", secondLevelList);

                    if (null != secondLevelList && secondLevelList.size() > 0) 
                    {
                        for (int j=0; j<secondLevelList.size(); j++)
                        {
                            TdProductCategory secondLevelCat = secondLevelList.get(j);
                            List<TdProductCategory> thirdLevelList = tdProductCategoryService
                                    .findByParentIdAndIsEnableTrueOrderBySortIdAsc(secondLevelCat.getId());
                            map.addAttribute("third_level_" + i + j + "_cat_list", thirdLevelList);
                        }
                    }
                }
            }
        }else{
        	CategoryByDistributor(disId,map);
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
	    	for (int i = 0; i < articleCategoryList.size()&& i<5 ; i++)
	    	{
	    		//分类文章
				TdArticleCategory category = articleCategoryList.get(i);
				map.addAttribute("second_level_"+i+"_category_list",
						tdArticleService.findByCategoryId(category.getId()));
			}
	    }
        

        // 友情链接
        map.addAttribute("site_link_list",
                tdSiteLinkService.findByIsEnableTrue());
        
        
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
    
    private void CategoryByDistributor(Long disId, ModelMap map) {

    	// 查询所有商品出售中商品集合
    	List<TdDistributorGoods> saleGoodsList = tdDistributorGoodsService.findByDisIdAndIsOnSaleTrue(disId);

    	// 查询所有一级类别
    	List<TdProductCategory> topCatList = tdProductCategoryService .findByParentIdIsNullAndIsEnableTrueOrderBySortIdAsc();
    	
    	List<TdProductCategory> categoryList = new ArrayList<TdProductCategory>();
    	if(null != saleGoodsList){
    		// 循环一级类别
    		for (TdProductCategory tdProductCategory : topCatList) {
				// 循环所有在售商品
				for (TdDistributorGoods saleGoods : saleGoodsList) {
    					// 如果新类别集合等于一级类别集合，表示所有类别都在售，不在继续循环
					if(categoryList.size() < topCatList.size()){
    					String catId = "["+tdProductCategory.getId()+"]";
    					if(null != saleGoods.getCategoryIdTree() && saleGoods.getCategoryIdTree().contains(catId)){
    						// 如果新集合没有此类别，则添加
    						if(!categoryList.contains(tdProductCategory)){
    							categoryList.add(tdProductCategory);
    							continue;
    						}
    					}
    				}
    			}
			}
    	}
    	
        map.addAttribute("top_cat_list", categoryList);

        if (null != categoryList && categoryList.size() > 0) 
        {
            for (int i = 0; i < categoryList.size(); i++) 
            {
                TdProductCategory topCat = topCatList.get(i);
                List<TdProductCategory> secondLevelList = tdProductCategoryService
                        .findByParentIdAndIsEnableTrueOrderBySortIdAsc(topCat.getId());
                map.addAttribute("second_level_" + i + "_cat_list", secondLevelList);

                if (null != secondLevelList && secondLevelList.size() > 0) 
                {
                    for (int j=0; j<secondLevelList.size(); j++)
                    {
                        TdProductCategory secondLevelCat = secondLevelList.get(j);
                        List<TdProductCategory> thirdLevelList = tdProductCategoryService
                                .findByParentIdAndIsEnableTrueOrderBySortIdAsc(secondLevelCat.getId());
                        map.addAttribute("third_level_" + i + j + "_cat_list", thirdLevelList);
                    }
                }
            }
        }
	}
	public void mapdistance(Double lng,Double lat,HttpServletRequest req,ModelMap map){

    	Map<String,Double> inmap = new HashMap<>();
//    	Map<String,Double> moremap = new HashMap<>();
    	List<TdDistributor> disList = tdDistributorService.findByIsEnableTrue();
    	
    	if(null == disList)
    	{
    		return;
    	}
    	for (TdDistributor tdDistributor : disList) {
    		if(null != tdDistributor.getLatitude() && null != tdDistributor.getLongitude())
    		{
    			double d = Cnvter.getDistance(lng, lat, tdDistributor.getLongitude(), tdDistributor.getLatitude());
    			
//					if(d < 5*1000){
    			inmap.put(tdDistributor.getId()+"", d);
//					}else{
//						moremap.put(tdDistributor.getId()+"", d);
//					}
    		}
    	}
    	 
    	List<Long> ids = new ArrayList<>(); // 定义一个list存范围内Id
    //	List<Long> outhers = new ArrayList<>(); // 定义一个list存范围外id
    	
    	ValueComparator bvc =  new ValueComparator(inmap);  
    	TreeMap<String,Double> sorted_map = new TreeMap<String,Double>(bvc);  
    	sorted_map.putAll(inmap);
    	Set<String> set = sorted_map.keySet();
    	for (String string : set) {
			ids.add(Long.parseLong(string));
		}
    	
//    	ValueComparator mbvc =  new ValueComparator(moremap);
//    	TreeMap<String,Double> more_map = new TreeMap<String,Double>(mbvc); 
//    	more_map.putAll(moremap);
//    	Set<String> more = more_map.keySet();
//    	for (String str : more) {
//			outhers.add(Long.parseLong(str));
//    	}
    	List<TdDistributor> shopList = new ArrayList<>();
    	
    	for (Long id : ids) {
			for (TdDistributor tdDistributor : disList) {
				if(tdDistributor.getId() == id){
					shopList.add(tdDistributor);
				}
			}
		}
    	
//    	map.addAttribute("shop_list", tdDistributorService.findAll(ids));
    	map.addAttribute("shop_list",shopList);
//    	map.addAttribute("more_list", tdDistributorService.findAll(outhers));
    	map.addAttribute("index", true);
    	
    	req.getSession().setAttribute("lng", lng);
    	req.getSession().setAttribute("lat", lat);
    	
    }
    
    /**
     * map 按value值排序
     * @author Max
     *
     */
    class ValueComparator implements Comparator<String> {  
  	  
        Map<String, Double> base;  
        public ValueComparator(Map<String, Double> map) {  
            this.base = map;  
        }  
      
	    public int compare(String a, String b) {  
	        if (base.get(a) <= base.get(b)) {  
	            return -1;  
	        } else {  
	            return 1;  
	        } 
	    }  
    } 
}
