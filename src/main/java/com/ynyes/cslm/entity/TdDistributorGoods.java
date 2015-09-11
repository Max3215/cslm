package com.ynyes.cslm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 分销商—商品表
 * 
 * @author libiao
 *
 */

@Entity
public class TdDistributorGoods {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//商品Id
	@Column
	private Long goodsId;
	
	//商品标题
	@Column
	private String goodsTitle;
	
	//商品价格
	@Column(scale=2)
	private Double goodsPrice;
	
	//商品图片
	@Column
	private String coverImageUri;
	
	//商品库存   
	@Column
	private Long number;
	
	//是否上架
	@Column
	private Boolean isOnSale;
	
	
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

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getCoverImageUri() {
		return coverImageUri;
	}

	public void setCoverImageUri(String coverImageUri) {
		this.coverImageUri = coverImageUri;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Boolean getIsOnSale() {
		return isOnSale;
	}

	public void setIsOnSale(Boolean isOnSale) {
		this.isOnSale = isOnSale;
	}
	
	
	
}