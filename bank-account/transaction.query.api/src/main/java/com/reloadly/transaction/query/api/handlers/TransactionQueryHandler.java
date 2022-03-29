package com.reloadly.transaction.query.api.handlers;

import com.reloadly.transaction.query.api.dto.TransactionLookupResponse;
import com.reloadly.transaction.query.api.queries.FindAllTransactionsQuery;
import com.reloadly.transaction.query.api.queries.FindTransactionByAccountIdQuery;
import com.reloadly.transaction.query.api.queries.FindTransactionByIdQuery;
import com.reloadly.transaction.query.api.queries.FindTransactionsWithAmountQuery;

public interface TransactionQueryHandler {
    TransactionLookupResponse findAllTransactions(FindAllTransactionsQuery query);
    TransactionLookupResponse FindTransactionById(FindTransactionByIdQuery query);
    TransactionLookupResponse FindTransactionByAccountId(FindTransactionByAccountIdQuery query);
    TransactionLookupResponse findTransactionsWithAmount(FindTransactionsWithAmountQuery query);
}
