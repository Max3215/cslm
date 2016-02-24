package com.ynyes.cslm.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * 超市—商品表
 * 
 * @author libiao
 *
 */

@Entity
public class TdDistributorGoods {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 商品Id
	@Column
	private Long goodsId;
	
	// 商品标题
	@Column
	private String goodsTitle;
	
	@Column
	private String subGoodsTitle;
	
	// 产品Id
	@Column
	private Long productId;
	
	// 商品价格
	@Column(scale=2)
	private Double goodsPrice;
	
	// 商品原价
	@Column(scale=2)
	private Double goodsMarketPrice;
	
	// 商品类型
	@Column
	private Long categoryId;
	
	// 商品所有类型
    @Column
    private String categoryIdTree;
	
	// 商品图片
	@Column
	private String coverImageUri;
	
	// 商品编码
	@Column 
	private String code;
	
	// 商品库存   
	@Column
	private Long leftNumber;
	
	// 是否上架
	@Column
	private Boolean isOnSale;
	
	//  true审核中     flase审核未通过  
	@Column
	private Boolean isAudit;
	
	// 是否分销
	@Column
	private Boolean isDistribution;
	
	// 销量
	@Column
	private Long soldNumber;
	
	// 赠送积分
	@Column
	private Long returnPoints;
	
	// 商品参数
    @OneToMany
    @JoinColumn(name="disGoodsId")
    private List<TdGoodsParameter> goodsParamList;
    
    // 参数值，用于搜索
    @Column
    private String paramValueCollect;
    
    // 筛选参数一值
    @Column
    private String selectOneValue;
    
    // 筛选参数二值
    @Column
    private String selectTwoValue;
    
    // 筛选参数三值
    @Column
    private String selectThreeValue;
    
    // 品牌
    @Column
    private String brandTitle;
    
    // 品牌ID
    @Column
    private Long brandId;
    
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
	private Date onSaleTime;

    @Column
    private String distributorTitle;
    
    // 批发商Id
    @Column
    private Long providerId;
    
    // 批发商名称
    @Column
    private String providerTitle;
    
    // 商品单位
    @Column
    private String unit;
    
    // 首页推荐
    private Boolean isRecommendIndex;
    
//    // 商品邮费
//    @Column
//    private Double postPrice;
//    
//    // 满额免
//    @Column
//    private Double maxPostPrice;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public String getSubGoodsTitle() {
		return subGoodsTitle;
	}

	public void setSubGoodsTitle(String subGoodsTitle) {
		this.subGoodsTitle = subGoodsTitle;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Double getGoodsMarketPrice() {
		return goodsMarketPrice;
	}

	public void setGoodsMarketPrice(Double goodsMarketPrice) {
		this.goodsMarketPrice = goodsMarketPrice;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryIdTree() {
		return categoryIdTree;
	}

	public void setCategoryIdTree(String categoryIdTree) {
		this.categoryIdTree = categoryIdTree;
	}

	public String getCoverImageUri() {
		return coverImageUri;
	}

	public void setCoverImageUri(String coverImageUri) {
		this.coverImageUri = coverImageUri;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getLeftNumber() {
		return leftNumber;
	}

	public void setLeftNumber(Long leftNumber) {
		this.leftNumber = leftNumber;
	}

	public Boolean getIsOnSale() {
		return isOnSale;
	}

	public void setIsOnSale(Boolean isOnSale) {
		this.isOnSale = isOnSale;
	}

	public Boolean getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Boolean isAudit) {
		this.isAudit = isAudit;
	}

	public Boolean getIsDistribution() {
		return isDistribution;
	}

	public void setIsDistribution(Boolean isDistribution) {
		this.isDistribution = isDistribution;
	}

	public Long getSoldNumber() {
		return soldNumber;
	}

	public void setSoldNumber(Long soldNumber) {
		this.soldNumber = soldNumber;
	}

	public Long getReturnPoints() {
		return returnPoints;
	}

	public void setReturnPoints(Long returnPoints) {
		this.returnPoints = returnPoints;
	}

	public List<TdGoodsParameter> getGoodsParamList() {
		return goodsParamList;
	}

	public void setGoodsParamList(List<TdGoodsParameter> goodsParamList) {
		this.goodsParamList = goodsParamList;
	}

	public String getParamValueCollect() {
		return paramValueCollect;
	}

	public void setParamValueCollect(String paramValueCollect) {
		this.paramValueCollect = paramValueCollect;
	}

	public String getBrandTitle() {
		return brandTitle;
	}

	public void setBrandTitle(String brandTitle) {
		this.brandTitle = brandTitle;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Date getOnSaleTime() {
		return onSaleTime;
	}

	public void setOnSaleTime(Date onSaleTime) {
		this.onSaleTime = onSaleTime;
	}

	public String getDistributorTitle() {
		return distributorTitle;
	}

	public void setDistributorTitle(String distributorTitle) {
		this.distributorTitle = distributorTitle;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public String getProviderTitle() {
		return providerTitle;
	}

	public void setProviderTitle(String providerTitle) {
		this.providerTitle = providerTitle;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getSelectOneValue() {
		return selectOneValue;
	}

	public void setSelectOneValue(String selectOneValue) {
		this.selectOneValue = selectOneValue;
	}

	public String getSelectTwoValue() {
		return selectTwoValue;
	}

	public void setSelectTwoValue(String selectTwoValue) {
		this.selectTwoValue = selectTwoValue;
	}

	public String getSelectThreeValue() {
		return selectThreeValue;
	}

	public void setSelectThreeValue(String selectThreeValue) {
		this.selectThreeValue = selectThreeValue;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Boolean getIsRecommendIndex() {
		return isRecommendIndex;
	}

	public void setIsRecommendIndex(Boolean isRecommendIndex) {
		this.isRecommendIndex = isRecommendIndex;
	}
	
	
//
//	public Double getPostPrice() {
//		return postPrice;
//	}
//
//	public void setPostPrice(Double postPrice) {
//		this.postPrice = postPrice;
//	}
//
//	public Double getMaxPostPrice() {
//		return maxPostPrice;
//	}
//
//	public void setMaxPostPrice(Double maxPostPrice) {
//		this.maxPostPrice = maxPostPrice;
//	}


	
	
}
