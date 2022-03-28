package com.reloadly.bankacc.query.api.handlers;

import com.reloadly.bankacc.core.events.AccountClosedEvent;
import com.reloadly.bankacc.core.events.AccountOpenedEvent;
import com.reloadly.bankacc.core.models.BankAccount;
import com.reloadly.bankacc.query.api.repositories.AccountRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("bankaccount-group")
public class AccountEventHandlerImpl implements AccountEventHandler {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountEventHandlerImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @EventHandler
    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolderId(event.getAccountHolderId())
                .creationDate(event.getCreationDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .build();

        accountRepository.save(bankAccount);
    }

    @EventHandler
    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}








