package com.example.demo.dao;

import java.util.Date;
import java.util.List;

import com.example.demo.entity.Item;

public interface ItemDao {

	//全件検索し、最終購入日が最新のものを取得
	List<Item> findLatestAll();
	
	//指定されたIDで検索し、最終購入日が最新のものを取得
	Item findById(int id) ;
	
	//最終購入日のリストを取得
	List<Date>  getPurchaseDate(int id);
	
	//品目レコード登録用（新規登録）
	void insertItem(Item item);
	
	//品目レコード登録用（最終購入日レコード追加）
	void insertPurchaseDate(Item item) ;
	
	//品目レコード更新用
	int updateItem(Item item);

	//最終購入日更新用
	int updatePurchaseDate(Item item);
	
	//レコード削除用
	int deleteById(int id);
}
