package com.example.bankapi.repository;

import com.example.bankapi.dto.input.AccountDTO;
import com.example.bankapi.dto.input.MoneyDTO;
import com.example.bankapi.entity.Account;

public interface AccountRepository {
    /**
     * Возвращает объект счета по id
     *
     * @param id в БД
     * @return экземпляр счета
     */
    Account getById(int id);

    /**
     * Метод для изменеия баланса счета
     *
     * @param id     счета, который изменяем
     * @param amount сумма
     * @return экземпляр обновленного счета
     */
    Account updateById(int id, MoneyDTO amount);

    /**
     * Создание счета
     *
     * @param dto объект, содержащий id клиента
     * @return экземпляр, созданного счета
     */
    Account create(AccountDTO dto);
}
