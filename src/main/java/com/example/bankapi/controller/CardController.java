package com.example.bankapi.controller;

import com.example.bankapi.entity.Card;
import com.example.bankapi.service.CardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/{accountId}/cards")
    public List<Card> getAllCardsByAccount(@PathVariable int accountId) {
        return cardService.getAccountListOfCards(accountId);
    }

    @PostMapping("{accountId}/cards")
    public Card createCardByAccount(@PathVariable int accountId) {
        return cardService.createNewCard(accountId);
    }

    @PutMapping("{cardId}/activate")
    public Card approveCard(@PathVariable int cardId) {
        return cardService.approveCard(cardId);
    }
}
