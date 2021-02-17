package com.example.teswebapp.validation;


import com.example.teswebapp.domain.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    private String message;

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final User user = (User) obj;
        return user.getPassword().equals(user.getConfirmPassword());
    }

}
