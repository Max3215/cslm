package com.ynyes.cslm.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdBank;

public interface TdBankRepo extends
		PagingAndSortingRepository<TdBank, Long>,
		JpaSpecificationExecutor<TdBank>{
	

}
