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

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 订单
 *
 * 记录了订单详情
 * 
 * @author Sharon
 *
 */

@Entity
public class TdOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 订单号
    @Column(unique=true)
    private String orderNumber;
    
    // 订单商品
    @OneToMany
    @JoinColumn(name="tdOrderId")
    private List<TdOrderGoods> orderGoodsList;
    
    // 收货地址
    @Column
    private String shippingAddress;
    
    // 收货人
    @Column
    private String shippingName;
    
    // 收货电话
    @Column
    private String shippingPhone;
    
    // 邮政编码
    @Column
    private String postalCode;
    
    // 线下超市
    @Column
    private Long shopId;
    
    // 超市名称
    @Column
    private String shopTitle;
    
    // 批发商id
    @Column
    private Long providerId;
    
    // 批发商名称
    @Column
    private String providerTitle;
    
    // 同盟店所获返利
    private Double rebate;
    
    // 支付方式
    @Column
    private Long payTypeId;
    
    // 支付方式名称
    @Column
    private String payTypeTitle;
    
    // 支付方式手续费
    @Column(scale=2)
    private Double payTypeFee;
    
    // 配送方式
    @Column
    private Long deliverTypeId;
    
    // 配送方式名称
    @Column
    private String deliverTypeTitle;
    
    // 配送方式名称
    @Column(scale=2)
    private Double deliverTypeFee;
    
    // 快递公司
    @Column
    private String expressCampany;
    
    // 快递单号
    @Column
    private String expressNumber;
    
    // 快递详情查询接口
    @Column
    private String expressUri;
    
    // 用户留言备注
    @Column
    private String userRemarkInfo;
    
    // 后台备注
    @Column
    private String remarkInfo;
    
    // 是否索要发票
    @Column
    private Boolean isNeedInvoice;
    
    // 发票抬头
    @Column
    private String invoiceTitle;
    
    // 发票内容
    @Column
    private String invoiceContent;
    
    // 发票类型
    @Column
    private String invoiceType;
    
    // 预约服务时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date appointmentTime;
    
    // 下单时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date orderTime;
    
    // 取消时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date cancelTime;
    
    // 确认时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date checkTime;
    
    // 付款时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date payTime;
    
    // 配送时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date deliveryTime;
    
    // 收货时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;
    
    // 评价时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date commentTime;
    
    // 完成时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date finishTime;
    
    //订单支付过期时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date orderTimeExpice;
    
    // 订单状态 1：待确认  2:待付款 3:待发货 4:待收货 5:待评价 6: 已完成 7: 已取消 8: 支付取消(失败) 9: 已删除
    @Column
    private Long statusId;
    
    //订单类型  0：普通订单  1：超市进货订单 2:分销订单
    @Column
    private Long typeId;
    
    // 订单取消原因
    @Column
    private String cancelReason;
    
    // 用户
    @Column
    private String username;
    
    // 会员手机号
    @Column
    private String mobile;
    
    // 发货时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date sendTime;
    
    // 配货人
    @Column
    private String deliveryPerson;
    
    // 配送人
    @Column
    private String distributionPerson;
    
    // 收款人
    @Column
    private String moneyReceivePerson;
    
    // 商品总金额
    @Column(scale=2)
    private Double totalGoodsPrice;
    
    // 订单总金额
    @Column(scale=2)
    private Double totalPrice;
    
    // 分销订单返利
    @Column(scale=2)
    private Double totalLeftPrice;
    
    // 排序号
    @Column
    private Long sortId;
    
    // 使用积分数 
    @Column
    private Long pointUse;
    
    // 可获取积分
    @Column
    private Long points;
    
    // 是否在线付款
    @Column
    private Boolean isOnlinePay;
    
    @Column
    private Double platformService;
    // 平台费
    @Column(scale=2)
    private Double trainService;
    
    // 配送费
    @Column(scale=2)
    private Double postPrice;
    
    // 第三方使用费
    @Column(scale=2)
    private Double aliPrice;
    
    // 配送方式
    @Column
    private Long deliveryMethod;
    
    // 自提点名称
    @Column
    private String shipAddressTitle;
    
    // 自提点地址
    @Column
    private String shipAddress;
    
    // 自提点联系方式
    @Column
    private String shipMobile;
