package com.ynyes.cslm.controller.front;

import java.util.Calendar;
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
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdOrderGoods;
import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdProviderGoods;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.entity.TdUserPoint;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdOrderService;
import com.ynyes.cslm.service.TdPayRecordService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.service.TdProviderGoodsService;
import com.ynyes.cslm.service.TdProviderService;
import com.ynyes.cslm.service.TdUserPointService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.util.ClientConstant;

/**
 * 批发商
 * @author libiao
 *
 */

@Controller
@RequestMapping(value="/provider")
public class TdProviderController {
	
	@Autowired
	private TdProviderService tdProviderService;
	
	@Autowired
	private TdProviderGoodsService tdProviderGoodsService;
	
	@Autowired
	private TdGoodsService tdGoodsService;
	
	@Autowired
	private TdCommonService tdCommonService;
	
	@Autowired
	private TdOrderService tdOrderService;
	
	@Autowired
	TdUserPointService tdUserPointService;
	
	@Autowired
	TdDistributorService tdDistributorService;
	
	@Autowired
	TdDistributorGoodsService tdDistributorGoodsService;
	
	@Autowired
	TdUserService tdUserService;

	@Autowired
	TdProductCategoryService tdProductCategoryService;
	
	@Autowired
	TdPayRecordService tdPayRecordService;
	
	@RequestMapping(value="/index")
	public String providerIndex(HttpServletRequest req,ModelMap map)
	{
		String username =(String)req.getSession().getAttribute("provider");
		if(null == username)
		{
			return "redirect:/login";
		}
		TdProvider provider = tdProviderService.findByUsername(username);
		if(null == provider)
		{
			return "error_404";
		}
		map.addAttribute("provider",provider);
		tdCommonService.setHeader(map, req);
		
		map.addAttribute("total_undelivered", 
				tdOrderService.countByShopIdAndTypeIdAndStatusId(provider.getId(), 1, 1));
		map.addAttribute("total_unreceived",
				tdOrderService.countByShopIdAndTypeIdAndStatusId(provider.getId(), 1, 2));
		map.addAttribute("total_finished",
				tdOrderService.countByShopIdAndTypeIdAndStatusId(provider.getId(), 1, 3));
		map.addAttribute("provider_order_page",
				tdOrderService.findByShopIdAndTypeId(provider.getId(),1, 0,ClientConstant.pageSize ));
		
		return "/client/provider_index";
	}
	
	@RequestMapping(value="/order/list/{statusId}")
	public String orderList(@PathVariable Integer statusId,
			Integer statusid,
			String keywords,
			Integer page,
			Integer timeId,
			HttpServletRequest req,
			ModelMap map)
	{
		String username =(String)req.getSession().getAttribute("provider");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == page)
		{
			page = 0;
		}
		if(null == statusId)
		{
			statusId= 0;
		}
		if (null == timeId) {
            timeId = 0;
        }
		if(null != statusid){
        	statusId = statusid;
        }
		TdProvider provider = tdProviderService.findByUsername(username);
		map.addAttribute("provider",provider);
		map.addAttribute("status_id", statusId);
		map.addAttribute("time_id",timeId);
		
		tdCommonService.setHeader(map, req);
		
