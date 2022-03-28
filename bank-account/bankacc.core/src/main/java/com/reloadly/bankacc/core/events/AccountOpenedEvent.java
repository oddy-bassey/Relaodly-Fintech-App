package com.reloadly.bankacc.core.events;

import com.reloadly.bankacc.core.models.AccountType;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class AccountOpenedEvent {

    @TargetAggregateIdentifier
    private String id;
    private String accountHolderId;
    private AccountType accountType;
    private Date creationDate;
    private BigDecimal openingBalance;
}
