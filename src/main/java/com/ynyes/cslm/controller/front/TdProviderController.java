package com.ynyes.cslm.controller.front;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdProviderGoods;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdOrderService;
import com.ynyes.cslm.service.TdProviderGoodsService;
import com.ynyes.cslm.service.TdProviderService;
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
	@RequestMapping(value="/goods/list")
	public String goodsList(Integer page,HttpServletRequest req,ModelMap map)
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
		map.addAttribute("provider", provider);
		map.addAttribute("page", page);
		map.addAttribute("provider_goods_page",
				tdProviderGoodsService.findByProviderId(provider.getId(),page,ClientConstant.pageSize));
		
		return "/client/provider_goods";
	}
	
	@RequestMapping(value="/goods/delete")
	public String providerGoodsDelete(Long id,Integer page,
			HttpServletRequest req,ModelMap map)
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
		if(null == page)
		{
			page = 0;
		}
		tdProviderGoodsService.delete(id);
		TdProvider provider = tdProviderService.findByUsername(username);
		map.addAttribute("provider", provider);
		map.addAttribute("page", page);
		map.addAttribute("provider_goods_page",
				tdProviderGoodsService.findByProviderId(provider.getId(),page,ClientConstant.pageSize));
		return "/client/provider_goods_list";
	}
	
	@RequestMapping(value="/goods/deleteCheck")
	public String deleteCheck(Long[] listId,
			Integer[] listChkId,
			Integer page,
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
		deleteCheck(listId,listChkId);
		
		return "redirect:/provider/goods/list?page="+page;
	}
	
	@RequestMapping(value="/goods/wholesaling")
	public String wholesaling(Integer page,HttpServletRequest req,ModelMap map)
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
		map.addAttribute("goods_page",
				tdGoodsService.findByIsOnSaleTrueOrderBySortIdAsc(page, 10));
		return "/client/provider_goods_onsale";
	}
	
	@RequestMapping(value="/wholesaling",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> wholesaling(Long goodsId,
			String goodsTitle,
			Double outFactoryPrice,
			Long leftNumber,
			Double shopReturnRation,
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
		provider.getGoodsList().add(proGoods);
		
		// 分销状态
		if(null == shopReturnRation || 0 ==shopReturnRation)
		{
			proGoods.setIsDistribution(false);
			proGoods.setIsAudit(true);
			tdProviderService.save(provider);
			res.put("msg","设置批发成功");
		}else{
			proGoods.setIsDistribution(true);
			proGoods.setIsAudit(false);
			tdProviderService.save(provider);
			res.put("msg", "分销商品，等待平台审核~");
		}
		return res;
	}
	
	public void deleteCheck(Long[] ids,Integer[] chkIds)
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
				tdProviderGoodsService.delete(id);
			}
		}
	}
	
	
	
	
	
	
}
