package com.example.bankapi.repository;

import com.example.bankapi.dto.input.PaymentDTO;
import com.example.bankapi.entity.Payment;
import com.example.bankapi.exception.NoSuchAccountException;
import com.example.bankapi.exception.PaymentException;
import com.example.bankapi.repository.mapper.PaymentMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    //Вынести в константы запросы

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PaymentRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Payment updateById(int paymentId) {
        Payment payment = findById(paymentId);
        if (payment.isApproved()) {
            throw new PaymentException("Данный платеж уже подтвержден");
        }
        final String subtractAmountQuery = "UPDATE ACCOUNTS SET BALANCE = (BALANCE - :amount) WHERE ID = :id AND BALANCE > :amount";
        final String addAmountQuery = "UPDATE ACCOUNTS SET BALANCE = BALANCE + :amount WHERE ID = :id";
        final String approveStatusQuery = "UPDATE PAYMENTS SET APPROVED = true WHERE ID = :payment_id";
        int rowNum = jdbcTemplate.update(subtractAmountQuery, new MapSqlParameterSource()
                .addValue("amount", payment.getAmount())
                .addValue("id", payment.getFromId()));

        if (rowNum < 1) {
            throw new PaymentException("Не хватает средств для перевода");
        }
        jdbcTemplate.update(addAmountQuery, new MapSqlParameterSource()
                .addValue("amount", payment.getAmount())
                .addValue("id", payment.getToId()));
        jdbcTemplate.update(approveStatusQuery, new MapSqlParameterSource()
                .addValue("payment_id", paymentId));
        return findById(paymentId);
    }

    @Override
    public Payment create(PaymentDTO dto) {
        final String sql = "INSERT into PAYMENTS (AMOUNT, APPROVED, FROM_ID, TO_ID) " +
                "VALUES (:amount, false, :from_id, :to_id)";
        MapSqlParameterSource mapParam = new MapSqlParameterSource()
                .addValue("amount", dto.getAmount())
                .addValue("account_id", false)
                .addValue("from_id", dto.getFromId())
                .addValue("to_id", dto.getToId());
        final KeyHolder holder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(sql, mapParam, holder, new String[]{"ID"});
        } catch (DataAccessException e) {
            throw new NoSuchAccountException("Невозможно выполнить платеж для несуществующих счетов!");
        }
        return findById(holder.getKey().intValue());

    }

    @Override
    public Payment findById(int paymentId) {
        final String sql = "SELECT * FROM PAYMENTS WHERE ID = :id";
        MapSqlParameterSource mapParam = new MapSqlParameterSource().addValue("id", paymentId);
        try {
            return jdbcTemplate.queryForObject(sql, mapParam, new PaymentMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new PaymentException("Нет такого платежа!");
        }
    }
}
