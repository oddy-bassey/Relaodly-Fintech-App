package com.reloadly.transaction.query.api.handlers;

import com.reloadly.transaction.query.api.dto.EqualityType;
import com.reloadly.transaction.query.api.dto.TransactionLookupResponse;
import com.reloadly.transaction.query.api.model.Transaction;
import com.reloadly.transaction.query.api.queries.FindAllTransactionsQuery;
import com.reloadly.transaction.query.api.queries.FindTransactionByAccountIdQuery;
import com.reloadly.transaction.query.api.queries.FindTransactionByIdQuery;
import com.reloadly.transaction.query.api.queries.FindTransactionsWithAmountQuery;
import com.reloadly.transaction.query.api.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TransactionQueryHandlerImpl implements TransactionQueryHandler {

    private final TransactionRepository transactionRepository;

    public TransactionQueryHandlerImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionLookupResponse findAllTransactions(FindAllTransactionsQuery query) {
        var transactionIterator = transactionRepository.findAll();

        if (!transactionIterator.iterator().hasNext())
            return new TransactionLookupResponse("no transaction where found!");

        var transactions = new ArrayList<Transaction>();
        transactionIterator.forEach(transactions::add);

        var count = transactions.size();

        return new TransactionLookupResponse("Successfully returned "+count+" Transaction(s)", transactions);
    }

    @Override
    public TransactionLookupResponse FindTransactionById(FindTransactionByIdQuery query) {
        var transaction = transactionRepository.findById(query.getTransactionId());

        var response = transaction.isPresent()?
                new TransactionLookupResponse("Transaction successfully returned", transaction.get())
                : new TransactionLookupResponse("No transaction found for transaction id: "+query.getTransactionId());

        return response;
    }

    @Override
    public TransactionLookupResponse FindTransactionByAccountId(FindTransactionByAccountIdQuery query) {
        var transaction = transactionRepository.findByAccountId(query.getAccountId());

        var response = transaction.isPresent()?
                new TransactionLookupResponse("Transaction successfully returned", transaction.get())
                : new TransactionLookupResponse("No transaction found for account id: "+query.getAccountId());

        return response;
    }

    @Override
    public TransactionLookupResponse findTransactionsWithAmount(FindTransactionsWithAmountQuery query) {
        var transactions = query.getEqualityType() == EqualityType.GREATER_THAN
                ? transactionRepository.findByAmountGreaterThan(query.getAmount())
                : transactionRepository.findByAmountLessThan(query.getAmount());

        var response = transactions != null && transactions.size() > 0
                ? new TransactionLookupResponse("successfully returned "+transactions.size()+" transaction(s)", transactions)
                : new TransactionLookupResponse("no bank accounts where found!");

        return response;
    }
}
