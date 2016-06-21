package com.ynyes.cslm.controller.front;

import static org.apache.commons.lang3.StringUtils.leftPad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cslm.payment.alipay.AlipayConfig;
import com.cslm.payment.alipay.PaymentChannelAlipay;
import com.ynyes.cslm.entity.TdArticle;
import com.ynyes.cslm.entity.TdArticleCategory;
import com.ynyes.cslm.entity.TdCash;
import com.ynyes.cslm.entity.TdCountSale;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdOrderGoods;
import com.ynyes.cslm.entity.TdPayRecord;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdProviderGoods;
import com.ynyes.cslm.entity.TdSetting;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.entity.TdUserPoint;
import com.ynyes.cslm.service.TdArticleCategoryService;
import com.ynyes.cslm.service.TdArticleService;
import com.ynyes.cslm.service.TdCashService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdOrderService;
import com.ynyes.cslm.service.TdPayRecordService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.service.TdProviderGoodsService;
import com.ynyes.cslm.service.TdProviderService;
import com.ynyes.cslm.service.TdSettingService;
import com.ynyes.cslm.service.TdUserPointService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.util.ClientConstant;
import com.ynyes.cslm.util.SiteMagConstant;

/**
 * 分销商控制器
 * @author libiao
 *
 */
@Controller
@RequestMapping(value="/supply")
public class TdSupplyController extends AbstractPaytypeController{
	
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
	
	@Autowired
	TdDistributorGoodsService tdDistributorGoodsService;
	
	@Autowired
	TdUserService tdUserService;
	
	@Autowired
	TdDistributorService tdDistributorService;
	
	@Autowired
	TdUserPointService tdUserPointService;
	
	@Autowired
	TdPayRecordService tdPayRecordService;
	
	@Autowired
	TdArticleCategoryService tdArticleCategoryService;
	
	@Autowired
	TdArticleService tdArticleService;
	
	@Autowired
	TdSettingService tdSettingService;
	
	@Autowired
	private TdCashService tdCashService;
	
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
	
	@RequestMapping(value="edit")
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

	@RequestMapping(value="/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> distributorPassword(String title,String province,
			String city,String disctrict,
			String address,String mobile,
			String password,Double postPrice,
			Long id,String payPassword,
			HttpServletRequest req,ModelMap map)
	{
		
		Map<String,Object> res = new HashMap<>();
		res.put("code", 0);
		
		String username = (String)req.getSession().getAttribute("supply");
		if(null == username)
		{
			res.put("msg", "请重新登录！");
			return res;
		}
		
		if(null == id)
		{
			res.put("msg", "参数错误！");
			return res;
		}
		
		TdProvider supply = tdProviderService.findOne(id);
		
		supply.setTitle(title);
		supply.setProvince(province);
		supply.setCity(city);
		supply.setDisctrict(disctrict);
		supply.setAddress(address);
		supply.setMobile(mobile);
		supply.setPassword(password);
		supply.setPayPassword(payPassword);
		supply.setPostPrice(postPrice);
		
		tdProviderService.save(supply);
		
		res.put("msg", "修改成功！");
		res.put("code", 1);
		return res;
	}
	
