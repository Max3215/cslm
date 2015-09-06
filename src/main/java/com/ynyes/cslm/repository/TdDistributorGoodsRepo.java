package com.ynyes.cslm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
	
	
	
	
	
	
	
	
	

}
