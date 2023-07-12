package com.example.demo.entity;

import java.time.LocalDateTime;

public class PurchaseInterval {
	
	private int purchaseIntervalId;
	private int itemId;
	private int purchaseInterval;
	private String createdBy;
	private LocalDateTime createdTime;
	private String updatedBy;
	private LocalDateTime updatedTime;

	public PurchaseInterval() {
	}

	public int getPurchaseIntervalId() {
		return purchaseIntervalId;
	}

	public void setPurchaseIntervalId(int purchaseIntervalId) {
		this.purchaseIntervalId = purchaseIntervalId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
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
