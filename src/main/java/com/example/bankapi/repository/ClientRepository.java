package com.example.bankapi.repository;

import com.example.bankapi.dto.input.ClientDTO;
import com.example.bankapi.dto.input.CounterPartyDTO;
import com.example.bankapi.entity.Client;

import java.util.List;

public interface ClientRepository {

    List<Client> getAllByClientId(int clientId);

    Client create(CounterPartyDTO counterPartyDTO);

    Client create(ClientDTO clientDTO);
}
