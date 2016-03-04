package com.ynyes.cslm.touch;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.multipart.MultipartFile;

import com.ynyes.cslm.entity.TdCoupon;
import com.ynyes.cslm.entity.TdCouponType;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdOrderGoods;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.entity.TdShippingAddress;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.entity.TdUserCollect;
import com.ynyes.cslm.entity.TdUserComment;
import com.ynyes.cslm.entity.TdUserConsult;
import com.ynyes.cslm.entity.TdUserPoint;
import com.ynyes.cslm.entity.TdUserRecentVisit;
import com.ynyes.cslm.entity.TdUserReturn;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdCouponService;
import com.ynyes.cslm.service.TdCouponTypeService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdOrderGoodsService;
import com.ynyes.cslm.service.TdOrderService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.service.TdShippingAddressService;
import com.ynyes.cslm.service.TdUserCashRewardService;
import com.ynyes.cslm.service.TdUserCollectService;
import com.ynyes.cslm.service.TdUserCommentService;
import com.ynyes.cslm.service.TdUserConsultService;
import com.ynyes.cslm.service.TdUserPointService;
import com.ynyes.cslm.service.TdUserRecentVisitService;
import com.ynyes.cslm.service.TdUserReturnService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.util.ClientConstant;
import com.ynyes.cslm.util.SiteMagConstant;

/**
 * 用户中心
 * @author Sharon
 *
 */
@Controller
@RequestMapping("/touch")
public class TdTouchUserController {
    
    @Autowired
    private TdUserService tdUserService;
    
    @Autowired
    private TdGoodsService tdGoodsService;
    
    @Autowired
    private TdUserReturnService tdUserReturnService;
    
    @Autowired
    private TdOrderService tdOrderService;
    
    @Autowired
    private TdUserPointService tdUserPointService;
    
    @Autowired
    private TdUserCollectService tdUserCollectService;
    
    @Autowired
    private TdUserConsultService tdUserConsultService;
    
    @Autowired
    private TdUserCommentService tdUserCommentService;
    
    @Autowired  
    private TdCouponService tdCouponService;
    
    @Autowired
    private TdUserRecentVisitService tdUserRecentVisitService;
    
    @Autowired
    private TdShippingAddressService tdShippingAddressService;
    
    @Autowired
    private TdOrderGoodsService tdOrderGoodsService;
    
    @Autowired
    private TdUserCashRewardService tdUserCashRewardService;
    
    @Autowired
    private TdCommonService tdCommonService;
    
    @Autowired
    private TdProductCategoryService tdProductCategoryService;
    
    @Autowired
    private TdDistributorGoodsService tdDistributorGoodsService;
    
//    @Autowired
//    private TdUserComplainService tdUserComplainService;
//    
//    @Autowired
//    private TdRedEnvelopeService tdRedEnvelopeService;
//    
//    @Autowired
//    private TdRedEnvelopeTypeService tdRedEnvelopeTypeService;
    
    @Autowired
    private TdCouponTypeService tdCouponTypeService;
    
    @RequestMapping(value = "/user")
    public String user(HttpServletRequest req, String username, ModelMap map) {
    	if (null == username) {
    		username = (String) req.getSession().getAttribute("username");
            if (null == username)
            {
                return "redirect:/touch/login";
            }
		}
        tdCommonService.setHeader(map, req);
        req.getSession().setAttribute("username", username);
        map.addAttribute("server_ip", req.getLocalName());
        map.addAttribute("server_port", req.getLocalPort());
        TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        if (null == tdUser)
        {
            return "/touch/error_404";
        }
        
        map.addAttribute("user", tdUser);
        map.addAttribute("order_page", tdOrderService.findByUsername(username, 0, ClientConstant.pageSize));
        map.addAttribute("collect_page", tdUserCollectService.findByUsername(username, 0, ClientConstant.pageSize));
        map.addAttribute("recent_page", tdUserRecentVisitService.findByUsernameOrderByVisitTimeDesc(username, 0, ClientConstant.pageSize));
//        map.addAttribute("total_unpayed", tdOrderService.countByUsernameAndStatusId(username, 2));
//        map.addAttribute("total_undelivered", tdOrderService.countByUsernameAndStatusId(username, 3));
//        map.addAttribute("total_unreceived", tdOrderService.countByUsernameAndStatusId(username, 4));
//        map.addAttribute("total_uncommented", tdOrderService.countByUsernameAndStatusId(username, 5));
//        map.addAttribute("total_finished", tdOrderService.countByUsernameAndStatusId(username, 6));
        map.addAttribute("recommend_goods_page", tdGoodsService.findByIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(0, ClientConstant.pageSize));
        
        return "/touch/user_index";
    }
    
