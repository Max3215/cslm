package com.ynyes.cslm.controller.front;

import static org.apache.commons.lang3.StringUtils.leftPad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdArticle;
import com.ynyes.cslm.entity.TdArticleCategory;
import com.ynyes.cslm.entity.TdCartGoods;
import com.ynyes.cslm.entity.TdDemand;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdOrderGoods;
import com.ynyes.cslm.entity.TdPayRecord;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdProviderGoods;
import com.ynyes.cslm.entity.TdSetting;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.entity.TdUserCollect;
import com.ynyes.cslm.entity.TdUserPoint;
import com.ynyes.cslm.entity.TdUserReturn;
import com.ynyes.cslm.service.TdArticleCategoryService;
import com.ynyes.cslm.service.TdArticleService;
import com.ynyes.cslm.service.TdCartGoodsService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDemandService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdOrderGoodsService;
import com.ynyes.cslm.service.TdOrderService;
import com.ynyes.cslm.service.TdPayRecordService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.service.TdProviderGoodsService;
import com.ynyes.cslm.service.TdProviderService;
import com.ynyes.cslm.service.TdSettingService;
import com.ynyes.cslm.service.TdUserCollectService;
import com.ynyes.cslm.service.TdUserPointService;
import com.ynyes.cslm.service.TdUserReturnService;
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
	
	@Autowired
	TdProductCategoryService tdProductCategoryService;
	
	@Autowired
	TdUserCollectService tdUserCollectService;
	
	@Autowired
	TdArticleCategoryService tdArticleCategoryService;
	
	@Autowired
	TdArticleService tdArticleService;
	
	@Autowired
	TdPayRecordService tdPayRecordService;
	
	@Autowired
	TdDemandService tdDemandService;
	
	@Autowired
	TdUserReturnService tdUserReturnService;
	
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
        		tdOrderService.findByUsernameAndTypeIdOrderByIdDesc(distributor.getUsername(), 0, 0, 5));
        map.addAttribute("dis_goodsOut_order_page",
        		tdOrderService.findByShopIdAndTypeId(distributor.getId(), 0, 0, ClientConstant.pageSize));
        map.addAttribute("total_unpayed",
        		tdOrderService.countByShopIdAndTypeIdAndStatusId(distributor.getId(), 0, 2));
        map.addAttribute("total_undelivered",
        		tdOrderService.countByShopIdAndTypeIdAndStatusId(distributor.getId(), 0, 3));
        map.addAttribute("total_unreceived",
        		tdOrderService.countByShopIdAndTypeIdAndStatusId(distributor.getId(), 0, 4));
        map.addAttribute("total_finished",
        		tdOrderService.countByShopIdAndTypeIdAndStatusId(distributor.getId(), 0, 6));
        	
        
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
	 * 
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
		
		map.addAttribute("dist_order_page", tdOrderService.findByShopIdAndTypeId(distributor.getId(),0L, page, 10));
		
		return "/client/distributor_sale";
	}
	
	/**
	 *	选择超市，超市编号存入session以便后面调用
	 *
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
		req.getSession().setAttribute("distributorTitle", distributor.getTitle());
		return res;
	}
	
	/**
	 * 超市中心查看出售中/仓库中商品
	 * 
	 */
	@RequestMapping(value="/goods/sale/{isSale}", method= RequestMethod.GET)
	public String disGoodsSale(@PathVariable Boolean isSale,Long categoryId,String keywords, Integer page,HttpServletRequest req,ModelMap map)
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

		map.addAttribute("isOnSale", isSale);
		map.addAttribute("page",page);
		map.addAttribute("keywords", keywords);
		map.addAttribute("categoryId", categoryId);
		
		List<Long> list = tdDistributorGoodsService.findByDistributorId(distributor.getId());
		List<TdProductCategory> category_list = new ArrayList<>();
		
		if(null != list)
		{
			for (int i = 0; i < list.size(); i++) {
				category_list.add(tdProductCategoryService.findOne(Long.parseLong(list.get(i)+"")));
			}
		}// 所有该批发商有的分类
		map.addAttribute("category_list",category_list);
		
