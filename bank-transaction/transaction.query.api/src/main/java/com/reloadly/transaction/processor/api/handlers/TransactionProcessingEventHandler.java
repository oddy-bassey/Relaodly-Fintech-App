package com.reloadly.transaction.processor.api.handlers;

import com.reloadly.transaction.core.events.DepositedTransactionEvent;
import com.reloadly.transaction.core.events.WithdrawnTransactionEvent;

public interface TransactionProcessingEventHandler {

    void on(DepositedTransactionEvent event);
    void on(WithdrawnTransactionEvent event);
}
