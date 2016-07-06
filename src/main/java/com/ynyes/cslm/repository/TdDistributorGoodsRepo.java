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
	
		List<TdDistributorGoods> findByIsOnSaleTrueAndLeftNumberLessThan(Long leftNumber);
		
		@Query(value="select category_id from td_distributor_goods where distributor_id=?1 group by category_id",nativeQuery=true)
		List<Long> findByDistributorIdAndGroupCategoryId(Long distributorId);
		
		@Query(value="select category_id from td_distributor_goods where distributor_id=?1 and is_audit=1 group by category_id",nativeQuery=true)
		List<Long> findByDistributorIdAndIsAuditAndGroupCategoryId(Long distributorId);
	
		List<TdDistributorGoods> findByGoodsIdAndIsOnSaleTrue(Long goodsId);
		
		List<TdDistributorGoods> findByGoodsId(Long goodsId);
	
		@Query("select g from TdDistributor d join d.goodsList g where d.username=?1 and g.isOnSale=?2")
		Page<TdDistributorGoods> findByUsernameAndIsOnSale(String username,Boolean onsale,Pageable page);
	
		@Query(value = "select * from td_distributor_goods where distributor_id = ?1 and goods_id=?2 and is_on_sale = ?3", nativeQuery = true)
		TdDistributorGoods findByDistributorIdAndGoodsIdAndIsOnSale(long disId,long goodsid, Boolean onsale);
		
