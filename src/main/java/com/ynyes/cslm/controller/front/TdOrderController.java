package com.ynyes.cslm.controller.front;

import static com.cslm.payment.util.PaymentUtil.post;
import static org.apache.commons.lang3.StringUtils.leftPad;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
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
import com.cslm.payment.alipay.core.AlipayConfirmGoods;
import com.cslm.payment.alipay.core.AlipayConfirmGoodsHandler;
import com.cslm.payment.alipay.core.AlipayNotify;
import com.ynyes.cslm.dao.MD5;
import com.ynyes.cslm.dao.WxPayReturnData;
import com.ynyes.cslm.entity.TdCartGoods;
import com.ynyes.cslm.entity.TdCash;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdOrderGoods;
import com.ynyes.cslm.entity.TdPayRecord;
import com.ynyes.cslm.entity.TdPayType;
import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdProviderGoods;
import com.ynyes.cslm.entity.TdSetting;
import com.ynyes.cslm.entity.TdShippingAddress;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.entity.TdUserPoint;
import com.ynyes.cslm.service.TdCartGoodsService;
import com.ynyes.cslm.service.TdCashService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdCouponService;
import com.ynyes.cslm.service.TdDeliveryTypeService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdOrderGoodsService;
import com.ynyes.cslm.service.TdOrderService;
import com.ynyes.cslm.service.TdPayRecordService;
import com.ynyes.cslm.service.TdPayTypeService;
import com.ynyes.cslm.service.TdProviderGoodsService;
import com.ynyes.cslm.service.TdProviderService;
import com.ynyes.cslm.service.TdSettingService;
import com.ynyes.cslm.service.TdShippingAddressService;
import com.ynyes.cslm.service.TdUserPointService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.service.TdWeiXinPayService;
import com.ynyes.cslm.util.QRCodeUtils;

import net.sf.json.JSONObject;

/**
 * 订单
 *
 */
@Controller
@RequestMapping("/order")
public class TdOrderController extends AbstractPaytypeController {

	private static final String PAYMENT_ALI = "ALI";
	private static final String PAYMENT_WX = "WX";

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
	private TdCouponService tdCouponService;

	@Autowired
	private TdPayRecordService payRecordService;

	@Autowired
	private TdPayTypeService tdPayTypeService;

	@Autowired
	private TdSettingService tdSettingService;

	@Autowired
	private TdDistributorService tdDistributorService;

	@Autowired
	private TdDistributorGoodsService tdDistributorGoodsService;

	@Autowired
	private TdProviderGoodsService tdProviderGoodsService;

	@Autowired
	private TdProviderService tdProviderService;

	@Autowired
	private TdPayRecordService tdPayRecordService;

	@Autowired
	private TdShippingAddressService tdShippingAddressService;

	@Autowired
	private TdCashService tdCashService;
	
	@Autowired
	private TdUserPointService tdUserPointService;
	
	@Autowired
	private TdWeiXinPayService tdWeiXinPayService;

	// @Autowired
	// private PaymentChannelCEB payChannelCEB;
	//
//	 @Autowired
//	 private PaymentChannelAlipay paymentChannelAlipay;


	@RequestMapping(value = "/info")
	public String orderInfo(Long code, HttpServletRequest req, HttpServletResponse resp, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			return "redirect:/login";
		}

		// 把所有的购物车项转到该登陆用户下
		String sessionId = req.getSession().getId();

		List<TdCartGoods> cartGoodsList = tdCartGoodsService.findByUsername(sessionId);

		if (null != cartGoodsList && cartGoodsList.size() > 0) {
			for (TdCartGoods cartGoods : cartGoodsList) {
				cartGoods.setUsername(username);
				cartGoods.setIsLoggedIn(true);
			}
			tdCartGoodsService.save(cartGoodsList);
		}

		TdUser user = tdUserService.findByUsernameAndIsEnabled(username);

		if (null == user) {
			return "redirect:/login";
		}
		map.addAttribute("user", user);

		List<TdCartGoods> selectedGoodsList = tdCartGoodsService.findByUsernameAndIsSelectedTrue(username);

		Long totalPointLimited = 0L;// 积分限制综总和
		Double totalPrice = 0.0; // 购物总额

		// 积分限制总和 和 购物总额
		if (null != selectedGoodsList) {
			for (TdCartGoods cg : selectedGoodsList) {
				TdGoods goods = tdGoodsService.findOne(cg.getGoodsId());
				if (null != goods && null != goods.getPointLimited()) {
					totalPointLimited += goods.getPointLimited() * cg.getQuantity();
				} else {
					totalPointLimited += 0;
				}
				totalPrice += cg.getPrice() * cg.getQuantity();
			}
		}

		// 查询出当前选择商品所属超市ID
		List<Long> list = tdCartGoodsService.countByDistributorId(username);

		Double postPrice = 0.0;
		if (null != list && list.size() == 1) // 如果只有一家超市、提供自提点选择
		{
			TdDistributor distributor = tdDistributorService.findOne(list.get(0));
			map.addAttribute("addressList", distributor.getShippingList());
			map.addAttribute("distributor", distributor);

			postPrice += distributor.getPostPrice();

			// 判断是否满额免
			if (null != distributor.getMaxPostPrice() && totalPrice > distributor.getMaxPostPrice()) {
				postPrice = 0.0;
			}
			map.addAttribute("maxPostPrice", distributor.getMaxPostPrice());
			map.addAttribute("postPrice", postPrice);
		} else {
			map.addAttribute("post", "当前结算商品包含多个超市，邮费另算");
		}

		map.addAttribute("coupon_list", tdCouponService.findByUsernameAndIsUseable(username));

		// 积分限额
		map.addAttribute("total_point_limit", totalPointLimited);

		// 线下加盟超市
		map.addAttribute("shop_list", tdDistributorService.findByIsEnableTrue());

		// 支付方式列表
		setPayTypes(map, true, false, req);

		map.addAttribute("totalPrice", totalPrice);
		// 配送方式
		map.addAttribute("delivery_type_list", tdDeliveryTypeService.findByIsEnableTrue());

		// 选中商品
		map.addAttribute("buy_goods_list", selectedGoodsList);

		// 余额不足提醒
		if (null != code && code == 1) {
			map.addAttribute("msg", "余额不足，请选择在线支付！");
		}

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
	public String orderEdit(HttpServletRequest req, HttpServletResponse resp, @PathVariable String type, Long gid,
			ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			return "redirect:/login";
		}

		// 把所有的购物车项转到该登陆用户下
		List<TdCartGoods> cgList = tdCartGoodsService.findByUsernameAndIsSelectedTrue(username);

