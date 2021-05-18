package com.example.bankapi.repository;

import com.example.bankapi.entity.Card;
import com.example.bankapi.model.MoneyModel;
import com.example.bankapi.utli.CardNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientRepository {
    @Autowired
    private  NamedParameterJdbcTemplate jdbcTemplate;


    public List<Card> getAllCardsForAccount(int accountId){
        final String sql = "SELECT * FROM CARDS WHERE ACCOUNT_ID = :account_id";
        MapSqlParameterSource paramMap = new MapSqlParameterSource().addValue("account_id", accountId);
        return jdbcTemplate.query(sql,paramMap,new CardMapper());
    }


    public Card createNewCard(int accountId) {
        String cardNumber = CardNumberGenerator.generate(16);
        final String sql = "INSERT INTO  CARDS (NUMBER, ACCOUNT_ID) VALUES ( :number, :account_id)";
        final String sqlGetNewCard = "SELECT * FROM CARDS WHERE ID = :id";
        final KeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource paramMap = new MapSqlParameterSource().
                addValue("number",cardNumber ).
                addValue("account_id", accountId);
        jdbcTemplate.update(sql, paramMap, holder, new String[] {"ID"});
        Number key = holder.getKey();
        MapSqlParameterSource paramMapCard = new MapSqlParameterSource()
                .addValue("id",key.intValue());
        return jdbcTemplate.queryForObject(sqlGetNewCard, paramMapCard, new CardMapper());
    }

    public Double getBalance(int accountId){
        final String sql = "SELECT BALANCE FROM ACCOUNTS WHERE ID = :account_id";
        MapSqlParameterSource paramMap = new MapSqlParameterSource().addValue("account_id", accountId);
        return jdbcTemplate.queryForObject(sql,paramMap,Double.class);
    }

    public Double increaseBalance(int accountId, MoneyModel amount) {
        final String sqlUpdate = "UPDATE ACCOUNTS SET BALANCE = BALANCE + :amount where ID = :account_id";
        final String sqlGetIncreasedBalance = "SELECT BALANCE FROM ACCOUNTS WHERE ID = :id";
        MapSqlParameterSource mapParam = new MapSqlParameterSource()
                .addValue("amount", amount.getAmount())
                .addValue("account_id",accountId);
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(sqlUpdate, mapParam, holder, new String[] {"ID"});
        Number key = holder.getKey();
        MapSqlParameterSource mapAmount = new MapSqlParameterSource().addValue("id", key.intValue());
        return jdbcTemplate.queryForObject(sqlGetIncreasedBalance, mapAmount, Double.class);

    }
}
