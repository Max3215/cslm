package com.ynyes.cslm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdUserCollect;

/**
 * TdUserCollect 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdUserCollectRepo extends
		PagingAndSortingRepository<TdUserCollect, Long>,
		JpaSpecificationExecutor<TdUserCollect> 
{
    Page<TdUserCollect> findByUsernameAndTypeOrderByIdDesc(String username,Integer type, Pageable page);
    
    Page<TdUserCollect> findByUsernameAndGoodsTitleContainingAndTypeOrderByIdDesc(String username, String keywords,Integer type, Pageable page);
    
    List<TdUserCollect> findByUsername(String username);
    
    TdUserCollect findByUsernameAndDistributorIdAndType(String username, Long goodsId,Integer type);
    
    TdUserCollect findByUsernameAndProviderId(String username, Long pId);
    
    Long countByGoodsId(Long goodsId);
}
