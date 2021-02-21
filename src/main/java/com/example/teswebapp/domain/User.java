package com.example.teswebapp.domain;

import com.example.teswebapp.validation.PasswordMatches;
import com.example.teswebapp.validation.ValidPassword;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
@DynamicUpdate
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String username;

    @OneToOne(mappedBy = "user")
    private Authority authority;

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
