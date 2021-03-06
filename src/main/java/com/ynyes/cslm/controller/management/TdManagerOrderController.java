package com.ynyes.cslm.controller.management;

import static org.apache.commons.lang3.StringUtils.leftPad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdCash;
import com.ynyes.cslm.entity.TdDeliveryType;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdOrderGoods;
import com.ynyes.cslm.entity.TdPayRecord;
import com.ynyes.cslm.entity.TdPayType;
import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdSetting;
import com.ynyes.cslm.entity.TdSpecificat;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.entity.TdUserPoint;
import com.ynyes.cslm.service.TdArticleService;
import com.ynyes.cslm.service.TdCashService;
import com.ynyes.cslm.service.TdDeliveryTypeService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdManagerLogService;
import com.ynyes.cslm.service.TdOrderService;
import com.ynyes.cslm.service.TdPayRecordService;
import com.ynyes.cslm.service.TdPayTypeService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.service.TdProviderService;
import com.ynyes.cslm.service.TdSettingService;
import com.ynyes.cslm.service.TdSpecificatService;
import com.ynyes.cslm.service.TdUserPointService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.util.SiteMagConstant;
import com.ynyes.cslm.util.StringUtils;
/**
 * 后台首页控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value="/Verwalter/order")
public class TdManagerOrderController {
    
    @Autowired
    TdProductCategoryService tdProductCategoryService;
    
    @Autowired
    TdArticleService tdArticleService;
    
    @Autowired
    TdGoodsService tdGoodsService;
    
    @Autowired
    TdPayTypeService tdPayTypeService;
    
    @Autowired
    TdDeliveryTypeService tdDeliveryTypeService;
    
    @Autowired
    TdDistributorService TdDistributorService;
    
    @Autowired
    TdUserPointService tdUserPointService;
    
    @Autowired
    TdOrderService tdOrderService;
    
    @Autowired
    TdUserService tdUserService;
    
    @Autowired
    TdManagerLogService tdManagerLogService;
    
    @Autowired
    TdPayRecordService tdPayRecordService;
    
    @Autowired
    TdDistributorService tdDistributorService;
    
    @Autowired
    TdProviderService tdProviderService;
    
    @Autowired
    TdSettingService tdSettingService;
    
    @Autowired
    TdDistributorGoodsService tdDistributorGoodsService;
    
    @Autowired
    TdCashService tdCashService;
    
    @Autowired
    private TdSpecificatService tdSpecificatService;
    
    // 订单设置
    @RequestMapping(value="/setting/{type}/list")
    public String setting(@PathVariable String type, 
                          Integer page,
                          Integer size,
                          String keywords,
                          String __EVENTTARGET,
                          String __EVENTARGUMENT,
                          String __VIEWSTATE,
                          Long[] listId,
                          Integer[] listChkId,
                          Long[] listSortId,
                          ModelMap map,
                          HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
        if (null != __EVENTTARGET)
        {
            if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
            {
                btnDelete(type, listId, listChkId);
                
                if (type.equalsIgnoreCase("pay"))
                {
                    tdManagerLogService.addLog("delete", "删除支付方式", req);
                }
                else if (type.equalsIgnoreCase("delivery"))
                {
                    tdManagerLogService.addLog("delete", "删除配送方式", req);
                }
                else if (type.equalsIgnoreCase("diysite"))
                {
                    tdManagerLogService.addLog("delete", "删除自提点", req);
                }
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnSave"))
            {
                btnSave(type, listId, listSortId);
                
                if (type.equalsIgnoreCase("pay"))
                {
                    tdManagerLogService.addLog("edit", "修改支付方式", req);
                }
                else if (type.equalsIgnoreCase("delivery"))
                {
                    tdManagerLogService.addLog("edit", "修改配送方式", req);
                }
                else if (type.equalsIgnoreCase("diysite"))
                {
                    tdManagerLogService.addLog("edit", "修改自提点", req);
                }
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnPage"))
            {
                if (null != __EVENTARGUMENT)
                {
                    page = Integer.parseInt(__EVENTARGUMENT);
                } 
            }
        }
        
        if (null == page || page < 0)
        {
            page = 0;
        }
        
        if (null == size || size <= 0)
        {
            size = SiteMagConstant.pageSize;;
        }
        
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("keywords", keywords);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
                
        if (null != type)
        {
            if (type.equalsIgnoreCase("pay")) // 支付方式
            {
                if (null == keywords)
                {
                    map.addAttribute("pay_type_page", 
                            tdPayTypeService.findAllOrderBySortIdAsc(page, size));
                }
                else
                {
                    map.addAttribute("pay_type_page", 
                            tdPayTypeService.searchAllOrderBySortIdAsc(keywords, page, size));
                }
                
                return "/site_mag/pay_type_list";
            }
            else if (type.equalsIgnoreCase("delivery")) // 配送方式
            {
                if (null == keywords)
                {
                    map.addAttribute("delivery_type_page", 
                            tdDeliveryTypeService.findAllOrderBySortIdAsc(page, size));
                }
                else
                {
                    map.addAttribute("delivery_type_page", 
                            tdDeliveryTypeService.searchAllOrderBySortIdAsc(keywords, page, size));
                }
                
                return "/site_mag/delivery_type_list";
            }
            else if (type.equalsIgnoreCase("diysite")) // 配送方式
            {
                if (null == keywords)
                {
                    map.addAttribute("diy_site_page", 
                            TdDistributorService.findAllOrderBySortIdAsc(page, size));
                }
                else
                {
                    map.addAttribute("diy_site_page", 
                            TdDistributorService.searchAllOrderBySortIdAsc(keywords, page, size));
                }
                
                return "/site_mag/diy_site_list";
            }
        }
        
        return "/site_mag/pay_type_list";
    }
    
    // 订单设置编辑
    @RequestMapping(value="/setting/{type}/edit")
    public String edit(@PathVariable String type, 
                        Long id,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        if (null != type)
        {
            if (type.equalsIgnoreCase("pay")) // 支付方式
            {
                if (null != id)
                {
                    map.addAttribute("pay_type", tdPayTypeService.findOne(id));
                }
                
                return "/site_mag/pay_type_edit";
            }
            else if (type.equalsIgnoreCase("delivery")) // 配送方式
            {
                if (null != id)
                {
                    map.addAttribute("delivery_type", tdDeliveryTypeService.findOne(id));
                }
                
                return "/site_mag/delivery_type_edit";
            }
            else if (type.equalsIgnoreCase("diysite")) // 自提点
            {
                if (null != id)
                {
                	TdDistributor distributor = TdDistributorService.findById(id);
                    map.addAttribute("diy_site", distributor);
                    map.addAttribute("pay_tecord_list", tdPayRecordService.findByDistributorId(distributor.getId()));
                    
                }
                
                return "/site_mag/diy_site_edit";
            }
        }
        
        return "/site_mag/pay_type_edit";
    }
    
    // 订单设置编辑
    @RequestMapping(value = "/setting/diysite/check", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> validateForm(String param, Long id) {
        Map<String, String> res = new HashMap<String, String>();

        res.put("status", "n");

        if (null == param || param.isEmpty()) {
            res.put("info", "该字段不能为空");
            return res;
        }
        
//        TdUser tdUser = tdUserService.findByUsername(param);
        TdDistributor distributor = tdDistributorService.findbyUsername(param);
        
        if (null == id) // 新增
        {
            if (null != distributor) {
                res.put("info", "该登录名不能使用");
                return res;
            }
        } 
        else // 修改，查找除当前ID的所有
        {
            TdDistributor dSite = TdDistributorService.findOne(id);
            
            if (null == dSite)
            {
                if (null != distributor ) {
                    res.put("info", "该登录名不能使用");
                    return res;
                }
            }
            else
            {
//                if (null != tdUser && tdUser.getUsername() != dSite.getUsername() && tdUser.getRoleId()!=1L) {
//                    res.put("info", "该登录名不能使用");
//                    return res;
//                }
            }
        }

        res.put("status", "y");

        return res;
    }
    
    @RequestMapping(value="/edit")
    public String orderEdit(Long id,
                        Long statusId,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        map.addAttribute("statusId", statusId);
        if (null != id)
        {
            map.addAttribute("order", tdOrderService.findOne(id));
        }
        return "/site_mag/order_edit";
    }
    
    @RequestMapping(value="/save")
    public String orderEdit(TdOrder tdOrder,
                        Long statusId,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        map.addAttribute("statusId", statusId);
        
        tdOrderService.save(tdOrder);
        
        tdManagerLogService.addLog("edit", "修改订单", req);
        
        return "redirect:/Verwalter/order/list/"+statusId;
    }
    
    
    // 订单列表
    @SuppressWarnings("deprecation")
	@RequestMapping(value="/list/{statusId}/{type}")
    public String goodsListDialog(String keywords,
                                @PathVariable Long statusId,
                                @PathVariable Long type,
                                Integer page, 
                                Integer size,
                                String startTime,String endTime,
                                String __EVENTTARGET,
                                String __EVENTARGUMENT,
                                String __VIEWSTATE,
                                Long[] listId,
                                Integer[] listChkId,
                                ModelMap map,
                                String exportUrl,
                                Long payId,
                                HttpServletResponse resp,
                                HttpServletRequest req) throws ParseException{
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        if (null != __EVENTTARGET)
        {
            if (__EVENTTARGET.equalsIgnoreCase("btnCancel"))
            {
                btnCancel(listId, listChkId);
                tdManagerLogService.addLog("cancel", "取消订单", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnConfirm"))
            {
                btnConfirm(listId, listChkId);
                tdManagerLogService.addLog("confirm", "确认订单", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
            {
                btnDelete(listId, listChkId);
                tdManagerLogService.addLog("delete", "删除订单", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("export"))
            {
            	exportUrl = SiteMagConstant.backupPath;
                tdManagerLogService.addLog("export", "导出订单", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("exportAll"))
            {
            	exportUrl = SiteMagConstant.backupPath;
                tdManagerLogService.addLog("export", "导出订单", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnPage"))
            {
                if (null != __EVENTARGUMENT)
                {
                    page = Integer.parseInt(__EVENTARGUMENT);
                } 
            }
        }
        
        if (null == page || page < 0)
        {
            page = 0;
        }
        
        if (null == size || size <= 0)
        {
            size = SiteMagConstant.pageSize;;
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
        
        
        map.addAttribute("pay_list", tdPayTypeService.findByIsEnableTrue());
       
		map.addAttribute("order_page", tdOrderService.findAll(statusId,payId, start, end, keywords, page, size));
          
		if (null != exportUrl) {
        	  
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
	      
	      Page<TdOrder> tdOrderPage = null;
	      if (__EVENTTARGET.equalsIgnoreCase("export"))
	      {
	    	  tdOrderPage = tdOrderService.findAll(statusId,payId, start, end, keywords, page, size);
	      }
	      else if (__EVENTTARGET.equalsIgnoreCase("exportAll"))
	      {
	    	  tdOrderPage = tdOrderService.findAll(statusId,payId, start, end, keywords, page, Integer.MAX_VALUE);
	      }
			
			if (ImportData(tdOrderPage, row, cell, sheet)) {
				download(wb, exportUrl, resp);
			}                          	                          
		}
          
        // 参数注回
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("keywords", keywords);
        map.addAttribute("statusId", statusId);
        map.addAttribute("startTime", start);
		map.addAttribute("endTime", end);
		map.addAttribute("payId", payId);
        
        map.addAttribute("type", type);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        return "/site_mag/order_list";
    }
    /**
	 * @author lc
	 * @注释：将page中的订单数据存入excel表格中
	 */
    @SuppressWarnings("deprecation")
	public boolean ImportData(Page<TdOrder> tdOrderPage, HSSFRow row, HSSFCell cell, HSSFSheet sheet){
    	for (int i = 0; i < tdOrderPage.getContent().size(); i++)  
        {  
    		row = sheet.createRow((int)i+1);
			TdOrder order = tdOrderPage.getContent().get(i);
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
    /**
	 * @author lc
	 * @注释：文件写入和下载
	 */
    public Boolean download(HSSFWorkbook wb, String exportUrl, HttpServletResponse resp){
    	 try  
         {  
	          FileOutputStream fout = new FileOutputStream(exportUrl+"order.xls");  
	          OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");	                       	     
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
    /**
	 * @author lc
	 * @注释：计算总额和销售额
	 */
    public Double countprice(List<TdOrder> list){
    	Double price = new Double(0.00);       
    	for (int i = 0; i < list.size(); i++) {
    		price += list.get(i).getTotalPrice();
    	}
    	return price;
    }
    public Double countsales(List<TdOrder> list){
    	Double sales = new Double(0.00);
    	for(int i = 0; i < list.size(); i++){
    		if (list.get(i).getStatusId().equals(2L) || list.get(i).getStatusId().equals(7L)) {	
    			
			}
    		else{
    			sales += list.get(i).getTotalPrice();
    		}
    	}
    	return sales;
    }
    
    @RequestMapping(value="/setting/pay/save", method = RequestMethod.POST)
    public String save(TdPayType tdPayType,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        if (null == tdPayType.getId())
        {
            tdManagerLogService.addLog("add", "新增支付方式"+tdPayType.getTitle(), req);
        }
        else
        {
            tdManagerLogService.addLog("edit", "修改支付方式"+tdPayType.getTitle(), req);
        }
        tdPayTypeService.save(tdPayType);
        
        return "redirect:/Verwalter/order/setting/pay/list";
    }
    
    @RequestMapping(value="/setting/delivery/save", method = RequestMethod.POST)
    public String save(TdDeliveryType tdDeliveryType,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        if (null == tdDeliveryType.getId())
        {
            tdManagerLogService.addLog("add", "新增配送方式", req);
        }
        else
        {
            tdManagerLogService.addLog("edit", "修改配送方式", req);
        }
        
        tdDeliveryTypeService.save(tdDeliveryType);
        
        return "redirect:/Verwalter/order/setting/delivery/list";
    }
    
    @RequestMapping(value="/dialog/contact")
    public String addressDialog(ModelMap map,
            HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
        return "/site_mag/dialog_contact";
    }
    
    @RequestMapping(value="/dialog/delivery")
    public String sendDialog(String orderNumber, ModelMap map,
            HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
        if (null != orderNumber && !orderNumber.isEmpty())
        {
            map.addAttribute("order", tdOrderService.findByOrderNumber(orderNumber));
        }
        
        map.addAttribute("delivery_type_list", tdDeliveryTypeService.findByIsEnableTrue());
        
        return "/site_mag/dialog_delivery";
    }
    
    @RequestMapping(value="/dialog/print")
    public String printDialog(String orderNumber, ModelMap map,
            HttpServletRequest req){
//        String username = (String) req.getSession().getAttribute("manager");
//        if (null == username) {
//            return "redirect:/Verwalter/login";
//        }
        
        if (null != orderNumber && !orderNumber.isEmpty())
        {
            TdOrder order = tdOrderService.findByOrderNumber(orderNumber);
            map.addAttribute("order", order);
            map.addAttribute("now", new Date());
            map.addAttribute("manager", req.getSession().getAttribute("manager"));
            
            if (null != order)
            {
                map.addAttribute("user", tdUserService.findByUsernameAndIsEnabled(order.getUsername()));
            }
        }
        
        return "/site_mag/dialog_order_print";
    }
    
    // 平台给超市充值
    @RequestMapping(value="/diy_site/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> diySiteEdit(Long diysiteId,
    		String type,
    		Double data,HttpServletRequest req)
    {
    	Map<String,Object> res = new HashMap<>();
    	res.put("code", 1);
    	
    	String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            res.put("message", "请重新登录");
            return res;
        }
    	
    	if(null == diysiteId)
    	{
    		res.put("message", "参数错误！");
    		return res;
    	}
    	
    	TdDistributor distributor = TdDistributorService.findOne(diysiteId);
    	if("add".equals(type)){
	    	if(null != distributor.getVirtualMoney())
	    	{
	    		distributor.setVirtualMoney(distributor.getVirtualMoney()+data);
	    	}else{
	    		distributor.setVirtualMoney(data);
	    	}
    	}else if("del".equals(type)){
    		if(null == distributor.getVirtualMoney() || distributor.getVirtualMoney() < data){
    			res.put("msg","账号余额不足");
    			return res;
    		}
    		distributor.setVirtualMoney(distributor.getVirtualMoney()-data);
    	}
    	TdDistributorService.save(distributor);
    	
    	TdPayRecord record = new TdPayRecord();
    	
    	Date current = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String curStr = sdf.format(current);
        Random random = new Random();
        String number="";
        if("add".equals(type)){
	        number = "CZ" + curStr+leftPad(Integer.toString(random.nextInt(999)), 3, "0");
	        record.setCont("平台充值");
        }else if("del".equals(type)){
        	number = "K" + curStr + leftPad(Integer.toString(random.nextInt(999)), 3, "0");
        	record.setCont("平台扣款");
        }
        record.setCreateTime(new Date());
        record.setDistributorId(distributor.getId());
        record.setDistributorTitle(distributor.getTitle());
        record.setStatusCode(1);
        record.setProvice(data);
        record.setOrderNumber(number);
        tdPayRecordService.save(record);
        
        TdSetting setting = tdSettingService.findTopBy();
        if("add".equals(type)){
			if( null != setting.getVirtualMoney())
		    {
		    	setting.setVirtualMoney(setting.getVirtualMoney()-data);
		    }else{
		    	setting.setVirtualMoney(0-data);
		    }
        }else if("del".equals(type)){
        	if( null != setting.getVirtualMoney())
		    {
		    	setting.setVirtualMoney(setting.getVirtualMoney()+data);
		    }else{
		    	setting.setVirtualMoney(data);
		    }
        }
        tdSettingService.save(setting); // 更新平台虚拟余额
        
     // 记录平台支出
        record = new TdPayRecord();
        if("add".equals(type)){
        	record.setCont("手动给"+distributor.getTitle()+"充值");
        }else if("del".equals(type)){
        	record.setCont("手动给"+distributor.getTitle()+"扣款");
        }
        record.setCreateTime(new Date());
        record.setDistributorTitle(distributor.getTitle());
        record.setOrderNumber(number);
        record.setStatusCode(1);
        record.setType(1L); // 类型 区分平台记录
        
        record.setProvice(data); // 订单总额
        record.setPostPrice(0.0); // 邮费
        record.setAliPrice(0.0);	// 第三方费
        record.setServicePrice(0.0);	// 平台费
        record.setTotalGoodsPrice(data); // 商品总价
        // 
        record.setRealPrice(data);
        
        tdPayRecordService.save(record);
        
        // 充值记录
        if("add".equals(type)){
	        TdCash cash = new TdCash();
	    	cash.setCashNumber("平台代充："+number);
	    	cash.setShopTitle(distributor.getTitle());
	    	cash.setUsername(distributor.getUsername());
	    	cash.setCreateTime(new Date());
	    	cash.setPrice(data); // 金额
	    	cash.setShopType(1L); // 类型-超市
	    	cash.setType(1L); // 类型-充值
	    	cash.setStatus(2L); // 状态 完成
	    	
	    	tdCashService.save(cash);
	        
	        tdManagerLogService.addLog("add", "手动给"+distributor.getTitle()+"充值"+data, req);
        }
        
        res.put("code", 0);
        res.put("message", "充值成功！");
    	return res;
    }
    
    @RequestMapping(value="/param/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> paramEdit(String orderNumber,
                        String type,
                        String data,
                        String name,
                        String address,
                        String postal,
                        String mobile,
                        String expressNumber,
                        Long deliverTypeId,
                        ModelMap map,
                        HttpServletRequest req){
        
        Map<String, Object> res = new HashMap<String, Object>();
        
        res.put("code", 1);
        
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            res.put("message", "请重新登录");
            return res;
        }
        
        if (null != orderNumber && !orderNumber.isEmpty() && null != type && !type.isEmpty())
        {
            TdOrder order = tdOrderService.findByOrderNumber(orderNumber);
            
            // 修改备注
            if (type.equalsIgnoreCase("editMark"))
            {
                order.setRemarkInfo(data);
            }
            // 修改商品总金额
            else if (type.equalsIgnoreCase("editTotalGoodsPrice"))
            {
                double goodsPrice = Double.parseDouble(data);
                order.setTotalGoodsPrice(goodsPrice);
                order.setTotalPrice(goodsPrice + order.getPayTypeFee() + order.getDeliverTypeFee());
            }
            // 修改配送费用
            else if (type.equalsIgnoreCase("editDeliveryPrice"))
            {
                double deliveryPrice = Double.parseDouble(data);
                order.setDeliverTypeFee(deliveryPrice);
                order.setTotalPrice(deliveryPrice + order.getPayTypeFee() + order.getTotalGoodsPrice());
            }
            // 修改支付手续费
            else if (type.equalsIgnoreCase("editPayPrice"))
            {
                double payPrice = Double.parseDouble(data);
                order.setPayTypeFee(payPrice);
                order.setTotalPrice(payPrice + order.getTotalGoodsPrice() + order.getDeliverTypeFee());
            }
            // 修改联系方式
            else if (type.equalsIgnoreCase("editContact"))
            {
                order.setShippingName(name);
                order.setShippingAddress(address);
                order.setShippingPhone(mobile);
                order.setPostalCode(postal);
            }
            // 确认订单
            else if (type.equalsIgnoreCase("orderConfirm"))
            {
                if (order.getStatusId().equals(1L))
                {
                    order.setStatusId(2L);
                    order.setCheckTime(new Date());
                }
            }
            // 确认付款
            else if (type.equalsIgnoreCase("orderPay"))
            {
                if (order.getStatusId().equals(2L))
                {
                	tdOrderService.addVir(order);
                	order.setStatusId(3L);
                    order.setPayTime(new Date());
                }
            }
            // 确认发货
            else if (type.equalsIgnoreCase("orderPayLeft"))
            {
                if (order.getStatusId().equals(3L))
                {
                    order.setStatusId(4L);
                }
            }
            // 确认已服务
            else if (type.equalsIgnoreCase("orderService"))
            {
                if (order.getStatusId().equals(4L))
                {
                    if(order.getTypeId()==1)
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
//    							tdProviderGoodsService.findByProviderIdAndGoodsId(, goodsId)
    							distributorGoods = new TdDistributorGoods();
    							
    							distributorGoods.setDistributorTitle(distributor.getTitle());
    							distributorGoods.setGoodsId(goods.getId());
    							distributorGoods.setGoodsTitle(goods.getTitle());
//    							distributorGoods.setGoodsPrice();
    							distributorGoods.setBrandId(goods.getBrandId());
    							distributorGoods.setBrandTitle(goods.getBrandTitle());
    							distributorGoods.setCategoryId(goods.getCategoryId());
    							distributorGoods.setCategoryIdTree(goods.getCategoryIdTree());
    							distributorGoods.setCode(goods.getCode());
    							distributorGoods.setCoverImageUri(goods.getCoverImageUri());
    							distributorGoods.setGoodsMarketPrice(tdOrderGoods.getPrice());
    							distributorGoods.setIsDistribution(false);
//    						distributorGoods.setGoodsParamList(goods.getParamList());
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
    					
                    }else{
                    	order.setStatusId(5L);
                    	order.setReceiveTime(new Date());
                    }
                }
            }
            // 货到付款确认付款
            else if (type.equalsIgnoreCase("orderPayOffline"))
            {
                if (order.getStatusId().equals(2L)
                        && !order.getIsOnlinePay())
                {
                    order.setStatusId(5L);
                    order.setPayTime(new Date());
                }
            }
            // 确认发货
            else if (type.equalsIgnoreCase("orderDelivery"))
            {
                if (order.getStatusId().equals(3L))
                {
                    order.setDeliverTypeId(deliverTypeId);
                    order.setExpressNumber(expressNumber);
                    order.setStatusId(4L);
                    order.setSendTime(new Date());
                    
                }
            }
            // 确认收货
            else if (type.equalsIgnoreCase("orderReceive"))
            {
                if (order.getStatusId().equals(4L))
                {
                    order.setStatusId(5L);
                    order.setReceiveTime(new Date());
                }
            }
            // 确认完成
            else if (type.equalsIgnoreCase("orderFinish"))
            {
                if (order.getStatusId().equals(5L))
                {
                    order.setStatusId(6L);
                    order.setFinishTime(new Date());
                    
                    if(order.getTypeId() ==0 || order.getTypeId() ==2){
                    	tdOrderService.addUserPoint(order,order.getUsername());
                    	tdUserService.addTotalSpend(order.getUsername(), order.getTotalGoodsPrice());
                    }else{ // 超市进货单
                    	TdDistributor distributor = tdDistributorService.findbyUsername(order.getUsername());
    					List<TdOrderGoods> goodsList = order.getOrderGoodsList();
    					for (TdOrderGoods tdOrderGoods : goodsList) {
    						
    						
    						TdDistributorGoods distributorGoods = tdDistributorGoodsService.findByDistributorIdAndGoodsId(distributor.getId(), tdOrderGoods.getGoodsId());
    						
    						if(null == distributorGoods && !distributor.getGoodsList().contains(distributorGoods))
    						{
    							TdGoods goods = tdGoodsService.findOne(tdOrderGoods.getGoodsId());
//    							tdProviderGoodsService.findByProviderIdAndGoodsId(, goodsId)
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
            }
            // 确认取消
            else if (type.equalsIgnoreCase("orderCancel"))
            {
                if (order.getStatusId().equals(1L) ||
                        order.getStatusId().equals(2L))
                {
                    order.setStatusId(7L);
                    order.setCancelTime(new Date());
                }
            }
            
            tdOrderService.save(order);
            tdManagerLogService.addLog("edit", "修改订单"+orderNumber, req);
            
            res.put("code", 0);
            res.put("message", "修改成功!");
            return res;
        }
        
        res.put("message", "参数错误!");
        return res;
    }
    
    @RequestMapping(value = "order/sumPrice" , method = RequestMethod.GET)
    public String sumPrice(String date,String date1,HttpServletRequest request){
    	
    	
    	return "/";
    }
    
    @RequestMapping(value="/setting/diysite/save", method = RequestMethod.POST)
    public String save(TdDistributor tdDistributor,
                        String[] hid_photo_name_show360,
                        ModelMap map,Boolean isSupply,Boolean isStock,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        String uris = parsePicUris(hid_photo_name_show360);

        tdDistributor.setShowPictures(uris);
        if(null == tdDistributor.getVirtualMoney() || "".equals(tdDistributor.getVirtualMoney())){
        	tdDistributor.setVirtualMoney(new Double(0));
        }
        
        // 设置初始支付密码
        if(null == tdDistributor.getPayPassword() || "".equals(tdDistributor.getPayPassword().trim()))
        {
        	tdDistributor.setPayPassword(tdDistributor.getPassword());
        }
        
        // 修改代理、进货权
        if(null != isSupply && isSupply)
        {
        	tdDistributor.setIsSupply(true);
        }else{
        	tdDistributor.setIsSupply(false);
        }
        
        if(null != isStock && isStock)
        {
        	tdDistributor.setIsStock(true);
        }else{
        	tdDistributor.setIsStock(false);
        }
        
        if (null == tdDistributor.getId())
        {
            tdManagerLogService.addLog("add", "新增加盟超市"+tdDistributor.getTitle(), req);
        }
        else
        {
            tdManagerLogService.addLog("edit", "修改加盟超市"+tdDistributor.getTitle(), req);
        }
        
        TdDistributorService.save(tdDistributor);
        
        return "redirect:/Verwalter/order/setting/diysite/list";
    }
    
    @ModelAttribute
    public void getModel(@RequestParam(value = "payTypeId", required = false) Long payTypeId,
                    @RequestParam(value = "deliveryTypeId", required = false) Long deliveryTypeId,
                    @RequestParam(value = "diySiteId", required = false) Long diySiteId,
                        Model model) {
        if (null != payTypeId) {
            model.addAttribute("tdPayType", tdPayTypeService.findOne(payTypeId));
        }
        
        if (null != deliveryTypeId) {
            model.addAttribute("tdDeliveryType", tdDeliveryTypeService.findOne(deliveryTypeId));
        }
        
        if (null != diySiteId) {
            model.addAttribute("tdDistributor", TdDistributorService.findById(diySiteId));
        }
    }
    
    private void btnSave(String type, Long[] ids, Long[] sortIds)
    {
        if (null == type || type.isEmpty())
        {
            return;
        }
        
        if (null == ids || null == sortIds
                || ids.length < 1 || sortIds.length < 1)
        {
            return;
        }
        
        for (int i = 0; i < ids.length; i++)
        {
            Long id = ids[i];
            
            if (type.equalsIgnoreCase("pay"))
            {
                TdPayType e = tdPayTypeService.findOne(id);
                
                if (null != e)
                {
                    if (sortIds.length > i)
                    {
                        e.setSortId(sortIds[i]);
                        tdPayTypeService.save(e);
                    }
                }
            }
            else if (type.equalsIgnoreCase("delivery"))
            {
                TdDeliveryType e = tdDeliveryTypeService.findOne(id);
                
                if (null != e)
                {
                    if (sortIds.length > i)
                    {
                        e.setSortId(sortIds[i]);
                        tdDeliveryTypeService.save(e);
                    }
                }
            }
            else if (type.equalsIgnoreCase("diysite"))
            {
                TdDistributor e = TdDistributorService.findOne(id);
                
                if (null != e)
                {
                    if (sortIds.length > i)
                    {
                        e.setSortId(sortIds[i]);
                        TdDistributorService.save(e);
                    }
                }
            }
        }
    }
    
    private void btnDelete(String type, Long[] ids, Integer[] chkIds)
    {
        if (null == type || type.isEmpty())
        {
            return;
        }
        
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
                
                if (type.equalsIgnoreCase("pay"))
                {
                    tdPayTypeService.delete(id);
                }
                else if (type.equalsIgnoreCase("delivery"))
                {
                    tdDeliveryTypeService.delete(id);
                }
                else if (type.equalsIgnoreCase("diysite"))
                {
                    TdDistributorService.delete(id);
                }
            }
        }
    }
    
    private void btnConfirm(Long[] ids, Integer[] chkIds)
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
                
                TdOrder tdOrder= tdOrderService.findOne(id);
                
                // 只有待确认(1L)订单能进行确认，确认后状态为待发货(3L)
                if (tdOrder.getStatusId().equals(1L))
                {
                    tdOrder.setStatusId(3L);
                    tdOrder.setCheckTime(new Date()); // 确认时间
                    tdOrderService.save(tdOrder);
                }
            }
        }
    }
    
    private void btnCancel(Long[] ids, Integer[] chkIds)
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
                
                TdOrder tdOrder= tdOrderService.findOne(id);
                
                // 只有待确认(1L)、待付款(2L)订单能进行删除，确认后状态为已取消(7L)
                if (tdOrder.getStatusId().equals(1L) ||
                        tdOrder.getStatusId().equals(2L))
                {
                    tdOrder.setStatusId(7L);
                    tdOrder.setCancelTime(new Date()); // 取消时间
                    tdOrderService.save(tdOrder);
                }
            }
        }
    }
    
    private void btnDelete(Long[] ids, Integer[] chkIds)
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
                
                TdOrder tdOrder= tdOrderService.findOne(id);
                
                // 只有已取消(7L)订单能进行删除
                if (tdOrder.getStatusId().equals(7L))
                {
                    tdOrderService.delete(tdOrder);
                }
            }
        }
    }
    
    /**
     * 图片地址字符串整理，多张图片用,隔开
     * 
     * @param params
     * @return
     */
    private String parsePicUris(String[] uris)
    {
        if (null == uris || 0 == uris.length)
        {
            return null;
        }
        
        String res = "";
        
        for (String item : uris)
        {
            String uri = item.substring(item.indexOf("|")+1, item.indexOf("|", 2));
            
            if (null != uri)
            {
                res += uri;
                res += ",";
            }
        }
        
        return res;
    }
    
    
//	// 添加会员积分
//	public void addUserPoint(TdOrder order,String username){
//		
//		TdUser user = tdUserService.findByUsername(username);
//		
//		 // 添加积分使用记录
//		 if (null != user) {
//			 if (null == user.getTotalPoints())
//			 {
//				 user.setTotalPoints(0L);
//				 user = tdUserService.save(user);
//			 }
//			 if(null != order.getPoints() && order.getPoints()!= 0L){
//				 
//				 TdUserPoint userPoint = new TdUserPoint();
//				 userPoint.setDetail("购买商品获得积分");
//				 userPoint.setOrderNumber(order.getOrderNumber());
//				 userPoint.setPoint(order.getPoints());
//				 userPoint.setPointTime(new Date());
//				 userPoint.setUsername(username);
//				 userPoint.setTotalPoint(user.getTotalPoints() + order.getPoints());
//				 tdUserPointService.save(userPoint);
//				 
//				 user.setTotalPoints(user.getTotalPoints() + order.getPoints());
//				 tdUserService.save(user);
//			 }
//		 }
//	}
    
//    // 商家销售单记录
//    public void addVir(TdOrder tdOrder)
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
//        servicePrice +=tdOrder.getTrainService();
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
