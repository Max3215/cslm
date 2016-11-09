package com.ynyes.cslm.controller.front;

import static org.apache.commons.lang3.StringUtils.leftPad;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdAdType;
import com.ynyes.cslm.entity.TdPointGoods;
import com.ynyes.cslm.entity.TdPointOrder;
import com.ynyes.cslm.entity.TdShippingAddress;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.service.TdAdService;
import com.ynyes.cslm.service.TdAdTypeService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdPointGoodsService;
import com.ynyes.cslm.service.TdPointOrderService;
import com.ynyes.cslm.service.TdShippingAddressService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.util.ClientConstant;

/**
 * 
 * @author Max
 * 积分兑换列表及兑换流程
 * 
 */
@Controller
public class TdPointGoodsController {

	
	@Autowired
	private TdCommonService tdCommonService;
	
	@Autowired
	private TdPointGoodsService tdPointGoodsService;
	
	@Autowired
	private TdUserService tdUserService;
	
	@Autowired
	private TdAdTypeService tdAdTypeService;
	
	@Autowired
	private TdAdService tdAdService;
	
	@Autowired
	private TdDistributorGoodsService tdDistributorGoodsService;
	
	@Autowired
	private TdShippingAddressService tdShippingAddressService;
	
	@Autowired
	private TdPointOrderService tdPointOrderService;
	
