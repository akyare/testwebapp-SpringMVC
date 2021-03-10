package com.example.teswebapp.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

//import com.baeldung.web.dto.UserDto;
import com.example.teswebapp.domain.VerificationToken;
import com.example.teswebapp.web.error.UserAlreadyExistException;
//import com.baeldung.persistence.model.PasswordResetToken;
import com.example.teswebapp.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
//import com.baeldung.persistence.model.VerificationToken;
//import com.baeldung.persistence.model.NewLocationToken;

public interface IUserService {

    Page<User> listAll(int pageNumber);

    User registerNewUserAccount(User account) throws UserAlreadyExistException;

    void updateUserNotPwd(User user);

    User findById(Long Id);

    User findByEmail(String email);

    User findByUsername(String username);

    List<User> findAll();

    void updateUserPwd(Long id, String password);

    boolean checkIfValidOldPassword(User user, String oldPassword);

    void deleteByUsername(String username);

    void createAuthority(User user, String authority);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    void updateUserEnabled(User user, boolean enabled);


}
