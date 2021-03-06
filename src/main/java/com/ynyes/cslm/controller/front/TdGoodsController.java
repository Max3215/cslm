package com.ynyes.cslm.controller.front;

import java.util.ArrayList;
import java.util.Date;
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
import com.ynyes.cslm.entity.TdSetting;
import com.ynyes.cslm.entity.TdSpecificat;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.entity.TdUserConsult;
import com.ynyes.cslm.entity.TdUserPoint;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdOrderService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.service.TdProductService;
import com.ynyes.cslm.service.TdSettingService;
import com.ynyes.cslm.service.TdSpecificatService;
import com.ynyes.cslm.service.TdUserCollectService;
import com.ynyes.cslm.service.TdUserCommentService;
import com.ynyes.cslm.service.TdUserConsultService;
import com.ynyes.cslm.service.TdUserPointService;
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
public class TdGoodsController {
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
    private TdSettingService tdSettingService;

    @Autowired
    private TdUserService tdUserService;

    @Autowired
    private TdProductService tdProductService;

    @Autowired
    private TdUserPointService tdUserPointService;

    //TdOrder服务类   libiao  
    @Autowired
    private TdDistributorService TdDistributorService;
    
    @Autowired
    private TdOrderService tdOrderService;
    
    @Autowired
    private TdDistributorGoodsService tdDistributorGoodsService;
    
    @Autowired
    private TdSpecificatService tdSpecificatService;

