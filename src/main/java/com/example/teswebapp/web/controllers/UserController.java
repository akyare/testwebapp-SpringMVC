package com.example.teswebapp.web.controllers;

import com.example.teswebapp.domain.User;
import com.example.teswebapp.password.RandomPasswordGenerator;
import com.example.teswebapp.repository.UserRepository;
import com.example.teswebapp.service.UserService;
import com.example.teswebapp.web.error.InvalidOldPasswordException;
import com.example.teswebapp.web.error.UserAlreadyExistException;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class UserController {


    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserService userService;

    public UserController(PasswordEncoder passwordEncoder, UserRepository userRepository, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping({"/forbidden-page"})
    public String showForbiddenPage(Model model) {

        return "forbidden-page";
    }

    @GetMapping({"/", "/index"})
    public String showUserList(Model model) {
        // Add all null check and authentication check before using. Because this is global
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(authentication.getName());

        String msgUsername = "visitor";
        String idUser = "";
        if (user != null) {
            log.warn("from showUserList username: " + user.getUsername());
            msgUsername = user.getUsername();
            idUser = user.getId().toString();
        }

        model.addAttribute("msgUsername", msgUsername);
        model.addAttribute("idUser", idUser);

        //model.addAttribute("loggedinuser", authentication.getName());
        model.addAttribute("roles", authentication.getAuthorities());

        model.addAttribute("users", userRepository.findAll());

        return "index";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/index"; //You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());

        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.debug("Error in bindingResult!!!");
            return "add-user";
        }

        try {
            userService.registerNewUserAccount(user);
        } catch (UserAlreadyExistException uaeEx) {
            model.addAttribute("message", "An account for that username/email already exists.");
            return "add-user";
        }

        return "redirect:/index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Principal principal, Model model) {
        User user = userService.findById(id);

        log.warn(principal.getName());

        // The user should only access his own data, otherwise redirect to another page
        if (!principal.getName().equals(user.getUsername())) {
            return "redirect:/forbidden-page";
        }

        // DEFAULT_PWD set to password and confirmPassword and used in thymeleaf if the password is not changed
        // reason is to pass the validations (password not null and password matches confirmPassword)
        RandomPasswordGenerator passGen = new RandomPasswordGenerator();
        final String DEFAULT_PWD = passGen.generatePassayPassword();
        user.setPassword(DEFAULT_PWD);
        user.setConfirmPassword(DEFAULT_PWD);

        model.addAttribute("user", user);
        model.addAttribute("defaultPwd", DEFAULT_PWD);
        log.warn("the generated default pwd: " + DEFAULT_PWD);

        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @ModelAttribute @Valid User user, BindingResult result,
                             Principal principal, Model model) {

        log.warn(principal.getName());

        // The user should only access his own data, otherwise redirect to another page
        if (!principal.getName().equals(user.getUsername())) {
            return "redirect:/forbidden-page";
        }


        log.warn("password: " + user.getPassword());
        log.warn("confirmpwd: " + user.getConfirmPassword());

        if (result.hasErrors()) {
            log.debug("Error in bindingResult!!!");
            return "update-user";
        }

        try {
            userService.updateUserNotPwd(user);
        } catch (UserAlreadyExistException uaeEx) {
            model.addAttribute("message", "An account for that username/email already exists.");
            return "update-user";
        }

        return "redirect:/index";
    }

    @GetMapping("/edit pwd/{id}")
    public String getUpdatePwd(@PathVariable("id") long id, Principal principal, Model model) {
        User user = userService.findById(id);

        log.warn(principal.getName());

        // The user should only access his own data, otherwise redirect to another page
        if (!principal.getName().equals(user.getUsername())) {
            return "redirect:/forbidden-page";
        }

        model.addAttribute("user", user);

        return "update-pwd";
    }

    @PostMapping("/update pwd/{id}")
    public String postUpdatePwd(@PathVariable("id") long id, @ModelAttribute @Valid User user, BindingResult result,
                                Principal principal, Model model) {


        log.warn(principal.getName());

        // The user should only access his own data, otherwise redirect to another page
        if (!principal.getName().equals(user.getUsername())) {
            return "redirect:/forbidden-page";
        }


        log.warn("pwd from postUpdatePwd: " + user.getPassword());
        log.warn("oldPwd from postUpdatePwd: " + user.getOldPassword());
        log.warn("confirmPwd from postUpdatePwd: " + user.getConfirmPassword());

        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> log.warn(objectError.toString()));
            return "update-pwd";
        }


        User userDb = userService.findById(id);

        boolean isOldPwdOk = passwordEncoder.matches(user.getOldPassword(), userDb.getEncodedPassword());
        log.warn("is old pwd ok: " + isOldPwdOk);

        if (!userService.checkIfValidOldPassword(userDb, user.getOldPassword())) {
            log.warn("the old password is not correct.");
            model.addAttribute("messageInvalidOldPwd", "The old password is invalid.");
            return "update-pwd";
        }

        log.warn("validation on old password is ok.");

        userService.updateUserPwd(id, user.getPassword());

        return "redirect:/index";
    }


    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id);
        userService.deleteByUsername(user.getUsername());

        return "redirect:/index";
    }


}
