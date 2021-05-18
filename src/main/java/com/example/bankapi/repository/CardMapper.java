package com.example.bankapi.repository;

import com.example.bankapi.entity.Card;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardMapper implements RowMapper<Card> {
    @Override
    public Card mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Card(resultSet.getInt("id"),
                resultSet.getString("number"),
                resultSet.getInt("account_id"),
                resultSet.getBoolean("is_active"));
    }
}
