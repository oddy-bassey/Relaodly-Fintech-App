package com.reloadly.bank.core.events.account;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Date;

@Data
@Builder
public class AccountClosedEvent {

    @TargetAggregateIdentifier
    private String id;
    private Date creationDate;
}
