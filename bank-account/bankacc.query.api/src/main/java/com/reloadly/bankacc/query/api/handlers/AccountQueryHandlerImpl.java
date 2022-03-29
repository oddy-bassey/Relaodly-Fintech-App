package com.reloadly.bankacc.query.api.handlers;

import com.reloadly.bank.core.models.BankAccount;
import com.reloadly.bankacc.query.api.dto.AccountLookupResponse;
import com.reloadly.bankacc.query.api.dto.EqualityType;
import com.reloadly.bankacc.query.api.queries.FindAccountByHolderIdQuery;
import com.reloadly.bankacc.query.api.queries.FindAccountByIdQuery;
import com.reloadly.bankacc.query.api.queries.FindAccountsWithBalanceQuery;
import com.reloadly.bankacc.query.api.queries.FindAllAccountsQuery;
import com.reloadly.bankacc.query.api.repositories.AccountRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AccountQueryHandlerImpl implements AccountQueryHandler {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountQueryHandlerImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse findAccountById(FindAccountByIdQuery query) {
        var account = accountRepository.findById(query.getId());

        var response = account.isPresent()?
                new AccountLookupResponse("bank account successfully returned", account.get())
                : new AccountLookupResponse("no bank acconut found for id: "+query.getId());

        return response;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse FindAccountByHolderId(FindAccountByHolderIdQuery query) {
        var account = accountRepository.findByAccountHolderId(query.getAccountHolderId());

        var response = account.isPresent()?
                new AccountLookupResponse("bank account successfully returned", account.get())
                : new AccountLookupResponse("no bank acconut found for holder id: "+query.getAccountHolderId());

        return response;
    }

    @QueryHandler
    @Override
    public AccountLookupResponse FindAllAccounts(FindAllAccountsQuery query) {
        var bankAccountIterator = accountRepository.findAll();

        if (!bankAccountIterator.iterator().hasNext())
            return new AccountLookupResponse("no bank accounts where found!");

        var bankAccounts = new ArrayList<BankAccount>();
        bankAccountIterator.forEach(bankAccounts::add);

        var count = bankAccounts.size();

        return new AccountLookupResponse("successfully returned "+count+" Bank Account(s)", bankAccounts);
    }

    @QueryHandler
    @Override
    public AccountLookupResponse FindAccountsWithBalance(FindAccountsWithBalanceQuery query) {

        var bankAccounts = query.getEqualityType() == EqualityType.GREATER_THAN
                ? accountRepository.findByBalanceGreaterThan(query.getBalance())
                : accountRepository.findByBalanceLessThan(query.getBalance());

        var response = bankAccounts != null && bankAccounts.size() > 0
                ? new AccountLookupResponse("successfully returned "+bankAccounts.size()+" Bank Account(s)", bankAccounts)
                : new AccountLookupResponse("no bank accounts where found!");

        return response;
    }
}
