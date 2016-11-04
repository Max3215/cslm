package com.ynyes.cslm.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdPointGoods;

public interface TdPointGoodsRepo extends
	PagingAndSortingRepository<TdPointGoods, Long>,
	JpaSpecificationExecutor<TdPointGoods>{

	TdPointGoods findByIdAndIsEnableTrue(Long id);
	
}
