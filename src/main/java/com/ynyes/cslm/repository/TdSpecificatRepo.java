package com.ynyes.cslm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdSpecificat;


public interface TdSpecificatRepo extends
	PagingAndSortingRepository<TdSpecificat, Long>,
	JpaSpecificationExecutor<TdSpecificat>{
	
	void deleteByGoodsIdAndType(Long goodsId,Integer type);
	
	List<TdSpecificat> findByGoodsIdAndType(Long goodsId,Integer type);
	
	List<TdSpecificat> findByShopIdAndGoodsIdAndType(Long shopId,Long goodsId,Integer type);
	
	List<TdSpecificat> findByOldId(Long id);
	TdSpecificat findByShopIdAndOldId(Long shopId,Long id);

}
