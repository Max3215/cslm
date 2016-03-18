package com.ynyes.cslm.controller.front;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.util.SMSUtil;
import com.ynyes.cslm.util.VerifServlet;

/**
 * 注册处理
 * 
 */
@Controller
public class TdRegController {
    @Autowired
    private TdUserService tdUserService;
    
    @Autowired
    private TdCommonService tdCommonService;
    
    @RequestMapping(value = "/reg/check/{type}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> validateForm(@PathVariable String type, String param) {
        Map<String, String> res = new HashMap<String, String>();

        res.put("status", "n");
        
        
        if (null == type)
        {
        	res.put("info", "参数错误");
            return res;
        }
        
        if (type.equalsIgnoreCase("username"))
        {
        	if (null == param || param.isEmpty()) {
                res.put("info", "用户名不能为空");
                return res;
            }
        	
        	TdUser user = tdUserService.findByUsername(param);
        	
        	if (null != user)
        	{
        		res.put("info", "该用户已经存在");
                return res;
        	}
        }
        
        /**
         * 	ajax实时验证
         * 	手机号查找用户
         * 	判断手机号是已否注册
         * @author libiao
         */
        if (type.equalsIgnoreCase("mobile"))		
        {
        	if (null == param || param.isEmpty())
        	{
                res.put("info", "用户名不能为空");
                return res;
            }
        	
        	TdUser user = tdUserService.findByMobile(param);		
        	
        	if (null != user)	
         	{
        		res.put("info", "该手机已经注册");
                return res;
        	}
        }

        res.put("status", "y");

        return res;
    }
    
    @RequestMapping("/reg")
    public String reg(Integer errCode, Integer shareId,String name,String carCode, HttpServletRequest request, ModelMap map) {
        String username = (String) request.getSession().getAttribute("username");
        
        if (null != shareId)
        {
            map.addAttribute("share_id", shareId);
        }
        // 基本信息
        tdCommonService.setHeader(map, request);
        
        
        if (null == username) {
            if (null != errCode)
            {
                if (errCode.equals(1))
                {
                    map.addAttribute("error", "验证码错误");
                }
                
                map.addAttribute("errCode", errCode);
            }
            map.addAttribute("username",name);
            map.addAttribute("carCode",carCode);
            return "/client/reg";
            
        }
        return "redirect:/";
    }
    
    @RequestMapping(value="/logutil")
    public String LogUtils(){
        return "/logutil";
    }
    /**
     * 
     * 注册用户保存到数据库<BR>
     * 方法名：saveUser<BR>
     * 时间：2015年2月2日-下午1:44:45 <BR>
     * @param user
     * @param name
     * @param mobile
     * @param password
     * @param newpassword
     * @return String<BR>
     * @param  [参数1]   [参数1说明]
     * @param  [参数2]   [参数2说明]
     * @exception <BR>
     * @since  1.0.0
     */
    @RequestMapping(value="/reg",method=RequestMethod.POST)
    public String reg(String username,
                String mobile,
                String password,
                String email,
                String smsCode,
                String code,
                String carCode,
                HttpServletRequest request){
        String codeBack = (String) request.getSession().getAttribute("RANDOMVALIDATECODEKEY");
//        String smsCodeSave = (String) request.getSession().getAttribute("SMSCODE");
//        if (null == codeBack || null == smsCodeSave)
//        {
//            if (null == shareId)
//            {
//                return "redirect:/reg?name= "+username+"&carCode="+carCode;
//            }
//            else
//            {
//                return "redirect:/reg?shareId=" + shareId + "&name= "+username+"&carCode="+carCode;
//            }
//        }
        
        if (!codeBack.equalsIgnoreCase(code))
        {
                return "redirect:/reg?errCode=1&name= "+username+"&carCode="+carCode;
        }
        
//        if (!smsCodeSave.equalsIgnoreCase(smsCode))
//        {
//            if (null == shareId)
//            {
//                return "redirect:/reg?errCode=4&name= "+username+"&mobile="+mobile;
//            }
//            else
//            {
//                return "redirect:/reg?errCode=4&shareId=" + shareId + "&name= "+username+"&carCode="+carCode;
//            }
//        }
        
        
       TdUser user = tdUserService.addNewUser(username, password, mobile, email, carCode);
        
        if (null == user)
        {
                return "redirect:/reg?errCode=3";
        }
        
        user = tdUserService.save(user);
        
//        // 奖励分享用户
//        if (null != shareId)
//        {
//            TdUser sharedUser = tdUserService.findOne(shareId);
//            
//            if (null != sharedUser && sharedUser.getRoleId().equals(0L))
//            {
//                TdSetting setting = tdSettingService.findTopBy();
//                TdUserPoint userPoint = new TdUserPoint();
//                
//                if (null != setting)
//                {
//                    userPoint.setPoint(setting.getRegisterSharePoints());
//                }
//                else
//                {
//                    userPoint.setPoint(0L);
//                }
//                
//                if (null != sharedUser.getTotalPoints())
//                {
//                    userPoint.setTotalPoint(sharedUser.getTotalPoints() + userPoint.getPoint());
//                }
//                else
//                {
//                    userPoint.setTotalPoint(userPoint.getPoint());
//                }
//                
//                userPoint.setUsername(sharedUser.getUsername());
//                userPoint.setDetail("用户分享网站成功奖励");
//                
//                userPoint = tdUserPointService.save(userPoint);
//                
//                sharedUser.setTotalPoints(userPoint.getTotalPoint()); // 积分
//                tdUserService.save(sharedUser);
//            }
//        }
        
        request.getSession().setAttribute("username", username);
        
        String referer = (String) request.getAttribute("referer");
        
        if (null != request.getAttribute("referer"))
        {
            return "redirect:" + referer;
        }
        
//        if (null == shareId)
//        {
//            return "redirect:/user";
//        }
        
        return "redirect:/";
    }
    
    @RequestMapping(value = "/code",method = RequestMethod.GET)
    public void verify(HttpServletResponse response, HttpServletRequest request) {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        VerifServlet randomValidateCode = new VerifServlet();
        randomValidateCode.getRandcode(request, response);
    }
    
//    @RequestMapping(value = "/reg/smscode",method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String, Object> smsCode(String mobile, HttpServletResponse response, HttpServletRequest request) {
//        Random random = new Random();
//        
//        String smscode = String.format("%04d", random.nextInt(9999));
//        
//        HttpSession session = request.getSession();
//        
//        session.setAttribute("SMSCODE", smscode);
//       
//        return SMSUtil.send(mobile, "15612" ,new String[]{smscode});
//    }
    
    @RequestMapping(value = "/reg/smscode",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> smsCode(String username,String mobile, HttpServletResponse response, HttpServletRequest request) {
    	HashMap<String, Object> map = new HashMap<>();
    	map.put("code", 1);
    	
    	TdUser user = tdUserService.findByUsername(username);
    	if(null == user)
    	{
    		user = tdUserService.findByMobile(username);
    		if(null == user)
    		{
    			map.put("msg", "账号不存在");
    			return map;
    		}
    	}
    	
    	if(null == mobile || !mobile.equals(user.getMobile())){
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
    
}