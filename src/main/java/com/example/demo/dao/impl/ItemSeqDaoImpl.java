package com.example.demo.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.ItemSeqDao;

@Repository	
public class ItemSeqDaoImpl implements ItemSeqDao {
	
	private final JdbcTemplate jdbcTemplete;

	public ItemSeqDaoImpl(JdbcTemplate jdbcTemplete) {
		this.jdbcTemplete = jdbcTemplete;
	}

	@Override
	public void insert(int itemId, int itemseq) {
		String sql = "INSERT INTO things_to_buy.items_seq(sequence_itemid, sequence_itemsequence)"
				+ " VALUES (?, ?)";
		
		jdbcTemplete.update(sql
				,itemId
				,itemseq);
	}

	@Override
	public int delete(int itemId) {
		String sql = "DELETE FROM things_to_buy.items_seq WHERE sequence_itemid = ?";
		
		int resultInt = jdbcTemplete.update(sql, itemId);
		
		return resultInt;
	}
}
