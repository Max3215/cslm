package com.ynyes.cslm.controller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdCartGoods;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdSpecificat;
import com.ynyes.cslm.service.TdCartGoodsService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsCombinationService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdSpecificatService;
import com.ynyes.cslm.service.TdUserService;

/**
 * 购物车
 *
 */
@Controller
public class TdCartController {

    @Autowired
    private TdCartGoodsService tdCartGoodsService;

    @Autowired
    private TdGoodsService tdGoodsService;
    
    @Autowired
    private TdGoodsCombinationService tdGoodsCombinationService;

    @Autowired
    private TdCommonService tdCommonService;
    
    @Autowired
    private TdUserService tdUserService;
    
    @Autowired
    private TdDistributorService tdDistributorService;
    
    @Autowired
    private TdDistributorGoodsService tdDistributorGoodsService;

    @Autowired
    private TdSpecificatService tdSpecificatService;
    
    /**
     * 加入购物车
     * @return
     */
//    @RequestMapping(value = "/cart/init")
//    public String addCart(Long id, Long quantity, String zhid, Integer m,
//            HttpServletRequest req,ModelMap map) {
//        // 是否已登录
//        boolean isLoggedIn = true;
//
//        String username = (String) req.getSession().getAttribute("username");
//        
//        TdDistributor distributor = null;
//        if(null != req.getSession().getAttribute("DISTRIBUTOR_ID"))
//        {
//        	Long distributorId= (Long)req.getSession().getAttribute("DISTRIBUTOR_ID");
//        	distributor = tdDistributorService.findOne(distributorId);
//        	
//        }else{
//        	Long distributorId = tdDistributorGoodsService.findDistributorId(id);
//    		distributor = tdDistributorService.findOne(distributorId);
//    		req.getSession().setAttribute("DISTRIBUTOR_ID",distributor.getId());
//    		req.getSession().setAttribute("distributorTitle", distributor.getTitle());
//        }
//        
//        tdCommonService.setHeader(map, req);
//        if(null == distributor)
//        {
//        	return "/client/error_404";
//        }
//        
//        if (null == username) {
//            isLoggedIn = false;
//            username = req.getSession().getId();
//        }
//        
//        if (null == m)
//        {
//            m = 0;
//        }
//        
//        if (null == quantity || quantity.compareTo(1L) < 0)
//        {
//            quantity = 1L;
//        }
//        
//        if (null != id) {
//        	TdDistributorGoods goods = tdDistributorGoodsService.findOne(id);
//            if (null != goods) {
//                
//                List<TdCartGoods> oldCartGoodsList = null;
//                
//                // 购物车是否已有该商品
//                oldCartGoodsList = tdCartGoodsService.findByGoodsIdAndUsername(goods.getId(), username);
//                
//                // 有多项，则在第一项上数量进行相加
//                if (null != oldCartGoodsList && oldCartGoodsList.size() > 0) {
//                    long oldQuantity = oldCartGoodsList.get(0).getQuantity();
//                    if(oldQuantity + quantity >= goods.getLeftNumber())
//                    {
//                    	oldCartGoodsList.get(0).setQuantity(goods.getLeftNumber());
//                    }else{
//                    	oldCartGoodsList.get(0).setQuantity(oldQuantity + quantity);
//                    }
//                    tdCartGoodsService.save(oldCartGoodsList.get(0));
//                }
//                // 新增购物车项
//                else
//                {
//                    TdCartGoods cartGoods = new TdCartGoods();
//                    
//                    cartGoods.setIsLoggedIn(isLoggedIn);
//                    cartGoods.setUsername(username);
//    
//                    cartGoods.setIsSelected(false);
//                    cartGoods.setGoodsId(goods.getGoodsId());
//                    cartGoods.setDistributorId(distributor.getId());
//                    cartGoods.setDistributorTitle(distributor.getTitle());
//                    cartGoods.setDistributorGoodsId(goods.getId());
//                    cartGoods.setDistributorId(tdDistributorGoodsService.findDistributorId(id));
//            		cartGoods.setProviderId(goods.getProviderId());
//            		cartGoods.setProviderTite(goods.getProviderTitle());
//            		cartGoods.setUnit(goods.getUnit());
//                    
//                    cartGoods.setQuantity(quantity);
//                    
//                    tdCartGoodsService.save(cartGoods);
//                }
//            }
//        }
//
//        return "redirect:/cart/add?id=" + id + "&m=" + m;
//    }
//
//    @RequestMapping(value = "/cart/add")
//    public String cartInit(Long id, Integer m, HttpServletRequest req, ModelMap map) {
//        tdCommonService.setHeader(map, req);
//        
//        if (null == m)
//        {
//            m = 0;
//        }
//        
//        if (m.equals(1)) { // 移动端浏览器
//            
//            return "/touch/cart_add_res";
//        }
//        
//        return "/client/cart_add_res";
//    }

