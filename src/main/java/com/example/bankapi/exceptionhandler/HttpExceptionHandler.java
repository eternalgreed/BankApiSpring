package com.example.bankapi.exceptionhandler;

import com.example.bankapi.exception.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(NoSuchAccountException.class)
    public ResponseEntity<ErrorDescription> noSuchAccountExceptionHandler(NoSuchAccountException e) {
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setError(e.getMessage());
        return new ResponseEntity<>(errorDescription, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateCounterPartyException.class)
    public ResponseEntity<ErrorDescription> duplicateCounterPartyExceptionHandler(DuplicateCounterPartyException e) {
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setError(e.getMessage());
        return new ResponseEntity<>(errorDescription, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchClientException.class)
    public ResponseEntity<ErrorDescription> noSuchClientExceptionHandler(NoSuchClientException e) {
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setError(e.getMessage());
        return new ResponseEntity<>(errorDescription, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchCardException.class)
    public ResponseEntity<ErrorDescription> noSuchCardExceptionHandler(NoSuchCardException e) {
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setError(e.getMessage());
        return new ResponseEntity<>(errorDescription, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchPaymentException.class)
    public ResponseEntity<ErrorDescription> noSuchCardExceptionHandler(NoSuchPaymentException e) {
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setError(e.getMessage());
        return new ResponseEntity<>(errorDescription, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SenderBalanceLowException.class)
    public ResponseEntity<ErrorDescription> noSuchCardExceptionHandler(SenderBalanceLowException e) {
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setError(e.getMessage());
        return new ResponseEntity<>(errorDescription, HttpStatus.BAD_REQUEST);
    }


    @Data
    @NoArgsConstructor
    private static class ErrorDescription {
        private String error;
    }
}
