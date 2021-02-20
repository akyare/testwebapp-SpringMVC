package com.example.teswebapp.web.controllers;

import com.example.teswebapp.domain.User;
import com.example.teswebapp.repository.UserRepository;
import com.example.teswebapp.service.UserService;
import com.example.teswebapp.web.error.UserAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping("/index")
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "index";
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
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id);


        // default password and confirmPassword to use in thymeleaf if the password is not changed
        // reason is to pass the validations (password not null and password matches confirmPassword
        final String DEFAULT_PWD = user.getEncodedPassword().substring(0,20);

        user.setPassword(DEFAULT_PWD);
        user.setConfirmPassword(DEFAULT_PWD);

        model.addAttribute("user", user);


        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @ModelAttribute @Valid User user, BindingResult result,
                             @RequestParam(value = "pwdIsNull", required = false) String pwdIsNull, Model model) {

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

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);

        return "redirect:/index";
    }
}
