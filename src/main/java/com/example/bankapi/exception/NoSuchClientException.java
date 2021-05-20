package com.example.bankapi.exception;

public class NoSuchClientException extends RuntimeException {
    public NoSuchClientException(String message) {
        super(message);
    }
}
