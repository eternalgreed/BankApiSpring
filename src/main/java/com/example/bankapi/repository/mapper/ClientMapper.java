package com.example.bankapi.repository.mapper;

import com.example.bankapi.entity.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper implements RowMapper<Client> {
    @Override
    public Client mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Client(resultSet.getInt("id"), resultSet.getString("name"));
    }
}
