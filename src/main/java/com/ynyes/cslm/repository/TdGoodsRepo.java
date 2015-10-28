package com.ynyes.cslm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdGoods;

/**
 * TdGoods 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdGoodsRepo extends
		PagingAndSortingRepository<TdGoods, Long>,
		JpaSpecificationExecutor<TdGoods> 
{
	TdGoods findById(Long Id);
	
    Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrue(String categoryId, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(String categoryId, Pageable page);
    
    Page<TdGoods> findByIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(Pageable page);
    
    Page<TdGoods> findByIsRecommendIndexTrueAndIsOnSaleTrueOrderByIdDesc(Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndIsRecommendIndexTrueAndIsOnSaleTrueOrderByIdDesc(String categoryId, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueOrderBySoldNumberDesc(String categoryId, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueOrderByOnSaleTimeDesc(String categoryId, Pageable page);
    
    List<TdGoods> findTop12ByIsOnSaleTrueOrderBySoldNumberDesc();
    
    Page<TdGoods> findByIsOnSaleTrue(Pageable page);
    
    Page<TdGoods> findByIsOnSaleTrueAndIsFlashSaleTrue(Pageable page);
    
    Page<TdGoods> findByIsOnSaleTrueAndIsGroupSaleTrue(Pageable page);
    
    Page<TdGoods> findByIsFlashSaleTrue(Pageable page);
    
    Page<TdGoods> findByIsGroupSaleTrue(Pageable page);
    
    Page<TdGoods> findByIsOnSaleFalse(Pageable page);
    
    Page<TdGoods> findByIsOnSaleFalseAndIsFlashSaleTrue(Pageable page);
    
    Page<TdGoods> findByIsOnSaleFalseAndIsGroupSaleTrue(Pageable page);
    
    List<TdGoods> findByIdAndIsOnSaleTrue(Iterable<Long> ids);
    
    Page<TdGoods> findByCategoryIdTreeContaining(String catId, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndIsFlashSaleTrue(String catId, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndIsGroupSaleTrue(String catId, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueAndIsGroupSaleTrue(String catId, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueAndIsFlashSaleTrue(String catId, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleFalse(String catId, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleFalseAndIsFlashSaleTrue(String catId, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleFalseAndIsGroupSaleTrue(String catId, Pageable page);
    
    Page<TdGoods> findByTitleContainingOrSubTitleContainingOrCodeContaining(String keywords1, String keywords2, String keywords3, Pageable page);
    
    Page<TdGoods> findByTitleContainingAndIsFlashSaleTrueOrSubTitleContainingAndIsFlashSaleTrueOrCodeContainingAndIsFlashSaleTrue(String keywords1, String keywords2, String keywords3, Pageable page);
    
    Page<TdGoods> findByTitleContainingAndIsGroupSaleTrueOrSubTitleContainingAndIsGroupSaleTrueOrCodeContainingAndIsGroupSaleTrue(String keywords1, String keywords2, String keywords3, Pageable page);
    
    Page<TdGoods> findByTitleContainingAndIsOnSaleTrueOrSubTitleContainingAndIsOnSaleTrueOrCodeContainingAndIsOnSaleTrue(String keywords1, String keywords2, String keywords3, Pageable page);
    
    Page<TdGoods> findByTitleContainingAndIsOnSaleTrueAndIsFlashSaleTrueOrSubTitleContainingAndIsOnSaleTrueAndIsFlashSaleTrueOrCodeContainingAndIsOnSaleTrueAndIsFlashSaleTrue(String keywords1, String keywords2, String keywords3, Pageable page);
    
    Page<TdGoods> findByTitleContainingAndIsOnSaleTrueAndIsGroupSaleTrueOrSubTitleContainingAndIsOnSaleTrueAndIsGroupSaleTrueOrCodeContainingAndIsOnSaleTrueAndIsGroupSaleTrue(String keywords1, String keywords2, String keywords3, Pageable page);
    
    Page<TdGoods> findByTitleContainingAndIsOnSaleFalseOrSubTitleContainingAndIsOnSaleFalseOrCodeContainingAndIsOnSaleFalse(String keywords1, String keywords2, String keywords3, Pageable page);
    
    Page<TdGoods> findByTitleContainingAndIsOnSaleFalseAndIsGroupSaleTrueOrSubTitleContainingAndIsOnSaleFalseAndIsGroupSaleTrueOrCodeContainingAndIsOnSaleFalseAndIsGroupSaleTrue(String keywords1, String keywords2, String keywords3, Pageable page);
    
    Page<TdGoods> findByTitleContainingAndIsOnSaleFalseAndIsFlashSaleTrueOrSubTitleContainingAndIsOnSaleFalseAndIsFlashSaleTrueOrCodeContainingAndIsOnSaleFalseAndIsFlashSaleTrue(String keywords1, String keywords2, String keywords3, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingOrCategoryIdTreeContainingAndSubTitleContainingOrCategoryIdTreeContainingAndCodeContaining(String catId1,
            String keywords1, 
            String catId2,
            String keywords2, 
            String catId3,
            String keywords3, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingAndIsGroupSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingAndIsGroupSaleTrueOrCategoryIdTreeContainingAndCodeContainingAndIsGroupSaleTrue(String catId1,
            String keywords1, 
            String catId2,
            String keywords2, 
            String catId3,
            String keywords3, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingAndIsFlashSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingAndIsFlashSaleTrueOrCategoryIdTreeContainingAndCodeContainingAndIsFlashSaleTrue(String catId1,
            String keywords1, 
            String catId2,
            String keywords2, 
            String catId3,
            String keywords3, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingAndIsOnSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingAndIsOnSaleTrueOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleTrue(String catId1,
            String keywords1, 
            String catId2,
            String keywords2, 
            String catId3,
            String keywords3, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingAndIsOnSaleTrueAndIsGroupSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingAndIsOnSaleTrueAndIsGroupSaleTrueOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleTrueAndIsGroupSaleTrue(String catId1,
            String keywords1, 
            String catId2,
            String keywords2, 
            String catId3,
            String keywords3, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingAndIsOnSaleTrueAndIsFlashSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingAndIsOnSaleTrueAndIsFlashSaleTrueOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleTrueAndIsFlashSaleTrue(String catId1,
            String keywords1, 
            String catId2,
            String keywords2, 
            String catId3,
            String keywords3, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingAndIsOnSaleFalseOrCategoryIdTreeContainingAndSubTitleContainingAndIsOnSaleTrueOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleTrue(String catId1,
            String keywords1, 
            String catId2,
            String keywords2, 
            String catId3,
            String keywords3, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingAndIsOnSaleFalseAndIsFlashSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingAndIsOnSaleTrueAndIsFlashSaleTrueOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleTrueAndIsFlashSaleTrue(String catId1,
            String keywords1, 
            String catId2,
            String keywords2, 
            String catId3,
            String keywords3, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndTitleContainingAndIsOnSaleFalseAndIsGroupSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingAndIsOnSaleTrueAndIsGroupSaleTrueOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleTrueAndIsGroupSaleTrue(String catId1,
            String keywords1, 
            String catId2,
            String keywords2, 
            String catId3,
            String keywords3, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(String categoryId, Long leftNumber, Double priceLow, Double priceHigh, String paramStr, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(String categoryId, Long brandId, Long leftNumber, Double priceLow, Double priceHigh, String paramStr, Pageable page);

    Page<TdGoods> findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(String categoryId, Long leftNumber, String paramStr, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(String categoryId, Long brandId, Long leftNumber, String paramStr, Pageable page);

    Page<TdGoods> findByCategoryIdTreeContainingAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(String categoryId, Double priceLow, Double priceHigh, String paramStr, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndBrandIdAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(String categoryId, Long brandId, Double priceLow, Double priceHigh, String paramStr, Pageable page);

    Page<TdGoods> findByCategoryIdTreeContainingAndParamValueCollectLikeAndIsOnSaleTrue(String categoryId, String paramStr, Pageable page);
    
    Page<TdGoods> findByCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(String categoryId, Long brandId, String paramStr, Pageable page);

    // 正在团购
    Page<TdGoods> findByIsGroupSaleTrueAndIsOnSaleTrueAndGroupSaleStopTimeAfterAndGroupSaleStartTimeBeforeOrderByGroupSaleStartTimeAsc(Date current, Date current1, Pageable page);
    
    // 正在百人团购
    Page<TdGoods> findByIsGroupSaleHundredTrueAndIsOnSaleTrueAndGroupSaleHundredStopTimeAfterAndGroupSaleHundredStartTimeBeforeOrderByGroupSaleHundredStartTimeAsc(Date current, Date current1, Pageable page);

    // 即将开始团购
    Page<TdGoods> findByIsGroupSaleTrueAndIsOnSaleTrueAndGroupSaleStartTimeAfterOrderByGroupSaleStartTimeAsc(Date current, Pageable page);
 
    // 即将开始百人团购
    Page<TdGoods> findByIsGroupSaleHundredTrueAndIsOnSaleTrueAndGroupSaleHundredStartTimeAfterOrderByGroupSaleHundredStartTimeAsc(Date current, Pageable page);
   
    // 已经结束团购
    Page<TdGoods> findByIsGroupSaleTrueAndIsOnSaleTrueAndGroupSaleStopTimeBeforeOrderByGroupSaleStartTimeAsc(Date current, Pageable page);
  
    // 已经结束百人团购
    Page<TdGoods> findByIsGroupSaleHundredTrueAndIsOnSaleTrueAndGroupSaleHundredStopTimeBeforeOrderByGroupSaleHundredStartTimeAsc(Date current, Pageable page);
   
    // 全部团购
    Page<TdGoods> findByIsGroupSaleTrueAndIsOnSaleTrueOrderByGroupSaleStartTimeAsc(Pageable page);
    
    // 全部百人团
    Page<TdGoods> findByIsGroupSaleHundredTrueAndIsOnSaleTrueOrderByGroupSaleHundredStartTimeAsc(Pageable page);
    
    // 正在秒杀
    Page<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStopTimeAfterAndFlashSaleStartTimeBeforeOrderByFlashSaleStartTimeAsc(Date current, Date current1, Pageable page);
    
    // 通过时间筛选
    Page<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStartTimeOrderByFlashSaleStartTimeAsc(Date startTime, Pageable page);
    List<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStartTimeOrderByFlashSaleStartTimeAsc(Date startTime);
    
    // 正在秒杀 限定开始时间
    Page<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStopTimeAfterAndFlashSaleStartTimeBeforeAndFlashSaleStartTimeOrderByFlashSaleStartTimeAsc(Date current, Date current1, Date startTime, Pageable page);
    
    // 即将开始秒杀
    Page<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStartTimeAfterOrderByFlashSaleStartTimeAsc(Date current, Pageable page);
    
    // 即将开始秒杀，限定开始时刻
    Page<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStartTimeAfterAndFlashSaleStartTimeOrderByFlashSaleStartTimeAsc(Date current, Date startTime, Pageable page);
    
    // 已经结束秒杀
    Page<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStopTimeBeforeOrderByFlashSaleStartTimeAsc(Date current, Pageable page);
    
    // 已经结束秒杀，限定开始时刻
    Page<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStopTimeBeforeAndFlashSaleStartTimeOrderByFlashSaleStartTimeAsc(Date current, Date startTime, Pageable page);
    
    // 全部秒杀
    Page<TdGoods> findByIsFlashSaleTrueAndIsOnSaleTrueOrderByFlashSaleStartTimeAsc(Pageable page);

    Page<TdGoods> findByTitleContainingIgnoreCaseAndIsOnSaleTrueOrSubTitleContainingAndIsOnSaleTrueOrParamValueCollectContainingAndIsOnSaleTrueOrCodeContainingAndIsOnSaleTrue(String key1,
            String key2,
            String key3,
            String key4,
            Pageable page);

    List<TdGoods> findByProductIdAndIsOnSaleTrue(Long productId);
    
    Page<TdGoods> findByReturnPriceNotAndIsOnSaleTrue(double returnPrice, Pageable page);
    
    Page<TdGoods> findByReturnPriceNotAndTitleContainingAndIsOnSaleTrue(double returnPrice, String keywords, Pageable page);
    
    //热销
    Page<TdGoods> findByIsHotTrueAndIsOnSaleTrueOrderByIdDesc(Pageable page);
}
