package com.reloadly.bankacc.query.api.handlers;

import com.reloadly.bank.core.events.account.AccountClosedEvent;
import com.reloadly.bank.core.events.account.AccountOpenedEvent;
import com.reloadly.bank.core.events.transaction.DepositTransactionProcessingEvent;
import com.reloadly.bank.core.events.transaction.WithdrawTransactionProcessingEvent;

public interface AccountEventHandler {

    void on(AccountOpenedEvent event);
    void on(AccountClosedEvent event);
    void on(WithdrawTransactionProcessingEvent event);
    void on(DepositTransactionProcessingEvent event);
}
