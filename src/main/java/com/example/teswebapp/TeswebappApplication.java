package com.example.teswebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TeswebappApplication  {

    public static void main(String[] args) {
        SpringApplication.run(TeswebappApplication.class, args);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    }

}
