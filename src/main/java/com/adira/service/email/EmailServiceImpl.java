package com.adira.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by didiyudha on 21/10/16.
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SimpleMailMessage simpleMailMessage;

    @Override
    public void sendEmail(String from, String to) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setFrom("didiyudha@gmail.com");
        mimeMessageHelper.setTo("kioson.xero.integration@gmail.com");
        mimeMessageHelper.setSubject("TEST MAIL");
        mimeMessageHelper.setText("TEST MAIL BRO");
        mailSender.send(mimeMessage);
        System.out.println("Email has been sent");
    }

}
