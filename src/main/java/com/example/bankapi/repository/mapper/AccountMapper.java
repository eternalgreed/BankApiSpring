package com.example.bankapi.repository.mapper;

import com.example.bankapi.entity.Account;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements RowMapper<Account> {
    @Override
    public Account mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Account(resultSet.getInt("id"),
                resultSet.getString("number"),
                resultSet.getDouble("balance"),
                resultSet.getInt("client_id"));
    }
}
