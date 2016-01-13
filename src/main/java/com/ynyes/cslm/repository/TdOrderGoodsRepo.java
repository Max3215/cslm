package com.ynyes.cslm.repository;


import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdOrderGoods;

/**
 * TdOrderGoods 实体数据库操作接口
 * 
 * @author Max
 *
 */

public interface TdOrderGoodsRepo extends
		PagingAndSortingRepository<TdOrderGoods, Long>,
		JpaSpecificationExecutor<TdOrderGoods> 
{
	
	 @Query(value="select og from TdOrder o join o.orderGoodsList og where o.shopId=?1 and o.typeId=?2 order by o.orderTime desc")
	 Page<TdOrderGoods> findByShopIdAndTypeId(Long disId,Long typeId,Pageable page);
	
	 @Query(value="select og from TdOrder o join o.orderGoodsList og where o.shopId=?1 and o.typeId=?2 and og.goodsTitle like ?3% "
														+ " or o.shopId=?4 and o.typeId=?5 and og.goodsCode like ?6% order by o.orderTime desc")
	 Page<TdOrderGoods> findByShopIdAndTypeIdAndGoodsTitleLIikeOrShopIdAndTypeIdAndGoodsCodeLike(Long disId,Long typeId,String goodsTitle,
																						Long shopId2,Long typeId2,String goodsCode,Pageable page);
	
	 @Query(value="select og from TdOrder o join o.orderGoodsList og where o.shopId=?1 and o.typeId=?2 and og.saleTime <?3 order by o.orderTime desc")
	 Page<TdOrderGoods> findByShopIdAndTypeIdAndSaleTimeBefore(Long disId,Long typeId,Date endTime,Pageable page);
	
	 @Query(value="select og from TdOrder o join o.orderGoodsList og where o.shopId=?1 and o.typeId=?2 and og.goodsTitle like ?3% and og.saleTime <?4"
																	+ " or o.shopId=?5 and o.typeId=?6 and og.goodsCode like ?7% and og.saleTime <?8 order by o.orderTime desc")
	 Page<TdOrderGoods> findByShopIdAndTypeIdAndGoodsTitleLIikeAndSaleTimeBeforeOrShopIdAndTypeIdAndGoodsCodeLikeAndSaleTimeBefore(
																		Long disId,Long typeId,String goodsTitle,Date endTime1,
																		Long shopId2,Long typeId2,String goodsCode,Date endTime2,Pageable page);
	
	 @Query(value="select og from TdOrder o join o.orderGoodsList og where o.shopId=?1 and o.typeId=?2 and og.saleTime >?3 order by o.orderTime desc")
	 Page<TdOrderGoods> findByShopIdAndTypeIdAndSaleTimeAfter(Long disId,Long typeId,Date startTime,Pageable page);
	
	 @Query(value="select og from TdOrder o join o.orderGoodsList og where o.shopId=?1 and o.typeId=?2 and og.goodsTitle like ?3% and og.saleTime >?4"
																+ " or o.shopId=?5 and o.typeId=?6 and og.goodsCode like ?7% and og.saleTime >?8 order by o.orderTime desc")
	 Page<TdOrderGoods> findByShopIdAndTypeIdAndGoodsTitleLIikeAndSaleTimeAfterOrShopIdAndTypeIdAndGoodsCodeLikeAndSaleTimeAfter(
															Long disId,Long typeId,String goodsTitle,Date startTime1,
															Long shopId2,Long typeId2,String goodsCode,Date startTime2,Pageable page);
	 
	 @Query(value="select og from TdOrder o join o.orderGoodsList og where o.shopId=?1 and o.typeId=?2 and og.saleTime >?3 and og.saleTime <?3 order by o.orderTime desc")
	 Page<TdOrderGoods> findByShopIdAndTypeIdAndSaleTimeAfterAndSaleTimeBefore(Long disId,Long typeId,Date startTime,Date endTime,Pageable page);
	 
	 @Query(value="select og from TdOrder o join o.orderGoodsList og where o.shopId=?1 and o.typeId=?2 and og.goodsTitle like ?3% and og.saleTime >?4  and og.saleTime <?5 "
																				+ " or o.shopId=?6 and o.typeId=?7 and og.goodsCode like ?8% and og.saleTime >?9  and og.saleTime <?10 order by o.orderTime desc")
	 Page<TdOrderGoods> findByShopIdAndTypeIdAndGoodsTitleLIikeAndSaleTimeAfterAndSaleTimeBeforeOrShopIdAndTypeIdAndGoodsCodeLikeAndSaleTimeAfterAndSaleTimeBefore(
																					Long disId,Long typeId,String goodsTitle,Date startTime1,Date endTime1,
																					Long shopId2,Long typeId2,String goodsCode,Date startTime2,Date endTime2,Pageable page);
	 
}
