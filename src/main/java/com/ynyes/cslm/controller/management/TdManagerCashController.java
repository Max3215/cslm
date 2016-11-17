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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ynyes.cslm.entity.TdCash;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdManager;
import com.ynyes.cslm.entity.TdManagerRole;
import com.ynyes.cslm.entity.TdPayRecord;
import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdServiceItem;
import com.ynyes.cslm.entity.TdSetting;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.service.TdCashService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdManagerLogService;
import com.ynyes.cslm.service.TdManagerRoleService;
import com.ynyes.cslm.service.TdManagerService;
import com.ynyes.cslm.service.TdPayRecordService;
import com.ynyes.cslm.service.TdProviderService;
import com.ynyes.cslm.service.TdSettingService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.util.SiteMagConstant;
import com.ynyes.cslm.util.StringUtils;


@Controller
@RequestMapping(value="/Verwalter/setting")
public class TdManagerCashController {
	
	@Autowired
    TdSettingService tdSettingService;
    
    @Autowired
    TdManagerLogService tdManagerLogService;
    
    @Autowired
    TdManagerService tdManagerService;
    
    @Autowired
    TdManagerRoleService tdManagerRoleService;
    
    @Autowired
    TdPayRecordService tdPayRecordService;
	
    @Autowired
    TdCashService tdCashService;
    
    @Autowired
    TdDistributorService tdDistributorService;
    
    @Autowired
    TdProviderService tdProviderService;
    
    @Autowired
    TdUserService tdUserService;
	
