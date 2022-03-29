package com.reloadly.transaction.cmd.api.events;

import com.reloadly.transaction.cmd.api.models.TransactionStatus;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.Date;

public class WithdrawnTransactionEvent {

    @TargetAggregateIdentifier
    private String id;
    private BigDecimal amount;
    private BigDecimal bookBalance;
    private Date createdDate;
    private TransactionStatus transactionStatus;
}
