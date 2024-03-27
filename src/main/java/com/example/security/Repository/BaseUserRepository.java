package com.example.security.Repository;

import com.example.security.Models.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseUserRepository extends JpaRepository<BaseUser,Integer> {
    Optional<BaseUser> findByEmail (String email);
BaseUser findByEmailIgnoreCase(String email);
    Optional<BaseUser> findBaseUserById(int id);


    Optional<BaseUser> findById(Integer id);
}
