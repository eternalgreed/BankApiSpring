package com.example.bankapi.controller;

import com.example.bankapi.entity.Account;
import com.example.bankapi.model.MoneyModel;
import com.example.bankapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/client/account")
public class AccountController {


    @Autowired
    AccountService accountService;

    @GetMapping("/{accountId}/balance")
    public Account getBalance(@PathVariable int accountId) {
        return accountService.getBalance(accountId);
    }

    @PutMapping("{accountId}/increase")
    public Account increaseBalance(@PathVariable int accountId, @RequestBody MoneyModel amount) {
        return accountService.increaseBalance(accountId, amount);
    }


}
