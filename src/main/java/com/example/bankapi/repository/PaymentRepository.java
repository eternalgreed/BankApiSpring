package com.example.bankapi.repository;

import com.example.bankapi.dto.input.PaymentDTO;
import com.example.bankapi.entity.Payment;

public interface PaymentRepository {
    /**
     * Подтверждение платежа
     *
     * @param paymentId id платежа
     * @return возвращает объект платежа, полученный из БД
     */
    Payment updateById(int paymentId);

    /**
     * Создание нового платежа
     *
     * @param dto информация о платеже
     * @return созданный платеж
     */
    Payment create(PaymentDTO dto);

    /**
     * Получение платежа по id
     *
     * @param paymentId id счета в БД
     * @return экземпляр найденного счета
     */
    Payment findById(int paymentId);
}
