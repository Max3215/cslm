package com.ynyes.cslm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * 订单商品
 *
 * 记录了订单商品的相关信息
 * 
 * @author Sharon
 *
 */

@Entity
public class TdOrderGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // 商品ID
    @Column
    private Long goodsId;
    
    // 商品名称
    @Column
    private String goodsTitle;
    
    @Column
    private String goodsCode;
    
    // 商品简介
    @Column
    private String goodsSubTitle;
    
    // 商品封面
    @Column
    private String goodsCoverImageUri;
    
    // 商品参数一
    @Column
    private String selectOneValue;
    
    // 商品参数二
    @Column
    private String selectTwoValue;
    
    // 商品参数三
    @Column
    private String selectThreeValue;
    
    // 商品销售方式
    @Column
    private Integer goodsSaleType;
    
    // 成交价
    @Column(scale=2)
    private Double price;
    
    // 商品数量
    @Column
    private Long quantity;
    
    // 发货数量
    @Column
    private Long deliveredQuantity;
    
    // 积分
    @Column
    private Long points;
    
    // 是否申请了退还该商品？
    @Column
    private Boolean isReturnApplied;
    
    // 是否已评价
    @Column
    private Boolean isCommented;
    
    // 评论ID
    @Column
    private Long commentId;
    
    // 时间
    @Column
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date saleTime;
    
    // 单位
    @Column
    private String unit;
    
    // 商品规格ID
    @Column
    private Long specId;
    
    // 商品规格值
    @Column
    private String specName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getGoodsSubTitle() {
        return goodsSubTitle;
    }

    public void setGoodsSubTitle(String goodsSubTitle) {
        this.goodsSubTitle = goodsSubTitle;
    }

    public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsCoverImageUri() {
        return goodsCoverImageUri;
    }

    public void setGoodsCoverImageUri(String goodsCoverImageUri) {
        this.goodsCoverImageUri = goodsCoverImageUri;
    }

    public String getSelectOneValue() {
		return selectOneValue;
	}

	public void setSelectOneValue(String selectOneValue) {
		this.selectOneValue = selectOneValue;
	}

	public String getSelectTwoValue() {
		return selectTwoValue;
	}

	public void setSelectTwoValue(String selectTwoValue) {
		this.selectTwoValue = selectTwoValue;
	}

	public String getSelectThreeValue() {
		return selectThreeValue;
	}

	public void setSelectThreeValue(String selectThreeValue) {
		this.selectThreeValue = selectThreeValue;
	}

	public Integer getGoodsSaleType() {
        return goodsSaleType;
    }

    public void setGoodsSaleType(Integer goodsSaleType) {
        this.goodsSaleType = goodsSaleType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public void setDeliveredQuantity(Long deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public Boolean getIsReturnApplied() {
        return isReturnApplied;
    }

    public void setIsReturnApplied(Boolean isReturnApplied) {
        this.isReturnApplied = isReturnApplied;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Boolean getIsCommented() {
        return isCommented;
    }

    public void setIsCommented(Boolean isCommented) {
        this.isCommented = isCommented;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Long getSpecId() {
		return specId;
	}

	public void setSpecId(Long specId) {
		this.specId = specId;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}
    
}
