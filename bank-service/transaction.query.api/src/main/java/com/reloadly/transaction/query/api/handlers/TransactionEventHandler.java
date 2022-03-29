package com.reloadly.transaction.query.api.handlers;

import com.reloadly.bank.core.events.transaction.DepositTransactionFailedEvent;
import com.reloadly.bank.core.events.transaction.DepositedTransactionEvent;
import com.reloadly.bank.core.events.transaction.WithdrawTransactionFailedEvent;
import com.reloadly.bank.core.events.transaction.WithdrawnTransactionEvent;

public interface TransactionEventHandler {

    void on(DepositedTransactionEvent event);
    void on(WithdrawnTransactionEvent event);
    void on(DepositTransactionFailedEvent event);
    void on(WithdrawTransactionFailedEvent event);
}
