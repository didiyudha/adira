package com.adira.controller;

import com.adira.dao.AuditDao;
import com.adira.entity.Audit;
import com.adira.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        auditDao.save(audit);
        return "redirect:/audits";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public void findAll(Model model) {
        List<Audit> audits = auditService.findAll();
        model.addAttribute("audits", audits);
    }

    @RequestMapping(value = "/{id}/view", method = RequestMethod.GET)
    public String findById(@PathVariable("id") String id, Model model) {
        Audit audit = auditService.findById(id);
        model.addAttribute("audit", audit);
        return "form";
    }

    @RequestMapping(value = "/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        Audit audit = auditDao.findOne(id);

        if (audit != null) {
            audit.setDeleted(true);
            auditDao.save(audit);
        }

        return "redirect:/audits";
    }
}
