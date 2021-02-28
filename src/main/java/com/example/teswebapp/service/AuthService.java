package com.example.teswebapp.service;

import com.example.teswebapp.domain.Authority;
import com.example.teswebapp.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AuthService implements IAuthService {

    @Autowired
    private AuthRepository authRepository;

    @Override
    public Authority save(Authority authority) {
        return authRepository.save(authority);
    }
}