	@RequestMapping(value="/edit/password",method= RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> passwordEdit(String type,String password,
							String newPassword,String newPassword2,HttpServletRequest req)
	{
		Map<String,Object> res = new HashMap<>();
		res.put("code", 0);
		
		String username = (String)req.getSession().getAttribute("supply");
		TdProvider supply = tdProviderService.findByUsername(username);
		
		if(null == supply)
		{
			res.put("msg", "请重新登录");
			return res;
		}
		
		if(null == password)
		{
			res.put("msg", "请输入密码");
			return res;
		}
		
		if(type.equalsIgnoreCase("pwd"))
		{
			if(!password.equalsIgnoreCase(supply.getPassword()))
			{
				res.put("msg", "原密码输入错误");
				return res;
			}
		}else if(type.equalsIgnoreCase("payPwd")){
			if(null != supply.getPayPassword() && !password.equalsIgnoreCase(supply.getPayPassword()))
			{
				res.put("msg", "原密码输入错误");
				return res;
			}
		}
		
		if(null == newPassword || newPassword.trim().length()< 6 || newPassword.trim().length()>20)
		{
			res.put("msg", "新密码长度为6-20");
			return res;
		}
		
		if(!newPassword.equalsIgnoreCase(newPassword2))
		{
			res.put("msg", "两次密码输入不一致");
			return res;
		}
		
		if(null != type)
		{
			if(type.equalsIgnoreCase("pwd"))
			{
				supply.setPassword(newPassword);
			}else if(type.equalsIgnoreCase("payPwd"))
			{
				supply.setPayPassword(newPassword);
			}
		}

		tdProviderService.save(supply);
		
		res.put("msg", "修改成功");
		res.put("code", 1);
		return res;
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
		
		map.addAttribute("category", tdProductCategoryService.findOne(categoryId));
		
		List<TdProductCategory> categortList = tdProductCategoryService.findByParentIdIsNullAndIsEnableTrueOrderBySortIdAsc();
        map.addAttribute("category_list", categortList);
        
		if(null == categoryId){
			if(null == keywords || "".equals(keywords.trim()))
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
			if(null == keywords || "".equals(keywords.trim()))
			{
				map.addAttribute("goods_page", 
						tdGoodsService.findByCategoryIdAndIsOnSaleTrue(categoryId, page, 10));
			}
			else
			{
				map.addAttribute("goods_page", 
						tdGoodsService.searchAndFindByCategoryIdAndIsOnSaleTrueOrderBySortIdAsc(keywords, categoryId, page, 10));
			}
			TdProductCategory category = tdProductCategoryService.findOne(categoryId);
            for (TdProductCategory tdProductCategory : categortList) {
            	
            	if(category.getParentTree().contains("["+tdProductCategory.getId()+"]"))
            	{
            		List<TdProductCategory> cateList = tdProductCategoryService.findByParentIdAndIsEnableTrueOrderBySortIdAsc(tdProductCategory.getId());
            		map.addAttribute("cateList", cateList);
            		
            		for (TdProductCategory productCategory : cateList) {
            			if(category.getParentTree().contains("["+productCategory.getId()+"]"))
            			{
            				map.addAttribute("categoryList", tdProductCategoryService.findByParentIdAndIsEnableTrueOrderBySortIdAsc(productCategory.getId()));
            			}
            		}
            		
            	}
            }
			
		}
		
		return "/client/supply_goods_onsale";
	}
	
	@RequestMapping(value="/distribution",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> wholesaling(Long goodsId,
			String goodsTitle,
			String subTitle,
			Double outFactoryPrice,
			Double marketPrice,
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
			proGoods.setSubGoodsTitle(subTitle);
			proGoods.setSubGoodsTitle(goods.getSubTitle());
			proGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
			proGoods.setOutFactoryPrice(outFactoryPrice);
			proGoods.setGoodsMarketPrice(marketPrice);
			proGoods.setLeftNumber(leftNumber);
			proGoods.setOnSaleTime(new Date());
			proGoods.setCode(goods.getCode());
			
			proGoods.setCategoryId(goods.getCategoryId());
			proGoods.setCategoryIdTree(goods.getCategoryIdTree());
			proGoods.setUnit(goods.getSaleType());
			proGoods.setShopReturnRation(shopReturnRation);
			proGoods.setIsDistribution(true);
			proGoods.setIsAudit(false);
		}
		else
		{
			proGoods.setGoodsTitle(goodsTitle);
			proGoods.setSubGoodsTitle(subTitle);
			proGoods.setLeftNumber(leftNumber);
			proGoods.setOutFactoryPrice(outFactoryPrice);
			proGoods.setGoodsMarketPrice(marketPrice);
			proGoods.setOnSaleTime(new Date());
			proGoods.setShopReturnRation(shopReturnRation);
			proGoods.setCategoryId(goods.getCategoryId());
			proGoods.setCategoryIdTree(goods.getCategoryIdTree());
			proGoods.setUnit(goods.getSaleType());
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
			Integer dir,
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
		map.addAttribute("dir", dir);
		
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
//		map.addAttribute("category_list",tdProductCategoryService.findAll());
		
		if(null ==categoryId)
		{
			if(isDistribution)
			{
				if(null == keywords || "".equals(keywords)){
					if(null != dir && 1== dir){
						map.addAttribute("supply_goods_page",
								tdProviderGoodsService.findByProviderIdAndIsDistributionAndIsAuditOrderByLeftNumberDesc(provider.getId(),true,true, page, ClientConstant.pageSize));
					}else if(null != dir && 2==dir){
						map.addAttribute("supply_goods_page",
								tdProviderGoodsService.findByProviderIdAndIsDistributionAndIsAuditOrderByLeftNumberAsc(provider.getId(),true,true, page, ClientConstant.pageSize));
					}else{
						map.addAttribute("supply_goods_page",
								tdProviderGoodsService.findByProviderIdAndIsDistributionAndIsAudit(provider.getId(),true,true, page, ClientConstant.pageSize));
					}
				}else{
					if(null != dir && 1 == dir){
						map.addAttribute("supply_goods_page",
								tdProviderGoodsService.searchAndProviderIdAndIsDistributionAndIsAuditOrderByLeftNumberDesc(provider.getId(),true,true,keywords,page, ClientConstant.pageSize));
					}else if(null != dir && 2 == dir){
						map.addAttribute("supply_goods_page",
								tdProviderGoodsService.searchAndProviderIdAndIsDistributionAndIsAuditOrderByLeftNumberAsc(provider.getId(),true,true,keywords,page, ClientConstant.pageSize));
					}else{
						map.addAttribute("supply_goods_page",
								tdProviderGoodsService.searchAndProviderIdAndIsDistributionAndIsAudit(provider.getId(),true,true,keywords,page, ClientConstant.pageSize));
					}
				}
			}
			else 
			{
				if(null == keywords){
					if(null != dir && 1==dir)
					{
						map.addAttribute("supply_goods_page",
								tdProviderGoodsService.findByProviderIdAndIsDistributionOrderByLeftNumberDesc(provider.getId(),false, page, ClientConstant.pageSize));
					}else if(null != dir && 2 == dir){
						map.addAttribute("supply_goods_page",
								tdProviderGoodsService.findByProviderIdAndIsDistributionOrderByLeftNumberAsc(provider.getId(),false, page, ClientConstant.pageSize));
					}else{
						map.addAttribute("supply_goods_page",
								tdProviderGoodsService.findByProviderIdAndIsDistribution(provider.getId(),false, page, ClientConstant.pageSize));
					}
				}else{
					if(null != dir && 1 == dir){
						map.addAttribute("provider_goods_page",
								tdProviderGoodsService.searchAndProviderIdAndIsDistributionOrderByLeftNumberDesc(provider.getId(), keywords, false, page, ClientConstant.pageSize));
					}else if(null != dir && 2 == dir){
						map.addAttribute("provider_goods_page",
								tdProviderGoodsService.searchAndProviderIdAndIsDistributionOrderByLeftNumberAsc(provider.getId(), keywords, false, page, ClientConstant.pageSize));
					}else{
						map.addAttribute("provider_goods_page",
								tdProviderGoodsService.searchAndProviderIdAndIsDistribution(provider.getId(), keywords, false, page, ClientConstant.pageSize));
					}
				}
			}
		}
		else
		{
			if(isDistribution)
			{
				if(null == keywords || "".equals(keywords)){
					if(null != dir && 1== dir){
						map.addAttribute("supply_goods_page",
								tdProviderGoodsService.findByProviderIdAndCategoryIdAndIsDistributionAndIsAuditOrderByLeftNumberDesc(provider.getId(), categoryId, true,true, page, ClientConstant.pageSize));
					}else if(null != dir && 2== dir){
						map.addAttribute("supply_goods_page",
								tdProviderGoodsService.findByProviderIdAndCategoryIdAndIsDistributionAndIsAuditOrderByLeftNumberAsc(provider.getId(), categoryId, true,true, page, ClientConstant.pageSize));
					}else{
						map.addAttribute("supply_goods_page",
								tdProviderGoodsService.findByProviderIdAndCategoryIdAndIsDistributionAndIsAudit(provider.getId(), categoryId, true,true, page, ClientConstant.pageSize));
					}
				}else{
					if(null != dir && 1 == dir){
						map.addAttribute("provider_goods_page",
								tdProviderGoodsService.searchAndProviderIdAndCategoryIdAndIsDistributionAndIsAudutOrderByLeftNumberDesc(provider.getId(), keywords, categoryId, true,true, page, ClientConstant.pageSize));
					}else if(null != dir && 2== dir){
						map.addAttribute("provider_goods_page",
								tdProviderGoodsService.searchAndProviderIdAndCategoryIdAndIsDistributionAndIsAudutOrderByLeftNumberAsc(provider.getId(), keywords, categoryId, true,true, page, ClientConstant.pageSize));
					}else{
						map.addAttribute("provider_goods_page",
								tdProviderGoodsService.searchAndProviderIdAndCategoryIdAndIsDistributionAndIsAudut(provider.getId(), keywords, categoryId, true,true, page, ClientConstant.pageSize));
					}
				}
			}
			else
			{
				if(null == keywords || "".equals(keywords)){
					if(null != dir && 1==dir){
						map.addAttribute("supply_goods_page",
								tdProviderGoodsService.findByProviderIdAndCategoryIdAndIsDistributionOrderByLeftNumberDesc(provider.getId(), categoryId, false, page, ClientConstant.pageSize));
					}else if(null != dir && 2==dir){
						map.addAttribute("supply_goods_page",
								tdProviderGoodsService.findByProviderIdAndCategoryIdAndIsDistributionOrderByLeftNumberAsc(provider.getId(), categoryId, false, page, ClientConstant.pageSize));
					}else{
						map.addAttribute("supply_goods_page",
								tdProviderGoodsService.findByProviderIdAndCategoryIdAndIsDistribution(provider.getId(), categoryId, false, page, ClientConstant.pageSize));
					}
				}else{
					if(null != dir && 1==dir){
						map.addAttribute("supply_goods_page",
								tdProviderGoodsService.searchAndProviderIdAndCategoryIdAndIsDistributionOrderByLeftNumberDesc(provider.getId(), keywords, categoryId, false, page, ClientConstant.pageSize));
					}else if(null != dir && 2==dir){
						map.addAttribute("supply_goods_page",
								tdProviderGoodsService.searchAndProviderIdAndCategoryIdAndIsDistributionOrderByLeftNumberAsc(provider.getId(), keywords, categoryId, false, page, ClientConstant.pageSize));
					}else{
						map.addAttribute("supply_goods_page",
								tdProviderGoodsService.searchAndProviderIdAndCategoryIdAndIsDistribution(provider.getId(), keywords, categoryId, false, page, ClientConstant.pageSize));
					}
				}
			}
		}
		
		return "/client/supply_goods";
	}
	
	//  分销、取消
	@RequestMapping(value="/goods/onsale/{pgId}")
	public String providerGoodsDelete(@PathVariable Long pgId,
			Boolean type,Integer page,
			HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("supply");
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
			providerGoods.setIsDistribution(type);
			tdProviderGoodsService.save(providerGoods);
			map.addAttribute("isDistribution", false);
			map.addAttribute("supply_goods_page",
					tdProviderGoodsService.findByProviderIdAndIsDistributionAndIsAudit(provider.getId(),false,true, page, ClientConstant.pageSize));
		}else{
			// 取消分销后 超市商品库删除分销商品
			List<TdDistributorGoods> list = tdDistributorGoodsService.findByProviderIdAndGoodsIdAndIsDistributionTrue(provider.getId(),providerGoods.getGoodsId());
			if(null != list && list.size() >0){
				for (TdDistributorGoods tdDistributorGoods : list) {
					tdDistributorGoodsService.delete(tdDistributorGoods);
				}
			}
			
			providerGoods.setIsDistribution(type);
			tdProviderGoodsService.save(providerGoods);
			map.addAttribute("isDistribution", true);
			map.addAttribute("supply_goods_page",
					tdProviderGoodsService.findByProviderIdAndIsDistributionAndIsAudit(provider.getId(),true,true, page, ClientConstant.pageSize));
		}
		
		map.addAttribute("provider", provider);
		return "/client/supply_goods_list";
	}
	
	// 删除
	@RequestMapping(value="/goods/delete/{pgId}")
	public String deleteGoods(@PathVariable Long pgId,
			Boolean type,Integer page,
			HttpServletRequest req,ModelMap map){
		String username = (String)req.getSession().getAttribute("supply");
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
		TdProviderGoods goods = tdProviderGoodsService.findOne(pgId);
		
		TdProvider provider = tdProviderService.findByUsername(username);
		
		List<TdDistributorGoods> list = tdDistributorGoodsService.findByProviderIdAndGoodsIdAndIsDistributionTrue(provider.getId(),goods.getGoodsId());
		if(null != list && list.size() >0){
			for (TdDistributorGoods tdDistributorGoods : list) {
				tdDistributorGoodsService.delete(tdDistributorGoods);
			}
		}
		
		tdProviderGoodsService.delete(pgId);
		
		map.addAttribute("supply_goods_page",
				tdProviderGoodsService.findByProviderIdAndIsDistributionAndIsAudit(provider.getId(),type,true, page, ClientConstant.pageSize));
		map.addAttribute("page", page);
		
		return "/client/supply_goods_list";
	}
	
	@RequestMapping(value="/goods/editOnSale",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> editOnSale(Long goodsId,Double outFactoryPrice,
							Long leftNumber,Double shopReturnRation,
							String code,String subTitle,
							Integer page,HttpServletRequest req)
	{
		Map<String,Object> res = new HashMap<>();
		res.put("code", 0);
		String username = (String)req.getSession().getAttribute("supply");
		if(null == username)
		{
			res.put("msg", "请重新登录!");
			return res;
		}
		
		if(null == goodsId)
		{
			res.put("msg", "参数错误!");
			return res;
		}
		if(null == leftNumber || leftNumber <=0)
		{
			res.put("msg", "库存输入错误");
			return res;
		}
		
		if(null == page )
		{
			page = 0;
		}
		TdProviderGoods proGoods = tdProviderGoodsService.findOne(goodsId);
		
		TdProvider provider = tdProviderService.findByUsername(username);
		
		proGoods.setLeftNumber(leftNumber);
		proGoods.setShopReturnRation(shopReturnRation);
		proGoods.setCode(code);
		proGoods.setSubGoodsTitle(subTitle);
		if(null != outFactoryPrice)
		{
			proGoods.setOutFactoryPrice(outFactoryPrice);
		}
		
		List<TdDistributorGoods> list = tdDistributorGoodsService.findByProviderIdAndGoodsIdAndIsDistributionTrue(provider.getId(),proGoods.getGoodsId());
		if(null != list && list.size() >0){
			for (TdDistributorGoods tdDistributorGoods : list) {
				if(null != tdDistributorGoods)
				{
					tdDistributorGoods.setGoodsPrice(outFactoryPrice);
					tdDistributorGoods.setSubGoodsTitle(subTitle);
					tdDistributorGoods.setLeftNumber(leftNumber);
					tdDistributorGoodsService.save(tdDistributorGoods);
				}
			}
		}
		tdProviderGoodsService.save(proGoods);
		
//		map.addAttribute("dis_goods_page", tdDistributorService.findByIdAndIsOnSale(distributor.getId(), false, page, 10));
		res.put("msg", "修改成功！");
		res.put("code", 1);
		return res;
	}
	
	
	/**
	 * 批量操作
	 * 
	 */
	@RequestMapping(value="/goods/checkAll/{type}")
	public String deleteCheck(@PathVariable Boolean type,
			Long[] listId,
			Integer[] listChkId,
			Integer page,
			Long categoryId,String keywords,
			HttpServletRequest req,
			ModelMap map){
		String username = (String)req.getSession().getAttribute("supply");
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
			return "redirect:/supply/goods/list/"+type+"?page="+page+"&keywords="+keywords;
		}else{
			return "redirect:/supply/goods/list/"+type+"?page="+page+"&categoryId="+categoryId+"&keywords="+keywords;
		}
	}
	
	/**
	 * 分销单
	 * @throws ParseException 
	 * 
	 */
	@RequestMapping(value="/disOrder/list")
	public String disOrder(
			Integer statusId,
			String keywords,
			Integer page,
			String startTime,String endTime,
			String eventTarget,
			HttpServletRequest req,
			HttpServletResponse resp,
			ModelMap map) throws ParseException
	{
		String username =(String)req.getSession().getAttribute("supply");
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date start = null;
		Date end = null ;
		
		if(null != startTime && !"".equals(startTime.trim()))
		{
			start = sdf.parse(startTime);
		}
		if(null != endTime && !"".equals(endTime.trim()))
		{
			end = sdf.parse(endTime);
		}
		
		
		String excelUrl=null;
		int size = 20;
		
		if(null != eventTarget)
		{
			if("excel".equalsIgnoreCase(eventTarget))
			{
				excelUrl=SiteMagConstant.backupPath;
			}
			if("excelAll".equalsIgnoreCase(eventTarget))
			{
				excelUrl=SiteMagConstant.backupPath;
				size =  Integer.MAX_VALUE;
			}
		}
		
		
		
		TdProvider provider = tdProviderService.findByUsername(username);
		map.addAttribute("provider",provider);
		map.addAttribute("statusId", statusId);
		map.addAttribute("startTime", start);
		map.addAttribute("endTime", end);
		map.addAttribute("page", page);
		
		tdCommonService.setHeader(map, req);
		
		Page<TdOrder> orderPage=tdOrderService.findByProviderId(provider.getId(), statusId, 2,start, end, page, ClientConstant.pageSize);
		
		if(null != excelUrl)
        {
			
			/**
			 * 导出表格
			 */
			// 创建一个webbook 对于一个Excel
			HSSFWorkbook wb = new HSSFWorkbook();
			// 在webbook中添加一个sheet,对应Excel文件中的sheet 
			HSSFSheet sheet = wb.createSheet("order"); 
			// 设置每个单元格宽度根据字多少自适应
			sheet.autoSizeColumn(1);
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        HSSFRow row = sheet.createRow((int) 0);
	        // 创建单元格，并设置值表头 设置表头居中 
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  // 居中
	        
	        HSSFCell cell = row.createCell((short) 0);  
	        cell.setCellValue("订单编号");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 1);  
	        cell.setCellValue("代理商");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 2);  
	        cell.setCellValue("预购会员");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 3);  
	        cell.setCellValue("收件地址");  
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 4);  
	        cell.setCellValue("订单总额");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 5);  
	        cell.setCellValue("下单时间");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 6);  
	        cell.setCellValue("订单状态");  
	        cell.setCellStyle(style); 
	        
          	Page<TdOrder> order_page = tdOrderService.findByProviderId(provider.getId(), statusId, 2,start, end, page, size);
          	if(orderImport(order_page, row, cell, sheet))
          	{
          		download(wb,"order", excelUrl, resp);
          	}
          }
		//		if (timeId.equals(0)) {
