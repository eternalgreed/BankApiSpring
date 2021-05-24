package com.example.bankapi.repository;

import com.example.bankapi.entity.Card;

import java.util.List;

public interface CardRepository {
    /**
     * Получение полного списк карт, привязнных к счету
     *
     * @param accountId id счета
     * @return списко всех карт
     */
    List<Card> getAllByAccountId(int accountId);

    /**
     * выпуск новой карты
     *
     * @param accountId id счета
     * @return возвращает выпущенную карту
     */
    Card createByAccountId(int accountId);

    /**
     * измение статуса карты
     *
     * @param cardId id карты
     * @return обновленную карту
     */
    Card updateById(int cardId);

}
