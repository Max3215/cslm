package com.ynyes.cslm.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.cslm.entity.TdCash;
import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdPayRecord;
import com.ynyes.cslm.entity.TdProvider;
import com.ynyes.cslm.entity.TdSetting;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.repository.TdCashRepo;
import com.ynyes.cslm.util.Criteria;
import com.ynyes.cslm.util.Restrictions;

/**
 * 
 * @author Max
 * 充值提现服务类
 *
 */
@Service
@Transactional
public class TdCashService {

	@Autowired
	private TdCashRepo repository;
	
	@Autowired
    TdProviderService tdProviderService;
    
    @Autowired
    TdSettingService tdSettingService;
    
    @Autowired
    TdPayRecordService tdPayRecordService;
    
    @Autowired
    TdDistributorService tdDistributorService;
    
    @Autowired
    TdUserService tdUserService;
	
	public TdCash save(TdCash e){
		if(null == e){
			return null;
		}
		return repository.save(e);
	}
	
	public TdCash findOne(Long id)
	{
		if(null == id){
			return null;
		}
		return repository.findOne(id);
	}
	
	public void delete(Long id)
	{
		if(null != id)
		{
			repository.delete(id);
		}
	}
	
	public TdCash findByCashNumber(String cashNumber)
	{
		if(null == cashNumber)
		{
			return null;
		}
		return repository.findByCashNumber(cashNumber);
	}
	
	public Page<TdCash> findAll(int page,int size)
	{
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
		
		return repository.findAll(pageRequest);
	}
	