    @RequestMapping("/goods/{dgId}")
    public String product(@PathVariable Long dgId, Long shareId,
            Integer qiang, ModelMap map, HttpServletRequest req) {

        tdCommonService.setHeader(map, req);
        
        if (null == dgId) {
            return "/client/error_404";
        }
        TdDistributorGoods distributorGoods = tdDistributorGoodsService.findByIdAndIsInSaleTrue(dgId);
        if(null == distributorGoods)
        {
        	return "/client/error_404";
        }
        
        map.addAttribute("spec_list", tdSpecificatService.findByShopIdAndGoodsIdAndType(distributorGoods.getDisId(), distributorGoods.getGoodsId(), 1));
        
        Long goodsId = distributorGoods.getGoodsId();
        
        String username = (String) req.getSession().getAttribute("username");
        if(null != req.getSession().getAttribute("DISTRIBUTOR_ID"))
        {
        	Long distributorId = tdDistributorGoodsService.findDistributorId(dgId);
        	TdDistributor distributor = TdDistributorService.findOne(distributorId);
        	if(null == distributor)
        	{
        		return "/client/error_404";
        	}
    		req.getSession().setAttribute("DISTRIBUTOR_ID",distributor.getId());
    		req.getSession().setAttribute("distributorTitle", distributor.getTitle()); 
        	
    		map.addAttribute("distributor", distributor);
        	 //成交数
            map.addAttribute("bargain_record_page",tdOrderService.findByShopIdAndGoodId(distributorId, dgId,0,10));
            
            map.addAttribute("dis_goods", tdDistributorGoodsService.findByDistributorIdAndGoodsIdAndIsOnSale(distributorId,distributorGoods.getGoodsId(), true));
            
         // 热卖
            map.addAttribute("dis_hot_list",
                    tdDistributorGoodsService.findByDistributorIdAndIsOnSaleTrueBySoldNumberDesc(distributorId,0,12).getContent());
        }
        else
        {
        	// 如没有选择超市 则默认选择所选商品所在超市
        	Long distributorId = tdDistributorGoodsService.findDistributorId(dgId);
        	TdDistributor distributor = TdDistributorService.findOne(distributorId);
        	if(null == distributor)
        	{
        		return "/client/error_404";
        	}
    		req.getSession().setAttribute("DISTRIBUTOR_ID",distributor.getId());
    		req.getSession().setAttribute("distributorTitle", distributor.getTitle()); 
    		
    		map.addAttribute("distributor", distributor);
    		 //成交数
            map.addAttribute("bargain_record_page",tdOrderService.findByShopIdAndGoodId(distributorId, dgId,0,10));
            
            map.addAttribute("dis_goods", tdDistributorGoodsService.findByDistributorIdAndGoodsIdAndIsOnSale(distributorId,distributorGoods.getGoodsId(), true));
            
         // 热卖
            map.addAttribute("dis_hot_list",
                    tdDistributorGoodsService.findByDistributorIdAndIsOnSaleTrueBySoldNumberDesc(distributorId,0,12).getContent());
        }

        // 添加浏览记录
        if (null != username) {
            tdUserRecentVisitService.addNew(username, distributorGoods.getId());
            map.addAttribute("user",
                    tdUserService.findByUsernameAndIsEnabled(username));
        } else {
            tdUserRecentVisitService.addNew(req.getSession().getId(),  distributorGoods.getId());
        }

        // 促销标志位
        map.addAttribute("qiang", qiang);

        // 读取浏览记录
        if (null == username) {
            map.addAttribute("recent_page", tdUserRecentVisitService
                    .findByUsernameOrderByVisitTimeDesc(req.getSession()
                            .getId(), 0, ClientConstant.pageSize));
        } else {
            map.addAttribute("recent_page", tdUserRecentVisitService
                    .findByUsernameOrderByVisitTimeDesc(username, 0,
                            ClientConstant.pageSize));
        }

        TdGoods goods = tdGoodsService.findOne(goodsId);

        if (null == goods) {
            return "error_404";
        }

        if(null != tdUserCollectService.findByUsernameAndDistributorId(username,dgId,1)){
        	map.addAttribute("collect", true);
        }
        
        TdProductCategory tdProductCategory = tdProductCategoryService
                .findOne(goods.getCategoryId());

        // 鑾峰彇璇ョ被鍨嬫墍鏈夌埗绫诲瀷
        if (null != tdProductCategory) {
            if (null != tdProductCategory.getParentTree()
                    && !"".equals(tdProductCategory.getParentTree())) {
                List<TdProductCategory> catList = new ArrayList<TdProductCategory>();

                for (String cid : tdProductCategory.getParentTree().split(",")) {
                    if (!"".equals(cid)) {
                        // 鍘婚櫎鏂规嫭鍙�
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


        map.addAttribute("server_ip", req.getLocalName());
        map.addAttribute("server_port", req.getLocalPort());

        return "/client/goods";
    }

    @RequestMapping("/goods/comment/{goodsId}")
    public String comments(@PathVariable Long goodsId, Integer page,
            Long stars, ModelMap map, HttpServletRequest req) {

        if (null == goodsId) {
            return "error_404";
        }

        if (null == page) {
            page = 0;
        }

        if (null == stars) {
            stars = 0L;
        }

        // 全部评论数
        map.addAttribute("comment_count",
                tdUserCommentService.countByGoodsIdAndIsShowable(goodsId));

        // 好评数
        map.addAttribute("three_star_comment_count", tdUserCommentService
                .countByGoodsIdAndStarsAndIsShowable(goodsId, 3L));

        // 中评数
        map.addAttribute("two_star_comment_count", tdUserCommentService
                .countByGoodsIdAndStarsAndIsShowable(goodsId, 2L));

        // 差评数
        map.addAttribute("one_star_comment_count", tdUserCommentService
                .countByGoodsIdAndStarsAndIsShowable(goodsId, 1L));

        if (stars.equals(0L)) {
            map.addAttribute("comment_page", tdUserCommentService
                    .findByGoodsIdAndIsShowable(goodsId, page,
                            ClientConstant.pageSize));
        } else {
            map.addAttribute("comment_page", tdUserCommentService
                    .findByGoodsIdAndStarsAndIsShowable(goodsId, stars, page,
                            ClientConstant.pageSize));
        }

        // 评论
        map.addAttribute("page", page);
        map.addAttribute("stars", stars);
        map.addAttribute("goodsId", goodsId);

        return "/client/goods_comment";
    }

    @RequestMapping("/goods/consult/{goodsId}")
    public String consults(@PathVariable Long goodsId, Integer page,
            ModelMap map, HttpServletRequest req) {

        if (null == goodsId) {
            return "error_404";
        }

        if (null == page) {
            page = 0;
        }

        Page<TdUserConsult> consultPage = tdUserConsultService
                .findByGoodsIdAndIsShowable(goodsId, page,
                        ClientConstant.pageSize);

        // 咨询
        map.addAttribute("consult_page", consultPage);
        map.addAttribute("page", page);
        map.addAttribute("goodsId", goodsId);

        return "/client/goods_consult";
    }

    @RequestMapping(value="/goods/record/{goodsId}")
    public String record(@PathVariable Long goodsId,Integer page,ModelMap map,HttpServletRequest req)
    {
    	if (null == goodsId) {
    		return "error_404";
    	}
    	
    	if (null == page) {
    		page = 0;
    	}
    	map.addAttribute("dis_goods", tdDistributorGoodsService.findOne(goodsId));
    	map.addAttribute("page", page);
    	
    	if(null != req.getSession().getAttribute("DISTRIBUTOR_ID"))
        {
        	Long distributorId= (Long)req.getSession().getAttribute("DISTRIBUTOR_ID");
        	
        	 //成交数
            map.addAttribute("bargain_record_page",tdOrderService.findByShopIdAndGoodId(distributorId, goodsId,page,10));
        }else
        {
        	 map.addAttribute("bargain_record_page",tdOrderService.findByShopIdAndGoodId(null, goodsId,page,10));
        }
        return "/client/goods_record";
    }
    
    /**
     * 用户选择超市后点击加入购物车或立即购买
     * 		前判断超市是否存在商品或者库存是否大于购买数量
     * @param id
     * @param quantity
     * @param req
     * @return
     */
    @RequestMapping(value ="/goods/incart")
    @ResponseBody
    public Map<String,Object> goodsBeforIncart(Long id,Long quantity,HttpServletRequest req)
    {
    	Map<String,Object> res =new HashMap<>();
    	res.put("code", 0);
    	
    	if(null == id)
    	{
    		res.put("msg","该商品不存在！");
    		return res;
    	}
    	if(null ==quantity)
    	{
    		quantity =1L;
    	}
	    	
    	TdDistributorGoods distributorGoods = tdDistributorGoodsService.findOne(id);
    	
    	if(null == distributorGoods)
    	{
    		res.put("msg", "本超市没有该商品，您可以选择其他超市购买！");
    		return res;
    	}
    	
    	if(quantity > distributorGoods.getLeftNumber())
    	{
    		res.put("msg", "本超市库存不足，请重新输入");
    		return res;
    	}
    	
    	res.put("code", 1);
    	return res;
    }
    
    @RequestMapping(value="/goods/specifica",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> findSpec(Long id,HttpServletRequest req){
    	Map<String,Object> res = new HashMap<String, Object>();
    	res.put("code", 0);
    	
    	if(null != id){
    		TdSpecificat specificat = tdSpecificatService.findOne(id);
    		if(null != specificat){
    			if(specificat.getLeftNumber() > 0 ){
    				res.put("num", specificat.getLeftNumber());
    				res.put("code", 1);
    				return res;
    			}
    		}
    	}
    	res.put("msg", "此规格已售完，请选择其它类型");
    	return res;
    }
}

    
