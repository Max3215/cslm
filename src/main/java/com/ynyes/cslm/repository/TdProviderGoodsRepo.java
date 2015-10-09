package com.ynyes.cslm.repository;

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
	
	@Query("select pg from TdProvider p join p.goodsList pg where p.id=?1")
	Page<TdProviderGoods> findByIdAndIsAudit(long providerId,Pageable page);
	
	@Query(value="select * from td_provider_goods where provider_id=?1 and goods_id=?2",nativeQuery =true)
	TdProviderGoods findByProviderIdAndGoodsId(long providerId,long goodsId);
	
	Page<TdProviderGoods> findAll(Pageable page);
	
	@Query(value="select pg.provider_id from td_provider_goods pg where pg.id=?1",nativeQuery=true)
	Long findById(Long id);
	
	Page<TdProviderGoods> findByGoodsTitleContainingOrSubGoodsTitleContainingOrProviderTitleContaining(String keywords1,String keywords2,String keywords3,Pageable page);
	
}