    @RequestMapping(value = "/user/center/headImg", method = RequestMethod.POST)
	public String uploadImg(@RequestParam MultipartFile Filedata, String username,HttpServletRequest req) {
    	if (null == username) {
    		username = (String) req.getSession().getAttribute("username");
            if (null == username)
            {
                return "redirect:/touch/login";
            }
		}
//    	String username = (String) req.getSession().getAttribute("username");
		TdUser user = tdUserService.findByUsername(username);
		if (null == user) {
			return "redirect:/touch/login";
		}

		String name = Filedata.getOriginalFilename();

		String ext = name.substring(name.lastIndexOf("."));

		try {
			byte[] bytes = Filedata.getBytes();

			Date dt = new Date(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String fileName = sdf.format(dt) + ext;

			String uri = SiteMagConstant.imagePath + "/" + fileName;

			File file = new File(uri);

			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
			stream.write(bytes);
			stream.close();
			user.setHeadImageUri("/images/" + fileName);
			tdUserService.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/touch/user";

	}
    
    @RequestMapping(value = "/user/center/qrcode", method = RequestMethod.GET)
   	public String getqrcode( HttpServletRequest req,String username, ModelMap map, Integer app) {
//   		String username = (String) req.getSession().getAttribute("username");
//   		if (null == username) {
//   			return "redirect:/touch/login";
//   		}
    	if (null == username) {
    		username = (String) req.getSession().getAttribute("username");
            if (null == username)
            {
                return "redirect:/touch/login";
            }
		}
   		tdCommonService.setHeader(map, req);
   		TdUser user = tdUserService.findByUsername(username);
   		map.addAttribute("user", user);
//   		if (null == user.getQrCodeUri()) {
//   			try {
//
//   	   			Date dt = new Date(System.currentTimeMillis());
//   	   			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//   	   			String fileName = sdf.format(dt) + ".png";
//
//   	   			String uri = SiteMagConstant.imagePath + "/" + fileName;
//
//   	   			File file = new File(uri);
//
//   	   			QRCodeUtils qr = new QRCodeUtils();
//   	   			qr.getQRCodeForsharer("http://116.55.230.207:8008/reg?shareId="+user.getId(), 300, file);
//   	   			
////   	   			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
////   	   			stream.write(bytes);
////   	   			stream.close();
//   	   			user.setQrCodeUri("/images/" + fileName);
//   	   			tdUserService.save(user);
//   	   		} catch (Exception e) {
//   	   			e.printStackTrace();
//   	   		}
//		}
   	
   		//判断是否为app链接
   		if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
   		
   		return "/touch/user_qrcode";

   	}
    
    @RequestMapping(value = "/user/redenvelope/list")
    public String redenvelopeList( Integer statusId, 
                        Integer page, String username,
                        HttpServletRequest req, Integer app,
                        ModelMap map){
    	if (null == username) {
    		username = (String) req.getSession().getAttribute("username");
            if (null == username)
            {
                return "redirect:/touch/login";
            }
		}
        
        tdCommonService.setHeader(map, req);
        
        if (null == page)
        {
            page = 0;
        }
        
        if (null == statusId)
        {
            statusId = 0;
        }
        
        TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
        map.addAttribute("status_id", statusId);
        
//        Page<TdRedEnvelope> redenvelopePage = null;
//        
//        if (0 == statusId) {
//			redenvelopePage = tdRedEnvelopeService.findByUsername(username, page, ClientConstant.pageSize);
//		}
//        else if (1 == statusId) {
//        	redenvelopePage = tdRedEnvelopeService.findByUsernameAndIsGetFalse(username, page, ClientConstant.pageSize);
//		} 
//        else if (2 == statusId) {
//        	redenvelopePage = tdRedEnvelopeService.findByUsernameAndIsGetTrue(username, page, ClientConstant.pageSize);
//		}
        
//        map.addAttribute("redenvelope_page", redenvelopePage);       
        
        //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_redenvelope_list";
    }
    
    @RequestMapping(value = "/user/redenvelope/edit")
    public String redenvelopeList( Long redenvelopeId,                         
                        HttpServletRequest req, String username, Integer app,
                        ModelMap map){
    	if (null == username) {
    		username = (String) req.getSession().getAttribute("username");
            if (null == username)
            {
                return "redirect:/touch/login";
            }
		}
        
        tdCommonService.setHeader(map, req);
        
        if (null == redenvelopeId)
        {
            return "/touch/error_404";
        }              
        
        TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
        
//        TdRedEnvelope tdRedEnvelope = tdRedEnvelopeService.findOne(redenvelopeId);
        
//        map.addAttribute("tdRedEnvelope", tdRedEnvelope);
           
        //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_redenvelope_edit";
    }
    
    @RequestMapping(value="/user/redenvelope/get", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> redEnvelopeGet(Long redenvelopeId,//订单id
                        ModelMap map,
                        HttpServletRequest req){
        
        Map<String, Object> res = new HashMap<String, Object>();
        
        res.put("code", 1);
        
		String username = (String) req.getSession().getAttribute("username");
		        
		if (null == username)
		{
		      res.put("message", "请登录！！");
		      return res;
		}
		
		tdCommonService.setHeader(map, req);
		        
//		if(null != redenvelopeId)
//		{
//			TdRedEnvelope tdRedEnvelope = tdRedEnvelopeService.findOne(redenvelopeId);
//            TdRedEnvelopeType tdRedEnvelopeType = tdRedEnvelopeTypeService.findOne(tdRedEnvelope.getTypeId());
//			
//            if (null != tdRedEnvelope && null != tdRedEnvelopeType) {
//				if (tdRedEnvelopeType.getLeftNumber() > 0) {
//					TdUser tdUser = tdUserService.findByUsername(username);
//					
//					tdRedEnvelope.setIsGet(true);
//					tdRedEnvelopeType.setLeftNumber(tdRedEnvelopeType.getLeftNumber() - 1);
//					
//					//红包奖励
//					if (tdRedEnvelopeType.getRedEnvelopeType().equals(0L)) {
//						if (null != tdRedEnvelopeType.getPrizePoints()) {
//							tdUser.setTotalPoints(tdRedEnvelopeType.getPrizePoints() + tdUser.getTotalPoints());
//							TdUserPoint userPoint = new TdUserPoint();
//
//							userPoint.setIsBackgroundShow(false);
//							userPoint.setTotalPoint(tdRedEnvelopeType.getPrizePoints() + tdUser.getTotalPoints());
//							userPoint.setUsername(tdUser.getUsername());
//							userPoint.setPoint(tdRedEnvelopeType.getPrizePoints());
//							userPoint.setDetail("红包奖励");
//							
//							userPoint = tdUserPointService.save(userPoint);
//						}
//					}
//					else if (tdRedEnvelopeType.getRedEnvelopeType().equals(1L)) {
//						if (null != tdRedEnvelopeType.getCouponTypeId()) {
//							TdCouponType tdCouponType = tdCouponTypeService.findOne(tdRedEnvelopeType.getCouponTypeId());
//							
//							TdCoupon getCoupon = new TdCoupon();
//						    
//
////						    getCoupon.setDiySiteId(leftCoupon.getDiySiteId());
////						    getCoupon.setDiySiteTitle(leftCoupon.getDiySiteTitle());
//						    getCoupon.setGetNumber(1L);
//						    getCoupon.setGetTime(new Date());
//						    
//						    if (null != tdCouponType && null != tdCouponType.getTotalDays())
//						    {
//					    	    Calendar ca = Calendar.getInstance();
//					    	    ca.add(Calendar.DATE, tdCouponType.getTotalDays().intValue());
//					    	    getCoupon.setExpireTime(ca.getTime());
//						    }
//						    
//						    getCoupon.setIsDistributted(true);
//						    getCoupon.setIsUsed(false);
//						    if (null != tdUser.getMobile()) {
//						    	getCoupon.setMobile(tdUser.getMobile());
//							}
//						    if (null != tdCouponType.getDescription()) {
//						    	getCoupon.setTypeDescription(tdCouponType.getDescription());
//							}	    
//						    getCoupon.setTypeId(tdCouponType.getId());
//						    if (null != tdCouponType.getPicUri()) {
//						    	getCoupon.setTypePicUri(tdCouponType.getPicUri());
//							}
//						    if (null != tdCouponType.getTitle()) {
//						    	getCoupon.setTypeTitle(tdCouponType.getTitle());
//							}
//						    if (null != tdCouponType.getPrice()) {
//								getCoupon.setPrice(tdCouponType.getPrice());
//							}
//						    
//						    getCoupon.setUsername(username);
//						    
//						    tdCouponService.save(getCoupon);
//						}
//					}
//					
//					tdRedEnvelope.setIsGetPrize(true);
//					
//					tdRedEnvelopeService.save(tdRedEnvelope);
//					tdRedEnvelopeTypeService.save(tdRedEnvelopeType);
//					
//					res.put("isGetPrize", true);
//					res.put("isGet", true);
//				}else {		
//					tdRedEnvelope.setIsGetPrize(false);
//					tdRedEnvelope.setIsGet(true);
//					
//					tdRedEnvelopeService.save(tdRedEnvelope);
//					
//					res.put("isGetPrize", false);
//					res.put("isGet", true);
//				}
//				
//				res.put("code", 0);            
//	            return res;
//			}                        
//		}
		res.put("message", "参数错误！！");
		return res;
    }
    
    @RequestMapping(value = "/user/order/list/{statusId}")
    public String orderList(@PathVariable Integer statusId, 
                        Integer page,
                        String keywords,
                        Integer timeId,
                        HttpServletRequest req, 
                        String username, Integer app,
                        ModelMap map){
    	if (null == username) {
    		username = (String) req.getSession().getAttribute("username");
            if (null == username)
            {
                return "redirect:/touch/login";
            }
		}
    	req.getSession().setAttribute("username", username);
//        if (null == username)
//        {
//            return "redirect:/touch/login";
//        }
        
        tdCommonService.setHeader(map, req);
        
        if (null == page)
        {
            page = 0;
        }
        
        if (null == timeId)
        {
            timeId = 0;
        }
        
        if (null == statusId)
        {
            statusId = 0;
        }
        
       TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
        map.addAttribute("status_id", statusId);
        map.addAttribute("time_id", timeId);
         
        Page<TdOrder> orderPage = null;
        
        
        if (statusId.equals(0))
            {
                if (null != keywords && !keywords.isEmpty())
                {
                    //orderPage = tdOrderService.findByUsernameAndSearch(username, keywords, page, ClientConstant.pageSize);
//                	orderPage = tdOrderService.findByUsernameAndStatusIdNotAndSearch(username, 8L, keywords, page, ClientConstant.pageSize);
                }
                else
                {
                    //orderPage = tdOrderService.findByUsername(username, page, ClientConstant.pageSize);
//                	orderPage = tdOrderService.findByUsernameAndStatusIdNot(username, 8L, page, ClientConstant.pageSize);
                }
            }
            else
            {
                if (null != keywords && !keywords.isEmpty())
                {
                    orderPage = tdOrderService.findByUsernameAndStatusIdAndSearch(username, statusId, keywords, page, ClientConstant.pageSize);
                }
                else
                {
                    orderPage = tdOrderService.findByUsernameAndStatusId(username, statusId, page, ClientConstant.pageSize);
                }
         }        
        
        map.addAttribute("order_page", orderPage);
        
        //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_order_list";
    }
   
    
    
    @RequestMapping(value = "/user/order/list/more/{statusId}")
    public String orderListmore(@PathVariable Integer statusId, 
                        Integer page,
                        String keywords,
                        Integer timeId, String username,
                        HttpServletRequest req, Integer app,
                        ModelMap map){
    	if (null == username) {
    		username = (String) req.getSession().getAttribute("username");
            if (null == username)
            {
                return "redirect:/touch/login";
            }
		}
        
        tdCommonService.setHeader(map, req);
        
        if (null == page)
        {
            page = 0;
        }
                     
        if (null == statusId)
        {
            statusId = 0;
        }
        
       TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
        map.addAttribute("status_id", statusId);       
         
        Page<TdOrder> orderPage = null;
        
        
        if (statusId.equals(0))
            {
                if (null != keywords && !keywords.isEmpty())
                {
                    //orderPage = tdOrderService.findByUsernameAndSearch(username, keywords, page, ClientConstant.pageSize);
//                    orderPage = tdOrderService.findByUsernameAndStatusIdNotAndSearch(username, 8L, keywords, page, ClientConstant.pageSize);
                }
                else
                {
                    //orderPage = tdOrderService.findByUsername(username, page, ClientConstant.pageSize);
//                	orderPage = tdOrderService.findByUsernameAndStatusIdNot(username, 8L, page, ClientConstant.pageSize);
                }
            }
            else
            {
                if (null != keywords && !keywords.isEmpty())
                {
                    orderPage = tdOrderService.findByUsernameAndStatusIdAndSearch(username, statusId, keywords, page, ClientConstant.pageSize);
                }
                else
                {
                    orderPage = tdOrderService.findByUsernameAndStatusId(username, statusId, page, ClientConstant.pageSize);
                }
        }       
        
        map.addAttribute("order_page", orderPage);
        
        //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_order_list_more";
    }
    
    /**
     * 用户中心确认收货
     * @author Zhangji
     * @param id
     * @param map
     * @param req
     * @return
     */
    @RequestMapping(value="/user/order/receive", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> orderReceive(Long id,//订单id
                        ModelMap map,
                        HttpServletRequest req){
        
        Map<String, Object> res = new HashMap<String, Object>();
        
        res.put("code", 1);
        
		 String username = (String) req.getSession().getAttribute("username");
		        
		        if (null == username)
		        {
		            res.put("message", "请登录！！");
		            return res;
		        }
		        tdCommonService.setHeader(map, req);
		        
		 if(null != id)
		 {
			 TdOrder order = tdOrderService.findOne(id);
             if (order.getStatusId().equals(4L))
             {
                 order.setStatusId(5L);
                 order.setReceiveTime(new Date());
             }
             tdOrderService.save(order);
             
             res.put("code", 0);
             res.put("message", "确认收货成功！！");
             return res;
		 }
		 res.put("message", "参数错误！！");
		 return res;
    }
    
    @RequestMapping(value = "/user/return/list")
    public String returnList(HttpServletRequest req, 
    					Integer timeId,
                        Integer page,
                        String keywords, String username, Integer app,
                        ModelMap map){
    	if (null == username) {
    		username = (String) req.getSession().getAttribute("username");
            if (null == username)
            {
                return "redirect:/touch/login";
            }
		}
        
        tdCommonService.setHeader(map, req);
        
        if (null == timeId)
        {
            timeId = 0;
        }
        
        if (null == page)
        {
            page = 0;
        }
       
        TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
        map.addAttribute("time_id", timeId);
         
        Page<TdOrder> orderPage = null;
        
        
        if (null != keywords && !keywords.isEmpty())
        {
//              orderPage = tdOrderService.findByUsernameAndOrderNumberAndStatusIdOrUsernameAndOrderNumberAndStatusIdOrUsernameAndOrderNumberAndStatusId( username, keywords,4L,username, keywords,5L,username, keywords,6L,page, ClientConstant.pageSize);
        }
        else
        {
//              orderPage = tdOrderService.findByUsernameAndStatusIdOrUsernameAndStatusIdOrUsernameAndStatusIdOrderByIdDesc(username,4L,username,5L,username,6L,page, ClientConstant.pageSize);
        }
                                                  
        map.addAttribute("return_page", orderPage);
        map.addAttribute("keywords", keywords);
        
      //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_return_list";
    }
    /**
     * 取消订单
     * @author Zhangji
     * @param page
     * @param req
     * @param map
     * @return
     */
    @RequestMapping(value = "/user/cancel/list")
    public String orderList( Integer page,
                        HttpServletRequest req, String username, Integer app,
                        ModelMap map){
    	if (null == username) {
    		username = (String) req.getSession().getAttribute("username");
            if (null == username)
            {
                return "redirect:/touch/login";
            }
		}
        
        tdCommonService.setHeader(map, req);
        
        if (null == page)
        {
            page = 0;
        }
        
        TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
        
        Page<TdOrder> cancelPage = null;
        
//        cancelPage = tdOrderService.findByUsernameAndIsCancelTrue(username, page, ClientConstant.pageSize);
        
        //猜你喜欢 zhangji
//        List<TdUserRecentVisit> lastVisitList = tdUserRecentVisitService.findByUsernameOrderByVisitCountDesc(username);
//        if (0 == lastVisitList.size())
//        {
//            List<TdProductCategory> topCategoryList = tdProductCategoryService
//                    .findByParentIdIsNullOrderBySortIdAsc();
//        	//没有浏览记录时，第一页
//            if (topCategoryList.size() > 0)
//            {
//		        map.addAttribute("reco_page0",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(0).getId(), 0, 4));
//		        map.addAttribute("categoryId0",topCategoryList.get(0).getId());
//		        map.addAttribute("categoryTitle0",topCategoryList.get(0).getTitle());
//            }
//	        //第二页
//            if (topCategoryList.size() > 1)
//            {
//            	
//		        map.addAttribute("reco_page1",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(1).getId(), 0, 4));
//		        map.addAttribute("categoryId1",topCategoryList.get(1).getId());
//		        map.addAttribute("categoryTitle1",topCategoryList.get(1).getTitle());
//            }
//	        //第三页
//            if (topCategoryList.size() > 2)
//            {
//		        map.addAttribute("reco_page2",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(2).getId(), 0, 4));
//		        map.addAttribute("categoryId2",topCategoryList.get(2).getId());
//		        map.addAttribute("categoryTitle2",topCategoryList.get(2).getTitle());
//            }
//        }
//        if (lastVisitList.size() > 0)
//	        	{
//		        	//猜你喜欢，第一页
//			        TdGoods good_0 =tdGoodsService.findOne(lastVisitList.get(0).getGoodsId());
//			        map.addAttribute("reco_page0",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_0.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId0",good_0.getCategoryId());
//			        map.addAttribute("categoryTitle0",good_0.getCategoryTitle());
//	        	}
//         if (lastVisitList.size() > 1)
//	        	{
//			        //猜你喜欢，第二页
//			        TdGoods good_1 =tdGoodsService.findOne(lastVisitList.get(1).getGoodsId());
//			        map.addAttribute("reco_page1",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_1.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId1",good_1.getCategoryId());
//			        map.addAttribute("categoryTitle1",good_1.getCategoryTitle());
//	        	}
//		  if (lastVisitList.size() > 2)
//	        	{
//				    //猜你喜欢，第三页
//			        TdGoods good_2 =tdGoodsService.findOne(lastVisitList.get(2).getGoodsId());
//			        map.addAttribute("reco_page2",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_2.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId2",good_2.getCategoryId());
//			        map.addAttribute("categoryTitle2",good_2.getCategoryTitle());
//	        	}
        
        map.addAttribute("cancel_page", cancelPage);
        
        //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_cancel_list";
    }
    
    @RequestMapping(value = "/user/cancel")
    public String cancel(Long id,
                        HttpServletRequest req, String username, Integer app,
                        ModelMap map){
    	if (null == username) {
    		username = (String) req.getSession().getAttribute("username");
            if (null == username)
            {
                return "redirect:/touch/login";
            }
		}
        
        tdCommonService.setHeader(map, req);
        
        TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
        
        if (null != id)
        {
            map.addAttribute("order", tdOrderService.findOne(id));
        }
        
        //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_cancel_detail";
    }
    
    /**
     * 订单取消申请
     * @author Zhangji
     * @param id
     * @param req
     * @param map
     * @return
     */
    @RequestMapping(value = "/user/cancel/edit")
    public String cancelEdit(Long id, 
                        HttpServletRequest req, String username, Integer app,
                        ModelMap map){
    	if (null == username) {
    		username = (String) req.getSession().getAttribute("username");
            if (null == username)
            {
                return "redirect:/touch/login";
            }
		}

        tdCommonService.setHeader(map, req);
        
        TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
                
        //猜你喜欢 zhangji
//        List<TdUserRecentVisit> lastVisitList = tdUserRecentVisitService.findByUsernameOrderByVisitCountDesc(username);
//        if (0 == lastVisitList.size())
//        {
//            List<TdProductCategory> topCategoryList = tdProductCategoryService
//                    .findByParentIdIsNullOrderBySortIdAsc();
//        	//没有浏览记录时，第一页
//            if (topCategoryList.size() > 0)
//            {
//		        map.addAttribute("reco_page0",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(0).getId(), 0, 4));
//		        map.addAttribute("categoryId0",topCategoryList.get(0).getId());
//		        map.addAttribute("categoryTitle0",topCategoryList.get(0).getTitle());
//            }
//	        //第二页
//            if (topCategoryList.size() > 1)
//            {
//            	
//		        map.addAttribute("reco_page1",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(1).getId(), 0, 4));
//		        map.addAttribute("categoryId1",topCategoryList.get(1).getId());
//		        map.addAttribute("categoryTitle1",topCategoryList.get(1).getTitle());
//            }
//	        //第三页
//            if (topCategoryList.size() > 2)
//            {
//		        map.addAttribute("reco_page2",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(2).getId(), 0, 4));
//		        map.addAttribute("categoryId2",topCategoryList.get(2).getId());
//		        map.addAttribute("categoryTitle2",topCategoryList.get(2).getTitle());
//            }
//        }
//        if (lastVisitList.size() > 0)
//	        	{
//		        	//猜你喜欢，第一页
//			        TdGoods good_0 =tdGoodsService.findOne(lastVisitList.get(0).getGoodsId());
//			        map.addAttribute("reco_page0",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_0.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId0",good_0.getCategoryId());
//			        map.addAttribute("categoryTitle0",good_0.getCategoryTitle());
//	        	}
//         if (lastVisitList.size() > 1)
//	        	{
//			        //猜你喜欢，第二页
//			        TdGoods good_1 =tdGoodsService.findOne(lastVisitList.get(1).getGoodsId());
//			        map.addAttribute("reco_page1",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_1.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId1",good_1.getCategoryId());
//			        map.addAttribute("categoryTitle1",good_1.getCategoryTitle());
//	        	}
//		  if (lastVisitList.size() > 2)
//	        	{
//				    //猜你喜欢，第三页
//			        TdGoods good_2 =tdGoodsService.findOne(lastVisitList.get(2).getGoodsId());
//			        map.addAttribute("reco_page2",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_2.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId2",good_2.getCategoryId());
//			        map.addAttribute("categoryTitle2",good_2.getCategoryTitle());
//	        	}
//        
//	        TdOrder order = tdOrderService.findOne(id);
//	        Long statusId = order.getStatusId();
//	        if (1L == statusId||2L == statusId||3L == statusId)
//	        {
//	        	//判断是否为app链接
//	        	if (null == app) {
//	       			Integer isApp = (Integer) req.getSession().getAttribute("app");
//	       	        if (null != isApp) {
//	       	        	map.addAttribute("app", isApp);
//	       			}
//	    		}else {
//	    			map.addAttribute("app", app);
//	    		}
//	        	
//	        	map.addAttribute("orderId", id);
//	        	map.addAttribute("statusId", statusId);
//	            map.addAttribute("order", order);
//	            return "/client/user_cancel_edit";
//	        }
        
        return "/touch/error_404";
    }
 
    @RequestMapping(value = "/user/cancel/save", method=RequestMethod.POST)
    public String cancelSave(HttpServletRequest req, 
                        Long id, // 订单ID
                        String cancelReason,
                        ModelMap map){
        String username = (String) req.getSession().getAttribute("username");
        
        if (null == username)
        {
            return "redirect:/touch/login";
        }
        
        tdCommonService.setHeader(map, req);
        
        //猜你喜欢 zhangji
//        List<TdUserRecentVisit> lastVisitList = tdUserRecentVisitService.findByUsernameOrderByVisitCountDesc(username);
//        if (0 == lastVisitList.size())
//        {
//            List<TdProductCategory> topCategoryList = tdProductCategoryService
//                    .findByParentIdIsNullOrderBySortIdAsc();
//        	//没有浏览记录时，第一页
//            if (topCategoryList.size() > 0)
//            {
//		        map.addAttribute("reco_page0",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(0).getId(), 0, 4));
//		        map.addAttribute("categoryId0",topCategoryList.get(0).getId());
//		        map.addAttribute("categoryTitle0",topCategoryList.get(0).getTitle());
//            }
//	        //第二页
//            if (topCategoryList.size() > 1)
//            {
//		        map.addAttribute("reco_page1",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(1).getId(), 0, 4));
//		        map.addAttribute("categoryId1",topCategoryList.get(1).getId());
//		        map.addAttribute("categoryTitle1",topCategoryList.get(1).getTitle());
//            }
//	        //第三页
//            if (topCategoryList.size() > 2)
//            {
//		        map.addAttribute("reco_page2",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(2).getId(), 0, 4));
//		        map.addAttribute("categoryId2",topCategoryList.get(2).getId());
//		        map.addAttribute("categoryTitle2",topCategoryList.get(2).getTitle());
//            }
//        }
//        if (lastVisitList.size() > 0)
//	        	{
//		        	//猜你喜欢，第一页
//			        TdGoods good_0 =tdGoodsService.findOne(lastVisitList.get(0).getGoodsId());
//			        map.addAttribute("reco_page0",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_0.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId0",good_0.getCategoryId());
//			        map.addAttribute("categoryTitle0",good_0.getCategoryTitle());
//	        	}
//         if (lastVisitList.size() > 1)
//	        	{
//			        //猜你喜欢，第二页
//			        TdGoods good_1 =tdGoodsService.findOne(lastVisitList.get(1).getGoodsId());
//			        map.addAttribute("reco_page1",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_1.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId1",good_1.getCategoryId());
//			        map.addAttribute("categoryTitle1",good_1.getCategoryTitle());
//	        	}
//		  if (lastVisitList.size() > 2)
//	        	{
//				    //猜你喜欢，第三页
//			        TdGoods good_2 =tdGoodsService.findOne(lastVisitList.get(2).getGoodsId());
//			        map.addAttribute("reco_page2",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_2.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId2",good_2.getCategoryId());
//			        map.addAttribute("categoryTitle2",good_2.getCategoryTitle());
//	        	}
//
//        if (null != id )
//        {
//            TdOrder order = tdOrderService.findOne(id);
//            
//            if (null != order)
//            {
//                if (null == order.getIsCancel()&&3L == order.getStatusId()||false == order.getIsCancel()&&3L == order.getStatusId())
//                {
//                    // 退货时间及原因
//                    order.setCancelReason(cancelReason);
//                    order.setCancelApplyTime(new Date());
//                    order.setIsCancel(true);
//                    // 保存
//                    tdOrderService.save(order);
//                }
//            }
//        }
        
        return "redirect:/touch/user/cancel/list";
    }
    @RequestMapping(value = "/user/cancel/direct")
    public String cancelDirect(HttpServletRequest req, 
                        Long id, // 订单ID
                        String cancelReason,String username,
                        ModelMap map){
    	if (null == username) {
    		username = (String) req.getSession().getAttribute("username");
            if (null == username)
            {
                return "redirect:/touch/login";
            }
		}
        
        tdCommonService.setHeader(map, req);
        
        //猜你喜欢 zhangji
//        List<TdUserRecentVisit> lastVisitList = tdUserRecentVisitService.findByUsernameOrderByVisitCountDesc(username);
//        if (0 == lastVisitList.size())
//        {
//            List<TdProductCategory> topCategoryList = tdProductCategoryService
//                    .findByParentIdIsNullOrderBySortIdAsc();
//        	//没有浏览记录时，第一页
//            if (topCategoryList.size() > 0)
//            {
//		        map.addAttribute("reco_page0",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(0).getId(), 0, 4));
//		        map.addAttribute("categoryId0",topCategoryList.get(0).getId());
//		        map.addAttribute("categoryTitle0",topCategoryList.get(0).getTitle());
//            }
//	        //第二页
//            if (topCategoryList.size() > 1)
//            {
//		        map.addAttribute("reco_page1",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(1).getId(), 0, 4));
//		        map.addAttribute("categoryId1",topCategoryList.get(1).getId());
//		        map.addAttribute("categoryTitle1",topCategoryList.get(1).getTitle());
//            }
//	        //第三页
//            if (topCategoryList.size() > 2)
//            {
//		        map.addAttribute("reco_page2",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(2).getId(), 0, 4));
//		        map.addAttribute("categoryId2",topCategoryList.get(2).getId());
//		        map.addAttribute("categoryTitle2",topCategoryList.get(2).getTitle());
//            }
//        }
//        if (lastVisitList.size() > 0)
//	        	{
//		        	//猜你喜欢，第一页
//			        TdGoods good_0 =tdGoodsService.findOne(lastVisitList.get(0).getGoodsId());
//			        map.addAttribute("reco_page0",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_0.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId0",good_0.getCategoryId());
//			        map.addAttribute("categoryTitle0",good_0.getCategoryTitle());
//	        	}
//         if (lastVisitList.size() > 1)
//	        	{
//			        //猜你喜欢，第二页
//			        TdGoods good_1 =tdGoodsService.findOne(lastVisitList.get(1).getGoodsId());
//			        map.addAttribute("reco_page1",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_1.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId1",good_1.getCategoryId());
//			        map.addAttribute("categoryTitle1",good_1.getCategoryTitle());
//	        	}
//		  if (lastVisitList.size() > 2)
//	        	{
//				    //猜你喜欢，第三页
//			        TdGoods good_2 =tdGoodsService.findOne(lastVisitList.get(2).getGoodsId());
//			        map.addAttribute("reco_page2",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_2.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId2",good_2.getCategoryId());
//			        map.addAttribute("categoryTitle2",good_2.getCategoryTitle());
//	        	}
//
//        if (null != id )
//        {
//            TdOrder order = tdOrderService.findOne(id);
//            
//            if (null != order)
//            {
//                if (1L == order.getStatusId()||2L == order.getStatusId())
//                {
//                	if (null == order.getIsCancel()||false == order.getIsCancel())
//	                {
//	                    order.setStatusId(7L);
//	                    order.setCancelApplyTime(new Date());
//	                    order.setIsCancel(true);
//	                    order.setIsRefund(true);
//	                    // 保存
//	                    tdOrderService.save(order);
//	                }
//                }
//            }
//        }
        
        return "redirect:/touch/user/order/list/0";
    }
    
    @RequestMapping(value = "/user/order/delete")
    public String orderdelete(Long id,  HttpServletRequest req, 
            				  ModelMap map){
    	 String username = (String) req.getSession().getAttribute("username");
         if (null == username)
         {
             return "redirect:/touch/login";
         }
         
         tdCommonService.setHeader(map, req);
    	
         if (null == id) {
			return "/touch/error_404";
		}
        
        TdOrder tdOrder = tdOrderService.findOne(id);
        
        if (null != tdOrder) {
			tdOrder.setStatusId(8L);
			tdOrderService.save(tdOrder);
		}
        
        return "redirect:/touch/user/order/list/0";
    }
    
    @RequestMapping(value = "/user/order")
    public String order(Long id,
                        HttpServletRequest req, Integer app,
                        ModelMap map){
        String username = (String) req.getSession().getAttribute("username");
        if (null == username)
        {
            return "redirect:/touch/login";
        }
        
        tdCommonService.setHeader(map, req);
        
        TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
        
        if (null != id)
        {
            map.addAttribute("order", tdOrderService.findOne(id));
        }
        
      //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_order_detail";
    }
    
    @RequestMapping(value = "/user/collect/list")
    public String collectList(HttpServletRequest req, 
                        Integer page,
                        String keywords, String username, Integer app,
                        ModelMap map){
    	if (null == username) {
    		username = (String) req.getSession().getAttribute("username");
            if (null == username)
            {
                return "redirect:/touch/login";
            }
		}
        
        tdCommonService.setHeader(map, req);
        
        if (null == page)
        {
            page = 0;
        }
        
        TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
        
        Page<TdUserCollect> collectPage = null;
        
        if (null == keywords || keywords.isEmpty())
        {
            collectPage = tdUserCollectService.findByUsername(username, page, ClientConstant.pageSize);
        }
        else
        {
            collectPage = tdUserCollectService.findByUsernameAndSearch(username, keywords, page, ClientConstant.pageSize);
        }
        
        //猜你喜欢 zhangji
//        List<TdUserRecentVisit> lastVisitList = tdUserRecentVisitService.findByUsernameOrderByVisitCountDesc(username);
//        if (0 == lastVisitList.size())
//        {
//            List<TdProductCategory> topCategoryList = tdProductCategoryService
//                    .findByParentIdIsNullOrderBySortIdAsc();
//        	//没有浏览记录时，第一页
//            if (topCategoryList.size() > 0)
//            {
//		        map.addAttribute("reco_page0",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(0).getId(), 0, 4));
//		        map.addAttribute("categoryId0",topCategoryList.get(0).getId());
//		        map.addAttribute("categoryTitle0",topCategoryList.get(0).getTitle());
//            }
//	        //第二页
//            if (topCategoryList.size() > 1)
//            {
//            	
//		        map.addAttribute("reco_page1",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(1).getId(), 0, 4));
//		        map.addAttribute("categoryId1",topCategoryList.get(1).getId());
//		        map.addAttribute("categoryTitle1",topCategoryList.get(1).getTitle());
//            }
//	        //第三页
//            if (topCategoryList.size() > 2)
//            {
//		        map.addAttribute("reco_page2",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(2).getId(), 0, 4));
//		        map.addAttribute("categoryId2",topCategoryList.get(2).getId());
//		        map.addAttribute("categoryTitle2",topCategoryList.get(2).getTitle());
//            }
//        }
//        if (lastVisitList.size() > 0)
//	        	{
//		        	//猜你喜欢，第一页
//			        TdGoods good_0 =tdGoodsService.findOne(lastVisitList.get(0).getGoodsId());
//			        map.addAttribute("reco_page0",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_0.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId0",good_0.getCategoryId());
//			        map.addAttribute("categoryTitle0",good_0.getCategoryTitle());
//	        	}
//         if (lastVisitList.size() > 1)
//	        	{
//			        //猜你喜欢，第二页
//			        TdGoods good_1 =tdGoodsService.findOne(lastVisitList.get(1).getGoodsId());
//			        map.addAttribute("reco_page1",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_1.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId1",good_1.getCategoryId());
//			        map.addAttribute("categoryTitle1",good_1.getCategoryTitle());
//	        	}
//		  if (lastVisitList.size() > 2)
//	        	{
//				    //猜你喜欢，第三页
//			        TdGoods good_2 =tdGoodsService.findOne(lastVisitList.get(2).getGoodsId());
//			        map.addAttribute("reco_page2",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_2.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId2",good_2.getCategoryId());
//			        map.addAttribute("categoryTitle2",good_2.getCategoryTitle());
//	        	}
        
        map.addAttribute("collect_page", collectPage);
        map.addAttribute("keywords", keywords);
        
        //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_collect_list";
    }
    
    @RequestMapping(value = "/user/collect/del")
    public String collectDel(HttpServletRequest req, 
            Long id,
            ModelMap map){
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/touch/login";
        }
        
        if (null != id)
        {
//            TdUserCollect collect = tdUserCollectService
//                    .findByUsernameAndGoodsId(username, id);

            // 删除收藏
//            if (null != collect) {
//                tdUserCollectService.delete(collect);
//            }
        }
        
        return "redirect:/touch/user/collect/list";
    }
    
    @RequestMapping(value = "/user/collect/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> collectAdd(HttpServletRequest req, Long disgId,
            ModelMap map) {

        Map<String, Object> res = new HashMap<String, Object>();
        res.put("code", 1);

        if (null == disgId) {
            res.put("message", "参数错误");
            return res;
        }

        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            res.put("message", "请先登录");
            return res;
        }

        res.put("code", 0);

        // 没有收藏
        if (null == tdUserCollectService.findByUsernameAndDistributorId(username,
                disgId)) {
            TdDistributorGoods distributorGoods = tdDistributorGoodsService.findOne(disgId);

            if (null == distributorGoods) {
                res.put("message", "商品不存在");
                return res;
            }
            TdGoods goods = tdGoodsService.findOne(distributorGoods.getGoodsId());
            
            if (null == goods.getTotalCollects())
            {
                goods.setTotalCollects(0L);
            }
            
            goods.setTotalCollects(goods.getTotalCollects() + 1L);
            
            tdGoodsService.save(goods);

            TdUserCollect collect = new TdUserCollect();

            collect.setUsername(username);
            collect.setDistributorId(distributorGoods.getId());
            collect.setGoodsId(goods.getId());
            collect.setGoodsCoverImageUri(distributorGoods.getCoverImageUri());
            collect.setGoodsTitle(distributorGoods.getGoodsTitle());
            collect.setGoodsSalePrice(distributorGoods.getGoodsPrice());
            collect.setCollectTime(new Date());

            tdUserCollectService.save(collect);

            res.put("message", "添加成功");

            return res;
        }

        res.put("message", "您已收藏了该商品");

        return res;
    }
    
    @RequestMapping(value = "/user/recent/list")
    public String recentList(HttpServletRequest req, 
    					Long categoryId,
                        Integer page,
                        String keywords, Integer app,
                        ModelMap map){
        String username = (String) req.getSession().getAttribute("username");
        
        if (null == username)
        {
            return "redirect:/touch/login";
        }
        
        tdCommonService.setHeader(map, req);
        
        if (null == page)
        {
            page = 0;
        }
        
        TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
        
        Page<TdUserRecentVisit> recentPage = null;
        
        if (null == categoryId)
        {
        	recentPage = tdUserRecentVisitService.findByUsernameOrderByVisitTimeDesc(username, page, ClientConstant.pageSize);
        }
        else
        {
//        	recentPage = tdUserRecentVisitService.findByUsernameAndCategoryIdOrderByVisitTimeDesc(username, categoryId,page, ClientConstant.pageSize);
        }
//        if (null == keywords || keywords.isEmpty())
//        {
//            recentPage = tdUserRecentVisitService.findByUsernameOrderByVisitTimeDesc(username, page, ClientConstant.pageSize);
//        }
//        else
//        {
//            recentPage = tdUserRecentVisitService.findByUsernameAndSearchOrderByVisitTimeDesc(username, keywords, page, ClientConstant.pageSize);
//        }
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(today);// 设置当前日期
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        calendar.add(Calendar.DATE,-1);
        Date tdby = calendar.getTime();
        
        map.addAttribute("today",today);
        map.addAttribute("yesterday", yesterday);
        map.addAttribute("tdby", tdby);
        map.addAttribute("categoryId",categoryId); //商品分类id zhangji
        map.addAttribute("recent_page", recentPage);
        map.addAttribute("keywords", keywords);
        
        //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_recent_list";
    }
    /*
     * 删除历史记录
     * @zhangji
     */
    @RequestMapping(value="/user/recent/delete/{id}")
    public String recentDelete(HttpServletRequest req, 
    																ModelMap map,
    																@PathVariable Long id, 
    																String date){
         String username = (String) req.getSession().getAttribute("username");

         if (null == username) {
        	 return "redirect:/touch/login";
         }
         if (0 == id &&null != date)
         {
//        	 List<TdUserRecentVisit> recentList = tdUserRecentVisitService.findByUsernameAndVisitDate(username,date);
//        	 tdUserRecentVisitService.delete(recentList);

         }
         else if(-1 == id && null != date)
         {
        	 List<TdUserRecentVisit> recentList = tdUserRecentVisitService.findByUsername(username);
        	 tdUserRecentVisitService.delete(recentList);
         }
         else
         {
        	 TdUserRecentVisit recent = tdUserRecentVisitService.findOne(id);
        	 tdUserRecentVisitService.delete(id);
         }
    	 return "redirect:/touch/user/recent/list";
    }
    
    //listId 0-全部，1-未使用，2-已使用，3-已过期 
    @RequestMapping(value = "/user/coupon/list/{listId}")
    public String couponList(HttpServletRequest req, Integer page,@PathVariable Integer listId, String username, Integer app,
                        ModelMap map){
    	if (null == username) {
    		username = (String) req.getSession().getAttribute("username");
            if (null == username)
            {
                return "redirect:/touch/login";
            }
		}
        
        tdCommonService.setHeader(map, req);
        
        if (null == page)
        {
            page = 0;
        }
        
         TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
        
        List<TdCoupon> coupanList = null;
        
//        switch(listId)
//        {
//        case 0:
//        	coupanList =tdCouponService.findByMoblie(tdUser.getMobile()); //全部优惠券
//        	break;
//        case 1:	
//        	coupanList =tdCouponService.findByMobileAndIsUseable(tdUser.getMobile());  //未使用的优惠券
//        	break;
//        case 2:
//        	coupanList =tdCouponService.findByMobileAndIsUsedTrue(tdUser.getMobile()); //已使用的优惠券
//        	break;
//        case 3:
//        	coupanList =tdCouponService.findByMobileAndExpireTimeBeforeAndIsDistributtedTrueAndIsUsedFalse(tdUser.getMobile()); //已过期的优惠券
//        	break;
//        default:
//        		coupanList =tdCouponService.findByMoblie(tdUser.getMobile()); //全部优惠券
//        }
        
        //取得剩余天数 zhangji
        Date today = new Date();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(today);
        
        for (TdCoupon cl:coupanList)
        {
        	Date deadline = cl.getExpireTime();
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(deadline);
            Long num = calendar2.getTimeInMillis() - calendar1.getTimeInMillis();
            Double hourLeft = new Double(num/(1000*60*60)).doubleValue();
            Long dateLeft = new Long(num/(1000*24*60*60)).longValue();
//            cl.setHourLeft(hourLeft);
//        	cl.setDateLeft(dateLeft);       
        }
        
      //猜你喜欢 zhangji
//        List<TdUserRecentVisit> lastVisitList = tdUserRecentVisitService.findByUsernameOrderByVisitCountDesc(username);
//        if (0 == lastVisitList.size())
//        {
//            List<TdProductCategory> topCategoryList = tdProductCategoryService
//                    .findByParentIdIsNullOrderBySortIdAsc();
//        	//没有浏览记录时，第一页
//            if (topCategoryList.size() > 0)
//            {
//		        map.addAttribute("reco_page0",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(0).getId(), 0, 4));
//		        map.addAttribute("categoryId0",topCategoryList.get(0).getId());
//		        map.addAttribute("categoryTitle0",topCategoryList.get(0).getTitle());
//            }
//	        //第二页
//            if (topCategoryList.size() > 1)
//            {
//		        map.addAttribute("reco_page1",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(1).getId(), 0, 4));
//		        map.addAttribute("categoryId1",topCategoryList.get(1).getId());
//		        map.addAttribute("categoryTitle1",topCategoryList.get(1).getTitle());
//            }
//	        //第三页
//            if (topCategoryList.size() > 2)
//            {
//		        map.addAttribute("reco_page2",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(2).getId(), 0, 4));
//		        map.addAttribute("categoryId2",topCategoryList.get(2).getId());
//		        map.addAttribute("categoryTitle2",topCategoryList.get(2).getTitle());
//            }
//        }
//        if (lastVisitList.size() > 0)
//	        	{
//		        	//猜你喜欢，第一页
//			        TdGoods good_0 =tdGoodsService.findOne(lastVisitList.get(0).getGoodsId());
//			        map.addAttribute("reco_page0",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_0.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId0",good_0.getCategoryId());
//			        map.addAttribute("categoryTitle0",good_0.getCategoryTitle());
//	        	}
//         if (lastVisitList.size() > 1)
//	        	{
//			        //猜你喜欢，第二页
//			        TdGoods good_1 =tdGoodsService.findOne(lastVisitList.get(1).getGoodsId());
//			        map.addAttribute("reco_page1",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_1.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId1",good_1.getCategoryId());
//			        map.addAttribute("categoryTitle1",good_1.getCategoryTitle());
//	        	}
//		  if (lastVisitList.size() > 2)
//	        	{
//				    //猜你喜欢，第三页
//			        TdGoods good_2 =tdGoodsService.findOne(lastVisitList.get(2).getGoodsId());
//			        map.addAttribute("reco_page2",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_2.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId2",good_2.getCategoryId());
//			        map.addAttribute("categoryTitle2",good_2.getCategoryTitle());
//	        	}
        
		map.addAttribute("listId", listId);  
        map.addAttribute("coupan_list", coupanList);
        
        //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_coupon_list";
    }
    
    /*
     * 删除优惠券记录
     * @zhangji
     */
    @RequestMapping(value = "/user/coupon/del")
    public String couponDel(HttpServletRequest req, Integer listId,Long id, Integer app,
                        ModelMap map){
        String username = (String) req.getSession().getAttribute("username");
        
        if (null == username)
        {
            return "redirect:/touch/login";
        }
        
        tdCommonService.setHeader(map, req);
        
         TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
        
        tdCouponService.delete(id);
        
        List<TdCoupon> coupanList = null;
        
//        switch(listId)
//        {
//        case 0:
//        	coupanList =tdCouponService.findByMoblie(tdUser.getMobile()); //全部优惠券
//        	break;
//        case 1:	
//        	coupanList =tdCouponService.findByMobileAndIsUseable(tdUser.getMobile());  //未使用的优惠券
//        	break;
//        case 2:
//        	coupanList =tdCouponService.findByMobileAndIsUsedTrue(tdUser.getMobile()); //已使用的优惠券
//        	break;
//        case 3:
//        	coupanList =tdCouponService.findByMobileAndExpireTimeBeforeAndIsDistributtedTrueAndIsUsedFalse(tdUser.getMobile()); //已过期的优惠券
//        	break;
//        default:
//        		coupanList =tdCouponService.findByMoblie(tdUser.getMobile()); //全部优惠券
//        }
        
        //取得剩余天数 zhangji
        Date today = new Date();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(today);
        
        for (TdCoupon cl:coupanList)
        {
        	Date deadline = cl.getExpireTime();
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(deadline);
            Long num = calendar2.getTimeInMillis() - calendar1.getTimeInMillis();
            Double hourLeft = new Double(num/(1000*60*60)).doubleValue();
            Long dateLeft = new Long(num/(1000*24*60*60)).longValue();
//            cl.setHourLeft(hourLeft);
//        	cl.setDateLeft(dateLeft);       
        }
        
        map.addAttribute("coupan_list", coupanList);
        
        //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_coupon_list_detail";
    }
        
    @RequestMapping(value = "/user/point/list")
    public String pointList(HttpServletRequest req, Integer page, String username, Integer app,
                        ModelMap map){
    	if (null == username) {
    		username = (String) req.getSession().getAttribute("username");
            if (null == username)
            {
                return "redirect:/touch/login";
            }
		}
        
        tdCommonService.setHeader(map, req);
        
        if (null == page)
        {
            page = 0;
        }
        
        TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
        
        Page<TdUserPoint> pointPage = null;
        
        pointPage = tdUserPointService.findByUsername(username, page, ClientConstant.pageSize);
        
        //猜你喜欢 zhangji
//        List<TdUserRecentVisit> lastVisitList = tdUserRecentVisitService.findByUsernameOrderByVisitCountDesc(username);
//        if (0 == lastVisitList.size())
//        {
//            List<TdProductCategory> topCategoryList = tdProductCategoryService
//                    .findByParentIdIsNullOrderBySortIdAsc();
//        	//没有浏览记录时，第一页
//            if (topCategoryList.size() > 0)
//            {
//		        map.addAttribute("reco_page0",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(0).getId(), 0, 4));
//		        map.addAttribute("categoryId0",topCategoryList.get(0).getId());
//		        map.addAttribute("categoryTitle0",topCategoryList.get(0).getTitle());
//            }
//	        //第二页
//            if (topCategoryList.size() > 1)
//            {
//		        map.addAttribute("reco_page1",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(1).getId(), 0, 4));
//		        map.addAttribute("categoryId1",topCategoryList.get(1).getId());
//		        map.addAttribute("categoryTitle1",topCategoryList.get(1).getTitle());
//            }
//	        //第三页
//            if (topCategoryList.size() > 2)
//            {
//		        map.addAttribute("reco_page2",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(2).getId(), 0, 4));
//		        map.addAttribute("categoryId2",topCategoryList.get(2).getId());
//		        map.addAttribute("categoryTitle2",topCategoryList.get(2).getTitle());
//            }
//        }
//        if (lastVisitList.size() > 0)
//	        	{
//		        	//猜你喜欢，第一页
//			        TdGoods good_0 =tdGoodsService.findOne(lastVisitList.get(0).getGoodsId());
//			        map.addAttribute("reco_page0",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_0.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId0",good_0.getCategoryId());
//			        map.addAttribute("categoryTitle0",good_0.getCategoryTitle());
//	        	}
//         if (lastVisitList.size() > 1)
//	        	{
//			        //猜你喜欢，第二页
//			        TdGoods good_1 =tdGoodsService.findOne(lastVisitList.get(1).getGoodsId());
//			        map.addAttribute("reco_page1",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_1.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId1",good_1.getCategoryId());
//			        map.addAttribute("categoryTitle1",good_1.getCategoryTitle());
//	        	}
//		  if (lastVisitList.size() > 2)
//	        	{
//				    //猜你喜欢，第三页
//			        TdGoods good_2 =tdGoodsService.findOne(lastVisitList.get(2).getGoodsId());
//			        map.addAttribute("reco_page2",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_2.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId2",good_2.getCategoryId());
//			        map.addAttribute("categoryTitle2",good_2.getCategoryTitle());
//	        	}
        
        map.addAttribute("point_page", pointPage);
        
      //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_point_list";
    }
    
    @RequestMapping(value = "/user/return/{orderId}")
    public String userReturn(HttpServletRequest req, 
                        @PathVariable Long orderId,
                        Long id, // 商品ID
                        String method, Integer app,
                        ModelMap map){
        String username = (String) req.getSession().getAttribute("username");
        
        if (null == username)
        {
            return "redirect:/touch/login";
        }
        
        tdCommonService.setHeader(map, req);
        
        //猜你喜欢 zhangji
//        List<TdUserRecentVisit> lastVisitList = tdUserRecentVisitService.findByUsernameOrderByVisitCountDesc(username);
//        if (0 == lastVisitList.size())
//        {
//            List<TdProductCategory> topCategoryList = tdProductCategoryService
//                    .findByParentIdIsNullOrderBySortIdAsc();
//        	//没有浏览记录时，第一页
//            if (topCategoryList.size() > 0)
//            {
//		        map.addAttribute("reco_page0",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(0).getId(), 0, 4));
//		        map.addAttribute("categoryId0",topCategoryList.get(0).getId());
//		        map.addAttribute("categoryTitle0",topCategoryList.get(0).getTitle());
//            }
//	        //第二页
//            if (topCategoryList.size() > 1)
//            {
//		        map.addAttribute("reco_page1",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(1).getId(), 0, 4));
//		        map.addAttribute("categoryId1",topCategoryList.get(1).getId());
//		        map.addAttribute("categoryTitle1",topCategoryList.get(1).getTitle());
//            }
//	        //第三页
//            if (topCategoryList.size() > 2)
//            {
//		        map.addAttribute("reco_page2",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(2).getId(), 0, 4));
//		        map.addAttribute("categoryId2",topCategoryList.get(2).getId());
//		        map.addAttribute("categoryTitle2",topCategoryList.get(2).getTitle());
//            }
//        }
//        if (lastVisitList.size() > 0)
//	        	{
//		        	//猜你喜欢，第一页
//			        TdGoods good_0 =tdGoodsService.findOne(lastVisitList.get(0).getGoodsId());
//			        map.addAttribute("reco_page0",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_0.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId0",good_0.getCategoryId());
//			        map.addAttribute("categoryTitle0",good_0.getCategoryTitle());
//	        	}
//         if (lastVisitList.size() > 1)
//	        	{
//			        //猜你喜欢，第二页
//			        TdGoods good_1 =tdGoodsService.findOne(lastVisitList.get(1).getGoodsId());
//			        map.addAttribute("reco_page1",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_1.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId1",good_1.getCategoryId());
//			        map.addAttribute("categoryTitle1",good_1.getCategoryTitle());
//	        	}
//		  if (lastVisitList.size() > 2)
//	        	{
//				    //猜你喜欢，第三页
//			        TdGoods good_2 =tdGoodsService.findOne(lastVisitList.get(2).getGoodsId());
//			        map.addAttribute("reco_page2",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_2.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId2",good_2.getCategoryId());
//			        map.addAttribute("categoryTitle2",good_2.getCategoryTitle());
//	        	}
        
        
        TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
        map.addAttribute("telephone", tdUser.getMobile());
        
      //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        if (null != orderId)
        {
            TdOrder tdOrder = tdOrderService.findOne(orderId);
            map.addAttribute("order", tdOrder);
            
            if (null != tdOrder && null != id)
            {
                for (TdOrderGoods tog : tdOrder.getOrderGoodsList())
                {
                    if (tog.getId().equals(id))
                    {
                        // 已经退换货
                        if (null != tog.getIsReturnApplied() && tog.getIsReturnApplied())
                        {
                            map.addAttribute("has_returned", true);
                            String orderNumber = tdOrder.getOrderNumber();
//                            TdUserReturn userReturn = tdUserReturnService.findByUsernameAndOrderNumberAndGoodsId(username, orderNumber, tog.getGoodsId());
//                            map.addAttribute("return", userReturn);
                        }

                        map.addAttribute("goodsId",tog.getGoodsId());
                        map.addAttribute("orderId",orderId);
                        map.addAttribute("order_goods", tog);

                        return "/touch/user_return_edit";
                    }
                }
            }
        }
        
        return "/touch/user_return_list";
    }
    
    @RequestMapping(value = "/user/return/save", method=RequestMethod.POST)
    public String returnSave(HttpServletRequest req, 
    					Boolean isReturn,
                        Long goodsId,
                        Long id, // 订单ID
                        String reason,
                        String telephone,
                        ModelMap map){
        String username = (String) req.getSession().getAttribute("username");
        
        if (null == username)
        {
            return "redirect:/touch/login";
        }
        
        tdCommonService.setHeader(map, req);
              
        if (null != id && null != goodsId)
        {
            TdOrder order = tdOrderService.findOne(id);
            
            if (null != order)
            {
                for (TdOrderGoods tog : order.getOrderGoodsList())
                {
                    if (goodsId.equals(tog.getGoodsId()))
                    {
                        TdUserReturn tdReturn = new TdUserReturn();
                        
                        tdReturn.setIsReturn(isReturn); 
                        
                        // 用户
                        tdReturn.setUsername(username);
                        tdReturn.setTelephone(telephone);;
                        
                        // 退货订单商品
                        tdReturn.setOrderNumber(order.getOrderNumber());
                        tdReturn.setGoodsId(goodsId);
                        tdReturn.setGoodsTitle(tog.getGoodsTitle());
                        tdReturn.setGoodsPrice(tog.getPrice());
                        tdReturn.setGoodsCoverImageUri(tog.getGoodsCoverImageUri());
                        
                        // 退货时间及原因
                        tdReturn.setReason(reason);
                        tdReturn.setReturnTime(new Date());
                        
                        tdReturn.setStatusId(0L);
                        tdReturn.setReturnNumber(1L);
                        
                        // 保存
                        tdUserReturnService.save(tdReturn);
                        
                        // 该商品已经退换货
                        tog.setIsReturnApplied(true);
                        tdOrderGoodsService.save(tog);
                        break;
                    }
                }
            }
        }
        
        return "redirect:/touch/user/return/list";
    }
    

    //zhangji
    @RequestMapping(value = "/user/comment/add", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> commentAdd(HttpServletRequest req,
            TdUserComment tdComment,String[] hid_photo_name_show360, Long orderId, Long ogId, String code,
            ModelMap map) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("code", 1);

        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            res.put("message", "请先登录！");
            return res;
        }
        

        if (null == tdComment.getGoodsId()) {
            res.put("message", "商品ID不能为空！");
            return res;
        }

        TdGoods goods = tdGoodsService.findOne(tdComment.getGoodsId());

        if (null == goods) {
            res.put("message", "评论的商品不存在！");
            return res;
        }

        //zhangji 图片uri
        String uris = parsePicUris(hid_photo_name_show360);

//        tdComment.setShowPictures(uris);
        
        tdComment.setCommentTime(new Date());
        tdComment.setGoodsCoverImageUri(goods.getCoverImageUri());
        tdComment.setGoodsTitle(goods.getTitle());
        tdComment.setIsReplied(false);
        tdComment.setNegativeNumber(0L);
        tdComment.setPositiveNumber(0L);
        tdComment.setUsername(username);
        
        tdComment = tdUserCommentService.save(tdComment);

        // 设置订单信息
        if (null != orderId) {
            TdOrder tdOrder = tdOrderService.findOne(orderId);

            if (null != tdOrder) {
                tdComment.setOrderNumber(tdOrder.getOrderNumber());
                
                List<TdOrderGoods> ogList = tdOrder.getOrderGoodsList();

                for (TdOrderGoods og : ogList) {
                    if (og.getId().equals(ogId)) {
                        og.setIsCommented(true);
                        og.setCommentId(tdComment.getId());
                        tdOrder = tdOrderService.save(tdOrder);
                        break;
                    }
                }

                // 判断订单是否完成
                boolean allIsCommented = true;
                for (TdOrderGoods og : tdOrder.getOrderGoodsList()) {
                    if (null == og.getIsCommented() || !og.getIsCommented()) {
                        allIsCommented = false;
                        break;
                    }
                }

                if (allIsCommented) {
                    tdOrder.setStatusId(6L);
                    tdOrder = tdOrderService.save(tdOrder);
                }
            }
        }

        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);

        if (null != user) {
            tdComment.setUserHeadUri(user.getHeadImageUri());
        }

        tdComment.setStatusId(0L);

        tdComment = tdUserCommentService.save(tdComment);

        if (null == goods.getTotalComments()) {
            goods.setTotalComments(1L);
        } else {
            goods.setTotalComments(goods.getTotalComments() + 1L);
        }

        res.put("code", 0);

        return res;
    }
    @RequestMapping(value = "/user/comment/sec")
    public String commentSec(HttpServletRequest req, Long commentId, Integer app,
            ModelMap map) {
    	
    	//判断是否为app链接
    	if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
    	
        return "/touch/comment_sec";
    }
    
