package com.BankDomain.service.impl;

import jakarta.mail.MessagingException;
import java.util.List;

import jakarta.mail.internet.InternetAddress;
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
            List<String> toEmailList,
            String subject
            ,String body,
            String filePath) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

            helper.setTo(toEmailList.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(body,true);// 'true' means it's HTML

            FileSystemResource file = new FileSystemResource(new File(filePath));
            helper.addAttachment(file.getFilename(),file);

            mailSender.send(mimeMessage);
            logger.info("Email Sent successfully to {}",toEmailList)    ;
        } catch (MessagingException e) {
            logger.error("Email Not Sent {}",toEmailList,e);
            throw new RuntimeException(e);
        }
    }
}
