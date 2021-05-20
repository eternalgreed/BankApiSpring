package com.example.bankapi.exception;

public class SenderBalanceLowException extends RuntimeException {
    public SenderBalanceLowException(String message) {
        super(message);
    }
}
