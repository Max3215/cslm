package com.ynyes.cslm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	
	// 批发名称
	@Column
	private String providerTitle;
	
	// 商品封面图片
	@Column
	private String goodsCoverImageUri;
	
	// 批发价
	@Column(scale=2)
	private Double outFactoryPrice;
	
	// 佣金比例
	@Column(scale=2)
	private Double shopReturnRation;
	
	// 审核中/未审核通过
	@Column
	private Boolean isAudit;
	
	// 库存
	@Column
	private Long leftNumber;

	
	
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
	
	
	
	
	
	
	
	
}
