package com.ynyes.cslm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdProviderGoods;

/**
 * TdProvider 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdProviderRepo extends
		PagingAndSortingRepository<TdProvider, Long>,
		JpaSpecificationExecutor<TdProvider> 
{
    Page<TdProvider> findByTitleContainingOrProvinceContainingOrCityContainingOrAddressContainingOrderBySortIdAsc(String keywords1, String keywords2, String keywords3, String keywords4, Pageable page);
    TdProvider findByTitle(String title);
    TdProvider findByTitleAndIdNot(String title, Long id);
    
    
    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    
    TdProvider findByUsernameAndIsEnableTrue(String username);
    
    @Query("select g from TdProvider p join p.goodsList g where p.username=?1 and g.isAudit=?2")
    Page<TdProviderGoods> findByUsernameAndIsAudit(String username,Boolean isAudit,Pageable page);
    
    TdProvider findByVirtualAccountAndIsEnableTrue(String virtualAccount);
    
    List<TdProvider> findByType(Long type);
    
    Page<TdProvider> findByTypeOrderBySortIdAsc(Long type,Pageable page);
    
    Page<TdProvider> findByTypeAndTitleContainingOrTypeAndProvinceContainingOrTypeAndCityContainingOrTypeAndAddressContainingOrderBySortIdAsc(
    											Long type1,String keywords1,
    											Long type2,String keywords2,
    											Long type3,String keywords3,
    											Long type4,String keywords4,Pageable page);
}
