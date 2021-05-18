package com.example.bankapi.repository;

import com.example.bankapi.entity.Account;
import com.example.bankapi.model.MoneyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

@Service
public class AccountRepositoryImpl {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public Account getById(int id) {
        final String sql = "SELECT * FROM ACCOUNTS WHERE ID = :account_id";
        MapSqlParameterSource paramMap = new MapSqlParameterSource().addValue("account_id", id);
        return jdbcTemplate.queryForObject(sql, paramMap, new AccountMapper());
    }

    public Account updateById(int id, MoneyModel amount) {
        final String sqlUpdate = "UPDATE ACCOUNTS SET BALANCE = BALANCE + :amount where ID = :account_id";
        final String sqlGetIncreasedBalance = "SELECT * FROM ACCOUNTS WHERE ID = :id";
        MapSqlParameterSource mapParam = new MapSqlParameterSource()
                .addValue("amount", amount.getAmount())
                .addValue("account_id", id);
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(sqlUpdate, mapParam, holder, new String[]{"ID"});
        Number key = holder.getKey();
        MapSqlParameterSource mapAmount = new MapSqlParameterSource().addValue("id", key.intValue());
        return jdbcTemplate.queryForObject(sqlGetIncreasedBalance, mapAmount, new AccountMapper());
    }
}
