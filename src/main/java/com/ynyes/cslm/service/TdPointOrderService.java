package com.ynyes.cslm.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ynyes.cslm.entity.TdPointGoods;
import com.ynyes.cslm.entity.TdPointOrder;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.entity.TdUserPoint;
import com.ynyes.cslm.repository.TdPointOrderRepo;
import com.ynyes.cslm.util.Criteria;
import com.ynyes.cslm.util.Restrictions;

@Service
@Transactional
public class TdPointOrderService {

	@Autowired
	private TdPointOrderRepo repository;
	
	@Autowired
	private TdUserService tdUserService;
	
	@Autowired
	private TdUserPointService tdUserPointService;
	
	@Autowired
	private TdPointGoodsService tdPointGoodsService;
	
	public TdPointOrder findOne(Long id){
		if(null != id){
			return repository.findOne(id);
		}
		return null;
	}
	
	public TdPointOrder save(TdPointOrder pointOrder){
		if(null != pointOrder){
			return repository.save(pointOrder);
		}
		return null;
	}
	
	public void delete(Long id){
		if(null != id){
			repository.delete(id);
		}
	}
	
	public Page<TdPointOrder> findAll(String username,String keywords,Date startTime,Date endTime,Integer statusId,int page,int size){
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
		
		Criteria<TdPointOrder> c = new Criteria<>();
		
		if(null != username){
			c.add(Restrictions.eq("username", username, true));
		}
		
		if(null != startTime){
    		c.add(Restrictions.gte("orderTime", startTime, true));
    	}
    	
    	if(null != endTime){
    		c.add(Restrictions.lte("orderTime", endTime, true));
    	}
    	
		if(null != keywords && !keywords.isEmpty()){
			c.add(Restrictions.or(Restrictions.like("username", keywords, true),Restrictions.like("orderNumber", keywords, true),Restrictions.like("goodsTitle",keywords, true)));
		}
		if(null != statusId){
			c.add(Restrictions.eq("statusId", statusId, true));
		}
		
		return repository.findAll(c, pageRequest);
	}
	
	/**
	 * 积分兑换商品积分记录
	 * 
	 */
	public void exChangeGoods(TdUser user,TdPointOrder pointOrder){
			
		TdUserPoint userPoint = new TdUserPoint();
		 userPoint.setDetail("积分兑换商品");
		 userPoint.setOrderNumber(pointOrder.getOrderNumber());
		 userPoint.setPoint(pointOrder.getPoint());
		 userPoint.setPointTime(new Date());
		 userPoint.setUsername(user.getUsername());
		 userPoint.setTotalPoint(user.getTotalPoints() - pointOrder.getPoint());
		 tdUserPointService.save(userPoint);
		 
		 user.setTotalPoints(user.getTotalPoints() - pointOrder.getPoint());
		 tdUserService.save(user);
		 
		 TdPointGoods goods = tdPointGoodsService.findOne(pointOrder.getId());
		 
		 if(null != goods){
			 if(null != goods.getSaleNumber()){
				 goods.setSaleNumber(1);
			 }else{
				 goods.setSaleNumber(goods.getSaleNumber()+1);
			 }
			 tdPointGoodsService.save(goods);
		 }
	}
	
	public void orderCancel(TdPointOrder pointOrder){
		
		TdUser user = tdUserService.findByUsername(pointOrder.getUsername());
		
		if(null != user){
			
			TdUserPoint userPoint = new TdUserPoint();
			 userPoint.setDetail("取消积分兑换商品返回");
			 userPoint.setOrderNumber(pointOrder.getOrderNumber());
			 userPoint.setPoint(pointOrder.getPoint());
			 userPoint.setPointTime(new Date());
			 userPoint.setUsername(user.getUsername());
			 userPoint.setTotalPoint(user.getTotalPoints() + pointOrder.getPoint());
			 tdUserPointService.save(userPoint);
			 
			 user.setTotalPoints(user.getTotalPoints() + pointOrder.getPoint());
			 tdUserService.save(user);
		}
	}
	
}
