package com.luxon.assignment.service.exceptions;

public class NoBalance extends RuntimeException {
    public NoBalance(String message) {
        super(message);
    }
}
