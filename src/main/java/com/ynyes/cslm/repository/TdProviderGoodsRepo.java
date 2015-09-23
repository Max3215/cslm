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
	
	
	
	
}