//		map.addAttribute("category_list", tdProductCategoryService.findAll());
		
		if(null == categoryId)
		{
			if(null == keywords || "".equals(keywords))
			{
				map.addAttribute("dis_goods_page",
						tdDistributorService.findByIdAndIsOnSale(distributor.getId(), isSale, page, 10));
			}
			else
			{
				map.addAttribute("dis_goods_page",
						tdDistributorGoodsService.searchAndIsOnSale(keywords, isSale, page, 10));
			}
		}
		else
		{
			if(null == keywords)
			{
				map.addAttribute("dis_goods_page", 
						tdDistributorGoodsService.findByCategoryIdAndIsOnSale(categoryId, isSale, page, 10));
			}
			else
			{
				map.addAttribute("dis_goods_page", 
						tdDistributorGoodsService.searchAndCategoryIdAndIsOnSale(categoryId, keywords, isSale, page, 10));
			}
		}
		
		return "/client/distributor_goods";
	}
	
	/**
	 * 超市中心商品上/下架
	 * 
	 */
	@RequestMapping(value="/goods/onsale/{disId}")
	public String disGoodsIsOnSale(@PathVariable Long disId,
						Boolean type,Integer page,
						HttpServletRequest req,ModelMap map)
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
		map.addAttribute("page", page);
		map.addAttribute("type", type);
		
		if(type)
		{
			distributorGoods.setIsOnSale(type);
			tdDistributorGoodsService.save(distributorGoods);
			map.addAttribute("dis_goods_page", tdDistributorService.findByIdAndIsOnSale(distributor.getId(), false, page, 10));
		}else{
			distributorGoods.setIsOnSale(type);
			tdDistributorGoodsService.save(distributorGoods);
			map.addAttribute("dis_goods_page", tdDistributorService.findByIdAndIsOnSale(distributor.getId(), true, page,10));
		}
		
		return "/client/distributor_goods_list";
	}
	
	@RequestMapping(value="/goods/editOnSale",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> editOnSale(Long goodsId,Double goodsPrice,
							Long leftNumber,	
							Integer page,HttpServletRequest req)
	{
		Map<String,Object> res = new HashMap<>();
		res.put("code", 0);
		String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			res.put("msg", "请重新登录!");
			return res;
		}
		
		if(null == goodsId)
		{
			res.put("msg", "参数错误!");
			return res;
		}
		if(null == leftNumber || leftNumber <=0)
		{
			res.put("msg", "库存输入错误");
			return res;
		}
		
		if(null == page )
		{
			page = 0;
		}
		TdDistributorGoods distributorGoods = tdDistributorGoodsService.findOne(goodsId);
		
//		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		
		if(null != goodsPrice)
		{
			distributorGoods.setGoodsPrice(goodsPrice);
		}
		distributorGoods.setLeftNumber(leftNumber);
		distributorGoods.setIsOnSale(false);
		tdDistributorGoodsService.save(distributorGoods);
//		map.addAttribute("dis_goods_page", tdDistributorService.findByIdAndIsOnSale(distributor.getId(), false, page, 10));
		res.put("msg", "修改成功！");
		res.put("code", 1);
		return res;
	}
	
	/**
	 * 超市中心删除商品
	 * 
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
		map.addAttribute("dis_goods_page", tdDistributorService.findByIdAndIsOnSale(distributor.getId(), type, page, 10));
		map.addAttribute("page",page);
		
		return "/client/distributor_goods_list";
	}
	
	/**
	 * 上架/下架多个商品
	 * 
	 */
	@RequestMapping(value="/onsaleAll/{type}")
	public String onSaleAll(@PathVariable Boolean type,
			Long[] listId,
			Integer[] listChkId,
			Integer page,
			Long categoryId,String keywords,
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
		if(null == categoryId){
			return "redirect:/distributor/goods/sale/"+type+"?page="+page+"&keywords="+keywords;
		}else{
			return "redirect:/distributor/goods/sale/"+type+"?page="+page+"&categoryId="+categoryId+"&keywords="+keywords;
		}
	}
	
	/**
	 * 超市销售订单查询
	 * @author libiao
	 * 
	 */
	@RequestMapping(value="/outOrder/list/{statusId}")
	public String outOrderList(@PathVariable Integer statusId,
			Integer statusid,
			String keywords,
			Integer page,
			Integer timeId,
			Integer typeId,
			String eventTarget,
			HttpServletRequest req,
			HttpServletResponse resp,
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
		if(null == typeId){
			typeId=0;
		}
		String excelUrl=null;
		if(null != eventTarget)
		{
			if("excel".equalsIgnoreCase(eventTarget))
			{
				excelUrl=SiteMagConstant.backupPath;
			}
		}
		
		/**
		 * 导出表格
		 */
		// 创建一个webbook 对于一个Excel
		HSSFWorkbook wb = new HSSFWorkbook();
		// 在webbook中添加一个sheet,对应Excel文件中的sheet 
		HSSFSheet sheet = wb.createSheet("order"); 
		// 设置每个单元格宽度根据字多少自适应
		sheet.autoSizeColumn(1);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);
        // 创建单元格，并设置值表头 设置表头居中 
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  // 居中
        
        HSSFCell cell = row.createCell((short) 0);  
        cell.setCellValue("订单编号");  
        cell.setCellStyle(style); 
        
        cell = row.createCell((short) 1);  
        cell.setCellValue("会员账户");  
        cell.setCellStyle(style); 
        
        cell = row.createCell((short) 2);  
        cell.setCellValue("收件人");  
        cell.setCellStyle(style); 
        
        cell = row.createCell((short) 3);  
        cell.setCellValue("收件地址");  
        cell.setCellStyle(style);
        
        cell = row.createCell((short) 4);  
        cell.setCellValue("收件人号码");  
        cell.setCellStyle(style);
        
        cell = row.createCell((short) 5);  
        cell.setCellValue("订单总额");  
        cell.setCellStyle(style); 
        
        cell = row.createCell((short) 6);  
        cell.setCellValue("下单时间");  
        cell.setCellStyle(style); 
        
        cell = row.createCell((short) 7);  
        cell.setCellValue("订单状态");  
        cell.setCellStyle(style); 
        
		
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		map.addAttribute("distributor", distributor);
		map.addAttribute("status_id", statusId);
		map.addAttribute("time_id", timeId);
		map.addAttribute("typeId", typeId);
		
		tdCommonService.setHeader(map, req);
		
		Page<TdOrder> orderPage=null;
		if (timeId.equals(0)) {
            if (statusId.equals(0)) {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndSearch(
                            distributor.getId(),typeId, keywords, page, ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page =tdOrderService.findByShopIdAndTypeIdAndSearch(
                                distributor.getId(),typeId, keywords, page, ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeId(distributor.getId(),typeId, page,
                            ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page =tdOrderService.findByShopIdAndTypeId(distributor.getId(),typeId, page,
                                ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndSearch(distributor.getId(),typeId,statusId, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page =tdOrderService
                                .findByShopIdAndTypeIdAndStatusIdAndSearch(distributor.getId(),typeId,statusId, keywords, page,
                                        ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndStatusId(
                            distributor.getId(),typeId,statusId, page, ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page =tdOrderService.findByShopIdAndTypeIdAndStatusId(
                                distributor.getId(),typeId,statusId, page, ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
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
                                    typeId,time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page =tdOrderService
                                .findByShopIdAndTypeIdAndTimeAfterAndSearch(distributor.getId(),typeId,time, keywords, page,
                                        ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndTimeAfter(
                            distributor.getId(),typeId,time, page, ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page =tdOrderService.findByShopIdAndTypeIdAndTimeAfter(
                                distributor.getId(),typeId,time, page, ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                    distributor.getId(),typeId, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page =tdOrderService
                                .findByShopIdAndTypeIdAndStatusIdAndTimeAfterAndSearch( distributor.getId(),typeId, statusId, time, keywords, page,
                                        ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService
                            .findByShopIdAndTypeIdAndStatusIdAndTimeAfter(distributor.getId(),
                                   typeId, statusId, time, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page =tdOrderService .findByShopIdAndTypeIdAndStatusIdAndTimeAfter(distributor.getId(),typeId, statusId, time, page,
                                         ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
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
                    orderPage = tdOrderService .findByShopIdAndTypeIdAndTimeAfterAndSearch(distributor.getId(),
                                   typeId, time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page = tdOrderService .findByShopIdAndTypeIdAndTimeAfterAndSearch(distributor.getId(),
                                typeId, time, keywords, page,
                                ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndTimeAfter(
                            distributor.getId(),typeId, time, page, ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page = tdOrderService.findByShopIdAndTypeIdAndTimeAfter(
                                distributor.getId(),typeId, time, page, ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService .findByShopIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                    distributor.getId(),typeId, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page = tdOrderService .findByShopIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                distributor.getId(),typeId, statusId, time, keywords, page,
                                ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService .findByShopIdAndTypeIdAndStatusIdAndTimeAfter(distributor.getId(),
                                    typeId,statusId, time, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page = tdOrderService .findByShopIdAndTypeIdAndStatusIdAndTimeAfter(distributor.getId(),
                                typeId,statusId, time, page,
                                ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
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
                    orderPage = tdOrderService .findByShopIdAndTypeIdAndTimeAfterAndSearch(distributor.getId(),
                                    typeId,time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page = tdOrderService .findByShopIdAndTypeIdAndTimeAfterAndSearch(distributor.getId(),
                                typeId,time, keywords, page,
                                ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndTimeAfter(
                            distributor.getId(),typeId, time, page, ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page = tdOrderService.findByShopIdAndTypeIdAndTimeAfter(
                                distributor.getId(),typeId, time, page, ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                    distributor.getId(),typeId, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page = tdOrderService.findByShopIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                distributor.getId(),typeId, statusId, time, keywords, page,
                                ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndStatusIdAndTimeAfter(distributor.getId(),
                                   typeId, statusId, time, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page = tdOrderService.findByShopIdAndTypeIdAndStatusIdAndTimeAfter(distributor.getId(),
                                typeId, statusId, time, page,
                                ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
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
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndTimeAfterAndSearch(distributor.getId(),
                                    typeId,time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page = tdOrderService.findByShopIdAndTypeIdAndTimeAfterAndSearch(distributor.getId(),
                                typeId,time, keywords, page,
                                ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndTimeAfter(
                            distributor.getId(),typeId, time, page, ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page = tdOrderService.findByShopIdAndTypeIdAndTimeAfter(
                                distributor.getId(),typeId, time, page, ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService.findByShopIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                    distributor.getId(),typeId, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page = tdOrderService.findByShopIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                distributor.getId(),typeId, statusId, time, keywords, page,
                                ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService .findByShopIdAndTypeIdAndStatusIdAndTimeAfter(distributor.getId(),
                                    typeId,statusId, time, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_Page = tdOrderService .findByShopIdAndTypeIdAndStatusIdAndTimeAfter(distributor.getId(),
                                typeId,statusId, time, page,
                                ClientConstant.pageSize);
                    	if(ImportData(order_Page,row,cell,sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
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
	 */
	@RequestMapping(value="/inOrder/list/{statusId}")
	public String inOrderList(@PathVariable Integer statusId,
			Integer statusid,
			String keywords,
			Integer page,
			Integer timeId,
			String eventTarget,
			HttpServletRequest req,
			HttpServletResponse resp,
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
		
		String excelUrl=null;
		if(null != eventTarget)
		{
			if("excel".equalsIgnoreCase(eventTarget))
			{
				excelUrl=SiteMagConstant.backupPath;
			}
		}
		
		/**
		 * 导出表格
		 */
		// 创建一个webbook 对于一个Excel
		HSSFWorkbook wb = new HSSFWorkbook();
		// 在webbook中添加一个sheet,对应Excel文件中的sheet 
		HSSFSheet sheet = wb.createSheet("order"); 
		// 设置每个单元格宽度根据字多少自适应
		sheet.autoSizeColumn(1);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);
        // 创建单元格，并设置值表头 设置表头居中 
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  // 居中
        
        HSSFCell cell = row.createCell((short) 0);  
        cell.setCellValue("订单编号");  
        cell.setCellStyle(style); 
        
        cell = row.createCell((short) 1);  
        cell.setCellValue("超市");  
        cell.setCellStyle(style); 
        
        cell = row.createCell((short) 2);  
        cell.setCellValue("批发商");  
        cell.setCellStyle(style); 
        
        cell = row.createCell((short) 3);  
        cell.setCellValue("订单总额");  
        cell.setCellStyle(style); 
        
        cell = row.createCell((short) 4);  
        cell.setCellValue("下单时间");  
        cell.setCellStyle(style); 
        
        cell = row.createCell((short) 5);  
        cell.setCellValue("订单状态");  
        cell.setCellStyle(style); 
		
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
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByUsernameAndTypeIdAndSearch(username, 1, keywords, page, ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByUsernameAndTypeIdOrderByIdDesc(username,1, page,
                            ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByUsernameAndTypeIdOrderByIdDesc(username,1, page,
                                ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService .findByUsernameAndTypeIdAndStatusIdAndSearch(username,
                                    1,statusId, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService .findByUsernameAndTypeIdAndStatusIdAndSearch(username,
                                1,statusId, keywords, page,
                                ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndStatusId(
                            username,1, statusId, page, ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByUsernameAndTypeIdAndStatusId(
                                username,1, statusId, page, ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
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
                    orderPage = tdOrderService .findByUsernameAndTypeIdAndTimeAfterAndSearch(username,
                                    1,time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService .findByUsernameAndTypeIdAndTimeAfterAndSearch(username,
                                1,time, keywords, page,
                                ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndTimeAfter(
                            username, 1,time, page, ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByUsernameAndTypeIdAndTimeAfter(
                                username, 1,time, page, ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                    username,1, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByUsernameAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                username,1, statusId, time, keywords, page,
                                ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService .findByUsernameAndTypeIdAndStatusIdAndTimeAfter(username,
                                   1, statusId, time, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService .findByUsernameAndTypeIdAndStatusIdAndTimeAfter(username,
                                1, statusId, time, page,
                                ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
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
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndTimeAfterAndSearch(username,
                                   1, time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByUsernameAndTypeIdAndTimeAfterAndSearch(username,
                                1, time, keywords, page,
                                ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndTimeAfter(
                            username,1, time, page, ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByUsernameAndTypeIdAndTimeAfter(
                                username,1, time, page, ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                    username,1, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByUsernameAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                username,1, statusId, time, keywords, page,
                                ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService .findByUsernameAndTypeIdAndStatusIdAndTimeAfter(username,
                                    1,statusId, time, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService .findByUsernameAndTypeIdAndStatusIdAndTimeAfter(username,
                                1,statusId, time, page,
                                ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
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
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndTimeAfterAndSearch(username,
                                    1,time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByUsernameAndTypeIdAndTimeAfterAndSearch(username,
                                1,time, keywords, page,
                                ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndTimeAfter(
                            username,1, time, page, ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByUsernameAndTypeIdAndTimeAfter(
                                username,1, time, page, ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                    username,1, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByUsernameAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                username,1, statusId, time, keywords, page,
                                ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndStatusIdAndTimeAfter(username,
                                   1, statusId, time, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByUsernameAndTypeIdAndStatusIdAndTimeAfter(username,
                                1, statusId, time, page,
                                ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
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
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndTimeAfterAndSearch(username,
                                    1,time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByUsernameAndTypeIdAndTimeAfterAndSearch(username,
                                1,time, keywords, page,
                                ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndTimeAfter(
                            username,1, time, page, ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByUsernameAndTypeIdAndTimeAfter(
                                username,1, time, page, ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                }
            } else {
                if (null != keywords && !keywords.isEmpty()) {
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                    username,1, statusId, time, keywords, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByUsernameAndTypeIdAndStatusIdAndTimeAfterAndSearch(
                                username,1, statusId, time, keywords, page,
                                ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                } else {
                    orderPage = tdOrderService.findByUsernameAndTypeIdAndStatusIdAndTimeAfter(username,
                                    statusId,1, time, page,
                                    ClientConstant.pageSize);
                    if(null != excelUrl)
                    {
                    	Page<TdOrder> order_page = tdOrderService.findByUsernameAndTypeIdAndStatusIdAndTimeAfter(username,
                                statusId,1, time, page,
                                ClientConstant.pageSize);
                    	if(InOrderImport(order_page, row, cell, sheet))
                    	{
                    		download(wb, excelUrl, resp);
                    	}
                    }
                }
            }
        }

        map.addAttribute("order_page", orderPage);
		
		return "/client/distributor_inOrder_list";
	}
	
	/**
	 * 超市销售订单详细
	 * @author libiao
	 * 
	 */
	@RequestMapping(value="/order")
	public String orderdetial(Long id, String type,HttpServletRequest req,ModelMap map)
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
		if("inOrder".equals(type))
		{
			return "/client/distributor_inOrder_detail";
		}
		return "/client/distributor_order_detail";
	}
	
	/**
	 * 超市确认销售订单  确认发货   确认已收货  确认评价
	 * @author libiao
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
            	TdProvider provider = tdProviderService.findOne(order.getProviderId());
            	
            	List<TdOrderGoods> tdOrderGoodsList = order.getOrderGoodsList();
            	
            	 Long totalPoints = 0L;
                 Double totalCash = 0.0;
                 Double platformService = 0.0; // 平台服务费
                 Double trainService = 0.0;	// 分销返利
                
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
                            	 platformService += tog.getPrice() * tdGoods.getPlatformServiceReturnRation();
         					}
                             if(null != order.getProviderId()){
                            	 TdProviderGoods providerGoods = tdProviderGoodsService.findByProviderIdAndGoodsId(order.getProviderId(), tog.getGoodsId());
                            	 if (null != providerGoods && null != providerGoods.getShopReturnRation()) {
                            		 trainService += tog.getPrice() * providerGoods.getShopReturnRation(); 
                            	 }
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
	                	   distributor.setVirtualMoney(distributor.getVirtualMoney()+trainService);
	                       tdDistributorService.save(distributor);
	                   }
	                   // 批发商入帐
	                   if(null != provider){
	                	   provider.setVirtualMoney(provider.getVirtualMoney()+order.getTotalGoodsPrice()-platformService-trainService);
	                	   tdProviderService.save(provider);
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
	
	@RequestMapping(value="/inOrder/param/edit")
	@ResponseBody
	public Map<String, Object> inparamEdit(String orderNumber,
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
			if(type.equalsIgnoreCase("orderPayLeft"))
			{
				if(order.getStatusId().equals(3L))
				{
					order.setStatusId(4L);
					order.setDeliveryTime(new Date());
				}
			}
			else if(type.equalsIgnoreCase("orderService"))
			{
				if(order.getStatusId().equals(4L))
				{
					order.setStatusId(6L);
					order.setFinishTime(new Date());
					
					TdDistributor distributor = tdDistributorService.findbyUsername(username);
					List<TdOrderGoods> goodsList = order.getOrderGoodsList();
					for (TdOrderGoods tdOrderGoods : goodsList) {
						
						TdDistributorGoods distributorGoods = tdDistributorGoodsService.findByDistributorIdAndGoodsId(distributor.getId(), tdOrderGoods.getGoodsId());
						
						if(null == distributorGoods)
						{
							TdGoods goods = tdGoodsService.findOne(tdOrderGoods.getGoodsId());
//							tdProviderGoodsService.findByProviderIdAndGoodsId(, goodsId)
							distributorGoods = new TdDistributorGoods();
							
							distributorGoods.setDistributorTitle(distributor.getTitle());
							distributorGoods.setGoodsId(goods.getId());
							distributorGoods.setGoodsTitle(goods.getTitle());
//							distributorGoods.setGoodsPrice();
							distributorGoods.setBrandId(goods.getBrandId());
							distributorGoods.setBrandTitle(goods.getBrandTitle());
							distributorGoods.setCategoryId(goods.getCategoryId());
							distributorGoods.setCategoryIdTree(goods.getCategoryIdTree());
							distributorGoods.setCode(goods.getCode());
							distributorGoods.setCoverImageUri(goods.getCoverImageUri());
							distributorGoods.setGoodsMarketPrice(tdOrderGoods.getPrice());
							distributorGoods.setIsDistribution(false);
//						distributorGoods.setGoodsParamList(goods.getParamList());
							distributorGoods.setReturnPoints(goods.getReturnPoints());
							distributorGoods.setParamValueCollect(goods.getParamValueCollect());
							distributorGoods.setIsOnSale(false);
							distributorGoods.setLeftNumber(tdOrderGoods.getQuantity());
							distributorGoods.setUnit(goods.getSaleType());
						}else{
							
							distributorGoods.setLeftNumber(distributorGoods.getLeftNumber()+tdOrderGoods.getQuantity());
						}
						distributor.getGoodsList().add(distributorGoods);
					}
					distributor.setGoodsList(distributor.getGoodsList());
					tdDistributorService.save(distributor);
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
	public String saleGoods(Integer page,String keywords,Long categoryId,HttpServletRequest req,ModelMap map)
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
		map.addAttribute("page", page);
		map.addAttribute("keywords",keywords);
		map.addAttribute("categoryId", categoryId);

		map.addAttribute("category_list", tdProductCategoryService.findAll());
		if(null == categoryId){
			if(null == keywords)
			{
				map.addAttribute("goods_page",
						tdGoodsService.findByIsOnSaleTrueOrderBySortIdAsc(page, 10));
			}
			else
			{
				map.addAttribute("goods_page",
						tdGoodsService.searchAndIsOnSaleTrueOrderBySortIdAsc(keywords, page, 10));
			}
		}
		else
		{
			if(null == keywords)
			{
				map.addAttribute("goods_page", 
						tdGoodsService.findByCategoryIdAndIsOnSaleTrue(categoryId, page, 10));
			}
			else
			{
				map.addAttribute("goods_page", 
						tdGoodsService.searchAndFindByCategoryIdAndIsOnSaleTrueOrderBySortIdAsc(keywords, categoryId, page, 10));
			}
		}
		return "/client/distributor_goods_onsale";
	}
	
	/**
	 * 从平台选择商品上架到超市
	 * @author libiao
	 * 
	 */
	
	@RequestMapping(value="/goodsOnsale", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> goodsOnsale(Long goodsId,
				String goodsTitle,
				Double goodsPrice,
				Long leftNumber,
				String unit,
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
			distributorGoods.setDistributorTitle(distributor.getTitle());
			distributorGoods.setGoodsId(goods.getId());
			distributorGoods.setGoodsTitle(goodsTitle);
			distributorGoods.setSubGoodsTitle(goods.getSubTitle());
			distributorGoods.setGoodsPrice(goodsPrice);
			distributorGoods.setBrandId(goods.getBrandId());
			distributorGoods.setBrandTitle(goods.getBrandTitle());
			distributorGoods.setCategoryId(goods.getCategoryId());
			distributorGoods.setCategoryIdTree(goods.getCategoryIdTree());
			distributorGoods.setCode(goods.getCode());
			distributorGoods.setProductId(goods.getProductId());
			distributorGoods.setSelectOneValue(goods.getSelectOneValue());
			distributorGoods.setSelectTwoValue(goods.getSelectTwoValue());
			distributorGoods.setSelectThreeValue(goods.getSelectThreeValue());
			distributorGoods.setCoverImageUri(goods.getCoverImageUri());
			distributorGoods.setGoodsMarketPrice(goods.getMarketPrice());
			distributorGoods.setIsDistribution(false);
//			distributorGoods.setGoodsParamList(goods.getParamList());
			distributorGoods.setReturnPoints(goods.getReturnPoints());
			distributorGoods.setParamValueCollect(goods.getParamValueCollect());
			distributorGoods.setIsOnSale(true);
			distributorGoods.setIsAudit(true);
			distributorGoods.setLeftNumber(leftNumber);
			distributorGoods.setOnSaleTime(new Date());
			if(null != unit || !"".equals(unit))
			{
				distributorGoods.setUnit(unit);
			}else{
				distributorGoods.setUnit(goods.getPromotion());
			}
			distributor.getGoodsList().add(distributorGoods);
		}else{
			disGoods.setIsAudit(true);
			disGoods.setOnSaleTime(new Date());
			disGoods.setGoodsTitle(goodsTitle);
			disGoods.setLeftNumber(leftNumber);
			disGoods.setGoodsPrice(goodsPrice);
			if(null != unit || !"".equals(unit))
			{
				disGoods.setUnit(unit);
			}else{
				disGoods.setUnit(goods.getPromotion());
			}
			distributor.getGoodsList().add(disGoods);
			
		}
//		List<TdDistributorGoods> list = tdDistributorGoodsService.findByGoodsId(goodsId);
//		
//		// 判断是否有超市在售此商品
//		if(list.size()==0)
//		{
//			goods.setIsOnSale(true);
//			tdGoodsService.save(goods);
//		}

		tdDistributorService.save(distributor);
		res.put("msg", "上架成功");
		return res;
	}
	
	/**
	 * 选择分销商品
	 * 
	 */
	@RequestMapping(value="/supply",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> distributionGoods(Long proGoodsId,HttpServletRequest req){
    	Map<String,Object> res=new HashMap<>();
    	String username =(String)req.getSession().getAttribute("distributor");
		if(null ==username)
		{
			res.put("msg", "请先登录！");
			return res;
		}
		if(null ==proGoodsId)
		{
			res.put("msg","选择的商品无效！");
			return res;
		}
		supply(username, proGoodsId);
    	res.put("msg", "已成功分销");
		
    	return res;
    }
	
	/**
	 * 批量分销
	 * 
	 */
	@RequestMapping(value="/supplyAll",method=RequestMethod.POST)
	public String supplyAll(Long[] listId,
			Integer[] listChkId,
			Integer page,
			Long providerId,String keywords,
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
		supplyAll(listId, listChkId, username);
		
		if(null == providerId){
			return "redirect:/distributor/supply/list/?page="+page+"&keywords="+keywords;
		}else{
			return "redirect:/distributor/goods/sale/?page="+page+"&providerId="+providerId+"&keywords="+keywords;
		}
	}
	
	@RequestMapping(value="/goods/list")
	public String inGoodslist(String keywords,Integer page,
			Long providerId,String isDistribution,
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
		map.addAttribute("provider_list", tdProviderService.findByType(1L));
		map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
		map.addAttribute("cart_goods_list", tdCartGoodsService.findByUsername(username));
		
		// 参数注回
		map.addAttribute("keywords", keywords);
		map.addAttribute("page", page);
//		map.addAttribute("distribution", isDistribution);
		map.addAttribute("providerId", providerId);
		
		if(null == providerId)
		{
//			if("isDistribution".equalsIgnoreCase(isDistribution))
//			{
//				if(null == keywords)
//				{
//					map.addAttribute("proGoods_page",
//							tdProviderGoodsService.findByIsDistributionTrueAndIsAuditTrue(page, 10));
//				}else{
//					map.addAttribute("proGoods_page", 
//							tdProviderGoodsService.searchAndIsDistributionTrueAndIsAuditTrue(keywords, page, 10));
//				}
//			}
//			else if("isNotDistribution".equalsIgnoreCase(isDistribution))
//			{	
//				if(null == keywords){
//					map.addAttribute("proGoods_page",
//							tdProviderGoodsService.findByIsDistributionFalseAndIsAuditTrue(page, 10));
//				}else{
//					map.addAttribute("proGoods_page",
//							tdProviderGoodsService.searchAndIsDistributionFalseAndIsAuditTrue(keywords, page, 10));
//				}
//			}
//			else
//			{
				if(null == keywords)
				{
					map.addAttribute("proGoods_page",
							tdProviderGoodsService.findByIsOnSaleTrue(page, 10));
					
				}else{
					map.addAttribute("proGoods_page", 
							tdProviderGoodsService.searchAndIsOnSaleTrue(keywords, page, 10));
				}
//			}
		}
		else
		{
//			if("isDistribution".equalsIgnoreCase(isDistribution))
//			{
//				if(null == keywords){
//					map.addAttribute("proGoods_page",
//							tdProviderGoodsService.findByProviderIdAndIsDistributionAndIsAudit(providerId,true, true, page, 10));
//				}else{
//					map.addAttribute("proGoods_page",
//							tdProviderGoodsService.searchAndProviderIdAndIsDistributionAndIsAudit(providerId, true, true, keywords, page, 10));
//				}
//			}
//			else if("isNotDistribution".equalsIgnoreCase(isDistribution))
//			{
//				if(null == keywords){
//					map.addAttribute("proGoods_page",
//							tdProviderGoodsService.findByProviderIdAndIsDistributionAndIsAudit(providerId,false, true, page, 10));
//				}else{
//					map.addAttribute("proGoods_page",
//							tdProviderGoodsService.searchAndProviderIdAndIsDistributionAndIsAudit(providerId, false, true, keywords, page, 10));
//				}
//			}
//			else
//			{
				if(null == keywords){
					map.addAttribute("proGoods_page",
							tdProviderGoodsService.findByProviderIdAndIsOnSale(providerId,true, page, 10));
				}else{
					map.addAttribute("proGoods_page",
							tdProviderGoodsService.searchAndProviderIdAndIsOnSale(providerId, keywords, true, page, 10));
				}
//			}
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
		
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
		
		TdProviderGoods providerGoods = tdProviderGoodsService.findOne(pgId);
		
		if(null != providerGoods){
			List<TdCartGoods> oldCartGoodsList = null;
           
            // 购物车是否已有该商品
//            oldCartGoodsList = tdCartGoodsService
//                            .findByGoodsIdAndUsername(providerGoods.getGoodsId(), username);
			System.err.println(providerGoods.getId()+"---"+username+"---"+tdProviderGoodsService.findProviderId(pgId));
			
			oldCartGoodsList = tdCartGoodsService.
						findByGoodsIdAndUsernameAndProviderId(providerGoods.getId(), username,tdProviderGoodsService.findProviderId(pgId));
           
            if(null !=oldCartGoodsList && oldCartGoodsList.size() >0){
            	 long oldQuantity = oldCartGoodsList.get(0).getQuantity();
            	 if(oldQuantity < providerGoods.getLeftNumber())
            	 {
            		 if(oldQuantity+quantity > providerGoods.getLeftNumber())
            		 {
            			 oldCartGoodsList.get(0).setQuantity(providerGoods.getLeftNumber());
            		 }else{
            			 oldCartGoodsList.get(0).setQuantity(oldQuantity + quantity);
            		 }
            		 tdCartGoodsService.save(oldCartGoodsList.get(0));
            	 }
            }else{
            	TdCartGoods cartGoods = new TdCartGoods();
            	cartGoods.setIsLoggedIn(true);
            	cartGoods.setUsername(username);
            	cartGoods.setGoodsId(providerGoods.getId());
            	cartGoods.setGoodsCoverImageUri(providerGoods.getGoodsCoverImageUri());
            	cartGoods.setGoodsTitle(providerGoods.getGoodsTitle());
            	cartGoods.setProviderTite(providerGoods.getProviderTitle());
            	cartGoods.setProviderId(tdProviderGoodsService.findProviderId(providerGoods.getId()));
            	cartGoods.setIsSelected(true);
            	cartGoods.setPrice(providerGoods.getOutFactoryPrice());
            	if(quantity>providerGoods.getLeftNumber())
            	{
            		cartGoods.setQuantity(providerGoods.getLeftNumber());
            	}else {
            		cartGoods.setQuantity(quantity);
				}
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
            
            TdProviderGoods providerGoods = tdProviderGoodsService.findOne(id);
            
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
    
    @RequestMapping(value = "/goods/changQuantity",method = RequestMethod.POST)
    public String cartNumberChange(Long id,Long quantity, HttpServletRequest req, ModelMap map) {

        String username = (String) req.getSession().getAttribute("distributor");

        if (null == username) {
            username = req.getSession().getId();
        }
        
        if(null == quantity){
        	quantity = 1L;
        }
        
        if (null != id) {
//            TdCartGoods cartGoods = tdCartGoodsService.findOne(id);
        	TdCartGoods cartGoods =tdCartGoodsService.findTopByGoodsIdAndUsername(id, username);

        	TdProviderGoods providerGoods = tdProviderGoodsService.findByProviderIdAndGoodsId(cartGoods.getProviderId(), cartGoods.getGoodsId());
            if (cartGoods.getUsername().equalsIgnoreCase(username)) {
                if(quantity < providerGoods.getLeftNumber()){
                	cartGoods.setQuantity(quantity);
                	tdCartGoodsService.save(cartGoods);
                }
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
     *  	增加相应批发账户
     */
    @RequestMapping(value="/order/info",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> orderInfo(HttpServletRequest req)
    {
    	Map<String,Object> res =new HashMap<>();
    	res.put("code", 0);
    	String username = (String) req.getSession().getAttribute("distributor");

        if (null == username) {
        	res.put("msg", "请重新登录");
        	return res;
        }
       TdDistributor distributor = tdDistributorService.findbyUsername(username);
        
        List<TdCartGoods> cartSelectedGoodsList = tdCartGoodsService.findByUsernameAndIsSelectedTrue(username);

//        List<TdOrderGoods> orderGoodsList = new ArrayList<TdOrderGoods>();
       
        // 储存批发商Id 和对应商品  为拆分订单做准备
        Map<Long,List<TdCartGoods>> cartGoodsMap =new HashMap<>();
        if(null != cartSelectedGoodsList)
        {
        	for (TdCartGoods cartGoods: cartSelectedGoodsList) 
        	{
				if(cartGoods.getIsSelected())
				{
					if(cartGoodsMap.containsKey(cartGoods.getProviderId()))
					{
						cartGoodsMap.get(cartGoods.getProviderId()).add(cartGoods);
					}else{
						List<TdCartGoods> clist=new ArrayList<>();
						clist.add(cartGoods);
						cartGoodsMap.put(cartGoods.getProviderId(),clist);
					}
				}
			}
        }
        
        if(null == cartGoodsMap || cartGoodsMap.size() <= 0){
//        	return "/client/error_404";
        	res.put("msg", "参数错误！");
        	return res;
        }
        
        // 订单拆分
        Set<Entry<Long,List<TdCartGoods>>> set = cartGoodsMap.entrySet();
        Iterator<Entry<Long, List<TdCartGoods>>> iterator = set.iterator();
        while(iterator.hasNext())
        {
        	Map.Entry<Long,List<TdCartGoods> > m  = iterator.next();
        
        	 List<TdOrderGoods> orderGoodsList = new ArrayList<TdOrderGoods>();
        	 Double totalPrice = 0.0; // 购物总额
        	 Double serviceRation = 0.0; // 平台返利
        	 
        	 
        	 for (int i = 0; i < m.getValue().size(); i++) {
        		 TdCartGoods cartGoods= m.getValue().get(i);
        		 TdProviderGoods providerGoods = tdProviderGoodsService.findOne(cartGoods.getGoodsId());
        		 
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
//			orderGoods.setGoodsSaleType(goodsSaleType);
        		 
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
        	 
        	 TdOrder tdOrder = new TdOrder();
        	 TdProvider provider = tdProviderService.findOne(m.getKey());
        	 
             Date current = new Date();
             SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
             String curStr = sdf.format(current);
             Random random = new Random();


             // 基本信息
             tdOrder.setUsername(username);
             tdOrder.setOrderTime(current);
             tdOrder.setShopId(provider.getId());
             tdOrder.setShopTitle(provider.getTitle());

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
             tdOrder.setStatusId(3L);
             // 总价
             tdOrder.setTotalPrice(totalPrice);
             
             if(null != provider.getServiceRation())
      		 {
      			serviceRation =totalPrice*provider.getServiceRation(); // 计算平台获利
      		 }
             tdOrder.setTrainService(serviceRation); // 平台服务费
             
             // 订单类型-批发订单
             tdOrder.setTypeId(1L);
             
             // 订单商品
             tdOrder.setOrderGoodsList(orderGoodsList);
             tdOrder.setTotalGoodsPrice(totalPrice);
             
             // 网站基本信息
//             TdSetting setting = tdSettingService.findTopBy();
            
             if(distributor.getVirtualMoney()<tdOrder.getTotalPrice())
             {
            	 res.put("msg", "账户余额不足，请先充值！");
            	 return res;
             }
             
             // 保存商品信息
             tdOrderGoodsService.save(orderGoodsList);
             
             // 扣除超市虚拟账户
             tdOrder = tdOrderService.save(tdOrder);
            distributor.setVirtualMoney(distributor.getVirtualMoney()-tdOrder.getTotalPrice());//扣除超市虚拟账户金额
            tdDistributorService.save(distributor);
            
     		if(null == provider.getVirtualMoney()){
     			provider.setVirtualMoney(new Double(0));
     		}
     		provider.setVirtualMoney(provider.getVirtualMoney()+tdOrder.getTotalGoodsPrice() - serviceRation);
     		tdProviderService.save(provider);
     		
//     		//平台虚拟账户增加金额
//     		setting.setVirtualMoney(tdOrder.getTotalPrice());
//     		tdSettingService.save(setting);
     		
     		// 保存交易记录
     		TdPayRecord record = new TdPayRecord();
            record.setCont("批发款");
            record.setCreateTime(new Date());
            record.setDistributorId(distributor.getId());
            record.setDistributorTitle(distributor.getTitle());
            record.setProviderId(provider.getId());
            record.setProviderTitle(provider.getTitle());
            record.setOrderId(tdOrder.getId());
            record.setOrderNumber(tdOrder.getOrderNumber());
            record.setStatusCode(1);
            record.setProvice(tdOrder.getTotalGoodsPrice());
            tdPayRecordService.save(record);
        	 
        }
        	
		// 删除已生成订单的购物车项
        tdCartGoodsService.delete(cartSelectedGoodsList);
        
//    	return "redirect:/distributor/inOrder/list/0";
        res.put("code", 1);
        return res;
    }
    
	
    /**
     *  添加收藏
     */
    @RequestMapping(value="/collect/add",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> collectAdd(Long pgId,HttpServletRequest req,ModelMap map){
    	 Map<String, Object> res = new HashMap<String, Object>();
         res.put("code", 1);
    	
         if(null == pgId){
        	 res.put("msg","参数错误！");
        	 return res;
         }
       
         String username =(String)req.getSession().getAttribute("distributor");
         if(null == username){
        	 res.put("msg", "请重新登录");
        	 return res;
         }
         res.put("code",0);
         
         TdProviderGoods providerGoods = tdProviderGoodsService.findOne(pgId);
         if(null == providerGoods){
        	 res.put("msg", "参数错误！");
        	 return res;
         }
         
         // 没有收藏
         if(null == tdUserCollectService.findByUsernameAndProviderId(username, tdProviderGoodsService.findProviderId(pgId))){
        	 TdUserCollect collect = new TdUserCollect();
        	 
        	 collect.setUsername(username);
        	 collect.setProviderId(pgId);
        	 collect.setGoodsId(providerGoods.getGoodsId());
        	 collect.setGoodsCoverImageUri(providerGoods.getGoodsCoverImageUri());
        	 collect.setGoodsSalePrice(providerGoods.getOutFactoryPrice());
        	 collect.setGoodsTitle(providerGoods.getGoodsTitle());
        	 collect.setCollectTime(new Date());
        	 
        	 tdUserCollectService.save(collect);
        	 res.put("msg","收藏成功");
        	 return res;
         }
         
         res.put("msg","已经收藏该商品");
         
    	return res;
    }
    /**
     * 收藏列表
     * 
     */
    
    @RequestMapping(value="/collect/list")
    public String collect(HttpServletRequest req,Integer page,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	if(null == page){
    		page=0;
    	}
    	tdCommonService.setHeader(map, req);
    	
    	map.addAttribute("collect_page",
    			tdUserCollectService.findByUsername(username, page,ClientConstant.pageSize));
    	
    	return "/client/distributor_collect";
    }
    
    /**
     * 	取消收藏
     * 
     */
    @RequestMapping(value="/collect/del")
    public String collectDle(HttpServletRequest req,ModelMap map,Long id){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	if(null != id){
    		tdUserCollectService.delete(id);
    	}
    	return "redirect:/distributor/collect/list";
    	
    }
    
    /**
     * 平台服务
     * 
     */
    @RequestMapping(value="/info/{mid}")
    public String info(@PathVariable Long mid,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	List<TdArticleCategory> catList = tdArticleCategoryService.findByMenuId(mid);
    	
    	tdCommonService.setHeader(map, req);
// 	    map.addAttribute("td_art_list",catList);
 	    map.addAttribute("mid", mid);
 	    
 	    map.addAttribute("new_list",tdArticleService.findByMenuId(mid));
 	   if (null != catList && catList.size() > 0) 
 	   {
	   		for (int i = 0; i < catList.size(); i++) {
				TdArticleCategory tdCat=catList.get(i);
				map.addAttribute("news_page", tdArticleService
   						.findByMenuIdAndCategoryIdAndIsEnableOrderByIdDesc(mid,
   								tdCat.getId(), 0, ClientConstant.pageSize).getContent());
				
			}
 	   }
 	   
 	   return "/client/distributor_info_list";
   }
    
    @RequestMapping(value="/content/{newId}")
    public String newContent(@PathVariable Long newId,Long mid,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	tdCommonService.setHeader(map, req);
    	if(null == newId){
    		return "/client/error_404";
    	}
    	map.addAttribute("mid",mid);

    	TdArticle tdArticle = tdArticleService.findOne(newId);
    	if(null != tdArticle){
    		map.addAttribute("info",tdArticle);
    	}
    	TdArticle article = tdArticleService.findPrevOne(newId, tdArticle.getCategoryId(), tdArticle.getMenuId());
    	
    	if(null != article){
    		map.addAttribute("prev_info",article);
    	}
    	TdArticle tdarticle =tdArticleService.findNextOne(newId, tdArticle.getCategoryId(), tdArticle.getMenuId());
    	if(null != tdarticle){
    		map.addAttribute("next_info",tdarticle);
    	}
    	return "/client/distributor_info";
    }
    
    /**
     * 交易记录
     * 
     */
    @RequestMapping(value="/pay/record")
    public String payRecord(Integer page,String cont,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	if(null == page ){
    		page = 0;
    	}
    	map.addAttribute("page", page);
    	map.addAttribute("cont", cont);
    	tdCommonService.setHeader(map, req);
    	
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	if(null == cont || "".equalsIgnoreCase(cont))
    	{
    		map.addAttribute("pay_record_page",
    				tdPayRecordService.findByDistributorId(distributor.getId(), page, ClientConstant.pageSize));
    	}else{
    		map.addAttribute("pay_record_page",
    				tdPayRecordService.searchByDistributorId(distributor.getId(),cont, page, ClientConstant.pageSize));
    	}
    	return "/client/distributor_record";
    }
    
    /**
     * 商品需求
     * 
     */
    @RequestMapping(value="/goods/need")
    public String noodGoods(HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("category_list", tdProductCategoryService.findAll());
    	return "/client/distributor_goods_need";
    }
    
    @RequestMapping(value="goods/need",method=RequestMethod.POST)
    public String needGoods(TdDemand demand,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	tdCommonService.setHeader(map, req);
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	
    	if(null == demand){
    		return "/client/error_404";
    	}
    	if(null != demand.getCategoryId()){
    		TdProductCategory category = tdProductCategoryService.findOne(demand.getCategoryId());
    		demand.setCategory(category.getTitle());
    	}
    	demand.setName(distributor.getTitle());
    	demand.setMobile(distributor.getMobile());
    	demand.setTime(new Date());
    	demand.setStatusId(0L);
    	
    	tdDemandService.save(demand);
    	
    	return "/client/distributor_end_need";
    }
    
    /**
     * 查看会员退货
     * 
     */
    @RequestMapping(value="/return/list")
    public String userReturn(Integer page,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	if(null == page){
    		page=0;
    	}
    	tdCommonService.setHeader(map, req);
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	map.addAttribute("return_page", 
    			tdUserReturnService.findByShopIdAndType(distributor.getId(), 1L, page,ClientConstant.pageSize));
    	
    	return "/client/distributor_user_return";
    }
    
    @RequestMapping(value="/return/param/edit")
    @ResponseBody
    public Map<String,Object> returnedit(Long id,HttpServletRequest req){
    	Map<String,Object> res =new HashMap<>();
    	res.put("code",1);
		String username = (String)req.getSession().getAttribute("distributor");
		
		if (null == username)
        {
            res.put("message", "请重新登录！");
            return res;
        }
		if(null != id)
		{
			TdUserReturn tdReturn = tdUserReturnService.findOne(id);
			if(null != tdReturn && tdReturn.getStatusId()==0)
			{
				tdReturn.setStatusId(1L);
				tdUserReturnService.save(tdReturn);
				res.put("message", "已处理此次退货！");
				res.put("code", 0);
				return res;
			}
		}
		
		res.put("message", "参数错误！");
    	return res;
    }
    
    @RequestMapping(value="/order/return")
    public String orderReturn(HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("pro_list", 
    			tdProviderService.findByType(1L));
    	
    	return "/client/distributor_order_return";
    }
    /**
     * 超市退货申请
     * 
     */
    @RequestMapping(value="/order/return",method=RequestMethod.POST)
    public String Return(TdUserReturn tdReturn,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	tdCommonService.setHeader(map, req);
    	
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	if(null !=tdReturn.getShopId()){
    		TdProvider provider = tdProviderService.findOne(tdReturn.getShopId());
    		tdReturn.setShopTitle(provider.getTitle());
    	}
    	
    	tdReturn.setDistributorId(distributor.getId());
    	tdReturn.setReturnTime(new Date());
    	tdReturn.setTelephone(distributor.getMobile());
    	tdReturn.setUsername(distributor.getTitle());
    	tdReturn.setType(2L);
    	tdReturn.setStatusId(0L);
    	
    	tdUserReturnService.save(tdReturn);
    	
    	return "/client/distributor_return_end";
    }
    
    @RequestMapping(value="/list/return")
    public String returnLIst(Integer page,HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	if(null == page){
    		page = 0;
    	}
    	tdCommonService.setHeader(map, req);
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	
    	map.addAttribute("return_page", 
    			tdUserReturnService.findByDistributorIdAndType(distributor.getId(), 2L, page, ClientConstant.pageSize));
    	
    	return "/client/distributor_return_list";
    }
    
    /**
     * 分销商品
     * 
     */
    @RequestMapping(value="/supply/list")
	public String goodslist(String keywords,Integer page,
			Long providerId,String isDistribution,
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
		map.addAttribute("provider_list", tdProviderService.findByType(2L));
		map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
		map.addAttribute("cart_goods_list", tdCartGoodsService.findByUsername(username));
		
		// 参数注回
		map.addAttribute("keywords", keywords);
		map.addAttribute("page", page);
//		map.addAttribute("distribution", isDistribution);
		map.addAttribute("providerId", providerId);
		
		if(null == providerId)
		{
				if(null == keywords)
				{
					map.addAttribute("proGoods_page",
							tdProviderGoodsService.findByIsDistributionTrueAndIsAuditTrue(page, 10));
				}else{
					map.addAttribute("proGoods_page", 
							tdProviderGoodsService.searchAndIsDistributionTrueAndIsAuditTrue(keywords, page, 10));
				}
		}
		else
		{
			if(null == keywords){
				map.addAttribute("proGoods_page",
						tdProviderGoodsService.findByProviderIdAndIsDistributionAndIsAudit(providerId,true, true, page, 10));
			}else{
				map.addAttribute("proGoods_page",
						tdProviderGoodsService.searchAndProviderIdAndIsDistributionAndIsAudit(providerId, true, true, keywords, page, 10));
			}
		}
		
		return "/client/distributor_supply";
	}
    
    @RequestMapping(value="/goods/supply", method= RequestMethod.GET)
	public String supplyGoods(Long categoryId,String keywords, Integer page,HttpServletRequest req,ModelMap map)
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

		map.addAttribute("page",page);
		map.addAttribute("keywords", keywords);
		map.addAttribute("categoryId", categoryId);
		
		List<Long> list = tdDistributorGoodsService.findByDistributorIdAndIsAudit(distributor.getId());
		
		List<TdProductCategory> category_list = new ArrayList<>();
		
		if(null != list)
		{
			for (int i = 0; i < list.size(); i++) {
				category_list.add(tdProductCategoryService.findOne(Long.parseLong(list.get(i)+"")));
			}
		}// 所有该批发商有的分类
		map.addAttribute("category_list",category_list);
//		map.addAttribute("category_list", tdProductCategoryService.findAll());
		
		if(null == categoryId)
		{
			if(null == keywords || "".equals(keywords.trim()))
			{
				map.addAttribute("dis_goods_page",
						tdDistributorGoodsService.findByDistributorIdAndIsDistributorAndIsAudit(distributor.getId(), true, true, page, ClientConstant.pageSize));
			}
			else
			{
				map.addAttribute("dis_goods_page",
						tdDistributorGoodsService.searchByDistributorIdAndIsDistributorAndIsAudit(distributor.getId(), true, true, keywords, page, ClientConstant.pageSize));
			}
		}
		else
		{
			if(null == keywords || "".equals(keywords.trim()))
			{
				map.addAttribute("dis_goods_page", 
						tdDistributorGoodsService.findByDistributorIdAndCategoryIdAndIsDistributorAndIsAudit(distributor.getId(), categoryId, true, true, page, ClientConstant.pageSize));
			}
			else
			{
				map.addAttribute("dis_goods_page", 
						tdDistributorGoodsService.searchByDistributorIdAndCategoryIdAndIsDistributorAndIsAudit(distributor.getId(), categoryId, true, true, keywords, page,ClientConstant.pageSize));
			}
		}
		
		return "/client/distributor_supply_list";
	}
    
    /**
     * 删除分销商品
     * 
     */
    @RequestMapping(value="/supply/delete/{disId}")
	public String GoodsDelete(@PathVariable Long disId,Integer page, HttpServletRequest req,ModelMap map)
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
		map.addAttribute("dis_goods_page",
				tdDistributorGoodsService.findByDistributorIdAndIsDistributorAndIsAudit(distributor.getId(), true, true, page, ClientConstant.pageSize));
		map.addAttribute("page",page);
		
		return "/client/distributor_goods_supply";
	}
    
    @RequestMapping(value="/deleteAll")
	public String deleteAll(
			Long[] listId,
			Integer[] listChkId,
			Integer page,
			Long categoryId,String keywords,
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
		
		deleteAll(listId, listChkId);
			
		if(null == categoryId){
			return "redirect:/distributor/goods/supply?page="+page+"&keywords="+keywords;
		}else{
			return "redirect:/distributor/goods/supply?page="+page+"&categoryId="+categoryId+"&keywords="+keywords;
		}
	}
    
    /**
     * 账号管理
     * 
     */
    @RequestMapping(value="/account")
    public String account(HttpServletRequest req,ModelMap map, Integer page){
    	String username = (String)req.getSession().getAttribute("distributor");
		if(null == username)
		{
			return "redirect:/login";
		}
		if(null == page){
			page = 0;
		}
		
		map.addAttribute("page", page);
    	tdCommonService.setHeader(map, req);
    	
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	
    	map.addAttribute("distributor", distributor);
    	map.addAttribute("pay_record_page",
    				tdPayRecordService.findByDistributorId(distributor.getId(), page, 5));
    	
    	return "/client/distributor_account";
    }
    
    /**
     * 充值
     * 
     */
    @RequestMapping(value="/topup1")
    public String topupOne(HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("distributor",
    				tdDistributorService.findbyUsername(username));
    	
    	return "/client/distributor_top_one";
    }
    
    @RequestMapping(value="/topup2",method=RequestMethod.POST)
    public String topupTwo(HttpServletRequest req,ModelMap map,TdPayRecord record){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	if(null == record)
    	{
    		return "/client/error_404";
    	}
    	
    	record.setStatusCode(2);
    	record.setCont("充值");
    	record.setCreateTime(new Date());
    	record = tdPayRecordService.save(record);
    	
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("distributor",
    				tdDistributorService.findbyUsername(username));
    	
    	
    	map.addAttribute("record", record);
    	
    	return "/client/distributor_top_end";
    }
    
    /**
     * 提现
     * 
     */
    @RequestMapping(value="/draw1")
    public String withdraw(HttpServletRequest req,ModelMap map){
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	
    	tdCommonService.setHeader(map, req);
    	map.addAttribute("distributor", tdDistributorService.findbyUsername(username));
    	
    	return "/client/distributor_draw_one";
    }
    
    @RequestMapping(value = "/edit/ImageUrl", method = RequestMethod.POST)
    @ResponseBody
    public String editimageUrl(String imgUrl,HttpServletRequest req)
    {
    	String username = (String)req.getSession().getAttribute("distributor");
    	if (null == username) {
            return "redirect:/login";
        }
    	TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	distributor.setImageUri(imgUrl);
    	tdDistributorService.save(distributor);
    	return "client/distributor_index";
    }
    
    // 批量上下架
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
               	if(null != goods){
               		goods.setIsOnSale(onsale);
               		tdDistributorGoodsService.save(goods);
               	}
            }
        }
	}
	
	// 批量删除
	public void deleteAll(Long[] ids,Integer[] chkIds)
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
	                tdDistributorGoodsService.delete(id);
	            }
	        }
	}
	
	// 批量分销
	public void supplyAll(Long[] ids,Integer[] chkIds, String username)
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
                supply(username, id);
            }
        }
	}
	
	// 超市分销商品
	public void supply(String username,Long goodsId)
	{
		TdDistributor distributor = tdDistributorService.findbyUsername(username);
    	TdProviderGoods pGoods = tdProviderGoodsService.findOne(goodsId);
    	TdGoods goods = tdGoodsService.findOne(pGoods.getGoodsId());
    	TdDistributorGoods distributorGoods = tdDistributorGoodsService.findByDistributorIdAndGoodsId(distributor.getId(),pGoods.getGoodsId());
    	
    	if(null == distributorGoods)
    	{
    		distributorGoods=new TdDistributorGoods();
    		distributorGoods.setDistributorTitle(distributor.getTitle());
			distributorGoods.setGoodsId(pGoods.getGoodsId());
			distributorGoods.setGoodsTitle(pGoods.getGoodsTitle());
			distributorGoods.setSubGoodsTitle(goods.getSubTitle());
			distributorGoods.setGoodsPrice(pGoods.getOutFactoryPrice());
			distributorGoods.setBrandId(goods.getBrandId());
			distributorGoods.setBrandTitle(goods.getBrandTitle());
			distributorGoods.setCategoryId(goods.getCategoryId());
			distributorGoods.setCategoryIdTree(goods.getCategoryIdTree());
			distributorGoods.setCode(goods.getCode());
			distributorGoods.setProductId(goods.getProductId());
			distributorGoods.setSelectOneValue(goods.getSelectOneValue());
			distributorGoods.setSelectTwoValue(goods.getSelectTwoValue());
			distributorGoods.setSelectThreeValue(goods.getSelectThreeValue());
			distributorGoods.setCoverImageUri(goods.getCoverImageUri());
			distributorGoods.setGoodsMarketPrice(pGoods.getOutFactoryPrice());
//			distributorGoods.setGoodsParamList(goods.getParamList());
			distributorGoods.setReturnPoints(goods.getReturnPoints());
			distributorGoods.setParamValueCollect(goods.getParamValueCollect());
			distributorGoods.setProviderId(tdProviderGoodsService.findProviderId(pGoods.getId()));
			distributorGoods.setProviderTitle(pGoods.getProviderTitle());
			distributorGoods.setIsDistribution(true);
			distributorGoods.setIsOnSale(true);
			distributorGoods.setIsAudit(true);
			distributorGoods.setOnSaleTime(new Date());
			distributor.getGoodsList().add(distributorGoods);
    	}else{
    		distributorGoods.setGoodsPrice(pGoods.getOutFactoryPrice());
    		distributorGoods.setGoodsTitle(pGoods.getGoodsTitle());
    		distributorGoods.setIsDistribution(true);
			distributorGoods.setIsOnSale(true);
			distributorGoods.setIsAudit(true);
			distributorGoods.setOnSaleTime(new Date());
    	}
    	tdDistributorService.save(distributor);
	}
	
	// 销售单
	@SuppressWarnings("deprecation")
	public Boolean ImportData(Page<TdOrder> orderPage,HSSFRow row, HSSFCell cell, HSSFSheet sheet)
	{
		for (int i = 0; i < orderPage.getContent().size(); i++) {
			row = sheet.createRow((int)i+1);
			TdOrder order = orderPage.getContent().get(i);
			// 获取用户信息
			TdUser user = tdUserService.findByUsername(order.getUsername());
			
			row.createCell((short) 0).setCellValue(order.getOrderNumber());
			row.createCell((short) 1).setCellValue(order.getUsername());
			row.createCell((short) 2).setCellValue(order.getShippingName());
			row.createCell((short) 3).setCellValue(order.getShippingAddress());
			row.createCell((short) 4).setCellValue(order.getShippingPhone());
			row.createCell((short) 5).setCellValue(order.getTotalPrice());
			row.createCell((short) 6).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(order.getOrderTime()));
			if(order.getStatusId() ==2)
			{
				row.createCell((short) 7).setCellValue("待付款");
			}else if(order.getStatusId() ==3)
			{
				row.createCell((short) 7).setCellValue("待发货");
			}else if(order.getStatusId() ==4)
			{
				row.createCell((short) 7).setCellValue("待收货");
			}else if(order.getStatusId() ==5)
			{
				row.createCell((short) 7).setCellValue("待评价");
			}else if(order.getStatusId() ==6)
			{
				row.createCell((short) 7).setCellValue("已完成");
			}else if(order.getStatusId() ==7)
			{
				row.createCell((short) 7).setCellValue("已取消");
			}
		}
		return true;
	}
	
	// 进货单
	@SuppressWarnings("deprecation")
	public Boolean InOrderImport(Page<TdOrder> orderPage,HSSFRow row, HSSFCell cell, HSSFSheet sheet)
	{
		for (int i = 0; i < orderPage.getContent().size(); i++) {
			row = sheet.createRow((int)i+1);
			TdOrder order = orderPage.getContent().get(i);
			// 获取用户信息
			TdUser user = tdUserService.findByUsername(order.getUsername());
			
			row.createCell((short) 0).setCellValue(order.getOrderNumber());
			row.createCell((short) 1).setCellValue(order.getShippingName());
			row.createCell((short) 2).setCellValue(order.getShopTitle());
			row.createCell((short) 3).setCellValue(order.getTotalPrice());
			row.createCell((short) 4).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(order.getOrderTime()));
			if(order.getStatusId() ==2)
			{
				row.createCell((short) 5).setCellValue("待付款");
			}else if(order.getStatusId() ==3)
			{
				row.createCell((short) 5).setCellValue("待发货");
			}else if(order.getStatusId() ==4)
			{
				row.createCell((short) 5).setCellValue("待收货");
			}else if(order.getStatusId() ==5)
			{
				row.createCell((short) 5).setCellValue("待评价");
			}else if(order.getStatusId() ==6)
			{
				row.createCell((short) 5).setCellValue("已完成");
			}else if(order.getStatusId() ==7)
			{
				row.createCell((short) 5).setCellValue("已取消");
			}
		}
		return true;
	}
	
	public Boolean download(HSSFWorkbook wb, String exportUrl, HttpServletResponse resp){
   	 try  
        {  
	          FileOutputStream fout = new FileOutputStream(exportUrl+"order.xls");  
//	          OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");	                       	     
	          wb.write(fout);  
	          fout.close();
        }catch (Exception e)  
        {  
            e.printStackTrace();  
        } 
   	 OutputStream os;
		 try {
				os = resp.getOutputStream();
				File file = new File(exportUrl + "order.xls");
                
            if (file.exists())
                {
                  try {
                        resp.reset();
                        resp.setHeader("Content-Disposition", "attachment; filename="
                                + "order.xls");
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

