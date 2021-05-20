package com.example.bankapi.repository;

import com.example.bankapi.dto.ClientDTO;
import com.example.bankapi.dto.CounterPartyDTO;
import com.example.bankapi.entity.Client;
import com.example.bankapi.exception.DuplicateCounterPartyException;
import com.example.bankapi.repository.mapper.ClientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientRepositoryImpl implements ClientRepository {
    private static Logger log = LoggerFactory.getLogger(AccountRepositoryImpl.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ClientRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Client> getAllByClientId(int clientId) {
        final String sql = "SELECT * FROM CLIENTS JOIN COUNTERPARTIES ON CLIENTS.ID = COUNTERPARTIES.COUNTERPARTY_ID WHERE PARTY_ID = :id";
        MapSqlParameterSource paramMap = new MapSqlParameterSource().addValue("id", clientId);
        return jdbcTemplate.query(sql, paramMap, new ClientMapper());
    }

    @Override
    public Client create(CounterPartyDTO dto) {
        final String sql = "INSERT INTO COUNTERPARTIES  (PARTY_ID, COUNTERPARTY_ID) VALUES ( :party_id, :counterparty_id)";
        final String sqlGetNewCounterParty = "SELECT * FROM CLIENTS WHERE ID = (SELECT COUNTERPARTY_ID FROM COUNTERPARTIES WHERE ID =:id)";
        final KeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource paramMap = new MapSqlParameterSource().
                addValue("party_id", dto.getPartyID()).
                addValue("counterparty_id", dto.getCounterPartyId());
        try {
            jdbcTemplate.update(sql, paramMap, holder, new String[]{"id"});
        } catch (DuplicateKeyException e) {
            throw new DuplicateCounterPartyException("Данный контрагент уже создан для этого клинта!");
        }
        Number key = holder.getKey();

        MapSqlParameterSource paramMapCard = new MapSqlParameterSource()
                .addValue("id", key.intValue());
        return jdbcTemplate.queryForObject(sqlGetNewCounterParty, paramMapCard, new ClientMapper());
    }

    @Override
    public Client create(ClientDTO clientDTO) {
        final String sql = "INSERT INTO CLIENTS (NAME) VALUES (:name)";
        final String sqlGetNewCard = "SELECT * FROM CLIENTS WHERE ID = :id";
        final KeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource paramMap = new MapSqlParameterSource().
                addValue("name", clientDTO.getName());
        jdbcTemplate.update(sql, paramMap, holder, new String[]{"id"});

        Number key = holder.getKey();

        MapSqlParameterSource paramMapCard = new MapSqlParameterSource()
                .addValue("id", key.intValue());
        return jdbcTemplate.queryForObject(sqlGetNewCard, paramMapCard, new ClientMapper());
    }
}
