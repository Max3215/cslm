package com.ynyes.cslm.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdPointGoodsService;
import com.ynyes.cslm.service.TdUserPointService;
import com.ynyes.cslm.service.TdUserService;

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
	
	
	@RequestMapping("/pointGoods/list")
	public String list(Integer page,HttpServletRequest req,ModelMap map){
		
		
		
		
		return "";
	}
	
}