    @RequestMapping(value = "/user/comment/edit")
    public String commentedit(Long goodsId, Long orderId, Integer app,
    		                  HttpServletRequest req, ModelMap map){
    	String username = (String) req.getSession().getAttribute("username");
        
        if (null == username)
        {
            return "redirect:/touch/login";
        }
        
        if (null == goodsId || null == orderId) {
        	return "/client/error_404";
		}
        tdCommonService.setHeader(map, req);
        
        TdOrderGoods tdOrderGoods = tdOrderGoodsService.findOne(goodsId);
        if (null == tdOrderGoods) {
			return "/client/error_404";
		}
        map.addAttribute("tdOrderGoods", tdOrderGoods);
        map.addAttribute("orderId", orderId);
        
        if (null != tdOrderGoods && null != tdOrderGoods.getCommentId()) {
            TdUserComment uc = tdUserCommentService
                    .findOne(tdOrderGoods.getCommentId());
            map.addAttribute("comment", uc);
        }
        
        //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_comment_edit";
    }
    
    @RequestMapping(value = "/user/comment/list")
    public String commentList(HttpServletRequest req, Integer page, Integer app,
            Integer statusId, // 0表示未评价, 1表示已评价
            String keywords,
            ModelMap map) {
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/touch/login";
        }

        tdCommonService.setHeader(map, req);

