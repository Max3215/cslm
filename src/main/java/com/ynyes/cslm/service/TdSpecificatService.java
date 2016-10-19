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
}
