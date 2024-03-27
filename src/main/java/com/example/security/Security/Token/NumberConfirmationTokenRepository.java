package com.example.security.Security.Token;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NumberConfirmationTokenRepository extends JpaRepository<NumberConfirmationToken,Integer> {
   Optional< NumberConfirmationToken> findByCode(String codeNumber);

   Optional< NumberConfirmationToken> findByUserId(Integer user_id);

   NumberConfirmationToken deleteNumberConfirmationTokenByUserId(Integer id);
}
