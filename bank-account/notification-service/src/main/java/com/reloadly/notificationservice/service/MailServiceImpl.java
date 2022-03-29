package com.reloadly.notificationservice.service;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private final MailSender mailSender;

    public MailServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmailNotification(SimpleMailMessage simpleMailMessage) {
        this.mailSender.send(simpleMailMessage);
    }
}
