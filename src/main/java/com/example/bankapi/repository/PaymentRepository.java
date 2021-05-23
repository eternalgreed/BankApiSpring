package com.example.bankapi.repository;

import com.example.bankapi.dto.input.PaymentDTO;
import com.example.bankapi.entity.Payment;

public interface PaymentRepository {
    Payment updateById(int paymentId);

    Payment create(PaymentDTO dto);

    Payment findById(int paymentId);
}
