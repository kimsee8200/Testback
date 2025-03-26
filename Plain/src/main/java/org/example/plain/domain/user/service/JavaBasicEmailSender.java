package org.example.plain.domain.user.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class JavaBasicEmailSender implements MailSender {
    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setProtocol("smtp");
        mailSender.setDefaultEncoding("UTF-8");

        SimpleMailMessage message = new SimpleMailMessage();
        for (SimpleMailMessage simpleMailMessage : simpleMessages) {
            message.setFrom(simpleMailMessage.getFrom());
            message.setSubject(simpleMailMessage.getSubject());
            message.setText(simpleMailMessage.getText());
            mailSender.send(message);
        }
    }
}
