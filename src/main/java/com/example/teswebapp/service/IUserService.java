package com.example.teswebapp.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

//import com.baeldung.web.dto.UserDto;
import com.example.teswebapp.web.error.UserAlreadyExistException;
//import com.baeldung.persistence.model.PasswordResetToken;
import com.example.teswebapp.domain.User;
import org.springframework.data.repository.query.Param;
//import com.baeldung.persistence.model.VerificationToken;
//import com.baeldung.persistence.model.NewLocationToken;

public interface IUserService {


    User registerNewUserAccount(User account) throws UserAlreadyExistException;

    void updateUserNotPwd(User user);

    void updateUserWithPwd(User user);

    User findById(Long Id);

    void updateUserPwd(Long id, String password);

    boolean checkIfValidOldPassword(User user, String oldPassword);

}
