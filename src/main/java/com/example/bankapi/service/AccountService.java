package com.example.bankapi.service;

import com.example.bankapi.dto.AccountDTO;
import com.example.bankapi.dto.MoneyDTO;
import com.example.bankapi.entity.Account;
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

    public Account increaseBalance(int accountId, MoneyDTO amount) {
        return repository.updateById(accountId, amount);
    }

    public Account createAccount(AccountDTO dto) {
        return repository.create(dto);
    }
}
