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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

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
import com.ynyes.cslm.entity.TdAd;
import com.ynyes.cslm.entity.TdArticle;
import com.ynyes.cslm.entity.TdArticleCategory;
import com.ynyes.cslm.entity.TdBank;
import com.ynyes.cslm.entity.TdCartGoods;
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
import com.ynyes.cslm.entity.TdRelevance;
import com.ynyes.cslm.entity.TdSetting;
import com.ynyes.cslm.entity.TdShippingAddress;
import com.ynyes.cslm.entity.TdSpecificat;
import com.ynyes.cslm.entity.TdTag;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.entity.TdUserCollect;
import com.ynyes.cslm.entity.TdUserReturn;
import com.ynyes.cslm.service.TdAdService;
import com.ynyes.cslm.service.TdAdTypeService;
import com.ynyes.cslm.service.TdArticleCategoryService;
import com.ynyes.cslm.service.TdArticleService;
import com.ynyes.cslm.service.TdBankService;
import com.ynyes.cslm.service.TdCartGoodsService;
import com.ynyes.cslm.service.TdCashService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDemandService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdOrderGoodsService;
import com.ynyes.cslm.service.TdOrderService;
import com.ynyes.cslm.service.TdPayRecordService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.service.TdProviderGoodsService;
import com.ynyes.cslm.service.TdProviderService;
import com.ynyes.cslm.service.TdRelevanceService;
import com.ynyes.cslm.service.TdSettingService;
import com.ynyes.cslm.service.TdShippingAddressService;
import com.ynyes.cslm.service.TdSpecificatService;
import com.ynyes.cslm.service.TdTagService;
import com.ynyes.cslm.service.TdUserCollectService;
import com.ynyes.cslm.service.TdUserPointService;
import com.ynyes.cslm.service.TdUserReturnService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.util.ClientConstant;
import com.ynyes.cslm.util.FileDownUtils;
import com.ynyes.cslm.util.SMSUtil;
import com.ynyes.cslm.util.SiteMagConstant;
import com.ynyes.cslm.util.StringUtils;


@Controller
@RequestMapping(value="/distributor")
public class TdDistributorController extends AbstractPaytypeController{
	
	@Autowired
	TdOrderService tdOrderService;
	
	@Autowired
	TdCommonService tdCommonService;
	
	@Autowired
	TdUserService tdUserService;
	
	@Autowired
	TdDistributorService tdDistributorService;
	
	@Autowired
	TdDistributorGoodsService tdDistributorGoodsService;
	
	@Autowired
    TdGoodsService tdGoodsService;
	
	@Autowired
	TdUserPointService tdUserPointService;
	
	@Autowired
	TdProviderGoodsService tdProviderGoodsService;
	
	@Autowired
	TdProviderService tdProviderService;
	
	@Autowired
	TdCartGoodsService tdCartGoodsService;
	
	@Autowired
	TdOrderGoodsService tdOrderGoodsService;
	
	@Autowired
	TdSettingService tdSettingService;
	
	@Autowired
	TdProductCategoryService tdProductCategoryService;
	
	@Autowired
	TdUserCollectService tdUserCollectService;
	
	@Autowired
	TdArticleCategoryService tdArticleCategoryService;
	
	@Autowired
	TdArticleService tdArticleService;
	
	@Autowired
	TdPayRecordService tdPayRecordService;
	
	@Autowired
	TdDemandService tdDemandService;
	
	@Autowired
	TdUserReturnService tdUserReturnService;
	
	@Autowired
	TdAdTypeService tdAdTypeService;
	
	@Autowired
	TdAdService tdAdService;
	
	@Autowired
	TdShippingAddressService tdShippingAddressService;
	
	@Autowired
	TdCashService tdCashService;
	
	@Autowired
	TdSpecificatService tdSpecificatService;
	
	@Autowired
	TdRelevanceService tdRelevanceService;
	
	@Autowired
	TdTagService tdTagService;
	
	@Autowired
	TdBankService tdBankService;
	
	@RequestMapping(value="/index")
	public String distributroindex(HttpServletRequest req, ModelMap map)
	{
		String username = (String) req.getSession().getAttribute("distributor");
		if (null == username) {
            return "redirect:/login";
        }
        
        tdCommonService.setHeader(map, req);
        
        TdDistributor distributor = tdDistributorService.findbyUsername(username);
        
        if(null == distributor){
        	return "/client/error_404";
        }
        
        map.addAttribute("distributor", distributor);
        
        map.addAttribute("dis_goodsIn_order_page",
        		tdOrderService.findByUsernameAndTypeIdOrderByIdDesc(distributor.getUsername(), 1, 0, 5));
        map.addAttribute("dis_goodsOut_order_page",
        		tdOrderService.findByShopIdAndTypeId(distributor.getId(), 0, 0, ClientConstant.pageSize));
        map.addAttribute("total_unpayed",
        		tdOrderService.countByShopIdAndTypeIdAndStatusId(distributor.getId(), 0, 2));
        map.addAttribute("total_undelivered",
        		tdOrderService.countByShopIdAndTypeIdAndStatusId(distributor.getId(), 0, 3));
        map.addAttribute("total_unreceived",
        		tdOrderService.countByShopIdAndTypeIdAndStatusId(distributor.getId(), 0, 4));
        map.addAttribute("total_finished",
        		tdOrderService.countByShopIdAndTypeIdAndStatusId(distributor.getId(), 0, 6));
        	
        
		return "/client/distributor_index";
	}
	
	
	
	@RequestMapping(value="/save", method = RequestMethod.GET)
	public String distributorSave(HttpServletRequest req,ModelMap map)
	{
		tdCommonService.setHeader(map, req);
		
		return "/client/distributor_save";
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)
	public String distributorSave(TdDistributor tdDistributor,HttpServletRequest req,ModelMap map)
	{
		tdCommonService.setHeader(map, req);
		tdDistributor.setVirtualMoney(new Double(0));
		tdDistributor.setIsEnable(false);
		tdDistributorService.save(tdDistributor);
		
		return "redirect:/";
	}
	
	
	/**
	 * 验证登录名和虚拟账号
	 * 
	 */
	@RequestMapping(value="/check/{type}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> validateForm(@PathVariable String type,String param)
	{
		 Map<String, String> res = new HashMap<String, String>();
		 res.put("status", "n");
		 
		 if(null == type)
		 {
			 res.put("info","参数错误");
			 return res;
		 }
		 //登录账号验证
		 if (type.equalsIgnoreCase("username"))
	        {
	        	if (null == param || param.isEmpty()) {
	                res.put("info", "用户名不能为空");
	                return res;
	            }
	        	
	        	TdUser user = tdUserService.findByUsername(param);
	        	
	        	if (null != user)
	        	{
	        		res.put("info", "该账号已经存在");
	                return res;
	        	}
	        }
		 //虚拟账号验证
		 if("virtualAccount".equalsIgnoreCase(type))
		 {
			 if (null == param || param.isEmpty()) {
	                res.put("info", "虚拟账户不能为空");
	                return res;
	            }
			 TdDistributor distributor = tdDistributorService.findByVirtualAccount(param);
			 if(null !=distributor){
				 res.put("info", "虚拟账号已被占用");
				 return res;
			 }
		 }
		 res.put("info", "通过信息验证！");
		 res.put("status", "y");
		 return res;
	}
	
	/**
	 * 超市已卖出商品
	 * 
	 */
	@RequestMapping(value="/sale")
	public String distributorSale(//Integer page,String keywords,
			String startTime,String endTime,
			Long statusId,
			Long shipAddressId,
			String eventTarget,HttpServletResponse resp,
			HttpServletRequest req,ModelMap map) throws ParseException
	{
		String username=(String)req.getSession().getAttribute("distributor");
		
		tdCommonService.setHeader(map, req);
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		
		if(null == username)
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
		map.addAttribute("addressList", distributor.getShippingList());
		map.addAttribute("shipAddressId", shipAddressId);
		
		List<TdOrder> list = tdOrderService.searchOrderGoods(distributor.getId(),null,shipAddressId,"dis",statusId,start, end);
		List<TdCountSale> countList = tdOrderService.sumOrderGoods(distributor.getId(),0L,list);
		
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
		
		map.addAttribute("distributor", distributor);
		map.addAttribute("startTime", start);
		map.addAttribute("endTime", end);
		map.addAttribute("status_id", statusId);
		
		return "/client/distributor_sale";
	}
	
	/**
	 *	选择超市，超市编号存入session以便后面调用
	 *
	 */
	@RequestMapping(value="/change" , method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> changeDistributor(HttpServletRequest req,Long disId)
	{
		Map<String,Object> res = new HashMap<>();
		if(null ==disId)
		{
			res.put("msg", "请选择超市");
			return res;
		}
		
		TdDistributor distributor = tdDistributorService.findOne(disId);
		 if(null == distributor)
		 {
			 res.put("msg","选择的超市不存在");
			 return res;
		 }
		 
		req.getSession().setAttribute("DISTRIBUTOR_ID", distributor.getId());
		req.getSession().setAttribute("distributorTitle", distributor.getTitle());
		return res;
	}
	
	/**
	 * 超市中心查看出售中/仓库中商品
	 * 
	 */
	@RequestMapping(value="/goods/sale/{isSale}")
	public String disGoodsSale(@PathVariable Boolean isSale,
			Integer dir,Long categoryId,
			String keywords, 
			Integer page,
			Long[] listId,
			Integer[] listChkId,
			String eventTarget,
			HttpServletRequest req,
			HttpServletResponse resp,
			ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		tdCommonService.setHeader(map, req);
		
		String excelUrl=null;
		if(null != eventTarget){
			if("btnSale".equalsIgnoreCase(eventTarget)){
				onSaleAll(listId,listChkId);
			}else if("excel".equalsIgnoreCase(eventTarget))
			{
				excelUrl=SiteMagConstant.backupPath;
			}
		}
		
		if(null == page )
		{
			page = 0;
		}
		
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		
		map.addAttribute("sort", dir);
		map.addAttribute("distributor", distributor);
		map.addAttribute("isOnSale", isSale);
		map.addAttribute("page",page);
		map.addAttribute("keywords", keywords);
		map.addAttribute("categoryId", categoryId);
		map.addAttribute("category", tdProductCategoryService.findOne(categoryId));
		
		
		List<TdProductCategory> categortList = tdProductCategoryService.findByParentIdIsNullAndIsEnableTrueOrderBySortIdAsc();
        map.addAttribute("category_list", categortList);
		
        PageRequest pageRequest = null;
        PageRequest excelPageRequest = null;
        if(null != dir && dir ==1)
		{
        	pageRequest = new PageRequest(page, 10,new Sort(Direction.ASC, "leftNumber"));
        	excelPageRequest = new PageRequest(page, Integer.MAX_VALUE,new Sort(Direction.ASC, "leftNumber"));
		}
        else if(null != dir && dir ==2)
		{
        	pageRequest = new PageRequest(page, 10,new Sort(Direction.DESC, "leftNumber"));
        	excelPageRequest = new PageRequest(page, Integer.MAX_VALUE,new Sort(Direction.DESC, "leftNumber"));
		}
        else
        {
        	pageRequest = new PageRequest(page, 10);
        	excelPageRequest = new PageRequest(page, Integer.MAX_VALUE);
        }
        
        map.addAttribute("dis_goods_page",tdDistributorGoodsService.findAll(distributor.getId(), isSale,false, categoryId, keywords, pageRequest));
		if(null != categoryId)
		{
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
		
		if(null != excelUrl && !"".equals(excelUrl)){
			/**
			 *  导出表格
			 */
			// 创建一个webbook 对于一个Excel
			HSSFWorkbook wb = new HSSFWorkbook();
			// 在webbook中添加一个sheet,对应Excel文件中的sheet 
			HSSFSheet sheet = wb.createSheet("goods"); 
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
	        cell.setCellValue("编码");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 3);  
	        cell.setCellValue("价格");  
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 4);  
	        cell.setCellValue("市场价");  
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 5);  
	        cell.setCellValue("分类");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 6);  
	        cell.setCellValue("品牌");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 7);  
	        cell.setCellValue("单位");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 8);  
	        cell.setCellValue("库存");  
	        cell.setCellStyle(style); 
	        
	        
	        
        	Page<TdDistributorGoods> goods_page = tdDistributorGoodsService.findAll(distributor.getId(), isSale,false, categoryId, keywords, excelPageRequest);
        	
        	if(disGoodsImport(goods_page,row,cell,sheet))
        	{
        		FileDownUtils.download("goods", wb, excelUrl, resp);
        	}
		}
		
		return "/client/distributor_goods";
	}
	
	
	/**
	 * 超市商品详情
	 * @author Max
	 * 2016-10-19
	 */
	@RequestMapping(value="/goods/detail",method=RequestMethod.POST)
	public String saleGoodsDetail(Long dis_goodsId,Long goodsId,HttpServletRequest req,ModelMap map){
		
		if(null != dis_goodsId){
			TdDistributorGoods goods = tdDistributorGoodsService.findOne(dis_goodsId);
			map.addAttribute("dis_goods", goods);
			map.addAttribute("goodsId", goods.getGoodsId());
		}else if(null != goodsId){
			map.addAttribute("goods", tdGoodsService.findOne(goodsId));
			map.addAttribute("goodsId", goodsId);
		}
		map.addAttribute("tag_list", tdTagService.findAll());
		
		return "/client/distributor_goods_detail";
	}
	
	/**
	 * 根据ID查找商品规格
	 * @author Max
	 * 2016-10-19
	 */
	@RequestMapping(value="/search/specifica",method=RequestMethod.POST)
	public String specificaSearch(Long goodsId,Long id,HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null != goodsId && null != username){
			map.addAttribute("goodsId", goodsId);
			TdDistributor distributor = tdDistributorService.findbyUsername(username);
			map.addAttribute("spec_list", tdSpecificatService.findByShopIdAndGoodsIdAndType(distributor.getId(),goodsId, 1));
			if(null != id){
				map.addAttribute("specifica", tdSpecificatService.findOne(id));
			}
		}
		return "/client/distributor_goods_spec";
	}
	/**
	 * 保存规格
	 */
	@RequestMapping(value="/specifica/save",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> specificaSave(TdSpecificat tdSpecificat,HttpServletRequest req,ModelMap map){
		Map<String,Object> res = new HashMap<String, Object>();
		res.put("code", 0);
		
		String username = (String)req.getSession().getAttribute("distributor");
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
				TdDistributor distributor = tdDistributorService.findbyUsername(username);
				
				tdSpecificat.setShopId(distributor.getId());//　设置超市id
				tdSpecificat.setType(1); //设置类型-超市
			}
			tdSpecificatService.save(tdSpecificat);
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
	 * 2016-10-19
	 * 
	 */
	@RequestMapping(value="/specifica/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteSpecifica(Long id,HttpServletRequest req){
		Map<String,Object> res = new HashMap<String, Object>();
		res.put("code", 0);
		
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username){
			res.put("msg", "登录超时");
			return res;
		}
		if(null != id){
			tdSpecificatService.delete(id);
			res.put("code", 1);
		}
		
		return res;
	}
	
	
	/**
	 * 设置推荐
	 * @author Max
	 * 
	 */
	@RequestMapping(value="/goods/recommed",method=RequestMethod.POST)
	public String recommed(Long id,Integer page,
							Long categoryId,Integer dir,String keywords,
							String type,HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		
		if(null == id)
		{
			return "/client/error_404";
		}
		
		if(null == page )
		{
			page = 0;
		}
		TdDistributorGoods distributorGoods = tdDistributorGoodsService.findOne(id);
		
		if("cat".equals(type)){
			// PC分类推荐设置
			if(null != distributorGoods.getIsRecommendType() && distributorGoods.getIsRecommendType() ==true){
				distributorGoods.setIsRecommendType(false);
			}else{
				distributorGoods.setIsRecommendType(true);
				distributorGoods.setIsRecommendTypeTime(new Date());
			}
		}else if("index".equals(type)){
			// PC首页推荐设置
			if(null != distributorGoods.getIsRecommendIndex() && distributorGoods.getIsRecommendIndex() ==true){
				distributorGoods.setIsRecommendIndex(false);
			}else{
				distributorGoods.setIsRecommendIndex(true);
				distributorGoods.setIsRecommendIndexTime(new Date());
			}
		}else if("touchcat".equals(type)){
			// 触屏分类推荐设置
			if(null != distributorGoods.getIsTouchRecommendType() && distributorGoods.getIsTouchRecommendType() ==true){
				distributorGoods.setIsTouchRecommendType(false);
			}else{
				distributorGoods.setIsTouchRecommendType(true);
				distributorGoods.setIsTouchRecommendTypeTime(new Date());
			}
		}else if("hot".equals(type)){
			// 触屏热卖设置
			if(null != distributorGoods.getIsTouchHot() && distributorGoods.getIsTouchHot() ==true){
				distributorGoods.setIsTouchHot(false);
			}else{
				distributorGoods.setIsTouchHot(true);
				distributorGoods.setIsTouchHotTime(new Date());
			}
		}
		
		tdDistributorGoodsService.save(distributorGoods);
		
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		map.addAttribute("page", page);
		map.addAttribute("type", true);
		map.addAttribute("distributor", distributor);
		map.addAttribute("isOnSale", true);
		
		PageRequest pageRequest = null;
        if(null != dir && dir ==1)
		{
        	pageRequest = new PageRequest(page, 10,new Sort(Direction.ASC, "leftNumber"));
		}
        else if(null != dir && dir ==2)
		{
        	pageRequest = new PageRequest(page, 10,new Sort(Direction.DESC, "leftNumber"));
		}
        else
        {
        	pageRequest = new PageRequest(page, 10);
        }
        map.addAttribute("dis_goods_page",tdDistributorGoodsService.findAll(distributor.getId(), true, false,categoryId, keywords, pageRequest));
		
		return "/client/distributor_goods_list";
	}
	
	@RequestMapping(value="/goods/supplyrecommed",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> supplyrecommed(Long id,
							String type,HttpServletRequest req,ModelMap map)
	{
		Map<String,Object> res = new HashMap<>();
		res.put("code", 0);
		
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			res.put("msg", "登录超时");
			return res;
		}
		
		if(null != id)
		{
			TdDistributorGoods distributorGoods = tdDistributorGoodsService.findOne(id);
			
			if("cat".equals(type)){
				// PC分类推荐设置
				if(null != distributorGoods.getIsRecommendType() && distributorGoods.getIsRecommendType() ==true){
					distributorGoods.setIsRecommendType(false);
				}else{
					distributorGoods.setIsRecommendType(true);
					distributorGoods.setIsRecommendTypeTime(new Date());
				}
			}else if("index".equals(type)){
				// PC首页推荐设置
				if(null != distributorGoods.getIsRecommendIndex() && distributorGoods.getIsRecommendIndex() ==true){
					distributorGoods.setIsRecommendIndex(false);
				}else{
					distributorGoods.setIsRecommendIndex(true);
					distributorGoods.setIsRecommendIndexTime(new Date());
				}
			}else if("touchcat".equals(type)){
				// 触屏分类推荐设置
				if(null != distributorGoods.getIsTouchRecommendType() && distributorGoods.getIsTouchRecommendType() ==true){
					distributorGoods.setIsTouchRecommendType(false);
				}else{
					distributorGoods.setIsTouchRecommendType(true);
					distributorGoods.setIsTouchRecommendTypeTime(new Date());
				}
			}else if("hot".equals(type)){
				// 触屏热卖设置
				if(null != distributorGoods.getIsTouchHot() && distributorGoods.getIsTouchHot() ==true){
					distributorGoods.setIsTouchHot(false);
				}else{
					distributorGoods.setIsTouchHot(true);
					distributorGoods.setIsTouchHotTime(new Date());
				}
			}
			tdDistributorGoodsService.save(distributorGoods);
			res.put("code", 1);
			return res;
		}else{
			res.put("code", 2);
			res.put("msg", "参数错误");
		}
		
		return res;
	}
	
	/**
	 * 超市中心商品上/下架
	 * 
	 */
	@RequestMapping(value="/goods/onsale",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> disGoodsIsOnSale(Long disId,
						Boolean type,
						HttpServletRequest req,ModelMap map)
	{
		Map<String,Object> res = new HashMap<String, Object>();
		res.put("code", 0);
		
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			res.put("msg", "登录超时");
			return res;
		}
		
		if(null != disId)
		{
			TdDistributorGoods distributorGoods = tdDistributorGoodsService.findOne(disId);
			if(null != type && null != distributorGoods){
				distributorGoods.setIsOnSale(type);
				tdDistributorGoodsService.save(distributorGoods);
				res.put("msg","操作成功");
				res.put("code", 1);
				return res;
			}
		}
		res.put("msg","参数错误");
		
		return res;
	}
	
	
	@RequestMapping(value="/goods/editOnSale",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> editOnSale(
					Long dis_goodsId,
					Long goodsId,
					Double goodsPrice,
					Double goodsMarketPrice,
					String subGoodsTitle,
					String code,String unit,
					Long leftNumber,
					Long tagId,
					HttpServletRequest req)
	{
		Map<String,Object> res = new HashMap<>();
		res.put("code", 0);
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			res.put("msg", "登录超时!");
			return res;
		}
		
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		if(null == distributor)
		{
			res.put("msg", "参数错误!");
			return res;
		}
		if(null == leftNumber || leftNumber <=0)
		{
			res.put("msg", "库存输入错误");
			return res;
		}
		
		TdDistributorGoods distributorGoods =null;
		
		// 判断是平台上架还是超市库编辑
		if(null == dis_goodsId)
		{
			TdGoods goods = tdGoodsService.findOne(goodsId);
			// 平台上架   新建数据
			distributorGoods = new TdDistributorGoods();
			distributorGoods.setDistributorTitle(distributor.getTitle());
			distributorGoods.setGoodsId(goods.getId());
			distributorGoods.setGoodsTitle(goods.getTitle());
			distributorGoods.setBrandId(goods.getBrandId());
			distributorGoods.setBrandTitle(goods.getBrandTitle());
			distributorGoods.setCategoryId(goods.getCategoryId());
			distributorGoods.setCategoryIdTree(goods.getCategoryIdTree());
			distributorGoods.setProductId(goods.getProductId());
			distributorGoods.setSelectOneValue(goods.getSelectOneValue());
			distributorGoods.setSelectTwoValue(goods.getSelectTwoValue());
			distributorGoods.setSelectThreeValue(goods.getSelectThreeValue());
			distributorGoods.setCoverImageUri(goods.getCoverImageUri());
			distributorGoods.setIsDistribution(false);
			distributorGoods.setReturnPoints(goods.getReturnPoints());
			distributorGoods.setParamValueCollect(goods.getParamValueCollect());
			distributorGoods.setIsOnSale(true);
			distributorGoods.setIsAudit(true);
			distributorGoods.setOnSaleTime(new Date());
			distributorGoods.setDisId(distributor.getId());
			distributor.getGoodsList().add(distributorGoods);
		}else{
			// 超市编辑   查找原数据
			distributorGoods = tdDistributorGoodsService.findOne(dis_goodsId);
		}
		
		if(null != goodsPrice)
		{
			distributorGoods.setGoodsPrice(goodsPrice);
		}
		if(null != goodsMarketPrice){
			distributorGoods.setGoodsMarketPrice(goodsMarketPrice);
		}
		distributorGoods.setDisId(distributor.getId());
		distributorGoods.setLeftNumber(leftNumber);
		distributorGoods.setSubGoodsTitle(subGoodsTitle);
		distributorGoods.setUnit(unit);
		distributorGoods.setCode(code);
		distributorGoods.setTagId(tagId);
		if(null != tagId){
			TdTag tag = tdTagService.findByIdAndIsEnableTrue(tagId);
			if(null != tag){
				distributorGoods.setTagImg(tag.getImgUrl());
			}else{
				distributorGoods.setTagImg(null);
			}
		}else{
			distributorGoods.setTagImg(null);
		}
		
		tdDistributorGoodsService.save(distributorGoods);
		
		res.put("msg", "操作成功！");
		res.put("code", 1);
		return res;
	}
	
	/**
	 * 超市中心删除商品
	 * 
	 */
	@RequestMapping(value="/goods/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> disGoodsDelete(Long disId, HttpServletRequest req,ModelMap map)
	{
		Map<String,Object> res  = new HashMap<String, Object>();
		res.put("code", 0);
		
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			res.put("msg", "登录超时");
			return res;
		}
		if(null != disId)
		{
			TdDistributorGoods distributorGoods = tdDistributorGoodsService.findOne(disId);
			if(null != distributorGoods){
				// 查找删除商品规格 
				List<TdSpecificat> list = tdSpecificatService.findByShopIdAndGoodsIdAndType(distributorGoods.getDisId(), distributorGoods.getGoodsId(), 1);
				if(null != list){
					tdSpecificatService.delete(list);
				}
				
			}
			tdDistributorGoodsService.delete(disId);
			res.put("code", 1);
			res.put("msg", "操作成功");
		}else{
			res.put("msg", "参数错误");
		}
		
		return res;
	}
	
