package com.example.teswebapp.domain;

import com.example.teswebapp.validation.PasswordMatches;
import com.example.teswebapp.validation.ValidPassword;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@PasswordMatches
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String confirmPassword;

    private Boolean isSomething;


//    public void setPassword(String password) {
//        this.password = password;
//        checkPassword();
//    }
//
//    public void setConfirmPassword(String confirmPassword) {
//        this.confirmPassword = confirmPassword;
//        checkPassword();
//    }
//
//    private void checkPassword() {
//        if (this.password == null || this.confirmPassword == null) {
//            return;
//        } else if (!this.password.equals(confirmPassword)) {
//            this.confirmPassword = null;
//        }
//    }

}
