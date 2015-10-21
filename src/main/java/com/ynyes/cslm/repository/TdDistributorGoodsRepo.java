package com.ynyes.cslm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdDistributorGoods;

/**
 * TdDistGoods  实体数据库操作接口
 * 
 * @author libiao
 *
 */
public interface TdDistributorGoodsRepo extends 
		PagingAndSortingRepository<TdDistributorGoods, Long>,
		JpaSpecificationExecutor<TdDistributorGoods>
{
//	Page<TdDistGoods> findByDistId(Pageable page);
	
		List<TdDistributorGoods> findByGoodsIdAndIsOnSaleTrue(Long goodsId);
	
		@Query("select g from TdDistributor d join d.goodsList g where d.username=?1 and g.isOnSale=?2")
		Page<TdDistributorGoods> findByUsernameAndIsOnSale(String username,Boolean onsale,Pageable page);
	
		@Query(value = "select * from td_distributor_goods where distributor_id = ?1 and goods_id=?2 and is_on_sale = ?3", nativeQuery = true)
		TdDistributorGoods findByDistributorIdAndGoodsIdAndIsOnSale(long disId,long goodsid, Boolean onsale);
		
		@Query(value = "select * from td_distributor_goods where distributor_id = ?1 and goods_id=?2", nativeQuery = true)
		TdDistributorGoods findByDistributorIdAndGoodsId(long disId,long goodsid);
		
		@Query(value="select distributor_id from td_distributor_goods where id = ?1",nativeQuery=true)
		Long findDistributorId(Long id);
		
		Page<TdDistributorGoods> findByIsOnSaleTrueOrderBySoldNumberDesc(Pageable page);
		@Query(value = "select g from TdDistributor d join d.goodsList g where d.id=?1 order by g.soldNumber desc")
		Page<TdDistributorGoods> findByDistributorIdAndIsOnSaleTrueOrderBySoldNumberDesc(long disId,Pageable page);
		
		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueOrderBySoldNumberDesc(String catstr,Pageable page);
		@Query(value = "select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.isOnSale=true order by g.soldNumber")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndIsOnSaleTrueOrderBySoldNumberDesc(long disId,String catstr,Pageable page);
		
		// search 
		Page<TdDistributorGoods> findByGoodsTitleContainingAndIsOnSaleOrSubGoodsTitleContainingAndIsOnSaleOrCodeContainingAndIsOnSaleOrDistributorTitleContainingAndIsOnSale(
																								String keywords1,Boolean isOnSale1,
																								String keywords2,Boolean isOnSale2,
																								String keywords3,Boolean isOnSale3,
																								String keywords4,Boolean isOnSale4,Pageable page);
		
		/**
		 * 	列表页收索排序
		 * 
		 */
		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(String categoryId, Double priceLow, Double priceHigh, String paramStr, Pageable page);
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.goodsPrice between ?3 and ?4  and g.paramValueCollect like ?5% and g.isOnSale=true order by g.soldNumber")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrueOrderBySoldNumber(Long distributorId,String categoryId, Double priceLow, Double priceHigh, String paramStr, Pageable page);
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.goodsPrice between ?3 and ?4  and g.paramValueCollect like ?5% and g.isOnSale=true order by g.goodsPrice")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrueOrderByGoodsPrice(Long distributorId,String categoryId, Double priceLow, Double priceHigh, String paramStr, Pageable page);
		
		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndBrandIdAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrue(String categoryId, Long brandId, Double priceLow, Double priceHigh, String paramStr, Pageable page);
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like %?2 and g.brandId=?3 and g.goodsPrice between ?4 and ?5  and g.paramValueCollect like ?6% and g.isOnSale=true order by g.soldNumber")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndBrandIdAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrueOrderBySoldNumber(Long distributorId,String categoryId, Long brandId, Double priceLow, Double priceHigh, String paramStr, Pageable page);
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like %?2 and g.brandId=?3 and g.goodsPrice between ?4 and ?5  and g.paramValueCollect like ?6% and g.isOnSale=true order by g.goodsPrice")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndBrandIdAndGoodsPriceBetweenAndParamValueCollectLikeAndIsOnSaleTrueOrderByGoodsPrice(Long distributorId,String categoryId, Long brandId, Double priceLow, Double priceHigh, String paramStr, Pageable page);

		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndParamValueCollectLikeAndIsOnSaleTrue(String categoryId, String paramStr, Pageable page);
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.paramValueCollect like ?3% and g.isOnSale =true order by g.soldNumber" )
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndParamValueCollectLikeAndIsOnSaleTrueOrderBySoldNumber(Long distributorId,String categoryId, String paramStr, Pageable page);
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.paramValueCollect like ?3% and g.isOnSale =true order by g.goodsPrice" )
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndParamValueCollectLikeAndIsOnSaleTrueOrderByGoodsPrice(Long distributorId,String categoryId, String paramStr, Pageable page);

		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(String categoryId, Long brandId, String paramStr, Pageable page);
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.brandId=?3 and g.paramValueCollect like ?4% and g.isOnSale =true order by g.soldNumber")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrueOrderBySoldNumber(Long distributorId,String categoryId, Long brandId, String paramStr, Pageable page);
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.brandId=?3 and g.paramValueCollect like ?4% and g.isOnSale =true order by g.goodsPrice")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrueOrderByGoodsPrice(Long distributorId,String categoryId, Long brandId, String paramStr, Pageable page);

		//
//		Page<TdDistributorGoods> findByIsOnSaleAndIsDistributionAndIsAudit(Boolean isOnSale,Boolean isDistribution,Boolean isAudit,Pageable page);
		
//		Page<TdDistributorGoods> findByGoodsTitleContainingAndIsOnSaleAndIsDistributionAndIsAuditOrSubGoodsTitleContainingAndIsOnSaleAndIsDistributionAndIsAuditOrCodeContainingAndIsOnSaleAndIsDistributionAndIsAudit(
//																									String keywords1,Boolean isOnSale1,
//																									Boolean isDistribution1,Boolean isAudit1,
//																									String keywords2,Boolean isOnSale2,
//																									Boolean isDistribution2,Boolean isAudit2,
//																									String keywords3,Boolean isOnSale3,
//																									Boolean isDistribution3,Boolean isAudit3,Pageable page);
		
//		Page<TdDistributorGoods> findByIsOnSaleAndIsDistribution(Boolean isOnSale,Boolean isDistribution,Pageable page);
		
//		Page<TdDistributorGoods> findByGoodsTitleContainingAndIsOnSaleAndIsDistributionOrSubGoodsTitleContainingAndIsOnSaleAndIsDistributionOrCodeContainingAndIsOnSaleAndIsDistribution(
//																									String keywords1,Boolean isOnSale1,
//																									Boolean isDistribution1,
//																									String keywords2,Boolean isOnSale2,
//																									Boolean isDistribution2,
//																									String keywords3,Boolean isOnSale3,
//																									Boolean isDistribution3,Pageable page);
		
		//
		Page<TdDistributorGoods> findByIsOnSaleAndIsAudit(Boolean isOnSale,Boolean isAudit,Pageable page);
		
		Page<TdDistributorGoods> findByGoodsTitleContainingAndIsOnSaleAndIsAuditOrSubGoodsTitleContainingAndIsOnSaleAndIsAuditOrCodeContainingAndIsOnSaleAndIsAudit(
																									String keywords1,Boolean isOnSale1,
																									Boolean isAudit1,
																									String keywords2,Boolean isOnSale2,
																									Boolean isAudit2,
																									String keywords3,Boolean isOnSale3,
																									Boolean isAudit3,Pageable page);
		
		Page<TdDistributorGoods> findByIsOnSale(Boolean isOnSale,Pageable page);
		
		Page<TdDistributorGoods> findByGoodsTitleContainingAndIsOnSaleOrSubGoodsTitleContainingAndIsOnSaleOrCodeContainingAndIsOnSale(
																									String keywords1,Boolean isOnSale1,
																									String keywords2,Boolean isOnSale2,
																									String keywords3,Boolean isOnSale3,Pageable page);
		
		//
//		Page<TdDistributorGoods> findByIsDistributionAndIsAudit(Boolean isDistribution,Boolean isAudit,Pageable page);
		
//		Page<TdDistributorGoods> findByGoodsTitleContainingAndIsDistributionAndIsAuditOrSubGoodsTitleContainingAndIsDistributionAndIsAuditOrCodeContainingAndIsDistributionAndIsAudit(
//																									String keywords1,
//																									Boolean isDistribution1,Boolean isAudit1,
//																									String keywords2,
//																									Boolean isDistribution2,Boolean isAudit2,
//																									String keywords3,
//																									Boolean isDistribution3,Boolean isAudit3,Pageable page);
		
//		Page<TdDistributorGoods> findByIsDistribution(Boolean isDistribution,Pageable page);
		
//		Page<TdDistributorGoods> findByGoodsTitleContainingAndIsDistributionOrSubGoodsTitleContainingAndIsDistributionOrCodeContainingAndIsDistribution(
//																									String keywords1,
//																									Boolean isDistribution1,
//																									String keywords2,
//																									Boolean isDistribution2,
//																									String keywords3,
//																									Boolean isDistribution3,Pageable page);
		
		//
		Page<TdDistributorGoods> findByIsAudit(Boolean isAudit,Pageable page);
		
		Page<TdDistributorGoods> findByGoodsTitleContainingAndIsAuditOrSubGoodsTitleContainingAndIsAuditOrCodeContainingAndIsAudit(
																									String keywords1,
																									Boolean isAudit1,
																									String keywords2,
																									Boolean isAudit2,
																									String keywords3,
																									Boolean isAudit3,Pageable page);
		
		Page<TdDistributorGoods> findByGoodsTitleContainingOrSubGoodsTitleContainingOrCodeContaining(String keywords1,
																									String keywords2,
																									String keywords3,Pageable page);
		
		// --
		// --
//		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndIsOnSaleAndIsDistributionAndIsAudit(String catId,Boolean isOnSale,Boolean isDistribution,Boolean isAudit,Pageable page);
		
//		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndGoodsTitleContainingAndIsOnSaleAndIsDistributionAndIsAuditOrCategoryIdTreeContainingAndSubGoodsTitleContainingAndIsOnSaleAndIsDistributionAndIsAuditOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleAndIsDistributionAndIsAudit(
//																									String catId1,String keywords1,Boolean isOnSale1,
//																									Boolean isDistribution1,Boolean isAudit1,
//																									String catId2,String keywords2,Boolean isOnSale2,
//																									Boolean isDistribution2,Boolean isAudit2,
//																									String catId3,String keywords3,Boolean isOnSale3,
//																									Boolean isDistribution3,Boolean isAudit3,Pageable page);
		
//		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndIsOnSaleAndIsDistribution(String catId,Boolean isOnSale,Boolean isDistribution,Pageable page);
		
//		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndGoodsTitleContainingAndIsOnSaleAndIsDistributionOrCategoryIdTreeContainingAndSubGoodsTitleContainingAndIsOnSaleAndIsDistributionOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleAndIsDistribution(
//																									String catId1,String keywords1,Boolean isOnSale1,
//																									Boolean isDistribution1,
//																									String catId2,String keywords2,Boolean isOnSale2,
//																									Boolean isDistribution2,
//																									String catId3,String keywords3,Boolean isOnSale3,
//																									Boolean isDistribution3,Pageable page);
		
		//
		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndIsOnSaleAndIsAudit(String catId,Boolean isOnSale,Boolean isAudit,Pageable page);
		
		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndGoodsTitleContainingAndIsOnSaleAndIsAuditOrCategoryIdTreeContainingAndSubGoodsTitleContainingAndIsOnSaleAndIsAuditOrCategoryIdTreeContainingAndCodeContainingAndIsOnSaleAndIsAudit(
																									String catId1,String keywords1,Boolean isOnSale1,
																									Boolean isAudit1,
																									String catId2,String keywords2,Boolean isOnSale2,
																									Boolean isAudit2,
																									String catId3,String keywords3,Boolean isOnSale3,
																									Boolean isAudit3,Pageable page);
		
		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndIsOnSale(String catId,Boolean isOnSale,Pageable page);
		
		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndGoodsTitleContainingAndIsOnSaleOrCategoryIdTreeContainingAndSubGoodsTitleContainingAndIsOnSaleOrCategoryIdTreeContainingAndCodeContainingAndIsOnSale(
																									String catId1,String keywords1,Boolean isOnSale1,
																									String catId2,String keywords2,Boolean isOnSale2,
																									String catId3,String keywords3,Boolean isOnSale3,Pageable page);
		
		//
//		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndIsDistributionAndIsAudit(String catId,Boolean isDistribution,Boolean isAudit,Pageable page);
		
//		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndGoodsTitleContainingAndIsDistributionAndIsAuditOrCategoryIdTreeContainingAndSubGoodsTitleContainingAndIsDistributionAndIsAuditOrCategoryIdTreeContainingAndCodeContainingAndIsDistributionAndIsAudit(
//																									String catId1,String keywords1,
//																									Boolean isDistribution1,Boolean isAudit1,
//																									String catId2,String keywords2,
//																									Boolean isDistribution2,Boolean isAudit2,
//																									String catId3,String keywords3,
//																									Boolean isDistribution3,Boolean isAudit3,Pageable page);
		
//		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndIsDistribution(String catId,Boolean isDistribution,Pageable page);
		
//		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndGoodsTitleContainingAndIsDistributionOrCategoryIdTreeContainingAndSubGoodsTitleContainingAndIsDistributionOrCategoryIdTreeContainingAndCodeContainingAndIsDistribution(
//																									String catId1,String keywords1,
//																									Boolean isDistribution1,
//																									String catId2,String keywords2,
//																									Boolean isDistribution2,
//																									String catId3,String keywords3,
//																									Boolean isDistribution3,Pageable page);
		
		//
		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndIsAudit(String catId,Boolean isAudit,Pageable page);
		
		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndGoodsTitleContainingAndIsAuditOrCategoryIdTreeContainingAndSubGoodsTitleContainingAndIsAuditOrCategoryIdTreeContainingAndCodeContainingAndIsAudit(
																									String catId1,String keywords1,
																									Boolean isAudit1,
																									String catId2,String keywords2,
																									Boolean isAudit2,
																									String catId3,String keywords3,
																									Boolean isAudit3,Pageable page);
		
		Page<TdDistributorGoods> findByCategoryIdTreeContaining(String catId,Pageable page);
		
		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndGoodsTitleContainingOrCategoryIdTreeContainingAndSubGoodsTitleContainingOrCategoryIdTreeContainingAndCodeContaining(
																									String catId1,String keywords1,
																									String catId2,String keywords2,
																									String catId3,String keywords3,Pageable page);
		
		// -------------   超市→未选分类 ...... ------------------------------
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.isOnSale=?2 and g.isAudit=?3")
		Page<TdDistributorGoods> findByDistributorIdAndIsOnSaleAndIsAudit(Long distributorId,Boolean isOnSale,Boolean isAudit,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.goodsTitle like ?2% and g.isOnSale=?3 and g.isAudit=?4 or "
																	+ "d.id=?5 and g.subGoodsTitle like ?6% and g.isOnSale=?7 and g.isAudit=?8 or "
																	+ "d.id=?9 and g.code like ?10% and g.isOnSale=?11 and g.isAudit=?12 ")
		Page<TdDistributorGoods> findByDistributorIdAndGoodsTitleLikeAndIsOnSaleAndIsAuditOrDistributorIdAndSubGoodsTitleLikeAndIsOnSaleAndIsAuditOrDistributorIdAndCodeLikeAndIsOnSaleAndIsAudit(
																									Long disId1,String keywords1,
																									Boolean isOnSale1,Boolean isAudit1,
																									Long disId2,String keywords2,
																									Boolean isOnSale2,Boolean isAudit2,
																									Long disId3,String keywords3,
																									Boolean isOnSale3,Boolean isAudit3,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.isOnSale=?2 ")
		Page<TdDistributorGoods> findByDistributorIdAndIsOnSale(Long distributorId,Boolean isOnSale,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.goodsTitle like ?2 and g.isOnSale=?3 or "
																	+ "d.id=?4 and g.subGoodsTitle like ?5 and g.isOnSale=?6 or "
																	+ "d.id=?7 and g.code like ?8 and g.isOnSale=?9  ")
		Page<TdDistributorGoods> findByDistributorIdAndGoodsTitleLikeAndIsOnSaleOrDistributorIdAndSubGoodsTitleLikeAndIsOnSaleOrDistributorIdAndCodeLikeAndIsOnSale(
																									Long disId1,String keywords1,
																									Boolean isOnSale1,
																									Long disId2,String keywords2,
																									Boolean isOnSale2,
																									Long disId3,String keywords3,
																									Boolean isOnSale3,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.isAudit=?2")
		Page<TdDistributorGoods> findByDistributorIdAndIsAudit(Long distributorId,Boolean isAudit,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.goodsTitle like ?2%  and g.isAudit=?3 or "
																	+ "d.id=?4 and g.subGoodsTitle like ?5%  and g.isAudit=?6 or "
																	+ "d.id=?7 and g.code like ?8%  and g.isAudit=?9 ")
		Page<TdDistributorGoods> findByDistributorIdAndGoodsTitleLikeAndIsAuditOrDistributorIdAndSubGoodsTitleLikeAndIsAuditOrDistributorIdAndCodeLikeAndIsAudit(
																									Long disId1,String keywords1,
																									Boolean isAudit1,
																									Long disId2,String keywords2,
																									Boolean isAudit2,
																									Long disId3,String keywords3,
																									Boolean isAudit3,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 ")
		Page<TdDistributorGoods> findByDistributorId(Long distributorId,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.goodsTitle like ?2%  or "
																	+ "d.id=?3 and g.subGoodsTitle like ?4%  or "
																	+ "d.id=?5 and g.code like ?6%   ")
		Page<TdDistributorGoods> findByDistributorIdAndGoodsTitleLikeOrDistributorIdAndSubGoodsTitleLikeOrDistributorIdAndCodeLike(
																									Long disId1,String keywords1,
																									Long disId2,String keywords2,
																									Long disId3,String keywords3,Pageable page);
		
		// =========== 超市→分类 .....  =======================================
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.isOnSale=?3 and g.isAudit=?4")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndIsOnSaleAndIsAudit(Long distributorId,String catId,Boolean isOnSale,Boolean isAudit,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.goodsTitle like ?3% and g.isOnSale=?4 and g.isAudit=?5 or "
																	+ "d.id=?6 and g.categoryIdTree like ?7% and g.subGoodsTitle like ?8% and g.isOnSale=?9 and g.isAudit=?10 or "
																	+ "d.id=?11 and g.categoryIdTree like ?12% and g.code like ?13% and g.isOnSale=?14 and g.isAudit=?15 ")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsOnSaleAndIsAuditOrDistributorIdAndCategoryIdTreeLikeAndSubGoodsTitleLikeAndIsOnSaleAndIsAuditOrDistributorIdAndCategoryIdTreeLikeAndCodeLikeAndIsOnSaleAndIsAudit(
																									Long disId1,String catId1,String keywords1,
																									Boolean isOnSale1,Boolean isAudit1,
																									Long disId2,String catId2,String keywords2,
																									Boolean isOnSale2,Boolean isAudit2,
																									Long disId3,String catId3,String keywords3,
																									Boolean isOnSale3,Boolean isAudit3,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.isOnSale=?3 ")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndIsOnSale(Long distributorId,String catId,Boolean isOnSale,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.goodsTitle like ?3% and g.isOnSale=?4 or "
																	+ "d.id=?5 and g.categoryIdTree like ?6% and g.subGoodsTitle like ?7% and g.isOnSale=?8 or "
																	+ "d.id=?9 and g.categoryIdTree like ?10% and g.code like ?11% and g.isOnSale=?12  ")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsOnSaleOrDistributorIdAndCategoryIdTreeLikeAndSubGoodsTitleLikeAndIsOnSaleOrDistributorIdAndCategoryIdTreeLikeAndCodeLikeAndIsOnSale(
																									Long disId1,String catId1,
																									String keywords1,Boolean isOnSale1,
																									Long disId2,String catId2,
																									String keywords2,Boolean isOnSale2,
																									Long disId3,String catId3,
																									String keywords3,Boolean isOnSale3,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.isAudit=?3")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndIsAudit(Long distributorId,String catId,Boolean isAudit,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.goodsTitle like ?3%  and g.isAudit=?4 or "
																	+ "d.id=?5 and g.categoryIdTree like ?6% and g.subGoodsTitle like ?7%  and g.isAudit=?8 or "
																	+ "d.id=?9 and g.categoryIdTree like ?10% and g.code like ?11%  and g.isAudit=?12 ")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsAuditOrDistributorIdAndCategoryIdTreeLikeAndSubGoodsTitleLikeAndIsAuditOrDistributorIdAndCategoryIdTreeLikeAndCodeLikeAndIsAudit(
																									Long disId1,String catId1,
																									String keywords1,Boolean isAudit1,
																									Long disId2,String catId2,
																									String keywords2,Boolean isAudit2,
																									Long disId3,String catId3,
																									String keywords3,Boolean isAudit3,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2%")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLike(Long distributorId,String catId,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.goodsTitle like ?3%  or "
																	+ "d.id=?4 and g.categoryIdTree like ?5% and g.subGoodsTitle like ?6%  or "
																	+ "d.id=?7 and g.categoryIdTree like ?8% and g.code like ?8%   ")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndGoodsTitleLikeOrDistributorIdAndCategoryIdTreeLikeAndSubGoodsTitleLikeOrDistributorIdAndCategoryIdTreeLikeAndCodeLike(
																									Long disId1,String catId1,String keywords1,
																									Long disId2,String catId2,String keywords2,
																									Long disId3,String catId3,String keywords3,Pageable page);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}
