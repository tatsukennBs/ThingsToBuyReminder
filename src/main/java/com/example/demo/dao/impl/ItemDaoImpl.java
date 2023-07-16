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

@Repository	
public class ItemDaoImpl implements ItemDao {
	
	private final JdbcTemplate jdbcTemplete;

	public ItemDaoImpl(JdbcTemplate jdbcTemplete) {
		this.jdbcTemplete = jdbcTemplete;
	}

	//Itemsテーブルを全件検索し、最終購入日が最新のものを取得
	@Override
	public List<Item> findLatestAll() {

		String sql = "SELECT with_rownum.item_id, with_rownum.item_sequence, with_rownum.item_name,"
				+ "with_rownum.category, with_rownum.purchase_date, purchase_interval.purchase_interval_id,"
				+ "purchase_interval.purchase_interval, with_rownum.created_by, with_rownum.created_time,"
				+ "with_rownum.updated_by, with_rownum.created_time FROM"
				+ "  (SELECT *,row_number() over(partition by item_id ORDER BY purchase_date desc) rownum"
				+ " FROM things_to_buy.items"
				+ " ) with_rownum"
				+ " LEFT JOIN things_to_buy.purchase_interval USING(item_id)"
				+ " WHERE rownum = 1";
		
		//JdbcTempleteを使用しSQL実行
		List<Map<String,Object>> resultList = jdbcTemplete.queryForList(sql);
		//List<Map<String,Object>> resultListInterval = jdbcTemplete.queryForList(sqlPuchaseInterval);
		
		//取得結果格納用Listを作成
		List<Item> list = new ArrayList<Item>();
		
		//SQL実行結果をEntityにセットする
		for (Map<String,Object>result : resultList) {
			
			Item item = new Item();
			item.setItemId((int)result.get("item_id"));
			item.setItemName((String)result.get("item_name"));
			item.setCategory((String)result.get("category"));
			item.setPurchaseDate((Date)result.get("purchase_date"));
			if (result.get("purchase_interval")  == null) {
				item.setPurchaseInterval(0);
			} else {
				item.setPurchaseInterval((int)result.get("purchase_interval"));
			}
			list.add(item);
		}
		
		return list;
	}
	
	//itemsテーブルからidに紐づく最終購入日のListを取得
	@Override
	public List<Date> getPurchaseDate(int id) {
		String sql = "SELECT purchase_date FROM things_to_buy.items WHERE item_id = ?";
		List<Date> list = jdbcTemplete.queryForList(sql, Date.class, id);
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
		
		//item_idをエンティティにも設定する
		item.setItemId(Integer.parseInt(getIDMax) + 1);
	}

	//Itemsテーブルの品目名、カテゴリの更新
	@Override
	public int updateItem(Item item) {
		String sql = "UPDATE things_to_buy.items SET item_name = ?, category = ?, updated_by=?, updated_time=? WHERE item_id = ?";
		String currentUser = "SELECT current_user()";
		
		int resultInt = jdbcTemplete.update(sql
				,item.getItemName()
				,item.getCategory()
				,currentUser
				,LocalDateTime.now()
				,item.getItemId());
		
		return resultInt;
	}

	//Itemsテーブル最終購入日を更新
	@Override
	public int updatePurchaseDate(Item item) {
		String preparesql = "SELECT @purchase_date_latest := MAX(purchase_date) FROM things_to_buy.items WHERE item_id = ?";
		String executesql ="UPDATE things_to_buy.items SET purchase_date = ?, updated_by=?, updated_time=? WHERE item_id = ? AND purchase_date = @purchase_date_latest";
		String currentUser = "SELECT current_user()";
		
		jdbcTemplete.queryForList(preparesql,item.getItemId());
		
		int resultInt = jdbcTemplete.update(executesql
				,item.getPurchaseDate()
				,currentUser
				,LocalDateTime.now()
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
