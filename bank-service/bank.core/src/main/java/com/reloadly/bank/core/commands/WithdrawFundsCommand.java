package com.reloadly.bank.core.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

/*
 * @TargetAggregateIdentifier tells the axon framework which instance of the aggregate
 * should handle the command message
*/

@Data
@Builder
public class WithdrawFundsCommand {

    @TargetAggregateIdentifier
    private String id;

    @Min(value = 10, message = "the withdrawal amount must be greater than $9")
    private BigDecimal amount;
}
