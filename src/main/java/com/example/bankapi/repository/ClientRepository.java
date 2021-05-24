package com.example.bankapi.repository;

import com.example.bankapi.dto.input.ClientDTO;
import com.example.bankapi.dto.input.CounterPartyDTO;
import com.example.bankapi.entity.Client;

import java.util.List;

public interface ClientRepository {

    /**
     * получегие списка всех контрагентов
     *
     * @param clientId id клиента, для которого получаем список
     * @return список контрагентов
     */
    List<Client> getAllByClientId(int clientId);

    /**
     * Создание контрагента
     *
     * @param counterPartyDTO содержит информацию для кого создается контрагент
     * @return созданного контрагента
     */
    Client create(CounterPartyDTO counterPartyDTO);

    /**
     * создание нового клиента (физического лица)
     *
     * @param clientDTO информация о клиента (имя)
     * @return созданного клиента
     */
    Client create(ClientDTO clientDTO);
}
