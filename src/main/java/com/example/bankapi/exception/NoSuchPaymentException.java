package com.example.bankapi.exception;

public class NoSuchPaymentException extends RuntimeException {
    public NoSuchPaymentException(String message) {
        super(message);
    }
}
