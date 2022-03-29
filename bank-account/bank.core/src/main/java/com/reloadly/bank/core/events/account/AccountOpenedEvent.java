package com.reloadly.bank.core.events.account;

import com.reloadly.bank.core.models.AccountType;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class AccountOpenedEvent {

    @TargetAggregateIdentifier
    private String id;
    private String accountHolderId;
    private String accountName;
    private String email;
    private AccountType accountType;
    private Date creationDate;
    private BigDecimal openingBalance;
}
