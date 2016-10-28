package com.ynyes.cslm.touch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdProduct;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.entity.TdUserConsult;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.service.TdProductService;
import com.ynyes.cslm.service.TdSpecificatService;
import com.ynyes.cslm.service.TdUserCollectService;
import com.ynyes.cslm.service.TdUserCommentService;
import com.ynyes.cslm.service.TdUserConsultService;
import com.ynyes.cslm.service.TdUserRecentVisitService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.util.ClientConstant;

/**
 * 商品详情页
 * 
 * @author Sharon
 *
 */
@Controller
@RequestMapping("/touch")
public class TdTouchGoodsController {
    @Autowired
    private TdGoodsService tdGoodsService;

    @Autowired
    private TdUserConsultService tdUserConsultService;

    @Autowired
    private TdUserCommentService tdUserCommentService;
    
    @Autowired
    private TdUserCollectService tdUserCollectService;

    @Autowired
    private TdProductCategoryService tdProductCategoryService;

    @Autowired
    private TdCommonService tdCommonService;

    @Autowired
    private TdUserRecentVisitService tdUserRecentVisitService;

    @Autowired
    private TdUserService tdUserService;

    @Autowired
    private TdProductService tdProductService;
    
    @Autowired
    private TdDistributorService tdDistributorService;;
    
    @Autowired
    private TdDistributorGoodsService tdDistributorGoodsService;
    
    @Autowired
    private TdSpecificatService tdSpecificatService;
    
    @RequestMapping("/goods/{dgId}")
    public String product(@PathVariable Long dgId, Long shareId,
            Integer qiang, ModelMap map, HttpServletRequest req) {

        tdCommonService.setHeader(map, req);
        
        if (null == dgId) {
            return "/touch/error_404";
        }
        TdDistributorGoods distributorGoods = tdDistributorGoodsService.findOne(dgId);
        
        if(null == distributorGoods){
        	return "/touch/error_404";
        }
        
        Long goodsId = distributorGoods.getGoodsId();
        
        String username = (String) req.getSession().getAttribute("username");
        if(null != req.getSession().getAttribute("DISTRIBUTOR_ID"))
        {
        	Long distributorId = tdDistributorGoodsService.findDistributorId(dgId);
        	TdDistributor distributor = tdDistributorService.findOne(distributorId);
        	if(null == distributor)
        	{
        		return "/touch/error_404";
        	}
        	
    		req.getSession().setAttribute("DISTRIBUTOR_ID",distributor.getId());
    		req.getSession().setAttribute("distributorTitle", distributor.getTitle()); 
        	
    		map.addAttribute("distributor", distributor);
            
            map.addAttribute("dis_goods", tdDistributorGoodsService.findByDistributorIdAndGoodsIdAndIsOnSale(distributorId,distributorGoods.getGoodsId(), true));
            
        }
        else
        {
        	// 如没有选择超市 则默认选择所选商品所在超市
        	Long distributorId = tdDistributorGoodsService.findDistributorId(dgId);
        	TdDistributor distributor = tdDistributorService.findOne(distributorId);
        	if(null == distributor)
        	{
        		return "/touch/error_404";
        	}
    		req.getSession().setAttribute("DISTRIBUTOR_ID",distributor.getId());
    		req.getSession().setAttribute("distributorTitle", distributor.getTitle()); 
    		
    		map.addAttribute("distributor", distributor);
            
            map.addAttribute("dis_goods", tdDistributorGoodsService.findByDistributorIdAndGoodsIdAndIsOnSale(distributorId,distributorGoods.getGoodsId(), true));
            
        }

        // 添加浏览记录
        if (null != username) {
            tdUserRecentVisitService.addNew(username, distributorGoods.getId());
            map.addAttribute("user",
                    tdUserService.findByUsernameAndIsEnabled(username));
        } else {
            tdUserRecentVisitService.addNew(req.getSession().getId(),  distributorGoods.getId());
        }

        // 规格
        map.addAttribute("spec_list", tdSpecificatService.findByShopIdAndGoodsIdAndType(distributorGoods.getDisId(), distributorGoods.getGoodsId(), 1));

        // 读取浏览记录
//        if (null == username) {
//            map.addAttribute("recent_page", tdUserRecentVisitService
//                    .findByUsernameOrderByVisitTimeDesc(req.getSession()
//                            .getId(), 0, ClientConstant.pageSize));
//        } else {
//            map.addAttribute("recent_page", tdUserRecentVisitService
//                    .findByUsernameOrderByVisitTimeDesc(username, 0,
//                            ClientConstant.pageSize));
//        }

        TdGoods goods = tdGoodsService.findOne(goodsId);

        if (null == goods) {
            return "/touch/error_404";
        }

        // 是否收藏
        if(null != tdUserCollectService.findByUsernameAndDistributorId(username,dgId,1)){
        	map.addAttribute("collect", true);
        }
        
        // 关联商品
        map.addAttribute("rele_list", tdDistributorGoodsService.findRelevanceGoods(dgId));
        
        // 商品
        map.addAttribute("goods", goods);

        // 全部评论
        map.addAttribute("comment_page",
                tdUserCommentService.findByGoodsIdAndIsShowable(dgId, 0,
                        ClientConstant.pageSize));
        
        // 全部评论数
        map.addAttribute("comment_count",
                tdUserCommentService.countByGoodsIdAndIsShowable(dgId));

        // 好评数
        map.addAttribute("three_star_comment_count", tdUserCommentService
                .countByGoodsIdAndStarsAndIsShowable(dgId, 3L));

        // 中评数
        map.addAttribute("two_star_comment_count", tdUserCommentService
                .countByGoodsIdAndStarsAndIsShowable(dgId, 2L));

        // 差评数
        map.addAttribute("one_star_comment_count", tdUserCommentService
                .countByGoodsIdAndStarsAndIsShowable(dgId, 1L));
        
        // 收藏总数
        map.addAttribute("total_collects",
                tdUserCollectService.countByGoodsId(goods.getId()));

        // 查找类型
        TdProductCategory tdProductCategory = tdProductCategoryService
                .findOne(goods.getCategoryId());

        // 获取该类型所有父类型
        if (null != tdProductCategory) {
            if (null != tdProductCategory.getParentTree()
                    && !"".equals(tdProductCategory.getParentTree())) {
                List<TdProductCategory> catList = new ArrayList<TdProductCategory>();

                for (String cid : tdProductCategory.getParentTree().split(",")) {
                    if (!"".equals(cid)) {
                        // 去除方括号
                        cid = cid.replace("[", "");
                        cid = cid.replace("]", "");

                        TdProductCategory tpc = tdProductCategoryService
                                .findOne(Long.parseLong(cid));

                        if (null != tpc) {
                            catList.add(tpc);
                        }
                    }
                }

                map.addAttribute("category_tree_list", catList);
            }
        }

        map.addAttribute("server_ip", req.getLocalName());
        map.addAttribute("server_port", req.getLocalPort());

        return "/touch/goods";
    }
    
