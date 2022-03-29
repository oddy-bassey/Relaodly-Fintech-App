package com.reloadly.transaction.cmd.api.events;

import com.reloadly.transaction.cmd.api.models.TransactionStatus;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class DepositedTransactionEvent {

    @TargetAggregateIdentifier
    private String id;
    private BigDecimal amount;
    private BigDecimal balance;
    private BigDecimal bookBalance;
    private Date createdDate;
    private TransactionStatus transactionStatus;
}
