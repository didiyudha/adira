package com.adira.service.email;

import com.adira.dao.AuditDao;
import com.adira.entity.Audit;
import com.adira.service.storage.StorageProperties;
import com.adira.service.workbook.WorkbookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

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
    public void sendEmailWithAttachment(String filePath) throws MessagingException {
        List<Audit> auditList = (List<Audit>) auditDao.findAll();
        Audit audit = auditList.get(0);

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

    private String createHtmlHeader() {
        String htmlHeader = "<html>" +
                "<body>";
        return htmlHeader;
    }

    private String createHeaderTable() {

        String[] header = WorkbookServiceImpl.fileColumn;
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("<table border=1><tr>");

        for (int i = 0; i <= header.length-1; i++) {
            stringBuilder.append("<td>");
            stringBuilder.append(header[i]);
            stringBuilder.append("</td>");
        }

        stringBuilder.append("</tr></table>");

        return stringBuilder.toString();
    }

    private String createHtmlFooter() {
        String htmlFooter = "</body>" +
                "</html>";
        return htmlFooter;
    }

    private String setContentAudit(Audit audit)  {
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("<td>");
        stringBuilder.append(audit.getAuditYear());
        stringBuilder.append("</td>");

        stringBuilder.append("<td>");
        stringBuilder.append(audit.getAuditor());
        stringBuilder.append("</td>");

        stringBuilder.append("<td>");
        stringBuilder.append(audit.getDomain());
        stringBuilder.append("</td>");

        stringBuilder.append("<td>");
        stringBuilder.append(audit.getUnit());
        stringBuilder.append("</td>");

        stringBuilder.append("<td>");
        stringBuilder.append(audit.getPic());
        stringBuilder.append("</td>");

        stringBuilder.append("<td>");
        stringBuilder.append(audit.getAuditIssue());
        stringBuilder.append("</td>");

        stringBuilder.append("<td>");
        stringBuilder.append(audit.getAuditIssueDescription());
        stringBuilder.append("</td>");

        stringBuilder.append("<td>");
        stringBuilder.append(audit.getActionPlan());
        stringBuilder.append("</td>");

        stringBuilder.append("<td>");
        stringBuilder.append(audit.getRiskLevel().toString());
        stringBuilder.append("</td>");

        stringBuilder.append("<td>");
        stringBuilder.append(audit.getOutstandingActionPlan());
        stringBuilder.append("</td>");

        stringBuilder.append("<td>");
        stringBuilder.append(audit.getInitialDueDate());
        stringBuilder.append("</td>");

        stringBuilder.append("<td>");
        stringBuilder.append(audit.getStatus().toString());
        stringBuilder.append("</td>");

        stringBuilder.append("<td>");
        stringBuilder.append(audit.getFirstRescheduled());
        stringBuilder.append("</td>");

        stringBuilder.append("<td>");
        stringBuilder.append(audit.getSecondRescheduled());
        stringBuilder.append("</td>");

        return stringBuilder.toString();
    }
}
