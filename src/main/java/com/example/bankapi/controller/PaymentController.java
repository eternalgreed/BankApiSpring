package com.example.bankapi.controller;

import com.example.bankapi.dto.PaymentDTO;
import com.example.bankapi.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /* @PostMapping
     public PaymentDTO createPayment(@RequestBody PaymentDTO dto){

     }

 */
    @PostMapping
    public void createPayment(@RequestBody PaymentDTO dto) {
        paymentService.createPayment(dto);
    }

    @PutMapping("{paymentId}/approve")
    public void approvePayment(@PathVariable int paymentId) {
        paymentService.approvePayment(paymentId);
    }


}