//	/**
//	 * 上架/下架多个商品
//	 * 
//	 */
//	@RequestMapping(value="/onsaleAll/{type}")
//	public String onSaleAll(@PathVariable Boolean type,
//			Long[] listId,
//			Integer[] listChkId,
//			Integer page,
//			Long categoryId,String keywords,
//			HttpServletRequest req,
//			ModelMap map){
//		String username = (String)req.getSession().getAttribute("distributor");
//		if(null == username)
//		{
//			return "redirect:/login";
//		}
//		if(null == page )
//		{
//			page = 0;
//		}
//		
//		if(type)
//		{
//			onSaleAll(false, listId, listChkId);
//		}else{
//			onSaleAll(true, listId, listChkId);
//		}
//		if(null == categoryId){
//			return "redirect:/distributor/goods/sale/"+type+"?page="+page+"&keywords="+keywords;
//		}else{
//			return "redirect:/distributor/goods/sale/"+type+"?page="+page+"&categoryId="+categoryId+"&keywords="+keywords;
//		}
//	}
	
	/**
	 * 超市销售订单查询
	 * @author libiao
	 * @throws ParseException 
	 * 
	 */
	@RequestMapping(value="/outOrder/list")
	public String outOrderList(
			Integer statusId,
			String keywords,
			Integer page,
			Integer typeId,
			Long[] listId,
			Integer[] listChkId,
			String startTime,String endTime,
			String eventTarget,
			HttpServletRequest req,
			HttpServletResponse resp,
			ModelMap map) throws ParseException
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == page )
		{
			page = 0;
		}
		if(null == statusId)
		{
			statusId= 0;
		}
		
		if(null == typeId){
			typeId=0;
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
//			if("excel".equalsIgnoreCase(eventTarget))
//			{
//				excelUrl=SiteMagConstant.backupPath;
//			}
			if("editParam".equalsIgnoreCase(eventTarget)){
				edirOrderStatusId(listId,listChkId);
			}else if("btnDelete".equalsIgnoreCase(eventTarget)){
				deleteOrder(listId, listChkId);
			}else if("excelAll".equalsIgnoreCase(eventTarget)){
				excelUrl=SiteMagConstant.backupPath;
				size =  Integer.MAX_VALUE;
			}
		}
		
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		map.addAttribute("distributor", distributor);
		map.addAttribute("status_id", statusId);
		map.addAttribute("typeId", typeId);
		map.addAttribute("startTime", start);
		map.addAttribute("endTime", end);
		map.addAttribute("page", page);
		
		tdCommonService.setHeader(map, req);
		
		Page<TdOrder> orderPage=tdOrderService.findAll(distributor.getId(), statusId, typeId,start, end, page, ClientConstant.pageSize);
		
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
	        cell.setCellValue("会员账户");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 2);  
	        cell.setCellValue("收件人");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 3);  
	        cell.setCellValue("收件地址");  
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 4);  
	        cell.setCellValue("收件人号码");  
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 5);  
	        cell.setCellValue("订单总额");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 6);  
	        cell.setCellValue("下单时间");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 7);  
	        cell.setCellValue("订单状态");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 8);  
	        cell.setCellValue("支付方式");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 9);  
	        cell.setCellValue("配送方式");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 10);  
	        cell.setCellValue("商家地址");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 11);  
	        cell.setCellValue("用户备注");  
	        cell.setCellStyle(style); 
			
        	Page<TdOrder> order_Page =tdOrderService.findAll(distributor.getId(), statusId,typeId, start, end, page, size);
        	if(ImportData(order_Page,row,cell,sheet))
        	{
        		download(wb,"order", excelUrl, resp);
        	}
        }
		
		

        map.addAttribute("order_page", orderPage);
		return "/client/distributor_saleOrder_list";
	}
	

	/**
	 * 超市进货订单
	 * @author libiao
	 * @throws ParseException 
	 * 
	 */
	@RequestMapping(value="/inOrder/list")
	public String inOrderList(
			Integer statusId,
			String keywords,
			Integer page,
			String startTime,String endTime,
			String eventTarget,
			HttpServletRequest req,
			HttpServletResponse resp,
			ModelMap map) throws ParseException
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		
		
		if(null == page )
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
		
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		map.addAttribute("distributor", distributor);
		map.addAttribute("statusId", statusId);
		map.addAttribute("startTime", start);
		map.addAttribute("endTime", end);
		map.addAttribute("page", page);
		
		tdCommonService.setHeader(map, req);
		
		Page<TdOrder> orderPage=tdOrderService.findAll(distributor.getUsername(), statusId, 1,start, end, page, ClientConstant.pageSize);
		
		map.addAttribute("orderList",tdOrderService.findAll(distributor.getUsername(), statusId, 1,start, end));
		
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
		      cell.setCellValue("超市");  
		      cell.setCellStyle(style); 
		      
		      cell = row.createCell((short) 2);  
		      cell.setCellValue("批发商");  
		      cell.setCellStyle(style); 
		      
		      cell = row.createCell((short) 3);  
		      cell.setCellValue("联系电话");  
		      cell.setCellStyle(style); 
		      
		      cell = row.createCell((short) 4);  
		      cell.setCellValue("地址");  
		      cell.setCellStyle(style); 
		      
		      cell = row.createCell((short) 5);  
		      cell.setCellValue("订单总额");  
		      cell.setCellStyle(style); 
		      
		      cell = row.createCell((short) 6);  
		      cell.setCellValue("下单时间");  
		      cell.setCellStyle(style); 
		      
		      cell = row.createCell((short) 7);  
		      cell.setCellValue("订单状态");  
		      cell.setCellStyle(style); 
    	  
	      	Page<TdOrder> order_page = tdOrderService.findAll(distributor.getUsername(), statusId, 1,start, end, page, size);
	      	if(InOrderImport(order_page, row, cell, sheet))
	      	{
	      		download(wb,"order", excelUrl, resp);
	      	}
      	}

        map.addAttribute("order_page", orderPage);
		
		return "/client/distributor_inOrder_list";
	}
	
	/**
	 * 超市销售订单详细
	 * @author libiao
	 * 
	 */
	@RequestMapping(value="/order")
	public String orderdetial(Long id, String type,HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == id)
		{
			return "/client/error_404";
		}
		tdCommonService.setHeader(map, req);
		
		map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
		
		map.addAttribute("order",tdOrderService.findOne(id));
		if("inOrder".equals(type))
		{
			return "/client/distributor_inOrder_detail";
		}
		return "/client/distributor_order_detail";
	}
	
	/**
	 * 超市确认销售订单  确认发货   确认已收货  确认评价
	 * @author libiao
	 */
	@RequestMapping(value="/order/param/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> paramEdit(String orderNumber,
			String type,
			ModelMap map,
			HttpServletRequest req)
	{
		Map<String, Object> res =new HashMap<>();
		res.put("code",1);
		String username = (String)req.getSession().getAttribute("distributor");
		
		if (null == username)
        {
            res.put("message", "请重新登录");
            return res;
        }
		
		if (null != orderNumber && !orderNumber.isEmpty() && null != type && !type.isEmpty())
        {
			TdOrder order = tdOrderService.findByOrderNumber(orderNumber);
			// 确认订单
            if (type.equalsIgnoreCase("orderConfirm"))
            {
                if (order.getStatusId().equals(1L))
                {
                    order.setStatusId(2L);
                    order.setCheckTime(new Date());
                }
            }
            //确认付款
            else if(type.equalsIgnoreCase("orderPay"))
            {
            	 tdOrderService.shopEditOrder(order);
                 order.setStatusId(3L);
                 order.setPayTime(new Date());
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
            // 确认收货
            else if(type.equalsIgnoreCase("orderService"))
            {
            	if(order.getStatusId().equals(4L))
            	{
            		order.setStatusId(5L);
            		order.setReceiveTime(new Date());
            	}
            }
            // 确认订单完成
            else if(type.equalsIgnoreCase("orderFinish"))
            {
            	if(order.getStatusId().equals(5L))
            	{
            		order.setStatusId(6L);
            		order.setFinishTime(new Date());
            		
            		if(order.getTypeId() ==0 || order.getTypeId() ==2){
                    	tdOrderService.addUserPoint(order,order.getUsername());// 添加积分记录
                    	tdUserService.addTotalSpend(order.getUsername(), order.getTotalGoodsPrice());// 增加累计使用金额
                    }
            		
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
	
	@RequestMapping(value="/inOrder/param/edit")
	@ResponseBody
	public Map<String, Object> inparamEdit(String orderNumber,
			String type,
			ModelMap map,
			HttpServletRequest req)
	{
		Map<String, Object> res =new HashMap<>();
		res.put("code",1);
		String username = (String)req.getSession().getAttribute("distributor");
		
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
					
					TdDistributor distributor = tdDistributorService.findbyUsername(username);
					List<TdOrderGoods> goodsList = order.getOrderGoodsList();
					for (TdOrderGoods tdOrderGoods : goodsList) {
						
						
						TdDistributorGoods distributorGoods = tdDistributorGoodsService.findByDistributorIdAndGoodsId(distributor.getId(), tdOrderGoods.getGoodsId());
						
						if(null == distributorGoods && !distributor.getGoodsList().contains(distributorGoods))
						{
							TdGoods goods = tdGoodsService.findOne(tdOrderGoods.getGoodsId());
							distributorGoods = new TdDistributorGoods();
							
							distributorGoods.setDistributorTitle(distributor.getTitle());
							distributorGoods.setGoodsId(goods.getId());
							distributorGoods.setGoodsTitle(goods.getTitle());
							distributorGoods.setSubGoodsTitle(goods.getSubTitle());
							distributorGoods.setBrandId(goods.getBrandId());
							distributorGoods.setBrandTitle(goods.getBrandTitle());
							distributorGoods.setCategoryId(goods.getCategoryId());
							distributorGoods.setCategoryIdTree(goods.getCategoryIdTree());
							distributorGoods.setCode(goods.getCode());
							distributorGoods.setCoverImageUri(goods.getCoverImageUri());
							distributorGoods.setGoodsMarketPrice(tdOrderGoods.getPrice());
							distributorGoods.setIsDistribution(false);
							distributorGoods.setReturnPoints(goods.getReturnPoints());
							distributorGoods.setParamValueCollect(goods.getParamValueCollect());
							distributorGoods.setIsOnSale(false);
							distributorGoods.setLeftNumber(tdOrderGoods.getQuantity());
							distributorGoods.setUnit(goods.getSaleType());
							distributorGoods.setDisId(distributor.getId());
							
							distributorGoods = tdDistributorGoodsService.save(distributorGoods);
							// 没有此商品，新加商品规格
							if(null != tdOrderGoods.getSpecId()){
								TdSpecificat specificat = tdSpecificatService.findOne(tdOrderGoods.getSpecId());
								if(null != specificat){
									TdSpecificat tdSpecificat = new TdSpecificat();
									tdSpecificat.setGoodsId(goods.getId());
									tdSpecificat.setLeftNumber(tdOrderGoods.getQuantity());
									tdSpecificat.setOldId(specificat.getId());
									tdSpecificat.setShopId(distributor.getId());
									tdSpecificat.setType(1);
									tdSpecificat.setSpecifict(specificat.getSpecifict());
									tdSpecificatService.save(tdSpecificat);
								}
							}
						}else{
							distributorGoods.setDisId(distributor.getId());
							distributorGoods.setLeftNumber(distributorGoods.getLeftNumber()+tdOrderGoods.getQuantity());
							// 查看是否有此规格
							if(null != tdOrderGoods.getSpecId()){
								TdSpecificat specificat = tdSpecificatService.findByShopIdAndOldId(distributor.getId(), tdOrderGoods.getSpecId());
								if(null != specificat){
									// 在原规格基础上加库存
									specificat.setLeftNumber(specificat.getLeftNumber()+tdOrderGoods.getQuantity());
									tdSpecificatService.save(specificat);
								}else{
									TdSpecificat tdSpecificat = new TdSpecificat();
									tdSpecificat.setGoodsId(tdOrderGoods.getGoodsId());
									tdSpecificat.setLeftNumber(tdOrderGoods.getQuantity());
									tdSpecificat.setOldId(tdOrderGoods.getSpecId());
									tdSpecificat.setShopId(distributor.getId());
									tdSpecificat.setType(1);
									tdSpecificat.setSpecifict(tdOrderGoods.getSpecName());
									tdSpecificatService.save(tdSpecificat);
								}
							}
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
	
	@RequestMapping(value="/edit")
	public String distributorPassword(HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		tdCommonService.setHeader(map, req);
		map.addAttribute("distributor",tdDistributorService.findbyUsername(username));
		return "/client/distributor_change_password";
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> distributorPassword(String title,String province,
			String city,String disctrict,
			String address,String mobile,
			String password,Double postPrice,String payPassword,
			Double maxPostPrice,Long id,String postInfo,
			HttpServletRequest req,ModelMap map)
	{
		Map<String,Object> res = new HashMap<>();
		res.put("code", 0);
		
		String username = (String)req.getSession().getAttribute("distributor");
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
		
		TdDistributor distributor = tdDistributorService.findOne(id);
		
		distributor.setTitle(title);
		distributor.setProvince(province);
		distributor.setCity(city);
		distributor.setDisctrict(disctrict);
		distributor.setAddress(address);
		distributor.setMobile(mobile);
		distributor.setPassword(password);
		distributor.setPayPassword(payPassword);
		distributor.setPostPrice(postPrice);
		distributor.setMaxPostPrice(maxPostPrice);
		distributor.setPostInfo(postInfo);
		
		tdDistributorService.save(distributor);
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
		
		String username = (String)req.getSession().getAttribute("distributor");
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		
		if(null == distributor)
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
			if(!password.equalsIgnoreCase(distributor.getPassword()))
			{
				res.put("msg", "原密码输入错误");
				return res;
			}
		}else if(type.equalsIgnoreCase("payPwd")){
			if(!password.equalsIgnoreCase(distributor.getPayPassword()))
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
				distributor.setPassword(newPassword);
			}else if(type.equalsIgnoreCase("payPwd"))
			{
				distributor.setPayPassword(newPassword);
			}
		}
		
		tdDistributorService.save(distributor);
		
		res.put("msg", "修改成功");
		res.put("code", 1);
		return res;
	}
	
	@RequestMapping(value="/goods/onsale")
	public String saleGoods(Integer page,String keywords,Long categoryId,HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		map.addAttribute("distributor", distributor);
		tdCommonService.setHeader(map, req);
		
		if(null == page)
		{
			page= 0;
		}
		
		PageRequest pageRequest = new PageRequest(0, Integer.MAX_VALUE);
		
		map.addAttribute("dis_goods_list", tdDistributorGoodsService.findAll(distributor.getId(),true, false,categoryId, keywords, pageRequest));
		
		map.addAttribute("page", page);
		map.addAttribute("keywords",keywords);
		map.addAttribute("categoryId", categoryId);
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
		return "/client/distributor_goods_onsale";
	}
	
	
	/**
	 * 选择分销商品
	 * 
	 */
	@RequestMapping(value="/supply",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> distributionGoods(Long proGoodsId,HttpServletRequest req){
    	Map<String,Object> res=new HashMap<>();
    	res.put("code", 0);
    	
    	String username =(String)req.getSession().getAttribute("distributor");
		if(null ==username)
		{
			res.put("msg", "登录超时");
			return res;
		}
		
		if(null ==proGoodsId)
		{
			res.put("msg","参数错误");
			return res;
		}
		
		supply(username, proGoodsId);
		res.put("code", 1);
    	res.put("msg", "已成功分销");
		
    	return res;
    }
	
	/**
	 * 批量分销
	 * 
	 */
	@RequestMapping(value="/supplyAll",method=RequestMethod.POST)
	public String supplyAll(Long[] listId,
			Integer[] listChkId,
			Integer page,
			Long providerId,String keywords,
			HttpServletRequest req,
			ModelMap map){
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == page )
		{
			page = 0;
		}
		supplyAll(listId, listChkId, username);
		
		if(null == providerId){
			return "redirect:/distributor/supply/list?page="+page+"&keywords="+keywords;
		}else{
			return "redirect:/distributor/supply/list?page="+page+"&providerId="+providerId+"&keywords="+keywords;
		}
	}
	
	@RequestMapping(value="/goods/list")
	public String inGoodslist(String keywords,Integer page,
			Long providerId,String isDistribution,
			Long categoryId,String excel,
			HttpServletRequest req,ModelMap map,
			HttpServletResponse resp)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		
		map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
		tdCommonService.setHeader(map, req);
		if(null == page)
		{
			page=0;
		}
		map.addAttribute("provider_list", tdProviderService.findByType(1L));
		map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
		
		// 参数注回
		map.addAttribute("keywords", keywords);
		map.addAttribute("page", page);
//		map.addAttribute("distribution", isDistribution);
		map.addAttribute("providerId", providerId);
		map.addAttribute("categoryId", categoryId);
		
		List<TdProductCategory> categortList = tdProductCategoryService.findByParentIdIsNullAndIsEnableTrueOrderBySortIdAsc();
		map.addAttribute("categoryList", categortList);
		
		PageRequest pageRequest = new PageRequest(page, 10,new Sort(Direction.DESC, "id"));
		
		map.addAttribute("proGoods_page",tdProviderGoodsService.findAll(providerId, true, categoryId, keywords, pageRequest));
		
		if(null != categoryId){
       	 TdProductCategory category = tdProductCategoryService.findOne(categoryId);
            for (TdProductCategory tdProductCategory : categortList) {
            	
            	if(category.getParentTree().contains("["+tdProductCategory.getId()+"]"))
            	{
            		List<TdProductCategory> cateList = tdProductCategoryService.findByParentIdOrderBySortIdAsc(tdProductCategory.getId());
            		map.addAttribute("cateList", cateList);
            		
            		for (TdProductCategory productCategory : cateList) {
            			if(category.getParentTree().contains("["+productCategory.getId()+"]"))
            			{
            				map.addAttribute("category_list", tdProductCategoryService.findByParentIdOrderBySortIdAsc(productCategory.getId()));
            			}
            		}
            		
            	}
            }
            map.addAttribute("category", category);
       }
		if(null != excel && !"".equals(excel)){
			/**
			 *  导出表格
			 */
			// 创建一个webbook 对于一个Excel
			HSSFWorkbook wb = new HSSFWorkbook();
			// 在webbook中添加一个sheet,对应Excel文件中的sheet 
			HSSFSheet sheet = wb.createSheet("goods"); 
			// 设置每个单元格宽度根据字多少自适应
			sheet.autoSizeColumn(1);
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	        HSSFRow row = sheet.createRow((int) 0);
	        // 创建单元格，并设置值表头 设置表头居中 
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  // 居中
	        
	        HSSFCell cell = row.createCell((short) 0);  
	        cell.setCellValue("供应商名称");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 1);  
	        cell.setCellValue("商品名称");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 2);  
	        cell.setCellValue("商品副标题");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 3);  
	        cell.setCellValue("编码");  
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 4);  
	        cell.setCellValue("所属分类");  
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 5);  
	        cell.setCellValue("品牌");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 6);  
	        cell.setCellValue("销售单位");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 7);  
	        cell.setCellValue("批发价");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 8);  
	        cell.setCellValue("原价");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 9);  
	        cell.setCellValue("商家库存");  
	        cell.setCellStyle(style); 
	        
	        String excelUrl=SiteMagConstant.backupPath;
	        
			pageRequest = new PageRequest(page, Integer.MAX_VALUE,new Sort(Direction.DESC, "id"));
			
        	Page<TdProviderGoods> goods_page =tdProviderGoodsService.findAll(providerId, true, categoryId, keywords, pageRequest);
        	
        	if(providerGoodsImport(goods_page,row,cell,sheet))
        	{
        		download(wb,"goods", excelUrl, resp);
        	}
		}
		
		return "/client/distributor_ingoods";
	}
	
	/**
	 * 我要进货页面——加载购物车
	 * @return
	 */
	@RequestMapping(value="/search/cartGoods")
	public String cartGoods(HttpServletRequest req,ModelMap map){
		String username = (String)req.getSession().getAttribute("distributor");
	
		if(null != username){
			map.addAttribute("cart_goods_list", tdCartGoodsService.findByUsername(username));
		}
		return "/client/distributor_ingoods_cartlist";
	}
	
	/**
	 * 我要进货页面，点击添加
	 * 加载供应商品信息
	 */
	@RequestMapping(value="/proGoods",method=RequestMethod.POST)
	public String proGoodsDetail(Long proId,HttpServletRequest req,ModelMap map){
		if(null != proId){
			 TdProviderGoods providerGoods = tdProviderGoodsService.findOne(proId);
			map.addAttribute("proGoods",providerGoods);
			// 规格
			map.addAttribute("specList", tdSpecificatService.findByShopIdAndGoodsIdAndType(providerGoods.getProId(), providerGoods.getGoodsId(), 2));
		}
		return "/client/distributor_ingoods_progoods";
	}
	
	/**
	 * 获取规格信息
	 */
	@RequestMapping(value="/goods/specifica",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> findSpec(Long id,HttpServletRequest req){
    	Map<String,Object> res = new HashMap<String, Object>();
    	res.put("code", 0);
    	
    	if(null != id){
    		TdSpecificat specificat = tdSpecificatService.findOne(id);
    		if(null != specificat){
    			if(specificat.getLeftNumber() > 0 ){
    				res.put("num", specificat.getLeftNumber());
    				res.put("code", 1);
    				return res;
    			}
    		}
    	}
    	res.put("msg", "此规格已售完，请选择其它类型");
    	return res;
    }
	
	/**
	 * 加入购物车
	 * @return
	 */
	@RequestMapping(value="/goods/addOne",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addOne(Long pro_id,Boolean isSpec,
			Long specId,Long quantity,HttpServletRequest req,ModelMap map)
	{
		Map<String,Object> res = new HashMap<String, Object>();
    	res.put("code", 0);
    	
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			res.put("msg", "登录超时");
			return res;
		}
		
		if(null != isSpec && isSpec ==true){
    		if(null == specId){
    			res.put("msg", "请先选择规格");
    			return res;
    		}
    	}
		TdProviderGoods goods = tdProviderGoodsService.findOne(pro_id);
		
		
		if (null == quantity || quantity.compareTo(1L) < 0)
        {
            quantity = 1L;
        }
		
		if(null != goods){
			List<TdSpecificat> specList = tdSpecificatService.findByShopIdAndGoodsIdAndType(goods.getProId(), goods.getGoodsId(), 2);
		    if(null != specList && specList.size() > 0){
		      	if(null == specId){
		      		res.put("msg", "请先选择规格");
		      		return res;
		      	}
		    }
		    
			List<TdCartGoods> oldCartGoodsList = null;
           
            // 购物车是否已有该商品
			
			oldCartGoodsList = tdCartGoodsService.
						findByGoodsIdAndUsernameAndProviderIdAndSpecificaId(goods.getId(), username,goods.getProId(),specId);
           
			// 查找购物车是否已有此（规格）商品
   		
   		 	Long goodsLeftNumber = goods.getLeftNumber(); // 商品库存
   		 	if(null != specId){
   		 		TdSpecificat specificat = tdSpecificatService.findOne(specId);
   		 		if(null != specificat){ // 如果有规格，取规格库存
   		 			goodsLeftNumber = specificat.getLeftNumber();
   		 		}
   		 	}
   		 	// 购物车已有
            if(null !=oldCartGoodsList && oldCartGoodsList.size() >0){
            	 long oldQuantity = oldCartGoodsList.get(0).getQuantity();
            	 if(oldQuantity < goodsLeftNumber)
            	 {
            		 if(oldQuantity+quantity > goodsLeftNumber)
            		 {
            			 oldCartGoodsList.get(0).setQuantity(goodsLeftNumber);
            		 }else{
            			 oldCartGoodsList.get(0).setQuantity(oldQuantity + quantity);
            		 }
            		 tdCartGoodsService.save(oldCartGoodsList.get(0));
            	 }
            }else{
            	TdCartGoods cartGoods = new TdCartGoods();
            	cartGoods.setIsLoggedIn(true);
            	cartGoods.setUsername(username);
            	cartGoods.setGoodsId(goods.getId());
            	cartGoods.setGoodsCoverImageUri(goods.getGoodsCoverImageUri());
            	cartGoods.setGoodsTitle(goods.getGoodsTitle());
            	cartGoods.setProviderTite(goods.getProviderTitle());
            	cartGoods.setProviderId(goods.getProId());
            	cartGoods.setIsSelected(true);
            	cartGoods.setPrice(goods.getOutFactoryPrice());
            	cartGoods.setGoodsSubTitle(goods.getSubGoodsTitle());
            	if(null != specId){
            		TdSpecificat specificat = tdSpecificatService.findOne(specId);
            		cartGoods.setSpecificaId(specId);
            		cartGoods.setSpecName(specificat.getSpecifict());
            	}
            	if(quantity>goodsLeftNumber)
            	{
            		cartGoods.setQuantity(goodsLeftNumber);
            	}else {
            		cartGoods.setQuantity(quantity);
				}
            	tdCartGoodsService.save(cartGoods);
            }
            res.put("code", 1);
            res.put("msg", "添加购物车成功");
		}
		return res;
	}
	
	@RequestMapping(value = "/goods/toggleSelect", method = RequestMethod.POST)
    public String cartToggle(Long id, HttpServletRequest req, ModelMap map) {

        String username = (String) req.getSession().getAttribute("distributor");

        if (null == username) {
            username = req.getSession().getId();
        }

        List<TdCartGoods> cartGoodsList = tdCartGoodsService
                .findByUsername(username);

        if (null != id) {
            for (TdCartGoods cartGoods : cartGoodsList) {
                if (cartGoods.getId().equals(id)) {
                    if (null == cartGoods.getIsSelected() || false == cartGoods.getIsSelected())
                    {
                        cartGoods.setIsSelected(true);
                    }
                    else
                    {
                        cartGoods.setIsSelected(false);
                    }
                    cartGoods = tdCartGoodsService.save(cartGoods);
                    break;
                }
            }
        }
        map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
        map.addAttribute("cart_goods_list", tdCartGoodsService.findByUsername(username));

        return "/client/distributor_ingoods_cartlist";
    }
	
	
	@RequestMapping(value = "/goods/toggleAll", method = RequestMethod.POST)
    public String cartToggleAll(Integer sid, HttpServletRequest req,
            ModelMap map) {

        String username = (String) req.getSession().getAttribute("distributor");

        if (null == username) {
            username = req.getSession().getId();
        }

        List<TdCartGoods> cartGoodsList = tdCartGoodsService
                .findByUsername(username);

        if (null != sid) {
            if (sid.equals(0)) // 全选
            {
                for (TdCartGoods cartGoods : cartGoodsList) {
                    cartGoods.setIsSelected(true);
                }
            } else // 取消全选
            {
                for (TdCartGoods cartGoods : cartGoodsList) {
                    cartGoods.setIsSelected(false);
                }
            }
            tdCartGoodsService.save(cartGoodsList);
        }
        map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
        map.addAttribute("cart_goods_list", tdCartGoodsService.findByUsername(username));

        return "/client/distributor_ingoods_cartlist";
    }
	
	@RequestMapping(value = "/goods/numberAdd", method = RequestMethod.POST)
    public String cartNumberAdd(Long id, HttpServletRequest req, ModelMap map) {

        String username = (String) req.getSession().getAttribute("distributor");

        if (null == username) {
            username = req.getSession().getId();
        }

        if (null != id) {
            TdCartGoods cartGoods =tdCartGoodsService.findOne(id);
            
            Long leftNumber = 0L;
			if(null != cartGoods.getSpecificaId()){
				TdSpecificat specificat = tdSpecificatService.findOne(cartGoods.getSpecificaId());
				if(null != specificat){
					leftNumber = specificat.getLeftNumber();
				}
			}else{
				TdProviderGoods providerGoods = tdProviderGoodsService.findOne(cartGoods.getGoodsId());
				if(null != providerGoods)
    			{
					leftNumber = providerGoods.getLeftNumber();
    			}
			}
            
            if (cartGoods.getUsername().equalsIgnoreCase(username)) {
                long quantity = cartGoods.getQuantity();
                
                if(quantity < leftNumber){
                	cartGoods.setQuantity(quantity + 1);
                }
                tdCartGoodsService.save(cartGoods);
            }
        }
        map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
        map.addAttribute("cart_goods_list",tdCartGoodsService.findByUsername(username));

        return "/client/distributor_ingoods_cartlist";
    }

    @RequestMapping(value = "/goods/numberMinus",method = RequestMethod.POST)
    public String cartNumberMinus(Long id, HttpServletRequest req, ModelMap map) {

        String username = (String) req.getSession().getAttribute("distributor");

        if (null == username) {
            username = req.getSession().getId();
        }

        if (null != id) {
            TdCartGoods cartGoods = tdCartGoodsService.findOne(id);

            if (cartGoods.getUsername().equalsIgnoreCase(username)) {
                long quantity = cartGoods.getQuantity();

                quantity = quantity > 1 ? quantity - 1 : quantity;

                cartGoods.setQuantity(quantity);
                tdCartGoodsService.save(cartGoods);
            }
        }
        map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
        map.addAttribute("cart_goods_list",tdCartGoodsService.findByUsername(username));

        return "/client/distributor_ingoods_cartlist";
    }
    
    @RequestMapping(value = "/goods/changQuantity",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> cartNumberChange(Long id,Long quantity, HttpServletRequest req, ModelMap map) {
    	
    	Map<String,Object> res = new HashMap<>();
    	res.put("code", 0);
        
        
        if(null != id && null != quantity && quantity != 0){
            TdCartGoods cartGoods = tdCartGoodsService.findOne(id);
        	
            if(null != cartGoods){
            	Long leftNumber = 0L;
    			if(null != cartGoods.getSpecificaId()){
    				TdSpecificat specificat = tdSpecificatService.findOne(cartGoods.getSpecificaId());
    				if(null != specificat){
    					leftNumber = specificat.getLeftNumber();
    				}
    			}else{
    				TdProviderGoods providerGoods = tdProviderGoodsService.findOne(cartGoods.getGoodsId());
    				if(null != providerGoods)
        			{
    					leftNumber = providerGoods.getLeftNumber();
        			}
    			}
				if(quantity > leftNumber){
					res.put("msg", "商家库存不足，请重新输入");
					return res;
				}else{
					cartGoods.setQuantity(quantity);
					tdCartGoodsService.save(cartGoods);
					res.put("code", 1);
					return res;
				}
            }
        }
        res.put("msg", "参数错误");
        return res;
    }
    

    @RequestMapping(value = "/goods/del",method = RequestMethod.POST)
    public String cartDel(Long id, HttpServletRequest req, ModelMap map) {

        String username = (String) req.getSession().getAttribute("distributor");

        if (null == username) {
            username = req.getSession().getId();
        }

        if (null != id) {
            TdCartGoods cartGoods = tdCartGoodsService.findOne(id);

            if (cartGoods.getUsername().equalsIgnoreCase(username)) {
                tdCartGoodsService.delete(cartGoods);
            }
        }

        map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
        map.addAttribute("cart_goods_list",tdCartGoodsService.findByUsername(username));

        return "/client/distributor_ingoods_cartlist";
    }
    
    /**
     *  提交进货单
     *  	扣除超市相应余额
     *  	增加相应批发账户
     */
    @RequestMapping(value="/order/info")
    @ResponseBody
    public Map<String,Object> orderInfo(String payPassword,HttpServletRequest req)
    {
    	Map<String,Object> res =new HashMap<>();
    	res.put("code", 0);
    	String username = (String) req.getSession().getAttribute("distributor");

        if (null == username) {
        	res.put("msg", "请重新登录");
        	return res;
        }
        if(null == payPassword)
        {
        	res.put("msg", "请输入支付密码");
        	return res;
        }
        
       TdDistributor distributor = tdDistributorService.findbyUsername(username);
       
       if(null == distributor || null == distributor.getPayPassword() || !payPassword.equalsIgnoreCase(distributor.getPayPassword()))
       {
    	   res.put("msg", "支付密码错误");
    	   return res;
       }
       
        List<TdCartGoods> cartSelectedGoodsList = tdCartGoodsService.findByUsernameAndIsSelectedTrue(username);

       
        // 储存批发商Id 和对应商品  为拆分订单做准备
        Map<Long,List<TdCartGoods>> cartGoodsMap =new HashMap<>();
        if(null != cartSelectedGoodsList)
        {
        	for (TdCartGoods cartGoods: cartSelectedGoodsList) 
        	{
				if(cartGoods.getIsSelected())
				{
					if(cartGoodsMap.containsKey(cartGoods.getProviderId()))
					{
						cartGoodsMap.get(cartGoods.getProviderId()).add(cartGoods);
					}else{
						List<TdCartGoods> clist=new ArrayList<>();
						clist.add(cartGoods);
						cartGoodsMap.put(cartGoods.getProviderId(),clist);
					}
				}
			}
        }
        
        if(null == cartGoodsMap || cartGoodsMap.size() <= 0){
//        	return "/client/error_404";
        	res.put("msg", "参数错误！");
        	return res;
        }
        
        // 订单拆分
        Set<Entry<Long,List<TdCartGoods>>> set = cartGoodsMap.entrySet();
        Iterator<Entry<Long, List<TdCartGoods>>> iterator = set.iterator();
        while(iterator.hasNext())
        {
        	Map.Entry<Long,List<TdCartGoods> > m  = iterator.next();
        
        	 List<TdOrderGoods> orderGoodsList = new ArrayList<TdOrderGoods>();
        	 
        	 Double totalGoodsPrice = 0.0; // 商品总额 
        	 Double totalPrice = 0.0; // 购物总额
        	 Double serviceRation = 0.0; // 平台费
        	 Double postPrice = 0.0; // 配送费
        	 Double aliPrice = 0.0; // 第三方费
        	 
        	 for (int i = 0; i < m.getValue().size(); i++) {
        		 TdCartGoods cartGoods= m.getValue().get(i);
        		 TdProviderGoods providerGoods = tdProviderGoodsService.findOne(cartGoods.getGoodsId());
        		 
        		 if(null == providerGoods || providerGoods.getIsOnSale()==false){
        			 continue;
        		 }
        		 
        		 TdOrderGoods orderGoods = new TdOrderGoods();
        		 // 商品信息
        		 orderGoods.setGoodsId(providerGoods.getGoodsId());
        		 orderGoods.setGoodsCoverImageUri(providerGoods.getGoodsCoverImageUri());
        		 orderGoods.setGoodsTitle(providerGoods.getGoodsTitle());
        		 orderGoods.setGoodsSubTitle(cartGoods.getGoodsSubTitle());
        		 orderGoods.setGoodsCode(providerGoods.getCode());
        		 orderGoods.setSaleTime(new Date());
        		 
        		 // 是否退货
        		 orderGoods.setIsReturnApplied(false);
        		 
        		 // 批发价
        		 orderGoods.setPrice(providerGoods.getOutFactoryPrice());
        		 
        		 // 数量
        		 long quantity = 0;
        		 quantity = Math.min(cartGoods.getQuantity(),providerGoods.getLeftNumber());
        		 orderGoods.setQuantity(quantity);
        		
        		 // 单位
 				 orderGoods.setUnit(providerGoods.getUnit());
 				 
 				 // 规格
 				 orderGoods.setSpecId(cartGoods.getSpecificaId());
 				 orderGoods.setSpecName(cartGoods.getSpecName());
        		 
        		 // 商品总价
        		 totalGoodsPrice +=cartGoods.getPrice()*cartGoods.getQuantity();
        		 
        		 orderGoodsList.add(orderGoods);
        		 
        		 long leftNumber = providerGoods.getLeftNumber();
        		 if(null != cartGoods.getSpecificaId()){
        			 TdSpecificat specificat = tdSpecificatService.findOne(cartGoods.getSpecificaId());
        			 if(null != specificat && null != specificat.getLeftNumber()){
							if(specificat.getLeftNumber() >= quantity){
								specificat.setLeftNumber(specificat.getLeftNumber()-quantity);
							}else{
								specificat.setLeftNumber(0L);
							}
							tdSpecificatService.save(specificat);
						}
        		 }
        		 
        		 if(leftNumber >= quantity){
        			 leftNumber= leftNumber-quantity;
        		 }
        		 providerGoods.setLeftNumber(leftNumber);
        		 tdProviderGoodsService.save(providerGoods);
			}
        	 
        	 TdOrder tdOrder = new TdOrder();
        	 TdProvider provider = tdProviderService.findOne(m.getKey());
        	 
             Date current = new Date();
             SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
             String curStr = sdf.format(current);
             Random random = new Random();


             // 基本信息
             tdOrder.setUsername(username);
             tdOrder.setMobile(distributor.getMobile());
             tdOrder.setOrderTime(current);
             tdOrder.setShopId(provider.getId());
             tdOrder.setShopTitle(provider.getTitle());

             // 订单号
             tdOrder.setOrderNumber("T" + curStr
                     + leftPad(Integer.toString(random.nextInt(999)), 3, "0"));
             
             // 发货信息
             tdOrder.setShippingName(distributor.getTitle());
             tdOrder.setShippingPhone(distributor.getServiceTele());
             tdOrder.setShippingAddress(distributor.getProvince()
             					+distributor.getCity()
             					+distributor.getDisctrict()
             					+distributor.getAddress());
             // 待发货
             tdOrder.setStatusId(3L);
             
             if(null != provider.getServiceRation())
      		 {
      			serviceRation =totalGoodsPrice*provider.getServiceRation(); // 计算平台获利
      		 }
             
             // 订单类型-批发订单
             tdOrder.setTypeId(1L);
             
             // 订单商品
             tdOrder.setOrderGoodsList(orderGoodsList);
             
             if(null != provider.getPostPrice())
             { 
            	postPrice += totalGoodsPrice*provider.getPostPrice(); // 配送费
             }
             if(null != provider.getAliRation()) 
         	{
         		aliPrice += provider.getAliRation()*totalGoodsPrice; // 第三方使用费
         	}
             
             totalPrice += totalGoodsPrice+postPrice; // 进货单总额为：商品总额+配送费
             
             tdOrder.setTotalGoodsPrice(totalGoodsPrice); // 商品总价
             tdOrder.setTrainService(serviceRation); // 平台服务费
             tdOrder.setTotalPrice(totalPrice); // 订单总价
             tdOrder.setPostPrice(postPrice); // 运费
             
             if(distributor.getVirtualMoney()<tdOrder.getTotalPrice())
             {
            	 res.put("msg", "账户余额不足，请先充值！");
            	 return res;
             }
             tdOrder.setPayTypeTitle("余额支付");
             tdOrder.setPayTypeId(0L);
             
             // 保存商品信息
             tdOrderGoodsService.save(orderGoodsList);
             
             // 扣除超市虚拟账户
            tdOrder = tdOrderService.save(tdOrder);
            distributor.setVirtualMoney(distributor.getVirtualMoney()-totalPrice);//扣除超市虚拟账户金额（订单总额）
            tdDistributorService.save(distributor);
            
     		if(null == provider.getVirtualMoney()){
     			provider.setVirtualMoney(new Double(0));
     		}
     		provider.setVirtualMoney(provider.getVirtualMoney()+totalPrice - serviceRation); // 批发商收入：订单总额-平台费
     		tdProviderService.save(provider);
     		
     		// 保存交易记录
     		TdPayRecord record = new TdPayRecord();
            record.setCont("批发货款");
            record.setCreateTime(new Date());
            record.setDistributorId(distributor.getId());
            record.setDistributorTitle(distributor.getTitle());
            record.setProviderTitle(provider.getTitle());
            record.setOrderId(tdOrder.getId());
            record.setOrderNumber(tdOrder.getOrderNumber());
            record.setStatusCode(1);
            record.setProvice(totalPrice); // 订单总额
            record.setTotalGoodsPrice(totalGoodsPrice); // 商品总额
            record.setPostPrice(postPrice); // 邮费
            record.setRealPrice(totalPrice);// 实际支付
            tdPayRecordService.save(record);
            
            record = new TdPayRecord();
            record.setCont("批发款");
            record.setCreateTime(new Date());
            record.setDistributorTitle(distributor.getTitle());
            record.setProviderId(provider.getId());
            record.setProviderTitle(provider.getTitle());
            record.setOrderId(tdOrder.getId());
            record.setOrderNumber(tdOrder.getOrderNumber());
            record.setStatusCode(1);
            record.setProvice(totalPrice); // 订单总额
            record.setTotalGoodsPrice(totalGoodsPrice); // 商品总额
            record.setPostPrice(postPrice); // 邮费
            record.setServicePrice(serviceRation);
            record.setRealPrice(totalPrice-serviceRation);
            tdPayRecordService.save(record);
            
          //平台虚拟账户增加金额
     		TdSetting setting = tdSettingService.findTopBy();
     		
     		if( null != setting.getVirtualMoney())
            {
            	setting.setVirtualMoney(setting.getVirtualMoney()+serviceRation);
            }else{
            	setting.setVirtualMoney(serviceRation);
            }
            tdSettingService.save(setting); // 更新平台虚拟余额
            
            // 记录平台收益
            record = new TdPayRecord();
            record.setCont("批发单抽取");
            record.setCreateTime(new Date());
            record.setDistributorTitle(tdOrder.getShopTitle());
            record.setOrderId(tdOrder.getId());
            record.setOrderNumber(tdOrder.getOrderNumber());
            record.setStatusCode(1);
            record.setType(1L); // 类型 区分平台记录
            
            record.setProvice(totalPrice); // 订单总额
            record.setPostPrice(postPrice); // 邮费
            record.setAliPrice(aliPrice);	// 第三方费
            record.setServicePrice(serviceRation);	// 平台费
            record.setTotalGoodsPrice(totalGoodsPrice); // 商品总价
            // 实际获利 平台服务费
            record.setRealPrice(serviceRation);
            
            tdPayRecordService.save(record);
        	 
        }
        	
		// 删除已生成订单的购物车项
        tdCartGoodsService.delete(cartSelectedGoodsList);
        
        res.put("msg", "提交成功！");
        res.put("code", 1);
        return res;
    }
    
	
    /**
     *  添加收藏
     */
    @RequestMapping(value="/collect/add",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> collectAdd(Long pgId,HttpServletRequest req,ModelMap map){
    	 Map<String, Object> res = new HashMap<String, Object>();
         res.put("code", 1);
    	
         if(null == pgId){
        	 res.put("msg","参数错误！");
        	 return res;
         }
       
         String username =(String)req.getSession().getAttribute("distributor");
         if(null == username){
        	 res.put("msg", "请重新登录");
        	 return res;
         }
         res.put("code",0);
         
         TdProviderGoods providerGoods = tdProviderGoodsService.findOne(pgId);
         if(null == providerGoods){
        	 res.put("msg", "参数错误！");
        	 return res;
         }
         
         // 没有收藏
         if(null == tdUserCollectService.findByUsernameAndProviderId(username, tdProviderGoodsService.findProviderId(pgId))){
        	 TdUserCollect collect = new TdUserCollect();
        	 
        	 collect.setUsername(username);
        	 collect.setProviderId(pgId);
        	 collect.setGoodsId(providerGoods.getGoodsId());
        	 collect.setGoodsCoverImageUri(providerGoods.getGoodsCoverImageUri());
        	 collect.setGoodsSalePrice(providerGoods.getOutFactoryPrice());
        	 collect.setGoodsTitle(providerGoods.getGoodsTitle());
        	 collect.setCollectTime(new Date());
        	 collect.setType(1);
        	 
        	 tdUserCollectService.save(collect);
        	 res.put("msg","收藏成功");
        	 return res;
         }
         
         res.put("msg","已经收藏该商品");
         
    	return res;
    }
    /**
     * 收藏列表
     * 
     */
    
    @RequestMapping(value="/collect/list")
    public String collect(HttpServletRequest req,Integer page,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	if(null == page){
    		page=0;
    	}
    	
    	map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
    	tdCommonService.setHeader(map, req);
    	
    	map.addAttribute("collect_page",
    			tdUserCollectService.findByUsername(username,1, page,ClientConstant.pageSize));
    	
    	return "/client/distributor_collect";
    }
    
    /**
     * 	取消收藏
     * 
     */
    @RequestMapping(value="/collect/del")
    public String collectDle(HttpServletRequest req,ModelMap map,Long id){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	if(null != id){
    		tdUserCollectService.delete(id);
    	}
    	return "redirect:/distributor/collect/list";
    	
    }
    
    /**
     * 平台服务
     * 
     */
    @RequestMapping(value="/info/{mid}")
    public String info(@PathVariable Long mid,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
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
 	   
 	   return "/client/distributor_info_list";
   }
    
    @RequestMapping(value="/content/{newId}")
    public String newContent(@PathVariable Long newId,Long mid,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
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
    	return "/client/distributor_info";
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
    	String username = (String)req.getSession().getAttribute("distributor");
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
    	if(null == page){
    		page = 0;
    	}
    	map.addAttribute("page", page);
    	map.addAttribute("cont", cont);
    	map.addAttribute("startTime", start);
    	map.addAttribute("endTime", end);
    	tdCommonService.setHeader(map, req);
    	
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	map.addAttribute("distributor", distributor);
    	
    	map.addAttribute("pay_record_page",tdPayRecordService.findAll("dis", distributor.getId(), cont, start, end, page, ClientConstant.pageSize));
    	
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
	        cell.setCellValue("实际金额");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 6);  
	        cell.setCellValue("交易时间");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 7);  
	        cell.setCellValue("交易类型");  
	        cell.setCellStyle(style); 
	        
        	Page<TdPayRecord> record_Page = tdPayRecordService.findAll("dis", distributor.getId(), cont, start, end, page, Integer.MAX_VALUE);
        	
			if(payRecordImportData(record_Page,row,cell,sheet))
        	{
        		FileDownUtils.download("payRecord", wb, exportUrl, resp);
        	}
    	}
    	
    	return "/client/distributor_record";
    }
    
    /**
     * 商品需求
     * 
     */
    @RequestMapping(value="/goods/need")
    public String noodGoods(HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
    	map.addAttribute("category_list", tdProductCategoryService.findAll());
    	return "/client/distributor_goods_need";
    }
    
    @RequestMapping(value="goods/need",method=RequestMethod.POST)
    public String needGoods(TdDemand demand,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	tdCommonService.setHeader(map, req);
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	map.addAttribute("distributor", distributor);
    	
    	if(null == demand){
    		return "/client/error_404";
    	}
    	if(null != demand.getCategoryId()){
    		TdProductCategory category = tdProductCategoryService.findOne(demand.getCategoryId());
    		demand.setCategory(category.getTitle());
    	}
    	demand.setName(distributor.getTitle());
    	demand.setMobile(distributor.getMobile());
    	demand.setTime(new Date());
    	demand.setStatusId(0L);
    	
    	tdDemandService.save(demand);
    	
    	return "/client/distributor_end_need";
    }
    
    /**
     * 查看会员退货
     * 
     */
    @RequestMapping(value="/return/list")
    public String userReturn(Integer page,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	if(null == page){
    		page=0;
    	}
    	tdCommonService.setHeader(map, req);
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	map.addAttribute("distributor", distributor);
    	map.addAttribute("return_page", 
    			tdUserReturnService.findByShopIdAndType(distributor.getId(), 1L, page,ClientConstant.pageSize));
    	
    	return "/client/distributor_user_return";
    }
    
    @RequestMapping("/user/returnDetail")
    public String turnDetail(Long id,HttpServletRequest req,ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	tdCommonService.setHeader(map, req);
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	map.addAttribute("distributor", distributor);
    	
    	TdUserReturn userreturn = tdUserReturnService.findOne(id);
	     
	     map.addAttribute("return", userreturn);
	     map.addAttribute("shop", tdDistributorService.findOne(userreturn.getShopId()));
	     
    	return "/client/distributor_user_turndetail";
    }
    
    
    @RequestMapping(value="/return/param/edit")
    @ResponseBody
    public Map<String,Object> returnedit(Long id,
    		Long statusId,String handleDetail,Double realPrice,
    		HttpServletRequest req){
    	Map<String,Object> res =new HashMap<>();
    	res.put("code",1);
		String username = (String)req.getSession().getAttribute("distributor");
		
		if (null == username)
        {
            res.put("message", "请重新登录！");
            return res;
        }
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		
		if(null != distributor)
		{
			if(null != id)
			{
				TdUserReturn e = tdUserReturnService.findOne(id);
				if(null != e && e.getStatusId()==0)
				{
					e.setStatusId(statusId);
					e.setHandleDetail(handleDetail);
					e.setRealPrice(realPrice);
					
					// 普通商品退
					if(null == e.getTurnType() || e.getTurnType() ==1 ){
						// 同意退
						if(statusId == 1){
							if(null != distributor.getVirtualMoney()&&  distributor.getVirtualMoney() > e.getGoodsPrice()*e.getReturnNumber())
							{
								turnGoods(e, distributor);
							}else{
								res.put("message", "账户余额不足");
								return res;
							}
						}
					}
					else // 分销商品退
					{
						if(statusId ==1){
							Integer trunCode = supplyReutrn(e, distributor);
							if(trunCode ==0){
								res.put("message", "商品已删除");
								return res;
							}else if(trunCode ==1){
								res.put("message", "分销商已不存在");
								return res;
							}else if(trunCode ==2){
								res.put("message", "账户余额不足");
								return res;
							}else if(trunCode ==3){
								
							}
					
						}
					}
					
					e = tdUserReturnService.save(e);
					
	            	res.put("message", "已处理此次退货！");
	            	res.put("code", 0);
	            	return res;
				}
			}
		}
		res.put("message", "参数错误！");
    	return res;
    }
    
    @RequestMapping(value="/order/return")
    public String orderReturn(HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("pro_list", 
    			tdProviderService.findByType(1L));
    	
    	map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
    	
    	return "/client/distributor_order_return";
    }
    /**
     * 超市退货申请
     * 
     */
    @RequestMapping(value="/order/return",method=RequestMethod.POST)
    public String Return(TdUserReturn tdReturn,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	tdCommonService.setHeader(map, req);
    	
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	if(null !=tdReturn.getShopId()){
    		TdProvider provider = tdProviderService.findOne(tdReturn.getShopId());
    		tdReturn.setShopTitle(provider.getTitle());
    	}
    	
    	Date current = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String curStr = sdf.format(current);
    	Random random = new Random();
    	
    	tdReturn.setOrderNumber("T"+curStr+leftPad(Integer.toString(random.nextInt(999)), 3, "0"));
    	tdReturn.setDistributorId(distributor.getId());
    	tdReturn.setReturnTime(new Date());
    	tdReturn.setTelephone(distributor.getMobile());
    	tdReturn.setUsername(distributor.getTitle());
    	tdReturn.setType(2L);
    	tdReturn.setStatusId(0L);
    	tdReturn.setRealPrice(tdReturn.getGoodsPrice());
    	tdReturn.setReturnNumber(1L);
    	
    	tdUserReturnService.save(tdReturn);
    	map.addAttribute("distributor", distributor);
    	
    	return "/client/distributor_return_end";
    }
    
    /**
     * 超市退货记录
     * @author Max
     * 
     */
    @RequestMapping(value="/list/return")
    public String returnLIst(Integer page,
    		Long statusId,
    		String eventTarget,
    		HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	if(null == page){
    		page = 0;
    	}
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("status_id",statusId);
    	map.addAttribute("page", page);
    	
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	
    	map.addAttribute("distributor", distributor);
    	map.addAttribute("return_page", tdUserReturnService.findAll("dis",distributor.getId(), 2L, statusId, page, ClientConstant.pageSize));
    	
    	return "/client/distributor_return_list";
    }
    
    /**
     * 退货详情
     * 
     */
    @RequestMapping(value="/return/detail")
    public String returnDetal(Long id,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	
    	map.addAttribute("distributor", distributor);
    	
    	tdCommonService.setHeader(map, req);
    	if(null != id){
    		map.addAttribute("turn", tdUserReturnService.findOne(id));
    	}
    	
    	return "/client/distributor_return_detail";
    }
    
    
    /**
     * 分销商品
     * 
     */
    @RequestMapping(value="/supply/list")
	public String goodslist(String keywords,Integer page,
			Long providerId,String isDistribution,
			HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		tdCommonService.setHeader(map, req);
		if(null == page)
		{
			page=0;
		}
		
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		
		map.addAttribute("distributor", distributor);
		map.addAttribute("provider_list", tdProviderService.findByType(2L));
		
		PageRequest pageRequest = new PageRequest(0, Integer.MAX_VALUE);
		map.addAttribute("goodsList", tdDistributorGoodsService.findAll(distributor.getId(), null, false,null , keywords, pageRequest));
		
		// 参数注回
		map.addAttribute("keywords", keywords);
		map.addAttribute("page", page);
		map.addAttribute("providerId", providerId);
		
		
		if(null == providerId)
		{
				if(null == keywords)
				{
					map.addAttribute("proGoods_page",
							tdProviderGoodsService.findByIsDistributionTrueAndIsAuditTrue(page, 10));
				}else{
					map.addAttribute("proGoods_page", 
							tdProviderGoodsService.searchAndIsDistributionTrueAndIsAuditTrue(keywords, page, 10));
				}
		}
		else
		{
			if(null == keywords){
				map.addAttribute("proGoods_page",
						tdProviderGoodsService.findByProviderIdAndIsDistributionAndIsAudit(providerId,true, true, page, 10));
			}else{
				map.addAttribute("proGoods_page",
						tdProviderGoodsService.searchAndProviderIdAndIsDistributionAndIsAudit(providerId, true, true, keywords, page, 10));
			}
		}
		
		return "/client/distributor_supply";
	}
    
    @RequestMapping(value="/goods/supply")
    public String supplyGoods(Long categoryId,
    		String keywords,
    		Integer page,
    		Long[] listId,
    		Integer[] listChkId,
    		String eventTarget,
    		HttpServletRequest req,
    		HttpServletResponse resp,
    		ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		tdCommonService.setHeader(map, req);
		
		if(null == page )
		{
			page = 0;
		}
		
		String excelUrl=null;
		if(null != eventTarget){
			if("btnDelete".equalsIgnoreCase(eventTarget)){
				deleteAll(listId,listChkId);
			}else if("excel".equalsIgnoreCase(eventTarget))
			{
				excelUrl=SiteMagConstant.backupPath;
			}
		}
		
		TdDistributor distributor = tdDistributorService.findbyUsername(username);

		map.addAttribute("distributor", distributor);
		map.addAttribute("page",page);
		map.addAttribute("keywords", keywords);
		map.addAttribute("categoryId", categoryId);
		map.addAttribute("category", tdProductCategoryService.findOne(categoryId));
		
		List<TdProductCategory> categortList = tdProductCategoryService.findByParentIdIsNullAndIsEnableTrueOrderBySortIdAsc();
        map.addAttribute("category_list", categortList);
		
        
        map.addAttribute("dis_goods_page",tdDistributorGoodsService.findAll(distributor.getId(), categoryId, keywords, true, page, 10));
		if(null != categoryId)
		{
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
		
		if(null != excelUrl && !"".equals(excelUrl)){
			/**
			 *  导出表格
			 */
			// 创建一个webbook 对于一个Excel
			HSSFWorkbook wb = new HSSFWorkbook();
			// 在webbook中添加一个sheet,对应Excel文件中的sheet 
			HSSFSheet sheet = wb.createSheet("goods"); 
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
	        cell.setCellValue("编码");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 3);  
	        cell.setCellValue("价格");  
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 4);  
	        cell.setCellValue("返利比");  
	        cell.setCellStyle(style);
	        
	        cell = row.createCell((short) 5);  
	        cell.setCellValue("分类");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 6);  
	        cell.setCellValue("品牌");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 7);  
	        cell.setCellValue("单位");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 8);  
	        cell.setCellValue("库存");  
	        cell.setCellStyle(style); 
	        
	        cell = row.createCell((short) 9);  
	        cell.setCellValue("分销商家");  
	        cell.setCellStyle(style); 
	        
	        
        	Page<TdDistributorGoods> goods_page = tdDistributorGoodsService.findAll(distributor.getId(), categoryId, keywords, true, page, Integer.MAX_VALUE);
        	
        	if(supplyGoodsImport(goods_page,row,cell,sheet))
        	{
        		FileDownUtils.download("goods", wb, excelUrl, resp);
        	}
		}
		
		
		return "/client/distributor_supply_list";
	}
    
    
