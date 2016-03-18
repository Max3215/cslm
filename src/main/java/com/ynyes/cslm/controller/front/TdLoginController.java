package com.ynyes.cslm.controller.front;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdProviderService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.util.VerifServlet;

/**
 * 登录
 *
 */
@Controller
public class TdLoginController {
	@Autowired
	private TdUserService tdUserService;

	@Autowired
	private TdCommonService tdCommonService;
	
	@Autowired
	private TdProviderService tdProviderservice;
	
	@Autowired
	private TdDistributorService tdDistributorService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest req, ModelMap map) {
		String username = (String) req.getSession().getAttribute("username");

		String referer = req.getHeader("referer");
		
		// 基本信息
		tdCommonService.setHeader(map, req);

		if (null == username) {
			return "/client/login";
		}

		if (null == referer) {
			referer = "/";
		}
		/**
		 * @author lc 
		 * @注释：同盟店登录
		 */
		String diysiteUsername = (String) req.getSession().getAttribute("diysiteUsername");
		
		TdUser tdUser = tdUserService.findByUsername(diysiteUsername);
		if(null != tdUser){
			if (null != tdUser.getRoleId() && tdUser.getRoleId().equals(2L)) {
				return "redirect:/user/diysite/order/list/0";
			}
		}
		
		return "redirect:" + referer;
	}

	/**
	 * 
	 * 密码找回<BR>
	 * 方法名：forget<BR>
	 * 创建人：guozhengyang <BR>
	 * 时间：2015年2月2日-下午4:37:35 <BR>
	 * 
	 * @return String<BR>
	 * @param [参数1]
	 *            [参数1说明]
	 * @param [参数2]
	 *            [参数2说明]
	 * @exception <BR>
	 * @since 1.0.0
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(String username, String password,String type,  HttpServletRequest request) {
		Map<String, Object> res = new HashMap<String, Object>();
//		String smsCodeSave = (String) request.getSession().getAttribute("SMSCODE");

		if (username.isEmpty() || password.isEmpty()) {
			res.put("msg", "用户名及密码不能为空");
		}

		// 普通会员登录
		if(null == type || "user".equals(type)){
			TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
			if (null != user) {
				if (!user.getPassword().equals(password)) {
					res.put("msg", "密码错误");
					return res;
				}
				user.setLastLoginTime(new Date());
				user = tdUserService.save(user);
				res.put("code", 0);
				request.getSession().setAttribute("username", user.getUsername());
				return res;
			}
		
		} 
		// 超市登录
		if("distributor".equals(type))
		{
			TdDistributor distributor = tdDistributorService.findbyUsername(username);
			if(null != distributor)
			{
				if(!distributor.getPassword().equals(password))
				{
					res.put("msg", "密码错误");
					return res;
				}
				request.getSession().setAttribute("distributor", distributor.getUsername());
				request.getSession().setAttribute("disTitle", distributor.getTitle());
				res.put("code", 1);
				return res;
			}
		}
		// 批发商登录
		if("provider".equals(type))
		{
			TdProvider provider = tdProviderservice.findByUsername(username);
			if(null != provider)
			{
				if(!provider.getPassword().equals(password))
				{
					res.put("msg", "密码错误");
					return res;
				}
				if(provider.getType() ==1L)
				{
					request.getSession().setAttribute("provider", provider.getUsername());
					request.getSession().setAttribute("proTitle", provider.getTitle());
					res.put("code",2);
					return res;
				}
			}
		}
		// 分销商登录
		if("supply".equals(type))
		{
			TdProvider provider = tdProviderservice.findByUsername(username);
			if(null != provider)
			{
				if(!provider.getPassword().equals(password))
				{
					res.put("msg", "密码错误");
					return res;
				}
				if(provider.getType() ==2L)
				{
					request.getSession().setAttribute("supply", provider.getUsername());
					request.getSession().setAttribute("supplyTitle", provider.getTitle());
					res.put("code",3);
					return res;
				}
			}
		}
		
		// 账号-手机都未通过验证，则用户不存在
			res.put("msg", "不存在该用户");
			return res;
		
	}

	/**
	 * 登录时查找用户手机号以便获取短信验证
	 * 
	 * @author libiao
	 * @param username
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/login/user", method=RequestMethod.GET)
	@ResponseBody
	public Map<String ,Object> findUser(String username, HttpServletRequest req){
		Map<String, Object> res = new HashMap<String, Object>();
		if (username.isEmpty()) {
			res.put("msg", "用户名不能为空！");
			return res;
		}
		
		TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
		
		if(null == user){
			res.put("msg", "用户不存在！");
			return res;
		}
		
		if(null == user.getMobile()){
			res.put("msg","用户不存在手机号！");
		}
		
		res.put("mobile", user.getMobile());
		
		return res;
	}
	
	
	
	
	
	
	/**
	 * @author lc
	 * @return 
	 * @注释：密码找回
	 */
	@RequestMapping(value = "/login/password_retrieve", method = RequestMethod.GET)
	public String Retrieve(String username,String mobile,Integer errCode,HttpServletRequest req, ModelMap map){
		tdCommonService.setHeader(map, req);
		
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
		return "/client/user_retrieve_step1";
	}
	
	
	@RequestMapping(value = "/login/retrieve_step1", method = RequestMethod.POST)
	public String Step2(String username,String mobile,String smsCode,HttpServletRequest req, ModelMap map){
		if (null == smsCode) {
			return "redirect:/login/password_retrieve?errCode=4&username="+username+"&mobile="+mobile;
		}
		String smsCodeSave = (String) req.getSession().getAttribute("SMSCODE");
		if(null == smsCodeSave){
			return "redirect:/login/password_retrieve?errCode=3&username="+username+"&mobile="+mobile;
		}
		
		if (!smsCodeSave.equalsIgnoreCase(smsCode)) {
			return "redirect:/login/password_retrieve?errCode=4&username="+username+"&mobile="+mobile;
		}
		tdCommonService.setHeader(map, req);
		
		map.addAttribute("username", username);
		map.addAttribute("mobile", mobile);
		
		return "/client/user_retrieve_step2";
	}
	
	@RequestMapping(value = "/login/retrieve_step2", method = RequestMethod.POST)
	public String Step3(String username,String password, HttpServletRequest req, ModelMap map){
		TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
		if (null != password) {
			user.setPassword(password);
			tdUserService.save(user);
			tdCommonService.setHeader(map, req);
			req.getSession().setAttribute("username", user.getUsername());
			req.getSession().setAttribute("usermobile", user.getMobile());
			return "/client/user_retrieve_step3";
		}
		return "/client/error_404";
	}
	
    /**
	 * @author lc
	 * @注释：支付宝绑定登陆
	 */
	@RequestMapping(value = "/login/alipay_accredit/{type}", method = RequestMethod.GET)
	public String alipaylogin(@PathVariable String type, String useralipay_username, HttpServletRequest request, ModelMap map) {
		TdUser user = tdUserService.findByalipayname(useralipay_username);
		if ("qq".equals(type)) {
			user = tdUserService.findByQqUserId(useralipay_username);
		}		
        
		String alipayusername = randomUsername();
        
		if (null != user) {
			user.setLastLoginTime(new Date());
			user = tdUserService.save(user);
			request.getSession().setAttribute("username", user.getUsername());
			request.getSession().setAttribute("usermobile", user.getMobile());

			return "redirect:/";
		} else {
		
			map.put("username1", useralipay_username);
			map.put("type", type);
			map.put("typeId", useralipay_username);
			tdCommonService.setHeader(map, request);
			return "client/login_verification";
		}
	}
    /**
	 * @author lc
	 * @注释：登录手机验证
	 */
	@RequestMapping(value = "/login/mobile_accredit", method = RequestMethod.POST)
	public String mobileVerification(String username, String mobile, String type, String typeId,
									HttpServletRequest request, ModelMap map){
		if (null == username) {
			return "client/error_404";
		}
		if (null == mobile) {
			return "client/error_404";
		}
		TdUser user = tdUserService.addNewUser(username, "123456", null, null, null);
		if (null != user) {
			if("qq".equals(type)){
				//QQ登录新建账号
				user.setQqUserId(typeId);
			}else{
				//支付宝登录新建账号
				user.setAlipayUserId(typeId);
			}
			user.setMobile(mobile);
			user.setLastLoginTime(new Date());
			tdUserService.save(user);
			request.getSession().setAttribute("username", user.getUsername());
			request.getSession().setAttribute("usermobile", user.getMobile());
			return "redirect:/";
		}
		return "client/error_404";
	}
	
	/**
	 * @author lc
	 * @注释：随机生成支付宝绑定用户名
	 */
	public String randomUsername() {
		Random random = new Random();
		String username = " ";
		while (true) {
			int temp1 = random.nextInt(10000000);
			username = "user_" + temp1;
			if (null == tdUserService.findByUsername(username)) {
				return username;
			}
		}
	}

	/**
	 * 跳转进入互联开放平台进行QQ登录验证
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author libiao
	 */
	@RequestMapping(value = "/qq/login", method = RequestMethod.GET)
	public void infoQQLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		try {
			response.sendRedirect(new Oauth().getAuthorizeURL(request));
		} catch (QQConnectException e) {
			e.printStackTrace();
		}
	}

	/**
	 * QQ登录返回结果处理
	 * @author libiao
	 * @param code
	 * @param state
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/login/qq_login_return", method = RequestMethod.GET)
	public String qqLoginReturn(String code, String state, HttpServletRequest request, ModelMap map) {

		tdCommonService.setHeader(map, request);
		try {
			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
			String accessToken = null, openID = null;
			long tokenExpireIn = 0L;

			if (accessTokenObj.getAccessToken().equals("")) {
				// 我们的网站被CSRF攻击了或者用户取消了授权
				// 做一些数据统计工作
			} else {
				accessToken = accessTokenObj.getAccessToken();
				
				tokenExpireIn = accessTokenObj.getExpireIn();

				request.getSession().setAttribute("demo_access_token", accessToken);
				request.getSession().setAttribute("demo_token_expirein", String.valueOf(tokenExpireIn));

				// 利用获取到的accessToken 去获取当前用的openid -------- start
				OpenID openIDObj = new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();
				System.err.println("openID-----------"+openID);

				//利用获取到的accessToken,openid 去获取用户在Qzone的昵称
				UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                if (userInfoBean.getRet() == 0) {
                   map.put("nickName",userInfoBean.getNickname());
                }
				
				//根据openID查找用户
				map.put("alipay_user_id", openID);
				map.put("qq", "qq");
				TdUser user = tdUserService.findByQqUserId(openID);
				if(null == user){
					//用户不存在，跳转绑定页面
					return "/client/accredit_login";
				}else{
					//用户存在，修改最后登录时间，跳转首页
					user.setLastLoginTime(new Date());
					tdUserService.save(user);
					request.getSession().setAttribute("username", user.getUsername());
					request.getSession().setAttribute("usermobile", user.getMobile());
					return "redirect:/";
				}
			}
		} catch (QQConnectException e) {
			
		}
		return "/client/error_404";
	}

	@RequestMapping("/logout")
	public String logOut(HttpServletRequest request) {
//		request.getSession().invalidate();
		request.getSession().removeAttribute("username");
		return "redirect:/";
	}
	
	@RequestMapping("/disout")
	public String distributorOut(HttpServletRequest request) {
//		request.getSession().invalidate();
		request.getSession().removeAttribute("DISTRIBUTOR_ID");
		request.getSession().removeAttribute("distributorTitle");
		return "redirect:/";
	}

	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public void verify(HttpServletResponse response, HttpServletRequest request) {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);
		VerifServlet randomValidateCode = new VerifServlet();
		randomValidateCode.getRandcode(request, response);
	}
}
