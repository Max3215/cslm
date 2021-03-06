package com.ynyes.cslm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.cslm.entity.TdUserReturn;
import com.ynyes.cslm.repository.TdUserReturnRepo;
import com.ynyes.cslm.util.Criteria;
import com.ynyes.cslm.util.Restrictions;

/**
 * TdUserReturn 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdUserReturnService {
    
    @Autowired
    TdUserReturnRepo repository;
    
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
    public void delete(TdUserReturn e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdUserReturn> entities)
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
    public TdUserReturn findOne(Long id)
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
    public List<TdUserReturn> findAll(Iterable<Long> ids)
    {
        return (List<TdUserReturn>) repository.findAll(ids);
    }
    
    public List<TdUserReturn> findByUsername(String username)
    {
        return repository.findByUsernameOrderByIdDesc(username);
    }
    
    public Page<TdUserReturn> findByUsername(String username, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        return repository.findByUsernameOrderByIdDesc(username, pageRequest);
    }
    
    public Page<TdUserReturn> findByUsernameAndSearch(String username, String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        return repository.findByUsernameAndGoodsTitleContainingOrderByIdDesc(username, keywords, pageRequest);
    }
    
    public Page<TdUserReturn> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "returnTime"));
        
        return repository.findAll(pageRequest);
    }
    
    public Page<TdUserReturn> findByStatusIdOrderBySortIdAsc(Long statusId, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "returnTime"));
        
        return repository.findByStatusIdOrderBySortIdAsc(statusId, pageRequest);
    }
    
    public Page<TdUserReturn> searchAndFindByStatusIdOrderBySortIdAsc(String keywords, Long statusId, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "returnTime"));
        
        return repository.findByUsernameContainingAndStatusIdOrGoodsTitleContainingAndStatusIdOrOrderNumberContainingAndStatusIdOrderBySortIdAsc(keywords, statusId, keywords, statusId, keywords, statusId, pageRequest);
    }
    
    public Page<TdUserReturn> searchAndOrderBySortIdAsc(String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "returnTime"));
        
        return repository.findByUsernameContainingOrGoodsTitleContainingOrOrderNumberContainingOrderBySortIdAsc(keywords, keywords, keywords, pageRequest);
    }
    
    public Page<TdUserReturn> findByShopIdAndType(Long shopId,Long tyoeId,int page,int size)
    {
    	PageRequest pageRequest = new PageRequest(page, size);
    	
    	return repository.findByShopIdAndTypeOrderByIdDesc(shopId, tyoeId, pageRequest);
    }
    
    public Page<TdUserReturn> findByDistributorIdAndType(Long disId,Long type,int page,int size)
    {
    	PageRequest pageRequest = new PageRequest(page, size);
    	return repository.findByDistributorIdAndTypeOrderByIdDesc(disId, type, pageRequest);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdUserReturn save(TdUserReturn e)
    {
        
        return repository.save(e);
    }
    
    public List<TdUserReturn> save(List<TdUserReturn> entities)
    {
        
        return (List<TdUserReturn>) repository.save(entities);
    }
    
    public Page<TdUserReturn> findAll(Long supplyId,Integer turnType,PageRequest pageRequest){
    	Criteria<TdUserReturn> c = new Criteria<>();
    	
    	if(null != supplyId){
    		c.add(Restrictions.eq("supplyId", supplyId, true));
    	}
    	if(null != turnType){
    		c.add(Restrictions.eq("turnType", turnType, true));
    	}
    	
    	return repository.findAll(c, pageRequest);
    }
    
    
    public Page<TdUserReturn> findAll(String shop,Long disId,Long type,Long statusId,int page,int size){
    	PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "returnTime"));
    	Criteria<TdUserReturn> c = new Criteria<>();
    	
    	if(null != shop){
    		if("dis".equalsIgnoreCase(shop)){
    			if(null != disId){
    	    		c.add(Restrictions.eq("distributorId", disId, true));
    	    	}
    		}else if("pro".equalsIgnoreCase(shop)){
    			c.add(Restrictions.eq("shopId", disId, true));
    		}
    	}
    	if(null != type){
    		c.add(Restrictions.eq("type", type, true));
    	}
    	if(null != statusId){
    		c.add(Restrictions.eq("statusId", statusId, true));
    	}
    	return repository.findAll(c, pageRequest);
    }
    
    
}
