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
import com.ynyes.cslm.repository.TdCashRepo;

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

	public Page<TdCash> findByCreateTimeBetween(Date startTime, Date endTime, Integer page, Integer size) 
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
		return repository.findByCreateTimeBetween(startTime, endTime, pageRequest);
	}
	
	public Page<TdCash> findByType(Long type, Integer page, Integer size) 
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
		return repository.findByType(type, pageRequest);
	}
	
	public Page<TdCash> findByTypeAndCreateTimeBetween(Long type,Date startTime, Date endTime, Integer page, Integer size) 
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
		return repository.findByTypeAndCreateTimeBetween(type,startTime, endTime, pageRequest);
	}
	
	public Page<TdCash> findByShopType(Long shopType,int page, int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
		return repository.findByShopType(shopType, pageRequest);
	}
	
	public Page<TdCash> findByShopTypeAndCreateTimeBetween(Long shopType,Date startTime, Date endTime, Integer page, Integer size) 
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
		return repository.findByShopTypeAndCreateTimeBetween(shopType, startTime, endTime, pageRequest);
	}
	
	public Page<TdCash> findByShopTypeAndType(Long shopType,Long type, Integer page, Integer size) 
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
		return repository.findByShopTypeAndType(shopType, type, pageRequest);
	}
	
	public Page<TdCash> findByShopTypeAndTypeAndCreateTimeBetween(Long shopType,Long type,Date startTime, Date endTime, Integer page, Integer size) 
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
		return repository.findByShopTypeAndTypeAndCreateTimeBetween(shopType,type,startTime, endTime, pageRequest);
	}
	
	
}
