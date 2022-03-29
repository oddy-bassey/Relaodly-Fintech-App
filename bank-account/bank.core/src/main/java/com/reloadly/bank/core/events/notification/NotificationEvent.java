package com.reloadly.bank.core.events.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEvent {

    @TargetAggregateIdentifier
    private String id;
    private String message;
    private String email;
}
