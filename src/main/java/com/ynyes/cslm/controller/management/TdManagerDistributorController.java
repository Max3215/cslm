package com.ynyes.cslm.controller.management;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.entity.TdProviderGoods;
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdManagerLogService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.util.FileDownUtils;
import com.ynyes.cslm.util.SiteMagConstant;
import com.ynyes.cslm.util.StringUtils;

/**
 * 后台超市商品控制器
 * 
 * @author libiao
 *
 */

@Controller
@RequestMapping(value="/Verwalter/distributor")
public class TdManagerDistributorController {
	
	@Autowired
	TdDistributorService tdDistributorService;
	
	@Autowired
	TdDistributorGoodsService tdDistributorGoodsService;
	
	@Autowired
	TdManagerLogService tdManagerLogService;
	
	@Autowired
	TdProductCategoryService tdProductCategoryService;
	
	@Autowired
	TdGoodsService tdGoodsService;
	
	@RequestMapping(value="/goods/list")
	public String goodsList(Integer page,
				Integer size,
				String distribution, 
				String audit,
				Long distributorId,
				String keywords,
				String onsale,
				Long categoryId,
				String __EVENTTARGET,
				String __EVENTARGUMENT,
				String __VIEWSTATE, 
				Long[] listId, 
				Integer[] listChkId, 
				Long[] listSortId,
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
        
        String exportUrl ="";
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
                tdManagerLogService.addLog("edit", "用户导出超市商品", req);
                break;
            case "btnPage":
                if (null != __EVENTARGUMENT) {
                    page = Integer.parseInt(__EVENTARGUMENT);
                }
                break;
            }
        }
        
        map.addAttribute("category_list", tdProductCategoryService.findAll());
        map.addAttribute("dis_list", tdDistributorService.findByIsEnableTrue());
        
        Page<TdDistributorGoods> goodsPage = null;
        
        PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "onSaleTime"));
        
        if("isOnSale".equalsIgnoreCase(onsale)){
			goodsPage = tdDistributorGoodsService.findAll(distributorId, true, categoryId, keywords, pageRequest);
        }else if("isNotOnSale".equalsIgnoreCase(onsale)){
        	goodsPage = tdDistributorGoodsService.findAll(distributorId, false, categoryId, keywords, pageRequest);
        }else{
        	goodsPage = tdDistributorGoodsService.findAll(distributorId, null, categoryId, keywords, pageRequest);
        }
        
        map.addAttribute("content_page", goodsPage);
       
        if(null != exportUrl && !"".equals(exportUrl)){
        	pageRequest = new PageRequest(page, Integer.MAX_VALUE,new Sort(Direction.DESC, "onSaleTime"));
            
            if("isOnSale".equalsIgnoreCase(onsale)){
    			goodsPage = tdDistributorGoodsService.findAll(distributorId, true, categoryId, keywords, pageRequest);
            }else if("isNotOnSale".equalsIgnoreCase(onsale)){
            	goodsPage = tdDistributorGoodsService.findAll(distributorId, false, categoryId, keywords, pageRequest);
            }else{
            	goodsPage = tdDistributorGoodsService.findAll(distributorId, null, categoryId, keywords, pageRequest);
            }
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
		      cell.setCellValue("商品标题");  
		      cell.setCellStyle(style);  
		      cell = row.createCell((short) 1);  
		      cell.setCellValue("商品副标题");  
		      cell.setCellStyle(style);  
		      cell = row.createCell((short) 2);  
		      cell.setCellValue("商家");  
		      cell.setCellStyle(style);  
		      cell = row.createCell((short) 3);  
		      cell.setCellValue("编码");  
		      cell.setCellStyle(style);
		      cell = row.createCell((short) 4);  
		      cell.setCellValue("原价");  
		      cell.setCellStyle(style);
		      cell = row.createCell((short) 5);  
		      cell.setCellValue("销售价");  
		      cell.setCellStyle(style);
		      cell = row.createCell((short) 6);  
		      cell.setCellValue("上架时间");  
		      cell.setCellStyle(style);
		      cell = row.createCell((short) 7);  
	  	      cell.setCellValue("单位");  
	  	      cell.setCellStyle(style);
	  	      cell = row.createCell((short) 8);  
		      cell.setCellValue("类别");  
		      cell.setCellStyle(style);
		      cell = row.createCell((short) 9);  
	  	      cell.setCellValue("品牌");  
	  	      cell.setCellStyle(style);
	  	      cell = row.createCell((short) 10);  
		      cell.setCellValue("库存");  
		      cell.setCellStyle(style);
		      cell = row.createCell((short) 11);  
		      cell.setCellValue("销量");  
		      cell.setCellStyle(style);
		      
			if (distributorGoodsImport(goodsPage, row, cell, sheet)) {
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
        map.addAttribute("categoryId", categoryId);
        map.addAttribute("distributorId", distributorId);
        map.addAttribute("audit",audit);
        map.addAttribute("onsale", onsale);
        
		
		return "/site_mag/dis_goods_list";
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
         map.addAttribute("category_list", tdProductCategoryService.findAll());
         
         if(null != id)
         {
        	 TdDistributorGoods goods = tdDistributorGoodsService.findOne(id);
        	 map.addAttribute("goods",goods);
        	 map.addAttribute("distributorId", tdDistributorGoodsService.findDistributorId(goods.getId()));
         }
         
        return "/site_mag/dis_goods_edit";
	}
	
	@RequestMapping(value="/goods/save")
    public String save(TdDistributorGoods tdDistributorGoods,
    		Boolean isSetRecommend,Boolean isRecommendCategory,
    		Boolean isSetRecommendType,Boolean isSetTouchHot,
    		String __EVENTTARGET, String __EVENTARGUMENT, String __VIEWSTATE,
    		HttpServletRequest req,ModelMap map){
    	String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
        if(null == tdDistributorGoods.getOnSaleTime())
        {
        	tdDistributorGoods.setOnSaleTime(new Date());
        }
        
        // 推荐类型
        if(null != isSetRecommend && isSetRecommend){
        	tdDistributorGoods.setIsSetRecommend(true);
        	tdDistributorGoods.setIsSetRecommendTime(new Date());
        }else{
        	tdDistributorGoods.setIsSetRecommend(false);
        }
        
        if(null != isRecommendCategory && isRecommendCategory){
        	tdDistributorGoods.setIsRecommendCategory(true);
        	tdDistributorGoods.setIsRecommendCategoryTime(new Date());
        }else{
        	tdDistributorGoods.setIsRecommendIndex(false);
        }
        if(null != isSetRecommendType && isSetRecommendType){
        	tdDistributorGoods.setIsSetRecommendType(true);
        	tdDistributorGoods.setIsSetRecommendTypeTime(new Date());
        }else{
        	tdDistributorGoods.setIsSetRecommendType(false);
        }
        if(null != isSetTouchHot && isSetTouchHot){
        	tdDistributorGoods.setIsSetTouchHot(true);
        	tdDistributorGoods.setIsSetTouchHotTime(new Date());
        }else{
        	tdDistributorGoods.setIsSetTouchHot(false);
        }
        
    	tdManagerLogService.addLog("edit", "用户修改"+tdDistributorGoods.getDistributorTitle()+"商品："+tdDistributorGoods.getGoodsTitle(), req);

        
        tdDistributorGoodsService.save(tdDistributorGoods);
        return "redirect:/Verwalter/distributor/goods/list?__EVENTTARGET=" + __EVENTTARGET
                + "&__EVENTARGUMENT=" + __EVENTARGUMENT + "&__VIEWSTATE="
                + __VIEWSTATE;
    }
	
	@ModelAttribute
    public void getModel(@RequestParam(value = "id", required = false) Long id,
                        Model model) {
        if (null != id) {
            model.addAttribute("tdDistributorGoods", tdDistributorGoodsService.findOne(id));
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

                tdDistributorGoodsService.delete(id);
            }
        }
    }
	
	@SuppressWarnings("deprecation")
	public Boolean distributorGoodsImport(Page<TdDistributorGoods> goodsPage,HSSFRow row, HSSFCell cell, HSSFSheet sheet)
	{
		if(null != goodsPage && goodsPage.getContent().size() >0){
			for (int i = 0; i < goodsPage.getContent().size(); i++) {
				System.err.println("--------"+i);
				row = sheet.createRow((int)i+1);
				TdDistributorGoods distributorGoods = goodsPage.getContent().get(i);
				
				row.createCell((short) 0).setCellValue(distributorGoods.getGoodsTitle());
				row.createCell((short) 1).setCellValue(distributorGoods.getSubGoodsTitle());
				row.createCell((short) 2).setCellValue(distributorGoods.getDistributorTitle());
				row.createCell((short) 3).setCellValue(distributorGoods.getCode());
				
				row.createCell((short) 4).setCellValue(StringUtils.scale(distributorGoods.getGoodsMarketPrice()));
				row.createCell((short) 5).setCellValue(StringUtils.scale(distributorGoods.getGoodsPrice()));
				cell = row.createCell((short) 6);  
    			cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(distributorGoods.getOnSaleTime()));
    			row.createCell((short) 7).setCellValue(distributorGoods.getUnit());
    			
				TdProductCategory category = tdProductCategoryService.findOne(distributorGoods.getCategoryId());
				if(null != category){
					row.createCell((short) 8).setCellValue(category.getTitle());
				}
				TdGoods goods = tdGoodsService.findOne(distributorGoods.getGoodsId());
				if(null != goods){
					row.createCell((short) 9).setCellValue(goods.getBrandTitle());
				}
				row.createCell((short) 10).setCellValue(distributorGoods.getLeftNumber());
				if(null != distributorGoods.getSoldNumber()){
					row.createCell((short) 11).setCellValue(distributorGoods.getSoldNumber());
				}
			}
		}
		return true;
	}

}
