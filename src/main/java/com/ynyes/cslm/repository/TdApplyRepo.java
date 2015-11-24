package com.ynyes.cslm.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdApply;

public interface TdApplyRepo extends 
		PagingAndSortingRepository<TdApply,Long>,JpaSpecificationExecutor<TdApply>
{

}
