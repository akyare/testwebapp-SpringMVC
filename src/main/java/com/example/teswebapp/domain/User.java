package com.example.teswebapp.domain;

import com.example.teswebapp.validation.PasswordMatches;
import com.example.teswebapp.validation.ValidPassword;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
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
    private String username;

    @NotBlank(message = "Name is mandatory")
    private String name;


    private String firstName;


    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @ValidPassword
    @Transient
    private String password;

    @Column(name = "password")
    private String encodedPassword;

    @NotNull
    @Size(min = 1)
    @Transient
    private String confirmPassword;

    private Boolean isSomething;

}
