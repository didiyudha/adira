package com.adira.controller;

import com.adira.service.email.EmailService;
import com.adira.service.workbook.WorkbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by didiyudha on 21/10/16.
 */
@Controller
public class TestController {
    @Autowired
    private WorkbookService workbookService;

    @RequestMapping(value = "/pushEmail", method = RequestMethod.GET)
    public String getPushEmail(Model model) {
        return "pushEmail";
    }

    @RequestMapping(value = "/pushEmail", method = RequestMethod.POST)
    public String postEmail() {
        workbookService.createWorkBook(null);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "push email berhasil");
        return "pushEmail";
    }
}
