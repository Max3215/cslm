package com.ynyes.cslm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdProviderGoods;

/**
 * TdProviderGoods数据接口
 * @author libiao
 *
 */

public interface TdProviderGoodsRepo extends 
		PagingAndSortingRepository<TdProviderGoods, Long> ,
		JpaSpecificationExecutor<TdProviderGoods>
{
	@Query(value="select category_id from td_provider_goods where provider_id=?1 group by category_id",nativeQuery=true)
	List<Long> findByProviderIdAndGroupByCategoryId(Long providerId);
	
	@Query(value="select category_id from td_provider_goods where provider_id=?1 and is_audit=0 group by category_id",nativeQuery=true)
	List<Long> findByProviderIdAndIsAuditAndGroupByCategoryId(Long providerId);
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 order by pg.id DESC")
	Page<TdProviderGoods> findByProviderId(long providerId,Pageable page);
	
	@Query(value="select * from td_provider_goods where provider_id=?1 and goods_id=?2",nativeQuery =true)
	TdProviderGoods findByProviderIdAndGoodsId(long providerId,long goodsId);
	
	Page<TdProviderGoods> findAll(Pageable page);
	
	@Query(value="select pg.provider_id from td_provider_goods pg where pg.id=?1",nativeQuery=true)
	Long findById(Long id);
	
	Page<TdProviderGoods> findByGoodsTitleContainingOrCodeContainingOrProviderTitleContaining(String keywords1,String keywords2,String keywords3,Pageable page);
	
	Page<TdProviderGoods> findByIsDistributionTrueAndIsAuditTrue(Pageable page);
	
	Page<TdProviderGoods> findByGoodsTitleContainingAndIsDistributionTrueAndIsAuditTrueOrCodeContainingAndIsDistributionTrueAndIsAuditTrue(String keywords1,String keywords2,Pageable page);
	
	Page<TdProviderGoods> findByIsDistributionTrueAndIsAuditFalse(Pageable page);
	
	Page<TdProviderGoods> findByGoodsTitleContainingAndIsDistributionTrueAndIsAuditFalseOrCodeContainingAndIsDistributionTrueAndIsAuditFalse(String keywords1,String keywords2,Pageable page);

	Page<TdProviderGoods> findByIsDistributionTrue(Pageable page);
	
	Page<TdProviderGoods> findByGoodsTitleContainingAndIsDistributionTrueOrCodeContainingAndIsDistributionTrue(String keywords1,String keywords2,Pageable page);
	
	Page<TdProviderGoods> findByIsDistributionFalseAndIsAuditTrue(Pageable page);
	
	Page<TdProviderGoods> findByGoodsTitleContainingAndIsDistributionFalseAndIsAuditTrueOrCodeContainingAndIsDistributionFalseAndIsAuditTrue(String keywords1,String keywords2,Pageable page);
	
	Page<TdProviderGoods> findByIsDistributionFalseAndIsAuditFalse(Pageable page);
	
	Page<TdProviderGoods> findByGoodsTitleContainingAndIsDistributionFalseAndIsAuditFalseOrCodeContainingAndIsDistributionFalseAndIsAuditFalse(String keywords1,String keywords2,Pageable page);

	Page<TdProviderGoods> findByIsDistributionFalse(Pageable page);
	
	Page<TdProviderGoods> findByGoodsTitleContainingAndIsDistributionFalseOrCodeContainingAndIsDistributionFalse(String keywords1,String keywords2,Pageable page);
	
	Page<TdProviderGoods> findByIsAuditTrue(Pageable page);
	
	Page<TdProviderGoods> findByIsOnSaleTrue(Pageable page);
	
	Page<TdProviderGoods> findByGoodsTitleContainingAndIsAuditTrueOrCodeContainingAndIsAuditTrue(String keywords1,String keywords2,Pageable page);
	
	Page<TdProviderGoods> findByGoodsTitleContainingAndIsOnSaleTrueOrCodeContainingAndIsOnSaleTrue(String keywords1,String keywords2,Pageable page);
	
	Page<TdProviderGoods> findByIsAuditFalse(Pageable page);
	
	Page<TdProviderGoods> findByGoodsTitleContainingAndIsAuditFalseOrCodeContainingAndIsAuditFalse(String keywords1,String keywords2,Pageable page);
	
	Page<TdProviderGoods> findByGoodsTitleContainingOrCodeContaining(String keywords1,String keywords2,Pageable page);

	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.isDistribution=?2 and pg.isAudit=?3")
	Page<TdProviderGoods> findByProviderIdAndIsDistributionAndIsAudit(Long providerId,Boolean isDistribution,Boolean isAudit,Pageable page);
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.goodsTitle like ?2 and pg.isDistribution=?3 and pg.isAudit=?4 or p.id=?5 and pg.code like ?6 and pg.isDistribution=?7 and pg.isAudit=?8")
	Page<TdProviderGoods> findByProviderIdAndGoodsTitleLikeAndIsDistributionAndIsAuditOrProviderIdAndCodeLikeAndIsDistributionAndIsAudit(
																						Long providerId,
																						String keywords,
																						Boolean isDistribution,
																						Boolean isAudit,
																						Long providerId2,
																						String keywords2,
																						Boolean isDistribution2,
																						Boolean isAudit2,Pageable page);
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.isDistribution=?2")
	Page<TdProviderGoods> findByProviderIdAndIsDistribution(Long providerId,Boolean isDistribution,Pageable page);
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.goodsTitle like ?2 and pg.isDistribution=?3 or p.id=?4 and pg.code like ?5 and pg.isDistribution=?6")
	Page<TdProviderGoods> findByProviderIdAndGoodsTitleLikeAndIsDistributionOrProviderIdAndCodeLikeAndIsDistribution(
			Long providerId,
			String keywords,
			Boolean isDistribution,
			Long providerId2,
			String keywords2,
			Boolean isDistribution2,
			Pageable page);
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1  and pg.isAudit=?2")
	Page<TdProviderGoods> findByProviderIdAndIsAudit(Long providerId,Boolean isAudit,Pageable page);
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.goodsTitle like ?2 and pg.isAudit=?3 or p.id=?4 and pg.code like ?5 and pg.isAudit=?6")
	Page<TdProviderGoods> findByProviderIdAndGoodsTitleLikeAndIsAuditOrProviderIdAndCodeLikeAndIsAudit(
																						Long providerId,
																						String keywords,
																						Boolean isAudit,
																						Long providerId2,
																						String keywords2,
																						Boolean isAudit2,Pageable page);
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.goodsTitle like ?2 and pg.isOnSale=?3 or p.id=?4 and pg.code like ?5 and pg.isOnSale=?6")
	Page<TdProviderGoods> findByProviderIdAndGoodsTitleLikeAndIsOnSaleOrProviderIdAndCodeLikeAndIsOnSale(
																						Long providerId,
																						String keywords,
																						Boolean inOnSale,
																						Long providerId2,
																						String keywords2,
																						Boolean isOnSale2,Pageable page);
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.goodsTitle like ?2 or p.id=?3 and pg.code like ?4")
	Page<TdProviderGoods> findByProviderIdAndGoodsTitleLikeOrProviderIdAndCodeLike(
																						Long providerId,
																						String keywords,
																						Long providerId2,
																						String keywords2,
																						Pageable page);
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.categoryIdTree like ?2 and pg.isDistribution=?3")
	Page<TdProviderGoods> findByProviderIdAndCategoryIdTreeLikeAndIsDistribution(Long providerId,String catId,Boolean isDistribution,Pageable page);
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.categoryIdTree like ?2 and pg.isDistribution=?3 and pg.isAudit=?4")
	Page<TdProviderGoods> findByProviderIdAndCategoryIdTreeLikeAndIsDistributionAndIsAudit(Long providerId,String catId,Boolean isDistribution,Boolean isAudit,Pageable page);
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.categoryIdTree like ?2 and pg.goodsTitle like ?3 and pg.isDistribution=?4 "
															+ "or p.id=?5 and pg.categoryIdTree like ?6 and pg.code like ?7 and pg.isDistribution=?8")
	Page<TdProviderGoods> findByProviderIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsDistributionOrProviderIdAndCategoryIdTreeLikeAndCodeLikeAndIsDistribution(
																						Long providerId,String catStr1,
																						String keywords,
																						Boolean isDistribution,
																						Long providerId2,String catStr2,
																						String keywords2,
																						Boolean isDistribution2,
																						Pageable page);
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.categoryIdTree like ?2 and pg.goodsTitle like ?3 and pg.isDistribution=?4 and pg.isAudit=?5 "
															+ "or p.id=?6 and pg.categoryIdTree like ?7 and pg.code like ?8 and pg.isDistribution=?9 and pg.isAudit=?10 ")
	Page<TdProviderGoods> findByProviderIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsDistributionAndIsAuditOrProviderIdAndCategoryIdTreeLikeAndCodeLikeAndIsDistributionAndIsAudit(
																						Long providerId,String catStr1,
																						String keywords,
																						Boolean isDistribution,Boolean isAudit,
																						Long providerId2,String catStr2,
																						String keywords2,
																						Boolean isDistribution2,Boolean isAudit2,
																						Pageable page);
	
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.categoryIdTree like ?2 order by pg.id DESC")
	Page<TdProviderGoods> findByProviderIdAndCategoryIdTreeLike(long providerId,String catStr,Pageable page);
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.categoryIdTree like ?2 and pg.goodsTitle like ?3 "
															+ "or p.id=?4 and pg.categoryIdTree like ?5 and pg.code like ?6")
	Page<TdProviderGoods> findByProviderIdAndCategoryIdTreeLikeAndGoodsTitleLikeOrProviderIdCategoryIdTreeLikeAndCodeLike(
																						Long providerId,String catStr1,
																						String keywords,
																						Long providerId2,String catStr2,
																						String keywords2,
																						Pageable page);
	
	
	
	
	//
	//
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.isOnSale=?2 order by pg.id DESC")
	Page<TdProviderGoods> findByProviderIdAndIsOnSale(long providerId,Boolean isOnSale,Pageable page);
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.goodsTitle like ?2 and pg.isOnSale = ?3 or "
																+ "p.id=?4 and pg.code like ?5 and pg.isOnSale =?6 or "
																+ "p.id=?7 and pg.code like ?8 and pg.isOnSale =?9 ")
	Page<TdProviderGoods> findByProviderIdAndGoodsTitleLikeAndIsOnSaleOrProviderIdAndCodeLikeAndIsOnSaleOrCodeLikeAndIsOnSale(
																						Long providerId,
																						String keywords,Boolean isOnSale1,
																						Long providerId2,
																						String keywords2,Boolean isOnSale2,
																						Long providerId3,
																						String keywords3,Boolean isOnSale3,
																						Pageable page);
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.categoryIdTree like ?2 and pg.isOnSale=?3 order by pg.id DESC")
	Page<TdProviderGoods> findByProviderIdAndCategoryIdTreeLikeAndIsOnSale(long providerId,String catStr,Boolean isOnSale,Pageable page);
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.categoryIdTree like ?2 and pg.goodsTitle like ?3 and pg.isOnSale=?4 or "
																+ " p.id=?5 and pg.categoryIdTree like ?6 and pg.code like ?7 and pg.isOnSale=?8 ")
	Page<TdProviderGoods> findByProviderIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsOnSaleOrProviderIdAndCategoryIdTreeLikeAndCodeLikeAndIsOnSale(
																							Long providerId,String catStr1,
																							String keywords,Boolean isOnSale1,
																							Long providerId2,String catStr2,
																							String keywords2,Boolean isOnSale2,
																							Pageable page);
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.categoryIdTree like ?2 and pg.isAudit=?3 order by pg.id DESC")
	Page<TdProviderGoods> findByProviderIdAndCategoryIdTreeLikeAndIsAudit(long providerId,String catStr,Boolean isAudit,Pageable page);
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1 and pg.categoryIdTree like ?2 and pg.goodsTitle like ?3 and pg.isAudit=?4 or "
			+ " p.id=?5 and pg.categoryIdTree like ?6 and pg.code like ?7 and pg.isAudit=?8 ")
	Page<TdProviderGoods> findByProviderIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsAuditOrProviderIdAndCategoryIdTreeLikeAndCodeLikeAndIsAudit(
																							Long providerId,String catStr1,
																							String keywords,Boolean isOnSale1,
																							Long providerId2,String catStr2,
																							String keywords2,Boolean isOnSale2,
																							Pageable page);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
