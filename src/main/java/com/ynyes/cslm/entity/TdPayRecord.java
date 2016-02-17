package com.ynyes.cslm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;
/*
 * 在线支付支付记录
 */
@Entity
public class TdPayRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 订单
    @Column(name="tdOrderId")
    private Long orderId;
    
    // 支付方式
    @Column(name="tdPayTypeId")
    private Long payTypeId;
    
    // 支付状态1: 等待支付2: 支付成功3: 支付失败
    @Column(name="statusCode")
    private Integer statusCode;
    
    //支付时间
    @Column(name="payTime")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    // 交易类型
    @Column
    private String cont;
    
    // 订单编号
    @Column
    private String orderNumber;
    
    // 超市ID
    @Column
    private Long distributorId;
    
    // 超市名称
    @Column
    private String distributorTitle;
    
    // 批发商ID
    @Column
    private Long providerId;
    
    // 批发商名称
    @Column
    private String providerTitle;
    
    // 会员账号
    @Column
    private String username;
    
    // 交易总金额
    @Column(scale=2)
    private Double provice;
    
    // 物流费
    @Column(scale=2)
    private Double postPrice;
    
    // 第三方使用费
    @Column(scale=2)
    private Double aliPrice;
    
    // 平台费
    @Column(scale=2)
    private Double servicePrice;
    
    // 商品总额
    @Column(scale=2)
    private Double totalGoodsPrice;
    
    // 实际收入额
    @Column(scale=2)
    private Double realPrice;
    
    // 分销利
    @Column(scale=2)
    private Double turnPrice;
    
    // 类型 主要区分平台记录
    @Column
    private Long type;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(Long payTypeId) {
        this.payTypeId = payTypeId;
    }
    
    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	public Long getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(Long distributorId) {
		this.distributorId = distributorId;
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

	public Double getProvice() {
		return provice;
	}

	public void setProvice(Double provice) {
		this.provice = provice;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Double getPostPrice() {
		return postPrice;
	}

	public void setPostPrice(Double postPrice) {
		this.postPrice = postPrice;
	}

	public Double getAliPrice() {
		return aliPrice;
	}

	public void setAliPrice(Double aliPrice) {
		this.aliPrice = aliPrice;
	}

	public Double getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(Double servicePrice) {
		this.servicePrice = servicePrice;
	}

	public Double getTotalGoodsPrice() {
		return totalGoodsPrice;
	}

	public void setTotalGoodsPrice(Double totalGoodsPrice) {
		this.totalGoodsPrice = totalGoodsPrice;
	}

	public Double getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
	}

	public Double getTurnPrice() {
		return turnPrice;
	}

	public void setTurnPrice(Double turnPrice) {
		this.turnPrice = turnPrice;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}
    
}