		Page<TdOrder> orderPage =null;
		if (timeId.equals(0)) {
            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndSearch(
                    		provider.getId(),1, keywords, page, ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeId(provider.getId(),1, page,
                            ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndSearch(provider.getId(),1,statusId, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndStatusId(
                    		provider.getId(),1,statusId, page, ClientConstant.pageSize);
                }
            }
        } else if (timeId.equals(1)) {
            Date cur = new Date();
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -1);// 月份减一
            Date time = calendar.getTime();

            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
                                    1,time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndTimeAfter(
                    		provider.getId(),1,time, page, ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                            		provider.getId(),1, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
                                   1, statusId, time, page,
                                    ClientConstant.pageSize);
                }
            }
        } else if (timeId.equals(3)) {
            Date cur = new Date();
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -3);// 月份减一
            Date time = calendar.getTime();

            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
                                   1, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndTimeAfter(
                    		provider.getId(),1, time, page, ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                            		provider.getId(),1, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
                                    1,statusId, time, page,
                                    ClientConstant.pageSize);
                }
            }
        } else if (timeId.equals(6)) {
            Date cur = new Date();
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -6);// 月份减一
            Date time = calendar.getTime();

            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
                                    1,time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndTimeAfter(
                    		provider.getId(),1, time, page, ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                            		provider.getId(),1, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
                                   1, statusId, time, page,
                                    ClientConstant.pageSize);
                }
            }
        } else if (timeId.equals(12)) {
            Date cur = new Date();
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.YEAR, -1);// 减一
            Date time = calendar.getTime();

            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
                                    1,time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndTimeAfter(
                    		provider.getId(),1, time, page, ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                            		provider.getId(),1, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
                                    1,statusId, time, page,
                                    ClientConstant.pageSize);
                }
            }
        }
		map.addAttribute("order_page",orderPage);
		return "/client/provider_order_list";
	}

	@RequestMapping(value="/disOrder/list/{statusId}")
	public String disOrder(@PathVariable Integer statusId,
			Integer statusid,
			String keywords,
			Integer page,
			Integer timeId,
			HttpServletRequest req,
			ModelMap map)
	{
		String username =(String)req.getSession().getAttribute("provider");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == page)
		{
			page = 0;
		}
		if(null == statusId)
		{
			statusId= 0;
		}
		if (null == timeId) {
            timeId = 0;
        }
		if(null != statusid){
        	statusId = statusid;
        }
		TdProvider provider = tdProviderService.findByUsername(username);
		map.addAttribute("provider",provider);
		map.addAttribute("status_id", statusId);
		map.addAttribute("time_id",timeId);
		
		tdCommonService.setHeader(map, req);
		
		Page<TdOrder> orderPage =null;
		if (timeId.equals(0)) {
            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndSearch(
                    		provider.getId(),2, keywords, page, ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByProviderIdAndTypeId(provider.getId(),2, page,
                            ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByProviderIdAndTypeIdAndStatusIdAndSearch(provider.getId(),2,statusId, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndStatusId(
                    		provider.getId(),1,statusId, page, ClientConstant.pageSize);
                }
            }
        } else if (timeId.equals(1)) {
            Date cur = new Date();
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -1);// 月份减一
            Date time = calendar.getTime();

            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
                                    2,time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
                    		provider.getId(),2,time, page, ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                            		provider.getId(),2, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService
                            .findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
                                   2, statusId, time, page,
                                    ClientConstant.pageSize);
                }
            }
        } else if (timeId.equals(3)) {
            Date cur = new Date();
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -3);// 月份减一
            Date time = calendar.getTime();

            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
                                   2, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
                    		provider.getId(),2, time, page, ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                            		provider.getId(),2, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService
                            .findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
                                    2,statusId, time, page,
                                    ClientConstant.pageSize);
                }
            }
        } else if (timeId.equals(6)) {
            Date cur = new Date();
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -6);// 月份减一
            Date time = calendar.getTime();

            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
                                    2,time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
                    		provider.getId(),2, time, page, ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                            		provider.getId(),2, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService
                            .findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
                                   2, statusId, time, page,
                                    ClientConstant.pageSize);
                }
            }
        } else if (timeId.equals(12)) {
            Date cur = new Date();
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.YEAR, -1);// 减一
            Date time = calendar.getTime();

            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
                                    2,time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
                    		provider.getId(),2, time, page, ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                            		provider.getId(),2, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService
                            .findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
                                    2,statusId, time, page,
                                    ClientConstant.pageSize);
                }
            }
        }
		map.addAttribute("order_page",orderPage);
		return "/client/provider_disOrder_list";
	}
	
	@RequestMapping(value="/order")
	public String orderDetail(Long id,HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("provider");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == id)
		{
			return "/client/error_404";
		}
		tdCommonService.setHeader(map, req);
		map.addAttribute("provider", tdProviderService.findByUsername(username));
		map.addAttribute("order",tdOrderService.findOne(id));
		return "/client/provider_order_detail";
	}
	
	@RequestMapping(value="/disOrder")
	public String disOrderDetail(Long id,HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("provider");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == id)
		{
			return "/client/error_404";
		}
		tdCommonService.setHeader(map, req);
		map.addAttribute("provider", tdProviderService.findByUsername(username));
		map.addAttribute("order",tdOrderService.findOne(id));
		return "/client/provider_disorder_detail";
	}
	
	@RequestMapping(value="/disorder/param/edit")
	@ResponseBody
	public Map<String, Object> disparamEdit(String orderNumber,
			String type,
			ModelMap map,
			HttpServletRequest req)
	{
		Map<String, Object> res =new HashMap<>();
		res.put("code",1);
		String username = (String)req.getSession().getAttribute("provider");
		
		if (null == username)
        {
            res.put("message", "请重新登录");
            return res;
        }
		
		if (null != orderNumber && !orderNumber.isEmpty() && null != type && !type.isEmpty())
        {
			TdOrder order = tdOrderService.findByOrderNumber(orderNumber);
			if(type.equalsIgnoreCase("orderConfirm"))
			{
				if(order.getStatusId().equals(1L))
				{
					order.setStatusId(2L);
					order.setDeliveryTime(new Date());
				}
			}
			else if(type.equalsIgnoreCase("orderPay"))
			{
				if(order.getStatusId().equals(2L))
				{
					TdUser tdUser = tdUserService.findByUsername(order.getUsername());
	            	TdDistributor distributor = tdDistributorService.findOne(order.getShopId());
	            	TdProvider provider = tdProviderService.findOne(order.getProviderId());
	            	
	            	List<TdOrderGoods> tdOrderGoodsList = order.getOrderGoodsList();
	            	
	            	 Long totalPoints = 0L;
	                 Double totalCash = 0.0;
	                 Double platformService = 0.0; // 平台服务费
	                 Double trainService = 0.0;	// 分销返利
	                
	                 // 返利总额
	                 if (null != tdOrderGoodsList) {
	                     for (TdOrderGoods tog : tdOrderGoodsList) {
	                         if (0 == tog.getGoodsSaleType()) // 正常销售
	                         {
	                             TdProviderGoods providerGoods = tdProviderGoodsService.findByProviderIdAndGoodsId(order.getProviderId(), tog.getGoodsId());
	                        	 TdDistributorGoods disGoods = tdDistributorGoodsService.findByDistributorIdAndGoodsIdAndIsOnSale(distributor.getId(), tog.getGoodsId(), true);
	                        	 TdGoods tdGoods = tdGoodsService.findOne(tog.getGoodsId());

	                             if (null != disGoods && null != disGoods.getReturnPoints()) {
	                                 totalPoints += disGoods.getReturnPoints(); // 赠送积分

//	                                 if (null != tdGoods.getShopReturnRation()) {
//	                                     totalCash += tdGoods.getCostPrice()
//	                                             * tdGoods.getShopReturnRation();
//	                                 }
	                             }
	                             if (null != disGoods && null != tdGoods.getPlatformServiceReturnRation()) {
	                             	platformService += tog.getPrice() * tdGoods.getPlatformServiceReturnRation();
	         					}
	                             if (null != providerGoods && null != providerGoods.getShopReturnRation()) {
	                             	trainService += tog.getPrice() * providerGoods.getShopReturnRation(); 
	         					}
	                         }
	                     }
	                  // 用户返利
	                     if (null != tdUser) {
	                         TdUserPoint userPoint = new TdUserPoint();

	                         userPoint.setDetail("购买商品赠送积分");
	                         userPoint.setOrderNumber(order.getOrderNumber());
	                         userPoint.setPoint(totalPoints);
	                         userPoint.setPointTime(new Date());
	                         userPoint.setTotalPoint(tdUser.getTotalPoints() + totalPoints);
	                         userPoint.setUsername(tdUser.getUsername());

	                         userPoint = tdUserPointService.save(userPoint);

	                         tdUser.setTotalPoints(userPoint.getTotalPoint());

	                         tdUserService.save(tdUser);
	                     }
	                     order.setRebate(order.getTotalGoodsPrice()-platformService);// 设置订单超市收益
	                     order.setPlatformService(platformService);// 设置订单平台服务费
	                     order.setTrainService(trainService);// 设置订单培训服务费
	                     order = tdOrderService.save(order);
	                  //超市入账
	                   if(null != distributor)
	                   {
	                	   distributor.setVirtualMoney(distributor.getVirtualMoney()+trainService);
	                       tdDistributorService.save(distributor);
	                   }
	                   // 批发商入帐
	                   if(null != provider){
	                	   provider.setVirtualMoney(provider.getVirtualMoney()+order.getTotalGoodsPrice()-platformService-trainService);
	                	   tdProviderService.save(provider);
	                   }
	                 }
					order.setStatusId(3L);
					order.setFinishTime(new Date());
				}
			}
			// 确认发货
            else if (type.equalsIgnoreCase("orderPayLeft"))
            {
            	if(order.getStatusId().equals(3L))
            	{
            		order.setStatusId(4L);
            		order.setDeliveryTime(new Date());
            	}
            }
			 tdOrderService.save(order);
	         res.put("code", 0);
	         res.put("message", "修改成功!");
	         return res;
        }
		res.put("message", "参数错误!");
		return res;
	}
	
	
	@RequestMapping(value="/order/param/edit")
	@ResponseBody
	public Map<String, Object> paramEdit(String orderNumber,
			String type,
			ModelMap map,
			HttpServletRequest req)
	{
		Map<String, Object> res =new HashMap<>();
		res.put("code",1);
		String username = (String)req.getSession().getAttribute("provider");
		
		if (null == username)
        {
            res.put("message", "请重新登录");
            return res;
        }
		
		if (null != orderNumber && !orderNumber.isEmpty() && null != type && !type.isEmpty())
        {
			TdOrder order = tdOrderService.findByOrderNumber(orderNumber);
			if(type.equalsIgnoreCase("orderPayLeft"))
			{
				if(order.getStatusId().equals(1L))
				{
					order.setStatusId(2L);
					order.setDeliveryTime(new Date());
				}
			}
			else if(type.equalsIgnoreCase("orderService"))
			{
				if(order.getStatusId().equals(2L))
				{
					order.setStatusId(3L);
					order.setFinishTime(new Date());
				}
			}
			 tdOrderService.save(order);
	         res.put("code", 0);
	         res.put("message", "修改成功!");
	         return res;
        }
		res.put("message", "参数错误!");
		return res;
	}
	
	
	@RequestMapping(value="/password")
	public String distributorPassword(HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("provider");
		if(null == username)
		{
			return "redirect:/login";
		}
		tdCommonService.setHeader(map, req);
		map.addAttribute("provider",tdProviderService.findByUsername(username));
		return "/client/provider_change_password";
	}
	
	@RequestMapping(value="/password", method = RequestMethod.POST)
	public String distributorPassword(String oldPassword,String newPassword,
			HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("provider");
		if(null == username)
		{
			return "redirect:/login";
		}
		tdCommonService.setHeader(map, req);
		TdProvider provider = tdProviderService.findByUsername(username);
		 if (provider.getPassword().equals(oldPassword)) 
		 {
			 provider.setPassword(newPassword);
		 }
		
		map.addAttribute("provider",tdProviderService.save(provider));
		
		return "redirect:/provider/password";
	}
	
	/**
	 *  批发商品列表
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/goods/list/{isSale}")
	public String goodsList(@PathVariable Boolean isSale,
			Integer page,
			Long categoryId,
//			String isDistribution,
			String keywords,
			HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("provider");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == page)
		{
			page = 0;
		}
		tdCommonService.setHeader(map, req);
		TdProvider provider = tdProviderService.findByUsername(username);

		// 参数注回
		map.addAttribute("isOnSale", isSale);
		map.addAttribute("provider", provider);
		map.addAttribute("page", page);
		map.addAttribute("keywords", keywords);
		map.addAttribute("categoryId", categoryId);
//		map.addAttribute("distribution",isDistribution); 
		
		map.addAttribute("category_list",tdProductCategoryService.findAll());
		
		if(null ==categoryId)
		{
//			if("isDistribution".equalsIgnoreCase(isDistribution))
//			{
//				if(null == keywords){
//					map.addAttribute("provider_goods_page",
//							tdProviderGoodsService.findByProviderIdAndIsDistribution(provider.getId(),true, page, ClientConstant.pageSize));
//				}else{
//					map.addAttribute("provider_goods_page",
//							tdProviderGoodsService.searchAndProviderIdAndIsDistribution(provider.getId(), keywords, true, page, ClientConstant.pageSize));
//				}
//			}
//			else if("isNotDistribution".equalsIgnoreCase(isDistribution))
//			{
//				if(null == keywords){
//					map.addAttribute("provider_goods_page",
//							tdProviderGoodsService.findByProviderIdAndIsDistribution(provider.getId(),false, page, ClientConstant.pageSize));
//				}else{
//					map.addAttribute("provider_goods_page",
//							tdProviderGoodsService.searchAndProviderIdAndIsDistribution(provider.getId(), keywords, false, page, ClientConstant.pageSize));
//				}
//			}
//			else
//			{
				if(null == keywords){
					map.addAttribute("provider_goods_page",
							tdProviderGoodsService.findByProviderIdAndIsOnSale(provider.getId(),isSale,page,ClientConstant.pageSize));
				}else{
					map.addAttribute("provider_goods_page",
							tdProviderGoodsService.searchAndProviderIdAndKeywordsAndIsOnSale(provider.getId(), keywords, isSale,page, ClientConstant.pageSize));
				}
//			}
		}
		else
		{
//			if("isDistribution".equalsIgnoreCase(isDistribution))
//			{
//				if(null == keywords){
//					map.addAttribute("provider_goods_page",
//							tdProviderGoodsService.findByProviderIdAndCategoryIdAndIsDistribution(provider.getId(), categoryId, true, page, ClientConstant.pageSize));
//				}else{
//					map.addAttribute("provider_goods_page",
//							tdProviderGoodsService.searchAndProviderIdAndCategoryIdAndIsDistribution(provider.getId(), keywords, categoryId, true, page, ClientConstant.pageSize));
//				}
//			}
//			else if("isNotDistribution".equalsIgnoreCase(isDistribution))
//			{
//				if(null == keywords){
//					map.addAttribute("provider_goods_page",
//							tdProviderGoodsService.findByProviderIdAndCategoryIdAndIsDistribution(provider.getId(), categoryId, false, page, ClientConstant.pageSize));
//				}else{
//					map.addAttribute("provider_goods_page",
//							tdProviderGoodsService.searchAndProviderIdAndCategoryIdAndIsDistribution(provider.getId(), keywords, categoryId, false, page, ClientConstant.pageSize));
//				}
//			}
//			else
//			{
				if(null == keywords){
					map.addAttribute("provider_goods_page",
							tdProviderGoodsService.findByProviderIdAndCategoryIdAndIsOnSale(provider.getId(), categoryId,isSale, page, ClientConstant.pageSize));
				}else{
					map.addAttribute("provider_goods_page",
							tdProviderGoodsService.searchAndProviderIdAndCategoryIdAndKeywordsAndIsOnSale(provider.getId(), categoryId, keywords,isSale, page, ClientConstant.pageSize));
				}
//			}
		}
		
		return "/client/provider_goods";
	}
	
	//      批发/取消批发
	@RequestMapping(value="/goods/onsale/{pgId}")
	public String providerGoodsDelete(@PathVariable Long pgId,
			Boolean type,Integer page,
			HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("provider");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == pgId)
		{
			return "/client/error_404";
		}
		if(null == page)
		{
			page = 0;
		}
		map.addAttribute("page", page);
		map.addAttribute("type",type);
		TdProviderGoods providerGoods = tdProviderGoodsService.findOne(pgId);
		TdProvider provider = tdProviderService.findByUsername(username);
		
		if(type)
		{
			providerGoods.setIsOnSale(type);
			tdProviderGoodsService.save(providerGoods);
			map.addAttribute("provider_goods_page",
					tdProviderGoodsService.findByProviderIdAndIsOnSale(provider.getId(), false, page,ClientConstant.pageSize));
		}else{
			providerGoods.setIsOnSale(type);
			tdProviderGoodsService.save(providerGoods);
			map.addAttribute("provider_goods_page",
					tdProviderGoodsService.findByProviderIdAndIsOnSale(provider.getId(), true, page,ClientConstant.pageSize));
		}
		
		map.addAttribute("provider", provider);
		return "/client/provider_goods_list";
	}
	
	// 删除
	@RequestMapping(value="/goods/delete/{pgId}")
	public String deleteGoods(@PathVariable Long pgId,
			Boolean type,Integer page,
			HttpServletRequest req,ModelMap map){
		String username = (String)req.getSession().getAttribute("provider");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == pgId)
		{
			return "/client/error_404";
		}
		if(null == page)
		{
			page = 0;
		}
		
		tdProviderGoodsService.delete(pgId);
		TdProvider provider = tdProviderService.findByUsername(username);
		map.addAttribute("provider_goods_page",
				tdProviderGoodsService.findByProviderIdAndIsOnSale(provider.getId(),type, page, ClientConstant.pageSize));
		
		
		return "/client/provider_goods_list";
	}
	
	@RequestMapping(value="/goods/checkAll/{type}")
	public String deleteCheck(@PathVariable Boolean type,
			Long[] listId,
			Integer[] listChkId,
			Integer page,
			Long categoryId,String keywords,
			HttpServletRequest req,
			ModelMap map){
		String username = (String)req.getSession().getAttribute("provider");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == page)
		{
			page = 0;
		}
		if(type){
		
			ChangeAll(false,listId,listChkId);
		}else{
			ChangeAll(true,listId,listChkId);
		}
		
		if(null == categoryId){
			return "redirect:/provider/goods/list/"+type+"?page="+page+"&keywords="+keywords;
		}else{
			return "redirect:/provider/goods/list/"+type+"?page="+page+"&categoryId="+categoryId+"&keywords="+keywords;
		}
	}
	
	@RequestMapping(value="/goods/wholesaling")
	public String wholesaling(Integer page,Long categoryId,String keywords,HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("provider");
		if(null == username)
		{
			return "redirect:/login";
		}
		tdCommonService.setHeader(map, req);
		if(null == page)
		{
			page = 0;
		}
		
		map.addAttribute("page", page);
		map.addAttribute("categoryId",categoryId);
		map.addAttribute("keywords",keywords);
		
		map.addAttribute("category_list", tdProductCategoryService.findAll());
		if(null == categoryId){
			if(null == keywords)
			{
				map.addAttribute("goods_page",
						tdGoodsService.findByIsOnSaleTrueOrderBySortIdAsc(page, 10));
			}
			else
			{
				map.addAttribute("goods_page",
						tdGoodsService.searchAndIsOnSaleTrueOrderBySortIdAsc(keywords, page, 10));
			}
		}
		else
		{
			if(null == keywords)
			{
				map.addAttribute("goods_page", 
						tdGoodsService.findByCategoryIdAndIsOnSaleTrue(categoryId, page, 10));
			}
			else
			{
				map.addAttribute("goods_page", 
						tdGoodsService.searchAndFindByCategoryIdAndIsOnSaleTrueOrderBySortIdAsc(keywords, categoryId, page, 10));
			}
		}
		
		return "/client/provider_goods_onsale";
	}
	
	@RequestMapping(value="/wholesaling",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> wholesaling(Long goodsId,
			String goodsTitle,
			Double outFactoryPrice,
			Long leftNumber,
			HttpServletRequest req)
	{
		Map<String,Object> res =new HashMap<>();
		String username =(String)req.getSession().getAttribute("provider");
		if(null == username )
		{
			res.put("msg", "请先登录！");
			return res;
		}
		if(null ==goodsId)
		{
			res.put("msg","选择的商品无效！");
			return res;
		}
		TdProvider provider = tdProviderService.findByUsername(username);
		TdProviderGoods proGoods = tdProviderGoodsService.findByProviderIdAndGoodsId(provider.getId(), goodsId);
		TdGoods goods = tdGoodsService.findOne(goodsId);
		
		if(null == proGoods)
		{
			proGoods=new TdProviderGoods();
			proGoods.setGoodsId(goods.getId());
			proGoods.setGoodsTitle(goodsTitle);
			proGoods.setSubGoodsTitle(goods.getSubTitle());
			proGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
			proGoods.setOutFactoryPrice(outFactoryPrice);
			proGoods.setLeftNumber(leftNumber);
			proGoods.setOnSaleTime(new Date());
			proGoods.setIsOnSale(true);
			proGoods.setCode(goods.getCode());
			proGoods.setCategoryId(goods.getCategoryId());
			proGoods.setCategoryIdTree(goods.getCategoryIdTree());
		}
		else
		{
			proGoods.setGoodsTitle(goodsTitle);
			proGoods.setLeftNumber(proGoods.getLeftNumber()+leftNumber);
			proGoods.setOutFactoryPrice(outFactoryPrice);
			proGoods.setOnSaleTime(new Date());
			proGoods.setIsOnSale(true);
		}
		proGoods.setProviderTitle(provider.getTitle());
		
		// 分销状态
//		if(null == shopReturnRation || 0 ==shopReturnRation)
//		{
			proGoods.setShopReturnRation(new Double(0));
//			proGoods.setIsDistribution(false);
			proGoods.setIsAudit(true);
			provider.getGoodsList().add(proGoods);
			tdProviderService.save(provider);
			res.put("msg","设置批发成功");
//		}else{
//			proGoods.setShopReturnRation(shopReturnRation);
//			proGoods.setIsDistribution(true);
//			proGoods.setIsAudit(false);
//			proGoods.setShopReturnRation(shopReturnRation);
//			provider.getGoodsList().add(proGoods);
//			tdProviderService.save(provider);
//			res.put("msg", "分销商品，等待平台审核~");
//		}
		return res;
	}
	
	 /**
     * 交易记录
     * 
     */
    @RequestMapping(value="/pay/record")
    public String payRecord(Integer page,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("provider");
    	if (null == username) {
            return "redirect:/login";
        }
    	if(null == page ){
    		page = 0;
    	}
    	map.addAttribute("page", page);
    	tdCommonService.setHeader(map, req);
    	
    	TdProvider provider = tdProviderService.findByUsername(username);
    	map.addAttribute("pay_record_page",
    				tdPayRecordService.findByProviderId(provider.getId(), page, ClientConstant.pageSize));
    	return "/client/provider_record";
    }
	
	@RequestMapping(value = "/edit/ImgUrl", method = RequestMethod.POST)
    @ResponseBody
    public String saveHeadPortrait(String imgUrl,HttpServletRequest rep)
    {
    	String username = (String)rep.getSession().getAttribute("provider");
    	if (null == username) {
            return "redirect:/login";
        }
        TdProvider provider = tdProviderService.findByUsername(username);
        provider.setImageUri(imgUrl);
        tdProviderService.save(provider);
    	return "client/provider_index";
    }
	
	
	public void ChangeAll(Boolean isOnSale,Long[] ids,Integer[] chkIds)
	{
		if (null == ids || null == chkIds
                || ids.length < 1 || chkIds.length < 1)
        {
            return;
        }
		for (int chkId : chkIds) {
			if(chkId >=0 && ids.length > chkId)
			{
				Long id = ids[chkId];
				TdProviderGoods providerGoods = tdProviderGoodsService.findOne(id);
				if(null != providerGoods){
					providerGoods.setIsOnSale(isOnSale);
					tdProviderGoodsService.save(providerGoods);
				}
			}
		}
	}
	
	
	
	
	
	
}
