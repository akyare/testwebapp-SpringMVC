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

//        User user = userService.findById(20L);
//        log.info("user.getEncodedPassword(): " + user.getEncodedPassword());
//        log.info("user.getEmail(): " + user.getEmail());
//        String encEncodePass = passwordEncoder.encode(user.getEncodedPassword());
//        log.info("passwordEncoder.encode(user.getEncodedPassword(): " + encEncodePass);
//        log.info(String.valueOf(passwordEncoder.matches("!nXkTT7C4#DNiU", user.getEncodedPassword())));
//        log.info(String.valueOf(passwordEncoder.matches("!nXkTT7C4#DNiU", encEncodePass)));

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
            User registered = userService.registerNewUserAccount(user);
        } catch (UserAlreadyExistException uaeEx) {
            model.addAttribute("message", "An account for that username/email already exists.");
            return "add-user";
        }

        //userService.registerNewUserAccount(user);
        return "redirect:/index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id);

        // default password and confirmPassword to use in thymeleaf if the password is not changed
        // reason is to pass the validations (password not null and password matches confirmPassword
        final String DEFAULT_PWD = "'!nXkTT7C4#DNiU'";

        model.addAttribute("user", user);
        model.addAttribute("defaultPwd", DEFAULT_PWD);

        //log for debugging, to delete
        log.info("user: " + user.getUsername() + ", password: " + user.getEncodedPassword());

        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @ModelAttribute @Valid User user, BindingResult result,
                             @RequestParam(value = "pwdIsNull", required = false) String pwdIsNull, Model model) {

        if (result.hasErrors()) {
            //user.setId(id);
            log.info(result.getFieldErrors().toString());
            log.info("password: " + user.getPassword());
            log.info("confirmPassword: " + user.getConfirmPassword());
            return "update-user";
        }

        log.info("value back from thymeleaf: " + pwdIsNull);

        User userDB = userService.findById(id);

        // only logs for debugging, to delete
        log.info("encodedpassword: " + userDB.getEncodedPassword());
        if(passwordEncoder.matches(user.getPassword(),userDB.getEncodedPassword())) {
            log.info("password matches the DB" );
        } else {
            log.info("password do not match the DB");
        }

        if(!passwordEncoder.matches(user.getPassword(),userDB.getEncodedPassword())) {
            userService.updateUserWithPwd(user);
        } else {
            userService.updateUserNotPwd(user);
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