    @RequestMapping("/goods/comment/{goodsId}")
    public String comments(@PathVariable Long goodsId, 
                    Integer page, 
                    Long stars,
                    ModelMap map, HttpServletRequest req) {
        
        if (null == goodsId)
        {
            return "error_404";
        }
        
        if (null == page)
        {
            page = 0;
        }
        
        if (null == stars)
        {
            stars = 0L;
        }
        
        tdCommonService.setHeader(map, req);
        
        // 全部评论数
        map.addAttribute("comment_count", tdUserCommentService
                .countByGoodsIdAndIsShowable(goodsId));
        
        // 好评数
        map.addAttribute("three_star_comment_count", tdUserCommentService
                .countByGoodsIdAndStarsAndIsShowable(goodsId, 3L));
        
        // 中评数
        map.addAttribute("two_star_comment_count", tdUserCommentService
                .countByGoodsIdAndStarsAndIsShowable(goodsId, 2L));
        
        // 差评数
        map.addAttribute("one_star_comment_count", tdUserCommentService
                .countByGoodsIdAndStarsAndIsShowable(goodsId, 1L));
        
        if (stars.equals(0L))
        {
            map.addAttribute("comment_page", tdUserCommentService
                    .findByGoodsIdAndIsShowable(goodsId, page, ClientConstant.pageSize));
        }
        else
        {
             map.addAttribute("comment_page", tdUserCommentService
                    .findByGoodsIdAndStarsAndIsShowable(goodsId, stars, page, ClientConstant.pageSize));
        }
        
        // 评论
        map.addAttribute("page", page);
        map.addAttribute("stars", stars);
        map.addAttribute("goodsId", goodsId);
        
      //判断是否为app链接
        Integer isApp = (Integer) req.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
        
        return "/touch/goods_comment";
    }
    
    @RequestMapping(value = "/goods/comment/more/{goodsId}", method=RequestMethod.POST)
    public String commentsmore(@PathVariable Long goodsId, 
                    Integer page, 
                    Long stars,
                    ModelMap map, HttpServletRequest req) {
        
        if (null == goodsId)
        {
            return "/touch/goods_comment_more";
        }
        
        if (null == page)
        {
            page = 0;
        }
        
        if (null == stars)
        {
            stars = 0L;
        }
                    
        if (stars.equals(0L))
        {
            map.addAttribute("comment_page", tdUserCommentService
                    .findByGoodsIdAndIsShowable(goodsId, page, ClientConstant.pageSize));
        }
        else
        {
             map.addAttribute("comment_page", tdUserCommentService
                    .findByGoodsIdAndStarsAndIsShowable(goodsId, stars, page, ClientConstant.pageSize));
        }
        
        // 评论
        //map.addAttribute("page", page);
        //map.addAttribute("stars", stars);
        //map.addAttribute("goodsId", goodsId);
        
        return "/touch/goods_comment_more";
    }
    
