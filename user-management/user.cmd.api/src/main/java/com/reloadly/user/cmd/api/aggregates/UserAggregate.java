package com.reloadly.user.cmd.api.aggregates;

import com.reloadly.user.cmd.api.commands.RegisterUserCommand;
import com.reloadly.user.cmd.api.commands.RemoveUserCommand;
import com.reloadly.user.cmd.api.commands.UpdateUserCommand;
import com.reloadly.user.cmd.api.security.PasswordEncoder;
import com.reloadly.user.cmd.api.security.PasswordEncoderImpl;
import com.reloadly.user.core.events.UserRegisteredEvent;
import com.reloadly.user.core.events.UserRemovedEvent;
import com.reloadly.user.core.events.UserUpdatedEvent;
import com.reloadly.user.core.models.User;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class UserAggregate {

    @AggregateIdentifier
    private String id;
    private User user;

    private final PasswordEncoder passwordEncoder;

    public UserAggregate(){
        this.passwordEncoder = new PasswordEncoderImpl();
    }

    @CommandHandler
    public UserAggregate(RegisterUserCommand command, PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;

        var newUser = command.getUser();
        newUser.setId(command.getId());
        var password = newUser.getAccount().getPassword();
        var hashedPassword = passwordEncoder.hashPassword(password);
        newUser.getAccount().setPassword(hashedPassword);

        var event = UserRegisteredEvent.builder()
                .id(command.getId())
                .user(newUser)
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateUserCommand command){
        var updatedUser = command.getUser();
        updatedUser.setId(command.getId());
        var password = updatedUser.getAccount().getPassword();
        var hashedPassword = passwordEncoder.hashPassword(password);
        updatedUser.getAccount().setPassword(hashedPassword);

        var event = UserUpdatedEvent.builder()
                .id(command.getId())
                .user(updatedUser)
                .build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(RemoveUserCommand command){
        var event = new UserRemovedEvent();
        event.setId(command.getId());
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(UserRegisteredEvent event){
        this.id = event.getId();
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UserUpdatedEvent event){
        this.id = event.getId();
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UserRemovedEvent event){
        this.id = event.getId();
        this.user.setId(event.getId());
    }
}


























