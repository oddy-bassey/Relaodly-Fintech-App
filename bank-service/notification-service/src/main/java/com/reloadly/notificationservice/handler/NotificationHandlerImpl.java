package com.reloadly.notificationservice.handler;

import com.reloadly.bank.core.events.notification.NotificationEvent;
import com.reloadly.bank.core.events.notification.OpenedAccountNotificationEvent;
import com.reloadly.notificationservice.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationHandlerImpl implements NotificationHandler {

    private final MailService mailService;

    public NotificationHandlerImpl(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void on(OpenedAccountNotificationEvent event) {
        sendCustomNotification(
                "lednontnovo@gmail.com", // replace with organisations mail
                event.getEmail(), "Bank Account Alert",
                String.format("Hi %s, your account with id %S has been created successfully."
                        ,event.getAccountName(), event.getAccountId()));
    }

    @EventHandler
    @Override
    public void on(NotificationEvent event) {
        sendCustomNotification(
                "lednontnovo@gmail.com",
                event.getEmail(), "Bank Account Alert",
                event.getMessage());
    }

    private void sendCustomNotification(String senderEmail, String receiverEmail, String subject, String body){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(senderEmail);
        simpleMailMessage.setTo(receiverEmail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        mailService.sendEmailNotification(simpleMailMessage);
    }
}
