package com.ynyes.cslm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.cslm.entity.TdDistributor;
import com.ynyes.cslm.entity.TdDistributorGoods;
import com.ynyes.cslm.entity.TdUser;
import com.ynyes.cslm.repository.TdDistributorRepo;

/**
 * TdDistributor 服务类
 * 
 * @author Sharon
 *
 */

@Service
@Transactional
public class TdDistributorService {
    @Autowired
    TdDistributorRepo repository;
    
    @Autowired
    TdUserCommentService TdUserCommentService;
    
    @Autowired
    TdUserService tdUserService;
    
    @Autowired
    TdDistributorGoodsService tdDistributorGoodsService;
    
    /**
     * 删除
     * 
     * @param id 菜单项ID
     */
    public void delete(Long id)
    {
        if (null != id)
        {
            repository.delete(id);
        }
    }
    
    /**
     * 删除
     * 
     * @param e 菜单项
     */
    public void delete(TdDistributor e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdDistributor> entities)
    {
        if (null != entities)
        {
            repository.delete(entities);
        }
    }
    
    /**
     * 查找
     * 
     * @param id ID
     * @return
     */
    public TdDistributor findOne(Long id)
    {
        if (null == id)
        {
            return null;
        }
        
        return repository.findByIdAndIsEnableTrue(id);
    }
    
    public TdDistributor findById(Long id)
    {
    	if(null == id){
    		return null;
    	}
    	return repository.findOne(id);
    }
    
    public List<TdDistributor> findBydisctrict(String disctrict){
    	return repository.findByDisctrictAndIsEnableTrue(disctrict);
    }
    
    public TdDistributor findByVirtualAccount(String virtualAccount)
    {
    	if(null == virtualAccount){
    		return null;
    	}
    	return repository.findByVirtualAccountAndIsEnableTrue(virtualAccount);
    }
    
//    public List<TdDistributor> findByProvince(String province)
//    {
//    	Sort sort = new Sort(Direction.ASC,"city").and(new Sort(Direction.ASC,"disctrict"));
//    	
//    	return repository.findByProvinceAndIsEnableTrueOrderByCityAndDisctrict(province, sort);
//    }
    
    /**
     * 查找
     * 
     * @param ids
     * @return
     */
    public List<TdDistributor> findAll(Iterable<Long> ids)
    {
        return (List<TdDistributor>) repository.findAll(ids);
    }
    
    public List<TdDistributor> findByIsEnableTrue()
    {
        return repository.findByIsEnableTrue();
    }
    
    public List<TdDistributor> findByCityAndIsEnableTrueOrderBySortIdAsc(String city)
    {
        if (null == city)
        {
            return null;
        }
        
        return repository.findByCityAndIsEnableTrueOrderBySortIdAsc(city);
    }
    
    public Page<TdDistributor> findAllOrderBySortIdAsc(int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "sortId"));
        
        return repository.findAll(pageRequest);
    }
    
    public Page<TdDistributor> searchAllOrderBySortIdAsc(String keywords, int page, int size)
    {
        PageRequest pageRequest = new PageRequest(page, size);
        
        return repository.findByTitleContainingOrderBySortIdAsc(keywords, pageRequest);
    }
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdDistributor save(TdDistributor e)
    {
        // 保存分销商商品
    	if(null != e.getGoodsList())
    	{
    		for (TdDistributorGoods disGoods : e.getGoodsList()) 
    		{
    			tdDistributorGoodsService.saveGoods(disGoods);
    		}
    	}
        
        return repository.save(e);
    }
    
    public List<TdDistributor> save(List<TdDistributor> entities)
    {
        
        return (List<TdDistributor>) repository.save(entities);
    }
    
    
    
    
    /**
	 * @author lc
	 * @注释：
	 */
    public TdDistributor findbyUsername(String username){
		
    	return (repository.findByUsernameAndIsEnableTrue(username)); 	
    }
    
//    /**
//	 * @author lc
//	 * @注释：同盟店评价信息
//	 */
//    public int ContdiysiteComment(Long diysiteId) {
//		List<TdUserComment> tdUserComment_list = TdUserCommentService.findByDiysiteIdOrderByIdDesc(diysiteId);
//		return tdUserComment_list.size();
//	}
//    
//    public float diysiteServiceStars(Long diysiteId){
//    	List<TdUserComment> tdUserComment_list = TdUserCommentService.findByDiysiteIdOrderByIdDesc(diysiteId);
//    	
//    	if (null != tdUserComment_list) {
//    		Long[] result = new Long[20];
//        	int temp = 0;
//        	if (tdUserComment_list.size()==0) {
//				return (float) 0.0;
//			}
//        	for(int i = 0; i < 20; i++){
//        		result[i] = tdUserComment_list.get(Math.abs(new Random().nextInt())%tdUserComment_list.size()).getServiceStar();
//        		temp = (int) (temp + result[i]);
//        	}
//        	return temp/20;
//		}
//    	
//    	return (float) 0.0;
//    	
//    }
    
//    public List<TdDistributor> findByIsEnableTrueGreoupByCity()
//    {
//    	return repository.findByIsENableTrueGroupByCity();
//    }
    	public TdDistributorGoods findByIdAndGoodId(Long id,Long goodsId)
    	{
    		return repository.findByIdAndGoodsId(id, goodsId);
    	}

    	public Page<TdDistributorGoods> findByIdAndIsOnSale(Long id,Boolean isOnSale,int page,int size)
    	{
    		PageRequest pageRequest = new PageRequest(page, size);
    		return repository.findByIdAndIsOnSale(id, isOnSale, pageRequest);
    	}
}
