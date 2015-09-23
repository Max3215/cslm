package com.ynyes.cslm.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;


/**
 * 批发商
 * 
 * @author Sharon
 *
 */

@Entity
public class TdProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
    // 名称
    @Column(unique=true)
    private String title;
    
    //登录名
    @Column
    private String username;
    
    //密码
    @Column
    private String password;
    
    //手机号
    @Column
    private String mobile;
    
    // 虚拟账户
    @Column
    private String virtualAccount;
    
    // 虚拟账号余额
    @Column(scale=2)
    private Double virtualMoney;
    
    // 省
    @Column
    private String province;
    
    // 市
    @Column
    private String city;
    
	// 详细地址
	@Column
	private String address;
    
    // 排序号
    @Column
    private Long sortId;
    
    //是否启用
    @Column
    private Boolean isEnable;
    
    //超市商品
    @Column
    @JoinColumn(name = "providerId")
    private List<TdProviderGoods> goodsList;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

	public List<TdProviderGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<TdProviderGoods> goodsList) {
		this.goodsList = goodsList;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
    
    
    
    
}

