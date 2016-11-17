package com.ynyes.cslm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.cslm.entity.TdBank;
import com.ynyes.cslm.repository.TdBankRepo;
import com.ynyes.cslm.util.Criteria;
import com.ynyes.cslm.util.Restrictions;

/**
 * 银行卡服务类
 * @author Max
 *
 */
@Service
@Transactional
public class TdBankService {
	
	@Autowired
	private TdBankRepo repository;
	
	
	public TdBank save(TdBank bank){
		if(null == bank){
			return null;
		}
		return repository.save(bank);
	}
	
	public void delete(Long id){
		if(null != id){
			repository.delete(id);
		}
	}
	
	public TdBank findOne(Long id){
		if(null != id){
			return repository.findOne(id);
		}
		return null;
	}
	
	
	public List<TdBank> findAll(String username,Integer type){
		Criteria<TdBank> c = new Criteria<>();
		
		if(null != username){
			c.add(Restrictions.eq("username", username, true));
		}
		if(null != type){
			c.add(Restrictions.eq("type",type, true));
		}
		
		return repository.findAll(c);
	}

}
