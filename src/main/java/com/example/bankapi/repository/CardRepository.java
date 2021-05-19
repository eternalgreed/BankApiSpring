package com.example.bankapi.repository;

import com.example.bankapi.entity.Card;

import java.util.List;

public interface CardRepository {
    List<Card> getAllByAccountId(int accountId);

    Card createByAccountId(int accountId);

}
