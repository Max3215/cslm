package com.ynyes.cslm.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.cslm.entity.TdBrand;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdGoodsCombination;
import com.ynyes.cslm.entity.TdGoodsGift;
import com.ynyes.cslm.entity.TdGoodsParameter;
import com.ynyes.cslm.entity.TdParameter;
import com.ynyes.cslm.entity.TdParameterCategory;
import com.ynyes.cslm.entity.TdPriceChangeLog;
import com.ynyes.cslm.entity.TdProductCategory;
import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdProviderGoods;
import com.ynyes.cslm.entity.TdSite;
import com.ynyes.cslm.entity.TdWarehouse;
import com.ynyes.cslm.repository.TdGoodsRepo;

/**
 * TdGoods 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdGoodsService {
    @Autowired
    TdGoodsRepo repository;

    @Autowired
    TdProductCategoryService tdProductCategoryService;

    @Autowired
    TdBrandService tdBrandService;

    @Autowired
    TdArticleService tdArticleService;

    @Autowired
    TdParameterService tdParameterService;

    @Autowired
    TdGoodsParameterService tdGoodsParameterService;

    @Autowired
    TdWarehouseService tdWarehouseService;

    @Autowired
    TdProviderService tdProviderService;

    @Autowired
    TdGoodsGiftService tdGoodsGiftService;

    @Autowired
    TdGoodsCombinationService tdGoodsCombinationService;

    @Autowired
    TdPriceChangeLogService tdPriceChangeLogService;

    @Autowired
    TdSiteService tdSiteService;

    @Autowired
    TdDistributorGoodsService tdDistributorGoodsService;
    
    @Autowired
    TdProviderGoodsService tdProviderGoodsService;
    
    @Autowired
    TdParameterCategoryService tdParameterCategoryService;
    
    /**
     * 热销
     * 2015-9-1  11:32
     * @author libiao
     * @return
     */
    public Page<TdGoods> findByIsHotTrueAndIsOnSaleTrueOrderByIdDesc(int page,int size){
    	PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
    	return repository.findByIsHotTrueAndIsOnSaleTrueOrderByIdDesc(pageRequest);
    }
    /**
     * 查找所有商品
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<TdGoods> findAll(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository.findAll(pageRequest);
    }

    public TdGoods findById(Long id) {
        return repository.findById(id);
    }

    public Page<TdGoods> findAllOrderBySortIdAsc(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository.findAll(pageRequest);
    }

    public Page<TdGoods> findByIsFlashSaleTrueOrderBySortIdAsc(int page,
            int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository.findByIsFlashSaleTrue(pageRequest);
    }

    public Page<TdGoods> findByIsGroupSaleTrueOrderBySortIdAsc(int page,
            int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository.findByIsGroupSaleTrue(pageRequest);
    }

    public Page<TdGoods> findByIsOnSaleTrueOrderBySortIdAsc(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository.findByIsOnSaleTrue(pageRequest);
    }

    public Page<TdGoods> findByIsOnSaleTrueAndFlashSaleTrueOrderBySortIdAsc(
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository.findByIsOnSaleTrueAndIsFlashSaleTrue(pageRequest);
    }

    public Page<TdGoods> findByIsOnSaleTrueAndGroupSaleTrueOrderBySortIdAsc(
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository.findByIsOnSaleTrueAndIsGroupSaleTrue(pageRequest);
    }

    public Page<TdGoods> findByIsOnSaleFalseOrderBySortIdAsc(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository.findByIsOnSaleFalse(pageRequest);
    }

    public Page<TdGoods> findByIsOnSaleFalseAndIsFlashSaleTrueOrderBySortIdAsc(
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository.findByIsOnSaleFalseAndIsFlashSaleTrue(pageRequest);
    }

    public Page<TdGoods> findByIsOnSaleFalseAndIsGroupSaleTrueOrderBySortIdAsc(
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository.findByIsOnSaleFalseAndIsGroupSaleTrue(pageRequest);
    }

    public Page<TdGoods> findAllAndIsOnSaleTrue(int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository.findByIsOnSaleTrue(pageRequest);
    }

    public List<TdGoods> findTop12ByIsOnSaleTrueOrderBySoldNumberDesc() {
        return repository.findTop12ByIsOnSaleTrueOrderBySoldNumberDesc();
    }

    public Page<TdGoods> searchAndOrderBySortIdAsc(String keywords, int page,
            int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByTitleContainingOrSubTitleContainingOrCodeContaining(
                        keywords, keywords, keywords, pageRequest);
    }

    public Page<TdGoods> searchAndIsFlashSaleTrueOrderBySortIdAsc(
            String keywords, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByTitleContainingAndIsFlashSaleTrueOrSubTitleContainingAndIsFlashSaleTrueOrCodeContainingAndIsFlashSaleTrue(
                        keywords, keywords, keywords, pageRequest);
    }

    public Page<TdGoods> searchAndIsGroupSaleTrueOrderBySortIdAsc(
            String keywords, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByTitleContainingAndIsGroupSaleTrueOrSubTitleContainingAndIsGroupSaleTrueOrCodeContainingAndIsGroupSaleTrue(
                        keywords, keywords, keywords, pageRequest);
    }

    public Page<TdGoods> searchAndIsOnSaleTrueOrderBySortIdAsc(String keywords,
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByTitleContainingAndIsOnSaleTrueOrSubTitleContainingAndIsOnSaleTrueOrCodeContainingAndIsOnSaleTrue(
                        keywords, keywords, keywords, pageRequest);
    }

    public Page<TdGoods> searchAndIsOnSaleTrueAndIsGroupSaleTrueOrderBySortIdAsc(
            String keywords, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByTitleContainingAndIsOnSaleTrueAndIsGroupSaleTrueOrSubTitleContainingAndIsOnSaleTrueAndIsGroupSaleTrueOrCodeContainingAndIsOnSaleTrueAndIsGroupSaleTrue(
                        keywords, keywords, keywords, pageRequest);
    }

    public Page<TdGoods> searchAndIsOnSaleTrueAndIsFlashSaleTrueOrderBySortIdAsc(
            String keywords, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByTitleContainingAndIsOnSaleTrueAndIsFlashSaleTrueOrSubTitleContainingAndIsOnSaleTrueAndIsFlashSaleTrueOrCodeContainingAndIsOnSaleTrueAndIsFlashSaleTrue(
                        keywords, keywords, keywords, pageRequest);
    }

    public Page<TdGoods> searchAndIsOnSaleFalseOrderBySortIdAsc(
            String keywords, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByTitleContainingAndIsOnSaleFalseOrSubTitleContainingAndIsOnSaleFalseOrCodeContainingAndIsOnSaleFalse(
                        keywords, keywords, keywords, pageRequest);
    }

    public Page<TdGoods> searchAndIsOnSaleFalseAndIsFlashSaleTrueOrderBySortIdAsc(
            String keywords, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByTitleContainingAndIsOnSaleFalseAndIsFlashSaleTrueOrSubTitleContainingAndIsOnSaleFalseAndIsFlashSaleTrueOrCodeContainingAndIsOnSaleFalseAndIsFlashSaleTrue(
                        keywords, keywords, keywords, pageRequest);
    }

    public Page<TdGoods> searchAndIsOnSaleFalseAndIsGroupSaleTrueOrderBySortIdAsc(
            String keywords, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByTitleContainingAndIsOnSaleFalseAndIsGroupSaleTrueOrSubTitleContainingAndIsOnSaleFalseAndIsGroupSaleTrueOrCodeContainingAndIsOnSaleFalseAndIsGroupSaleTrue(
                        keywords, keywords, keywords, pageRequest);
    }

    public Page<TdGoods> searchAndFindByCategoryIdOrderBySortIdAsc(
            String keywords, Long categoryId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + categoryId + "]";

        return repository
                .findByCategoryIdTreeContainingAndTitleContainingOrCategoryIdTreeContainingAndSubTitleContainingOrCategoryIdTreeContainingAndCodeContaining(
                        catIdStr, keywords, catIdStr, keywords, catIdStr,
                        keywords, pageRequest);
    }

    public Page<TdGoods> searchAndFindByCategoryIdAndIsGroupSaleTrueOrderBySortIdAsc(
            String keywords, Long categoryId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + categoryId + "]";

        return repository
                .findByCategoryIdTreeContainingAndTitleContainingAndIsGroupSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingAndIsGroupSaleTrueOrCategoryIdTreeContainingAndCodeContainingAndIsGroupSaleTrue(
                        catIdStr, keywords, catIdStr, keywords, catIdStr,
                        keywords, pageRequest);
    }

    public Page<TdGoods> searchAndFindByCategoryIdAndIsFlashSaleTrueOrderBySortIdAsc(
            String keywords, Long categoryId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + categoryId + "]";

        return repository
                .findByCategoryIdTreeContainingAndTitleContainingAndIsFlashSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingAndIsFlashSaleTrueOrCategoryIdTreeContainingAndCodeContainingAndIsFlashSaleTrue(
                        catIdStr, keywords, catIdStr, keywords, catIdStr,
                        keywords, pageRequest);
    }

    public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleTrueOrderBySortIdAsc(
            String keywords, Long categoryId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + categoryId + "]";

        return repository
                .findByCategoryIdTreeContainingAndTitleContainingAndIsOnSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingAndIsOnSaleTrueOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleTrue(
                        catIdStr, keywords, catIdStr, keywords, catIdStr,
                        keywords, pageRequest);
    }

    public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleTrueAndIsGroupSaleTrueOrderBySortIdAsc(
            String keywords, Long categoryId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + categoryId + "]";

        return repository
                .findByCategoryIdTreeContainingAndTitleContainingAndIsOnSaleTrueAndIsGroupSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingAndIsOnSaleTrueAndIsGroupSaleTrueOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleTrueAndIsGroupSaleTrue(
                        catIdStr, keywords, catIdStr, keywords, catIdStr,
                        keywords, pageRequest);
    }

    public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleTrueAndIsFlashSaleTrueOrderBySortIdAsc(
            String keywords, Long categoryId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + categoryId + "]";

        return repository
                .findByCategoryIdTreeContainingAndTitleContainingAndIsOnSaleTrueAndIsFlashSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingAndIsOnSaleTrueAndIsFlashSaleTrueOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleTrueAndIsFlashSaleTrue(
                        catIdStr, keywords, catIdStr, keywords, catIdStr,
                        keywords, pageRequest);
    }

    public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleFalseOrderBySortIdAsc(
            String keywords, Long categoryId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + categoryId + "]";

        return repository
                .findByCategoryIdTreeContainingAndTitleContainingAndIsOnSaleFalseOrCategoryIdTreeContainingAndSubTitleContainingAndIsOnSaleTrueOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleTrue(
                        catIdStr, keywords, catIdStr, keywords, catIdStr,
                        keywords, pageRequest);
    }

    public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleFalseAndIsFlashSaleTrueOrderBySortIdAsc(
            String keywords, Long categoryId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + categoryId + "]";

        return repository
                .findByCategoryIdTreeContainingAndTitleContainingAndIsOnSaleFalseAndIsFlashSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingAndIsOnSaleTrueAndIsFlashSaleTrueOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleTrueAndIsFlashSaleTrue(
                        catIdStr, keywords, catIdStr, keywords, catIdStr,
                        keywords, pageRequest);
    }

    public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleFalseAndIsGroupSaleTrueOrderBySortIdAsc(
            String keywords, Long categoryId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + categoryId + "]";

        return repository
                .findByCategoryIdTreeContainingAndTitleContainingAndIsOnSaleFalseAndIsGroupSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingAndIsOnSaleTrueAndIsGroupSaleTrueOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleTrueAndIsGroupSaleTrue(
                        catIdStr, keywords, catIdStr, keywords, catIdStr,
                        keywords, pageRequest);
    }

    public Page<TdGoods> searchAndFindByCategoryIdAndIsOnSaleFalseAndIsFlashSaleTrueAndIsGroupSaleTrueOrderBySortIdAsc(
            String keywords, Long categoryId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + categoryId + "]";

        return repository
                .findByCategoryIdTreeContainingAndTitleContainingAndIsOnSaleFalseAndIsGroupSaleTrueOrCategoryIdTreeContainingAndSubTitleContainingAndIsOnSaleTrueAndIsGroupSaleTrueOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleTrueAndIsGroupSaleTrue(
                        catIdStr, keywords, catIdStr, keywords, catIdStr,
                        keywords, pageRequest);
    }

    public List<TdGoods> findByIdAndIsOnSaleTrue(Iterable<Long> ids) {
        return repository.findByIdAndIsOnSaleTrue(ids);
    }

    /**
     * 按类型查找所有商品
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<TdGoods> findByReturnPriceNotZeroAndIsOnSaleTrue(int page,
            int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository.findByReturnPriceNotAndIsOnSaleTrue(0.0, pageRequest);
    }

    public Page<TdGoods> findByReturnPriceNotZeroAndSearchAndIsOnSaleTrue(
            int page, int size, String keywords) {
        if (null == keywords) {
            return null;
        }

        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByReturnPriceNotAndTitleContainingAndIsOnSaleTrue(0.0,
                        keywords, pageRequest);
    }

    /**
     * 正在团购商品
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<TdGoods> findByGroupSalingOrderByGroupSaleStartTimeAsc(
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByIsGroupSaleTrueAndIsOnSaleTrueAndGroupSaleStopTimeAfterAndGroupSaleStartTimeBeforeOrderByGroupSaleStartTimeAsc(
                        new Date(), new Date(), pageRequest);
    }
    
    /**
     * 正在百人团购商品
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<TdGoods> findByGroupSalingHundredOrderByGroupSaleStartTimeAsc(
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByIsGroupSaleHundredTrueAndIsOnSaleTrueAndGroupSaleHundredStopTimeAfterAndGroupSaleHundredStartTimeBeforeOrderByGroupSaleHundredStartTimeAsc(
                        new Date(), new Date(), pageRequest);
    }

    /**
     * 即将开始团购
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<TdGoods> findByGroupSaleGoingToStartOrderByGroupSaleStartTimeAsc(
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByIsGroupSaleTrueAndIsOnSaleTrueAndGroupSaleStartTimeAfterOrderByGroupSaleStartTimeAsc(
                        new Date(), pageRequest);
    }
    
    /**
	 * @author lc
	 * 即将开始百人团购
	 */
	public Page<TdGoods> findByGroupSaleGoingToHundredOrderByGroupSaleHundredStartTimeAsc(int page, int size){
		 PageRequest pageRequest = new PageRequest(page, size, new Sort(
	                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));
		 return repository.findByIsGroupSaleHundredTrueAndIsOnSaleTrueAndGroupSaleHundredStartTimeAfterOrderByGroupSaleHundredStartTimeAsc(
                 new Date(), pageRequest);
	} 
	
    /**
     * 已结束团购
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<TdGoods> findByGroupSaleEndedOrderByGroupSaleStartTimeAsc(
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByIsGroupSaleTrueAndIsOnSaleTrueAndGroupSaleStopTimeBeforeOrderByGroupSaleStartTimeAsc(
                        new Date(), pageRequest);
    }
    
    /**
     * 已经结束百人团购
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<TdGoods> findByGroupSaleHundredEndedOrderByGroupSaleHundredStartTimeAsc(
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByIsGroupSaleHundredTrueAndIsOnSaleTrueAndGroupSaleHundredStopTimeBeforeOrderByGroupSaleHundredStartTimeAsc(
                        new Date(), pageRequest);
    }
    
    /**
	 * @author lc
	 * 已经结束百人团购
	 */
    public Page<TdGoods> findByGroupSaleEndedHundredOrderByGroupSaleHundredStartTimeAsc(
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByIsGroupSaleHundredTrueAndIsOnSaleTrueAndGroupSaleHundredStopTimeBeforeOrderByGroupSaleHundredStartTimeAsc(
                        new Date(), pageRequest);
    }
    
    /**
     * 所有团购
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<TdGoods> findByGroupSaleAllOrderByGroupSaleStartTimeAsc(
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByIsGroupSaleTrueAndIsOnSaleTrueOrderByGroupSaleStartTimeAsc(pageRequest);
    }
    
    /**
     * 所有百人团
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<TdGoods> findByGroupSaleHundredAllOrderByGroupSaleHundredStartTimeAsc(
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByIsGroupSaleHundredTrueAndIsOnSaleTrueOrderByGroupSaleHundredStartTimeAsc(pageRequest);
    }
    

    /**
     * 正在秒杀商品
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<TdGoods> findByFlashSalingOrderByFlashSaleStartTimeAsc(
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStopTimeAfterAndFlashSaleStartTimeBeforeOrderByFlashSaleStartTimeAsc(
                        new Date(), new Date(), pageRequest);
    }

    /**
     * 通过开始时间查找秒杀商品
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<TdGoods> findByIsFlashSaleTrueAndFlashSaleStartTimeOrderByFlashSaleStartTimeAsc(
            Date startTime, int page, int size) 
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStartTimeOrderByFlashSaleStartTimeAsc(
                        startTime, pageRequest);
    }
    public List<TdGoods> findByIsFlashSaleTrueAndFlashSaleStartTimeOrderByFlashSaleStartTimeAsc(Date startTime){
    	return repository.findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStartTimeOrderByFlashSaleStartTimeAsc(startTime);
    }
    /**
     * 正在秒杀商品，限定开始时间
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<TdGoods> findByFlashSalingAndStartTimeOrderByFlashSaleStartTimeAsc(
            Date startTime, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStopTimeAfterAndFlashSaleStartTimeBeforeAndFlashSaleStartTimeOrderByFlashSaleStartTimeAsc(
                        new Date(), new Date(), startTime, pageRequest);
    }

    /**
     * 即将开始秒杀
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<TdGoods> findByFlashSaleGoingToStartOrderByFlashSaleStartTimeAsc(
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStartTimeAfterOrderByFlashSaleStartTimeAsc(
                        new Date(), pageRequest);
    }

    /**
     * 即将开始秒杀，限定开始时间
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<TdGoods> findByFlashSaleGoingToStartAndStartTimeOrderByFlashSaleStartTimeAsc(
            Date startTime, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStartTimeAfterAndFlashSaleStartTimeOrderByFlashSaleStartTimeAsc(
                        new Date(), startTime, pageRequest);
    }

    /**
     * 已结束秒杀
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<TdGoods> findByFlashSaleEndedOrderByFlashSaleStartTimeAsc(
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStopTimeBeforeOrderByFlashSaleStartTimeAsc(
                        new Date(), pageRequest);
    }

    /**
     * 已结束秒杀，限定开始时间
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<TdGoods> findByFlashSaleEndedAndStartTimeOrderByFlashSaleStartTimeAsc(
            Date startTime, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByIsFlashSaleTrueAndIsOnSaleTrueAndFlashSaleStopTimeBeforeAndFlashSaleStartTimeOrderByFlashSaleStartTimeAsc(
                        new Date(), startTime, pageRequest);
    }

    /**
     * 所有秒杀
     * 
     * @param page
     * @param size
     * @return
     */
    public Page<TdGoods> findByFlashSaleAllOrderByFlashSaleStartTimeAsc(
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByIsFlashSaleTrueAndIsOnSaleTrueOrderByFlashSaleStartTimeAsc(pageRequest);
    }

    public Page<TdGoods> findByCategoryIdTreeContainingOrderBySortIdAsc(
            Long catId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + catId + "]";

        return repository.findByCategoryIdTreeContaining(
                catIdStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdTreeContainingAndIsFlashSaleTrueOrderBySortIdAsc(
            Long catId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + catId + "]";

        return repository
                .findByCategoryIdTreeContainingAndIsFlashSaleTrue(
                        catIdStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdTreeContainingAndIsGroupSaleTrueOrderBySortIdAsc(
            Long catId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + catId + "]";

        return repository
                .findByCategoryIdTreeContainingAndIsGroupSaleTrue(
                        catIdStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueOrderBySortIdAsc(
            Long catId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + catId + "]";

        return repository
                .findByCategoryIdTreeContainingAndIsOnSaleTrue(
                        catIdStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueAndIsGroupSaleTrueOrderBySortIdAsc(
            Long catId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + catId + "]";

        return repository
                .findByCategoryIdTreeContainingAndIsOnSaleTrueAndIsGroupSaleTrue(
                        catIdStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueAndIsFlashSaleTrueOrderBySortIdAsc(
            Long catId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + catId + "]";

        return repository
                .findByCategoryIdTreeContainingAndIsOnSaleTrueAndIsFlashSaleTrue(
                        catIdStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleFalseOrderBySortIdAsc(
            Long catId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + catId + "]";

        return repository
                .findByCategoryIdTreeContainingAndIsOnSaleFalse(
                        catIdStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleFalseAndIsFlashSaleTrueOrderBySortIdAsc(
            Long catId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + catId + "]";

        return repository
                .findByCategoryIdTreeContainingAndIsOnSaleFalseAndIsFlashSaleTrue(
                        catIdStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdTreeContainingAndIsOnSaleFalseAndIsGroupSaleTrueOrderBySortIdAsc(
            Long catId, int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catIdStr = "[" + catId + "]";

        return repository
                .findByCategoryIdTreeContainingAndIsOnSaleFalseAndIsGroupSaleTrue(
                        catIdStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndIsOnSaleTrue(Long catId, int page,
            int size) {
        if (null == catId) {
            return null;
        }

        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catStr = "[" + catId + "]";

        return repository.findByCategoryIdTreeContainingAndIsOnSaleTrue(catStr,
                pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(
            Long catId, int page, int size) {
        if (null == catId) {
            return null;
        }

        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catStr = "[" + catId + "]";

        return repository
                .findByCategoryIdTreeContainingAndIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(
                        catStr, pageRequest);
    }

    public Page<TdGoods> findByIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByIsRecommendTypeTrueAndIsOnSaleTrueOrderByIdDesc(pageRequest);
    }

    public Page<TdGoods> findByIsRecommendIndexTrueAndIsOnSaleTrueOrderByIdDesc(
            int page, int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        return repository
                .findByIsRecommendIndexTrueAndIsOnSaleTrueOrderByIdDesc(pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndIsRecommendIndexTrueAndIsOnSaleTrueOrderByIdDesc(
            Long catId, int page, int size) {
        if (null == catId) {
            return null;
        }

        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catStr = "[" + catId + "]";

        return repository
                .findByCategoryIdTreeContainingAndIsRecommendIndexTrueAndIsOnSaleTrueOrderByIdDesc(
                        catStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndIsOnSaleTrueOrderBySoldNumberDesc(
            Long catId, int page, int size) {
        if (null == catId) {
            return null;
        }

        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catStr = "[" + catId + "]";

        return repository
                .findByCategoryIdTreeContainingAndIsOnSaleTrueOrderBySoldNumberDesc(
                        catStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndIsOnSaleTrueOrderByOnSaleTimeDesc(
            Long catId, int page, int size) {
        if (null == catId) {
            return null;
        }

        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "sortId").and(new Sort(Direction.DESC, "id")));

        String catStr = "[" + catId + "]";

        return repository
                .findByCategoryIdTreeContainingAndIsOnSaleTrueOrderByOnSaleTimeDesc(
                        catStr, pageRequest);
    }

    public Page<TdGoods> findByIsOnSaleTrueOrderBySoldNumberDesc(int page,
            int size) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "soldNumber"));

        return repository.findByIsOnSaleTrue(pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
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
                .findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", 0L, priceLow, priceHigh, paramStr,
                        pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
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
                .findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", 0L, priceLow, priceHigh, paramStr,
                        pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
            long catId, double priceLow, double priceHigh, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "salePrice"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", 0L, priceLow, priceHigh, paramStr,
                        pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
            long catId, double priceLow, double priceHigh, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "salePrice"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", 0L, priceLow, priceHigh, paramStr,
                        pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeAsc(
            long catId, double priceLow, double priceHigh, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "onSaleTime"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", 0L, priceLow, priceHigh, paramStr,
                        pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeDesc(
            long catId, double priceLow, double priceHigh, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "onSaleTime"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", 0L, priceLow, priceHigh, paramStr,
                        pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
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
                .findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, 0L, priceLow, priceHigh,
                        paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
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
                .findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, 0L, priceLow, priceHigh,
                        paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
            long catId, long brandId, double priceLow, double priceHigh,
            int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "salePrice"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, 0L, priceLow, priceHigh,
                        paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
            long catId, long brandId, double priceLow, double priceHigh,
            int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "salePrice"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, 0L, priceLow, priceHigh,
                        paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeAsc(
            long catId, long brandId, double priceLow, double priceHigh,
            int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "onSaleTime"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, 0L, priceLow, priceHigh,
                        paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeDesc(
            long catId, long brandId, double priceLow, double priceHigh,
            int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "onSaleTime"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, 0L, priceLow, priceHigh,
                        paramStr, pageRequest);
    }

    // 无价格区间
    public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
            long catId, int page, int size, List<String> paramValueList) {
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
                .findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", 0L, paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
            long catId, int page, int size, List<String> paramValueList) {
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
                .findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", 0L, paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
            long catId, int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "salePrice"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", 0L, paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
            long catId, int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "salePrice"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", 0L, paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeAsc(
            long catId, int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "onSaleTime"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", 0L, paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeDesc(
            long catId, int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "onSaleTime"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", 0L, paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
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
                .findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, 0L, paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
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
                .findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, 0L, paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
            long catId, long brandId, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "salePrice"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, 0L, paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
            long catId, long brandId, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "salePrice"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, 0L, paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeAsc(
            long catId, long brandId, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "onSaleTime"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, 0L, paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndBrandIdAndLeftNumberGreaterThanZeroAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeDesc(
            long catId, long brandId, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "onSaleTime"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndLeftNumberGreaterThanAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, 0L, paramStr, pageRequest);
    }

    // 显示有货和无货商品
    public Page<TdGoods> findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
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
                .findByCategoryIdTreeContainingAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", priceLow, priceHigh, paramStr,
                        pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
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
                .findByCategoryIdTreeContainingAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", priceLow, priceHigh, paramStr,
                        pageRequest);
    }

    public Page<TdGoods> searchGoods(String keywords, int page, int size) {
        if (null == keywords) {
            return null;
        }

        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "id"));

        return repository
                .findByTitleContainingIgnoreCaseAndIsOnSaleTrueOrSubTitleContainingAndIsOnSaleTrueOrParamValueCollectContainingAndIsOnSaleTrueOrCodeContainingAndIsOnSaleTrue(
                        keywords, keywords, keywords, keywords, pageRequest);
    }

    /**
     * 搜索商品
     * 
     * @param keywords
     *            关键字
     * @param page
     *            页号
     * @param size
     *            页大小
     * @param sortName
     *            排序字段名
     * @param sd
     *            排序方向
     * @return
     */
    public Page<TdGoods> searchGoods(String keywords, int page, int size,
            String sortName, Direction dir) {
        if (null == keywords || null == sortName) {
            return null;
        }

        PageRequest pageRequest = new PageRequest(page, size, new Sort(dir,
                sortName));

        return repository
                .findByTitleContainingIgnoreCaseAndIsOnSaleTrueOrSubTitleContainingAndIsOnSaleTrueOrParamValueCollectContainingAndIsOnSaleTrueOrCodeContainingAndIsOnSaleTrue(
                        keywords, keywords, keywords, keywords, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
            long catId, double priceLow, double priceHigh, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "salePrice"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", priceLow, priceHigh, paramStr,
                        pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
            long catId, double priceLow, double priceHigh, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "salePrice"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", priceLow, priceHigh, paramStr,
                        pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeAsc(
            long catId, double priceLow, double priceHigh, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "onSaleTime"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", priceLow, priceHigh, paramStr,
                        pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeDesc(
            long catId, double priceLow, double priceHigh, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "onSaleTime"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", priceLow, priceHigh, paramStr,
                        pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
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
                .findByCategoryIdTreeContainingAndBrandIdAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, priceLow, priceHigh,
                        paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
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
                .findByCategoryIdTreeContainingAndBrandIdAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, priceLow, priceHigh,
                        paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
            long catId, long brandId, double priceLow, double priceHigh,
            int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "salePrice"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, priceLow, priceHigh,
                        paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
            long catId, long brandId, double priceLow, double priceHigh,
            int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "salePrice"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, priceLow, priceHigh,
                        paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeAsc(
            long catId, long brandId, double priceLow, double priceHigh,
            int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "onSaleTime"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, priceLow, priceHigh,
                        paramStr, pageRequest);
    }

    public Page<TdGoods> findByCategoryIdAndBrandIdAndSalePriceBetweenAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeDesc(
            long catId, long brandId, double priceLow, double priceHigh,
            int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "onSaleTime"));

        String paramStr = "%";

        for (int i = 0; i < paramValueList.size(); i++) {
            String value = paramValueList.get(i);
            if (!"".equals(value)) {
                paramStr += value;
                paramStr += "%";
            }
        }

        return repository
                .findByCategoryIdTreeContainingAndBrandIdAndSalePriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(
                        "[" + catId + "]", brandId, priceLow, priceHigh,
                        paramStr, pageRequest);
    }

    // 无价格区间
    public Page<TdGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
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

    public Page<TdGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
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

    public Page<TdGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
            long catId, int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "salePrice"));

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

    public Page<TdGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
            long catId, int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "salePrice"));

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

    public Page<TdGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeAsc(
            long catId, int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "onSaleTime"));

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

    public Page<TdGoods> findByCategoryIdAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeDesc(
            long catId, int page, int size, List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "onSaleTime"));

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

    public Page<TdGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberAsc(
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

    public Page<TdGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderBySoldNumberDesc(
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

    public Page<TdGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderBySalePriceAsc(
            long catId, long brandId, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "salePrice"));

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

    public Page<TdGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderBySalePriceDesc(
            long catId, long brandId, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "salePrice"));

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

    public Page<TdGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeAsc(
            long catId, long brandId, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.ASC, "onSaleTime"));

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

    public Page<TdGoods> findByCategoryIdAndBrandIdAndParamsLikeAndIsOnSaleTrueOrderByOnSaleTimeDesc(
            long catId, long brandId, int page, int size,
            List<String> paramValueList) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(
                Direction.DESC, "onSaleTime"));

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

    public List<TdGoods> findByProductIdAndIsOnSaleTrue(Long productId) {
        if (null == productId) {
            return null;
        }

        return repository.findByProductIdAndIsOnSaleTrue(productId);
    }

    /**
     * 删除
     * 
     * @param id
     *            菜单项ID
     */
    public void delete(Long id) {
        if (null != id) {
            repository.delete(id);
            
            tdDistributorGoodsService.delete(tdDistributorGoodsService.findByGoodsId(id));
            tdProviderGoodsService.delete(tdProviderGoodsService.findByGoodsId(id));
        }
    }

    /**
     * 删除
     * 
     * @param e
     *            菜单项
     */
    public void delete(TdGoods e) {
        if (null != e) {
            repository.delete(e);
        }
    }

    /**
     * 查找
     * 
     * @param id
     *            ID
     * @return
     */
    public TdGoods findOne(Long id) {
        if (null == id) {
            return null;
        }

        return repository.findOne(id);
    }

    /**
     * 判断该商品是否正在进行秒杀
     * 
     * @param tdGoods
     * @return
     */
    public boolean isFlashSaleTrue(TdGoods tdGoods) {
        if (null == tdGoods) {
            return false;
        }

        Date curr = new Date();

        if (null != tdGoods.getIsFlashSale() && tdGoods.getIsFlashSale()
                && null != tdGoods.getFlashSaleStartTime()
                && tdGoods.getFlashSaleStartTime().before(curr)
                && null != tdGoods.getFlashSaleStopTime()
                && tdGoods.getFlashSaleStopTime().after(curr)
                && null != tdGoods.getFlashSaleLeftNumber()
                && tdGoods.getFlashSaleLeftNumber().compareTo(0L) > 0) {
            return true;
        }

        return false;
    }

    /**
     * 判断该商品是否正在进行十人团
     * 
     * @param tdGoods
     * @return
     */
    public boolean isGroupSaleTrue(TdGoods tdGoods) {
        if (null == tdGoods) {
            return false;
        }

        Date curr = new Date();

        if (null != tdGoods.getIsGroupSale() && tdGoods.getIsGroupSale()
                && null != tdGoods.getGroupSaleStartTime()
                && tdGoods.getGroupSaleStartTime().before(curr)
                && null != tdGoods.getGroupSaleStopTime()
                && tdGoods.getGroupSaleStopTime().after(curr)
                && null != tdGoods.getGroupSaleLeftNumber()
                && tdGoods.getGroupSaleLeftNumber().compareTo(0L) > 0) {
            return true;
        }

        return false;
    }

    /**
     * 判断该商品是否正在进行百人团
     * 
     * @param tdGoods
     *            商品
     * @return
     */
    public boolean isGroupSaleHundredTrue(TdGoods tdGoods) {
        if (null == tdGoods) {
            return false;
        }

        Date curr = new Date();

        if (null != tdGoods.getIsGroupSaleHundred()
                && tdGoods.getIsGroupSaleHundred()
                && null != tdGoods.getGroupSaleHundredStartTime()
                && tdGoods.getGroupSaleHundredStartTime().before(curr)
                && null != tdGoods.getGroupSaleHundredStopTime()
                && tdGoods.getGroupSaleHundredStopTime().after(curr)
                && null != tdGoods.getGroupSaleHundredLeftNumber()
                && tdGoods.getGroupSaleHundredLeftNumber().compareTo(0L) > 0) {
            return true;
        }

        return false;
    }

    public TdGoods save(TdGoods e)
    {
        if (null == e)
        {
            return null;
        }
        
        return repository.save(e);
    }
    /**
     * 保存类型
     * 
     * @param e
     * @return
     */
    public TdGoods save(TdGoods e, String manager) {
        if (null == e) {
            return null;
        }

        // 参数类型ID
        Long paramCategoryId = null;
        
        // 保存分类名称
        if (null != e.getCategoryId()) {
            TdProductCategory cat = tdProductCategoryService.findOne(e
                    .getCategoryId());
            e.setCategoryTitle(cat.getTitle());
            e.setCategoryIdTree(cat.getParentTree());

            Long pcId = cat.getParamCategoryId();
            TdParameterCategory category = tdParameterCategoryService.findOne(pcId);
            
            if (null != pcId) {
            	if(null != category.getParentId() && cat.getLayerCount()==3)
            	{
            		paramCategoryId = category.getParentId();
            	}else{
            		paramCategoryId = cat.getParamCategoryId();
            	}
            }
        }

        // 保存品牌名称
        if (null != e.getCategoryId()) {
            TdBrand brand = tdBrandService.findOne(e.getBrandId());

            if (null != brand) {
                e.setBrandTitle(brand.getTitle());
            }
        }

        // 创建时间
        if (null == e.getCreateTime()) {
            e.setCreateTime(new Date());
        }

        // 上架时间
        if (null == e.getOnSaleTime()) {
            e.setOnSaleTime(new Date());
        }



        // 当修改时，若切换过类型，参数数量由多变少，需要删除多余的参数
        if (null != e.getId() && null != paramCategoryId) {
            int count = tdParameterService
                    .countByCategoryTreeContaining(paramCategoryId);
            int size = e.getParamList().size();

            if (size > count) {
                List<TdGoodsParameter> subList = e.getParamList().subList(
                        count, size);
                tdGoodsParameterService.delete(subList);
                e.getParamList().removeAll(subList);
            }
        }


        if (null != e.getParamList() && e.getParamList().size() > 0) {
        	
        	TdProductCategory tpc = tdProductCategoryService.findOne(e.getCategoryId()); //
        	
            String valueCollect = "";
            for (TdGoodsParameter gp : e.getParamList()) {
                valueCollect += gp.getValue();
                valueCollect += ",";
                
                if (null != tpc && null != tpc.getParamCategoryId()) {// --手动输入参数的自动添加到参数内容记录
                	// 分类关联参数内容
            		List<TdParameter> paramList = tdParameterService.findByCategoryTreeContaining(tpc.getParamCategoryId());
            		if(null != paramList && paramList.size()>0)
            		{
            			for (TdParameter tdParameter : paramList) {
            				// 手动输入参数 
            				if(null != tdParameter.getInputType() && tdParameter.getInputType()==0 && gp.getParamName().equals(tdParameter.getTitle()))
            				{
            					if(null == tdParameter.getValueList())
            					{
            						tdParameter.setValueList(gp.getValue());
            					}
            					else if(!tdParameter.getValueList().contains(gp.getValue()))
            					{
            						tdParameter.setValueList(tdParameter.getValueList()+","+gp.getValue());
            					}
            					tdParameterService.save(tdParameter);
            				}
            				
            			}
            		}
            	} // --
            }
            e.setParamValueCollect(valueCollect);

            // 保存参数
            tdGoodsParameterService.save(e.getParamList());

        } else {
            e.setParamValueCollect("");
        }

        if (null != e.getId()) {
        	List<TdDistributorGoods> disGoodslist = tdDistributorGoodsService.findByGoodsId(e.getId());
        	if(null != disGoodslist && disGoodslist.size() >0 )
        	{
        		for (TdDistributorGoods tdDistributorGoods : disGoodslist) {
					tdDistributorGoods.setCoverImageUri(e.getCoverImageUri());
				}
        	}
        	// 同步更新超市商品封面
        	tdDistributorGoodsService.save(disGoodslist);
        	
        	List<TdProviderGoods> proGoodslist = tdProviderGoodsService.findByGoodsId(e.getId());
        	if(null != proGoodslist && proGoodslist.size()> 0){
        		for (TdProviderGoods tdProviderGoods : proGoodslist) {
					tdProviderGoods.setGoodsCoverImageUri(e.getCoverImageUri());
				}
        	}
        	// 同步更新批发、分销商品封面图片
        	tdProviderGoodsService.save(proGoodslist);
        }

        e = repository.save(e);


        return e;
    }

    /**
     * 计算实时秒杀价
     * 
     * @param goods
     * @return
     */
    public Double getFlashPrice(TdGoods goods) {
        if (null == goods) {
            return null;
        }

        Double flashPrice = null;
        Date curr = new Date();

        if (null != goods.getIsFlashSale()
                && null != goods.getFlashSaleStartTime()
                && null != goods.getFlashSaleStopTime()
                && null != goods.getFlashSalePrice() && goods.getIsFlashSale()
                && goods.getFlashSaleStopTime().after(curr)
                && goods.getFlashSaleStartTime().before(curr)) {
            // 剩余毫秒数
            long ts = goods.getFlashSaleStopTime().getTime() - curr.getTime();
            // 总共毫秒数
            long allts = goods.getFlashSaleStopTime().getTime()
                    - goods.getFlashSaleStartTime().getTime();

            flashPrice = goods.getFlashSalePrice() * ts / allts;

            BigDecimal b = new BigDecimal(flashPrice);

            flashPrice = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        return flashPrice;
    }
    
    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    
    
}
