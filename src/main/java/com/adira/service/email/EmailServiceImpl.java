package com.adira.service.email;

import com.adira.dao.AuditDao;
import com.adira.service.storage.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by didiyudha on 21/10/16.
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private AuditDao auditDao;
    @Autowired
    StorageProperties properties;

    @Override
    public void sendEmail(String from, String to) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("didiyudha@gmail.com");
        mimeMessageHelper.setTo("ini@andikanggakusuma.web.id");
        mimeMessageHelper.setSubject("TEST EMAIL VIA APLIKASI");
        mimeMessageHelper.setText("TEST EMAIL VIA APLIKASI BRO");

        FileSystemResource file = new FileSystemResource("adira.docx");
        mimeMessageHelper.addAttachment(file.getFilename(), file);
        mailSender.send(mimeMessage);
        System.out.println("Email has been sent");
    }

    @Override
    public void sendEmailWithAttachment(String from, String to, String filePath) {

    }
}
