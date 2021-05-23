package com.example.bankapi.controller;

import com.example.bankapi.dto.input.ClientDTO;
import com.example.bankapi.dto.input.CounterPartyDTO;
import com.example.bankapi.entity.Client;
import com.example.bankapi.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/counterparties/{clientId}")
    public List<Client> getCounterPartiesList(@PathVariable int clientId) {
        return clientService.getCounterPartiesList(clientId);
    }

    @PostMapping("/counterparties")
    public Client createCounterParty(@RequestBody CounterPartyDTO dto) {
        return clientService.createCounterParty(dto);
    }

    @PostMapping()
    public Client createClient(@RequestBody ClientDTO dto) {
        return clientService.createClient(dto);
    }
}
