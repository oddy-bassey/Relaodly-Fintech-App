package com.reloadly.transaction.query.api.dto;


import com.reloadly.bank.core.dto.BaseResponse;
import com.reloadly.transaction.query.api.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionLookupResponse extends BaseResponse {

    private List<Transaction> transactions;

    public TransactionLookupResponse(String message) {
        super(message);
    }

    public TransactionLookupResponse(String message, List<Transaction> transactions) {
        super(message);
        this.transactions = transactions;
    }

    public TransactionLookupResponse(String message, Transaction account) {
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
