package com.example.teswebapp.web.controllers;

import com.example.teswebapp.domain.User;
import com.example.teswebapp.repository.UserRepository;
import com.example.teswebapp.service.UserService;
import com.example.teswebapp.web.error.UserAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class UserController {

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
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
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        model.addAttribute("password",user.getPassword());
        model.addAttribute("confirmPassword",user.getConfirmPassword());

        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        userRepository.save(user);

        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);

        return "redirect:/index";
    }
}
