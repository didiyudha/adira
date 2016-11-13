package com.adira.service.email;

import com.adira.dao.AuditRepository;
import com.adira.dao.AuditTokenRepository;
import com.adira.entity.Audit;
import com.adira.enumeration.DocumentType;
import com.adira.service.security.SecurityService;
import com.adira.service.storage.StorageProperties;
import com.adira.service.workbook.WorkbookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created by didiyudha on 21/10/16.
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private AuditRepository auditRepository;
    @Autowired
    private StorageProperties properties;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private AuditTokenRepository auditTokenRepository;

    @Override
    public void sendEmail(String to) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("didiyudha@gmail.com");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject("TEST EMAIL VIA APLIKASI");
        mimeMessageHelper.setText("TEST EMAIL VIA APLIKASI BRO");
        mailSender.send(mimeMessage);
        System.out.println("Email has been sent");
    }

    @Override
    public void sendEmailWithAttachment(String to, String filePath, String content, DocumentType documentType) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        FileSystemResource file = null;
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("didiyudha@gmail.com");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject("TEST EMAIL VIA APLIKASI");
        mimeMessageHelper.setText("INI TEST AJA SI", content);

        switch (documentType.toString().toUpperCase()) {
            case "DATA":
                    file = new FileSystemResource(properties.getUploadPath()+ File.separator+filePath);
                break;
            case "AUDITEE":
                file = new FileSystemResource(properties.getAuditeePath()+ File.separator+filePath);
                break;
            default:
                file = new FileSystemResource(properties.getUploadPath()+ File.separator+filePath);
                break;
        }

        mimeMessageHelper.addAttachment(file.getFilename(), file);
        mailSender.send(mimeMessage);

        System.out.println("Email has been sent");
    }

    @Override
    public String constructHtmlContent(Audit audit, String token) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(createHtmlHeader())
                .append(createHeaderTable())
                .append(setContentAudit(audit))
                .append(createLink(audit.getId(), token))
                .append(createHtmlFooter());
        return stringBuilder.toString();
    }

    private String createHtmlHeader() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
                .append("<html>")
                .append("<head>" +
                        "<style>" +
                        "table {" +
                        "    font-family: arial, sans-serif;" +
                        "    border-collapse: collapse;" +
                        "    width: 100%;" +
                        "}" +
                        "" +
                        "td, th {" +
                        "    border: 1px solid #dddddd;" +
                        "    text-align: left;" +
                        "    padding: 8px;" +
                        "}" +
                        "" +
                        "tr:nth-child(even) {" +
                        "    background-color: #dddddd;" +
                        "}" +
                        "</style>" +
                        "</head>")
                .append("<body>");

        return stringBuilder.toString();
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

        stringBuilder.append("</tr>");

        return stringBuilder.toString();
    }

    private String createHtmlFooter() {
        String htmlFooter = "</body>" +
                "</html>";
        return htmlFooter;
    }

    private String setContentAudit(Audit audit)  {
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("<tr>");
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
        stringBuilder.append("</tr>");
        stringBuilder.append("</table>");

        return stringBuilder.toString();
    }

    private String createLink(String auditId, String token) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<div>")
                .append("<p><a href=http://localhost:8081/auditee/"+auditId+"?token="+token+"><h1>Visit our Page</h1></a></p>")
                .append("</div>");
        return stringBuilder.toString();
    }
}
