package com.example.teswebapp;

import com.example.teswebapp.domain.User;
import com.example.teswebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TeswebappApplication {


    public static void main(String[] args) {
        SpringApplication.run(TeswebappApplication.class, args);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    }

}
