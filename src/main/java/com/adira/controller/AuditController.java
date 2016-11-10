package com.adira.controller;

import com.adira.dao.AuditRepository;
import com.adira.entity.Audit;
import com.adira.service.audit.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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
    private AuditRepository auditRepository;
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

    @RequestMapping(value = "/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        Audit audit = auditRepository.findOne(id);

        if (audit != null) {
            audit.setDeleted(true);
            auditRepository.save(audit);
        }

        return "redirect:/audits";
    }
}
