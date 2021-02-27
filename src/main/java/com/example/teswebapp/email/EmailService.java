package com.example.teswebapp.email;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);
}