        if (null == page) {
            page = 0;
        }

        if (null == statusId) {
            statusId = 0;
        }

        TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);

        map.addAttribute("user", tdUser);

        if (null != tdUser) {
            if (0 == statusId) {
                // 查找该用户的未评价订单
            	if (null != keywords)
            	{
            		Page<TdOrder> orderPage = tdOrderService.findByUsernameAndSearch(username, keywords, page,   ClientConstant.pageSize);
            		   map.addAttribute("order_page", orderPage);
   	                if (null != orderPage) {
   	                    for (TdOrder tdOrder : orderPage.getContent()) {
   	                        if (null != tdOrder) {
   	                            for (TdOrderGoods og : tdOrder.getOrderGoodsList()) {
   	                                if (null != og && null != og.getCommentId()) {
   	                                    TdUserComment uc = tdUserCommentService
   	                                            .findOne(og.getCommentId());
   	                                    map.addAttribute("comment_"+tdOrder.getId()+"_"+og.getId(), uc);
   	                                }
   	                            }
   	                        }
   	                    }
   	                }
            	}  
            	else
            	{
	            	Page<TdOrder> orderPage = tdOrderService
	                        .findByUsernameAndStatusId(username, 5L, page,
	                                ClientConstant.pageSize);
	                map.addAttribute("order_page", orderPage);
	                if (null != orderPage) {
	                    for (TdOrder tdOrder : orderPage.getContent()) {
	                        if (null != tdOrder) {
	                            for (TdOrderGoods og : tdOrder.getOrderGoodsList()) {
	                                if (null != og && null != og.getCommentId()) {
	                                    TdUserComment uc = tdUserCommentService
	                                            .findOne(og.getCommentId());
	                                    map.addAttribute("comment_"+tdOrder.getId()+"_"+og.getId(), uc);
	                                }
	                            }
	                        }
	                    }
	                }
            	}
//                map.addAttribute("order_page", tdOrderService
//                        .findByUsernameAndStatusId(username, 5L, page,
//                                ClientConstant.pageSize));
            } else {
                // 查找该用户的已评价订单
                Page<TdOrder> orderPage = tdOrderService
                        .findByUsernameAndStatusId(username, 6L, page,
                                ClientConstant.pageSize);
                map.addAttribute("order_page", orderPage);

                if (null != orderPage) {
                    for (TdOrder tdOrder : orderPage.getContent()) {
                        if (null != tdOrder) {
                            for (TdOrderGoods og : tdOrder.getOrderGoodsList()) {
                                if (null != og && null != og.getCommentId()) {
                                    TdUserComment uc = tdUserCommentService
                                            .findOne(og.getCommentId());
                                    map.addAttribute("comment_"+tdOrder.getId()+"_"+og.getId(), uc);
                                }
                            }
                        }
                    }
                }
            }
        }

        map.addAttribute("statusId", statusId);

        //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_comment_list";
    }
    
    @RequestMapping(value = "/user/consult/add", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> consultAdd(HttpServletRequest req, 
                        //TdUserConsult tdConsult,
                        Long goodsId,String content,
                        String code,
                        ModelMap map){
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("code", 1);
        
        String username = (String) req.getSession().getAttribute("username");
        
        if (null == username)
        {
            res.put("message", "请先登录！");
            return res;
        }
        
//        if (null == tdConsult.getGoodsId())
//        {
//            res.put("message", "商品ID不能为空！");
//            return res;
//        }
        
//        TdGoods goods = tdGoodsService.findOne(tdConsult.getGoodsId());
        
        if (null == goodsId) {
        	res.put("message", "商品ID不能为空！");
            return res;
		}
        if (null == content) {
        	res.put("message", "请输入咨询内容！");
            return res;
		}
        
        TdGoods goods = tdGoodsService.findOne(goodsId);
        if (null == goods)
        {
            res.put("message", "咨询的商品不存在！");
            return res;
        }
        
//        String codeBack = (String) req.getSession().getAttribute("RANDOMVALIDATECODEKEY");
//        
//        if (!codeBack.equalsIgnoreCase(code))
//        {
//            res.put("message", "验证码不匹配！");
//            return res;
//        }
        TdUserConsult  tdConsult = new TdUserConsult();
        tdConsult.setGoodsId(goodsId);
        tdConsult.setContent(content);
        tdConsult.setConsultTime(new Date());
        tdConsult.setGoodsCoverImageUri(goods.getCoverImageUri());
        tdConsult.setGoodsTitle(goods.getTitle());
        tdConsult.setIsReplied(false);
        tdConsult.setStatusId(0L);
        tdConsult.setUsername(username);
        
        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
        if (null != user)
        {
            tdConsult.setUserHeadImageUri(user.getHeadImageUri());
        }
        
        tdUserConsultService.save(tdConsult);
        
        res.put("code", 0);
        
        return res;
    }
    
    @RequestMapping(value = "/user/consult/list")
    public String consultList(HttpServletRequest req, 
                        Integer page,
                        String keywords, Integer app,
                        ModelMap map){
        String username = (String) req.getSession().getAttribute("username");
        
        if (null == username)
        {
            return "redirect:/touch/login";
        }
        
        tdCommonService.setHeader(map, req);
        
        if (null == page)
        {
            page = 0;
        }
        
        TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
        
        Page<TdUserConsult> consultPage = null;
        
        if (null == keywords || keywords.isEmpty())
        {
            consultPage = tdUserConsultService.findByUsername(username, page, ClientConstant.pageSize);
        }
        else
        {
            consultPage = tdUserConsultService.findByUsernameAndSearch(username, keywords, page, ClientConstant.pageSize);
        }
        
        map.addAttribute("consult_page", consultPage);
        map.addAttribute("keywords", keywords);
        
      //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_consult_list";
    }
    
    /*
     * 投诉
     * @zhangji
     */
    @RequestMapping(value = "/user/complain/list")
    public String complainList(HttpServletRequest req, 
                        Integer page,
                        String keywords, Integer app,
                        ModelMap map){
        String username = (String) req.getSession().getAttribute("username");
        
        if (null == username)
        {
            return "redirect:/touch/login";
        }
        
        tdCommonService.setHeader(map, req);
        
        if (null == page)
        {
            page = 0;
        }
        
        TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
        
        Page<TdOrder> complainPage = null;

        if (null != keywords && !keywords.isEmpty())
        {
        	complainPage = tdOrderService.findByUsernameAndSearch(username, keywords, page, ClientConstant.pageSize);
        }
        else
        {
        	complainPage = tdOrderService.findByUsername(username, page, ClientConstant.pageSize);
        }
       
        //猜你喜欢 zhangji
//        List<TdUserRecentVisit> lastVisitList = tdUserRecentVisitService.findByUsernameOrderByVisitCountDesc(username);
//        if (0 == lastVisitList.size())
//        {
//            List<TdProductCategory> topCategoryList = tdProductCategoryService
//                    .findByParentIdIsNullOrderBySortIdAsc();
//        	//没有浏览记录时，第一页
//            if (topCategoryList.size() > 0)
//            {
//		        map.addAttribute("reco_page0",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(0).getId(), 0, 4));
//		        map.addAttribute("categoryId0",topCategoryList.get(0).getId());
//		        map.addAttribute("categoryTitle0",topCategoryList.get(0).getTitle());
//            }
//	        //第二页
//            if (topCategoryList.size() > 1)
//            {
//		        map.addAttribute("reco_page1",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(1).getId(), 0, 4));
//		        map.addAttribute("categoryId1",topCategoryList.get(1).getId());
//		        map.addAttribute("categoryTitle1",topCategoryList.get(1).getTitle());
//            }
//	        //第三页
//            if (topCategoryList.size() > 2)
//            {
//		        map.addAttribute("reco_page2",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(topCategoryList.get(2).getId(), 0, 4));
//		        map.addAttribute("categoryId2",topCategoryList.get(2).getId());
//		        map.addAttribute("categoryTitle2",topCategoryList.get(2).getTitle());
//            }
//        }
//        if (lastVisitList.size() > 0)
//	        	{
//		        	//猜你喜欢，第一页
//			        TdGoods good_0 =tdGoodsService.findOne(lastVisitList.get(0).getGoodsId());
//			        map.addAttribute("reco_page0",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_0.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId0",good_0.getCategoryId());
//			        map.addAttribute("categoryTitle0",good_0.getCategoryTitle());
//	        	}
//         if (lastVisitList.size() > 1)
//	        	{
//			        //猜你喜欢，第二页
//			        TdGoods good_1 =tdGoodsService.findOne(lastVisitList.get(1).getGoodsId());
//			        map.addAttribute("reco_page1",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_1.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId1",good_1.getCategoryId());
//			        map.addAttribute("categoryTitle1",good_1.getCategoryTitle());
//	        	}
//		  if (lastVisitList.size() > 2)
//	        	{
//				    //猜你喜欢，第三页
//			        TdGoods good_2 =tdGoodsService.findOne(lastVisitList.get(2).getGoodsId());
//			        map.addAttribute("reco_page2",tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderBySortIdAsc(good_2.getCategoryId(), 0, 4));
//			        map.addAttribute("categoryId2",good_2.getCategoryId());
//			        map.addAttribute("categoryTitle2",good_2.getCategoryTitle());
//	        	}
        
        map.addAttribute("complain_page", complainPage);
        map.addAttribute("keywords", keywords);
        
      //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_complain_list";
    }
//    @RequestMapping(value = "/user/complain/add", method=RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> complainAdd(HttpServletRequest req, 
//                        TdUserComplain tdComplain,
//                        String code,
//                        ModelMap map){
//        Map<String, Object> res = new HashMap<String, Object>();
//        res.put("code", 1);
//        
//        String username = (String) req.getSession().getAttribute("username");
//        
//        if (null == username)
//        {
//            res.put("message", "请先登录！");
//            return res;
//        }
//
//        if(null != tdUserComplainService.findByUsernameAndOrderId(username, tdComplain.getOrderId()))
//        {
//        	res.put("message", "该订单已提交投诉，请勿重复提交。");
//        	return res;
//        }
//        if (null == tdComplain.getOrderId())
//        {
//            res.put("message", "订单ID不能为空！");
//            return res;
//        }
//        
//        TdOrder order = tdOrderService.findOne(tdComplain.getOrderId());
//        
//        if (null == order)
//        {
//            res.put("message", "投诉的订单不存在！");
//            return res;
//        }
//        
//        tdComplain.setConsultTime(new Date());
//        tdComplain.setOrderId(order.getId());
//        tdComplain.setOrderNumber(order.getOrderNumber());
//        tdComplain.setIsReplied(false);
//        tdComplain.setStatusId(0L);
//        tdComplain.setUsername(username);
//        
//        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
//        if (null != user)
//        {
//        	tdComplain.setUserHeadImageUri(user.getHeadImageUri());
//        }
//        
//        tdUserComplainService.save(tdComplain);
//        
//        res.put("code", 0);
//        return res;
//    }
    
    @RequestMapping(value = "/user/address/ajax/add")
    @ResponseBody
    public Map<String, Object> addAddress(String receiverName,
                                    String prov,
                                    String city,
                                    String dist,
                                    String detail,
                                    String postcode,
                                    String mobile,
                                    HttpServletRequest req) {
        Map<String, Object> res = new HashMap<String, Object>();
        
        res.put("code", 1);
        
        String username = (String) req.getSession().getAttribute("username");
        
        if (null == username)
        {
            res.put("message", "请先登录");
            return res;
        }
        
        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
        
        if (null == user)
        {
            res.put("message", "该用户不存在");
            return res;
        }
        
        TdShippingAddress address = new TdShippingAddress();
        
        address.setReceiverName(receiverName);
        address.setProvince(prov);
        address.setCity(city);
        address.setDisctrict(dist);
        address.setDetailAddress(detail);
        address.setPostcode(postcode);
        address.setReceiverMobile(mobile);
        
        user.getShippingAddressList().add(address);
        
        tdShippingAddressService.save(address);
        
        tdUserService.save(user);
        
        res.put("code", 0);
        
        return res;
    }
    
    @RequestMapping(value = "/user/address/{method}")
    public String address(HttpServletRequest req, 
                        @PathVariable String method,
                        Long id,
                        TdShippingAddress tdShippingAddress, Integer app,
                        ModelMap map){
        String username = (String) req.getSession().getAttribute("username");
        
        if (null == username)
        {
            return "redirect:/touch/login";
        }
        
        tdCommonService.setHeader(map, req);
        
        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);

        map.addAttribute("user", user);
        
        if (null != user)
        {
            List<TdShippingAddress> addressList = user.getShippingAddressList();
            
            if (null != method && !method.isEmpty())
            {
                if (method.equalsIgnoreCase("update"))
                {
                    if (null != id)
                    {
                        //map.addAttribute("address", s)
                        for (TdShippingAddress add : addressList)
                        {
                            if (add.getId().equals(id))
                            {
                                map.addAttribute("address", add);
                            }
                        }
                    }
                }
                else if (method.equalsIgnoreCase("delete"))
                {
                    if (null != id)
                    {
                        for (TdShippingAddress add : addressList)
                        {
                            if (add.getId().equals(id))
                            {
                                addressList.remove(id);
                                user.setShippingAddressList(addressList);
                                tdShippingAddressService.delete(add);
                                return "redirect:/touch/user/address/list";
                            }
                        }
                    }
                }
                else if (method.equalsIgnoreCase("save"))
                {
                    // 修改
                    if (null != tdShippingAddress.getId())
                    {
                        tdShippingAddressService.save(tdShippingAddress);
                    }
                    // 新增
                    else
                    {
                        addressList.add(tdShippingAddressService.save(tdShippingAddress));
                        user.setShippingAddressList(addressList);
                        tdUserService.save(user);
                    }
                    
                    return "redirect:/touch/user/address/list";
                }
            }
            
            map.addAttribute("address_list", user.getShippingAddressList());
        }
        
      //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_address_list";
    }
    
    @RequestMapping(value = "/user/distributor/return")
    public String distributorReturnList(HttpServletRequest req, 
                        Integer page, Integer app,
                        ModelMap map){
        String username = (String) req.getSession().getAttribute("username");
        
        if (null == username)
        {
            return "redirect:/touch/login";
        }
        
        tdCommonService.setHeader(map, req);
        
        if (null == page)
        {
            page = 0;
        }
        
        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);

        map.addAttribute("user", user);
        map.addAttribute("reward_page", tdUserCashRewardService.findByUsernameOrderByIdDesc(username, page, ClientConstant.pageSize));
        
      //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_distributor_return";
    }
    
    @RequestMapping(value = "/user/distributor/lower")
    public String distributorLowerList(HttpServletRequest req, Integer app,
                        Integer page,
                        ModelMap map){
        
        String username = (String) req.getSession().getAttribute("username");
        
        if (null == username)
        {
            return "redirect:/touch/login";
        }
        
        tdCommonService.setHeader(map, req);
        
        if (null == page)
        {
            page = 0;
        }
        
        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);

        map.addAttribute("user", user);
