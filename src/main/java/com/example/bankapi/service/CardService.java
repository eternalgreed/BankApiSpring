package com.example.bankapi.service;

import com.example.bankapi.entity.Card;
import com.example.bankapi.repository.CardRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    @Autowired
    CardRepositoryImpl repository;

    public List<Card> getAccountListOfCards(int accountId) {
        return repository.getAllByAccountId(accountId);
    }

    public Card createNewCard(int accountId) {
        return repository.createByAccountId(accountId);
    }

}
