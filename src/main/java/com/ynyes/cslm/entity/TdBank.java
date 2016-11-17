package com.ynyes.cslm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 银行卡信息
 * @author Max
 *
 */
@Entity
public class TdBank {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	// 角色账号，配合类型查找所属账号
	@Column
	private String username;
	
	// 角色类型 1、会员  2、超市  3、批发商  4、分销商
	@Column
	private Integer type;
	
	// 银行卡号
	@Column
	private String bankCard;

	// 开户行
	@Column
	private String bankName;
	
	// 开户名
	@Column
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
