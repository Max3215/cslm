package com.ynyes.cslm.touch;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.cslm.service.TdCommonService;

@Controller
@RequestMapping("/touch")
public class TdTouchSettingController {

	@Autowired
	private TdCommonService tdCommonService;
	
	
	
	
	@RequestMapping(value="/setting/index")
	public String setting(HttpServletRequest req,ModelMap map )
	{
		String username = (String)req.getSession().getAttribute("username");
		if(null == username)
		{
			return "redirect:/touch/login";
		}
		tdCommonService.setHeader(map, req);
		
		return "/touch/setting";
	}
	
	
}
