package com.ynyes.cslm.controller.front;

import java.util.ArrayList;
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

import com.ynyes.cslm.entity.TdArticle;
import com.ynyes.cslm.entity.TdArticleCategory;
import com.ynyes.cslm.entity.TdDemand;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdOrderGoods;
import com.ynyes.cslm.entity.TdPayRecord;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdProviderGoods;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.entity.TdUserPoint;
import com.ynyes.cslm.entity.TdUserReturn;
import com.ynyes.cslm.service.TdArticleCategoryService;
import com.ynyes.cslm.service.TdArticleService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDemandService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdOrderService;
import com.ynyes.cslm.service.TdPayRecordService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.service.TdProviderGoodsService;
import com.ynyes.cslm.service.TdProviderService;
import com.ynyes.cslm.service.TdUserPointService;
import com.ynyes.cslm.service.TdUserReturnService;
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
	
	@Autowired
	TdDemandService tdDemandService;
	
	@Autowired
	TdArticleCategoryService tdArticleCategoryService;
	
	@Autowired
	TdArticleService tdArticleService;
	
	@Autowired
	TdUserReturnService tdUserReturnService;
	
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
	
//	@RequestMapping(value="/disOrder")
//	public String disOrderDetail(Long id,HttpServletRequest req,ModelMap map)
//	{
//		String username = (String)req.getSession().getAttribute("provider");
//		if(null == username)
//		{
//			return "redirect:/login";
//		}
//		if(null == id)
//		{
//			return "/client/error_404";
//		}
//		tdCommonService.setHeader(map, req);
//		map.addAttribute("provider", tdProviderService.findByUsername(username));
//		map.addAttribute("order",tdOrderService.findOne(id));
//		return "/client/provider_disorder_detail";
//	}
	
	
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
		if(null == categoryId)
		{
			categoryId = 0L;
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
		// 所有分类Id
		List<Long> list = tdProviderGoodsService.findByProviderId(provider.getId());
		
		List<TdProductCategory> category_list = new ArrayList<>();
		
		if(null != list)
		{
			for (int i = 0; i < list.size(); i++) {
				category_list.add(tdProductCategoryService.findOne(Long.parseLong(list.get(i)+"")));
			}
		}// 所有该批发商有的分类
		map.addAttribute("category_list",category_list);
		
		if(null ==categoryId || categoryId==0)
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
	
	@RequestMapping(value="/goods/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> edit(Long goodsId,Integer page,
					Double outFactoryPrice,Long leftNumber,HttpServletRequest req,ModelMap map)
	{
		Map<String,Object> res = new HashMap<>();
		res.put("code", 0);
		
		String username =(String)req.getSession().getAttribute("provider");
		if(null == username)
		{
			res.put("msg", "请重新登录");
			return res;
		}
		if(null == goodsId)
		{
			res.put("msg", "参数错误");
			return res;
		}
		if(null == leftNumber || leftNumber <=0)
		{
			res.put("msg", "库存输入错误");
			return res;
		}
		TdProviderGoods providerGoods = tdProviderGoodsService.findOne(goodsId);
		TdProvider provider = tdProviderService.findByUsername(username);
		
		providerGoods.setOutFactoryPrice(outFactoryPrice);
		providerGoods.setLeftNumber(leftNumber);
		providerGoods.setIsOnSale(true);
		tdProviderGoodsService.save(providerGoods);
		
		res.put("msg", "设置批发成功");
		res.put("code", 1);
		
		return res;
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
//			proGoods.setShopReturnRation(new Double(0));
//			proGoods.setIsDistribution(false);
//			proGoods.setIsAudit(true);
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
    public String payRecord(Integer page,String cont, HttpServletRequest req,ModelMap map){
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
    	if(null == cont || "".equals(cont)){
    		map.addAttribute("pay_record_page",
    				tdPayRecordService.findByProviderId(provider.getId(), page, ClientConstant.pageSize));
    	}else{
    		map.addAttribute("pay_record_page",
    				tdPayRecordService.searchByProviderId(provider.getId(),cont, page, ClientConstant.pageSize));
    	}
    	return "/client/provider_record";
    }
    
    /**
     * 商品需求
     * 
     */
    @RequestMapping(value="/goods/need")
    public String noodGoods(HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("provider");
    	if (null == username) {
            return "redirect:/login";
        }
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("category_list", tdProductCategoryService.findAll());
    	return "/client/provider_goods_need";
    }
    
    @RequestMapping(value="goods/need",method=RequestMethod.POST)
    public String needGoods(TdDemand demand,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("provider");
    	if (null == username) {
            return "redirect:/login";
        }
    	tdCommonService.setHeader(map, req);
    	TdProvider provider = tdProviderService.findByUsername(username);
    	
    	if(null == demand){
    		return "/client/error_404";
    	}
    	if(null != demand.getCategoryId()){
    		TdProductCategory category = tdProductCategoryService.findOne(demand.getCategoryId());
    		demand.setCategory(category.getTitle());
    	}
    	demand.setName(provider.getTitle());
    	demand.setMobile(provider.getMobile());
    	demand.setTime(new Date());
    	demand.setStatusId(0L);
    	
    	tdDemandService.save(demand);
    	
    	return "/client/provider_end_need";
    }
    
    /**
     * 平台服务
     * 
     */
    @RequestMapping(value="/info/{mid}")
    public String info(@PathVariable Long mid,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("provider");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	List<TdArticleCategory> catList = tdArticleCategoryService.findByMenuId(mid);
    	
    	tdCommonService.setHeader(map, req);
// 	    map.addAttribute("td_art_list",catList);
 	    map.addAttribute("mid", mid);
 	    
 	    map.addAttribute("new_list",tdArticleService.findByMenuId(mid));
 	   if (null != catList && catList.size() > 0) 
 	   {
	   		for (int i = 0; i < catList.size(); i++) {
				TdArticleCategory tdCat=catList.get(i);
				map.addAttribute("news_page", tdArticleService
   						.findByMenuIdAndCategoryIdAndIsEnableOrderByIdDesc(mid,
   								tdCat.getId(), 0, ClientConstant.pageSize).getContent());
				
			}
 	   }
 	   
 	   return "/client/provider_info_list";
   }
    @RequestMapping(value="/content/{newId}")
    public String newContent(@PathVariable Long newId,Long mid,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("provider");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	tdCommonService.setHeader(map, req);
    	if(null == newId){
    		return "/client/error_404";
    	}
    	map.addAttribute("mid",mid);

    	TdArticle tdArticle = tdArticleService.findOne(newId);
    	if(null != tdArticle){
    		map.addAttribute("info",tdArticle);
    	}
    	TdArticle article = tdArticleService.findPrevOne(newId, tdArticle.getCategoryId(), tdArticle.getMenuId());
    	
    	if(null != article){
    		map.addAttribute("prev_info",article);
    	}
    	TdArticle tdarticle =tdArticleService.findNextOne(newId, tdArticle.getCategoryId(), tdArticle.getMenuId());
    	if(null != tdarticle){
    		map.addAttribute("next_info",tdarticle);
    	}
    	return "/client/provider_info";
    }
    
    @RequestMapping(value="/return/list")
    public String returnList(Integer page,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("provider");
    	if (null == username) {
            return "redirect:/login";
        }
    	if(null == page){
    		page = 0;
    	}
    	
    	tdCommonService.setHeader(map, req);
    	TdProvider provider = tdProviderService.findByUsername(username);
    	
    	map.addAttribute("return_page",
    			tdUserReturnService.findByShopIdAndType(provider.getId(), 2L, page, ClientConstant.pageSize));
    	
    	return "/client/provider_return_list";
    }
    
    @RequestMapping(value="/return/param/edit")
    @ResponseBody
    public Map<String,Object> returnedit(Long id,HttpServletRequest req){
    	Map<String,Object> res =new HashMap<>();
    	res.put("code",1);
		String username = (String)req.getSession().getAttribute("provider");
		
		if (null == username)
        {
            res.put("message", "请重新登录！");
            return res;
        }
		if(null != id)
		{
			TdUserReturn tdReturn = tdUserReturnService.findOne(id);
			if(null != tdReturn && tdReturn.getStatusId()==0)
			{
				tdReturn.setStatusId(1L);
				tdUserReturnService.save(tdReturn);
				res.put("message", "已处理此次退货！");
				res.put("code", 0);
				return res;
			}
		}
		
		res.put("message", "参数错误！");
    	return res;
    }
    
    /**
     * 账号管理
     * 
     */
    @RequestMapping(value="/account")
    public String account(HttpServletRequest req,ModelMap map, Integer page){
    	String username = (String)req.getSession().getAttribute("provider");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == page){
			page = 0;
		}
		
		map.addAttribute("page", page);
    	tdCommonService.setHeader(map, req);
    	
    	TdProvider provider = tdProviderService.findByUsername(username);
    	
    	map.addAttribute("provider", provider);
    	map.addAttribute("pay_record_page",
    				tdPayRecordService.findByProviderId(provider.getId(), page, ClientConstant.pageSize));
    	
    	return "/client/provider_account";
    }
    
    /**
     * 充值
     * 
     */
    @RequestMapping(value="/topup1")
    public String topupOne(HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("provider");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("provider",
    				tdProviderService.findByUsername(username));
    	
    	return "/client/provider_top_one";
    }
    
    /**
     * 提现
     * 
     */
    @RequestMapping(value="/draw1")
    public String withdraw(HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("provider");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("provider", tdProviderService.findByUsername(username));
    	
    	return "/client/provider_draw_one";
    }
    
    @RequestMapping(value="/topup2",method=RequestMethod.POST)
    public String topupTwo(HttpServletRequest req,ModelMap map,TdPayRecord record){
    	String username = (String)req.getSession().getAttribute("provider");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("provider",
    				tdProviderService.findByUsername(username));
    	
    	map.addAttribute("record", record);
    	
    	return "/client/distributor_top_end";
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
