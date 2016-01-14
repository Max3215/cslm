package com.ynyes.cslm.controller.front;

import static org.apache.commons.lang3.StringUtils.leftPad;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.SysexMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csii.payment.client.core.CebMerchantSignVerify;
import com.cslm.payment.alipay.AlipayConfig;
import com.cslm.payment.alipay.Constants;
import com.cslm.payment.alipay.PaymentChannelAlipay;
import com.cslm.payment.alipay.core.AlipayNotify;
import com.ynyes.cslm.entity.TdCartGoods;
import com.ynyes.cslm.entity.TdCoupon;
import com.ynyes.cslm.entity.TdCouponType;
import com.ynyes.cslm.entity.TdDeliveryType;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdGoodsCombination;
import com.ynyes.cslm.entity.TdGoodsDto;
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdOrderGoods;
import com.ynyes.cslm.entity.TdPayRecord;
import com.ynyes.cslm.entity.TdPayType;
import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdProviderGoods;
import com.ynyes.cslm.entity.TdShippingAddress;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.entity.TdUserPoint;
import com.ynyes.cslm.service.TdCartGoodsService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdCouponService;
import com.ynyes.cslm.service.TdCouponTypeService;
import com.ynyes.cslm.service.TdDeliveryTypeService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsCombinationService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdOrderGoodsService;
import com.ynyes.cslm.service.TdOrderService;
import com.ynyes.cslm.service.TdPayRecordService;
import com.ynyes.cslm.service.TdPayTypeService;
import com.ynyes.cslm.service.TdProviderGoodsService;
import com.ynyes.cslm.service.TdProviderService;
import com.ynyes.cslm.service.TdSettingService;
import com.ynyes.cslm.service.TdUserPointService;
import com.ynyes.cslm.service.TdUserService;

import net.sf.json.JSONObject;

/**
 * 订单
 *
 */
@Controller
@RequestMapping("/order")
public class TdOrderController extends AbstractPaytypeController{

    private static final String PAYMENT_ALI = "ALI";

    @Autowired
    private TdCartGoodsService tdCartGoodsService;

    @Autowired
    private TdUserService tdUserService;

    @Autowired
    private TdGoodsService tdGoodsService;

    @Autowired
    private TdOrderGoodsService tdOrderGoodsService;

    @Autowired
    private TdOrderService tdOrderService;

    @Autowired
    private TdDeliveryTypeService tdDeliveryTypeService;

    @Autowired
    private TdCommonService tdCommonService;

    @Autowired
    private TdUserPointService tdUserPointService;

    @Autowired
    private TdCouponService tdCouponService;

    @Autowired
    private TdCouponTypeService tdCouponTypeService;

    @Autowired
    private TdPayRecordService payRecordService;

    @Autowired
    private TdGoodsCombinationService tdGoodsCombinationService;
    
    @Autowired
    private TdPayTypeService tdPayTypeService;

    @Autowired
    private TdSettingService tdSettingService;
    
    @Autowired
    private TdDistributorService tdDistributorService;
    
    @Autowired
    private TdDistributorGoodsService tdDistributoGoodsService;
    
    @Autowired
    private TdProviderGoodsService tdProviderGoodsService;
    
    @Autowired
    private TdProviderService tdProviderService;
    
    @Autowired
    private TdPayRecordService tdPayRecordService;
    
//    @Autowired
//    private PaymentChannelCEB payChannelCEB;
//
//    @Autowired
//    private PaymentChannelAlipay payChannelAlipay;

    /**
     * 立即购买
     * 
     * @param type
     *            购买类型 (comb: 组合购买)
     * @param gid
     *            商品ID
     * @param zhid
     *            组合ID，多个组合商品以","分开
     * @param req
     * @param map
     * @return
     */
    @RequestMapping(value = "/buy/{type}")
    public String orderBuy(@PathVariable String type, Long gid, String zhid,
            HttpServletRequest req, ModelMap map) {
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/login";
        }

        if (null == type || null == gid) {
            return "/client/error_404";
        }

        List<TdGoodsDto> tdGoodsList = new ArrayList<TdGoodsDto>();

        // 组合购买
        if (type.equalsIgnoreCase("comb")) {
            // 购买商品
            TdGoods goods = tdGoodsService.findOne(gid);

            if (null == goods) {
                return "/client/error_404";
            }

            // 优惠券
            map.addAttribute("coupon_list",
                    tdCouponService.findByUsernameAndIsUseable(username));

            // 积分限额
            map.addAttribute("total_point_limit", goods.getPointLimited());

            TdGoodsDto buyGoods = new TdGoodsDto();

            buyGoods.setGoodsId(goods.getId());
            buyGoods.setGoodsTitle(goods.getTitle());
            buyGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
            buyGoods.setPrice(goods.getSalePrice());
            buyGoods.setQuantity(1L);
            buyGoods.setSaleId(0);

            tdGoodsList.add(buyGoods);

            // 添加组合商品
            if (null != zhid && !zhid.isEmpty()) {
              
                String[] zhidArray = zhid.split(",");

                for (String idStr : zhidArray) {
                    if (!idStr.isEmpty()) {
                        Long zid = Long.parseLong(idStr);
    
                        if (null == zid) {
                            continue;
                        }
    
                        TdGoodsCombination combGoods = tdGoodsCombinationService
                                .findOne(zid);
    
                        if (null == combGoods) {
                            continue;
                        }
    
                        TdGoodsDto buyZhGoods = new TdGoodsDto();
    
                        buyZhGoods.setGoodsId(combGoods.getGoodsId());
                        buyZhGoods.setGoodsTitle(combGoods.getGoodsTitle());
                        buyZhGoods.setGoodsCoverImageUri(combGoods
                                .getCoverImageUri());
                        buyZhGoods.setPrice(combGoods.getCurrentPrice());
                        buyZhGoods.setQuantity(1L);
                        buyZhGoods.setSaleId(1);
    
                        tdGoodsList.add(buyZhGoods);
                    }
                }
            }
        }
        // 抢购
        else if (type.equalsIgnoreCase("qiang")) {
            // 购买商品
            TdGoods goods = tdGoodsService.findOne(gid);

            // 检查是否在秒杀
            if (null == goods || null == goods.getIsOnSale()
                    || !goods.getIsOnSale()
                    || !tdGoodsService.isFlashSaleTrue(goods)) {
                return "/client/error_404";
            }

            TdGoodsDto buyGoods = new TdGoodsDto();

            buyGoods.setGoodsId(goods.getId());
            buyGoods.setGoodsTitle(goods.getTitle());
            buyGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
            Double flashSalePrice = tdGoodsService.getFlashPrice(goods);
            
            if (null == flashSalePrice)
            {
                return "/client/error_404";
            }
            
            buyGoods.setPrice(flashSalePrice);
            buyGoods.setQuantity(1L);
            buyGoods.setSaleId(2);

            tdGoodsList.add(buyGoods);
        }
        // 十人团
        else if (type.equalsIgnoreCase("tentuan")) {
            // 购买商品
            TdGoods goods = tdGoodsService.findOne(gid);

            // 检查是否在十人团
            if (null == goods || null == goods.getIsOnSale()
                    || !goods.getIsOnSale()
                    || !tdGoodsService.isGroupSaleTrue(goods)) {
                return "/client/error_404";
            }

            TdGoodsDto buyGoods = new TdGoodsDto();

            buyGoods.setGoodsId(goods.getId());
            buyGoods.setGoodsTitle(goods.getTitle());
            buyGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
            buyGoods.setPrice(goods.getGroupSalePrice());
            buyGoods.setQuantity(1L);
            buyGoods.setSaleId(3);

            tdGoodsList.add(buyGoods);
        }
        // 百人团
        else if (type.equalsIgnoreCase("baituan")) {
            // 购买商品
            TdGoods goods = tdGoodsService.findOne(gid);

            TdGoodsDto buyGoods = new TdGoodsDto();

            buyGoods.setGoodsId(goods.getId());
            buyGoods.setGoodsTitle(goods.getTitle());
            buyGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
            buyGoods.setPrice(goods.getGroupSalePrePayPrice());
            buyGoods.setQuantity(1L);
            buyGoods.setSaleId(4);

            tdGoodsList.add(buyGoods);
        }