	@RequestMapping(value="/cash/list")
    public String cashList(Integer page,Integer size,
				    		ModelMap map,String __EVENTTARGET,
					        String __EVENTARGUMENT,
					        String __VIEWSTATE,
					        Long[] listId,
					        Integer[] listChkId,
					        String exportUrl,
					        String start,String end,
					        Long type,
					        Long shopType,
                            Long status,
					        HttpServletRequest req,
					        HttpServletResponse resp) throws ParseException
    {
    	
    	String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
      //管理员角色
        TdManager tdManager = tdManagerService.findByUsernameAndIsEnableTrue(username);
        TdManagerRole tdManagerRole = null;
        
        if (null != tdManager.getRoleId())
        {
            tdManagerRole = tdManagerRoleService.findOne(tdManager.getRoleId());
        }
        
        if (null != tdManagerRole) {
			map.addAttribute("tdManagerRole", tdManagerRole);
		}
        
        if (null != __EVENTTARGET)
        {
            if (__EVENTTARGET.equalsIgnoreCase("btnCancel"))
            {
//                btnCancel(listId, listChkId);
                tdManagerLogService.addLog("cancel", "取消订单", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnVerify"))
            {
            	btnVerify(listId, listChkId);
                tdManagerLogService.addLog("confirm", "确认操作", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
            {
            	cashDelete(listId, listChkId);
                tdManagerLogService.addLog("delete", "删除充值提现记录", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("export"))
            {
            	exportUrl = SiteMagConstant.backupPath;
                tdManagerLogService.addLog("export", "导出充值提现记录", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("exportAll"))
            {
            	exportUrl = SiteMagConstant.backupPath;
                tdManagerLogService.addLog("export", "导出充值提现记录", req);
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Date startTime =null; // 起始时间
        Date endTime = null; // 截止时间
        
        if(null != start && !"".equals(start.trim()))
        {
			startTime = sdf.parse(start);
        }
        
        if(null != end && !"".equals(end.trim()))
        {
			endTime = sdf.parse(end);
        }
        
        map.addAttribute("cash_page", tdCashService.findAll(shopType, type,status, startTime, endTime, page, size));
        
        if (null != exportUrl) {
        	
        	
        	Page<TdCash> cash_page =  null;
        	if (__EVENTTARGET.equalsIgnoreCase("export"))
            {
        		cash_page =  tdCashService.findAll(shopType, type,status, startTime, endTime, page, size);
            }
        	else if (__EVENTTARGET.equalsIgnoreCase("exportAll"))
            {
            	cash_page =  tdCashService.findAll(shopType, type,status, startTime, endTime, page, Integer.MAX_VALUE);
            }
			
        	String name = "cash";
            HSSFWorkbook wb = new HSSFWorkbook();
    		// 在webbook中添加一个sheet,对应Excel文件中的sheet 
    		HSSFSheet sheet = wb.createSheet(name); 
    		// 设置每个单元格宽度根据字多少自适应
    		sheet.autoSizeColumn(1);
    		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
            HSSFRow row = sheet.createRow((int) 0);
            // 创建单元格，并设置值表头 设置表头居中 
            HSSFCellStyle style = wb.createCellStyle();  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  // 居中
            
            HSSFCell cell = row.createCell((short) 0);  
            cell.setCellValue("单号");  
            cell.setCellStyle(style); 
            
            cell = row.createCell((short) 1);  
            cell.setCellValue("名称");  
            cell.setCellStyle(style); 
            
            cell = row.createCell((short) 2);  
            cell.setCellValue("账号");  
            cell.setCellStyle(style); 
            
            cell = row.createCell((short) 3);  
            cell.setCellValue("提交时间");  
            cell.setCellStyle(style);
            
            cell = row.createCell((short) 4);  
            cell.setCellValue("金额");  
            cell.setCellStyle(style);
            
            cell = row.createCell((short) 5);  
            cell.setCellValue("卡号");  
            cell.setCellStyle(style); 
            
            cell = row.createCell((short) 6);  
            cell.setCellValue("开户行");  
            cell.setCellStyle(style); 
            
            cell = row.createCell((short) 7);  
            cell.setCellValue("开户姓名");  
            cell.setCellStyle(style); 
            
            cell = row.createCell((short) 8);  
            cell.setCellValue("会员类型");  
            cell.setCellStyle(style); 
            
            cell = row.createCell((short) 9);  
            cell.setCellValue("类型");  
            cell.setCellStyle(style);
            
            cell = row.createCell((short) 10);  
            cell.setCellValue("状态");  
            cell.setCellStyle(style); 
        	
			if (cashImportData(cash_page, row, cell, sheet)) {
				download(wb, exportUrl,name, resp);
			}                          	                          
		}
        
        
        
        map.addAttribute("shopType", shopType);
        map.addAttribute("type", type);
        map.addAttribute("startime", startTime);
        map.addAttribute("endTime", endTime);
        map.addAttribute("status", status);
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
    	
    	return "/site_mag/cash_list";
    }
	
	
	@RequestMapping("/cash/edit")
	public String cashEdit(Long id,
			String __VIEWSTATE,
			HttpServletRequest req,ModelMap map)
	{
		String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        if(null != id){
        	map.addAttribute("cash", tdCashService.findOne(id));
        }
		
		return "/site_mag/cash_edit";
	}

	@RequestMapping(value = "/cash/save",method=RequestMethod.POST)
	public String save(TdCash tdCash,HttpServletRequest req,ModelMap map)
	{
		String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        String type = null;
        
        if (null == tdCash.getId())
        {
            type = "add";
        }
        else
        {
            type = "edit";
        }
        
        if(null != tdCash.getStatus() && tdCash.getType()==1){
        	if(tdCash.getStatus() ==2){
        		tdCashService.afterCash(tdCash);
        	}
        }else{
        	tdCashService.editDrawCash(tdCash);
        }
        
        tdCashService.save(tdCash);
        
        tdManagerLogService.addLog(type, "修改充值提现单号："+tdCash.getCashNumber(), req);
		
		return "redirect:/Verwalter/setting/cash/list";
	}

	
	@ModelAttribute
    public void getModel(@RequestParam(value = "cashId", required = false) Long cashId,
                            ModelMap map) {
        if (null != cashId) {
            map.addAttribute("tdCash", tdCashService.findOne(cashId));
        }
    } 
	
	private void cashDelete(Long[] ids, Integer[] chkIds)
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
                
                TdCash cash = tdCashService.findOne(id);
                if(cash.getStatus() == 2 ){
                	continue;
                }
                tdCashService.delete(id);
            }
        }
    }
	
	private void btnVerify( Long[] ids, Integer[] chkIds)
    {
        if (null == ids || null == chkIds
                || ids.length < 1 || chkIds.length < 1 )
        {
            return;
        }
        
        for (int chkId : chkIds)
        {
            if (chkId >=0 && ids.length > chkId)
            {
                Long id = ids[chkId];
                TdCash cash = tdCashService.findOne(id);
                if(null != cash)
                {
                	cash.setStatus(2L);
                	tdCashService.save(cash);
                	
                	if(null != cash.getStatus() && cash.getType()==1){
                    	if(cash.getStatus() ==2){
                    		tdCashService.afterCash(cash);
                    	}
                    }else{
                    	tdCashService.editDrawCash(cash);
                    }
//                	afterCash(cash);
                }
            }
        }
    }
    
	
	@SuppressWarnings("deprecation")
	public Boolean cashImportData(Page<TdCash> casgPage,HSSFRow row, HSSFCell cell, HSSFSheet sheet)
	{
		for (int i = 0; i < casgPage.getContent().size(); i++) {
			row = sheet.createRow((int)i+1);
			TdCash cash = casgPage.getContent().get(i);
			// 获取用户信息
			row.createCell((short) 0).setCellValue(cash.getCashNumber());
			row.createCell((short) 1).setCellValue(cash.getShopTitle());
			row.createCell((short) 2).setCellValue(cash.getUsername());
			row.createCell((short) 3).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(cash.getCreateTime()));
			row.createCell((short) 4).setCellValue(StringUtils.scale(cash.getPrice()));
			row.createCell((short) 5).setCellValue(cash.getCard());
			row.createCell((short) 6).setCellValue(cash.getBank());
			row.createCell((short) 7).setCellValue(cash.getName());
			if(cash.getShopType()==1){
				row.createCell((short) 8).setCellValue("超市");
			}else if (cash.getShopType()==2){
				row.createCell((short) 8).setCellValue("批发商");
			}else if (cash.getShopType()==3){
				row.createCell((short) 8).setCellValue("分销商");
			}else if (cash.getShopType()==4){
				row.createCell((short) 8).setCellValue("会员");
			}
			if(cash.getType() ==1)
			{
				row.createCell((short) 9).setCellValue("充值");
			}else if(cash.getType() ==2)
			{
				row.createCell((short) 9).setCellValue("提现");
			}
			if(cash.getStatus() ==1)
			{
				row.createCell((short) 10).setCellValue("新提交");
			}else if(cash.getStatus() ==2)
			{
				row.createCell((short) 10).setCellValue("已完成");
			}else{
				row.createCell((short) 10).setCellValue("未通过");
			}
		}
		return true;
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
