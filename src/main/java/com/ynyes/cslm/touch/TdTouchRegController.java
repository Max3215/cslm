package com.ynyes.cslm.touch;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ynyes.cslm.entity.TdSetting;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.entity.TdUserPoint;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdSettingService;
import com.ynyes.cslm.service.TdUserPointService;
import com.ynyes.cslm.service.TdUserService;

@Controller
public class TdTouchRegController {
	@Autowired
    private TdUserService tdUserService;
    
    @Autowired
    private TdUserPointService tdUserPointService;
    
    @Autowired
    private TdSettingService tdSettingService;
    
    @Autowired
    private TdCommonService tdCommonService;
    
    @RequestMapping("/touch/reg")
    public String regquick(Integer errCode, 
    				  Integer shareId, String mobile,
    				  HttpServletRequest request,
    				  ModelMap map) {
        String username = (String) request.getSession().getAttribute("username");
        if (null != shareId)
        {
            map.addAttribute("share_id", shareId);
        }
        
        // 基本信息
        tdCommonService.setHeader(map, request);
        
      //判断是否为app链接
        Integer isApp = (Integer) request.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
        
        if (null == username) {
            if (null != errCode)
            {
                if(errCode.equals(1))
                {
                	map.addAttribute("error","验证码错误");
                }
                else if (errCode.equals(2))
                {
                    map.addAttribute("error", "用户名已存在");
                }
                else if (errCode.equals(3))
                {
                    map.addAttribute("error", "短信验证码错误");
                }
                map.addAttribute("errCode", errCode);
                map.addAttribute("mobile", mobile);
            }           
            return "/touch/reg_quick";
        }
        return "redirect:/touch/user";
    }
    
    @RequestMapping(value="/touch/regquick",method=RequestMethod.POST)
    public String regquick(String mobile,
                String code,
                String smscode,
//                Long shareId,
                HttpServletRequest request){
        String codeBack = (String) request.getSession().getAttribute("RANDOMVALIDATECODEKEY");
        String smsCodeSave = (String) request.getSession().getAttribute("SMSCODE");
        if (null == smsCodeSave ) {
			smsCodeSave = "123456";			
		}
        if (null == codeBack ) {
        	codeBack = "123456";			
		}
        
     // 从session中获取shareid
        Long shareId = (Long) request.getSession().getAttribute("shareId");
        
        if ( null == code)
        {
             if (null == shareId)
                 {
                     return "redirect:/touch/reg?mobile="+ mobile ;
             }
             else
             {
                     return "redirect:/touch/reg?shareId=" + shareId + "&mobile=" + mobile;
             }
        }
             
        if (!codeBack.equalsIgnoreCase(code))
        {
             if (null == shareId)
             {
                     return "redirect:/touch/reg?errCode=1" + "&mobile=" + mobile;
             }
             else
             {
                     return "redirect:/touch/reg?errCode=1&shareId=" + shareId + "&mobile=" + mobile;
             }
        }
        	 
        if (!smsCodeSave.equalsIgnoreCase(smscode))
        {
                 if (null == shareId)
                 {
                     return "redirect:/touch/reg?errCode=3" + "&mobile=" + mobile;
                 }
                 else
                 {
                     return "redirect:/touch/reg?errCode=3&shareId=" + shareId + "&mobile=" + mobile;
                 }
        }
        //将手机号作为用户名
                            
        TdUser user = tdUserService.findByUsername(mobile);
        
        if (null != user)
        {
            if (null == shareId)
            {
                return "redirect:/touch/reg?errCode=2";
            }
            else
            {
                return "redirect:/touch/reg?errCode=2&shareId=" + shareId;
            }
        }
        
        user = tdUserService.addNewUser(null, mobile, "huizhidian", mobile, null);
        
        if (null == user)
        {
            if (null == shareId)
            {
                return "redirect:/touch/reg?errCode=3";
            }
            else
            {
                return "redirect:/touch/reg?errCode=3&shareId=" + shareId;
            }
        }
        
        user = tdUserService.save(user);
        
        // 奖励分享用户
        if (null != shareId)
        {
            TdUser sharedUser = tdUserService.findOne(shareId);
            
            if (null != sharedUser )
            {
                TdSetting setting = tdSettingService.findTopBy();
                TdUserPoint userPoint = new TdUserPoint();
                
                if (null != setting)
                {
                    userPoint.setPoint(setting.getRegisterSharePoints());
                }
                else
                {
                    userPoint.setPoint(0L);
                }
                
                if (null != sharedUser.getTotalPoints())
                {
                    userPoint.setTotalPoint(sharedUser.getTotalPoints() + userPoint.getPoint());
                }
                else
                {
                    userPoint.setTotalPoint(userPoint.getPoint());
                }
                
                userPoint.setUsername(sharedUser.getUsername());
                userPoint.setDetail("用户分享网站成功奖励");
                
                userPoint = tdUserPointService.save(userPoint);
                
                sharedUser.setTotalPoints(userPoint.getTotalPoint()); // 积分
                
                // 角色变换限制
                if (!sharedUser.getRoleId().equals(2L)) {
                	sharedUser.setRoleId(1L);
				}
                                
                if (null == sharedUser.getTotalLowerUsers()) {
					sharedUser.setTotalLowerUsers(1L);
				}else {
					sharedUser.setTotalLowerUsers(sharedUser.getTotalLowerUsers() + 1);
				}
                
                tdUserService.save(sharedUser);
                
                //用户层级关系
//                user.setUpperUsername(sharedUser.getUsername());
                tdUserService.save(user);
            }
            
           
        }
        
        request.getSession().setAttribute("username", mobile);
        
        String referer = (String) request.getAttribute("referer");
        
        if (null != request.getAttribute("referer"))
        {
            return "redirect:" + referer;
        }
        
        if (null == shareId)
        {
            return "redirect:/touch/user";
        }
        
        return "redirect:/touch/user?shareId=" + shareId;
    }
    
