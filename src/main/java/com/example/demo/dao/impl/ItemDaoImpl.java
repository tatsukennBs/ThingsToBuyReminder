package com.example.demo.dao.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.ItemDao;
import com.example.demo.entity.Item;
import com.example.demo.entity.PurchaseInterval;

@Repository	
public class ItemDaoImpl implements ItemDao {
	
	private final JdbcTemplate jdbcTemplete;

	public ItemDaoImpl(JdbcTemplate jdbcTemplete) {
		this.jdbcTemplete = jdbcTemplete;
	}

	//Itemsテーブルを全件検索し、最終購入日が最新のものを取得
	@Override
	public List<Item> findLatestAll() {
		String sqlItem = "SELECT * FROM"
				+ "  (SELECT *,row_number() over(partition by item_id ORDER BY purchase_date desc) rownum"
				+ " FROM things_to_buy.items"
				+ " ) with_rownum"
				+ " WHERE"
				+ " rownum = 1";
		String sqlPuchaseInterval = "SELECT * FROM things_to_buy.purchase_interval";
	
		//JdbcTempleteを使用しSQL実行
		List<Map<String,Object>> resultList = jdbcTemplete.queryForList(sqlItem);
		List<Map<String,Object>> resultListInterval = jdbcTemplete.queryForList(sqlPuchaseInterval);
		
		//取得結果格納用Listを作成
		List<Item> list = new ArrayList<Item>();

		Item item = new Item();
		PurchaseInterval purchaseInterval = new PurchaseInterval();
		
		//SQL実行結果をEntityにセットする
		for (Map<String,Object>result : resultList) {
			item.setItemName((String)result.get("item_name"));
			item.setCategory((String)result.get("category"));
			item.setPurchaseDate((Date)result.get("purchase_date"));
		}
		for (Map<String,Object>resultinterval : resultListInterval) {
			purchaseInterval.setPurchaseInterval((int)resultinterval.get("purchase_interval"));
		}
		
		//itemにpurchaseIntervalをセット(テーブル結合をオブジェクトとして反映)
		item.setPurchaseinterval(purchaseInterval);

		list.add(item);
		
		return list;
	}

	//Itemsテーブルにレコード登録
	@Override
	public void insertItem(Item item) {
		String sql = "INSERT INTO things_to_buy.items(item_id, item_sequence, item_name, category, purchase_date, created_by, created_time, updated_by, updated_time)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		String getIDMax = "SELECT MAX(sequence_itemid) FROM things_to_buy.items_seq";
		String currentUser = "SELECT current_user()";
		
		jdbcTemplete.update(sql
				,Integer.parseInt(getIDMax) + 1
				,1
				,item.getItemName()
				,item.getCategory()
				,item.getPurchaseDate()
				,currentUser
				,LocalDateTime.now()
				,currentUser				
				,LocalDateTime.now());
	}

	//Itemsテーブルの品目名、カテゴリの更新
	@Override
	public int updateItem(Item item) {
		String sql = "UPDATE things_to_buy.items SET item_name = ?, category = ? WHERE item_id = ?";
		
		int resultInt = jdbcTemplete.update(sql
				,item.getItemName()
				,item.getCategory()
				,item.getItemId());
		
		return resultInt;
	}

	//Itemsテーブル最終購入日を更新
	@Override
	public int updatePurchaseDate(Item item) {
		String preparesql = "SELECT @purchase_date_latest := MAX(purchase_date) FROM things_to_buy.items WHERE item_id = ?";
		String executesql ="UPDATE things_to_buy.items SET purchase_date = ? WHERE item_id = ? AND purchase_date = @purchase_date_latest";
		
		jdbcTemplete.queryForList(preparesql,item.getItemId());
		
		int resultInt = jdbcTemplete.update(executesql
				,item.getPurchaseDate()
				,item.getItemId());
		
		return resultInt;
	}

	//Itemsテーブルのレコード削除
	@Override
	public int deleteById(int id) {
		String sql = "DELETE FROM things_to_buy.items WHERE item_id = ?";
		
		int resultInt = jdbcTemplete.update(sql,id);
		
		return resultInt;
	}
}
