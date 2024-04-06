package com.example.security.Security.Token;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NumberConfirmationTokenRepository extends JpaRepository<NumberConfirmationToken,Integer> {
   Optional< NumberConfirmationToken> findByCode(String codeNumber);



   Optional<NumberConfirmationToken >deleteNumberConfirmationTokenByUserEmail(String email);

   Optional<NumberConfirmationToken >getNumberConfirmationTokenByUserEmail(String email);
}