//        map.addAttribute("lower_page", tdUserService.findByUpperUsernameAndIsEnabled(username, page, ClientConstant.pageSize));
        
      //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_distributor_lower";
    }
    
    @RequestMapping(value = "/user/distributor/bankcard")
    public String distributorLowerList(HttpServletRequest req, Integer app,
                        ModelMap map){
        String username = (String) req.getSession().getAttribute("username");
        
        if (null == username)
        {
            return "redirect:/touch/login";
        }
        
        tdCommonService.setHeader(map, req);
        
        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);

        map.addAttribute("user", user);
        
      //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_distributor_bankcard";
    }
    
    /**
     * 返现商品列表
     * 
     * @param req
     * @param page
     * @param map
     * @return
     */
    @RequestMapping(value = "/user/distributor/goods")
    public String distributorGoodsList(HttpServletRequest req, 
                        String keywords,
                        Integer page, Integer app,
                        ModelMap map){
        String username = (String) req.getSession().getAttribute("username");
        
        if (null == username)
        {
            return "redirect:/touch/login";
        }
        
        tdCommonService.setHeader(map, req);
        
        if (null == page)
        {
            page = 0;
        }
        
        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);

        map.addAttribute("user", user);
        
        if (null == keywords || keywords.isEmpty())
        {
            map.addAttribute("goods_page", tdGoodsService.findByReturnPriceNotZeroAndIsOnSaleTrue(page, ClientConstant.pageSize));
        }
        else
        {
            map.addAttribute("goods_page", tdGoodsService.findByReturnPriceNotZeroAndSearchAndIsOnSaleTrue(page, ClientConstant.pageSize, keywords));
        }
        
        //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_distributor_goods";
    }
    
    @RequestMapping(value = "/user/info", method=RequestMethod.GET)
    public String userInfo(HttpServletRequest req, String username, Integer app,
                        ModelMap map){
    	if (null == username) {
    		username = (String) req.getSession().getAttribute("username");
            if (null == username)
            {
                return "redirect:/touch/login";
            }
		}
        
        tdCommonService.setHeader(map, req);
        
        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", user);
        
        map.addAttribute("recommend_goods_page", tdGoodsService.findByIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(0, ClientConstant.pageSize));
        
      //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_info";
    }
    
    @RequestMapping(value = "/user/info", method=RequestMethod.POST)
    public String userInfo(HttpServletRequest req,
                        String realName,
                        String sex,
                        String email,
                        String mobile,
                        String province,
                        String city,
                        String disctrict,
                        ModelMap map){
        String username = (String) req.getSession().getAttribute("username");
        
        if (null == username)
        {
            return "redirect:/touch/login";
        }
        
        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
        
        if (null != email && null != mobile)
        {
            user.setRealName(realName);
            user.setSex(sex);
            user.setEmail(email);
            user.setMobile(mobile);
            user.setProvince(province);
            user.setCity(city);
            user.setDistrict(disctrict);
            user = tdUserService.save(user);
            
        }
        
        return "redirect:/touch/user/info";
    }
    
    @RequestMapping(value = "/user/password", method=RequestMethod.GET)
    public String userPassword(HttpServletRequest req, String username, Integer app,
                        ModelMap map){
    	if (null == username) {
    		username = (String) req.getSession().getAttribute("username");
            if (null == username)
            {
                return "redirect:/touch/login";
            }
		}
        
        tdCommonService.setHeader(map, req);
        
        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", user);
        
      //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        return "/touch/user_change_password";
    }
    
    @RequestMapping(value = "/user/password", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> userPassword(HttpServletRequest req, String oldPassword,
            String newPassword, ModelMap map) {
    	Map<String, Object> res = new HashMap<String, Object>();
        res.put("code", 1);
        
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
        	res.put("msg", "请先登录！");
            return res;
        }

        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);

        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
        }

        map.addAttribute("user", tdUserService.save(user));

        res.put("code", 0);
        return res;
    }
    
    
    @ModelAttribute
    public void getModel(@RequestParam(value = "addressId", required = false) Long addressId,
            Model model) {
        if (addressId != null) {
            model.addAttribute("tdShippingAddress", tdShippingAddressService.findOne(addressId));
        }
    }
    
    /**
     * 图片地址字符串整理，多张图片用,隔开
     * 
     * @param params
     * @return
     */
    private String parsePicUris(String[] uris) {
        if (null == uris || 0 == uris.length) {
            return null;
        }

        String res = "";

        for (String item : uris) {
            String uri = item.substring(item.indexOf("|") + 1,
                    item.indexOf("|", 2));

            if (null != uri) {
                res += uri;
                res += ",";
            }
        }

        return res;
    }
}