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
	public Page<TdProviderGoods> findByProviderId(Long providerId,int page ,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		if(null ==providerId)
		{
			return null;
		}
		return repository.findByProviderId(providerId,pageRequest );
	}
	
	public TdProviderGoods findByProviderIdAndGoodsId(Long providerId,Long goodsId)
	{
		return repository.findByProviderIdAndGoodsId(providerId, goodsId);
	}
	
	public TdProviderGoods save(TdProviderGoods e)
	{
		e.setOnSaleTime(new Date());
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
	
	public Page<TdProviderGoods> findByIsDistributionTrueAndIsAuditTrue(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsDistributionTrueAndIsAuditTrue(pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndIsDistributionTrueAndIsAuditTrue(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsDistributionTrueAndIsAuditTrueOrSubGoodsTitleContainingAndIsDistributionTrueAndIsAuditTrue(keywords,keywords,pageRequest);
	}
	
	public Page<TdProviderGoods> findByIsDistributionTrueAndIsAuditFalse(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsDistributionTrueAndIsAuditFalse(pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndIsDistributionTrueAndIsAuditFalse(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsDistributionTrueAndIsAuditFalseOrSubGoodsTitleContainingAndIsDistributionTrueAndIsAuditFalse(keywords,keywords,pageRequest);
	}
	
	public Page<TdProviderGoods> findByIsDistributionTrue(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsDistributionTrue(pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndIsDistributionTrue(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsDistributionTrueOrSubGoodsTitleContainingAndIsDistributionTrue(keywords,keywords,pageRequest);
	}
	
	public Page<TdProviderGoods> findByIsDistributionFalseAndIsAuditTrue(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsDistributionFalseAndIsAuditTrue(pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndIsDistributionFalseAndIsAuditTrue(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsDistributionFalseAndIsAuditTrueOrSubGoodsTitleContainingAndIsDistributionFalseAndIsAuditTrue(keywords,keywords,pageRequest);
	}
	
	public Page<TdProviderGoods> findByIsDistributionFalseAndIsAuditFalse(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsDistributionFalseAndIsAuditFalse(pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndIsDistributionFalseAndIsAuditFalse(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsDistributionFalseAndIsAuditFalseOrSubGoodsTitleContainingAndIsDistributionFalseAndIsAuditFalse(keywords,keywords,pageRequest);
	}
	
	public Page<TdProviderGoods> findByIsDistributionFalse(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsDistributionFalse(pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndIsDistributionFalse(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsDistributionFalseOrSubGoodsTitleContainingAndIsDistributionFalse(keywords,keywords,pageRequest);
	}
	
	public Page<TdProviderGoods> findByIsAuditTrue(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsAuditTrue(pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndIsAuditTrue(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsAuditTrueOrSubGoodsTitleContainingAndIsAuditTrue(keywords,keywords,pageRequest);
	}
	
	public Page<TdProviderGoods> findByIsAuditFalse(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsAuditFalse(pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndIsAuditFalse(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsAuditFalseOrSubGoodsTitleContainingAndIsAuditFalse(keywords,keywords,pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndKeywords(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingOrSubGoodsTitleContaining(keywords,keywords,pageRequest);
	}
	
	public Page<TdProviderGoods> findByProviderIdAndIsDistributionAndIsAudit(Long providerId,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		if(null == providerId){
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		
		return repository.findByProviderIdAndIsDistributionAndIsAudit(providerId, isDistribution, isAudit, pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndProviderIdAndIsDistributionAndIsAudit(Long providerId,Boolean isDistribution,Boolean isAudit,String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByProviderIdAndGoodsTitleLikeAndIsDistributionAndIsAuditOrProviderIdAndSubGoodsTitleLikeAndIsDistributionAndIsAudit(providerId, keywords, isDistribution, isAudit, providerId, keywords, isDistribution, isAudit, pageRequest);
	}
	
	public Page<TdProviderGoods> findByProviderIdAndIsDistribution(Long providerId,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByProviderIdAndIsDistribution(providerId, isDistribution, pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndProviderIdAndIsDistribution(Long providerId,String keywords,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByProviderIdAndGoodsTitleLikeAndIsDistributionOrProviderIdAndSubGoodsTitleLikeAndIsDistribution(providerId, keywords, isDistribution, providerId, keywords, isDistribution, pageRequest);
	}
	
	public Page<TdProviderGoods> findByProviderIdAndIsAudit(Long providerId,Boolean isAudit, int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByProviderIdAndIsAudit(providerId, isAudit, pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndProviderIdAndIsAudit(Long providerId,String keywords,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByProviderIdAndGoodsTitleLikeAndIsAuditOrProviderIdAndSubGoodsTitleLikeAndIsAudit(providerId, keywords, isAudit, providerId, keywords, isAudit, pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndProviderIdAndKeywords(Long providerId,String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByProviderIdAndGoodsTitleLikeOrProviderIdAndSubGoodsTitleLike(providerId, keywords, providerId, keywords, pageRequest);
	}
	
	
	
	
	
	
	
}
