package com.ynyes.cslm.controller.management;

import static org.apache.commons.lang3.StringUtils.leftPad;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdCash;
import com.ynyes.cslm.entity.TdDemand;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdPayRecord;
import com.ynyes.cslm.entity.TdSetting;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.entity.TdUserComment;
import com.ynyes.cslm.entity.TdUserConsult;
import com.ynyes.cslm.entity.TdUserLevel;
import com.ynyes.cslm.entity.TdUserPoint;
import com.ynyes.cslm.entity.TdUserReturn;
import com.ynyes.cslm.service.TdCashService;
import com.ynyes.cslm.service.TdDemandService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdManagerLogService;
import com.ynyes.cslm.service.TdPayRecordService;
import com.ynyes.cslm.service.TdProviderService;
import com.ynyes.cslm.service.TdSettingService;
import com.ynyes.cslm.service.TdUserCashRewardService;
import com.ynyes.cslm.service.TdUserCollectService;
import com.ynyes.cslm.service.TdUserCommentService;
import com.ynyes.cslm.service.TdUserConsultService;
import com.ynyes.cslm.service.TdUserLevelService;
import com.ynyes.cslm.service.TdUserPointService;
import com.ynyes.cslm.service.TdUserRecentVisitService;
import com.ynyes.cslm.service.TdUserReturnService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.service.TdUserSuggestionService;
import com.ynyes.cslm.util.FileDownUtils;
import com.ynyes.cslm.util.SiteMagConstant;
import com.ynyes.cslm.util.StringUtils;

