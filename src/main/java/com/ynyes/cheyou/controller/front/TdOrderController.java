package com.ynyes.cheyou.controller.front;

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
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csii.payment.client.core.CebMerchantSignVerify;
import com.cytm.payment.alipay.AlipayConfig;
import com.cytm.payment.alipay.Constants;
import com.cytm.payment.alipay.PaymentChannelAlipay;
import com.cytm.payment.alipay.core.AlipayNotify;
import com.cytm.payment.ceb.CEBPayConfig;
import com.cytm.payment.ceb.PaymentChannelCEB;
import com.ynyes.cheyou.entity.TdCartGoods;
import com.ynyes.cheyou.entity.TdCoupon;
import com.ynyes.cheyou.entity.TdCouponType;
import com.ynyes.cheyou.entity.TdDeliveryType;
import com.ynyes.cheyou.entity.TdDiySite;
import com.ynyes.cheyou.entity.TdGoods;
import com.ynyes.cheyou.entity.TdOrder;
import com.ynyes.cheyou.entity.TdOrderGoods;
import com.ynyes.cheyou.entity.TdPayRecord;
import com.ynyes.cheyou.entity.TdPayType;
import com.ynyes.cheyou.entity.TdShippingAddress;
import com.ynyes.cheyou.entity.TdUser;
import com.ynyes.cheyou.entity.TdUserPoint;
import com.ynyes.cheyou.service.TdCartGoodsService;
import com.ynyes.cheyou.service.TdCommonService;
import com.ynyes.cheyou.service.TdCouponService;
import com.ynyes.cheyou.service.TdCouponTypeService;
import com.ynyes.cheyou.service.TdDeliveryTypeService;
import com.ynyes.cheyou.service.TdDiySiteService;
import com.ynyes.cheyou.service.TdGoodsService;
import com.ynyes.cheyou.service.TdOrderGoodsService;
import com.ynyes.cheyou.service.TdOrderService;
import com.ynyes.cheyou.service.TdPayRecordService;
import com.ynyes.cheyou.service.TdUserPointService;
import com.ynyes.cheyou.service.TdUserService;
import com.ynyes.cheyou.util.SMSUtil;

/**
 * 订单
 *
 */
@Controller
@RequestMapping("/order")
public class TdOrderController extends AbstractPaytypeController {

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
    private TdDiySiteService tdDiySiteService;

    @Autowired
    private TdPayRecordService payRecordService;

    @Autowired
    private PaymentChannelCEB payChannelCEB;

    @Autowired
    private PaymentChannelAlipay payChannelAlipay;

