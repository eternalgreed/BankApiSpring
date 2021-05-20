package com.example.bankapi.service;

import com.example.bankapi.dto.PaymentDTO;
import com.example.bankapi.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public void approvePayment(int paymentId) {
        repository.updateById(paymentId);
    }

    public void createPayment(PaymentDTO dto) {
        repository.create(dto);
    }
}
