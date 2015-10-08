package com.ynyes.cslm.controller.front;

import static org.apache.commons.lang3.StringUtils.leftPad;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdCartGoods;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdOrderGoods;
import com.ynyes.cslm.entity.TdProviderGoods;
import com.ynyes.cslm.entity.TdSetting;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.entity.TdUserPoint;
import com.ynyes.cslm.service.TdCartGoodsService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdOrderGoodsService;
import com.ynyes.cslm.service.TdOrderService;
import com.ynyes.cslm.service.TdProviderGoodsService;
import com.ynyes.cslm.service.TdProviderService;
import com.ynyes.cslm.service.TdSettingService;
import com.ynyes.cslm.service.TdUserPointService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.util.ClientConstant;
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
	TdDistributorService tdDistributorService;
	
	@Autowired
	TdDistributorGoodsService tdDistributorGoodsService;
	
	@Autowired
    TdGoodsService tdGoodsService;
	
	@Autowired
	TdUserPointService tdUserPointService;
	
	@Autowired
	TdProviderGoodsService tdProviderGoodsService;
	
	@Autowired
	TdProviderService tdProviderService;
	
	@Autowired
	TdCartGoodsService tdCartGoodsService;
	
	@Autowired
	TdOrderGoodsService tdOrderGoodsService;
	
	@Autowired
	TdSettingService tdSettingService;
	
	@RequestMapping(value="/index")
	public String distributroindex(HttpServletRequest req, ModelMap map)
	{
		String username = (String) req.getSession().getAttribute("distributor");
		if (null == username) {
            return "redirect:/login";
        }
        
        tdCommonService.setHeader(map, req);
        
        TdDistributor distributor = tdDistributorService.findbyUsername(username);
        
        if(null == distributor){
        	return "/client/error_404";
        }
        
        map.addAttribute("distributor", distributor);
        
        map.addAttribute("dis_goodsIn_order_page",
        		tdOrderService.findByUsernameAndTypeIdOrderByIdDesc(distributor.getUsername(), 1, 0, 5));
        map.addAttribute("dis_goodsOut_order_page",
        		tdOrderService.findByShopIdAndTypeId(distributor.getId(), 0, 0, ClientConstant.pageSize));
        map.addAttribute("total_undelivered",
        		tdOrderService.countByUsernameAndTypeIdAndStatusId(distributor.getUsername(), 1, 1));
        map.addAttribute("total_unreceived",
        		tdOrderService.countByUsernameAndTypeIdAndStatusId(distributor.getUsername(), 1, 2));
        map.addAttribute("total_finished",
        		tdOrderService.countByUsernameAndTypeIdAndStatusId(distributor.getUsername(), 1, 3));
        
		return "/client/distributor_index";
	}
	
	
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

        TdDistributor TdDistributor = tdDistributorService.findbyUsername(username);
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

        TdDistributor TdDistributor = tdDistributorService.findbyUsername(username);        
        
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
	
