package com.luxon.assignment.service.exceptions;

public class NoWallet extends RuntimeException {
    public NoWallet(String message) {
        super(message);
    }
}
