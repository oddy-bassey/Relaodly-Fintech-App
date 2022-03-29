package com.revoltcode.bankacc.cmd.api.commands;

import com.revoltcode.bankacc.core.models.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

/*
 * @TargetAggregateIdentifier tells the axon framework which instance of the aggregate
 * should handle the command message
*/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositFundsCommand {

    @TargetAggregateIdentifier
    private String id;

    @Min(value = 1, message = "the deposit amount must be greater than 0")
    private BigDecimal amount;
}
