package com.ynyes.cslm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.cslm.entity.TdOrderGoods;
import com.ynyes.cslm.repository.TdOrderGoodsRepo;

/**
 * TdOrderGoods 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdOrderGoodsService {
    
    @Autowired
    TdOrderGoodsRepo repository;
    
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
    public void delete(TdOrderGoods e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdOrderGoods> entities)
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
    public TdOrderGoods findOne(Long id)
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
    public List<TdOrderGoods> findAll(Iterable<Long> ids)
    {
        return (List<TdOrderGoods>) repository.findAll(ids);
    }
    
    public List<TdOrderGoods> findAll()
    {
        return (List<TdOrderGoods>) repository.findAll();
    }
    
    public Page<TdOrderGoods> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdOrderGoods save(TdOrderGoods e)
    {
        
        return repository.save(e);
    }
    
    public List<TdOrderGoods> save(List<TdOrderGoods> entities)
    {
        
        return (List<TdOrderGoods>) repository.save(entities);
    }
    
    
    /**
     * 查看各条件商品销售状态
     * 
     */
    public Page<TdOrderGoods> findByDistributorIdAndTypeId(Long disId,Long typeId,int page,int size)
    {
    	if(null == disId || null == typeId)
    	{
    		return null;
    	}
    	
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findByShopIdAndTypeId(disId, typeId,pageRequest );
    }
    
    public Page<TdOrderGoods> searchAndDistributorIdAndTypeId(Long disId,Long typeId,String keywords,int page,int size)
    {
    	if(null == disId || null == typeId)
    	{
    		return null;
    	}
    	PageRequest pageRequest = new PageRequest(page, size);
    	
    	return repository.findByShopIdAndTypeIdAndGoodsTitleLIikeOrShopIdAndTypeIdAndGoodsCodeLike(disId, typeId, "%"+keywords+"%", disId, typeId, "%"+keywords+"%", pageRequest);
    }
    
    public Page<TdOrderGoods> findByDistributorIdAndTypeIdAndSaleTimeBefore(Long disId,Long typeId,Date endTime,int page ,int size)
    {
    	if(null == disId || null == typeId)
    	{
    		return null;
    	}
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findByShopIdAndTypeIdAndSaleTimeBefore(disId, typeId, endTime, pageRequest);
    }
    
    public Page<TdOrderGoods> searchAndDistributorIdAndTypeIdAndSaleTimeBefore(Long disId,Long typeId,String keywords,Date endTime,int page ,int size)
    {
    	if(null == disId || null == typeId)
    	{
    		return null;
    	}
    	PageRequest pageRequest = new PageRequest(page, size);
    	
    	return repository.findByShopIdAndTypeIdAndGoodsTitleLIikeAndSaleTimeBeforeOrShopIdAndTypeIdAndGoodsCodeLikeAndSaleTimeBefore(
						    														disId, typeId, "%"+keywords+"%", endTime, 
						    														disId, typeId, "%"+keywords+"%", endTime, pageRequest);
    }
    
    public Page<TdOrderGoods> findByDistributorIdAndTypeIdAndSaleTimeAfter(Long disId,Long typeId,Date startTime,int page ,int size)
    {
    	if(null == disId || null == typeId)
    	{
    		return null;
    	}
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findByShopIdAndTypeIdAndSaleTimeAfter(disId, typeId, startTime, pageRequest);
    }
    
    public Page<TdOrderGoods> searchAndDistributorIdAndTypeIdAndSaleTimeAfter(Long disId,Long typeId,String keywords,Date startTme,int page ,int size)
    {
    	if(null == disId || null == typeId)
    	{
    		return null;
    	}
    	PageRequest pageRequest = new PageRequest(page, size);
    	
    	return repository.findByShopIdAndTypeIdAndGoodsTitleLIikeAndSaleTimeAfterOrShopIdAndTypeIdAndGoodsCodeLikeAndSaleTimeAfter(disId, typeId,"%"+keywords+"%", startTme, disId, typeId, "%"+keywords+"%", startTme, pageRequest);
    }
    
    public Page<TdOrderGoods> findByDistributorIdAndTypeIdAndSaleTimeAfterAndEndTimeBefore(Long disId,Long typeId,Date startTime,Date endTime,int page ,int size)
    {
    	if(null == disId || null == typeId)
    	{
    		return null;
    	}
    	PageRequest pageRequest = new PageRequest(page, size);
    	
    	return repository.findByShopIdAndTypeIdAndSaleTimeAfterAndSaleTimeBefore(disId, typeId, startTime, endTime, pageRequest);
    }
    
    public Page<TdOrderGoods> searchAndDistributorIAndTypeIdAndSaleTimeAfterAndEndTimeBefore(Long disId,Long typeId,String keywords,Date startTme,Date endTime,int page ,int size)
    {
    	if(null == disId || null == typeId)
    	{
    		return null;
    	}
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findByShopIdAndTypeIdAndGoodsTitleLIikeAndSaleTimeAfterAndSaleTimeBeforeOrShopIdAndTypeIdAndGoodsCodeLikeAndSaleTimeAfterAndSaleTimeBefore(
    																			disId, typeId, "%"+keywords+"%", startTme, endTime, 
    																			disId, typeId, "%"+keywords+"%", startTme, endTime, pageRequest);
    }
    
}
