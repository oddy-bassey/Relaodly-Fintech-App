package com.reloadly.bank.core.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenedAccountNotificationCommand {

    @TargetAggregateIdentifier
    private String id;
    private String accountId;
    private String accountName;
    private String email;
}
