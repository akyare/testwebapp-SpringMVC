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
import com.example.teswebapp.repository.AuthRepository;
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

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // API

    @Override
    public User registerNewUserAccount(final User user) {

        if (emailExists(user.getEmail())) {
            throw new UserAlreadyExistException("There is an user with that email address: " + user.getEmail());
        }
        if (usernameExists(user.getUsername())) {
            throw new UserAlreadyExistException("There is an user with that username: " + user.getUsername());
        }

        user.setEncodedPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public void updateUserNotPwd(User user) {

        if (emailExistsForId(user.getEmail(),user.getId())) {
            throw new UserAlreadyExistException("There is an user with that email address: " + user.getEmail());
        }
        if (usernameExistsForId(user.getUsername(), user.getId())) {
            throw new UserAlreadyExistException("There is an user with that username: " + user.getUsername());
        }

        userRepository.updateUserNotPwd(user.getId(),user.getUsername(),user.getName(),user.getEmail(),user.getIsWriter());
    }

    @Override
    public void updateUserWithPwd(User user) {

        userRepository.updateUserWithPwd(user.getId(),user.getUsername(),user.getName(),user.getEmail(),user.getIsWriter(),
                passwordEncoder.encode(user.getPassword()));
    }

    @Override
    public void updateUserPwd(Long id, String password) {
        userRepository.updateUserPwd(id, passwordEncoder.encode(password));
    }

    public boolean checkIfValidOldPassword(User user, String oldPassword) {

        return passwordEncoder.matches(oldPassword,user.getEncodedPassword());
    }


    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }

    private boolean usernameExists(final String username) {
        return userRepository.findByUsername(username) != null;
    }

    private boolean emailExistsForId(final String email, final Long id) {
        int sizeUsers = userRepository.findByEmailNotEqualToId(email, id).size();
        return  sizeUsers > 0;
    }

    private boolean usernameExistsForId(final String username, final Long id) {
        int sizeUsers = userRepository.findByUsernameNotEqualToId(username, id).size();
        return  sizeUsers > 0;
    }

    @Override
    public User findById(Long id){

        return userRepository.findById(id).get();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteByUsername(String username) {

        authRepository.deleteAllByUsername(username);
        userRepository.deleteByUsername(username);
    }
}
