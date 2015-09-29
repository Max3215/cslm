package com.ynyes.cslm.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ynyes.cslm.entity.TdBrand;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdGoodsParameter;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.repository.TdDistributorGoodsRepo;

/**
 * TdDistGoods 服务类
 * 
 * @author libiao
 *
 */
@Service
@Transactional
public class TdDistributorGoodsService {
	
	@Autowired
	TdDistributorGoodsRepo repository;
	
	@Autowired
	TdProductCategoryService tdProductCategoryService;
	
	@Autowired
	TdBrandService tdBrandService;
	
	@Autowired
	TdGoodsParameterService tdGoodsParameterService;
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(Long id)
	{
	     if (null != id)
	     {
	          repository.delete(id);
	     }
	}
	
	public void delete(TdDistributorGoods e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdDistributorGoods> entities)
    {
        if (null != entities)
        {
            repository.delete(entities);
        }
    }
	
    /**
     * 
     * 查找
     * 
     */
    
    public List<TdDistributorGoods> findByGoodsId(Long goodsId)
    {
    	if(null == goodsId){
    		return null;
    	}
    	return repository.findByGoodsIdAndIsOnSaleTrue(goodsId);
    }
    
    /**
     * 
     * 保存
     * 
     */
    public TdDistributorGoods save(TdDistributorGoods e)
    {
    	return repository.save(e);
    }
    
    public TdDistributorGoods saveGoods(TdDistributorGoods e)
    {
    	 if (null == e) {
             return null;
         }
    	 // 参数类型ID
         Long paramCategoryId = null;

         // 保存分类名称
         if (null != e.getCategoryId()) {
             TdProductCategory cat = tdProductCategoryService.findOne(e
                     .getCategoryId());
             e.setCategoryIdTree(cat.getParentTree());

             paramCategoryId = cat.getParamCategoryId();
         }

         // 保存品牌名称
         if (null != e.getCategoryId()) {
             TdBrand brand = tdBrandService.findOne(e.getBrandId());

             if (null != brand) {
                 e.setBrandTitle(brand.getTitle());
             }
         }
         if (null != e.getParamList() && e.getParamList().size() > 0) {
             String valueCollect = "";
             for (TdGoodsParameter gp : e.getParamList()) {
                 valueCollect += gp.getValue();
                 valueCollect += ",";
             }
             e.setParamValueCollect(valueCollect);

             // 保存参数
             tdGoodsParameterService.save(e.getParamList());
         } else {
             e.setParamValueCollect("");
         }
        return repository.save(e);
    }
    
    public List<TdDistributorGoods> save(List<TdDistributorGoods> entities)
    {
    	return (List<TdDistributorGoods>) repository.save(entities);
    }
	
    
    public Page<TdDistributorGoods> findByUsenameAndIsOnSale(String username,Boolean onsale ,int page ,int size)
	{
		if(null == username)
		{
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameAndIsOnSale(username,onsale, pageRequest);
	}
    
//    public Page<TdDistributorGoods> findByDistributorIdAndIsOnSale(Long disId,Boolean onsale ,int page ,int size)
//	{
//		PageRequest pageRequest = new PageRequest(page, size);
//		return repository.findByDistributorIdAndIsOnSale(disId,onsale,pageRequest);
//	}
    
    public TdDistributorGoods findOne(Long id)
    {
    	return repository.findOne(id);
    }
    
    public TdDistributorGoods findByDistributorIdAndGoodsIdAndIsOnSale(Long distributorId,Long goodsId, Boolean isOnSale)
    {
    	return repository.findByDistributorIdAndGoodsIdAndIsOnSale(distributorId,goodsId, isOnSale);
    }
    
    public List<TdDistributorGoods> findTop12ByDistributorIdAndIsOnSaleTrueBySoldNumberDesc(Long disId)
    {
    	if(null == disId)
    	{
    		return null ;
    	}
    	return repository.findTop12ByDistributorIdAndIsOnSaleTrueOrderBySoldNumberDesc(disId);
    }
    
    /**
     * 	列表页排序
     * 	
     */
    
    public Page<TdDistributorGoods> findByCategoryIdAndBrandIdAndGoodsPriceBetweenAndParamsLikeAndIsOnSaleTrue(
            long catId, long brandId, double priceLow, double priceHigh,
            List<String> paramValueList, Pageable pageRequest) {
        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, priceLow, priceHigh,
                        paramStr, pageRequest);
    }
    
//    public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndBrandIdAndGoodsPriceBetweenAndParamsLikeAndIsOnSaleTrue(
//            long distributorId,long catId, long brandId, double priceLow, double priceHigh,
//            List<String> paramValueList, Pageable pageRequest) {
//        String paramStr = "%";
//
//        for (int i = 0; i < paramValueList.size(); i++) {
//            String value = paramValueList.get(i);
//            if (!"".equals(value)) {
//                paramStr += value;
//                paramStr += "%";
//            }
//        }
//
//        return repository
//                .findByDistributorIdAndCategoryIdTreeLikeAndBrandIdAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
//                        distributorId,"[" + catId + "]", brandId, priceLow, priceHigh,
//                        paramStr, pageRequest);
//    }
    
