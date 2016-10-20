package com.ynyes.cslm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class TdSpecificat {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	// 商品Id
	@Column
	private Long goodsId;
	
	// 商家ID
	@Column
	private Long shopId;
	
	// 类型   区分超市和批发分销商品   1为超市商品属性   2 批发、分销商品属性
	@Column 
	private Integer type;
	
	// 规格值
	@Column
	private String specifict;
	
	// 库存
	@Column
	private Long leftNumber;
	
	// 原规格ID
	@Column
	private Long oldId;

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

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSpecifict() {
		return specifict;
	}

	public void setSpecifict(String specifict) {
		this.specifict = specifict;
	}

	public Long getLeftNumber() {
		return leftNumber;
	}

	public void setLeftNumber(Long leftNumber) {
		this.leftNumber = leftNumber;
	}

	public Long getOldId() {
		return oldId;
	}

	public void setOldId(Long oldId) {
		this.oldId = oldId;
	}
	
	
}
