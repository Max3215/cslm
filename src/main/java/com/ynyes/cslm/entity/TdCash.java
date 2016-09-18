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
 * 充值提现
 */
@Entity
public class TdCash {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	// 记录编号 CS-超市  PF-批发  FX-分销  USE-会员
	@Column(unique=true)
	private String cashNumber;
	
	// 商家
	@Column
	private String shopTitle;
	
	// 账户
	@Column
	private String username;
	
	// 时间
	@Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
	
	// 金额
	@Column(scale=2)
	private Double price;
	
	// 提现卡号
	@Column
	private String card;
	
	// 银行名称
	@Column
	private String bank;
	
	// 姓名
	@Column
	private String name;
	
	// 会员类型 1-超市  2-批发商  3-分销商  4-会员
	@Column
	private Long shopType;
	
	// 类型  1 充值  2 提现
	@Column
	private Long type;
	
	// 状态 1 已提交  2 已完成  3 已取消
	@Column
	private Long status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCashNumber() {
		return cashNumber;
	}

	public void setCashNumber(String cashNumber) {
		this.cashNumber = cashNumber;
	}

	public String getShopTitle() {
		return shopTitle;
	}

	public void setShopTitle(String shopTitle) {
		this.shopTitle = shopTitle;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}
	
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getShopType() {
		return shopType;
	}

	public void setShopType(Long shopType) {
		this.shopType = shopType;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "TdCash [id=" + id + ", cashNumber=" + cashNumber + ", shopTitle=" + shopTitle + ", username=" + username
				+ ", createTime=" + createTime + ", price=" + price + ", card=" + card + ", shopType=" + shopType
				+ ", type=" + type + ", status=" + status + "]";
	}
	
	
	
}