	/**
	 * 兑换商品列表
	 * 
	 */
	@RequestMapping("/pointGoods/list")
	public String list(Integer page,HttpServletRequest req,ModelMap map){
		String username =(String)req.getSession().getAttribute("username");
		
		if(null !=username){
			map.addAttribute("user", tdUserService.findByUsernameAndIsEnabled(username));
		}
		
    	map.addAttribute("hot_sale_list",tdDistributorGoodsService.findByIsOnSaleTrueBySoldNumberDesc(0,10).getContent());
		
		 // 列表页轮播广告
        TdAdType adType = tdAdTypeService.findByTitle("积分兑换列表页广告");

        if (null != adType) {
            map.addAttribute("point_ad_list", tdAdService
                    .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),0L));
        }
		
		tdCommonService.setHeader(map, req);
		
		if(null == page){
			page = 0;
		}
		
		PageRequest pageRequest = new PageRequest(page, ClientConstant.pageSize, new Sort(Direction.DESC, "onSaleTime"));
		
		map.addAttribute("goods_page", tdPointGoodsService.findAll(null, true, pageRequest));
		
		return "/client/point/goood_list";
	}
	
	/**
	 * 详情
	 * 
	 */
	@RequestMapping(value="/point/goods/detail")
	public String goodsDetail(Long id,HttpServletRequest req,ModelMap map){
		String username =(String)req.getSession().getAttribute("username");
		
		if(null !=username){
			map.addAttribute("user", tdUserService.findByUsernameAndIsEnabled(username));
		}
		
		tdCommonService.setHeader(map, req);
		
		map.addAttribute("hot_sale_list",tdDistributorGoodsService.findByIsOnSaleTrueBySoldNumberDesc(0,10).getContent());
		
		map.addAttribute("goods", tdPointGoodsService.findOne(id));
		
		return "/client/point/goods_detail";
	}
	
	/**
	 * 兑换前验证
	 * 
	 */
	@RequestMapping(value="/point/goods/check",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> chkedAdd(Long id,HttpServletRequest req,ModelMap map){
		Map<String,Object> res = new HashMap<String, Object>();
		map.addAttribute("code", 0);
		
		String username = (String)req.getSession().getAttribute("username");
		if(null == username){
			res.put("code",1);
			res.put("msg", "请先登录");
			return res;
		}
		
		TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
		TdPointGoods pointGoods = tdPointGoodsService.findOne(id);
		if(null != user && null != pointGoods){
			if(null == user.getTotalPoints() || user.getTotalPoints() < pointGoods.getPoint()){
				res.put("msg", "积分不足");
				return res;
			}
		}else{
			res.put("msg", "参数错误");
			return res;
		}
		res.put("code", 2);
		return res;
	}
	
	/**
	 * 跳转兑换信息页面
	 * 
	 */
	@RequestMapping(value="/point/goods/exChange")
	public String exChang(Long id,HttpServletRequest req,ModelMap map){
		
		String username = (String)req.getSession().getAttribute("username");
		if(null == username){
			return "redirect:/login";
		}
		TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
		
		tdCommonService.setHeader(map, req);
		map.addAttribute("user", user);
		TdPointGoods goods = tdPointGoodsService.findOne(id);
		
		if(null == goods){
			return "/client/error_404";
		}
		map.addAttribute("goods", goods);
		
		return "/client/point/chang_order";
	}
	
	@RequestMapping(value="/point/order/submit",method=RequestMethod.POST)
	public String orderSubmit(Long addressId,Long goodsId,String userRemarke, HttpServletRequest req,ModelMap map){
		String username = (String)req.getSession().getAttribute("username");
		if(null == username){
			return "redirect:/login";
		}
		
		TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
		TdShippingAddress address = tdShippingAddressService.findOne(addressId);
		TdPointGoods goods = tdPointGoodsService.findOne(goodsId);
		
		tdCommonService.setHeader(map, req);
		
		if(null == user || null == address || null == goods){
			return "/client/error_404";
		}
		
		 Date current = new Date();
         SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
         String curStr = sdf.format(current);
         Random random = new Random();
		
		TdPointOrder pointOrder = new TdPointOrder();
		
		pointOrder.setCreateTime(current);
		pointOrder.setGoodsImg( goods.getImgUrl());
		pointOrder.setGoodsTitle(goods.getGoodsTitle());
		pointOrder.setPointId(goodsId);
		pointOrder.setUserRemarke(userRemarke);
		pointOrder.setPoint(goods.getPoint());
		pointOrder.setSubTitle(goods.getSubGoodsTitle());
		pointOrder.setGoodsCode(goods.getCode());
		
		pointOrder.setOrderNumber("D" + curStr
                     + leftPad(Integer.toString(random.nextInt(999)), 3, "0"));
		pointOrder.setStatusId(1);
		pointOrder.setUsername(user.getUsername());
		
		pointOrder.setShippingAddress(address.getDetailAddress());
		pointOrder.setPostalCode(address.getPostcode());
		pointOrder.setShippingName(address.getReceiverName());
		pointOrder.setShippingPhone(address.getReceiverMobile());
		
		pointOrder = tdPointOrderService.save(pointOrder);
		
		tdPointOrderService.exChangeGoods(user, pointOrder);
		
		return "redirect:/user/point/order/detail?id="+pointOrder.getId();
	}
	
	@RequestMapping(value="/user/point/order/list")
	public String orderlist(Integer statusId,
			String keywords,
			Integer page,
			HttpServletRequest req,ModelMap map){
		String username = (String)req.getSession().getAttribute("username");
		if(null == username){
			return "redirect:/login";
		}
		
		tdCommonService.setHeader(map, req);
		
		if(null == page )
		{
			page = 0;
		}
		
		map.addAttribute("statusId", statusId);
		map.addAttribute("page", page);
		map.addAttribute("keywords", keywords);
		
		map.addAttribute("order_page", tdPointOrderService.findAll(username, keywords,null,null, statusId, page, ClientConstant.pageSize));
		
		return "/client/point/order_list";
	}
	
	@RequestMapping(value="/user/point/order/detail")
	public String orderDetail(Long id,HttpServletRequest req,ModelMap map){
		String username = (String)req.getSession().getAttribute("username");
		if(null == username){
			return "redirect:/login";
		}
		
		tdCommonService.setHeader(map, req);
		
		map.addAttribute("order", tdPointOrderService.findOne(id));
		
		return "/client/point/order_detail";
	}
	
	@RequestMapping(value="/point/order/param",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> orderParam(Long orderId,Integer statusId,HttpServletRequest req,ModelMap map){
		Map<String, Object> res = new HashMap<>();
		
		res.put("code", 0);
		
		if(null == statusId){
			statusId =1;
		}
		
		TdPointOrder pointOrder = tdPointOrderService.findOne(orderId);
		if(null != pointOrder){
			
			pointOrder.setStatusId(statusId);
			tdPointOrderService.save(pointOrder);
			if(statusId == 4){
				tdPointOrderService.orderCancel(pointOrder);
			}
			
			res.put("code", 1);
			res.put("msg", "操作成功");
		}else{
			res.put("msg", "操作失败");
		}
		return res;
	}
	
}
