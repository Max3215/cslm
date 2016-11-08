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
 * 
 * @author Max
 * 积分商品
 *
 */
@Entity
public class TdPointGoods {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	// 商品标题
	@Column
	private String goodsTitle;
	
	// 商品副标题
	@Column
	private String subGoodsTitle;
	
	// 编码
	@Column
	private String code;
	
	@Column
	private String imgUrl;
	
	// 商品原价
	@Column
	private Double goodsPrice;
	
	// 是否开启兑换
	@Column
	private Boolean isEnable;
	
	// 兑换积分
	@Column
	private Long point;
	
	// 兑换说明
	@Column
	private String changeDetail;
	
	// 详情
	@Column
	private String detail;
	
	// 兑换数量
	@Column
	private Integer saleNumber;
	
	// 总数量
	@Column
	private Integer leftNumber;
	
	@Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
	private Date onSaleTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public String getChangeDetail() {
		return changeDetail;
	}

	public void setChangeDetail(String changeDetail) {
		this.changeDetail = changeDetail;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}


	public Integer getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(Integer saleNumber) {
		this.saleNumber = saleNumber;
	}

	public Integer getLeftNumber() {
		return leftNumber;
	}

	public void setLeftNumber(Integer leftNumber) {
		this.leftNumber = leftNumber;
	}

	public Date getOnSaleTime() {
		return onSaleTime;
	}

	public void setOnSaleTime(Date onSaleTime) {
		this.onSaleTime = onSaleTime;
	}
	
	
	
}
