package com.ynyes.cslm.controller.front;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdAdType;
import com.ynyes.cslm.entity.TdPointGoods;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.service.TdAdService;
import com.ynyes.cslm.service.TdAdTypeService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdPointGoodsService;
import com.ynyes.cslm.service.TdUserPointService;
import com.ynyes.cslm.service.TdUserService;
import com.ynyes.cslm.util.ClientConstant;

/**
 * 
 * @author Max
 * 积分兑换列表及兑换流程
 * 
 */
@Controller
public class TdPointGoodsController {

	
	@Autowired
	private TdCommonService tdCommonService;
	
	@Autowired
	private TdPointGoodsService tdPointGoodsService;
	
	@Autowired
	private TdUserService tdUserService;
	
	@Autowired
	private TdUserPointService tdUserPointService;
	
	@Autowired
	private TdAdTypeService tdAdTypeService;
	
	@Autowired
	private TdAdService tdAdService;
	
	@Autowired
	private TdDistributorGoodsService tdDistributorGoodsService;
	
	
	@RequestMapping("/pointGoods/list")
	public String list(Integer page,HttpServletRequest req,ModelMap map){
		String username =(String)req.getSession().getAttribute("username");
		
		if(null !=username){
			map.addAttribute("user", tdUserService.findByUsernameAndIsEnabled(username));
		}
		
    	map.addAttribute("hot_sale_list",tdDistributorGoodsService.findByIsOnSaleTrueBySoldNumberDesc(0,10).getContent());
		
		 // 列表页轮播广告
        TdAdType adType = tdAdTypeService.findByTitle("积分兑换列表页广告");

        if (null != adType) {
            map.addAttribute("point_ad_list", tdAdService
                    .findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(adType.getId(),0L));
        }
		
		tdCommonService.setHeader(map, req);
		
		if(null == page){
			page = 0;
		}
		
		PageRequest pageRequest = new PageRequest(page, ClientConstant.pageSize, new Sort(Direction.DESC, "onSaleTime"));
		
		map.addAttribute("goods_page", tdPointGoodsService.findAll(null, true, pageRequest));
		
		return "/client/point/goood_list";
	}
	
	@RequestMapping(value="/point/goods/detail")
	public String goodsDetail(Long id,HttpServletRequest req,ModelMap map){
		String username =(String)req.getSession().getAttribute("username");
		
		if(null !=username){
			map.addAttribute("user", tdUserService.findByUsernameAndIsEnabled(username));
		}
		
		tdCommonService.setHeader(map, req);
		
		map.addAttribute("hot_sale_list",tdDistributorGoodsService.findByIsOnSaleTrueBySoldNumberDesc(0,10).getContent());
		
		map.addAttribute("goods", tdPointGoodsService.findOne(id));
		
		return "/client/point/goods_detail";
	}
	
	@RequestMapping(value="/point/goods/check",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> chkedAdd(Long id,HttpServletRequest req,ModelMap map){
		Map<String,Object> res = new HashMap<String, Object>();
		map.addAttribute("code", 0);
		
		String username = (String)req.getSession().getAttribute("username");
		if(null == username){
			res.put("code",1);
			res.put("msg", "请先登录");
			return res;
		}
		
		TdUser user = tdUserService.findByUsernameAndIsEnabled(username);
		TdPointGoods pointGoods = tdPointGoodsService.findOne(id);
		if(null != user && null != pointGoods){
			if(null == user.getTotalPoints() || user.getTotalPoints() < pointGoods.getPoint()){
				res.put("msg", "积分不足");
				return res;
			}
		}else{
			res.put("msg", "参数错误");
			return res;
		}
		res.put("code", 2);
		return res;
	}
	
	
}
