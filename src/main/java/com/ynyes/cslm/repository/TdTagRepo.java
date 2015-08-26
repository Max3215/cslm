package com.ynyes.cslm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdTag;

public interface TdTagRepo extends 
		PagingAndSortingRepository<TdTag, Long>,
		JpaSpecificationExecutor<TdTag>
{

	List<TdTag> findByTypeId(Long id);
	
	Page<TdTag> findByTypeId(Long typeId,Pageable page);
}
