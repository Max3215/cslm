package com.ynyes.cslm.touch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.cslm.entity.TdArticle;
import com.ynyes.cslm.entity.TdArticleCategory;
import com.ynyes.cslm.entity.TdNavigationMenu;
import com.ynyes.cslm.service.TdAdService;
import com.ynyes.cslm.service.TdAdTypeService;
import com.ynyes.cslm.service.TdArticleCategoryService;
import com.ynyes.cslm.service.TdArticleService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdNavigationMenuService;
import com.ynyes.cslm.service.TdUserRecentVisitService;
import com.ynyes.cslm.util.ClientConstant;

@Controller
@RequestMapping("/touch/info")
public class TdTouchInfoController {
	@Autowired 
	private TdArticleService tdArticleService;
	
	@Autowired 
    private TdArticleCategoryService tdArticleCategoryService;
	
	@Autowired 
    private TdNavigationMenuService tdNavigationMenuService;
	
	@Autowired
    private TdCommonService tdCommonService;
	
	@Autowired
    private TdAdTypeService tdAdTypeService;
	
	@Autowired
    private TdAdService tdAdService;
	
	@Autowired
    private TdUserRecentVisitService tdUserRecentVisitService;
    
	@RequestMapping("/list/{mid}")
    public String infoList(@PathVariable Long mid, 
                            Long catId, 
                            Integer page, 
                            ModelMap map,
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
        
	    if (null == mid)
	    {
	        return "/touch/error_404";
	    }
	    
	    if (null == page)
	    {
	        page = 0;
	    }
	    
	    TdNavigationMenu menu = tdNavigationMenuService.findOne(mid);
	    
	    map.addAttribute("menu_name", menu.getTitle());
	    
	    
	    map.addAttribute("pageId", page);
	    map.addAttribute("catId", catId);
	    map.addAttribute("mid", mid);
	    
	    if(null != req.getSession().getAttribute("DISTRIBUTOR_ID")){
	    	Long distributorId = (Long)req.getSession().getAttribute("DISTRIBUTOR_ID");
	    	System.err.println(distributorId);
	    	map.addAttribute("latest_info_page", tdArticleService.findByDistributorIdAndMenuIdAndIsEnableOrderByIdDesc(distributorId, mid, page, ClientConstant.pageSize));
	    }else{
	    	map.addAttribute("latest_info_page", tdArticleService.findByMenuIdAndIsEnableOrderByIdDesc(mid, page, ClientConstant.pageSize));
	    }
	    
	  //判断是否为app链接
        Integer isApp = (Integer) req.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
	    
        if(mid==12) // 帮助中心
        {
        	return "/touch/info_help_list";
        }
        return "/touch/info_list";
    }
	
	@RequestMapping("/content/{id}")
    public String content(@PathVariable Long id, Long mid, ModelMap map, HttpServletRequest req){
        
	    tdCommonService.setHeader(map, req);
	    
        if (null == id || null == mid)
        {
            return "/touch/error_404";
        }
        
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
        
        TdNavigationMenu menu = tdNavigationMenuService.findOne(mid);
        
        map.addAttribute("menu_name", menu.getTitle());
        
        List<TdArticleCategory> catList = tdArticleCategoryService.findByMenuId(mid);
        
        map.addAttribute("info_category_list", catList);
        map.addAttribute("mid", mid);
        
        TdArticle tdArticle = tdArticleService.findOne(id);
        
        if (null != tdArticle)
        {
            map.addAttribute("info", tdArticle);
            map.addAttribute("prev_info", tdArticleService.findPrevOne(id, tdArticle.getCategoryId(), tdArticle.getMenuId()));
            map.addAttribute("next_info", tdArticleService.findNextOne(id, tdArticle.getCategoryId(), tdArticle.getMenuId()));
        }
        
        // 最近添加
        map.addAttribute("latest_info_page", tdArticleService.findByMenuIdAndIsEnableOrderByIdDesc(mid, 0, ClientConstant.pageSize));
        
      //判断是否为app链接
        Integer isApp = (Integer) req.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
        
        return "/touch/info";
    }
}
