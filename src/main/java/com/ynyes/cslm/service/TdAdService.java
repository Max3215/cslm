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

import com.ynyes.cslm.entity.TdAd;
import com.ynyes.cslm.entity.TdAdType;
import com.ynyes.cslm.repository.TdAdRepo;
import com.ynyes.cslm.util.Criteria;
import com.ynyes.cslm.util.Restrictions;

/**
 * TdAd 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdAdService {
    
    @Autowired
    TdAdRepo repository;
    
    @Autowired
    TdAdTypeService tdAdTypeService;
    
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
    public void delete(TdAd e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdAd> entities)
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
    public TdAd findOne(Long id)
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
    public List<TdAd> findAll(Iterable<Long> ids)
    {
        return (List<TdAd>) repository.findAll(ids);
    }
    
    public List<TdAd> findAll()
    {
        return (List<TdAd>) repository.findAll();
    }
    
    public List<TdAd> findByTypeIdAndIsValidTrueOrderBySortIdAsc(Long typeId)
    {
        return repository.findByTypeIdAndStartTimeBeforeAndEndTimeAfterAndIsEnableTrueOrderBySortIdAsc(typeId, new Date(), new Date());
    }
    
    public List<TdAd> findByTypeIdAndDistributorIdAndIsValidTrueOrderBySortIdAsc(Long typeId,Long disId)
    {
        return repository.findByTypeIdAndDistributorIdAndStartTimeBeforeAndEndTimeAfterAndIsEnableTrueOrderBySortIdAsc(typeId, disId,new Date(), new Date());
    }
    
    public Page<TdAd> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    public List<TdAd> findAllOrderBySortIdAsc()
    {
        return (List<TdAd>) repository.findAll(new Sort(Direction.ASC, "sortId"));
    }
    
    public List<TdAd> findByDistributorId(Long disId)
    {
    	return repository.findByDistributorId(disId);
    }
    
    public Page<TdAd> findAll(Long typeId,Long disId,int page,int size)
    {
    	PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
    	Criteria<TdAd> c = new Criteria<>();
    	
    	if(null != typeId)
    	{
    		c.add(Restrictions.eq("typeId", typeId, true));
    	}
    	if(null != disId)
    	{
    		c.add(Restrictions.eq("distributorId", disId, true));
    	}
    	
    	return repository.findAll(c, pageRequest);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdAd save(TdAd e)
    {
        if (null == e.getCreateTime())
        {
            e.setCreateTime(new Date());
        }
        
        if (null != e.getTypeId())
        {
            TdAdType adType = tdAdTypeService.findOne(e.getTypeId());
            
            if (null != adType)
            {
                e.setTypeIsNewWindow(adType.getIsNewWindow());
            }
        }
        
        return repository.save(e);
    }
    
    public List<TdAd> save(List<TdAd> entities)
    {
        
        return (List<TdAd>) repository.save(entities);
    }
}
