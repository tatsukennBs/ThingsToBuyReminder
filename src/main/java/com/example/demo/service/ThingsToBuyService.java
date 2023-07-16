package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Item;
import com.example.demo.entity.PurchaseInterval;

public interface ThingsToBuyService {
	
	//全件検索し、最終購入日が最新のものを取得
	List<Item> findLatestAll();

	//品目レコード登録用
	void insertItem(Item item);
	
	//購入間隔レコード登録用
	void insertPurchaseInterval(PurchaseInterval purchaseinterval, int itemId) ;
	
	//品目レコード更新用
	void updateItem(Item item);

	//最終購入日更新用
	void updatePurchaseDate(Item item);

	//購入間隔レコード更新用
	void updatePurchaseInterval(PurchaseInterval purchaseinterval, int itemId) ;
	
	//レコード削除用
	void deleteById(int id);
	
	//購入間隔レコード削除用
	void deletePurchaseInterval(int itemId);
}
