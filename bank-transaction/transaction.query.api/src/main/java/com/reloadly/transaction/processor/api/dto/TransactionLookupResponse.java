package com.reloadly.transaction.processor.api.dto;

import com.reloadly.transaction.core.dto.BaseResponse;
import com.reloadly.transaction.core.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class AccountLookupResponse extends BaseResponse {

    private List<Transaction> transactions;

    public AccountLookupResponse(String message) {
        super(message);
    }

    public AccountLookupResponse(String message, List<Transaction> transactions) {
        super(message);
        this.transactions = transactions;
    }

    public AccountLookupResponse(String message, Transaction account) {
        super(message);
        this.transactions = new ArrayList<>();
        this.transactions.add(account);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
