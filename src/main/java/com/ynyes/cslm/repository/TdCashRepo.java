package com.ynyes.cslm.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdCash;

public interface TdCashRepo extends
		PagingAndSortingRepository<TdCash, Long>,
		JpaSpecificationExecutor<TdCash>{
	
		TdCash findByCashNumber(String cashNumber);
		
		Page<TdCash> findByCreateTimeBetween(Date startTime,Date endTime,Pageable page);
		Page<TdCash> findByType(Long type,Pageable page);
		Page<TdCash> findByTypeAndCreateTimeBetween(Long type,Date startTime,Date endTime,Pageable page);
		
		Page<TdCash> findByShopType(Long shopType,Pageable page);
		Page<TdCash> findByShopTypeAndCreateTimeBetween(Long shopType,Date startTime,Date endTime,Pageable page);
		Page<TdCash> findByShopTypeAndType(Long shopType,Long type,Pageable page);
		Page<TdCash> findByShopTypeAndTypeAndCreateTimeBetween(Long shopType,Long type,Date startTime,Date endTime,Pageable page);
		
}
