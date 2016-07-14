package com.ynyes.cslm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdCartGoods;

/**
 * TdCartGoods 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdCartGoodsRepo extends
		PagingAndSortingRepository<TdCartGoods, Long>,
		JpaSpecificationExecutor<TdCartGoods> 
{
//    TdCartGoods findTopByGoodsIdAndPriceAndUsername(Long goodsId, Double price, String username);
    TdCartGoods findTopByGoodsIdAndUsername(Long goodsId, String username);
    
//    List<TdCartGoods> findByGoodsIdAndPriceAndUsername(Long goodsId, Double price, String username);
    
    List<TdCartGoods> findByDistributorGoodsIdAndUsername(Long goodsId, String username);
    
    List<TdCartGoods> findByGoodsIdAndUsernameAndProviderId(Long goodsId, String username,Long providerId);
    
    List<TdCartGoods> findByUsernameOrderByIdDesc(String username);
    
    List<TdCartGoods> findByUsernameAndIsSelectedTrueOrderByIdDesc(String username);
    
    // 查询用户选择商品所属超市ID
    @Query(value="select cg.distributorId from TdCartGoods cg where cg.username =?1 and cg.isSelected =true group by cg.distributorId")
    List<Long> findByUsernameAndIsSelectedTrue(String username);
}