//	@RequestMapping(value="/order/param/edit", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, Object> paramEdit(String orderNumber, String password,
//                        String type,
//                        String data,
//                        String name,
//                        String address,
//                        String postal,
//                        String mobile,
//                        String expressNumber,
//                        Long deliverTypeId,
//                        ModelMap map,
//                        HttpServletRequest req){
//        
//        Map<String, Object> res = new HashMap<String, Object>();
//        
//        res.put("code", 1);
//        
//        String username = (String) req.getSession().getAttribute("diysiteUsername");
//        if (null == username)
//        {
//            res.put("message", "请重新登录");
//            return res;
//        }
//        
//        if (null != orderNumber && !orderNumber.isEmpty() && null != type && !type.isEmpty() && null != password)
//        {
//            TdOrder order = tdOrderService.findByOrderNumber(orderNumber);
//            TdUser tdUser = tdUserService.findByUsername(order.getUsername());
//            // 修改备注
//            if (type.equalsIgnoreCase("editMark"))
//            {
//                order.setRemarkInfo(data);
//            }
//            // 确认已服务
//            else if (type.equalsIgnoreCase("orderService"))
//            {
//                if (order.getStatusId().equals(4L))
//                {
//                	if (null == tdUser.getUpperDiySiteId()) {
//						tdUser.setUpperDiySiteId(order.getShopId());
//						tdUserService.save(tdUser);
//					}
//                	if (order.getOrderNumber().substring(order.getOrderNumber().length() - 4).equals(password)) {
//                		order.setStatusId(5L);
//					}else{
//						res.put("message", "消费密码错误!");
//						return res;
//					}
//                    
//                }
//            }
//          
//            
//            tdOrderService.save(order);
//            
//            res.put("code", 0);
//            res.put("message", "修改成功!");
//            return res;
//        }
//        
//        res.put("message", "参数错误!");
//        return res;
//    }
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
   			List<TdDistributor> TdDistributor = tdDistributorService.findBydisctrict(disctrict);
   			
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
		tdDistributor.setVirtualMoney(new Double(0));
		tdDistributor.setIsEnable(false);
		tdDistributorService.save(tdDistributor);
		
		return "redirect:/";
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
			 TdDistributor distributor = tdDistributorService.findByVirtualAccount(param);
			 if(null !=distributor){
				 res.put("info", "虚拟账号已被占用");
				 return res;
			 }
		 }
		 res.put("info", "通过信息验证！");
		 res.put("status", "y");
		 return res;
	}
	
	/**
	 * 超市已卖出商品
	 * @param page
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/sale", method=RequestMethod.GET)
	public String distributorSale(Integer page,HttpServletRequest req,ModelMap map)
	{
		String username=(String)req.getSession().getAttribute("distributor");
		
		tdCommonService.setHeader(map, req);
		
		if(null == username)
		{
			return "redirect:/login";
		}
		
		if(null == page)
		{
			page = 0;
		}
		
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		
		map.addAttribute("dist_order_page", tdOrderService.findByShopId(distributor.getId(), page, 5));
		
		return "/client/distributor_sale";
	}
	
	/**
	 *	选择超市，超市编号存入session以便后面调用
	 * @param req
	 * @param disId
	 * @return
	 */
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
		
		TdDistributor distributor = tdDistributorService.findOne(disId);
		 if(null == distributor)
		 {
			 res.put("msg","选择的超市不存在");
			 return res;
		 }
		 
		req.getSession().setAttribute("DISTRIBUTOR_ID", distributor.getId());
		return res;
	}
	
	/**
	 * 超市中心查看出售中/仓库中商品
	 * 
	 * @param isSale
	 * @param page
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/goods/sale/{isSale}", method= RequestMethod.GET)
	public String disGoodsSale(@PathVariable Boolean isSale, Integer page,HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		tdCommonService.setHeader(map, req);
		
		if(null == page )
		{
			page = 0;
		}
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		
		map.addAttribute("dis_goods_page", tdDistributorService.findByIdAndIsOnSale(distributor.getId(), isSale, page, 7));

		map.addAttribute("isOnSale", isSale);
		map.addAttribute("page",page);
		
		return "/client/distributor_goods";
	}
	
	/**
	 * 超市中心商品上/下架
	 * 
	 * @param disId
	 * @param type
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/goods/onsale/{disId}")
	public String disGoodsIsOnSale(@PathVariable Long disId,Boolean type,Integer page,HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		
		if(null == disId)
		{
			return "/client/error_404";
		}
		
		if(null == page )
		{
			page = 0;
		}
		TdDistributorGoods distributorGoods = tdDistributorGoodsService.findOne(disId);
		
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		map.addAttribute("page",page);
		
		
		if(type)
		{
			distributorGoods.setIsOnSale(type);
			tdDistributorGoodsService.save(distributorGoods);
			map.addAttribute("dis_goods_page", tdDistributorService.findByIdAndIsOnSale(distributor.getId(), false, page, 7));
		}else{
			distributorGoods.setIsOnSale(type);
			tdDistributorGoodsService.save(distributorGoods);
			map.addAttribute("dis_goods_page", tdDistributorService.findByIdAndIsOnSale(distributor.getId(), true, page, 7));
		}
		
		return "/client/distributor_goods_list";
	}
	
	/**
	 * 超市中心删除商品
	 * 
	 * @param disId
	 * @param type
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/goods/delete/{disId}")
	public String disGoodsDelete(@PathVariable Long disId,Boolean type,Integer page, HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == disId)
		{
			return "/client/error_404";
		}
		if(null == page )
		{
			page = 0;
		}
		
		tdDistributorGoodsService.delete(disId);
		
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		
		map.addAttribute("dis_goods_page", tdDistributorService.findByIdAndIsOnSale(distributor.getId(), type, page, 7));
		map.addAttribute("page",page);
		
		return "/client/distributor_goods_list";
	}
	
	/**
	 * 上架/下架多个商品
	 * 
	 * @param type
	 * @param listId
	 * @param listChkId
	 * @param page
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/onsaleAll/{type}")
	public String onSaleAll(@PathVariable Boolean type,
			Long[] listId,
			Integer[] listChkId,
			Integer page,
			HttpServletRequest req,
			ModelMap map){
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == page )
		{
			page = 0;
		}
		
		if(type)
		{
			onSaleAll(false, listId, listChkId);
		}else{
			onSaleAll(true, listId, listChkId);
		}
		
		return "redirect:/distributor/goods/sale/"+type+"?page="+page;
	}
	
	/**
	 * 超市销售订单查询
	 * @author libiao
	 * 
	 * @param statusId
	 * @param statusid
	 * @param keywords
	 * @param page
	 * @param timeId
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/outOrder/list/{statusId}")
	public String outOrderList(@PathVariable Integer statusId,
			Integer statusid,
			String keywords,
			Integer page,
			Integer timeId,
			HttpServletRequest req,
			ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == page )
		{
			page = 0;
		}
		if(null == statusId)
		{
			statusId= 0;
		}
		if (null == timeId) {
            timeId = 0;
        }
		if(null != statusid){
        	statusId = statusid;
        }
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		map.addAttribute("distributor", distributor);
		map.addAttribute("status_id", statusId);
		map.addAttribute("time_id", timeId);
		
		tdCommonService.setHeader(map, req);
		
		Page<TdOrder> orderPage=null;
		if (timeId.equals(0)) {
            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndSearch(
                            distributor.getId(),0, keywords, page, ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeId(distributor.getId(),0, page,
                            ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndSearch(distributor.getId(),0,statusId, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndStatusId(
                            distributor.getId(),0,statusId, page, ClientConstant.pageSize);
                }
            }
        } else if (timeId.equals(1)) {
            Date cur = new Date();
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -1);// 月份减一
            Date time = calendar.getTime();

            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndTimeAfterAndSearch(distributor.getId(),
                                    0,time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndTimeAfter(
                            distributor.getId(),0,time, page, ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                    distributor.getId(),0, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndTimeAfter(distributor.getId(),
                                   0, statusId, time, page,
                                    ClientConstant.pageSize);
                }
            }
        } else if (timeId.equals(3)) {
            Date cur = new Date();
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -3);// 月份减一
            Date time = calendar.getTime();

            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndTimeAfterAndSearch(distributor.getId(),
                                   0, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndTimeAfter(
                            distributor.getId(),0, time, page, ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                    distributor.getId(),0, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndTimeAfter(distributor.getId(),
                                    0,statusId, time, page,
                                    ClientConstant.pageSize);
                }
            }
        } else if (timeId.equals(6)) {
            Date cur = new Date();
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -6);// 月份减一
            Date time = calendar.getTime();

            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndTimeAfterAndSearch(distributor.getId(),
                                    0,time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndTimeAfter(
                            distributor.getId(),0, time, page, ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                    distributor.getId(),0, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndTimeAfter(distributor.getId(),
                                   0, statusId, time, page,
                                    ClientConstant.pageSize);
                }
            }
        } else if (timeId.equals(12)) {
            Date cur = new Date();
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.YEAR, -1);// 减一
            Date time = calendar.getTime();

            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndTimeAfterAndSearch(distributor.getId(),
                                    0,time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndTimeAfter(
                            distributor.getId(),0, time, page, ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                    distributor.getId(),0, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndTimeAfter(distributor.getId(),
                                    0,statusId, time, page,
                                    ClientConstant.pageSize);
                }
            }
        }

        map.addAttribute("order_page", orderPage);
		return "/client/distributor_saleOrder_list";
	}
	
	
	
	/**
	 * 超市进货订单
	 * @author libiao
	 * 
	 * @param statusId
	 * @param statusid
	 * @param keywords
	 * @param page
	 * @param timeId
	 * @param req
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/inOrder/list/{statusId}")
	public String inOrderList(@PathVariable Integer statusId,
			Integer statusid,
			String keywords,
			Integer page,
			Integer timeId,
			HttpServletRequest req,
			ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == page )
		{
			page = 0;
		}
		if(null == statusId)
		{
			statusId= 0;
		}
		if (null == timeId) {
            timeId = 0;
        }
		if(null != statusid){
        	statusId = statusid;
        }
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		map.addAttribute("distributor", distributor);
		map.addAttribute("status_id", statusId);
		map.addAttribute("time_id", timeId);
		
		tdCommonService.setHeader(map, req);
		
		Page<TdOrder> orderPage=null;
		if (timeId.equals(0)) {
            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndSearch(
                            username,1, keywords, page, ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByUsernameAndTypeIdOrderByIdDesc(username,1, page,
                            ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByUsernameAndTypeIdAndStatusIdAndSearch(username,
                                    1,statusId, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndStatusId(
                            username,1, statusId, page, ClientConstant.pageSize);
                }
            }
        } else if (timeId.equals(1)) {
            Date cur = new Date();
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -1);// 月份减一
            Date time = calendar.getTime();

            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByUsernameAndTypeIdAndTimeAfterAndSearch(username,
                                    1,time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndTimeAfter(
                            username, 1,time, page, ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByUsernameAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                    username,1, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService
                            .findByUsernameAndTypeIdAndStatusIdAndTimeAfter(username,
                                   1, statusId, time, page,
                                    ClientConstant.pageSize);
                }
            }
        } else if (timeId.equals(3)) {
            Date cur = new Date();
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -3);// 月份减一
            Date time = calendar.getTime();

            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByUsernameAndTypeIdAndTimeAfterAndSearch(username,
                                   1, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndTimeAfter(
                            username,1, time, page, ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByUsernameAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                    username,1, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService
                            .findByUsernameAndTypeIdAndStatusIdAndTimeAfter(username,
                                    1,statusId, time, page,
                                    ClientConstant.pageSize);
                }
            }
        } else if (timeId.equals(6)) {
            Date cur = new Date();
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.MONTH, -6);// 月份减一
            Date time = calendar.getTime();

            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByUsernameAndTypeIdAndTimeAfterAndSearch(username,
                                    1,time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndTimeAfter(
                            username,1, time, page, ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByUsernameAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                    username,1, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService
                            .findByUsernameAndTypeIdAndStatusIdAndTimeAfter(username,
                                   1, statusId, time, page,
                                    ClientConstant.pageSize);
                }
            }
        } else if (timeId.equals(12)) {
            Date cur = new Date();
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(cur);// 设置当前日期
            calendar.add(Calendar.YEAR, -1);// 减一
            Date time = calendar.getTime();

            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByUsernameAndTypeIdAndTimeAfterAndSearch(username,
                                    1,time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndTimeAfter(
                            username,1, time, page, ClientConstant.pageSize);
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByUsernameAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                    username,1, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                } else {
                    orderPage = tdOrderService
                            .findByUsernameAndTypeIdAndStatusIdAndTimeAfter(username,
                                    statusId,1, time, page,
                                    ClientConstant.pageSize);
                }
            }
        }

        map.addAttribute("order_page", orderPage);
		
		return "/client/distributor_inOrder_list";
	}
	
	/**
	 * 超市销售订单详细
	 * @param onsale
	 * @param ids
	 * @param chkIds
	 */
	@RequestMapping(value="/order")
	public String orderdetial(Long id,HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == id)
		{
			return "/client/error_404";
		}
		tdCommonService.setHeader(map, req);
		
		map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
		
		map.addAttribute("order",tdOrderService.findOne(id));
		
		return "/client/distributor_order_detail";
	}
	/**
	 * 超市确认订单  确认发货   确认已收货  确认评价
	 * @author libiao
	 * @param orderNumber
	 * @param type
	 * @param map
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/order/param/edit",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> paramEdit(String orderNumber,
			String type,
			ModelMap map,
			HttpServletRequest req)
	{
		Map<String, Object> res =new HashMap<>();
		res.put("code",1);
		String username = (String)req.getSession().getAttribute("distributor");
		
		if (null == username)
        {
            res.put("message", "请重新登录");
            return res;
        }
		
		if (null != orderNumber && !orderNumber.isEmpty() && null != type && !type.isEmpty())
        {
			TdOrder order = tdOrderService.findByOrderNumber(orderNumber);
			// 确认订单
            if (type.equalsIgnoreCase("orderConfirm"))
            {
                if (order.getStatusId().equals(1L))
                {
                    order.setStatusId(2L);
                    order.setCheckTime(new Date());
                }
            }
            //确认付款
            else if(type.equalsIgnoreCase("orderPay"))
            {
            	TdUser tdUser = tdUserService.findByUsername(order.getUsername());
            	TdDistributor distributor = tdDistributorService.findOne(order.getShopId());
            	
            	List<TdOrderGoods> tdOrderGoodsList = order.getOrderGoodsList();
            	
            	 Long totalPoints = 0L;
                 Double totalCash = 0.0;
                 Double platformService = 0.0;
                 Double trainService = 0.0;
                
                 // 返利总额
                 if (null != tdOrderGoodsList) {
                     for (TdOrderGoods tog : tdOrderGoodsList) {
                         if (0 == tog.getGoodsSaleType()) // 正常销售
                         {
                             TdDistributorGoods disGoods = tdDistributorGoodsService.findByDistributorIdAndGoodsIdAndIsOnSale(distributor.getId(), tog.getGoodsId(), true);
                        	 TdGoods tdGoods = tdGoodsService.findOne(tog.getGoodsId());

                             if (null != disGoods && null != disGoods.getReturnPoints()) {
                                 totalPoints += disGoods.getReturnPoints(); // 赠送积分

//                                 if (null != tdGoods.getShopReturnRation()) {
//                                     totalCash += tdGoods.getCostPrice()
//                                             * tdGoods.getShopReturnRation();
//                                 }
                             }
                             if (null != disGoods && null != tdGoods.getPlatformServiceReturnRation()) {
                             	platformService += disGoods.getGoodsPrice() * tdGoods.getPlatformServiceReturnRation();
         					}
                             if (null != disGoods && null != tdGoods.getTrainServiceReturnRation()) {
                             	trainService += disGoods.getGoodsPrice() * tdGoods.getTrainServiceReturnRation(); 
         					}
                         }
                     }
                  // 用户返利
                     if (null != tdUser) {
                         TdUserPoint userPoint = new TdUserPoint();

                         userPoint.setDetail("购买商品赠送积分");
                         userPoint.setOrderNumber(order.getOrderNumber());
                         userPoint.setPoint(totalPoints);
                         userPoint.setPointTime(new Date());
                         userPoint.setTotalPoint(tdUser.getTotalPoints() + totalPoints);
                         userPoint.setUsername(tdUser.getUsername());

                         userPoint = tdUserPointService.save(userPoint);

                         tdUser.setTotalPoints(userPoint.getTotalPoint());

                         tdUserService.save(tdUser);
                     }
                     order.setRebate(order.getTotalGoodsPrice()-platformService);// 设置订单超市收益
                     order.setPlatformService(platformService);// 设置订单平台服务费
                     order.setTrainService(trainService);// 设置订单培训服务费
                     order = tdOrderService.save(order);
                  //超市入账
                   if(null != distributor)
                   {
                	   distributor.setVirtualMoney(distributor.getVirtualMoney()+order.getTotalGoodsPrice()-platformService);
                       tdDistributorService.save(distributor);
                   }
                 }
                 order.setStatusId(3L);
                 order.setPayTime(new Date());
            }
            // 确认发货
            else if (type.equalsIgnoreCase("orderPayLeft"))
            {
            	if(order.getStatusId().equals(3L))
            	{
            		order.setStatusId(4L);
            		order.setDeliveryTime(new Date());
            	}
            }
            // 确认收货
            else if(type.equalsIgnoreCase("orderService"))
            {
            	if(order.getStatusId().equals(4L))
            	{
            		order.setStatusId(5L);
            		order.setReceiveTime(new Date());
            	}
            }
            // 确认订单完成
            else if(type.equalsIgnoreCase("orderFinish"))
            {
            	if(order.getStatusId().equals(5L))
            	{
            		order.setStatusId(6L);
            		order.setFinishTime(new Date());
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
	
	@RequestMapping(value="/password")
	public String distributorPassword(HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		tdCommonService.setHeader(map, req);
		map.addAttribute("distributor",tdDistributorService.findbyUsername(username));
		return "/client/distributor_change_password";
	}
	
	@RequestMapping(value="/password", method = RequestMethod.POST)
	public String distributorPassword(String oldPassword,String newPassword,
			HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		tdCommonService.setHeader(map, req);
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		 if (distributor.getPassword().equals(oldPassword)) 
		 {
			 distributor.setPassword(newPassword);
		 }
		
		map.addAttribute("distributor",tdDistributorService.save(distributor));
		
		return "redirect:/distributor/password";
	}
	
	@RequestMapping(value="/goods/onsale")
	public String saleGoods(Integer page,HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		tdCommonService.setHeader(map, req);
		
		if(null == page)
		{
			page= 0;
		}
		map.addAttribute("goods_page",
				tdGoodsService.findByIsOnSaleTrueOrderBySortIdAsc(page, 10));
		return "/client/distributor_goods_onsale";
	}
	
	/**
	 * 从平台选择商品上架到超市
	 * @author libiao
	 * 
	 * @param goodsId
	 * @param goodsTitle
	 * @param goodsPrice
	 * @param leftNumber
	 * @param req
	 * @return
	 */
	
	@RequestMapping(value="/goodsOnsale", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> goodsOnsale(Long goodsId,
				String goodsTitle,
				Double goodsPrice,
				Long leftNumber,
				HttpServletRequest req)
	{
		Map<String,Object> res =new HashMap<>();
		String username =(String)req.getSession().getAttribute("distributor");
		if(null ==username)
		{
			res.put("msg", "请先登录！");
			return res;
		}
		if(null ==goodsId)
		{
			res.put("msg","选择的商品无效！");
			return res;
		}
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		TdGoods goods = tdGoodsService.findById(goodsId);
		TdDistributorGoods disGoods = tdDistributorGoodsService.findByDistributorIdAndGoodsId(distributor.getId(),goodsId);
		// 判断本超市是否已经存在该商品
		if(null == disGoods)
		{
			TdDistributorGoods distributorGoods = new TdDistributorGoods();
			distributorGoods.setGoodsId(goods.getId());
			distributorGoods.setGoodsTitle(goodsTitle);
			distributorGoods.setGoodsPrice(goodsPrice);
			distributorGoods.setBrandId(goods.getBrandId());
			distributorGoods.setBrandTitle(goods.getBrandTitle());
			distributorGoods.setCategoryId(goods.getCategoryId());
			distributorGoods.setCategoryIdTree(goods.getCategoryIdTree());
			distributorGoods.setCode(goods.getCode());
			distributorGoods.setCoverImageUri(goods.getCoverImageUri());
			distributorGoods.setGoodsMarketPrice(goods.getMarketPrice());
			distributorGoods.setIsDistribution(false);
//			distributorGoods.setGoodsParamList(goods.getParamList());
			distributorGoods.setReturnPoints(goods.getReturnPoints());
			distributorGoods.setParamValueCollect(goods.getParamValueCollect());
			distributorGoods.setIsOnSale(true);
			distributorGoods.setLeftNumber(leftNumber);
			
			distributor.getGoodsList().add(distributorGoods);
		}else{
			disGoods.setGoodsTitle(goodsTitle);
			disGoods.setLeftNumber(disGoods.getLeftNumber()+leftNumber);
			disGoods.setGoodsPrice(goodsPrice);
		}
		List<TdDistributorGoods> list = tdDistributorGoodsService.findByGoodsId(goodsId);
		// 判断是否有超市在售此商品
		if(list.size()==0)
		{
			goods.setIsOnSale(true);
			tdGoodsService.save(goods);
		}

		tdDistributorService.save(distributor);
		res.put("msg", "上架成功");
		return res;
	}
	
	@RequestMapping(value="/goods/list")
	public String inGoodslist(String keywords,Integer page,
			HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		tdCommonService.setHeader(map, req);
		if(null == page)
		{
			page=0;
		}
		
		map.addAttribute("keywords", keywords);
		map.addAttribute("page", page);
		map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
		map.addAttribute("cart_goods_list", tdCartGoodsService.findByUsername(username));
		
		if(null == keywords)
		{
			map.addAttribute("proGoods_page",tdProviderGoodsService.findAll(page, 10));
		}else{
			map.addAttribute("proGoods_page", tdProviderGoodsService.findByGoodsTitleOrSubGoodsTitleOrProviderTitle(keywords,page,10));
		}
		
		return "/client/distributor_ingoods";
	}
	
	@RequestMapping(value="/goods/addOne")
	public String addOne(Long pgId,Long quantity,HttpServletRequest req,ModelMap map)
	{
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == pgId)
		{
			return "/client/error_404";
		}
		if (null == quantity || quantity.compareTo(1L) < 0)
        {
            quantity = 1L;
        }
		TdProviderGoods providerGoods = tdProviderGoodsService.findOne(pgId);
		if(null != providerGoods){
			List<TdCartGoods> oldCartGoodsList = null;
            
            // 购物车是否已有该商品
            oldCartGoodsList = tdCartGoodsService
                            .findByGoodsIdAndUsername(pgId, username);
            if(null !=oldCartGoodsList && oldCartGoodsList.size() >0){
            	 long oldQuantity = oldCartGoodsList.get(0).getQuantity();
            	 if(oldQuantity > providerGoods.getLeftNumber())
            	 {
            		 oldCartGoodsList.get(0).setQuantity(oldQuantity + quantity);
            		 tdCartGoodsService.save(oldCartGoodsList.get(0));
            	 }
            }else{
            	TdCartGoods cartGoods = new TdCartGoods();
            	cartGoods.setIsLoggedIn(true);
            	cartGoods.setUsername(username);
            	cartGoods.setGoodsId(providerGoods.getGoodsId());
            	cartGoods.setGoodsCoverImageUri(providerGoods.getGoodsCoverImageUri());
            	cartGoods.setGoodsTitle(providerGoods.getGoodsTitle());
            	cartGoods.setProviderTite(providerGoods.getProviderTitle());
            	cartGoods.setProviderId(tdProviderGoodsService.findProviderId(providerGoods.getId()));
            	cartGoods.setIsSelected(true);
            	cartGoods.setPrice(providerGoods.getOutFactoryPrice());
            	cartGoods.setQuantity(quantity);
            	tdCartGoodsService.save(cartGoods);
            }
		}
		map.addAttribute("cart_goods_list", tdCartGoodsService.findByUsername(username));
		return "/client/distributor_ingoods_cartlist";
	}
	
	@RequestMapping(value = "/goods/toggleSelect", method = RequestMethod.POST)
    public String cartToggle(Long id, HttpServletRequest req, ModelMap map) {

        String username = (String) req.getSession().getAttribute("distributor");

        if (null == username) {
            username = req.getSession().getId();
        }

        List<TdCartGoods> cartGoodsList = tdCartGoodsService
                .findByUsername(username);

        if (null != id) {
            for (TdCartGoods cartGoods : cartGoodsList) {
                if (cartGoods.getId().equals(id)) {
                    if (null == cartGoods.getIsSelected() || false == cartGoods.getIsSelected())
                    {
                        cartGoods.setIsSelected(true);
                    }
                    else
                    {
                        cartGoods.setIsSelected(false);
                    }
                    cartGoods = tdCartGoodsService.save(cartGoods);
                    break;
                }
            }
        }

        map.addAttribute("cart_goods_list", tdCartGoodsService.findByUsername(username));

        return "/client/distributor_ingoods_cartlist";
    }
	
	
	@RequestMapping(value = "/goods/toggleAll", method = RequestMethod.POST)
    public String cartToggleAll(Integer sid, HttpServletRequest req,
            ModelMap map) {

        String username = (String) req.getSession().getAttribute("distributor");

        if (null == username) {
            username = req.getSession().getId();
        }

        List<TdCartGoods> cartGoodsList = tdCartGoodsService
                .findByUsername(username);

        if (null != sid) {
            if (sid.equals(0)) // 全选
            {
                for (TdCartGoods cartGoods : cartGoodsList) {
                    cartGoods.setIsSelected(true);
                }
            } else // 取消全选
            {
                for (TdCartGoods cartGoods : cartGoodsList) {
                    cartGoods.setIsSelected(false);
                }
            }
            tdCartGoodsService.save(cartGoodsList);
        }

        map.addAttribute("cart_goods_list", tdCartGoodsService.findByUsername(username));

        return "/client/distributor_ingoods_cartlist";
    }
	
	@RequestMapping(value = "/goods/numberAdd", method = RequestMethod.POST)
    public String cartNumberAdd(Long id, HttpServletRequest req, ModelMap map) {

        String username = (String) req.getSession().getAttribute("distributor");

        if (null == username) {
            username = req.getSession().getId();
        }

        if (null != id) {
            TdCartGoods cartGoods =tdCartGoodsService.findTopByGoodsIdAndUsername(id, username);
            
            TdProviderGoods providerGoods = tdProviderGoodsService.findByProviderTitleAndGoodsId(cartGoods.getProviderTite(), cartGoods.getGoodsId());
            
            
            if (cartGoods.getUsername().equalsIgnoreCase(username)) {
                long quantity = cartGoods.getQuantity();
                
                if(quantity < providerGoods.getLeftNumber()){
                	cartGoods.setQuantity(quantity + 1);
                }
                tdCartGoodsService.save(cartGoods);
            }
        }

        map.addAttribute("cart_goods_list",tdCartGoodsService.findByUsername(username));

        return "/client/distributor_ingoods_cartlist";
    }

    @RequestMapping(value = "/goods/numberMinus",method = RequestMethod.POST)
    public String cartNumberMinus(Long id, HttpServletRequest req, ModelMap map) {

        String username = (String) req.getSession().getAttribute("distributor");

        if (null == username) {
            username = req.getSession().getId();
        }

        if (null != id) {
//            TdCartGoods cartGoods = tdCartGoodsService.findOne(id);
        	TdCartGoods cartGoods =tdCartGoodsService.findTopByGoodsIdAndUsername(id, username);

            if (cartGoods.getUsername().equalsIgnoreCase(username)) {
                long quantity = cartGoods.getQuantity();

                quantity = quantity > 1 ? quantity - 1 : quantity;

                cartGoods.setQuantity(quantity);
                tdCartGoodsService.save(cartGoods);
            }
        }

        map.addAttribute("cart_goods_list",tdCartGoodsService.findByUsername(username));

        return "/client/distributor_ingoods_cartlist";
    }

    @RequestMapping(value = "/goods/del",method = RequestMethod.POST)
    public String cartDel(Long id, HttpServletRequest req, ModelMap map) {

        String username = (String) req.getSession().getAttribute("distributor");

        if (null == username) {
            username = req.getSession().getId();
        }

        if (null != id) {
            TdCartGoods cartGoods = tdCartGoodsService.findOne(id);

            if (cartGoods.getUsername().equalsIgnoreCase(username)) {
                tdCartGoodsService.delete(cartGoods);
            }
        }

        map.addAttribute("cart_goods_list",tdCartGoodsService.findByUsername(username));

        return "/client/distributor_ingoods_cartlist";
    }
    
    /**
     *  提交进货单
     *  	扣除超市相应余额
     * @param req
     * @param map
     * @return
     */
    @RequestMapping(value="/order/info")
    public String orderInfo(HttpServletRequest req,ModelMap map)
    {
    	String username = (String) req.getSession().getAttribute("distributor");

        if (null == username) {
            return "redirect:/login";
        }
       TdDistributor distributor = tdDistributorService.findbyUsername(username);
        
        List<TdCartGoods> cartSelectedGoodsList = tdCartGoodsService.findByUsernameAndIsSelectedTrue(username);
        Double totalPrice = 0.0; // 购物总额

        List<TdOrderGoods> orderGoodsList = new ArrayList<TdOrderGoods>();
        if(null != cartSelectedGoodsList)
        {
        	for (TdCartGoods cartGoods: cartSelectedGoodsList) 
        	{
				if(cartGoods.getIsSelected())
				{
					TdProviderGoods providerGoods = tdProviderGoodsService.findByProviderTitleAndGoodsId(cartGoods.getProviderTite(),cartGoods.getGoodsId());
					
					if(null == providerGoods || providerGoods.getIsOnSale()==false){
						continue;
					}
					TdOrderGoods orderGoods = new TdOrderGoods();
					// 商品信息
					orderGoods.setGoodsId(providerGoods.getGoodsId());
					orderGoods.setGoodsCoverImageUri(providerGoods.getGoodsCoverImageUri());
					orderGoods.setGoodsTitle(providerGoods.getGoodsTitle());
					orderGoods.setGoodsSubTitle(providerGoods.getSubGoodsTitle());
					
					// 是否退货
					orderGoods.setIsReturnApplied(false);
					
					// 销售方式
//					orderGoods.setGoodsSaleType(goodsSaleType);
					
					// 批发价
					orderGoods.setPrice(providerGoods.getOutFactoryPrice());
					
					// 数量
					long quantity = 0;
					quantity = Math.min(cartGoods.getQuantity(),providerGoods.getLeftNumber());
					orderGoods.setQuantity(quantity);
					
					// 商品总价
					totalPrice +=cartGoods.getPrice()*cartGoods.getQuantity();
					
					orderGoodsList.add(orderGoods);
					
					long leftNumber = providerGoods.getLeftNumber();
					if(leftNumber >= quantity){
						leftNumber= leftNumber-quantity;
					}
					providerGoods.setLeftNumber(leftNumber);
					tdProviderGoodsService.save(providerGoods);
					
				}
			}
        }
        
       
        if(null == orderGoodsList || orderGoodsList.size() <= 0){
        	return "/client/error_404";
        }
        
        TdOrder tdOrder = new TdOrder();
        
        Date current = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String curStr = sdf.format(current);
        Random random = new Random();


        // 基本信息
        tdOrder.setUsername(username);
        tdOrder.setOrderTime(current);

        // 订单号
        tdOrder.setOrderNumber("T" + curStr
                + leftPad(Integer.toString(random.nextInt(999)), 3, "0"));
        
        // 发货信息
        tdOrder.setShippingName(distributor.getTitle());
        tdOrder.setShippingPhone(distributor.getServiceTele());
        tdOrder.setShippingAddress(distributor.getProvince()
        					+distributor.getCity()
        					+distributor.getDisctrict()
        					+distributor.getAddress());
        // 待发货
        tdOrder.setStatusId(1L);
        // 总价
        tdOrder.setTotalPrice(totalPrice);
        
        // 订单类型-批发订单
        tdOrder.setTypeId(1L);
        
        // 订单商品
        tdOrder.setOrderGoodsList(orderGoodsList);
        tdOrder.setTotalGoodsPrice(totalPrice);
        
        // 保存商品信息
        tdOrderGoodsService.save(orderGoodsList);
        
        // 网站基本信息
        TdSetting setting = tdSettingService.findTopBy();
        // 扣除超市虚拟账户
        distributor.setVirtualMoney(distributor.getVirtualMoney()-tdOrder.getTotalPrice());//扣除超市虚拟账户金额
		tdDistributorService.save(distributor);
		
		//平台虚拟账户增加金额
		setting.setVirtualMoney(tdOrder.getTotalPrice());
		tdSettingService.save(setting);
      
		tdOrderService.save(tdOrder);
		
		// 删除已生成订单的购物车项
        tdCartGoodsService.delete(cartSelectedGoodsList);
        
    	return "redirect:/distributor/order?id="+tdOrder.getId();
    }
	
	
	
	
	
	
	public void onSaleAll(Boolean onsale,Long[] ids,Integer[] chkIds)
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
                TdDistributorGoods goods = tdDistributorGoodsService.findOne(id);
               	goods.setIsOnSale(onsale);
               	tdDistributorGoodsService.save(goods);
            }
        }
	}
	
	
	
}

