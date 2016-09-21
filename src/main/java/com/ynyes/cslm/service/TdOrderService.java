package com.ynyes.cslm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.cslm.entity.TdCountSale;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.entity.TdOrderGoods;
import com.ynyes.cslm.entity.TdPayRecord;
import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdProviderGoods;
import com.ynyes.cslm.entity.TdSetting;
import com.ynyes.cslm.repository.TdOrderRepo;
import com.ynyes.cslm.util.Criteria;
import com.ynyes.cslm.util.Restrictions;

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
    
    @Autowired
    TdProviderGoodsService tdProviderGoodsService;
    
    @Autowired
    TdDistributorGoodsService tdDistributorGoodsService;
    
    @Autowired
    TdCountSaleService tdCountSaleService;
    
    @Autowired
    TdDistributorService tdDistributorService;
    
    @Autowired
    TdProviderService tdProviderService;
    
    @Autowired
    TdSettingService tdSettingService;
    
    @Autowired
    TdPayRecordService tdPayRecordService;
    
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
    
    public Long countByUsernameAndTypeIdAndStatusId(String username, long statusId)
    {
        return repository.countByUsernameAndStatusId(username,statusId);
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
    
    public Page<TdOrder> findByShopIdAndTypeId(Long shopId,Long typeId,int page,int size){
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findByShopIdAndTypeIdOrderByIdDesc(shopId,typeId, pageRequest);
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
    
    public Page<TdOrder> findByProviderIdAndTypeId(Long shopId,int typeId,int page,int size)
    {
    	if(null == shopId)
    	{
    		return null;
    	}
    	PageRequest pageRequest=new PageRequest(page, size);
    	return repository.findByProviderIdAndTypeIdOrderByOrderTimeDesc(shopId,typeId,pageRequest);
    }
    
    public Long countByShopIdAndTypeIdAndStatusId(Long shopId,long typeId, long statusId)
    {
    	
        return repository.countByShopIdAndTypeIdAndStatusId(shopId,typeId ,statusId);
    }
    
    public Long countByProviderIdAndTypeIdAndStatusId(Long providerId,long typeId, long statusId)
    {
    	
        return repository.countByProviderIdAndTypeIdAndStatusId(providerId,typeId ,statusId);
    }
    
    public Page<TdOrder> findAll(Long shopId,int statusId,int typeId,Date startTime,Date endTime,int page,int size){
    	PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "orderTime"));
    	Criteria<TdOrder> c = new Criteria<>();
    	
    	if(null != shopId){
    		c.add(Restrictions.eq("shopId", shopId, true));
    	}
    	
    	if(statusId != 0 ){
    		c.add(Restrictions.eq("statusId", statusId, true));
    	}
    	
    	if(null != startTime){
    		c.add(Restrictions.gte("orderTime", startTime, true));
    	}
    	
    	if(null != endTime){
    		c.add(Restrictions.lte("orderTime", endTime, true));
    	}
    	c.add(Restrictions.eq("typeId", typeId, true));
    	
    	return repository.findAll(c,pageRequest);
    }
    
    public Page<TdOrder> findAll(Long statusId,Long payId,Date startTime,Date endTime,String keywords,int page,int size){
    	PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "orderTime"));
    	Criteria<TdOrder> c = new Criteria<>();
    	
    	
    	if(statusId != 0 ){
    		c.add(Restrictions.eq("statusId", statusId, true));
    	}
    	
    	if(payId != null ){
    		c.add(Restrictions.eq("payTypeId", payId, true));
    	}
    	
    	if(null != startTime){
    		c.add(Restrictions.gte("orderTime", startTime, true));
    	}
    	
    	if(null != endTime){
    		c.add(Restrictions.lte("orderTime", endTime, true));
    	}
    	if(null != keywords && !keywords.isEmpty()){
    		c.add(Restrictions.or(Restrictions.like("orderNumber", keywords, true),Restrictions.like("username", keywords, true)));
    	}
    	
    	return repository.findAll(c,pageRequest);
    }
    
    public Page<TdOrder> findAll(String username,int statusId,int typeId,Date startTime,Date endTime,int page,int size){
    	PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "orderTime"));
    	Criteria<TdOrder> c = new Criteria<>();
    	
    	if(null != username){
    		c.add(Restrictions.eq("username", username, true));
    	}
    	
    	if(statusId != 0 ){
    		c.add(Restrictions.eq("statusId", statusId, true));
    	}
    	
    	if(null != startTime){
    		c.add(Restrictions.gte("orderTime", startTime, true));
    	}
    	
    	if(null != endTime){
    		c.add(Restrictions.lte("orderTime", endTime, true));
    	}
    	c.add(Restrictions.eq("typeId", typeId, true));
    	
    	return repository.findAll(c,pageRequest);
    }
    
    public Page<TdOrder> findByProviderId(Long providerId,int statusId,int typeId,Date startTime,Date endTime,int page,int size){
    	PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "orderTime"));
    	Criteria<TdOrder> c = new Criteria<>();
    	
    	if(null != providerId){
    		c.add(Restrictions.eq("providerId", providerId, true));
    	}
    	
    	if(statusId != 0 ){
    		c.add(Restrictions.eq("statusId", statusId, true));
    	}
    	
    	if(null != startTime){
    		c.add(Restrictions.gte("orderTime", startTime, true));
    	}
    	
    	if(null != endTime){
    		c.add(Restrictions.lte("orderTime", endTime, true));
    	}
    	c.add(Restrictions.eq("typeId", typeId, true));
    	
    	return repository.findAll(c,pageRequest);
    }
    
    // 关键字筛选
    public Page<TdOrder> searchAll(String keywords,int page,int size)
    {
    	PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
    	return repository.findByOrderNumberContainingOrShippingNameContainingOrShopTitleContainingOrProviderTitleContaining(keywords, keywords, keywords, keywords, pageRequest);
    }
    
    public Page<TdOrder> searchAndtypeIdOrderByIdDesc(String keywords,long typeId, int page, int size){
    	PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
    	return repository.findByOrderNumberContainingAndTypeIdOrShippingNameContainingAndTypeIdOrShopTitleContainingAndTypeIdOrProviderTitleContainingAndTypeId(keywords, typeId, keywords, typeId, keywords, typeId, keywords, typeId, pageRequest);
    }
    
    public Page<TdOrder> searchAndStatusId(String keywords,Long statusId,int page,int size)
    {
    	PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
    	return repository.findByOrderNumberContainingAndStatusIdOrShippingNameContainingAndStatusIdOrShopTitleContainingAndStatusIdOrProviderTitleContainingAndStatusId(keywords, statusId, keywords, statusId, keywords, statusId, keywords, statusId, pageRequest);
    }
    
    public Page<TdOrder> searchAndStatusIdAndTypeId(String keywords,Long statusId,Long typeId,int page,int size)
    {
    	PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
    	return repository.findByOrderNumberContainingAndStatusIdAndTypeIdOrShippingNameContainingAndStatusIdAndTypeIdOrShopTitleContainingAndStatusIdAndTypeIdOrProviderTitleContainingAndStatusIdAndTypeId(
    																						keywords, statusId, typeId, 
    																						keywords, statusId, typeId, 
    																						keywords, statusId, typeId, 
    																						keywords, statusId, typeId, pageRequest);
    }
    
    public Page<TdOrder> searchAndTimeAfter(String keywords,Date time, int page, int size)
    {
    	PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
        return repository.findByOrderNumberContainingAndOrderTimeAfterOrShippingNameContainingAndOrderTimeAfterOrShopTitleContainingAndOrderTimeAfterOrProviderTitleContainingAndOrderTimeAfter(keywords, time, keywords, time, keywords, time, keywords, time, pageRequest);
    }
    
    public Page<TdOrder> searchAndTypeIdAndOrderTimeAfter(String keywords,Long typeId,Date time,int page,int size)
    {
    	PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
    	return repository.findByOrderNumberContainingAndTypeIdAndOrderTimeAfterOrShippingNameContainingAndTypeIdAndOrderTimeAfterOrShopTitleContainingAndTypeIdAndOrderTimeAfterOrProviderTitleContainingAndTypeIdAndOrderTimeAfter(
    																						keywords, typeId, time, 
    																						keywords, typeId, time, 
    																						keywords, typeId, time, 
    																						keywords, typeId, time, pageRequest);
    }
    
    public Page<TdOrder> searchAndStatusIdAndOrderTimeAfter(String keywords,Long statusId,Date time,int page,int size)
    {
    	PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
    	return repository.findByOrderNumberContainingAndStatusIdAndOrderTimeAfterOrShippingNameContainingAndStatusIdAndOrderTimeAfterOrShopTitleContainingAndStatusIdAndOrderTimeAfterOrProviderTitleContainingAndStatusIdAndOrderTimeAfter(
    																						keywords, statusId, time, 
    																						keywords, statusId, time, 
    																						keywords, statusId, time, 
    																						keywords, statusId, time, pageRequest);
    }
    
    public Page<TdOrder> searchAndStatusIdAndTypeIdAndOrderTimeAfter(String keywords,Long statusId,Long typeId,Date time,int page,int size)
    {
    	PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
    	return repository.findByOrderNumberContainingAndStatusIdAndTypeIdAndOrderTimeAfterOrShippingNameContainingAndStatusIdAndTypeIdAndOrderTimeAfterOrShopTitleContainingAndStatusIdAndTypeIdAndOrderTimeAfterOrProviderTitleContainingAndStatusIdAndTypeIdAndOrderTimeAfter(
    																						keywords, statusId, typeId, time, 
    																						keywords, statusId, typeId, time, 
    																						keywords, statusId, typeId, time, 
    																						keywords, statusId, typeId, time, pageRequest);
    }
    
    public List<TdOrder> searchOrderGoods(Long shopId,Long providerId, String type,Long statusId, Date begin, Date end
    		//,int page,
			//int size
    		) {
	//	PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "orderTime"));
		Criteria<TdOrder> c = new Criteria<>();
		if(null != shopId)
		{
			c.add(Restrictions.eq("shopId", shopId, true));
		}
		if(null != providerId)
		{
			c.add(Restrictions.eq("providerId", providerId, true));
		}
		if(null != type)
		{
			if("dis".equals(type)){
				c.add(Restrictions.or(Restrictions.eq("typeId", 0L, true),Restrictions.eq("typeId", 2L, true)));
			}else if("pro".equals(type)){
				Restrictions.eq("typeId", 1L, true);
			}else if("supply".equals(type)){
				Restrictions.eq("typeId", 2, true);
			}
		}
		if(null != statusId && statusId != 0){
			c.add(Restrictions.eq("statusId", statusId, true));
		}
		if (begin != null) {
			c.add(Restrictions.gte("orderTime", begin, true));
		}
		if (end != null) {
			c.add(Restrictions.lte("orderTime", end, true));
		}
		return repository.findAll(c);
	}
    
    
    public List<TdCountSale> sumOrderGoods(Long shopId,Long saleType,List<TdOrder> orderList)
    {
    	// 删除之前数据
    	tdCountSaleService.delete(shopId, saleType);
    	
    	Map<Long,Long> goodsMap = new HashMap<>();
    	Map<Long,Double> priceMap = new HashMap<>();
    	if(null != orderList && orderList.size()>0)
    	{
    		for (TdOrder tdOrder : orderList) {
				List<TdOrderGoods> goodsList = tdOrder.getOrderGoodsList();
				if(null != goodsList && goodsList.size()>0)
				{
					for (TdOrderGoods tdOrderGoods : goodsList) {
						if(goodsMap.containsKey(tdOrderGoods.getGoodsId())) // 判断Map是否存有当前商品
						{
							long quantity = Long.parseLong(goodsMap.get(tdOrderGoods.getGoodsId())+"");  
							long oldQuantity = Long.parseLong(tdOrderGoods.getQuantity()+"");
							Double price = priceMap.get(tdOrderGoods.getGoodsId());
							Double newPrice  = tdOrderGoods.getPrice()*oldQuantity;
							
							goodsMap.put(tdOrderGoods.getGoodsId(), quantity+oldQuantity); // 如map存有商品，数量相加
							priceMap.put(tdOrderGoods.getGoodsId(), price+newPrice);
						}else{
							goodsMap.put(tdOrderGoods.getGoodsId(), tdOrderGoods.getQuantity()); // 如没有，存入商品ID及数量
							priceMap.put(tdOrderGoods.getGoodsId(), tdOrderGoods.getQuantity()*tdOrderGoods.getPrice());
						}
					}
				}
			}
    	}
    	
    	List<TdCountSale> saleList = new ArrayList<>();
    
    	if(goodsMap.size()>0)
    	{
    		for (long key : goodsMap.keySet()) {
    			if(saleType ==0L || saleType==2)
    			{
    				TdDistributorGoods goods = tdDistributorGoodsService.findOne(key);
    				if(null != goods)
    				{
    					TdCountSale countSale = new TdCountSale();
    					countSale.setGoodsTitle(goods.getGoodsTitle());
    					countSale.setSubTitle(goods.getSubGoodsTitle());
    					countSale.setGoodsCode(goods.getCode());
    					countSale.setPrice(goods.getGoodsPrice());
    					countSale.setQuantity(goodsMap.get(key));
    					countSale.setTotalPrice(priceMap.get(key));
    					countSale.setShipId(shopId);
    					countSale.setSaleType(saleType);
    					saleList.add(countSale);
    				}
    			}else{
    				TdProviderGoods goods = tdProviderGoodsService.findByProviderIdAndGoodsId(shopId, key);
    				if(null != goods)
    				{
    					TdCountSale countSale = new TdCountSale();
    					countSale.setGoodsTitle(goods.getGoodsTitle());
    					countSale.setSubTitle(goods.getSubGoodsTitle());
    					countSale.setGoodsCode(goods.getCode());
    					countSale.setPrice(goods.getOutFactoryPrice());
    					countSale.setQuantity(goodsMap.get(key));
    					countSale.setTotalPrice(priceMap.get(key));
    					countSale.setShipId(shopId);
    					countSale.setSaleType(saleType);
    					saleList.add(countSale);
    				}
    			}
    		}
    	}
    	
    	tdCountSaleService.save(saleList);
    	
    	return tdCountSaleService.findByShipIdAndTypeId(shopId, saleType);
    	
    }
    
    public void addVir(TdOrder tdOrder) {
		Double price = 0.0; // 交易总金额
		Double postPrice = 0.0; // 物流费
		Double aliPrice = 0.0; // 第三方使用费
		Double servicePrice = 0.0; // 平台服务费
		Double totalGoodsPrice = 0.0; // 商品总额
		Double realPrice = 0.0; // 商家实际收入
		Double turnPrice = 0.0; // 分销单超市返利

		price += tdOrder.getTotalPrice();
		postPrice += tdOrder.getPostPrice();
		aliPrice += tdOrder.getAliPrice();
		servicePrice += tdOrder.getTrainService();
		totalGoodsPrice += tdOrder.getTotalGoodsPrice();

		// 添加商家余额及交易记录
		if (0 == tdOrder.getTypeId()) {

			TdDistributor distributor = tdDistributorService.findOne(tdOrder.getShopId());
			if (null != distributor) {
				// 超市普通销售单实际收入： 交易总额-第三方使用费-平台服务费=实际收入
				realPrice += price - aliPrice - servicePrice;

				distributor.setVirtualMoney(distributor.getVirtualMoney() + realPrice);
				tdDistributorService.save(distributor);

				TdPayRecord record = new TdPayRecord();
				record.setCont("订单销售款");
				record.setCreateTime(new Date());
				record.setDistributorId(distributor.getId());
				record.setDistributorTitle(distributor.getTitle());
				record.setOrderId(tdOrder.getId());
				record.setOrderNumber(tdOrder.getOrderNumber());
				record.setStatusCode(1);
				record.setProvice(price); // 交易总额
				record.setTotalGoodsPrice(totalGoodsPrice); // 商品总额
				record.setPostPrice(postPrice); // 邮费
				record.setAliPrice(aliPrice); // 第三方使用费
				record.setServicePrice(servicePrice); // 平台服务费
				record.setRealPrice(realPrice); // 实际收入

				tdPayRecordService.save(record);
			}
		} else if (2 == tdOrder.getTypeId()) {
			TdDistributor distributor = tdDistributorService.findOne(tdOrder.getShopId());
			TdProvider provider = tdProviderService.findOne(tdOrder.getProviderId());

			turnPrice = tdOrder.getTotalLeftPrice();
			if (null != distributor) {

				distributor.setVirtualMoney(distributor.getVirtualMoney() + turnPrice); // 超市分销单收入为分销返利额
				tdDistributorService.save(distributor);

				TdPayRecord record = new TdPayRecord();
				record.setCont("代售获利");
				record.setCreateTime(new Date());
				record.setDistributorId(distributor.getId());
				record.setDistributorTitle(distributor.getTitle());
				record.setOrderId(tdOrder.getId());
				record.setOrderNumber(tdOrder.getOrderNumber());
				record.setStatusCode(1);
				record.setProvice(price); // 订单总额
				record.setTurnPrice(turnPrice); // 超市返利
				record.setRealPrice(turnPrice); // 超市实际收入
				tdPayRecordService.save(record);
			}
			if (null != provider) {
				// 分销商实际收入：商品总额-第三方使用费-邮费-超市返利-平台费
				realPrice += price - aliPrice - postPrice - turnPrice - servicePrice;

				provider.setVirtualMoney(provider.getVirtualMoney() + realPrice);

				TdPayRecord record = new TdPayRecord();
				record.setCont("分销收款");
				record.setCreateTime(new Date());
				record.setDistributorTitle(distributor.getTitle());
				record.setProviderId(provider.getId());
				record.setProviderTitle(provider.getTitle());
				record.setOrderId(tdOrder.getId());
				record.setOrderNumber(tdOrder.getOrderNumber());
				record.setStatusCode(1);

				record.setProvice(price); // 订单总额
				record.setPostPrice(postPrice); // 邮费
				record.setAliPrice(aliPrice); // 第三方费
				record.setServicePrice(servicePrice); // 平台费
				record.setTotalGoodsPrice(totalGoodsPrice); // 商品总价
				record.setTurnPrice(turnPrice); // 超市返利
				record.setRealPrice(realPrice); // 实际获利
				tdPayRecordService.save(record);
			}
		}

		TdSetting setting = tdSettingService.findTopBy();
		if (null != setting.getVirtualMoney()) {
			setting.setVirtualMoney(setting.getVirtualMoney() + servicePrice + aliPrice);
		} else {
			setting.setVirtualMoney(servicePrice + aliPrice);
		}
		tdSettingService.save(setting); // 更新平台虚拟余额

		// 记录平台收益
		TdPayRecord record = new TdPayRecord();
		record.setCont("商家销售抽取");
		record.setCreateTime(new Date());
		record.setDistributorTitle(tdOrder.getShopTitle());
		record.setOrderId(tdOrder.getId());
		record.setOrderNumber(tdOrder.getOrderNumber());
		record.setStatusCode(1);
		record.setType(1L); // 类型 区分平台记录

		record.setProvice(price); // 订单总额
		record.setPostPrice(postPrice); // 邮费
		record.setAliPrice(aliPrice); // 第三方费
		record.setServicePrice(servicePrice); // 平台费
		record.setTotalGoodsPrice(totalGoodsPrice); // 商品总价
		record.setTurnPrice(turnPrice); // 超市返利
		// 实际获利 =平台服务费+第三方费
		record.setRealPrice(servicePrice + aliPrice);

		tdPayRecordService.save(record);
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
}
