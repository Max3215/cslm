package com.ynyes.cslm.service;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.repository.TdDistributorGoodsRepo;
import com.ynyes.cslm.repository.TdDistributorRepo;

/**
 * TdDistGoods 服务类
 * 
 * @author libiao
 *
 */
@Service
@Transactional
public class TdDistributorGoodsService {
	
	@Autowired
	TdDistributorGoodsRepo repository;
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(Long id)
	{
	     if (null != id)
	     {
	          repository.delete(id);
	     }
	}
	
	public void delete(TdDistributorGoods e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdDistributorGoods> entities)
    {
        if (null != entities)
        {
            repository.delete(entities);
        }
    }
	
    /**
     * 
     * 查找
     * 
     */
    
    public List<TdDistributorGoods> findByGoodsId(Long goodsId)
    {
    	if(null == goodsId){
    		return null;
    	}
    	return repository.findByGoodsId(goodsId);
    }
    
    /**
     * 
     * 保存
     * 
     */
    public TdDistributorGoods save(TdDistributorGoods e)
    {
    	return repository.save(e);
    }
    
    public List<TdDistributorGoods> save(List<TdDistributorGoods> entities)
    {
    	return (List<TdDistributorGoods>) repository.save(entities);
    }
	
    
    public Page<TdDistributorGoods> findByUsenameAndIsOnSale(String username,Boolean onsale ,int page ,int size)
	{
		if(null == username)
		{
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByUsernameAndIsOnSale(username,onsale, pageRequest);
	}
    
//    public Page<TdDistributorGoods> findByDistributorIdAndIsOnSale(Long disId,Boolean onsale ,int page ,int size)
//	{
//		PageRequest pageRequest = new PageRequest(page, size);
//		return repository.findByDistributorIdAndIsOnSale(disId,onsale,pageRequest);
//	}
    
    public TdDistributorGoods findOne(Long id)
    {
    	return repository.findOne(id);
    }
    
    public TdDistributorGoods findByDistributorIdAndGoodsIdAndIsOnSale(Long distributorId,Long goodsId, Boolean isOnSale)
    {
    	return repository.findByDistributorIdAndGoodsIdAndIsOnSale(distributorId,goodsId, isOnSale);
    }
    
    public List<TdDistributorGoods> findTop12ByDistributorIdAndIsOnSaleTrueBySoldNumberDesc(Long disId)
    {
    	if(null == disId)
    	{
    		return null ;
    	}
    	return repository.findTop12ByDistributorIdAndIsOnSaleTrueOrderBySoldNumberDesc(disId);
    }
    
}
