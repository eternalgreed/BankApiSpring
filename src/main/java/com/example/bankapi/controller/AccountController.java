package com.example.bankapi.controller;

import com.example.bankapi.dto.AccountDTO;
import com.example.bankapi.dto.MoneyDTO;
import com.example.bankapi.entity.Account;
import com.example.bankapi.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}/balance")
    public Account getBalance(@PathVariable int accountId) {
        return accountService.getBalance(accountId);
    }

    @PutMapping("{accountId}/increase")
    public Account increaseBalance(@PathVariable int accountId, @RequestBody MoneyDTO amount) {
        return accountService.increaseBalance(accountId, amount);
    }

    @PostMapping()
    public Account createAccount(@RequestBody AccountDTO dto) {
        return accountService.createAccount(dto);
    }


}
