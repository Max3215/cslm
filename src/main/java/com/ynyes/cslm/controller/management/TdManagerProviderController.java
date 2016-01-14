package com.ynyes.cslm.controller.management;

import static org.apache.commons.lang3.StringUtils.leftPad;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdPayRecord;
import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdProviderGoods;
import com.ynyes.cslm.service.TdManagerLogService;
import com.ynyes.cslm.service.TdPayRecordService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.service.TdProviderGoodsService;
import com.ynyes.cslm.service.TdProviderService;
import com.ynyes.cslm.util.SiteMagConstant;

/**
 * 后台用户管理控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value="/Verwalter/provider")
public class TdManagerProviderController {
    
    @Autowired
    TdProviderService tdProviderService;
    
    @Autowired
    TdManagerLogService tdManagerLogService;
    
    @Autowired
    TdProviderGoodsService tdProviderGoodsService;
    
    @Autowired
    TdProductCategoryService tdProductCategoryService;
    
    @Autowired
    TdPayRecordService tdPayRecordService;
    
    
    
    @RequestMapping(value="/check/{type}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> validateForm(@PathVariable String type,String param, Long id) {
        Map<String, String> res = new HashMap<String, String>();
        
        res.put("status", "n");
        
        if (null == param || param.isEmpty())
        {
            res.put("info", "该字段不能为空");
            return res;
        }
        if(type.equalsIgnoreCase("title"))
        {
        	if (null != tdProviderService.findByTitle(param))
            {
                res.put("info", "已存在同名商商家");
                return res;
            }
        }
        if(type.equalsIgnoreCase("username"))
        {
        	if(null != tdProviderService.findByUsername(param))
        	{
        		res.put("info", "账号已存在");
        		return res;
        	}
        }
        if(type.equalsIgnoreCase("virtualAccount"))
        {
        	if(null != tdProviderService.findByVirtualAccount(param))
        	{
        		res.put("info","虚拟账号已被占用");
        		return res;
        	}
        }
        res.put("status", "y");
        
        return res;
    }
    
    @RequestMapping(value="/list")
    public String setting(Integer page,
                          Integer size,
                          String keywords,
                          Long type,
                          String __EVENTTARGET,
                          String __EVENTARGUMENT,
                          String __VIEWSTATE,
                          Long[] listId,
                          Integer[] listChkId,
                          Long[] listSortId,
                          ModelMap map,
                          HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        if (null != __EVENTTARGET)
        {
            if (__EVENTTARGET.equalsIgnoreCase("btnPage"))
            {
                if (null != __EVENTARGUMENT)
                {
                    page = Integer.parseInt(__EVENTARGUMENT);
                } 
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnDelete"))
            {
                btnDelete(listId, listChkId);
                tdManagerLogService.addLog("delete", "删除供应商", req);
            }
            else if (__EVENTTARGET.equalsIgnoreCase("btnSave"))
            {
                btnSave(listId, listSortId);
                tdManagerLogService.addLog("edit", "修改供应商", req);
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
        
        if (null != keywords)
        {
            keywords = keywords.trim();
        }
        
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("type", type);
        map.addAttribute("keywords", keywords);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);

        Page<TdProvider> providerPage = null;
        if(null == type)
        {
        	if (null == keywords || "".equalsIgnoreCase(keywords))
        	{
        		providerPage = tdProviderService.findAllOrderBySortIdAsc(page, size);
        	}
        	else
        	{
        		providerPage = tdProviderService.searchAndOrderBySortIdAsc(keywords, page, size);
        	}
        }
        else
        {
        	if (null == keywords || "".equalsIgnoreCase(keywords))
        	{
        		providerPage = tdProviderService.findByTypeOrderBySortIdAsc(type,page,size);
        	}
        	else
        	{
        		providerPage = tdProviderService.searchAndTypeOrderBySortIdAsc(type,keywords, page, size);
        	}
        }
        
        map.addAttribute("provider_page", providerPage);
        
        return "/site_mag/provider_list";
    }
    
    @RequestMapping(value="/edit")
    public String orderEdit(Long id,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);

        if (null != id)
        {
        	TdProvider provider = tdProviderService.findOne(id);
            map.addAttribute("provider", provider);
            map.addAttribute("pay_tecord_list", tdPayRecordService.findByProviderId(provider.getId()));
        }
        return "/site_mag/provider_edit";
    }
    
    // 平台给超市充值
    @RequestMapping(value="/virtualMoney/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> diySiteEdit(Long id,
    		Double data,HttpServletRequest req)
    {
    	Map<String,Object> res = new HashMap<>();
    	res.put("code", 1);
    	
    	String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            res.put("message", "请重新登录");
            return res;
        }
    	
    	if(null == id)
    	{
    		res.put("message", "参数错误！");
    		return res;
    	}
    	
    	TdProvider provider = tdProviderService.findOne(id);
    	if(null != provider.getVirtualMoney())
    	{
    		provider.setVirtualMoney(provider.getVirtualMoney()+data);
    	}else{
    		provider.setVirtualMoney(data);
    	}
        tdProviderService.save(provider);
        
        Date current = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String curStr = sdf.format(current);
        Random random = new Random();
    	
    	TdPayRecord record = new TdPayRecord();
        record.setCont("平台充值");
        record.setCreateTime(new Date());
        record.setProviderId(id);
        record.setProviderTitle(provider.getTitle());
        record.setStatusCode(1);
        record.setProvice(data);
        record.setOrderNumber("CZ" + curStr
                + leftPad(Integer.toString(random.nextInt(999)), 3, "0"));
        
        tdPayRecordService.save(record);
    	
        res.put("code", 0);
        res.put("message", "充值成功！");
    	return res;
    }
    
    
    @RequestMapping(value="/save")
    public String orderEdit(TdProvider tdProvider,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        if(null == tdProvider.getVirtualMoney() || "".equals(tdProvider.getVirtualMoney())){
        	tdProvider.setVirtualMoney(new Double(0));
        }
        
        if (null == tdProvider.getId())
        {
            tdManagerLogService.addLog("add", "用户修改供应商", req);
        }
        else
        {
            tdManagerLogService.addLog("edit", "用户修改供应商", req);
        }
        System.err.println(tdProvider.getGoodsList());;
        tdProviderService.save(tdProvider);
        
        return "redirect:/Verwalter/provider/list";
    }
    
    @RequestMapping(value="/goods/list")
    public String goodsList(Integer page,Integer size,
            String distribution, String __EVENTTARGET,
            String audit,Long providerId,
            String __EVENTARGUMENT, String __VIEWSTATE, String keywords,
            Long[] listId, Integer[] listChkId, Long[] listSortId,
            ModelMap map, HttpServletRequest req){
    	String username = (String) req.getSession().getAttribute("manager");
    	if (null == username) {
            return "redirect:/Verwalter/login";
        }

        if (null == page || page < 0) {
            page = 0;
        }

        if (null == size || size <= 0) {
            size = SiteMagConstant.pageSize;
        }

        if (null != keywords) {
            keywords = keywords.trim();
        }
        if (null != __EVENTTARGET) {
            switch (__EVENTTARGET) {
            case "lbtnViewTxt":
            case "lbtnViewImg":
                __VIEWSTATE = __EVENTTARGET;
                break;

            case "btnSave":
//                btnSave(listId, listSortId, username);
                tdManagerLogService.addLog("edit", "用户修改商品", req);
                break;

            case "btnDelete":
                btnGoodsDelete(listId, listChkId);
                tdManagerLogService.addLog("delete", "用户删除商品", req);
                break;

            case "btnPage":
                if (null != __EVENTARGUMENT) {
                    page = Integer.parseInt(__EVENTARGUMENT);
                }
                break;

//            case "btnOnSale":
//                if (null != __EVENTARGUMENT) {
//                    Long goodsId = Long.parseLong(__EVENTARGUMENT);
//
//                    if (null != goodsId) {
//                        TdGoods goods = tdGoodsService.findOne(goodsId);
//                        TdProviderGoods providerGoods = tdProviderGoodsService.findOne(goodsId);
//
//                        if (null != goods) {
//                            if (null == goods.getIsOnSale()
//                                    || !goods.getIsOnSale()) {
//                                goods.setIsOnSale(true);
//                                goods.setOnSaleTime(new Date());
//                                tdManagerLogService.addLog("delete", "用户上架商品:"
//                                        + goods.getTitle(), req);
//                            } else {
//                                goods.setIsOnSale(false);
//                                tdManagerLogService.addLog("delete", "用户下架商品:"
//                                        + goods.getTitle(), req);
//                            }
//                            tdGoodsService.save(goods, username);
//                        }
//                    }
//                }
//                break;
            }
        }
        
        map.addAttribute("provider_list", tdProviderService.findAll());
       
        Page<TdProviderGoods> goodsPage = null;
        
        if(null ==providerId)// 批发商Id
        {
    		if("isDistribution".equalsIgnoreCase(distribution)) // 分销
    		{
    			if("isAudit".equalsIgnoreCase(audit))	// 已审核
    			{
    				if(null == keywords ||"".equalsIgnoreCase(keywords))
    				{
    					goodsPage = tdProviderGoodsService.findByIsDistributionTrueAndIsAuditTrue(page, size);
    				}else{
    					goodsPage = tdProviderGoodsService.searchAndIsDistributionTrueAndIsAuditTrue(keywords, page, size);
    				}
    			}
    			else if("isNotAudit".equalsIgnoreCase(audit)) // 未审核
    			{
    				if(null == keywords || "".equalsIgnoreCase(keywords))
    				{
    					goodsPage = tdProviderGoodsService.findByIsDistributionTrueAndIsAuditFalse(page, size);
    				}else{
    					goodsPage = tdProviderGoodsService.searchAndIsDistributionTrueAndIsAuditFalse(keywords, page, size);
    				}
    			}
    			else
    			{
    				if(null == keywords || "".equalsIgnoreCase(keywords))
    				{
    					goodsPage = tdProviderGoodsService.findByIsDistributionTrue(page, size);
    				}else{
    					goodsPage = tdProviderGoodsService.searchAndIsDistributionTrue(keywords, page, size);
    				}
    			}
    		}
    		else if("isNotDistribution".equalsIgnoreCase(distribution))	// 未分销
    		{
    			if("isAudit".equalsIgnoreCase(audit))	// 已审核
    			{
    				if(null == keywords ||"".equalsIgnoreCase(keywords))
    				{
    					goodsPage = tdProviderGoodsService.findByIsDistributionFalseAndIsAuditTrue(page, size);
    				}else{
    					goodsPage = tdProviderGoodsService.searchAndIsDistributionFalseAndIsAuditTrue(keywords, page, size);
    				}
    			}
    			else if("isNotAudit".equalsIgnoreCase(audit)) // 未审核
    			{
    				if(null == keywords || "".equalsIgnoreCase(keywords))
    				{
    					goodsPage = tdProviderGoodsService.findByIsDistributionFalseAndIsAuditFalse(page, size);
    				}else{
    					goodsPage = tdProviderGoodsService.searchAndIsDistributionFalseAndIsAuditFalse(keywords, page, size);
    				}
    			}
    			else
    			{
    				if(null == keywords || "".equalsIgnoreCase(keywords))
    				{
    					goodsPage = tdProviderGoodsService.findByIsDistributionFalse(page, size);
    				}else{
    					goodsPage = tdProviderGoodsService.searchAndIsDistributionFalse(keywords, page, size);
    				}
    			}
    		}
    		else
    		{
    			if("isAudit".equalsIgnoreCase(audit))	// 已审核
    			{
    				if(null == keywords ||"".equalsIgnoreCase(keywords))
    				{
    					goodsPage = tdProviderGoodsService.findByIsAuditTrue(page, size);
    				}else{
    					goodsPage = tdProviderGoodsService.searchAndIsAuditTrue(keywords, page, size);
    				}
    			}
    			else if("isNotAudit".equalsIgnoreCase(audit)) // 未审核
    			{
    				if(null == keywords || "".equalsIgnoreCase(keywords))
    				{
    					goodsPage = tdProviderGoodsService.findByIsAuditFalse(page, size);
    				}else{
    					goodsPage = tdProviderGoodsService.searchAndIsAuditFalse(keywords, page, size);
    				}
    			}
    			else
    			{
    				if(null == keywords || "".equalsIgnoreCase(keywords))
    				{
    					goodsPage = tdProviderGoodsService.findAll(page, size);
    				}else{
    					goodsPage = tdProviderGoodsService.searchAndKeywords(keywords, page, size);
    				}
    			}
    		}
        }
        else
        {
        	if("isDistribution".equalsIgnoreCase(distribution)) // 分销
    		{
    			if("isAudit".equalsIgnoreCase(audit))	// 已审核
    			{
    				if(null == keywords ||"".equalsIgnoreCase(keywords))
    				{
    					goodsPage = tdProviderGoodsService.findByProviderIdAndIsDistributionAndIsAudit(providerId, true, true, page, size);
    				}else{
    					goodsPage = tdProviderGoodsService.searchAndProviderIdAndIsDistributionAndIsAudit(providerId, true, true, keywords, page, size);
    				}
    			}
    			else if("isNotAudit".equalsIgnoreCase(audit)) // 未审核
    			{
    				if(null == keywords || "".equalsIgnoreCase(keywords))
    				{
    					goodsPage = tdProviderGoodsService.findByProviderIdAndIsDistributionAndIsAudit(providerId, true, false, page, size);
    				}else{
    					goodsPage = tdProviderGoodsService.searchAndProviderIdAndIsDistributionAndIsAudit(providerId, true, false, keywords, page, size);
    				}
    			}
    			else
    			{
    				if(null == keywords || "".equalsIgnoreCase(keywords))
    				{
    					goodsPage = tdProviderGoodsService.findByProviderIdAndIsDistribution(providerId, true, page, size);
    				}else{
    					goodsPage = tdProviderGoodsService.searchAndProviderIdAndIsDistribution(providerId, keywords, true, page, size);
    				}
    			}
    		}
    		else if("isNotDistribution".equalsIgnoreCase(distribution))	// 未分销
    		{
    			if("isAudit".equalsIgnoreCase(audit))	// 已审核
    			{
    				if(null == keywords ||"".equalsIgnoreCase(keywords))
    				{
    					goodsPage = tdProviderGoodsService.findByProviderIdAndIsDistributionAndIsAudit(providerId, false, true, page, size);
    				}else{
    					goodsPage = tdProviderGoodsService.searchAndProviderIdAndIsDistributionAndIsAudit(providerId, false, true, keywords, page, size);
    				}
    			}
    			else if("isNotAudit".equalsIgnoreCase(audit)) // 未审核
    			{
    				if(null == keywords || "".equalsIgnoreCase(keywords))
    				{
    					goodsPage = tdProviderGoodsService.findByProviderIdAndIsDistributionAndIsAudit(providerId, false, false, page, size);
    				}else{
    					goodsPage = tdProviderGoodsService.searchAndProviderIdAndIsDistributionAndIsAudit(providerId, false, false, keywords, page, size);
    				}
    			}
    			else
    			{
    				if(null == keywords || "".equalsIgnoreCase(keywords))
    				{
    					goodsPage = tdProviderGoodsService.findByProviderIdAndIsDistribution(providerId, false, page, size);
    				}else{
    					goodsPage = tdProviderGoodsService.searchAndProviderIdAndIsDistribution(providerId, keywords, false, page, size);
    				}
    			}
    		}
    		else
    		{
    			if("isAudit".equalsIgnoreCase(audit))	// 已审核
    			{
    				if(null == keywords ||"".equalsIgnoreCase(keywords))
    				{
    					goodsPage = tdProviderGoodsService.findByProviderIdAndIsAudit(providerId, true, page, size);
    				}else{
    					goodsPage = tdProviderGoodsService.searchAndProviderIdAndIsAudit(providerId, keywords, true, page, size);
    				}
    			}
    			else if("isNotAudit".equalsIgnoreCase(audit)) // 未审核
    			{
    				if(null == keywords || "".equalsIgnoreCase(keywords))
    				{
    					goodsPage = tdProviderGoodsService.findByProviderIdAndIsAudit(providerId, false, page, size);
    				}else{
    					goodsPage = tdProviderGoodsService.searchAndProviderIdAndIsAudit(providerId, keywords, false, page, size);
    				}
    			}
    			else
    			{
    				if(null == keywords || "".equalsIgnoreCase(keywords))
    				{
    					goodsPage = tdProviderGoodsService.findByProviderId(providerId, page, size);
    				}else{
    					goodsPage = tdProviderGoodsService.searchAndProviderIdAndKeywords(providerId, keywords, page, size);
    				}
    			}
    		}
        }
        
        map.addAttribute("content_page", goodsPage);
        
        // 参数注回
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("keywords", keywords);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        map.addAttribute("providerId", providerId);
        map.addAttribute("audit", audit);
        map.addAttribute("distribution", distribution);
        
    	return "/site_mag/provider_goods_list";
    }
    
    @RequestMapping(value="/goods/edit")
    public String goodsEdit(Long id, String __EVENTTARGET,
            String __EVENTARGUMENT, String __VIEWSTATE, ModelMap map,
            HttpServletRequest req){
    	 String username = (String) req.getSession().getAttribute("manager");
         if (null == username) {
             return "redirect:/Verwalter/login";
         }
         map.addAttribute("__EVENTTARGET", __EVENTTARGET);
         map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
         map.addAttribute("__VIEWSTATE", __VIEWSTATE);
         
         
    	if(null != id)
    	{
    		TdProviderGoods providerGoods = tdProviderGoodsService.findOne(id);
    		map.addAttribute("providerGoods",providerGoods);
    		map.addAttribute("providerId", tdProviderGoodsService.findProviderId(id));
    	}
         
    	return "/site_mag/provider_goods_edit";
    }
    
    @RequestMapping(value="/goods/save")
    public String save(TdProviderGoods providerGoods,
    		String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE,
    		HttpServletRequest req,ModelMap map){
    	String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        tdProviderGoodsService.save(providerGoods);
        return "redirect:/Verwalter/provider/goods/list?__EVENTTARGET=" + __EVENTTARGET
                + "&__EVENTARGUMENT=" + __EVENTARGUMENT + "&__VIEWSTATE="
                + __VIEWSTATE;
    }
    
    @ModelAttribute
    public void getModel(@RequestParam(value = "id", required = false) Long id,
                        Model model) {
        if (null != id) {
            model.addAttribute("tdProvider", tdProviderService.findOne(id));
        }
    }
    
    private void btnSave(Long[] ids, Long[] sortIds)
    {
        if (null == ids || null == sortIds
                || ids.length < 1 || sortIds.length < 1)
        {
            return;
        }
        
        for (int i = 0; i < ids.length; i++)
        {
            Long id = ids[i];
            
            TdProvider e = tdProviderService.findOne(id);
            
            if (null != e)
            {
                if (sortIds.length > i)
                {
                    e.setSortId(sortIds[i]);
                    tdProviderService.save(e);
                }
            }
        }
    }
    
    private void btnGoodsDelete(Long[] ids, Integer[] chkIds) {
        if (null == ids || null == chkIds || ids.length < 1
                || chkIds.length < 1) {
            return;
        }

        for (int chkId : chkIds) {
            if (chkId >= 0 && ids.length > chkId) {
                Long id = ids[chkId];

                tdProviderGoodsService.delete(id);
            }
        }
    }
    
    private void btnDelete(Long[] ids, Integer[] chkIds)
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
                
                tdProviderService.delete(id);
            }
        }
    }
}
