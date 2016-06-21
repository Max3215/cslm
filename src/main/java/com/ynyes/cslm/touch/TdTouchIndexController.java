package com.ynyes.cslm.touch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdAdType;
import com.ynyes.cslm.entity.TdArticleCategory;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.service.TdAdService;
import com.ynyes.cslm.service.TdAdTypeService;
import com.ynyes.cslm.service.TdArticleCategoryService;
import com.ynyes.cslm.service.TdArticleService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.util.ClientConstant;
import com.ynyes.cslm.util.Cnvter;

@Controller
@RequestMapping("/touch")
public class TdTouchIndexController {
	@Autowired
    private TdCommonService tdCommonService;

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
         	
         	map.addAttribute("distributor", tdDistributorService.findOne(distributorId));
         	
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
         	
         	 map.addAttribute("recommed_index_page",tdDistributorGoodsService.findByIdAndIsOnSaleOrderByLeftNumberDesc(distributorId,true, 0, 40));
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
         	map.addAttribute("recommed_index_page",tdDistributorGoodsService.findByIsOnSaleTrueAndIsTouchHotTrueOrderByOnSaleTimeDesc(0, 40));
         	
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
                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),0L));
             }
             adType = tdAdTypeService.findByTitle("触屏新品推荐上侧广告");

             if (null != adType) {
                 map.addAttribute("recommend_top_ad_list", tdAdService
                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),0L));
             }
             adType = tdAdTypeService.findByTitle("触屏新品推荐下侧广告");

             if (null != adType) {
                 map.addAttribute("recommend_bot_ad_list", tdAdService
                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),0L));
             }
             
          // 超市快讯广告
             adType = tdAdTypeService.findByTitle("触屏超市快讯图");

             if (null != adType) {
                 map.addAttribute("news_ad_list", tdAdService
                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),0L));
             }
             
             // 新品推荐广告
             adType = tdAdTypeService.findByTitle("触屏首页轮播广告");

             if (null != adType) {
                 map.addAttribute("banner_ad_list", tdAdService
                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),0L));
             }
             
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
    	return "redirect:/touch";
    }
    
    @RequestMapping(value="/distributor/change" , method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> changeDistributor(HttpServletRequest req,Long disId)
	{
		Map<String,Object> res = new HashMap<>();
		if(null ==disId)
		{
			res.put("msg", "请选择超市");
			return res;
		}
		
		TdDistributor distributor = tdDistributorService.findOne(disId);
		 if(null == distributor)
		 {
			 res.put("msg","选择的超市不存在");
			 return res;
		 }
		 
		req.getSession().setAttribute("DISTRIBUTOR_ID", distributor.getId());
		req.getSession().setAttribute("distributorTitle", distributor.getTitle());
		return res;
	}
    
    @RequestMapping("/disout")
	public String distributorOut(HttpServletRequest request) {
//		request.getSession().invalidate();
		request.getSession().removeAttribute("DISTRIBUTOR_ID");
		request.getSession().removeAttribute("distributorTitle");
		return "redirect:/touch";
	}
    
    /**
     * 根据定位缩小超市范围
     * @author Max
     * 
     */
    @RequestMapping(value="/distance",method = RequestMethod.POST)
    public String mapdistance(Double lng,Double lat,HttpServletRequest req,ModelMap map){

    	Map<String,Double> inmap = new HashMap<>();
    	Map<String,Double> moremap = new HashMap<>();
    	List<TdDistributor> disList = tdDistributorService.findByIsEnableTrue();
    	
    	if(null != disList)
    	{
    		for (TdDistributor tdDistributor : disList) {
				if(null != tdDistributor.getLatitude() && null != tdDistributor.getLongitude())
				{
					double d = Cnvter.getDistance(lng, lat, tdDistributor.getLongitude(), tdDistributor.getLatitude());
					
					if(d < 5*1000){
						inmap.put(tdDistributor.getId()+"", d);
					}else{
						moremap.put(tdDistributor.getId()+"", d);
					}
				}
			}
    	}
    	 
    	List<Long> ids = new ArrayList<>(); // 定义一个list存范围内Id
    	List<Long> outhers = new ArrayList<>(); // 定义一个list存范围外id
    	
    	ValueComparator bvc =  new ValueComparator(inmap);  
    	TreeMap<String,Double> sorted_map = new TreeMap<String,Double>(bvc);  
    	sorted_map.putAll(inmap);
    	Set<String> set = sorted_map.keySet();
    	for (String string : set) {
			ids.add(Long.parseLong(string));
		}
    	
    	ValueComparator mbvc =  new ValueComparator(moremap);
    	TreeMap<String,Double> more_map = new TreeMap<String,Double>(mbvc); 
    	more_map.putAll(moremap);
    	Set<String> more = more_map.keySet();
    	for (String str : more) {
			outhers.add(Long.parseLong(str));
    	}
    	
    	map.addAttribute("shop_list", tdDistributorService.findAll(ids));
    	map.addAttribute("more_list", tdDistributorService.findAll(outhers));
    	map.addAttribute("index", true);
    	
    	return "/touch/shop_list";
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
