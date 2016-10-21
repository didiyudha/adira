package com.adira.controller;

import com.adira.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by didiyudha on 21/10/16.
 */
@RestController
public class TestController {
    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/api/main", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> pushEmail() {

        try {
            emailService.sendEmail(null, "kioson.xero.integration@gmail.com");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        Map<String, String> map = new HashMap<>();
        map.put("1", "Okay");
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }
}
