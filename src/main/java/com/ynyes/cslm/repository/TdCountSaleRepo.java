package com.ynyes.cslm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdCountSale;

public interface TdCountSaleRepo extends
	PagingAndSortingRepository<TdCountSale, Long>,
	JpaSpecificationExecutor<TdCountSale>{

	List<TdCountSale> findByShipIdAndSaleTypeOrderByQuantityDesc(Long shipId,Long saleType);
	
	void deleteByShipIdAndSaleType(Long shipId,Long saleType);
}
