package com.ynyes.cslm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdProviderGoods;
import com.ynyes.cslm.repository.TdProviderGoodsRepo;
import com.ynyes.cslm.util.Criteria;
import com.ynyes.cslm.util.Restrictions;

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
	
	public void delete(TdProviderGoods e)
	{
		if(null != e)
		{
			repository.delete(e);
		}
	}
	
	public void delete(List<TdProviderGoods> e)
	{
		if(null != e)
		{
			repository.delete(e);
		}
	}
	
	/**
	 * 
	 * 各种查看
	 * 
	 */
	public List<Long> findByProviderId(Long providerId)
	{
		return repository.findByProviderIdAndGroupByCategoryId(providerId);
	}
	
	public List<Long> findByProviderIdAndIsAudit(Long providerId)
	{
		return repository.findByProviderIdAndIsAuditAndGroupByCategoryId(providerId);
	}
	
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
	
	public Page<TdProviderGoods> findByGoodsTitleOrCodeOrProviderTitle(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		return repository.findByGoodsTitleContainingOrCodeContainingOrProviderTitleContaining(keywords, keywords, keywords,pageRequest);
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
		return repository.findByGoodsTitleContainingAndIsDistributionTrueAndIsAuditTrueOrCodeContainingAndIsDistributionTrueAndIsAuditTrue(keywords,keywords,pageRequest);
	}
	
	public Page<TdProviderGoods> findByIsDistributionTrueAndIsAuditFalse(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsDistributionTrueAndIsAuditFalse(pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndIsDistributionTrueAndIsAuditFalse(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsDistributionTrueAndIsAuditFalseOrCodeContainingAndIsDistributionTrueAndIsAuditFalse(keywords,keywords,pageRequest);
	}
	
	public Page<TdProviderGoods> findByIsDistributionTrue(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsDistributionTrue(pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndIsDistributionTrue(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsDistributionTrueOrCodeContainingAndIsDistributionTrue(keywords,keywords,pageRequest);
	}
	
	public Page<TdProviderGoods> findByIsDistributionFalseAndIsAuditTrue(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsDistributionFalseAndIsAuditTrue(pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndIsDistributionFalseAndIsAuditTrue(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsDistributionFalseAndIsAuditTrueOrCodeContainingAndIsDistributionFalseAndIsAuditTrue(keywords,keywords,pageRequest);
	}
	
	public Page<TdProviderGoods> findByIsDistributionFalseAndIsAuditFalse(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsDistributionFalseAndIsAuditFalse(pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndIsDistributionFalseAndIsAuditFalse(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsDistributionFalseAndIsAuditFalseOrCodeContainingAndIsDistributionFalseAndIsAuditFalse(keywords,keywords,pageRequest);
	}
	
	public Page<TdProviderGoods> findByIsDistributionFalse(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsDistributionFalse(pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndIsDistributionFalse(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsDistributionFalseOrCodeContainingAndIsDistributionFalse(keywords,keywords,pageRequest);
	}
	
	public Page<TdProviderGoods> findByIsAuditTrue(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsAuditTrue(pageRequest);
	}
	
	public Page<TdProviderGoods> findByIsOnSaleTrue(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsOnSaleTrue(pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndIsAuditTrue(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsAuditTrueOrCodeContainingAndIsAuditTrue(keywords,keywords,pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndIsOnSaleTrue(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsOnSaleTrueOrCodeContainingAndIsOnSaleTrue(keywords,keywords,pageRequest);
	}
	
	public Page<TdProviderGoods> findByIsAuditFalse(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByIsAuditFalse(pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndIsAuditFalse(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingAndIsAuditFalseOrCodeContainingAndIsAuditFalse(keywords,keywords,pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndKeywords(String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByGoodsTitleContainingOrCodeContaining(keywords,keywords,pageRequest);
	}
	
	// ---------------------------
	public Page<TdProviderGoods> findByProviderIdAndIsDistributionAndIsAudit(Long providerId,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		if(null == providerId){
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		
		return repository.findByProviderIdAndIsDistributionAndIsAudit(providerId, isDistribution, isAudit, pageRequest);
	}
	
	public Page<TdProviderGoods> findAll(Long providerId,Long categoryId,String keywords,Boolean isDistribution,Boolean isAudit,PageRequest pageRequest)
	{
		Criteria<TdProviderGoods> c = new Criteria<>();
		if(null != providerId)
		{
			c.add(Restrictions.eq("proId", providerId, true));
		}
		if(null != keywords && !"".equals(keywords.trim()))
		{
			c.add(Restrictions.or(Restrictions.like("goodsTitle", keywords, true),Restrictions.like("code", keywords, true)));
		}
		
		if(null != isDistribution)
		{
			c.add(Restrictions.eq("isDistribution", isDistribution, true));
		}
		if(null != isAudit)
		{
			c.add(Restrictions.eq("isAudit", isAudit, true));
		}
		if(null != categoryId)
		{
			c.add(Restrictions.like("categoryIdTree", "["+categoryId+"]", true));
		}
		
		return repository.findAll(c, pageRequest);
	}
	
	public Page<TdProviderGoods> findByProviderIdAndIsDistributionAndIsAuditOrderByLeftNumberDesc(Long providerId,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		if(null == providerId){
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		
		return repository.findByProviderIdAndIsDistributionAndIsAuditOrderByLeftNumberDesc(providerId, isDistribution, isAudit, pageRequest);
	}
	public Page<TdProviderGoods> findByProviderIdAndIsDistributionAndIsAuditOrderByLeftNumberAsc(Long providerId,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		if(null == providerId){
			return null;
		}
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		
		return repository.findByProviderIdAndIsDistributionAndIsAuditOrderByLeftNumberAsc(providerId, isDistribution, isAudit, pageRequest);
	}
	// ----------------------------------
	
	public Page<TdProviderGoods> searchAndProviderIdAndIsDistributionAndIsAudit(Long providerId,Boolean isDistribution,Boolean isAudit,String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByProviderIdAndGoodsTitleLikeAndIsDistributionAndIsAuditOrProviderIdAndCodeLikeAndIsDistributionAndIsAudit(providerId, "%"+keywords+"%", isDistribution, isAudit, pageRequest);
	}
	//-------------------------------------------------------------------------------------
	
	public Page<TdProviderGoods> findByProviderIdAndIsDistribution(Long providerId,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByProviderIdAndIsDistribution(providerId, isDistribution, pageRequest);
	}
	public Page<TdProviderGoods> findByProviderIdAndIsDistributionOrderByLeftNumberDesc(Long providerId,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByProviderIdAndIsDistributionOrderByLeftNumberDesc(providerId, isDistribution, pageRequest);
	}
	public Page<TdProviderGoods> findByProviderIdAndIsDistributionOrderByLeftNumberAsc(Long providerId,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByProviderIdAndIsDistributionOrderByLeftNumberAsc(providerId, isDistribution, pageRequest);
	}
	// -------------------------------------------------------------
	
	public Page<TdProviderGoods> searchAndProviderIdAndIsDistributionOrderByLeftNumberDesc(Long providerId,String keywords,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByProviderIdAndGoodsTitleLikeAndIsDistributionOrProviderIdAndCodeLikeAndIsDistributionOrderByLeftNumberDesc(providerId, "%"+keywords+"%", isDistribution, pageRequest);
	}
	public Page<TdProviderGoods> searchAndProviderIdAndIsDistributionOrderByLeftNumberAsc(Long providerId,String keywords,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByProviderIdAndGoodsTitleLikeAndIsDistributionOrProviderIdAndCodeLikeAndIsDistributionOrderByLeftNumberAsc(providerId, "%"+keywords+"%", isDistribution, pageRequest);
	}
	// -----------------------------------------------------
	
	public Page<TdProviderGoods> findByProviderIdAndIsAudit(Long providerId,Boolean isAudit, int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByProviderIdAndIsAudit(providerId, isAudit, pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndProviderIdAndIsAudit(Long providerId,String keywords,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByProviderIdAndGoodsTitleLikeAndIsAuditOrProviderIdAndCodeLikeAndIsAudit(providerId, "%"+keywords+"%", isAudit, providerId, "%"+keywords+"%", isAudit, pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndProviderIdAndIsOnSale(Long providerId,String keywords,Boolean isOnSale,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByProviderIdAndGoodsTitleLikeAndIsOnSaleOrProviderIdAndCodeLikeAndIsOnSale(providerId, "%"+keywords+"%", isOnSale, providerId, "%"+keywords+"%", isOnSale, pageRequest);
	}
	
	
	//----------------------------
	public Page<TdProviderGoods> findByProviderIdAndCategoryIdAndIsDistribution(Long providerId,Long catId,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndIsDistribution(providerId,catStr, isDistribution, pageRequest);
	}
	public Page<TdProviderGoods> findByProviderIdAndCategoryIdAndIsDistributionOrderByLeftNumberDesc(Long providerId,Long catId,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndIsDistributionOrderByLeftNumberDesc(providerId,catStr, isDistribution, pageRequest);
	}
	public Page<TdProviderGoods> findByProviderIdAndCategoryIdAndIsDistributionOrderByLeftNumberAsc(Long providerId,Long catId,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndIsDistributionOrderByLeftNumberAsc(providerId,catStr, isDistribution, pageRequest);
	}
	
	// -----------------------------------------
	public Page<TdProviderGoods> findByProviderIdAndCategoryIdAndIsDistributionAndIsAudit(Long providerId,Long catId,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndIsDistributionAndIsAudit(providerId,catStr, isDistribution,isAudit, pageRequest);
	}
	public Page<TdProviderGoods> findByProviderIdAndCategoryIdAndIsDistributionAndIsAuditOrderByLeftNumberDesc(Long providerId,Long catId,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndIsDistributionAndIsAuditOrderByLeftNumberDesc(providerId,catStr, isDistribution,isAudit, pageRequest);
	}
	public Page<TdProviderGoods> findByProviderIdAndCategoryIdAndIsDistributionAndIsAuditOrderByLeftNumberAsc(Long providerId,Long catId,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndIsDistributionAndIsAuditOrderByLeftNumberAsc(providerId,catStr, isDistribution,isAudit, pageRequest);
	}
	// -----------------------------------------------------------------
	
	public Page<TdProviderGoods> searchAndProviderIdAndCategoryIdAndIsDistribution(Long providerId,String keywords,Long catId,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsDistributionOrProviderIdAndCategoryIdTreeLikeAndCodeLikeAndIsDistribution(
																providerId,catStr, "%"+keywords+"%", isDistribution, pageRequest);
	}
	public Page<TdProviderGoods> searchAndProviderIdAndCategoryIdAndIsDistributionOrderByLeftNumberDesc(Long providerId,String keywords,Long catId,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsDistributionOrProviderIdAndCategoryIdTreeLikeAndCodeLikeAndIsDistributionOrderByLeftNumberDesc(
																providerId,catStr, "%"+keywords+"%", isDistribution, pageRequest);
	}
	public Page<TdProviderGoods> searchAndProviderIdAndCategoryIdAndIsDistributionOrderByLeftNumberAsc(Long providerId,String keywords,Long catId,Boolean isDistribution,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsDistributionOrProviderIdAndCategoryIdTreeLikeAndCodeLikeAndIsDistributionOrderByLeftNumberAsc(
																providerId,catStr, "%"+keywords+"%", isDistribution, pageRequest);
	}
	
	// ----------------------------
	public Page<TdProviderGoods> searchAndProviderIdAndCategoryIdAndIsDistributionAndIsAudut(Long providerId,String keywords,Long catId,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsDistributionAndIsAuditOrProviderIdAndCategoryIdTreeLikeAndCodeLikeAndIsDistributionAndIsAudit(
																providerId,catStr, "%"+keywords+"%", isDistribution,isAudit,pageRequest);
	}
	public Page<TdProviderGoods> searchAndProviderIdAndCategoryIdAndIsDistributionAndIsAudutOrderByLeftNumberDesc(Long providerId,String keywords,Long catId,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsDistributionAndIsAuditOrProviderIdAndCategoryIdTreeLikeAndCodeLikeAndIsDistributionAndIsAuditOrderByLeftNumberDesc(
																providerId,catStr, "%"+keywords+"%", isDistribution,isAudit,pageRequest);
	}
	public Page<TdProviderGoods> searchAndProviderIdAndCategoryIdAndIsDistributionAndIsAudutOrderByLeftNumberAsc(Long providerId,String keywords,Long catId,Boolean isDistribution,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsDistributionAndIsAuditOrProviderIdAndCategoryIdTreeLikeAndCodeLikeAndIsDistributionAndIsAuditOrderByLeftNumberAsc(
																providerId,catStr, "%"+keywords+"%", isDistribution,isAudit,pageRequest);
	}
	// -----------------------------------------------------
	
	public Page<TdProviderGoods> findByProviderIdAndCategoryId(Long providerId,Long catId,int page ,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		if(null ==providerId)
		{
			return null;
		}
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLike(providerId, catStr, pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndProviderIdAndCategoryIdAndKeywords(Long providerId,Long catId,String keywords,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndGoodsTitleLikeOrProviderIdCategoryIdTreeLikeAndCodeLike(
															providerId,catStr, "%"+keywords+"%", 
															providerId,catStr, "%"+keywords+"%", pageRequest);
	}
	
	// 
	public Page<TdProviderGoods> findByProviderIdAndIsOnSale(Long providerId,Boolean isOnSale,int page ,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		if(null ==providerId)
		{
			return null;
		}
		return repository.findByProviderIdAndIsOnSale(providerId,isOnSale,pageRequest );
	}
	public Page<TdProviderGoods> findByProviderIdAndIsOnSaleOrderByLeftNumberDesc(Long providerId,Boolean isOnSale,int page ,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		if(null ==providerId)
		{
			return null;
		}
		return repository.findByProviderIdAndIsOnSaleOrderByLeftNumberDesc(providerId,isOnSale,pageRequest );
	}
	public Page<TdProviderGoods> findByProviderIdAndIsOnSaleOrderByLeftNumberAsc(Long providerId,Boolean isOnSale,int page ,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		if(null ==providerId)
		{
			return null;
		}
		return repository.findByProviderIdAndIsOnSaleOrderByLeftNumberAsc(providerId,isOnSale,pageRequest );
	}
	//--------
	
	public Page<TdProviderGoods> searchAndProviderIdAndKeywordsAndIsOnSale(Long providerId,String keywords,Boolean isOnSale,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByProviderIdAndGoodsTitleLikeAndIsOnSaleOrProviderIdAndCodeLikeAndIsOnSaleOrCodeLikeAndIsOnSale(providerId, "%"+keywords+"%", isOnSale, pageRequest);
	}
	public Page<TdProviderGoods> searchAndProviderIdAndKeywordsAndIsOnSaleOrderByLeftNumberDesc(Long providerId,String keywords,Boolean isOnSale,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByProviderIdAndGoodsTitleLikeAndIsOnSaleOrProviderIdAndCodeLikeAndIsOnSaleOrCodeLikeAndIsOnSaleOrderByLeftNumberDesc(providerId, "%"+keywords+"%", isOnSale, pageRequest);
	}
	public Page<TdProviderGoods> searchAndProviderIdAndKeywordsAndIsOnSaleOrderByLeftNumberAsc(Long providerId,String keywords,Boolean isOnSale,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		return repository.findByProviderIdAndGoodsTitleLikeAndIsOnSaleOrProviderIdAndCodeLikeAndIsOnSaleOrCodeLikeAndIsOnSaleOrderByLeftNumberAsc(providerId, "%"+keywords+"%", isOnSale, pageRequest);
	}
	// ------------------------------
	
	public Page<TdProviderGoods> findByProviderIdAndCategoryIdAndIsOnSale(Long providerId,Long catId,Boolean isOnSale,int page ,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		if(null ==providerId)
		{
			return null;
		}
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndIsOnSale(providerId, catStr,isOnSale, pageRequest);
	}
	public Page<TdProviderGoods> findByProviderIdAndCategoryIdAndIsOnSaleOrderByLeftNumberDesc(Long providerId,Long catId,Boolean isOnSale,int page ,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		if(null ==providerId)
		{
			return null;
		}
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndIsOnSaleOrderByLeftNumberDesc(providerId, catStr,isOnSale, pageRequest);
	}
	public Page<TdProviderGoods> findByProviderIdAndCategoryIdAndIsOnSaleOrderByLeftNumberAsc(Long providerId,Long catId,Boolean isOnSale,int page ,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		if(null ==providerId)
		{
			return null;
		}
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndIsOnSaleOrderByLeftNumberAsc(providerId, catStr,isOnSale, pageRequest);
	}
	// ---------------------------------------
	
	public Page<TdProviderGoods> searchAndProviderIdAndCategoryIdAndKeywordsAndIsOnSale(Long providerId,Long catId,String keywords,Boolean isOnSale,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsOnSaleOrProviderIdAndCategoryIdTreeLikeAndCodeLikeAndIsOnSale(
															providerId,catStr, "%"+keywords+"%", isOnSale,pageRequest);
	}
	public Page<TdProviderGoods> searchAndProviderIdAndCategoryIdAndKeywordsAndIsOnSaleOrderByLeftNumberDesc(Long providerId,Long catId,String keywords,Boolean isOnSale,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsOnSaleOrProviderIdAndCategoryIdTreeLikeAndCodeLikeAndIsOnSaleOrderByLeftNumberDesc(
															providerId,catStr, "%"+keywords+"%", isOnSale,pageRequest);
	}
	public Page<TdProviderGoods> searchAndProviderIdAndCategoryIdAndKeywordsAndIsOnSaleOrderByLeftNumberAsc(Long providerId,Long catId,String keywords,Boolean isOnSale,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsOnSaleOrProviderIdAndCategoryIdTreeLikeAndCodeLikeAndIsOnSaleOrderByLeftNumberAsc(
															providerId,catStr, "%"+keywords+"%", isOnSale,pageRequest);
	}
	// ---------------------------
	
	public Page<TdProviderGoods> findByProviderIdAndCategoryIdAndIsAudit(Long providerId,Long catId,Boolean isAudit,int page ,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		if(null ==providerId)
		{
			return null;
		}
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndIsOnSale(providerId, catStr,isAudit, pageRequest);
	}
	
	public Page<TdProviderGoods> searchAndProviderIdAndCategoryIdAndKeywordsAndIsAudit(Long providerId,Long catId,String keywords,Boolean isAudit,int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		String catStr = "%[" + catId + "]%";
		return repository.findByProviderIdAndCategoryIdTreeLikeAndGoodsTitleLikeAndIsAuditOrProviderIdAndCategoryIdTreeLikeAndCodeLikeAndIsAudit(
															providerId,catStr, "%"+keywords+"%", isAudit,
															providerId,catStr, "%"+keywords+"%", isAudit,pageRequest);
	}
	
	public List<TdProviderGoods> findByGoodsId(Long goodsId)
	{
		return repository.findByGoodsId(goodsId); 
	}
	
	
	
	
}
