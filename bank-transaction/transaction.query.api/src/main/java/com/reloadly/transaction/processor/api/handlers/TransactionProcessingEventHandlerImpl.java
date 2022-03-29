package com.reloadly.transaction.processor.api.handlers;

import com.reloadly.transaction.core.events.DepositedTransactionEvent;
import com.reloadly.transaction.core.events.WithdrawnTransactionEvent;
import org.axonframework.config.ProcessingGroup;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("transaction-processing-group")
public class TransactionProcessingEventHandlerImpl implements TransactionProcessingEventHandler {

    @Override
    public void on(DepositedTransactionEvent event) {


    }

    @Override
    public void on(WithdrawnTransactionEvent event) {

    }
}
