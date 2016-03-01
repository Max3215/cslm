package com.ynyes.cslm.touch;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ynyes.cslm.entity.TdBrand;
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdParameter;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.service.TdArticleService;
import com.ynyes.cslm.service.TdBrandService;
import com.ynyes.cslm.service.TdCommonService;
import com.ynyes.cslm.service.TdGoodsService;
import com.ynyes.cslm.service.TdParameterService;
import com.ynyes.cslm.service.TdProductCategoryService;
import com.ynyes.cslm.util.ClientConstant;

@Controller
@RequestMapping("/touch")
public class TdTouchListController {
		@Autowired
	    private TdGoodsService tdGoodsService;
	    
	    @Autowired
	    private TdProductCategoryService tdProductCategoryService;
	    
	    @Autowired
	    private TdArticleService tdArticleService;
	    
	    @Autowired
	    private TdCommonService tdCommonService;
	    
	    @Autowired
	    private TdBrandService tdBrandService;
	    
	    @Autowired
	    private TdParameterService tdParameterService;
	    
	    // 组成：typeID-brandIndex-[paramIndex]-[排序字段]-[销量排序标志]-[价格排序标志]-[上架时间排序标志]-[页号]_[价格低值]-[价格高值]
	    //新组成：typeID-brandIndex-[paramIndex]-[排序字段]-[销量排序标志]-[价格排序标志]-[上架时间排序标志]-[人气]-[评价]-[页号]_[价格低值]-[价格高值]
	    @RequestMapping("/list/{listStr}")
	    public String list(@PathVariable String listStr, ModelMap map, HttpServletRequest req){
	    	 tdCommonService.setHeader(map, req);
	         
	         if (null == listStr || "".equals(listStr))
	         {
	             return "/touch/error_404";
	         }
	         // 排序字段个数
	         int totalSorts = 4;
	         
	         // 4个排序字段
	         String[] sortName = {"onSaleTime", "salePrice", "soldNumber","totalComments"};
	         
	         Integer priceLow = null; // 价格低值
	         Integer priceHigh = null; // 价格高值
	         
	         if (listStr.contains("_")) // 包括价格区间
	         {
	             String[] listGroup = listStr.split("_");
	             
	             if (listGroup.length > 1)
	             {
	                 String[] priceGroup = listGroup[1].split("-");
	                 
	                 if (priceGroup.length > 1)
	                 {
	                     priceLow = Integer.parseInt(priceGroup[0]);
	                     priceHigh = Integer.parseInt(priceGroup[1]);
	                 }
	             }
	             
	             listStr = listGroup[0];
	         }
	         
	         map.addAttribute("priceLow", priceLow);
	         map.addAttribute("priceHigh", priceHigh);
	         
	         String[] numberGroup = listStr.split("-");
	         
	         Long categoryId = null;
	         
	         if (numberGroup.length <= 0)
	         {
	             return "/touch/error_404";
	         }
	         
	         // 分类ID
	         categoryId = Long.parseLong(numberGroup[0]);
	         
	         map.addAttribute("categoryId", categoryId);
	         
	         TdProductCategory tdProductCategory = tdProductCategoryService.findOne(categoryId);
	         
	         if (null == tdProductCategory)
	         {
	             return "/touch/error_404";
	         }
	         map.addAttribute("productCategory", tdProductCategory);
	         
	         // 品牌
	         Integer brandIndex = 0;
	         
	         if (numberGroup.length > 1) // 解析品牌
	         {
	             brandIndex = Integer.parseInt(numberGroup[1]);
	         }
	         
	         map.addAttribute("brandIndex", brandIndex);
	         
	         // 品牌列表
	         List<TdBrand> brandList = tdBrandService.findByStatusIdAndProductCategoryTreeContaining(1L, categoryId);
	         
	         map.addAttribute("brand_list", brandList);
	         
	         // 品牌ID
	         Long brandId = null;
	         
	         if (brandIndex.intValue() > 0 && brandList.size() >= brandIndex.intValue())
	         {
	             TdBrand brand = brandList.get(brandIndex - 1);
	             brandId = brand.getId();
	         }
	         
	         // 筛选参数个数
	         Integer paramCount = 0;
	         List<Integer> paramIndexList = new ArrayList<Integer>();
	         List<String> paramValueList = new ArrayList<String>();
	         
	         // 参数列表
	         if (null != tdProductCategory.getParamCategoryId())
	         {
	             Long paramCategoryId = tdProductCategory.getParamCategoryId();
	             
	             List<TdParameter> paramList = tdParameterService.findByCategoryTreeContainingAndIsSearchableTrue(paramCategoryId);
	         
	             paramCount = paramList.size();
	             
	             if (numberGroup.length >= paramCount + 2) // 解析参数
	             {
	                 for (int i=0; i<paramCount; i++)
	                 {
	                     String indexStr = numberGroup[2 + i];
	                     TdParameter param = paramList.get(i);
	                     
	                     if (null != indexStr)
	                     {
	                         Integer paramIndex = Integer.parseInt(indexStr);
	                         paramIndexList.add(paramIndex);
	                         
	                         if (paramIndex > 0 && null != param.getValueList() && !"".equals(param.getValueList()))
	                         {
	                             String[] values = param.getValueList().split(",");
	                             
	                             if (values.length >= paramIndex)
	                             {
	                                 String value = values[paramIndex-1].trim();
	                                 paramValueList.add(value);
	                             }
	                         }
	                     }
	                 }
	             }
	             else
	             {
	                 for (int i=0; i<paramCount; i++)
	                 {
	                     paramIndexList.add(0);
	                 }
	             }
	             
	             map.addAttribute("param_list", paramList);
	         }
	         
	         map.addAttribute("param_count", paramCount);
	         map.addAttribute("param_index_list", paramIndexList);
	         
	         
	         
	         // 排序字段  可能的取值范围为[0...totalSorts-1]
	         Integer orderId = 0;
	         
	         if (numberGroup.length >= paramCount + 3)
	         {
	             String orderIdStr = numberGroup[2 + paramCount];
	             
	             if (null != orderIdStr)
	             {
	                 orderId = Integer.parseInt(orderIdStr);
	             }
	         }
	         
	         map.addAttribute("orderId", orderId);
	         
	      // 排序字段
	         int[] sortIds = new int[totalSorts];
	         
	         //  排序字段0标志位，0：降序，1：升序
	         if (numberGroup.length >= paramCount + 4)
	         {
	             String sortIdStr = numberGroup[3 + paramCount];
	             
	             if (null != sortIdStr)
	             {
	                 sortIds[0] = Integer.parseInt(sortIdStr);
	             }
	         }
	         
	         // 排序字段1标志位，0：降序，1：升序
	         if (numberGroup.length >= paramCount + 5)
	         {
	             String sortIdStr = numberGroup[4 + paramCount];
	             
	             if (null != sortIdStr)
	             {
	                 sortIds[1] = Integer.parseInt(sortIdStr);
	             }
	         }
	         
	         // 排序字段2标志位，0：降序，1：升序
	         if (numberGroup.length >= paramCount + 6)
	         {
	             String sortIdStr = numberGroup[5 + paramCount];
	             
	             if (null != sortIdStr)
	             {
	                 sortIds[2] = Integer.parseInt(sortIdStr);
	             }
	         }
	         
	         // 排序字段3标志位，0：降序，1：升序
	         if (numberGroup.length >= paramCount + 7)
	         {
	             String sortIdStr = numberGroup[6 + paramCount];
	             
	             if (null != sortIdStr)
	             {
	                 sortIds[3] = Integer.parseInt(sortIdStr);
	             }
	         }
	         
//	         // 排序字段4标志位，0：降序，1：升序
//	         if (numberGroup.length >= paramCount + 8)
//	         {
//	             String sortIdStr = numberGroup[7 + paramCount];
//	             
//	             if (null != sortIdStr)
//	             {
//	                 sortIds[4] = Integer.parseInt(sortIdStr);
//	             }
//	         }
	         
	         map.addAttribute("sort_id_list", sortIds);

	         // 页号
	         Integer pageId = 0;
	         
	         if (numberGroup.length >= paramCount + 8)
	         {
	             String pageIdStr = numberGroup[7 + paramCount];
	             
	             if (null != pageIdStr)
	             {
	                 pageId = Integer.parseInt(pageIdStr);
	             }
	         }
	         
	         map.addAttribute("pageId", pageId);
	         
	         // 是否有货
	         Integer leftId = 0;
	         
	         if (numberGroup.length >= paramCount + 9)
	         {
	             String leftIdStr = numberGroup[8 + paramCount];
	             
	             if (null != leftIdStr)
	             {
	                 leftId = Integer.parseInt(leftIdStr);
	             }
	         }
	         
	         map.addAttribute("leftId", leftId);
	         
	         // 获取该类型所有父类型
	         if (null != tdProductCategory)
	         {
	             if (null != tdProductCategory.getParentTree() && !"".equals(tdProductCategory.getParentTree()))
	             {
	                 List<TdProductCategory> catList = new ArrayList<TdProductCategory>();
	                 
	                 for (String cid : tdProductCategory.getParentTree().split(","))
	                 {
	                     if (!"".equals(cid))
	                     {
	                         // 去除方括号
	                         cid = cid.replace("[", "");
	                         cid = cid.replace("]", "");
	                         
	                         TdProductCategory tpc = tdProductCategoryService.findOne(Long.parseLong(cid));
	                         
	                         if (null != tpc)
	                         {
	                             catList.add(tpc);
	                         }
	                     }
	                 }
	                 
	                 map.addAttribute("category_tree_list", catList);
	             }
	         }
	         
	         // 分类热卖推荐
	         map.addAttribute("hot_sale_list", tdGoodsService.findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(categoryId, 0, 10).getContent());   
	         
	         // 销量排行
	         map.addAttribute("most_sold_list", tdGoodsService.findByCategoryIdAndIsOnSaleTrueOrderBySoldNumberDesc(categoryId, 0, 10).getContent());   
	         
	         // 新品推荐
	         map.addAttribute("newest_list", tdGoodsService.findByCategoryIdAndIsOnSaleTrueOrderByOnSaleTimeDesc(categoryId, 0, 10).getContent());   
	         
	         PageRequest pageRequest;
	         
	         // 0: 降序 1: 升序
	         if (0 == sortIds[orderId])
	         {
	             pageRequest = new PageRequest(pageId, ClientConstant.pageSize, new Sort(
	                 Direction.DESC, sortName[orderId]));
	         }
	         else
	         {
	             pageRequest = new PageRequest(pageId, ClientConstant.pageSize, new Sort(
	                     Direction.ASC, sortName[orderId]));
	         }
	      // 查找商品
	         Page<TdGoods> goodsPage = null;
	         
	         // 仅显示有货
	         if (leftId.intValue() > 0)
//	         {
//	             // 按价格区间
//	             if (null != priceLow && null != priceHigh)
//	             {
//	                 // 全部品牌
//	                 if (0 == brandIndex.intValue())
//	                 {
//	                     // 有货、价格区间
//	                     goodsPage = tdGoodsService.findByCategoryIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrue(
//	                                         categoryId, priceLow, priceHigh, paramValueList, pageRequest);
//	                 }
//	                 else
//	                 {
//	                     goodsPage = tdGoodsService.findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrue(
//	                                         categoryId, brandId, priceLow, priceHigh, paramValueList, pageRequest);
//	                     
//	                 }
//	             }
//	             // 所有价格
//	             else
//	             {
//	                 // 全部品牌
//	                 if (0 == brandIndex.intValue())
//	                 {
//	                     goodsPage = tdGoodsService.findByCategoryIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrue(
//	                                         categoryId, paramValueList, pageRequest);
//	                 }
//	                 else
//	                 {
//	                     goodsPage = tdGoodsService.findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrue(
//	                                         categoryId, brandId, paramValueList, pageRequest);
//	                     
//	                 }
//	             }
//	         }
//	         // 所有商品
//	         else
//	         {
//	             // 按价格区间
//	             if (null != priceLow && null != priceHigh)
//	             {
//	                 // 全部品牌
//	                 if (0 == brandIndex.intValue())
//	                 {
//	                     goodsPage = tdGoodsService.findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrue(
//	                                         categoryId, priceLow, priceHigh, paramValueList, pageRequest);
//	                     
//	                 }
//	                 else // 品牌、价格区间
//	                 {
//	                     goodsPage = tdGoodsService.findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrue(
//	                                         categoryId, brandId, priceLow, priceHigh, paramValueList, pageRequest);
//	                 }
//	             }
//	             // 所有价格
//	             else
//	             {
//	                 // 全部品牌
//	                 if (0 == brandIndex.intValue())
//	                 {
//	                     goodsPage = tdGoodsService.findByCategoryIdAndParamsLikeAndIsOnSaleTrue(
//	                                         categoryId, paramValueList, pageRequest);
//	                 }
//	                 else
//	                 {
//	                     // 品牌
//	                     goodsPage = tdGoodsService.findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrue(
//	                                         categoryId, brandId, paramValueList, pageRequest);
//	                 }
//	             }
//	         }

	         
	         map.addAttribute("goods_page", goodsPage);   
	         
	       //判断是否为app链接
	         Integer isApp = (Integer) req.getSession().getAttribute("app");
	         if (null != isApp) {
	         	map.addAttribute("app", isApp);
	 		}
	         
	         return "/touch/goods_list";
	    }
	    
