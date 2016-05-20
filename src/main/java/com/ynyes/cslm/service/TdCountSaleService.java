package com.ynyes.cslm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.cslm.entity.TdCountSale;
import com.ynyes.cslm.repository.TdCountSaleRepo;

/**
 * TdCountSale 服务类
 * @author Max
 *
 */
@Service
@Transactional
public class TdCountSaleService {
	
	@Autowired
	private TdCountSaleRepo repostory;
	
	
	public TdCountSale save(TdCountSale e)
	{
		if(null == e)
		{
			return null;
		}
		return repostory.save(e);
	}
	
	public List<TdCountSale> save(List<TdCountSale> entities)
	{
		if(null == entities)
		{
			return null;
		}
		return (List<TdCountSale>) repostory.save(entities);
	}
	
	public void delete(Long id)
	{
		if(null != id)
		{
			repostory.delete(id);
		}
	}
	
	public void delete(Long shipId,Long saleType)
	{
		if(null != shipId && null != saleType)
		{
			repostory.deleteByShipIdAndSaleType(shipId, saleType);
		}
	}
	
	
	public List<TdCountSale> findByShipIdAndTypeId(Long shipId,Long saleType)
	{
		if(null == shipId || null == saleType)
		{
			return null;
		}
		return repostory.findByShipIdAndSaleTypeOrderByQuantityDesc(shipId, saleType);
	}
	
	
}
