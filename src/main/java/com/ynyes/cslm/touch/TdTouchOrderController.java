package com.ynyes.cslm.touch;

import static org.apache.commons.lang3.StringUtils.leftPad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cslm.payment.alipay.PaymentChannelAlipay;
import com.ibm.icu.util.Calendar;
import com.ynyes.cslm.controller.front.AbstractPaytypeController;
import com.ynyes.cslm.entity.TdCartGoods;
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
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.service.TdProviderGoodsService;
import com.ynyes.cslm.service.TdProviderService;
import com.ynyes.cslm.service.TdSettingService;
import com.ynyes.cslm.service.TdShippingAddressService;
import com.ynyes.cslm.service.TdUserCashRewardService;
import com.ynyes.cslm.service.TdUserPointService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.util.ClientConstant;

/**
 * 订单
 *
 */
@Controller
@RequestMapping("/touch/order")
public class TdTouchOrderController {
    private static final String PAYMENT_ALI = "ALI";
	
    @Autowired
    private TdCartGoodsService tdCartGoodsService;

    @Autowired
    private TdUserService tdUserService;

    @Autowired
    private TdGoodsService tdGoodsService;

    @Autowired
    private TdPayTypeService tdPayTypeService;

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
    private TdGoodsCombinationService tdGoodsCombinationService;
    
    @Autowired
    private TdCouponTypeService tdCouponTypeService;
    
    @Autowired
    private TdCouponService tdCouponService;
    
    @Autowired
    private TdSettingService tdSettingService;
    
    @Autowired
    private TdProductCategoryService tdProductCategoryService;
    
    @Autowired
    private TdPayRecordService payRecordService;
    
    @Autowired
    TdUserCashRewardService tdUserCashRewardService;
    
    @Autowired
    private TdDistributorService tdDistributorService;
    
    @Autowired
    private TdDistributorGoodsService tdDistributorGoodsService;
    
    @Autowired
    private TdPayRecordService tdPayRecordService;
    
    @Autowired
    private TdProviderGoodsService tdProviderGoodsService;
    
    @Autowired
    private TdShippingAddressService tdShippingAddressService;
    
    @Autowired
    private TdProviderService tdProviderService;
    
    
    
    
    @RequestMapping(value = "/info")
    public String orderInfo(Long code, HttpServletRequest req, HttpServletResponse resp,
            ModelMap map) {
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/touch/login";
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

        if (null == user) {
        	return "redirect:/touch/login";
        }
        map.addAttribute("user", user);

        List<TdCartGoods> selectedGoodsList = tdCartGoodsService
                .findByUsernameAndIsSelectedTrue(username);

        Long totalPointLimited = 0L;// 积分限制综总和
        Double totalPrice = 0.0; // 购物总额
        Long totalQuantity = 0L;
        
        // 积分限制总和 和 购物总额
        if (null != selectedGoodsList) {
            for (TdCartGoods cg : selectedGoodsList) {
                TdGoods goods = tdGoodsService.findOne(cg.getGoodsId());
                if (null != goods && null != goods.getPointLimited()) {
                    totalPointLimited += goods.getPointLimited()
                            * cg.getQuantity();
                }else{
                	totalPointLimited +=0;
                }
                totalPrice += cg.getPrice() * cg.getQuantity();
                totalQuantity += cg.getQuantity();
            }
        }
        
        
        // 查询出当前选择商品所属超市ID
        List<Long> list = tdCartGoodsService.countByDistributorId(username);
        
        Double postPrice = 0.0;
        if(null != list && list.size()==1) // 如果只有一家超市、提供自提点选择
        {
        	TdDistributor distributor = tdDistributorService.findOne(list.get(0));
        	map.addAttribute("addressList", distributor.getShippingList());
        	map.addAttribute("distributor", distributor); 
        	
        	postPrice += distributor.getPostPrice();
        	
        	// 判断是否满额免
        	if(null!= distributor.getMaxPostPrice() && totalPrice > distributor.getMaxPostPrice())
        	{
        		postPrice =0.0;
        	}
        	map.addAttribute("postPrice", postPrice);
        }else{
        	map.addAttribute("post", "结算商品来自多个超市，邮费另算");
        }
        
        // 积分限额
        if (null != user.getTotalPoints()) {
            if (totalPointLimited > user.getTotalPoints()) {
                map.addAttribute("total_point_limit", user.getTotalPoints());
            } else {
                map.addAttribute("total_point_limit", totalPointLimited);
            }
        }
        
        map.addAttribute("pay_type_list", tdPayTypeService.findByIsEnableTrue());
        map.addAttribute("delivery_type_list",
                tdDeliveryTypeService.findByIsEnableTrue());
        map.addAttribute("selected_goods_list", selectedGoodsList);
        map.addAttribute("totalPrice", totalPrice);
        map.addAttribute("totalQuantity", totalQuantity);
        
        // 余额不足提醒
        if(null != code && code==1)
        {
        	map.addAttribute("msg", "余额不足，请选择在线支付！");
        }
        
        tdCommonService.setHeader(map, req);

      //判断是否为app链接
        Integer isApp = (Integer) req.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
        
        return "/touch/order_info";
    }

