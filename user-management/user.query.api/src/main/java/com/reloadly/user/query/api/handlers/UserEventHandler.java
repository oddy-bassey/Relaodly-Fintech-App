package com.reloadly.user.query.api.handlers;

import com.reloadly.user.core.events.UserRegisteredEvent;
import com.reloadly.user.core.events.UserRemovedEvent;
import com.reloadly.user.core.events.UserUpdatedEvent;

public interface UserEventHandler {

    public  void on(UserRegisteredEvent event);
    public  void on(UserUpdatedEvent event);
    public  void on(UserRemovedEvent event);
}
