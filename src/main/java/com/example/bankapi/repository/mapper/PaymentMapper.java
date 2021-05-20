package com.example.bankapi.repository.mapper;

import com.example.bankapi.entity.Payment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentMapper implements RowMapper<Payment> {
    @Override
    public Payment mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Payment(resultSet.getInt("id"),
                resultSet.getDouble("amount"),
                resultSet.getBoolean("approved"),
                resultSet.getInt("from_id"),
                resultSet.getInt("to_id"));
    }
}