//    @RequestMapping(value="/deleteAll")
//	public String deleteAll(
//			Long[] listId,
//			Integer[] listChkId,
//			Integer page,
//			Long categoryId,String keywords,
//			HttpServletRequest req,
//			ModelMap map){
//		String username = (String)req.getSession().getAttribute("distributor");
//		if(null == username)
//		{
//			return "redirect:/login";
//		}
//		if(null == page )
//		{
//			page = 0;
//		}
//		
//		deleteAll(listId, listChkId);
//			
//		if(null == categoryId){
//			return "redirect:/distributor/goods/supply?page="+page+"&keywords="+keywords;
//		}else{
//			return "redirect:/distributor/goods/supply?page="+page+"&categoryId="+categoryId+"&keywords="+keywords;
//		}
//	}
    
    /**
     * 账号管理
     * 
     */
    @RequestMapping(value="/account")
    public String account(HttpServletRequest req,ModelMap map, Integer page){
    	String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == page){
			page = 0;
		}
		
		map.addAttribute("page", page);
    	tdCommonService.setHeader(map, req);
    	
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	
    	map.addAttribute("distributor", distributor);
    	map.addAttribute("pay_record_page",
    				tdPayRecordService.findByDistributorId(distributor.getId(), page, 5));
    	
    	return "/client/distributor_account";
    }
    
    /**
     * 充值
     * 
     */
    @RequestMapping(value="/topup1")
    public String topupOne(HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("distributor",
    				tdDistributorService.findbyUsername(username));
    	
    	// 支付方式列表
        setPayTypes(map, true, false, req);
    	
    	return "/client/distributor_top_one";
    }
    
    @RequestMapping(value="/topup2",method=RequestMethod.POST)
    public String topupTwo(HttpServletRequest req,ModelMap map,
    			Double provice,Long payTypeId){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	tdCommonService.setHeader(map, req);
    	
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);

    	Date current = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String curStr = sdf.format(current);
    	Random random = new Random();
    	
    	TdCash cash = new TdCash();
    	cash.setCashNumber("CS"+curStr+leftPad(Integer.toString(random.nextInt(999)), 3, "0"));
    	cash.setShopTitle(distributor.getTitle());
    	cash.setUsername(username);
    	cash.setCreateTime(new Date());
    	cash.setPrice(provice); // 金额
    	cash.setShopType(1L); // 类型-超市
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
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	tdCommonService.setHeader(map, req);
    	
    	map.addAttribute("distributor",tdDistributorService.findbyUsername(username));
    	if(null != cashNumber)
    	{
    		map.addAttribute("cash", tdCashService.findByCashNumber(cashNumber));
    	}
    	
    	return "/client/distributor_top_end";
    }
    
    /**
     * 提现
     * 
     */
    @RequestMapping(value="/draw1")
    public String withdraw(HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
    	
    	return "/client/distributor_draw_one";
    }
    
    /**
     * 加载信息录入
     * 
     */
    @RequestMapping(value="/draw/from")
    public String drawFrom(Long bankId,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	
    	map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
    	
    	if(null != bankId){
    		map.addAttribute("bank", tdBankService.findOne(bankId));
    	}
    	return "/client/distributor_draw_from";
    }
    
    /**
     * 加载卡号信息
     * 
     */
    @RequestMapping(value="/search/bank")
    public String bankList(HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	
    	
    	if(null != username){
    		map.addAttribute("bankList", tdBankService.findAll(username, 2));
    	}
    	return "/client/distributor_draw_list";
    }
    
    @RequestMapping(value="/delete/bank",method =RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteBank(Long bankId,HttpServletRequest req){
    	Map<String,Object> res = new HashMap<>();
    	res.put("code", 0);
    	
    	String username = (String)req.getSession().getAttribute("distributor");
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
    	
    	String username = (String)req.getSession().getAttribute("distributor");
    	if(null == username)
    	{
    		res.put("msg", "请重新登录");
    		return res;
    	}
    	
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	if(null == distributor)
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
    	
    	if(null == distributor.getVirtualMoney() || price > distributor.getVirtualMoney()){
    		res.put("msg", "账户余额不足");
    		return res;
    	}
    	
    	if(null == payPassword || !payPassword.equalsIgnoreCase(distributor.getPayPassword()))
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
		cash.setPrice(price);
		cash.setBank(bank);
		cash.setName(name);
		cash.setCreateTime(new Date());
		cash.setCashNumber("CS"+curStr+leftPad(Integer.toString(random.nextInt(999)), 3, "0"));
		cash.setShopTitle(distributor.getTitle());
		cash.setUsername(username);
		cash.setShopType(1L);
		cash.setType(2L);
		cash.setStatus(1L);
		
		tdCashService.save(cash);
		
		tdCashService.beforeCash(cash); // 提现先扣余额
		
		// 新加银行卡信息记录
		distributor.setBankCardCode(card);
		distributor.setBankTitle(bank);
		distributor.setBankName(name);
		tdDistributorService.save(distributor);
		
		boolean bankUser = true;
		List<TdBank> bankList = tdBankService.findAll(username, 2);
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
			tdBank.setType(2);
			tdBank.setBankCard(card);
			tdBank.setBankName(bank);
			tdBank.setName(name);
			
			tdBankService.save(tdBank);
		}
		
		res.put("msg", "提交成功");
		res.put("code", 1);
		return res;
    	
    }
    
    @RequestMapping(value = "/edit/ImageUrl", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editimageUrl(String imgUrl,HttpServletRequest req)
    {
    	Map<String, Object> res = new HashMap<>();
    	res.put("code", 1);
    	
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            res.put("msg", "登录超时");
            return res;
        }
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	distributor.setImageUri(imgUrl);
    	tdDistributorService.save(distributor);
    	res.put("code", 0);
    	return res;
    }
    
    // 广告列表
    @RequestMapping(value="/ad/list")
    public String adList(HttpServletRequest req,ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("distributor");
    	if(null == username)
    	{
    		return "redirect:/login";
    	}
    	
    	tdCommonService.setHeader(map, req);
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	
    	map.addAttribute("distributor", distributor);
    	map.addAttribute("ad_list", tdAdService.findByDistributorId(distributor.getId()));
    	
    	return "/client/distributor_ad";
    }
    
    @RequestMapping(value="/ad/edit")
    public String adEdit(Long id,HttpServletRequest req,ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("distributor");
    	if(null == username)
    	{
    		return "redirect:/login";
    	}
    	
    	map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("ad_type_list", tdAdTypeService.findAllOrderBySortIdAsc());
    	
    	if(null != id)
    	{
    		map.addAttribute("ad",tdAdService.findOne(id));
    	}
    	
    	return "/client/distributor_ad_edit";
    }
    
    // 添加广告
    @RequestMapping(value="/ad/save",method=RequestMethod.POST)
    public String adSave(TdAd ad,HttpServletRequest req,ModelMap map)
    {
    	String username =(String)req.getSession().getAttribute("distributor");
    	if(null == username)
    	{
    		return "redirect:/login";
    	}
    	
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	map.addAttribute("distributor", distributor);
    	
    	if(null != ad)
    	{
    		ad.setDistributorId(distributor.getId());
    		ad.setCreateTime(new Date());
    		tdAdService.save(ad);
    	}
    	return "redirect:/distributor/ad/list";
    }
    
    // 批量删除广告
    @RequestMapping(value="/ad/deleteAll")
    public String deleteAll(
    			Long[] listId,
    			Integer[] listChkId,
    			HttpServletRequest req,
    			ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("distributor");
    	if(null == username)
    	{
    		return "redirect:/login";
    	}
    	
    	adDelete(listId, listChkId);
    	
    	return "redirect:/distributor/ad/list";
    }
    
    @RequestMapping(value="/ad/delete")
    public String delete(Long id,HttpServletRequest req,ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("distributor");
    	if(null == username)
    	{
    		return "redirect:/login";
    	}
    	
    	if(null != id)
    	{
    		tdAdService.delete(id);
    	}
    	
    	return "redirect:/distributor/ad/list";
    }
    
    /**
     * 超市资讯列表
     * @author Max
     * 
     */
    @RequestMapping(value="/info/list")
    public String infoList(HttpServletRequest req,ModelMap map,Integer page)
    {
    	String username = (String)req.getSession().getAttribute("distributor");
    	if(null == username)
    	{
    		return "redirect:/login";
    	}
    	
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	
    	if(null == distributor)
    	{
    		return "/client/error_404";
    	}
    	
    	if(null == page)
    	{
    		page = 0;
    	}
    	
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("distributor", distributor);
    	
    	// 超市快讯
        List<TdArticleCategory> catList = tdArticleCategoryService.findByMenuId(10L);
        if(null != catList && catList.size() >0)
        {
        	for (TdArticleCategory tdCat : catList) {
        		if (null != tdCat.getTitle() && tdCat.getTitle().equals("超市快讯"))
                {
                    map.addAttribute("news_page", tdArticleService.findByMenuIdAndCategoryIdAndDistributorIdAndIsEnableOrderByIdDesc(10L,
                                    tdCat.getId(),distributor.getId(), page, 15));
                    break;
                }
			}
        }
    	
    	return "/client/distributor_article_list";
    }
    
    
    @RequestMapping(value="/info/edit")
    public String infoEdit(Long id,HttpServletRequest req,ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("distributor");
    	
    	if(null == username)
    	{
    		return "redirect:/login";
    	}
    	
    	map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
    	tdCommonService.setHeader(map, req);
    	
    	if(null !=id)
    	{
    		map.addAttribute("info", tdArticleService.findOne(id));
    	}
    	
    	return "/client/distributor_article_edit";
    }
    
    /**
     * 新加资讯
     * @author Max
     */
    @RequestMapping(value="/article/save")
    public String saveArticle(TdArticle article,
    			HttpServletRequest req,ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("distributor");
    	if(null == username)
    	{
    		return "redirct:/login";
    	}
    	
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	if(null == distributor)
    	{
    		return "/client/error_404";
    	}
    	
    	if(null != article)
    	{
    		if(null ==article.getId())
    		{
    			// 超市快讯
    	        List<TdArticleCategory> catList = tdArticleCategoryService.findByMenuId(10L);
    	        if(null != catList && catList.size() >0)
    	        {
    	        	for (TdArticleCategory tdCat : catList) {
    	        		if (null != tdCat.getTitle() && tdCat.getTitle().equals("超市快讯"))
    	                {
    	        			// 设置分类——超市快讯
    	                    article.setMenuId(tdCat.getMenuId());
    	                    article.setCategoryId(tdCat.getId());
    	                    article.setChannelId(tdCat.getChannelId());
    	        			break;
    	                }
    				}
    	        }
    	        // 归属店铺
    	        article.setDistributorId(distributor.getId());
    	        article.setSource(distributor.getTitle());
    	        article.setImgUrl("");
    	        article.setCallIndex("");
    		}
    		article.setSortId(99L);
    		tdArticleService.save(article);
    	}
    	return "redirect:/distributor/info/list";
    }
    /**
     * 删除超市资讯
     * @author Max
     * 
     */
    @RequestMapping(value="/info/delete")
    public String deleteInfo(Long id,HttpServletRequest req,ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("distributor");
    	if(null == username)
    	{
    		return "redirect:/login";
    	}
    	if(null != id)
    	{
    		tdArticleService.delete(id);
    	}
    	return "redirect:/distributor/info/list";
    }
    
    /**
     * 自提点列表
     * @author Max
     * 
     */
    @RequestMapping(value="/address/{method}")
    public String addressList(@PathVariable String method,
    		Long id,
    		HttpServletRequest req,ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("distributor");
    	if(null == username)
    	{
    		return "redirect:/login";
    	}
    	tdCommonService.setHeader(map, req);
    	
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	map.addAttribute("distributor", distributor);
    	if(null != distributor)
    	{
    		List<TdShippingAddress> addressList = distributor.getShippingList();
    		
    		if (null != method && !method.isEmpty()) {
                if (method.equalsIgnoreCase("update")) {
                    if (null != id) {
                        // map.addAttribute("address", s)
                        for (TdShippingAddress add : addressList) {
                            if (add.getId().equals(id)) {
                                map.addAttribute("address", add);
                            }
                        }
                    }
                } else if (method.equalsIgnoreCase("delete")) {
                    if (null != id) {
                        for (TdShippingAddress add : addressList) {
                            if (add.getId().equals(id)) {
                                addressList.remove(id);
                                distributor.setShippingList(addressList);
                                tdShippingAddressService.delete(add);
                                return "redirect:/distributor/address/list";
                            }
                        }
                    }
                }else if(method.equalsIgnoreCase("default")){
                	if(null != id){
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
                	return "redirect:/distributor/address/list";
                } 
            }
    		map.addAttribute("address_list", distributor.getShippingList());
    	}
    	
    	return "/client/distributor_address_list";
    }
    
    @RequestMapping(value="/address/save",method=RequestMethod.POST)
    public String addressSave(TdShippingAddress tdShippingAddress,HttpServletRequest req,ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("distributor");
    	if(null == username)
    	{
    		return "redirect:/login";
    	}
    	tdCommonService.setHeader(map, req);
    	
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	if(null != distributor)
    	{
    		List<TdShippingAddress> addressList = distributor.getShippingList();
    		
    		// 修改
    		if (null != tdShippingAddress.getId()) {
    			tdShippingAddressService.save(tdShippingAddress);
    		}
    		// 新增
    		else {
    			addressList.add(tdShippingAddressService
    					.save(tdShippingAddress));
    			distributor.setShippingList(addressList);
    			tdDistributorService.save(distributor);
    		}
    	}
        return "redirect:/distributor/address/list";
    }
    
    /**
     * 找回支付密码
     * 第一步
     */
    @RequestMapping("/retrieve_step1")
    public String retrieve1(String username,String mobile,Integer errCode,HttpServletRequest req,ModelMap map){
    	String distributor = (String)req.getSession().getAttribute("distributor");
    	if(null == distributor)
    	{
    		return "redirect:/login";
    	}
    	tdCommonService.setHeader(map, req);
    	
    	TdDistributor tdDistributor = tdDistributorService.findbyUsername(distributor);
    	
    	map.addAttribute("distributor", tdDistributor);
    	
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
    	
    	return "/client/distributor_retrieve_one";
    	
    }
    /**
     * 找回支付密码
     * 第二步，账号、手机号验证码验证
     */
    @RequestMapping(value = "/retrieve_step2", method = RequestMethod.POST)
	public String Step2(String username,String mobile,String smsCode,HttpServletRequest req, ModelMap map){
    	String distributor = (String)req.getSession().getAttribute("distributor");
    	if(null == distributor)
    	{
    		return "redirect:/login";
    	}
    	if (null == smsCode) {
			return "redirect:/distributor/retrieve_step1?errCode=4&username="+username+"&mobile="+mobile;
		}
		String smsCodeSave = (String) req.getSession().getAttribute("SMSCODE");
		if(null == smsCodeSave){
			return "redirect:/distributor/retrieve_step1?errCode=3&username="+username+"&mobile="+mobile;
		}
		
		if (!smsCodeSave.equalsIgnoreCase(smsCode)) {
			return "redirect:/distributor/retrieve_step1?errCode=4&username="+username+"&mobile="+mobile;
		}
		tdCommonService.setHeader(map, req);
		
		TdDistributor tdDistributor = tdDistributorService.findbyUsername(distributor);
    	
    	map.addAttribute("distributor", tdDistributor);
		map.addAttribute("username", username);
		map.addAttribute("mobile", mobile);
		
		return "/client/distributor_retrieve_two";
	}
    /**
     * 找回支付密码
     * 第三步，设置新密码
     */
   @RequestMapping(value = "/retrieve_step3", method = RequestMethod.POST)
	public String Step3(String username,String payPassword, HttpServletRequest req, ModelMap map){
	   TdDistributor tdDistributor = tdDistributorService.findbyUsername(username);
		tdCommonService.setHeader(map, req);
		if (null != payPassword) {
			tdDistributor.setPayPassword(payPassword);
			tdDistributorService.save(tdDistributor);
			
			map.addAttribute("distributor", tdDistributor);
			return "/client/distributor_retrieve_three";
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
    	
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	if(null == distributor)
    	{
    			map.put("msg", "账号不存在");
    			return map;
    	}
    	
    	if(null == mobile || !mobile.equals(distributor.getMobile())){
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
     * 超市给会员转账
     * @author Max
     * 2016-10-18
     * 
     */
    @RequestMapping("/transfer1")
    public String transfer(HttpServletRequest req,ModelMap map){
    	String distributor = (String)req.getSession().getAttribute("distributor");
    	if(null == distributor)
    	{
    		return "redirect:/login";
    	}
    	
    	tdCommonService.setHeader(map, req);
    	
    	map.addAttribute("distributor", tdDistributorService.findbyUsername(distributor));
    	
    	return "/client/distributor_transfer1";
    }
    
    @RequestMapping(value="/transfer2",method=RequestMethod.POST)
    public String transfer2(String username,Double price,HttpServletRequest req,ModelMap map){
    	String distributor = (String)req.getSession().getAttribute("distributor");
    	if(null == distributor)
    	{
    		return "redirect:/login";
    	}
    	
    	map.addAttribute("distributor", tdDistributorService.findbyUsername(distributor));
    	tdCommonService.setHeader(map, req);
    	
    	map.addAttribute("name", username);
    	map.addAttribute("price", price);
    	
    	return "/client/distributor_transfer2";
    }
    
    @RequestMapping(value="/transfer3",method=RequestMethod.POST)
    public String transfer3(String username,Double price,
    					String payPwd,HttpServletRequest req,ModelMap map){
    	String distributor = (String)req.getSession().getAttribute("distributor");
    	if(null == distributor)
    	{
    		return "redirect:/login";
    	}
    	
    	tdCommonService.setHeader(map, req);
    	
    	if(username == null || price == null){
    		return "/client/error_404";
    	}
    	
    	TdDistributor tdDistributor = tdDistributorService.findbyUsername(distributor);
    	// 先扣除超市转账金额
    	
    	tdDistributor.setVirtualMoney(tdDistributor.getVirtualMoney()-price);
    	tdDistributorService.save(tdDistributor);
    	
    	TdUser user = tdUserService.findByUsername(username);
    	if(null == user){
    		user = tdUserService.findByMobile(username);
    	}
    	if(null != user){
    		if(null == user.getVirtualMoney()){
    			user.setVirtualMoney(price);
    		}else{
    			user.setVirtualMoney(user.getVirtualMoney() + price);
    		}
    	}
    	
    	
    	Date current = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String curStr = sdf.format(current);
    	Random random = new Random();
    	String number = "Z"+curStr+leftPad(Integer.toString(random.nextInt(999)), 3, "0");
    	
    	 // 添加会员虚拟账户金额记录
    	TdPayRecord record = new TdPayRecord();
    	
    	record.setAliPrice(0.0);
    	record.setPostPrice(0.0);
    	record.setRealPrice(price);
    	record.setTotalGoodsPrice(price);
    	record.setServicePrice(0.0);
    	record.setProvice(price);
    	record.setOrderNumber(number);
    	record.setCreateTime(new Date());
    	record.setUsername(username);
    	record.setType(2L);
    	record.setCont("由"+tdDistributor.getTitle()+"转账");
    	record.setDistributorTitle(tdDistributor.getTitle());
    	record.setStatusCode(1);
    	tdPayRecordService.save(record); // 保存会员虚拟账户记录
    	
    	record = new TdPayRecord();
    	
    	record.setAliPrice(0.0);
    	record.setPostPrice(0.0);
    	record.setRealPrice(price);
    	record.setTotalGoodsPrice(price);
    	record.setServicePrice(0.0);
    	record.setProvice(price);
    	record.setOrderNumber(number);
    	record.setCreateTime(new Date());
    	record.setCont("向会员"+username+"转账扣款");
    	record.setDistributorId(tdDistributor.getId());
    	record.setDistributorTitle(tdDistributor.getTitle());
    	record.setStatusCode(1);
    	tdPayRecordService.save(record); // 保存超市扣款记录
    	
    	map.addAttribute("name", username);
    	map.addAttribute("price", price);
    	map.addAttribute("number", number);
    	map.addAttribute("distributor", tdDistributor);
    	
		return "/client/distributor_transfer3";
    }
    
    /**
     * 转账各项验证
     * @return
     */
    @RequestMapping(value="/transferCheck",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> checkUser(String data,String type,HttpServletRequest req)
    {
    	Map<String,Object> res = new HashMap<String, Object>();
    	res.put("code", 0);
    	
    	if(null != type){ // 转入账号验证
    		if("username".equalsIgnoreCase(type)){
    			TdUser user = tdUserService.findByUsername(data);
    			if(null == user){
    				user = tdUserService.findByMobile(data);
    				if(null == user){
    					res.put("msg", "输入的账号不存在，请仔细检查");
    					return res;
    				}
    			}
    			
    			res.put("code", 1);
    			res.put("user", user);
    			return res;
    		}else if("price".equalsIgnoreCase(type)){ // 转账金额验证
    			 double sferPrice = Double.parseDouble(data);
    			 String username = (String)req.getSession().getAttribute("distributor");
    			 
    			 if(null == username){
    				 res.put("msg", "登录超时");
    				 return res;
    			 }
    			 // 账号验证
    			 TdDistributor distributor = tdDistributorService.findbyUsername(username);
    			 if(null == distributor){
    				 res.put("msg", "账号异常");
    				 return res;
    			 }
    			 // 余额验证
    			 if(null == distributor.getVirtualMoney() || distributor.getVirtualMoney() < sferPrice){
    				 res.put("msg", "当前账户余额不足");
    				 return res;
    			 }
    			 res.put("code", 1);
    		}else if("payPwd".equalsIgnoreCase(type)){
    			String username = (String)req.getSession().getAttribute("distributor");
   			 
	   			 if(null == username){
	   				 res.put("msg", "登录超时");
	   				 return res;
	   			 }
	   			 // 账号验证
	   			 TdDistributor distributor = tdDistributorService.findbyUsername(username);
	   			 if(null == distributor){
	   				 res.put("msg", "账号异常");
	   				 return res;
	   			 }
	   			 if(null == distributor.getPayPassword() || !distributor.getPayPassword().equalsIgnoreCase(data)){
	   				 res.put("msg", "支付密码错误");
	   				 return res;
	   			 }
	   			 res.put("code", 1);
    		}
    		
    	}
    	return res;
    }
    
    @RequestMapping(value="/relevance/list")
	public String relevance(Long goodsId,
			HttpServletRequest req,ModelMap map,
			HttpServletResponse resp)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		
		map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
		tdCommonService.setHeader(map, req);
		map.addAttribute("goodsId", goodsId);
		
		return "/client/distributor_relevance";
	}
    /**
     * 加载已关联商品
     * @author Max
     * 2016-10-24
     */
    @RequestMapping(value="/search/relevance",method=RequestMethod.POST)
    public String relevanceGoods(Long goodsId,HttpServletRequest req,ModelMap map){
    	if(null != goodsId){
    		map.addAttribute("goodsId", goodsId);
    		map.addAttribute("relevance_list", tdDistributorGoodsService.findRelevanceGoods(goodsId));
    	}
    	
    	return "/client/distributor_relevance_list";
    }
    /**
     *  加载出售中的商品
     *  @author Max
     *  2016-10-24
     */
    @RequestMapping(value="/search/saleGoods")
    public String saleGoods(String keywords,Integer page,
    			String isDistribution,Long goodsId,
			Long categoryId,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	
    	if(null == page)
		{
			page=0;
		}
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		if(null != distributor){
			PageRequest pageRequest = new PageRequest(page, 10,new Sort(Direction.DESC, "id"));
			
			map.addAttribute("sale_page",tdDistributorGoodsService.findAll(distributor.getId(), true, null,categoryId, keywords, pageRequest));
			
		}
    	
		// 参数注回
		map.addAttribute("keywords", keywords);
		map.addAttribute("page", page);
		map.addAttribute("categoryId", categoryId);
		map.addAttribute("goodsId", goodsId);
		
		List<TdProductCategory> categortList = tdProductCategoryService.findByParentIdIsNullAndIsEnableTrueOrderBySortIdAsc();
		map.addAttribute("categoryList", categortList);
		
		if(null != categoryId){
       	 TdProductCategory category = tdProductCategoryService.findOne(categoryId);
            for (TdProductCategory tdProductCategory : categortList) {
            	
            	if(category.getParentTree().contains("["+tdProductCategory.getId()+"]"))
            	{
            		List<TdProductCategory> cateList = tdProductCategoryService.findByParentIdOrderBySortIdAsc(tdProductCategory.getId());
            		map.addAttribute("cateList", cateList);
            		
            		for (TdProductCategory productCategory : cateList) {
            			if(category.getParentTree().contains("["+productCategory.getId()+"]"))
            			{
            				map.addAttribute("category_list", tdProductCategoryService.findByParentIdOrderBySortIdAsc(productCategory.getId()));
            			}
            		}
            		
            	}
            }
            map.addAttribute("category", category);
       }
		return "/client/distributor_rele_goods";
    }
    
    @RequestMapping(value="/relevance/{type}",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> paramRelevance(@PathVariable String type,
    		Long goodsId1,// 商品ID
    		Long goodsId2, // 关联商品ID
    		Long id, // 关联记录ID
    		HttpServletRequest req,ModelMap map){
    	Map<String,Object> res = new HashMap<String, Object>();
    	res.put("code", 0);
    	String username = (String)req.getSession().getAttribute("distributor");
    	if(null == username){
    		res.put("msg", "登录超时");
    		return res;
    	}
    	
    	if(null != type){
    		// 新增关联
    		if("add".equalsIgnoreCase(type)){
    			// 查询以商品ID为主，需关联商品ID为副的关联记录，
    			List<TdRelevance> list = tdRelevanceService.findAll(goodsId1, goodsId2);
    			if(null != list && list.size() > 0){
    				res.put("msg", "此关联已存在");
    				return res;
    			}
    			// 查询以商品ID为副，需关联商品Id为主的关联记录
    			List<TdRelevance> reList = tdRelevanceService.findAll(goodsId2, goodsId1);
    			if(null != reList && reList.size() > 0 ){
    				res.put("msg", "此关联已存在");
    				return res;
    			}
    			// 没有相关关联记录，保存新记录
    			TdRelevance relevance = new TdRelevance();
    			relevance.setGoodsId1(goodsId1); // 主ID
    			relevance.setGoodsId2(goodsId2); // 副ID （关联商品ID）
    			
    			tdRelevanceService.save(relevance);
    			res.put("msg", "关联成功");
    			res.put("code", 1);
    			return res;
    		}else if("delete".equalsIgnoreCase(type)){
    			List<TdRelevance> list = tdRelevanceService.findAll(goodsId1, goodsId2);
    			if(null != list && list.size() > 0){
    				// 删除已商品ID为主关联商品ID为副的关联
    				tdRelevanceService.delete(list);
    			}
    			// 查询以商品ID为副，需关联商品Id为主的关联记录
    			List<TdRelevance> reList = tdRelevanceService.findAll(goodsId2, goodsId1);
    			if(null != reList && reList.size() > 0 ){
    				// 删除已关联商品ID为主商品ID为副的关联
    				tdRelevanceService.delete(reList);
    			}
    			res.put("msg", "已成功解除关联");
    			res.put("code",1);
    			return res;
    		}
    	}
    	
    	return res;
    }
    
    
    // 批量删除广告
    public void adDelete(Long[] ids,Integer[] chkIds)
    {
    	
    	if(null == ids || null == chkIds
                || ids.length < 1 || chkIds.length < 1)
    	{
    		return ;
    	}
    	
    	for(int chkid : chkIds)
    	{
    		if(chkid >=0 && ids.length > chkid)
    		{
    			Long id = ids[chkid];
    			
    			tdAdService.delete(id);
    		}
    	}
    }
    
    @RequestMapping(value="/region/change",method=RequestMethod.POST)
    public String regionChange(String city,String dist,HttpServletRequest req,ModelMap map)
    {
    	tdCommonService.setHeader(map, req);
    	
    	if(null != city)
    	{
    		map.addAttribute("selcity", city);
    	}
    	if(null != dist)
    	{
    		map.addAttribute("dist", dist);
    	}
    	
    	return "/client/common_distributor_list";
    }
    
    /**
     * 超市同意普通商品退货，
     * 退还商品货款，扣除超市相应余额
     */
    public void turnGoods(TdUserReturn userRturn,TdDistributor tdDistributor){
    	Double turnPrice =0.0; // 退款金额
    	
    	turnPrice = userRturn.getRealPrice();
    	if(null != tdDistributor.getVirtualMoney()&&  tdDistributor.getVirtualMoney() > turnPrice)
		{
    		
    		// 扣除超市余额
    		tdDistributor.setVirtualMoney(tdDistributor.getVirtualMoney()- turnPrice);
        	tdDistributorService.save(tdDistributor);
        		
        		
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
        	record.setRealPrice(turnPrice);
        	record.setTotalGoodsPrice(turnPrice);
        	record.setServicePrice(0.0);
        	record.setProvice(userRturn.getGoodsPrice());
        	record.setOrderNumber(userRturn.getOrderNumber());
        	record.setCreateTime(new Date());
        	record.setCont("用户退货返款");
        	record.setDistributorId(userRturn.getShopId());
        	record.setDistributorTitle(userRturn.getShopTitle());
        	record.setStatusCode(1);
        	tdPayRecordService.save(record); // 保存超市退款记录
		}
    }
    
    /**
     * 分销商品退货，超市同意退，退回超市提取的代售提成给分销商，交由分销商处理
     * 返回值：0 商品已删除，  1，商家以删除， 
     * 		   2，超市余额不足以退给分销商提成  3，超市处理成功
     * 
     */
    public Integer supplyReutrn(TdUserReturn userRturn,TdDistributor distributor){
    	
    	// 查找超市商品记录
    	TdDistributorGoods goods = tdDistributorGoodsService.findOne(userRturn.getGoodsId());
    	if(null == goods){
    		return 0;
    	}
    	
    	// 超市分销商商品记录
    	TdProviderGoods providerGoods = tdProviderGoodsService.findByProviderIdAndGoodsId(userRturn.getSupplyId(), goods.getGoodsId());
    	TdProvider supply = tdProviderService.findOne(userRturn.getSupplyId());
    	if(null == supply){
    		
    		return 1;
    	}
    	if(null == providerGoods){
    		return 0;
    	}
    	
    	Double turnRation =0.0;  // 返利比
    	Double trunPrice =0.0; // 退货额
    	Double rationPrice = 0.0;  // 超市应退提成
    	
    	turnRation =providerGoods.getShopReturnRation();
    	trunPrice = userRturn.getRealPrice();
    	rationPrice = trunPrice * turnRation;
    	
    	if(null == distributor.getVirtualMoney() || distributor.getVirtualMoney() < rationPrice){
    		return 2;
    	}
    	
    	// 超市扣除提成
    	distributor.setVirtualMoney(distributor.getVirtualMoney() - rationPrice);
    	tdDistributorService.save(distributor);
    	
    	// 分销商获取提成
    	supply.setVirtualMoney(supply.getVirtualMoney() + rationPrice);
    	tdProviderService.save(supply);
    	
    	
    	 // 添加超市虚拟账户退货提成记录
    	TdPayRecord record = new TdPayRecord();
    	
    	record.setAliPrice(0.0);
    	record.setPostPrice(0.0);
    	record.setRealPrice(rationPrice);
    	record.setTotalGoodsPrice(rationPrice);
    	record.setServicePrice(0.0);
    	record.setProvice(userRturn.getGoodsPrice());
    	record.setOrderNumber(userRturn.getOrderNumber());
    	record.setCreateTime(new Date());
    	record.setDistributorId(userRturn.getShopId());
    	record.setType(1L);
    	record.setCont("同意分销商品退货退还代售提成");
    	record.setDistributorTitle(userRturn.getShopTitle());
    	record.setStatusCode(1);
    	
    	tdPayRecordService.save(record); 
    	
    	record = new TdPayRecord();
    	
    	record.setCont("超市同意用户退货退还返利");
		record.setCreateTime(new Date());
		record.setDistributorTitle(distributor.getTitle());
		record.setProviderId(supply.getId());
		record.setProviderTitle(supply.getTitle());
		record.setOrderNumber(userRturn.getOrderNumber());
		record.setStatusCode(1);

		record.setProvice(rationPrice); // 订单总额
		record.setPostPrice(0.0); // 邮费
		record.setAliPrice(0.0); // 第三方费
		record.setServicePrice(0.0); // 平台费
		record.setTotalGoodsPrice(rationPrice); // 商品总价
		record.setTurnPrice(0.0); // 超市返利
		record.setRealPrice(rationPrice); // 实际获利
    	tdPayRecordService.save(record); // 保存超市退款记录
    	
    	
    	return 3;
    }
    
 // 添加会员积分
// 	public void addUserPoint(TdOrder order,String username){
// 		
// 		TdUser user = tdUserService.findByUsername(username);
// 		
// 		 // 添加积分使用记录
// 		 if (null != user) {
// 			 if (null == user.getTotalPoints())
// 			 {
// 				 user.setTotalPoints(0L);
// 				 user = tdUserService.save(user);
// 			 }
// 		
// 			 if(null != order.getTotalPrice()){
//				 Long turnPoint = Math.round(order.getTotalPrice());
// 				 
//				 TdUserPoint userPoint = new TdUserPoint();
//				 userPoint.setDetail("购买商品获得积分");
//				 userPoint.setOrderNumber(order.getOrderNumber());
//				 userPoint.setPoint(turnPoint);
//				 userPoint.setPointTime(new Date());
//				 userPoint.setUsername(username);
//				 userPoint.setTotalPoint(user.getTotalPoints() + turnPoint);
//				 tdUserPointService.save(userPoint);
//				 
//				 user.setTotalPoints(user.getTotalPoints() + turnPoint);
//				 tdUserService.save(user);
//			 }
// 		 }
// 	}
    
    // 批量上下架
	public void onSaleAll(Long[] ids,Integer[] chkIds)
	{
        if (null == ids || null == chkIds
                || ids.length < 1 || chkIds.length < 1)
        {
            return;
        }
        
        for (int chkId : chkIds)
        {
            if (chkId >=0 && ids.length > chkId)
            {
                Long id = ids[chkId];
                TdDistributorGoods goods = tdDistributorGoodsService.findOne(id);
               	if(null != goods){
               		if(goods.getIsOnSale()==true){
               			goods.setIsOnSale(false);
               		}else{
               			goods.setIsOnSale(true);
               		}
               		tdDistributorGoodsService.save(goods);
               	}
            }
        }
	}
	
	// 批量删除
	public void deleteAll(Long[] ids,Integer[] chkIds)
	{
		 if (null == ids || null == chkIds
	                || ids.length < 1 || chkIds.length < 1)
	        {
	            return;
	        }
	        
	        for (int chkId : chkIds)
	        {
	            if (chkId >=0 && ids.length > chkId)
	            {
	                Long id = ids[chkId];
	                tdDistributorGoodsService.delete(id);
	            }
	        }
	}
	
	// 批量分销
	public void supplyAll(Long[] ids,Integer[] chkIds, String username)
	{
		if (null == ids || null == chkIds
                || ids.length < 1 || chkIds.length < 1)
        {
            return;
        }
        
        for (int chkId : chkIds)
        {
            if (chkId >=0 && ids.length > chkId)
            {
                Long id = ids[chkId];
                supply(username, id);
            }
        }
	}
	
	/**
	 * 订单状态批量修改
	 * @author Max
	 * 2016-10-31
	 * 
	 */
	private void edirOrderStatusId(Long[] ids, Integer[] chkIds) {
		if (null == ids || null == chkIds
                || ids.length < 1 || chkIds.length < 1)
        {
            return;
        }
		
		for (int chkId : chkIds)
        {
            if (chkId >=0 && ids.length > chkId)
            {
                Long id = ids[chkId];
                TdOrder order = tdOrderService.findOne(id);
                if(null != order && null != order.getStatusId()){
                	if(order.getStatusId() ==1L){
                		order.setStatusId(2L);
                	}else if(order.getStatusId() ==2L){
                		order.setStatusId(3L);
                	}else if(order.getStatusId() ==3L){
                		order.setStatusId(4L);
                	}else if(order.getStatusId() ==4L){
                		order.setStatusId(5L);
                	}else if(order.getStatusId() ==5L){
                		order.setStatusId(6L);
                	}
                	tdOrderService.save(order);
                }
            }
        }
	}
	
	/**
	 * 批量删除订单
	 * @author Max
	 * 2016-10-31
	 * 
	 */
	public void deleteOrder(Long[] ids,Integer[] chkIds)
	{
		 if (null == ids || null == chkIds
	                || ids.length < 1 || chkIds.length < 1)
	        {
	            return;
	        }
	        
	        for (int chkId : chkIds)
	        {
	            if (chkId >=0 && ids.length > chkId)
	            {
	                Long id = ids[chkId];
	                TdOrder order = tdOrderService.findOne(id);
	                if(order.getStatusId() == 7L){
	                	tdOrderService.delete(id);
	                }
	            }
	        }
	}
	
	// 超市分销商品
	public void supply(String username,Long goodsId)
	{
		TdProviderGoods pGoods = tdProviderGoodsService.findOne(goodsId);
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		TdGoods goods = tdGoodsService.findOne(pGoods.getGoodsId());
		TdDistributorGoods distributorGoods = tdDistributorGoodsService.findByDistributorIdAndGoodsId(distributor.getId(),pGoods.getGoodsId());

		if(null != pGoods){
			// 查找代理商品的所有规格
			List<TdSpecificat> specList = tdSpecificatService.findByShopIdAndGoodsIdAndType(pGoods.getProId(), pGoods.getGoodsId(), 3);
			if(null != specList){
				// 如果代理的商品有规格，复制一份规格信息作为超市用
				for (TdSpecificat tdSpecificat : specList) {
					TdSpecificat specificat = new TdSpecificat();
					
					specificat.setGoodsId(tdSpecificat.getGoodsId());
					specificat.setSpecifict(tdSpecificat.getSpecifict());
					specificat.setLeftNumber(tdSpecificat.getLeftNumber());
					specificat.setOldId(tdSpecificat.getId());// 原规格ID， 删除时可使用
					specificat.setShopId(distributor.getId());
					specificat.setType(1);
					tdSpecificatService.save(specificat);
				}
			}
		}
		
    	
    	if(null == distributorGoods)
    	{
    		distributorGoods=new TdDistributorGoods();
    		distributorGoods.setDistributorTitle(distributor.getTitle());
			distributorGoods.setGoodsId(pGoods.getGoodsId());
			distributorGoods.setGoodsTitle(pGoods.getGoodsTitle());
			distributorGoods.setSubGoodsTitle(pGoods.getSubGoodsTitle());
			distributorGoods.setGoodsPrice(pGoods.getOutFactoryPrice());
			distributorGoods.setBrandId(goods.getBrandId());
			distributorGoods.setBrandTitle(goods.getBrandTitle());
			distributorGoods.setCategoryId(goods.getCategoryId());
			distributorGoods.setCategoryIdTree(goods.getCategoryIdTree());
			distributorGoods.setCode(pGoods.getCode());
			distributorGoods.setProductId(goods.getProductId());
			distributorGoods.setSelectOneValue(goods.getSelectOneValue());
			distributorGoods.setSelectTwoValue(goods.getSelectTwoValue());
			distributorGoods.setSelectThreeValue(goods.getSelectThreeValue());
			distributorGoods.setCoverImageUri(goods.getCoverImageUri());
			distributorGoods.setGoodsMarketPrice(pGoods.getGoodsMarketPrice());
//			distributorGoods.setGoodsParamList(goods.getParamList());
			distributorGoods.setReturnPoints(goods.getReturnPoints());
			distributorGoods.setParamValueCollect(goods.getParamValueCollect());
			distributorGoods.setProviderId(tdProviderGoodsService.findProviderId(pGoods.getId()));
			distributorGoods.setProviderTitle(pGoods.getProviderTitle());
			distributorGoods.setIsDistribution(true);
			distributorGoods.setIsOnSale(true);
			distributorGoods.setIsAudit(true);
			distributorGoods.setOnSaleTime(new Date());
			distributorGoods.setLeftNumber(pGoods.getLeftNumber());
			distributorGoods.setDisId(distributor.getId());
			distributorGoods.setUnit(pGoods.getUnit());
			
			distributor.getGoodsList().add(distributorGoods);
    	}else{
    		distributorGoods.setCategoryId(goods.getCategoryId());
			distributorGoods.setCategoryIdTree(goods.getCategoryIdTree());
    		distributorGoods.setGoodsPrice(pGoods.getOutFactoryPrice());
    		distributorGoods.setGoodsTitle(pGoods.getGoodsTitle());
    		distributorGoods.setLeftNumber(pGoods.getLeftNumber());
    		distributorGoods.setIsDistribution(true);
			distributorGoods.setIsOnSale(true);
			distributorGoods.setIsAudit(true);
			distributorGoods.setOnSaleTime(new Date());
			distributorGoods.setDisId(distributor.getId());
			distributorGoods.setUnit(pGoods.getUnit());
    	}
    	tdDistributorService.save(distributor);
	}
	
	// 销售单
	@SuppressWarnings("deprecation")
	public Boolean ImportData(Page<TdOrder> orderPage,HSSFRow row, HSSFCell cell, HSSFSheet sheet)
	{
		for (int i = 0; i < orderPage.getContent().size(); i++) {
			row = sheet.createRow((int)i+1);
			TdOrder order = orderPage.getContent().get(i);
			row.createCell((short) 0).setCellValue(order.getOrderNumber());
			row.createCell((short) 1).setCellValue(order.getUsername());
			row.createCell((short) 2).setCellValue(order.getShippingName());
			row.createCell((short) 3).setCellValue(order.getShippingAddress());
			row.createCell((short) 4).setCellValue(order.getShippingPhone());
			row.createCell((short) 5).setCellValue(StringUtils.scale(order.getTotalPrice()));
			row.createCell((short) 6).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(order.getOrderTime()));
			if(order.getStatusId() ==2)
			{
				row.createCell((short) 7).setCellValue("待付款");
			}else if(order.getStatusId() ==3)
			{
				row.createCell((short) 7).setCellValue("待发货");
			}else if(order.getStatusId() ==4)
			{
				row.createCell((short) 7).setCellValue("待收货");
			}else if(order.getStatusId() ==5)
			{
				row.createCell((short) 7).setCellValue("待评价");
			}else if(order.getStatusId() ==6)
			{
				row.createCell((short) 7).setCellValue("已完成");
			}else if(order.getStatusId() ==7)
			{
				row.createCell((short) 7).setCellValue("已取消");
			}
			row.createCell((short) 8).setCellValue(order.getPayTypeTitle());
			if(order.getDeliveryMethod()==1){
				row.createCell((short) 9).setCellValue("门店自提");
			}else{
				row.createCell((short) 9).setCellValue("送货上门");
			}
			row.createCell((short) 10).setCellValue(order.getShipAddress());
			row.createCell((short) 11).setCellValue(order.getRemarkInfo());
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
					row.createCell((short) 1).setCellValue(record.getDistributorTitle());
					row.createCell((short) 2).setCellValue(StringUtils.scale(record.getServicePrice()));
					row.createCell((short) 3).setCellValue(StringUtils.scale(record.getPostPrice()));
					row.createCell((short) 4).setCellValue(StringUtils.scale(record.getTotalGoodsPrice()));
					row.createCell((short) 5).setCellValue(StringUtils.scale(record.getRealPrice()));
					row.createCell((short) 6).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(record.getCreateTime()));
					row.createCell((short) 7).setCellValue(record.getCont());
				}
			}
			return true;
		}
	
	// 进货单
	@SuppressWarnings("deprecation")
	public Boolean InOrderImport(Page<TdOrder> orderPage,HSSFRow row, HSSFCell cell, HSSFSheet sheet)
	{
		for (int i = 0; i < orderPage.getContent().size(); i++) {
			row = sheet.createRow((int)i+1);
			TdOrder order = orderPage.getContent().get(i);
			// 获取用户信息
			TdUser user = tdUserService.findByUsername(order.getUsername());
			TdProvider provider = tdProviderService.findOne(order.getShopId());
			
			row.createCell((short) 0).setCellValue(order.getOrderNumber());
			row.createCell((short) 1).setCellValue(order.getShippingName());
			row.createCell((short) 2).setCellValue(order.getShopTitle());
			if(null != provider){
				row.createCell((short) 3).setCellValue(provider.getMobile());
				row.createCell((short) 4).setCellValue(provider.getAddress());
				
			}
			row.createCell((short) 5).setCellValue(StringUtils.scale(order.getTotalPrice()));
			row.createCell((short) 6).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(order.getOrderTime()));
			if(order.getStatusId() ==2)
			{
				row.createCell((short) 7).setCellValue("待付款");
			}else if(order.getStatusId() ==3)
			{
				row.createCell((short) 7).setCellValue("待发货");
			}else if(order.getStatusId() ==4)
			{
				row.createCell((short) 7).setCellValue("待收货");
			}else if(order.getStatusId() ==5)
			{
				row.createCell((short) 7).setCellValue("待评价");
			}else if(order.getStatusId() ==6)
			{
				row.createCell((short) 7).setCellValue("已完成");
			}else if(order.getStatusId() ==7)
			{
				row.createCell((short) 7).setCellValue("已取消");
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
	
	@SuppressWarnings("deprecation")
	public Boolean providerGoodsImport(Page<TdProviderGoods> goodsPage,HSSFRow row, HSSFCell cell, HSSFSheet sheet)
	{
		if(null != goodsPage && goodsPage.getContent().size() >0){
			for (int i = 0; i < goodsPage.getContent().size(); i++) {
				row = sheet.createRow((int)i+1);
				TdProviderGoods providerGoods = goodsPage.getContent().get(i);
				
				row.createCell((short) 0).setCellValue(providerGoods.getProviderTitle());
				row.createCell((short) 1).setCellValue(providerGoods.getGoodsTitle());
				row.createCell((short) 2).setCellValue(providerGoods.getSubGoodsTitle());
				row.createCell((short) 3).setCellValue(providerGoods.getCode());
				
				TdProductCategory category = tdProductCategoryService.findOne(providerGoods.getCategoryId());
				if(null != category){
					row.createCell((short) 4).setCellValue(category.getTitle());
				}
				TdGoods goods = tdGoodsService.findOne(providerGoods.getGoodsId());
				if(null != goods){
					row.createCell((short) 5).setCellValue(goods.getBrandTitle());
				}
				row.createCell((short) 6).setCellValue(providerGoods.getUnit());
				row.createCell((short) 7).setCellValue(StringUtils.scale(providerGoods.getOutFactoryPrice()));
				row.createCell((short) 8).setCellValue(StringUtils.scale(providerGoods.getGoodsMarketPrice()));
				row.createCell((short) 9).setCellValue(providerGoods.getLeftNumber());
			}
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public Boolean supplyGoodsImport(Page<TdDistributorGoods> goodsPage,HSSFRow row, HSSFCell cell, HSSFSheet sheet)
	{
		if(null != goodsPage && goodsPage.getContent().size() >0){
			for (int i = 0; i < goodsPage.getContent().size(); i++) {
				row = sheet.createRow((int)i+1);
				TdDistributorGoods disGoods = goodsPage.getContent().get(i);
				
				row.createCell((short) 0).setCellValue(disGoods.getGoodsTitle());
				row.createCell((short) 1).setCellValue(disGoods.getSubGoodsTitle());
				row.createCell((short) 2).setCellValue(disGoods.getCode());
				row.createCell((short) 3).setCellValue(StringUtils.scale(disGoods.getGoodsPrice()));
				
				TdProviderGoods providerGoods = tdProviderGoodsService.findByProviderIdAndGoodsId(disGoods.getProviderId(), disGoods.getGoodsId());
				if(null != providerGoods){
					row.createCell((short) 4).setCellValue(StringUtils.scale(providerGoods.getShopReturnRation()));
				}
				
				TdProductCategory category = tdProductCategoryService.findOne(disGoods.getCategoryId());
				if(null != category){
					row.createCell((short) 5).setCellValue(category.getTitle());
				}
				TdGoods goods = tdGoodsService.findOne(disGoods.getGoodsId());
				if(null != goods){
					row.createCell((short) 6).setCellValue(goods.getBrandTitle());
				}
				row.createCell((short) 7).setCellValue(disGoods.getUnit());
				row.createCell((short) 8).setCellValue(disGoods.getLeftNumber());
				row.createCell((short) 9).setCellValue(disGoods.getProviderTitle());
			}
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public Boolean disGoodsImport(Page<TdDistributorGoods> goodsPage,HSSFRow row, HSSFCell cell, HSSFSheet sheet)
	{
		if(null != goodsPage && goodsPage.getContent().size() >0){
			for (int i = 0; i < goodsPage.getContent().size(); i++) {
				row = sheet.createRow((int)i+1);
				TdDistributorGoods disGoods = goodsPage.getContent().get(i);
				
				row.createCell((short) 0).setCellValue(disGoods.getGoodsTitle());
				row.createCell((short) 1).setCellValue(disGoods.getSubGoodsTitle());
				row.createCell((short) 2).setCellValue(disGoods.getCode());
				row.createCell((short) 3).setCellValue(StringUtils.scale(disGoods.getGoodsPrice()));
				row.createCell((short) 4).setCellValue(StringUtils.scale(disGoods.getGoodsMarketPrice()));
				
				TdProductCategory category = tdProductCategoryService.findOne(disGoods.getCategoryId());
				if(null != category){
					row.createCell((short) 5).setCellValue(category.getTitle());
				}
				TdGoods goods = tdGoodsService.findOne(disGoods.getGoodsId());
				if(null != goods){
					row.createCell((short) 6).setCellValue(goods.getBrandTitle());
				}
				row.createCell((short) 7).setCellValue(disGoods.getUnit());
				row.createCell((short) 8).setCellValue(disGoods.getLeftNumber());
			}
		}
		return true;
	}
	
	public Boolean download(HSSFWorkbook wb,String name, String exportUrl, HttpServletResponse resp){
   	 try  
        {  
	          FileOutputStream fout = new FileOutputStream(exportUrl+name+".xls");  
//	          OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");	                       	     
	          wb.write(fout);  
	          fout.close();
        }catch (Exception e)  
        {  
            e.printStackTrace();  
        } 
   	 OutputStream os;
		 try {
				os = resp.getOutputStream();
				File file = new File(exportUrl +name+".xls");
                
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

