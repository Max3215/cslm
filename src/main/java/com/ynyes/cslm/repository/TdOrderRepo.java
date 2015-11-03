package com.ynyes.cslm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdOrderGoods;
import com.ynyes.cslm.entity.TdUser;

/**
 * TdOrder 实体数据库操作接口
 * 
 * @author Sharon
 *
 */

public interface TdOrderRepo extends
		PagingAndSortingRepository<TdOrder, Long>,
		JpaSpecificationExecutor<TdOrder> 
{
    Page<TdOrder> findByStatusIdOrderByIdDesc(Long statusId, Pageable page);
    
    Page<TdOrder> findByUsernameOrderByIdDesc(String username, Pageable page);
    
    Page<TdOrder> findByUsernameAndOrderTimeAfterOrderByIdDesc(String username, Date time, Pageable page);
    
    Page<TdOrder> findByUsernameAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(String username, Date time, String keywords, Pageable page);
    
    Page<TdOrder> findByUsernameAndOrderNumberContainingOrderByIdDesc(String username, String keywords, Pageable page);
    
    Page<TdOrder> findByUsernameAndStatusIdOrderByIdDesc(String username, Long statusId, Pageable page);
    
    Page<TdOrder> findByUsernameAndStatusIdAndOrderNumberContainingOrderByIdDesc(String username, Long statusId, String keywords, Pageable page);
    
    Page<TdOrder> findByUsernameAndStatusIdAndOrderTimeAfterOrderByIdDesc(String username, Long statusId, Date time, Pageable page);
    
    Page<TdOrder> findByUsernameAndStatusIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(String username, Long statusId, Date time, String keywords, Pageable page);
  
    Long countByUsernameAndTypeIdAndStatusId(String username,Long typeId, Long statusId);
    
    TdOrder findByOrderNumber(String orderNumber);
    
    /**
	 * @author lichong
	 * @注释：同盟店订单查询
	 */
    Page<TdOrder> findByshopTitleOrderByIdDesc(String diystiename, Pageable page);
    Page<TdOrder> findByshopTitleAndOrderNumberContainingOrderByIdDesc(String diystiename, String keywords, Pageable page);
    Page<TdOrder> findByshopTitleAndStatusIdAndOrderNumberContainingOrderByIdDesc(String diystiename, Long statusId, String keywords, Pageable page);
    Page<TdOrder> findByshopTitleAndStatusIdOrderByIdDesc(String diystiename, Long statusId, Pageable page);
    Page<TdOrder> findByshopTitleAndOrderTimeAfterOrderByIdDesc(String diystiename, Date time, Pageable page);
    Page<TdOrder> findByshopTitleAndStatusIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(String diystiename, Long statusId, Date time, String keywords, Pageable page);
    Page<TdOrder> findByshopTitleAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(String diystiename, Date time, String keywords, Pageable page);
    Page<TdOrder> findByshopTitleAndStatusIdAndOrderTimeAfterOrderByIdDesc(String diystiename, Long statusId, Date time, Pageable page);
    
    /**
	 * @author lc
	 * @注释：同盟店信息查询
	 */
    List<TdOrder> findByShopIdAndStatusIdOrderByIdDesc(long shopId, long statusId);
    
    /**
	 * @author lc
	 * @注释：同盟店订单收入查询
	 */
    List<TdOrder> findByStatusIdAndShopTitleOrStatusIdAndShopTitle(Long statusId, String diystiename, Long statusId1, String diystiename1);
    Page<TdOrder> findByStatusIdAndShopTitleOrStatusIdAndShopTitleOrderByIdDesc(Long statusId, String diystiename,  Long statusId1, String diystiename1, Pageable page);
    
    List<TdOrder> findByStatusIdAndShopTitleAndOrderTimeAfterOrStatusIdAndShopTitleAndOrderTimeAfterOrderByIdDesc(Long statusId, String diystiename, Date time, Long statusId1, String diystiename1, Date time1);
    Page<TdOrder> findByStatusIdAndShopTitleAndOrderTimeAfterOrStatusIdAndShopTitleAndOrderTimeAfterOrderByIdDesc(Long statusId, String diystiename, Date time, Long statusId1, String diystiename1, Date time1, Pageable page);
    
    /**
	 * @author lc
	 * @注释：同盟店返利收入
	 */
    List<TdOrder> findByUsernameIn(List<String> tdUsers);
    Page<TdOrder> findByUsernameInOrderByIdDesc(List<String> tdUsers , Pageable page);
    
    List<TdOrder> findByUsernameInAndOrderTimeAfterOrderByIdDesc(List<String> tdUsers,  Date time);
    Page<TdOrder> findByUsernameInAndOrderTimeAfterOrderByIdDesc(List<String> tdUsers,  Date time, Pageable page);
    /**
	 * @author lc
	 * @注释：按订单类型和状态查询
	 */
    Page<TdOrder> findByStatusIdAndTypeIdOrderByIdDesc(long statusId, long typeId, Pageable page);
    List<TdOrder> findByStatusIdAndTypeIdOrderByIdDesc(long statusId, long typeId);
    Page<TdOrder> findByStatusIdOrderByIdDesc(long statusId, Pageable page);
    List<TdOrder> findByStatusIdOrderByIdDesc(long statusId);
    Page<TdOrder> findBytypeIdOrderByIdDesc(long typeId, Pageable page);
    List<TdOrder> findBytypeIdOrderByIdDesc(long typeId);
    /**
	 * @author lc
	 * @注释： 按时间、订单类型和订单状态查询
	 */
    Page<TdOrder> findByOrderTimeAfterOrderByIdDesc(Date time, Pageable page);
    List<TdOrder> findByOrderTimeAfterOrderByIdDesc(Date time);
    Page<TdOrder> findByStatusIdAndTypeIdAndOrderTimeAfterOrderByIdDesc(long statusId, long typeId, Date time, Pageable page);
    List<TdOrder> findByStatusIdAndTypeIdAndOrderTimeAfterOrderByIdDesc(long statusId, long typeId, Date time);
    Page<TdOrder> findByStatusIdAndOrderTimeAfterOrderByIdDesc(long statusId, Date time, Pageable page);
    List<TdOrder> findByStatusIdAndOrderTimeAfterOrderByIdDesc(long statusId, Date time);
    Page<TdOrder> findBytypeIdAndOrderTimeAfterOrderByIdDesc(long typeId, Date time, Pageable page);
    List<TdOrder> findBytypeIdAndOrderTimeAfterOrderByIdDesc(long typeId, Date time);
    /**
     * 按交易状态查询
     * @author libiao
     */
    List<TdOrder> findByStatusId(Long statusId);
//    List<TdOrder> findAll();
    
    @Query("select o from TdOrder o join o.orderGoodsList g where o.shopId=?1 and g.goodsId=?2")
    Page<TdOrder> findByShopIdAndGoodsId(Long shopId, Long gid,Pageable page);
    
//    @Query("select o from TdOrder o join o.orderGoodsList g where o.shopId=?1")
    Page<TdOrder> findByShopIdAndTypeIdOrderByIdDesc(Long shopTitle,Long typeId, Pageable page);
    
    Page<TdOrder> findByUsernameAndTypeIdOrderByIdDesc(String username,long typeId,Pageable page);
    Page<TdOrder> findByShopIdAndTypeIdOrderByOrderTimeDesc(long shopId,long typeId,Pageable page);
    Page<TdOrder> findByProviderIdAndTypeIdOrderByOrderTimeDesc(long provider,long typeId,Pageable page);
    
    /**
     *  超市进货订单
     * @param username
     * @param typeId
     * @param time
     * @param page
     * @return
     */
    Page<TdOrder> findByUsernameAndTypeIdAndOrderTimeAfterOrderByIdDesc(String username,long typeId, Date time, Pageable page);
    Page<TdOrder> findByUsernameAndTypeIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(String username,long typeId, Date time, String keywords, Pageable page);
    Page<TdOrder> findByUsernameAndTypeIdAndOrderNumberContainingOrderByIdDesc(String username,long typeId, String keywords, Pageable page);
    Page<TdOrder> findByUsernameAndTypeIdAndStatusIdOrderByIdDesc(String username,long typeId, Long statusId, Pageable page);
    Page<TdOrder> findByUsernameAndTypeIdAndStatusIdAndOrderNumberContainingOrderByIdDesc(String username,long typeId, Long statusId, String keywords, Pageable page);
    Page<TdOrder> findByUsernameAndTypeIdAndStatusIdAndOrderTimeAfterOrderByIdDesc(String username,long typeId, Long statusId, Date time, Pageable page);
    Page<TdOrder> findByUsernameAndTypeIdAndStatusIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(String username,long typeId, Long statusId, Date time, String keywords, Pageable page);
    
    /**
     * 超市销售订单
     * @param ShopId
     * @param typeId
     * @param time
     * @param page
     * @return
     */
    Page<TdOrder> findByShopIdAndTypeIdAndOrderTimeAfterOrderByIdDesc(long ShopId,long typeId, Date time, Pageable page);
    Page<TdOrder> findByShopIdAndTypeIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(long ShopId,long typeId, Date time, String keywords, Pageable page);
    Page<TdOrder> findByShopIdAndTypeIdAndOrderNumberContainingOrderByIdDesc(long ShopId,long typeId, String keywords, Pageable page);
    Page<TdOrder> findByShopIdAndTypeIdAndStatusIdOrderByIdDesc(long ShopId,long typeId, Long statusId, Pageable page);
    Page<TdOrder> findByShopIdAndTypeIdAndStatusIdAndOrderNumberContainingOrderByIdDesc(long ShopId,long typeId, Long statusId, String keywords, Pageable page);
    Page<TdOrder> findByShopIdAndTypeIdAndStatusIdAndOrderTimeAfterOrderByIdDesc(long ShopId,long typeId, Long statusId, Date time, Pageable page);
    Page<TdOrder> findByShopIdAndTypeIdAndStatusIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(long ShopId,long typeId, Long statusId, Date time, String keywords, Pageable page);
    
    Page<TdOrder> findByProviderIdAndTypeIdAndOrderTimeAfterOrderByIdDesc(long ProviderId,long typeId, Date time, Pageable page);
    Page<TdOrder> findByProviderIdAndTypeIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(long ProviderId,long typeId, Date time, String keywords, Pageable page);
    Page<TdOrder> findByProviderIdAndTypeIdAndOrderNumberContainingOrderByIdDesc(long ProviderId,long typeId, String keywords, Pageable page);
    Page<TdOrder> findByProviderIdAndTypeIdAndStatusIdOrderByIdDesc(long ProviderId,long typeId, Long statusId, Pageable page);
    Page<TdOrder> findByProviderIdAndTypeIdAndStatusIdAndOrderNumberContainingOrderByIdDesc(long ProviderId,long typeId, Long statusId, String keywords, Pageable page);
    Page<TdOrder> findByProviderIdAndTypeIdAndStatusIdAndOrderTimeAfterOrderByIdDesc(long ProviderId,long typeId, Long statusId, Date time, Pageable page);
    Page<TdOrder> findByProviderIdAndTypeIdAndStatusIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(long ProviderId,long typeId, Long statusId, Date time, String keywords, Pageable page);
    
    Long countByShopIdAndTypeIdAndStatusId(long shopId,long typeId,long statusId);
    
    Long countByProviderIdAndTypeIdAndStatusId(long providerId,long typeId,long statusId);
    
}