//            if (statusId.equals(0)) {
//                if (null != keywords && !keywords.isEmpty()) {
//                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndSearch(
//                    		provider.getId(),2, keywords, page, ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndSearch(
//                        		provider.getId(),2, keywords, page, ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                } else {
//                    orderPage = tdOrderService.findByProviderIdAndTypeId(provider.getId(),2, page,
//                            ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeId(provider.getId(),2, page,
//                                ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                }
//            } else {
//                if (null != keywords && !keywords.isEmpty()) {
//                    orderPage = tdOrderService .findByProviderIdAndTypeIdAndStatusIdAndSearch(provider.getId(),2,statusId, keywords, page,
//                                    ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService .findByProviderIdAndTypeIdAndStatusIdAndSearch(provider.getId(),2,statusId, keywords, page,
//                                ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                } else {
//                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndStatusId(
//                    		provider.getId(),2,statusId, page, ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndStatusId(
//                        		provider.getId(),2,statusId, page, ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                }
//            }
//        } else if (timeId.equals(1)) {
//            Date cur = new Date();
//            Calendar calendar = Calendar.getInstance();// 日历对象
//            calendar.setTime(cur);// 设置当前日期
//            calendar.add(Calendar.MONTH, -1);// 月份减一
//            Date time = calendar.getTime();
//
//            if (statusId.equals(0)) {
//                if (null != keywords && !keywords.isEmpty()) {
//                    orderPage = tdOrderService .findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
//                                    2,time, keywords, page,
//                                    ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService .findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
//                                2,time, keywords, page,
//                                ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                } else {
//                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
//                    		provider.getId(),2,time, page, ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
//                        		provider.getId(),2,time, page, ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                }
//            } else {
//                if (null != keywords && !keywords.isEmpty()) {
//                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
//                            		provider.getId(),2, statusId, time, keywords, page,
//                                    ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
//                        		provider.getId(),2, statusId, time, keywords, page,
//                                ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                } else {
//                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
//                                   2, statusId, time, page,
//                                    ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
//                                2, statusId, time, page,
//                                ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                }
//            }
//        } else if (timeId.equals(3)) {
//            Date cur = new Date();
//            Calendar calendar = Calendar.getInstance();// 日历对象
//            calendar.setTime(cur);// 设置当前日期
//            calendar.add(Calendar.MONTH, -3);// 月份减一
//            Date time = calendar.getTime();
//
//            if (statusId.equals(0)) {
//                if (null != keywords && !keywords.isEmpty()) {
//                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
//                                   2, time, keywords, page,
//                                    ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
//                                2, time, keywords, page,
//                                ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                } else {
//                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
//                    		provider.getId(),2, time, page, ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
//                        		provider.getId(),2, time, page, ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                }
//            } else {
//                if (null != keywords && !keywords.isEmpty()) {
//                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
//                            		provider.getId(),2, statusId, time, keywords, page,
//                                    ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
//                        		provider.getId(),2, statusId, time, keywords, page,
//                                ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                } else {
//                    orderPage = tdOrderService .findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
//                                    2,statusId, time, page,
//                                    ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService .findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
//                                2,statusId, time, page,
//                                ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                }
//            }
//        } else if (timeId.equals(6)) {
//            Date cur = new Date();
//            Calendar calendar = Calendar.getInstance();// 日历对象
//            calendar.setTime(cur);// 设置当前日期
//            calendar.add(Calendar.MONTH, -6);// 月份减一
//            Date time = calendar.getTime();
//
//            if (statusId.equals(0)) {
//                if (null != keywords && !keywords.isEmpty()) {
//                    orderPage = tdOrderService .findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
//                                    2,time, keywords, page,
//                                    ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService .findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
//                                2,time, keywords, page,
//                                ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                } else {
//                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
//                    		provider.getId(),2, time, page, ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
//                        		provider.getId(),2, time, page, ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                }
//            } else {
//                if (null != keywords && !keywords.isEmpty()) {
//                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
//                            		provider.getId(),2, statusId, time, keywords, page,
//                                    ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
//                        		provider.getId(),2, statusId, time, keywords, page,
//                                ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                } else {
//                    orderPage = tdOrderService .findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
//                                   2, statusId, time, page,
//                                    ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService .findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
//                                2, statusId, time, page,
//                                ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                }
//            }
//        } else if (timeId.equals(12)) {
//            Date cur = new Date();
//            Calendar calendar = Calendar.getInstance();// 日历对象
//            calendar.setTime(cur);// 设置当前日期
//            calendar.add(Calendar.YEAR, -1);// 减一
//            Date time = calendar.getTime();
//
//            if (statusId.equals(0)) {
//                if (null != keywords && !keywords.isEmpty()) {
//                    orderPage = tdOrderService .findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
//                                    2,time, keywords, page,
//                                    ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService .findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
//                                2,time, keywords, page,
//                                ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                } else {
//                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
//                    		provider.getId(),2, time, page, ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
//                        		provider.getId(),2, time, page, ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                }
//            } else {
//                if (null != keywords && !keywords.isEmpty()) {
//                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
//                            		provider.getId(),2, statusId, time, keywords, page,
//                                    ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
//                        		provider.getId(),2, statusId, time, keywords, page,
//                                ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                } else {
//                    orderPage = tdOrderService .findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
//                                    2,statusId, time, page,
//                                    ClientConstant.pageSize);
//                    if(null != excelUrl)
//                    {
//                    	Page<TdOrder> order_page = tdOrderService .findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
//                                2,statusId, time, page,
//                                ClientConstant.pageSize);
//                    	if(orderImport(order_page, row, cell, sheet))
//                    	{
//                    		download(wb,"order", excelUrl, resp);
//                    	}
//                    }
//                }
//            }
//        }
		
		map.addAttribute("order_page",orderPage);
		return "/client/supply_order_list";
	}
	
	/**
	 * 分销单详情
	 * 
	 */
	@RequestMapping(value="/order")
	public String disOrderDetail(Long id,HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("supply");
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
		return "/client/supply_order_detail";
	}
	
	@RequestMapping(value="/order/param/edit")
	@ResponseBody
	public Map<String, Object> disparamEdit(String orderNumber,
			String type,
			ModelMap map,
			HttpServletRequest req)
	{
		Map<String, Object> res =new HashMap<>();
		res.put("code",1);
		String username = (String)req.getSession().getAttribute("supply");
		
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
					addVir(order);
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
	
	/**
	 *  审核中商品
	 *  
	 */
	@RequestMapping(value="/goods/audit")
	public String auditGoods(Integer page,String keywords,Long categoryId,HttpServletRequest req,ModelMap map){
		String username = (String)req.getSession().getAttribute("supply");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	if(null == page){
    		page=0;
    	}
    	tdCommonService.setHeader(map, req);
    	TdProvider provider = tdProviderService.findByUsername(username);
    	
    	map.addAttribute("page", page);
    	map.addAttribute("categoryId",categoryId);
    	map.addAttribute("keywords",keywords);
    	
    	// 所有分类Id
		List<Long> list = tdProviderGoodsService.findByProviderIdAndIsAudit(provider.getId());
		
		List<TdProductCategory> category_list = new ArrayList<>();
		
		if(null != list)
		{
			for (int i = 0; i < list.size(); i++) {
				System.err.println(list.get(i));
				category_list.add(tdProductCategoryService.findOne(Long.parseLong(list.get(i)+"")));
			}
		}// 所有该批发商有的分类
		map.addAttribute("category_list",category_list);
//    	map.addAttribute("category_list",tdProductCategoryService.findAll());
    	
    	
    	if(null == categoryId)
    	{
    		if(null == keywords || "".equals(keywords.trim())){
    			map.addAttribute("goods_page", 
    					tdProviderGoodsService.findByProviderIdAndIsAudit(provider.getId(), false, page, ClientConstant.pageSize));
    		}else{
    			map.addAttribute("goods_page",
    					tdProviderGoodsService.searchAndProviderIdAndIsAudit(provider.getId(), keywords, false, page, ClientConstant.pageSize));
    		}
    	}
    	else
    	{
    		if(null == keywords || "".equals(keywords.trim())){
    			map.addAttribute("goods_page", 
    					tdProviderGoodsService.findByProviderIdAndCategoryIdAndIsAudit(provider.getId(),categoryId, false, page, ClientConstant.pageSize));
    		}else{
    			map.addAttribute("goods_page",
    					tdProviderGoodsService.searchAndProviderIdAndCategoryIdAndKeywordsAndIsAudit(provider.getId(),categoryId, keywords, false, page, ClientConstant.pageSize));
    		}
    	}
		return "/client/supply_goods_audit";
	}
	
	/**
	 * 交易记录
	 * 
	 */
	@RequestMapping(value="/pay/record")
    public String payRecord(Integer page,String cont, HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("supply");
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
    	return "/client/supply_record";
    }
	
	/**
     * 平台服务
     * 
     */
    @RequestMapping(value="/info/{mid}")
    public String info(@PathVariable Long mid,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("supply");
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
 	   
 	   return "/client/supply_info_list";
   }
    @RequestMapping(value="/content/{newId}")
    public String newContent(@PathVariable Long newId,Long mid,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("supply");
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
    	return "/client/supply_info";
    }
    
    /**
     * 销售统计
     * @author Max
     * 
     */
    @RequestMapping(value="/order/sum")
    public String sumOrderGoods(String startTime,String endTime,
    		String eventTarget,HttpServletResponse resp,
    		HttpServletRequest req,ModelMap map) throws ParseException
    {
    	String username = (String)req.getSession().getAttribute("supply");
    	if(null == username)
		{
			return "redirect:/login";
		}
    	tdCommonService.setHeader(map, req);
    	
    	TdProvider supply = tdProviderService.findByUsername(username);
    	if(null == supply)
    	{
    		return "redirect:/login";
    	}
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date start = null;
		Date end = null ;
		
		if(null != startTime && !"".equals(startTime.trim()))
		{
			start = sdf.parse(startTime);
		}
		if(null != endTime && !"".equals(endTime.trim()))
		{
			end = sdf.parse(endTime);
		}
		
		List<TdOrder> list = tdOrderService.searchOrderGoods(null, supply.getId(),2L,start, end);
		List<TdCountSale> countList = tdOrderService.sumOrderGoods(supply.getId(),2L,list);
		
		String excelUrl=null;
		if(null != eventTarget)
		{
			if("excel".equalsIgnoreCase(eventTarget))
			{
				excelUrl=SiteMagConstant.backupPath;
			}
		}
		
        if(null != excelUrl)
        {
        	/**
    		 * 导出表格
    		 */
    		// 创建一个webbook 对于一个Excel
    		HSSFWorkbook wb = new HSSFWorkbook();
    		// 在webbook中添加一个sheet,对应Excel文件中的sheet 
    		HSSFSheet sheet = wb.createSheet("countSale"); 
    		// 设置每个单元格宽度根据字多少自适应
    		sheet.autoSizeColumn(1);
    		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
            HSSFRow row = sheet.createRow((int) 0);
            // 创建单元格，并设置值表头 设置表头居中 
            HSSFCellStyle style = wb.createCellStyle();  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  // 居中
            
            HSSFCell cell = row.createCell((short) 0);  
            cell.setCellValue("商品名称");  
            cell.setCellStyle(style); 
            cell = row.createCell((short) 1);  
            cell.setCellValue("商品副标题");  
            cell.setCellStyle(style); 
            cell = row.createCell((short) 2);  
            cell.setCellValue("商品编码");  
            cell.setCellStyle(style); 
            
            cell = row.createCell((short) 3);  
            cell.setCellValue("销售数量");  
            cell.setCellStyle(style); 
            
            cell = row.createCell((short) 4);  
            cell.setCellValue("售价");  
            cell.setCellStyle(style);
            
            cell = row.createCell((short) 5);  
            cell.setCellValue("销售额");  
            cell.setCellStyle(style); 
            
            
        	if(saleImport(countList,startTime,endTime, row, cell, sheet))
        	{
        		download(wb,"countSale", excelUrl, resp);
        	}
        }
		
		map.addAttribute("saleList", countList);
		map.addAttribute("startTime", start);
		map.addAttribute("endTime", end);
		
		return "/client/supply_sale";
    }
    
    /**
     * 账号管理
     * 
     */
    @RequestMapping(value="/account")
    public String account(HttpServletRequest req,ModelMap map, Integer page){
    	String username = (String)req.getSession().getAttribute("supply");
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
    	
    	map.addAttribute("supply", provider);
    	map.addAttribute("pay_record_page",
    				tdPayRecordService.findByProviderId(provider.getId(), page, ClientConstant.pageSize));
    	
    	return "/client/supply_account";
    }
    
    /**
     * 充值
     * 
     */
    @RequestMapping(value="/topup1")
    public String topupOne(HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("supply");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("supply",
    				tdProviderService.findByUsername(username));
    	// 支付方式列表
        setPayTypes(map, true, false, req);
    	
    	return "/client/supply_top_one";
    }
    
    @RequestMapping(value="/topup2",method=RequestMethod.POST)
    public String topupTwo(HttpServletRequest req,ModelMap map,
    			Double provice,Long payTypeId){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	tdCommonService.setHeader(map, req);
    	TdProvider provider = tdProviderService.findByUsername(username);

    	Date current = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String curStr = sdf.format(current);
    	Random random = new Random();
    	
    	TdCash cash = new TdCash();
    	cash.setCashNumber("FX"+curStr+leftPad(Integer.toString(random.nextInt(999)), 3, "0"));
    	cash.setShopTitle(provider.getTitle());
    	cash.setUsername(username);
    	cash.setCreateTime(new Date());
    	cash.setPrice(provice); // 金额
    	cash.setShopType(3L); // 类型-超市
    	cash.setType(1L); // 类型-充值
    	cash.setStatus(1L); // 状态 提交
    	
    	cash = tdCashService.save(cash);
    	
    	req.setAttribute("orderNumber", cash.getCashNumber());
    	req.setAttribute("totalPrice",cash.getPrice().toString());
    	
    	PaymentChannelAlipay paymentChannelAlipay = new PaymentChannelAlipay();
        String payForm = paymentChannelAlipay.getPayFormData(req);
        map.addAttribute("charset", AlipayConfig.CHARSET);
    	
        map.addAttribute("payForm", payForm);
    	
//    	return "/client/distributor_top_end";
        return "/client/order_pay_form";
    }
    
    @RequestMapping(value="/cash")
    public String cashReturn(String cashNumber,HttpServletRequest req,ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("supply");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	tdCommonService.setHeader(map, req);
    	
    	map.addAttribute("supply",tdProviderService.findByUsername(username));
    	if(null != cashNumber)
    	{
    		map.addAttribute("cash", tdCashService.findByCashNumber(cashNumber));
    	}
    	
    	return "/client/supply_top_end";
    }
    
    /**
     * 提现
     * 
     */
    @RequestMapping(value="/draw1")
    public String withdraw(HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("supply");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("supply", tdProviderService.findByUsername(username));
    	
    	return "/client/supply_draw_one";
    }
    
    @RequestMapping(value="/drwa2",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> userDrwa(String card,Double price,String payPassword,
    			HttpServletRequest req){
    	Map<String,Object> res = new HashMap<>();
    	res.put("code", 0);
    	
    	String username = (String)req.getSession().getAttribute("supply");
    	if(null == username)
    	{
    		res.put("msg", "请重新登录");
    		return res;
    	}
    	
    	TdProvider supply = tdProviderService.findByUsername(username);
    	
    	if(null == price)
    	{
    		res.put("msg", "请输入金额");
    		return res;
    	}
    	
    	if(price < 100){
    		res.put("msg", "提现金额必须大于100");
    		return res;
    	}
    	
    	if(null == payPassword || !payPassword.equalsIgnoreCase(supply.getPayPassword()))
    	{
    		res.put("msg", "密码错误");
    		return res;
    	}
    	
    	if(null != supply)
    	{
    		Date current = new Date();
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        	String curStr = sdf.format(current);
        	Random random = new Random();
        	
    		
    		TdCash cash = new TdCash();
    		
    		cash.setCard(card);
    		cash.setPrice(price);
    		cash.setCreateTime(new Date());
    		cash.setCashNumber("FX"+curStr+leftPad(Integer.toString(random.nextInt(999)), 3, "0"));
    		cash.setShopTitle(supply.getTitle());
    		cash.setUsername(username);
    		cash.setShopType(3L);
    		cash.setType(2L);
    		cash.setStatus(1L);
    		
    		tdCashService.save(cash);
    		res.put("msg", "提交成功");
    		res.put("code", 1);
    		return res;
    	}
    	
    	res.put("msg", "参数错误");
    	return res;
    }
	
	@RequestMapping(value = "/edit/ImgUrl", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveHeadPortrait(String imgUrl,HttpServletRequest rep)
    {
		Map<String, Object> res = new HashMap<>();
    	res.put("code", 1);
    	
    	String username = (String)rep.getSession().getAttribute("supply");
    	if (null == username) {
    		 res.put("msg", "登录超时");
             return res;
        }
        TdProvider provider = tdProviderService.findByUsername(username);
        provider.setImageUri(imgUrl);
        tdProviderService.save(provider);
        res.put("code", 0);
    	return res;
    }
	
	public void ChangeAll(Boolean isDistribution,Long[] ids,Integer[] chkIds)
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
					providerGoods.setIsDistribution(isDistribution);
					tdProviderGoodsService.save(providerGoods);
				}
				// 如果是取消分销
				if(!isDistribution){
					Long proId = tdProviderGoodsService.findProviderId(id);
					// 取消分销后 超市商品库删除分销商品
					List<TdDistributorGoods> list = tdDistributorGoodsService.findByProviderIdAndGoodsIdAndIsDistributionTrue(proId,providerGoods.getGoodsId());
					if(null != list && list.size() >0){
						for (TdDistributorGoods tdDistributorGoods : list) {
							tdDistributorGoodsService.delete(tdDistributorGoods);
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public Boolean orderImport(Page<TdOrder> orderPage,HSSFRow row, HSSFCell cell, HSSFSheet sheet)
	{
		for (int i = 0; i < orderPage.getContent().size(); i++) {
			row = sheet.createRow((int)i+1);
			TdOrder order = orderPage.getContent().get(i);
			
			row.createCell((short) 0).setCellValue(order.getOrderNumber());
			row.createCell((short) 1).setCellValue(order.getShopTitle());
			row.createCell((short) 2).setCellValue(order.getShippingName());
			row.createCell((short) 3).setCellValue(order.getShippingAddress());
			row.createCell((short) 4).setCellValue(order.getTotalPrice());
			row.createCell((short) 5).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(order.getOrderTime()));
			if(order.getStatusId() ==2)
			{
				row.createCell((short) 6).setCellValue("待付款");
			}else if(order.getStatusId() ==3)
			{
				row.createCell((short) 6).setCellValue("待发货");
			}else if(order.getStatusId() ==4)
			{
				row.createCell((short) 6).setCellValue("待收货");
			}else if(order.getStatusId() ==5)
			{
				row.createCell((short) 6).setCellValue("待评价");
			}else if(order.getStatusId() ==6)
			{
				row.createCell((short) 6).setCellValue("已完成");
			}else if(order.getStatusId() ==7)
			{
				row.createCell((short) 6).setCellValue("已取消");
			}
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public Boolean saleImport(List<TdCountSale> saleList,String startTime,String endTime,HSSFRow row, HSSFCell cell, HSSFSheet sheet)
	{
		for (int i = 0; i < saleList.size(); i++) {
			row = sheet.createRow((int)i+1);
			TdCountSale countSale = saleList.get(i);
			
			row.createCell((short) 0).setCellValue(countSale.getGoodsTitle());
			row.createCell((short) 1).setCellValue(countSale.getSubTitle());
			row.createCell((short) 2).setCellValue(countSale.getGoodsCode());
			row.createCell((short) 3).setCellValue(countSale.getQuantity());
			row.createCell((short) 4).setCellValue(countSale.getPrice());
			row.createCell((short) 5).setCellValue(countSale.getTotalPrice());
		}
		return true;
	}
	
	public Boolean download(HSSFWorkbook wb,String name, String exportUrl, HttpServletResponse resp){
	   	 try  
	        {  
		          FileOutputStream fout = new FileOutputStream(exportUrl+name+".xls");  
//		          OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");	                       	     
		          wb.write(fout);  
		          fout.close();
	        }catch (Exception e)  
	        {  
	            e.printStackTrace();  
	        } 
	   	 OutputStream os;
			 try {
					os = resp.getOutputStream();
					File file = new File(exportUrl+name+".xls");
	                
	            if (file.exists())
	                {
	                  try {
	                        resp.reset();
	                        resp.setHeader("Content-Disposition", "attachment; filename="
	                        		+name+".xls");
	                        resp.setContentType("application/octet-stream; charset=utf-8");
	                        os.write(FileUtils.readFileToByteArray(file));
	                        os.flush();
	                    } finally {
	                        if (os != null) {
	                            os.close();
	                        }
	                    }
	            }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			 }
			 return true;	
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
        servicePrice +=tdOrder.getTotalPrice();
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
