package com.example.bankapi.service;

import com.example.bankapi.dto.input.ClientDTO;
import com.example.bankapi.dto.input.CounterPartyDTO;
import com.example.bankapi.entity.Client;
import com.example.bankapi.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public List<Client> getCounterPartiesList(int clientId) {
        return repository.getAllByClientId(clientId);
    }

    public Client createCounterParty(CounterPartyDTO dto) {
        return repository.create(dto);
    }

    public Client createClient(ClientDTO dto) {
        return repository.create(dto);
    }
}
