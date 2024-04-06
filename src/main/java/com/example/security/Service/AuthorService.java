package com.example.security.Service;

import com.example.security.Models.Author;
import com.example.security.Models.BaseUser;
import com.example.security.Repository.BaseUserRepository;
import com.example.security.Request.TestRequest;
import com.example.security.RolesAndPermission.Roles;
import com.example.security.Repository.AuthorRepository;
import com.example.security.Request.Register_Login_Request;
import com.example.security.Security.Config.JwtService;
import com.example.security.Security.Token.*;
import com.example.security.Security.auth.AuthenticationResponse;

import com.example.security.Validator.ObjectsValidator;
import com.example.security.email.EmailService;
import io.netty.handler.codec.http.HttpRequest;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.AUTH;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor

public class AuthorService {
     private final    AuthorRepository authorRepository;
     private final  TokenRepository tokenRepository;
     private final JwtService jwtService;
     private final  PasswordEncoder passwordEncoder;
     private final  AuthenticationManager authenticationManager;
    private final BaseUserRepository baseUserRepository;
    private final ConfirmationRepository confirmationRepository;
    private final EmailService emailService;
    private final NumberConfirmationTokenRepository numberConfRepo;
    private final ObjectsValidator<Register_Login_Request>userValidator;

    private final Validator validator;

    private static final Logger logger= LogManager.getLogger(AuthorService.class);







   public  AuthenticationResponse verifyToken(String token)
    {
        ConfirmationToken confirmationToken=confirmationRepository.findByToken(token);
       BaseUser user= baseUserRepository.findByEmailIgnoreCase(confirmationToken.getUser().getEmail());

       user.setActive(true);
    var jwtToken= jwtService.generateToken(user);


   baseUserRepository.save(user);

  saveAuthorToken(user,jwtToken);
   return AuthenticationResponse.builder()
           .token(jwtToken)
           .build();

    }

    private void saveAuthorToken(BaseUser author, String jwtToken) {
        var token = Token.builder()
                .user(author)
              //  .author(author)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokedAllUserTokens(BaseUser user)
    {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
                    token.setExpired(true);
                    token.setRevoked(true);
                });
        tokenRepository.saveAll(validUserTokens);
    }

}
