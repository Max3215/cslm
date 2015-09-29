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
	
		@Query(value = "select * from td_distributor_goods where distributor_id = ?1 ", nativeQuery = true)
		List<TdDistributorGoods> findTop12ByDistributorIdAndIsOnSaleTrueOrderBySoldNumberDesc(long disId);
		
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

		
		

		
}
