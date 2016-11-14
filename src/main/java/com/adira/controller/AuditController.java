package com.adira.controller;

import com.adira.dao.AuditRepository;
import com.adira.dao.UserRepository;
import com.adira.dto.AuditeeReplyDto;
import com.adira.entity.Audit;
import com.adira.entity.Comment;
import com.adira.entity.User;
import com.adira.service.audit.AuditService;
import com.adira.service.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by didi-realtime on 27/09/16.
 */
@Controller
@RequestMapping(value = "audits")
public class AuditController {
    @Autowired
    private AuditRepository auditRepository;
    @Autowired
    private AuditService auditService;
    @Autowired
    private SecurityService securityService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String showForm(Audit audit) {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String save(@Valid Audit audit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        auditRepository.save(audit);
        return "redirect:/audits";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public void findAll(Model model, @RequestParam(value = "date", required = false) Date date) {
        Date d = date;
        List<Audit> audits = auditService.findAll();
        model.addAttribute("audits", audits);
    }

    @RequestMapping(value = "/{id}/view", method = RequestMethod.GET)
    public String findById(@PathVariable("id") String id, Model model) {
        Audit audit = auditService.findById(id);
        model.addAttribute("audit", audit);
        return "form";
    }

    @RequestMapping(value = "/{id}/reply", method = RequestMethod.GET)
    public String findReply(@PathVariable("id") String id, Model model) {
        Audit audit = auditService.findById(id);
        Comment comment = null;

        if (audit.getComments() != null) {
            for (Comment cm : audit.getComments()) {
                comment = cm;
            }
        }

        AuditeeReplyDto auditeeReplyDto = new AuditeeReplyDto();
        auditeeReplyDto.setAuditId(audit.getId());
        auditeeReplyDto.setReferenceNo(audit.getReferenceNo());
        auditeeReplyDto.setPic(audit.getPic());
        auditeeReplyDto.setAuditIssue(audit.getAuditIssue());
        auditeeReplyDto.setSubject("TEST EMAIL VIA APLIKASI");

        if (comment != null) {
            auditeeReplyDto.setComment(comment.getContent());
            auditeeReplyDto.setFileName(comment.getFileName());
        }

        model.addAttribute("audit", auditeeReplyDto);
        return "auditeeReply";
    }

    @RequestMapping(value = "/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        Audit audit = auditRepository.findOne(id);

        if (audit != null) {
            audit.setDeleted(true);
            auditRepository.save(audit);
        }

        return "redirect:/audits";
    }

    @RequestMapping(value = "/auditeeReply", method = RequestMethod.GET)
    public String audityReply(Model model) {

        User user = securityService.getUserLogedIn();
        List<Audit> audits = (List<Audit>) auditRepository.findAll();

        audits = audits
                    .stream()
                    .filter(audit -> audit.getComments() != null)
                .collect(Collectors.toList());

        Audit audit = audits.get(0);
        AuditeeReplyDto replyDto = new AuditeeReplyDto();

        if (audit != null) {
            Comment comment = null;

            if (audit.getComments() != null) {
                for (Comment cm : audit.getComments()) {
                    comment = cm;
                }

                if (comment != null) {
                    replyDto.setFileName(comment.getFileName());
                    replyDto.setComment(comment.getContent());
                }
            }

            replyDto.setAuditId(audit.getId());
            replyDto.setReferenceNo(audit.getReferenceNo());
            replyDto.setAuditor(audit.getAuditor());
            replyDto.setPic(audit.getPic());


        }

        model.addAttribute("audit", replyDto);

        return "auditeeReply";
    }
}
