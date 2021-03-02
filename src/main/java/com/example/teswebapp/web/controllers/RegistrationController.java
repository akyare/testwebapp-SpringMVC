package com.example.teswebapp.web.controllers;

import com.example.teswebapp.domain.User;
import com.example.teswebapp.domain.VerificationToken;
import com.example.teswebapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.util.Calendar;

@Slf4j
@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registrationConfirm")
    public String confirmRegistration
            (WebRequest request, Model model, @RequestParam("token") String token) {

        log.warn("token: " + token);

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            return "redirect:/forbidden-page";
        }

        log.warn("User from registrationController: " + verificationToken.getUser());
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "redirect:/forbidden-page";
        }

        userService.updateUserEnabled(user, true);

        return "redirect:/index";
    }

    private void login() {

    }
}