//		@Query(value = "select * from td_distributor_goods where distributor_id = ?1 and goods_id=?2 and is_audit=?3", nativeQuery = true)
//		TdDistributorGoods findByDistributorIdAndGoodsIdAndIsAudit(long disId,long goodsid,Boolean isAudit);
		
		@Query(value = "select * from td_distributor_goods where distributor_id = ?1 and goods_id=?2", nativeQuery = true)
		TdDistributorGoods findByDistributorIdAndGoodsId(long disId,long goodsid);
		
		@Query(value="select distributor_id from td_distributor_goods where id = ?1",nativeQuery=true)
		Long findDistributorId(Long id);
		
		Page<TdDistributorGoods> findByIsOnSaleTrueOrderBySoldNumberDesc(Pageable page);
		Page<TdDistributorGoods> findByIsOnSaleTrueOrderByGoodsPriceDesc(Pageable page);
		
		@Query(value = "select g from TdDistributor d join d.goodsList g where d.id=?1 and g.isOnSale=true order by g.soldNumber desc")
		Page<TdDistributorGoods> findByDistributorIdAndIsOnSaleTrueOrderBySoldNumberDesc(long disId,Pageable page);
		
		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndIsOnSaleTrueOrderBySoldNumberDesc(String catstr,Pageable page);
		
		Page<TdDistributorGoods> findByDisIdAndCategoryIdTreeContainingAndIsOnSaleTrueOrderBySoldNumberDesc(long disId,String catstr,Pageable page);
		
		@Query(value = "select g from TdDistributor d join d.goodsList g where d.id=?1 and g.productId=?2 and g.isOnSale=1")
		List<TdDistributorGoods> findByDistributorIdAndProductIdAndIsOnSaleTrue(Long disId,Long productId);
		
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
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.brandId=?3 and g.paramValueCollect like ?4% and g.isOnSale =true order by g.soldNumber desc")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrueOrderBySoldNumber(Long distributorId,String categoryId, Long brandId, String paramStr, Pageable page);
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.brandId=?3 and g.paramValueCollect like ?4% and g.isOnSale =true order by g.goodsPrice desc")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrueOrderByGoodsPrice(Long distributorId,String categoryId, Long brandId, String paramStr, Pageable page);

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
		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndIsOnSaleAndIsRecommendTypeTrue(String catId,Boolean isOnSale,Pageable page);
		
		Page<TdDistributorGoods> findByCategoryIdTreeContainingAndGoodsTitleContainingAndIsOnSaleOrCategoryIdTreeContainingAndSubGoodsTitleContainingAndIsOnSaleOrCategoryIdTreeContainingAndCodeContainingAndIsOnSale(
																									String catId1,String keywords1,Boolean isOnSale1,
																									String catId2,String keywords2,Boolean isOnSale2,
																									String catId3,String keywords3,Boolean isOnSale3,Pageable page);
		
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
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.isOnSale=?2")
	    Page<TdDistributorGoods> findByIdAndIsOnSale(Long id, Boolean isOnSale, Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.isOnSale=?2 order by g.leftNumber desc")
	    Page<TdDistributorGoods> findByIdAndIsOnSaleOrderByLeftNumberDesc(Long id, Boolean isOnSale, Pageable page);
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.isOnSale=?2 order by g.leftNumber asc")
	    Page<TdDistributorGoods> findByIdAndIsOnSaleOrderByLeftNumberAsc(Long id, Boolean isOnSale, Pageable page);
		
		// -------------   超市→未选分类 ...... ------------------------------
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.isOnSale=?2 and g.isAudit=?3")
		Page<TdDistributorGoods> findByDistributorIdAndIsOnSaleAndIsAudit(Long distributorId,Boolean isOnSale,Boolean isAudit,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.goodsTitle like ?2% and g.isOnSale=?3 and g.isAudit=?4 or "
																	+ "d.id=?1 and g.subGoodsTitle like ?2% and g.isOnSale=?3 and g.isAudit=?4 or "
																	+ "d.id=?1 and g.code like ?2% and g.isOnSale=?3 and g.isAudit=?4 ")
		Page<TdDistributorGoods> findByDistributorIdAndGoodsTitleLikeAndIsOnSaleAndIsAuditOrDistributorIdAndSubGoodsTitleLikeAndIsOnSaleAndIsAuditOrDistributorIdAndCodeLikeAndIsOnSaleAndIsAudit(
																									Long disId1,String keywords1,
																									Boolean isOnSale1,Boolean isAudit1,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.isOnSale=?2 ")
		Page<TdDistributorGoods> findByDistributorIdAndIsOnSale(Long distributorId,Boolean isOnSale,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.goodsTitle like ?2 and g.isOnSale=?3 or "
																	+ "d.id=?1 and g.subGoodsTitle like ?2 and g.isOnSale=?3 or "
																	+ "d.id=?1 and g.code like ?2 and g.isOnSale=?3  ")
		Page<TdDistributorGoods> findByDistributorIdAndGoodsTitleLikeAndIsOnSaleOrDistributorIdAndSubGoodsTitleLikeAndIsOnSaleOrDistributorIdAndCodeLikeAndIsOnSale(
																									Long disId1,String keywords1,
																									Boolean isOnSale1,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.goodsTitle like ?2 and g.isOnSale=?3 or "
																	+ "d.id=?1 and g.subGoodsTitle like ?2 and g.isOnSale=?3 or "
																	+ "d.id=?1 and g.code like ?2 and g.isOnSale=?3 order by g.leftNumber desc ")
		Page<TdDistributorGoods> findByDistributorIdAndGoodsTitleLikeAndIsOnSaleOrDistributorIdAndSubGoodsTitleLikeAndIsOnSaleOrDistributorIdAndCodeLikeAndIsOnSaleOrderByLeftNumberDesc(
																	Long disId1,String keywords1,
																	Boolean isOnSale1,Pageable page);
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.goodsTitle like ?2 and g.isOnSale=?3 or "
																	+ "d.id=?1 and g.subGoodsTitle like ?2 and g.isOnSale=?3 or "
																	+ "d.id=?1 and g.code like ?2 and g.isOnSale=?3 order by g.leftNumber asc ")
		Page<TdDistributorGoods> findByDistributorIdAndGoodsTitleLikeAndIsOnSaleOrDistributorIdAndSubGoodsTitleLikeAndIsOnSaleOrDistributorIdAndCodeLikeAndIsOnSaleOrderByLeftNumberasc(
																			Long disId1,String keywords1,
																			Boolean isOnSale1,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.isAudit=?2")
		Page<TdDistributorGoods> findByDistributorIdAndIsAudit(Long distributorId,Boolean isAudit,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.goodsTitle like ?2%  and g.isAudit=?3 or "
																	+ "d.id=?1 and g.subGoodsTitle like ?2%  and g.isAudit=?3 or "
																	+ "d.id=?1 and g.code like ?2%  and g.isAudit=?3 ")
		Page<TdDistributorGoods> findByDistributorIdAndGoodsTitleLikeAndIsAuditOrDistributorIdAndSubGoodsTitleLikeAndIsAuditOrDistributorIdAndCodeLikeAndIsAudit(
																									Long disId1,String keywords1,
																									Boolean isAudit1,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 ")
		Page<TdDistributorGoods> findByDistributorId(Long distributorId,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.goodsTitle like ?2%  or "
																	+ "d.id=?1 and g.subGoodsTitle like ?2%  or "
																	+ "d.id=?1 and g.code like ?2%   ")
		Page<TdDistributorGoods> findByDistributorIdAndGoodsTitleLikeOrDistributorIdAndSubGoodsTitleLikeOrDistributorIdAndCodeLike(
																									Long disId1,String keywords1,Pageable page);
		
		// =========== 超市→分类 .....  =======================================
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.isOnSale=?3 and g.isAudit=?4")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndIsOnSaleAndIsAudit(Long distributorId,String catId,Boolean isOnSale,Boolean isAudit,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.goodsTitle like ?3% and g.isOnSale=?4 and g.isAudit=?5 or "
																	+ "d.id=?1 and g.categoryIdTree like ?2% and g.subGoodsTitle like ?3% and g.isOnSale=?4 and g.isAudit=?5 or "
																	+ "d.id=?1 and g.categoryIdTree like ?2% and g.code like ?3% and g.isOnSale=?4 and g.isAudit=?5 ")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsOnSaleAndIsAuditOrDistributorIdAndCategoryIdTreeLikeAndSubGoodsTitleLikeAndIsOnSaleAndIsAuditOrDistributorIdAndCategoryIdTreeLikeAndCodeLikeAndIsOnSaleAndIsAudit(
																									Long disId1,String catId1,String keywords1,
																									Boolean isOnSale1,Boolean isAudit1,Pageable page);
		
		Page<TdDistributorGoods> findByDisIdAndCategoryIdTreeLikeAndIsOnSale(Long distributorId,String catId,Boolean isOnSale,Pageable page);
		Page<TdDistributorGoods> findByDisIdAndCategoryIdTreeLikeAndIsOnSaleTrueAndIsRecommendCategoryTrue(Long distributorId,String catId,Pageable page);
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.isOnSale=?3 order by g.leftNumber desc ")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndIsOnSaleOrderByLeftNumberDesc(Long distributorId,String catId,Boolean isOnSale,Pageable page);
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.isOnSale=?3 order by g.leftNumber asc ")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndIsOnSaleOrderByLeftNumberAsc(Long distributorId,String catId,Boolean isOnSale,Pageable page);
		
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.goodsTitle like ?3% and g.isOnSale=?4 or "
																	+ "d.id=?1 and g.categoryIdTree like ?2% and g.subGoodsTitle like ?3% and g.isOnSale=?4 or "
																	+ "d.id=?1 and g.categoryIdTree like ?2% and g.code like ?3% and g.isOnSale=?4  ")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsOnSaleOrDistributorIdAndCategoryIdTreeLikeAndSubGoodsTitleLikeAndIsOnSaleOrDistributorIdAndCategoryIdTreeLikeAndCodeLikeAndIsOnSale(
																									Long disId1,String catId1,
																									String keywords1,Boolean isOnSale1,Pageable page);
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.goodsTitle like ?3% and g.isOnSale=?4 or "
																	+ "d.id=?1 and g.categoryIdTree like ?2% and g.subGoodsTitle like ?3% and g.isOnSale=?4 or "
																	+ "d.id=?1 and g.categoryIdTree like ?2% and g.code like ?3% and g.isOnSale=?4  order by g.leftNumber desc  ")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsOnSaleOrDistributorIdAndCategoryIdTreeLikeAndSubGoodsTitleLikeAndIsOnSaleOrDistributorIdAndCategoryIdTreeLikeAndCodeLikeAndIsOnSaleOrderByLeftNumberDesc(
																		Long disId1,String catId1,
																		String keywords1,Boolean isOnSale1,Pageable page);
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.goodsTitle like ?3% and g.isOnSale=?4 or "
																	+ "d.id=?1 and g.categoryIdTree like ?2% and g.subGoodsTitle like ?3% and g.isOnSale=?4 or "
																	+ "d.id=?1 and g.categoryIdTree like ?2% and g.code like ?3% and g.isOnSale=?4  order by g.leftNumber asc  ")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsOnSaleOrDistributorIdAndCategoryIdTreeLikeAndSubGoodsTitleLikeAndIsOnSaleOrDistributorIdAndCategoryIdTreeLikeAndCodeLikeAndIsOnSaleOrderByLeftNumberAsc(
																				Long disId1,String catId1,
																				String keywords1,Boolean isOnSale1,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.isAudit=?3")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndIsAudit(Long distributorId,String catId,Boolean isAudit,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.goodsTitle like ?3%  and g.isAudit=?4 or "
																	+ "d.id=?1 and g.categoryIdTree like ?2% and g.subGoodsTitle like ?3%  and g.isAudit=?4 or "
																	+ "d.id=?1 and g.categoryIdTree like ?2% and g.code like ?3%  and g.isAudit=?4 ")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsAuditOrDistributorIdAndCategoryIdTreeLikeAndSubGoodsTitleLikeAndIsAuditOrDistributorIdAndCategoryIdTreeLikeAndCodeLikeAndIsAudit(
																									Long disId1,String catId1,
																									String keywords1,Boolean isAudit1,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2%")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLike(Long distributorId,String catId,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.goodsTitle like ?3%  or "
																	+ "d.id=?1 and g.categoryIdTree like ?2% and g.subGoodsTitle like ?3%  or "
																	+ "d.id=?1 and g.categoryIdTree like ?2% and g.code like ?3%   ")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndGoodsTitleLikeOrDistributorIdAndCategoryIdTreeLikeAndSubGoodsTitleLikeOrDistributorIdAndCategoryIdTreeLikeAndCodeLike(
																									Long disId1,String catId1,String keywords1,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.isDistribution=?2 and g.isAudit=?3")
		Page<TdDistributorGoods> findByDistributorIdAndIsDistributionAndIsAudit(Long distributorId,Boolean isDistribution,Boolean isAudit,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.goodsTitle like ?2 and g.isDistribution =?3 and g.isAudit=?4 or "
																	+ "d.id=?1 and g.code like ?2 and g.isDistribution =?3 and g.isAudit=?4")
		Page<TdDistributorGoods> findByDistributorIdAndIsDistributionAndGoodsTitleLikeAndIsAuditOrDistributorIsAndIsDistributionAndCodeLikeAndIsAudit(
																									Long distributorId,	String keywords,
																									Boolean isDistribution,Boolean isAudit,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2% and g.isDistribution=?3 and g.isAudit=?4")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeAndIsDistributionAndIsAudit(Long distributorId,String catId,Boolean isDistribution,Boolean isAudit,Pageable page);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.categoryIdTree like ?2 and g.isDistribution =?3 and g.isAudit=?4 and g.goodsTitle like ?5 or "
																	+ "d.id=?1 and g.categoryIdTree like ?2 and g.isDistribution =?3 and g.isAudit=?4 and g.code like ?5")
		Page<TdDistributorGoods> findByDistributorIdAndCategoryIdTreeLikeAndIsDistributionAndIsAuditAndGoodsTitleLikeOrDistributorIsAndCategoryIdTreeLikeAndIsDistributionAndIsAuditAndCodeLike(
																				Long distributorId,String catId,
																				Boolean isDistribution,Boolean isAudit,String keywords,Pageable page);
		
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.isOnSale=1 and g.isRecommendIndex=1 order by g.onSaleTime desc")
		Page<TdDistributorGoods> findByDistributorIdAndIsOnSaleTrueAndIsRecommendIndexTrueOrderByOnSaleTime(Long distributorId,Pageable page);
		
		Page<TdDistributorGoods> findByIsOnSaleTrueAndIsRecommendIndexTrueOrderByOnSaleTimeDesc(Pageable page);
		Page<TdDistributorGoods> findByIsOnSaleTrueAndIsSetRecommendTrueOrderByOnSaleTimeDesc(Pageable page);
		Page<TdDistributorGoods> findByIsOnSaleTrueAndIsTouchHotTrueOrderByOnSaleTimeDesc(Pageable page);
		
		List<TdDistributorGoods> findByProviderIdAndGoodsIdAndIsDistributionTrue(Long proId,Long goodsId);
		
		@Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.isOnSale=true order by g.onSaleTime desc")
		Page<TdDistributorGoods> findByDistributorIdAndIsOnSaleTrueOrderByOnSaleTime(Long distributorId,Pageable page);
		
		
		
		Page<TdDistributorGoods> findByDisIdAndGoodsTitleLikeAndIsOnSaleOrDisIdAndSubGoodsTitleLikeAndIsOnSaleOrDisIdAndCodeLikeAndIsOnSale(
																										Long disId1,String keywords1,Boolean isOnSale1,
																										Long disId2,String keywords2,Boolean isOnSale2,
																										Long disId3,String keywords3,Boolean isOnSale3,Pageable page);
		
		Page<TdDistributorGoods> findByDisIdAndCategoryIdTreeContainingAndParamValueCollectLikeAndIsOnSaleTrue(Long disId,String categoryId, String paramStr, Pageable page);
		Page<TdDistributorGoods> findByDisIdAndCategoryIdTreeContainingAndBrandIdAndParamValueCollectLikeAndIsOnSaleTrue(Long disId,String categoryId, Long brandId, String paramStr, Pageable page);
		
		TdDistributorGoods findByIdAndIsOnSaleTrue(Long id);
}
