package com.ynyes.cslm.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdPointOrder;

public interface TdPointOrderRepo extends
	PagingAndSortingRepository<TdPointOrder, Long>,
	JpaSpecificationExecutor<TdPointOrder>{

}
