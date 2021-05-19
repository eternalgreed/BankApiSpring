package com.example.bankapi.repository;

import com.example.bankapi.entity.Card;
import com.example.bankapi.exception.NoSuchAccountException;
import com.example.bankapi.utli.CardNumberGenerator;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CardRepositoryImpl implements CardRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CardRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Card> getAllByAccountId(int accountId) {
        final String sql = "SELECT * FROM CARDS WHERE ACCOUNT_ID = :account_id";
        MapSqlParameterSource paramMap = new MapSqlParameterSource().addValue("account_id", accountId);
        try {
            return jdbcTemplate.query(sql, paramMap, new CardMapper());
        } catch (DataAccessException e) {
            throw new NoSuchAccountException("Невозможно получить список карт по несуществующему счету!");
        }
    }


    public Card createByAccountId(int accountId) {
        String cardNumber = CardNumberGenerator.generate(16);
        final String sql = "INSERT INTO  CARDS (NUMBER, ACCOUNT_ID) VALUES ( :number, :account_id)";
        final String sqlGetNewCard = "SELECT * FROM CARDS WHERE ID = :id";
        final KeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource paramMap = new MapSqlParameterSource().
                addValue("number", cardNumber).
                addValue("account_id", accountId);
        try {
            jdbcTemplate.update(sql, paramMap, holder, new String[]{"ID"});
        } catch (DataAccessException e) {
            throw new NoSuchAccountException("Невозможно выпустить карту по несуществующему счету!");
        }
        Number key = holder.getKey();

        MapSqlParameterSource paramMapCard = new MapSqlParameterSource()
                .addValue("id", key.intValue());
        return jdbcTemplate.queryForObject(sqlGetNewCard, paramMapCard, new CardMapper());
    }

}
