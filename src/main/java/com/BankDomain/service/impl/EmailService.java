package com.BankDomain.service.impl;

import jakarta.mail.MessagingException;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailService {


    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    private final JavaMailSender mailSender;

    public void sendMailWithAttachment(
            String toEmail,
            String subject
            ,String body,
            String filePath) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            helper.addAttachment(file.getFilename(),file);

            mailSender.send(mimeMessage);
            logger.info("Email Sent successfully to {}",toEmail)    ;
        } catch (MessagingException e) {
            logger.error("Email Not Sent {}",toEmail,e);
            throw new RuntimeException(e);
        }
    }
}
