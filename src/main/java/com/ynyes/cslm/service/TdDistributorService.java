package com.ynyes.cslm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.repository.TdDistributorRepo;

/**
 * TdDistributor 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdDistributorService {
    @Autowired
    TdDistributorRepo repository;
    
    @Autowired
    TdUserCommentService TdUserCommentService;
    
    @Autowired
    TdUserService tdUserService;
    
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
        	// 删除超市所有商品
        	TdDistributor distributor = this.findOne(id);
        	if(null != distributor){
        		List<TdDistributorGoods> goodsList = distributor.getGoodsList();
        		tdDistributorGoodsService.delete(goodsList);
        	}
            repository.delete(id);
        }
    }
    
    /**
     * 删除
     * 
     * @param e 菜单项
     */
    public void delete(TdDistributor e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdDistributor> entities)
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
    public TdDistributor findOne(Long id)
    {
        if (null == id)
        {
            return null;
        }
        
        return repository.findByIdAndIsEnableTrue(id);
    }
    
    public TdDistributor findById(Long id)
    {
    	if(null == id){
    		return null;
    	}
    	return repository.findOne(id);
    }
    
    public List<TdDistributor> findBydisctrict(String disctrict){
    	return repository.findByDisctrictAndIsEnableTrue(disctrict);
    }
    
    public TdDistributor findByVirtualAccount(String virtualAccount)
    {
    	if(null == virtualAccount){
    		return null;
    	}
    	return repository.findByVirtualAccountAndIsEnableTrue(virtualAccount);
    }
    
//    public List<TdDistributor> findByProvince(String province)
//    {
//    	Sort sort = new Sort(Direction.ASC,"city").and(new Sort(Direction.ASC,"disctrict"));
//    	
//    	return repository.findByProvinceAndIsEnableTrueOrderByCityAndDisctrict(province, sort);
//    }
    
    /**
     * 查找
     * 
     * @param ids
     * @return
     */
    public List<TdDistributor> findAll(Iterable<Long> ids)
    {
        return (List<TdDistributor>) repository.findAll(ids);
    }
    
    public List<TdDistributor> findByIsEnableTrue()
    {
        return repository.findByIsEnableTrue();
    }
    
    public List<TdDistributor> findByCityAndIsEnableTrueOrderBySortIdAsc(String city)
    {
        if (null == city)
        {
            return null;
        }
        
        return repository.findByCityAndIsEnableTrueOrderBySortIdAsc(city);
    }
    
    public Page<TdDistributor> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    public Page<TdDistributor> searchAllOrderBySortIdAsc(String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByTitleContainingOrderBySortIdAsc(keywords, pageRequest);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdDistributor save(TdDistributor e)
    {
    	if(null == e){
    		return null;
    	}
    	
    	List<TdDistributorGoods> goodsList = e.getGoodsList();
    	if(null == e.getIsEnable() || !e.getIsEnable()){ // 如果禁用超市，则同时下架超市所有商品
    		if(null != goodsList){
    			for (TdDistributorGoods tdDistributorGoods : goodsList) {
					tdDistributorGoods.setIsOnSale(false);
				}
    		}
    	}
    	// 保存分销商商品
    	if(null != goodsList)
    	{
    		tdDistributorGoodsService.save(goodsList);
    	}
        
        return repository.save(e);
    }
    
    public List<TdDistributor> save(List<TdDistributor> entities)
    {
        
        return (List<TdDistributor>) repository.save(entities);
    }
    
    
    
    
    /**
	 * @author lc
	 * @注释：
	 */
    public TdDistributor findbyUsername(String username){
		
    	return (repository.findByUsernameAndIsEnableTrue(username)); 	
    }
    
	public TdDistributorGoods findByIdAndGoodId(Long id,Long goodsId)
	{
		return repository.findByIdAndGoodsId(id, goodsId);
	}

}
