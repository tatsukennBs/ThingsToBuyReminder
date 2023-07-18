package com.example.demo.dao;

public interface ItemSeqDao {

	//シーケンスID登録用
	void insert(int itemId);
	
	//シーケンスID削除用
	int delete(int itemId);
}
