package com.example.bankapi.controller;

import com.example.bankapi.entity.Card;
import com.example.bankapi.model.MoneyModel;
import com.example.bankapi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/client/account")
public class ClientController {

   @Autowired
   ClientService clientService;

    @GetMapping("/{accountId}")
    public List<Card> getAllCardsByAccount(@PathVariable int accountId){
       return clientService.getAccountListOfCards(accountId);
    }

    @PostMapping("{accountId}")
    public Card createCardByAccount(@PathVariable int accountId){
        return clientService.createNewCard(accountId);
    }

    @GetMapping ("/{accountId}/balance")
    public Double getBalance(@PathVariable int accountId){
        return clientService.getBalance(accountId);
    }

    @PostMapping("{accountId}/increase")
    public Double increaseBalance(@PathVariable int accountId, @RequestBody MoneyModel amount){
        return clientService.increaseBalance(accountId, amount);
    }




}
