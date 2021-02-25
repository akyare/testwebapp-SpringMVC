package com.example.teswebapp.repository;

import com.example.teswebapp.domain.Authority;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthRepository extends CrudRepository<Authority, Long> {

    //List<Authority> findByUsername(String username);

    @Modifying
    @Query("delete from Authority a where a.username = :username")
    void deleteAllByUsername(@Param(value = "username") String username);
}
