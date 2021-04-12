package com.example.electronico.service;

import com.example.electronico.exception.ElectronicoException;
import com.example.electronico.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendMail(NotificationEmail notificationEmail) throws ElectronicoException {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("noreply@electronico.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Email Sent");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new ElectronicoException("Exception occurred when sending mail to " + notificationEmail.getRecipient());
        }
    }

}
