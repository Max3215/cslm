package com.ynyes.cslm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * 购物车
 * 
 * @author Sharon
 *
 */

@Entity
public class TdCartGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 用户
    @Column
    private String username;
    
    // 商品ID
    @Column
    private Long goodsId;
    
    // 商品标题
    private String goodsTitle;
    
    // 商品封面
    private String goodsCoverImageUri;
    
    // 商品数量
    private Long quantity;
    
    // 商品超市Id
    @Column
    private Long distributorId;
    
    @Column
    private Long distributorGoodsId;
    
    // 商品所在超市名称
    @Column
    private String distributorTitle;
    
    // 批发商id
    @Column
    private Long providerId;
    
    // 批发商名
    @Column
    private String providerTite;
    
    // 成交价
    @Column
    private Double price;
    
    // 是否选中，选中的商品将进行结算
    @Column
    private Boolean isSelected;
    
    // 是否是登陆用户
    @Column
    private Boolean isLoggedIn;

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

    public String getGoodsCoverImageUri() {
        return goodsCoverImageUri;
    }

    public void setGoodsCoverImageUri(String goodsCoverImageUri) {
        this.goodsCoverImageUri = goodsCoverImageUri;
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

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(Boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
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

	public String getProviderTite() {
		return providerTite;
	}

	public void setProviderTite(String providerTite) {
		this.providerTite = providerTite;
	}

	public Long getDistributorGoodsId() {
		return distributorGoodsId;
	}

	public void setDistributorGoodsId(Long distributorGoodsId) {
		this.distributorGoodsId = distributorGoodsId;
	}
    
	
    
    
}
