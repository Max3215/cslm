package com.ynyes.cslm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdPayRecord;

public interface TdPayRecordRepo extends
        PagingAndSortingRepository<TdPayRecord, Long>,
        JpaSpecificationExecutor<TdPayRecord> 
{
    List<TdPayRecord> findByOrderIdOrderByCreateTimeDesc(Long orderId);
    
    Page<TdPayRecord> findByDistributorIdOrderByCreateTimeDesc(Long disId,Pageable page);
    List<TdPayRecord> findByDistributorIdOrderByCreateTimeDesc(Long disId);
    
    Page<TdPayRecord> findByDistributorIdAndContContainingOrderByCreateTimeDesc(Long disId,String cont,Pageable page);
    
    Page<TdPayRecord> findByProviderIdOrderByCreateTimeDesc(Long proId,Pageable page);
    List<TdPayRecord> findByProviderIdOrderByCreateTimeDesc(Long proId);
    
    Page<TdPayRecord> findByProviderIdAndContContainingOrderByCreateTimeDesc(Long proId,String cont,Pageable page);
    
    Page<TdPayRecord> findByUsernameOrderByCreateTimeDesc(String username,Pageable page);
}
