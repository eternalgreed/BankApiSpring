package com.example.bankapi.controller;

import com.example.bankapi.entity.Account;
import com.example.bankapi.entity.Card;
import com.example.bankapi.model.MoneyModel;
import com.example.bankapi.service.AccountService;
import com.example.bankapi.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/client/account")
public class ClientController {

    @Autowired
    CardService cardService;
    @Autowired
    AccountService accountService;

    @GetMapping("/{accountId}")
    public List<Card> getAllCardsByAccount(@PathVariable int accountId) {
        return cardService.getAccountListOfCards(accountId);
    }

    @PostMapping("{accountId}")
    public Card createCardByAccount(@PathVariable int accountId) {
        return cardService.createNewCard(accountId);
    }

    @GetMapping("/{accountId}/balance")
    public Account getBalance(@PathVariable int accountId) {
        return accountService.getBalance(accountId);
    }

    @PostMapping("{accountId}/increase")
    public Account increaseBalance(@PathVariable int accountId, @RequestBody MoneyModel amount) {
        return accountService.increaseBalance(accountId, amount);
    }


}
