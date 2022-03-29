package com.reloadly.bankacc.query.api.handlers;

import com.reloadly.bank.core.commands.FinnishedTransactionCommand;
import com.reloadly.bank.core.commands.OpenedAccountNotificationCommand;
import com.reloadly.bank.core.commands.NotifyCommand;
import com.reloadly.bank.core.enums.TransactionType;
import com.reloadly.bank.core.events.account.AccountClosedEvent;
import com.reloadly.bank.core.events.account.AccountOpenedEvent;
import com.reloadly.bank.core.events.transaction.DepositTransactionProcessingEvent;
import com.reloadly.bank.core.events.transaction.WithdrawTransactionProcessingEvent;
import com.reloadly.bank.core.exceptions.AccountNotFoundException;
import com.reloadly.bank.core.models.BankAccount;
import com.reloadly.bankacc.query.api.repositories.AccountRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@ProcessingGroup("bankaccount-group")
public class AccountEventHandlerImpl implements AccountEventHandler {

    private final AccountRepository accountRepository;
    private final CommandGateway commandGateway;

    @Autowired
    public AccountEventHandlerImpl(AccountRepository accountRepository, CommandGateway commandGateway) {
        this.accountRepository = accountRepository;
        this.commandGateway = commandGateway;
    }

    @EventHandler
    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolderId(event.getAccountHolderId())
                .accountName(event.getAccountName())
                .email(event.getEmail())
                .creationDate(event.getCreationDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .build();

        accountRepository.save(bankAccount);

        // send email notification subscription command
        var command = OpenedAccountNotificationCommand.builder()
                .id(UUID.randomUUID().toString())
                .accountId(event.getId())
                .accountName(event.getAccountName())
                .email(event.getEmail())
                .build();

        commandGateway.sendAndWait(command);
    }

    @EventHandler
    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }

    @EventHandler
    @Override
    public void on(DepositTransactionProcessingEvent event) {
        var bankAccount = accountRepository.findById(event.getId());

        if(bankAccount.isEmpty()) {
            throw new AccountNotFoundException("Fatal Error: Account with id "+event.getId()+" does not exist!");
        }

        bankAccount.get().setBalance(event.getBalance());
        accountRepository.save(bankAccount.get());

        // ToDo send successful deposit transaction command & notification
        var finnishCommand = FinnishedTransactionCommand.builder()
                .id(UUID.randomUUID().toString())
                .amount(event.getAmount())
                .balance(event.getBalance())
                .transactionType(TransactionType.DEPOSIT)
                .creationDate(new Date())
                .build();

        var notifyCommand = NotifyCommand.builder()
                .id(UUID.randomUUID().toString())
                .message("Successfully deposited "+event.getAmount()+" to your account, current balance : "+event.getBalance())
                .email(bankAccount.get().getEmail())
                .build();

        commandGateway.sendAndWait(finnishCommand);
        commandGateway.send(notifyCommand); // don't wait for notification to be sent.
    }

    @EventHandler
    @Override
    public void on(WithdrawTransactionProcessingEvent event) {
        var bankAccount = accountRepository.findById(event.getId());

        if(bankAccount.isEmpty()) {
            throw new AccountNotFoundException("Fatal Error: Account with id "+event.getId()+" does not exist!");
        }

        bankAccount.get().setBalance(event.getBalance());
        accountRepository.save(bankAccount.get());

        // ToDo: send successful withdrawal transaction command
        var finnishCommand = FinnishedTransactionCommand.builder()
                .id(UUID.randomUUID().toString())
                .amount(event.getAmount())
                .balance(event.getBalance())
                .transactionType(TransactionType.WITHDRAWAL)
                .creationDate(new Date())
                .build();

        var notifyCommand = NotifyCommand.builder()
                .id(UUID.randomUUID().toString())
                .message("Successfully withdrew "+event.getAmount()+" from your account, current balance : "+event.getBalance())
                .email(bankAccount.get().getEmail())
                .build();

        commandGateway.sendAndWait(finnishCommand);
        commandGateway.send(notifyCommand); // don't wait for notification to be sent.
    }
}








