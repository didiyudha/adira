package com.adira.controller;

import com.adira.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by didiyudha on 21/10/16.
 */
@Controller
public class TestController {
    @RequestMapping(value = "/pushEmail", method = RequestMethod.GET)
    public String getPushEmail() {
        return "pushEmail";
    }

    @RequestMapping(value = "/pushEmail", method = RequestMethod.POST)
    public String postEmail() {
        return "pushEmail";
    }
}
