package com.ynyes.cslm.service;

import java.util.List;

import javax.transaction.Transactional;

import org.bouncycastle.crypto.signers.DSASigner;
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
	
	@Autowired
	TdDistributorService tdDistributorService;
	
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
    
    public List<Long> findByDistributorId(Long distributorId)
    {
    	return repository.findByDistributorIdAndGroupCategoryId(distributorId);
    }
    public List<Long> findByDistributorIdAndIsAudit(Long distributorId)
    {
    	return repository.findByDistributorIdAndIsAuditAndGroupCategoryId(distributorId);
    }
    
    public List<TdDistributorGoods> findByGoodsIdAndIsOnSale(Long goodsId)
    {
    	if(null == goodsId){
    		return null;
    	}
    	return repository.findByGoodsIdAndIsOnSaleTrue(goodsId);
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
    
//    public Page<TdDistributorGoods> findByDistributorIdAndIsOnSale(Long disId,Boolean onsale ,int page ,int size)
//	{
//		PageRequest pageRequest = new PageRequest(page, size);
//		return repository.findByDistributorIdAndIsOnSale(disId,onsale,pageRequest);
//	}
    
    public TdDistributorGoods findOne(Long id)
    {
    	return repository.findOne(id);
    }
    
    public Long findDistributorId(Long id)
    {
    	return repository.findDistributorId(id);
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
    
//    public TdDistributorGoods findByDistributorIdAndGoodsIdAndIsAudit(Long distributorId,Long goodsId)
//    {
//    	return repository.findByDistributorIdAndGoodsIdAndIsAudit(distributorId,goodsId,true);
//    }
    
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
    	
    	return repository.findByDistributorIdAndCategoryIdTreeLikeAndIsOnSaleTrueOrderBySoldNumberDesc(disId,catStr, pageRequest);
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
    
    
	// ---------------------------------------------------------------------------
	// ------------    后  台   页  面  ------------------------------------------
	// ---------------------------------------------------------------------------
	
	
	// ---------------------------------------------------------------------------
	// -------------   上架   分销   审核 ----------------------------------------
	// ---------------------------------------------------------------------------
/*	public Page<TdDistributorGoods> findByIsOnSaleAndIsDistributionAndIsAudit(Boolean isOnSale,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsOnSaleAndIsDistributionAndIsAudit(isOnSale, isDistribution, isAudit, pageRequest);
	}
    
	public Page<TdDistributorGoods> searchAndIsOnSaleAndIsDistributionAndIsAudit(String keywords,Boolean isOnSale,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsOnSaleAndIsDistributionAndIsAuditOrSubGoodsTitleContainingAndIsOnSaleAndIsDistributionAndIsAuditOrCodeContainingAndIsOnSaleAndIsDistributionAndIsAudit(
								keywords, isOnSale, isDistribution, isAudit,
								keywords, isOnSale, isDistribution, isAudit,
								keywords, isOnSale, isDistribution, isAudit,pageRequest);
	}
	
	public Page<TdDistributorGoods> findByIsOnSaleAndIsDistribution(Boolean isOnSale,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsOnSaleAndIsDistribution(isOnSale, isDistribution, pageRequest);
	}
    
	public Page<TdDistributorGoods> searchAndIsOnSaleAndIsDistribution(String keywords,Boolean isOnSale,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsOnSaleAndIsDistributionOrSubGoodsTitleContainingAndIsOnSaleAndIsDistributionOrCodeContainingAndIsOnSaleAndIsDistribution(
								keywords, isOnSale, isDistribution,
								keywords, isOnSale, isDistribution,
								keywords, isOnSale, isDistribution,pageRequest);
	}*/
    
    // ---------------------------------------------------------------------------
    // ------------  上架   审核 -------------------------------------------------	
	// ---------------------------------------------------------------------------
	public Page<TdDistributorGoods> findByIsOnSaleAndIsAudit(Boolean isOnSale,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsOnSaleAndIsAudit(isOnSale, isAudit, pageRequest);
	}
    
	public Page<TdDistributorGoods> searchAndIsOnSaleAndIsAudit(String keywords,Boolean isOnSale,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsOnSaleAndIsAuditOrSubGoodsTitleContainingAndIsOnSaleAndIsAuditOrCodeContainingAndIsOnSaleAndIsAudit(
								keywords, isOnSale, isAudit,
								keywords, isOnSale, isAudit,
								keywords, isOnSale, isAudit,pageRequest);
	}
	
	public Page<TdDistributorGoods> findByIsOnSale(Boolean isOnSale,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsOnSale(isOnSale, pageRequest);
	}
    
	public Page<TdDistributorGoods> searchAndIsOnSale(String keywords,Boolean isOnSale,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsOnSaleOrSubGoodsTitleContainingAndIsOnSaleOrCodeContainingAndIsOnSale(
								keywords, isOnSale, 
								keywords, isOnSale, 
								keywords, isOnSale, pageRequest);
	}
	
	// -----------------------------------------------------------
	// ----------- 分销 审核  ------------------------------------
 	// -----------------------------------------------------------
/*	public Page<TdDistributorGoods> findByIsDistributionAndIsAudit(Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsDistributionAndIsAudit( isDistribution, isAudit, pageRequest);
	}
    
	public Page<TdDistributorGoods> searchAndIsDistributionAndIsAudit(String keywords,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsDistributionAndIsAuditOrSubGoodsTitleContainingAndIsDistributionAndIsAuditOrCodeContainingAndIsDistributionAndIsAudit(
								keywords, isDistribution, isAudit,
								keywords, isDistribution, isAudit,
								keywords, isDistribution, isAudit,pageRequest);
	}
	
	public Page<TdDistributorGoods> findByIsDistribution(Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsDistribution( isDistribution, pageRequest);
	}
    
	public Page<TdDistributorGoods> searchAndIsDistribution(String keywords,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsDistributionOrSubGoodsTitleContainingAndIsDistributionOrCodeContainingAndIsDistribution(
								keywords,  isDistribution,
								keywords,  isDistribution,
								keywords,  isDistribution,pageRequest);
	}*/
	
	//
	public Page<TdDistributorGoods> findByIsAudit(Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsAudit( isAudit, pageRequest);
	}
    
	public Page<TdDistributorGoods> searchAndIsAudit(String keywords,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsAuditOrSubGoodsTitleContainingAndIsAuditOrCodeContainingAndIsAudit(
								keywords, isAudit,
								keywords, isAudit,
								keywords, isAudit,pageRequest);
	}
	
	public Page<TdDistributorGoods> findAll(int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findAll(pageRequest);
	}
	
	public Page<TdDistributorGoods> searchDistributorGoods(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingOrSubGoodsTitleContainingOrCodeContaining(
								keywords,  
								keywords, 
								keywords,  pageRequest);
	}
    
    // String catIdStr = "[" + catId + "]";
    
	// ------------------------------------------------------------------
    // ------------  分类  分销    上下架 ------------------------------
    // -----------------------------------------------------------------
	/*public Page<TdDistributorGoods> findByCategoryIdAndIsOnSaleAndIsDistributionAndIsAudit(Long catId,Boolean isOnSale,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "[" + catId + "]";
		return repository.findByCategoryIdTreeContainingAndIsOnSaleAndIsDistributionAndIsAudit(catIdStr,isOnSale, isDistribution, isAudit, pageRequest);
	}
    
	public Page<TdDistributorGoods> searchAndCategoryIdAndIsOnSaleAndIsDistributionAndIsAudit(Long catId,String keywords,Boolean isOnSale,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "[" + catId + "]";
		return repository.findByCategoryIdTreeContainingAndGoodsTitleContainingAndIsOnSaleAndIsDistributionAndIsAuditOrCategoryIdTreeContainingAndSubGoodsTitleContainingAndIsOnSaleAndIsDistributionAndIsAuditOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleAndIsDistributionAndIsAudit(
														catIdStr,keywords, isOnSale, isDistribution, isAudit,
														catIdStr,keywords, isOnSale, isDistribution, isAudit,
														catIdStr,keywords, isOnSale, isDistribution, isAudit,pageRequest);
	}
	
	public Page<TdDistributorGoods> findByCategoryIdAndIsOnSaleAndIsDistribution(Long catId,Boolean isOnSale,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "[" + catId + "]";
		return repository.findByCategoryIdTreeContainingAndIsOnSaleAndIsDistribution(catIdStr,isOnSale, isDistribution, pageRequest);
	}
    
	public Page<TdDistributorGoods> searchAndCategoryIdAndIsOnSaleAndIsDistribution(Long catId,String keywords,Boolean isOnSale,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "[" + catId + "]";
		return repository.findByCategoryIdTreeContainingAndGoodsTitleContainingAndIsOnSaleAndIsDistributionOrCategoryIdTreeContainingAndSubGoodsTitleContainingAndIsOnSaleAndIsDistributionOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleAndIsDistribution(
								catIdStr,keywords, isOnSale, isDistribution,
								catIdStr,keywords, isOnSale, isDistribution,
								catIdStr,keywords, isOnSale, isDistribution,pageRequest);
	}*/
    
    // ---------------------------------------------------------------------------
    // ------------  分类 上架   审核 -------------------------------------------------	
	// ---------------------------------------------------------------------------
	public Page<TdDistributorGoods> findByCategoryIdAndIsOnSaleAndIsAudit(Long catId,Boolean isOnSale,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "[" + catId + "]";
		return repository.findByCategoryIdTreeContainingAndIsOnSaleAndIsAudit(catIdStr,isOnSale, isAudit, pageRequest);
	}
    
	public Page<TdDistributorGoods> searchAndCategoryIdAndIsOnSaleAndIsAudit(Long catId,String keywords,Boolean isOnSale,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "[" + catId + "]";
		return repository.findByCategoryIdTreeContainingAndGoodsTitleContainingAndIsOnSaleAndIsAuditOrCategoryIdTreeContainingAndSubGoodsTitleContainingAndIsOnSaleAndIsAuditOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleAndIsAudit(
								catIdStr,keywords, isOnSale, isAudit,
								catIdStr,keywords, isOnSale, isAudit,
								catIdStr,keywords, isOnSale, isAudit,pageRequest);
	}
	
	public Page<TdDistributorGoods> findByCategoryIdAndIsOnSale(Long catId,Boolean isOnSale,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "[" + catId + "]";
		return repository.findByCategoryIdTreeContainingAndIsOnSale(catIdStr,isOnSale, pageRequest);
	}
    
	public Page<TdDistributorGoods> searchAndCategoryIdAndIsOnSale(Long catId,String keywords,Boolean isOnSale,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "[" + catId + "]";
		return repository.findByCategoryIdTreeContainingAndGoodsTitleContainingAndIsOnSaleOrCategoryIdTreeContainingAndSubGoodsTitleContainingAndIsOnSaleOrCategoryIdTreeContainingAndCodeContainingAndIsOnSale(
								catIdStr,keywords, isOnSale, 
								catIdStr,keywords, isOnSale, 
								catIdStr,keywords, isOnSale, pageRequest);
	}
	
	// -----------------------------------------------------------
	// -----------  分类 分销 审核  ------------------------------------
 	// -----------------------------------------------------------
/*	public Page<TdDistributorGoods> findByCategoryIdAndIsDistributionAndIsAudit(Long catId,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "[" + catId + "]";
		return repository.findByCategoryIdTreeContainingAndIsDistributionAndIsAudit(catIdStr,isDistribution, isAudit, pageRequest);
	}
    
	public Page<TdDistributorGoods> searchAndCategoryIdAndIsDistributionAndIsAudit(Long catId,String keywords,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "[" + catId + "]";
		return repository.findByCategoryIdTreeContainingAndGoodsTitleContainingAndIsDistributionAndIsAuditOrCategoryIdTreeContainingAndSubGoodsTitleContainingAndIsDistributionAndIsAuditOrCategoryIdTreeContainingAndCodeContainingAndIsDistributionAndIsAudit(
								catIdStr,keywords, isDistribution, isAudit,
								catIdStr,keywords, isDistribution, isAudit,
								catIdStr,keywords, isDistribution, isAudit,pageRequest);
	}
	
	public Page<TdDistributorGoods> findByCategoryIdAndIsDistribution(Long catId,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "[" + catId + "]";
		return repository.findByIsDistribution( isDistribution, pageRequest);
	}
    
	public Page<TdDistributorGoods> searchAndCategoryIdAndIsDistribution(Long catId,String keywords,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "[" + catId + "]";
		return repository.findByGoodsTitleContainingAndIsDistributionOrSubGoodsTitleContainingAndIsDistributionOrCodeContainingAndIsDistribution(
								keywords,  isDistribution,
								keywords,  isDistribution,
								keywords,  isDistribution,pageRequest);
	}*/
	
	//
	public Page<TdDistributorGoods> findByCategoryIdAndIsAudit(Long catId,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "[" + catId + "]";
		return repository.findByCategoryIdTreeContainingAndIsAudit(catIdStr, isAudit, pageRequest);
	}
    
	public Page<TdDistributorGoods> searchAndCategoryIdAndIsAudit(Long catId,String keywords,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "[" + catId + "]";
		return repository.findByCategoryIdTreeContainingAndGoodsTitleContainingAndIsAuditOrCategoryIdTreeContainingAndSubGoodsTitleContainingAndIsAuditOrCategoryIdTreeContainingAndCodeContainingAndIsAudit(
								catIdStr,keywords, isAudit,
								catIdStr,keywords, isAudit,
								catIdStr,keywords, isAudit,pageRequest);
	}
	
	public Page<TdDistributorGoods> findByCategoryId(Long catId,int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "[" + catId + "]";
		return repository.findByCategoryIdTreeContaining(catIdStr, pageRequest);
	}
	
	public Page<TdDistributorGoods> searchAndCategoryId(Long catId,String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "[" + catId + "]";
		return repository.findByCategoryIdTreeContainingAndGoodsTitleContainingOrCategoryIdTreeContainingAndSubGoodsTitleContainingOrCategoryIdTreeContainingAndCodeContaining(
								catIdStr,keywords,  
								catIdStr,keywords, 
								catIdStr,keywords,  pageRequest);
	}
	
	// --------------------------------------------------------------
	// ↓↓↓↓↓↓↓↓  选 择  超  市  ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
 	// --------------------------------------------------------------
	
	// ----------------  未   选   择   -----------------------------
	// ----------------     分   类     -----------------------------
	public Page<TdDistributorGoods> findByDistributorIdAndIsOnSaleAndIsAudit(Long distributorId,Boolean isOnSale,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByDistributorIdAndIsOnSaleAndIsAudit(distributorId,isOnSale,isAudit,pageRequest);
	}
	
	public Page<TdDistributorGoods> searchAndDistributorIdAndIsOnSaleAndIsAudit(Long distributorId,String keywords,Boolean isOnSale,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByDistributorIdAndGoodsTitleLikeAndIsOnSaleAndIsAuditOrDistributorIdAndSubGoodsTitleLikeAndIsOnSaleAndIsAuditOrDistributorIdAndCodeLikeAndIsOnSaleAndIsAudit(
													distributorId,"%"+keywords+"%",isOnSale,isAudit,
													distributorId,"%"+keywords+"%",isOnSale,isAudit,
													distributorId,"%"+keywords+"%",isOnSale,isAudit,pageRequest);
	}
	
	public Page<TdDistributorGoods> findByDistributorIdAndIsOnSale(Long distributorId,Boolean isOnSale,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByDistributorIdAndIsOnSale(distributorId, isOnSale, pageRequest);
	}
	
	public Page<TdDistributorGoods> searchAndDistributorIdAndIsOnSale(Long distributorId,String keywords,Boolean isOnSale,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByDistributorIdAndGoodsTitleLikeAndIsOnSaleOrDistributorIdAndSubGoodsTitleLikeAndIsOnSaleOrDistributorIdAndCodeLikeAndIsOnSale(
													distributorId,"%"+keywords+"%",isOnSale,
													distributorId,"%"+keywords+"%",isOnSale,
													distributorId,"%"+keywords+"%",isOnSale,pageRequest);
	}
	
	public Page<TdDistributorGoods> findByDistributorIdAndIsAudit(Long distributorId,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByDistributorIdAndIsAudit(distributorId,isAudit,pageRequest);
	}
	
	public Page<TdDistributorGoods> searchAndDistributorIdAndIsAudit(Long distributorId,String keywords,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByDistributorIdAndGoodsTitleLikeAndIsAuditOrDistributorIdAndSubGoodsTitleLikeAndIsAuditOrDistributorIdAndCodeLikeAndIsAudit(
													distributorId,"%"+keywords+"%",isAudit,
													distributorId,"%"+keywords+"%",isAudit,
													distributorId,"%"+keywords+"%",isAudit,pageRequest);
	}
	
	public Page<TdDistributorGoods> findByDistributorId(Long distributorId,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByDistributorId(distributorId,pageRequest);
	}
	
	public Page<TdDistributorGoods> searchAndDistributorId(Long distributId,String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByDistributorIdAndGoodsTitleLikeOrDistributorIdAndSubGoodsTitleLikeOrDistributorIdAndCodeLike(
													distributId, "%"+keywords+"%",
													distributId, "%"+keywords+"%",
													distributId, "%"+keywords+"%", pageRequest);
	}
	
	// -------------------  超市→分类 --------------------------
	
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndIsOnSaleAndIsAudit(Long distributorId,Long catId,Boolean isOnSale,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "%[" + catId + "]%";
		return repository.findByDistributorIdAndCategoryIdTreeLikeAndIsOnSaleAndIsAudit(distributorId,catIdStr,isOnSale,isAudit,pageRequest);
	}
	
	public Page<TdDistributorGoods> searchAndDistributorIdAndCategoryIdAndIsOnSaleAndIsAudit(Long distributorId,Long catId,String keywords,Boolean isOnSale,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "%[" + catId + "]%";
		return repository.findByDistributorIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsOnSaleAndIsAuditOrDistributorIdAndCategoryIdTreeLikeAndSubGoodsTitleLikeAndIsOnSaleAndIsAuditOrDistributorIdAndCategoryIdTreeLikeAndCodeLikeAndIsOnSaleAndIsAudit(
													distributorId,catIdStr,"%"+keywords+"%",isOnSale,isAudit,
													distributorId,catIdStr,"%"+keywords+"%",isOnSale,isAudit,
													distributorId,catIdStr,"%"+keywords+"%",isOnSale,isAudit,pageRequest);
	}
	
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndIsOnSale(Long distributorId,Long catId,Boolean isOnSale,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "%[" + catId + "]%";
		return repository.findByDistributorIdAndCategoryIdTreeLikeAndIsOnSale(distributorId,catIdStr, isOnSale, pageRequest);
	}
	
	public Page<TdDistributorGoods> searchAndDistributorIdAndCategoryIdAndIsOnSale(Long distributorId,Long catId,String keywords,Boolean isOnSale,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "%[" + catId + "]%";
		return repository.findByDistributorIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsOnSaleOrDistributorIdAndCategoryIdTreeLikeAndSubGoodsTitleLikeAndIsOnSaleOrDistributorIdAndCategoryIdTreeLikeAndCodeLikeAndIsOnSale(
													distributorId,catIdStr,"%"+keywords+"%",isOnSale,
													distributorId,catIdStr,"%"+keywords+"%",isOnSale,
													distributorId,catIdStr,"%"+keywords+"%",isOnSale,pageRequest);
	}
	
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryIdAndIsAudit(Long distributorId,Long catId,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "%[" + catId + "]%";
		return repository.findByDistributorIdAndCategoryIdTreeLikeAndIsAudit(distributorId,catIdStr,isAudit,pageRequest);
	}
	
	public Page<TdDistributorGoods> searchAndDistributorIdAndCategoryIdAndIsAudit(Long distributorId,Long catId,String keywords,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "%[" + catId + "]%";
		return repository.findByDistributorIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsAuditOrDistributorIdAndCategoryIdTreeLikeAndSubGoodsTitleLikeAndIsAuditOrDistributorIdAndCategoryIdTreeLikeAndCodeLikeAndIsAudit(
													distributorId,catIdStr,"%"+keywords+"%",isAudit,
													distributorId,catIdStr,"%"+keywords+"%",isAudit,
													distributorId,catIdStr,"%"+keywords+"%",isAudit,pageRequest);
	}
	
	public Page<TdDistributorGoods> findByDistributorIdAndCategoryId(Long distributorId,Long catId,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "%[" + catId + "]%";
		return repository.findByDistributorIdAndCategoryIdTreeLike(distributorId,catIdStr,pageRequest);
	}
	
	public Page<TdDistributorGoods> searchAndDistributorIdAndCategoryId(Long distributId,Long catId,String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catIdStr = "%[" + catId + "]%";
		return repository.findByDistributorIdAndCategoryIdTreeLikeAndGoodsTitleLikeOrDistributorIdAndCategoryIdTreeLikeAndSubGoodsTitleLikeOrDistributorIdAndCategoryIdTreeLikeAndCodeLike(
													distributId,catIdStr, "%"+keywords+"%",
													distributId,catIdStr, "%"+keywords+"%",
													distributId,catIdStr, "%"+keywords+"%", pageRequest);
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
													distributorId, "%"+keywords+"%",isDistribution, isAudit,
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
																		distributorId, catIdStr, isDistribution,isAudit, keywords,
																		distributorId, catIdStr, isDistribution,isAudit, keywords, pageRequest);
	}
	
	
	//--------------------------------------
	
	public Page<TdDistributorGoods> findByDistribuorIdAndIsRecommendIndexTrueOrderByOnSaleTime(Long disId,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByDistributorIdAndIsOnSaleTrueAndIsRecommendIndexTrueOrderByOnSaleTime(disId, pageRequest);
	}
	
	public Page<TdDistributorGoods> findAllByIsRecommendIndexTrueOrderByOnSaleTime(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "onSaleTime"));
		return repository.findByIsOnSaleTrueAndIsRecommendIndexTrueOrderByOnSaleTimeDesc(pageRequest);
	}
	
	public List<TdDistributorGoods> findByProviderIdAndGoodsIdAndIsDistributionTrue(Long proId,Long goodsId){
		return repository.findByProviderIdAndGoodsIdAndIsDistributionTrue(proId, goodsId);
	}
	
	/**
	 * 检索
	 */
	public Page<TdDistributorGoods> searchAndDistributorIdAndIsOnSaleOrderBy(Long distributorId,String keywords,Boolean isOnSale,int page,int size,String sortName, Direction dir)
	{
//		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		if (null == keywords || null == sortName) {
            return null;
        }

        PageRequest pageRequest = new PageRequest(page, size, new Sort(dir,sortName));
		return repository.findByDistributorIdAndGoodsTitleLikeAndIsOnSaleOrDistributorIdAndSubGoodsTitleLikeAndIsOnSaleOrDistributorIdAndCodeLikeAndIsOnSale(
													distributorId,"%"+keywords+"%",isOnSale,
													distributorId,"%"+keywords+"%",isOnSale,
													distributorId,"%"+keywords+"%",isOnSale,pageRequest);
	}
	
	public Page<TdDistributorGoods> searchGoodsAndIsOnSaleOrderBy(String keywords,Boolean isOnSale,int page,int size,String sortName, Direction dir)
	{
//		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
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
	
}
