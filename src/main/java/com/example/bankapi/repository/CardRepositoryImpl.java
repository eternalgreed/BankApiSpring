package com.example.bankapi.repository;

import com.example.bankapi.entity.Card;
import com.example.bankapi.exception.NoSuchAccountException;
import com.example.bankapi.exception.NoSuchCardException;
import com.example.bankapi.repository.mapper.CardMapper;
import com.example.bankapi.utli.NumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;


@Repository
public class CardRepositoryImpl implements CardRepository {

    private static final String SELECT_ALL_FROM_CARDS_BY_ACC_ID =
            "SELECT * FROM CARDS WHERE ACCOUNT_ID = :id";
    private static final String SELECT_CARD_BY_ID = "SELECT * FROM CARDS WHERE ID = :id";
    private static final String INSERT_NEW_CARD =
            "INSERT INTO  CARDS (NUMBER, ACCOUNT_ID) VALUES ( :number, :account_id)";
    private static final String UPDATE_STATUS = "UPDATE CARDS SET IS_ACTIVE = true WHERE ID = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static Logger log = LoggerFactory.getLogger(CardRepositoryImpl.class);

    public CardRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Card> getAllByAccountId(int accountId) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource().addValue("id", accountId);
        try {
            return jdbcTemplate.query(SELECT_ALL_FROM_CARDS_BY_ACC_ID, paramMap, new CardMapper());
        } catch (DataAccessException e) {
            log.info(e.getMessage());
            throw new NoSuchAccountException("Невозможно получить список карт по несуществующему счету!");
        }
    }

    public Card createByAccountId(int accountId) {
        String cardNumber = NumberGenerator.generate(16);
        final KeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource paramMap = new MapSqlParameterSource().
                addValue("number", cardNumber).
                addValue("account_id", accountId);
        try {
            jdbcTemplate.update(INSERT_NEW_CARD, paramMap, holder, new String[]{"ID"});
        } catch (DataAccessException e) {
            log.info(e.getMessage());
            throw new NoSuchAccountException("Невозможно выпустить карту по несуществующему счету!");
        }
        Number key = holder.getKey();

        MapSqlParameterSource paramMapCard = new MapSqlParameterSource()
                .addValue("id", key.intValue());
        return jdbcTemplate.queryForObject(SELECT_CARD_BY_ID, paramMapCard, new CardMapper());
    }

    @Override
    public Card updateById(int cardId) {
        MapSqlParameterSource mapParam = new MapSqlParameterSource()
                .addValue("id", cardId);
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(UPDATE_STATUS, mapParam, holder, new String[]{"ID"});
        Number key = holder.getKey();
        if (Objects.isNull(key)) {
            throw new NoSuchCardException("Данной карты не существует!");
        }
        MapSqlParameterSource mapAmount = new MapSqlParameterSource().addValue("id", key.intValue());
        return jdbcTemplate.queryForObject(SELECT_CARD_BY_ID, mapAmount, new CardMapper());
    }

}
