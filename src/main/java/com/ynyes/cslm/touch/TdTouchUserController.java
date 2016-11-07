package com.ynyes.cslm.touch;

import static org.apache.commons.lang3.StringUtils.leftPad;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

import com.cslm.payment.alipay.AlipayConfig;
import com.cslm.payment.alipay.PaymentChannelAlipay;
import com.ynyes.cslm.entity.TdCash;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdOrderGoods;
import com.ynyes.cslm.entity.TdShippingAddress;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.entity.TdUserCollect;
import com.ynyes.cslm.entity.TdUserComment;
import com.ynyes.cslm.entity.TdUserConsult;
import com.ynyes.cslm.entity.TdUserPoint;
import com.ynyes.cslm.entity.TdUserRecentVisit;
import com.ynyes.cslm.entity.TdUserReturn;
import com.ynyes.cslm.entity.TdUserSuggestion;
import com.ynyes.cslm.service.TdCashService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdOrderGoodsService;
import com.ynyes.cslm.service.TdOrderService;
import com.ynyes.cslm.service.TdPayRecordService;
import com.ynyes.cslm.service.TdShippingAddressService;
import com.ynyes.cslm.service.TdUserCashRewardService;
import com.ynyes.cslm.service.TdUserCollectService;
import com.ynyes.cslm.service.TdUserCommentService;
import com.ynyes.cslm.service.TdUserConsultService;
import com.ynyes.cslm.service.TdUserPointService;
import com.ynyes.cslm.service.TdUserRecentVisitService;
import com.ynyes.cslm.service.TdUserReturnService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.service.TdUserSuggestionService;
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
    private TdDistributorGoodsService tdDistributorGoodsService;
    
    @Autowired
    private TdUserSuggestionService tdUserSuggestionService;
    
    @Autowired
    private TdCashService tdCashService;
    
    @Autowired
    private TdDistributorService tdDistributorService;
    
    
    @Autowired
    private TdPayRecordService tdPayRecordService;
    
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
        
        Long total_unpayed = tdOrderService.countByUsernameAndTypeIdAndStatusId(username,2);
        if(null != total_unpayed && total_unpayed >0L)
        {
        	map.addAttribute("total_unpayed",total_unpayed);
        }
        Long total_undelivered = tdOrderService.countByUsernameAndTypeIdAndStatusId(username, 3);
        if(null != total_undelivered && total_undelivered>0L)
        {
        	map.addAttribute("total_undelivered",total_undelivered);
        }
        Long total_unreceived = tdOrderService.countByUsernameAndTypeIdAndStatusId(username,4);
        if(null != total_unpayed && total_unreceived>0L)
        {
        	map.addAttribute("total_unreceived",total_unreceived);
        }
        Long total_finished = tdOrderService.countByUsernameAndTypeIdAndStatusId(username,5);
        if(null != total_finished && total_finished>0L)
        {
        	map.addAttribute("total_finished",total_finished);
        }
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
		        
		res.put("message", "参数错误！！");
		return res;
    }
    
    @RequestMapping(value = "/user/order/list/{statusId}")
    public String orderList(@PathVariable Integer statusId, 
                        Integer page,
                        HttpServletRequest req, 
                       Integer app,
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
        
        if (null == statusId)
        {
            statusId = 0;
        }
        
       TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
        map.addAttribute("status_id", statusId);
         
        Page<TdOrder> orderPage = null;
        
        
        if (statusId.equals(0)) {
            
                orderPage = tdOrderService.findByUsername(username, page,
                        ClientConstant.pageSize);
        } else {
                orderPage = tdOrderService.findByUsernameAndStatusId(
                        username, statusId, page, ClientConstant.pageSize);
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
   
    
    
    @RequestMapping(value = "/user/order/list_more/{statusId}")
    public String orderListmore(@PathVariable Integer statusId, 
                        Integer page,
                        
                        HttpServletRequest req, Integer app,
                        ModelMap map){
    	String username = (String) req.getSession().getAttribute("username");
        if (null == username)
        {
            return "redirect:/touch/login";
        }

        if (null == page)
        {
            page = 0;
        }
                     
        if (null == statusId)
        {
            statusId = 0;
        }
        
        map.addAttribute("status_id", statusId);       
         
        Page<TdOrder> orderPage = null;
        
        
        if (statusId.equals(0)) {
		        orderPage = tdOrderService.findByUsername(username, page,
		                ClientConstant.pageSize);
		} else {
		        orderPage = tdOrderService.findByUsernameAndStatusId(
		                username, statusId, page, ClientConstant.pageSize);
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
     * @author  
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
    public String returnList(HttpServletRequest req, Integer page,
             ModelMap map) {
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/login";
        }

        tdCommonService.setHeader(map, req);

        if (null == page) {
            page = 0;
        }

        TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);

        map.addAttribute("user", tdUser);

        Page<TdUserReturn> returnPage = tdUserReturnService.findByUsername(username, page,
                    ClientConstant.pageSize);

        map.addAttribute("return_page", returnPage);

        return "/touch/user_return_list";
    }
    
    @RequestMapping(value = "/user/return/list_more")
    public String returnListMore(HttpServletRequest req, Integer page,
             ModelMap map) {
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/login";
        }

        tdCommonService.setHeader(map, req);

        if (null == page) {
            page = 0;
        }

        Page<TdUserReturn> returnPage = tdUserReturnService.findByUsername(username, page,
                    ClientConstant.pageSize);

        map.addAttribute("return_page", returnPage);

        return "/touch/user_return_more";
    }
    
    /**
     * 取消订单
     * @author  
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
        
        if (null != id )
        {
            TdOrder order = tdOrderService.findOne(id);
            
            if (null != order)
            {
                if (1L == order.getStatusId()||2L == order.getStatusId())
                {
	                    order.setStatusId(7L);
	                    // 保存
	                    tdOrderService.save(order);
                }
            }
        }
        
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
    
    @RequestMapping(value = "/user/collect/list/{type}")
    public String collectList(@PathVariable Integer type,
    					HttpServletRequest req, 
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
        
        
        if(null == type){
        	type = 1;
        }
        
        TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
        
        map.addAttribute("user", tdUser);
        map.addAttribute("type", type);
        
        map.addAttribute("collect_page", tdUserCollectService.findByUsername(username,type, page, ClientConstant.pageSize));
        
        //判断是否为app链接
        if (null == app) {
   			Integer isApp = (Integer) req.getSession().getAttribute("app");
   	        if (null != isApp) {
   	        	map.addAttribute("app", isApp);
   			}
		}else {
			map.addAttribute("app", app);
		}
        
        if(type==1)
        {
        	return "/touch/user_collect_list";
        }else{
        	return "/touch/user_collect_shop";
        }
    }
    
    @RequestMapping(value = "/user/collect/list/{type}/more")
    public String collectListMore(@PathVariable Integer type,
    					HttpServletRequest req, 
                        Integer page,
                        ModelMap map){
		String username = (String) req.getSession().getAttribute("username");
        if (null == username)
        {
            return "redirect:/touch/login";
        }
        
        if (null == page)
        {
            page = 0;
        }
        
        
        if(null == type){
        	type = 1;
        }
        
        map.addAttribute("type", type);
        
        map.addAttribute("collect_page", tdUserCollectService.findByUsername(username,type, page, ClientConstant.pageSize));
        
        if(type==1)
        {
        	return "/touch/user_collect_more";
        }else{
        	return "/touch/user_collect_shop";
        }
    }
    
    @RequestMapping(value="/user/collect/select")
    public String select(Long id,HttpServletRequest req,ModelMap map){
    	String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/touch/login";
        }
        
    	if(null != id){
    		TdUserCollect collect = tdUserCollectService.findOne(id);
    		
    		if(null != collect)
    		{
    			if(null == collect.getIsSelect() || !collect.getIsSelect())
    			{
    				collect.setIsSelect(true);
    			}else{
    				collect.setIsSelect(false);
    			}
    			tdUserCollectService.save(collect);
    		}
    	}
    	return "redirect:/touch/user/collect/list/1";
    }
    
    @RequestMapping(value="/user/collect/selectAll")
    public String selectAll(HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("username");
    	
    	if (null == username) {
            return "redirect:/touch/login";
        }
		
    	List<TdUserCollect> list = tdUserCollectService.findByUsername(username,1, 0, ClientConstant.pageSize).getContent();
		
		if(null != list && list.size() > 0)
		{
			for (TdUserCollect collect : list) {
				if(null == collect.getIsSelect() || !collect.getIsSelect())
				{
					collect.setIsSelect(true);
				}else{
					collect.setIsSelect(false);
				}
				tdUserCollectService.save(collect);
			}
		}
    	return "redirect:/touch/user/collect/list/1";
    }
    
    @RequestMapping(value="/user/collect/delAll")
    public String delAll(HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("username");
    	
    	if (null == username) {
            return "redirect:/touch/login";
        }
		
    	List<TdUserCollect> list = tdUserCollectService.findByUsername(username,1, 0, ClientConstant.pageSize).getContent();
		
		if(null != list && list.size() > 0)
		{
			for (TdUserCollect collect : list) {
				if(null != collect.getIsSelect() && collect.getIsSelect())
				{
					tdUserCollectService.delete(collect);;
				}
			}
		}
    	return "redirect:/touch/user/collect/list/1";
    }
    
    
    @RequestMapping(value = "/user/collect/del")
    public String collectDel(HttpServletRequest req, 
            Long id,
            ModelMap map){
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/touch/login";
        }
        
        if (null != id) {
            TdUserCollect collect = tdUserCollectService
                    .findByUsernameAndDistributorId(username, id,1);

            // 删除收藏
            if (null != collect) {
                tdUserCollectService.delete(collect);
                
                TdGoods goods = tdGoodsService.findOne(collect.getGoodsId());
                
                if (null != goods && null != goods.getTotalCollects())
                {
                    goods.setTotalCollects(goods.getTotalCollects() - 1L);
                    
                    tdGoodsService.save(goods);
                }
            }
        }
        
        return "redirect:/touch/user/collect/list/1";
    }
    
    @RequestMapping(value = "/user/collect/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> collectAdd(HttpServletRequest req, Long disgId,
            ModelMap map) {

        Map<String, Object> res = new HashMap<String, Object>();
        res.put("code", 1);

        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            res.put("message", "请先登录");
            return res;
        }
        if (null == disgId) {
        	res.put("message", "参数错误");
        	return res;
        }
        
        TdDistributorGoods distributorGoods = tdDistributorGoodsService.findOne(disgId);
        
        if (null == distributorGoods) {
        	res.put("message", "商品不存在");
        	return res;
        }
        
        res.put("code", 0);

        TdUserCollect collect = tdUserCollectService.findByUsernameAndDistributorId(username,disgId,1);
        // 没有收藏
        if (null == collect) {
            TdGoods goods = tdGoodsService.findOne(distributorGoods.getGoodsId());
            
            if (null == goods.getTotalCollects())
            {
                goods.setTotalCollects(0L);
            }
            
            goods.setTotalCollects(goods.getTotalCollects() + 1L);
            
            tdGoodsService.save(goods);

            collect = new TdUserCollect();

            collect.setUsername(username);
            collect.setDistributorId(distributorGoods.getId());
            collect.setGoodsId(goods.getId());
            collect.setGoodsCoverImageUri(distributorGoods.getCoverImageUri());
            collect.setGoodsTitle(distributorGoods.getGoodsTitle());
            collect.setGoodsSalePrice(distributorGoods.getGoodsPrice());
            collect.setCollectTime(new Date());
            collect.setType(1);

            tdUserCollectService.save(collect);

            return res;
        }else{
        	tdUserCollectService.delete(collect);
        	res.put("message", "");
        }


        return res;
    }
    
    @RequestMapping(value="/user/collect/shop")
    @ResponseBody
    public Map<String,Object> collectShop(Long disId,HttpServletRequest req)
    {
    	Map<String, Object> res = new HashMap<String, Object>();
        res.put("code", 1);

        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            res.put("message", "请先登录");
            return res;
        }
        
        res.put("code", 0);
        if (null == disId) {
            res.put("message", "参数错误");
            return res;
        }
        
        TdUserCollect collect = tdUserCollectService.findByUsernameAndDistributorId(username, disId, 2);
        if(null == collect){
        	collect = new TdUserCollect();
        	
        	TdDistributor distributor = tdDistributorService.findOne(disId);

        	if(null != distributor){
        		
        		collect.setUsername(username);
        		collect.setDistributorId(disId);
        		collect.setCollectTime(new Date());
        		collect.setGoodsCoverImageUri(distributor.getImageUri());
        		collect.setGoodsTitle(distributor.getTitle());
        		collect.setType(2);
        		
        		tdUserCollectService.save(collect);
        		res.put("message","收藏成功");
        	}
        	
        }else{
        	res.put("message", "您已收藏该店铺");
        }

        return res;
    }
    
    @RequestMapping(value = "/user/collect/delshop")
    public String collectDelShop(HttpServletRequest req, 
            Long id,
            ModelMap map){
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/touch/login";
        }
        
        if (null != id) {
            TdUserCollect collect = tdUserCollectService.findOne(id);

            // 删除收藏
            if (null != collect) {
                tdUserCollectService.delete(collect);
                
            }
        }
        
        return "redirect:/touch/user/collect/list/2";
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
        map.addAttribute("categoryId",categoryId); //商品分类id  
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
     * @ 
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
        	 tdUserRecentVisitService.delete(id);
         }
    	 return "redirect:/touch/user/recent/list";
    }
    
//    //listId 0-全部，1-未使用，2-已使用，3-已过期 
//    @RequestMapping(value = "/user/coupon/list/{listId}")
//    public String couponList(HttpServletRequest req, Integer page,@PathVariable Integer listId, String username, Integer app,
//                        ModelMap map){
//    	if (null == username) {
//    		username = (String) req.getSession().getAttribute("username");
//            if (null == username)
//            {
//                return "redirect:/touch/login";
//            }
//		}
//        
//        tdCommonService.setHeader(map, req);
//        
//        if (null == page)
//        {
//            page = 0;
//        }
//        
//         TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
//        
//        map.addAttribute("user", tdUser);
//        
//        List<TdCoupon> coupanList = null;
//        
//        
//        //取得剩余天数  
//        Date today = new Date();
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.setTime(today);
//        
//        for (TdCoupon cl:coupanList)
//        {
//        	Date deadline = cl.getExpireTime();
//            Calendar calendar2 = Calendar.getInstance();
//            calendar2.setTime(deadline);
//            Long num = calendar2.getTimeInMillis() - calendar1.getTimeInMillis();
//            Double hourLeft = new Double(num/(1000*60*60)).doubleValue();
//            Long dateLeft = new Long(num/(1000*24*60*60)).longValue();
////            cl.setHourLeft(hourLeft);
////        	cl.setDateLeft(dateLeft);       
//        }
//        
//        
//		map.addAttribute("listId", listId);  
//        map.addAttribute("coupan_list", coupanList);
//        
//        //判断是否为app链接
//        if (null == app) {
//   			Integer isApp = (Integer) req.getSession().getAttribute("app");
//   	        if (null != isApp) {
//   	        	map.addAttribute("app", isApp);
//   			}
//		}else {
//			map.addAttribute("app", app);
//		}
//        
//        return "/touch/user_coupon_list";
//    }
//    
//    /*
//     * 删除优惠券记录
//     * 
//     */
//    @RequestMapping(value = "/user/coupon/del")
//    public String couponDel(HttpServletRequest req, Integer listId,Long id, Integer app,
//                        ModelMap map){
//        String username = (String) req.getSession().getAttribute("username");
//        
//        if (null == username)
//        {
//            return "redirect:/touch/login";
//        }
//        
//        tdCommonService.setHeader(map, req);
//        
//         TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);
//        
//        map.addAttribute("user", tdUser);
//        
//        tdCouponService.delete(id);
//        
//        List<TdCoupon> coupanList = null;
//        
//        
//        //取得剩余天数 
//        Date today = new Date();
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.setTime(today);
//        
//        for (TdCoupon cl:coupanList)
//        {
//        	Date deadline = cl.getExpireTime();
//            Calendar calendar2 = Calendar.getInstance();
//            calendar2.setTime(deadline);
//            Long num = calendar2.getTimeInMillis() - calendar1.getTimeInMillis();
//            Double hourLeft = new Double(num/(1000*60*60)).doubleValue();
//            Long dateLeft = new Long(num/(1000*24*60*60)).longValue();
////            cl.setHourLeft(hourLeft);
////        	cl.setDateLeft(dateLeft);       
//        }
//        
//        map.addAttribute("coupan_list", coupanList);
//        
//        //判断是否为app链接
//        if (null == app) {
//   			Integer isApp = (Integer) req.getSession().getAttribute("app");
//   	        if (null != isApp) {
//   	        	map.addAttribute("app", isApp);
//   			}
//		}else {
//			map.addAttribute("app", app);
//		}
//        
//        return "/touch/user_coupon_list_detail";
//    }
        
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
            @PathVariable Long orderId, Long id, // 商品ID
            String method, ModelMap map) {
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/touch/login";
        }

        tdCommonService.setHeader(map, req);

        TdUser tdUser = tdUserService.findByUsernameAndIsEnabled(username);

        map.addAttribute("user", tdUser);

        if (null != orderId) {
            TdOrder tdOrder = tdOrderService.findOne(orderId);
            map.addAttribute("order", tdOrder);
//            if(null !=tdOrder.getTypeId() && tdOrder.getTypeId() ==0)
//            {
            	map.addAttribute("shop", tdDistributorService.findOne(tdOrder.getShopId()));
//            }

            if (null != tdOrder && null != id) {
                for (TdOrderGoods tog : tdOrder.getOrderGoodsList()) {
                    if (tog.getId().equals(id)) {
                        // 已经退换货
                        if (null != tog.getIsReturnApplied()
                                && tog.getIsReturnApplied()) {
                            map.addAttribute("has_returned", true);
                        }

                        map.addAttribute("order_goods", tog);

                        return "/touch/user_return_edit";
                    }
                }
            }
        }

        return "redirect:/touch/user/order?id="+orderId;
    }
    
    @RequestMapping(value = "/user/return/save", method = RequestMethod.POST)
    public String returnSave(HttpServletRequest req, Long goodsId, Long id, // 订单ID
            Long shopId,String shopTitle, String reason, String telephone, ModelMap map) {
        String username = (String) req.getSession().getAttribute("username");

        if (null == username) {
            return "redirect:/touch/login";
        }

        tdCommonService.setHeader(map, req);

        if (null != id && null != goodsId) {
            TdOrder order = tdOrderService.findOne(id);

            if (null != order) {
                for (TdOrderGoods tog : order.getOrderGoodsList()) {
                    if (goodsId.equals(tog.getGoodsId())) {
                    	if(null != tog.getIsReturnApplied() && tog.getIsReturnApplied() == false)
                    	{
                    		TdUserReturn tdReturn = new TdUserReturn();
                    		
                    		tdReturn.setIsReturn(true);
                    		
                    		// 用户
                    		tdReturn.setUsername(username);
                    		
                    		tdReturn.setTelephone(telephone);
                    		tdReturn.setShopId(shopId);
                    		tdReturn.setShopTitle(shopTitle);
                    		
                    		// 退货订单商品
                    		tdReturn.setOrderNumber(order.getOrderNumber());
                    		tdReturn.setGoodsId(goodsId);
                    		tdReturn.setGoodsTitle(tog.getGoodsTitle());
                    		tdReturn.setGoodsPrice(tog.getPrice());
                    		tdReturn.setGoodsCoverImageUri(tog
                    				.getGoodsCoverImageUri());
                    		
                    		// 退货时间及原因
                    		tdReturn.setReason(reason);
                    		tdReturn.setReturnTime(new Date());
                    		
                    		tdReturn.setType(1L);
                    		tdReturn.setStatusId(0L);
                    		tdReturn.setReturnNumber(tog.getQuantity());
                    		
                    		if(order.getTypeId() ==0L){
                            	tdReturn.setTurnType(1);
                            }else if(order.getTypeId() ==2L){
                            	tdReturn.setTurnType(2);
                            	tdReturn.setSupplyId(order.getProviderId());
                            }
                    		
                    		// 保存
                    		tdUserReturnService.save(tdReturn);
                    		
                    		// 该商品已经退换货
                    		tog.setIsReturnApplied(true);
                    		tdOrderGoodsService.save(tog);
                    	}
                        break;
                    }
                }
            }
        }

        return "redirect:/touch/user/return/list";
    }
    

    @RequestMapping(value = "/user/comment/add", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> commentAdd(HttpServletRequest req,
    		TdUserComment tdComment, Long orderId, Long ogId,
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

        TdDistributorGoods distributorGoods = tdDistributorGoodsService.findOne(tdComment.getGoodsId());
        
        if (null == distributorGoods) {
        	res.put("message", "评论的商品不存在！");
        	return res;
        }
        TdGoods goods = tdGoodsService.findOne(distributorGoods.getGoodsId());
        if (null == goods) {
        	res.put("message", "评论的商品不存在！");
        	return res;
        }


        tdComment.setCommentTime(new Date());
        tdComment.setGoodsCoverImageUri(goods.getCoverImageUri());
        tdComment.setGoodsTitle(goods.getTitle());
        tdComment.setIsReplied(false);
        tdComment.setNegativeNumber(0L);
        tdComment.setPositiveNumber(0L);
        tdComment.setUsername(username);
        
        tdComment = tdUserCommentService.save(tdComment);
        
        TdUser user = tdUserService.findByUsernameAndIsEnabled(username);

        if (null != orderId) {
            TdOrder tdOrder = tdOrderService.findOne(orderId);

            if (null != tdOrder) {
                tdComment.setOrderNumber(tdOrder.getOrderNumber());
                tdComment.setDiysiteId(tdOrder.getShopId());
                
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
                    if(tdOrder.getTypeId() ==0 || tdOrder.getTypeId() ==2){
                    	tdOrderService.addUserPoint(tdOrder,tdOrder.getUsername()); // 添加积分记录
                    	tdUserService.addTotalSpend(tdOrder.getUsername(), tdOrder.getTotalGoodsPrice()); // 增加累计使用金额
                    }
                }
            }
        }


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
        tdGoodsService.save(goods);

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
        	return "/touch/error_404";
		}
        tdCommonService.setHeader(map, req);
        
        TdOrderGoods tdOrderGoods = tdOrderGoodsService.findOne(goodsId);
        if (null == tdOrderGoods) {
			return "/touch/error_404";
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
                map.addAttribute("order_page", tdOrderService
                        .findByUsernameAndStatusId(username, 5L, page,
                                ClientConstant.pageSize));
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
                        Long id,String type,
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
        map.addAttribute("type", type);
        
        if (null != user)
        {
            List<TdShippingAddress> addressList = user.getShippingAddressList();
            
            if (null != method && !method.isEmpty())
            {
                if (method.equalsIgnoreCase("update"))
                {
                    if (null != id)
                    {
                        for (TdShippingAddress add : addressList)
                        {
                            if (add.getId().equals(id))
                            {
                                map.addAttribute("address", add);
                            }
                        }
                    }
                    return "/touch/user_address_edit";
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
                                
                                if(null != type && !"".equals(type))
                                {
                                	return "redirect:/touch/order/info";
                                }
                                return "redirect:/touch/user/address/list";
                            }
                        }
                    }
                }
                else if (method.equalsIgnoreCase("save"))
                {
                	if(null != addressList && addressList.size() > 0){
                		if(null != tdShippingAddress.getIsDefaultAddress() && tdShippingAddress.getIsDefaultAddress() ==true){
                			for (TdShippingAddress address : addressList) {
                				if(!address.getId().equals(tdShippingAddress.getId())){
                					address.setIsDefaultAddress(false);
                					tdShippingAddressService.save(address);}
                			}
                		}
                	}else{
                		tdShippingAddress.setIsDefaultAddress(true);
                	}
                	
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
                    if(null != type && !"".equals(type))
                    {
                    	return "redirect:/touch/order/info";
                    }
                    return "redirect:/touch/user/address/list";
                }else if(method.equalsIgnoreCase("default")){
                	if(null != id)
                	{
                		for (TdShippingAddress address : addressList) {
							if(address.getId().equals(id)){
								address.setIsDefaultAddress(true);
								tdShippingAddressService.save(address);
							}else{
								address.setIsDefaultAddress(false);
								tdShippingAddressService.save(address);
							}
						}
                	}
                	return "redirect:/touch/user/address/list";
                }
            }else if(method.equalsIgnoreCase("edit"))
            {
            	return "/touch/user_address_edit";
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
    
    /**
     * 账户管理
     * @author Max
     * 
     */
    @RequestMapping(value="/user/account/{type}")
    public String account(@PathVariable String type,HttpServletRequest req,ModelMap map ){
    	String username = (String)req.getSession().getAttribute("username");
    	if(null == username)
    	{
    		return "redirect:/touch/login";
    	}
    	
    	tdCommonService.setHeader(map, req);
    	
    	map.addAttribute("user", tdUserService.findByUsername(username));
    	if(null != type)
    	{
    		if("show".equalsIgnoreCase(type))
    		{
    			return "/touch/user_account_index";
    		}
    	}
    	
    	map.addAttribute("virtualPage", tdPayRecordService.findByUsername(username, 2L, 0, ClientConstant.pageSize));
    	
    	
    	return "/touch/user_account";
    }
    
    /**
     * 加载更多。。。
     * virtualPage 交易记录
     * point 积分
     * @return
     */
    @RequestMapping(value="/user/search/more/{type}")
    public String searchMore(@PathVariable String type,Integer page,
    		HttpServletRequest req,ModelMap map){
    	String username = (String) req.getSession().getAttribute("username");
    	
    	if(null == page){
    		page =0;
    	}
    	
    	if("virtualPage".equals(type)){
    		map.addAttribute("virtualPage", tdPayRecordService.findByUsername(username, 2L, page, ClientConstant.pageSize));
    		return "/touch/user_account__more";
    	}else if("point".equals(type)){
    		map.addAttribute("point_page", tdUserPointService.findByUsername(username, page,ClientConstant.pageSize));
    	}
    	return "/touch/user_point_more";
    }
    
    /**
     * 充值
     * 
     */
    @RequestMapping(value="/user/topup1")
    public String topup1(HttpServletRequest req,ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("username");
    	if(null == username)
    	{
    		return "redirect:/touch/login";
    	}
    	
    	tdCommonService.setHeader(map, req);
    	
    	map.addAttribute("user", tdUserService.findByUsername(username));
    	
    	return "/touch/user_top_one";
    }
    
    @RequestMapping(value="/user/topup2",method=RequestMethod.POST)
    public String topupTwo(Double price,Long payTypeId,
    		HttpServletRequest req,ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("username");
    	if(null == username)
    	{
    		return "redirect:/touch/login";
    	}
    	
    	tdCommonService.setHeader(map, req);
    	
    	TdUser user = tdUserService.findByUsername(username);
    	
    	Date current = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String curStr = sdf.format(current);
    	Random random = new Random();
    	
    	TdCash cash = new TdCash();
    	cash.setCashNumber("USE"+curStr+leftPad(Integer.toString(random.nextInt(999)), 3, "0"));
    	cash.setShopTitle(user.getRealName());
    	cash.setUsername(username);
    	cash.setCreateTime(new Date());
    	cash.setPrice(price); // 金额
    	cash.setShopType(4L); // 类型-会员
    	cash.setType(1L); // 类型-充值
    	cash.setStatus(1L); // 状态 提交
    	
    	cash = tdCashService.save(cash);
    	
    	req.setAttribute("orderNumber", cash.getCashNumber());
    	req.setAttribute("totalPrice",cash.getPrice().toString());
    	req.setAttribute("type", "m");
    	
    	PaymentChannelAlipay paymentChannelAlipay = new PaymentChannelAlipay();
        String payForm = paymentChannelAlipay.getPayFormData(req);
        map.addAttribute("charset", AlipayConfig.CHARSET);
    	
        map.addAttribute("payForm", payForm);
    	
        return "/touch/order_pay_form";
    	
    }
    
    @RequestMapping(value="/user/cash")
    public String cashReturn(String cashNumber,HttpServletRequest req,ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("username");
    	if (null == username) {
            return "redirect:/touch/login";
        }
    	
    	tdCommonService.setHeader(map, req);
    	
    	map.addAttribute("user",tdUserService.findByUsername(username));
    	if(null != cashNumber)
    	{
    		map.addAttribute("cash", tdCashService.findByCashNumber(cashNumber));
    	}
    	
    	return "/touch/user_top_end";
    }
    
    /**
     * 提现
     * @author Max
     */
    @RequestMapping(value="/user/draw1")
    public String withdraw(HttpServletRequest req,ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("username");
    	if(null == username)
    	{
    		return "redirect:/touch/login";
    	}
    	
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("user", tdUserService.findByUsername(username));
    	return "/touch/user_draw_one";
    	
    }
    
    @RequestMapping(value="/user/draw2",method=RequestMethod.POST)
    public String draw2(String card,Double price,
    			String bank,String name,
    			HttpServletRequest req,ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("username");
    	if(null == username)
    	{
    		return "redirect:/touch/login";
    	}
    	
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("user", tdUserService.findByUsername(username));
    	map.addAttribute("card", card);
    	map.addAttribute("price", price);
    	map.addAttribute("bank", bank);
    	map.addAttribute("name", name);
    	
    	return "/touch/user_draw_two";
    }
    
    @RequestMapping(value="/user/draw3",method = RequestMethod.POST)
    public String draw3(String card,Double price,
    		String bank,String name,
    		HttpServletRequest req,ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("username");
    	if(null == username)
    	{
    		return "redirect:/touch/login";
    	}
    	
    	tdCommonService.setHeader(map, req);
    	
    	TdUser user = tdUserService.findByUsername(username);
    	
    	if(null == user)
    	{
    		return "/touch/error_404";
    	}
    	
		Date current = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String curStr = sdf.format(current);
    	Random random = new Random();
    	
		
		TdCash cash = new TdCash();
		
		cash.setCard(card);
		cash.setBank(bank);
		cash.setName(name);
		cash.setPrice(price);
		cash.setCreateTime(new Date());
		cash.setCashNumber("USE"+curStr+leftPad(Integer.toString(random.nextInt(999)), 3, "0"));
		cash.setShopTitle(user.getRealName());
		cash.setUsername(username);
		cash.setShopType(4L);
		cash.setType(2L);
		cash.setStatus(1L);
		
		cash = tdCashService.save(cash);
    	
		// 新加银行卡信息记录
		user.setBankCardCode(card);
		user.setBankTitle(bank);
		user.setBankName(name);
		tdUserService.save(user);
		
    	map.addAttribute("cash", cash);
    	
    	return "/touch/user_draw_end";
    }
    
    /**
     * 跳转修改昵称、邮箱、手机、密码、支付密码
     * @author Max
     */
    @RequestMapping(value="/user/account/edit/{type}")
    public String accountEdit(@PathVariable String type,HttpServletRequest req,ModelMap map)
    {
    	String username = (String)req.getSession().getAttribute("username");
    	if(null == username)
    	{
    		return "redirect:/touch/login";
    	}
    	tdCommonService.setHeader(map, req);
    	
    	map.addAttribute("user", tdUserService.findByUsername(username));
    	map.addAttribute("type", type);
    	
    	return "/touch/user_account_edit";
    }
    
    /**
     * 修改昵称、邮箱、手机、密码、支付密码
     * @author Max
     */
    @RequestMapping(value="/user/account/save/{type}",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> accountSave(@PathVariable String type,
    									String realName,String nickname,
    									String email,String identity,String homeAddress,
    									String mobile,String password,
    									String newPwd,String payPassword,
    									String newPayPwd,HttpServletRequest req){
    	Map<String,Object> res = new HashMap<>();
    	res.put("code",0);
    	
    	String username = (String)req.getSession().getAttribute("username");
    	if(null == username)
    	{
    		res.put("msg", "登录超时，请再次登录");
    		return res;
    	}
    	
    	TdUser user = tdUserService.findByUsername(username);
    	if(null != user && null != type && !type.isEmpty())
    	{
    		if("email".equalsIgnoreCase(type)) { // 邮箱
    			user.setEmail(email);
    		}else if("mobile".equalsIgnoreCase(type)){ // 手机
    			user.setMobile(mobile);
    		}else if("password".equalsIgnoreCase(type)){ // 密码
    			if(null != password && user.getPassword().equalsIgnoreCase(password))
    			{
    				user.setPassword(newPwd);
    			}else{
    				res.put("msg", "原始密码错误");
    				return res;
    			}
    		}else if("payPassword".equalsIgnoreCase(type)){ // 支付密码
    			if(null == user.getPayPassword()) // 首次设置
    			{
    				user.setPayPassword(newPayPwd);
    			}else{
    				if(null != payPassword && payPassword.equalsIgnoreCase(user.getPayPassword())){
        				user.setPayPassword(newPayPwd);
        				user.setIsUpdatePay(true);
        			}else {
        				res.put("msg", "原密码错误");
        				return res;
        			}
    			} 
    		}else{
    			user.setRealName(realName);
    			user.setNickname(nickname);
    			user.setIdentity(identity);
    			user.setHomeAddress(homeAddress);
    		}
    		tdUserService.save(user);
    		res.put("msg", "修改成功");
    		res.put("code", 1);
    		return res;
    	}
    	
    	res.put("msg", "参数错误");
    	return res;
    }
    
    @RequestMapping(value="/user/suggestion/list")
    public String suggestionList(HttpServletRequest req,ModelMap map){
	    
	     tdCommonService.setHeader(map, req);
	    
	     return "/touch/user_suggestion";
    }
    
    @RequestMapping(value = "/suggestion/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> suggestionAdd(HttpServletRequest req,
            String content, String code, ModelMap map) {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("code", 1);
        
        String username = (String)req.getSession().getAttribute("username");
        
        TdUser user = tdUserService.findByUsername(username);
        
        TdUserSuggestion tdSuggestion = new TdUserSuggestion();
        if(null != user)
        {
        	tdSuggestion.setContent(content);
        	tdSuggestion.setTime(new Date());
        	tdSuggestion.setName(user.getUsername());
        	tdSuggestion.setMail(user.getEmail());
        	tdSuggestion.setMobile(user.getMobile());
        }else{
        	tdSuggestion.setContent(content);
        	tdSuggestion.setTime(new Date());
        	tdSuggestion.setName("匿名");
        	tdSuggestion.setMail("");
        	tdSuggestion.setMobile("");
        }
        
        tdSuggestion.setStatus(1);
        tdUserSuggestionService.save(tdSuggestion);
        res.put("code", 0);
        res.put("msg", "提交成功");
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