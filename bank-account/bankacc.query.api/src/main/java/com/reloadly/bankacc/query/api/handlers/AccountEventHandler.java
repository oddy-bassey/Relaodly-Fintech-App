package com.reloadly.bankacc.query.api.handlers;

import com.reloadly.bankacc.core.events.AccountClosedEvent;
import com.reloadly.bankacc.core.events.AccountOpenedEvent;

public interface AccountEventHandler {

    void on(AccountOpenedEvent event);
    void on(AccountClosedEvent event);
}