//    
    // 自提点ID
    @Column
    private Long shipAddressId;
    
    
    
    
    public Long getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(Long deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	public String getShipAddressTitle() {
		return shipAddressTitle;
	}

	public void setShipAddressTitle(String shipAddressTitle) {
		this.shipAddressTitle = shipAddressTitle;
	}

	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	public String getShipMobile() {
		return shipMobile;
	}

	public void setShipMobile(String shipMobile) {
		this.shipMobile = shipMobile;
	}

	public Double getAliPrice() {
		return aliPrice;
	}

	public void setAliPrice(Double aliPrice) {
		this.aliPrice = aliPrice;
	}

	public Double getPlatformService() {
		return platformService;
	}

	public void setPlatformService(Double platformService) {
		this.platformService = platformService;
	}

	public Double getTrainService() {
		return trainService;
	}

	public void setTrainService(Double trainService) {
		this.trainService = trainService;
	}

	public Long getId() {
        return id;
    }
         
	public void setId(Long id) {
        this.id = id;
    }
	
    public String getOrderNumber() {
        return orderNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<TdOrderGoods> getOrderGoodsList() {
        return orderGoodsList;
    }

    public void setOrderGoodsList(List<TdOrderGoods> orderGoodsList) {
        this.orderGoodsList = orderGoodsList;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public String getShippingPhone() {
        return shippingPhone;
    }

    public void setShippingPhone(String shippingPhone) {
        this.shippingPhone = shippingPhone;
    }

    public Long getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(Long payTypeId) {
        this.payTypeId = payTypeId;
    }

    public String getPayTypeTitle() {
        return payTypeTitle;
    }

    public void setPayTypeTitle(String payTypeTitle) {
        this.payTypeTitle = payTypeTitle;
    }

    public Double getPayTypeFee() {
        return payTypeFee;
    }

    public void setPayTypeFee(Double payTypeFee) {
        this.payTypeFee = payTypeFee;
    }

    public String getDeliverTypeTitle() {
        return deliverTypeTitle;
    }

    public void setDeliverTypeTitle(String deliverTypeTitle) {
        this.deliverTypeTitle = deliverTypeTitle;
    }

    public Long getDeliverTypeId() {
        return deliverTypeId;
    }

    public void setDeliverTypeId(Long deliverTypeId) {
        this.deliverTypeId = deliverTypeId;
    }

    public Double getDeliverTypeFee() {
        return deliverTypeFee;
    }

    public void setDeliverTypeFee(Double deliverTypeFee) {
        this.deliverTypeFee = deliverTypeFee;
    }

    public String getUserRemarkInfo() {
        return userRemarkInfo;
    }

    public void setUserRemarkInfo(String userRemarkInfo) {
        this.userRemarkInfo = userRemarkInfo;
    }

    public String getRemarkInfo() {
        return remarkInfo;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    public Boolean getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public void setIsNeedInvoice(Boolean isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }
    
    public Date getOrderTimeExpice() {
		return orderTimeExpice;
	}

	public void setOrderTimeExpice(Date orderTimeExpice) {
		this.orderTimeExpice = orderTimeExpice;
	}

	public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getExpressCampany() {
        return expressCampany;
    }

    public void setExpressCampany(String expressCampany) {
        this.expressCampany = expressCampany;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getExpressUri() {
        return expressUri;
    }

    public void setExpressUri(String expressUri) {
        this.expressUri = expressUri;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getDeliveryPerson() {
        return deliveryPerson;
    }

    public void setDeliveryPerson(String deliveryPerson) {
        this.deliveryPerson = deliveryPerson;
    }

    public String getDistributionPerson() {
        return distributionPerson;
    }

    public void setDistributionPerson(String distributionPerson) {
        this.distributionPerson = distributionPerson;
    }

    public String getMoneyReceivePerson() {
        return moneyReceivePerson;
    }

    public void setMoneyReceivePerson(String moneyReceivePerson) {
        this.moneyReceivePerson = moneyReceivePerson;
    }

    public Double getTotalGoodsPrice() {
        return totalGoodsPrice;
    }

    public void setTotalGoodsPrice(Double totalGoodsPrice) {
        this.totalGoodsPrice = totalGoodsPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Boolean getIsOnlinePay() {
        return isOnlinePay;
    }

    public void setIsOnlinePay(Boolean isOnlinePay) {
        this.isOnlinePay = isOnlinePay;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }   
    
    public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	public Long getPointUse() {
        return pointUse;
    }

    public void setPointUse(Long pointUse) {
        this.pointUse = pointUse;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopTitle() {
        return shopTitle;
    }

    public void setShopTitle(String shopTitle) {
        this.shopTitle = shopTitle;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Double getTotalLeftPrice() {
        return totalLeftPrice;
    }

    public void setTotalLeftPrice(Double totalLeftPrice) {
        this.totalLeftPrice = totalLeftPrice;
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

	public Double getPostPrice() {
		return postPrice;
	}

	public void setPostPrice(Double postPrice) {
		this.postPrice = postPrice;
	}

	public Long getShipAddressId() {
		return shipAddressId;
	}

	public void setShipAddressId(Long shipAddressId) {
		this.shipAddressId = shipAddressId;
	}
	
	
    
}
