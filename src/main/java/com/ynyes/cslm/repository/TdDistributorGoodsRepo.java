package com.ynyes.cslm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

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
	
		List<TdDistributorGoods> findByGoodsId(Long goodsId);
	
	
		@Query("select g from TdDistributor d join d.goodsList g where d.username=?1 and g.isOnSale=?2")
		Page<TdDistributorGoods> findByUsernameAndIsOnSale(String username,Boolean onsale,Pageable page);
	
		@Query(value = "select * from td_distributor_goods where distributor_id = ?1 and goods_id=?2 and is_on_sale = ?3", nativeQuery = true)
		TdDistributorGoods findByDistributorIdAndGoodsIdAndIsOnSale(long disId,long goodsid, Boolean onsale);
	
		@Query(value = "select * from td_distributor_goods where distributor_id = ?1 ", nativeQuery = true)
		List<TdDistributorGoods> findTop12ByDistributorIdAndIsOnSaleTrueOrderBySoldNumberDesc(long disId);
	
	

}
