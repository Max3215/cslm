package com.ynyes.cslm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdProviderGoods;
import com.ynyes.cslm.repository.TdProviderGoodsRepo;

/**
 * TdProviderGoods 服务类
 * @author libiao
 *
 */


@Service
public class TdProviderGoodsService {

	@Autowired
	private TdProviderGoodsRepo repository;
	
	public void delete(Long id)
	{
		repository.delete(id);
	}
	
	
	/**
	 * 
	 * 各种查看
	 * 
	 */
	public Page<TdProviderGoods> findByProviderIdAndIsAudit(Long providerId,int page ,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		if(null ==providerId)
		{
			return null;
		}
		return repository.findByIdAndIsAudit(providerId,pageRequest );
	}
	
	public TdProviderGoods findByProviderTitleAndGoodsId(String providerId,Long goodsId)
	{
		return repository.findByProviderIdAndGoodsId(providerId, goodsId);
	}
	
	public TdProviderGoods save(TdProviderGoods e)
	{
		return repository.save(e);
	}
	
	public List<TdProviderGoods> save(List<TdProviderGoods> entities)
	{
		return (List<TdProviderGoods>) repository.save(entities);
	}
	
	public Page<TdProviderGoods> findAll(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findAll(pageRequest);
	}
	
	public Page<TdProviderGoods> findByGoodsTitleOrSubGoodsTitleOrProviderTitle(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByGoodsTitleContainingOrSubGoodsTitleContainingOrProviderTitleContaining(keywords, keywords, keywords,pageRequest);
	}
	
	public TdProviderGoods findOne(Long id)
	{
		return repository.findOne(id);
	}
	
	public Long findProviderId(Long id)
	{
		return repository.findById(id);
	}
	
	
}
