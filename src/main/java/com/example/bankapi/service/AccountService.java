package com.example.bankapi.service;

import com.example.bankapi.entity.Account;
import com.example.bankapi.model.MoneyModel;
import com.example.bankapi.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account getBalance(int accountId) {
        return repository.getById(accountId);
    }

    public Account increaseBalance(int accountId, MoneyModel amount) {
        return repository.updateById(accountId, amount);
    }
}
