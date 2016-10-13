package com.ynyes.cslm.controller.management;

import static org.apache.commons.lang3.StringUtils.leftPad;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdCash;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdPayRecord;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdProviderGoods;
import com.ynyes.cslm.entity.TdSetting;
import com.ynyes.cslm.service.TdCashService;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdManagerLogService;
import com.ynyes.cslm.service.TdPayRecordService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.service.TdProviderGoodsService;
import com.ynyes.cslm.service.TdProviderService;
import com.ynyes.cslm.service.TdSettingService;
import com.ynyes.cslm.util.ClientConstant;
import com.ynyes.cslm.util.FileDownUtils;
import com.ynyes.cslm.util.SiteMagConstant;
import com.ynyes.cslm.util.StringUtils;

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
    
    @Autowired
    TdDistributorGoodsService tdDistributorGoodsService;
    
    @Autowired
    TdCashService tdCashService;
    
    @Autowired
    TdGoodsService tdGoodsService;
    
    @Autowired
    TdSettingService tdSettingService;
    
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
    		String type, 
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
    	if("add".equals(type)){
    		if(null != provider.getVirtualMoney())
    		{
    			provider.setVirtualMoney(provider.getVirtualMoney()+data);
    		}else{
    			provider.setVirtualMoney(data);
    		}
    	}else if("del".equals(type)){
    		if(null == provider.getVirtualMoney() || provider.getVirtualMoney() < data){
    			res.put("msg","账号余额不足");
    			return res;
    		}
    		provider.setVirtualMoney(provider.getVirtualMoney()-data);
    	}
        tdProviderService.save(provider);
        
        TdPayRecord record = new TdPayRecord();
        
        Date current = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String curStr = sdf.format(current);
        Random random = new Random();
        String number="";
        if("add".equals(type)){
        	number = "CZ" + curStr + leftPad(Integer.toString(random.nextInt(999)), 3, "0");
        	record.setCont("平台充值");
        }else if("del".equals(type)){
        	number = "K" + curStr + leftPad(Integer.toString(random.nextInt(999)), 3, "0");
        	record.setCont("平台扣款");
        }
        
        record.setCreateTime(new Date());
        record.setProviderId(id);
        record.setProviderTitle(provider.getTitle());
        record.setStatusCode(1);
        record.setProvice(data);
        record.setOrderNumber(number);
        
        tdPayRecordService.save(record);
    	
        TdSetting setting = tdSettingService.findTopBy();
        if("add".equals(type)){
	 		if( null != setting.getVirtualMoney())
	        {
	        	setting.setVirtualMoney(setting.getVirtualMoney()-data);
	        }else{
	        	setting.setVirtualMoney(0-data);
	        }
        }else if("del".equals(type)){
        	if( null != setting.getVirtualMoney())
	        {
	        	setting.setVirtualMoney(setting.getVirtualMoney()+data);
	        }else{
	        	setting.setVirtualMoney(data);
	        }
        }
        tdSettingService.save(setting); // 更新平台虚拟余额
        
        // 记录平台支出
        record = new TdPayRecord();
        if("add".equals(type)){
        	record.setCont("手动给"+provider.getTitle()+"充值");
        }else if("del".equals(type)){
        	record.setCont("手动给"+provider.getTitle()+"扣款");
        }
        record.setCreateTime(new Date());
        record.setDistributorTitle(provider.getTitle());
        record.setOrderNumber(number);
        record.setStatusCode(1);
        record.setType(1L); // 类型 区分平台记录
        
        record.setProvice(data); // 订单总额
        record.setPostPrice(0.0); // 邮费
        record.setAliPrice(0.0);	// 第三方费
        record.setServicePrice(0.0);	// 平台费
        record.setTotalGoodsPrice(data); // 商品总价
        // 
        record.setRealPrice(data);
        
        tdPayRecordService.save(record);
        
        // 充值记录
        if("add".equals(type)){
	        TdCash cash = new TdCash();
	    	cash.setCashNumber("平台代充："+number);
	    	cash.setShopTitle(provider.getTitle());
	    	cash.setUsername(provider.getUsername());
	    	cash.setCreateTime(new Date());
	    	cash.setPrice(data); // 金额
	    	if (provider.getType() ==1) {
	    		cash.setShopType(2L); // 类型-批发商
			}else {
				cash.setShopType(3L); // 类型-分销商
			}
	    	cash.setType(1L); // 类型-充值
	    	cash.setStatus(2L); // 状态 完成
	    	
	    	tdCashService.save(cash);
	        
	        tdManagerLogService.addLog("add", "手动给"+provider.getTitle()+"充值"+data, req);
        }
        res.put("code", 0);
        res.put("message", "操作成功！");
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
        if(null == tdProvider.getPayPassword() || "".equals(tdProvider.getPayPassword().trim()))
        {
        	// 设置初始密码
        	tdProvider.setPayPassword(tdProvider.getPassword());
        }
        
        if (null == tdProvider.getId())
        {
            tdManagerLogService.addLog("add", "用户新增供应商"+tdProvider.getTitle(), req);
        }
        else
        {
            tdManagerLogService.addLog("edit", "用户修改供应商"+tdProvider.getTitle(), req);
        }
        
        tdProviderService.save(tdProvider);
        
        return "redirect:/Verwalter/provider/list";
    }
    
    @RequestMapping(value="/goods/list")
    public String goodsList(Integer page,
    				Integer size,
					String distribution, 
					String onSale,
					Long providerId,
					Long categoryId,
					String __EVENTTARGET,
					String __EVENTARGUMENT,
					String __VIEWSTATE, 
					String keywords,
					Long[] listId, Integer[] listChkId, Long[] listSortId,
					ModelMap map,
					HttpServletRequest req,HttpServletResponse resp){
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
        String exportUrl="";
        if (null != __EVENTTARGET) {
            switch (__EVENTTARGET) {
            case "lbtnViewTxt":
            case "lbtnViewImg":
                __VIEWSTATE = __EVENTTARGET;
                break;
            case "btnDelete":
                btnGoodsDelete(listId, listChkId);
                tdManagerLogService.addLog("delete", "用户删除商品", req);
                break;
            case "exportAll":
            	exportUrl = SiteMagConstant.backupPath;
                tdManagerLogService.addLog("edit", "用户导出商家商品", req);
                break;
            case "btnPage":
                if (null != __EVENTARGUMENT) {
                    page = Integer.parseInt(__EVENTARGUMENT);
                }
                break;
            }
        }
        
        map.addAttribute("provider_list", tdProviderService.findAll());
        map.addAttribute("category_list", tdProductCategoryService.findAll());
        
        Boolean isDistribution = null;
        Boolean isOnSale = null;
        
        if("isDistribution".equalsIgnoreCase(distribution)) // 分销
		{
        	isDistribution = true;
		}
        else if("isNotDistribution".equalsIgnoreCase(distribution))	// 未分销
		{
			isDistribution = false;
		}
        if("isOnSale".equalsIgnoreCase(onSale))	// 已审核
		{
        	isOnSale = true;
		}
        else if("isNotOnSale".equalsIgnoreCase(onSale)) // 未审核
		{
        	isOnSale = false;
		}
        
        PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
        map.addAttribute("content_page", tdProviderGoodsService.findAll(providerId, categoryId, keywords, isDistribution, isOnSale, pageRequest));
        
        if(null != exportUrl && !"".equals(exportUrl)){
    		  pageRequest = new PageRequest(page, Integer.MAX_VALUE,new Sort(Direction.DESC, "id"));
            
            	// 第一步，创建一个webbook，对应一个Excel文件  
		      HSSFWorkbook wb = new HSSFWorkbook();  
		      // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
		      HSSFSheet sheet = wb.createSheet("goods");  
		      // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
		      HSSFRow row = sheet.createRow((int) 0);  
		      // 第四步，创建单元格，并设置值表头 设置表头居中  
		      HSSFCellStyle style = wb.createCellStyle();  
		      style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		      
		      HSSFCell cell = row.createCell((short) 0);  
		        cell.setCellValue("供应商名称");  
		        cell.setCellStyle(style); 
		        
		        cell = row.createCell((short) 1);  
		        cell.setCellValue("商品名称");  
		        cell.setCellStyle(style); 
		        
		        cell = row.createCell((short) 2);  
		        cell.setCellValue("商品副标题");  
		        cell.setCellStyle(style); 
		        
		        cell = row.createCell((short) 3);  
		        cell.setCellValue("编码");  
		        cell.setCellStyle(style);
		        
		        cell = row.createCell((short) 4);  
		        cell.setCellValue("所属分类");  
		        cell.setCellStyle(style);
		        
		        cell = row.createCell((short) 5);  
		        cell.setCellValue("品牌");  
		        cell.setCellStyle(style); 
		        
		        cell = row.createCell((short) 6);  
		        cell.setCellValue("销售单位");  
		        cell.setCellStyle(style); 
		        
		        cell = row.createCell((short) 7);  
		        cell.setCellValue("批发价");  
		        cell.setCellStyle(style); 
		        
		        cell = row.createCell((short) 8);  
		        cell.setCellValue("原价");  
		        cell.setCellStyle(style); 
		        
		        cell = row.createCell((short) 9);  
		        cell.setCellValue("商家库存");  
		        cell.setCellStyle(style); 
		      
		      Page<TdProviderGoods> goodsPage = tdProviderGoodsService.findAll(providerId, categoryId, keywords, isDistribution, isOnSale, pageRequest);
			if (providerGoodsImport(goodsPage, row, cell, sheet)) {
				FileDownUtils.download("goods", wb, exportUrl, resp);
			} 
        }
        
        
        // 参数注回
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("keywords", keywords);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        map.addAttribute("providerId", providerId);
        map.addAttribute("onSale", onSale);
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
    public String save(TdProviderGoods tdProviderGoods,
    		String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE,
    		HttpServletRequest req,ModelMap map){
    	String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        tdProviderGoodsService.save(tdProviderGoods);
        
        TdProvider provider = tdProviderService.findOne(tdProviderGoods.getProId());
        if(null != provider){
        	List<TdDistributorGoods> list = tdDistributorGoodsService.findByProviderIdAndGoodsIdAndIsDistributionTrue(provider.getId(),tdProviderGoods.getGoodsId());
        	
        	if(null != tdProviderGoods.getIsDistribution() &&  tdProviderGoods.getIsDistribution() ==true){
        		if(null != list && list.size() >0){
        			for (TdDistributorGoods tdDistributorGoods : list) {
        				if(null != tdDistributorGoods)
        				{
        					tdDistributorGoods.setGoodsPrice(tdProviderGoods.getOutFactoryPrice());
        					tdDistributorGoods.setSubGoodsTitle(tdProviderGoods.getSubGoodsTitle());
        					tdDistributorGoods.setLeftNumber(tdProviderGoods.getLeftNumber());
        					tdDistributorGoods.setUnit(tdProviderGoods.getUnit());
        					tdDistributorGoods.setGoodsMarketPrice(tdProviderGoods.getGoodsMarketPrice());
        					
        					tdDistributorGoodsService.save(tdDistributorGoods);
        				}
        			}
        		}
        		
        	}else if(null != tdProviderGoods.getIsDistribution() &&  tdProviderGoods.getIsDistribution() ==false){
        		// 取消分销后 超市商品库删除分销商品
        		if(null != list && list.size() >0){
        			for (TdDistributorGoods tdDistributorGoods : list) {
        				tdDistributorGoodsService.delete(tdDistributorGoods);
        			}
        		}
        	}
        }
        
        tdManagerLogService.addLog("edit", "用户修改"+tdProviderGoods.getProviderTitle()+"商品："+tdProviderGoods.getGoodsTitle(), req);
        
        return "redirect:/Verwalter/provider/goods/list?__EVENTTARGET=" + __EVENTTARGET
                + "&__EVENTARGUMENT=" + __EVENTARGUMENT + "&__VIEWSTATE="
                + __VIEWSTATE;
    }
    
    @ModelAttribute
    public void getModel(@RequestParam(value = "id", required = false) Long id,
    		@RequestParam(value = "providerId", required = false) Long providerId, Model model) {
        if (null != id) {
            model.addAttribute("tdProvider", tdProviderService.findOne(id));
        }
        if(null != providerId)
        {
        	model.addAttribute("tdProviderGoods", tdProviderGoodsService.findOne(providerId));
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
    
    @SuppressWarnings("deprecation")
	public Boolean providerGoodsImport(Page<TdProviderGoods> goodsPage,HSSFRow row, HSSFCell cell, HSSFSheet sheet)
	{
		if(null != goodsPage && goodsPage.getContent().size() >0){
			for (int i = 0; i < goodsPage.getContent().size(); i++) {
				row = sheet.createRow((int)i+1);
				TdProviderGoods providerGoods = goodsPage.getContent().get(i);
				
				row.createCell((short) 0).setCellValue(providerGoods.getProviderTitle());
				row.createCell((short) 1).setCellValue(providerGoods.getGoodsTitle());
				row.createCell((short) 2).setCellValue(providerGoods.getSubGoodsTitle());
				row.createCell((short) 3).setCellValue(providerGoods.getCode());
				
				TdProductCategory category = tdProductCategoryService.findOne(providerGoods.getCategoryId());
				if(null != category){
					row.createCell((short) 4).setCellValue(category.getTitle());
				}
				TdGoods goods = tdGoodsService.findOne(providerGoods.getGoodsId());
				if(null != goods){
					row.createCell((short) 5).setCellValue(goods.getBrandTitle());
				}
				row.createCell((short) 6).setCellValue(providerGoods.getUnit());
				row.createCell((short) 7).setCellValue(StringUtils.scale(providerGoods.getOutFactoryPrice()));
				row.createCell((short) 8).setCellValue(StringUtils.scale(providerGoods.getGoodsMarketPrice()));
				row.createCell((short) 9).setCellValue(providerGoods.getLeftNumber());
			}
		}
		return true;
	}
}
