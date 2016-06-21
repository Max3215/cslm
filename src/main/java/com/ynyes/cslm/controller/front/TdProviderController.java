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
import com.ynyes.cslm.service.TdCashService;
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
import com.ynyes.cslm.util.SiteMagConstant;

/**
 * 批发商
 * @author libiao
 *
 */

@Controller
@RequestMapping(value="/provider")
public class TdProviderController extends AbstractPaytypeController{
	
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
	
	@Autowired
	private TdCashService tdCashService;
	
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
				tdOrderService.countByShopIdAndTypeIdAndStatusId(provider.getId(), 1, 3));
		map.addAttribute("total_unreceived",
				tdOrderService.countByShopIdAndTypeIdAndStatusId(provider.getId(), 1, 4));
		map.addAttribute("total_finished",
				tdOrderService.countByShopIdAndTypeIdAndStatusId(provider.getId(), 1, 6));
		map.addAttribute("provider_order_page",
				tdOrderService.findByShopIdAndTypeId(provider.getId(),1, 0,ClientConstant.pageSize ));
		
		return "/client/provider_index";
	}
	
	@RequestMapping(value="/order/list")
	public String orderList(
			Integer statusId,
			String keywords,
			Integer page,
			String startTime,String endTime,
			String eventTarget,
			HttpServletRequest req,
			HttpServletResponse resp,
			ModelMap map) throws ParseException
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
		
		Page<TdOrder> orderPage=tdOrderService.findAll(provider.getId(), statusId, 1,start, end, page, ClientConstant.pageSize);

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
	        cell.setCellValue("名称");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 2);  
	        cell.setCellValue("收件商");  
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
			
          	Page<TdOrder> order_page = tdOrderService.findAll(provider.getId(), statusId, 1,start, end, page, size);
          	if(orderImport(order_page, row, cell, sheet))
          	{
          		download(wb,"order", excelUrl, resp);
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
				if(order.getStatusId().equals(3L))
				{
					order.setStatusId(4L);
					order.setDeliveryTime(new Date());
				}
			}
			else if(type.equalsIgnoreCase("orderService"))
			{
				if(order.getStatusId().equals(4L))
				{
					order.setStatusId(6L);
					order.setFinishTime(new Date());
					
					TdDistributor distributor = tdDistributorService.findbyUsername(order.getUsername());
					List<TdOrderGoods> goodsList = order.getOrderGoodsList();
					for (TdOrderGoods tdOrderGoods : goodsList) {
						
						TdDistributorGoods distributorGoods = tdDistributorGoodsService.findByDistributorIdAndGoodsId(distributor.getId(), tdOrderGoods.getGoodsId());
						
						if(null == distributorGoods)
						{
							TdGoods goods = tdGoodsService.findOne(tdOrderGoods.getGoodsId());
//							tdProviderGoodsService.findByProviderIdAndGoodsId(, goodsId)
							distributorGoods = new TdDistributorGoods();
							
							distributorGoods.setDistributorTitle(distributor.getTitle());
							distributorGoods.setGoodsId(goods.getId());
							distributorGoods.setGoodsTitle(goods.getTitle());
//							distributorGoods.setGoodsPrice();
							distributorGoods.setBrandId(goods.getBrandId());
							distributorGoods.setBrandTitle(goods.getBrandTitle());
							distributorGoods.setCategoryId(goods.getCategoryId());
							distributorGoods.setCategoryIdTree(goods.getCategoryIdTree());
							distributorGoods.setCode(goods.getCode());
							distributorGoods.setCoverImageUri(goods.getCoverImageUri());
							distributorGoods.setGoodsMarketPrice(tdOrderGoods.getPrice());
							distributorGoods.setIsDistribution(false);
//						distributorGoods.setGoodsParamList(goods.getParamList());
							distributorGoods.setReturnPoints(goods.getReturnPoints());
							distributorGoods.setParamValueCollect(goods.getParamValueCollect());
							distributorGoods.setIsOnSale(false);
							distributorGoods.setLeftNumber(tdOrderGoods.getQuantity());
							distributorGoods.setUnit(goods.getSaleType());
						}else{
							
							distributorGoods.setLeftNumber(distributorGoods.getLeftNumber()+tdOrderGoods.getQuantity());
						}
						distributor.getGoodsList().add(distributorGoods);
					}
					distributor.setGoodsList(distributor.getGoodsList());
					tdDistributorService.save(distributor);
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
	
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
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
		
		String username = (String)req.getSession().getAttribute("provider");
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
		
		TdProvider provider = tdProviderService.findOne(id);
		
		provider.setTitle(title);
		provider.setProvince(province);
		provider.setCity(city);
		provider.setDisctrict(disctrict);
		provider.setAddress(address);
		provider.setMobile(mobile);
		provider.setPassword(password);
		provider.setPayPassword(payPassword);
		provider.setPostPrice(postPrice);
		
		tdProviderService.save(provider);
		
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
		
		String username = (String)req.getSession().getAttribute("provider");
		TdProvider provider = tdProviderService.findByUsername(username);
		
		if(null == provider)
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
			if(!password.equalsIgnoreCase(provider.getPassword()))
			{
				res.put("msg", "原密码输入错误");
				return res;
			}
		}else if(type.equalsIgnoreCase("payPwd")){
			if(null != provider.getPayPassword() && !password.equalsIgnoreCase(provider.getPayPassword()))
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
				provider.setPassword(newPassword);
			}else if(type.equalsIgnoreCase("payPwd"))
			{
				provider.setPayPassword(newPassword);
			}
		}
		
		tdProviderService.save(provider);
		
		res.put("msg", "修改成功");
		res.put("code", 1);
		return res;
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
			Integer dir,
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
		map.addAttribute("dir", dir);
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
				if(null == keywords || "".equals(keywords)){
					if(null == dir || dir ==0){
						map.addAttribute("provider_goods_page",
								tdProviderGoodsService.findByProviderIdAndIsOnSale(provider.getId(),isSale,page,ClientConstant.pageSize));
					}else if(1== dir){
						map.addAttribute("provider_goods_page",
								tdProviderGoodsService.findByProviderIdAndIsOnSaleOrderByLeftNumberDesc(provider.getId(),isSale,page,ClientConstant.pageSize));
					}else{
						map.addAttribute("provider_goods_page",
								tdProviderGoodsService.findByProviderIdAndIsOnSaleOrderByLeftNumberAsc(provider.getId(),isSale,page,ClientConstant.pageSize));
					}
				}else{
					if(null == dir || dir ==0){
						map.addAttribute("provider_goods_page",
								tdProviderGoodsService.searchAndProviderIdAndKeywordsAndIsOnSale(provider.getId(), keywords, isSale,page, ClientConstant.pageSize));
					}else if(1==dir){
						map.addAttribute("provider_goods_page",
								tdProviderGoodsService.searchAndProviderIdAndKeywordsAndIsOnSaleOrderByLeftNumberDesc(provider.getId(), keywords, isSale,page, ClientConstant.pageSize));
					}else{
						map.addAttribute("provider_goods_page",
								tdProviderGoodsService.searchAndProviderIdAndKeywordsAndIsOnSaleOrderByLeftNumberAsc(provider.getId(), keywords, isSale,page, ClientConstant.pageSize));
					}
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
				if(null == keywords || "".equals(keywords)){
					if(null == dir || dir == 0){
						map.addAttribute("provider_goods_page",
								tdProviderGoodsService.findByProviderIdAndCategoryIdAndIsOnSale(provider.getId(), categoryId,isSale, page, ClientConstant.pageSize));
					}else if(1== dir){
						map.addAttribute("provider_goods_page",
								tdProviderGoodsService.findByProviderIdAndCategoryIdAndIsOnSaleOrderByLeftNumberDesc(provider.getId(), categoryId,isSale, page, ClientConstant.pageSize));
					}else{
						map.addAttribute("provider_goods_page",
								tdProviderGoodsService.findByProviderIdAndCategoryIdAndIsOnSaleOrderByLeftNumberAsc(provider.getId(), categoryId,isSale, page, ClientConstant.pageSize));
					}
				}else{
					if(null == dir || 0 ==dir){
						map.addAttribute("provider_goods_page",
								tdProviderGoodsService.searchAndProviderIdAndCategoryIdAndKeywordsAndIsOnSale(provider.getId(), categoryId, keywords,isSale, page, ClientConstant.pageSize));
					}else if(1== dir){
						map.addAttribute("provider_goods_page",
								tdProviderGoodsService.searchAndProviderIdAndCategoryIdAndKeywordsAndIsOnSaleOrderByLeftNumberDesc(provider.getId(), categoryId, keywords,isSale, page, ClientConstant.pageSize));
					}else{
						map.addAttribute("provider_goods_page",
								tdProviderGoodsService.searchAndProviderIdAndCategoryIdAndKeywordsAndIsOnSaleOrderByLeftNumberAsc(provider.getId(), categoryId, keywords,isSale, page, ClientConstant.pageSize));
					}
					
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
			map.addAttribute("isOnSale", false);
			map.addAttribute("provider_goods_page",
					tdProviderGoodsService.findByProviderIdAndIsOnSale(provider.getId(), false, page,ClientConstant.pageSize));
		}else{
			providerGoods.setIsOnSale(type);
			tdProviderGoodsService.save(providerGoods);
			map.addAttribute("isOnSale", true);
			map.addAttribute("provider_goods_page",
					tdProviderGoodsService.findByProviderIdAndIsOnSale(provider.getId(), true, page,ClientConstant.pageSize));
		}
		
		map.addAttribute("provider", provider);
		return "/client/provider_goods_list";
	}
	
	@RequestMapping(value="/goods/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> edit(Long goodsId,Integer page,String subTitle, String code,
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
//		TdProvider provider = tdProviderService.findByUsername(username);
		
		providerGoods.setOutFactoryPrice(outFactoryPrice);
		providerGoods.setLeftNumber(leftNumber);
//		providerGoods.setIsOnSale(true);
		providerGoods.setSubGoodsTitle(subTitle);
		providerGoods.setCode(code);
		
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
		map.addAttribute("category", tdProductCategoryService.findOne(categoryId));
		
		List<TdProductCategory> categortList = tdProductCategoryService.findByParentIdIsNullAndIsEnableTrueOrderBySortIdAsc();
        map.addAttribute("category_list", categortList);
		
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
		
		return "/client/provider_goods_onsale";
	}
	
	@RequestMapping(value="/wholesaling",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> wholesaling(Long goodsId,
			String goodsTitle,
			String subTitle,
			Double outFactoryPrice,
			Double marketPrice,
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
			proGoods.setSubGoodsTitle(subTitle);
			proGoods.setSubGoodsTitle(goods.getSubTitle());
			proGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
			proGoods.setOutFactoryPrice(outFactoryPrice);
			proGoods.setGoodsMarketPrice(marketPrice);
			proGoods.setLeftNumber(leftNumber);
			proGoods.setOnSaleTime(new Date());
			proGoods.setIsOnSale(true);
			proGoods.setCode(goods.getCode());
			proGoods.setCategoryId(goods.getCategoryId());
			proGoods.setCategoryIdTree(goods.getCategoryIdTree());
			proGoods.setUnit(goods.getSaleType());
		}
		else
		{
			proGoods.setGoodsTitle(goodsTitle);
			proGoods.setSubGoodsTitle(subTitle);
			proGoods.setLeftNumber(leftNumber);
			proGoods.setGoodsMarketPrice(marketPrice);
			proGoods.setOutFactoryPrice(outFactoryPrice);
			proGoods.setUnit(goods.getSaleType());
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
     * 销售统计
     * @author Max
     * 
     */
    @RequestMapping(value="/order/sum")
    public String sumOrderGoods(String startTime,String endTime,
    		String eventTarget,HttpServletResponse resp,
    		HttpServletRequest req,ModelMap map) throws ParseException
    {
    	String username = (String)req.getSession().getAttribute("provider");
    	if(null == username)
		{
			return "redirect:/login";
		}
    	tdCommonService.setHeader(map, req);
    	
    	TdProvider provider = tdProviderService.findByUsername(username);
    	if(null == provider)
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
		
		List<TdOrder> list = tdOrderService.searchOrderGoods(provider.getId(),null,1L,start, end);
		List<TdCountSale> countList = tdOrderService.sumOrderGoods(provider.getId(),1L,list);
		
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
		
		return "/client/provider_sale";
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
    	// 支付方式列表
        setPayTypes(map, true, false, req);
    	
    	return "/client/provider_top_one";
    }
    
    
    @RequestMapping(value="/topup2",method=RequestMethod.POST)
    public String topupTwo(HttpServletRequest req,ModelMap map,
    		Double provice,Long payTypeId){
    	String username = (String)req.getSession().getAttribute("provider");
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
    	cash.setCashNumber("PF"+curStr+leftPad(Integer.toString(random.nextInt(999)), 3, "0"));
    	cash.setShopTitle(provider.getTitle());
    	cash.setUsername(username);
    	cash.setCreateTime(new Date());
    	cash.setPrice(provice); // 金额
    	cash.setShopType(2L); // 类型-批发
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
    	String username = (String)req.getSession().getAttribute("provider");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	tdCommonService.setHeader(map, req);
    	
    	map.addAttribute("provider",tdProviderService.findByUsername(username));
    	if(null != cashNumber)
    	{
    		map.addAttribute("cash", tdCashService.findByCashNumber(cashNumber));
    	}
    	
    	return "/client/provider_top_end";
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
    
    @RequestMapping(value="/drwa2",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> userDrwa(String card,Double price,String payPassword,
    			HttpServletRequest req){
    	Map<String,Object> res = new HashMap<>();
    	res.put("code", 0);
    	
    	String username = (String)req.getSession().getAttribute("provider");
    	if(null == username)
    	{
    		res.put("msg", "请重新登录");
    		return res;
    	}
    	
    	TdProvider provider = tdProviderService.findByUsername(username);
    	
    	if(null == price)
    	{
    		res.put("msg", "请输入金额");
    		return res;
    	}
    	
    	if(price < 100){
    		res.put("msg", "提现金额必须大于100");
    		return res;
    	}
    	
    	if(null == payPassword || !payPassword.equalsIgnoreCase(provider.getPayPassword()))
    	{
    		res.put("msg", "密码错误");
    		return res;
    	}
    	
    	if(null != provider)
    	{
    		Date current = new Date();
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        	String curStr = sdf.format(current);
        	Random random = new Random();
        	
    		
    		TdCash cash = new TdCash();
    		
    		cash.setCard(card);
    		cash.setPrice(price);
    		cash.setCreateTime(new Date());
    		cash.setCashNumber("PF"+curStr+leftPad(Integer.toString(random.nextInt(999)), 3, "0"));
    		cash.setShopTitle(provider.getTitle());
    		cash.setUsername(username);
    		cash.setShopType(2L);
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
    	
    	String username = (String)rep.getSession().getAttribute("provider");
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
					File file = new File(exportUrl+name + ".xls");
	                
	            if (file.exists())
	                {
	                  try {
	                        resp.reset();
	                        resp.setHeader("Content-Disposition", "attachment; filename="
	                                +name +".xls");
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
	
	
	
}
