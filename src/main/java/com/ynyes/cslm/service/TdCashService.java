package com.ynyes.cslm.service;

import org.springframework.beans.factory.annotation.Autowired;
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
		if(null != id){
			return null;
		}
		return repository.findOne(id);
	}
	
	public TdCash findByCashNumber(String cashNumber)
	{
		if(null == cashNumber)
		{
			return null;
		}
		return repository.findByCashNumber(cashNumber);
	}
	
}
