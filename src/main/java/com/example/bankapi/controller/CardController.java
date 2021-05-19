package com.example.bankapi.controller;

import com.example.bankapi.entity.Card;
import com.example.bankapi.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client/account/")
public class CardController {
    @Autowired
    CardService cardService;

    @GetMapping("/{accountId}/cards")
    public List<Card> getAllCardsByAccount(@PathVariable int accountId) {
        return cardService.getAccountListOfCards(accountId);
    }

    @PostMapping("{accountId}/cards")
    public Card createCardByAccount(@PathVariable int accountId) {
        return cardService.createNewCard(accountId);
    }
}
