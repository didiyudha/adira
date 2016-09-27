package com.adira.controller;

import com.adira.dao.AuditDao;
import com.adira.entity.Audit;
import com.adira.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by didi-realtime on 27/09/16.
 */
@Controller
@RequestMapping(value = "audits")
public class AuditController {
    @Autowired
    private AuditDao auditDao;
    @Autowired
    private AuditService auditService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String save(@Valid Audit audit) {
        auditDao.save(audit);
        return "index";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String findAll(Model model) {
        List<Audit> audits = auditService.findAll();
        model.addAttribute("audits", audits);
        return "audits";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String findById(@PathVariable("id") String id, Model model) {
        Audit audit = auditService.findById(id);
        model.addAttribute("audit", audit);
        return "auditShow";
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        Audit audit = auditDao.findOne(id);

        if (audit != null) {
            audit.setDeleted(true);
            auditDao.save(audit);
        }

        return "index";
    }
}
