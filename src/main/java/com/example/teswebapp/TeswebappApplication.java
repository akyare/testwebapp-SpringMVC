package com.example.teswebapp;

import com.example.teswebapp.domain.VerificationToken;
import com.example.teswebapp.email.EmailService;
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
import java.util.Properties;

@Slf4j
@SpringBootApplication
public class TeswebappApplication {

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerifTokenRepository verifTokenRepository;

    public static void main(String[] args) {
        SpringApplication.run(TeswebappApplication.class, args);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    }

//    // test to send a simple message from the console
//    @EventListener(ApplicationReadyEvent.class)
//    public void triggerWhenStarts() {
//        emailService.sendSimpleMessage("akyare@hotmail.com","Hi there.","Test from blog app.");
//    }

        // test to send a simple message from the console
//    @EventListener(ApplicationReadyEvent.class)
//    public void triggerWhenStarts() {
//        String token = "d7163c6d-064f-4ec0-8ade-891df3709359";
//        VerificationToken verifToken = verifTokenRepository.findByToken(token);
//        log.warn("token from init " + verifToken.getUserId());
//
//    }


}
