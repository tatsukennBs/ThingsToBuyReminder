package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dao.ItemDao;
import com.example.demo.dao.PurchaseIntervalDao;
import com.example.demo.entity.Item;
import com.example.demo.entity.PurchaseInterval;
import com.example.demo.logic.PurchaseIntervalLogic;

@Service
public class ThingsToBuyServiceImpl implements ThingsToBuyService {

	private final ItemDao dao;
	private final PurchaseIntervalDao daoInterval;
	
	public ThingsToBuyServiceImpl(ItemDao dao, PurchaseIntervalDao daoInterval) {
		this.dao = dao;
		this.daoInterval = daoInterval;
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
	
	@Override
	public void insertPurchaseInterval(PurchaseInterval purchaseinterval, int itemId) {
		
		//購入間隔の平均値をエンティティに設定
		setPurchaseDate(purchaseinterval, itemId);
		daoInterval.insertInterval(purchaseinterval, itemId);	
	}

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
	public void updatePurchaseInterval(PurchaseInterval purchaseinterval, int itemId) {
		
		//購入間隔の平均値をエンティティに設定
		setPurchaseDate(purchaseinterval, itemId);
		
		if (daoInterval.updateInterval(purchaseinterval, itemId) == 0) {
			throw new PurchaseIntervalNotFoundException("次回購入予測用の日付が計算できませんでした。");
		}		
	}

	@Override
	public void deleteById(int id) {
		
		if (dao.deleteById(id) == 0) {
			throw new ItemNotFoundException("削除する品目が存在しませんでした。");
		}
	}
	
	@Override
	public void deletePurchaseInterval(int itemId) {
		
		if (daoInterval.deleteInterval(itemId) == 0) {
			throw new PurchaseIntervalNotFoundException("次回購入予測用の日付が計算できませんでした。");
		}
	}
	
	/**
	 *  購入間隔の平均値をエンティティに設定するプライベートメソッド
	 * @param purchaseinterval
	 * @param itemId
	 */
	private void setPurchaseDate(PurchaseInterval purchaseinterval, int itemId) {
		//最終購入日リストを取得
		List<Date> list = dao.getPurchaseDate(itemId);
		
		//最終購入日リストをもとに購入間隔の平均値を算出
		PurchaseIntervalLogic logic = new PurchaseIntervalLogic();
		int result = logic.calcaveragePurchaseInterval(list);
		
		//算出した購入間隔の平均値をエンティティに設定
		purchaseinterval.setPurchaseInterval(result);
	}
}