    @RequestMapping(value = "/goods/{type}")
    public String orderEdit(HttpServletRequest req, HttpServletResponse resp,
            @PathVariable String type, Long gid, ModelMap map) {
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/touch/login";
        }

        // 把所有的购物车项转到该登陆用户下
        List<TdCartGoods> cgList = tdCartGoodsService
                .findByUsernameAndIsSelectedTrue(username);
        Double totalPrice = 0.0; // 购物总额
        if (null != cgList && null != type && null != gid) {
            for (TdCartGoods cg : cgList) {
                if (gid.equals(cg.getGoodsId())) {
                	
                    TdGoods goods = tdGoodsService.findOne(cg.getGoodsId());

                    if (null != goods) {
                        if (type.equalsIgnoreCase("plus")) 
                        {
                            // 团购
                            if (goods.getIsGroupSale()
                                    && goods.getGroupSaleStartTime().before(new Date())
                                    && goods.getGroupSaleStopTime().after(new Date())
                                    && cg.getPrice().equals(goods.getGroupSalePrice())) 
                            {
                                if (cg.getQuantity().compareTo(goods.getGroupSaleLeftNumber()) < 0) {
                                    cg.setQuantity(cg.getQuantity() + 1L);
                                }
                            }
                            else
                            {
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
                
             // 总价
                totalPrice += cg.getPrice() * cg.getQuantity();
            }
        }
//        
//        List<TdCartGoods> selectedGoodsList = tdCartGoodsService.findByUsernameAndIsSelectedTrue(username);
//        map.addAttribute("selected_goods_list", selectedGoodsList);
        
     // 运费计算
        Double totalPostage = 0.0;      
        TdGoods tdGoods = null;
//        for(TdCartGoods tdCartGoods : cgList){
//        	tdGoods = tdGoodsService.findOne(tdCartGoods.getGoodsId());
//        	if (null != tdGoods.getIsFeeNot() && !tdGoods.getIsFeeNot()) {
//				if (null != tdGoods.getPostage()) {
//					totalPostage += tdGoods.getPostage() * tdCartGoods.getQuantity();
//				}
//        		
//			}
//        }
//        TdSetting tdSetting = tdSettingService.findTopBy();
//        if (null != tdSetting.getMaxPostage()) {
//			if (totalPrice > tdSetting.getMaxPostage()) {
//				totalPostage = 0.0;
//			}
//		}
        map.addAttribute("totalPostage", totalPostage);
        
        return "redirect:/touch/order/info";
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
            String type,
            HttpServletRequest req, ModelMap map) {
        String username = (String) req.getSession().getAttribute("username");
       
        if (null == username) {
            return "redirect:/touch/login";
        }
        tdCommonService.setHeader(map, req);
        
        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);

        if (null == user) {
            return "/touch/error_404";
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
        	if(null != pointUse)
        	{
        		if (pointUse.compareTo(user.getTotalPoints()) >= 0) {
        			pointUse = user.getTotalPoints();
        		}
        	}
        }

        // 购物车商品
        List<TdCartGoods> cartSelectedGoodsList = tdCartGoodsService
                .findByUsernameAndIsSelectedTrue(username);

        // 储存超市Id 和对应商品  为拆分订单做准备
        Map<Long,List<TdCartGoods>> cartGoodsMap =new HashMap<>();
        
        Double cartGoodsTotalPrice = 0.0;
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
					cartGoodsTotalPrice += cartGoods.getPrice();
				}
			}
        }
        
       
        if(null == cartGoodsMap || cartGoodsMap.size() <= 0){
        	return "//touch/error_404";
        }
        
