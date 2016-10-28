package com.ynyes.cslm.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynyes.cslm.entity.TdRelevance;
import com.ynyes.cslm.repository.TdRelevanceRepo;
import com.ynyes.cslm.util.Criteria;
import com.ynyes.cslm.util.Restrictions;

/**
 * 商品关联服务类
 * @author Max
 *
 */
@Service
@Transactional
public class TdRelevanceService {

	@Autowired
	private TdRelevanceRepo repository;
	
	
	public TdRelevance save(TdRelevance relevance){
		if(null != relevance){
			return repository.save(relevance);
		}
		return null;
	}
	
	public TdRelevance findOne(Long id){
		if(null != id){
			return repository.findOne(id);
		}
		return null;
	}
	
	public void delete(Long id){
		if(null != id){
			repository.delete(id);
		}
	}
	
	public void delete(TdRelevance e){
		if(null != e){
			repository.delete(e);
		}
	}
	
	public void delete(List<TdRelevance> reList){
		if(null != reList){
			repository.delete(reList);
		}
	}
	
	public List<TdRelevance> findAll(Long goodsId1,Long goodsId2){
		Criteria<TdRelevance> c = new Criteria<TdRelevance>();
		
		if(null != goodsId1){
			c.add(Restrictions.eq("goodsId1", goodsId1, true));
		}
		if(null != goodsId2){
			c.add(Restrictions.eq("goodsId2", goodsId2, true));
		}
		
		return repository.findAll(c);
	}
	
	
	
	
	
	
}
