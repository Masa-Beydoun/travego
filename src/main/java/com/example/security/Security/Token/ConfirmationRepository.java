package com.example.security.Security.Token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationRepository extends JpaRepository<ConfirmationToken,Integer> {
    ConfirmationToken findByToken(String token);

}
