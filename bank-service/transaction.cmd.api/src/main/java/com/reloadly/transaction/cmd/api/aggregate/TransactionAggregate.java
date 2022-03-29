package com.reloadly.transaction.cmd.api.aggregate;

import com.reloadly.bank.core.commands.FinnishedTransactionCommand;
import com.reloadly.bank.core.enums.TransactionType;
import com.reloadly.bank.core.events.transaction.DepositedTransactionEvent;
import com.reloadly.bank.core.events.transaction.WithdrawnTransactionEvent;
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
public class TransactionAggregate {

    @AggregateIdentifier
    private String id;
    private BigDecimal amount;
    private BigDecimal balance;

    @CommandHandler
    public TransactionAggregate(FinnishedTransactionCommand command) {
        if (command.getTransactionType().equals(TransactionType.DEPOSIT)){
            var depositSuccessfulEvent = DepositedTransactionEvent.builder()
                    .id(command.getId())
                    .amount(command.getAmount())
                    .balance(command.getBalance())
                    .creationDate(new Date())
                    .build();

            AggregateLifecycle.apply(depositSuccessfulEvent);
        }else{
            var withdrawalSuccessfulEvent = WithdrawnTransactionEvent.builder()
                    .id(command.getId())
                    .amount(command.getAmount())
                    .balance(command.getBalance())
                    .creationDate(new Date())
                    .build();

            AggregateLifecycle.apply(withdrawalSuccessfulEvent);
        }
    }

    @EventSourcingHandler
    public void on(DepositedTransactionEvent event){
        this.id = event.getId();
        this.amount = event.getAmount();
        this.balance = event.getBalance();
    }

    @EventSourcingHandler
    public void on(WithdrawnTransactionEvent event){
        this.id = event.getId();
        this.amount = event.getAmount();
        this.balance = event.getBalance();
    }
}
