package com.ynyes.cslm.touch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ynyes.cslm.entity.TdCartGoods;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.service.TdCartGoodsService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsCombinationService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdUserService;

/**
 * 购物车
 *
 */
@Controller
public class TdTouchCartController {

    @Autowired
    private TdCartGoodsService tdCartGoodsService;

    @Autowired
    private TdGoodsService tdGoodsService;
    
    @Autowired
    private TdGoodsCombinationService tdGoodsCombinationService;

    @Autowired
    private TdCommonService tdCommonService;
    
    @Autowired
    private TdDistributorGoodsService tdDistributorGoodsService;
    
    @Autowired
    private TdDistributorService tdDistributorService;
    
    @Autowired
    private TdUserService tdUserService;

    @RequestMapping(value = "/touch/cart/init")
    public String addCart(Long id, Long quantity, String zhid, Integer m,
            HttpServletRequest req,ModelMap map) {

//      // 是否已登录
        boolean isLoggedIn = true;

        String username = (String) req.getSession().getAttribute("username");
        
        TdDistributor distributor = null;
        if(null != req.getSession().getAttribute("DISTRIBUTOR_ID"))
        {
        	Long distributorId= (Long)req.getSession().getAttribute("DISTRIBUTOR_ID");
        	distributor = tdDistributorService.findOne(distributorId);
        	
        }
        if(null == distributor)
        {
        	return "/client/error_404";
        }
        
        if (null == username) {
            isLoggedIn = false;
            username = req.getSession().getId();
        }
        
        if (null == m)
        {
            m = 0;
        }
        
        if (null == quantity || quantity.compareTo(1L) < 0)
        {
            quantity = 1L;
        }
        
        if (null != id) {
        	TdDistributorGoods goods = tdDistributorGoodsService.findOne(id);
            if (null != goods) {
                
                List<TdCartGoods> oldCartGoodsList = null;
                
                // 购物车是否已有该商品
                oldCartGoodsList = tdCartGoodsService
                                .findByGoodsIdAndUsername(goods.getGoodsId(), username);
                
                // 有多项，则在第一项上数量进行相加
                if (null != oldCartGoodsList && oldCartGoodsList.size() > 0) {
                    long oldQuantity = oldCartGoodsList.get(0).getQuantity();
                    oldCartGoodsList.get(0).setQuantity(oldQuantity + quantity);
                    tdCartGoodsService.save(oldCartGoodsList.get(0));
                }
                // 新增购物车项
                else
                {
                    TdCartGoods cartGoods = new TdCartGoods();
                    
                    cartGoods.setIsLoggedIn(isLoggedIn);
                    cartGoods.setUsername(username);
    
                    cartGoods.setIsSelected(true);
                    cartGoods.setGoodsId(goods.getGoodsId());
                    cartGoods.setDistributorId(distributor.getId());
                    cartGoods.setDistributorTitle(distributor.getTitle());
                    cartGoods.setDistributorGoodsId(goods.getId());
                    // 商品信息在读取购物车时再获取
//                    cartGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
//                    cartGoods.setGoodsTitle(goods.getTitle());
//                    cartGoods.setPrice(goods.getSalePrice());
                    
                    cartGoods.setQuantity(quantity);
                    
                    tdCartGoodsService.save(cartGoods);
                }
            }
        }

        List<TdCartGoods> resList = tdCartGoodsService.findByUsername(username);
        
        map.addAttribute("cart_goods_list", tdCartGoodsService.updateGoodsInfo(resList));

//        if (null != shareId) {
//        	map.addAttribute("shareId", shareId);
//		}
        
        tdCommonService.setHeader(map, req);

        //判断是否为app链接
        Integer isApp = (Integer) req.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
        
        return "/touch/cart";
    }
    
    @RequestMapping(value = "/touch/cart")
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
            List<TdCartGoods> cartUserGoodsList = tdCartGoodsService
                    .findByUsername(username);
            
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
                List<TdCartGoods> findList = tdCartGoodsService
                        .findByGoodsIdAndUsername(cg1.getGoodsId(), username);

                if (null != findList && findList.size() > 1) 
                {
                    tdCartGoodsService.delete(findList.subList(1,findList.size()));
                }
            }
        }

        List<TdCartGoods> resList = tdCartGoodsService.findByUsername(username);
        
        map.addAttribute("user",tdUserService.findByUsername(username));
        
        map.addAttribute("cart_goods_list",tdCartGoodsService.updateGoodsInfo(resList));
        
        
        tdCommonService.setHeader(map, req);

        return "/touch/cart";
    }

    @RequestMapping(value = "/touch/cart/toggleSelect", method = RequestMethod.POST)
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

        return "/touch/cart_goods";
    }

    @RequestMapping(value = "/touch/cart/toggleAll", method = RequestMethod.POST)
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

        return "/touch/cart_goods";
    }

    @RequestMapping(value = "/touch/cart/numberAdd", method = RequestMethod.POST)
    public String cartNumberAdd(Long id, HttpServletRequest req, ModelMap map) {

        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            username = req.getSession().getId();
        }

        // 226 和 1644商品仅限购买一次
        if (null != id && !id.equals(226L) && !id.equals(1644L)) {
            TdCartGoods cartGoods = tdCartGoodsService.findOne(id);

            if (cartGoods.getUsername().equalsIgnoreCase(username)) {
                long quantity = cartGoods.getQuantity();
                cartGoods.setQuantity(quantity + 1);
                tdCartGoodsService.save(cartGoods);
            }
        }

        map.addAttribute("cart_goods_list",
                tdCartGoodsService.updateGoodsInfo(tdCartGoodsService.findByUsername(username)));

        //判断是否为app链接
        Integer isApp = (Integer) req.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
        
        return "/touch/cart_goods";
    }

    @RequestMapping(value = "/touch/cart/numberMinus", method = RequestMethod.POST)
    public String cartNumberMinus(Long id, HttpServletRequest req, ModelMap map) {

        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            username = req.getSession().getId();
        }

        // 226 和 1644商品仅限购买一次
        if (null != id && !id.equals(226L) && !id.equals(1644L)) {
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

      //判断是否为app链接
        Integer isApp = (Integer) req.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
        
        return "/touch/cart_goods";
    }

    @RequestMapping(value = "/touch/cart/del", method = RequestMethod.POST)
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

        //判断是否为app链接
        Integer isApp = (Integer) req.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
        
        return "/touch/cart_goods";
    }
}
