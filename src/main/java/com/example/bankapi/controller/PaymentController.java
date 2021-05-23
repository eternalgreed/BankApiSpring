package com.example.bankapi.controller;

import com.example.bankapi.dto.input.PaymentDTO;
import com.example.bankapi.entity.Payment;
import com.example.bankapi.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Payment createPayment(@RequestBody PaymentDTO dto) {
        return paymentService.createPayment(dto);
    }

    @PutMapping("{paymentId}/approve")
    public Payment approvePayment(@PathVariable int paymentId) {
        return paymentService.approvePayment(paymentId);
    }

}
