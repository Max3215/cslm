package com.ynyes.cslm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;

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
    
    TdDistributor findByUsernameAndIsEnableTrue(String username);
    
    List<TdDistributor> findByDisctrictAndIsEnableTrue(String disctrict);
    
    TdDistributor findByVirtualAccountAndIsEnableTrue(String virtualAccount);
    
    TdDistributor findByIdAndIsEnableTrue(Long id);
    
//    List<TdDistributor> findByIsEnableTrueGroupCity();
    
//    List<TdDistributor> findByProvinceAndIsEnableTrueOrderByCityAndDisctrict(String province,Sort sort);
    
    @Query("select g from TdDistributor d join d.goodsList g where d.id=?1 and g.goodsId=?2")
    TdDistributorGoods findByIdAndGoodsId(Long id, Long goodsId);
    
   
    
}
