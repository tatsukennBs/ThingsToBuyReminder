package com.example.demo.service;

import java.util.List;

import com.example.demo.dao.ItemDao;
import com.example.demo.entity.Item;

public class ThingsToBuyServiceImpl implements ThingsToBuyService {

	private final ItemDao dao;
	
	public ThingsToBuyServiceImpl(ItemDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Item> findLatestAll() {
		//DAO(Repository)クラスで取得したものをそのまま返却
		return dao.findLatestAll();
	}

	@Override
	public void insertItem(Item item) {
		//DAO(Repository)クラスのInsert処理を呼び出し
		dao.insertItem(item);
	}

	
	//
	@Override
	public void updateItem(Item item) {
		
		if (dao.updateItem(item) == 0) {
			throw new ItemNotFoundException("更新する品目が存在しませんでした。");
		}
	}

	@Override
	public void updatePurchaseDate(Item item) {
		
		if (dao.updatePurchaseDate(item) == 0) {
			throw new ItemNotFoundException("更新する品目が存在しませんでした。");
		}
	}

	@Override
	public void deleteById(int id) {
		
		if (dao.deleteById(id) == 0) {
			throw new ItemNotFoundException("削除する品目が存在しませんでした。");
		}
	}
}
