package com.reloadly.bankacc.cmd.api.commands;

import com.reloadly.bank.core.models.AccountType;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class OpenAccountCommand {

    @TargetAggregateIdentifier
    private String id;

    @NotNull(message = "no account holder ID was supplied")
    private String accountHolderId;

    @NotNull(message = "no account name was supplied")
    private String accountName;

    @Email(message = "please provide a valid email")
    private String email;

    @NotNull(message = "no account type was supplied")
    private AccountType accountType;

    @NotNull(message = "opening balance must be at least 50")
    private BigDecimal openingBalance;
}
