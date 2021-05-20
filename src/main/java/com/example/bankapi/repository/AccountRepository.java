package com.example.bankapi.repository;

import com.example.bankapi.dto.AccountDTO;
import com.example.bankapi.dto.MoneyDTO;
import com.example.bankapi.entity.Account;

public interface AccountRepository {
    Account getById(int id);

    Account updateById(int id, MoneyDTO amount);

    Account create(AccountDTO dto);
}
