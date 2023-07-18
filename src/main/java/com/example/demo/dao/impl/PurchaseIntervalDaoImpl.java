package com.example.demo.dao.impl;

import java.time.LocalDateTime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.PurchaseIntervalDao;
import com.example.demo.entity.PurchaseInterval;

@Repository
public class PurchaseIntervalDaoImpl implements PurchaseIntervalDao {
	
	private final JdbcTemplate jdbcTemplete;
	
	public PurchaseIntervalDaoImpl(JdbcTemplate jdbcTemplete) {
		this.jdbcTemplete = jdbcTemplete;
	}

	@Override
	public void insertInterval(int itemId) {
		
		String sql = "INSERT INTO things_to_buy.purchase_interval(item_id, purchase_interval, created_by, created_time, updated_by, updated_time)"
				+ " VALUES (?, ?, ?, ?, ?, ?)";
		
		jdbcTemplete.update(sql
				,itemId
				,0
				,getCurrentUser()
				,LocalDateTime.now()
				,getCurrentUser()				
				,LocalDateTime.now());
	}

	@Override
	public int updateInterval(PurchaseInterval interval, int itemId) {
		String sql = "UPDATE things_to_buy.purchase_interval SET purchase_interval = ?, updated_by=?, updated_time=? WHERE item_id = ?";
		
		int resultInt = jdbcTemplete.update(sql
				,interval.getPurchaseInterval()
				,getCurrentUser()
				,LocalDateTime.now()
				,itemId);
		
		return resultInt;
	}

	@Override
	public int deleteInterval(int itemId) {
		
		String sql = "DELETE FROM things_to_buy.purchase_interval WHERE item_id = ?";
		
		int resultInt = jdbcTemplete.update(sql,itemId);
		
		return resultInt;
	}
	
	/**
	 * 現在のDBユーザーを取得する
	 * @return DBユーザー名
	 */
	private String getCurrentUser() {
		String getCurrentUser = "SELECT current_user()";
		String currentUser = jdbcTemplete.queryForObject(getCurrentUser, String.class);
		return currentUser;
	}
}
