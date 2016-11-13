package com.adira.service.email;

import com.adira.entity.Audit;
import com.adira.enumeration.DocumentType;

import javax.mail.MessagingException;

/**
 * Created by didiyudha on 21/10/16.
 */
public interface EmailService {
    void sendEmail(String to) throws MessagingException;

    void sendEmailWithAttachment(String to, String filePath, String content, DocumentType documentType) throws MessagingException;

    String constructHtmlContent(Audit audit, String token);
}
