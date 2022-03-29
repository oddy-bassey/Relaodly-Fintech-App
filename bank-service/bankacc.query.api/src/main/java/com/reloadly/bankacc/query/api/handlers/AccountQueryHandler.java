package com.reloadly.bankacc.query.api.handlers;

import com.reloadly.bankacc.query.api.dto.AccountLookupResponse;
import com.reloadly.bankacc.query.api.queries.FindAccountByHolderIdQuery;
import com.reloadly.bankacc.query.api.queries.FindAccountByIdQuery;
import com.reloadly.bankacc.query.api.queries.FindAccountsWithBalanceQuery;
import com.reloadly.bankacc.query.api.queries.FindAllAccountsQuery;

public interface AccountQueryHandler {
    AccountLookupResponse findAccountById(FindAccountByIdQuery query);
    AccountLookupResponse FindAccountByHolderId(FindAccountByHolderIdQuery query);
    AccountLookupResponse FindAllAccounts(FindAllAccountsQuery query);
    AccountLookupResponse FindAccountsWithBalance(FindAccountsWithBalanceQuery query);
}