		if (null != cgList && null != type && null != gid) {
			for (TdCartGoods cg : cgList) {
				if (gid.equals(cg.getGoodsId())) {
					TdGoods goods = tdGoodsService.findOne(cg.getGoodsId());

					if (null != goods) {
						if (type.equalsIgnoreCase("plus")) {
							// 闪购
							if (goods.getIsFlashSale() && goods.getFlashSaleStartTime().before(new Date())
									&& goods.getFlashSaleStopTime().after(new Date())
									&& cg.getPrice().equals(goods.getFlashSalePrice())) {
								if (cg.getQuantity().compareTo(goods.getFlashSaleLeftNumber()) < 0) {
									cg.setQuantity(cg.getQuantity() + 1L);
								}
							} else {
								if (cg.getQuantity().compareTo(goods.getLeftNumber()) < 0) {
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
			Long pointUse, // 使用积分
			Boolean isNeedInvoice, // 是否需要发票
			String invoiceTitle, // 发票抬头
			String userMessage, // 用户留言
			Long couponId, // 优惠券ID
			Long deliveryType, // 配送方式
			Long shipAddressId, // 自提点Id
			String type, HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			return "redirect:/login";
		}
		tdCommonService.setHeader(map, req);
		
		TdUser user = tdUserService.findByUsernameAndIsEnabled(username);

		if (null == user) {
			return "/client/error_404";
		}

		// 网站基本信息
        TdSetting setting = tdSettingService.findTopBy();
        
        // 积分兑换币比例
        Long point_limt = setting.getRegisterSharePoints();
        if(null == point_limt ){
        	point_limt =1L;
        }
        
		double payTypeFee = 0.0;

		// 收货地址
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

		// 积分使用
		if (null == pointUse) {
			pointUse = 0L;
		}
		if (null != user.getTotalPoints()) {
			if (null != pointUse) {
				if (pointUse.compareTo(user.getTotalPoints()) >= 0) {
					pointUse = user.getTotalPoints();
				}
			}
		}

		// 购物车商品
		List<TdCartGoods> cartSelectedGoodsList = tdCartGoodsService.findByUsernameAndIsSelectedTrue(username);

		// 储存超市Id 和对应商品 为拆分订单做准备
		Map<Long, List<TdCartGoods>> cartGoodsMap = new HashMap<>();

		Double cartGoodsTotalPrice = 0.0;
		if (null != cartSelectedGoodsList) {
			for (TdCartGoods cartGoods : cartSelectedGoodsList) {
				if (cartGoods.getIsSelected()) {
					if (cartGoodsMap.containsKey(cartGoods.getDistributorId())) {
						cartGoodsMap.get(cartGoods.getDistributorId()).add(cartGoods);
					} else {
						List<TdCartGoods> clist = new ArrayList<>();
						clist.add(cartGoods);
						cartGoodsMap.put(cartGoods.getDistributorId(), clist);
					}
					cartGoodsTotalPrice += cartGoods.getPrice()*cartGoods.getQuantity();
				}
			}
		}

		if (null == cartGoodsMap || cartGoodsMap.size() <= 0) {
			return "/client/error_404";
		}

		if (null != payTypeId && payTypeId == 0) {
			if (user.getVirtualMoney() == null || cartGoodsTotalPrice > user.getVirtualMoney()) {
				return "redirect:/order/info?code=" + 1;
			}
		}

		TdOrder order = new TdOrder();
		// 订单拆分
		Set<Entry<Long, List<TdCartGoods>>> set = cartGoodsMap.entrySet();
		Iterator<Entry<Long, List<TdCartGoods>>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<Long, List<TdCartGoods>> m = iterator.next();

			TdOrder tdOrder = new TdOrder();
			List<TdOrderGoods> orderGoodsList = new ArrayList<TdOrderGoods>();
			List<TdOrderGoods> disOrderGoodsList = new ArrayList<>();

			Double totalGoodsPrice = 0.0;// 商品总价
			Double returnPrice = 0.0;// 商品返利
			Long totalPointReturn = 0L; // 积分总额
			Long disPointReturn  = 0L;
		//	Double servicePrice = 0.0;// 平台服务费
			Double disGoodsPrice = 0.0; // 分销商品费用

			for (int i = 0; i < m.getValue().size(); i++) {
				TdCartGoods cartGoods = m.getValue().get(i);
				TdDistributorGoods distributorGoods = tdDistributorGoodsService
						.findOne(cartGoods.getDistributorGoodsId());

				if (null == distributorGoods || distributorGoods.getIsOnSale() == false) {
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
				orderGoods.setIsCommented(false);

				// 销售方式
				orderGoods.setGoodsSaleType(0);

				// 销售价
				orderGoods.setPrice(distributorGoods.getGoodsPrice());

				// 单位
				orderGoods.setUnit(distributorGoods.getUnit());
				// 数量
				long quantity = Math.min(cartGoods.getQuantity(), distributorGoods.getLeftNumber());

				orderGoods.setQuantity(quantity);

				if (null == distributorGoods || !distributorGoods.getIsDistribution()) {
					
					// 获得积分
					if (null != goods.getReturnPoints()) {
						totalPointReturn += goods.getReturnPoints() * quantity;
						orderGoods.setPoints(goods.getReturnPoints() * quantity);
					}
					// 商品总价
					totalGoodsPrice += cartGoods.getPrice() * quantity;
					orderGoodsList.add(orderGoods);
				} else {
					
					// 获得积分
					if (null != goods.getReturnPoints()) {
						disPointReturn += goods.getReturnPoints() * quantity;
						orderGoods.setPoints(goods.getReturnPoints() * quantity);
					}
					// 商品总价
					disGoodsPrice += cartGoods.getPrice() * quantity;
					disOrderGoodsList.add(orderGoods);
				}
				
				// 分销商品添加发货商信息
				if (distributorGoods.getIsDistribution()) {
					tdOrder.setProviderId(distributorGoods.getProviderId());
					tdOrder.setProviderTitle(distributorGoods.getProviderTitle());

					TdProviderGoods providerGoods = tdProviderGoodsService.findByProviderIdAndGoodsId(
							distributorGoods.getProviderId(), distributorGoods.getGoodsId());

					// 分销返利
					returnPrice += cartGoods.getPrice() * providerGoods.getShopReturnRation() * cartGoods.getQuantity();
				}

				// 更新销量
				Long soldNumber = distributorGoods.getSoldNumber();

				if (null == soldNumber) {
					soldNumber = 0L;
				}

				soldNumber += quantity;
				distributorGoods.setSoldNumber(soldNumber);

				if (!distributorGoods.getIsDistribution()) {
					// 更新库存
					Long leftNumber = distributorGoods.getLeftNumber();
					if (leftNumber >= quantity) {
						leftNumber = leftNumber - quantity;
					}
					distributorGoods.setLeftNumber(leftNumber);
					
					if(distributorGoods.getLeftNumber() < 1){
						distributorGoods.setIsOnSale(false);
					}
				} else {
					TdProviderGoods providerGoods = tdProviderGoodsService.findByProviderIdAndGoodsId(
							distributorGoods.getProviderId(), distributorGoods.getGoodsId());
					providerGoods.setLeftNumber(providerGoods.getLeftNumber() - quantity);
					providerGoods = tdProviderGoodsService.save(providerGoods); // 更新分销商库存
					Long proId = tdProviderGoodsService.findProviderId(providerGoods.getId());

					List<TdDistributorGoods> list = tdDistributorGoodsService
							.findByProviderIdAndGoodsIdAndIsDistributionTrue(proId, providerGoods.getGoodsId());
					if (null != list && list.size() > 0) {
						for (TdDistributorGoods tdDistributorGoods : list) {
							tdDistributorGoods.setLeftNumber(providerGoods.getLeftNumber());
							if(distributorGoods.getLeftNumber() < 1){
								distributorGoods.setIsOnSale(false);
							}
							tdDistributorGoodsService.save(tdDistributorGoods); // 更新各超市该商品库存
						}
					}
				}

				tdDistributorGoodsService.save(distributorGoods);
			}

			TdDistributor distributor = tdDistributorService.findOne(m.getKey());

			if (null != disOrderGoodsList && disOrderGoodsList.size() > 0) { // 如果有分销商品，
				Double aliPrice = 0.0; // 第三方使用费
				Double postPrice = 0.0;// 配送费
				Double totalPrice = 0.0;// 订单总价
				double point_price = 0.0; // 积分抵消金额
				Double servicePrice = 0.0;// 平台服务费
				
				// order= new TdOrder();
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
					tdOrder.setShippingAddress(appenAddress(address).toString());
				}

				// 用户留言
				tdOrder.setUserRemarkInfo(userMessage);

				// 待付款
				tdOrder.setStatusId(2L);

				if (null != distributor.getPostPrice() && null != deliveryType && deliveryType != 1) // 送货上门计算邮费
				{
					// 计算邮费
					postPrice += distributor.getPostPrice();

					// 判断是否满额免
					if (null != distributor.getMaxPostPrice()
							&& totalGoodsPrice + disGoodsPrice > distributor.getMaxPostPrice()) {
						postPrice = 0.0;
						totalPrice += disGoodsPrice;
					} else {
						totalPrice += disGoodsPrice + postPrice;
					}

				} else {
					totalPrice += disGoodsPrice;
				}

				// 发票
				if (null != isNeedInvoice) {
					tdOrder.setIsNeedInvoice(isNeedInvoice);
					tdOrder.setInvoiceTitle(invoiceTitle);
				} else {
					tdOrder.setIsNeedInvoice(false);
				}

				// 积分奖励
				tdOrder.setPoints(disPointReturn);


				// 添加订单超市信息
				tdOrder.setShopId(distributor.getId());
				tdOrder.setShopTitle(distributor.getTitle());

				// if(null != type && type.equals("pro")){

				// 超市未设置自提点 取超市地址
				StringBuffer newAddress = new StringBuffer();
				if (null != distributor.getProvince()) {
					newAddress.append(distributor.getProvince() + "-");
				}
				if (null != distributor.getCity()) {
					newAddress.append(distributor.getCity() + "-");
				}
				if (null != distributor.getDisctrict()) {
					newAddress.append(distributor.getDisctrict() + " ");
				}
				if (null != distributor.getAddress()) {
					newAddress.append(distributor.getAddress());
				}
				
				tdOrder.setDeliveryPerson(newAddress.toString()); // 分销商供货地址（超市地址）
				tdOrder.setDeliveryMethod(deliveryType); // 配送方式 0、送货上门 1、门店自提

				List<Long> list = tdCartGoodsService.countByDistributorId(username);
				if (null != deliveryType && deliveryType == 1) // 门店自提
				{

					if (null != list && list.size() == 1) // 如果只有一家超市、提供自提点选择
					{
						// 使用积分
						tdOrder.setPointUse(pointUse);
						point_price = (double)pointUse/point_limt;
						
						TdShippingAddress shippingAddress = tdShippingAddressService.findOne(shipAddressId);
						if (null != shippingAddress) {
							tdOrder.setShipAddress(appenAddress(shippingAddress).toString()); // 添加自提点地址
							tdOrder.setShipMobile(shippingAddress.getReceiverMobile()); // 添加自提点联系方式
							tdOrder.setShipAddressTitle(shippingAddress.getReceiverName());
							tdOrder.setDeliveryPerson(appenAddress(shippingAddress).toString());
						}
					} 
					else if (list.size() > 1) // 多家超市
					{
						tdOrder.setPointUse(0L);
						List<TdShippingAddress> shippingList = distributor.getShippingList();

						if (null != shippingList) // 超市设置了自提点 取默认地址
						{
							TdShippingAddress newShipping = new TdShippingAddress();
							for (TdShippingAddress tdShippingAddress : shippingList) {
								if (null != tdShippingAddress.getIsDefaultAddress() && tdShippingAddress.getIsDefaultAddress()) {
									newShipping = tdShippingAddress;
								}
							}
							tdOrder.setShipAddress(appenAddress(newShipping).toString()); // 添加自提点地址
							tdOrder.setShipMobile(newShipping.getReceiverMobile()); // 添加自提点联系方式
							tdOrder.setShipAddressTitle(newShipping.getReceiverName());
							
							tdOrder.setDeliveryPerson(appenAddress(newShipping).toString());
						} else {
							tdOrder.setShippingAddress(newAddress.toString()); // 超市地址
							tdOrder.setShipAddressTitle(distributor.getTitle()); // 超市名称
							tdOrder.setShipMobile(distributor.getMobile()); // 超市联系方式
						}
					}
				}else{

					if (null != list && list.size() == 1) // 如果只有一家超市、提供自提点选择
					{
						// 使用积分
						tdOrder.setPointUse(pointUse);
						point_price = (double)pointUse/point_limt;
					}else{
						tdOrder.setPointUse(0L);
					} 
					
				}
				tdDistributorService.save(distributor);

				// 订单商品
				tdOrder.setOrderGoodsList(disOrderGoodsList);
				tdOrder.setTotalGoodsPrice(disGoodsPrice);

				tdOrderGoodsService.save(disOrderGoodsList);
				// 订单号
				tdOrder.setOrderNumber("Y" + curStr + leftPad(Integer.toString(random.nextInt(999)), 3, "0"));

				// 设置订单类型 预定
				tdOrder.setTypeId(2L);

				tdOrder.setTotalLeftPrice(returnPrice);

				TdProvider provider = tdProviderService.findOne(tdOrder.getProviderId());
				if (null != provider.getAliRation()) {
					aliPrice += provider.getAliRation() * disGoodsPrice; // 第三方使用费
				}
				if (null != provider.getServiceRation()) {
					servicePrice += provider.getServiceRation() * disGoodsPrice; // 平台服务费
				}
				
				tdOrder.setTotalPrice(totalPrice-point_price); // 订单总价
				tdOrder.setPostPrice(postPrice); // 邮费
				tdOrder.setTrainService(servicePrice); // 平台服务费
				tdOrder.setAliPrice(aliPrice); // 第三方使用费

				// 支付方式
				if (null != payTypeId && payTypeId != 0) { // 平台付
					TdPayType payType = tdPayTypeService.findOne(payTypeId);

					// 支付类型
					payTypeFee = payType.getFee();
					tdOrder.setPayTypeId(payType.getId());
					tdOrder.setPayTypeTitle(payType.getTitle());
					tdOrder.setPayTypeFee(payTypeFee);
					tdOrder.setIsOnlinePay(payType.getIsOnlinePay());
				} else if (payTypeId == 0)// 余额付
				{
					user.setVirtualMoney(user.getVirtualMoney() - totalPrice); // 虚拟账号扣除
					tdUserService.save(user);

					tdOrder.setPayTypeTitle("余额支付");
					tdOrder.setPayTypeId(0L);
					tdOrder.setStatusId(3L);

					// 添加会员虚拟账户金额记录
					TdPayRecord record = new TdPayRecord();

					record.setAliPrice(aliPrice);
					record.setPostPrice(postPrice);
					record.setRealPrice(totalPrice);
					record.setTotalGoodsPrice(disGoodsPrice);
					record.setServicePrice(servicePrice);
					record.setProvice(totalPrice);
					record.setOrderNumber(tdOrder.getOrderNumber());
					record.setCreateTime(new Date());
					record.setUsername(username);
					record.setType(2L);
					record.setCont("订单支付");
					record.setDistributorTitle(distributor.getTitle());
					record.setStatusCode(1);

					tdPayRecordService.save(record); // 保存会员虚拟账户记录

					tdOrderService.addVir(tdOrder);// 商家账户记录
				}

				order = tdOrderService.save(tdOrder);
				
				if(pointUse != 0){
					pointUse(order,username);
				}
			}

			if (null != orderGoodsList && orderGoodsList.size() > 0) {
				Double aliPrice = 0.0; // 第三方使用费
				Double postPrice = 0.0;// 配送费
				Double totalPrice = 0.0;// 订单总价
				double point_price = 0.0; // 积分抵消金额
				Double servicePrice = 0.0;// 平台服务费
				
				order = new TdOrder();
				Date current = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String curStr = sdf.format(current);
				Random random = new Random();

				// 基本信息
				order.setUsername(user.getUsername());
				order.setOrderTime(current);

				// 发货信息
				if (null != address) {

					order.setPostalCode(address.getPostcode());

					order.setShippingName(address.getReceiverName());
					order.setShippingPhone(address.getReceiverMobile());
					order.setShippingAddress(appenAddress(address).toString());
				}

				// 用户留言
				order.setUserRemarkInfo(userMessage);

				// 待付款
				order.setStatusId(2L);

				if (null != distributor.getPostPrice() && null != deliveryType && deliveryType != 1) // 送货上门计算邮费
				{
					// 计算邮费
					postPrice += distributor.getPostPrice();

					// 判断是否满额免
					if (null != distributor.getMaxPostPrice()
							&& totalGoodsPrice + disGoodsPrice > distributor.getMaxPostPrice()) {
						postPrice = 0.0;
						totalPrice += totalGoodsPrice;
					} else {
						totalPrice += totalGoodsPrice + postPrice;
					}

				} else {
					totalPrice += totalGoodsPrice;
				}

				// 发票
				if (null != isNeedInvoice) {
					order.setIsNeedInvoice(isNeedInvoice);
					order.setInvoiceTitle(invoiceTitle);
				} else {
					order.setIsNeedInvoice(false);
				}

				// 粮草奖励
				order.setPoints(totalPointReturn);


				// 添加订单超市信息
				order.setShopId(distributor.getId());
				order.setShopTitle(distributor.getTitle());

				// if(null != type && type.equals("pro")){

				order.setDeliveryMethod(deliveryType); // 配送方式 0、送货上门 1、门店自提
				List<Long> list = tdCartGoodsService.countByDistributorId(username);
				if (null != deliveryType && deliveryType == 1) // 门店自提
				{

					if (null != list && list.size() == 1) // 如果只有一家超市、提供自提点选择
					{
						// 使用积分
						order.setPointUse(pointUse);
						point_price = (double)pointUse/point_limt;
						
						TdShippingAddress shippingAddress = tdShippingAddressService.findOne(shipAddressId);
						if (null != shippingAddress) {
							order.setShipAddress(appenAddress(shippingAddress).toString()); // 添加自提点地址
							order.setShipMobile(shippingAddress.getReceiverMobile()); // 添加自提点联系方式
							order.setShipAddressTitle(shippingAddress.getReceiverName());
						}
					} 
					else if (list.size() > 1) // 多家超市
					{
						order.setPointUse(0L);
						
						List<TdShippingAddress> shippingList = distributor.getShippingList();
						if (null != shippingList) // 超市设置了自提点 取默认地址
						{
							TdShippingAddress newShipping = new TdShippingAddress();
							for (TdShippingAddress tdShippingAddress : shippingList) {
								if (null != tdShippingAddress.getIsDefaultAddress() && tdShippingAddress.getIsDefaultAddress()) {
									newShipping = tdShippingAddress;
								}
							}
							order.setShipAddress(appenAddress(newShipping).toString()); // 添加自提点地址
							order.setShipMobile(newShipping.getReceiverMobile()); // 添加自提点联系方式
							order.setShipAddressTitle(newShipping.getReceiverName());
						} else {
							// 超市未设置自提点 取超市地址
							StringBuffer newAddress = new StringBuffer();
							if (null != distributor.getProvince()) {
								newAddress.append(distributor.getProvince() + "-");
							}
							if (null != distributor.getCity()) {
								newAddress.append(distributor.getCity() + "-");
							}
							if (null != distributor.getDisctrict()) {
								newAddress.append(distributor.getDisctrict() + " ");
							}
							if (null != distributor.getAddress()) {
								newAddress.append(distributor.getAddress());
							}
							order.setShippingAddress(newAddress.toString()); // 超市地址
							order.setShipAddressTitle(distributor.getTitle()); // 超市名称
							order.setShipMobile(distributor.getMobile()); // 超市联系方式
						}
					}
				}else{
					if (null != list && list.size() == 1) // 如果只有一家超市、提供自提点选择
					{
						// 使用积分
						order.setPointUse(pointUse);
						point_price = (double)pointUse/point_limt;
					}else{
						// 使用积分
						order.setPointUse(0L);
					}
				}

				tdDistributorService.save(distributor);

				// 订单商品
				order.setOrderGoodsList(orderGoodsList);
				order.setTotalGoodsPrice(totalGoodsPrice);

				tdOrderGoodsService.save(orderGoodsList);
				order.setOrderNumber("P" + curStr + leftPad(Integer.toString(random.nextInt(999)), 3, "0"));

				// 设置订单类型
				order.setTypeId(0L);

				if (null != distributor.getAliRation()) // 计算平台收费总比例
				{
					aliPrice += distributor.getAliRation() * totalGoodsPrice; // 第三方使用费
				}
				if (null != distributor.getServiceRation()) {
					servicePrice += distributor.getServiceRation() * totalGoodsPrice; // 平台服务费
				}
				order.setTotalGoodsPrice(totalGoodsPrice); // 商品总价
				order.setTotalPrice(totalPrice-point_price); // 订单总价
				order.setPostPrice(postPrice); // 邮费
				order.setTrainService(servicePrice); // 平台服务费
				order.setAliPrice(aliPrice); // 第三方使用费

				// 支付方式
				if (null != payTypeId && payTypeId != 0) { // 平台付
					TdPayType payType = tdPayTypeService.findOne(payTypeId);

					// 支付类型
					payTypeFee = payType.getFee();
					order.setPayTypeId(payType.getId());
					order.setPayTypeTitle(payType.getTitle());
					order.setPayTypeFee(payTypeFee);
					order.setIsOnlinePay(payType.getIsOnlinePay());
				} 
				else if (payTypeId == 0)// 余额付
				{
					user.setVirtualMoney(user.getVirtualMoney() - totalPrice); // 虚拟账号扣除
					tdUserService.save(user);

					order.setPayTypeTitle("余额支付");
					order.setPayTypeId(0L);
					order.setStatusId(3L);

					// 添加会员虚拟账户金额记录
					TdPayRecord record = new TdPayRecord();

					record.setAliPrice(aliPrice);
					record.setPostPrice(postPrice);
					record.setRealPrice(totalPrice);
					record.setTotalGoodsPrice(totalGoodsPrice);
					record.setServicePrice(servicePrice);
					record.setProvice(totalPrice);
					record.setOrderNumber(order.getOrderNumber());
					record.setCreateTime(new Date());
					record.setUsername(username);
					record.setType(2L);
					record.setCont("订单支付");
					record.setDistributorTitle(distributor.getTitle());
					record.setStatusCode(1);

					tdPayRecordService.save(record); // 保存会员虚拟账户记录

					tdOrderService.addVir(order);// 商家账户记录
				}

				order = tdOrderService.save(order);
				
				if(pointUse != 0){
					pointUse(order,username);
				}
			}

		}

		// 删除已生成订单的购物车项
		tdCartGoodsService.delete(cartSelectedGoodsList);

		if (null != payTypeId && payTypeId == 0) {
			return "redirect:/user/order?id=" + order.getId();
		}
		return "redirect:/order/pay?orderId=" + order.getId();
		// return "redirect:/order/success?orderId=" + tdOrder.getId();
	}
	
	
	public void pointUse(TdOrder order,String username){
		TdUser user = tdUserService.findByUsername(username);
		if(null != user){
			if (null == user.getTotalPoints())
			 {
				 user.setTotalPoints(0L);
				 user = tdUserService.save(user);
			 }
			 TdUserPoint userPoint = new TdUserPoint();
			 userPoint.setDetail("购买商品积分抵现");
			 userPoint.setOrderNumber(order.getOrderNumber());
			 userPoint.setPoint(order.getPointUse());
			 userPoint.setPointTime(new Date());
			 userPoint.setUsername(username);
			 userPoint.setTotalPoint(user.getTotalPoints() - order.getPointUse());
			 tdUserPointService.save(userPoint);
			
			 user.setTotalPoints(user.getTotalPoints() - order.getPointUse());
			 tdUserService.save(user);
		}
		
	}
	

	// 拼接地址
	public StringBuffer appenAddress(TdShippingAddress shippingAddress) {
		StringBuffer newAddress = new StringBuffer();
		if (null != shippingAddress) {
			if (null != shippingAddress.getProvince()) {
				newAddress.append(shippingAddress.getProvince() + "-");
			}
			if (null != shippingAddress.getCity()) {
				newAddress.append(shippingAddress.getCity() + "-");
			}
			if (null != shippingAddress.getDisctrict()) {
				newAddress.append(shippingAddress.getDisctrict() + " ");
			}
			if (null != shippingAddress.getDetailAddress()) {
				newAddress.append(shippingAddress.getDetailAddress());
			}
		}
		return newAddress;
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
	public String payOrder(@PathVariable Long orderId, ModelMap map, HttpServletRequest req) {
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

		// 判断订单是否过时 订单提交后24小时内
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
			}else if(PAYMENT_WX.equals(payCode)){
				map.addAttribute("order_number", order.getOrderNumber());
				map.addAttribute("total_price", order.getTotalPrice());
				map.addAttribute("order", order); // Max

				 WxPayReturnData res = this.tdWeiXinPayService.unifiedOrder("支付订单" + 
						 	order.getOrderNumber(), order.getOrderNumber(), null, 
		        (int)Math.round(order.getTotalPrice().doubleValue() * 100.0D), "NATIVE");

		      if (("SUCCESS".equalsIgnoreCase(res.getReturn_code())) && 
		    		  ("SUCCESS".equalsIgnoreCase(res.getResult_code()))) {
			        String code_url = res.getCode_url();
			        
			        req.getSession().setAttribute("WXPAYURLSESSEION", code_url);

				return "/client/order_pay_wx";
		      }else{
		    	  return "/client/order_pay_failed";
		      }
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
	


	@RequestMapping(value = "/pay/notify")
	public String payNotify(ModelMap map, HttpServletRequest req) {

		tdCommonService.setHeader(map, req);

		return "/client/order_pay";
	}

	/*
	 * 
	 */
	 private static final Logger paymentLogger = Logger.getLogger("paymentApi");
	 private static final String ISO_ENCODING = "ISO-8859-1";
	 private static final String OUT_TRADE_NO_PARA = "out_trade_no";
	    private static final String ORDER_NO_TB_PARA = "trade_no";
	    private static final String ORDER_STATUS_PARA = "trade_status";
	// 交易完成
     public static String TRADE_FINISHED = "TRADE_FINISHED";
     // 支付成功
     public static String TRADE_SUCCESS = "TRADE_SUCCESS";
     // 交易创建
     public static String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
     // 交易关闭
     public static String TRADE_CLOSED = "TRADE_CLOSED";
     // 等待付款
     public static String TRADE_PENDING ="TRADE_PENDING";
	 
	@RequestMapping(value = "/pay/notify_alipay")
	public void payNotifyAlipay(ModelMap map, HttpServletRequest req, HttpServletResponse resp) {
//		PaymentChannelAlipay paymentChannelAlipay = new PaymentChannelAlipay();
//		paymentChannelAlipay.doResponse(req, resp);
		Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = req.getParameterMap();
        StringBuilder loggeMessage = new StringBuilder();
        for (Iterator<String> iter = requestParams.keySet().iterator(); 
                iter.hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            loggeMessage.append(name).append("=").append(valueStr).append("&");
            params.put(name, valueStr);
        }
        // 消息记录处理
        loggeMessage.setLength(loggeMessage.length() - 1);

        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        // 商户订单号
        String orderNo = null;

        // 支付宝交易号
        String trade_no = null;
        System.err.println("Max:支付宝异步通知");
        // 交易状态
        String trade_status = null;
        try {
            orderNo = new String(req.getParameter(OUT_TRADE_NO_PARA)
                    .getBytes(ISO_ENCODING), AlipayConfig.CHARSET);
            orderNo = orderNo == null ? "" : 
                orderNo.substring(0, orderNo.length() - 6);
            trade_no = new String(req.getParameter(ORDER_NO_TB_PARA)
                    .getBytes(ISO_ENCODING), AlipayConfig.CHARSET);
            trade_status = new String(req.getParameter(ORDER_STATUS_PARA)
                    .getBytes(ISO_ENCODING), AlipayConfig.CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        
        PrintWriter out = null;
        try {
            paymentLogger.info(String.format("AlipayNotify:{%s}.", loggeMessage.toString()));
            // 获取支付宝的通知返回参数
            // 根据状态调用何时接口操作订单状态
            out = resp.getWriter();
            if (AlipayNotify.verify(params)) {// 验证成功
                paymentLogger.info("AlipayNotify:Accepted!");

                System.err.println("Max:"+orderNo);
                if(orderNo.contains("CS") || orderNo.contains("PF") || orderNo.contains("FX") ||orderNo.contains("USE"))
                {
                	TdCash cash = tdCashService.findByCashNumber(orderNo);
                	if (TRADE_FINISHED.equals(trade_status)) {
                		paymentLogger.info(String.format("AlipayNotify:{%s}交易完成",orderNo));
                		 out.println("success");// 请不要修改或删除
                		
                	}else if (TRADE_SUCCESS.equals(trade_status)) {
                		paymentLogger.info(String.format("AlipayNotify:{%s}用户交易成功!",orderNo));
                		// 充值完成
                		tdCashService.afterCash(cash);
                		out.println("success");// 请不要修改或删除
                	} else {
                		paymentLogger.info(String.format("AlipayNotify:{%s}在指定时间段内未支付时关闭的交易；S", orderNo));
//                		cash.setStatus(3L);
//                		tdCashService.save(cash);
                		
                	}
                }else{
                	TdOrder order = tdOrderService.findByOrderNumber(orderNo);
                	List<TdPayRecord> payRecords = payRecordService.getAllByOrderId(order.getId());
                	
                	System.err.println("Max:支付宝异步通知："+trade_status);
                	if (TRADE_SUCCESS.equals(trade_status)) {
                		paymentLogger.info(String.format("AlipayNotify:{%s}用户已经付款给支付宝",orderNo));
                		
                		if(order != null && (order.getStatusId() == 2 || order.getStatusId() == 8)) {
                			order.setStatusId(3l);
                			order.setPayTime(new Date());
                			order = tdOrderService.save(order);
                			tdOrderService.addVir(order);
                			
                		}
                		
                		if(!payRecords.isEmpty()) {
                			int i = 0;
                			for(TdPayRecord record : payRecords) {
                				if(i == 0) {
                					record.setStatusCode(2);
                				} else {
                					record.setStatusCode(3);
                				}
                				i++;
                			}
                			payRecordService.save(payRecords);
                		}
                		
                		sendConfirmGoods(FIRST_TIME, trade_no);
                		
                		out.println("success");// 触发通知
                		 
                	} else if (TRADE_FINISHED.equals(trade_status)) {
                		// 
                		paymentLogger.info(String.format("AlipayNotify:{%s}交易完成!", 
                				orderNo));
                		out.println("success");// 触发通知
                	} else if (TRADE_CLOSED.equals(trade_status)) {
                		
                		paymentLogger.info(String.format("AlipayNotify:{%s}其他状态，未触发通知!", 
                				orderNo));
                		
                	}
                }
               
            } else {// 验证失败
                paymentLogger.info("AlipayNotify:Rejected!");
                out.println("fail");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out != null) {
                out.close();
            }
        }
	}
	private static final int FIRST_TIME = 0;
    private static final int MAX_TIME = 10;
    private static final String NEW_LINE = System.getProperty("line.seperator",
            "\n");
	private boolean sendConfirmGoods(int timeCount, String trade_no) {
        try {
            @SuppressWarnings({ "unchecked", "rawtypes" })
            String result = (String)post(AlipayConfig.REAUESTURL,
                    AlipayConfirmGoods.generatNameValuePair(trade_no),
                    new AlipayConfirmGoodsHandler(), AlipayConfig.CHARSET);
            paymentLogger.info(String.format("AlipayConfirmGoods:%s%s!", NEW_LINE, result));

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            if (timeCount < MAX_TIME) {
                sendConfirmGoods(timeCount++, trade_no);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (timeCount < MAX_TIME) {
                sendConfirmGoods(timeCount++, trade_no);
            }
        }
        return false;
    }

	/*
	 * 
	 */
	@RequestMapping(value = "/pay/result_alipay")
	public String payResultAlipay(Device device, ModelMap map, HttpServletRequest req, HttpServletResponse resp) {
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = req.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			try {
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), AlipayConfig.CHARSET);
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
			orderNo = new String(req.getParameter(Constants.KEY_OUT_TRADE_NO).getBytes("ISO-8859-1"),
					AlipayConfig.CHARSET);
			// 交易状态
			trade_status = new String(req.getParameter("trade_status").getBytes("ISO-8859-1"), AlipayConfig.CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);

		tdCommonService.setHeader(map, req);
		
		if (orderNo.contains("CS") || orderNo.contains("PF") || orderNo.contains("FX") || orderNo.contains("USE")) {
			TdCash cash = tdCashService.findByCashNumber(orderNo);
			// if(null == cash){
			// return "redirect:/distributor/cash_return?type=1";
			// }

			if (verify_result) {// 验证成功
				if ("TRADE_SUCCESS".equals(trade_status)) {

					// afterCash(cash);
					if (orderNo.contains("CS")) {
						return "redirect:/distributor/cash?cashNumber=" + cash.getCashNumber();
					} else if (orderNo.contains("PF")) {
						return "redirect:/provider/cash?cashNumber=" + cash.getCashNumber();
					} else if (orderNo.contains("FX")) {
						return "redirect:/supply/cash?cashNumber=" + cash.getCashNumber();
					} else if (orderNo.contains("USE")) {
						if (device.isMobile() || device.isTablet()) {
							return "redirect:/touch/user/cash?cashNumber=" + cash.getCashNumber();
						}
						return "redirect:/user/cash?cashNumber=" + cash.getCashNumber();
					}
				}
			}
		} else {
			orderNo = (orderNo == null) ? ""
					: (orderNo.length() < 6) ? orderNo : orderNo.substring(0, orderNo.length() - 6);
			TdOrder order = tdOrderService.findByOrderNumber(orderNo);
			if (order == null) {
				// 订单不存在
				if (device.isMobile() || device.isTablet()) {
					return "/touch/order_pay_failed";
				}
				return "/client/order_pay_failed";
			}
			map.put("order", order);

			if (verify_result) {// 验证成功
				if ("TRADE_SUCCESS".equals(trade_status)) {

					// 订单支付成功
					// afterPaySuccess(order);

					if (device.isMobile() || device.isTablet()) {
						return "/touch/order_pay_success";
					}
					return "/client/order_pay_success";
				}
			}
		}
		if (device.isMobile() || device.isTablet()) {
			return "/touch/order_pay_failed";
		}
		// 验证失败或者支付失败
		return "/client/order_pay_failed";
	}

	/*
	 * 
	 */
	@RequestMapping(value = "/pay/result_cebpay")
	public String payResultCEBPay(ModelMap map, HttpServletRequest req, HttpServletResponse resp) {
		tdCommonService.setHeader(map, req);

		String plainData = req.getParameter("Plain");
		String signature = req.getParameter("Signature");

		// 计算得出通知验证结果
		boolean verify_result = CebMerchantSignVerify.merchantVerifyPayGate_ABA(signature, plainData);
		String plainObjectStr = "";

		if (plainData.endsWith("~|~")) {
			plainObjectStr = plainData.substring(0, plainData.length() - 3);
		}

		plainObjectStr = plainObjectStr.replaceAll("=", "\":\"").replaceAll("~\\|~", "\",\"");
		plainObjectStr = "{\"" + plainObjectStr + "\"}";

		JSONObject paymentResult = JSONObject.fromObject(plainObjectStr);

		String orderNo = paymentResult.getString("orderId");
		orderNo = (orderNo == null) ? ""
				: (orderNo.length() < 6) ? orderNo : orderNo.substring(0, orderNo.length() - 6);
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

	
	@RequestMapping(value = "/payqrcode", method = RequestMethod.GET)
	public void verify(HttpServletResponse response, HttpServletRequest request) {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);

		QRCodeUtils qr = new QRCodeUtils();
		String url = (String) request.getSession().getAttribute("WXPAYURLSESSEION");
		qr.getQRCode(url, 300, response);
	}
	@RequestMapping(value = "/remind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> remind(Long id, HttpServletRequest req) {
		Map<String, Object> res = new HashMap<>();
		res.put("code", 1);
		if (null != id) {
			TdOrder order = tdOrderService.findOne(id);
			if (null != order) {
				if (order.getStatusId().equals(3L)) {
					res.put("code", 0);
				}
			}
		}

		return res;
	}

	@RequestMapping(value = "/pay/success")
	public String paySuccess(Long orderId, ModelMap map, HttpServletRequest req) {

		tdCommonService.setHeader(map, req);
		if (null != orderId) {
			map.addAttribute("order", tdOrderService.findOne(orderId));
		}

		return "/client/order_pay_success";

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
		// TdUser tdUser = tdUserService.findByUsername(tdOrder.getUsername());

		if (tdOrder.getStatusId().equals(2L)) {
			// 待发货
			tdOrder.setStatusId(3L);
			tdOrder = tdOrderService.save(tdOrder);
		}
		tdOrderService.addVir(tdOrder);
	}

	/**
	 * 立即购买
	 * 
	 * @param id
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/byNow/{dGoodsId}")
	public String ByNow(@PathVariable Long dGoodsId, Long quantity, HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			return "redirect:/login";
		}
		if (null == quantity) {
			quantity = 1L;
		}

		tdCommonService.setHeader(map, req);

		TdDistributorGoods dgoods = tdDistributorGoodsService.findOne(dGoodsId);

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
		cartGoods.setDistributorId(tdDistributorGoodsService.findDistributorId(dGoodsId));
		cartGoods.setDistributorTitle(dgoods.getDistributorTitle());
		cartGoods.setProviderId(dgoods.getProviderId());
		cartGoods.setProviderTite(dgoods.getProviderTitle());
		cartGoods.setDistributorGoodsId(dgoods.getId());
		tdCartGoodsService.save(cartGoods);
		cartGoodsList.add(cartGoods);

		totalPrice = dgoods.getGoodsPrice() * quantity;
		map.addAttribute("totalPrice", totalPrice);

		map.addAttribute("selected_goods_list", cartGoodsList);

		setPayTypes(map, true, false, req);

		Double postPrice = 0.0;
		if (null != req.getSession().getAttribute("DISTRIBUTOR_ID")) {
			Long disId = (Long) req.getSession().getAttribute("DISTRIBUTOR_ID");
			TdDistributor distributor = tdDistributorService.findOne(disId);
			map.addAttribute("addressList", distributor.getShippingList());

			postPrice += distributor.getPostPrice();
			// 判断是否满额免
			if (null != distributor.getMaxPostPrice() && totalPrice > distributor.getMaxPostPrice()) {
				postPrice = 0.0;
			}
		}

		map.addAttribute("postPrice", postPrice);

		return "/client/order_info";
	}

	@RequestMapping(value = "/proGoods/{dGoodsId}")
	public String distributionGoods(@PathVariable Long dGoodsId, Long quantity, HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			return "redirect:/login";
		}

		tdCommonService.setHeader(map, req);
		if (null == dGoodsId) {
			return "/client/error_404";
		}

		if (null == quantity) {
			quantity = 1L;
		}


		TdDistributorGoods dgoods = tdDistributorGoodsService.findOne(dGoodsId);

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
		cartGoods.setDistributorId(tdDistributorGoodsService.findDistributorId(dGoodsId));
		cartGoods.setDistributorTitle(dgoods.getDistributorTitle());
		cartGoods.setProviderId(dgoods.getProviderId());
		cartGoods.setProviderTite(dgoods.getProviderTitle());
		cartGoods.setDistributorGoodsId(dgoods.getId());

		tdCartGoodsService.save(cartGoods);
		cartGoodsList.add(cartGoods);

		totalPrice = dgoods.getGoodsPrice() * quantity;
		map.addAttribute("totalPrice", totalPrice);

		map.addAttribute("selected_goods_list", cartGoodsList);

		map.addAttribute("type", "pro");
		setPayTypes(map, true, false, req);

		Double postPrice = 0.0;
		if (null != req.getSession().getAttribute("DISTRIBUTOR_ID")) {
			Long disId = (Long) req.getSession().getAttribute("DISTRIBUTOR_ID");
			TdDistributor distributor = tdDistributorService.findOne(disId);
			map.addAttribute("addressList", distributor.getShippingList());

			postPrice += distributor.getPostPrice();
			// 判断是否满额免
			if (null != distributor.getMaxPostPrice() && totalPrice > distributor.getMaxPostPrice()) {
				postPrice = 0.0;
			}
		}
		map.addAttribute("postPrice", postPrice);

		return "/client/order_info";
	}

	@RequestMapping(value="/check/password",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> userPassword(String paypwd,HttpServletRequest req){
		Map<String,Object> res = new HashMap<>();
		res.put("code", 0);
		
		String username = (String)req.getSession().getAttribute("username");
		if(null == username)
		{
			res.put("msg", "登录超时，请重新登录");
			return res;
		}
		TdUser user = tdUserService.findByUsername(username);
		if(null == user){
			res.put("msg", "账号异常，请联系客户");
			return res;
		}
		
		if(null == paypwd || "".equals(paypwd.trim()))
		{
			res.put("msg", "请输入支付密码");
			return res;
		}
		
		if(null == user.getPayPassword())
		{
			res.put("msg", "支付密码未设置，请先到个人中心设置或者使用其他方式支付。");
			return res;
		}
		
		if(!user.getPayPassword().equals(paypwd)){
			res.put("msg", "密码错误，请重新输入");
			return res;
		}
		res.put("code", 1);
		return res;
	}
	
	
//	public void addVir(TdOrder tdOrder) {
//		Double price = 0.0; // 交易总金额
//		Double postPrice = 0.0; // 物流费
//		Double aliPrice = 0.0; // 第三方使用费
//		Double servicePrice = 0.0; // 平台服务费
//		Double totalGoodsPrice = 0.0; // 商品总额
//		Double realPrice = 0.0; // 商家实际收入
//		Double turnPrice = 0.0; // 分销单超市返利
//
//		price += tdOrder.getTotalPrice();
//		postPrice += tdOrder.getPostPrice();
//		aliPrice += tdOrder.getAliPrice();
//		servicePrice += tdOrder.getTrainService();
//		totalGoodsPrice += tdOrder.getTotalGoodsPrice();
//
//		// 添加商家余额及交易记录
//		if (0 == tdOrder.getTypeId()) {
//
//			TdDistributor distributor = tdDistributorService.findOne(tdOrder.getShopId());
//			if (null != distributor) {
//				// 超市普通销售单实际收入： 交易总额-第三方使用费-平台服务费=实际收入
//				realPrice += price - aliPrice - servicePrice;
//
//				distributor.setVirtualMoney(distributor.getVirtualMoney() + realPrice);
//				tdDistributorService.save(distributor);
//
//				TdPayRecord record = new TdPayRecord();
//				record.setCont("订单销售款");
//				record.setCreateTime(new Date());
//				record.setDistributorId(distributor.getId());
//				record.setDistributorTitle(distributor.getTitle());
//				record.setOrderId(tdOrder.getId());
//				record.setOrderNumber(tdOrder.getOrderNumber());
//				record.setStatusCode(1);
//				record.setProvice(price); // 交易总额
//				record.setTotalGoodsPrice(totalGoodsPrice); // 商品总额
//				record.setPostPrice(postPrice); // 邮费
//				record.setAliPrice(aliPrice); // 第三方使用费
//				record.setServicePrice(servicePrice); // 平台服务费
//				record.setRealPrice(realPrice); // 实际收入
//
//				tdPayRecordService.save(record);
//			}
//		} else if (2 == tdOrder.getTypeId()) {
//			TdDistributor distributor = tdDistributorService.findOne(tdOrder.getShopId());
//			TdProvider provider = tdProviderService.findOne(tdOrder.getProviderId());
//
//			turnPrice = tdOrder.getTotalLeftPrice();
//			if (null != distributor) {
//
//				distributor.setVirtualMoney(distributor.getVirtualMoney() + turnPrice); // 超市分销单收入为分销返利额
//				tdDistributorService.save(distributor);
//
//				TdPayRecord record = new TdPayRecord();
//				record.setCont("代售获利");
//				record.setCreateTime(new Date());
//				record.setDistributorId(distributor.getId());
//				record.setDistributorTitle(distributor.getTitle());
//				record.setOrderId(tdOrder.getId());
//				record.setOrderNumber(tdOrder.getOrderNumber());
//				record.setStatusCode(1);
//				record.setProvice(price); // 订单总额
//				record.setTurnPrice(turnPrice); // 超市返利
//				record.setRealPrice(turnPrice); // 超市实际收入
//				tdPayRecordService.save(record);
//			}
//			if (null != provider) {
//				// 分销商实际收入：商品总额-第三方使用费-邮费-超市返利-平台费
//				realPrice += price - aliPrice - postPrice - turnPrice - servicePrice;
//
//				provider.setVirtualMoney(provider.getVirtualMoney() + realPrice);
//
//				TdPayRecord record = new TdPayRecord();
//				record.setCont("分销收款");
//				record.setCreateTime(new Date());
//				record.setDistributorTitle(distributor.getTitle());
//				record.setProviderId(provider.getId());
//				record.setProviderTitle(provider.getTitle());
//				record.setOrderId(tdOrder.getId());
//				record.setOrderNumber(tdOrder.getOrderNumber());
//				record.setStatusCode(1);
//
//				record.setProvice(price); // 订单总额
//				record.setPostPrice(postPrice); // 邮费
//				record.setAliPrice(aliPrice); // 第三方费
//				record.setServicePrice(servicePrice); // 平台费
//				record.setTotalGoodsPrice(totalGoodsPrice); // 商品总价
//				record.setTurnPrice(turnPrice); // 超市返利
//				record.setRealPrice(realPrice); // 实际获利
//				tdPayRecordService.save(record);
//			}
//		}
//
//		TdSetting setting = tdSettingService.findTopBy();
//		if (null != setting.getVirtualMoney()) {
//			setting.setVirtualMoney(setting.getVirtualMoney() + servicePrice + aliPrice);
//		} else {
//			setting.setVirtualMoney(servicePrice + aliPrice);
//		}
//		tdSettingService.save(setting); // 更新平台虚拟余额
//
//		// 记录平台收益
//		TdPayRecord record = new TdPayRecord();
//		record.setCont("商家销售抽取");
//		record.setCreateTime(new Date());
//		record.setDistributorTitle(tdOrder.getShopTitle());
//		record.setOrderId(tdOrder.getId());
//		record.setOrderNumber(tdOrder.getOrderNumber());
//		record.setStatusCode(1);
//		record.setType(1L); // 类型 区分平台记录
//
//		record.setProvice(price); // 订单总额
//		record.setPostPrice(postPrice); // 邮费
//		record.setAliPrice(aliPrice); // 第三方费
//		record.setServicePrice(servicePrice); // 平台费
//		record.setTotalGoodsPrice(totalGoodsPrice); // 商品总价
//		record.setTurnPrice(turnPrice); // 超市返利
//		// 实际获利 =平台服务费+第三方费
//		record.setRealPrice(servicePrice + aliPrice);
//
//		tdPayRecordService.save(record);
//	}

	/**
	 * @author Max
	 * @param cash
	 *            充值完成
	 * 
	 */
	private void afterCash(TdCash cash) {
		if (null != cash) {
			TdPayRecord record = new TdPayRecord();

			if (cash.getCashNumber().contains("CS") && cash.getShopType() == 1) // 超市充值
			{
				TdDistributor distributor = tdDistributorService.findbyUsername(cash.getUsername());
				if (null != distributor) {
					if (null != distributor) {

						distributor.setVirtualMoney(distributor.getVirtualMoney() + cash.getPrice());
						tdDistributorService.save(distributor);

						record.setCont("商家充值");
						record.setCreateTime(new Date());
						record.setDistributorId(distributor.getId());
						record.setDistributorTitle(distributor.getTitle());
						// record.setOrderId(cash.getId());
						record.setOrderNumber(cash.getCashNumber());
						record.setStatusCode(1);

						record.setProvice(cash.getPrice()); // 订单总额
						record.setPostPrice(0.00); // 邮费
						record.setAliPrice(0.00); // 第三方费
						record.setServicePrice(0.00); // 平台费
						record.setTotalGoodsPrice(cash.getPrice()); // 商品总价
						record.setTurnPrice(0.00); // 超市返利
						record.setRealPrice(cash.getPrice()); // 实际获利
						tdPayRecordService.save(record);

					}
				}
			} else if (cash.getCashNumber().contains("USE") && cash.getShopType() == 4) {
				TdUser user = tdUserService.findByUsername(cash.getUsername());
				if (null != user) {
					user.setVirtualMoney(user.getVirtualMoney() + cash.getPrice());
					tdUserService.save(user);

					record.setType(2L);

					record.setCont("会员充值");
					record.setCreateTime(new Date());
					// record.setDistributorId(distributor.getId());
					// record.setDistributorTitle(distributor.getTitle());
					// record.setOrderId(cash.getId());
					record.setOrderNumber(cash.getCashNumber());
					record.setStatusCode(1);
					record.setUsername(cash.getUsername());

					record.setProvice(cash.getPrice()); // 订单总额
					record.setPostPrice(0.00); // 邮费
					record.setAliPrice(0.00); // 第三方费
					record.setServicePrice(0.00); // 平台费
					record.setTotalGoodsPrice(cash.getPrice()); // 商品总价
					record.setTurnPrice(0.00); // 超市返利
					record.setRealPrice(cash.getPrice()); // 实际获利
					tdPayRecordService.save(record);
				}
			} else {
				TdProvider provider = tdProviderService.findByUsername(cash.getUsername());
				if (null != provider) {

					provider.setVirtualMoney(provider.getVirtualMoney() + cash.getPrice());

					record.setCont("商家充值");
					record.setCreateTime(new Date());
					// record.setDistributorId(distributor.getId());
					// record.setDistributorTitle(distributor.getTitle());
					record.setProviderId(provider.getId());
					record.setProviderTitle(provider.getTitle());
					// record.setOrderId(tdOrder.getId());
					record.setOrderNumber(cash.getCashNumber());
					record.setStatusCode(1);

					record.setProvice(cash.getPrice()); // 订单总额
					record.setPostPrice(0.00); // 邮费
					record.setAliPrice(0.00); // 第三方费
					record.setServicePrice(0.00); // 平台费
					record.setTotalGoodsPrice(cash.getPrice()); // 商品总价
					record.setTurnPrice(0.00); // 超市返利
					record.setRealPrice(cash.getPrice()); // 实际获利
					tdPayRecordService.save(record);
				}
			}

			// 平台支出
			TdSetting setting = tdSettingService.findTopBy();
			if (null != setting.getVirtualMoney()) {
				setting.setVirtualMoney(setting.getVirtualMoney() - cash.getPrice());
			}
			tdSettingService.save(setting); // 更新平台虚拟余额

			// 记录平台收益
			record = new TdPayRecord();
			record.setCont("商家充值");
			record.setCreateTime(new Date());
			record.setDistributorTitle(cash.getShopTitle());
			// record.setOrderId(tdOrder.getId());
			record.setOrderNumber(cash.getCashNumber());
			record.setStatusCode(1);
			record.setType(1L); // 类型 区分平台记录

			record.setProvice(cash.getPrice()); // 订单总额
			record.setPostPrice(0.00); // 邮费
			record.setAliPrice(0.00); // 第三方费
			record.setServicePrice(0.00); // 平台费
			record.setTotalGoodsPrice(cash.getPrice()); // 商品总价
			record.setTurnPrice(0.00); // 超市返利
			record.setRealPrice(cash.getPrice());

			tdPayRecordService.save(record);

			cash.setStatus(2L); // 已完成
			tdCashService.save(cash);
		}

	}

}
