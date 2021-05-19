package com.example.bankapi.exceptionhandler;

import com.example.bankapi.exception.NoSuchAccountException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HttpExceptionHandler {
    @ExceptionHandler(NoSuchAccountException.class)
    public ResponseEntity<ErrorDescription> othersExceptionHandler(NoSuchAccountException e) {
        ErrorDescription errorDescription = new ErrorDescription();
        errorDescription.setError(e.getMessage());
        return new ResponseEntity<>(errorDescription, HttpStatus.NOT_FOUND);
    }

    @Data
    @NoArgsConstructor
    private static class ErrorDescription {
        private String error;
    }
}
