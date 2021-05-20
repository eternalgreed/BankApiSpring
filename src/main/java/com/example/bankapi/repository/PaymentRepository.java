package com.example.bankapi.repository;

import com.example.bankapi.dto.PaymentDTO;
import com.example.bankapi.entity.Payment;

public interface PaymentRepository {
    void updateById(int paymentId);

    void create(PaymentDTO dto);

    Payment findById(int paymentId);
}
