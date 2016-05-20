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
import com.cslm.payment.alipay.core.AlipayNotify;
import com.ynyes.cslm.entity.TdCartGoods;
import com.ynyes.cslm.entity.TdCash;
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
import com.ynyes.cslm.entity.TdSetting;
import com.ynyes.cslm.entity.TdShippingAddress;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.entity.TdUserPoint;
import com.ynyes.cslm.service.TdCartGoodsService;
import com.ynyes.cslm.service.TdCashService;
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
import com.ynyes.cslm.service.TdShippingAddressService;
import com.ynyes.cslm.service.TdUserPointService;
import com.ynyes.cslm.service.TdUserService;

import net.sf.json.JSONObject;

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

	// @Autowired
	// private PaymentChannelCEB payChannelCEB;
	//
	// @Autowired
	// private PaymentChannelAlipay payChannelAlipay;


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

		if (null != user) {
			map.addAttribute("user", user);
		}

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
			Long pointUse, // 使用粮草
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
					cartGoodsTotalPrice += cartGoods.getPrice();
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
			Double servicePrice = 0.0;// 平台服务费
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

				// 数量
				long quantity = 0;
				if (distributorGoods.getIsDistribution()) {
					quantity = 1L;
				} else {
					quantity = Math.min(cartGoods.getQuantity(), distributorGoods.getLeftNumber());
				}

				orderGoods.setQuantity(quantity);

				// 获得积分
				if (null != goods.getReturnPoints()) {
					totalPointReturn += goods.getReturnPoints() * quantity;
					orderGoods.setPoints(goods.getReturnPoints() * quantity);
				}

				if (null == distributorGoods || !distributorGoods.getIsDistribution()) {
					// 商品总价
					totalGoodsPrice += cartGoods.getPrice() * cartGoods.getQuantity();
					orderGoodsList.add(orderGoods);
				} else {
					// 商品总价
					disGoodsPrice += cartGoods.getPrice() * cartGoods.getQuantity();
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

				// 使用积分
				tdOrder.setPointUse(pointUse);

				// 用户留言
				tdOrder.setUserRemarkInfo(userMessage);

				// 待付款
				tdOrder.setStatusId(2L);

				if (null != distributor.getPostPrice() && null != deliveryType && deliveryType != 1) // 送货上门计算邮费
				{
					// 计算邮费
					postPrice += distributor.getPostPrice();

					// 判断是否满额免
					if (orderGoodsList.size() == 0) {
						if (null != distributor.getMaxPostPrice()
								&& totalGoodsPrice + disGoodsPrice > distributor.getMaxPostPrice()) {
							postPrice = 0.0;
							totalPrice += disGoodsPrice;
						} else {
							totalPrice += disGoodsPrice + postPrice;
						}
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

				// 订单商品
				// tdOrder.setOrderGoodsList(disOrderGoodsList);
				// tdOrder.setTotalGoodsPrice(disGoodsPrice);

				// 粮草奖励
				tdOrder.setPoints(totalPointReturn);

				// 保存订单商品及订单
				// tdOrderGoodsService.save(orderGoodsList);

				// 添加订单超市信息
				tdOrder.setShopId(distributor.getId());
				tdOrder.setShopTitle(distributor.getTitle());

				// if(null != type && type.equals("pro")){

				tdOrder.setDeliveryMethod(deliveryType); // 配送方式 0、送货上门 1、门店自提
				if (null != deliveryType && deliveryType == 1) // 门店自提
				{
					List<Long> list = tdCartGoodsService.countByDistributorId(username);

					if (null != list && list.size() == 1) // 如果只有一家超市、提供自提点选择
					{
						TdShippingAddress shippingAddress = tdShippingAddressService.findOne(shipAddressId);
						if (null != shippingAddress) {
							tdOrder.setShipAddress(appenAddress(shippingAddress).toString()); // 添加自提点地址
							tdOrder.setShipMobile(shippingAddress.getReceiverMobile()); // 添加自提点联系方式
							tdOrder.setShipAddressTitle(shippingAddress.getReceiverName());
						}
					} else if (list.size() > 1) // 多家超市
					{
						List<TdShippingAddress> shippingList = distributor.getShippingList();

						if (null != shippingList) // 超市设置了自提点 取默认地址
						{
							TdShippingAddress newShipping = new TdShippingAddress();
							for (TdShippingAddress tdShippingAddress : shippingList) {
								if (tdShippingAddress.getIsDefaultAddress()) {
									newShipping = tdShippingAddress;
								}
							}
							tdOrder.setShipAddress(appenAddress(newShipping).toString()); // 添加自提点地址
							tdOrder.setShipMobile(newShipping.getReceiverMobile()); // 添加自提点联系方式
							tdOrder.setShipAddressTitle(newShipping.getReceiverName());
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
							tdOrder.setShippingAddress(newAddress.toString()); // 超市地址
							tdOrder.setShipAddressTitle(distributor.getTitle()); // 超市名称
							tdOrder.setShipMobile(distributor.getMobile()); // 超市联系方式
						}
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
					aliPrice += provider.getAliRation() * totalGoodsPrice; // 第三方使用费
				}
				if (null != provider.getServiceRation()) {
					servicePrice += provider.getServiceRation() * totalGoodsPrice; // 平台服务费
				}
				
				tdOrder.setTotalGoodsPrice(totalGoodsPrice); // 商品总价
				tdOrder.setTotalPrice(totalPrice); // 订单总价
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
					tdOrder.setStatusId(3L);

					// 添加会员虚拟账户金额记录
					TdPayRecord record = new TdPayRecord();

					record.setAliPrice(aliPrice);
					record.setPostPrice(postPrice);
					record.setRealPrice(totalPrice);
					record.setTotalGoodsPrice(totalGoodsPrice);
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

					addVir(tdOrder);// 商家账户记录
				}

				order = tdOrderService.save(tdOrder);
			}

			if (null != orderGoodsList && orderGoodsList.size() > 0) {
				Double aliPrice = 0.0; // 第三方使用费
				Double postPrice = 0.0;// 配送费
				Double totalPrice = 0.0;// 订单总价

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

				// 使用积分
				order.setPointUse(pointUse);

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

				// 保存订单商品及订单
				// tdOrderGoodsService.save(orderGoodsList);

				// 添加订单超市信息
				order.setShopId(distributor.getId());
				order.setShopTitle(distributor.getTitle());

				// if(null != type && type.equals("pro")){

				order.setDeliveryMethod(deliveryType); // 配送方式 0、送货上门 1、门店自提
				if (null != deliveryType && deliveryType == 1) // 门店自提
				{
					List<Long> list = tdCartGoodsService.countByDistributorId(username);

					if (null != list && list.size() == 1) // 如果只有一家超市、提供自提点选择
					{
						TdShippingAddress shippingAddress = tdShippingAddressService.findOne(shipAddressId);
						if (null != shippingAddress) {
							order.setShipAddress(appenAddress(shippingAddress).toString()); // 添加自提点地址
							order.setShipMobile(shippingAddress.getReceiverMobile()); // 添加自提点联系方式
							order.setShipAddressTitle(shippingAddress.getReceiverName());
						}
					} else if (list.size() > 1) // 多家超市
					{
						List<TdShippingAddress> shippingList = distributor.getShippingList();

						if (null != shippingList) // 超市设置了自提点 取默认地址
						{
							TdShippingAddress newShipping = new TdShippingAddress();
							for (TdShippingAddress tdShippingAddress : shippingList) {
								if (tdShippingAddress.getIsDefaultAddress()) {
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
				order.setTotalPrice(totalPrice); // 订单总价
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
				} else if (payTypeId == 0)// 余额付
				{
					user.setVirtualMoney(user.getVirtualMoney() - totalPrice); // 虚拟账号扣除
					tdUserService.save(user);

					order.setPayTypeTitle("余额支付");
					order.setStatusId(3L);

					// 添加会员虚拟账户金额记录
					TdPayRecord record = new TdPayRecord();

					record.setAliPrice(aliPrice);
					record.setPostPrice(postPrice);
					record.setRealPrice(totalPrice);
					record.setTotalGoodsPrice(totalGoodsPrice);
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

					addVir(order);// 商家账户记录
				}

				order = tdOrderService.save(order);
			}

			// // 添加积分使用记录
			// if (null != user) {
			// if (null == user.getTotalPoints())
			// {
			// user.setTotalPoints(0L);
			//
			// user = tdUserService.save(user);
			// }
			//
			// if (pointUse.compareTo(0L) >= 0 && null != user.getTotalPoints()
			// && user.getTotalPoints().compareTo(pointUse) >= 0) {
			// TdUserPoint userPoint = new TdUserPoint();
			// userPoint.setDetail("购买商品使用积分抵扣");
			// userPoint.setOrderNumber(tdOrder.getOrderNumber());
			// userPoint.setPoint(0 - pointUse);
			// userPoint.setPointTime(new Date());
			// userPoint.setUsername(username);
			// userPoint.setTotalPoint(user.getTotalPoints() - pointUse);
			// tdUserPointService.save(userPoint);
			//
			// user.setTotalPoints(user.getTotalPoints() - pointUse);
			// tdUserService.save(user);
			// }
			// }
		}

		// 删除已生成订单的购物车项
		tdCartGoodsService.delete(cartSelectedGoodsList);

		if (null != payTypeId && payTypeId == 0) {
			return "redirect:/user/order?id=" + order.getId();
		}
		return "redirect:/order/pay?orderId=" + order.getId();
		// return "redirect:/order/success?orderId=" + tdOrder.getId();
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

	/**
	 * 支付尾款
	 * 
	 * @param orderId
	 * @param map
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/dopayleft/{orderId}")
	public String payOrderLeft(@PathVariable Long orderId, ModelMap map, HttpServletRequest req) {
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

		// String amount = order.getTotalLeftPrice().toString();
		// req.setAttribute("totalPrice", amount);

		String payForm = "";

//		Long payId = order.getPayTypeId();
//		TdPayType payType = tdPayTypeService.findOne(payId);
		// if (payType != null) {
		// TdPayRecord record = new TdPayRecord();
		// record.setCreateTime(new Date());
		// record.setOrderId(order.getId());
		// record.setPayTypeId(payType.getId());
		// record.setStatusCode(1);
		// record.setCreateTime(new Date());
		// record = payRecordService.save(record);
		//
		// String payRecordId = record.getId().toString();
		// int recordLength = payRecordId.length();
		// if (recordLength > 6) {
		// payRecordId = payRecordId.substring(recordLength - 6);
		// } else {
		// payRecordId = leftPad(payRecordId, 6, "0");
		// }
		// req.setAttribute("payRecordId", payRecordId);
		//
		// req.setAttribute("orderNumber", order.getOrderNumber());
		//
		// String payCode = payType.getCode();
		// if (PAYMENT_ALI.equals(payCode)) {
		// payForm = payChannelAlipay.getPayFormData(req);
		// map.addAttribute("charset", AlipayConfig.CHARSET);
		// } else if (CEBPayConfig.INTER_B2C_BANK_CONFIG.keySet().contains(
		// payCode)) {
		// req.setAttribute("payMethod", payCode);
		// payForm = payChannelCEB.getPayFormData(req);
		// map.addAttribute("charset", "GBK");
		// } else {
		// // 其他目前未实现的支付方式
		// return "/client/error_404";
		// }
		// } else {
		// return "/client/error_404";
		// }

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
	public void payNotifyAlipay(ModelMap map, HttpServletRequest req, HttpServletResponse resp) {
		PaymentChannelAlipay paymentChannelAlipay = new PaymentChannelAlipay();
		paymentChannelAlipay.doResponse(req, resp);
	}

	/*
	 * 
	 */
	// @RequestMapping(value = "/pay/notify_cebpay")
	// public void payNotifyCEBPay(ModelMap map, HttpServletRequest req,
	// HttpServletResponse resp) {
	// payChannelCEB.doResponse(req, resp);
	// }

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

					afterCash(cash);
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
					afterPaySuccess(order);

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

	/*
	 * 
	 */
	@RequestMapping(value = "/change_paymethod", method = { RequestMethod.POST })
	public @ResponseBody Map<String, String> changePaymentMethod(Long orderId, Long paymentMethodId, ModelMap map,
			HttpServletRequest req) {
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
		double couponAndPointsFee = goodPrice + orgPayTypeFee + deliverTypeFee - order.getTotalPrice();

		/*
		 * 按百分比收取手续费,手续费重新计算(商品总额*百分比)
		 */
		if (payType.getIsFeeCountByPecentage()) {
			payTypeFee = goodPrice * payTypeFee / 100;
		}

		order.setTotalPrice(goodPrice + payTypeFee + deliverTypeFee - couponAndPointsFee);
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
		// TdUser tdUser = tdUserService.findByUsername(tdOrder.getUsername());

		if (tdOrder.getStatusId().equals(2L)) {
			// 待发货
			tdOrder.setStatusId(3L);
			tdOrder = tdOrderService.save(tdOrder);
		}
		addVir(tdOrder);
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

		// TdGoods goods = tdGoodsService.findOne(dGoodsId);
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

		map.addAttribute("totalPrice", dgoods.getGoodsPrice() * quantity);

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

		if (null == dGoodsId) {
			return "/client/error_404";
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

		map.addAttribute("totalPrice", dgoods.getGoodsPrice() * quantity);

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

	public void addVir(TdOrder tdOrder) {
		Double price = 0.0; // 交易总金额
		Double postPrice = 0.0; // 物流费
		Double aliPrice = 0.0; // 第三方使用费
		Double servicePrice = 0.0; // 平台服务费
		Double totalGoodsPrice = 0.0; // 商品总额
		Double realPrice = 0.0; // 商家实际收入
		Double turnPrice = 0.0; // 分销单超市返利

		price += tdOrder.getTotalPrice();
		postPrice += tdOrder.getPostPrice();
		aliPrice += tdOrder.getAliPrice();
		servicePrice += tdOrder.getTrainService();
		totalGoodsPrice += tdOrder.getTotalGoodsPrice();

		// 添加商家余额及交易记录
		if (0 == tdOrder.getTypeId()) {

			TdDistributor distributor = tdDistributorService.findOne(tdOrder.getShopId());
			if (null != distributor) {
				// 超市普通销售单实际收入： 交易总额-第三方使用费-平台服务费=实际收入
				realPrice += price - aliPrice - servicePrice;

				distributor.setVirtualMoney(distributor.getVirtualMoney() + realPrice);
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
				record.setPostPrice(postPrice); // 邮费
				record.setAliPrice(aliPrice); // 第三方使用费
				record.setServicePrice(servicePrice); // 平台服务费
				record.setRealPrice(realPrice); // 实际收入

				tdPayRecordService.save(record);
			}
		} else if (2 == tdOrder.getTypeId()) {
			TdDistributor distributor = tdDistributorService.findOne(tdOrder.getShopId());
			TdProvider provider = tdProviderService.findOne(tdOrder.getProviderId());

			turnPrice = tdOrder.getTotalLeftPrice();
			if (null != distributor) {

				distributor.setVirtualMoney(distributor.getVirtualMoney() + turnPrice); // 超市分销单收入为分销返利额
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
			if (null != provider) {
				// 分销商实际收入：商品总额-第三方使用费-邮费-超市返利-平台费
				realPrice += price - aliPrice - postPrice - turnPrice - servicePrice;

				provider.setVirtualMoney(provider.getVirtualMoney() + realPrice);

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
				record.setAliPrice(aliPrice); // 第三方费
				record.setServicePrice(servicePrice); // 平台费
				record.setTotalGoodsPrice(totalGoodsPrice); // 商品总价
				record.setTurnPrice(turnPrice); // 超市返利
				record.setRealPrice(realPrice); // 实际获利
				tdPayRecordService.save(record);
			}
		}

		TdSetting setting = tdSettingService.findTopBy();
		if (null != setting.getVirtualMoney()) {
			setting.setVirtualMoney(setting.getVirtualMoney() + servicePrice + aliPrice);
		} else {
			setting.setVirtualMoney(servicePrice + aliPrice);
		}
		tdSettingService.save(setting); // 更新平台虚拟余额

		// 记录平台收益
		TdPayRecord record = new TdPayRecord();
		record.setCont("商家销售抽取");
		record.setCreateTime(new Date());
		record.setDistributorTitle(tdOrder.getShopTitle());
		record.setOrderId(tdOrder.getId());
		record.setOrderNumber(tdOrder.getOrderNumber());
		record.setStatusCode(1);
		record.setType(1L); // 类型 区分平台记录

		record.setProvice(price); // 订单总额
		record.setPostPrice(postPrice); // 邮费
		record.setAliPrice(aliPrice); // 第三方费
		record.setServicePrice(servicePrice); // 平台费
		record.setTotalGoodsPrice(totalGoodsPrice); // 商品总价
		record.setTurnPrice(turnPrice); // 超市返利
		// 实际获利 =平台服务费+第三方费
		record.setRealPrice(servicePrice + aliPrice);

		tdPayRecordService.save(record);
	}

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