        if(null != payTypeId && payTypeId ==0)
        {
        	if(user.getVirtualMoney() == null || cartGoodsTotalPrice > user.getVirtualMoney())
        	{
        		return "redirect:/touch/order/info?code="+1;
        	}
        }
        
        TdOrder order = new TdOrder();
        // 订单拆分
        Set<Entry<Long,List<TdCartGoods>>> set = cartGoodsMap.entrySet();
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
					totalGoodsPrice += cartGoods.getPrice() * cartGoods.getQuantity();
					orderGoodsList.add(orderGoods);
				} else {
					// 获得积分
					if (null != goods.getReturnPoints()) {
						disPointReturn += goods.getReturnPoints() * quantity;
						orderGoods.setPoints(goods.getReturnPoints() * quantity);
					}
					
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

				// 积分奖励
				tdOrder.setPoints(disPointReturn);

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
					tdOrder.setPayTypeId(0L);
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
				
				addUserPoint(order,username);
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
					tdOrder.setPayTypeId(0L);
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
				
				addUserPoint(order,username);
			}

		}

        // 删除已生成订单的购物车项
        tdCartGoodsService.delete(cartSelectedGoodsList);
         
        if(null != payTypeId && payTypeId ==0)
        {
        	return "redirect:/touch/user/order?id="+order.getId();
        }
         return "redirect:/touch/order/pay?orderId=" + order.getId();
        // return "redirect:/order/success?orderId=" + tdOrder.getId();
    }
    
 // 添加会员积分
 	public void addUserPoint(TdOrder order,String username){
 		
 		TdUser user = tdUserService.findByUsername(username);
 		
 		 // 添加积分使用记录
 		 if (null != user) {
 			 if (null == user.getTotalPoints())
 			 {
 				 user.setTotalPoints(0L);
 				 user = tdUserService.save(user);
 			 }
 		
 				 TdUserPoint userPoint = new TdUserPoint();
 				 userPoint.setDetail("购买商品获得积分抵扣");
 				 userPoint.setOrderNumber(order.getOrderNumber());
 				 userPoint.setPoint(order.getPoints());
 				 userPoint.setPointTime(new Date());
 				 userPoint.setUsername(username);
 				 userPoint.setTotalPoint(user.getTotalPoints() + order.getPoints());
 				 tdUserPointService.save(userPoint);
 				
 				 user.setTotalPoints(user.getTotalPoints() + order.getPoints());
 				 tdUserService.save(user);
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
			return "redirect:/touch/login";
		}
		if(null== quantity){
			quantity = 1L;
		}
		
		tdCommonService.setHeader(map, req);
		
//		TdGoods goods = tdGoodsService.findOne(dGoodsId);
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

		map.addAttribute("totalPrice",dgoods.getGoodsPrice()*quantity);
		
        map.addAttribute("selected_goods_list",cartGoodsList);
        
        map.addAttribute("pay_type_list", tdPayTypeService.findByIsEnableTrue());
        
        Double postPrice = 0.0;
        if(null !=req.getSession().getAttribute("DISTRIBUTOR_ID"))
    	{
        	Long disId = (Long)req.getSession().getAttribute("DISTRIBUTOR_ID");
        	TdDistributor distributor = tdDistributorService.findOne(disId);
        	map.addAttribute("addressList", distributor.getShippingList());

        	postPrice += distributor.getPostPrice();
        	// 判断是否满额免
        	if(null!= distributor.getMaxPostPrice() && totalPrice > distributor.getMaxPostPrice())
        	{
        		postPrice =0.0;
        	}
    	}
        
        	
        	map.addAttribute("postPrice", postPrice);
        
    	return "/touch/order_info";
    }
    
    /**
     * 
     * 立即预购
     * 
     */
    @RequestMapping(value = "/proGoods/{dGoodsId}")
	public String distributionGoods(@PathVariable Long dGoodsId, Long quantity, HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");

		if (null == username) {
			return "redirect:/touch/login";
		}

		if (null == dGoodsId) {
			return "/touch/error_404";
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
		map.addAttribute("pay_type_list", tdPayTypeService.findByIsEnableTrue());

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

		return "/touch/order_info";
	}

    @RequestMapping(value="/address",method=RequestMethod.POST)
    public String address(Long id,HttpServletRequest req,ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("username");
    	if(null == username)
    	{
    		return "redirect:/touch/login";
    	}
    	TdUser user = tdUserService.findByUsername(username);
    	List<TdShippingAddress> addressList = user.getShippingAddressList();
    	
    	if(null != id)
    	{
    		for (TdShippingAddress address : addressList) {
				if(address.getId().equals(id)){
					address.setIsDefaultAddress(true);
					tdShippingAddressService.save(address);
				}else{
					address.setIsDefaultAddress(false);
					tdShippingAddressService.save(address);
				}
			}
    	}
    	map.addAttribute("user", user);
    	
    	return "/touch/order_info_addr";
    }
    
    
    @RequestMapping(value = "/pay")
    public String pay(Long orderId, ModelMap map, HttpServletRequest req) {

        tdCommonService.setHeader(map, req);

        if (null == orderId) {
            return "/touch/error_404";
        }

        map.addAttribute("order", tdOrderService.findOne(orderId));

      //判断是否为app链接
        Integer isApp = (Integer) req.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
        
        return "/touch/order_pay";
    }

    @RequestMapping(value = "/success")
    public String success(Long orderId, ModelMap map, HttpServletRequest req) {

        tdCommonService.setHeader(map, req);

        if (null == orderId) {
            return "/touch/error_404";
        }

        map.addAttribute("order", tdOrderService.findOne(orderId));
        
        /**
		 * @author lc
		 * @注释：添加同种商品推荐 取订单第一个商品
		 */
        List<TdOrderGoods> orderGoodsList = (tdOrderService.findOne(orderId)).getOrderGoodsList();
        TdOrderGoods tdOrderGoods = orderGoodsList.get(0);
        TdGoods tdGoods = tdGoodsService.findOne(tdOrderGoods.getGoodsId());
        map.addAttribute("recommend_goods_page", tdGoodsService.findByCategoryIdTreeContainingOrderBySortIdAsc(tdGoods.getCategoryId(), 0, ClientConstant.pageSize));
        
      //判断是否为app链接
        Integer isApp = (Integer) req.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
        
        return "/touch/order_success";
    }

    @RequestMapping(value = "/pay/success")
    public String paySuccess(ModelMap map, HttpServletRequest req) {

        tdCommonService.setHeader(map, req);

      //判断是否为app链接
        Integer isApp = (Integer) req.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
        
        return "/touch/order_pay_success";
    }
    
    @RequestMapping(value = "/dopay/{orderId}")
    public String payOrder(@PathVariable Long orderId, ModelMap map,
            HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/touch/login";
        }

        tdCommonService.setHeader(map, req);

        if (null == orderId) {
            return "/touch/error_404";
        }

        TdOrder order = tdOrderService.findOne(orderId);

        if (null == order) {
            return "/touch/error_404";
        }

        // 根据订单类型来判断支付时间是否过期
//        if (order.getTypeId().equals(3L)) { // 抢拍 订单提交后30分钟内
//            Date cur = new Date();
//            long temp = cur.getTime() - order.getOrderTime().getTime();
//            // System.out.println(temp);
//            if (temp > 1000 * 60 * 30) {
//                return "/touch/overtime";
//            }
//        } else if (order.getTypeId().equals(4L) || order.getTypeId().equals(5L)) { // 团购
//                                                                                   // 
//            Date cur = new Date();
//            long temp = 0L;
//           
//            TdGoods tdGoods = tdGoodsService.findOne(order.getOrderGoodsList().get(0).getGoodsId());
//            if (null != tdGoods) {
//				if (order.getTypeId().equals(4L)) {
//					temp = cur.getTime() - tdGoods.getGroupSaleStopTime().getTime();
//				}else if (order.getTypeId().equals(5L)) {
//					temp = cur.getTime() - tdGoods.getGroupSaleHundredStopTime().getTime();
//				}
//				if (temp > 0) {
//                    return "/client/overtime";
//                }
//			}
//        } else { // 普通 订单提交后24小时内
//            Date cur = new Date();
//            long temp = cur.getTime() - order.getOrderTime().getTime();
//            if (temp > 1000 * 3600 * 24) {
//                return "/touch/overtime";
//            }
//        }

        // 待付款
        if (!order.getStatusId().equals(2L)) {
            return "/touch/error_404";
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
            	PaymentChannelAlipay payChannelAlipay = new PaymentChannelAlipay();
                payForm = payChannelAlipay.getPayFormData(req);
//                map.addAttribute("charset", AlipayConfig.CHARSET);
            } 
//            } 
//            else if (CEBPayConfig.INTER_B2C_BANK_CONFIG.keySet().contains(
//                    payCode)) {
//                req.setAttribute("payMethod", payCode);
//                payForm = payChannelCEB.getPayFormData(req);
//                map.addAttribute("charset", "GBK");
            else {
                // 其他目前未实现的支付方式
                return "/touch/error_404";
            }
        } else {
            return "/touch/error_404";
        }

        order.setPayTime(new Date());

        tdOrderService.save(order);

        map.addAttribute("payForm", payForm);

        return "/touch/order_pay_form";
    }
    
    @RequestMapping(value = "/pay/notify")
    public String payNotify(ModelMap map, HttpServletRequest req) {
        // String username = (String) req.getSession().getAttribute("username");
        //
        // if (null == username) {
        // return "redirect:/login";
        // }

        tdCommonService.setHeader(map, req);

        return "/touch/order_pay";
    }

    /*
     * 
     */
    @RequestMapping(value = "/pay/notify_alipay")
    public void payNotifyAlipay(ModelMap map, HttpServletRequest req,
            HttpServletResponse resp) {
    	PaymentChannelAlipay payChannelAlipay = new PaymentChannelAlipay();
        payChannelAlipay.doResponse(req, resp);
    }

    /*
     * 
     */
