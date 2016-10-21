package com.adira.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Created by didiyudha on 21/10/16.
 */
@Configuration
public class MailConfiguration {
    @Value("${mail.host}")
    private String host;
    @Value("${mail.from}")
    private String from;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        /*Properties props = new Properties();
        props.put("mail.default-encoding","UTF-8");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.username", "didiyudha@gmail.com");
        props.put("mail.password", "lovemother");
        javaMailSender.setJavaMailProperties(props);*/
        return javaMailSender;
    }

    @Bean
    public SimpleMailMessage simpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        /*simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject("TEST 123");*/
        return simpleMailMessage;
    }

}
