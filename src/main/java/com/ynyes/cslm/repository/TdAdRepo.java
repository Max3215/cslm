package com.ynyes.cslm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdAd;

/**
 * TdAd 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdAdRepo extends
		PagingAndSortingRepository<TdAd, Long>,
		JpaSpecificationExecutor<TdAd> 
{
    Page<TdAd> findByIsEnableTrueOrderBySortIdAsc(Pageable page);
    List<TdAd> findByTypeIdAndStartTimeBeforeAndEndTimeAfterAndIsEnableTrueOrderBySortIdAsc(Long typeId, Date current1, Date current2);
    
    List<TdAd> findByTypeIdAndDistributorIdAndStartTimeBeforeAndEndTimeAfterAndIsEnableTrueOrderBySortIdAsc(Long typeId,Long disId, Date current1, Date current2);
}
