package com.reloadly.transaction.query.api.handlers;

import com.reloadly.bank.core.enums.TransactionType;
import com.reloadly.bank.core.events.transaction.DepositTransactionFailedEvent;
import com.reloadly.bank.core.events.transaction.DepositedTransactionEvent;
import com.reloadly.bank.core.events.transaction.WithdrawTransactionFailedEvent;
import com.reloadly.bank.core.events.transaction.WithdrawnTransactionEvent;
import com.reloadly.transaction.query.api.model.Transaction;
import com.reloadly.transaction.query.api.repositories.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@ProcessingGroup("transaction-processing-group")
public class TransactionEventHandlerImpl implements TransactionEventHandler {

    private final TransactionRepository transactionRepository;

    public TransactionEventHandlerImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @EventHandler
    @Override
    public void on(DepositedTransactionEvent event) {
        Transaction transaction = Transaction.builder()
                .id(event.getId())
                .accountId(event.getId())
                .amount(event.getAmount())
                .accountBalance(event.getBalance())
                .transactionType(TransactionType.DEPOSIT)
                .createdDate(new Date())
                .build();

        transactionRepository.save(transaction);
    }

    @EventHandler
    @Override
    public void on(WithdrawnTransactionEvent event) {
        Transaction transaction = Transaction.builder()
                .id(event.getId())
                .accountId(event.getId())
                .amount(event.getAmount())
                .accountBalance(event.getBalance())
                .transactionType(TransactionType.DEPOSIT)
                .createdDate(new Date())
                .build();

        transactionRepository.save(transaction);
    }

    @EventHandler
    @Override
    public void on(DepositTransactionFailedEvent event) {

        // ToDO implement
        log.info("DEPOSIT FAILED");
    }

    @EventHandler
    @Override
    public void on(WithdrawTransactionFailedEvent event) {

        // ToDO implement
        log.info("WITHDRAWAL SUCCESSFUL");
    }
}
