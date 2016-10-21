package com.ynyes.cslm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.cslm.entity.TdSpecificat;
import com.ynyes.cslm.repository.TdSpecificatRepo;
/**
 * 商品规格服务类
 * @author Max
 * 2016-10-19
 */
@Service
@Transactional
public class TdSpecificatService {

	
	@Autowired
	private TdSpecificatRepo repository;
	
	
	/**
	 * 新增
	 */
	public TdSpecificat save(TdSpecificat specificat){
		if(null != specificat){
			return repository.save(specificat);
		}
		return null;
	}
	
	public List<TdSpecificat> save(List<TdSpecificat> specList){
		return (List<TdSpecificat>) repository.save(specList);
	}
	
	public TdSpecificat findOne(Long id){
		if(null != id){
			return repository.findOne(id);
		}
		return null;
	}
	
	/**
	 * 删除
	 */
	public void delete(Long id){
		if(null != id){
			repository.delete(id);
		}
	}
	
	public void delete(List<TdSpecificat> specList){
		if(null != specList){
			repository.delete(specList);
		}
	}
	
	/**
	 * 根据商品ID和类型删除
	 */
	public void deleteByGoodsIdAndType(Long goodsId,Integer type){
		if(null != goodsId && null != type){
			repository.deleteByGoodsIdAndType(goodsId, type);
		}
	}
	
	/**
	 * 查找商品规格
	 * 
	 */
	public List<TdSpecificat> findByGoodsIdAndType(Long goodsId,Integer type){
		if(null != goodsId && null != type){
			return repository.findByGoodsIdAndType(goodsId, type);
		}
		return null;
	}
	
	public List<TdSpecificat> findByShopIdAndGoodsIdAndType(Long shopId,Long goodsId,Integer type){
		if(null != goodsId && null != type){
			return repository.findByShopIdAndGoodsIdAndType(shopId,goodsId, type);
		}
		return null;
	}
	
	// 根据原ID查找，主要用于分销商商品规格更新
	public List<TdSpecificat> findByOldId(Long oldId){
		if(null != oldId){
			return repository.findByOldId(oldId);
		}
		return null;
	}
}
