package com.ynyes.cslm.controller.management;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ynyes.cslm.entity.TdArticle;
import com.ynyes.cslm.entity.TdArticleCategory;
import com.ynyes.cslm.service.TdArticleCategoryService;
import com.ynyes.cslm.service.TdArticleService;
import com.ynyes.cslm.service.TdBrandService;
import com.ynyes.cslm.service.TdDistributorService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdManagerLogService;
import com.ynyes.cslm.service.TdParameterCategoryService;
import com.ynyes.cslm.service.TdParameterService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.service.TdProductService;
import com.ynyes.cslm.service.TdWarehouseService;

/**
 * 后台首页控制器
 * 
 * @author Sharon
 */

@Controller
@RequestMapping(value="/Verwalter")
public class TdManagerEditController {
    
    @Autowired
    TdArticleCategoryService tdArticleCategoryService;
    
    @Autowired
    TdArticleService tdArticleService;
    
    @Autowired
    TdProductCategoryService tdProductCategoryService;
    
    @Autowired
    TdGoodsService tdGoodsService;
    
    @Autowired
    TdWarehouseService tdWarehouseService;
    
    @Autowired
    TdManagerLogService tdManagerLogService;
    
    @Autowired
    TdParameterCategoryService tdParameterCategoryService;
    
    @Autowired
    TdParameterService tdParameterService;
    
    @Autowired
    TdProductService tdProductService;
    
    @Autowired
    TdBrandService tdBrandService;
    
    @Autowired
    TdDistributorService tdDistributorService;
    
    @RequestMapping(value="/category/edit")
    public String categoryEditDialog(Long cid, Long mid, Long id, Long sub, 
                                    String __EVENTTARGET,
                                    String __EVENTARGUMENT,
                                    String __VIEWSTATE,
                                    ModelMap map,
                                    HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }

        map.addAttribute("cid", cid);
        map.addAttribute("mid", mid);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
//        if (null != cid && cid.equals(2L)) // 商品分类
//        {
//            map.addAttribute("category_list", tdProductCategoryService.findAll());
//            
//            // 参数类型表
//            map.addAttribute("param_category_list", tdParameterCategoryService.findAll());
//            
//            if (null != sub) // 添加子类
//            {
//                if (null != id)
//                {
//                    map.addAttribute("fatherCat", tdProductCategoryService.findOne(id));
//                }
//            }
//            else
//            {
//                if (null != id)
//                {
//                    map.addAttribute("cat", tdProductCategoryService.findOne(id));
//                }
//            }
//            
//            return "/site_mag/product_category_edit";
//        }
//        else
        {
            if (null != mid)
            {
                map.addAttribute("category_list", tdArticleCategoryService.findByMenuId(mid));
            }
            
            if (null != id)
            {
                if (null != sub) // 添加子类
                {
                    map.addAttribute("fatherCat", tdArticleCategoryService.findOne(id));
                }
                else
                {
                    map.addAttribute("cat", tdArticleCategoryService.findOne(id));
                }
            }
            
            return "/site_mag/article_category_edit";
        }
    }
    
    @RequestMapping(value="/category/save", method = RequestMethod.POST)
    public String save(TdArticleCategory cat, 
                        String __EVENTTARGET,
                        String __EVENTARGUMENT,
                        String __VIEWSTATE,
                        ModelMap map,
                        HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username)
        {
            return "redirect:/Verwalter/login";
        }
        
        if (null == cat.getId())
        {
            tdManagerLogService.addLog("add", "用户修改文章分类"+cat.getTitle(), req);
        }
        else
        {
            tdManagerLogService.addLog("edit", "用户修改文章分类"+cat.getTitle(), req);
        }
        
        tdArticleCategoryService.save(cat);
        
        return "redirect:/Verwalter/category/list?cid=" + cat.getChannelId() 
                + "&mid=" + cat.getMenuId()
                + "&__EVENTTARGET=" + __EVENTTARGET
                + "&__EVENTARGUMENT=" + __EVENTARGUMENT
                + "&__VIEWSTATE=" + __VIEWSTATE;
    }
    
    @RequestMapping(value="/article/edit")
    public String articleEditDialog(Long cid, Long mid, Long pid, Long id, 
            String __EVENTTARGET,
            String __EVENTARGUMENT,
            String __VIEWSTATE,
            ModelMap map,
            HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
        if (null != id)
        {
            map.addAttribute("article", tdArticleService.findOne(id));
        }
        
        map.addAttribute("cid", cid);
        map.addAttribute("mid", mid);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        
        map.addAttribute("dis_list", tdDistributorService.findByIsEnableTrue());
        
        if (null != mid)
        {
            map.addAttribute("category_list", tdArticleCategoryService.findByMenuId(mid));
        }
        
//        if (null != cid)
//        {
//            if (cid.equals(5L)) // 产品
//            {
//                map.addAttribute("category_list", tdProductCategoryService.findAll());
//                return "/site_mag/product_edit";
//            }
//            else if (cid.equals(2L)) // 商品
//            {
//                map.addAttribute("category_list", tdProductCategoryService.findAll());
//                
//                if (null != id)
//                {
//                    TdGoods tdGoods = tdGoodsService.findOne(id);
//                    
//                    if (null != tdGoods)
//                    {
//                        // 参数列表
//                        TdProductCategory tpc = tdProductCategoryService.findOne(tdGoods.getCategoryId());
//                        
//                        if (null != tpc && null != tpc.getParamCategoryId())
//                        {
//                            map.addAttribute("param_list", tdParameterService.findByCategoryTreeContaining(tpc.getParamCategoryId()));
//                        }
//                        
//                        // 查找产品列表
//                        map.addAttribute("product_list", tdProductService.findByProductCategoryTreeContaining(tdGoods.getCategoryId()));
//                    
//                        // 查找品牌
//                        map.addAttribute("brand_list", tdBrandService.findByProductCategoryTreeContaining(tdGoods.getCategoryId()));
//                        
//                        map.addAttribute("warehouse_list", tdWarehouseService.findAll());
//                        
//                        if (null != tdGoods.getProductId())
//                        {
//                            map.addAttribute("product", tdProductService.findOne(tdGoods.getProductId()));
//                        }
//                        
//                        map.addAttribute("goods", tdGoods);
//                    }
//                }
//                
//                return "/site_mag/goods_edit";
//            }
//        }
        
        return "/site_mag/article_content_edit";
    }
    
    @RequestMapping(value="/article/save", method = RequestMethod.POST)
    public String save(TdArticle article, 
            String __EVENTTARGET,
            String __EVENTARGUMENT,
            String __VIEWSTATE,
            ModelMap map,
            HttpServletRequest req){
        String username = (String) req.getSession().getAttribute("manager");
        if (null == username) {
            return "redirect:/Verwalter/login";
        }
        
        if (null == article.getId())
        {
            tdManagerLogService.addLog("add", "用户新增文章"+article.getTitle(), req);
        }
        else
        {
            tdManagerLogService.addLog("edit", "用户修改文章"+article.getTitle(), req);
        }
        tdArticleService.save(article);
        
        
        return "redirect:/Verwalter/content/list?cid=" + article.getChannelId() 
                + "&mid=" + article.getMenuId()
                + "&__EVENTTARGET=" + __EVENTTARGET
                + "&__EVENTARGUMENT=" + __EVENTARGUMENT
                + "&__VIEWSTATE=" + __VIEWSTATE;
    }
    
}
