package com.ynyes.cslm.controller.front;

import static org.apache.commons.lang3.StringUtils.leftPad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
import com.ynyes.cslm.entity.TdBank;
import com.ynyes.cslm.entity.TdCash;
import com.ynyes.cslm.entity.TdCountSale;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdPayRecord;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdProviderGoods;
import com.ynyes.cslm.entity.TdSpecificat;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.entity.TdUserReturn;
import com.ynyes.cslm.service.TdArticleCategoryService;
import com.ynyes.cslm.service.TdArticleService;
import com.ynyes.cslm.service.TdBankService;
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
import com.ynyes.cslm.service.TdSpecificatService;
import com.ynyes.cslm.service.TdUserPointService;
import com.ynyes.cslm.service.TdUserReturnService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.util.ClientConstant;
import com.ynyes.cslm.util.FileDownUtils;
import com.ynyes.cslm.util.SMSUtil;
import com.ynyes.cslm.util.SiteMagConstant;
import com.ynyes.cslm.util.StringUtils;

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
	
	@Autowired
	private TdUserReturnService tdUserReturnService;
	
	@Autowired
	private TdSpecificatService tdSpecificatService;
	
	@Autowired
	private TdBankService tdBankService;
	
	@RequestMapping(value="/index")
	public String Index(HttpServletRequest req,ModelMap map)
	{
		String username =(String)req.getSession().getAttribute("supply");
		if(null == username)
		{
			return "redirect:/login";
		}
		tdCommonService.setHeader(map, req);
		TdProvider provider = tdProviderService.findByUsername(username);
		if(null == provider)
		{
			return "/client/error_404";
		}
		map.addAttribute("supply",provider);
		
		
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
	public String wholesaling(Integer page,Long categoryId,
			String keywords,HttpServletRequest req,ModelMap map)
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
		
		TdProvider provider = tdProviderService.findByUsername(username);
		
		map.addAttribute("page", page);
		map.addAttribute("categoryId",categoryId);
		map.addAttribute("keywords",keywords);
		
		PageRequest pageRequest = new PageRequest(0, Integer.MAX_VALUE);
		map.addAttribute("sypply_List", tdProviderGoodsService.findAll(provider.getId(), categoryId, keywords, true, null, pageRequest));
		
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
	
	/**
     * 商品信息
     * @author Max
     * 2016-10-21
     */
   @RequestMapping(value="/goods/detail",method=RequestMethod.POST)
	public String saleGoodsDetail(Long sup_id,Long goodsId,HttpServletRequest req,ModelMap map){
		
		if(null != sup_id){
			TdProviderGoods goods = tdProviderGoodsService.findOne(sup_id);
			map.addAttribute("sup_goods", goods);
			map.addAttribute("goodsId", goods.getGoodsId());
		}else if(null != goodsId){
			map.addAttribute("goods", tdGoodsService.findOne(goodsId));
			map.addAttribute("goodsId", goodsId);
		}
		return "/client/supply_goods_detail";
	}
   
   /**
	 * 根据ID查找商品规格
	 * @author Max
	 * 2016-10-21
	 */
	@RequestMapping(value="/search/specifica",method=RequestMethod.POST)
	public String specificaSearch(Long goodsId,Long id,HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("supply");
		if(null != goodsId && null != username){
			map.addAttribute("goodsId", goodsId);
			TdProvider tdProvider = tdProviderService.findByUsername(username);
			map.addAttribute("spec_list", tdSpecificatService.findByShopIdAndGoodsIdAndType(tdProvider.getId(),goodsId, 3));
			if(null != id){
				map.addAttribute("specifica", tdSpecificatService.findOne(id));
			}
		}
		return "/client/supply_goods_spec";
	}
	
	/**
	 * 保存规格
	 * @author Max
	 * 2016-10-21
	 */
	@RequestMapping(value="/specifica/save",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> specificaSave(TdSpecificat tdSpecificat,HttpServletRequest req,ModelMap map){
		Map<String,Object> res = new HashMap<String, Object>();
		res.put("code", 0);
		
		String username = (String)req.getSession().getAttribute("supply");
		if(null == username){
			res.put("msg", "登录超时");
			return res;
		}
		if(null != tdSpecificat){
			if(null == tdSpecificat.getSpecifict() || "".equals(tdSpecificat.getSpecifict().trim()) || null == tdSpecificat.getLeftNumber()){
				res.put("msg", "规格填写错误");
				return res;
			}
			if(null == tdSpecificat.getId()){
				TdProvider supply = tdProviderService.findByUsername(username);
				
				tdSpecificat.setShopId(supply.getId());//　设置超市id
				tdSpecificat.setType(3); //设置类型-分销商
			}
			tdSpecificat = tdSpecificatService.save(tdSpecificat);
			
			// 查看引用规格记录
			List<TdSpecificat> list = tdSpecificatService.findByOldId(tdSpecificat.getId());
			
			if(null != list && list.size() > 0){
				for (TdSpecificat tdSpecificat2 : list) {
					tdSpecificat2.setSpecifict(tdSpecificat.getSpecifict());
					tdSpecificat2.setLeftNumber(tdSpecificat.getLeftNumber());
				}
				tdSpecificatService.save(list);
			}
			
			res.put("code", 1);
			res.put("goodsId", tdSpecificat.getGoodsId());
		}else{
			res.put("msg", "参数错误");
		}
		return res;
	}
	
	/**
	 * 删除规格
	 * @author Max
	 * 2016-10-21
	 * 
	 */
	@RequestMapping(value="/specifica/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteSpecifica(Long id,HttpServletRequest req){
		Map<String,Object> res = new HashMap<String, Object>();
		res.put("code", 0);
		
		String username = (String)req.getSession().getAttribute("supply");
		if(null == username){
			res.put("msg", "登录超时");
			return res;
		}
		if(null != id){
			
			List<TdSpecificat> list = tdSpecificatService.findByOldId(id);
			if(null != list && list.size() > 0){
				tdSpecificatService.delete(list);
			}
			tdSpecificatService.delete(id);
			
			res.put("code", 1);
		}
		
		return res;
	}
	
	/**
	 * 信息修改保存
	 * @author Max
	 * 2016-10-21
	 */
	@RequestMapping(value="/distribution",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> wholesaling(
			Long sup_goodsId,
			Long goodsId,
			String subGoodsTitle,
			Double goodsPrice,
			Double goodsMarketPrice,
			Double shopReturnRation,
			Long leftNumber,
			String code,
			String unit,
			HttpServletRequest req)
	{
		Map<String,Object> res =new HashMap<>();
		res.put("code", 0);
		
		String username =(String)req.getSession().getAttribute("supply");
		if(null == username )
		{
			res.put("msg", "登录超时！");
			return res;
		}
		
		TdProvider provider = tdProviderService.findByUsername(username);
		
		if(null == provider){
			res.put("msg", "参数错误!");
			return res;
		}
		
		if(null == leftNumber || leftNumber <=0)
		{
			res.put("msg", "库存输入错误");
			return res;
		}
		
		TdProviderGoods tdProviderGoods =null;
		
		// 判断是选择分销还是修改信息
		if(null == sup_goodsId){
			TdGoods goods = tdGoodsService.findOne(goodsId);
			
			tdProviderGoods = new TdProviderGoods();
			
			tdProviderGoods.setGoodsId(goods.getId());
			tdProviderGoods.setGoodsTitle(goods.getTitle());
			tdProviderGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
			tdProviderGoods.setOnSaleTime(new Date());
			tdProviderGoods.setCategoryId(goods.getCategoryId());
			tdProviderGoods.setCategoryIdTree(goods.getCategoryIdTree());
			
			tdProviderGoods.setIsDistribution(true);
			tdProviderGoods.setIsAudit(true);
			tdProviderGoods.setProId(provider.getId());
			
		}else{
			tdProviderGoods= tdProviderGoodsService.findOne(sup_goodsId);
		}
		
		tdProviderGoods.setSubGoodsTitle(subGoodsTitle);
		tdProviderGoods.setOutFactoryPrice(goodsPrice);
		tdProviderGoods.setGoodsMarketPrice(goodsMarketPrice);
		tdProviderGoods.setLeftNumber(leftNumber);
		tdProviderGoods.setCode(code);
		tdProviderGoods.setShopReturnRation(shopReturnRation);
		tdProviderGoods.setUnit(unit);
		
		tdProviderGoods.setProviderTitle(provider.getTitle());
		
		provider.getGoodsList().add(tdProviderGoods);
		tdProviderService.save(provider);
		
		if(null != sup_goodsId){ // 修改信息，同步超市代理商品信息
			List<TdDistributorGoods> list = tdDistributorGoodsService.findByProviderIdAndGoodsIdAndIsDistributionTrue(provider.getId(),tdProviderGoods.getGoodsId());
			if(null != list && list.size() >0){
				for (TdDistributorGoods tdDistributorGoods : list) {
					if(null != tdDistributorGoods)
					{
						tdDistributorGoods.setGoodsPrice(goodsPrice);
						tdDistributorGoods.setSubGoodsTitle(subGoodsTitle);
						tdDistributorGoods.setLeftNumber(leftNumber);
						tdDistributorGoods.setUnit(unit);
						tdDistributorGoods.setGoodsMarketPrice(goodsMarketPrice);
						
						
						tdDistributorGoodsService.save(tdDistributorGoods);
					}
				}
			}
		}
		
		res.put("msg", "操作成功");
		res.put("code",1);
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
		
//		if(null ==categoryId)
//		{
		PageRequest pageRequest =null;
		if(null != dir && 1== dir){
			pageRequest = new PageRequest(page, ClientConstant.pageSize,new Sort(Direction.DESC, "leftNumber"));
		}else if(null != dir && 2==dir){
			pageRequest = new PageRequest(page, ClientConstant.pageSize,new Sort(Direction.ASC, "leftNumber"));
		}else{
			pageRequest = new PageRequest(page, ClientConstant.pageSize,new Sort(Direction.DESC, "id"));
		}
		
		if(isDistribution)
		{
			map.addAttribute("supply_goods_page",tdProviderGoodsService.findAll(provider.getId(),categoryId, keywords, true, null, pageRequest));
		}
		else 
		{
			map.addAttribute("supply_goods_page",tdProviderGoodsService.findAll(provider.getId(),categoryId, keywords, false, null, pageRequest));
		}
		
		return "/client/supply_goods";
	}
	
	//  分销、取消
	@RequestMapping(value="/goods/onsale",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> providerGoodsDelete(Long pgId,
			Boolean type,
			HttpServletRequest req,ModelMap map)
	{
		Map<String,Object> res= new HashMap<String, Object>();
		res.put("code",0);
		
		String username = (String)req.getSession().getAttribute("supply");
		if(null == username)
		{
			res.put("msg","登录超时");
			return res;
		}
		TdProvider provider = tdProviderService.findByUsername(username);
		
		if(null != pgId)
		{
			TdProviderGoods providerGoods = tdProviderGoodsService.findOne(pgId);
			if(null != type && null != providerGoods){
				providerGoods.setIsDistribution(type);
				tdProviderGoodsService.save(providerGoods);
				
				if(null != type && type ==false){
					// 取消分销后 超市商品库删除分销商品
					List<TdDistributorGoods> list = tdDistributorGoodsService.findByProviderIdAndGoodsIdAndIsDistributionTrue(provider.getId(),providerGoods.getGoodsId());
					if(null != list && list.size() >0){
						for (TdDistributorGoods tdDistributorGoods : list) {
							tdDistributorGoodsService.delete(tdDistributorGoods);
						}
					}
				}
				
				res.put("code", 1);
				res.put("msg", "操作成功");
				return res;
			}
		}
		res.put("msg","参数错误");
		return res;
	}
	
	/**
	 * 删除分销商品
	 * 
	 */
	@RequestMapping(value="/goods/delete",method=RequestMethod.POST)
	@ResponseBody
	public  Map<String,Object> deleteGoods(Long pgId,
			HttpServletRequest req,ModelMap map){
		Map<String,Object> res  = new HashMap<String, Object>();
		res.put("code", 0);
		
		String username = (String)req.getSession().getAttribute("supply");
		if(null == username)
		{
			res.put("msg", "登录超时");
			return res;
		}
		if(null != pgId)
		{
			TdProviderGoods providerGoods = tdProviderGoodsService.findOne(pgId);
			if(null != providerGoods){
				// 查找删除商品规格 
				List<TdSpecificat> list = tdSpecificatService.findByShopIdAndGoodsIdAndType(providerGoods.getProId(), providerGoods.getGoodsId(), 3);
				if(null != list){
					tdSpecificatService.delete(list);
				}
				
			}
			tdProviderGoodsService.delete(pgId);
			res.put("code", 1);
			res.put("msg", "操作成功");
		}else{
			res.put("msg", "参数错误");
		}
		
		return res;
	}
	
	@RequestMapping(value="/goods/editOnSale",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> editOnSale(Long goodsId,Double outFactoryPrice,
							Long leftNumber,Double shopReturnRation,
							String code,String subTitle,String unit,
							Double goodsMarketPrice,
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
		proGoods.setUnit(unit);
		if(null !=goodsMarketPrice ){
			proGoods.setGoodsMarketPrice(goodsMarketPrice);
		}
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
					tdDistributorGoods.setUnit(unit);
					tdDistributorGoods.setGoodsMarketPrice(goodsMarketPrice);
					
					
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
		tdCommonService.setHeader(map, req);
		if(null == id)
		{
			return "/client/error_404";
		}
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
					tdOrderService.shopEditOrder(order);
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
	 * @throws ParseException 
	 * 
	 */
	@RequestMapping(value="/pay/record")
    public String payRecord(Integer page,
    		String cont, 
    		String startTime,
			String endTime,
			String eventTarget,
			String eventArgument,
    		HttpServletRequest req,HttpServletResponse resp,
    		ModelMap map) throws ParseException{
    	String username = (String)req.getSession().getAttribute("supply");
    	if (null == username) {
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
		
		String exportUrl ="";
		if(null != eventTarget){
			if(eventTarget.equalsIgnoreCase("excel")){
				exportUrl = SiteMagConstant.backupPath;
			}else if (eventTarget.equalsIgnoreCase("btnPage"))
			{
				if (null != eventArgument)
				{
					page = Integer.parseInt(eventArgument);
				} 
			}
		}
    	if(null == page ){
    		page = 0;
    	}
    	map.addAttribute("page", page);
    	map.addAttribute("cont", cont);
    	map.addAttribute("startTime", start);
    	map.addAttribute("endTime", end);
    	tdCommonService.setHeader(map, req);
    	
    	
    	TdProvider provider = tdProviderService.findByUsername(username);
    	map.addAttribute("pay_record_page",tdPayRecordService.findAll("pro", provider.getId(), cont, start, end, page, ClientConstant.pageSize));
//    	
//    	if(null == cont || "".equals(cont)){
//    		map.addAttribute("pay_record_page",
//    				tdPayRecordService.findByProviderId(provider.getId(), page, ClientConstant.pageSize));
//    	}else{
//    		map.addAttribute("pay_record_page",
//    				tdPayRecordService.searchByProviderId(provider.getId(),cont, page, ClientConstant.pageSize));
//    	}
    	
    	if(null != exportUrl && !"".equals(exportUrl)){
    		/**
			 * 导出表格
			 */
			// 创建一个webbook 对于一个Excel
			HSSFWorkbook wb = new HSSFWorkbook();
			// 在webbook中添加一个sheet,对应Excel文件中的sheet 
			HSSFSheet sheet = wb.createSheet("payRecord"); 
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
	        cell.setCellValue("商家");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 2);  
	        cell.setCellValue("服务费");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 3);  
	        cell.setCellValue("物流费");  
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 4);  
	        cell.setCellValue("商品总额");  
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 5);  
	        cell.setCellValue("商家返利");  
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 6);  
	        cell.setCellValue("实际金额");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 7);  
	        cell.setCellValue("交易时间");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 8);  
	        cell.setCellValue("交易类型");  
	        cell.setCellStyle(style); 
	        
        	Page<TdPayRecord> record_Page = tdPayRecordService.findAll("pro", provider.getId(), cont, start, end, page, Integer.MAX_VALUE);
        	
			if(payRecordImportData(record_Page,row,cell,sheet))
        	{
        		FileDownUtils.download("payRecord", wb, exportUrl, resp);
        	}
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
    		Long statusId,
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
    	if(null == statusId)
		{
			statusId = 0L;
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
		
		List<TdOrder> list = tdOrderService.searchOrderGoods(null, supply.getId(),null,"supply",statusId,start, end);
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
		map.addAttribute("status_id", statusId);
		
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
    	String username = (String)req.getSession().getAttribute("supply");
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
    
    /**
     * 加载信息录入
     * 
     */
    @RequestMapping(value="/draw/from")
    public String drawFrom(Long bankId,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("supply");
    	
    	map.addAttribute("supply", tdProviderService.findByUsername(username));
    	
    	if(null != bankId){
    		map.addAttribute("bank", tdBankService.findOne(bankId));
    	}
    	return "/client/supply_draw_from";
    }
    
    /**
     * 加载卡号信息
     * 
     */
    @RequestMapping(value="/search/bank")
    public String bankList(HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("supply");
    	
    	
    	if(null != username){
    		map.addAttribute("bankList", tdBankService.findAll(username, 4));
    	}
    	return "/client/supply_draw_list";
    }
    
    @RequestMapping(value="/delete/bank",method =RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteBank(Long bankId,HttpServletRequest req){
    	Map<String,Object> res = new HashMap<>();
    	res.put("code", 0);
    	
    	String username = (String)req.getSession().getAttribute("supply");
    	if(null == username)
    	{
    		res.put("msg", "请重新登录");
    		return res;
    	}
    	
    	if(null != bankId){
    		tdBankService.delete(bankId);
    		res.put("code", 1);
    	}else{
    		res.put("msg", "参数错误");
    	}
    	
    	return res;
    }
    
    @RequestMapping(value="/drwa2",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> userDrwa(String card,Double price,
    		String payPassword,String bank,String name,
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
    	if(null == supply)
    	{
    		res.put("msg", "参数错误");
        	return res;
    	}
    	
    	if(null == price)
    	{
    		res.put("msg", "请输入金额");
    		return res;
    	}
    	
    	if(price < 100){
    		res.put("msg", "提现金额必须大于100");
    		return res;
    	}
    	
    	if(null == supply.getVirtualMoney() || price > supply.getVirtualMoney()){
    		res.put("msg", "账户余额不足");
    		return res;
    	}
    	
    	if(null == payPassword || !payPassword.equalsIgnoreCase(supply.getPayPassword()))
    	{
    		res.put("msg", "密码错误");
    		return res;
    	}
    	
    	
		Date current = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String curStr = sdf.format(current);
    	Random random = new Random();
    	
		
		TdCash cash = new TdCash();
		
		cash.setCard(card);
		cash.setBank(bank);
		cash.setName(name);
		cash.setPrice(price);
		cash.setCreateTime(new Date());
		cash.setCashNumber("FX"+curStr+leftPad(Integer.toString(random.nextInt(999)), 3, "0"));
		cash.setShopTitle(supply.getTitle());
		cash.setUsername(username);
		cash.setShopType(3L);
		cash.setType(2L);
		cash.setStatus(1L);
		
		tdCashService.save(cash);
		
		tdCashService.beforeCash(cash); // 提现先扣余额
		
		// 新加银行卡信息记录
		supply.setBankCardCode(card);
		supply.setBankTitle(bank);
		supply.setBankName(name);
		tdProviderService.save(supply);
		
		boolean bankUser = true;
		List<TdBank> bankList = tdBankService.findAll(username, 4);
		if(null != bank){
			for (TdBank tdBank : bankList) {
				if(null == tdBank || card.equals(tdBank.getBankCard())){
					bankUser = false;
				}
			}
		}
		// 如果卡号为使用，添加记录
		if(bankUser){
			TdBank tdBank = new TdBank();
			
			tdBank.setUsername(username);
			tdBank.setType(4);
			tdBank.setBankCard(card);
			tdBank.setBankName(bank);
			tdBank.setName(name);
			
			tdBankService.save(tdBank);
		}
		
		res.put("msg", "提交成功");
		res.put("code", 1);
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
	
	@RequestMapping("/user/return")
	public String userReturn(Integer page,HttpServletRequest req,ModelMap map){
		String username = (String)req.getSession().getAttribute("supply");
		if(null == username){
			return "redirect:/login;";
		}
		
		tdCommonService.setHeader(map, req);
		
		TdProvider supply = tdProviderService.findByUsername(username);
		if(null == supply){
			return "/client/error_404";
		}
		
		if(null == page){
			page = 0;
		}
		
		PageRequest pageRequest = new PageRequest(page, ClientConstant.pageSize);
		
		map.addAttribute("turn_page", tdUserReturnService.findAll(supply.getId(), 2, pageRequest));
		
		return "/client/supply_return_list";
	}
	
	@RequestMapping("/return/detail")
	public String returnDetail(Long id,HttpServletRequest req,ModelMap map){
		String username = (String)req.getSession().getAttribute("supply");
		if(null == username){
			return "redirect:/login;";
		}
		tdCommonService.setHeader(map, req);
		
		if(null == id){
			return "/client/error_404";
		}
		
		map.addAttribute("userRturn", tdUserReturnService.findOne(id));
		return "/client/supply_return_detail";
	}
	
	@RequestMapping(value="/return/param/edit")
    @ResponseBody
    public Map<String,Object> returnedit(Long id,
    		Long statusId,String suppDetail,Double realPrice,
    		HttpServletRequest req){
    	Map<String,Object> res =new HashMap<>();
    	res.put("code",1);
		String username = (String)req.getSession().getAttribute("supply");
		
		if (null == username)
        {
            res.put("message", "登录超时！");
            return res;
        }
		
		TdProvider supply = tdProviderService.findByUsername(username);
		
		if(null != supply)
		{
			if(null != id)
			{
				TdUserReturn e = tdUserReturnService.findOne(id);
				if(null != e && e.getStatusId()==1)
				{
					e.setStatusId(statusId);
					e.setSuppDetail(suppDetail);
					e.setRealPrice(realPrice);
					e = tdUserReturnService.save(e);
					
					if(statusId ==3){
						if(null != supply.getVirtualMoney()&&  supply.getVirtualMoney() > e.getGoodsPrice()*e.getReturnNumber())
						{
							turnGoods(e, supply);
						}else{
							res.put("message", "账户余额不足");
							return res;
						}
					}
					
	            	res.put("message", "已处理此次退货！");
	            	res.put("code", 0);
	            	return res;
				}
			}
		}
		res.put("message", "参数错误！");
    	return res;
    }
	
	/**
     * 找回支付密码
     * 第一步
     */
    @RequestMapping("/retrieve_step1")
    public String retrieve1(String username,String mobile,Integer errCode,HttpServletRequest req,ModelMap map){
    	String supply = (String)req.getSession().getAttribute("supply");
    	if(null == supply)
    	{
    		return "redirect:/login";
    	}
    	tdCommonService.setHeader(map, req);
    	
    	map.addAttribute("supply", tdProviderService.findByUsername(supply));
    	
    	if (null != errCode)
	     {
	         if (errCode.equals(1))
	         {
	             map.addAttribute("error", "验证码错误");
	         }
	         
	         map.addAttribute("errCode", errCode);
	     }
		
		map.addAttribute("username", username);
		map.addAttribute("mobile", mobile);
    	
    	return "/client/supply_retrieve_one";
    	
    }
    
    /**
     * 找回支付密码
     * 第二步，账号、手机号验证码验证
     */
    @RequestMapping(value = "/retrieve_step2", method = RequestMethod.POST)
	public String Step2(String username,String mobile,String smsCode,HttpServletRequest req, ModelMap map){
    	String supply = (String)req.getSession().getAttribute("supply");
    	if(null == supply)
    	{
    		return "redirect:/login";
    	}
    	if (null == smsCode) {
			return "redirect:/supply/retrieve_step1?errCode=4&username="+username+"&mobile="+mobile;
		}
		String smsCodeSave = (String) req.getSession().getAttribute("SMSCODE");
		if(null == smsCodeSave){
			return "redirect:/supply/retrieve_step1?errCode=3&username="+username+"&mobile="+mobile;
		}
		
		if (!smsCodeSave.equalsIgnoreCase(smsCode)) {
			return "redirect:/supply/retrieve_step1?errCode=4&username="+username+"&mobile="+mobile;
		}
		tdCommonService.setHeader(map, req);
		
		map.addAttribute("supply", tdProviderService.findByUsername(supply));
		map.addAttribute("username", username);
		map.addAttribute("mobile", mobile);
		
		return "/client/supply_retrieve_two";
	}
    /**
     * 找回支付密码
     * 第三步，设置新密码
     */
   @RequestMapping(value = "/retrieve_step3", method = RequestMethod.POST)
	public String Step3(String username,String payPassword, HttpServletRequest req, ModelMap map){
	   TdProvider supply = tdProviderService.findByUsername(username);
		tdCommonService.setHeader(map, req);
		if (null != supply) {
			supply.setPayPassword(payPassword);
			tdProviderService.save(supply);
			
			return "/client/supply_retrieve_three";
		}
		
		return "/client/error_404";
	}
	
    /**
     * 验证账号手机号，发送短信验证码
     * 
     */
     @RequestMapping(value = "/smscode",method = RequestMethod.POST)
     @ResponseBody
     public Map<String, Object> smsCode(String username,String mobile, HttpServletResponse response, HttpServletRequest request) {
     	HashMap<String, Object> map = new HashMap<>();
     	map.put("code", 1);
     	
     	TdProvider provider =  tdProviderService.findByUsername(username);
     	if(null == provider)
     	{
     			map.put("msg", "账号不存在");
     			return map;
     	}
     	
     	if(null == mobile || !mobile.equals(provider.getMobile())){
     		map.put("msg", "账号和手机号不匹配");
     		return map;
     	}
     	
     	Random random = new Random();
         
         String smscode = String.format("%04d", random.nextInt(9999));
         
         HttpSession session = request.getSession();
         
         session.setAttribute("SMSCODE", smscode);
         session.setMaxInactiveInterval(60*10*1000);
         
         map = SMSUtil.send(mobile, "73697" ,new String[]{smscode});
         map.put("status", "0");
         map.put("msg" ,"验证码发送成功!");
         map.put("code", smscode);
         return map;
         
     }
    
	
	/**
     * 分销商同意普通商品退货，
     * 退还商品货款，扣除超市相应余额
     */
    public void turnGoods(TdUserReturn userRturn,TdProvider supply){
    	Double turnPrice =0.0; // 退款金额
    	
    	turnPrice = userRturn.getRealPrice();
    	if(null != supply.getVirtualMoney()&&  supply.getVirtualMoney() > turnPrice)
		{
    		
    		// 扣除超市余额
    		supply.setVirtualMoney(supply.getVirtualMoney()- turnPrice);
        	tdProviderService.save(supply);
        		
        		
    		TdUser user = tdUserService.findByUsername(userRturn.getUsername());
            if(null != user)
            {
            	if(null != user.getVirtualMoney())
            	{
            		user.setVirtualMoney(user.getVirtualMoney()+turnPrice);
            	}else{
            		user.setVirtualMoney(turnPrice);
            	}
            }
            
            tdUserService.save(user);
                
                
             // 添加会员虚拟账户金额记录
        	TdPayRecord record = new TdPayRecord();
        	
        	record.setAliPrice(0.0);
        	record.setPostPrice(0.0);
        	record.setRealPrice(turnPrice);
        	record.setTotalGoodsPrice(turnPrice);
        	record.setServicePrice(0.0);
        	record.setProvice(userRturn.getGoodsPrice());
        	record.setOrderNumber(userRturn.getOrderNumber());
        	record.setCreateTime(new Date());
        	record.setUsername(user.getUsername());
        	record.setType(2L);
        	record.setCont("退货返款");
        	record.setDistributorTitle(userRturn.getShopTitle());
        	record.setStatusCode(1);
        	tdPayRecordService.save(record); // 保存会员虚拟账户记录
        	
        	record = new TdPayRecord();
        	
        	record.setAliPrice(0.0);
        	record.setPostPrice(0.0);
        	record.setServicePrice(0.0);
        	record.setRealPrice(turnPrice);
        	record.setTotalGoodsPrice(turnPrice);
        	record.setProvice(userRturn.getGoodsPrice());
        	record.setOrderNumber(userRturn.getOrderNumber());
        	record.setCreateTime(new Date());
        	record.setCont("用户退货返款");
        	record.setDistributorTitle(userRturn.getShopTitle());
        	record.setProviderId(supply.getId());
    		record.setProviderTitle(supply.getTitle());
        	record.setStatusCode(1);
        	tdPayRecordService.save(record); // 保存超市退款记录
		}
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
			row.createCell((short) 4).setCellValue(StringUtils.scale(order.getTotalPrice()));
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
			row.createCell((short) 4).setCellValue(StringUtils.scale(countSale.getPrice()));
			row.createCell((short) 5).setCellValue(StringUtils.scale(countSale.getTotalPrice()));
		}
		return true;
	}
	
	// 交易記錄
		@SuppressWarnings("deprecation")
		public Boolean payRecordImportData(Page<TdPayRecord> recordPage,HSSFRow row, HSSFCell cell, HSSFSheet sheet)
		{
			if(null != recordPage && recordPage.getContent().size() > 0){
				for (int i = 0; i < recordPage.getContent().size(); i++) {
					row = sheet.createRow((int)i+1);
					TdPayRecord record = recordPage.getContent().get(i);
					// 获取用户信息
					row.createCell((short) 0).setCellValue(record.getOrderNumber());
					row.createCell((short) 1).setCellValue(record.getProviderTitle());
					row.createCell((short) 2).setCellValue(StringUtils.scale(record.getServicePrice()));
					row.createCell((short) 3).setCellValue(StringUtils.scale(record.getPostPrice()));
					row.createCell((short) 4).setCellValue(StringUtils.scale(record.getTotalGoodsPrice()));
					row.createCell((short) 5).setCellValue(StringUtils.scale(record.getTurnPrice()));
					row.createCell((short) 6).setCellValue(StringUtils.scale(record.getRealPrice()));
					row.createCell((short) 7).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(record.getCreateTime()));
					row.createCell((short) 8).setCellValue(record.getCont());
				}
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
	
//	public void addVir(TdOrder tdOrder)
//    {
//    	Double price = 0.0; // 交易总金额
//        Double postPrice = 0.0;  // 物流费
//        Double aliPrice = 0.0;	// 第三方使用费
//        Double servicePrice = 0.0;	// 平台服务费
//        Double totalGoodsPrice = 0.0; // 商品总额
//        Double realPrice = 0.0; // 商家实际收入
//        Double turnPrice = 0.0; // 分销单超市返利
//
//        price += tdOrder.getTotalPrice();
//        postPrice += tdOrder.getPostPrice();
//        aliPrice += tdOrder.getAliPrice();
//        servicePrice +=tdOrder.getTotalPrice();
//        totalGoodsPrice += tdOrder.getTotalGoodsPrice();
//        
//        
//        // 添加商家余额及交易记录
//        if(0==tdOrder.getTypeId())
//        {
//        	
//        	TdDistributor distributor = tdDistributorService.findOne(tdOrder.getShopId());
//        	if(null != distributor)
//        	{	
//        		// 超市普通销售单实际收入： 交易总额-第三方使用费-平台服务费=实际收入
//        		realPrice +=price-aliPrice-servicePrice;
//        		
//        		distributor.setVirtualMoney(distributor.getVirtualMoney()+realPrice); 
//        		tdDistributorService.save(distributor);
//        		
//        		TdPayRecord record = new TdPayRecord();
//        		record.setCont("订单销售款");
//        		record.setCreateTime(new Date());
//        		record.setDistributorId(distributor.getId());
//        		record.setDistributorTitle(distributor.getTitle());
//        		record.setOrderId(tdOrder.getId());
//        		record.setOrderNumber(tdOrder.getOrderNumber());
//        		record.setStatusCode(1);
//        		record.setProvice(price); // 交易总额
//        		record.setTotalGoodsPrice(totalGoodsPrice); // 商品总额
//        		record.setPostPrice(postPrice);	// 邮费
//        		record.setAliPrice(aliPrice);	// 第三方使用费
//        		record.setServicePrice(servicePrice);	// 平台服务费
//        		record.setRealPrice(realPrice); // 实际收入
//        		
//        		tdPayRecordService.save(record);
//        	}
//        }
//        else if(2 == tdOrder.getTypeId())
//        {
//        	TdDistributor distributor = tdDistributorService.findOne(tdOrder.getShopId());
//        	TdProvider provider = tdProviderService.findOne(tdOrder.getProviderId());
//        	
//        	turnPrice = tdOrder.getTotalLeftPrice();
//        	if(null != distributor)
//        	{
//        		
//        		distributor.setVirtualMoney(distributor.getVirtualMoney()+turnPrice); // 超市分销单收入为分销返利额
//        		tdDistributorService.save(distributor);
//        		
//        		TdPayRecord record = new TdPayRecord();
//        		record.setCont("代售获利");
//        		record.setCreateTime(new Date());
//        		record.setDistributorId(distributor.getId());
//        		record.setDistributorTitle(distributor.getTitle());
//        		record.setOrderId(tdOrder.getId());
//        		record.setOrderNumber(tdOrder.getOrderNumber());
//        		record.setStatusCode(1);
//        		record.setProvice(price); // 订单总额
//        		record.setTurnPrice(turnPrice); // 超市返利
//        		record.setRealPrice(turnPrice); // 超市实际收入
//        		tdPayRecordService.save(record);
//        	}
//        	if(null != provider)
//        	{
//        		// 分销商实际收入：商品总额-第三方使用费-邮费-超市返利-平台费 
//        		realPrice += price-aliPrice-postPrice-turnPrice-servicePrice;
//        		
//        		provider.setVirtualMoney(provider.getVirtualMoney()+realPrice);
//        		
//        		TdPayRecord record = new TdPayRecord();
//                record.setCont("分销收款");
//                record.setCreateTime(new Date());
//                record.setDistributorId(distributor.getId());
//                record.setDistributorTitle(distributor.getTitle());
//                record.setProviderId(provider.getId());
//                record.setProviderTitle(provider.getTitle());
//                record.setOrderId(tdOrder.getId());
//                record.setOrderNumber(tdOrder.getOrderNumber());
//                record.setStatusCode(1);
//                
//                record.setProvice(price); // 订单总额
//                record.setPostPrice(postPrice); // 邮费
//                record.setAliPrice(aliPrice);	// 第三方费
//                record.setServicePrice(servicePrice);	// 平台费
//                record.setTotalGoodsPrice(totalGoodsPrice); // 商品总价
//                record.setTurnPrice(turnPrice); // 超市返利
//                record.setRealPrice(realPrice); // 实际获利
//                tdPayRecordService.save(record);
//        	}
//        }
//
//        TdSetting setting = tdSettingService.findTopBy();
//        if( null != setting.getVirtualMoney())
//        {
//        	setting.setVirtualMoney(setting.getVirtualMoney()+servicePrice+aliPrice);
//        }else{
//        	setting.setVirtualMoney(servicePrice+aliPrice);
//        }
//        tdSettingService.save(setting); // 更新平台虚拟余额
//        
//        // 记录平台收益
//        TdPayRecord record = new TdPayRecord();
//        record.setCont("商家销售抽取");
//        record.setCreateTime(new Date());
//        record.setDistributorTitle(tdOrder.getShopTitle());
//        record.setOrderId(tdOrder.getId());
//        record.setOrderNumber(tdOrder.getOrderNumber());
//        record.setStatusCode(1);
//        record.setType(1L); // 类型 区分平台记录
//        
//        record.setProvice(price); // 订单总额
//        record.setPostPrice(postPrice); // 邮费
//        record.setAliPrice(aliPrice);	// 第三方费
//        record.setServicePrice(servicePrice);	// 平台费
//        record.setTotalGoodsPrice(totalGoodsPrice); // 商品总价
//        record.setTurnPrice(turnPrice); // 超市返利
//        // 实际获利 =平台服务费+第三方费
//        record.setRealPrice(servicePrice+aliPrice);
//        
//        tdPayRecordService.save(record);
//    }
}
