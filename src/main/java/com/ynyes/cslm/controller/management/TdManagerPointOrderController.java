package com.ynyes.cslm.controller.management;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdPointOrder;
import com.ynyes.cslm.service.TdManagerLogService;
import com.ynyes.cslm.service.TdManagerService;
import com.ynyes.cslm.service.TdPointGoodsService;
import com.ynyes.cslm.service.TdPointOrderService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.util.FileDownUtils;
import com.ynyes.cslm.util.SiteMagConstant;
import com.ynyes.cslm.util.StringUtils;

/**
 * 后台兑换订单列表
 * @author Max
 *
 */
@Controller
@RequestMapping(value="/Verwalter/pointOrder")
public class TdManagerPointOrderController {

	@Autowired
	private TdPointGoodsService tdPointGoodsService;
	
	@Autowired
	private TdPointOrderService tdPointOrderService;
	
	@Autowired
	private TdUserService tdUserService;
	
	@Autowired
	private TdManagerLogService tdManagerLogService;
	
	@Autowired
	private TdManagerService tdManagerService;
	
	
	
	@RequestMapping(value="/list")
	public String orderList(String keywords,
								Integer page, 
					            Integer size,
					            Integer statusId,
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
					            HttpServletRequest req) throws Exception{
		
		String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
        if (null != __EVENTTARGET)
        {
            if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
            {
//                btnDelete(listId, listChkId);
                tdManagerLogService.addLog("delete", "删除订单", req);
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
		
		map.addAttribute("order_page", tdPointOrderService.findAll(null, keywords, start, end, statusId, page, size));
		
		// 参数注回
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("keywords", keywords);
        map.addAttribute("statusId", statusId);
        map.addAttribute("startTime", start);
		map.addAttribute("endTime", end);
		
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        if (null != exportUrl) {
      	  
        	// 第一步，创建一个webbook，对应一个Excel文件  
	      HSSFWorkbook wb = new HSSFWorkbook();  
	      // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	      HSSFSheet sheet = wb.createSheet("order");  
	      // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	      HSSFRow row = sheet.createRow((int) 0);  
	      // 第四步，创建单元格，并设置值表头 设置表头居中  
	      HSSFCellStyle style = wb.createCellStyle();  
	      style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	      
	      HSSFCell cell = row.createCell((short) 0);  
	      cell.setCellValue("单号");  
	      cell.setCellStyle(style);  
	      cell = row.createCell((short) 1);  
	      cell.setCellValue("会员账号");  
	      cell.setCellStyle(style);  
	      cell = row.createCell((short) 2);  
	      cell.setCellValue("兑换商品名称");  
	      cell.setCellStyle(style);  
	      cell = row.createCell((short) 3);  
	      cell.setCellValue("副标题");  
	      cell.setCellStyle(style);
	      cell = row.createCell((short) 4);  
	      cell.setCellValue("编码");  
	      cell.setCellStyle(style);
	      cell = row.createCell((short) 5);  
	      cell.setCellValue("收货人姓名");  
	      cell.setCellStyle(style);
	      cell = row.createCell((short) 6);  
	      cell.setCellValue("地址");  
	      cell.setCellStyle(style);
	      cell = row.createCell((short) 7);  
	      cell.setCellValue("电话");  
	      cell.setCellStyle(style);
	      cell = row.createCell((short) 8);  
	      cell.setCellValue("兑换积分");  
	      cell.setCellStyle(style);
	      cell = row.createCell((short) 9);  
	      cell.setCellValue("兑换时间");  
	      cell.setCellStyle(style);
	      cell = row.createCell((short) 10);  
	      cell.setCellValue("状态");  
	      cell.setCellStyle(style);
	      cell = row.createCell((short) 11);  
	      cell.setCellValue("备注");  
	      cell.setCellStyle(style);
	      
	      Page<TdPointOrder> orderPage = tdPointOrderService.findAll(null, keywords, start, end, statusId, page, Integer.MAX_VALUE);
			
			if (ImportData(orderPage, row, cell, sheet)) {
				FileDownUtils.download("order", wb, exportUrl, resp);
			}                          	                          
		}
		
		
		return "/site_mag/point_order_list";
	}
	
	@RequestMapping(value="/edit")
	public String edit(Long id,HttpServletRequest req, ModelMap map){
		String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        if (null != id)
        {
            map.addAttribute("order", tdPointOrderService.findOne(id));
        }
        return "/site_mag/point_order_edit";
	}
	
	@RequestMapping(value="/param/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> paramEdit(Long orderId,
						String type,
						String data,
						HttpServletRequest req,
						ModelMap map){
		
		Map<String, Object> res = new HashMap<String, Object>();
        
        res.put("code", 1);
        
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            res.put("message", "请重新登录");
            return res;
        }
		
        TdPointOrder order = tdPointOrderService.findOne(orderId);
        
        if(null != orderId && null != type && !type.isEmpty()){
        	
        	// 发货
        	if("orderSeed".equalsIgnoreCase(type))
        	{
        		order.setStatusId(2);
        	}
        	// 收货
        	else if("orderReceive".equalsIgnoreCase(type)){
        		order.setStatusId(3);
        	}
        	// 修改备注
        	else if("editMark".equalsIgnoreCase(type)){
        		order.setSiteRemarke(data);
        	}
        	// 取消
        	else if("orderCancel".equalsIgnoreCase(type)){
        		order.setStatusId(4);
        		tdPointOrderService.orderCancel(order);  // 取消订单，返回积分
        	}
        	
        	tdPointOrderService.save(order);
        	tdManagerLogService.addLog("edit", "修改订单"+order.getOrderNumber(), req);
        	
        	res.put("code", 0);
            res.put("message", "修改成功!");
            return res;
        }
        
        res.put("message", "参数错误!");
        return res;
	}
	
	
	@SuppressWarnings("deprecation")
	public boolean ImportData(Page<TdPointOrder> tdOrderPage, HSSFRow row, HSSFCell cell, HSSFSheet sheet){
    	for (int i = 0; i < tdOrderPage.getContent().size(); i++)  
        {  
            row = sheet.createRow((int) i + 1);  
            TdPointOrder tdOrder = tdOrderPage.getContent().get(i);  
            // 第四步，创建单元格，并设置值  
            row.createCell((short) 0).setCellValue(tdOrder.getOrderNumber());  
            row.createCell((short) 1).setCellValue(tdOrder.getUsername());  
            row.createCell((short) 2).setCellValue(tdOrder.getGoodsTitle());
            row.createCell((short) 3).setCellValue(tdOrder.getSubTitle());
            row.createCell((short) 4).setCellValue(tdOrder.getGoodsCode());
            row.createCell((short) 5).setCellValue(tdOrder.getShippingName());
            row.createCell((short) 6).setCellValue(tdOrder.getShippingAddress());
            row.createCell((short) 7).setCellValue(tdOrder.getShippingPhone());
            row.createCell((short) 8).setCellValue(tdOrder.getPoint());
            
            cell = row.createCell((short) 9);  
            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(tdOrder.getCreateTime()));                                
      
            if (tdOrder.getStatusId().equals(1)) {
            	row.createCell((short) 10).setCellValue("待发货");
			}else if (tdOrder.getStatusId().equals(2)) {
				row.createCell((short) 10).setCellValue("待收货");
			}else if (tdOrder.getStatusId().equals(3)) {
				row.createCell((short) 10).setCellValue("已完成");
			}else if (tdOrder.getStatusId().equals(4)) {
				row.createCell((short) 10).setCellValue("已取消");
			}
            row.createCell((short) 11).setCellValue(tdOrder.getUserRemarke());
        } 
    	return true;
    }
	
}