//    @RequestMapping(value = "/pay/notify_cebpay")
//    public void payNotifyCEBPay(ModelMap map, HttpServletRequest req,
//            HttpServletResponse resp) {
//        payChannelCEB.doResponse(req, resp);
//    }
  
    /**
     * 触屏微信支付
     * 
     * @param orderId
     * @param map
     * @param req
     * @return
     */
//    @RequestMapping(value = "/dopay")
//    public String payOrder(Long orderId, ModelMap map,HttpServletRequest req,Long state,String code)
//    {
//        String username = (String) req.getSession().getAttribute("username");
//
//        if (null == username)
//        {
//            return "redirect:/touch/login";
//        }
//        TdUser user = tdUserService.findByUsername(username);
//
//        tdCommonService.setHeader(map, req);
//
//        String openId = "";
//        if (null == state) 
//        {
//            return "/touch/error_404";
//        }
//        if (user.getOpenid() == null)
//        {
//            if (code != null)
//        	{
//        		System.out.println("Madejing: code = " + code);
//        		
//    			String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Configure.getAppid() +"&secret=" + Configure.getKey() +"&code=" + code + "&grant_type=authorization_code";
//    			
//    			System.out.println("Madejing: accessTokenUrl = " + accessTokenUrl);
//    			
//    			String result = HttpRequest.sendGet(accessTokenUrl, null);
//    			
//    			System.out.println("madjeing: result =" + result);
//    			
//    		    openId = JSONObject.fromObject(result).getString("openid");
//    		    TdUser user2 = tdUserService.findByOpenid(openId);
//    		    if (user2 == null && user.getOpenid() == null)
//    		    {
//    		    	user.setOpenid(JSONObject.fromObject(result).getString("openid"));
//				}
//    		    
//    		    System.out.println("Madejing: openid = " + openId);
//    		}
//		}
//
//
//        TdOrder order = tdOrderService.findOne(state);
//
//        if (null == order)
//        {
//            return "/client/error_404";
//        }
//
//        // 根据订单类型来判断支付时间是否过期
//        // 普通 订单提交后30分钟内
////        Date cur = new Date();
////        long temp = cur.getTime() - order.getOrderTime().getTime();
//
//        // 待付款
//        if (!order.getStatusId().equals(2L))
//        {
//            return "/touch/error_404";
//        }
//
//        String amount = order.getTotalPrice().toString();
//        req.setAttribute("totalPrice", amount);
//
//        String payForm = "";
//        map.addAttribute("order", order);
//        map.addAttribute("order_number", order.getOrderNumber());
//		map.addAttribute("total_price", order.getTotalPrice());
//
//		Calendar calExpire = Calendar.getInstance();
//		calExpire.setTime(order.getOrderTime());
//
//
//		//统一支付接口
//		String noncestr = RandomStringGenerator.getRandomStringByLength(32);
//		ModelMap signMap = new ModelMap();
//		signMap.addAttribute("appid", Configure.getAppid());
//		signMap.addAttribute("attach", "订单支付");
//		signMap.addAttribute("body", "支付订单" + order.getOrderNumber());
//		signMap.addAttribute("mch_id", Configure.getMchid());
//		signMap.addAttribute("nonce_str",noncestr);
//		signMap.addAttribute("out_trade_no", order.getOrderNumber());
//		signMap.addAttribute("total_fee", Math.round(order.getTotalPrice() * 100));
//		signMap.addAttribute("spbill_create_ip", "116.55.230.207");
//		signMap.addAttribute("notify_url", "http://www.huizhidian.com/touch/order/wx_notify");
//		signMap.addAttribute("trade_type", "JSAPI");
//		signMap.addAttribute("openid", user.getOpenid());
//
//		String mysign = Signature.getSign(signMap);
//
//		String content = "<xml>\n" + "<appid>"
//				+ Configure.getAppid()
//				+ "</appid>\n"
//				+ "<attach>订单支付</attach>\n"
//				+ "<body>支付订单"
//				+ order.getOrderNumber()
//				+ "</body>\n"
//				+ "<mch_id>"
//				+ Configure.getMchid()
//				+ "</mch_id>\n"
//				+ "<nonce_str>"
//				+ noncestr
//				+ "</nonce_str>\n"
//				+ "<notify_url>http://www.huizhidian.com/touch/order/wx_notify</notify_url>\n"
//				+ "<out_trade_no>" + order.getOrderNumber() + "</out_trade_no>\n"
//				+ "<spbill_create_ip>116.55.230.207</spbill_create_ip>\n"
//				+ "<total_fee>" + Math.round(order.getTotalPrice() * 100)
//				+ "</total_fee>\n" + "<trade_type>JSAPI</trade_type>\n"
//				+ "<sign>" + mysign + "</sign>\n"
//				+ "<openid>" + user.getOpenid() + "</openid>\n" + "</xml>\n";
//		System.out.print("MDJ: xml=" + content + "\n");
//
//		String return_code = null;
//		String prepay_id = null;
//		String result_code = null;
//		String line = null;
//		HttpsURLConnection urlCon = null;
//		try
//		{
//			urlCon = (HttpsURLConnection) (new URL("https://api.mch.weixin.qq.com/pay/unifiedorder")).openConnection();
//			urlCon.setDoInput(true);
//			urlCon.setDoOutput(true);
//			urlCon.setRequestMethod("POST");
//			urlCon.setRequestProperty("Content-Length",String.valueOf(content.getBytes().length));
//			urlCon.setUseCaches(false);
//			// 设置为gbk可以解决服务器接收时读取的数据中文乱码问题
//			urlCon.getOutputStream().write(content.getBytes("utf-8"));
//			urlCon.getOutputStream().flush();
//			urlCon.getOutputStream().close();
//			BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
//
//			while ((line = in.readLine()) != null)
//			{
//				System.out.println(": rline: " + line);
//				if (line.contains("<return_code>"))
//				{
//					return_code = line.replaceAll(
//							"<xml><return_code><\\!\\[CDATA\\[", "")
//							.replaceAll("\\]\\]></return_code>", "");
//				} 
//				else if (line.contains("<prepay_id>")) 
//				{
//					prepay_id = line.replaceAll("<prepay_id><\\!\\[CDATA\\[",
//							"").replaceAll("\\]\\]></prepay_id>", "");
//				}
//				else if (line.contains("<result_code>"))
//				{
//					result_code = line.replaceAll(
//							"<result_code><\\!\\[CDATA\\[", "").replaceAll(
//									"\\]\\]></result_code>", "");
//				}
//			}
//
//			System.out.println("MDJ: return_code: " + return_code + "\n");
//			System.out.println("MDJ: prepay_id: " + prepay_id + "\n");
//			System.out.println("MDJ: result_code: " + result_code + "\n");
//
//			if ("SUCCESS".equalsIgnoreCase(return_code)
//					&& "SUCCESS".equalsIgnoreCase(result_code)
//					&& null != prepay_id)
//			{
//				noncestr = RandomStringGenerator.getRandomStringByLength(32);
//
//				String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
//				String packageString = "prepay_id=" + prepay_id;
//				String signType = "MD5";
//				ModelMap returnsignmap = new ModelMap();
//				returnsignmap.addAttribute("appId", Configure.getAppid());
//				returnsignmap.addAttribute("timeStamp", timeStamp);
//				returnsignmap.addAttribute("nonceStr", noncestr);
//				returnsignmap.addAttribute("package", packageString);
//				returnsignmap.addAttribute("signType", signType);
//
//				
//				String returnsign = Signature.getSign(returnsignmap);
//				content = "<xml>\n" + 
//				"<appid>" + Configure.getAppid() + "</appid>\n" + 
//				"<timeStamp>" + timeStamp + "</timeStamp>\n" +
//				"<nonceStr>" + noncestr + "</nonceStr>\n" + 
//				"<package>" + packageString + "</package>\n" + 
//				"<signType>" + signType + "</signType>\n" + 
//				"<signType>" + returnsign + "</signType>\n" + 
//				"</xml>\n";
//
//				System.out.print(": returnPayData xml=" + content);
//				map.addAttribute("appId", Configure.getAppid());
//				map.addAttribute("timeStamp", timeStamp);
//				map.addAttribute("nonceStr", noncestr);
//				map.addAttribute("package", packageString);
//				map.addAttribute("signType", signType);
//				map.addAttribute("paySign", returnsign);
//				map.addAttribute("orderId", state);
//			}
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//        tdOrderService.save(order);
//
//        map.addAttribute("payForm", payForm);
//
//        return "/touch/pay_order";
//    }
    
    @RequestMapping(value = "/wx_notify")
    public void wx_notify(HttpServletResponse response,HttpServletRequest request) throws IOException
    {
    	System.out.println("MDJ: 回调方法触发！\n");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

		String line = null;
		String return_code = null;
		String result_code = null;
		String noncestr = null;
		String out_trade_no = null;

		try {
			while ((line = br.readLine()) != null) {
				System.out.print("MDJ: notify" + line + "\n");

				if (line.contains("<return_code>")) {
					return_code = line.replaceAll("<return_code><\\!\\[CDATA\\[", "") .replaceAll("\\]\\]></return_code>", "");
				} else if (line.contains("<out_trade_no>")) {
					out_trade_no = line.replaceAll("<out_trade_no><\\!\\[CDATA\\[", "").replaceAll("\\]\\]></out_trade_no>", "");
				} else if (line.contains("<result_code>")) {
					result_code = line.replaceAll("<result_code><\\!\\[CDATA\\[", "").replaceAll("\\]\\]></result_code>", "");
				}
			}

			System.out.println("MDJ: notify return_code: " + return_code + "\n");
			System.out.println("MDJ: notify out_trade_no: " + out_trade_no + "\n");
			System.out.println("MDJ: notify result_code: " + result_code + "\n");

			if (return_code.contains("SUCCESS") && 
					result_code.contains("SUCCESS") && 
					null != out_trade_no)
			{
				TdOrder order = tdOrderService.findByOrderNumber(out_trade_no);

				if (null != order)
				{
					afterPaySuccess(order);
				}
				
				String content = "<xml>\n"
						+ "<result_code>SUCCESS</result_code>\n"
						+ "<return_code></return_code>\n"
						+ "</xml>\n";

				System.out.print("MDJ: return xml=" + content + "\n");

				try {
					// 把xml字符串写入响应
					byte[] xmlData = content.getBytes();

					response.setContentType("text/xml");
					response.setContentLength(xmlData.length);

					ServletOutputStream os = response.getOutputStream();
					os.write(xmlData);

					os.flush();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			br.close();
		}
    }
    
    /**
     * 订单支付成功后步骤
     * 
     * @param tdOrder
     *            订单
     */
    private void afterPaySuccess(TdOrder tdOrder) {
        if (null == tdOrder) 
        {
            return;
        }

        // 用户
        TdUser tdUser = tdUserService.findByUsername(tdOrder.getUsername());
        
        if (null == tdUser)
        {
            return;
        }       
      
        
        // 设置抢购最后时间
        if (null != tdOrder.getTypeId() && tdOrder.getTypeId().equals(3L))
        {
            Date nextTime = new Date();

            Calendar calendar = Calendar.getInstance();

            calendar.setTime(nextTime);

            calendar.add(Calendar.DATE, 30); 
            
//            tdUser.setLastFlashBuyTime(calendar.getTime());
            tdUser = tdUserService.save(tdUser);
        }
        
        if (tdOrder.getStatusId().equals(2L))
        {
            tdOrder.setPayTime(new Date()); // 设置付款时间
        }
        else
        {
//            tdOrder.setPayLeftTime(new Date()); // 设置付尾款时间
        }

      
        // 待发货
        tdOrder.setStatusId(3L);
        tdOrder = tdOrderService.save(tdOrder);
       

        
      //购买商品积分奖励
        Long totalPoints = 0L; // 总用户返利
        // 返利总额
        List<TdOrderGoods> tdOrderGoodsList = tdOrder.getOrderGoodsList();
        if (null != tdOrderGoodsList) {
            for (TdOrderGoods tog : tdOrderGoodsList) {
                 TdGoods tdGoods = tdGoodsService.findOne(tog.getGoodsId());

                 if (null != tdGoods && null != tdGoods.getReturnPoints()) {
                        totalPoints += tdGoods.getReturnPoints()
                                * tog.getQuantity();
                       
                 }
            }

            final Long totalPointsDely = totalPoints;
            final TdUser tdUserDely = tdUser;
            final TdOrder tdOrderDely = tdOrder;
            // 用户返利
            if (null != tdUser) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        // System.out.println("-------设定要指定任务--------");
                        TdUserPoint userPoint = new TdUserPoint();
                        TdOrder tdOrder1 = tdOrderService
                                .findByOrderNumber(tdOrderDely.getOrderNumber());

                        userPoint.setDetail("购买商品赠送积分");
                        userPoint.setOrderNumber(tdOrderDely.getOrderNumber());
                        userPoint.setPoint(totalPointsDely);
                        userPoint.setPointTime(new Date());
                        userPoint.setTotalPoint(tdUserDely.getTotalPoints()
                                + totalPointsDely);
                        userPoint.setUsername(tdUserDely.getUsername());

                        userPoint = tdUserPointService.save(userPoint);

                        tdUserDely.setTotalPoints(userPoint.getTotalPoint());

//                        tdOrder1.setIsReturnPoints(true);
                        tdOrderService.save(tdOrder1);
                        tdUserService.save(tdUserDely);
                    }
                }, 0);// 设定指定的时间time,

            }
        }
    }
    
 // 拼接地址
    public StringBuffer appenAddress(TdShippingAddress shippingAddress)
    {
    	StringBuffer newAddress = new StringBuffer();
    	if(null != shippingAddress)
    	{
    		if(null != shippingAddress.getProvince())
    		{
    			newAddress.append(shippingAddress.getProvince()+"-");
    		}
    		if(null != shippingAddress.getCity())
    		{
    			newAddress.append(shippingAddress.getCity()+"-");
    		}
    		if(null != shippingAddress.getDisctrict())
    		{
    			newAddress.append(shippingAddress.getDisctrict()+" ");
    		}
    		if(null != shippingAddress.getDetailAddress())
    		{
    			newAddress.append(shippingAddress.getDetailAddress());
    		}
    	}
    	return newAddress;
    }
    
    public void addVir(TdOrder tdOrder)
    {
    	Double price = 0.0; // 交易总金额
        Double postPrice = 0.0;  // 物流费
        Double aliPrice = 0.0;	// 第三方使用费
        Double servicePrice = 0.0;	// 平台服务费
        Double totalGoodsPrice = 0.0; // 商品总额
        Double realPrice = 0.0; // 商家实际收入
        Double turnPrice = 0.0; // 分销单超市返利

        price += tdOrder.getTotalPrice();
        postPrice += tdOrder.getPostPrice();
        aliPrice += tdOrder.getAliPrice();
        servicePrice +=tdOrder.getTrainService();
        totalGoodsPrice += tdOrder.getTotalGoodsPrice();
        
        
        // 添加商家余额及交易记录
        if(0==tdOrder.getTypeId())
        {
        	
        	TdDistributor distributor = tdDistributorService.findOne(tdOrder.getShopId());
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
        	
        	turnPrice = tdOrder.getTotalLeftPrice();
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

        TdSetting setting = tdSettingService.findTopBy();
        if( null != setting.getVirtualMoney())
        {
        	setting.setVirtualMoney(setting.getVirtualMoney()+servicePrice+aliPrice);
        }else{
        	setting.setVirtualMoney(servicePrice+aliPrice);
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
        record.setAliPrice(aliPrice);	// 第三方费
        record.setServicePrice(servicePrice);	// 平台费
        record.setTotalGoodsPrice(totalGoodsPrice); // 商品总价
        record.setTurnPrice(turnPrice); // 超市返利
        // 实际获利 =平台服务费+第三方费
        record.setRealPrice(servicePrice+aliPrice);
        
        tdPayRecordService.save(record);
    }
}
