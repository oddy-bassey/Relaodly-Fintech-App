package com.reloadly.transaction.cmd.api.dto;


import com.reloadly.bankacc.core.models.BankAccount;
import com.reloadly.transaction.core.dto.BaseResponse;

import java.util.ArrayList;
import java.util.List;

public class AccountLookupResponse extends BaseResponse {

    private List<BankAccount> accounts;

    public AccountLookupResponse(String message) {
        super(message);
    }

    public AccountLookupResponse(String message, List<BankAccount> accounts) {
        super(message);
        this.accounts = accounts;
    }

    public AccountLookupResponse(String message, BankAccount account) {
        super(message);
        this.accounts = new ArrayList<>();
        this.accounts.add(account);
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BankAccount> accounts) {
        this.accounts = accounts;
    }
}
