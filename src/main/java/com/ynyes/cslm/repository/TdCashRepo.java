package com.ynyes.cslm.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdCash;

public interface TdCashRepo extends
		PagingAndSortingRepository<TdCash, Long>,
		JpaSpecificationExecutor<TdCash>{
	
		TdCash findByCashNumber(String cashNumber);
	
}
