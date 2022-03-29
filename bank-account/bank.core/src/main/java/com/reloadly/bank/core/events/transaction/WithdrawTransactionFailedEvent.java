package com.reloadly.bank.core.events.transaction;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class WithdrawTransactionFailedEvent {

    @TargetAggregateIdentifier
    private String id;
    private String accountHolderId;
    private BigDecimal amount;
    private BigDecimal balance;
    private String message;
    private Date creationDate;
}
