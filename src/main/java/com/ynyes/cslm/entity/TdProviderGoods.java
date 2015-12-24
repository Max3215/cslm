package com.ynyes.cslm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 批发商商品库
 * @author libiao
 *
 */

@Entity
public class TdProviderGoods {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 商品ID
	@Column
	private Long goodsId;
	
	// 商品标题
	@Column
	private String goodsTitle;
	
	// 商品副标题
	@Column
	private String subGoodsTitle;
	
	// 编码
	@Column
	private String code;
	
	// 批发商名称
	@Column
	private String providerTitle;
	
	// 商品类型
	@Column
	private Long categoryId;
	
	// 商品所有类型
    @Column
    private String categoryIdTree;
	
	// 商品封面图片
	@Column
	private String goodsCoverImageUri;
	
	// 批发价
	@Column(scale=2)
	private Double outFactoryPrice;
	
	// 市场价
	@Column(scale=2)
	private Double goodsMarketPrice;
	
	// 佣金比例
	@Column(scale=2)
	private Double shopReturnRation;
	
	// 审核中/未审核通过
	@Column
	private Boolean isAudit;
	
	// 库存
	@Column
	private Long leftNumber;
	
	// 是否在批发
	@Column
	private Boolean isOnSale;
	
	// 是否分销
	@Column
	private Boolean isDistribution;
	
	// 时间
	@Column
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
	private Date onSaleTime;
	
	// 商品单位
	@Column
	private String unit;
	
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSubGoodsTitle() {
		return subGoodsTitle;
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

	public void setSubGoodsTitle(String subGoodsTitle) {
		this.subGoodsTitle = subGoodsTitle;
	}

	public String getGoodsCoverImageUri() {
		return goodsCoverImageUri;
	}

	public void setGoodsCoverImageUri(String goodsCoverImageUri) {
		this.goodsCoverImageUri = goodsCoverImageUri;
	}

	public Double getOutFactoryPrice() {
		return outFactoryPrice;
	}

	public void setOutFactoryPrice(Double outFactoryPrice) {
		this.outFactoryPrice = outFactoryPrice;
	}

	public Double getShopReturnRation() {
		return shopReturnRation;
	}

	public void setShopReturnRation(Double shopReturnRation) {
		this.shopReturnRation = shopReturnRation;
	}

	public Boolean getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Boolean isAudit) {
		this.isAudit = isAudit;
	}

	public Long getLeftNumber() {
		return leftNumber;
	}

	public void setLeftNumber(Long leftNumber) {
		this.leftNumber = leftNumber;
	}

	public String getProviderTitle() {
		return providerTitle;
	}

	public void setProviderTitle(String providerTitle) {
		this.providerTitle = providerTitle;
	}

	public Boolean getIsOnSale() {
		return isOnSale;
	}

	public void setIsOnSale(Boolean isOnSale) {
		this.isOnSale = isOnSale;
	}

	public Boolean getIsDistribution() {
		return isDistribution;
	}

	public void setIsDistribution(Boolean isDistribution) {
		this.isDistribution = isDistribution;
	}

	public Date getOnSaleTime() {
		return onSaleTime;
	}

	public void setOnSaleTime(Date onSaleTime) {
		this.onSaleTime = onSaleTime;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getGoodsMarketPrice() {
		return goodsMarketPrice;
	}

	public void setGoodsMarketPrice(Double goodsMarketPrice) {
		this.goodsMarketPrice = goodsMarketPrice;
	}
	
	
	
}
