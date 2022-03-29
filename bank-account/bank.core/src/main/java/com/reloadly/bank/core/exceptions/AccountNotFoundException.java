package com.reloadly.bank.core.exceptions;

public class AccountNotFoundException extends IllegalStateException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
