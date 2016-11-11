package com.adira.controller;

import com.adira.dao.AuditRepository;
import com.adira.dao.AuditTokenRepository;
import com.adira.dto.AuditDto;
import com.adira.entity.Audit;
import com.adira.entity.AuditToken;
import com.adira.entity.Comment;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by didiyudha on 10/11/16.
 */
@Controller
public class AuditeeController {

    @Autowired
    private AuditTokenRepository auditTokenRepository;
    @Autowired
    private AuditRepository auditRepository;

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
    public String uploadFile(@PathVariable("auditId") String auditId, @RequestParam("file") MultipartFile file,
                             @RequestParam("token") String token, AuditDto auditDto) {

        Audit audit = auditRepository.findOne(auditId);
        Comment comment = new Comment();
        comment.setContent(auditDto.getComment());
        comment.setFileName(file.getOriginalFilename());
        audit.setComments(Sets.newHashSet(comment));
        return "redirect:/audits";
    }

}
