package com.ynyes.cslm.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.bouncycastle.crypto.signers.DSASigner;
import org.neo4j.cypher.internal.compiler.v2_1.docbuilders.internalDocBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ynyes.cslm.entity.TdBrand;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdGoodsParameter;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.entity.TdRelevance;
import com.ynyes.cslm.repository.TdDistributorGoodsRepo;
import com.ynyes.cslm.util.Criteria;
import com.ynyes.cslm.util.Restrictions;

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
	
	@Autowired
	TdDistributorService tdDistributorService;
	
	@Autowired
	TdRelevanceService tdRelevanceService;
	
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
    
    public List<TdDistributorGoods> findByIsOnSaleAndLeftNumber(){
    	return repository.findByIsOnSaleTrueAndLeftNumberLessThan(1L);
    }
	
    /**
     * 
     * 查找
     * 
     */
    
    public List<Long> findByDistributorId(Long distributorId)
    {
    	return repository.findByDistributorIdAndGroupCategoryId(distributorId);
    }
    
    
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
         if (null != e.getGoodsParamList() && e.getGoodsParamList().size() > 0) {
             String valueCollect = "";
             for (TdGoodsParameter gp : e.getGoodsParamList()) {
                 valueCollect += gp.getValue();
                 valueCollect += ",";
             }
             e.setParamValueCollect(valueCollect);

             // 保存参数
             tdGoodsParameterService.save(e.getGoodsParamList());
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
    
    
    public TdDistributorGoods findOne(Long id)
    {
    	return repository.findOne(id);
    }
    
    public Long findDistributorId(Long id)
    {
    	return repository.findDistributorId(id);
    }
    
    public TdDistributorGoods findByIdAndIsInSaleTrue(Long id)
    {
    	return repository.findByIdAndIsOnSaleTrue(id);
    }
    
    public TdDistributorGoods findByDistributorIdAndGoodsIdAndIsOnSale(Long distributorId,Long goodsId, Boolean isOnSale)
    {
    	TdDistributor distributor = tdDistributorService.findOne(distributorId);
    	if(null == distributor.getGoodsList())
    	{
    		return null;
    	}
    	return repository.findByDistributorIdAndGoodsIdAndIsOnSale(distributorId,goodsId, isOnSale);
    }
    
    public TdDistributorGoods findByDistributorIdAndGoodsId(Long distributorId,Long goodsId)
    {
    	return repository.findByDistributorIdAndGoodsId(distributorId,goodsId);
    }
    
    public Page<TdDistributorGoods> findByDistributorIdAndIsOnSaleTrueBySoldNumberDesc(Long disId,int page ,int size)
    {
    	if(null == disId)
    	{
    		return null ;
    	}
    	PageRequest pageRequest = new PageRequest(page,size);
    	return repository.findByDistributorIdAndIsOnSaleTrueOrderBySoldNumberDesc(disId,pageRequest);
    }
    
    public Page<TdDistributorGoods> findByIsOnSaleTrueBySoldNumberDesc(int page ,int size)
    {
    	PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "soldNumber"));
    	return repository.findByIsOnSaleTrueOrderBySoldNumberDesc(pageRequest);
    }
    
    public Page<TdDistributorGoods> findByIsOnSaleTrueByGoodsPriceDesc(int page ,int size)
    {
    	PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "goodsPrice"));
    	return repository.findByIsOnSaleTrueOrderByGoodsPriceDesc(pageRequest);
    }
    
    public List<TdDistributorGoods> findByDistributorIdAndProductIdAndIsOnSale(Long disId,Long productId){
    	return repository.findByDistributorIdAndProductIdAndIsOnSaleTrue(disId, productId);
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
    
    
    public Page<TdDistributorGoods> findByCategoryIdAndIsOnSaleTrueOrderBySoldNumberDesc(Long catId, int page, int size)
    {
    	if (null == catId) {
            return null;
        }
    	PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "soldNumber"));
    	String catStr = "[" + catId + "]";
    	
    	return repository.findByCategoryIdTreeContainingAndIsOnSaleTrueOrderBySoldNumberDesc(catStr, pageRequest);
    }
    
    public Page<TdDistributorGoods> findByDIstributorIdAndCategoryIdAndIsOnSaleTrueOrderBySoldNumberDesc(Long disId,Long catId, int page, int size)
    {
    	if (null == catId) {
            return null;
        }
    	PageRequest pageRequest = new PageRequest(page,size);
    	String catStr = "[" + catId + "]";
    	
    	return repository.findByDisIdAndCategoryIdTreeContainingAndIsOnSaleTrueOrderBySoldNumberDesc(disId,catStr, pageRequest);
    }
    
    /**
     * search
     */
    public Page<TdDistributorGoods> searchGoodsAndIsOnSale(String keywords,Boolean isOnSale,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsOnSaleOrSubGoodsTitleContainingAndIsOnSaleOrCodeContainingAndIsOnSaleOrDistributorTitleContainingAndIsOnSale(
								keywords, isOnSale, 
								keywords, isOnSale, 
								keywords, isOnSale,
								keywords, isOnSale,pageRequest);
	}
    
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

    // 无价格区间
    public Page<TdDistributorGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrue(
            long catId, Pageable pageable, List<String> paramValueList) {

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
                        "[" + catId + "]", paramStr, pageable);
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

   public Page<TdDistributorGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrue(
           long catId, long brandId, Pageable pageable,
           List<String> paramValueList) {

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
                       "[" + catId + "]", brandId, paramStr, pageable);
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
	            		 distributorId,  "%[" + catId + "]%", priceLow, priceHigh, paramStr,
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
	            		 distributorId, "%[" + catId + "]%", priceLow, priceHigh, paramStr,
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
	            		 distributorId,  "%[" + catId + "]%", priceLow, priceHigh, paramStr,
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
	            		 distributorId,  "%[" + catId + "]%", priceLow, priceHigh, paramStr,
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
	            		 distributorId, "%[" + catId + "]%", brandId, priceLow, priceHigh,
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
	            		 distributorId,  "%[" + catId + "]%", brandId, priceLow, priceHigh,
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
	            		 distributorId, "%[" + catId + "]%", brandId, priceLow, priceHigh,
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
	            		 distributorId, "%[" + catId + "]%", brandId, priceLow, priceHigh,
	                    paramStr, pageRequest);
	}
	
	
	
	// 无价格区间
	//---
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
	            		 distributorId, "%[" + catId + "]%", paramStr, pageRequest);
	}
	// ---
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
	            		 distributorId, "%[" + catId + "]%", paramStr, pageRequest);
	}
	
	// --
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
	            		 distributorId, "%[" + catId + "]%", paramStr, pageRequest);
	}
	
	//--
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
	            		 distributorId, "%[" + catId + "]%", paramStr, pageRequest);
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
	            		 distributorId, "%[" + catId + "]%", brandId, paramStr, pageRequest);
	}
	
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrue(
			long distributorId, long catId, long brandId, Pageable pageable,
	        List<String> paramValueList) {
//	    PageRequest pageRequest = new PageRequest(page, size);
	
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
	            		 distributorId, "%[" + catId + "]%", brandId, paramStr, pageable);
	}
	
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndParamsLikeAndIsOnSaleTrue(
			long distributorId, long catId,  Pageable pageable,
	        List<String> paramValueList) {
//	    PageRequest pageRequest = new PageRequest(page, size);
	
	    String paramStr = "%";
	
	    for (int i = 0; i < paramValueList.size(); i++) {
	        String value = paramValueList.get(i);
	        if (!"".equals(value)) {
	            paramStr += value;
	            paramStr += "%";
	        }
	    }
	
	    return repository
	            .findByDistributorIdAndCategoryIdTreeLikeAndParamValueCollectLikeAndIsOnSaleTrueOrderBySoldNumber(
	            		 distributorId, "%[" + catId + "]%",  paramStr, pageable);
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
	            		 distributorId, "%[" + catId + "]%", brandId, paramStr, pageRequest);
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
	            		 distributorId, "%[" + catId + "]%", brandId, paramStr, pageRequest);
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
                  distributorId,  "%[" + catId + "]%", brandId, paramStr, pageRequest);
	} 
    
	
	//--------------------------------------
	public Page<TdDistributorGoods> searchAndDistributorIdAndIsOnSale(Long distributorId,String keywords,Boolean isOnSale,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByDistributorIdAndGoodsTitleLikeAndIsOnSaleOrDistributorIdAndSubGoodsTitleLikeAndIsOnSaleOrDistributorIdAndCodeLikeAndIsOnSale(
													distributorId,"%"+keywords+"%",isOnSale,pageRequest);
	}
	public Page<TdDistributorGoods> findAllOrderByOnSaleTime(int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "onSaleTime"));
		return repository.findAll(pageRequest);
	}
	public Page<TdDistributorGoods> findByIdAndIsOnSale(Long id,Boolean isOnSale,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByIdAndIsOnSale(id, isOnSale, pageRequest);
	}
	public Page<TdDistributorGoods> findByDistributorIdAndIsDistributorAndIsAudit(Long distributorId,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByDistributorIdAndIsDistributionAndIsAudit(distributorId, isDistribution, isAudit, pageRequest);
	}
	
	public Page<TdDistributorGoods> searchByDistributorIdAndIsDistributorAndIsAudit(Long distributorId,Boolean isDistribution,Boolean isAudit,String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByDistributorIdAndIsDistributionAndGoodsTitleLikeAndIsAuditOrDistributorIsAndIsDistributionAndCodeLikeAndIsAudit(
													distributorId, "%"+keywords+"%",isDistribution, isAudit, pageRequest);
	}
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndIsDistributorAndIsAudit(Long distributorId,Long catId,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		String catIdStr = "%[" + catId + "]%";
		return repository.findByDistributorIdAndCategoryIdTreeAndIsDistributionAndIsAudit(distributorId, catIdStr, isDistribution,isAudit, pageRequest);
	}
	
	public Page<TdDistributorGoods> searchByDistributorIdAndCategoryIdAndIsDistributorAndIsAudit(Long distributorId,Long catId,Boolean isDistribution,Boolean isAudit,String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		String catIdStr = "%[" + catId + "]%";
		return repository.findByDistributorIdAndCategoryIdTreeLikeAndIsDistributionAndIsAuditAndGoodsTitleLikeOrDistributorIsAndCategoryIdTreeLikeAndIsDistributionAndIsAuditAndCodeLike(
																		distributorId, catIdStr, isDistribution,isAudit, keywords, pageRequest);
	}
	
	public List<TdDistributorGoods> findByProviderIdAndGoodsIdAndIsDistributionTrue(Long proId,Long goodsId){
		if(null == proId || null == goodsId)
		{
			return null;
		}
		return repository.findByProviderIdAndGoodsIdAndIsDistributionTrue(proId, goodsId);
	}
	
	public List<TdDistributorGoods> findByProviderId(Long proId){
		if(null == proId )
		{
			return null;
		}
		return repository.findByProviderId(proId);
	}
	
	/**
	 * 检索
	 */
	public Page<TdDistributorGoods> searchAndDistributorIdAndIsOnSaleOrderBy(Long distributorId,String keywords,Boolean isOnSale,int page,int size,String sortName, Direction dir)
	{
		if (null == keywords || null == sortName) {
            return null;
        }

        PageRequest pageRequest = new PageRequest(page, size, new Sort(dir,sortName));
		return repository.findByDistributorIdAndGoodsTitleLikeAndIsOnSaleOrDistributorIdAndSubGoodsTitleLikeAndIsOnSaleOrDistributorIdAndCodeLikeAndIsOnSale(
													distributorId,"%"+keywords+"%",isOnSale,pageRequest);
	}
	
	public Page<TdDistributorGoods> searchGoodsAndIsOnSaleOrderBy(String keywords,Boolean isOnSale,int page,int size,String sortName, Direction dir)
	{
		if (null == keywords || null == sortName) {
            return null;
        }

        PageRequest pageRequest = new PageRequest(page, size, new Sort(dir,sortName));
		return repository.findByGoodsTitleContainingAndIsOnSaleOrSubGoodsTitleContainingAndIsOnSaleOrCodeContainingAndIsOnSaleOrDistributorTitleContainingAndIsOnSale(
								keywords, isOnSale, 
								keywords, isOnSale, 
								keywords, isOnSale,
								keywords, isOnSale,pageRequest);
	}

	public Page<TdDistributorGoods> findByDistributorIdAndIsOnSaleTrueOrderByOnSaleTime(Long distributorId,int page, int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		
		return repository.findByDistributorIdAndIsOnSaleTrueOrderByOnSaleTime(distributorId, pageRequest);
	}
	
	/**
	 * 新加disId字段方便筛选
	 * @author Max
	 * 
	 */
	public Page<TdDistributorGoods> searchAndDisIdAndIsOnSaleOrderBy(Long distributorId,String keywords,Boolean isOnSale,int page,int size,String sortName, Direction dir)
	{
		if (null == keywords || null == sortName) {
            return null;
        }

        PageRequest pageRequest = new PageRequest(page, size, new Sort(dir,sortName));
		return repository.findByDisIdAndGoodsTitleLikeAndIsOnSaleOrDisIdAndSubGoodsTitleLikeAndIsOnSaleOrDisIdAndCodeLikeAndIsOnSale(
													distributorId,"%"+keywords+"%",isOnSale,
													distributorId,"%"+keywords+"%",isOnSale,
													distributorId,"%"+keywords+"%",isOnSale,pageRequest);
	}
	/**
	 * @author Max
	 * 
	 */
	public Page<TdDistributorGoods> findByDisIdAndCategoryIdAndParamsLikeAndIsOnSaleTrue(Long disId,
            long catId, Pageable pageable, List<String> paramValueList) {

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

        return repository .findByDisIdAndCategoryIdTreeContainingAndParamValueCollectLikeAndIsOnSaleTrue(disId, "[" + catId + "]", paramStr, pageable);
    }
	
	public Page<TdDistributorGoods> findByDisIdAndCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrue(Long disId,
	           long catId, long brandId, Pageable pageable,
	           List<String> paramValueList) {

	       String paramStr = "%";

	       for (int i = 0; i < paramValueList.size(); i++) {
	           String value = paramValueList.get(i);
	           if (!"".equals(value)) {
	               paramStr += value;
	               paramStr += "%";
	           }
	       }

	       return repository .findByDisIdAndCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(
	                       						disId,"[" + catId + "]", brandId, paramStr, pageable);
	   }
	
	public List<TdDistributorGoods> findByDisIdAndIsOnSaleTrue(Long disId){
		if(null != disId){
			return repository.findByDisIdAndIsOnSaleTrue(disId);
		}
		return null;
	}
	
	public Page<TdDistributorGoods> findByDisId(Long disId,Long catId,Long brandId,
					Integer priceLow,Integer priceHigh,List<String> paramValueList,Pageable pageable)
	{
		Criteria<TdDistributorGoods> c = new Criteria<>();
		
		if(null != disId)
		{
			c.add(Restrictions.eq("disId", disId, true));
		}
		if(null != brandId)
		{
			c.add(Restrictions.eq("brandId", brandId, true));
		}
		if(null != catId)
		{
			c.add(Restrictions.like("categoryIdTree", "["+catId+"]", true));
		}
		if(null != priceLow)
		{
			c.add(Restrictions.gte("goodsPrice", priceLow, true));
		}
		if(null != priceHigh)
		{
			c.add(Restrictions.lte("goodsPrice", priceHigh, true));
		}
		
		String paramStr = "%";

       for (int i = 0; i < paramValueList.size(); i++) {
           String value = paramValueList.get(i);
           if (!"".equals(value)) {
               paramStr += value;
               paramStr += "%";
           }
       }
		c.add(Restrictions.like("paramValueCollect", paramStr, true));
		
		return repository.findAll(c, pageable);
	}
	
	
	
	public Page<TdDistributorGoods> findAll(Long disId,Boolean isOnsSale,Boolean isDistribution,Long catId,String keywords,PageRequest pageRequest)
	{
		Criteria<TdDistributorGoods> c = new Criteria<>();
		if(null != disId)
		{
			c.add(Restrictions.eq("disId", disId, true));
		}
		if(null != isOnsSale)
		{
			c.add(Restrictions.eq("isOnSale", isOnsSale, true));
		}
		if(null != catId)
		{
			c.add(Restrictions.like("categoryIdTree", "[" + catId + "]", true));
		}
		if(null != isDistribution){
			c.add(Restrictions.eq("isDistribution", isDistribution, true));
		}
		
		if(null != keywords && !"".equals(keywords.trim()))
		{
			c.add(Restrictions.or(Restrictions.like("goodsTitle", keywords, true),Restrictions.like("code", keywords, true)));
		}
		return repository.findAll(c, pageRequest);
	}
	
	public Page<TdDistributorGoods> findAll(Long disId,String recommendType,Long catId, int page,int size)
	{
		PageRequest pageRequest = null;
		
        if(null != recommendType)
		{
        	switch (recommendType) {
			case "isRecommendIndex":
				pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "isRecommendIndexTime"));
				break;
			case "isRecommendType":
				pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "isRecommendTypeTime"));
				break;
			case "isTouchRecommendType":
				pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "isTouchRecommendTypeTime"));
				break;
			case "isTouchHot":
				pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "isTouchHotTime"));
				break;
			case "isSetRecommend":
				pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "isSetRecommendTime"));
				break;
			case "isRecommendCategory":
				pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "isRecommendCategoryTime"));
				break;
			case "isSetRecommendType":
				pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "isSetRecommendTypeTime"));
				break;
			case "isSetTouchHot":
				pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "isSetTouchHotTime"));
				break;
			default:
				break;
			}
		}else{
        	pageRequest = new PageRequest(page, size);
        }
        Criteria<TdDistributorGoods> c = new Criteria<>();
        
        c.add(Restrictions.eq("isOnSale", true, true));
        
        if(null != disId)
		{
			c.add(Restrictions.eq("disId", disId, true));
		}
        if(null != recommendType){
        	c.add(Restrictions.eq(recommendType, true, true));
        }
        if(null != catId){
        	c.add(Restrictions.like("categoryIdTree", "["+catId+"]", true));
        }
        
        return repository.findAll(c, pageRequest);
	}
	
	/**
	 * 查询商品关联的其他商品
	 * @param goodsId
	 * @return
	 */
	public List<TdDistributorGoods> findRelevanceGoods(Long goodsId){
		if(null == goodsId){
			return null;
		}
//		List<TdDistributorGoods> goodsList = new ArrayList<TdDistributorGoods>();
		List<Long> idList = new ArrayList<Long>();
		
		// 查询商品作为主商品的关联记录
		List<TdRelevance> reList = tdRelevanceService.findAll(goodsId, null);
		if(null != reList){
			for (TdRelevance tdRelevance : reList) {
				idList.add(tdRelevance.getGoodsId2());
			}
		}
		
		// 商品作为被关联存在的关联记录
		List<TdRelevance> subList = tdRelevanceService.findAll(null, goodsId);
		if(null != subList){
			for (TdRelevance tdRelevance : subList) {
				//ID集合是否存在关联的主商品
				if(!idList.contains(tdRelevance.getGoodsId1())){
					idList.add(tdRelevance.getGoodsId1());
				}
			}
		}
		
		return this.findIds(idList);
	}
	
	
	public List<TdDistributorGoods> findIds(List<Long> ids){
		if(null != ids){
			return (List<TdDistributorGoods>) repository.findAll(ids);
		}
		return null;
	}
}
