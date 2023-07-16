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
	public void insertInterval(PurchaseInterval interval, int itemId) {
		
		String sql = "INSERT INTO things_to_buy.purchase_interval(item_id, purchase_interval, created_by, created_time, updated_by, updated_time)"
				+ " VALUES (?, ?, ?, ?, ?, ?)";
		String currentUser = "SELECT current_user()";
		
		jdbcTemplete.update(sql
				,itemId
				,interval.getPurchaseInterval()
				,currentUser
				,LocalDateTime.now()
				,currentUser				
				,LocalDateTime.now());
	}

	@Override
	public int updateInterval(PurchaseInterval interval, int itemId) {
		String sql = "UPDATE things_to_buy.purchase_interval SET purchase_interval = ?, updated_by=?, updated_time=? WHERE item_id = ?";
		String currentUser = "SELECT current_user()";
		
		int resultInt = jdbcTemplete.update(sql
				,interval.getPurchaseInterval()
				,currentUser
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
}
