package com.adira.controller;

import com.adira.dao.AuditRepository;
import com.adira.dao.AuditTokenRepository;
import com.adira.dto.AuditDto;
import com.adira.entity.Audit;
import com.adira.entity.AuditToken;
import com.adira.entity.Comment;
import com.adira.enumeration.DocumentType;
import com.adira.service.audit.AuditService;
import com.adira.service.email.EmailService;
import com.adira.service.storage.StorageService;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.util.Date;

/**
 * Created by didiyudha on 10/11/16.
 */
@Controller
public class AuditeeController {

    @Autowired
    private AuditTokenRepository auditTokenRepository;
    @Autowired
    private AuditRepository auditRepository;
    @Autowired
    private StorageService storageService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AuditService auditService;

    @RequestMapping(value = "auditee", method = RequestMethod.GET)
    public String showMessage(@RequestParam("isSuccess") boolean isSuccess,
                              Model model) {
        if (isSuccess) {
            model.addAttribute("message","Email has been sent to auditor");
        } else {
            model.addAttribute("message","Can not send email to auditor");
        }

        return "auditeeMessage";
    }

    @RequestMapping(value = "auditee/{auditId}", method = RequestMethod.GET)
    public String findAudit(@PathVariable("auditId") String auditId,
                            @RequestParam("token") String token,
                            Model model) {

        AuditToken auditToken = auditTokenRepository.findByAuditAndToken(auditId, token);

        if (auditToken == null)
            return "login";

        AuditDto auditDto = new AuditDto();
        auditDto.setId(auditId);
        auditDto.setAuditIssue(auditToken.getAudit().getAuditIssue());
        auditDto.setReferenceNo(auditToken.getAudit().getReferenceNo());
        auditDto.setComment(null);
        auditDto.setPic(auditToken.getAudit().getPic());
        auditDto.setAuditor(auditToken.getAudit().getAuditor());
        auditDto.setToken(token);

        model.addAttribute("audit", auditDto);

        return "auditee";
    }

    @RequestMapping(value = "auditee/{auditId}", method = RequestMethod.POST)
    public String uploadFile(@PathVariable("auditId") String auditId,
                             @RequestParam("auditeeFile") MultipartFile auditeeFile,
                             @RequestParam("token") String token,
                             AuditDto auditDto) {

        storageService.store(auditeeFile, DocumentType.AUDITEE);
        Audit audit = auditRepository.findOne(auditId);
        Comment comment = new Comment();
        comment.setContent(auditDto.getComment());
        comment.setFileName(auditeeFile.getOriginalFilename() == null ? null : auditeeFile.getOriginalFilename());
        comment.setCreatedOn(new Date());

        audit.setComments(Sets.newHashSet(comment));
        auditRepository.save(audit);

        try {

            if (auditeeFile.getOriginalFilename() == null) {
                emailService.sendEmail("kioson.xero@gmail.com");
            } else {

                StringBuilder sb = new StringBuilder();
                sb.append("<html><body><b>Auditee send you a reply message</b></body></html>");

                emailService.sendEmailWithAttachment("kioson.xero@gmail.com", auditeeFile.getOriginalFilename(),
                        sb.toString(), DocumentType.AUDITEE);
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        auditService.inActiveToken(auditId, token);
        return "redirect:/auditee?isSuccess=true";
    }

}
