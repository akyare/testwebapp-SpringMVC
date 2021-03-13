package com.example.teswebapp;

import com.example.teswebapp.domain.Authority;
import com.example.teswebapp.domain.User;
import com.example.teswebapp.domain.VerificationToken;
import com.example.teswebapp.email.EmailService;
import com.example.teswebapp.repository.AuthRepository;
import com.example.teswebapp.repository.UserRepository;
import com.example.teswebapp.repository.VerifTokenRepository;
import com.sun.xml.internal.ws.api.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
@SpringBootApplication
public class TeswebappApplication {

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerifTokenRepository verifTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthRepository authRepository;

    public static void main(String[] args) {
        SpringApplication.run(TeswebappApplication.class, args);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    }

//    // test to send a simple message from the console
//    @EventListener(ApplicationReadyEvent.class)
//    public void triggerWhenStarts() {
//        emailService.sendSimpleMessage("akyare@hotmail.com","Hi there.","Test from blog app.");
//    }

//         test to send a simple message from the console
    @EventListener(ApplicationReadyEvent.class)
    public void triggerWhenStarts() {
//        List<Authority> authorities = new ArrayList<>();
//        authorities.add(Authority.builder().authority("WRITER").build());
//
//        User user = User.builder().username("ab2").authority(authorities).build();
//        userRepository.save(user);

        User user = userRepository.findByUsername("ab2");

        log.warn("user from init " + user.getAuthority());

    }


}
