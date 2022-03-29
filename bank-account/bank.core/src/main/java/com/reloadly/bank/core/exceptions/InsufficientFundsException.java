package com.reloadly.bank.core.exceptions;

public class InsufficientFundsException extends IllegalStateException {

    public InsufficientFundsException(String message) {
        super(message);
    }
}
