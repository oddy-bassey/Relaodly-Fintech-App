package com.reloadly.bank.core.commands;

import com.reloadly.bank.core.enums.TransactionType;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class FinnishedTransactionCommand {

    @TargetAggregateIdentifier
    private String id;
    private BigDecimal amount;
    private BigDecimal balance;
    TransactionType transactionType;
    private Date creationDate;
}
