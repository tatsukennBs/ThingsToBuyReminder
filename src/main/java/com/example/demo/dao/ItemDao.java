package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Item;

public interface ItemDao {

	//全件検索し、最終購入日が最新のものを取得
	List<Item> findLatestAll();
	
	//品目レコード登録用
	void insertItem(Item item);
	
	//品目レコード更新用
	int updateItem(Item item);

	//最終購入日更新用
	int updatePurchaseDate(Item item);
	
	//レコード削除用
	int deleteById(int id);
}
