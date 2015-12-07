package com.ynyes.cslm.controller.front;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.ynyes.cslm.entity.TdArticle;
import com.ynyes.cslm.entity.TdArticleCategory;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdOrderGoods;
import com.ynyes.cslm.entity.TdPayRecord;
import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdProviderGoods;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.entity.TdUserPoint;
import com.ynyes.cslm.service.TdArticleCategoryService;
import com.ynyes.cslm.service.TdArticleService;
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
import com.ynyes.cslm.util.SiteMagConstant;

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
							tdProviderGoodsService.findByProviderIdAndIsDistributionAndIsAudit(provider.getId(),true,true, page, ClientConstant.pageSize));
				}else{
					map.addAttribute("supply_goods_page",
							tdProviderGoodsService.searchAndProviderIdAndIsDistributionAndIsAudit(provider.getId(),true,true,keywords,page, ClientConstant.pageSize));
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
							tdProviderGoodsService.findByProviderIdAndCategoryIdAndIsDistributionAndIsAudit(provider.getId(), categoryId, true,true, page, ClientConstant.pageSize));
				}else{
					map.addAttribute("provider_goods_page",
							tdProviderGoodsService.searchAndProviderIdAndCategoryIdAndIsDistributionAndIsAudut(provider.getId(), keywords, categoryId, true,true, page, ClientConstant.pageSize));
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
		
		tdProviderGoodsService.delete(pgId);
		TdProvider provider = tdProviderService.findByUsername(username);
		map.addAttribute("supply_goods_page",
				tdProviderGoodsService.findByProviderIdAndIsDistributionAndIsAudit(provider.getId(),type,true, page, ClientConstant.pageSize));
		
		
		return "/client/supply_goods_list";
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
	 * 
	 */
	@RequestMapping(value="/disOrder/list/{statusId}")
	public String disOrder(@PathVariable Integer statusId,
			Integer statusid,
			String keywords,
			Integer page,
			Integer timeId,
			String eventTarget,
			HttpServletRequest req,
			HttpServletResponse resp,
			ModelMap map)
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
		if (null == timeId) {
            timeId = 0;
        }
		if(null != statusid){
        	statusId = statusid;
        }
		
		String excelUrl=null;
		if(null != eventTarget)
		{
			if("excel".equalsIgnoreCase(eventTarget))
			{
				excelUrl=SiteMagConstant.backupPath;
			}
		}
		
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
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndSearch(
                        		provider.getId(),2, keywords, page, ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByProviderIdAndTypeId(provider.getId(),2, page,
                            ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeId(provider.getId(),2, page,
                                ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService .findByProviderIdAndTypeIdAndStatusIdAndSearch(provider.getId(),2,statusId, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService .findByProviderIdAndTypeIdAndStatusIdAndSearch(provider.getId(),2,statusId, keywords, page,
                                ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndStatusId(
                    		provider.getId(),2,statusId, page, ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndStatusId(
                        		provider.getId(),2,statusId, page, ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
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
                    orderPage = tdOrderService .findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
                                    2,time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService .findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
                                2,time, keywords, page,
                                ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
                    		provider.getId(),2,time, page, ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
                        		provider.getId(),2,time, page, ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                            		provider.getId(),2, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                        		provider.getId(),2, statusId, time, keywords, page,
                                ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
                                   2, statusId, time, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
                                2, statusId, time, page,
                                ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
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
                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
                                   2, time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
                                2, time, keywords, page,
                                ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
                    		provider.getId(),2, time, page, ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
                        		provider.getId(),2, time, page, ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                            		provider.getId(),2, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                        		provider.getId(),2, statusId, time, keywords, page,
                                ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService .findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
                                    2,statusId, time, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService .findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
                                2,statusId, time, page,
                                ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
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
                    orderPage = tdOrderService .findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
                                    2,time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService .findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
                                2,time, keywords, page,
                                ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
                    		provider.getId(),2, time, page, ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
                        		provider.getId(),2, time, page, ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                            		provider.getId(),2, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                        		provider.getId(),2, statusId, time, keywords, page,
                                ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService .findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
                                   2, statusId, time, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService .findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
                                2, statusId, time, page,
                                ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
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
                    orderPage = tdOrderService .findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
                                    2,time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService .findByProviderIdAndTypeIdAndTimeAfterAndSearch(provider.getId(),
                                2,time, keywords, page,
                                ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
                    		provider.getId(),2, time, page, ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndTimeAfter(
                        		provider.getId(),2, time, page, ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                            		provider.getId(),2, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByProviderIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                        		provider.getId(),2, statusId, time, keywords, page,
                                ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService .findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
                                    2,statusId, time, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService .findByProviderIdAndTypeIdAndStatusIdAndTimeAfter(provider.getId(),
                                2,statusId, time, page,
                                ClientConstant.pageSize);
                    	if(orderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                }
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
	                   // 保存交易记录
	                   TdPayRecord record = new TdPayRecord();
	                   record.setCont("代售提成");
	                   record.setCreateTime(new Date());
	                   record.setDistributorId(distributor.getId());
	                   record.setDistributorTitle(distributor.getTitle());
//	                   record.setProviderId(provider.getId());
	                   record.setProviderTitle(provider.getTitle());
	                   record.setOrderId(order.getId());
	                   record.setOrderNumber(order.getOrderNumber());
	                   record.setStatusCode(1);
	                   record.setProvice(order.getTotalGoodsPrice());
	                   tdPayRecordService.save(record);
	                   
	                   // 批发商入帐
	                   if(null != provider){
	                	   provider.setVirtualMoney(provider.getVirtualMoney()+order.getTotalGoodsPrice()-platformService-trainService);
	                	   tdProviderService.save(provider);
	                   }
	                   // 保存交易记录
	                   record = new TdPayRecord();
	                   record.setCont("分销收款");
	                   record.setCreateTime(new Date());
//	                   record.setDistributorId(distributor.getId());
	                   record.setDistributorTitle(distributor.getTitle());
	                   record.setProviderId(provider.getId());
	                   record.setProviderTitle(provider.getTitle());
	                   record.setOrderId(order.getId());
	                   record.setOrderNumber(order.getOrderNumber());
	                   record.setStatusCode(1);
	                   record.setProvice(order.getTotalGoodsPrice());
	                   tdPayRecordService.save(record);
	                  
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
    	
    	map.addAttribute("page", page);
    	map.addAttribute("categoryId",categoryId);
    	map.addAttribute("keywords",keywords);
    	map.addAttribute("category_list",tdProductCategoryService.findAll());
    	
    	TdProvider provider = tdProviderService.findByUsername(username);
    	
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
    	
    	return "/client/supply_top_one";
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
	
	public Boolean download(HSSFWorkbook wb, String exportUrl, HttpServletResponse resp){
	   	 try  
	        {  
		          FileOutputStream fout = new FileOutputStream(exportUrl+"order.xls");  
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
					File file = new File(exportUrl + "order.xls");
	                
	            if (file.exists())
	                {
	                  try {
	                        resp.reset();
	                        resp.setHeader("Content-Disposition", "attachment; filename="
	                                + "order.xls");
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