    public Page<TdDistributorGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrue(
            long catId, List<String> paramValueList, Pageable pageRequest) {
        String paramStr = "%";

        if (null != paramValueList) {
            for (int i = 0; i < paramValueList.size(); i++) {
                String value = paramValueList.get(i);
                if (!"".equals(value)) {
                    paramStr += value;
                    paramStr += "%";
                }
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", paramStr, pageRequest);
    }
    
//    public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndParamsLikeAndIsOnSaleTrue(
//           long distributorId, long catId, List<String> paramValueList, Pageable pageRequest) {
//        String paramStr = "%";
//
//        if (null != paramValueList) {
//            for (int i = 0; i < paramValueList.size(); i++) {
//                String value = paramValueList.get(i);
//                if (!"".equals(value)) {
//                    paramStr += value;
//                    paramStr += "%";
//                }
//            }
//        }
//
//        return repository
//                .findByDistributorIdAndCategoryIdTreeLikeAndParamValueCollectLikeAndIsOnSaleTrue(
//                       distributorId, "[" + catId + "]", paramStr, pageRequest);
//    }
    
    public Page<TdDistributorGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrue(
            long catId, long brandId,
            List<String> paramValueList, Pageable pageRequest) {
        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, paramStr, pageRequest);
    }
    
//    public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrue(
//            long distributorId, long catId, long brandId,
//            List<String> paramValueList, Pageable pageRequest) {
//        String paramStr = "%";
//
//        for (int i = 0; i < paramValueList.size(); i++) {
//            String value = paramValueList.get(i);
//            if (!"".equals(value)) {
//                paramStr += value;
//                paramStr += "%";
//            }
//        }
//
//        return repository
//                .findByDistributorIdAndCategoryIdTreeLikeAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(
//                       distributorId, "[" + catId + "]", brandId, paramStr, pageRequest);
//    }
    
    
    /**
     * 	↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
     * 	↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
     */
    
    
     public Page<TdDistributorGoods> findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
            long catId, double priceLow, double priceHigh, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "soldNumber"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", priceLow, priceHigh, paramStr,
                        pageRequest);
    }

   public Page<TdDistributorGoods> findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
            long catId, double priceLow, double priceHigh, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "soldNumber"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", priceLow, priceHigh, paramStr,
                        pageRequest);
    }



   public Page<TdDistributorGoods> findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
            long catId, double priceLow, double priceHigh, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "goodsPrice"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", priceLow, priceHigh, paramStr,
                        pageRequest);
    }

    public Page<TdDistributorGoods> findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
            long catId, double priceLow, double priceHigh, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "goodsPrice"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", priceLow, priceHigh, paramStr,
                        pageRequest);
    }



  public Page<TdDistributorGoods> findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
            long catId, long brandId, double priceLow, double priceHigh,
            int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "soldNumber"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, priceLow, priceHigh,
                        paramStr, pageRequest);
    }

   public Page<TdDistributorGoods> findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
            long catId, long brandId, double priceLow, double priceHigh,
            int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "soldNumber"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, priceLow, priceHigh,
                        paramStr, pageRequest);
    }

    public Page<TdDistributorGoods> findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
            long catId, long brandId, double priceLow, double priceHigh,
            int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "goodsPrice"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, priceLow, priceHigh,
                        paramStr, pageRequest);
    }

   public Page<TdDistributorGoods> findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
            long catId, long brandId, double priceLow, double priceHigh,
            int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "goodsPrice"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, priceLow, priceHigh,
                        paramStr, pageRequest);
    }



    // 无价格区间
    public Page<TdDistributorGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
            long catId, int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "soldNumber"));

        String paramStr = "%";

        if (null != paramValueList) {
            for (int i = 0; i < paramValueList.size(); i++) {
                String value = paramValueList.get(i);
                if (!"".equals(value)) {
                    paramStr += value;
                    paramStr += "%";
                }
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", paramStr, pageRequest);
    }

    public Page<TdDistributorGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
            long catId, int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "soldNumber"));

        String paramStr = "%";

        if (null != paramValueList) {
            for (int i = 0; i < paramValueList.size(); i++) {
                String value = paramValueList.get(i);
                if (!"".equals(value)) {
                    paramStr += value;
                    paramStr += "%";
                }
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", paramStr, pageRequest);
    }

   public Page<TdDistributorGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
            long catId, int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "goodsPrice"));

        String paramStr = "%";

        if (null != paramValueList) {
            for (int i = 0; i < paramValueList.size(); i++) {
                String value = paramValueList.get(i);
                if (!"".equals(value)) {
                    paramStr += value;
                    paramStr += "%";
                }
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", paramStr, pageRequest);
    }

    public Page<TdDistributorGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
            long catId, int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "goodsPrice"));

        String paramStr = "%";

        if (null != paramValueList) {
            for (int i = 0; i < paramValueList.size(); i++) {
                String value = paramValueList.get(i);
                if (!"".equals(value)) {
                    paramStr += value;
                    paramStr += "%";
                }
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", paramStr, pageRequest);
    }



   public Page<TdDistributorGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
            long catId, long brandId, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "soldNumber"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, paramStr, pageRequest);
    }

    public Page<TdDistributorGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
            long catId, long brandId, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "soldNumber"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, paramStr, pageRequest);
    }

    public Page<TdDistributorGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
            long catId, long brandId, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "goodsPrice"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, paramStr, pageRequest);
    }

    public Page<TdDistributorGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
            long catId, long brandId, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "goodsPrice"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, paramStr, pageRequest);
    }
    
    
    

	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
	       long distributorId, long catId, double priceLow, double priceHigh, int page, int size,
	        List<String> paramValueList) {
	    PageRequest pageRequest = new PageRequest(page, size);
	
	    String paramStr = "%";
	
	    for (int i = 0; i < paramValueList.size(); i++) {
	        String value = paramValueList.get(i);
	        if (!"".equals(value)) {
	            paramStr += value;
	            paramStr += "%";
	        }
	    }
	
	    return repository
	            .findByDistributorIdAndCategoryIdTreeLikeAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrueOrderBySoldNumber(
	            		 distributorId,  "[" + catId + "]", priceLow, priceHigh, paramStr,
	                    pageRequest);
	}
	
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
			long distributorId, long catId, double priceLow, double priceHigh, int page, int size,
	        List<String> paramValueList) {
	    PageRequest pageRequest = new PageRequest(page, size);
	
	    String paramStr = "%";
	
	    for (int i = 0; i < paramValueList.size(); i++) {
	        String value = paramValueList.get(i);
	        if (!"".equals(value)) {
	            paramStr += value;
	            paramStr += "%";
	        }
	    }
	
	    return repository
	            .findByDistributorIdAndCategoryIdTreeLikeAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrueOrderBySoldNumber(
	            		 distributorId, "[" + catId + "]", priceLow, priceHigh, paramStr,
	                    pageRequest);
	}
	
	
	
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
			long distributorId, long catId, double priceLow, double priceHigh, int page, int size,
	        List<String> paramValueList) {
	    PageRequest pageRequest = new PageRequest(page, size);
	
	    String paramStr = "%";
	
	    for (int i = 0; i < paramValueList.size(); i++) {
	        String value = paramValueList.get(i);
	        if (!"".equals(value)) {
	            paramStr += value;
	            paramStr += "%";
	        }
	    }
	
	    return repository
	            .findByDistributorIdAndCategoryIdTreeLikeAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrueOrderByGoodsPrice(
	            		 distributorId,  "[" + catId + "]", priceLow, priceHigh, paramStr,
	                    pageRequest);
	}
	
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
			long distributorId, long catId, double priceLow, double priceHigh, int page, int size,
	        List<String> paramValueList) {
	    PageRequest pageRequest = new PageRequest(page, size);
	
	    String paramStr = "%";
	
	    for (int i = 0; i < paramValueList.size(); i++) {
	        String value = paramValueList.get(i);
	        if (!"".equals(value)) {
	            paramStr += value;
	            paramStr += "%";
	        }
	    }
	
	    return repository
	            .findByDistributorIdAndCategoryIdTreeLikeAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrueOrderByGoodsPrice(
	            		 distributorId,  "[" + catId + "]", priceLow, priceHigh, paramStr,
	                    pageRequest);
	}
	
	
	
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
			long distributorId, long catId, long brandId, double priceLow, double priceHigh,
	        int page, int size, List<String> paramValueList) {
	    PageRequest pageRequest = new PageRequest(page, size);
	
	    String paramStr = "%";
	
	    for (int i = 0; i < paramValueList.size(); i++) {
	        String value = paramValueList.get(i);
	        if (!"".equals(value)) {
	            paramStr += value;
	            paramStr += "%";
	        }
	    }
	
	    return repository
	            .findByDistributorIdAndCategoryIdTreeLikeAndBrandIdAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrueOrderBySoldNumber(
	            		 distributorId, "[" + catId + "]", brandId, priceLow, priceHigh,
	                    paramStr, pageRequest);
	}
	
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
			long distributorId, long catId, long brandId, double priceLow, double priceHigh,
	        int page, int size, List<String> paramValueList) {
	    PageRequest pageRequest = new PageRequest(page, size);
	
	    String paramStr = "%";
	
	    for (int i = 0; i < paramValueList.size(); i++) {
	        String value = paramValueList.get(i);
	        if (!"".equals(value)) {
	            paramStr += value;
	            paramStr += "%";
	        }
	    }
	    return repository
	            .findByDistributorIdAndCategoryIdTreeLikeAndBrandIdAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrueOrderBySoldNumber(
	            		 distributorId,  "[" + catId + "]", brandId, priceLow, priceHigh,
	                    paramStr, pageRequest);
	}
	
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
			long distributorId,long catId, long brandId, double priceLow, double priceHigh,
	        int page, int size, List<String> paramValueList) {
	    PageRequest pageRequest = new PageRequest(page, size);
	
	    String paramStr = "%";
	
	    for (int i = 0; i < paramValueList.size(); i++) {
	        String value = paramValueList.get(i);
	        if (!"".equals(value)) {
	            paramStr += value;
	            paramStr += "%";
	        }
	    }
	    return repository
	            .findByDistributorIdAndCategoryIdTreeLikeAndBrandIdAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrueOrderByGoodsPrice(
	            		 distributorId, "[" + catId + "]", brandId, priceLow, priceHigh,
	                    paramStr, pageRequest);
	}
	
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
			long distributorId, long catId, long brandId, double priceLow, double priceHigh,
	        int page, int size, List<String> paramValueList) {
	    PageRequest pageRequest = new PageRequest(page, size);
	
	    String paramStr = "%";
	
	    for (int i = 0; i < paramValueList.size(); i++) {
	        String value = paramValueList.get(i);
	        if (!"".equals(value)) {
	            paramStr += value;
	            paramStr += "%";
	        }
	    }
	    return repository
	            .findByDistributorIdAndCategoryIdTreeLikeAndBrandIdAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrueOrderByGoodsPrice(
	            		 distributorId, "[" + catId + "]", brandId, priceLow, priceHigh,
	                    paramStr, pageRequest);
	}
	
	
	
	// 无价格区间
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
			long distributorId, long catId, int page, int size, List<String> paramValueList) {
	    PageRequest pageRequest = new PageRequest(page, size);
	
	    String paramStr = "%";
	
	    if (null != paramValueList) {
	        for (int i = 0; i < paramValueList.size(); i++) {
	            String value = paramValueList.get(i);
	            if (!"".equals(value)) {
	                paramStr += value;
	                paramStr += "%";
	            }
	        }
	    }
	
	    return repository
	            .findByDistributorIdAndCategoryIdTreeLikeAndParamValueCollectLikeAndIsOnSaleTrueOrderBySoldNumber(
	            		 distributorId, "[" + catId + "]", paramStr, pageRequest);
	}
	
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
			long distributorId, long catId, int page, int size, List<String> paramValueList) {
	    PageRequest pageRequest = new PageRequest(page, size);
	
	    String paramStr = "%";
	
	    if (null != paramValueList) {
	        for (int i = 0; i < paramValueList.size(); i++) {
	            String value = paramValueList.get(i);
	            if (!"".equals(value)) {
	                paramStr += value;
	                paramStr += "%";
	            }
	        }
	    }
	
	    return repository
	            .findByDistributorIdAndCategoryIdTreeLikeAndParamValueCollectLikeAndIsOnSaleTrueOrderBySoldNumber(
	            		 distributorId, "[" + catId + "]", paramStr, pageRequest);
	}
	
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
			long distributorId, long catId, int page, int size, List<String> paramValueList) {
	    PageRequest pageRequest = new PageRequest(page, size);
	
	    String paramStr = "%";
	
	    if (null != paramValueList) {
	        for (int i = 0; i < paramValueList.size(); i++) {
	            String value = paramValueList.get(i);
	            if (!"".equals(value)) {
	                paramStr += value;
	                paramStr += "%";
	            }
	        }
	    }
	
	    return repository
	            .findByDistributorIdAndCategoryIdTreeLikeAndParamValueCollectLikeAndIsOnSaleTrueOrderBySoldNumber(
	            		 distributorId, "[" + catId + "]", paramStr, pageRequest);
	}
	
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
			long distributorId,  long catId, int page, int size, List<String> paramValueList) {
	    PageRequest pageRequest = new PageRequest(page, size);
	
	    String paramStr = "%";
	
	    if (null != paramValueList) {
	        for (int i = 0; i < paramValueList.size(); i++) {
	            String value = paramValueList.get(i);
	            if (!"".equals(value)) {
	                paramStr += value;
	                paramStr += "%";
	            }
	        }
	    }
	
	    return repository
	            .findByDistributorIdAndCategoryIdTreeLikeAndParamValueCollectLikeAndIsOnSaleTrueOrderByGoodsPrice(
	            		 distributorId, "[" + catId + "]", paramStr, pageRequest);
	}
	
	
	
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
			long distributorId, long catId, long brandId, int page, int size,
	        List<String> paramValueList) {
	    PageRequest pageRequest = new PageRequest(page, size);
	
	    String paramStr = "%";
	
	    for (int i = 0; i < paramValueList.size(); i++) {
	        String value = paramValueList.get(i);
	        if (!"".equals(value)) {
	            paramStr += value;
	            paramStr += "%";
	        }
	    }
	
	    return repository
	            .findByDistributorIdAndCategoryIdTreeLikeAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrueOrderBySoldNumber(
	            		 distributorId, "[" + catId + "]", brandId, paramStr, pageRequest);
	}
	
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
			long distributorId, long catId, long brandId, int page, int size,
	        List<String> paramValueList) {
	    PageRequest pageRequest = new PageRequest(page, size);
	
	    String paramStr = "%";
	
	    for (int i = 0; i < paramValueList.size(); i++) {
	        String value = paramValueList.get(i);
	        if (!"".equals(value)) {
	            paramStr += value;
	            paramStr += "%";
	        }
	    }
	
	    return repository
	            .findByDistributorIdAndCategoryIdTreeLikeAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrueOrderBySoldNumber(
	            		 distributorId, "[" + catId + "]", brandId, paramStr, pageRequest);
	}

	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
			long distributorId, long catId, long brandId, int page, int size,
	        List<String> paramValueList) {
	    PageRequest pageRequest = new PageRequest(page, size);
	
	    String paramStr = "%";
	
	    for (int i = 0; i < paramValueList.size(); i++) {
	        String value = paramValueList.get(i);
	        if (!"".equals(value)) {
	            paramStr += value;
	            paramStr += "%";
	        }
	    }
	
	    return repository
	            .findByDistributorIdAndCategoryIdTreeLikeAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrueOrderByGoodsPrice(
	            		 distributorId, "[" + catId + "]", brandId, paramStr, pageRequest);
	}

	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
      long distributorId,  long catId, long brandId, int page, int size,
        List<String> paramValueList) {
    PageRequest pageRequest = new PageRequest(page, size);

    String paramStr = "%";

    for (int i = 0; i < paramValueList.size(); i++) {
        String value = paramValueList.get(i);
        if (!"".equals(value)) {
            paramStr += value;
            paramStr += "%";
        }
    }

    return repository
            .findByDistributorIdAndCategoryIdTreeLikeAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrueOrderByGoodsPrice(
                  distributorId,  "[" + catId + "]", brandId, paramStr, pageRequest);
	} 
    
    
    
    
    
    
    
    
    
    
    
    
}