        // 购买商品表
        map.addAttribute("buy_goods_list", tdGoodsList);

        // 将购买的商品保存到session
        req.getSession().setAttribute("buy_goods_list", tdGoodsList);
        // 购买类型
        req.getSession().setAttribute("buyType", type);

        // 线下加盟店
        map.addAttribute("shop_list", tdDistributorService.findByIsEnableTrue());

        // 支付方式列表
//        setPayTypes(map, true, false, req);

        // 选中商品
        // map.addAttribute("selected_goods_list", selectedGoodsList);

        tdCommonService.setHeader(map, req);

        if (type.equalsIgnoreCase("comb"))
        {
            return "/client/order_buy_zh";
        }
        else if (type.equalsIgnoreCase("qiang"))
        {
            return "/client/order_buy_qiang";
        }
        else if (type.equalsIgnoreCase("baituan"))
        {
            return "/client/order_buy_bt";
        }
        else
        {
            return "/client/order_buy_tt";
        }
    }

    /**
     * 
     * @param addressId
     * @param shopId
     * @param payTypeId
     * @param deliveryTypeId
     * @param isNeedInvoice
     * @param invoiceTitle
     * @param userMessage
     * @param appointmentTime
     * @param req
     * @param map
     * @return
     */
    @RequestMapping(value = "/buysubmit", method = RequestMethod.POST)
    public String buySubmit(Long addressId, // 送货地址
            Long shopId, Long payTypeId, // 支付方式ID
            Long couponId, Long deliveryTypeId, // 配送方式ID
            Long pointUse, // 使用粮草
            Boolean isNeedInvoice, // 是否需要发票
            String invoiceTitle, // 发票抬头
            String userMessage, // 用户留言
            String appointmentTime, HttpServletRequest req, ModelMap map) {
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/login";
        }

        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);

        if (null == user) {
            return "/client/error_404";
        }

        String buyType = (String) req.getSession().getAttribute("buyType");

        if (null == buyType) {
            return "/client/error_404";
        }

        double payTypeFee = 0.0;
        double deliveryTypeFee = 0.0;
        double pointFee = 0.0;
        double couponFee = 0.0;

        // 订单商品
        List<TdOrderGoods> orderGoodsList = new ArrayList<TdOrderGoods>();

        // 商品总价
        Double totalGoodsPrice = 0.0;

        // 商品总尾款
        Double totalLeftPrice = 0.0;

        // 返粮草总额
        Long totalPointReturn = 0L;

        // 组合购买
        if (buyType.equalsIgnoreCase("comb")) {
            @SuppressWarnings("unchecked")
            List<TdGoodsDto> tdGoodsList = (List<TdGoodsDto>) req.getSession()
                    .getAttribute("buy_goods_list");

            if (null != tdGoodsList && tdGoodsList.size() > 0) {
                for (TdGoodsDto buyGoods : tdGoodsList) {
                    // 原商品
                    TdGoods goods = tdGoodsService.findOne(buyGoods
                            .getGoodsId());

                    // 不存在该商品或已下架或已售罄，则跳过
                    if (null == goods || !goods.getIsOnSale()
                            || null == goods.getLeftNumber()
                            || goods.getLeftNumber().compareTo(1L) < 0) {
                        return "/client/error_404";
                    }

                    TdOrderGoods orderGoods = new TdOrderGoods();

                    // 商品信息
                    orderGoods.setGoodsId(goods.getId());
                    orderGoods.setGoodsTitle(goods.getTitle());
                    orderGoods.setGoodsSubTitle(goods.getSubTitle());
                    orderGoods.setGoodsCoverImageUri(goods.getCoverImageUri());

                    // 是否已申请退货
                    orderGoods.setIsReturnApplied(false);

                    // 正常销售
                    if (0 == buyGoods.getSaleId()) {
                        orderGoods.setPrice(goods.getSalePrice());

                        // 销售方式
                        orderGoods.setGoodsSaleType(0);

                        // 商品总价
                        totalGoodsPrice += goods.getSalePrice();

                    } else { // 组合销售
                        orderGoods.setPrice(buyGoods.getPrice());

                        // 销售方式
                        orderGoods.setGoodsSaleType(1);

                        // 商品总价
                        totalGoodsPrice += buyGoods.getPrice();
                    }

                    // 数量
                    orderGoods.setQuantity(1L);

                    // 获得积分
                    if (null != goods.getReturnPoints()) {
                        totalPointReturn += goods.getReturnPoints();
                        orderGoods.setPoints(goods.getReturnPoints());
                    }

                    orderGoodsList.add(orderGoods);

                    // 更新销量
                    Long soldNumber = goods.getSoldNumber();
                    Long leftNumber = goods.getLeftNumber();

                    if (null == soldNumber) {
                        soldNumber = 0L;
                    }

                    soldNumber += 1L;
                    goods.setSoldNumber(soldNumber);
                    goods.setLeftNumber(leftNumber - 1);

                    // 保存商品
                    tdGoodsService.save(goods, username);
                }
            }

            // 使用粮草
            if (null != user.getTotalPoints()) {
                if (pointUse.compareTo(user.getTotalPoints()) >= 0) {
                    pointUse = user.getTotalPoints();
                }
            }
        }

        if (null == orderGoodsList || orderGoodsList.size() <= 0) {
            return "/client/error_404";
        }

        // 安装信息
        TdShippingAddress address = null;

        if (null != addressId) {

            List<TdShippingAddress> addressList = user.getShippingAddressList();

            for (TdShippingAddress add : addressList) {
                if (add.getId().equals(addressId)) {
                    address = add;
                    break;
                }
            }
        }

        TdOrder tdOrder = new TdOrder();

        Date current = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String curStr = sdf.format(current);
        Random random = new Random();

        // 预约时间
        if (null != appointmentTime) {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 小写的mm表示的是分钟

            try {
                Date appTime = sdf.parse(appointmentTime);

                tdOrder.setAppointmentTime(appTime);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // 基本信息
        tdOrder.setUsername(user.getUsername());
        tdOrder.setOrderTime(current);

        // 订单号
        tdOrder.setOrderNumber("P" + curStr
                + leftPad(Integer.toString(random.nextInt(999)), 3, "0"));

        // 安装信息
        if (null != address) {

            tdOrder.setShippingName(address.getReceiverName());
            tdOrder.setShippingAddress(address.getProvince()
                    + address.getCity() + address.getDisctrict()
                    + address.getDetailAddress());
        }

        if (null != payTypeId) {
            TdPayType payType = tdPayTypeService.findOne(payTypeId);

            // 支付类型
            payTypeFee = payType.getFee();
            tdOrder.setPayTypeId(payType.getId());
            tdOrder.setPayTypeTitle(payType.getTitle());
            tdOrder.setPayTypeFee(payTypeFee);
            tdOrder.setIsOnlinePay(payType.getIsOnlinePay());
        }

        // 配送方式
        if (null != deliveryTypeId) {
            TdDeliveryType deliveryType = tdDeliveryTypeService
                    .findOne(deliveryTypeId);
            tdOrder.setDeliverTypeId(deliveryType.getId());
            tdOrder.setDeliverTypeTitle(deliveryType.getTitle());
            tdOrder.setDeliverTypeFee(deliveryType.getFee());
            deliveryTypeFee = deliveryType.getFee();
        }

        // 线下加盟超市
        if (null != shopId) {
            TdDistributor shop = tdDistributorService.findOne(shopId);

            if (null != shop) {
                tdOrder.setShopId(shop.getId());
                tdOrder.setShopTitle(shop.getTitle());
                
                // 用户归属
                if (null != user.getUpperDiySiteId())
                {
                    user.setUpperDiySiteId(shop.getId());
                    user = tdUserService.save(user);
                }
            }
        }
        
        // 用户留言
        tdOrder.setUserRemarkInfo(userMessage);

        if (buyType.equalsIgnoreCase("comb"))
        {
            // 使用积分
            tdOrder.setPointUse(pointUse);

            // 优惠券
            if (null != couponId) {
                TdCoupon coupon = tdCouponService.findOne(couponId);

                if (null != coupon) {
                    TdCouponType couponType = tdCouponTypeService
                            .findOne(coupon.getId());

                    couponFee = couponType.getPrice();
                    coupon.setIsUsed(true);
                    tdCouponService.save(coupon);
                }
            }

            // 粮草奖励
            tdOrder.setPoints(totalPointReturn);

            pointFee = pointUse / 1;

            // 总价
            tdOrder.setTotalPrice(totalGoodsPrice + payTypeFee
                    + deliveryTypeFee - pointFee - couponFee);
            
            // 添加积分使用记录
            if (null != user) {
                if (null == user.getTotalPoints())
                {
                    user.setTotalPoints(0L);
                    
                    user = tdUserService.save(user);
                }
                
                if (pointUse.compareTo(0L) >= 0
                        && null != user.getTotalPoints()
                        && user.getTotalPoints().compareTo(pointUse) >= 0) {
                    TdUserPoint userPoint = new TdUserPoint();
                    userPoint.setDetail("购买商品使用积分抵扣");
                    userPoint.setOrderNumber(tdOrder.getOrderNumber());
                    userPoint.setPoint(0 - pointUse);
                    userPoint.setPointTime(new Date());
                    userPoint.setUsername(username);
                    userPoint.setTotalPoint(user.getTotalPoints() - pointUse);
                    tdUserPointService.save(userPoint);

                    user.setTotalPoints(user.getTotalPoints() - pointUse);
                    tdUserService.save(user);
                }
            }
        }
        else
        {
         // 总价
            tdOrder.setTotalPrice(totalGoodsPrice + payTypeFee + deliveryTypeFee);
        }

        // 待付款
        tdOrder.setStatusId(2L);

        // 需付尾款额
//        tdOrder.setTotalLeftPrice(totalLeftPrice);

        // 发票
        if (null != isNeedInvoice) {
            tdOrder.setIsNeedInvoice(isNeedInvoice);
            tdOrder.setInvoiceTitle(invoiceTitle);
        } else {
            tdOrder.setIsNeedInvoice(false);
        }

        // 订单商品
        tdOrder.setOrderGoodsList(orderGoodsList);
        tdOrder.setTotalGoodsPrice(totalGoodsPrice);

        // 保存订单商品及订单
        tdOrderGoodsService.save(orderGoodsList);
       
        tdOrder.setTypeId(0L);
//        for(TdOrderGoods tdOrderGoods : orderGoodsList){
//        	if (tdOrderGoods.getGoodsSaleType() == 1) {
//				tdOrder.setTypeId(2L);
//			}       	
//        }
//        //抢购 团购 都只有一个商品
//        for(TdOrderGoods tdOrderGoods : orderGoodsList){
//        	if (tdOrderGoods.getGoodsSaleType() == 2) {
//				tdOrder.setTypeId(3L);
//			}
//        	else if (tdOrderGoods.getGoodsSaleType() == 3) {
//        		tdOrder.setTypeId(4L);
//			}
//        	else if (tdOrderGoods.getGoodsSaleType() == 4) {
//        		tdOrder.setTypeId(5L);
//			}
//        }
        
        tdOrder = tdOrderService.save(tdOrder);

        // if (tdOrder.getIsOnlinePay()) {
        return "redirect:/order/pay?orderId=" + tdOrder.getId();
        // }

        // return "redirect:/order/success?orderId=" + tdOrder.getId();
    }

    @RequestMapping(value = "/info")
    public String orderInfo(HttpServletRequest req, HttpServletResponse resp,
            ModelMap map) {
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/login";
        }

        // 把所有的购物车项转到该登陆用户下
        String sessionId = req.getSession().getId();

        List<TdCartGoods> cartGoodsList = tdCartGoodsService
                .findByUsername(sessionId);

        if (null != cartGoodsList && cartGoodsList.size() > 0) {
            for (TdCartGoods cartGoods : cartGoodsList) {
                cartGoods.setUsername(username);
                cartGoods.setIsLoggedIn(true);
            }
            tdCartGoodsService.save(cartGoodsList);
        }

        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);

        if (null != user) {
            map.addAttribute("user", user);
        }

        List<TdCartGoods> selectedGoodsList = tdCartGoodsService
                .findByUsernameAndIsSelectedTrue(username);

        Long totalPointLimited = 0L;// 积分限制综总和
        Double totalPrice = 0.0; // 购物总额

        // 积分限制总和 和 购物总额
        if (null != selectedGoodsList) {
            for (TdCartGoods cg : selectedGoodsList) {
                TdGoods goods = tdGoodsService.findOne(cg.getGoodsId());
                if (null != goods && null != goods.getPointLimited()) {
                    totalPointLimited += goods.getPointLimited()
                            * cg.getQuantity();
                    totalPrice += cg.getPrice() * cg.getQuantity();
                }
            }
        }

        map.addAttribute("coupon_list",
                tdCouponService.findByUsernameAndIsUseable(username));

        // 积分限额
        map.addAttribute("total_point_limit", totalPointLimited);

        // 线下加盟超市
        map.addAttribute("shop_list", tdDistributorService.findByIsEnableTrue());

        // 支付方式列表
        setPayTypes(map, true, false, req);
        
        map.addAttribute("totalPrice", totalPrice);
        // 配送方式
        map.addAttribute("delivery_type_list",
                tdDeliveryTypeService.findByIsEnableTrue());

        // 选中商品
        map.addAttribute("buy_goods_list", selectedGoodsList);

        tdCommonService.setHeader(map, req);

        return "/client/order_info";
    }

    /**
     * 购物车数量加减
     * 
     * @param req
     * @param resp
     * @param type
     *            加减标志位
     * @param gid
     *            商品ID
     * @param map
     * @return
     */
    @RequestMapping(value = "/goods/{type}")
    public String orderEdit(HttpServletRequest req, HttpServletResponse resp,
            @PathVariable String type, Long gid, ModelMap map) {
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/login";
        }

        // 把所有的购物车项转到该登陆用户下
        List<TdCartGoods> cgList = tdCartGoodsService
                .findByUsernameAndIsSelectedTrue(username);

        if (null != cgList && null != type && null != gid) {
            for (TdCartGoods cg : cgList) {
                if (gid.equals(cg.getGoodsId())) {
                    TdGoods goods = tdGoodsService.findOne(cg.getGoodsId());

                    if (null != goods) {
                        if (type.equalsIgnoreCase("plus")) {
                            // 闪购
                            if (goods.getIsFlashSale()
                                    && goods.getFlashSaleStartTime().before(
                                            new Date())
                                    && goods.getFlashSaleStopTime().after(
                                            new Date())
                                    && cg.getPrice().equals(
                                            goods.getFlashSalePrice())) {
                                if (cg.getQuantity().compareTo(
                                        goods.getFlashSaleLeftNumber()) < 0) {
                                    cg.setQuantity(cg.getQuantity() + 1L);
                                }
                            } else {
                                if (cg.getQuantity().compareTo(
                                        goods.getLeftNumber()) < 0) {
                                    cg.setQuantity(cg.getQuantity() + 1L);
                                }
                            }
                        } else {
                            if (cg.getQuantity().compareTo(1L) > 0) {
                                cg.setQuantity(cg.getQuantity() - 1L);
                            }
                        }

                        tdCartGoodsService.save(cg);
                        break;
                    }
                }
            }
        }
        //
        // List<TdCartGoods> selectedGoodsList =
        // tdCartGoodsService.findByUsernameAndIsSelectedTrue(username);
        // map.addAttribute("selected_goods_list", selectedGoodsList);

        return "redirect:/order/info";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submit(Long addressId, // 送货地址
            Long payTypeId, // 支付方式ID
            Long pointUse, // 使用粮草
            Boolean isNeedInvoice, // 是否需要发票
            String invoiceTitle, // 发票抬头
            String userMessage, // 用户留言
            Long couponId, // 优惠券ID
            String type,
            HttpServletRequest req, ModelMap map) {
        String username = (String) req.getSession().getAttribute("username");
       
        if (null == username) {
            return "redirect:/login";
        }

        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);

        if (null == user) {
            return "/client/error_404";
        }

        double payTypeFee = 0.0;

        // 收货地址
        TdShippingAddress address = null;

        if (null != addressId) {
            if (null == pointUse) {
                pointUse = 0L;
            }

            List<TdShippingAddress> addressList = user.getShippingAddressList();

            for (TdShippingAddress add : addressList) {
                if (add.getId().equals(addressId)) {
                    address = add;
                    break;
                }
            }
        }

        // 使用粮草
        if (null != user.getTotalPoints()) {
            if (pointUse.compareTo(user.getTotalPoints()) >= 0) {
                pointUse = user.getTotalPoints();
            }
        }

        // 购物车商品
        List<TdCartGoods> cartSelectedGoodsList = tdCartGoodsService
                .findByUsernameAndIsSelectedTrue(username);

        // 储存超市Id 和对应商品  为拆分订单做准备
        Map<Long,List<TdCartGoods>> cartGoodsMap =new HashMap<>();
        
        if(null != cartSelectedGoodsList)
        {
        	for (TdCartGoods cartGoods: cartSelectedGoodsList) 
        	{
				if(cartGoods.getIsSelected())
				{
					if(cartGoodsMap.containsKey(cartGoods.getDistributorId()))
					{
						cartGoodsMap.get(cartGoods.getDistributorId()).add(cartGoods);
					}else{
						List<TdCartGoods> clist=new ArrayList<>();
						clist.add(cartGoods);
						cartGoodsMap.put(cartGoods.getDistributorId(),clist);
					}
					
				}
			}
        }
        
       
        if(null == cartGoodsMap || cartGoodsMap.size() <= 0){
        	return "/client/error_404";
        }
        
        TdOrder order = new TdOrder();
        // 订单拆分
        Set<Entry<Long,List<TdCartGoods>>> set = cartGoodsMap.entrySet();
        Iterator<Entry<Long, List<TdCartGoods>>> iterator = set.iterator();
        
        while(iterator.hasNext())
        {
        	Map.Entry<Long,List<TdCartGoods> > m  = iterator.next();
        	
        	TdOrder tdOrder = new TdOrder();
        	List<TdOrderGoods> orderGoodsList = new ArrayList<TdOrderGoods>();
        	
        	
            Double totalGoodsPrice = 0.0;// 商品总价
            Double totalPrice= 0.0;// 订单总价
            Double returnPrice =0.0;// 商品返利
            Double postPrice = 0.0;// 配送费
            Double aliPrice = 0.0;	// 第三方使用费
            Long totalPointReturn = 0L;	// 积分总额
            Double servicePrice=0.0;// 平台服务费
            
            for (int i = 0; i < m.getValue().size(); i++) 
            {
            	TdCartGoods cartGoods= m.getValue().get(i);
	       		TdDistributorGoods distributorGoods = tdDistributoGoodsService.findOne(cartGoods.getDistributorGoodsId());
	       		 
	       		
	       		if(null == distributorGoods || distributorGoods.getIsOnSale()==false)
	       		{
	       			continue;
	       		}
	       		
	       		TdGoods goods = tdGoodsService.findOne(distributorGoods.getGoodsId());
	       		 
	       		TdOrderGoods orderGoods = new TdOrderGoods();

                // 商品信息
                orderGoods.setGoodsId(distributorGoods.getId());
                orderGoods.setGoodsTitle(distributorGoods.getGoodsTitle());
                orderGoods.setGoodsSubTitle(goods.getSubTitle());
                orderGoods.setGoodsCoverImageUri(distributorGoods.getCoverImageUri());
                orderGoods.setSelectOneValue(goods.getSelectOneValue());
                orderGoods.setSelectTwoValue(goods.getSelectTwoValue());
                orderGoods.setSelectThreeValue(goods.getSelectThreeValue());
                orderGoods.setGoodsCode(distributorGoods.getCode());
                orderGoods.setSaleTime(new Date());

                // 是否已申请退货
                orderGoods.setIsReturnApplied(false);

                // 销售方式
                orderGoods.setGoodsSaleType(0);
                
                // 销售价
                orderGoods.setPrice(distributorGoods.getGoodsPrice());
                
                // 数量
                long quantity=0;
                if(distributorGoods.getIsDistribution()){
                	quantity=1L;
                }else{
                	quantity = Math.min(cartGoods.getQuantity(),
                			distributorGoods.getLeftNumber());
                }

                orderGoods.setQuantity(quantity);
                
                // 获得积分
                if (null != goods.getReturnPoints()) 
                {
                    totalPointReturn += goods.getReturnPoints() * quantity;
                    orderGoods
                            .setPoints(goods.getReturnPoints() * quantity);
                }
                
                // 商品总价
                totalGoodsPrice += cartGoods.getPrice()
                        * cartGoods.getQuantity();
                
                orderGoodsList.add(orderGoods);
                
                // 分销商品添加发货商信息
                if(distributorGoods.getIsDistribution()){
                	tdOrder.setProviderId(distributorGoods.getProviderId());
                	tdOrder.setProviderTitle(distributorGoods.getProviderTitle());
                	
                	TdProviderGoods providerGoods = tdProviderGoodsService.findByProviderIdAndGoodsId(distributorGoods.getProviderId(), distributorGoods.getGoodsId());
                	
                	// 分销返利
                	returnPrice +=cartGoods.getPrice()*providerGoods.getShopReturnRation()*cartGoods.getQuantity();
                }
                
                // 更新销量
                Long soldNumber = distributorGoods.getSoldNumber();

                if (null == soldNumber) {
                    soldNumber = 0L;
                }

                soldNumber += quantity;
                distributorGoods.setSoldNumber(soldNumber);
                
                if(!distributorGoods.getIsDistribution()){
                	// 更新库存
                	Long leftNumber = distributorGoods.getLeftNumber();
                	if (leftNumber >= quantity) {
                		leftNumber = leftNumber - quantity;
                	}               
                	distributorGoods.setLeftNumber(leftNumber);
                }
                
                tdDistributoGoodsService.save(distributorGoods);
			}
            
            
            TdDistributor distributor = tdDistributorService.findOne(m.getKey());
            
            Date current = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String curStr = sdf.format(current);
            Random random = new Random();

            // 基本信息
            tdOrder.setUsername(user.getUsername());
            tdOrder.setOrderTime(current);


            // 发货信息
            if (null != address) {

                tdOrder.setPostalCode(address.getPostcode());

                tdOrder.setShippingName(address.getReceiverName());
                tdOrder.setShippingPhone(address.getReceiverMobile());
                tdOrder.setShippingAddress(address.getProvince()
                        + address.getCity() + address.getDisctrict()
                        + address.getDetailAddress());
            }

            if (null != payTypeId) {
                TdPayType payType = tdPayTypeService.findOne(payTypeId);

                // 支付类型
                payTypeFee = payType.getFee();
                tdOrder.setPayTypeId(payType.getId());
                tdOrder.setPayTypeTitle(payType.getTitle());
                tdOrder.setPayTypeFee(payTypeFee);
                tdOrder.setIsOnlinePay(payType.getIsOnlinePay());
            }

            // 使用积分
            tdOrder.setPointUse(pointUse);

            // 用户留言
            tdOrder.setUserRemarkInfo(userMessage);

            // 待付款
            tdOrder.setStatusId(2L);

            if(null != distributor.getPostPrice())
            {
            	// 计算邮费
            	postPrice += distributor.getPostPrice();
            	
            	// 判断是否满额免
            	if(totalGoodsPrice > distributor.getMaxPostPrice())
            	{
            		totalPrice += totalGoodsPrice;
            	}else{
            		totalPrice += totalGoodsPrice+postPrice;
            	}
            	
            }
            else
            {
            	totalPrice += totalGoodsPrice;
            }
            
            // 发票
            if (null != isNeedInvoice) {
                tdOrder.setIsNeedInvoice(isNeedInvoice);
                tdOrder.setInvoiceTitle(invoiceTitle);
            } else {
                tdOrder.setIsNeedInvoice(false);
            }

            // 订单商品
            tdOrder.setOrderGoodsList(orderGoodsList);
            tdOrder.setTotalGoodsPrice(totalGoodsPrice);

            // 粮草奖励
            tdOrder.setPoints(totalPointReturn);

            // 保存订单商品及订单
            tdOrderGoodsService.save(orderGoodsList);
           
            // 添加订单超市信息
        	tdOrder.setShopId(distributor.getId());
        	tdOrder.setShopTitle(distributor.getTitle());
            
        	
            if(null != type && type.equals("pro")){
            	// 订单号
            	tdOrder.setOrderNumber("Y" + curStr
                        + leftPad(Integer.toString(random.nextInt(999)), 3, "0"));
            	
            	// 设置订单类型 预定
            	tdOrder.setTypeId(2L);
            	
            	tdOrder.setTotalLeftPrice(returnPrice);
            	
            	TdProvider provider = tdProviderService.findOne(tdOrder.getProviderId());
            	if(null != provider.getAliRation()) 
            	{
            		aliPrice += provider.getAliRation()*totalGoodsPrice; // 第三方使用费
            	}
            	if(null != provider.getServiceRation())
            	{
            		servicePrice += provider.getServiceRation()*totalGoodsPrice; // 平台服务费
            	}
            	
            }else{
            	tdOrder.setOrderNumber("P" + curStr
            			+ leftPad(Integer.toString(random.nextInt(999)), 3, "0"));
            	
            	 // 设置订单类型
            	 tdOrder.setTypeId(0L);
            	 
        		 if(null != distributor.getAliRation()) // 计算平台收费总比例
              	 {
        			 aliPrice += distributor.getAliRation()*totalGoodsPrice; // 第三方使用费
              	 }
        		 if(null != distributor.getServiceRation())
        		 {
        			 servicePrice +=distributor.getServiceRation()*totalGoodsPrice; // 平台服务费
        		 }
            	
            }
            
            tdOrder.setTotalGoodsPrice(totalGoodsPrice); // 商品总价
            tdOrder.setTotalPrice(totalPrice); // 订单总价
            tdOrder.setPostPrice(postPrice); // 邮费
            tdOrder.setTrainService(servicePrice); // 平台服务费
            tdOrder.setAliPrice(aliPrice); // 第三方使用费
            
			tdDistributorService.save(distributor);
            
            order = tdOrderService.save(tdOrder);

//            // 添加积分使用记录
//            if (null != user) {
//                if (null == user.getTotalPoints())
//                {
//                    user.setTotalPoints(0L);
//                    
//                    user = tdUserService.save(user);
//                }
//                
//                if (pointUse.compareTo(0L) >= 0 && null != user.getTotalPoints()
//                        && user.getTotalPoints().compareTo(pointUse) >= 0) {
//                    TdUserPoint userPoint = new TdUserPoint();
//                    userPoint.setDetail("购买商品使用积分抵扣");
//                    userPoint.setOrderNumber(tdOrder.getOrderNumber());
//                    userPoint.setPoint(0 - pointUse);
//                    userPoint.setPointTime(new Date());
//                    userPoint.setUsername(username);
//                    userPoint.setTotalPoint(user.getTotalPoints() - pointUse);
//                    tdUserPointService.save(userPoint);
//
//                    user.setTotalPoints(user.getTotalPoints() - pointUse);
//                    tdUserService.save(user);
//                }
//            }
        }

        // 删除已生成订单的购物车项
        tdCartGoodsService.delete(cartSelectedGoodsList);
         
         return "redirect:/order/pay?orderId=" + order.getId();
        // return "redirect:/order/success?orderId=" + tdOrder.getId();
    }

    @RequestMapping(value = "/success")
    public String success(Long orderId, ModelMap map, HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/login";
        }

        tdCommonService.setHeader(map, req);

        if (null == orderId) {
            return "/client/error_404";
        }

        map.addAttribute("order", tdOrderService.findOne(orderId));

        return "/client/order_success";
    }

    /**
     * 支付选择页面
     * 
     * @param orderId
     *            订单ID
     * @param map
     * @param req
     * @return
     */
    @RequestMapping(value = "/pay")
    public String pay(Long orderId, ModelMap map, HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/login";
        }

        tdCommonService.setHeader(map, req);

        if (null == orderId) {
            return "/client/error_404";
        }

        map.addAttribute("order", tdOrderService.findOne(orderId));

        return "/client/order_pay";
    }

    /**
     * 支付
     * 
     * @param orderId
     * @param map
     * @param req
     * @return
     */
    @RequestMapping(value = "/dopay/{orderId}")
    public String payOrder(@PathVariable Long orderId, ModelMap map,
            HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/login";
        }

        tdCommonService.setHeader(map, req);

        if (null == orderId) {
            return "/client/error_404";
        }

        TdOrder order = tdOrderService.findOne(orderId);

        if (null == order) {
            return "/client/error_404";
        }
        
        // 	判断订单是否过时 订单提交后24小时内
        Date cur = new Date();
        long temp = cur.getTime() - order.getOrderTime().getTime();
        if (temp > 1000 * 3600 * 24) {
        	order.setSortId(7L);
        	tdOrderService.save(order);
            return "/client/overtime";
        }

        // 待付款
        if (!order.getStatusId().equals(2L)) {
            return "/client/error_404";
        }

        String amount = order.getTotalPrice().toString();
        req.setAttribute("totalPrice", amount);

        String payForm = "";

        Long payId = order.getPayTypeId();
        TdPayType payType = tdPayTypeService.findOne(payId);
        if (payType != null) {
            TdPayRecord record = new TdPayRecord();
            record.setCreateTime(new Date());
            record.setOrderId(order.getId());
            record.setPayTypeId(payType.getId());
            record.setStatusCode(1);
            record.setCreateTime(new Date());
            record = payRecordService.save(record);

            String payRecordId = record.getId().toString();
            int recordLength = payRecordId.length();
            if (recordLength > 6) {
                payRecordId = payRecordId.substring(recordLength - 6);
            } else {
                payRecordId = leftPad(payRecordId, 6, "0");
            }
            req.setAttribute("payRecordId", payRecordId);

            req.setAttribute("orderNumber", order.getOrderNumber());

            String payCode = payType.getCode();
            if (PAYMENT_ALI.equals(payCode)) {
            	PaymentChannelAlipay paymentChannelAlipay = new PaymentChannelAlipay();
                payForm = paymentChannelAlipay.getPayFormData(req);
                map.addAttribute("charset", AlipayConfig.CHARSET);
            }
            else {
                // 其他目前未实现的支付方式
                return "/client/error_404";
            }
        } else {
            return "/client/error_404";
        }

        order.setPayTime(new Date());

        tdOrderService.save(order);
       
        map.addAttribute("payForm", payForm);

        return "/client/order_pay_form";
    }

    /**
     * 支付尾款
     * 
     * @param orderId
     * @param map
     * @param req
     * @return
     */
    @RequestMapping(value = "/dopayleft/{orderId}")
    public String payOrderLeft(@PathVariable Long orderId, ModelMap map,
            HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/login";
        }

        tdCommonService.setHeader(map, req);

        if (null == orderId) {
            return "/client/error_404";
        }

        TdOrder order = tdOrderService.findOne(orderId);

        if (null == order) {
            return "/client/error_404";
        }

        // 待付尾款
        if (!order.getStatusId().equals(3L)) {
            return "/client/error_404";
        }

