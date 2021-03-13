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
import org.springframework.security.crypto.password.PasswordEncoder;


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

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        User user = User.builder()
                .username("ab2")
                .encodedPassword(passwordEncoder.encode("!nXkTT7C4#DNiU")).build();

        List<User> users = new ArrayList<>();
        users.add(user);
        List<Authority> authorities = new ArrayList<>();
        authorities.add(Authority.builder().authority("WRITER").users(users).build());

        List<VerificationToken> tokens = new ArrayList<>();
        VerificationToken verificationToken = new VerificationToken("125",user);
        tokens.add(verificationToken);

        user.setAuthority(authorities);
        user.setVerificationToken(tokens);


        User user1 = userRepository.save(user);

//        User user1 = userRepository.findByUsername("ab2");

        log.warn("user from init " + user1.getVerificationToken());

    }




}
