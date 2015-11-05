package com.ynyes.cslm.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 商品供求
 * 
 * @author libiao
 *
 */
@Entity
public class TdDemand {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	// 要求的内容
	@Column
	private String content;
	
	//提交时间
	@Column
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date time;
	
	//称呼
	@Column
	private String name;
	
	//邮箱
	@Column
	private String mail;
	
	//联系方式
	@Column
	private String mobile;
	
    // 处理状态
    @Column
    private Long statusId;
    
    // 需求\供应
    private Long type;
    	
    // 需求商品类型Id
    @Column
    private Long categoryId;
    
    @Column
    private String category;
    
    // 数量
    @Column
    private Long number;
    
    // 价格
    @Column(scale=2)
    private Double price;
    
    // 时间
    @Column
    private String needTime;
    
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getNeedTime() {
		return needTime;
	}

	public void setNeedTime(String needTime) {
		this.needTime = needTime;
	}

	
    
	
}
