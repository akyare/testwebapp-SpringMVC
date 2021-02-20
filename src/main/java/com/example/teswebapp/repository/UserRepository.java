package com.example.teswebapp.repository;

import com.example.teswebapp.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);

    List<User> findByName(String name);

    @Modifying
    @Query("update User u set u.username = :username, u.name = :name, u.email = :email, u.isSomething = :writer where u.id = :id")
    void updateUserNotPwd(@Param(value = "id") Long id, @Param(value = "username") String username,
                     @Param(value = "name") String name, @Param(value = "email") String email,
                     @Param(value = "writer") Boolean writer);

    @Modifying
    @Query("update User u set u.username = :username, u.name = :name, u.email = :email, u.isSomething = :writer, u.encodedPassword = :password where u.id = :id")
    void updateUserWithPwd(@Param(value = "id") Long id, @Param(value = "username") String username,
                    @Param(value = "name") String name, @Param(value = "email") String email,
                    @Param(value = "writer") Boolean writer, @Param(value = "password") String password);

    @Query("select u from User u where u.email = :email and u.id <> :id")
    List<User> findByEmailNotEqualToId(@Param(value = "email") String email, @Param(value = "id") Long id);

    @Query("select u from User u where u.username = :username and u.id <> :id")
    List<User> findByUsernameNotEqualToId(@Param(value = "username") String username,  @Param(value = "id") Long id);
}
