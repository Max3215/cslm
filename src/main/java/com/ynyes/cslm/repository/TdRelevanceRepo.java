package com.ynyes.cslm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdRelevance;

public interface TdRelevanceRepo extends
	PagingAndSortingRepository<TdRelevance, Long>,
	JpaSpecificationExecutor<TdRelevance>{

//	List<TdRelevance> findByGoodsId1(Long goodsId);
//	List<TdRelevance> findByGoodsId2(Long goodsId);
//	
//	TdRelevance findByGoodsId1AndGoodsId2(Long goodsId1,Long goodsId2);
}
