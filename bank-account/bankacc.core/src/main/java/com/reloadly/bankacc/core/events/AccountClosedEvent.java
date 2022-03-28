package com.reloadly.bankacc.core.events;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class AccountClosedEvent {

    @TargetAggregateIdentifier
    private String id;
}
