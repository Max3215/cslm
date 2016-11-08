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

@Entity
public class TdPointOrder {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	// 兑换单号
    @Column(unique=true)
    private String orderNumber;
    
    // 兑换账号
    @Column
    private String username;
    
    
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
    
    // 商品ID
    @Column
    private Long pointId;
    
    // 商品标题
    @Column
    private String goodsTitle;
    
    // 副标题
    @Column
    private String subTitle;
    
    // 编码
    @Column
    private String goodsCode;
    
    // 商品图片
    @Column
    private String goodsImg;
    
    // 兑换积分
    @Column
    private Long point;
    
    // 订单时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
    
    // 状态
    // 1 - 待发货    2 - 待收货   3 - 完成   4- 已取消
    @Column
    private Integer statusId;
    
    // 用户备注
    @Column
    private String userRemarke;
    
    // 后台备注
    @Column
    private String siteRemarke;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Long getPointId() {
		return pointId;
	}

	public void setPointId(Long pointId) {
		this.pointId = pointId;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
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

	public String getGoodsImg() {
		return goodsImg;
	}

	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getUserRemarke() {
		return userRemarke;
	}

	public void setUserRemarke(String userRemarke) {
		this.userRemarke = userRemarke;
	}

	public String getSiteRemarke() {
		return siteRemarke;
	}

	public void setSiteRemarke(String siteRemarke) {
		this.siteRemarke = siteRemarke;
	}
    
    
	
}