    @RequestMapping("/touch/regid")
    public String reg(Integer errCode, 
    				  Integer shareId, String username1,  String mobile, String email,
    				  HttpServletRequest request,
    				  ModelMap map) {
        String username = (String) request.getSession().getAttribute("username");
        if (null != shareId)
        {
            map.addAttribute("share_id", shareId);
        }
        
        // 基本信息
        tdCommonService.setHeader(map, request);
        
      //判断是否为app链接
        Integer isApp = (Integer) request.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
        
        if (null == username) {
            if (null != errCode)
            {
                if(errCode.equals(1))
                {
                	map.addAttribute("error","验证码错误");
                }
                else if (errCode.equals(2))
                {
                    map.addAttribute("error", "用户名已存在");
                }
                else if (errCode.equals(3))
                {
                    map.addAttribute("error", "短信验证码错误");
                }
                map.addAttribute("errCode", errCode);
            }
            map.addAttribute("username1", username1);
            map.addAttribute("mobile", mobile);
            map.addAttribute("email", email);
            return "/touch/reg";
        }
        return "redirect:/touch/user";
    }
    
    @RequestMapping(value="/touch/regid",method=RequestMethod.POST)
    public String reg(String username,
                String mobile,
                String password,
                String email,               
                String code,
                String smscode,
//                Long shareId,
                HttpServletRequest request){
        String codeBack = (String) request.getSession().getAttribute("RANDOMVALIDATECODEKEY");

        if (null == codeBack ) {
        	codeBack = "123456";			
		}
        
        // 从session中获取shareid
        Long shareId = (Long) request.getSession().getAttribute("shareId");
        if (null == username) {
        	
             
		}else {
			if (null == code)
            {
                if (null == shareId)
                {
                    return "redirect:/touch/regid?username1=" + username + "&mobile=" + mobile +"&email=" + email;
                }
                else
                {
                    return "redirect:/touch/regid?shareId=" + shareId + "&username1=" + username + "&mobile=" + mobile+"&email=" + email;
                }
            }
            
            if (!codeBack.equalsIgnoreCase(code))
            {
                if (null == shareId)
                {
                    return "redirect:/touch/regid?errCode=1" + "&username1=" + username + "&mobile=" + mobile +"&email=" + email;
                }
                else
                {
                    return "redirect:/touch/regid?errCode=1&shareId=" + shareId + "&username1=" + username + "&mobile=" + mobile  +"&email=" + email;
                }
            }
                       
		}
       
          
        TdUser user = tdUserService.findByUsername(username);
        
        if (null != user)
        {
            if (null == shareId)
            {
                return "redirect:/touch/regid?errCode=2";
            }
            else
            {
                return "redirect:/touch/regid?errCode=2&shareId=" + shareId;
            }
        }
        
        user = tdUserService.addNewUser(null, username, password, mobile, email);
        
        if (null == user)
        {
            if (null == shareId)
            {
                return "redirect:/touch/regid?errCode=1";
            }
            else
            {
                return "redirect:/touch/regid?errCode=1&shareId=" + shareId;
            }
        }
        
        user = tdUserService.save(user);
        
        // 奖励分享用户
        if (null != shareId)
        {
            TdUser sharedUser = tdUserService.findOne(shareId);
            
            if (null != sharedUser )
            {
                TdSetting setting = tdSettingService.findTopBy();
                TdUserPoint userPoint = new TdUserPoint();
                
                if (null != setting)
                {
                    userPoint.setPoint(setting.getRegisterSharePoints());
                }
                else
                {
                    userPoint.setPoint(0L);
                }
                
                if (null != sharedUser.getTotalPoints())
                {
                    userPoint.setTotalPoint(sharedUser.getTotalPoints() + userPoint.getPoint());
                }
                else
                {
                    userPoint.setTotalPoint(userPoint.getPoint());
                }
                
                userPoint.setUsername(sharedUser.getUsername());
                userPoint.setDetail("用户分享网站成功奖励");
                
                userPoint = tdUserPointService.save(userPoint);
                
                sharedUser.setTotalPoints(userPoint.getTotalPoint()); // 积分
                
                // 角色变换限制
                if (!sharedUser.getRoleId().equals(2L)) {
                	sharedUser.setRoleId(1L);
				}
                                
                if (null == sharedUser.getTotalLowerUsers()) {
					sharedUser.setTotalLowerUsers(1L);
				}else {
					sharedUser.setTotalLowerUsers(sharedUser.getTotalLowerUsers() + 1);
				}
                
                tdUserService.save(sharedUser);
                
                //用户层级关系
//                user.setUpperUsername(sharedUser.getUsername());
                tdUserService.save(user);
            }
            
           
        }
        
        request.getSession().setAttribute("username", username);
        
        String referer = (String) request.getAttribute("referer");
        
        if (null != request.getAttribute("referer"))
        {
            return "redirect:" + referer;
        }
        
        if (null == shareId)
        {
            return "redirect:/touch/user";
        }
        
        return "redirect:/touch/user?shareId=" + shareId;
    }
}
