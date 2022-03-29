package com.revoltcode.bankacc.cmd.api.aggregates;

import com.revoltcode.bankacc.cmd.api.commands.CloseAccountCommand;
import com.revoltcode.bankacc.cmd.api.commands.DepositFundsCommand;
import com.revoltcode.bankacc.cmd.api.commands.OpenAccountCommand;
import com.revoltcode.bankacc.cmd.api.commands.WithdrawFundsCommand;
import com.revoltcode.bankacc.core.events.AccountClosedEvent;
import com.revoltcode.bankacc.core.events.FundsDepositedEvent;
import com.revoltcode.bankacc.core.events.FundsWithdrawnEvent;
import com.revoltcode.bankacc.core.events.AccountOpenedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.util.Date;

/*
 * @AggregateIdentifier marks id as a reference to the account aggregate that axon uses to know the aggregate that
 * a given command is targeting.
 *
 * @EventSourcingHandler: this informs axon that the annotated
 * method is called when the aggregate is sourced from it's events
 *
 * NOTE:
 * all event sourcing handlers together will form the aggregate
 * they are responsible for changing the state of the account aggregate
 */
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
    public void handle(DepositFundsCommand command){
        var amount = command.getAmount();
        var event = FundsDepositedEvent.builder()
                .id(command.getId())
                .amount(amount)
                .balance(this.balance.add(amount))
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(FundsDepositedEvent event){
        this.balance = this.balance.add(event.getAmount());
    }

    @CommandHandler
    public void handle(WithdrawFundsCommand command){
        var amount = command.getAmount();

        if(this.balance.subtract(amount).compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalStateException("Withdrawal declined, insufficient funds!");
        }

        var event = FundsWithdrawnEvent.builder()
                .id(command.getId())
                .amount(amount)
                .balance(this.balance.subtract(amount))
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(FundsWithdrawnEvent event){
        this.balance = this.balance.subtract(event.getAmount());
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
}