	public Page<TdCash> findAll(Long shopType,Long type,Long  status,Date startTime,Date endTime,int page,int size){
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "createTime"));
    	Criteria<TdCash> c = new Criteria<>();
    	
    	if(null != shopType)
    	{
    		c.add(Restrictions.eq("shopType", shopType, true));
    	}
    	if(null != type)
    	{
    		c.add(Restrictions.eq("type", type, true));
    	}
    	
    	if(null != startTime){
    		c.add(Restrictions.gte("createTime", startTime, true));
    	}
    	
    	if(null != endTime){
    		c.add(Restrictions.lte("createTime", endTime, true));
    	}
    	if(null != status){
    		c.add(Restrictions.eq("status", status, true));
    	}
		
    	return repository.findAll(c,pageRequest);
	}

	public void afterCash(TdCash cash) {
		if (null != cash) {
			TdPayRecord record = new TdPayRecord();

			if (cash.getCashNumber().contains("CS") && cash.getShopType() == 1) // 超市充值
			{
				TdDistributor distributor = tdDistributorService.findbyUsername(cash.getUsername());
				if (null != distributor) {
					if (null != distributor) {

						distributor.setVirtualMoney(distributor.getVirtualMoney() + cash.getPrice());
						tdDistributorService.save(distributor);

						record.setCont("商家充值");
						record.setCreateTime(new Date());
						record.setDistributorId(distributor.getId());
						record.setDistributorTitle(distributor.getTitle());
						record.setOrderNumber(cash.getCashNumber());
						record.setStatusCode(1);

						record.setProvice(cash.getPrice()); // 订单总额
						record.setPostPrice(0.00); // 邮费
						record.setAliPrice(0.00); // 第三方费
						record.setServicePrice(0.00); // 平台费
						record.setTotalGoodsPrice(cash.getPrice()); // 商品总价
						record.setTurnPrice(0.00); // 超市返利
						record.setRealPrice(cash.getPrice()); // 实际获利
						tdPayRecordService.save(record);

					}
				}
			} else if (cash.getCashNumber().contains("USE") && cash.getShopType() == 4) {
				TdUser user = tdUserService.findByUsername(cash.getUsername());
				if (null != user) {
					user.setVirtualMoney(user.getVirtualMoney() + cash.getPrice());
					tdUserService.save(user);

					record.setType(2L);

					record.setCont("会员充值");
					record.setCreateTime(new Date());
					record.setOrderNumber(cash.getCashNumber());
					record.setStatusCode(1);
					record.setUsername(cash.getUsername());

					record.setProvice(cash.getPrice()); // 订单总额
					record.setPostPrice(0.00); // 邮费
					record.setAliPrice(0.00); // 第三方费
					record.setServicePrice(0.00); // 平台费
					record.setTotalGoodsPrice(cash.getPrice()); // 商品总价
					record.setTurnPrice(0.00); // 超市返利
					record.setRealPrice(cash.getPrice()); // 实际获利
					tdPayRecordService.save(record);
				}
			} else {
				TdProvider provider = tdProviderService.findByUsername(cash.getUsername());
				if (null != provider) {

					provider.setVirtualMoney(provider.getVirtualMoney() + cash.getPrice());

					record.setCont("商家充值");
					record.setCreateTime(new Date());
					record.setProviderId(provider.getId());
					record.setProviderTitle(provider.getTitle());
					record.setOrderNumber(cash.getCashNumber());
					record.setStatusCode(1);

					record.setProvice(cash.getPrice()); // 订单总额
					record.setPostPrice(0.00); // 邮费
					record.setAliPrice(0.00); // 第三方费
					record.setServicePrice(0.00); // 平台费
					record.setTotalGoodsPrice(cash.getPrice()); // 商品总价
					record.setTurnPrice(0.00); // 超市返利
					record.setRealPrice(cash.getPrice()); // 实际获利
					tdPayRecordService.save(record);
				}
			}

			// 平台支出
			TdSetting setting = tdSettingService.findTopBy();
			if (null != setting.getVirtualMoney()) {
				setting.setVirtualMoney(setting.getVirtualMoney() - cash.getPrice());
			}
			tdSettingService.save(setting); // 更新平台虚拟余额

			// 记录平台收益
			record = new TdPayRecord();
			record.setCont("商家充值");
			record.setCreateTime(new Date());
			record.setDistributorTitle(cash.getShopTitle());
			record.setOrderNumber(cash.getCashNumber());
			record.setStatusCode(1);
			record.setType(1L); // 类型 区分平台记录

			record.setProvice(cash.getPrice()); // 订单总额
			record.setPostPrice(0.00); // 邮费
			record.setAliPrice(0.00); // 第三方费
			record.setServicePrice(0.00); // 平台费
			record.setTotalGoodsPrice(cash.getPrice()); // 商品总价
			record.setTurnPrice(0.00); // 超市返利
			record.setRealPrice(cash.getPrice());

			tdPayRecordService.save(record);

			cash.setStatus(2L); // 已完成
			repository.save(cash);
		}

	}
	
	
	public void beforeCash(TdCash cash) {
		if(null != cash)
		{
			TdPayRecord record = new TdPayRecord();
			
			if(cash.getCashNumber().contains("CS") && cash.getShopType() ==1) // 超市充值
			{
				TdDistributor distributor = tdDistributorService.findbyUsername(cash.getUsername());
				if(null != distributor)
				{
					if(null != distributor)
		        	{
	        			distributor.setVirtualMoney(distributor.getVirtualMoney()-cash.getPrice()); 
	        			tdDistributorService.save(distributor);
	        			
	        			record.setCont("商家提现");
		        		record.setCreateTime(new Date());
		        		record.setDistributorId(distributor.getId());
		        		record.setDistributorTitle(distributor.getTitle());
		        		record.setOrderNumber(cash.getCashNumber());
		        		record.setStatusCode(1);
		        		
		        		record.setProvice(cash.getPrice()); // 订单总额
		                record.setPostPrice(0.00); // 邮费
		                record.setAliPrice(0.00);	// 第三方费
		                record.setServicePrice(0.00);	// 平台费
		                record.setTotalGoodsPrice(cash.getPrice()); // 商品总价
		                record.setTurnPrice(0.00); // 超市返利
		                record.setRealPrice(cash.getPrice()); // 实际获利
		        		tdPayRecordService.save(record);
		        		
		        	}
				}
			}else if(cash.getCashNumber().contains("USE") && cash.getShopType()==4){
				TdUser user = tdUserService.findByUsername(cash.getUsername());
				if(null != user)
				{
        			user.setVirtualMoney(user.getVirtualMoney()-cash.getPrice());
					tdUserService.save(user);
					
					record.setCont("会员提现");
					
					record.setType(2L);
	        		record.setCreateTime(new Date());
	        		record.setOrderNumber(cash.getCashNumber());
	        		record.setStatusCode(1);
	        		record.setUsername(cash.getUsername());
	        		
	        		record.setProvice(cash.getPrice()); // 订单总额
	                record.setPostPrice(0.00); // 邮费
	                record.setAliPrice(0.00);	// 第三方费
	                record.setServicePrice(0.00);	// 平台费
	                record.setTotalGoodsPrice(cash.getPrice()); // 商品总价
	                record.setTurnPrice(0.00); // 超市返利
	                record.setRealPrice(cash.getPrice()); // 实际获利
	        		tdPayRecordService.save(record);
				}
			}else {
				TdProvider provider = tdProviderService.findByUsername(cash.getUsername());
				if(null != provider)
	        	{
        			provider.setVirtualMoney(provider.getVirtualMoney()-cash.getPrice());
	        		tdProviderService.save(provider);
        			
	                record.setCont("商家提现");
	        		
	                record.setCreateTime(new Date());
	                record.setProviderId(provider.getId());
	                record.setProviderTitle(provider.getTitle());
	                record.setOrderNumber(cash.getCashNumber());
	                record.setStatusCode(1);
	                
	                record.setProvice(cash.getPrice()); // 订单总额
	                record.setPostPrice(0.00); // 邮费
	                record.setAliPrice(0.00);	// 第三方费
	                record.setServicePrice(0.00);	// 平台费
	                record.setTotalGoodsPrice(cash.getPrice()); // 商品总价
	                record.setTurnPrice(0.00); // 超市返利
	                record.setRealPrice(cash.getPrice()); // 实际获利
	                tdPayRecordService.save(record);
	        	}
			}
		}
	}
	
	public void editDrawCash(TdCash cash) {
		if(null != cash)
		{
			TdPayRecord record = new TdPayRecord();
			
			if(cash.getCashNumber().contains("CS") && cash.getShopType() ==1) // 超市充值
			{
				TdDistributor distributor = tdDistributorService.findbyUsername(cash.getUsername());
				if(null != distributor)
				{
					if(null != distributor)
		        	{
		        		if(cash.getStatus()==3){
		        			distributor.setVirtualMoney(distributor.getVirtualMoney()+cash.getPrice()); 
		        			tdDistributorService.save(distributor);
	        			
	        			
		        			record.setCont("商家提现未通过");
			        		record.setCreateTime(new Date());
			        		record.setDistributorId(distributor.getId());
			        		record.setDistributorTitle(distributor.getTitle());
			        		record.setOrderNumber(cash.getCashNumber());
			        		record.setStatusCode(1);
			        		
			        		record.setProvice(cash.getPrice()); // 订单总额
			                record.setPostPrice(0.00); // 邮费
			                record.setAliPrice(0.00);	// 第三方费
			                record.setServicePrice(0.00);	// 平台费
			                record.setTotalGoodsPrice(cash.getPrice()); // 商品总价
			                record.setTurnPrice(0.00); // 超市返利
			                record.setRealPrice(cash.getPrice()); // 实际获利
			        		tdPayRecordService.save(record);
		        		}
		        	}
				}
			}else if(cash.getCashNumber().contains("USE") && cash.getShopType()==4){
				TdUser user = tdUserService.findByUsername(cash.getUsername());
				if(null != user)
				{
					if(cash.getStatus()==3){
						user.setVirtualMoney(user.getVirtualMoney()+cash.getPrice());
						tdUserService.save(user);
						
						record.setCont("会员提现未通过");
					
						record.setType(2L);
		        		record.setCreateTime(new Date());
		        		record.setOrderNumber(cash.getCashNumber());
		        		record.setStatusCode(1);
		        		record.setUsername(cash.getUsername());
		        		
		        		record.setProvice(cash.getPrice()); // 订单总额
		                record.setPostPrice(0.00); // 邮费
		                record.setAliPrice(0.00);	// 第三方费
		                record.setServicePrice(0.00);	// 平台费
		                record.setTotalGoodsPrice(cash.getPrice()); // 商品总价
		                record.setTurnPrice(0.00); // 超市返利
		                record.setRealPrice(cash.getPrice()); // 实际获利
		        		tdPayRecordService.save(record);
					}
				}
			}else {
				TdProvider provider = tdProviderService.findByUsername(cash.getUsername());
				if(null != provider)
	        	{
					if(cash.getStatus()==3){
						provider.setVirtualMoney(provider.getVirtualMoney()+cash.getPrice());
		        		tdProviderService.save(provider);
						
		                record.setCont("商家提现未通过");
		                record.setCreateTime(new Date());
		                record.setProviderId(provider.getId());
		                record.setProviderTitle(provider.getTitle());
		                record.setOrderNumber(cash.getCashNumber());
		                record.setStatusCode(1);
		                
		                record.setProvice(cash.getPrice()); // 订单总额
		                record.setPostPrice(0.00); // 邮费
		                record.setAliPrice(0.00);	// 第三方费
		                record.setServicePrice(0.00);	// 平台费
		                record.setTotalGoodsPrice(cash.getPrice()); // 商品总价
		                record.setTurnPrice(0.00); // 超市返利
		                record.setRealPrice(cash.getPrice()); // 实际获利
		                tdPayRecordService.save(record);
	                }
	        	}
			}
			
			// 平台支出
			if(cash.getStatus()==2){
				TdSetting setting = tdSettingService.findTopBy();
		        if( null != setting.getVirtualMoney())
		        {
		        	setting.setVirtualMoney(setting.getVirtualMoney()-cash.getPrice());
		        }
		        tdSettingService.save(setting); // 更新平台虚拟余额
		        
		        // 记录平台收益
		        record = new TdPayRecord();
	        	record.setCont("商家提现");
		        record.setCreateTime(new Date());
		        record.setDistributorTitle(cash.getShopTitle());
		        record.setOrderNumber(cash.getCashNumber());
		        record.setStatusCode(1);
		        record.setType(1L); // 类型 区分平台记录
		        
		        record.setProvice(cash.getPrice()); // 订单总额
	            record.setPostPrice(0.00); // 邮费
	            record.setAliPrice(0.00);	// 第三方费
	            record.setServicePrice(0.00);	// 平台费
	            record.setTotalGoodsPrice(cash.getPrice()); // 商品总价
	            record.setTurnPrice(0.00); // 超市返利
	            record.setRealPrice(cash.getPrice()); 
		        
		        tdPayRecordService.save(record);
			}
	        
		}
	}
	
}
