package com.ynyes.cslm.controller.management;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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
        
        if(null == distributorId) // 未选择超市
        {
        	if(null == categoryId)	// 未选择分类
        	{
        		if("isOnSale".equalsIgnoreCase(onsale)) // 上架 
        		{
    				if("isAudit".equalsIgnoreCase(audit))	// 审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByIsOnSaleAndIsAudit(true,  true, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndIsOnSaleAndIsAudit(keywords, true,  true, page, size);
    					}
    				}
    				else if("isNotAudit".equalsIgnoreCase(audit)) // 未审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByIsOnSaleAndIsAudit(true,  false, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndIsOnSaleAndIsAudit(keywords, true, false, page, size);
    					}
    				}
    				else //未选是否审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByIsOnSale(true,  page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndIsOnSale(keywords, true, page, size);
    					}
    				}
        		}	// -----------上架  END ----------------
        		else if("isNotOnSale".equalsIgnoreCase(onsale))	 // 下架
        		{
    				if("isAudit".equalsIgnoreCase(audit))	// 审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByIsOnSaleAndIsAudit(false,  true, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndIsOnSaleAndIsAudit(keywords, false,  true, page, size);
    					}
    				}
    				else if("isNotAudit".equalsIgnoreCase(audit)) // 未审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByIsOnSaleAndIsAudit(false,  false, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndIsOnSaleAndIsAudit(keywords, false, false, page, size);
    					}
    				}
    				else //未选是否审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByIsOnSale(false,  page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndIsOnSale(keywords, false, page, size);
    					}
    				}
        		} //------------- 下架→是否分销  END ----------------------
        		else // 未选上下架	
        		{
    				if("isAudit".equalsIgnoreCase(audit))	// 审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByIsAudit( true, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndIsAudit(keywords,  true, page, size);
    					}
    				}
    				else if("isNotAudit".equalsIgnoreCase(audit)) // 未审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByIsAudit(  false, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndIsAudit(keywords,  false, page, size);
    					}
    				}
    				else //未选是否审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findAll(page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchDistributorGoods(keywords, page, size);
    					}
    				}
        		} //------- 未选是否上架→ END ---------------------------
        	}	// ---------- 未选分类 END  ---------------------
        	else	// 选择分类
        	{
        		if("isOnSale".equalsIgnoreCase(onsale)) // 上架 
        		{
    				if("isAudit".equalsIgnoreCase(audit))	// 审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByCategoryIdAndIsOnSaleAndIsAudit(categoryId,true,  true, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndCategoryIdAndIsOnSaleAndIsAudit(categoryId,keywords, true,  true, page, size);
    					}
    				}
    				else if("isNotAudit".equalsIgnoreCase(audit)) // 未审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByCategoryIdAndIsOnSaleAndIsAudit(categoryId,true,  false, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndCategoryIdAndIsOnSaleAndIsAudit(categoryId,keywords, true, false, page, size);
    					}
    				}
    				else //未选是否审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByCategoryIdAndIsOnSale(categoryId,true,  page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndCategoryIdAndIsOnSale(categoryId,keywords, true, page, size);
    					}
    				}
        		}	// -----------上架  END ----------------
        		else if("isNotOnSale".equalsIgnoreCase(onsale))	 // 下架
        		{
    				if("isAudit".equalsIgnoreCase(audit))	// 审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByCategoryIdAndIsOnSaleAndIsAudit(categoryId,false,  true, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndCategoryIdAndIsOnSaleAndIsAudit(categoryId,keywords, false,  true, page, size);
    					}
    				}
    				else if("isNotAudit".equalsIgnoreCase(audit)) // 未审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByCategoryIdAndIsOnSaleAndIsAudit(categoryId,false,  false, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndCategoryIdAndIsOnSaleAndIsAudit(categoryId,keywords, false, false, page, size);
    					}
    				}
    				else //未选是否审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByCategoryIdAndIsOnSale(categoryId,false,  page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndCategoryIdAndIsOnSale(categoryId,keywords, false, page, size);
    					}
    				}
        		} //------------- 下架  END ----------------------
        		else // 未选上下架	
        		{
    				if("isAudit".equalsIgnoreCase(audit))	// 审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByCategoryIdAndIsAudit(categoryId, true, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndCategoryIdAndIsAudit(categoryId,keywords,  true, page, size);
    					}
    				}
    				else if("isNotAudit".equalsIgnoreCase(audit)) // 未审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByCategoryIdAndIsAudit( categoryId, false, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndCategoryIdAndIsAudit(categoryId,keywords,  false, page, size);
    					}
    				}
    				else //未选是否审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByCategoryId(categoryId, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndCategoryId(categoryId, keywords, page, size);
    					}
    				}
        		}
        	} // ----- 选择分类 END ----------------------
        }
        else // 选择超市----------------- start ------------------------
        {
        	if(null == categoryId)	// 未选择分类
        	{
        		if("isOnSale".equalsIgnoreCase(onsale)) // 上架 
        		{
    				if("isAudit".equalsIgnoreCase(audit))	// 审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByDistributorIdAndIsOnSaleAndIsAudit(distributorId,true,  true, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndDistributorIdAndIsOnSaleAndIsAudit(distributorId,keywords, true,  true, page, size);
    					}
    				}
    				else if("isNotAudit".equalsIgnoreCase(audit)) // 未审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByDistributorIdAndIsOnSaleAndIsAudit(distributorId,true,  false, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndDistributorIdAndIsOnSaleAndIsAudit(distributorId,keywords, true, false, page, size);
    					}
    				}
    				else //未选是否审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByDistributorIdAndIsOnSale(distributorId,true,  page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndDistributorIdAndIsOnSale(distributorId,keywords, true, page, size);
    					}
    				}
        		}	// -----------  超市 上架  END ----------------
        		else if("isNotOnSale".equalsIgnoreCase(onsale))	 // 下架
        		{
    				if("isAudit".equalsIgnoreCase(audit))	// 审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByDistributorIdAndIsOnSaleAndIsAudit(distributorId,false,  true, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndDistributorIdAndIsOnSaleAndIsAudit(distributorId,keywords, false,  true, page, size);
    					}
    				}
    				else if("isNotAudit".equalsIgnoreCase(audit)) // 未审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByDistributorIdAndIsOnSaleAndIsAudit(distributorId,false,  false, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndDistributorIdAndIsOnSaleAndIsAudit(distributorId,keywords, false, false, page, size);
    					}
    				}
    				else //未选是否审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByDistributorIdAndIsOnSale(distributorId,false,  page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndDistributorIdAndIsOnSale(distributorId,keywords, false, page, size);
    					}
    				}
        		} //-------------  超市 下架  END ----------------------
        		else // 未选上下架	
        		{
    				if("isAudit".equalsIgnoreCase(audit))	// 审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByDistributorIdAndIsAudit(distributorId, true, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndDistributorIdAndIsAudit(distributorId,keywords,  true, page, size);
    					}
    				}
    				else if("isNotAudit".equalsIgnoreCase(audit)) // 未审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByDistributorIdAndIsAudit(distributorId,  false, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndDistributorIdAndIsAudit(distributorId,keywords,  false, page, size);
    					}
    				}
    				else //未选是否审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByDistributorId(distributorId, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndDistributorId(distributorId, keywords, page, size);
    					}
    				}
        		} //------- 超市 未选是否上架→ END ---------------------------
        	}	// ---------- 超市 未选分类 END  ---------------------
        	else	// 超市 选择分类
        	{
        		if("isOnSale".equalsIgnoreCase(onsale)) // 上架 
        		{
    				if("isAudit".equalsIgnoreCase(audit))	// 审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByDistributorIdAndCategoryIdAndIsOnSaleAndIsAudit(distributorId,categoryId,true,  true, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndDistributorIdAndCategoryIdAndIsOnSaleAndIsAudit(distributorId,categoryId,keywords, true,  true, page, size);
    					}
    				}
    				else if("isNotAudit".equalsIgnoreCase(audit)) // 未审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByDistributorIdAndCategoryIdAndIsOnSaleAndIsAudit(distributorId,categoryId,true,  false, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndDistributorIdAndCategoryIdAndIsOnSaleAndIsAudit(distributorId,categoryId,keywords, true, false, page, size);
    					}
    				}
    				else //未选是否审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByDistributorIdAndCategoryIdAndIsOnSale(distributorId,categoryId,true,  page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndDistributorIdAndCategoryIdAndIsOnSale(distributorId,categoryId,keywords, true, page, size);
    					}
    				}
        		}	// ----------- 超市 分类 上架  END ----------------
        		else if("isNotOnSale".equalsIgnoreCase(onsale))	 // 下架
        		{
    				if("isAudit".equalsIgnoreCase(audit))	// 审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByDistributorIdAndCategoryIdAndIsOnSaleAndIsAudit(distributorId,categoryId,false,  true, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndDistributorIdAndCategoryIdAndIsOnSaleAndIsAudit(distributorId,categoryId,keywords, false,  true, page, size);
    					}
    				}
    				else if("isNotAudit".equalsIgnoreCase(audit)) // 未审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByDistributorIdAndCategoryIdAndIsOnSaleAndIsAudit(distributorId,categoryId,false,  false, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndDistributorIdAndCategoryIdAndIsOnSaleAndIsAudit(distributorId,categoryId,keywords, false, false, page, size);
    					}
    				}
    				else //未选是否审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByDistributorIdAndCategoryIdAndIsOnSale(distributorId,categoryId,false,  page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndDistributorIdAndCategoryIdAndIsOnSale(distributorId,categoryId,keywords, false, page, size);
    					}
    				}
        		} //------------- 超市 分类 下架  END ----------------------
        		else // 未选上下架	
        		{
    				if("isAudit".equalsIgnoreCase(audit))	// 审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByDistributorIdAndCategoryIdAndIsAudit(distributorId,categoryId, true, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndDistributorIdAndCategoryIdAndIsAudit(distributorId,categoryId,keywords,  true, page, size);
    					}
    				}
    				else if("isNotAudit".equalsIgnoreCase(audit)) // 未审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByDistributorIdAndCategoryIdAndIsAudit(distributorId, categoryId, false, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndDistributorIdAndCategoryIdAndIsAudit(distributorId,categoryId,keywords,  false, page, size);
    					}
    				}
    				else //未选是否审核
    				{
    					if(null == keywords || "".equalsIgnoreCase(keywords))
    					{
    						goodsPage = tdDistributorGoodsService.findByDistributorIdAndCategoryId(distributorId,categoryId, page, size);
    					}else{
    						goodsPage = tdDistributorGoodsService.searchAndDistributorIdAndCategoryId(distributorId,categoryId, keywords, page, size);
    					}
    				}
        		} // ------- 超市  分类 END  ----------------
        	} // ------  超市==分类   END  ----------------------- 
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
        
        tdDistributorGoodsService.save(tdDistributorGoods);
        return "redirect:/Verwalter/distributor/goods/list?__EVENTTARGET=" + __EVENTTARGET
                + "&__EVENTARGUMENT=" + __EVENTARGUMENT + "&__VIEWSTATE="
                + __VIEWSTATE;
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
