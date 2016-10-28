package com.ynyes.cslm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.cslm.entity.TdPayRecord;
import com.ynyes.cslm.repository.TdPayRecordRepo;
import com.ynyes.cslm.util.Criteria;
import com.ynyes.cslm.util.Restrictions;

/**
 * 订单支付记录操作服务类
 *
 */
@Service
@Transactional
public class TdPayRecordService {
    @Autowired
    TdPayRecordRepo repository;
    
    /*
     * 取得支付记录
     */
    public TdPayRecord getById(Long id) {
        return repository.findOne(id);
    }
    
    /*
     * 保存支付记录
     */
    public TdPayRecord save(TdPayRecord record) {
        return repository.save(record);
    }
    
    /*
     * 保存支付记录
     */
    public List<TdPayRecord> save(List<TdPayRecord> records) {
        List<TdPayRecord> savedRecords = new ArrayList<TdPayRecord>();
        for(Iterator<TdPayRecord> datas = repository.save(records).iterator(); 
                datas.hasNext();) {
            savedRecords.add(datas.next());
        }
        return savedRecords;
    }
    
    /**
     * 取得订单相关的所有支付记录
     * @param orderId订单Id
     * @return
     */
    public List<TdPayRecord> getAllByOrderId(Long orderId) {
        return repository.findByOrderIdOrderByCreateTimeDesc(orderId);
    }
    
    /**
     * 取得订单的最后一次支付记录
     * @param orderId订单Id
     * @return
     */
    public TdPayRecord getByOrderId(Long orderId) {
        List<TdPayRecord> payRecords = repository.findByOrderIdOrderByCreateTimeDesc(orderId);
        if(payRecords.isEmpty()) {
            return null;
        } else {
            return payRecords.get(0);
        }
    }
    
    public Page<TdPayRecord> findByDistributorId(Long disId,int page,int size){
    	PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC,"createTime"));
    	return repository.findByDistributorIdOrderByCreateTimeDesc(disId, pageRequest);
    }
    
    public List<TdPayRecord> findByDistributorId(Long disId){
    	return repository.findByDistributorIdOrderByCreateTimeDesc(disId);
    }
    
    public Page<TdPayRecord> searchByDistributorId(Long disId,String cont,int page,int size){
    	PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC,"createTime"));
    	return repository.findByDistributorIdAndContContainingOrderByCreateTimeDesc(disId, cont, pageRequest);
    	
    }
    
    
    public Page<TdPayRecord> findByProviderId(Long proId,int page,int size){
    	PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC,"createTime"));
    	return repository.findByProviderIdOrderByCreateTimeDesc(proId, pageRequest);
    }
    
    public List<TdPayRecord> findByProviderId(Long proId){
    	return repository.findByProviderIdOrderByCreateTimeDesc(proId);
    }
    
    public Page<TdPayRecord> searchByProviderId(Long proId,String cont,int page,int size){
    	PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC,"createTime"));
    	return repository.findByProviderIdAndContContainingOrderByCreateTimeDesc(proId, cont, pageRequest);
    }
    
    public Page<TdPayRecord> findByUsername(String username,Long type,int page,int size)
    {
    	if(null == username || null == type)
    	{
    		return null ;
    	}
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findByUsernameAndTypeOrderByCreateTimeDesc(username,type, pageRequest);
    }
    
    public Page<TdPayRecord> findByType(Long type,int page,int size)
    {
    	PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC,"createTime"));
    	return repository.findByType(type, pageRequest);
    }
    
    public void delete(Long id)
    {
    	if(null != id)
    	{
    		repository.delete(id);
    	}
    }
    
    public Page<TdPayRecord> findAll(Long typy,Date startTime,Date endTime,String cont,int page,int size){
    	
    	PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC,"createTime"));
    	Criteria<TdPayRecord> c = new Criteria<>();
    	
    	if(null != typy){
    		c.add(Restrictions.eq("type", typy, true));
    	}
    	
    	if(null != startTime){
    		c.add(Restrictions.gte("createTime", startTime, true));
    	}
    	
    	if(null != endTime){
    		c.add(Restrictions.lte("createTime", endTime, true));
    	}
    	if(null != cont && !cont.isEmpty()){
    		c.add(Restrictions.like("cont", cont, true));
    	}
    	
    	return repository.findAll(c, pageRequest);
    }
    
    public Page<TdPayRecord> findAll(String selectType,Long shipId,String count,Date startTime,Date endTime,int page,int size)
    {
    	PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC,"createTime"));
    	Criteria<TdPayRecord> c = new Criteria<>();
    	if(null != selectType){
    		if("dis".equalsIgnoreCase(selectType)){
    			c.add(Restrictions.eq("distributorId", shipId, true));
    		}else if("pro".equalsIgnoreCase(selectType)){
    			c.add(Restrictions.eq("providerId", shipId, true));
    		}
    	}
    	if(null != count && !"".equalsIgnoreCase(count)){
    		c.add(Restrictions.like("cont", count, true));
    	}
    	
    	if(null != startTime){
    		c.add(Restrictions.gte("createTime", startTime, true));
    	}
    	
    	if(null != endTime){
    		c.add(Restrictions.lte("createTime", endTime, true));
    	}
    	
    	return repository.findAll(c, pageRequest);
    }
    
}
