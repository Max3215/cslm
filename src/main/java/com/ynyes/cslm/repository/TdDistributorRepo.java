package com.ynyes.cslm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdDistributor;

/**
 * TdDistributor 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdDistributorRepo extends
		PagingAndSortingRepository<TdDistributor, Long>,
		JpaSpecificationExecutor<TdDistributor> 
{
    Page<TdDistributor> findByTitleContainingOrderBySortIdAsc(String keywords, Pageable page);
    
    List<TdDistributor> findByIsEnableTrue();
    
    List<TdDistributor> findByCityAndIsEnableTrueOrderBySortIdAsc(String city);
    
    /**
	 * @author lc
	 * @注释：
	 */
    TdDistributor findByUsernameAndIsEnableTrue(String username);
}
