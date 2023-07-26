package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.ItemDao;
import com.example.demo.dao.ItemSeqDao;
import com.example.demo.dao.PurchaseIntervalDao;
import com.example.demo.entity.Item;
import com.example.demo.entity.PurchaseInterval;
import com.example.demo.logic.PurchaseIntervalLogic;

@Service
public class ThingsToBuyServiceImpl implements ThingsToBuyService {

	private final ItemDao daoItem;
	private final ItemSeqDao daoSeq;
	private final PurchaseIntervalDao daoInterval;
	
	public ThingsToBuyServiceImpl(ItemDao daoItem, ItemSeqDao daoSeq, PurchaseIntervalDao daoInterval) {
		this.daoItem = daoItem;
		this.daoSeq = daoSeq;
		this.daoInterval = daoInterval;
	}
	
	@Override
	public List<Item> findLatestAll() {
		//DAO(Repository)クラスで取得したものをそのまま返却
		return daoItem.findLatestAll();
	}
	
	@Override
	public Item findById(int itemId) {
		//DAO(Repository)クラスで取得したものをそのまま返却
		return daoItem.findById(itemId);		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void insertItem(Item item) {
		//DAO(Repository)クラスのInsert処理を呼び出し
		daoItem.insertItem(item);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void insertPurchaseDate(Item item) {
		//DAO(Repository)クラスのInsert処理を呼び出し
		daoItem.insertPurchaseDate(item);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void insertItemSeq(int itemId, int itemseq) {
		//DAO(Repository)クラスのInsert処理を呼び出し
		daoSeq.insert(itemId, itemseq);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void insertPurchaseInterval(int itemId) {
		//DAO(Repository)クラスのInsert処理を呼び出し
		daoInterval.insertInterval(itemId);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updateItem(Item item) {
		
		if (daoItem.updateItem(item) == 0) {
			throw new ItemNotFoundException("更新する品目が存在しませんでした");
		}
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updatePurchaseDate(Item item) {
		
		if (daoItem.updatePurchaseDate(item) == 0) {
			throw new ItemNotFoundException("更新する品目が存在しませんでした");
		}
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updatePurchaseInterval(PurchaseInterval purchaseinterval, int itemId) {
		
		//購入間隔の平均値をエンティティに設定
		setPurchaseDate(purchaseinterval, itemId);
		
		if (daoInterval.updateInterval(purchaseinterval, itemId) == 0) {
			throw new PurchaseIntervalNotFoundException("次回購入予測用の日付が計算できませんでした");
		}		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void deleteById(int itemId) {
		
		daoItem.deleteById(itemId);
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void deleteItemSeq(int itemId) {
		
		daoSeq.delete(itemId);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void deletePurchaseInterval(int itemId) {
		
		daoInterval.deleteInterval(itemId);
	}
	
	/**
	 *  購入間隔の平均値をエンティティに設定するプライベートメソッド
	 * @param purchaseinterval
	 * @param itemId
	 */
	private void setPurchaseDate(PurchaseInterval purchaseinterval, int itemId) {
		//最終購入日リストを取得
		List<Date> list = daoItem.getPurchaseDate(itemId);
		
		//最終購入日リストをもとに購入間隔の平均値を算出
		PurchaseIntervalLogic logic = new PurchaseIntervalLogic();
		int result = logic.calcaveragePurchaseInterval(list);
		
		//算出した購入間隔の平均値をエンティティに設定
		purchaseinterval.setPurchaseInterval(result);
	}
}