//        String amount = order.getTotalLeftPrice().toString();
//        req.setAttribute("totalPrice", amount);

        String payForm = "";

        Long payId = order.getPayTypeId();
        TdPayType payType = tdPayTypeService.findOne(payId);
//        if (payType != null) {
//            TdPayRecord record = new TdPayRecord();
//            record.setCreateTime(new Date());
//            record.setOrderId(order.getId());
//            record.setPayTypeId(payType.getId());
//            record.setStatusCode(1);
//            record.setCreateTime(new Date());
//            record = payRecordService.save(record);
//
//            String payRecordId = record.getId().toString();
//            int recordLength = payRecordId.length();
//            if (recordLength > 6) {
//                payRecordId = payRecordId.substring(recordLength - 6);
//            } else {
//                payRecordId = leftPad(payRecordId, 6, "0");
//            }
//            req.setAttribute("payRecordId", payRecordId);
//
//            req.setAttribute("orderNumber", order.getOrderNumber());
//
//            String payCode = payType.getCode();
//            if (PAYMENT_ALI.equals(payCode)) {
//                payForm = payChannelAlipay.getPayFormData(req);
//                map.addAttribute("charset", AlipayConfig.CHARSET);
//            } else if (CEBPayConfig.INTER_B2C_BANK_CONFIG.keySet().contains(
//                    payCode)) {
//                req.setAttribute("payMethod", payCode);
//                payForm = payChannelCEB.getPayFormData(req);
//                map.addAttribute("charset", "GBK");
//            } else {
//                // 其他目前未实现的支付方式
//                return "/client/error_404";
//            }
//        } else {
//            return "/client/error_404";
//        }

        order.setPayTime(new Date());

        tdOrderService.save(order);

        map.addAttribute("payForm", payForm);

        return "/client/order_pay_form";
    }

    @RequestMapping(value = "/pay/success")
    public String paySuccess(ModelMap map, HttpServletRequest req) {
        // String username = (String) req.getSession().getAttribute("username");
        //
        // if (null == username) {
        // return "redirect:/login";
        // }

        tdCommonService.setHeader(map, req);

        return "/client/order_pay_success";
    }

    @RequestMapping(value = "/pay/notify")
    public String payNotify(ModelMap map, HttpServletRequest req) {
        // String username = (String) req.getSession().getAttribute("username");
        //
        // if (null == username) {
        // return "redirect:/login";
        // }

        tdCommonService.setHeader(map, req);

        return "/client/order_pay";
    }

    /*
     * 
     */
    @RequestMapping(value = "/pay/notify_alipay")
    public void payNotifyAlipay(ModelMap map, HttpServletRequest req,
            HttpServletResponse resp) {
    	PaymentChannelAlipay paymentChannelAlipay = new PaymentChannelAlipay();
        paymentChannelAlipay.doResponse(req, resp);
    }

    /*
     * 
     */
