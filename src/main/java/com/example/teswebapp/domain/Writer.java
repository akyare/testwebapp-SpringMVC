package com.example.teswebapp.domain;

import com.example.teswebapp.validation.PasswordMatches;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Writer extends User{

    private Boolean canWrite;
}
