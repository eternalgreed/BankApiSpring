package com.example.bankapi.repository;

import com.example.bankapi.dto.input.ClientDTO;
import com.example.bankapi.dto.input.CounterPartyDTO;
import com.example.bankapi.entity.Client;
import com.example.bankapi.exception.DuplicateCounterPartyException;
import com.example.bankapi.exception.NoSuchClientException;
import com.example.bankapi.repository.mapper.ClientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    private static final String SELECT_ALL_COUNTERPARTIES =
            "SELECT * FROM CLIENTS JOIN COUNTERPARTIES ON CLIENTS.ID =" +
                    " COUNTERPARTIES.COUNTERPARTY_ID WHERE PARTY_ID = :id";

    private static final String INSERT_NEW_COUNTERPARTY =
            "INSERT INTO COUNTERPARTIES  (PARTY_ID, COUNTERPARTY_ID) VALUES ( :party_id, :counterparty_id)";

    private static final String SELECT_CLIENT_BY_COUNTERPARTY_ID =
            "SELECT * FROM CLIENTS WHERE ID = (SELECT COUNTERPARTY_ID FROM COUNTERPARTIES WHERE ID =:id)";

    private static final String INSERT_NEW_CLIENT = "INSERT INTO CLIENTS (NAME) VALUES (:name)";

    private static final String SELECT_CLIENT_BY_ID = "SELECT * FROM CLIENTS WHERE ID = :id";

    private static Logger log = LoggerFactory.getLogger(ClientRepositoryImpl.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ClientRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Client> getAllByClientId(int clientId) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource().addValue("id", clientId);
        return jdbcTemplate.query(SELECT_ALL_COUNTERPARTIES, paramMap, new ClientMapper());
    }

    @Override
    public Client create(CounterPartyDTO dto) {
        final KeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource paramMap = new MapSqlParameterSource().
                addValue("party_id", dto.getPartyID()).
                addValue("counterparty_id", dto.getCounterPartyId());
        try {
            jdbcTemplate.update(INSERT_NEW_COUNTERPARTY, paramMap, holder, new String[]{"id"});
        } catch (DuplicateKeyException e) {
            log.info(e.getMessage());
            throw new DuplicateCounterPartyException("Данный контрагент уже создан для этого клинта!");
        } catch (DataAccessException e) {
            log.info(e.getMessage());
            throw new NoSuchClientException("Невозможно создать контрагента для несуществующего клиента");
        }
        Number key = holder.getKey();

        MapSqlParameterSource paramMapCard = new MapSqlParameterSource()
                .addValue("id", key.intValue());
        return jdbcTemplate.queryForObject(SELECT_CLIENT_BY_COUNTERPARTY_ID, paramMapCard, new ClientMapper());
    }

    @Override
    public Client create(ClientDTO clientDTO) {
        final KeyHolder holder = new GeneratedKeyHolder();
        MapSqlParameterSource paramMap = new MapSqlParameterSource().
                addValue("name", clientDTO.getName());
        jdbcTemplate.update(INSERT_NEW_CLIENT, paramMap, holder, new String[]{"id"});

        Number key = holder.getKey();

        MapSqlParameterSource paramMapCard = new MapSqlParameterSource()
                .addValue("id", key.intValue());
        return jdbcTemplate.queryForObject(SELECT_CLIENT_BY_ID, paramMapCard, new ClientMapper());
    }
}
