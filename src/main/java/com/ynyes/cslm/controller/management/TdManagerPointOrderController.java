package com.ynyes.cslm.controller.management;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdPointOrder;
import com.ynyes.cslm.service.TdManagerLogService;
import com.ynyes.cslm.service.TdManagerService;
import com.ynyes.cslm.service.TdPointGoodsService;
import com.ynyes.cslm.service.TdPointOrderService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.util.SiteMagConstant;

/**
 * 后台兑换订单列表
 * @author Max
 *
 */
@Controller
@RequestMapping(value="/Verwalter/pointOrder")
public class TdManagerPointOrderController {

	@Autowired
	private TdPointGoodsService tdPointGoodsService;
	
	@Autowired
	private TdPointOrderService tdPointOrderService;
	
	@Autowired
	private TdUserService tdUserService;
	
	@Autowired
	private TdManagerLogService tdManagerLogService;
	
	@Autowired
	private TdManagerService tdManagerService;
	
	
	
	@RequestMapping(value="/list")
	public String orderList(String keywords,
								Integer page, 
					            Integer size,
					            Integer statusId,
					            String startTime,String endTime,
					            String __EVENTTARGET,
					            String __EVENTARGUMENT,
					            String __VIEWSTATE,
					            Long[] listId,
					            Integer[] listChkId,
					            ModelMap map,
					            String exportUrl,
					            Long payId,
					            HttpServletResponse resp,
					            HttpServletRequest req) throws Exception{
		
		String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
        if (null != __EVENTTARGET)
        {
            if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
            {
//                btnDelete(listId, listChkId);
                tdManagerLogService.addLog("delete", "删除订单", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("exportAll"))
            {
            	exportUrl = SiteMagConstant.backupPath;
                tdManagerLogService.addLog("export", "导出订单", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnPage"))
            {
                if (null != __EVENTARGUMENT)
                {
                    page = Integer.parseInt(__EVENTARGUMENT);
                } 
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
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date start = null;
		Date end = null ;
		
		if(null != startTime && !"".equals(startTime.trim()))
		{
			start = sdf.parse(startTime);
		}
		if(null != endTime && !"".equals(endTime.trim()))
		{
			end = sdf.parse(endTime);
		}
		
		map.addAttribute("order_page", tdPointOrderService.findAll(null, keywords, start, end, statusId, page, size));
		
		// 参数注回
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("keywords", keywords);
        map.addAttribute("statusId", statusId);
        map.addAttribute("startTime", start);
		map.addAttribute("endTime", end);
		
		map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
		
		
		return "/site_mag/point_order_list";
	}
	
	@RequestMapping(value="/edit")
	public String edit(Long id,HttpServletRequest req, ModelMap map){
		String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        if (null != id)
        {
            map.addAttribute("order", tdPointOrderService.findOne(id));
        }
        return "/site_mag/point_order_edit";
	}
	
	@RequestMapping(value="/param/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> paramEdit(Long orderId,
						String type,
						String data,
						HttpServletRequest req,
						ModelMap map){
		
		Map<String, Object> res = new HashMap<String, Object>();
        
        res.put("code", 1);
        
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            res.put("message", "请重新登录");
            return res;
        }
		
        TdPointOrder order = tdPointOrderService.findOne(orderId);
        
        if(null != orderId && null != type && !type.isEmpty()){
        	
        	// 发货
        	if("orderSeed".equalsIgnoreCase(type))
        	{
        		order.setStatusId(2);
        	}
        	// 收货
        	else if("orderReceive".equalsIgnoreCase(type)){
        		order.setStatusId(3);
        	}
        	// 修改备注
        	else if("editMark".equalsIgnoreCase(type)){
        		order.setSiteRemarke(data);
        	}
        	// 取消
        	else if("orderCancel".equalsIgnoreCase(type)){
        		order.setStatusId(4);
        		tdPointOrderService.orderCancel(order);  // 取消订单，返回积分
        	}
        	
        	tdPointOrderService.save(order);
        	tdManagerLogService.addLog("edit", "修改订单"+order.getOrderNumber(), req);
        	
        	res.put("code", 0);
            res.put("message", "修改成功!");
            return res;
        }
        
        res.put("message", "参数错误!");
        return res;
	}
	
}