    @RequestMapping(value = "/cart")
    public String cart(HttpServletRequest req, ModelMap map) {

        String username = (String) req.getSession().getAttribute("username");

        // 未登录用户的购物车商品
        List<TdCartGoods> cartSessionGoodsList = tdCartGoodsService
                .findByUsername(req.getSession().getId());
        if (null == username)
        {
            username = req.getSession().getId();
        }
        else
        {
            // 合并商品
            // 已登录用户的购物车
            List<TdCartGoods> cartUserGoodsList = tdCartGoodsService.findByUsername(username);
            
            for (TdCartGoods cg : cartSessionGoodsList)
            {
            // 将未登录用户的购物车加入已登录用户购物车中
                cg.setUsername(username);
                cartUserGoodsList.add(cg);
            }

            cartUserGoodsList = tdCartGoodsService.save(cartUserGoodsList);

            for (TdCartGoods cg1 : cartUserGoodsList) 
            {
                // 删除重复的商品
//                List<TdCartGoods> findList = tdCartGoodsService
//                        .findByGoodsIdAndUsername(cg1.getDistributorGoodsId(), username);
            	List<TdCartGoods> cartGoods = tdCartGoodsService.findByUsernameAndDistributorGoodsIdAndSpecificaId(username, cg1.getDistributorGoodsId(), cg1.getSpecificaId());

                if (null != cartGoods && cartGoods.size() > 1) 
                {
                    tdCartGoodsService.delete(cartGoods.subList(1,cartGoods.size()));
                }
            }
        }

        List<TdCartGoods> resList = tdCartGoodsService.findByUsername(username);
        
        map.addAttribute("user",tdUserService.findByUsername(username));
        
        map.addAttribute("cart_goods_list",tdCartGoodsService.updateGoodsInfo(resList));
        
        
        tdCommonService.setHeader(map, req);

        return "/client/cart";
    }

    @RequestMapping(value = "/cart/toggleSelect", method = RequestMethod.POST)
    public String cartToggle(Long id, HttpServletRequest req, ModelMap map) {

        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            username = req.getSession().getId();
        }

        List<TdCartGoods> cartGoodsList = tdCartGoodsService
                .findByUsername(username);

        if (null != id) {
            for (TdCartGoods cartGoods : cartGoodsList) {
                if (cartGoods.getId().equals(id)) {
                    if (null == cartGoods.getIsSelected() || false == cartGoods.getIsSelected())
                    {
                        cartGoods.setIsSelected(true);
                    }
                    else
                    {
                        cartGoods.setIsSelected(false);
                    }
                    cartGoods = tdCartGoodsService.save(cartGoods);
                    break;
                }
            }
        }

        map.addAttribute("cart_goods_list", tdCartGoodsService.updateGoodsInfo(cartGoodsList));

