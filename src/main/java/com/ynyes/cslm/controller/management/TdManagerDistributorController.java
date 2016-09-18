package com.ynyes.cslm.controller.management;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

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
import com.ynyes.cslm.service.TdDistributorGoodsService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdManagerLogService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.util.SiteMagConstant;

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
	
	
	@RequestMapping(value="/goods/list")
	public String goodsList(Integer page,Integer size,
            String distribution, String __EVENTTARGET,
            String audit,Long distributorId,String onsale,Long categoryId,
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

}
