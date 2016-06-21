package com.ynyes.cslm.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.cslm.entity.TdCash;
import com.ynyes.cslm.entity.TdOrder;
import com.ynyes.cslm.repository.TdCashRepo;
import com.ynyes.cslm.util.Criteria;
import com.ynyes.cslm.util.Restrictions;

/**
 * 
 * @author Max
 * 充值提现服务类
 *
 */
@Service
@Transactional
public class TdCashService {

	@Autowired
	private TdCashRepo repository;
	
	
	
	public TdCash save(TdCash e){
		if(null == e){
			return null;
		}
		return repository.save(e);
	}
	
	public TdCash findOne(Long id)
	{
		if(null == id){
			return null;
		}
		return repository.findOne(id);
	}
	
	public void delete(Long id)
	{
		if(null != id)
		{
			repository.delete(id);
		}
	}
	
	public TdCash findByCashNumber(String cashNumber)
	{
		if(null == cashNumber)
		{
			return null;
		}
		return repository.findByCashNumber(cashNumber);
	}
	
	public Page<TdCash> findAll(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
		
		return repository.findAll(pageRequest);
	}
	
	public Page<TdCash> findAll(Long shopType,Long type,Date startTime,Date endTime,int page,int size){
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "createTime"));
    	Criteria<TdCash> c = new Criteria<>();
    	
    	if(null != shopType)
    	{
    		c.add(Restrictions.eq("shopType", shopType, true));
    	}
    	if(null != type)
    	{
    		c.add(Restrictions.eq("type", type, true));
    	}
    	
    	if(null != startTime){
    		c.add(Restrictions.gte("createTime", startTime, true));
    	}
    	
    	if(null != endTime){
    		c.add(Restrictions.lte("createTime", endTime, true));
    	}
		
    	return repository.findAll(c,pageRequest);
	}

	
	
}
