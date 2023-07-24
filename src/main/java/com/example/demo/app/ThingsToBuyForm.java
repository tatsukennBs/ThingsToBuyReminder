package com.example.demo.app;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class ThingsToBuyForm {
	
	@NotBlank (message = "品目名を入力してください")
	@Size(max = 128, message = "品目名は128文字以内で入力してください")
	private String itemName;
	
	@NotBlank (message = "カテゴリを入力してください")
	@Size(max = 128, message = "カテゴリは128文字以内で入力してください")
	private String category;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date purchaseDate;
	
	private int purchaseInterval;
	
	
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

	public int getPurchaseInterval() {
		return purchaseInterval;
	}

	public void setPurchaseInterval(int purchaseInterval) {
		this.purchaseInterval = purchaseInterval;
	}
}
