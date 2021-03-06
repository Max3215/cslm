package com.ynyes.cslm.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.codehaus.commons.compiler.samples.ShippingCost;

/**
 * 超市
 * 
 * @author libiao
 *
 */
@Entity
public class TdDistributor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 分销商名称
    @Column
    private String title;
    
    // 分销商地址
    @Column
    private String address;
    
    // 付款方式
    @Column
    private String payType;
    
    // 营业时间
    @Column
    private String openTimeSpan;
    
    // 客服电话
    @Column
    private String serviceTele;
    
    // 投诉电话
    @Column
    private String complainTele;
    
    //省
    @Column
    private String province;
    
    // 市
    @Column
    private String city;
    
    // 区
    @Column
    private String disctrict;
    
    // 是否启用
    @Column
    private Boolean isEnable;
    
    // 排序数字
    @Column
    private Long sortId;
    
    // 经度
    @Column
    private Double longitude;
    
    // 纬度
    @Column
    private Double latitude;
    
    // 描述说明
    @Column
    private String info;
    
    // 图片地址
    @Column
    private String imageUri;
    
    // 证件地址
    @Column
    private String fileUri;
    
    // 轮播展示图片，多张图片以,隔开
    @Column
    private String showPictures;
    
    // 登录名
    @Column
    private String username;
    
    // 登录密码
    @Column
    private String password;
    
    // 手机号
    @Column
    private String mobile;
    
    //虚拟账号
    @Column
    private String virtualAccount;
    
    //虚拟账户金额
    @Column(scale=2)
    private Double virtualMoney;
    
    // 支付密码
    @Column
    private String payPassword;
    
    // 返利
    @Column
    private Double totalCash;
    
    //分销商商品
    @OneToMany
    @JoinColumn(name = "distributorId")
    private List<TdDistributorGoods> goodsList;
    
    // 平台服务收取服务比例
    @Column(scale=2)
    private Double serviceRation;
    
    // 支付宝使用费
    @Column(scale=2)
    private Double aliRation;
    
    // 邮费
    @Column(scale=2)
    private Double postPrice;
    
    // 满额免邮
    @Column
    private Double maxPostPrice;
    
    // 是否开启代理权
    @Column
    private Boolean isSupply;
    
    // 是否开启进货权
    @Column
    private Boolean isStock;
    
    // 自提点
    @OneToMany
    @JoinColumn(name="distributorId")
    private List<TdShippingAddress> shippingList;
    
    // 配送说明
    @Column
    private String postInfo;
    
    // 分销商银行卡号
    @Column
    private String bankCardCode;
    
    // 分销商银行卡名称
    @Column
    private String bankTitle;
    
    // 新加银行卡开户名
    @Column
    private String bankName;
    
    
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }
    
    public Long getSortId() {
        return sortId;
    }

    public void setSortId(Long sortId) {
        this.sortId = sortId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
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

	public String getDisctrict() {
		return disctrict;
	}

	public void setDisctrict(String disctrict) {
		this.disctrict = disctrict;
	}

	public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOpenTimeSpan() {
        return openTimeSpan;
    }

    public void setOpenTimeSpan(String openTimeSpan) {
        this.openTimeSpan = openTimeSpan;
    }

    public String getServiceTele() {
        return serviceTele;
    }

    public void setServiceTele(String serviceTele) {
        this.serviceTele = serviceTele;
    }

    public String getComplainTele() {
        return complainTele;
    }

    public void setComplainTele(String complainTele) {
        this.complainTele = complainTele;
    }

    public String getShowPictures() {
        return showPictures;
    }

    public void setShowPictures(String showPictures) {
        this.showPictures = showPictures;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVirtualAccount() {
		return virtualAccount;
	}

	public void setVirtualAccount(String virtualAccount) {
		this.virtualAccount = virtualAccount;
	}

	public Double getVirtualMoney() {
		return virtualMoney;
	}

	public void setVirtualMoney(Double virtualMoney) {
		this.virtualMoney = virtualMoney;
	}

	public Double getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(Double totalCash) {
        this.totalCash = totalCash;
    }

	public List<TdDistributorGoods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<TdDistributorGoods> goodsList) {
		this.goodsList = goodsList;
	}
	
	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public Double getServiceRation() {
		return serviceRation;
	}

	public void setServiceRation(Double serviceRation) {
		this.serviceRation = serviceRation;
	}

	public Double getAliRation() {
		return aliRation;
	}

	public void setAliRation(Double aliRation) {
		this.aliRation = aliRation;
	}

	public Double getPostPrice() {
		return postPrice;
	}

	public void setPostPrice(Double postPrice) {
		this.postPrice = postPrice;
	}

	public Double getMaxPostPrice() {
		return maxPostPrice;
	}

	public void setMaxPostPrice(Double maxPostPrice) {
		this.maxPostPrice = maxPostPrice;
	}

	public Boolean getIsSupply() {
		return isSupply;
	}

	public void setIsSupply(Boolean isSupply) {
		this.isSupply = isSupply;
	}

	public Boolean getIsStock() {
		return isStock;
	}

	public void setIsStock(Boolean isStock) {
		this.isStock = isStock;
	}

	public List<TdShippingAddress> getShippingList() {
		return shippingList;
	}

	public void setShippingList(List<TdShippingAddress> shippingList) {
		this.shippingList = shippingList;
	}

	public String getFileUri() {
		return fileUri;
	}

	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}

	public String getPostInfo() {
		return postInfo;
	}

	public void setPostInfo(String postInfo) {
		this.postInfo = postInfo;
	}

	public String getBankCardCode() {
		return bankCardCode;
	}

	public void setBankCardCode(String bankCardCode) {
		this.bankCardCode = bankCardCode;
	}

	public String getBankTitle() {
		return bankTitle;
	}

	public void setBankTitle(String bankTitle) {
		this.bankTitle = bankTitle;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	
	

}
