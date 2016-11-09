package com.ynyes.cslm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.cslm.entity.TdCartGoods;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdGoods;
import com.ynyes.cslm.entity.TdSpecificat;
import com.ynyes.cslm.repository.TdCartGoodsRepo;

/**
 * TdCartGoods 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdCartGoodsService {
    
    @Autowired
    TdCartGoodsRepo repository;
    
    @Autowired
    TdGoodsService tdGoodsService;
    
    @Autowired
    TdDistributorService tdDistributorService;
    
    @Autowired
    TdSpecificatService tdSpecificatService;
    
    @Autowired
    TdDistributorGoodsService tdDistributorGoodsService;
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
    public void delete(TdCartGoods e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdCartGoods> entities)
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
    public TdCartGoods findOne(Long id)
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
    public List<TdCartGoods> findAll(Iterable<Long> ids)
    {
        return (List<TdCartGoods>) repository.findAll(ids);
    }
    
    public List<TdCartGoods> findAll()
    {
        Sort sort = new Sort(Direction.DESC, "id");
        return (List<TdCartGoods>) repository.findAll(sort);
    }
    
    public Page<TdCartGoods> findAllOrderByIdDesc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "id"));
        
        return repository.findAll(pageRequest);
    }
    
    public TdCartGoods findTopByGoodsIdAndUsername(long goodsId, String username)
    {
        if (null == username)
        {
            return null;
        }
        
        return repository.findTopByGoodsIdAndUsername(goodsId, username);
    }
    
    public List<TdCartGoods> findByGoodsIdAndUsername(Long goodsId, String username)
    {
        if (null == goodsId || null == username)
        {
            return null;
        }
        
        return repository.findByDistributorGoodsIdAndUsername(goodsId, username);
    }
    
    public List<TdCartGoods> findByGoodsIdAndUsernameAndProviderIdAndSpecificaId(Long goodsId, 
    			String username,Long providerId,Long specId)
    {
        if (null == goodsId || null == username || null == providerId)
        {
            return null;
        }
        if(null != specId){
        	return repository.findByGoodsIdAndUsernameAndProviderIdAndSpecificaId(goodsId, username, providerId, specId);
        }
        return repository.findByGoodsIdAndUsernameAndProviderId(goodsId, username,providerId);
    }
    
    public List<TdCartGoods> findByUsername(String username)
    {
    	if(null == username){
    		return null;
    	}
        return repository.findByUsernameOrderByIdDesc(username);
    }
    
    public List<TdCartGoods> updateGoodsInfo(List<TdCartGoods> cartGoodsList)
    {
        if (null == cartGoodsList || cartGoodsList.size() < 0)
        {
            return null;
        }
        
        for (TdCartGoods cartGoods : cartGoodsList)
        {
            if (null != cartGoods)
            {
            	TdDistributorGoods goods = tdDistributorGoodsService.findByIdAndIsInSaleTrue(cartGoods.getDistributorGoodsId());
                
                if (null != goods)
                {
                    cartGoods.setGoodsCoverImageUri(goods.getCoverImageUri());
                    cartGoods.setGoodsTitle(goods.getGoodsTitle());
                    cartGoods.setUnit(goods.getUnit());
                    cartGoods.setPrice(goods.getGoodsPrice());
                    if(null != cartGoods.getSpecificaId()){
                    	TdSpecificat specificat = tdSpecificatService.findOne(cartGoods.getSpecificaId());
                    	if(null != specificat){
                    		cartGoods.setSpecName(specificat.getSpecifict());
                    	}
                    }
                }
            }
        }
        return (List<TdCartGoods>) repository.save(cartGoodsList);
    }
    
    public List<TdCartGoods> findByUsernameAndIsSelectedTrue(String username)
    {
        return repository.findByUsernameAndIsSelectedTrueOrderByIdDesc(username);
    }
    
    public void delete(Iterable<TdCartGoods> entities)
    {
        repository.delete(entities);
    }
    
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdCartGoods save(TdCartGoods e)
    {
        
        return repository.save(e);
    }
    
    public List<TdCartGoods> save(List<TdCartGoods> entities)
    {
        
        return (List<TdCartGoods>) repository.save(entities);
    }
    
    public List<Long> countByDistributorId(String username)
    {
    	return repository.findByUsernameAndIsSelectedTrue(username);
    }
    
    public List<TdCartGoods> findByUsernameAndDistributorGoodsIdAndSpecificaId(String username,Long dis_goodsId,Long specId){
    	if(null == username ||  null == dis_goodsId){
    		return null;
    	}
    	if(null == specId){
    		return repository.findByUsernameAndDistributorGoodsId(username, dis_goodsId);
    	}
    	return repository.findByUsernameAndDistributorGoodsIdAndSpecificaId(username, dis_goodsId, specId);
    }
    
}
