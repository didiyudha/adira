package com.adira.service;

import javax.mail.MessagingException;

/**
 * Created by didiyudha on 21/10/16.
 */
public interface EmailService {
    void sendEmail(String from, String to) throws MessagingException;
}