	    // 组成：typeID-[排序字段]-[综合排序标志]-[价格排序标志]-[销量排序标志]-[评价]-[页号]
	    @RequestMapping("/list/more/{listStr}")
	    public String goodsList(@PathVariable String listStr, ModelMap map, HttpServletRequest req){
	        if (null == listStr || "".equals(listStr))
	        {
	            return "/touch/list_more";
	        }
	            
	        String[] numberGroup = listStr.split("-");
	        
	        if (numberGroup.length <= 0)
	        {
	            return "/touch/list_more";
	        }
	        
	        Long categoryId = null;
	        
	        // 分类ID
	        categoryId = Long.parseLong(numberGroup[0]);
	        
//	        map.addAttribute("categoryId", categoryId);
	        
	        TdProductCategory tdProductCategory = tdProductCategoryService.findOne(categoryId);
	        
	        if (null == tdProductCategory)
	        {
	            return "/touch/list_more";
	        }
	        
//	        map.addAttribute("productCategory", tdProductCategory);
	        
	        // 排序字段
	        Integer orderId = 0;
	        
	        if (numberGroup.length > 1)
	        {
	            String orderIdStr = numberGroup[1];
	            
	            if (null != orderIdStr)
	            {
	                orderId = Integer.parseInt(orderIdStr);
	            }
	        }
	        
//	        map.addAttribute("orderId", orderId);
	        
	        //  综合排序标志
	        Integer Id = 0;
	        
	        if (numberGroup.length > 2)
	        {
	            String IdStr = numberGroup[2];
	            
	            if (null != IdStr)
	            {
	                Id = Integer.parseInt(IdStr);
	            }
	        }
	        
//	        map.addAttribute("soldId", soldId);
	        
	        // 价格排序标志
	        Integer priceId = 0;
	        
	        if (numberGroup.length > 3)
	        {
	            String priceIdStr = numberGroup[3];
	            
	            if (null != priceIdStr)
	            {
	                priceId = Integer.parseInt(priceIdStr);
	            }
	        }
	        
//	        map.addAttribute("priceId", priceId);
	        
	        // 销量排序标志
	        Integer soldId = 0;
	        
	        if (numberGroup.length > 4)
	        {
	            String soldIdStr = numberGroup[4];
	            
	            if (null != soldIdStr)
	            {
	            	soldId = Integer.parseInt(soldIdStr);
	            }
	        }
	        
//	        map.addAttribute("timeId", timeId);
	        
	        // 评价排序标志
	        Integer commentsId = 0;
	        
	        if (numberGroup.length > 5)
	        {
	            String commentsIdStr = numberGroup[5];
	            
	            if (null != commentsIdStr)
	            {
	            	commentsId = Integer.parseInt(commentsIdStr);
	            }
	        }
	        
	        // 页号
	        Integer pageId = 0;
	        
	        if (numberGroup.length > 6)
	        {
	            String pageIdStr = numberGroup[6];
	            
	            if (null != pageIdStr)
	            {
	                pageId = Integer.parseInt(pageIdStr);
	            }
	        }
	        
//	        map.addAttribute("pageId", pageId);

	        // 查找商品
	        Page<TdGoods> goodsPage = null;
	        

	     // 按综合排序
	        if (0 == orderId.intValue())
//	        {
//	            if (0 == Id.intValue())
//	            {
//	                goodsPage = tdGoodsService.findByCategoryIdAndParamsLikeAndIsOnSaleTrue(
//	                            categoryId, null, new PageRequest(pageId, ClientConstant.pageSize, new Sort(
//	                                    Direction.DESC, "id")));
//	        
//	            }
//	            else
//	            {
//	                goodsPage = tdGoodsService.findByCategoryIdAndParamsLikeAndIsOnSaleTrue(
//	                        categoryId, null, new PageRequest(pageId, ClientConstant.pageSize, new Sort(
//	                                Direction.ASC, "id")));
//	    
//	            }
//	        }
//	        // 按价格排序
//	        else if (1 == orderId.intValue())
//	        {
//	            if (0 == priceId.intValue())
//	            {
//	                goodsPage = tdGoodsService.findByCategoryIdAndParamsLikeAndIsOnSaleTrue(
//	                        categoryId, null, new PageRequest(pageId, ClientConstant.pageSize, new Sort(
//	                                Direction.DESC, "salePrice")));
//	        
//	            }
//	            else
//	            {
//	                goodsPage = tdGoodsService.findByCategoryIdAndParamsLikeAndIsOnSaleTrue(
//	                        categoryId, null, new PageRequest(pageId, ClientConstant.pageSize, new Sort(
//	                                Direction.ASC, "salePrice")));
//	    
//	            }
//	        }
//	        // 按销量排序
//	        else if (2 == orderId.intValue())
//	        {
//	            if (0 == soldId.intValue())
//	            {
//	                goodsPage = tdGoodsService.findByCategoryIdAndParamsLikeAndIsOnSaleTrue(
//	                        categoryId, null, new PageRequest(pageId, ClientConstant.pageSize, new Sort(
//	                                Direction.DESC, "soldNumber")));
//	            }
//	            else
//	            {
//	                goodsPage = tdGoodsService.findByCategoryIdAndParamsLikeAndIsOnSaleTrue(
//	                        categoryId, null, new PageRequest(pageId, ClientConstant.pageSize, new Sort(
//	                                Direction.ASC, "soldNumber")));
//	    
//	            }
//	        }
//	        // 按评价排序
//	        else 
//	        {
//	            if (0 == commentsId.intValue())
//	            {
//	                goodsPage = tdGoodsService.findByCategoryIdAndParamsLikeAndIsOnSaleTrue(
//	                        categoryId, null, new PageRequest(pageId, ClientConstant.pageSize, new Sort(
//	                                Direction.DESC, "totalComments")));
//	            }
//	            else
//	            {
//	                goodsPage = tdGoodsService.findByCategoryIdAndParamsLikeAndIsOnSaleTrue(
//	                        categoryId, null, new PageRequest(pageId, ClientConstant.pageSize, new Sort(
//	                                Direction.ASC, "totalComments")));
//	    
//	            }
//	        }
	        
	        map.addAttribute("goods_page", goodsPage);
	        
	        return "/touch/goods_list_more";
	    }
}
