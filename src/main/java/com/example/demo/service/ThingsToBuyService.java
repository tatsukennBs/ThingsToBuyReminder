package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Item;
import com.example.demo.entity.PurchaseInterval;

public interface ThingsToBuyService {
	
	//全件検索し、最終購入日が最新のものを取得
	List<Item> findLatestAll();

	//指定されたIDで検索し、最終購入日が最新のものを取得
	Item findById(int itenId);
	
	//品目レコード登録用（新規登録）
	void insertItem(Item item);
	
	//品目レコード登録用（最終購入日レコード追加）
	void insertPurchaseDate(Item item);
	
	//シーケンステーブル登録用
	void insertItemSeq(int itemId, int itemseq) ;
	
	//購入間隔レコード登録用
	void insertPurchaseInterval(int itemId) ;
	
	//品目レコード更新用
	void updateItem(Item item);

	//最終購入日更新用
	void updatePurchaseDate(Item item);

	//購入間隔レコード更新用
	void updatePurchaseInterval(PurchaseInterval purchaseinterval, int itemId) ;
	
	//レコード削除用
	void deleteById(int id);
	
	//シーケンステーブル削除用
	public void deleteItemSeq(int itemId);
	
	//購入間隔レコード削除用
	void deletePurchaseInterval(int itemId);
}
