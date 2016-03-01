package com.ynyes.cslm.touch;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdCoupon;
import com.ynyes.cslm.entity.TdCouponType;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdCouponService;
import com.ynyes.cslm.service.TdCouponTypeService;
import com.ynyes.cslm.service.TdUserService;

@Controller
@RequestMapping("/touch/coupon")
public class TdTouchCouponController {
	@Autowired
	private TdCommonService tdCommonService;
		
	@Autowired 
    private TdCouponService tdCouponService;
	
	@Autowired
	private TdUserService tdUserService;
	
	@Autowired 
    private TdCouponTypeService tdCouponTypeService;
	
	@RequestMapping("/list")
	public String couponList(HttpServletRequest req, ModelMap map)
	{
		tdCommonService.setHeader(map, req);		
        
        // 优惠券种类
        List<TdCouponType> couponTypeList = tdCouponTypeService.findAllOrderBySortIdAsc();
        
        map.addAttribute("coupon_type_list", couponTypeList);
        
        if (null != couponTypeList)
        {
            for (TdCouponType ct : couponTypeList)
            {
                List<TdCoupon> couponList = tdCouponService.findByTypeIdAndIsDistributtedFalse(ct.getId());
                
                List<TdCoupon> disCouponList = tdCouponService.findByTypeIdAndIsDistributtedTrueOrderByIdDesc(ct.getId());
                
                if (null != couponList) {
                	// 未领取优惠券
                    map.addAttribute("coupon_" + ct.getId() + "_list", couponList);
				}

                // 已领取优惠券
                map.addAttribute("distributed_coupon_" + ct.getId() + "_list", disCouponList);
            }
        }
        //传入用户信息
        String username = (String) req.getSession().getAttribute("username");
        TdUser tdUser = tdUserService.findByUsername(username);
        if (null != tdUser) {
        	 map.addAttribute("user", tdUser);
		}
        
      //判断是否为app链接
        Integer isApp = (Integer) req.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
        
		return "/touch/coupon_list";
	}
	
	@RequestMapping("/get")
	public String couponGet(Long couponTypeId,
							HttpServletRequest req, ModelMap map)
	{
		String username = (String) req.getSession().getAttribute("username");
        if (null == username)
        {
            return "redirect:/touch/login";
        }
        
        tdCommonService.setHeader(map, req);
        
        if (null == couponTypeId) {
        	return "/touch/error_404";
		}
        
        List<TdCoupon> couponList = tdCouponService.findByTypeIdAndIsDistributtedFalse(couponTypeId);
        
        List<TdCoupon> disCouponList = tdCouponService.findByTypeIdAndIsDistributtedTrueOrderByIdDesc(couponTypeId);
        
        // 未领取优惠券
        map.addAttribute("coupon_list", couponList);
        // 已领取优惠券
        map.addAttribute("distributed_coupon_list", disCouponList);
        
        //优惠券类别
        map.addAttribute("couponType", tdCouponTypeService.findOne(couponTypeId));
        
        //传入用户信息
        TdUser tdUser = tdUserService.findByUsername(username);
        if (null != tdUser) {
        	 map.addAttribute("user", tdUser);
		}
        
      //判断是否为app链接
        Integer isApp = (Integer) req.getSession().getAttribute("app");
        if (null != isApp) {
        	map.addAttribute("app", isApp);
		}
        
		return "/touch/coupon_get";
	}
	
	/**
	 * 优惠券领用
	 * @param page
	 * @param map
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/request", method=RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> couponRequest(Long couponId, 
                            HttpServletRequest req) {
	    Map<String, Object> res = new HashMap<String, Object>();
//	    String codeBack = (String) req.getSession().getAttribute("RANDOMVALIDATECODEKEY");
	   
	    res.put("code", 1);
	    
	    String username = (String) req.getSession().getAttribute("username");
        if (null == username)
        {
        	res.put("message", "请登录！");
            return res;
        }
        
	    if (null == couponId)
	    {
	        res.put("message", "未选择优惠券");
	        return res;
	    }
	    
//	    if (null == code || null == codeBack || !code.equalsIgnoreCase(codeBack))
//	    {
//	        res.put("message", "验证码错误");
//            return res;
//	    }
	    
	    TdCoupon leftCoupon = tdCouponService.findOne(couponId);
	    
	    TdUser tdUser = tdUserService.findByUsername(username);
	    
	    if (null == leftCoupon || leftCoupon.getLeftNumber().compareTo(1L) < 0)
	    {
	        res.put("message", leftCoupon.getTypeTitle()+"已被领完");
            return res;
	    }
	    
//	    TdCoupon coupon = tdCouponService.findByTypeIdAndUsernameAndIsDistributtedTrue(leftCoupon.getTypeId(), username);
	    
//	    if (null != coupon)
//	    {
//	        res.put("message", "每个用户限领一张同类型优惠券");
//            return res;
//	    }
	    
	    leftCoupon.setLeftNumber(leftCoupon.getLeftNumber() - 1L);
	    leftCoupon.setGetNumber(leftCoupon.getGetNumber() + 1L);
	    tdCouponService.save(leftCoupon);
	    
	    TdCouponType ctype = tdCouponTypeService.findOne(leftCoupon.getTypeId());
	    
	    TdCoupon getCoupon = new TdCoupon();
	    

//	    getCoupon.setDiySiteId(leftCoupon.getDiySiteId());
//	    getCoupon.setDiySiteTitle(leftCoupon.getDiySiteTitle());
	    getCoupon.setGetNumber(1L);
	    getCoupon.setGetTime(new Date());
	    
	    if (null != ctype && null != ctype.getTotalDays())
	    {
    	    Calendar ca = Calendar.getInstance();
    	    ca.add(Calendar.DATE, ctype.getTotalDays().intValue());
    	    getCoupon.setExpireTime(ca.getTime());
	    }
	    
	    getCoupon.setIsDistributted(true);
	    getCoupon.setIsUsed(false);
	    if (null != tdUser.getMobile()) {
	    	getCoupon.setMobile(tdUser.getMobile());
		}
	    if (null != leftCoupon.getTypeDescription()) {
	    	getCoupon.setTypeDescription(leftCoupon.getTypeDescription());
		}	    
	    getCoupon.setTypeId(leftCoupon.getTypeId());
	    if (null != leftCoupon.getTypePicUri()) {
	    	getCoupon.setTypePicUri(leftCoupon.getTypePicUri());
		}
	    if (null != leftCoupon.getTypeTitle()) {
	    	getCoupon.setTypeTitle(leftCoupon.getTypeTitle());
		}
//	    if (null != leftCoupon.getPrice()) {
//			getCoupon.setPrice(leftCoupon.getPrice());
//		}
	    
	    getCoupon.setUsername(username);
	    
	    tdCouponService.save(getCoupon);
	    
	    res.put("code", 0);
	    
	    return res;
	}
}
