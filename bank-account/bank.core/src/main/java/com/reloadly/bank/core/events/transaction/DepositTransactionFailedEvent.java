package com.reloadly.bank.core.events.transaction;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class DepositTransactionFailedEvent {

    @TargetAggregateIdentifier
    private String id;
    private BigDecimal amount;
    private BigDecimal balance;
    private Date creationDate;
}
