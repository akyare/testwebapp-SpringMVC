package com.example.teswebapp.service;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

//import com.baeldung.persistence.dao.NewLocationTokenRepository;
//import com.baeldung.persistence.dao.PasswordResetTokenRepository;
import com.example.teswebapp.repository.UserRepository;
//import com.baeldung.persistence.dao.UserLocationRepository;
//import com.baeldung.persistence.dao.UserRepository;
//import com.baeldung.web.dto.UserDto;
import com.example.teswebapp.web.error.UserAlreadyExistException;
//import com.baeldung.persistence.dao.VerificationTokenRepository;
//import com.baeldung.persistence.model.NewLocationToken;
//import com.baeldung.persistence.model.PasswordResetToken;
import com.example.teswebapp.domain.User;
//import com.baeldung.persistence.model.UserLocation;
//import com.baeldung.persistence.model.VerificationToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import com.maxmind.geoip2.DatabaseReader;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // API

    @Override
    public User registerNewUserAccount(final User user) {

        if (emailExists(user.getEmail())) {
            throw new UserAlreadyExistException("There is an user with that email address: " + user.getEmail());
        }
        //final User user = new User();

        //user.setFirstName(user.getFirstName());
        //user.setLastName(user.getLastName());
        user.setEncodedPassword(passwordEncoder.encode(user.getPassword()));
        //user.setConfirmPassword(user.getConfirmPassword());
        //user.setEmail(user.getEmail());
        //user.setUsing2FA(user.isUsing2FA());
        //user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public User findById(Long id){
        return userRepository.findById(id).get();
    }
}
