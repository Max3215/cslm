package com.ynyes.cslm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdKeywords;
import com.ynyes.cslm.entity.TdTag;
import com.ynyes.cslm.repository.TdTagRepo;


/**
 * TdTag 服务类
 * 
 * @author libiao
 *
 */
@Service
public class TdTagService{

	@Autowired
	TdTagRepo repository;
	
	@Autowired
	TdDistributorGoodsService tdDistributorGoodsService;
	
	
	/**
	 * 删除
	 * @author libiao
	 * @param id
	 */
	 public void delete(Long id)
	    {
	        if (null != id)
	        {
	            repository.delete(id);
	        }
	    }
	 
	 public void delete(TdTag e)
     {
        if (null != e)
        {
            repository.delete(e);
        }
     }
    
    public void delete(List<TdTag> entities)
    {
        if (null != entities)
        {
            repository.delete(entities);
        }
    } 
	 
	
	/**
	 * 查找
	 * 
	 * @return
	 */
	public TdTag findOne(Long id)
	{
		if(null == id){
			
			return null;
		}
		
		return repository.findOne(id);
	}
	
	public Page<TdTag> findAllBySortIdAsc(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.ASC,"sortId"));
		return repository.findAll(pageRequest);
	}
	public Page<TdTag> findByTypeIdBySortIdAsc(Long typeId,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.ASC,"sortId"));
		return repository.findByTypeId(typeId, pageRequest);
	}
	
	public List<TdTag> findByTypeId(Long id)
	{
		return repository.findByTypeId(id);
	}
	
	
	public List<TdTag> findAll(){
		return (List<TdTag>) repository.findAll();
	}
	
	public TdTag save(TdTag e)
	{
		if(null == e.getCreateTime())
		{
			e.setCreateTime(new Date());
		}
		// 修改
		if(null != e.getId()){
			// 查找使用标签的所有商品
			List<TdDistributorGoods> goodList = tdDistributorGoodsService.findByTagId(e.getId());
			
			// 如果有使用此标签的商品，同步修改商品图片
			if(null != goodList && goodList.size() > 0){
				for (TdDistributorGoods tdDistributorGoods : goodList) {
					tdDistributorGoods.setTagImg(e.getImgUrl());
				}
				tdDistributorGoodsService.save(goodList);
			}
		}
		
		return repository.save(e);
	}
	
	public TdTag findByIdAndIsEnableTrue(Long id){
		if(null != id){
			return repository.findByIdAndIsEnableTrue(id);
		}
		return null;
	}
	
}