/**
 * 后台用户管理控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value="/Verwalter/user")
public class TdManagerUserController {
    
    @Autowired
    TdUserService tdUserService;
    
    @Autowired
    TdUserLevelService tdUserLevelService;
    
    @Autowired
    TdUserConsultService tdUserConsultService;
    
    @Autowired
    TdUserCommentService tdUserCommentService;
    
    @Autowired
    TdUserSuggestionService tdUserSuggestionService;  //add by zhangji
    
    @Autowired
    TdDemandService tdDemandService;  //@zhangji 2015年7月30日11:25:00
    
    @Autowired
    TdUserReturnService tdUserReturnService;
    
    @Autowired
    TdUserCollectService tdUserCollectService;
    
    @Autowired
    TdUserPointService tdUserPointService;
    
    @Autowired
    TdUserRecentVisitService tdUserRecentVisitService;
    
    @Autowired
    TdUserCashRewardService tdUserCashRewardService;
    
    @Autowired
    TdManagerLogService tdManagerLogService;
    
    @Autowired
    TdPayRecordService tdPayRecordService;
    
    @Autowired
    TdSettingService tdSettingService;
    
    @Autowired
    TdDistributorService tdDistributorService;
    
    @Autowired
    TdCashService tdCashService;
    
    @Autowired
    TdProviderService tdProviderService;
    
    @RequestMapping(value="/check/{type}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> userCheckForm(@PathVariable String type,String param, Long id) {
        Map<String, String> res = new HashMap<String, String>();
        
        res.put("status", "n");
        
        
        if (null == param || param.isEmpty())
        {
            res.put("info", "该字段不能为空");
            return res;
        }
        if("username".equals(type))
        {
        	if (null == id)
            {
                if (null != tdUserService.findByUsername(param))
                {
                    res.put("info", "已存在同名用户");
                    return res;
                }
            }
            else
            {
                if (null != tdUserService.findByUsernameAndIdNot(param, id))
                {
                    res.put("info", "已存在同名用户");
                    return res;
                }
            }
        }else if("mobile".equals(type)){
        	if (null == id)
            {
                if (null != tdUserService.findByMobile(param))
                {
                    res.put("info", "此手机号已被占用");
                    return res;
                }
            }
            else
            {
                if (null != tdUserService.findByMobileAndIdNot(param, id))
                {
                    res.put("info", "此手机号已被占用");
                    return res;
                }
            }
        }
        
        
        res.put("status", "y");
        
        return res;
    }
    
    @RequestMapping(value="/list")
    public String setting(Integer page,
                          Integer size,
                          String keywords,
                          Long roleId,
                          String __EVENTTARGET,
                          String __EVENTARGUMENT,
                          String __VIEWSTATE,
                          Long[] listId,
                          Integer[] listChkId,
                          ModelMap map,
                          HttpServletRequest req,
                          HttpServletResponse resp){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
        String exportUrl ="";
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
                btnDelete("user", listId, listChkId);
                tdManagerLogService.addLog("delete", "删除用户", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("exportAll"))
            {
            	System.err.println("导出会员");
            	exportUrl = SiteMagConstant.backupPath;
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
        
        if (null != keywords)
        {
            keywords = keywords.trim();
        }
        
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("keywords", keywords);
        map.addAttribute("roleId", roleId);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);

        Page<TdUser> userPage = tdUserService.findAll(keywords, page, size);
        
        
//        if (null == roleId)
//        {
//            if (null == keywords || "".equalsIgnoreCase(keywords))
//            {
//                userPage = tdUserService.findAllOrderBySortIdAsc(page, size);
//            }
//            else
//            {
//                userPage = tdUserService.searchAndOrderByIdDesc(keywords, page, size);
//            }
//        }
//        else
//        {
//            if (null == keywords || "".equalsIgnoreCase(keywords))
//            {
//                userPage = tdUserService.findByRoleIdOrderByIdDesc(roleId, page, size);
//            }
//            else
//            {
//                userPage = tdUserService.searchAndFindByRoleIdOrderByIdDesc(keywords, roleId, page, size);
//            }
//        }
        
        map.addAttribute("user_page", userPage);
        
        if(null != exportUrl && !"".equals(exportUrl)){
        	// 第一步，创建一个webbook，对应一个Excel文件  
  	      HSSFWorkbook wb = new HSSFWorkbook();  
  	      // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
  	      HSSFSheet sheet = wb.createSheet("user");  
  	      // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
  	      HSSFRow row = sheet.createRow((int) 0);  
  	      // 第四步，创建单元格，并设置值表头 设置表头居中  
  	      HSSFCellStyle style = wb.createCellStyle();  
  	      style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
  	      
  	      HSSFCell cell = row.createCell((short) 0);  
  	      cell.setCellValue("会员ID");  
  	      cell.setCellStyle(style);  
  	      cell = row.createCell((short) 1);  
  	      cell.setCellValue("会员账号");  
  	      cell.setCellStyle(style);  
  	      cell = row.createCell((short) 2);  
  	      cell.setCellValue("姓名");  
  	      cell.setCellStyle(style);  
  	      cell = row.createCell((short) 3);  
  	      cell.setCellValue("昵称");  
  	      cell.setCellStyle(style);
  	      cell = row.createCell((short) 4);  
  	      cell.setCellValue("性别");  
  	      cell.setCellStyle(style);
  	      cell = row.createCell((short) 5);  
  	      cell.setCellValue("注册时间");  
  	      cell.setCellStyle(style);
  	      cell = row.createCell((short) 6);  
  	      cell.setCellValue("身份证号");  
  	      cell.setCellStyle(style);
  	      cell = row.createCell((short) 7);  
	      cell.setCellValue("手机号");  
	      cell.setCellStyle(style);
	      cell = row.createCell((short) 8);  
  	      cell.setCellValue("邮箱");  
  	      cell.setCellStyle(style);
  	      cell = row.createCell((short) 9);  
	      cell.setCellValue("家庭地址");  
	      cell.setCellStyle(style);
	      cell = row.createCell((short) 10);  
  	      cell.setCellValue("积分");  
  	      cell.setCellStyle(style);
  	      cell = row.createCell((short) 11);  
	      cell.setCellValue("余额");  
	      cell.setCellStyle(style);
	      cell = row.createCell((short) 12);  
  	      cell.setCellValue("累计消费金额");  
  	      cell.setCellStyle(style);
  	      
  	      userPage = tdUserService.findAll(keywords, page, Integer.MAX_VALUE);
  			
  			if (ImportData(userPage, row, cell, sheet)) {
  				FileDownUtils.download("user", wb, exportUrl, resp);
  			}                          	                          
        }
        
        return "/site_mag/user_list";
    }
    
   
    @RequestMapping(value="/edit")
    public String orderEdit(Long id,
                        Long roleId,
                        String action,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        map.addAttribute("roleId", roleId);
        if (null != id)
        {
            map.addAttribute("user", tdUserService.findOne(id));
        }
        return "/site_mag/user_edit";
    }
    
    /**
	 * @author lc
	 * @注释：手动修改粮草
	 */
    @RequestMapping(value="/param/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> paramEdit(Long userId,
                        Long totalPoints,
                        String data, 
                        String type,
                        Double virtualMoney,
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
        if (null != userId && null != type && !type.isEmpty()) {
        	TdUser tdUser = tdUserService.findOne(userId);
        	
        	if (type.equalsIgnoreCase("editPoint")) {
        		if (null != totalPoints) {
        			tdUser.setTotalPoints(totalPoints);
        			TdUserPoint userPoint = new TdUserPoint();
        	        
        	        userPoint.setTotalPoint(totalPoints);
        	        userPoint.setUsername(tdUser.getUsername());
        	        userPoint.setPoint(totalPoints);
        			if (null != data) {	
        		        userPoint.setDetail(data);   
        			}
        			userPoint = tdUserPointService.save(userPoint);
        			
        			tdManagerLogService.addLog("add", "手动将会员"+tdUser.getUsername()+"修改积分为"+data, req);
        			res.put("code", 0);
        			return res;
        		}
    		}
        	else if(type.equalsIgnoreCase("virtualMoney")) // 充值
        	{
        		if(null != virtualMoney)
        		{
        			if(null == tdUser.getVirtualMoney())
        			{
        				tdUser.setVirtualMoney(virtualMoney);
        			}else{
        				tdUser.setVirtualMoney(tdUser.getVirtualMoney()+virtualMoney);
        			}
        			tdUserService.save(tdUser);
        			
        			Date current = new Date();
        	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        	        String curStr = sdf.format(current);
        	        Random random = new Random();
        	        
        			// 添加会员虚拟账户金额记录
                	TdPayRecord record = new TdPayRecord();
                	
                	record.setAliPrice(0.0);
                	record.setPostPrice(0.0);
                	record.setRealPrice(virtualMoney);
                	record.setTotalGoodsPrice(virtualMoney);
                	record.setServicePrice(0.0);
                	record.setProvice(virtualMoney);
                	
                	String number = "CZ" + curStr
                        	+ leftPad(Integer.toString(random.nextInt(999)), 3, "0");
                        record.setOrderNumber(number);
                        
                	record.setCreateTime(new Date());
                	record.setUsername(tdUser.getUsername());
                	record.setType(2L);
                	record.setCont("平台充值");
                	record.setStatusCode(1);
                	
                	tdPayRecordService.save(record); // 保存会员虚拟账户记录
                	
                	TdSetting setting = tdSettingService.findTopBy();
             		
             		if( null != setting.getVirtualMoney())
                    {
                    	setting.setVirtualMoney(setting.getVirtualMoney()-virtualMoney);
                    }else{
                    	setting.setVirtualMoney(0-virtualMoney);
                    }
                    tdSettingService.save(setting); // 更新平台虚拟余额
                    
                 // 记录平台支出
                    record = new TdPayRecord();
                    record.setCont("手动给会员"+tdUser.getUsername()+"充值");
                    record.setCreateTime(new Date());
                    record.setUsername(tdUser.getUsername());
                    record.setOrderNumber(number);
                    record.setStatusCode(1);
                    record.setType(1L); // 类型 区分平台记录
                    
                    record.setProvice(virtualMoney); // 订单总额
                    record.setPostPrice(0.0); // 邮费
                    record.setAliPrice(0.0);	// 第三方费
                    record.setServicePrice(0.0);	// 平台费
                    record.setTotalGoodsPrice(virtualMoney); // 商品总价
                    // 
                    record.setRealPrice(virtualMoney);
                    
                    tdPayRecordService.save(record);
                    
                    tdManagerLogService.addLog("add", "手动给会员"+tdUser.getUsername()+"充值"+data, req);
                    
                    // 添加会员充值记录
                	
                	TdCash cash = new TdCash();
                	cash.setCashNumber("平台充值:"+number);
                	cash.setShopTitle(tdUser.getRealName());
                	cash.setUsername(tdUser.getUsername());
                	cash.setCreateTime(new Date());
                	cash.setPrice(virtualMoney); // 金额
                	cash.setShopType(4L); // 类型-会员
                	cash.setType(1L); // 类型-充值
                	cash.setStatus(2L); // 状态 完成
                	
                	tdCashService.save(cash);
                    
                    res.put("code", 0);
        			return res;
        		}
        	}
		}
        
        
        return res;
    }
    @RequestMapping(value="/save")
    public String orderEdit(TdUser tdUser,Long totalPoints, String totalPointsRemarks,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        /**
		 * @author lc
		 * @注释：手动修改用户积分
		 */
        if (null != totalPoints) {
			tdUser.setTotalPoints(totalPoints);
			TdUserPoint userPoint = new TdUserPoint();
	        
	        userPoint.setTotalPoint(totalPoints);
	        userPoint.setUsername(tdUser.getUsername());
	        userPoint.setPoint(totalPoints);
			if (null !=totalPointsRemarks) {	
		        userPoint.setDetail(totalPointsRemarks);   
			}
			userPoint = tdUserPointService.save(userPoint);
		}
        
        if (null == tdUser.getId())
        {
            tdManagerLogService.addLog("add", "修改会员"+tdUser.getUsername(), req);
            tdUser = tdUserService.addNewUser(tdUser.getUsername(), tdUser.getPassword(), tdUser.getMobile(), tdUser.getEmail(), null);
        }
        else
        {
            tdManagerLogService.addLog("edit", "修改会员"+tdUser.getUsername(), req);
        }
        
        tdUserService.save(tdUser);
        
        return "redirect:/Verwalter/user/list/";
    }
    
    @RequestMapping(value="/level/edit")
    public String edit(Long id,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        if (null != id)
        {
            map.addAttribute("userLevelId", id);
            map.addAttribute("user_level", tdUserLevelService.findOne(id));
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        return "/site_mag/user_level_edit";
    }
    
    @RequestMapping(value="/level/save")
    public String levelSave(TdUserLevel tdUserLevel,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        if (null == tdUserLevel.getId())
        {
            tdManagerLogService.addLog("add", "修改用户等级", req);
        }
        else
        {
            tdManagerLogService.addLog("edit", "修改用户等级", req);
        }
        
        tdUserLevelService.save(tdUserLevel);
        
        return "redirect:/Verwalter/user/level/list";
    }
    
    @RequestMapping(value="/level/check/{type}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> validateForm(@PathVariable String type, 
                                            String param,
                                            Long id) {
        Map<String, String> res = new HashMap<String, String>();
        
        res.put("status", "n");
        res.put("info", "通过");
        
        if (null != type)
        {
            if (type.equalsIgnoreCase("levelId"))
            {
                if (null == param || param.isEmpty())
                {
                    res.put("info", "该字段不能为空");
                    return res;
                }
                
                if (null == id)
                {
                    if (null != tdUserLevelService.findByLevelId(Long.parseLong(param)))
                    {
                        res.put("info", "该用户等级已存在");
                        return res;
                    }
                }
                else
                {
                    if (null != tdUserLevelService.findByLevelIdAndIdNot(Long.parseLong(param), id))
                    {
                        res.put("info", "该用户等级已存在");
                        return res;
                    }
                }
                
                res.put("status", "y");
            }
            else if (type.equalsIgnoreCase("title"))
            {
                if (null == param || param.isEmpty())
                {
                    res.put("info", "该字段不能为空");
                    return res;
                }
                
                if (null == id)
                {
                    if (null != tdUserLevelService.findByTitle(param))
                    {
                        res.put("info", "该等级用户名称已存在");
                        return res;
                    }
                }
                else
                {
                    if (null != tdUserLevelService.findByTitleAndIdNot(param, id))
                    {
                        res.put("info", "该等级用户名称已存在");
                        return res;
                    }
                }
                
                res.put("status", "y");
            }
        }
        
        return res;
    }
    
    @RequestMapping(value="/consult/edit")
    public String consultEdit(Long id,
                        Long statusId,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        if (null != id)
        {
            map.addAttribute("userConsultId", id);
            map.addAttribute("user_consult", tdUserConsultService.findOne(id));
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        map.addAttribute("statusId", statusId);
        
        return "/site_mag/user_consult_edit";
    }
    
    @RequestMapping(value="/consult/save")
    public String consultSave(TdUserConsult tdUserConsult,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        if (null == tdUserConsult.getIsReplied() || !tdUserConsult.getIsReplied())
        {
            tdUserConsult.setIsReplied(true);
            tdUserConsult.setReplyTime(new Date());
        }
        
        if (null == tdUserConsult.getId())
        {
            tdManagerLogService.addLog("add", "修改用户咨询", req);
        }
        else
        {
            tdManagerLogService.addLog("edit", "修改用户咨询", req);
        }
        
        tdUserConsultService.save(tdUserConsult);
        
        return "redirect:/Verwalter/user/consult/list?statusId=" + __VIEWSTATE;
    }
    
    @RequestMapping(value="/comment/edit")
    public String commentEdit(Long id,
                        Long statusId,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        if (null != id)
        {
            map.addAttribute("userCommentId", id);
            map.addAttribute("user_comment", tdUserCommentService.findOne(id));
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        map.addAttribute("statusId", statusId);
        
        return "/site_mag/user_comment_edit";
    }
    
    @RequestMapping(value="/comment/save")
    public String commentSave(TdUserComment tdUserComment,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        if (null == tdUserComment.getIsReplied() || !tdUserComment.getIsReplied())
        {
            tdUserComment.setIsReplied(true);
            tdUserComment.setReplyTime(new Date());
        }
        
        if (null == tdUserComment.getId())
        {
            tdManagerLogService.addLog("add", "修改用户评论", req);
        }
        else
        {
            tdManagerLogService.addLog("edit", "修改用户评论", req);
        }
        
        
        tdUserCommentService.save(tdUserComment);
        
        return "redirect:/Verwalter/user/comment/list?statusId=" + __VIEWSTATE;
    }
    
    @RequestMapping(value="/return/edit")
    public String returnEdit(Long id,
                        Long statusId,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        if (null != id)
        {
            map.addAttribute("userReturnId", id);
            TdUserReturn userReturn = tdUserReturnService.findOne(id);
            map.addAttribute("user_return",userReturn);
            
            if(null != userReturn.getTurnType() && userReturn.getTurnType() ==2){
	    		 map.addAttribute("supply", tdProviderService.findOne(userReturn.getSupplyId()));
	    	 }
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        map.addAttribute("statusId", statusId);
        
        return "/site_mag/user_return_edit";
    }
    
    @RequestMapping(value="/return/save")
    public String returnSave(TdUserReturn tdUserReturn,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        if (null == tdUserReturn.getId())
        {
            tdManagerLogService.addLog("add", "修改用户退换货", req);
        }
        else
        {
            tdManagerLogService.addLog("edit", "修改用户退换货", req);
        }
        
        
        tdUserReturnService.save(tdUserReturn);
        
        return "redirect:/Verwalter/user/return/list?statusId=" + __VIEWSTATE;
    }
    
    @RequestMapping(value="/{type}/list")
    public String list(@PathVariable String type,
                        Integer page,
                        Integer size,
                        Long userId,
                        Long statusId,
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
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
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
                btnDelete(type, listId, listChkId);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnSave"))
            {
                btnSave(type, listId, listSortId);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnVerify"))
            {
                btnVerify(type, listId, listChkId);
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
        
        if (null != keywords)
        {
            keywords = keywords.trim();
        }
        
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("userId", userId);
        map.addAttribute("statusId", statusId);
        map.addAttribute("keywords", keywords);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
            
        if (null != type)
        {
            if (type.equalsIgnoreCase("point")) // 积分
            {
                if (null == userId)
                {
                    return "/site_mag/error_404";
                }
                
                TdUser user = tdUserService.findOne(userId);
                
                if (null == user || null == user.getUsername())
                {
                    return "/site_mag/error_404";
                }
                
                map.addAttribute("user_point_page", tdUserPointService.findByUsername(user.getUsername(), page, size));
                return "/site_mag/user_point_list";
            }
            else if (type.equalsIgnoreCase("collect")) // 关注
            {
                if (null == userId)
                {
                    return "/site_mag/error_404";
                }
                
                TdUser user = tdUserService.findOne(userId);
                
                if (null == user || null == user.getUsername())
                {
                    return "/site_mag/error_404";
                }
                
                map.addAttribute("user_collect_page", tdUserCollectService.findByUsername(user.getUsername(),1, page, size));
                return "/site_mag/user_collect_list";
            }
            else if (type.equalsIgnoreCase("recent")) // 最近浏览
            {
                if (null == userId)
                {
                    return "/site_mag/error_404";
                }
                
                TdUser user = tdUserService.findOne(userId);
                
                if (null == user || null == user.getUsername())
                {
                    return "/site_mag/error_404";
                }
                
                map.addAttribute("user_recent_page", tdUserRecentVisitService.findByUsernameOrderByVisitTimeDesc(user.getUsername(), page, size));
                return "/site_mag/user_recent_list";
            }
            else if (type.equalsIgnoreCase("reward")) // 返现
            {
                if (null == userId)
                {
                    return "/site_mag/error_404";
                }
                
                TdUser user = tdUserService.findOne(userId);
                
                if (null == user || null == user.getUsername())
                {
                    return "/site_mag/error_404";
                }
                
                map.addAttribute("user_reward_page", tdUserCashRewardService.findByUsernameOrderByIdDesc(user.getUsername(), page, size));
                return "/site_mag/user_reward_list";
            }
            else if (type.equalsIgnoreCase("level")) // 用户等级
            {
                map.addAttribute("user_level_page", tdUserLevelService.findAllOrderBySortIdAsc(page, size));
                return "/site_mag/user_level_list";
            }
            else if (type.equalsIgnoreCase("consult")) // 用户咨询
            {
                map.addAttribute("user_consult_page", findTdUserConsult(statusId, keywords, page, size));
                return "/site_mag/user_consult_list";
            }
            else if (type.equalsIgnoreCase("comment")) // 用户评论
            {
                map.addAttribute("user_comment_page", findTdUserComment(statusId, keywords, page, size));
                return "/site_mag/user_comment_list";
            }
            else if (type.equalsIgnoreCase("return")) // 退换货
            {
                map.addAttribute("user_return_page", findTdUserReturn(statusId, keywords, page, size));
                return "/site_mag/user_return_list";
            }
        }
        
        return "/site_mag/error_404";
    }
    
    @ModelAttribute
    public void getModel(@RequestParam(value = "userId", required = false) Long userId,
                    @RequestParam(value = "userLevelId", required = false) Long userLevelId,
                    @RequestParam(value = "userConsultId", required = false) Long userConsultId,
                    @RequestParam(value = "userCommentId", required = false) Long userCommentId,
                    @RequestParam(value = "userReturnId", required = false) Long userReturnId,
                        Model model) {
        if (null != userId) {
            model.addAttribute("tdUser", tdUserService.findOne(userId));
        }
        
        if (null != userLevelId) {
            model.addAttribute("tdUserLevel", tdUserLevelService.findOne(userLevelId));
        }
        
        if (null != userConsultId) {
            model.addAttribute("tdUserConsult", tdUserConsultService.findOne(userConsultId));
        }
        
        if (null != userCommentId) {
            model.addAttribute("tdUserComment", tdUserCommentService.findOne(userCommentId));
        }
        
        if (null != userReturnId) {
            model.addAttribute("tdUserReturn", tdUserReturnService.findOne(userReturnId));
        }
    }
    
    private Page<TdUserConsult> findTdUserConsult(Long statusId, String keywords, int page, int size)
    {
        Page<TdUserConsult> dataPage = null;
        
        if (null == statusId)
        {
            if (null == keywords || "".equalsIgnoreCase(keywords))
            {
                dataPage = tdUserConsultService.findAllOrderByIdDesc(page, size);
            }
            else
            {
                dataPage = tdUserConsultService.searchAndOrderByIdDesc(keywords, page, size);
            }
        }
        else
        {
            if (null == keywords || "".equalsIgnoreCase(keywords))
            {
                dataPage = tdUserConsultService.findByStatusIdOrderByIdDesc(statusId, page, size);
            }
            else
            {
                dataPage = tdUserConsultService.searchAndFindByStatusIdOrderByIdDesc(keywords, statusId, page, size);
            }
        }
        
        return dataPage;
    }
    
    private Page<TdUserComment> findTdUserComment(Long statusId, String keywords, int page, int size)
    {
        Page<TdUserComment> dataPage = null;
        
        if (null == statusId)
        {
            if (null == keywords || "".equalsIgnoreCase(keywords))
            {
                dataPage = tdUserCommentService.findAllOrderByIdDesc(page, size);
            }
            else
            {
                dataPage = tdUserCommentService.searchAndOrderByIdDesc(keywords, page, size);
            }
        }
        else
        {
            if (null == keywords || "".equalsIgnoreCase(keywords))
            {
                dataPage = tdUserCommentService.findByStatusIdOrderByIdDesc(statusId, page, size);
            }
            else
            {
                dataPage = tdUserCommentService.searchAndFindByStatusIdOrderByIdDesc(keywords, statusId, page, size);
            }
        }

        return dataPage;
    }
    
    private Page<TdUserReturn> findTdUserReturn(Long statusId, String keywords, int page, int size)
    {
        Page<TdUserReturn> dataPage = null;
        
        if (null == statusId)
        {
            if (null == keywords || "".equalsIgnoreCase(keywords))
            {
                dataPage = tdUserReturnService.findAllOrderBySortIdAsc(page, size);
            }
            else
            {
                dataPage = tdUserReturnService.searchAndOrderBySortIdAsc(keywords, page, size);
            }
        }
        else
        {
            if (null == keywords || "".equalsIgnoreCase(keywords))
            {
                dataPage = tdUserReturnService.findByStatusIdOrderBySortIdAsc(statusId, page, size);
            }
            else
            {
                dataPage = tdUserReturnService.searchAndFindByStatusIdOrderBySortIdAsc(keywords, statusId, page, size);
            }
        }
        
        return dataPage;
    }
    
    private void btnSave(String type, Long[] ids, Long[] sortIds)
    {
        if (null == ids || null == sortIds
                || ids.length < 1 || sortIds.length < 1
                || null == type || "".equals(type))
        {
            return;
        }
        
        for (int i = 0; i < ids.length; i++)
        {
            Long id = ids[i];
            
            if (type.equalsIgnoreCase("user")) // 用户
            {
                TdUser e = tdUserService.findOne(id);
                
                if (null != e)
                {
                    if (sortIds.length > i)
                    {
                        e.setSortId(sortIds[i]);
                        tdUserService.save(e);
                    }
                }
            }
            else if (type.equalsIgnoreCase("level")) // 用户等级
            {
                TdUserLevel e = tdUserLevelService.findOne(id);
                
                if (null != e)
                {
                    if (sortIds.length > i)
                    {
                        e.setSortId(sortIds[i]);
                        tdUserLevelService.save(e);
                    }
                }
            }
            else if (type.equalsIgnoreCase("consult")) // 咨询
            {
                TdUserConsult e = tdUserConsultService.findOne(id);
                
                if (null != e)
                {
                    if (sortIds.length > i)
                    {
                        e.setSortId(sortIds[i]);
                        tdUserConsultService.save(e);
                    }
                }
            }
            else if (type.equalsIgnoreCase("comment")) // 评论
            {
                TdUserComment e = tdUserCommentService.findOne(id);
                
                if (null != e)
                {
                    if (sortIds.length > i)
                    {
                        e.setSortId(sortIds[i]);
                        tdUserCommentService.save(e);
                    }
                }
            }
            else if (type.equalsIgnoreCase("return")) // 退换货
            {
                TdUserReturn e = tdUserReturnService.findOne(id);
                
                if (null != e)
                {
                    if (sortIds.length > i)
                    {
                        e.setSortId(sortIds[i]);
                        tdUserReturnService.save(e);
                    }
                }
            }
        }
    }
    
    private void btnDelete(String type, Long[] ids, Integer[] chkIds)
    {
        if (null == ids || null == chkIds
                || ids.length < 1 || chkIds.length < 1 
                || null == type || "".equals(type))
        {
            return;
        }
        
        for (int chkId : chkIds)
        {
            if (chkId >=0 && ids.length > chkId)
            {
                Long id = ids[chkId];
                
                if (type.equalsIgnoreCase("user")) // 用户
                {
                    tdUserService.delete(id);
                }
                else if (type.equalsIgnoreCase("level")) // 用户等级
                {
                    tdUserLevelService.delete(id);
                }
                else if (type.equalsIgnoreCase("consult")) // 咨询
                {
                    tdUserConsultService.delete(id);
                }
                else if (type.equalsIgnoreCase("comment")) // 评论
                {
                    tdUserCommentService.delete(id);
                }
                else if (type.equalsIgnoreCase("suggestion")) //投诉  @ by zhangji
                {
                	tdUserSuggestionService.delete(id);
                }
                else if (type.equalsIgnoreCase("return")) // 退换货
                {
                    tdUserReturnService.delete(id);
                }
            }
        }
    }
    
    private void btnVerify(String type, Long[] ids, Integer[] chkIds)
    {
        if (null == ids || null == chkIds
                || ids.length < 1 || chkIds.length < 1 
                || null == type || "".equals(type))
        {
            return;
        }
        
        for (int chkId : chkIds)
        {
            if (chkId >=0 && ids.length > chkId)
            {
                Long id = ids[chkId];
                
                if (type.equalsIgnoreCase("consult")) // 咨询
                {
                    TdUserConsult e = tdUserConsultService.findOne(id);
                    
                    if (null != e)
                    {
                        e.setStatusId(1L);
                        tdUserConsultService.save(e);
                    }
                }
                else if (type.equalsIgnoreCase("comment")) // 评论
                {
                    TdUserComment e = tdUserCommentService.findOne(id);
                    
                    if (null != e)
                    {
                        e.setStatusId(1L);
                        tdUserCommentService.save(e);
                    }
                }
                else if(type.equalsIgnoreCase("demand"))  //团购要求      @zhangji 2015年7月30日11:23:51
                {
                	TdDemand e = tdDemandService.findOne(id);
                	
                	if (null != e)
                	{
                		e.setStatusId(1L);
                		tdDemandService.save(e);
                	}
                		
                }
                else if (type.equalsIgnoreCase("return")) // 退换货
                {
                    TdUserReturn e = tdUserReturnService.findOne(id);
                    
                    if (null != e)
                    {
                    	TdDistributor distributor = tdDistributorService.findOne(e.getShopId());
                        
                        if(null != distributor)
                        {
                        	if(null != distributor.getVirtualMoney()&&  distributor.getVirtualMoney() > e.getGoodsPrice())
                        	{
                        		distributor.setVirtualMoney(distributor.getVirtualMoney()-e.getGoodsPrice());
                        		tdDistributorService.save(distributor);
                        		
                        		e.setStatusId(1L);
                        		tdUserReturnService.save(e);
                        		
                        		TdUser user = tdUserService.findByUsername(e.getUsername());
                                if(null != user)
                                {
                                	if(null != user.getVirtualMoney())
                                	{
                                		user.setVirtualMoney(user.getVirtualMoney()+e.getGoodsPrice());
                                	}else{
                                		user.setVirtualMoney(e.getGoodsPrice());
                                	}
                                }
                                tdUserService.save(user);
                                
                                
                             // 添加会员虚拟账户金额记录
                            	TdPayRecord record = new TdPayRecord();
                            	
                            	record.setAliPrice(0.0);
                            	record.setPostPrice(0.0);
                            	record.setRealPrice(e.getGoodsPrice());
                            	record.setTotalGoodsPrice(e.getGoodsPrice());
                            	record.setServicePrice(0.0);
                            	record.setProvice(e.getGoodsPrice());
                            	record.setOrderNumber(e.getOrderNumber());
                            	record.setCreateTime(new Date());
                            	record.setUsername(user.getUsername());
                            	record.setType(2L);
                            	record.setCont("退货返款");
                            	record.setDistributorTitle(e.getShopTitle());
                            	record.setStatusCode(1);
                            	tdPayRecordService.save(record); // 保存会员虚拟账户记录
                            	
                            	record = new TdPayRecord();
                            	
                            	record.setAliPrice(0.0);
                            	record.setPostPrice(0.0);
                            	record.setRealPrice(e.getGoodsPrice());
                            	record.setTotalGoodsPrice(e.getGoodsPrice());
                            	record.setServicePrice(0.0);
                            	record.setProvice(e.getGoodsPrice());
                            	record.setOrderNumber(e.getOrderNumber());
                            	record.setCreateTime(new Date());
                            	record.setCont("用户退货返款");
                            	record.setDistributorId(e.getShopId());
                            	record.setDistributorTitle(e.getShopTitle());
                            	record.setStatusCode(1);
                            	tdPayRecordService.save(record); // 保存会员虚拟账户记录
                        		
                        	}
                        }
                    }
                }
            }
        }
    }
    
    
    @SuppressWarnings("deprecation")
	public boolean ImportData(Page<TdUser> userPage, HSSFRow row, HSSFCell cell, HSSFSheet sheet){
    	if(null != userPage && userPage.getContent().size() > 0){
    		for (int i = 0; i < userPage.getContent().size(); i++)  
    		{  
    			row = sheet.createRow((int) i + 1);  
    			TdUser user = userPage.getContent().get(i); 
    			
    			// 第四步，创建单元格，并设置值  
    			row.createCell((short) 0).setCellValue(user.getId());  
    			row.createCell((short) 1).setCellValue(user.getUsername());  
    			row.createCell((short) 2).setCellValue(user.getRealName());
    			row.createCell((short) 3).setCellValue(user.getNickname());
    			row.createCell((short) 4).setCellValue(user.getSex());
    			cell = row.createCell((short) 5);  
    			cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(user.getRegisterTime()));                                
    			row.createCell((short) 6).setCellValue(user.getIdentity());
    			row.createCell((short) 7).setCellValue(user.getMobile());
    			row.createCell((short) 8).setCellValue(user.getEmail());
    			row.createCell((short) 9).setCellValue(user.getHomeAddress());
    			row.createCell((short) 10).setCellValue(user.getTotalPoints());
    			row.createCell((short) 11).setCellValue(StringUtils.scale(user.getVirtualMoney()));
    			row.createCell((short) 12).setCellValue(StringUtils.scale(user.getTotalSpendCash()));
    			
    		} 
    	}
    	return true;
    }
}
