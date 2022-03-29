package com.reloadly.notificationservice.aggregate;

import com.reloadly.bank.core.commands.OpenedAccountNotificationCommand;
import com.reloadly.bank.core.commands.NotifyCommand;
import com.reloadly.bank.core.events.notification.NotificationEvent;
import com.reloadly.bank.core.events.notification.OpenedAccountNotificationEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
@ProcessingGroup("notification-group")
public class NotificationAggregate {

    @AggregateIdentifier
    private String id;
    private String message;
    private String email;

    @CommandHandler
    public NotificationAggregate(NotifyCommand command){
        var event = NotificationEvent.builder()
                .id(command.getId())
                .message(command.getMessage())
                .email(command.getEmail())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(NotificationEvent event){
        this.id = event.getId();
        this.message = event.getMessage();
        this.email = event.getEmail();
    }

    @CommandHandler
    public void on(OpenedAccountNotificationCommand command){
        var event = OpenedAccountNotificationEvent.builder()
                .id(command.getId())
                .email(command.getEmail())
                .build();

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(OpenedAccountNotificationEvent event){
        this.id = event.getId();
        this.email = event.getEmail();
    }
}
