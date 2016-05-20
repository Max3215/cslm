package com.ynyes.cslm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 订单商品统计表
 * @author Max
 *
 */
@Entity
public class TdCountSale {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 店铺Id
	@Column
	private Long shipId;
	
	// 销售类型
	@Column
	private Long saleType;
	
	// 商品ID
	@Column
	private Long goodsId;
	
	// 商品标题
	@Column
	private String goodsTitle;
	
	// 副标题
	@Column
	private String subTitle;
	
	// 编码
	@Column
	private String goodsCode;
	
	// 数量
	@Column
	private Long quantity;
	
	// 销售价
	@Column
	private Double price;
	
	// 总额
	@Column
	private Double totalPrice;

	public Long getShipId() {
		return shipId;
	}

	public void setShipId(Long shipId) {
		this.shipId = shipId;
	}

	public Long getSaleType() {
		return saleType;
	}

	public void setSaleType(Long saleType) {
		this.saleType = saleType;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	
	
	
	

}