        return "/client/cart_goods";
    }

    @RequestMapping(value = "/cart/toggleAll", method = RequestMethod.POST)
    public String cartToggleAll(Integer sid, HttpServletRequest req,
            ModelMap map) {

        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            username = req.getSession().getId();
        }

        List<TdCartGoods> cartGoodsList = tdCartGoodsService
                .findByUsername(username);

        if (null != sid) {
            if (sid.equals(0)) // 全选
            {
                for (TdCartGoods cartGoods : cartGoodsList) {
                    cartGoods.setIsSelected(true);
                }
            } else // 取消全选
            {
                for (TdCartGoods cartGoods : cartGoodsList) {
                    cartGoods.setIsSelected(false);
                }
            }
            tdCartGoodsService.save(cartGoodsList);
        }

        map.addAttribute("cart_goods_list", tdCartGoodsService.updateGoodsInfo(cartGoodsList));

        return "/client/cart_goods";
    }

    @RequestMapping(value = "/cart/numberAdd", method = RequestMethod.POST)
    public String cartNumberAdd(Long id, HttpServletRequest req, ModelMap map) {

        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            username = req.getSession().getId();
        }

        if (null != id) {
            TdCartGoods cartGoods =tdCartGoodsService.findOne(id);
            
            Long leftNumber = 0L;
			if(null != cartGoods.getSpecificaId()){
				TdSpecificat specificat = tdSpecificatService.findOne(cartGoods.getSpecificaId());
				if(null != specificat){
					leftNumber = specificat.getLeftNumber();
				}
			}else{
				TdDistributorGoods distributorGoods = tdDistributorGoodsService.findByIdAndIsInSaleTrue(cartGoods.getDistributorGoodsId());
				if(null != distributorGoods)
    			{
					leftNumber = distributorGoods.getLeftNumber();
    			}
			}
            if (cartGoods.getUsername().equalsIgnoreCase(username)) {
                long quantity = cartGoods.getQuantity();
                
                if(quantity < leftNumber){
                	cartGoods.setQuantity(quantity + 1);
                }else{
                	cartGoods.setQuantity(leftNumber);
                }
                tdCartGoodsService.save(cartGoods);
            }
        }

        map.addAttribute("cart_goods_list",
                tdCartGoodsService.updateGoodsInfo(tdCartGoodsService.findByUsername(username)));

        return "/client/cart_goods";
    }

    @RequestMapping(value = "/cart/numberMinus", method = RequestMethod.POST)
    public String cartNumberMinus(Long id, HttpServletRequest req, ModelMap map) {

        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            username = req.getSession().getId();
        }

        if (null != id) {
            TdCartGoods cartGoods = tdCartGoodsService.findOne(id);

            if (cartGoods.getUsername().equalsIgnoreCase(username)) {
                long quantity = cartGoods.getQuantity();

                quantity = quantity > 1 ? quantity - 1 : quantity;

                cartGoods.setQuantity(quantity);
                tdCartGoodsService.save(cartGoods);
            }
        }

        map.addAttribute("cart_goods_list",
                tdCartGoodsService.updateGoodsInfo(tdCartGoodsService.findByUsername(username)));

        return "/client/cart_goods";
    }

    @RequestMapping(value = "/cart/del", method = RequestMethod.POST)
    public String cartDel(Long id, HttpServletRequest req, ModelMap map) {

        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            username = req.getSession().getId();
        }

        if (null != id) {
            TdCartGoods cartGoods = tdCartGoodsService.findOne(id);

            if (cartGoods.getUsername().equalsIgnoreCase(username)) {
                tdCartGoodsService.delete(cartGoods);
            }
        }

        map.addAttribute("cart_goods_list",
                tdCartGoodsService.updateGoodsInfo(tdCartGoodsService.findByUsername(username)));

        return "/client/cart_goods";
    }
    
    @RequestMapping(value="/cart/changeQuantity",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> changeQuantity(Long id,Long quantity,HttpServletRequest req)
    {
    	Map<String,Object> res = new HashMap<>();
    	res.put("code", 0);
    	
    	if(null != id && null != quantity && quantity != 0)
    	{
    		TdCartGoods cartGoods = tdCartGoodsService.findOne(id);
    		
    		if(null != cartGoods)
    		{
    			Long leftNumber = 0L;
    			if(null != cartGoods.getSpecificaId()){
    				TdSpecificat specificat = tdSpecificatService.findOne(cartGoods.getSpecificaId());
    				if(null != specificat){
    					leftNumber = specificat.getLeftNumber();
    				}
    			}else{
    				TdDistributorGoods distributorGoods = tdDistributorGoodsService.findByIdAndIsInSaleTrue(cartGoods.getDistributorGoodsId());
    				if(null != distributorGoods)
        			{
    					leftNumber = distributorGoods.getLeftNumber();
        			}
    			}
				if(quantity > leftNumber){
					res.put("msg", "商家库存不足，请重新输入");
					return res;
				}else{
					cartGoods.setQuantity(quantity);
					tdCartGoodsService.save(cartGoods);
					res.put("code", 1);
					return res;
				}
    		}
    	}
        res.put("msg", "参数错误");
        return res;
    }
    
    
    /**
     * 添加购物车
     * @author Max
     * 2016-10-19 21:11
     * 
     */
    @RequestMapping(value="/cart/addIncart",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addIncart(Long dis_id,Boolean isSpec,
    					Long specId,Long quantity,HttpServletRequest req){
    	Map<String,Object> res = new HashMap<String, Object>();
    	res.put("code", 0);
    	
    	// 是否已登录
        boolean isLoggedIn = true;

        String username = (String) req.getSession().getAttribute("username");
        if (null == username) {
            isLoggedIn = false;
            username = req.getSession().getId();
        }
        
        TdDistributor distributor = null;
        
        TdDistributorGoods goods = tdDistributorGoodsService.findOne(dis_id);
        
        if(null != req.getSession().getAttribute("DISTRIBUTOR_ID"))
        {
        	Long distributorId= (Long)req.getSession().getAttribute("DISTRIBUTOR_ID");
        	distributor = tdDistributorService.findOne(distributorId);
        	
        }else{
        	Long distributorId = goods.getDisId();
    		distributor = tdDistributorService.findOne(distributorId);
    		req.getSession().setAttribute("DISTRIBUTOR_ID",distributor.getId());
    		req.getSession().setAttribute("distributorTitle", distributor.getTitle());
        }
        
        if(null == quantity){
        	quantity = 1L;
        }
    	
    	// 判断是否选择规格
    	if(null != isSpec && isSpec ==true){
    		if(null == specId){
    			res.put("msg", "请先选择规格");
    			return res;
    		}
    	}
    	
      	if (null != goods) {
      		 List<TdSpecificat> specList = tdSpecificatService.findByShopIdAndGoodsIdAndType(goods.getDisId(), goods.getGoodsId(), 1);
  	      	 if(null != specList && specList.size() > 0){
  	      		 if(null == specId){
  	      			 res.put("msg", "请先选择规格");
  	      			 res.put("code", 0);
  	      			 return res;
  	      		 }
  	      	 }
  	      	 // 查找购物车是否已有此（规格）商品
    		 List<TdCartGoods> cartList = tdCartGoodsService.findByUsernameAndDistributorGoodsIdAndSpecificaId(username, dis_id, specId);
    		
    		 Long goodsLeftNumber = goods.getLeftNumber(); // 商品库存
    		 if(null != specId){
    			TdSpecificat specificat = tdSpecificatService.findOne(specId);
    			if(null != specificat){ // 如果有规格，取规格库存
    				goodsLeftNumber = specificat.getLeftNumber();
    			}
    		 }
    		
    		 if(null != cartList && cartList.size() != 0){
    			
    			TdCartGoods tdCartGoods = cartList.get(0);
    			Long oldQuantity = tdCartGoods.getQuantity();
    			// 购物车已有
    			 if(oldQuantity + quantity >= goodsLeftNumber){
                 	tdCartGoods.setQuantity(goodsLeftNumber);
                 }else{
                 	tdCartGoods.setQuantity(oldQuantity + quantity);
                 }
                 tdCartGoodsService.save(tdCartGoods);
    		 }else{
    			// 购物车没有此（规格）商品
    			TdCartGoods tdCartGoods = new TdCartGoods();
                
    			tdCartGoods = new TdCartGoods();
    			tdCartGoods.setUsername(username);
    			tdCartGoods.setGoodsId(goods.getGoodsId()); // 商品ID
    			tdCartGoods.setGoodsTitle(goods.getGoodsTitle());
    			tdCartGoods.setGoodsSubTitle(goods.getSubGoodsTitle());
    			tdCartGoods.setQuantity(quantity);
    			tdCartGoods.setPrice(goods.getGoodsPrice());
    			tdCartGoods.setIsLoggedIn(isLoggedIn);
    			tdCartGoods.setIsSelected(false);
    			tdCartGoods.setDistributorId(distributor.getId()); // 超市ID
    			tdCartGoods.setDistributorTitle(distributor.getTitle());  
    			tdCartGoods.setDistributorGoodsId(goods.getId());  // 超市商品ID
    			tdCartGoods.setProviderId(goods.getProviderId()); // 分销商ID
    			tdCartGoods.setProviderTite(goods.getProviderTitle());
    			tdCartGoods.setUnit(goods.getUnit());
                tdCartGoods.setSpecificaId(specId); 
                tdCartGoodsService.save(tdCartGoods);
    		}
    		res.put("code", 1);
    		res.put("msg", "添加购物车成功");
            
    	}
    	return res;
    }
    
    @RequestMapping(value="/cart/byNow",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> byNowIncart(Long dis_id,Boolean isSpec,
    					Long specId,Long quantity,HttpServletRequest req){
    	Map<String,Object> res = new HashMap<String, Object>();
    	res.put("code", 0);
    	
    	// 判断是否选择规格
    	if(null != isSpec && isSpec ==true){
    		if(null == specId){
    			res.put("msg", "请先选择规格");
    			return res;
    		}
    	}
    	
        String username = (String) req.getSession().getAttribute("username");
        if (null == username) {
        	res.put("code", 1);
            res.put("msg", "请先登录");
            
            username = req.getSession().getId();
            List<TdCartGoods> cartList = tdCartGoodsService.findByUsername(username);
            if(null != cartList){
            	for (TdCartGoods tdCartGoods : cartList) {
					tdCartGoods.setIsSelected(false);
				}
            	tdCartGoodsService.save(cartList);
            }
            return res;
        }else{
        	List<TdCartGoods> cartList = tdCartGoodsService.findByUsername(username);
            if(null != cartList){
            	for (TdCartGoods tdCartGoods : cartList) {
					tdCartGoods.setIsSelected(false);
				}
            	tdCartGoodsService.save(cartList);
            }
        }
        
        TdDistributorGoods goods = tdDistributorGoodsService.findOne(dis_id);
        
//        List<TdSpecificat> specList = tdSpecificatService.findByShopIdAndGoodsIdAndType(goods.getDisId(), goods.getGoodsId(), 1);
//        if(null != specList){
//        	if(null == specId){
//        		res.put("msg", "请先选择规格");
//        		res.put("code", 0);
//        		return res;
//        	}
//        }
        
        
        TdDistributor distributor = tdDistributorService.findOne(goods.getDisId());
        
        if(null == quantity){
        	quantity = 1L;
        }
    	
    	if (null != goods) {
            // 查找购物车是否已有此（规格）商品
    		List<TdCartGoods> cartLisst = tdCartGoodsService.findByUsernameAndDistributorGoodsIdAndSpecificaId(username, dis_id, specId);
    		
    		if(null != cartLisst && cartLisst.size() != 0){
    			// 购物车已有
             	cartLisst.get(0).setQuantity(quantity);
             	cartLisst.get(0).setIsSelected(true);;
             	tdCartGoodsService.save(cartLisst.get(0));
    		}else{
    			// 购物车没有此（规格）商品
    			TdCartGoods tdCartGoods = new TdCartGoods();
    			tdCartGoods.setUsername(username);
    			tdCartGoods.setGoodsId(goods.getGoodsId()); // 商品ID
    			tdCartGoods.setGoodsTitle(goods.getGoodsTitle());
    			tdCartGoods.setGoodsSubTitle(goods.getSubGoodsTitle());
    			tdCartGoods.setQuantity(quantity);
    			tdCartGoods.setPrice(goods.getGoodsPrice());
    			tdCartGoods.setIsLoggedIn(true);
    			tdCartGoods.setIsSelected(true);
    			tdCartGoods.setDistributorId(distributor.getId()); // 超市ID
    			tdCartGoods.setDistributorTitle(distributor.getTitle());  
    			tdCartGoods.setDistributorGoodsId(goods.getId());  // 超市商品ID
    			tdCartGoods.setProviderId(goods.getProviderId()); // 分销商ID
    			tdCartGoods.setProviderTite(goods.getProviderTitle());
    			tdCartGoods.setUnit(goods.getUnit());
                tdCartGoods.setSpecificaId(specId); // 规格ID

                tdCartGoodsService.save(tdCartGoods);
    		}
    		res.put("code", 2);
    	}
    	return res;
    }
    
}
