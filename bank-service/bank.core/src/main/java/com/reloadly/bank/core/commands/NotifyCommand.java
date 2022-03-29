package com.reloadly.bank.core.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class NotifyCommand {

    @TargetAggregateIdentifier
    private String id;
    private String message;
    private String email;
}
