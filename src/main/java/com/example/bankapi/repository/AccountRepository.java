package com.example.bankapi.repository;

import com.example.bankapi.entity.Account;
import com.example.bankapi.model.MoneyModel;

public interface AccountRepository {
    Account getById(int id);

    Account updateById(int id, MoneyModel amount);

}
