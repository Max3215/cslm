package com.ynyes.cslm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TdRelevance {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	
	// 关联商品1
	@Column
	private Long goodsId1;
	
	// 关联商品2
	@Column
	private Long goodsId2;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGoodsId1() {
		return goodsId1;
	}

	public void setGoodsId1(Long goodsId1) {
		this.goodsId1 = goodsId1;
	}

	public Long getGoodsId2() {
		return goodsId2;
	}

	public void setGoodsId2(Long goodsId2) {
		this.goodsId2 = goodsId2;
	}
	
	
	
}
