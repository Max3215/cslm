package com.ynyes.cslm.touch;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ynyes.cslm.entity.TdKeywords;
import com.ynyes.cslm.service.TdArticleCategoryService;
import com.ynyes.cslm.service.TdArticleService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdKeywordsService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.util.ClientConstant;

@Controller
@RequestMapping("/touch")
public class TdTouchSearchController {
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
	    private TdDistributorGoodsService tdDistributorGoodsService;
	    
	    @Autowired
	    private TdDistributorService tdDistributorService;

	    /**
	     * 搜索
	     * 
	     */
	    @RequestMapping(value = "/search", method = RequestMethod.GET)
	    public String list(String keywords,String type, Integer page, Integer st, Integer sd,
	            HttpServletRequest req, ModelMap map) {
	    	
            tdCommonService.setHeader(map, req);
            
	        if (null == page || page < 0) {
	            page = 0;
	        }
	        
	        if (null == st)
	        {
	            st = 0;
	        }
	        
	        if (null == sd)
	        {
	            sd = 0;
	        }

	        if(null != type && "店铺".equals(type)){
	        	map.addAttribute("distributorList", tdDistributorService.searchAllAndIsEnableTrueOrderBySortIdAsc(keywords, page, ClientConstant.pageSize));
	        	return "/touch/distributor_list";
	        }
	        
	        if (null != keywords) {
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
	            
	            String orderColumn = "soldNumber";
	                    
	            if (st.equals(1))
	            {
	                orderColumn = "goodsPrice";
	            }
	            else if (st.equals(2))
	            {
	                orderColumn = "onSaleTime";
	            }
	            
	            Direction dir = Direction.DESC;
	            
	            if (sd.equals(1))
	            {
	                dir = Direction.ASC;
	            }

	            if(null != req.getSession().getAttribute("DISTRIBUTOR_ID"))
	            {
	              Long distributorId = (Long)req.getSession().getAttribute("DISTRIBUTOR_ID");
	              map.addAttribute("goods_page",tdDistributorGoodsService.searchAndDisIdAndIsOnSaleOrderBy(distributorId, 
	            		  			keywords, true, page, ClientConstant.pageSize,orderColumn,dir));
	              
	            }else{
	            	map.addAttribute("goods_page", tdDistributorGoodsService.searchGoodsAndIsOnSaleOrderBy(keywords, true, page, ClientConstant.pageSize,orderColumn,dir));
	            	
	            }
	        }

	        map.addAttribute("pageId", page);
	        map.addAttribute("keywords", keywords);
	        map.addAttribute("st", st);
	        map.addAttribute("sd", sd);
	        
	      //判断是否为app链接
	        Integer isApp = (Integer) req.getSession().getAttribute("app");
	        if (null != isApp) {
	        	map.addAttribute("app", isApp);
			}
	        
	        return "/touch/search_list";
	    }
	    
	    @RequestMapping(value = "/search", method = RequestMethod.POST)
	    public String searchMore(String keywords, Integer page, Integer st, Integer sd,
	            HttpServletRequest req, ModelMap map) {

	    	tdCommonService.setHeader(map, req);
	    	
	        if (null == page || page < 0) {
	            page = 0;
	        }
	        
	        if (null == st)
	        {
	            st = 0;
	        }
	        
	        if (null == sd)
	        {
	            sd = 0;
	        }

	        if (null != keywords) {
	            TdKeywords key = tdKeywordsService.findByTitle(keywords);

	            if (null != key) {
	                if (null == key.getTotalSearch()) {
	                    key.setTotalSearch(1L);
	                } else {
	                    key.setTotalSearch(key.getTotalSearch() + 1L);
	                }

	                key.setLastSearchTime(new Date());

	                tdKeywordsService.save(key);
	            }
	            
	            String orderColumn = "soldNumber";
	                    
	            if (st.equals(1))
	            {
	                orderColumn = "goodsPrice";
	            }
	            else if (st.equals(2))
	            {
	                orderColumn = "onSaleTime";
	            }
	            
	            Direction dir = Direction.DESC;
	            
	            if (sd.equals(1))
	            {
	                dir = Direction.ASC;
	            }

	            if(null != req.getSession().getAttribute("DISTRIBUTOR_ID"))
	            {
	              Long distributorId = (Long)req.getSession().getAttribute("DISTRIBUTOR_ID");
	              map.addAttribute("goods_page",tdDistributorGoodsService.searchAndDisIdAndIsOnSaleOrderBy(distributorId, 
      		  				keywords, true, page, ClientConstant.pageSize,orderColumn,dir));
	              
	            }else{
	            	map.addAttribute("goods_page", tdDistributorGoodsService.searchGoodsAndIsOnSaleOrderBy(keywords, true, page, ClientConstant.pageSize,orderColumn,dir));
	            	
	            }
	        }
	        
	        map.addAttribute("pageId", page);
	        map.addAttribute("keywords", keywords);
	        map.addAttribute("st", st);
	        map.addAttribute("sd", sd);

	      //判断是否为app链接
	        Integer isApp = (Integer) req.getSession().getAttribute("app");
	        if (null != isApp) {
	        	map.addAttribute("app", isApp);
			}
	        
	        return "/touch/search_result_more";
	    }
	    
	    @RequestMapping(value="/findNew",method = RequestMethod.GET)
	    public String findNewGoods(Integer page,HttpServletRequest req,ModelMap map){
	    	tdCommonService.setHeader(map, req);
	    	
	    	if (null == page || page < 0) {
	            page = 0;
	        }
	    	
	    	if(null != req.getSession().getAttribute("DISTRIBUTOR_ID"))
            {
              Long distributorId = (Long)req.getSession().getAttribute("DISTRIBUTOR_ID");
              map.addAttribute("goods_page",tdDistributorGoodsService.findByDistributorIdAndIsOnSaleTrueOrderByOnSaleTime(distributorId,  page, ClientConstant.pageSize));
              
            }else{
            	 map.addAttribute("goods_page",tdDistributorGoodsService.findAllOrderByOnSaleTime(page, ClientConstant.pageSize));
            }
	    	
	    	map.addAttribute("pageId", page);
	    	return "/touch/goods_new";
	    }
	    
	    @RequestMapping(value="/findNew",method = RequestMethod.POST)
	    public String findNewGoodsMore(Integer page,HttpServletRequest req,ModelMap map){
	    	tdCommonService.setHeader(map, req);
	    	
	    	if (null == page || page < 0) {
	            page = 0;
	        }
	    	
	    	if(null != req.getSession().getAttribute("DISTRIBUTOR_ID"))
            {
              Long distributorId = (Long)req.getSession().getAttribute("DISTRIBUTOR_ID");
              map.addAttribute("goods_page",tdDistributorGoodsService.findByDistributorIdAndIsOnSaleTrueOrderByOnSaleTime(distributorId,  page, ClientConstant.pageSize));
              
            }else{
            	 map.addAttribute("goods_page",tdDistributorGoodsService.findAllOrderByOnSaleTime(page, ClientConstant.pageSize));
            }
	    	
	    	map.addAttribute("pageId", page);
	    	return "/touch/search_result_more";
	    }
}
