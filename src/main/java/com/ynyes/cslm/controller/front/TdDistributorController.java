package com.ynyes.cslm.controller.front;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdOrderGoods;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdOrderService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.util.SiteMagConstant;


@Controller
@RequestMapping(value="/distributor")
public class TdDistributorController {
	
	@Autowired
	TdOrderService tdOrderService;
	
	@Autowired
	TdCommonService tdCommonService;
	
	@Autowired
	TdUserService tdUserService;
	
	@Autowired
	TdDistributorService TdDistributorService;
	
//	@RequestMapping(value="/index")
//	public String distributroindex(HttpServletRequest req, ModelMap map)
//	{
//		String username = (String) req.getSession().getAttribute("diysiteUsername");
//		if (null == username) {
//            return "redirect:/login";
//        }
//        
//        tdCommonService.setHeader(map, req);
//        
//        TdDistributor distributor = TdDistributorService.findbyUsername(username);
//        
//        if(null == distributor){
//        	return "/client/error_404";
//        }
//        
//        
//		
//        
//		return "";
//	}
	
	
	@RequestMapping(value = "/order/rebateincome")
	public String rebateincome(Integer page,
	                        Integer timeId, 
	                        HttpServletRequest req,
	                        ModelMap map) {
		String username = (String) req.getSession().getAttribute("diysiteUsername");
		if (null == username) {
            return "redirect:/login";
        }
        
        tdCommonService.setHeader(map, req);

        if (null == page) {
            page = 0;
        }

        if (null == timeId) {
            timeId = 0;
        }

        TdDistributor TdDistributor = TdDistributorService.findbyUsername(username);
        Double rebates = new Double(0.00);
        
        List<TdUser> tdUserlist = tdUserService.findByUpperDiySiteIdAndIsEnabled(TdDistributor.getId());
        List<String> tdUsers = new ArrayList<>();
        for(int i = 0; i < tdUserlist.size(); i++){
        	tdUsers.add(tdUserlist.get(i).getUsername());
        }
        if (timeId.equals(0)) {
        	if (null != tdUsers) {
        		List<TdOrder> list = tdOrderService.findByUsernameIn(tdUsers);
            	rebates = countrebates(list);
            	map.addAttribute("order_page", tdOrderService.findByUsernameIn(tdUsers, page, SiteMagConstant.pageSize));
			}     	
		}else if (timeId.equals(1)) {
			Date cur = new Date(); 
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
          //  calendar.add(Calendar.MONTH, -1);// 月份减一
          //  calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date time = calendar.getTime();
            time.setHours(0);
            time.setMinutes(0);
            
            if (null != tdUsers) {
        		List<TdOrder> list = tdOrderService.findByUsernameInAndOrderTimeAfter(tdUsers, time);
            	rebates = countrebates(list);
            	map.addAttribute("order_page", tdOrderService.findByUsernameInAndOrderTimeAfter(tdUsers, time, page, SiteMagConstant.pageSize));
			}   
		}else if (timeId.equals(2)) {
			Date cur = new Date(); 
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
          //  calendar.add(Calendar.MONTH, -1);// 月份减一
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            Date time = calendar.getTime();
            time.setHours(0);
            time.setMinutes(0);
            if (null != tdUsers) {
        		List<TdOrder> list = tdOrderService.findByUsernameInAndOrderTimeAfter(tdUsers, time);
            	rebates = countrebates(list);
            	map.addAttribute("order_page", tdOrderService.findByUsernameInAndOrderTimeAfter(tdUsers, time, page, SiteMagConstant.pageSize));
			}   
		}else if (timeId.equals(3)) {
			Date cur = new Date(); 
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -1);// 月份减一
            //calendar.add(Calendar.DAY_OF_MONTH, -7);
            Date time = calendar.getTime();
            time.setHours(0);
            time.setMinutes(0);
            if (null != tdUsers) {
        		List<TdOrder> list = tdOrderService.findByUsernameInAndOrderTimeAfter(tdUsers, time);
            	rebates = countrebates(list);
            	map.addAttribute("order_page", tdOrderService.findByUsernameInAndOrderTimeAfter(tdUsers, time, page, SiteMagConstant.pageSize));
			}   
		}else if (timeId.equals(4)) {
			Date cur = new Date(); 
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -3);// 月份减一
            //calendar.add(Calendar.DAY_OF_MONTH, -7);
            Date time = calendar.getTime();
            time.setHours(0);
            time.setMinutes(0);
            if (null != tdUsers) {
        		List<TdOrder> list = tdOrderService.findByUsernameInAndOrderTimeAfter(tdUsers, time);
            	rebates = countrebates(list);
            	map.addAttribute("order_page", tdOrderService.findByUsernameInAndOrderTimeAfter(tdUsers, time, page, SiteMagConstant.pageSize));
			}   
		}else if (timeId.equals(6)) {
			Date cur = new Date(); 
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -6);// 月份减一
            //calendar.add(Calendar.DAY_OF_MONTH, -7);
            Date time = calendar.getTime();
            time.setHours(0);
            time.setMinutes(0);
            if (null != tdUsers) {
        		List<TdOrder> list = tdOrderService.findByUsernameInAndOrderTimeAfter(tdUsers, time);
            	rebates = countrebates(list);
            	map.addAttribute("order_page", tdOrderService.findByUsernameInAndOrderTimeAfter(tdUsers, time, page, SiteMagConstant.pageSize));
			}   
		}else if (timeId.equals(12)) {
			Date cur = new Date(); 
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -12);// 月份减一
            //calendar.add(Calendar.DAY_OF_MONTH, -7);
            Date time = calendar.getTime();
            time.setHours(0);
            time.setMinutes(0);
            if (null != tdUsers) {
        		List<TdOrder> list = tdOrderService.findByUsernameInAndOrderTimeAfter(tdUsers, time);
            	rebates = countrebates(list);
            	map.addAttribute("order_page", tdOrderService.findByUsernameInAndOrderTimeAfter(tdUsers, time, page, SiteMagConstant.pageSize));
			}   
		}
        map.addAttribute("time_id", timeId);
        map.addAttribute("rebates",rebates);
        map.addAttribute("page", page);
        
        return "/client/diysite_rebate_income";
	}
	/**
	 * @author lc
	 * @注释：计算返利总额
	 */
	public Double countrebates(List<TdOrder> list){
    	Double rebates = new Double(0.00);       
    	for (int i = 0; i < list.size(); i++) {
    		if (null != list.get(i).getRebate()) {
    			rebates += list.get(i).getRebate();
			}  		
    	}
    	return rebates;
    }
	/**
	 * @author lc
	 * @注释：订单收入
	 */
	@RequestMapping(value = "/order/orderincome")
	public String orderincome(Integer page,
	                        Integer timeId, 
	                        HttpServletRequest req,
	                        ModelMap map) {
		String username = (String) req.getSession().getAttribute("diysiteUsername");

        if (null == username) {
            return "redirect:/login";
        }
        
        tdCommonService.setHeader(map, req);

        if (null == page) {
            page = 0;
        }

        if (null == timeId) {
            timeId = 0;
        }
        
        
        Double sales = new Double(0.00);

        TdDistributor TdDistributor = TdDistributorService.findbyUsername(username);        
        
        if (timeId.equals(0)) {
        	List<TdOrder> list = tdOrderService.findAllVerifyBelongShopTitle(TdDistributor.getTitle());
        	sales = countsales(list);
        	map.addAttribute("order_page", tdOrderService.findAllVerifyBelongShopTitleOrderByIdDesc(TdDistributor.getTitle(), page, SiteMagConstant.pageSize));
		}else if (timeId.equals(1)) {
			Date cur = new Date(); 
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
          //  calendar.add(Calendar.MONTH, -1);// 月份减一
          //  calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date time = calendar.getTime();
            time.setHours(0);
            time.setMinutes(0);
            
            List<TdOrder> list = tdOrderService.findAllVerifyBelongShopTitleAndTimeAfter(TdDistributor.getTitle(), time);
            sales = countsales(list);
            map.addAttribute("order_page", tdOrderService.findAllVerifyBelongShopTitleTimeAfterOrderByIdDesc(TdDistributor.getTitle(), time, page, SiteMagConstant.pageSize));
            
		}else if (timeId.equals(2)) {
			Date cur = new Date(); 
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
          //  calendar.add(Calendar.MONTH, -1);// 月份减一
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            Date time = calendar.getTime();
            time.setHours(0);
            time.setMinutes(0);
            
            List<TdOrder> list = tdOrderService.findAllVerifyBelongShopTitleAndTimeAfter(TdDistributor.getTitle(), time);
            sales = countsales(list);
            map.addAttribute("order_page", tdOrderService.findAllVerifyBelongShopTitleTimeAfterOrderByIdDesc(TdDistributor.getTitle(), time, page, SiteMagConstant.pageSize));
		}else if (timeId.equals(3)) {
			Date cur = new Date(); 
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -1);// 月份减一
            //calendar.add(Calendar.DAY_OF_MONTH, -7);
            Date time = calendar.getTime();
            time.setHours(0);
            time.setMinutes(0);
            
            List<TdOrder> list = tdOrderService.findAllVerifyBelongShopTitleAndTimeAfter(TdDistributor.getTitle(), time);
            sales = countsales(list);
            map.addAttribute("order_page", tdOrderService.findAllVerifyBelongShopTitleTimeAfterOrderByIdDesc(TdDistributor.getTitle(), time, page, SiteMagConstant.pageSize));
		}else if (timeId.equals(4)) {
			Date cur = new Date(); 
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -3);// 月份减一
            //calendar.add(Calendar.DAY_OF_MONTH, -7);
            Date time = calendar.getTime();
            time.setHours(0);
            time.setMinutes(0);
            
            List<TdOrder> list = tdOrderService.findAllVerifyBelongShopTitleAndTimeAfter(TdDistributor.getTitle(), time);
            sales = countsales(list);
            map.addAttribute("order_page", tdOrderService.findAllVerifyBelongShopTitleTimeAfterOrderByIdDesc(TdDistributor.getTitle(), time, page, SiteMagConstant.pageSize));
		}else if (timeId.equals(6)) {
			Date cur = new Date(); 
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -6);// 月份减一
            //calendar.add(Calendar.DAY_OF_MONTH, -7);
            Date time = calendar.getTime();
            time.setHours(0);
            time.setMinutes(0);
            
            List<TdOrder> list = tdOrderService.findAllVerifyBelongShopTitleAndTimeAfter(TdDistributor.getTitle(), time);
            sales = countsales(list);
            map.addAttribute("order_page", tdOrderService.findAllVerifyBelongShopTitleTimeAfterOrderByIdDesc(TdDistributor.getTitle(), time, page, SiteMagConstant.pageSize));
		}else if (timeId.equals(12)) {
			Date cur = new Date(); 
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -12);// 月份减一
            //calendar.add(Calendar.DAY_OF_MONTH, -7);
            Date time = calendar.getTime();
            time.setHours(0);
            time.setMinutes(0);
            
            List<TdOrder> list = tdOrderService.findAllVerifyBelongShopTitleAndTimeAfter(TdDistributor.getTitle(), time);
            sales = countsales(list);
            map.addAttribute("order_page", tdOrderService.findAllVerifyBelongShopTitleTimeAfterOrderByIdDesc(TdDistributor.getTitle(), time, page, SiteMagConstant.pageSize));
		}
        map.addAttribute("time_id", timeId);
        map.addAttribute("sales",sales);
        map.addAttribute("page", page);
        
        return "/client/diysite_order_income";
	}
	/**
	 * @author lc
	 * @注释：计算总额和销售额
	 */
    public Double countprice(List<TdOrder> list){
    	Double price = new Double(0.00);       
    	for (int i = 0; i < list.size(); i++) {
    		price += list.get(i).getTotalPrice();
    	}
    	return price;
    }
    public Double countsales(List<TdOrder> list){
    	Double sales = new Double(0.00);
    	for(int i = 0; i < list.size(); i++){
    		if (list.get(i).getStatusId().equals(2L) || list.get(i).getStatusId().equals(7L)) {	
    			
			}
    		else{
    			sales += list.get(i).getTotalPrice();
    		}
    	}
    	return sales;
    }
	
	@RequestMapping(value="/order/param/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> paramEdit(String orderNumber, String password,
                        String type,
                        String data,
                        String name,
                        String address,
                        String postal,
                        String mobile,
                        String expressNumber,
                        Long deliverTypeId,
                        ModelMap map,
                        HttpServletRequest req){
        
        Map<String, Object> res = new HashMap<String, Object>();
        
        res.put("code", 1);
        
        String username = (String) req.getSession().getAttribute("diysiteUsername");
        if (null == username)
        {
            res.put("message", "请重新登录");
            return res;
        }
        
        if (null != orderNumber && !orderNumber.isEmpty() && null != type && !type.isEmpty() && null != password)
        {
            TdOrder order = tdOrderService.findByOrderNumber(orderNumber);
            TdUser tdUser = tdUserService.findByUsername(order.getUsername());
            // 修改备注
            if (type.equalsIgnoreCase("editMark"))
            {
                order.setRemarkInfo(data);
            }
            // 确认已服务
            else if (type.equalsIgnoreCase("orderService"))
            {
                if (order.getStatusId().equals(4L))
                {
                	if (null == tdUser.getUpperDiySiteId()) {
						tdUser.setUpperDiySiteId(order.getShopId());
						tdUserService.save(tdUser);
					}
                	if (order.getOrderNumber().substring(order.getOrderNumber().length() - 4).equals(password)) {
                		order.setStatusId(5L);
					}else{
						res.put("message", "消费密码错误!");
						return res;
					}
                    
                }
            }
          
            
            tdOrderService.save(order);
            
            res.put("code", 0);
            res.put("message", "修改成功!");
            return res;
        }
        
        res.put("message", "参数错误!");
        return res;
    }
