package com.example.demo.dao;

import com.example.demo.entity.PurchaseInterval;

public interface PurchaseIntervalDao {
	
	//購入間隔（平均）登録用
	void insertInterval(PurchaseInterval interval, int itemId);
	
	//購入間隔（平均）更新用
	int updateInterval(PurchaseInterval interval, int itemId);
	
	//レコード削除用
	int deleteInterval(int itemId);
}
