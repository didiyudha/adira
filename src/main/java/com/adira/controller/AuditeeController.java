package com.adira.controller;

import com.adira.dao.AuditTokenRepository;
import com.adira.dto.AuditDto;
import com.adira.entity.AuditToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by didiyudha on 10/11/16.
 */
@Controller
public class AuditeeController {

    @Autowired
    private AuditTokenRepository auditTokenRepository;

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
        model.addAttribute("audit", auditDto);

        return "auditee";
    }

}
