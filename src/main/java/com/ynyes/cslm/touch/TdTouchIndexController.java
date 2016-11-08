package com.ynyes.cslm.touch;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdAdType;
import com.ynyes.cslm.entity.TdArticleCategory;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.entity.TdSetting;
import com.ynyes.cslm.service.TdAdService;
import com.ynyes.cslm.service.TdAdTypeService;
import com.ynyes.cslm.service.TdArticleCategoryService;
import com.ynyes.cslm.service.TdArticleService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.service.TdSettingService;
import com.ynyes.cslm.util.CookieUtil;
import com.ynyes.cslm.util.SiteMagConstant;

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
    
    @Autowired
    private TdSettingService tdSettingService;

    @RequestMapping
    public String index(HttpServletRequest req, ModelMap map, HttpServletResponse response, Integer app) {
    	
    	tdCommonService.setHeader(map, req);
    	
    	String username = (String) req.getSession().getAttribute("username");
    	Double lng = (Double)req.getSession().getAttribute("lng");
    	Double lat = (Double)req.getSession().getAttribute("lat");
    	Boolean ISF = (Boolean)req.getSession().getAttribute("ISF");
    	
    	if(null == username ){
    		try {
    			CookieUtil.readCookieAndLogon(req, response);
    		} catch (Exception e) {
    			e.printStackTrace();
    		} 
    	}
    	
    	if(null == ISF){
    		req.getSession().setAttribute("ISF", true);
    	}else{
    		req.getSession().setAttribute("ISF", false);
    	}
    	
		if(null != lng && null != lat){
			tdCommonService.mapdistance(lng, lat, req, map);
		}
    	
    	// 超市快讯
        List<TdArticleCategory> catList = tdArticleCategoryService
                .findByMenuId(10L);
        TdAdType adType = new TdAdType();
    	
        Long distributorId =0L;
    	 if(null != req.getSession().getAttribute("DISTRIBUTOR_ID"))
         {
         	distributorId= (Long)req.getSession().getAttribute("DISTRIBUTOR_ID");
         	
         	map.addAttribute("distributor", tdDistributorService.findOne(distributorId));
         	
         	if (null != catList && catList.size() > 0) {
                 for (TdArticleCategory tdCat : catList)
                 {
                     if (null != tdCat.getTitle() && tdCat.getTitle().equals("超市快讯"))
                     {
                         map.addAttribute("news_page", tdArticleService
                                 .findByMenuIdAndCategoryIdAndDistributorIdAndIsEnableOrderByIdDesc(10L,tdCat.getId(),distributorId, 0, 5));
                         break;
                     }
                 }
             }
         }else{ // 为选择超市
         	if (null != catList && catList.size() > 0) {
         		for (TdArticleCategory tdCat : catList)
         		{
         			if (null != tdCat.getTitle() && tdCat.getTitle().equals("超市快讯"))
         			{
         				map.addAttribute("news_page", tdArticleService
                                 .findByMenuIdAndCategoryIdAndDistributorIdAndIsEnableOrderByIdDesc(10L,tdCat.getId(),null, 0, 5));
         				break;
         			}
         			
         		}
         	}
         }	
    	 
    	 map.addAttribute("tag_goodsList", tdDistributorGoodsService.findAll(distributorId));
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
         	
//          // 首页新品广告
//             adType = tdAdTypeService.findByTitle("触屏新品推荐右侧广告");
//
//             if (null != adType) {
//                 map.addAttribute("recommend_right_ad_list", tdAdService
//                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),0L));
//             }
//             adType = tdAdTypeService.findByTitle("触屏新品推荐上侧广告");
//
//             if (null != adType) {
//                 map.addAttribute("recommend_top_ad_list", tdAdService
//                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),0L));
//             }
//             adType = tdAdTypeService.findByTitle("触屏新品推荐下侧广告");
//
//             if (null != adType) {
//                 map.addAttribute("recommend_bot_ad_list", tdAdService
//                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),0L));
//             }
//             
//          // 超市快讯广告
//             adType = tdAdTypeService.findByTitle("触屏超市快讯图");
//
//             if (null != adType) {
//                 map.addAttribute("news_ad_list", tdAdService
//                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),0L));
//             }
//             
//             // 新品推荐广告
//             adType = tdAdTypeService.findByTitle("触屏首页轮播广告");
//
//             if (null != adType) {
//                 map.addAttribute("banner_ad_list", tdAdService
//                         .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),0L));
//             }
//             
//         }
    	
           
        // app标志位
        if (null != app) {
        	map.addAttribute("app", app);
        	req.getSession().setAttribute("app", app);
        	if(app == 1){
        		req.getSession().setAttribute("isIOS", true);
        	}
		}
        
        
        
        return "/touch/index";
    }
    
    @RequestMapping(value="/search/goods",method=RequestMethod.POST)
    public String categoryGoods(Long catId,HttpServletRequest req,ModelMap map){
    	
    	Long disId = (Long)req.getSession().getAttribute("DISTRIBUTOR_ID");
    	if(null == disId){
    		map.addAttribute("goodsPage",tdDistributorGoodsService.findAll(disId, "isRecommendCategory", catId, 0, 40));
    	}else{
    		map.addAttribute("goodsPage",tdDistributorGoodsService.findAll(disId, "isRecommendType", catId, 0, 40));
    	}
    
    	return "/touch/index_goods";
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

    	tdCommonService.mapdistance(lng, lat, req, map);
    	
    	return "/touch/shop_list";
    }
    
    
    // 扫进入二维码下载页面
    @RequestMapping(value="/download/android/apk")    
    public String getApk1(HttpServletRequest req, ModelMap map, HttpServletResponse resp){
    	
    	tdCommonService.setHeader(map, req);
    	
    	// 首页新品广告
        TdAdType adType = tdAdTypeService.findByTitle("下载页面展示");

        if (null != adType) {
            map.addAttribute("down_ad_list", tdAdService
                    .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),0L));
        }
    	
    	return "/touch/down";
    }
    
    // 安卓下载
    @RequestMapping(value="/download")
    public String download(HttpServletRequest req,ModelMap map,HttpServletResponse resp){
    	String url = SiteMagConstant.apkPath;
    	
    	TdSetting tdSetting = tdSettingService.findTopBy();
    	String filename = tdSetting.getAndroidUrl();
    	
    	if (download(filename, url, resp)) {
			return null;
		}    	
    	return null ;
    }
    
    // android 版本更新
    @RequestMapping(value="/android/update")
    @ResponseBody
    public Map<String, Object> androidupdate(ModelMap map,
			  								HttpServletRequest req){
    	Map<String, Object> res = new HashMap<String, Object>();
        
        res.put("code", 1);

        TdSetting tdSetting = tdSettingService.findTopBy();
        
        if (null != tdSetting.getAndroidVersion()) {
			res.put("version", tdSetting.getAndroidVersion());
		}
        if (null != tdSetting.getAndroidUrl()) {
        	res.put("downloadUrl", "/android/apk/"+ tdSetting.getAndroidUrl());
		}
        res.put("des", tdSetting.getUpdateinfo());
        res.put("time", tdSetting.getUpdataTime());
               
        res.put("code", 0);
        
        return res;
    }
    
    // 获取Android apk
    @RequestMapping(value="/android/apk/{filename}")    
    public String getApk(@PathVariable String filename, HttpServletRequest req, ModelMap map, HttpServletResponse resp){
    	
    	String url = SiteMagConstant.apkPath;
    	
    	if (download(filename+".apk", url, resp)) {
			return null;
		}    	
    	return null ;
    }
    
    public Boolean download(String filename, String exportUrl, HttpServletResponse resp){
        
      	 OutputStream os;
  		 try {
  				os = resp.getOutputStream();
  				File file = new File(exportUrl + filename);  
  				
               if (file.exists())
                   {
                     try {
                           resp.reset();
                           resp.setHeader("Content-Disposition", "attachment; filename="
                                   + filename );
                           resp.setContentType("application/octet-stream; charset=utf-8");
                           os.write(FileUtils.readFileToByteArray(file));
                           os.flush();
                       } finally {
                           if (os != null) {
                               os.close();
                           }
                       }
               }
  			} catch (IOException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  		 }
  		 return true;	
   } 
    
}
