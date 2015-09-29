package com.ynyes.cslm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
	public Page<TdProviderGoods> findByProviderIdAndIsAudit(Long providerId,Boolean isAudit,int page ,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size);
		if(null ==providerId)
		{
			return null;
		}
		return repository.findByIdAndIsAudit(providerId,isAudit,pageRequest );
	}
	
	
	
	
}
