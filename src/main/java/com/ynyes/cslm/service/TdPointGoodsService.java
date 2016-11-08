package com.ynyes.cslm.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ynyes.cslm.entity.TdPointGoods;
import com.ynyes.cslm.repository.TdPointGoodsRepo;
import com.ynyes.cslm.util.Criteria;
import com.ynyes.cslm.util.Restrictions;

/**
 * 
 * @author Max
 * 积分商品服务类
 *
 */
@Service
@Transactional
public class TdPointGoodsService {

	@Autowired
	private TdPointGoodsRepo repository;
	
	
	public TdPointGoods save(TdPointGoods poingGoods){
		if(null != poingGoods){
			if(null == poingGoods.getOnSaleTime()){
				poingGoods.setOnSaleTime(new Date());
			}
			
			return repository.save(poingGoods);
		}
		return null;
	}
	
	public void delete(Long id){
		if(null != id){
			repository.delete(id);
		}
	}
	
	
	public TdPointGoods findOne(Long id){
		if(null != id){
			return repository.findOne(id);
		}
		return null;
	}
	
	public TdPointGoods findOneAndIsEnable(Long id){
		if(null != id){
			return repository.findByIdAndIsEnableTrue(id);
		}
		return null;
	}
	
	public TdPointGoods findByCode(String code){
    	if(null != code){
    		return repository.findByCode(code);
    	}
    	return null;
    }
    
    public TdPointGoods findByCodeAndIdNot(String code,Long id){
    	if(null == code || null == id){
    		return null;
    	}
    	return repository.findByCodeAndAndIdNot(code, id);
    }
	
	public Page<TdPointGoods> findAll(String keywords,Boolean isEnable,PageRequest pageRequest){
		Criteria<TdPointGoods> c = new Criteria<>();
		
		if(null != keywords && !keywords.isEmpty()){
			c.add(Restrictions.or(Restrictions.like("goodsTitle", keywords, true),Restrictions.like("code", keywords, true),Restrictions.like("subGoodsTitle", keywords, true)));
		}
		
		if(null != isEnable){
			c.add(Restrictions.eq("isEnable", isEnable, true));
		}
		return repository.findAll(c, pageRequest);
	}
	
	
}
