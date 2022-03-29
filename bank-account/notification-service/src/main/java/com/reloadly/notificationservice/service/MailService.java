package com.reloadly.notificationservice.service;

import org.springframework.mail.SimpleMailMessage;

public interface MailService {

    public void sendEmailNotification(SimpleMailMessage simpleMailMessage);
}
