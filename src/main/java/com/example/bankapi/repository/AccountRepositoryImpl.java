package com.example.bankapi.repository;

import com.example.bankapi.dto.input.AccountDTO;
import com.example.bankapi.dto.input.MoneyDTO;
import com.example.bankapi.entity.Account;
import com.example.bankapi.exception.NoSuchAccountException;
import com.example.bankapi.exception.NoSuchClientException;
import com.example.bankapi.repository.mapper.AccountMapper;
import com.example.bankapi.utli.NumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AccountRepositoryImpl implements AccountRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static Logger log = LoggerFactory.getLogger(AccountRepositoryImpl.class);

    public AccountRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Account getById(int id) {
        final String sql = "SELECT * FROM ACCOUNTS WHERE ID = :account_id";
        MapSqlParameterSource paramMap = new MapSqlParameterSource().addValue("account_id", id);
        Account account;
        try {
            account = jdbcTemplate.queryForObject(sql, paramMap, new AccountMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchAccountException("Данного счета не существует!");
        }
        return account;
    }

    public Account updateById(int id, MoneyDTO amount) {
        final String sqlUpdate = "UPDATE ACCOUNTS SET BALANCE = BALANCE + :amount where ID = :account_id";
        final String sqlGetIncreasedBalance = "SELECT * FROM ACCOUNTS WHERE ID = :id";
        MapSqlParameterSource mapParam = new MapSqlParameterSource()
                .addValue("amount", amount.getAmount())
                .addValue("account_id", id);
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(sqlUpdate, mapParam, holder, new String[]{"ID"});
        Number key = holder.getKey();
        if (Objects.isNull(key)) {
            throw new NoSuchAccountException("Данного счета не существует!");
        }
        MapSqlParameterSource mapAmount = new MapSqlParameterSource().addValue("id", key.intValue());
        return jdbcTemplate.queryForObject(sqlGetIncreasedBalance, mapAmount, new AccountMapper());
    }

    @Override
    public Account create(AccountDTO dto) {
        String cardNumber = NumberGenerator.generate(20);
        final String sql = "INSERT INTO ACCOUNTS (number, balance, client_id) VALUES ( :number, :balance, :client_id)";
        final String sqlGetNewCard = "SELECT * FROM ACCOUNTS WHERE ID = :id";
        final KeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource paramMap = new MapSqlParameterSource().
                addValue("number", cardNumber).
                addValue("balance", 0.0).
                addValue("client_id", dto.getClientId());
        try {
            jdbcTemplate.update(sql, paramMap, holder, new String[]{"ID"});
        } catch (DataAccessException e) {
            throw new NoSuchClientException("Невозможно выпустить карту для несуществующего клиента!");
        }
        Number key = holder.getKey();

        MapSqlParameterSource paramMapCard = new MapSqlParameterSource()
                .addValue("id", key.intValue());
        return jdbcTemplate.queryForObject(sqlGetNewCard, paramMapCard, new AccountMapper());
    }
}