    @RequestMapping("/goods/consult/{goodsId}")
    public String consults(@PathVariable Long goodsId, 
                            Integer page, 
                            ModelMap map, 
                            HttpServletRequest req) {
        
        if (null == goodsId)
        {
            return "error_404";
        }
        
        if (null == page)
        {
            page = 0;
        }
        tdCommonService.setHeader(map, req);
        
        Page<TdUserConsult> consultPage = tdUserConsultService
                .findByGoodsIdAndIsShowable(goodsId, page, ClientConstant.pageSize);
        
        // 咨询
        map.addAttribute("consult_page", consultPage);
        map.addAttribute("page", page);
        map.addAttribute("goodsId", goodsId);
        
      //判断是否为app链接
        Integer isApp = (Integer) req.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
        
        return "/touch/goods_consult";
    }
    
    @RequestMapping(value = "/goods/consult/more/{goodsId}", method=RequestMethod.POST)
    public String consultsmore(@PathVariable Long goodsId, 
            Integer page, 
            ModelMap map, 
            HttpServletRequest req) {

		if (null == goodsId)
		{
		return "error_404";
		}
		
		if (null == page)
		{
		page = 0;
		}
		//tdCommonService.setHeader(map, req);
		
		Page<TdUserConsult> consultPage = tdUserConsultService
		.findByGoodsIdAndIsShowable(goodsId, page, ClientConstant.pageSize);
		
		// 咨询
		map.addAttribute("consult_page", consultPage);
		map.addAttribute("page", page);
		map.addAttribute("goodsId", goodsId);
		
		return "/touch/goods_consult_more";
	}
    
    @RequestMapping("/goods/detail/{goodsId}")
    public String detail(@PathVariable Long goodsId,
                            ModelMap map, 
                            HttpServletRequest req) {
        
        tdCommonService.setHeader(map, req);
        
        if (null == goodsId)
        {
            return "/touch/goods_detail";
        }
        
        // 商品
        map.addAttribute("goods", tdGoodsService.findOne(goodsId));
        
        return "/touch/goods_detail";
    }
    
    @RequestMapping("/goods/param/{goodsId}")
    public String param(@PathVariable Long goodsId,
                            ModelMap map, 
                            HttpServletRequest req) {
        
        tdCommonService.setHeader(map, req);
        
        if (null == goodsId)
        {
            return "/touch/goods_param";
        }
        
        // 商品
        map.addAttribute("goods", tdGoodsService.findOne(goodsId));
        
      //判断是否为app链接
        Integer isApp = (Integer) req.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
        
        return "/touch/goods_param";
    }
    
    
    
    /**
     * @author Max
     * 
     */
    @RequestMapping(value ="/goods/incart")
    @ResponseBody
    public Map<String,Object> goodsBeforIncart(Long id,Long quantity,HttpServletRequest req)
    {
    	Map<String,Object> res =new HashMap<>();
    	
	    	if(null == id)
	    	{
	    		res.put("msg","该商品不存在！");
	    		return res;
	    	}
	    	if(null ==quantity)
	    	{
	    		quantity =1L;
	    	}
	    	
    	if(null !=req.getSession().getAttribute("DISTRIBUTOR_ID"))
    	{
    		Long disId = (Long)req.getSession().getAttribute("DISTRIBUTOR_ID");
	    	
	//    	List<TdDistributorGoods> disGoods = tdDistributorGoodsService.findByGoodsId(id);
//	    	TdDistributorGoods distributorGoods = TdDistributorService.findByIdAndGoodId(disId, id);
	    	TdDistributorGoods distributorGoods = tdDistributorGoodsService.findOne(id);
	    	
	    	if(null == distributorGoods)
	    	{
	    		res.put("msg", "本超市没有该商品，您可以选择其他超市购买！");
	    		return res;
	    	}
	    	
	    	if(quantity > distributorGoods.getLeftNumber())
	    	{
	    		res.put("msg", "本超市库存不足！");
	    		return res;
	    	}
    	}else{
    		Long distributorId = tdDistributorGoodsService.findDistributorId(id);
    		TdDistributor distributor = tdDistributorService.findOne(distributorId);
    		req.getSession().setAttribute("DISTRIBUTOR_ID",distributor.getId());
    		req.getSession().setAttribute("distributorTitle", distributor.getTitle());
    	}
    	return res;
    }
}

