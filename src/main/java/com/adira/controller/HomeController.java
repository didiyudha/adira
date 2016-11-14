package com.adira.controller;

import com.adira.dao.AuditRepository;
import com.adira.entity.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by didi-realtime on 14/11/16.
 */
@Controller
public class HomeController {
    @Autowired
    private AuditRepository auditRepository;

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String home(Model model, @RequestParam(value = "referenceNo", required = false) String referenceNo) {
        List<Audit> audits = null;

        if (referenceNo == null) {
            audits = (List<Audit>) auditRepository.findAll();
        } else {
            referenceNo = "%"+referenceNo+"%";
            audits = auditRepository.findByReferenceNo(referenceNo);
        }

        model.addAttribute("audits", audits);
        return "home";
    }
}
