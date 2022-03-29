package com.reloadly.bankacc.cmd.api.aggregates;

import com.reloadly.bankacc.cmd.api.commands.CloseAccountCommand;
import com.reloadly.bankacc.cmd.api.commands.OpenAccountCommand;
import com.reloadly.bank.core.events.account.AccountClosedEvent;
import com.reloadly.bank.core.events.account.AccountOpenedEvent;
import com.reloadly.bank.core.events.transaction.DepositTransactionProcessingEvent;
import com.reloadly.bank.core.events.transaction.WithdrawTransactionFailedEvent;
import com.reloadly.bank.core.events.transaction.WithdrawTransactionProcessingEvent;
import com.reloadly.bank.core.commands.DepositFundsCommand;
import com.reloadly.bank.core.commands.WithdrawFundsCommand;
import com.reloadly.bank.core.exceptions.InsufficientFundsException;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.util.Date;

@Aggregate
@NoArgsConstructor
public class AccountAggregate {

    @AggregateIdentifier
    private String id;
    private String accountHolderId;
    private BigDecimal balance;

    @CommandHandler
    public AccountAggregate(OpenAccountCommand command){
        var event = AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolderId(command.getAccountHolderId())
                .accountName(command.getAccountName())
                .email(command.getEmail())
                .accountType(command.getAccountType())
                .creationDate(new Date())
                .openingBalance(command.getOpeningBalance())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountOpenedEvent event){
        this.id = event.getId();
        this.accountHolderId = event.getAccountHolderId();
        this.balance = event.getOpeningBalance();
    }

    @CommandHandler
    public void handle(CloseAccountCommand command){
        var event = AccountClosedEvent.builder()
                .id(command.getId())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountClosedEvent event){
        AggregateLifecycle.markDeleted();
    }

    @CommandHandler
    public void handle(DepositFundsCommand command){
        var amount = command.getAmount();
        var event = DepositTransactionProcessingEvent.builder()
                .id(command.getId())
                .accountHolderId(this.accountHolderId)
                .amount(amount)
                .balance(this.balance.add(amount))
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(DepositTransactionProcessingEvent event){
        this.balance = this.balance.add(event.getAmount());
    }

    @CommandHandler
    public void handle(WithdrawFundsCommand command){
        var amount = command.getAmount();

        if(this.balance.subtract(amount).compareTo(BigDecimal.ZERO) < 0){

            String message = "Withdrawal on account with id: "+command.getId()+" declined, insufficient funds!";
            WithdrawTransactionFailedEvent failedEvent = WithdrawTransactionFailedEvent.builder()
                    .id(command.getId())
                    .amount(command.getAmount())
                    .balance(this.balance)
                    .message(message)
                    .creationDate(new Date())
                    .build();

            AggregateLifecycle.apply(failedEvent);
            throw new InsufficientFundsException(message);
        }else {

            var event = WithdrawTransactionProcessingEvent.builder()
                    .id(command.getId())
                    .accountHolderId(this.accountHolderId)
                    .amount(amount)
                    .balance(this.balance.subtract(amount))
                    .build();

            AggregateLifecycle.apply(event);
        }
    }

    @EventSourcingHandler
    public void on(WithdrawTransactionProcessingEvent event){
        this.balance = this.balance.subtract(event.getAmount());
    }
}
