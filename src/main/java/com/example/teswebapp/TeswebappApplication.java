package com.example.teswebapp;

import com.example.teswebapp.email.EmailService;
import com.sun.xml.internal.ws.api.message.Message;
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

@SpringBootApplication
public class TeswebappApplication {

    @Autowired
    private EmailService emailService;

    public static void main(String[] args) {
        SpringApplication.run(TeswebappApplication.class, args);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    }

//    // test to send a simple message from the console
//    @EventListener(ApplicationReadyEvent.class)
//    public void triggerWhenStarts() {
//        emailService.sendSimpleMessage("akyare@hotmail.com","Hi there.","Test from blog app.");
//    }


}
