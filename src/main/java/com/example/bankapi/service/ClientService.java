package com.example.bankapi.service;

import com.example.bankapi.entity.Card;
import com.example.bankapi.model.MoneyModel;
import com.example.bankapi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    ClientRepository repository;

    public List<Card> getAccountListOfCards(int accountId) {
        return repository.getAllCardsForAccount(accountId);
    }

    public Card createNewCard(int accountId) {
        return repository.createNewCard(accountId);
    }

    public Double getBalance(int accountId){
        return repository.getBalance(accountId);
    }

    public Double increaseBalance(int accountId, MoneyModel amount) {
        return repository.increaseBalance(accountId, amount);
    }
}
