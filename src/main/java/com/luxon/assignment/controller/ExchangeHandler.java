package com.luxon.assignment.controller;

import com.luxon.assignment.service.exceptions.InsufficientBalance;
import com.luxon.assignment.service.exceptions.NoBalance;
import com.luxon.assignment.service.exceptions.NoWallet;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExchangeHandler {

    @ExceptionHandler({NoWallet.class, NoBalance.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleNoBalanceException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InsufficientBalance.class})
    public ResponseEntity<String> handleInsufficientBalanceException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
