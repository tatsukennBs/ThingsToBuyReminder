package com.example.demo.app;

import java.util.Date;

public class ThingsToBuyForm {
	
	private String itemName;
	
	private String category;
	
	private Date purchaseDate;
	
	private Date purchaseinterval;
	
	public ThingsToBuyForm() {
		
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

	public Date getPurchaseinterval() {
		return purchaseinterval;
	}

	public void setPurchaseinterval(Date purchaseinterval) {
		this.purchaseinterval = purchaseinterval;
	}

}
