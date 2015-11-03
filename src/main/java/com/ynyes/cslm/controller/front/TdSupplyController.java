package com.ynyes.cslm.controller.front;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdProviderGoods;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdOrderService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.service.TdProviderGoodsService;
import com.ynyes.cslm.service.TdProviderService;
import com.ynyes.cslm.util.ClientConstant;

/**
 * 分销商控制器
 * @author libiao
 *
 */
@Controller
@RequestMapping(value="/supply")
public class TdSupplyController {
	
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
	TdProductCategoryService tdProductCategoryService;
	
	@RequestMapping(value="/index")
	public String Index(HttpServletRequest req,ModelMap map)
	{
		String username =(String)req.getSession().getAttribute("supply");
		if(null == username)
		{
			return "redirect:/login";
		}
		TdProvider provider = tdProviderService.findByUsername(username);
		if(null == provider)
		{
			return "error_404";
		}
		map.addAttribute("supply",provider);
		tdCommonService.setHeader(map, req);
		
		
		map.addAttribute("total_unpayed", 
				tdOrderService.countByProviderIdAndTypeIdAndStatusId(provider.getId(), 2, 2));
		map.addAttribute("total_undelivered",
				tdOrderService.countByProviderIdAndTypeIdAndStatusId(provider.getId(), 2, 3));
		map.addAttribute("total_unreceived",
				tdOrderService.countByProviderIdAndTypeIdAndStatusId(provider.getId(), 2, 4));
		map.addAttribute("total_finished",
				tdOrderService.countByProviderIdAndTypeIdAndStatusId(provider.getId(), 2, 6));
		map.addAttribute("supply_order_page",
				tdOrderService.findByProviderIdAndTypeId(provider.getId(), 2, 0, 10));
		
		
		return "/client/supply_index";
	}
	
	@RequestMapping(value="/password")
	public String distributorPassword(HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("supply");
		if(null == username)
		{
			return "redirect:/login";
		}
		tdCommonService.setHeader(map, req);
		map.addAttribute("supply",tdProviderService.findByUsername(username));
		return "/client/supply_change_password";
	}

	@RequestMapping(value="/password", method = RequestMethod.POST)
	public String distributorPassword(String oldPassword,String newPassword,
			HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("supply");
		if(null == username)
		{
			return "redirect:/login";
		}
		tdCommonService.setHeader(map, req);
		TdProvider supply = tdProviderService.findByUsername(username);
		 if (supply.getPassword().equals(oldPassword)) 
		 {
			 supply.setPassword(newPassword);
		 }
		
		map.addAttribute("supply",tdProviderService.save(supply));
		
		return "redirect:/supply/password";
	}
	
	/**
	 * 选择商品进行分销
	 * 
	 */
	@RequestMapping(value="/goods/distribution")
	public String wholesaling(Integer page,Long categoryId,String keywords,HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("supply");
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
		
		return "/client/supply_goods_onsale";
	}
	
	@RequestMapping(value="/distribution",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> wholesaling(Long goodsId,
			String goodsTitle,
			Double outFactoryPrice,
			Double shopReturnRation,
			Long leftNumber,
			HttpServletRequest req)
	{
		Map<String,Object> res =new HashMap<>();
		String username =(String)req.getSession().getAttribute("supply");
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
			proGoods.setCode(goods.getCode());
			proGoods.setCategoryId(goods.getCategoryId());
			proGoods.setCategoryIdTree(goods.getCategoryIdTree());
			proGoods.setShopReturnRation(shopReturnRation);
			proGoods.setIsDistribution(true);
			proGoods.setIsAudit(false);
		}
		else
		{
			proGoods.setGoodsTitle(goodsTitle);
			proGoods.setLeftNumber(proGoods.getLeftNumber()+leftNumber);
			proGoods.setOutFactoryPrice(outFactoryPrice);
			proGoods.setOnSaleTime(new Date());
			proGoods.setShopReturnRation(shopReturnRation);
			proGoods.setIsDistribution(true);
			proGoods.setIsAudit(false);
		}
		proGoods.setProviderTitle(provider.getTitle());
		provider.getGoodsList().add(proGoods);
		tdProviderService.save(provider);
		
		res.put("msg", "分销商品，等待平台审核~");
		
		return res;
	}
	
	@RequestMapping(value="/goods/list/{isDistribution}")
	public String goodsList(@PathVariable Boolean isDistribution,
			Integer page,
			Long categoryId,
//			String isDistribution,
			String keywords,
			HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("supply");
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
//		map.addAttribute("isOnSale", isSale);
		map.addAttribute("provider", provider);
		map.addAttribute("page", page);
		map.addAttribute("keywords", keywords);
		map.addAttribute("categoryId", categoryId);
		map.addAttribute("isDistribution",isDistribution); 
		
		map.addAttribute("category_list",tdProductCategoryService.findAll());
		
		if(null ==categoryId)
		{
			if(isDistribution)
			{
				if(null == keywords){
					map.addAttribute("supply_goods_page",
							tdProviderGoodsService.findByProviderIdAndIsDistribution(provider.getId(),true, page, ClientConstant.pageSize));
				}else{
					map.addAttribute("supply_goods_page",
							tdProviderGoodsService.searchAndProviderIdAndIsDistribution(provider.getId(), keywords, true, page, ClientConstant.pageSize));
				}
			}
			else 
			{
				if(null == keywords){
					map.addAttribute("supply_goods_page",
							tdProviderGoodsService.findByProviderIdAndIsDistribution(provider.getId(),false, page, ClientConstant.pageSize));
				}else{
					map.addAttribute("provider_goods_page",
							tdProviderGoodsService.searchAndProviderIdAndIsDistribution(provider.getId(), keywords, false, page, ClientConstant.pageSize));
				}
			}
		}
		else
		{
			if(isDistribution)
			{
				if(null == keywords){
					map.addAttribute("supply_goods_page",
							tdProviderGoodsService.findByProviderIdAndCategoryIdAndIsDistribution(provider.getId(), categoryId, true, page, ClientConstant.pageSize));
				}else{
					map.addAttribute("provider_goods_page",
							tdProviderGoodsService.searchAndProviderIdAndCategoryIdAndIsDistribution(provider.getId(), keywords, categoryId, true, page, ClientConstant.pageSize));
				}
			}
			else
			{
				if(null == keywords){
					map.addAttribute("supply_goods_page",
							tdProviderGoodsService.findByProviderIdAndCategoryIdAndIsDistribution(provider.getId(), categoryId, false, page, ClientConstant.pageSize));
				}else{
					map.addAttribute("supply_goods_page",
							tdProviderGoodsService.searchAndProviderIdAndCategoryIdAndIsDistribution(provider.getId(), keywords, categoryId, false, page, ClientConstant.pageSize));
				}
			}
		}
		
		return "/client/supply_goods";
	}
	
	
	@RequestMapping(value = "/edit/ImgUrl", method = RequestMethod.POST)
    @ResponseBody
    public String saveHeadPortrait(String imgUrl,HttpServletRequest rep)
    {
    	String username = (String)rep.getSession().getAttribute("supply");
    	if (null == username) {
            return "redirect:/login";
        }
        TdProvider provider = tdProviderService.findByUsername(username);
        provider.setImageUri(imgUrl);
        tdProviderService.save(provider);
    	return "client/supply_index";
    }
}
