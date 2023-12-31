package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.Date;

public class Item {
	
	private int itemId;
	private int itemSequence;
	private String itemName;
	private String category;
	private Date purchaseDate;
	private int purchaseIntervalId;
	private int purchaseInterval;
	private String createdBy;
	private LocalDateTime createdTime;
	private String updatedBy;
	private LocalDateTime updatedTime;

	public Item() {
		
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getItemSequence() {
		return itemSequence;
	}
	public void setItemSequence(int itemSequence) {
		this.itemSequence = itemSequence;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public int getPurchaseIntervalId() {
		return purchaseIntervalId;
	}
	public void setPurchaseIntervalId(int purchaseIntervalId) {
		this.purchaseIntervalId = purchaseIntervalId;
	}
	public int getPurchaseInterval() {
		return purchaseInterval;
	}
	public void setPurchaseInterval(int purchaseInterval) {
		this.purchaseInterval = purchaseInterval;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public LocalDateTime getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}
}
