package com.ynyes.cslm.controller.management;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ynyes.cslm.entity.TdDemand;
import com.ynyes.cslm.entity.TdPayRecord;
import com.ynyes.cslm.entity.TdServiceItem;
import com.ynyes.cslm.entity.TdSetting;
import com.ynyes.cslm.entity.TdUserSuggestion;
import com.ynyes.cslm.service.TdCashService;
import com.ynyes.cslm.service.TdDemandService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdManagerLogService;
import com.ynyes.cslm.service.TdManagerRoleService;
import com.ynyes.cslm.service.TdManagerService;
import com.ynyes.cslm.service.TdPayRecordService;
import com.ynyes.cslm.service.TdProviderService;
import com.ynyes.cslm.service.TdServiceItemService;
import com.ynyes.cslm.service.TdSettingService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.service.TdUserSuggestionService;
import com.ynyes.cslm.util.SiteMagConstant;
import com.ynyes.cslm.util.StringUtils;

/**
 * 后台广告管理控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value="/Verwalter/setting")
public class TdManagerSettingController {
    
    @Autowired
    TdSettingService tdSettingService;
    
    @Autowired
    TdManagerLogService tdManagerLogService;
    
    @Autowired
    TdServiceItemService tdServiceItemService;
    
    @Autowired
    TdUserSuggestionService tdUserSuggestionService;
    
    @Autowired
    TdDemandService tdDemandService;
    
    @Autowired
    TdPayRecordService tdPayRecordService;
    
    @Autowired
    TdManagerService tdManagerService;
    
    @Autowired
    TdManagerRoleService tdManagerRoleService;
    
    @Autowired
    TdCashService tdCashService;
    
    @Autowired
    TdDistributorService tdDistributorService;
    
    @Autowired
    TdProviderService tdProviderService;
    
    @Autowired
    TdUserService tdUserService;
    
    @RequestMapping
    public String setting(Long status, ModelMap map,
            HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("setting", tdSettingService.findTopBy());
        map.addAttribute("status", status);
        
        return "/site_mag/setting_edit";
    }
    
    @RequestMapping(value="/save")
    public String orderEdit(TdSetting tdSetting,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        if (null == tdSetting.getId())
        {
            tdManagerLogService.addLog("add", "用户修改系统设置", req);
        }
        else
        {
            tdManagerLogService.addLog("edit", "用户修改系统设置", req);
        }
        
        tdSettingService.save(tdSetting);
        
        return "redirect:/Verwalter/setting?status=1";
    }
    
    @RequestMapping(value="/service/list")
    public String service(String __EVENTTARGET,
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
                btnDelete(listId, listChkId);
                
                tdManagerLogService.addLog("edit", "删除服务", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnSave"))
            {
                btnSave(listId, listSortId);
                
                tdManagerLogService.addLog("edit", "修改服务", req);
            }
        }

        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
                
        map.addAttribute("service_item_list", tdServiceItemService.findAllOrderBySortIdAsc());
                
        return "/site_mag/service_item_list";
    }
    
    /**
     * 后台投诉查看页面跳转
     * @author Zhangji
     * 
     */
    @RequestMapping(value="/suggestion/list")
    public String suggestion( String __EVENTTARGET,
                        String __EVENTARGUMENT,
                        String __VIEWSTATE,
                        Integer page,
                        Integer size,
                        Long id,
                        String name,
                        String content,
                        String mail,
                        Long mobile,
                        Long statusId,
                        Long[] listId,
                        Integer[] listChkId,
                        Long[] listSortId,
                        ModelMap map,
                        HttpServletRequest req){
    	
    	if (null != __EVENTTARGET)
        {
            if (__EVENTTARGET.equalsIgnoreCase("btnPage"))
            {
                if (null != __EVENTARGUMENT)
                {
                    page = Integer.parseInt(__EVENTARGUMENT);
                } 
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
            {
                btnDeleteSuggesiton( listId, listChkId);
                tdManagerLogService.addLog("delete", "删除投诉", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnVerify"))
            {
                btnEditSuggesiton( listId, listChkId);
                tdManagerLogService.addLog("delete", "处理投诉", req);
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

        Page<TdUserSuggestion> suggestionPage = null;
       
        suggestionPage = tdUserSuggestionService.findAllOrderByTimeDesc(page, size);
                   
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        map.addAttribute("suggestion_page", suggestionPage);
        
        return "/site_mag/suggestion_list";
    }
    

	/**
     * 后台“车友还想团购”查看页面跳转
     * @author Zhangji
     * 
     */
    @RequestMapping(value="/demand/list")
    public String demand( String __EVENTTARGET,
                        String __EVENTARGUMENT,
                        String __VIEWSTATE,
                        Long statusId,
                        Integer page,
                        Integer size,
                        Long id,
                        String name,
                        String content,
                        String mail,
                        Long mobile,
                        Long[] listId,
                        Integer[] listChkId,
                        Long[] listSortId,
                        ModelMap map,
                        HttpServletRequest req){
    	
    	if (null != __EVENTTARGET)
        {
            if (__EVENTTARGET.equalsIgnoreCase("btnPage"))
            {
                if (null != __EVENTARGUMENT)
                {
                    page = Integer.parseInt(__EVENTARGUMENT);
                } 
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
            {
                btnDeleteDemand(listId, listChkId);
                tdManagerLogService.addLog("delete", "删除demand", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnVerify"))
            {
            	btnVerifyDemand(listId,listChkId);
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

        Page<TdDemand> tdDemandPage = null;
       
        tdDemandPage = tdDemandService.findAllOrderByTimeDesc(page, size);
                   
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        map.addAttribute("demand_page", tdDemandPage);
        
        return "/site_mag/demand_list";
    }
    
    @RequestMapping(value="/service/edit")
    public String edit(Long id,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("manager");
        
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        if (null != id)
        {
            map.addAttribute("service_item", tdServiceItemService.findOne(id));
        }
        
        return "/site_mag/service_item_edit";
    }
    
    @RequestMapping(value="/service/save", method = RequestMethod.POST)
    public String serviceItemEdit(TdServiceItem tdServiceItem,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req) {
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        tdServiceItemService.save(tdServiceItem);
        
        tdManagerLogService.addLog("edit", "修改商城服务", req);
        
        return "redirect:/Verwalter/setting/service/list";
    }
    
    
    
    @SuppressWarnings("deprecation")
	@RequestMapping(value="/payrecord/list")
    public String settingPayList(Integer page,
						        Integer size,
						        String __EVENTTARGET,
						        String __EVENTARGUMENT,
						        String __VIEWSTATE,
						        String startTime,String endTime,
						        String cont,
						        Long[] listId,
						        Integer[] listChkId,
						        String exportUrl,
						        ModelMap map,
						        HttpServletRequest req,
						        HttpServletResponse resp) throws ParseException
    {
    	String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        if (null != __EVENTTARGET)
        {
            if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
            {
                btnDeletePay(listId, listChkId);
                tdManagerLogService.addLog("delete", "删除交易记录", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnPage"))
            {
                if (null != __EVENTARGUMENT)
                {
                    page = Integer.parseInt(__EVENTARGUMENT);
                } 
            }
            else if (__EVENTTARGET.equalsIgnoreCase("export"))
            {
            	exportUrl = SiteMagConstant.backupPath;
                tdManagerLogService.addLog("export", "导出交易记录", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("exportAll"))
            {
            	exportUrl = SiteMagConstant.backupPath;
                tdManagerLogService.addLog("export", "导出交易记录", req);
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
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
        
		map.addAttribute("record_page", tdPayRecordService.findAll(1L, start, end,cont, page, size));
        
        if (null != exportUrl) {
        	Page<TdPayRecord> recordPage = null;
        	
        	if (__EVENTTARGET.equalsIgnoreCase("export"))
  	      	{
        		recordPage = tdPayRecordService.findByType(1L, page, size);
  	      	}
        	else if (__EVENTTARGET.equalsIgnoreCase("exportAll"))
  	      	{
        		recordPage = tdPayRecordService.findByType(1L, page, Integer.MAX_VALUE);
  	      	}
        	
        	String name= "record";
            // 第一步，创建一个webbook，对应一个Excel文件  
               HSSFWorkbook wb = new HSSFWorkbook();  
               // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
               HSSFSheet sheet = wb.createSheet(name);  
               // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
               HSSFRow row = sheet.createRow((int) 0);  
               // 第四步，创建单元格，并设置值表头 设置表头居中  
               HSSFCellStyle style = wb.createCellStyle();  
               style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
               
               HSSFCell cell = row.createCell((short) 0);  
               cell.setCellValue("单号");  
               cell.setCellStyle(style);  
               cell = row.createCell((short) 1);  
               cell.setCellValue("记录时间");  
               cell.setCellStyle(style);  
               cell = row.createCell((short) 2);  
               cell.setCellValue("物流费");  
               cell.setCellStyle(style);  
               cell = row.createCell((short) 3);  
               cell.setCellValue("服务器");  
               cell.setCellStyle(style);
               cell = row.createCell((short) 4);  
               cell.setCellValue("第三方使用费");  
               cell.setCellStyle(style);
               cell = row.createCell((short) 5);  
               cell.setCellValue("商品总额");  
               cell.setCellStyle(style);
               cell = row.createCell((short) 6);  
               cell.setCellValue("订单总金额");  
               cell.setCellStyle(style);
               cell = row.createCell((short) 7);
               cell.setCellValue("实际入账/支出");  
               cell.setCellStyle(style);
               cell = row.createCell((short) 8);
               cell.setCellValue("说明");  
               cell.setCellStyle(style);
        	
        	if (ImportData(recordPage, row, cell, sheet)) {
				download(wb, exportUrl,name, resp);
			}   
        }
		
        map.addAttribute("cont",cont);
        map.addAttribute("startTime", start);
		map.addAttribute("endTime", end);
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        
    	return "/site_mag/setting_record_list";
    }
    
    /**
     * 将平台交易记录数据存入Excel表格
     * @author Max
     * 
     */
    @SuppressWarnings("deprecation")
	public boolean ImportData(Page<TdPayRecord> recordPage, HSSFRow row, HSSFCell cell, HSSFSheet sheet){
    	
    	for (int i = 0; i < recordPage.getContent().size(); i++)  
        {  // 单号  时间   物流费  服务费  第三方费   商品总额   订单总额   实际   说明
            row = sheet.createRow((int) i + 1);  
            TdPayRecord payrecord = recordPage.getContent().get(i);  
            // 第四步，创建单元格，并设置值  
            row.createCell((short) 0).setCellValue(payrecord.getOrderNumber());  
            row.createCell((short) 1).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(payrecord.getCreateTime()));  
            row.createCell((short) 2).setCellValue(StringUtils.scale(payrecord.getPostPrice()));
            row.createCell((short) 3).setCellValue(StringUtils.scale(payrecord.getServicePrice()));
        	row.createCell((short) 4).setCellValue(StringUtils.scale(payrecord.getAliPrice()));
            row.createCell((short) 5).setCellValue(StringUtils.scale(payrecord.getTotalGoodsPrice()));
            row.createCell((short) 6).setCellValue(StringUtils.scale(payrecord.getProvice()));
            row.createCell((short) 7).setCellValue(StringUtils.scale(payrecord.getRealPrice()));
            row.createCell((short) 8).setCellValue(payrecord.getCont());
        } 
    	return true;
    }
    
    

    @ModelAttribute
    public void getModel(@RequestParam(value = "id", required = false) Long id,
                            @RequestParam(value = "serviceItemId", required = false) Long serviceItemId,
                            Model model) {
  
        if (null != id) {
        	model.addAttribute("tdSetting", tdSettingService.findOne(id));
        }
        
        if (null != serviceItemId) {
            TdServiceItem serviceItem = tdServiceItemService.findOne(serviceItemId);
            model.addAttribute("tdServiceItem", serviceItem);
        }
    } 
    
    
    
    private void btnSave(Long[] ids, Long[] sortIds)
    {
        if (null == ids || null == sortIds
                || ids.length < 1 || sortIds.length < 1)
        {
            return;
        }
        
        for (int i = 0; i < ids.length; i++)
        {
            Long id = ids[i];
            
            TdServiceItem e = tdServiceItemService.findOne(id);
            
            if (null != e)
            {
                if (sortIds.length > i)
                {
                    e.setSortId(sortIds[i]);
                    tdServiceItemService.save(e);
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
                
                tdServiceItemService.delete(id);
            }
        }
    }
    
    
    private void btnDeletePay(Long[] ids, Integer[] chkIds)
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
                
                tdPayRecordService.delete(id);
            }
        }
    }
    
    
    /**
     * 删除团购要求
     * @author Zhangji
     * 2015年7月30日12:47:56
     * @param ids
     * @param chkIds
     */
    private void btnDeleteDemand(Long[] ids, Integer[] chkIds)
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
                
                tdDemandService.delete(id);
            }
        }
    }
    /**
     * 审核团购要求
     * @author Zhangji
     * 2015年7月30日13:24:06
     * @param ids
     * @param chkIds
     */
    private void btnVerifyDemand(Long[] ids, Integer[] chkIds)
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
                
                TdDemand e = tdDemandService.findOne(id);
                if (null != e)
                {
                	e.setStatusId(1L);
                	 tdDemandService.save(e);
                }
               
            }
        }
    }
    
    /**
     * 删除投诉
     */
    private void btnDeleteSuggesiton(Long[] ids, Integer[] chkIds)
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
                
                tdUserSuggestionService.delete(id);
            }
        }
    }
    
    private void btnEditSuggesiton(Long[] ids, Integer[] chkIds) {
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
                
                TdUserSuggestion suggestion = tdUserSuggestionService.findOne(id);
                suggestion.setStatus(2);
                tdUserSuggestionService.save(suggestion);
            }
        }
		
	}
    
    public Boolean download(HSSFWorkbook wb, String exportUrl,String name, HttpServletResponse resp){
   	 try  
        {  
	          FileOutputStream fout = new FileOutputStream(exportUrl+name+".xls");  
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
				File file = new File(exportUrl +name+ ".xls");
                
            if (file.exists())
                {
                  try {
                        resp.reset();
                        resp.setHeader("Content-Disposition", "attachment; filename="
                                + name+".xls");
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
