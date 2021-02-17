package com.example.teswebapp.validation;


import com.example.teswebapp.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    private String message;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {

        final User user = (User) obj;
        //return user.getPassword().equals(user.getConfirmPassword());

        //log.info(user.getPassword());
        //log.info(user.getConfirmPassword() + " | " +user.getPassword() + "|" + passwordEncoder.matches(user.getConfirmPassword(), passwordEncoder.encode(user.getPassword())) );


        return user.getPassword().equals(user.getConfirmPassword());
    }

}
