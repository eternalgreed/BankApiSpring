package com.example.bankapi.exception;

public class DuplicateCounterPartyException extends RuntimeException {
    public DuplicateCounterPartyException(String message) {
        super(message);
    }
}
