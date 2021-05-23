package com.example.bankapi.service;

import com.example.bankapi.dto.input.PaymentDTO;
import com.example.bankapi.entity.Payment;
import com.example.bankapi.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment approvePayment(int paymentId) {
        return repository.updateById(paymentId);
    }

    public Payment createPayment(PaymentDTO dto) {
        return repository.create(dto);
    }
}
