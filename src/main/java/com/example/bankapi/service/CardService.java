package com.example.bankapi.service;

import com.example.bankapi.entity.Card;
import com.example.bankapi.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    private final CardRepository repository;

    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    public List<Card> getAccountListOfCards(int accountId) {
        return repository.getAllByAccountId(accountId);
    }

    public Card createNewCard(int accountId) {
        return repository.createByAccountId(accountId);
    }

    public Card approveCard(int cardId) {
        return repository.updateById(cardId);
    }
}
