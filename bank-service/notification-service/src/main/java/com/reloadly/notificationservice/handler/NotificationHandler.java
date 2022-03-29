package com.reloadly.notificationservice.handler;

import com.reloadly.bank.core.events.notification.NotificationEvent;
import com.reloadly.bank.core.events.notification.OpenedAccountNotificationEvent;

public interface NotificationHandler {

    void on(OpenedAccountNotificationEvent event);
    void on(NotificationEvent event);
}