//    @RequestMapping(value = "/pay/notify_cebpay")
//    public void payNotifyCEBPay(ModelMap map, HttpServletRequest req,
//            HttpServletResponse resp) {
//        payChannelCEB.doResponse(req, resp);
//    }

    /*
     * 
     */
    @RequestMapping(value = "/pay/result_alipay")
    public String payResultAlipay(ModelMap map, HttpServletRequest req,
            HttpServletResponse resp) {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = req.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter
                .hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"),
                        AlipayConfig.CHARSET);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put(name, valueStr);
        }

        // 获取支付宝的返回参数
        String orderNo = "";
        String trade_status = "";
        try {
            // 商户订单号
            orderNo = new String(req.getParameter(Constants.KEY_OUT_TRADE_NO)
                    .getBytes("ISO-8859-1"), AlipayConfig.CHARSET);
            // 交易状态
            trade_status = new String(req.getParameter("trade_status")
                    .getBytes("ISO-8859-1"), AlipayConfig.CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 计算得出通知验证结果
        boolean verify_result = AlipayNotify.verify(params);

        tdCommonService.setHeader(map, req);
        orderNo = (orderNo == null) ? "" : (orderNo.length() < 6) ? orderNo
                : orderNo.substring(0, orderNo.length() - 6);
        TdOrder order = tdOrderService.findByOrderNumber(orderNo);
        if (order == null) {
            // 订单不存在
            return "/client/order_pay_failed";
        }
        map.put("order", order);
        if (verify_result) {// 验证成功
            if ("WAIT_SELLER_SEND_GOODS".equals(trade_status)) {

                // 订单支付成功
                afterPaySuccess(order);

                return "/client/order_pay_success";
            }
        }

        // 验证失败或者支付失败
        return "/client/order_pay_failed";
    }

    /*
     * 
     */
    @RequestMapping(value = "/pay/result_cebpay")
    public String payResultCEBPay(ModelMap map, HttpServletRequest req,
            HttpServletResponse resp) {
        tdCommonService.setHeader(map, req);

        String plainData = req.getParameter("Plain");
        String signature = req.getParameter("Signature");

        // 计算得出通知验证结果
        boolean verify_result = CebMerchantSignVerify
                .merchantVerifyPayGate_ABA(signature, plainData);
        String plainObjectStr = "";

        if (plainData.endsWith("~|~")) {
            plainObjectStr = plainData.substring(0, plainData.length() - 3);
        }

        plainObjectStr = plainObjectStr.replaceAll("=", "\":\"").replaceAll(
                "~\\|~", "\",\"");
        plainObjectStr = "{\"" + plainObjectStr + "\"}";

        JSONObject paymentResult = JSONObject.fromObject(plainObjectStr);

        String orderNo = paymentResult.getString("orderId");
        orderNo = (orderNo == null) ? "" : (orderNo.length() < 6) ? orderNo
                : orderNo.substring(0, orderNo.length() - 6);
        TdOrder order = tdOrderService.findByOrderNumber(orderNo);
        if (order == null) {
            // 订单不存在
            return "/client/order_pay_failed";
        }

        map.put("order", order);

        if (verify_result) {// 验证成功
            String trade_status = paymentResult.getString("respCode");
            if ("".equals(trade_status) || "AAAAAAA".equals(trade_status)) {
                // 订单支付成功

                afterPaySuccess(order);

                return "/client/order_pay_success";
            }

        }
        // 验证失败或者支付失败
        return "/client/order_pay_failed";
    }

    /*
     * 
     */
    @RequestMapping(value = "/change_paymethod", method = { RequestMethod.POST })
    public @ResponseBody Map<String, String> changePaymentMethod(Long orderId,
            Long paymentMethodId, ModelMap map, HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("username");
        Map<String, String> result = new HashMap<String, String>();
        result.put("status", "F");
        if (null == username) {
            result.put("message", "请先登录！");
            return result;
        }

        if (null == orderId) {
            result.put("message", "订单Id非法！");
            return result;
        }

        if (null == paymentMethodId) {
            result.put("message", "支付方式非法！");
            return result;
        }

        TdOrder order = tdOrderService.findOne(orderId);

        if (null == order) {
            result.put("message", "不存在的订单信息！");
            return result;
        }

        TdPayType payType = tdPayTypeService.findOne(paymentMethodId);
        if (null == payType) {
            result.put("message", "不存在的支付方式信息！");
            return result;
        }

        if (!order.getStatusId().equals(2L) && !order.getStatusId().equals(3L)) {
            result.put("message", "订单不能修改支付方式！");
            return result;
        }

        if (payType.getIsEnable()) {
            result.put("message", "所选的支付方式暂不支持，请选择其他支付方式！");
        }

        Double payTypeFee = payType.getFee();
        payTypeFee = payTypeFee == null ? 0.0 : payTypeFee;

        double goodPrice = order.getTotalGoodsPrice();
        Double deliverTypeFee = order.getDeliverTypeFee();
        deliverTypeFee = deliverTypeFee == null ? 0.0 : deliverTypeFee;
        /*
         * 订单金额=商品总额+支付手续费+运费-优惠券金额-积分抵扣金额 优惠券金额+积分抵扣金额=商品总额+支付手续费+运费-订单金额
         */
        Double orgPayTypeFee = order.getPayTypeFee();
        orgPayTypeFee = orgPayTypeFee == null ? 0.0 : orgPayTypeFee;
        double couponAndPointsFee = goodPrice + orgPayTypeFee + deliverTypeFee
                - order.getTotalPrice();

        /*
         * 按百分比收取手续费,手续费重新计算(商品总额*百分比)
         */
        if (payType.getIsFeeCountByPecentage()) {
            payTypeFee = goodPrice * payTypeFee / 100;
        }

        order.setTotalPrice(goodPrice + payTypeFee + deliverTypeFee
                - couponAndPointsFee);
        order.setPayTypeFee(payTypeFee);
        order.setPayTypeId(payType.getId());
        order.setPayTypeTitle(payType.getTitle());
        order.setIsOnlinePay(payType.getIsOnlinePay());

        tdOrderService.save(order);

        result.put("status", "S");
        result.put("message", "订单支付方式修改成功！");
        return result;
    }

    /**
     * 订单支付成功后步骤
     * 
     * @param tdOrder
     *            订单
     */
    private void afterPaySuccess(TdOrder tdOrder) {
        if (null == tdOrder) {
            return;
        }

        // 用户
//        TdUser tdUser = tdUserService.findByUsername(tdOrder.getUsername());
        
        if (tdOrder.getStatusId().equals(2L))
        {
            // 待发货
            tdOrder.setStatusId(3L);
            tdOrder = tdOrderService.save(tdOrder);
            return;
        }
        
        Double price = 0.0; // 交易总金额
        Double postPrice = 0.0;  // 物流费
        Double aliPrice = 0.0;	// 第三方使用费
        Double servicePrice = 0.0;	// 平台服务费
        Double totalGoodsPrice = 0.0; // 商品总额
        Double realPrice = 0.0; // 商家实际收入

        price += tdOrder.getTotalPrice();
        postPrice += tdOrder.getPostPrice();
        aliPrice += tdOrder.getAliPrice();
        servicePrice +=tdOrder.getTotalPrice();
        totalGoodsPrice += tdOrder.getTotalGoodsPrice();
        
        
        // 添加商家余额及交易记录
        if(0==tdOrder.getTypeId())
        {
        	
        	TdDistributor distributor = tdDistributorService.findOne(tdOrder.getId());
        	if(null != distributor)
        	{	
        		// 超市普通销售单实际收入： 交易总额-第三方使用费-平台服务费=实际收入
        		realPrice +=price-aliPrice-servicePrice;
        		
        		distributor.setVirtualMoney(distributor.getVirtualMoney()+realPrice); 
        		tdDistributorService.save(distributor);
        		
        		TdPayRecord record = new TdPayRecord();
        		record.setCont("订单销售款");
        		record.setCreateTime(new Date());
        		record.setDistributorId(distributor.getId());
        		record.setDistributorTitle(distributor.getTitle());
        		record.setOrderId(tdOrder.getId());
        		record.setOrderNumber(tdOrder.getOrderNumber());
        		record.setStatusCode(1);
        		record.setProvice(price); // 交易总额
        		record.setTotalGoodsPrice(totalGoodsPrice); // 商品总额
        		record.setPostPrice(postPrice);	// 邮费
        		record.setAliPrice(aliPrice);	// 第三方使用费
        		record.setServicePrice(servicePrice);	// 平台服务费
        		record.setRealPrice(realPrice); // 实际收入
        		
        		tdPayRecordService.save(record);
        	}
        }
        else if(2 == tdOrder.getTypeId())
        {
        	TdDistributor distributor = tdDistributorService.findOne(tdOrder.getShopId());
        	TdProvider provider = tdProviderService.findOne(tdOrder.getProviderId());
        	
        	Double turnPrice = tdOrder.getTotalLeftPrice();
        	if(null != distributor)
        	{
        		
        		distributor.setVirtualMoney(distributor.getVirtualMoney()+turnPrice); // 超市分销单收入为分销返利额
        		tdDistributorService.save(distributor);
        		
        		TdPayRecord record = new TdPayRecord();
        		record.setCont("代售获利");
        		record.setCreateTime(new Date());
        		record.setDistributorId(distributor.getId());
        		record.setDistributorTitle(distributor.getTitle());
        		record.setOrderId(tdOrder.getId());
        		record.setOrderNumber(tdOrder.getOrderNumber());
        		record.setStatusCode(1);
        		record.setProvice(price); // 订单总额
        		record.setTurnPrice(turnPrice); // 超市返利
        		record.setRealPrice(turnPrice); // 超市实际收入
        		tdPayRecordService.save(record);
        	}
        	if(null != provider)
        	{
        		// 分销商实际收入：商品总额-第三方使用费-邮费-超市返利-平台费 
        		realPrice += price-aliPrice-postPrice-turnPrice-servicePrice;
        		
        		provider.setVirtualMoney(provider.getVirtualMoney()+realPrice);
        		
        		TdPayRecord record = new TdPayRecord();
                record.setCont("分销收款");
                record.setCreateTime(new Date());
                record.setDistributorId(distributor.getId());
                record.setDistributorTitle(distributor.getTitle());
                record.setProviderId(provider.getId());
                record.setProviderTitle(provider.getTitle());
                record.setOrderId(tdOrder.getId());
                record.setOrderNumber(tdOrder.getOrderNumber());
                record.setStatusCode(1);
                
                record.setProvice(price); // 订单总额
                record.setPostPrice(postPrice); // 邮费
                record.setAliPrice(aliPrice);	// 第三方费
                record.setServicePrice(servicePrice);	// 平台费
                record.setTotalGoodsPrice(totalGoodsPrice); // 商品总价
                record.setTurnPrice(turnPrice); // 超市返利
                record.setRealPrice(realPrice); // 实际获利
                tdPayRecordService.save(record);
        	}
        }

        
             
    }
    
    /**
     * 立即购买
     * @param id
     * @param req
     * @param map
     * @return
     */
    @RequestMapping(value="/byNow/{dGoodsId}")
    public String ByNow(@PathVariable Long dGoodsId,Long quantity, HttpServletRequest req, ModelMap map){
    	String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			return "redirect:/login";
		}
		if(null== quantity){
			quantity = 1L;
		}
		
		tdCommonService.setHeader(map, req);
		
//		TdGoods goods = tdGoodsService.findOne(dGoodsId);
		TdDistributorGoods dgoods = tdDistributoGoodsService.findOne(dGoodsId);
		
		List<TdCartGoods> cartGoodsList = new ArrayList<>();
		TdCartGoods cartGoods = new TdCartGoods();
		
		Double totalPrice = new Double(0);
		
		cartGoods.setUsername(username);
		cartGoods.setGoodsId(dgoods.getGoodsId());
		cartGoods.setGoodsTitle(dgoods.getGoodsTitle());
		cartGoods.setGoodsCoverImageUri(dgoods.getCoverImageUri());
		cartGoods.setQuantity(quantity);
		cartGoods.setPrice(dgoods.getGoodsPrice());
		cartGoods.setIsSelected(true);
		cartGoods.setDistributorId(tdDistributoGoodsService.findDistributorId(dGoodsId));
		cartGoods.setDistributorTitle(dgoods.getDistributorTitle());
		cartGoods.setProviderId(dgoods.getProviderId());
		cartGoods.setProviderTite(dgoods.getProviderTitle());
		cartGoods.setDistributorGoodsId(dgoods.getId());
		tdCartGoodsService.save(cartGoods);
		cartGoodsList.add(cartGoods);

		map.addAttribute("totalPrice",dgoods.getGoodsPrice()*quantity);
		
        map.addAttribute("selected_goods_list",cartGoodsList);
        
        setPayTypes(map, true, false, req);
        
		
        
    	return "/client/order_info";
    }
    
    @RequestMapping(value="/proGoods/{dGoodsId}")
    public String distributionGoods(@PathVariable Long dGoodsId,HttpServletRequest req,ModelMap map){
    	String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			return "redirect:/login";
		}
		
		if(null == dGoodsId){
			return "/client/error_404";
		}
		
		tdCommonService.setHeader(map, req);
		
		TdDistributorGoods dgoods = tdDistributoGoodsService.findOne(dGoodsId);
		
		List<TdCartGoods> cartGoodsList = new ArrayList<>();
		TdCartGoods cartGoods = new TdCartGoods();
		
		Double totalPrice = new Double(0);
		
		cartGoods.setUsername(username);
		cartGoods.setGoodsId(dgoods.getGoodsId());
		cartGoods.setGoodsTitle(dgoods.getGoodsTitle());
		cartGoods.setGoodsCoverImageUri(dgoods.getCoverImageUri());
		cartGoods.setQuantity(1L);
		cartGoods.setPrice(dgoods.getGoodsPrice());
		cartGoods.setIsSelected(true);
		cartGoods.setDistributorId(tdDistributoGoodsService.findDistributorId(dGoodsId));
		cartGoods.setDistributorTitle(dgoods.getDistributorTitle());
		cartGoods.setProviderId(dgoods.getProviderId());
		cartGoods.setProviderTite(dgoods.getProviderTitle());
		cartGoods.setDistributorGoodsId(dgoods.getId());
		
		tdCartGoodsService.save(cartGoods);
		cartGoodsList.add(cartGoods);

		map.addAttribute("totalPrice",dgoods.getGoodsPrice()*1);
		
        map.addAttribute("selected_goods_list",cartGoodsList);
        
        map.addAttribute("type","pro");
        setPayTypes(map, true, false, req);
		
    	
    	
    	return "/client/order_info";
    }
    
    
    
    
    
    
    
    
    
}
