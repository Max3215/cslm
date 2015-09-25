package com.ynyes.cslm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdOrderGoods;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.repository.TdOrderRepo;

import scala.xml.dtd.PublicID;

/**
 * TdOrder 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdOrderService {
    @Autowired
    TdOrderRepo repository;
    
    /**
     * 删除
     * 
     * @param id 菜单项ID
     */
    public void delete(Long id)
    {
        if (null != id)
        {
            repository.delete(id);
        }
    }
    
    /**
     * 删除
     * 
     * @param e 菜单项
     */
    public void delete(TdOrder e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdOrder> entities)
    {
        if (null != entities)
        {
            repository.delete(entities);
        }
    }
    
    /**
     * 查找
     * 
     * @param id ID
     * @return
     */
    public TdOrder findOne(Long id)
    {
        if (null == id)
        {
            return null;
        }
        
        return repository.findOne(id);
    }
    
    /**
     * 查找
     * 
     * @param ids
     * @return
     */
    public List<TdOrder> findAll(Iterable<Long> ids)
    {
        return (List<TdOrder>) repository.findAll(ids);
    }
    
    public List<TdOrder> findAll()
    {
        return (List<TdOrder>) repository.findAll();
    }
    
    public Page<TdOrder> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    public Page<TdOrder> findAllOrderByIdDesc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
        
        return repository.findAll(pageRequest);
    }
    
    public Page<TdOrder> findByStatusIdOrderByIdDesc(long statusId, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByStatusIdOrderByIdDesc(statusId, pageRequest);
    }
    
    public Page<TdOrder> findByUsername(String username, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByUsernameOrderByIdDesc(username, pageRequest);
    }
    
    public TdOrder findByOrderNumber(String orderNumber)
    {
        return repository.findByOrderNumber(orderNumber);
    }
    
    public Page<TdOrder> findByUsernameAndTimeAfter(String username, Date time, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByUsernameAndOrderTimeAfterOrderByIdDesc(username, time, pageRequest);
    }
    
    public Page<TdOrder> findByUsernameAndTimeAfterAndSearch(String username, Date time, String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByUsernameAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(username, time, keywords, pageRequest);
    }
    
    public Page<TdOrder> findByUsernameAndSearch(String username, String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByUsernameAndOrderNumberContainingOrderByIdDesc(username, keywords, pageRequest);
    }
    
    public Page<TdOrder> findByUsernameAndStatusId(String username, long statusId, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByUsernameAndStatusIdOrderByIdDesc(username, statusId, pageRequest);
    }
    
    public Page<TdOrder> findByUsernameAndStatusIdAndSearch(String username, long statusId, String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByUsernameAndStatusIdAndOrderNumberContainingOrderByIdDesc(username, statusId, keywords, pageRequest);
    }
    
    public Page<TdOrder> findByUsernameAndStatusIdAndTimeAfter(String username, long statusId, Date time, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByUsernameAndStatusIdAndOrderTimeAfterOrderByIdDesc(username, statusId, time, pageRequest);
    }
    
    public Page<TdOrder> findByUsernameAndStatusIdAndTimeAfterAndSearch(String username, long statusId, Date time, String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByUsernameAndStatusIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(username, statusId, time, keywords, pageRequest);
    }
    
    public Long countByUsernameAndTypeIdAndStatusId(String username,long typeId, long statusId)
    {
        return repository.countByUsernameAndTypeIdAndStatusId(username,typeId ,statusId);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdOrder save(TdOrder e)
    {
        
        return repository.save(e);
    }
    
    public List<TdOrder> save(List<TdOrder> entities)
    {
        
        return (List<TdOrder>) repository.save(entities);
    }
    /**
	 * @author lichong
	 * @注释：
	 */
    public Page<TdOrder> findByDiysitename(String diysitename, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByshopTitleOrderByIdDesc(diysitename, pageRequest);
    }
    public Page<TdOrder> findByDiysitenameAndSearch(String diysitename, String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByshopTitleAndOrderNumberContainingOrderByIdDesc(diysitename, keywords, pageRequest);
    }
    public Page<TdOrder> findByDiysitenameAndStatusIdAndSearch(String diysitename, long statusId, String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByshopTitleAndStatusIdAndOrderNumberContainingOrderByIdDesc(diysitename, statusId, keywords, pageRequest);
    }
    public Page<TdOrder> findByDiysitenameAndStatusId(String diysitename, long statusId, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByshopTitleAndStatusIdOrderByIdDesc(diysitename, statusId, pageRequest);
    }
    public Page<TdOrder> findByDiysitenameAndTimeAfter(String diysitename, Date time, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByshopTitleAndOrderTimeAfterOrderByIdDesc(diysitename, time, pageRequest);
    }
    public Page<TdOrder> findByDiysitenameAndTimeAfterAndSearch(String diysitename, Date time, String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByshopTitleAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(diysitename, time, keywords, pageRequest);
    }
    public Page<TdOrder> findByDiysitenameAndStatusIdAndTimeAfterAndSearch(String diysitename, long statusId, Date time, String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByshopTitleAndStatusIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(diysitename, statusId, time, keywords, pageRequest);
    }
    public Page<TdOrder> findByDiysitenameAndStatusIdAndTimeAfter(String diysitename, long statusId, Date time, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByshopTitleAndStatusIdAndOrderTimeAfterOrderByIdDesc(diysitename, statusId, time, pageRequest);
    }
    
    /**
	 * @author lc
	 * @注释：线下同盟店信息
	 */
    public List<TdOrder> findByshopIdAndstatusId(long shopId, long statusId){
    	return repository.findByShopIdAndStatusIdOrderByIdDesc(shopId, statusId);
    }
    /**
	 * @author lc
	 * @注释：订单收入查询
	 */
    public List<TdOrder> findAllVerifyBelongShopTitle(String diysitename){
    	return repository.findByStatusIdAndShopTitleOrStatusIdAndShopTitle(5L, diysitename, 6L, diysitename);
    }
    public Page<TdOrder> findAllVerifyBelongShopTitleOrderByIdDesc(String diysitename, int page, int size){
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findByStatusIdAndShopTitleOrStatusIdAndShopTitleOrderByIdDesc(5L, diysitename, 6L, diysitename, pageRequest);
    }
    public List<TdOrder> findAllVerifyBelongShopTitleAndTimeAfter(String diysitename, Date time){
    	return repository.findByStatusIdAndShopTitleAndOrderTimeAfterOrStatusIdAndShopTitleAndOrderTimeAfterOrderByIdDesc(5L, diysitename, time, 6L, diysitename, time);
    }
    public Page<TdOrder> findAllVerifyBelongShopTitleTimeAfterOrderByIdDesc(String diysitename, Date time,  int page, int size){
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findByStatusIdAndShopTitleAndOrderTimeAfterOrStatusIdAndShopTitleAndOrderTimeAfterOrderByIdDesc(5L, diysitename, time, 6L, diysitename, time, pageRequest);
    }
    
    /**
	 * @author lc
	 * @注释：订单返利查询
	 */
    public List<TdOrder> findByUsernameIn(List<String> tdUsers){
    	return repository.findByUsernameIn(tdUsers);
    }
    public Page<TdOrder> findByUsernameIn(List<String> tdUsers, int page, int size){
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findByUsernameInOrderByIdDesc(tdUsers, pageRequest);
    }
    
    public List<TdOrder> findByUsernameInAndOrderTimeAfter(List<String> tdUsers, Date time){
    	return repository.findByUsernameInAndOrderTimeAfterOrderByIdDesc(tdUsers, time);
    }
    public Page<TdOrder> findByUsernameInAndOrderTimeAfter(List<String> tdUsers, Date time, int page, int size){
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findByUsernameInAndOrderTimeAfterOrderByIdDesc(tdUsers, time, pageRequest);
    }
    /**
	 * @author lc
	 * @注释：根据订单类型和订单状态进行查询
	 */
    public Page<TdOrder> findByStatusAndTypeOrderByIdDesc(long statusId, long typeId, int page, int size){
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findByStatusIdAndTypeIdOrderByIdDesc(statusId, typeId, pageRequest);
    }
    public List<TdOrder> findByStatusAndTypeIdOrderByIdDesc(long statusId, long typeId){
    	return repository.findByStatusIdAndTypeIdOrderByIdDesc(statusId, typeId);
    }
    public Page<TdOrder> findByStatusOrderByIdDesc(long StatusId, int page, int size){
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findByStatusIdOrderByIdDesc(StatusId, pageRequest);
    }
    public List<TdOrder> findByStatusOrderByIdDesc(long StatusId){
    	return repository.findByStatusIdOrderByIdDesc(StatusId);
    }
    public Page<TdOrder> findBytypeIdOrderByIdDesc(long typeId, int page, int size){
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findBytypeIdOrderByIdDesc(typeId, pageRequest);
    }
    public List<TdOrder> findBytypeIdOrderByIdDesc(long typeId){
    	return repository.findBytypeIdOrderByIdDesc(typeId);
    }
    /**
	 * @author lc
	 * @注释 按时间、订单类型和订单状态查询
	 */
    public Page<TdOrder> findByTimeAfterOrderByIdDesc(Date time, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByOrderTimeAfterOrderByIdDesc(time, pageRequest);
    }
    public List<TdOrder> findByTimeAfterOrderByIdDesc(Date time){
    	return repository.findByOrderTimeAfterOrderByIdDesc(time);
    }
    
    public Page<TdOrder> findByStatusIdAndTypeIdAndTimeAfterOrderByIdDesc(long statusId, long typeId, Date time, int page, int size){
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findByStatusIdAndTypeIdAndOrderTimeAfterOrderByIdDesc(statusId, typeId, time, pageRequest);
    }
    public List<TdOrder> findByStatusAndTypeIdAndTimeAfterOrderByIdDesc(long statusId, long typeId, Date time){
    	return repository.findByStatusIdAndTypeIdAndOrderTimeAfterOrderByIdDesc(statusId, typeId, time);
    }
    public Page<TdOrder> findByStatusAndTimeAfterOrderByIdDesc(long StatusId, Date time, int page, int size){
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findByStatusIdAndOrderTimeAfterOrderByIdDesc(StatusId, time, pageRequest);
    }
    public List<TdOrder> findByStatusAndTimeAfterOrderByIdDesc(long StatusId, Date time){
    	return repository.findByStatusIdAndOrderTimeAfterOrderByIdDesc(StatusId, time);
    }
    public Page<TdOrder> findBytypeIdAndTimeAfterOrderByIdDesc(long typeId, Date time, int page, int size){
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findBytypeIdAndOrderTimeAfterOrderByIdDesc(typeId, time, pageRequest);
    }
    public List<TdOrder> findBytypeIdAndTimeAfterOrderByIdDesc(long typeId, Date time){
    	return repository.findBytypeIdAndOrderTimeAfterOrderByIdDesc(typeId, time);
    }
    /**
     * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
     * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
     * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
     * 
     * 按交易状态查询
     * @author libiao
     */
    public List<TdOrder> findByStatusId(Long statusId){
    	return repository.findByStatusId(statusId);
    }
    
    public List<TdOrder> findAll(Long statusId){
    	return (List<TdOrder>) repository.findAll();
    }
    
    public Page<TdOrder> findByShopIdAndGoodId(Long shopId,Long gid,int page,int size)
    {
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findByShopIdAndGoodsId(shopId, gid,pageRequest);
    }
    
    public Page<TdOrder> findByShopId(Long shopId,int page,int size){
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findByShopIdOrderByIdDesc(shopId, pageRequest);
    }
    public Page<TdOrder> findByUsernameAndTypeIdOrderByIdDesc(String username,long typeId,int page,int size)
    {
    	if(null == username)
    	{
    		return null;
    	}
    	PageRequest pageRequest =new PageRequest(page, size);
    	return repository.findByUsernameAndTypeIdOrderByIdDesc(username, typeId, pageRequest);
    }
    
    public Page<TdOrder> findByShopIdAndTypeId(Long shopId,int typeId,int page,int size)
    {
    	if(null == shopId)
    	{
    		return null;
    	}
    	PageRequest pageRequest=new PageRequest(page, size);
    	return repository.findByShopIdAndTypeIdOrderByOrderTimeDesc(shopId,typeId,pageRequest);
    }
    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓		进   货   单   查   询		↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓			 libiao					↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    public Page<TdOrder> findByUsernameAndTypeIdAndTimeAfter(String username, long typeId,Date time, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByUsernameAndTypeIdAndOrderTimeAfterOrderByIdDesc(username, typeId,time, pageRequest);
    }
    
    public Page<TdOrder> findByUsernameAndTypeIdAndTimeAfterAndSearch(String username,long typeId, Date time, String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByUsernameAndTypeIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(username,typeId, time, keywords, pageRequest);
    }
    
    public Page<TdOrder> findByUsernameAndTypeIdAndSearch(String username, long typeId,String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByUsernameAndTypeIdAndOrderNumberContainingOrderByIdDesc(username,typeId, keywords, pageRequest);
    }
    
    public Page<TdOrder> findByUsernameAndTypeIdAndStatusId(String username,long typeId, long statusId, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByUsernameAndTypeIdAndStatusIdOrderByIdDesc(username,typeId,statusId, pageRequest);
    }
    
    public Page<TdOrder> findByUsernameAndTypeIdAndStatusIdAndSearch(String username,long typeId, long statusId, String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByUsernameAndTypeIdAndStatusIdAndOrderNumberContainingOrderByIdDesc(username, typeId,statusId, keywords, pageRequest);
    }
    
    public Page<TdOrder> findByUsernameAndTypeIdAndStatusIdAndTimeAfter(String username,long typeId, long statusId, Date time, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByUsernameAndTypeIdAndStatusIdAndOrderTimeAfterOrderByIdDesc(username,typeId, statusId, time, pageRequest);
    }
    
    public Page<TdOrder> findByUsernameAndTypeIdAndStatusIdAndTimeAfterAndSearch(String username,long typeId, long statusId, Date time, String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByUsernameAndTypeIdAndStatusIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(username, typeId,statusId, time, keywords, pageRequest);
    }
    
    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓		销   售   单   查   询		↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓			 libiao					↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    public Page<TdOrder> findByShopIdAndTypeIdAndTimeAfter(long shopId, long typeId,Date time, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByShopIdAndTypeIdAndOrderTimeAfterOrderByIdDesc(shopId, typeId,time, pageRequest);
    }
    
    public Page<TdOrder> findByShopIdAndTypeIdAndTimeAfterAndSearch(long ShopId,long typeId, Date time, String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByShopIdAndTypeIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(ShopId,typeId, time, keywords, pageRequest);
    }
    
    public Page<TdOrder> findByShopIdAndTypeIdAndSearch(long ShopId, long typeId,String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByShopIdAndTypeIdAndOrderNumberContainingOrderByIdDesc(ShopId,typeId, keywords, pageRequest);
    }
    
    public Page<TdOrder> findByShopIdAndTypeIdAndStatusId(long ShopId,long typeId, long statusId, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByShopIdAndTypeIdAndStatusIdOrderByIdDesc(ShopId,typeId,statusId, pageRequest);
    }
    
    public Page<TdOrder> findByShopIdAndTypeIdAndStatusIdAndSearch(long ShopId,long typeId, long statusId, String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByShopIdAndTypeIdAndStatusIdAndOrderNumberContainingOrderByIdDesc(ShopId, typeId,statusId, keywords, pageRequest);
    }
    
    public Page<TdOrder> findByShopIdAndTypeIdAndStatusIdAndTimeAfter(long ShopId,long typeId, long statusId, Date time, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByShopIdAndTypeIdAndStatusIdAndOrderTimeAfterOrderByIdDesc(ShopId,typeId, statusId, time, pageRequest);
    }
    
    public Page<TdOrder> findByShopIdAndTypeIdAndStatusIdAndTimeAfterAndSearch(long ShopId,long typeId, long statusId, Date time, String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByShopIdAndTypeIdAndStatusIdAndOrderTimeAfterAndOrderNumberContainingOrderByIdDesc(ShopId, typeId,statusId, time, keywords, pageRequest);
    }
    public Long countByShopIdAndTypeIdAndStatusId(Long shopId,long typeId, long statusId)
    {
    	
        return repository.countByShopIdAndTypeIdAndStatusId(shopId,typeId ,statusId);
    }
}
