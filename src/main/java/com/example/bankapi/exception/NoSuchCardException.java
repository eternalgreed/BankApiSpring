package com.example.bankapi.exception;

public class NoSuchCardException extends RuntimeException {
    public NoSuchCardException(String message) {
        super(message);
    }
}