    @RequestMapping(value = "/info")
    public String orderInfo(HttpServletRequest req, HttpServletResponse resp,
            ModelMap map) {
        String username = (String) req.getSession().getAttribute("username");
        System.err.println("进入！");
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

        Long totalPointLimited = 0L;//积分限制综总和
        Double totalPrice = 0.0;	//购物总额

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

        // 优惠券
        // TODO: 满减券， 单品类券，普通券查找
//        List<TdCoupon> userCoupons = tdCouponService.findByUsernameAndIsUseable(username);//根据账号查询所有优惠券
//        
//        /**
//         * 判断能使用的优惠券
//         * 1，满购券金额是否达到要求
//         * 2，单品类券是否包含有订单商品能使用的
//         * 3，普通券
//         * @author libiao
//         */
//        List<TdCoupon> userCouponList =null;
//        if(null != userCoupons)
//        {
//        	if(userCoupons.size()>0)
//        	{
//        		for (int i = 0; i < userCoupons.size(); i++) 
//        		{
//        			//查看优惠券
//        			TdCouponType couponType = tdCouponTypeService.findOne(userCoupons.get(i).getTypeId());
//						//判断为满购券
//        				if(couponType.getCategoryId().equals(1L))
//        				{
//        					//判断购物总价>满购券使用金额
//        					if(totalPrice>couponType.getCanUsePrice())
//        					{
//        						userCouponList.add(userCoupons.get(i));
//        					}
//        				}
//        				//判断为普通券
//        				if(couponType.getCategoryId().equals(0L))
//        				{
//        					userCouponList.add(userCoupons.get(i));
//        				}
//        				//判断为单品类券
//        				if(couponType.getCategoryId().equals(2L))
//        				{
//        					
//        				}
//						 /**
//		                 * 取出购物车所有商品Id
//		                 * @author libiao
//		                 */
//		                List<Long> goodIds =new ArrayList<>();
//		                for (int j = 0; j < selectedGoodsList.size(); i++) 
//		                {
//		        			Long goodsId = selectedGoodsList.get(j).getGoodsId();
//		        			goodIds.add(goodsId);
//		        		}
//		                /**
//		                 * 根据取出商品Id查找其所属分类Id
//		                 * @author libiao
//		                 */
//		                List<Long> productIds =new ArrayList<>();
//		                
//		                
//		                for (int y = 0; i < goodIds.size(); i++) {
//		        			Long productId = tdGoodsService.findProductIdById(goodIds.get(y)).getProductId();
//		        			if(!productIds.contains(productId)){
//		        				productIds.add(productId);
//		        			}
//		        		}
////		                /**
////		                 * 查找所有能用的单品类券
////		                 */
////		                for (int i = 0; i < productIds.size(); i++) {
////		        			List<TdCouponType> counponTypes = tdCouponTypeService.findByCategoryId(productIds.get(i));
////		        		}
//					
//				}
//        		
//        	}
//        } 
        
        map.addAttribute("coupon_list",
                tdCouponService.findByUsernameAndIsUseable(username));

        // 积分限额
        map.addAttribute("total_point_limit", totalPointLimited);

        // 线下同盟店
        map.addAttribute("shop_list", tdDiySiteService.findByIsEnableTrue());

        // 支付方式列表
        setPayTypes(map, true, false, req);

        // 配送方式
        map.addAttribute("delivery_type_list",
                tdDeliveryTypeService.findByIsEnableTrue());

        // 选中商品
        
        map.addAttribute("selected_goods_list", selectedGoodsList);

        tdCommonService.setHeader(map, req);

        return "/client/order_info";
    }

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
            Long shopId, Long payTypeId, // 支付方式ID
            Long deliveryTypeId, // 配送方式ID
            Long pointUse, // 使用积分
            Boolean isNeedInvoice, // 是否需要发票
            String invoiceTitle, // 发票抬头
            String userMessage, // 用户留言
            Long couponId, // 优惠券ID
            String appointmentTime, HttpServletRequest req, ModelMap map) {
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/login";
        }

        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);

        double payTypeFee = 0.0;
        double deliveryTypeFee = 0.0;
        double pointFee = 0.0;
        double couponFee = 0.0;

        TdShippingAddress address = null;

        if (null != addressId) {
            if (null == pointUse) {
                pointUse = 0L;
            }

            if (null != user.getTotalPoints()) {
                if (pointUse.compareTo(user.getTotalPoints()) >= 0) {
                    pointUse = user.getTotalPoints();
                }
            }

            List<TdShippingAddress> addressList = user.getShippingAddressList();

            for (TdShippingAddress add : addressList) {
                if (add.getId().equals(addressId)) {
                    address = add;
                    break;
                }
            }
        }

        List<TdCartGoods> cartGoodsList = tdCartGoodsService
                .findByUsernameAndIsSelectedTrue(username);
        List<TdOrderGoods> orderGoodsList = new ArrayList<TdOrderGoods>();

        Double totalPrice = 0.0;

        if (null != cartGoodsList) {
            for (TdCartGoods cartGoods : cartGoodsList) {
                if (cartGoods.getIsSelected()) {
                    TdGoods goods = tdGoodsService.findOne(cartGoods
                            .getGoodsId());

                    if (null == goods) {
                        continue;
                    }

                    TdOrderGoods orderGoods = new TdOrderGoods();
                    orderGoods.setGoodsId(goods.getId());
                    orderGoods.setGoodsTitle(goods.getTitle());
                    orderGoods.setGoodsSubTitle(goods.getSubTitle());
                    orderGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
                    orderGoods.setPrice(cartGoods.getPrice());
                    orderGoods.setIsReturnApplied(false);
                    orderGoods.setGoodsSaleType(cartGoods.getQiang());

                    long quantity = 0;

                    // 闪购
                    if (null != goods.getIsFlashSale()
                            && goods.getIsFlashSale()
                            && null != goods.getFlashSaleStartTime()
                            && goods.getFlashSaleStartTime().before(new Date())
                            && null != goods.getFlashSaleStopTime()
                            && goods.getFlashSaleStopTime().after(new Date())
                            && cartGoods.getPrice().equals(
                                    goods.getFlashSalePrice())) {
                        if (null != goods.getFlashSaleLeftNumber()) {
                            quantity = Math.min(cartGoods.getQuantity(),
                                    goods.getFlashSaleLeftNumber());
                        }
                    }
                    // 团购
                    else if (null != goods.getIsGroupSale()
                            && goods.getIsGroupSale()
                            && null != goods.getGroupSaleStartTime()
                            && goods.getGroupSaleStartTime().before(new Date())
                            && null != goods.getGroupSaleStopTime()
                            && goods.getGroupSaleStopTime().after(new Date())
                            && cartGoods.getPrice().equals(
                                    goods.getGroupSalePrice())) {
                        if (null != goods.getGroupSaleLeftNumber()) {
                            quantity = Math.min(cartGoods.getQuantity(),
                                    goods.getGroupSaleLeftNumber());
                        }
                    } else {
                        quantity = Math.min(cartGoods.getQuantity(),
                                goods.getLeftNumber());
                    }

                    orderGoods.setQuantity(quantity);
                    orderGoods.setPoints(goods.getReturnPoints());

                    totalPrice += cartGoods.getPrice()
                            * cartGoods.getQuantity();

                    orderGoodsList.add(orderGoods);
                    tdGoodsService.save(goods, username);
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
        tdOrder.setUsername(username);
        tdOrder.setOrderTime(current);

        // 订单号
        tdOrder.setOrderNumber("P" + curStr
                + leftPad(Integer.toString(random.nextInt(999)), 3, "0"));

        // 收货地址
        if (null != address)
        {
            //增加车牌 by zhangji      	
        	tdOrder.setCarCode(address.getReceiverCarcode());
        	//增加车型  by zhangji
        	tdOrder.setCarType(address.getReceiverCartype());
        	
            tdOrder.setPostalCode(address.getPostcode());          

            tdOrder.setShippingName(address.getReceiverName());
            tdOrder.setShippingPhone(address.getReceiverMobile());
            tdOrder.setShippingAddress(address.getProvince()
                    + address.getCity() + address.getDisctrict()
                    + address.getDetailAddress());
        }
        if (null != payTypeId) {
            TdPayType payType = tdPayTypeService.findOne(payTypeId);

            if (payType.getIsOnlinePay()) {
                tdOrder.setStatusId(2L); // 待付款
            } else {
                tdOrder.setStatusId(1L); // 待确认
            }

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

        // 线下同盟店
        if (null != shopId) {
            TdDiySite shop = tdDiySiteService.findOne(shopId);

            if (null != shop) {
                tdOrder.setShopId(shop.getId());
                tdOrder.setShopTitle(shop.getTitle());
            }
        }

        // 使用积分
        tdOrder.setPointUse(pointUse);

        // 用户留言
        tdOrder.setUserRemarkInfo(userMessage);

        // 优惠券
        if (null != couponId) {
            TdCoupon coupon = tdCouponService.findOne(couponId);

            if (null != coupon) {
                TdCouponType couponType = tdCouponTypeService.findOne(coupon
                        .getId());

                couponFee = couponType.getPrice();
            }
        }

        pointFee = pointUse * 1;

        tdOrder.setTotalPrice(totalPrice + payTypeFee + deliveryTypeFee
                - pointFee - couponFee);

        // 发票
        if (null != isNeedInvoice) {
            tdOrder.setIsNeedInvoice(isNeedInvoice);
            tdOrder.setInvoiceTitle(invoiceTitle);
        } else {
            tdOrder.setIsNeedInvoice(false);
        }

        // 订单商品
        tdOrder.setOrderGoodsList(orderGoodsList);
        tdOrder.setTotalGoodsPrice(totalPrice);

        // 积分奖励
        tdOrder.setPoints(0L);

        // 保存订单
        tdOrderGoodsService.save(orderGoodsList);
        tdOrder = tdOrderService.save(tdOrder);

        // 优惠券
        if (null != couponId) {
            TdCoupon coupon = tdCouponService.findOne(couponId);

            if (null != coupon) {
                coupon.setIsUsed(true);
                tdCouponService.save(coupon);
            }
        }

        if (null != user) {
            if (pointUse.compareTo(0L) >= 0 && null != user.getTotalPoints()
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

        // 删除已生成订单的购物车项
        tdCartGoodsService.delete(cartGoodsList);

        if (tdOrder.getIsOnlinePay()) {
            return "redirect:/order/pay?orderId=" + tdOrder.getId();
        }

        return "redirect:/order/success?orderId=" + tdOrder.getId();
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
                payForm = payChannelAlipay.getPayFormData(req);
                map.addAttribute("charset", AlipayConfig.CHARSET);
            } else if (CEBPayConfig.INTER_B2C_BANK_CONFIG.keySet().contains(
                    payCode)) {
                req.setAttribute("payMethod", payCode);
                payForm = payChannelCEB.getPayFormData(req);
                map.addAttribute("charset", "GBK");
            } else {
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
        payChannelAlipay.doResponse(req, resp);
    }

    /*
     * 
     */
    @RequestMapping(value = "/pay/notify_cebpay")
    public void payNotifyCEBPay(ModelMap map, HttpServletRequest req,
            HttpServletResponse resp) {
        payChannelCEB.doResponse(req, resp);
    }

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

        if (order.getStatusId() != 2l) {
            result.put("message", "订单不能修改支付方式！");
            return result;
        }

        if (payType.getIsEnable()) {
            result.put("message", "所选的支付方式暂不支持，请选择其他支付方式！");
        }

        if (payType.getIsOnlinePay()) {
            order.setStatusId(2L); // 待付款
        } else {
            order.setStatusId(1L); // 待确认
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
        TdUser tdUser = tdUserService.findByUsername(tdOrder.getUsername());
        
        // 同盟店
        TdDiySite tdShop = tdDiySiteService.findOne(tdOrder.getShopId());

        // 待发货
        tdOrder.setStatusId(3L);

        tdOrder = tdOrderService.save(tdOrder);

        // 给用户发送短信
        if (null != tdUser && null != tdUser.getMobile()) {
            SMSUtil.send(tdUser.getMobile(), "16910",
                    new String[] { tdUser.getUsername(),
                            tdOrder.getOrderGoodsList().get(0).getGoodsTitle(),
                            tdOrder.getOrderNumber().substring(tdOrder.getOrderNumber().length()-4)});
        }
        
        // 给商户发短信
        if (null != tdShop && null != tdUser && null != tdShop.getMobile())
        {
            SMSUtil.send(tdShop.getMobile(), "16912",
                new String[] { tdShop.getTitle(),
                tdUser.getUsername(),
                tdOrder.getOrderGoodsList().get(0).getGoodsTitle()});
        }

        List<TdOrderGoods> tdOrderGoodsList = tdOrder.getOrderGoodsList();

        Long totalPoints = 0L;
        Double totalCash = 0.0;

        // 返利总额
        if (null != tdOrderGoodsList) {
            for (TdOrderGoods tog : tdOrderGoodsList) {
                if (0 == tog.getGoodsSaleType()) // 正常销售
                {
                    TdGoods tdGoods = tdGoodsService.findOne(tog.getGoodsId());

                    if (null != tdGoods && null != tdGoods.getReturnPoints()) {
                        totalPoints += tdGoods.getReturnPoints();

                        if (null != tdGoods.getShopReturnRation()) {
                            totalCash = tdGoods.getSalePrice()
                                    * tdGoods.getShopReturnRation();
                        }
                    }
                }
            }
            
            // 用户返利
            if (null != tdUser) {
                TdUserPoint userPoint = new TdUserPoint();

                userPoint.setDetail("购买商品赠送粮草");
                userPoint.setOrderNumber(tdOrder.getOrderNumber());
                userPoint.setPoint(totalPoints);
                userPoint.setPointTime(new Date());
                userPoint.setTotalPoint(tdUser.getTotalPoints() + totalPoints);
                userPoint.setUsername(tdUser.getUsername());

                userPoint = tdUserPointService.save(userPoint);

                tdUser.setTotalPoints(userPoint.getTotalPoint());

                tdUserService.save(tdUser);
            }
        }

        // 同盟店返利
        if (null != tdShop) {
            if (null == tdShop.getTotalCash()) {
                tdShop.setTotalCash(totalCash);
            } else {
                tdShop.setTotalCash(tdShop.getTotalCash() + totalCash);
            }

            tdDiySiteService.save(tdShop);
        }
    }
}
