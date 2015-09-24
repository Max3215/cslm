package com.ynyes.cslm.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdProviderGoodsService;
import com.ynyes.cslm.service.TdProviderService;

/**
 * 批发商
 * @author libiao
 *
 */

@Controller
@RequestMapping(value="/provider")
public class TdProviderController {
	
	@Autowired
	private TdProviderService tdProviderService;
	
	@Autowired
	private TdProviderGoodsService tdProviderGoodsService;
	
	@Autowired
	private TdGoodsService tdGoodsService;
	
	@RequestMapping(value="/index")
	public String providerIndex(HttpServletRequest req,ModelMap map)
	{
		String username =(String)req.getSession().getAttribute("provider");
		if(null == username)
		{
			return "redirect:/login";
		}
		
		return "";
	}
	
	
	
	
}