//========================================================================================================================================================================================
//========================================================================================================================================================================================
	/**
   	 * @注释：通过地区获取同盟店列表
   	 */
     @RequestMapping(value="/getdiysites", method = RequestMethod.POST)
     @ResponseBody
     public Map<String, Object> getdiysites(String disctrict){
       Map<String, Object> res = new HashMap<String, Object>();         
       res.put("code", 1);
            
       if (null != disctrict) {
   			List<TdDistributor> TdDistributor = TdDistributorService.findBydisctrict(disctrict);
   			
   			res.put("tdDiySites", TdDistributor);
   			res.put("code", 0);
       }
            
       return res;
    }
	
//	@RequestMapping(value="/getaddress", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> getaddress(Long id){
//    	 Map<String, Object> res = new HashMap<String, Object>();         
//         res.put("code", 1);
//         
//         if (null != id) {
//        	 TdDistributor TdDistributor = TdDistributorService.findOne(id);
//			res.put("address", TdDistributor.getAddress());
//			res.put("code", 0);
//		}else{
//			res.put("address", " ");
//			res.put("code", 0);
//		}
//         
//         return res;
//    }
	
	
	@RequestMapping(value="/save", method = RequestMethod.GET)
	public String distributorSave(HttpServletRequest req,ModelMap map)
	{
		tdCommonService.setHeader(map, req);
		
		return "/client/distributor_save";
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)
	public String distributorSave(TdDistributor tdDistributor,HttpServletRequest req,ModelMap map)
	{
		tdCommonService.setHeader(map, req);
		TdDistributorService.save(tdDistributor);
		
		return "";
	}
	
	/**
	 * 验证登录名和虚拟账号
	 * 
	 * @param type
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/check/{type}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> validateForm(@PathVariable String type,String param)
	{
		 Map<String, String> res = new HashMap<String, String>();
		 res.put("status", "n");
		 
		 if(null == type)
		 {
			 res.put("info","参数错误");
			 return res;
		 }
		 //登录账号验证
		 if (type.equalsIgnoreCase("username"))
	        {
	        	if (null == param || param.isEmpty()) {
	                res.put("info", "用户名不能为空");
	                return res;
	            }
	        	
	        	TdUser user = tdUserService.findByUsername(param);
	        	
	        	if (null != user)
	        	{
	        		res.put("info", "该账号已经存在");
	                return res;
	        	}
	        }
		 //虚拟账号验证
		 if("virtualAccount".equalsIgnoreCase(type))
		 {
			 if (null == param || param.isEmpty()) {
	                res.put("info", "虚拟账户不能为空");
	                return res;
	            }
			 TdDistributor distributor = TdDistributorService.findByVirtualAccount(param);
			 if(null !=distributor){
				 res.put("info", "虚拟账号已被占用");
				 return res;
			 }
		 }
		 res.put("info", "通过信息验证！");
		 return res;
	}
	
	@RequestMapping(value="/sale", method=RequestMethod.GET)
	public String distributorSale(Integer page,HttpServletRequest req,ModelMap map)
	{
		String username=(String)req.getSession().getAttribute("username");
		
		tdCommonService.setHeader(map, req);
		
		if(null == username)
		{
			return "/client/login";
		}
		
		if(null == page)
		{
			page = 0;
		}
		
		TdDistributor distributor = TdDistributorService.findbyUsername(username);
		
		map.addAttribute("dist_order_page", tdOrderService.findByShopId(distributor.getId(), page, 5));
		
		return "/client/distributor_sale";
	}
	
	@RequestMapping(value="/change" , method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> changeDistributor(HttpServletRequest req,Long disId)
	{
		Map<String,Object> res = new HashMap<>();
		if(null ==disId)
		{
			res.put("msg", "请选择超市");
			return res;
		}
		
		TdDistributor distributor = TdDistributorService.findOne(disId);
		 if(null == distributor)
		 {
			 res.put("msg","选择的超市不存在");
			 return res;
		 }
		 
		req.getSession().setAttribute("DISTRIBUTOR_ID", distributor.getId());
		return res;
	}
	
	
	
	
	
}

