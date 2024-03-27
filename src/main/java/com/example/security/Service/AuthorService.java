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

    public ResponseEntity<String> Register_with_ConfCode(Register_Login_Request request)throws MailSendException {
        try {

            Optional<BaseUser> user_found = baseUserRepository.findByEmail(request.getEmail());

            if (user_found.isPresent()) {

                System.out.println("SAMER SHARAF");
                var user = user_found.get();

              //  RegenerateToken( user.getId());
                Random random = new Random();
                int code = 100000 + random.nextInt(900000);
                String thecode = Integer.toString(code);

                Optional<NumberConfirmationToken> optional1 = numberConfRepo.findByUserId(user.getId());
                NumberConfirmationToken oldCode = optional1.get();
                numberConfRepo.delete(oldCode);
                NumberConfirmationToken confirmation = new NumberConfirmationToken();
                confirmation.setUser(user);
                confirmation.setCode(thecode);
                numberConfRepo.save(confirmation);
                emailService.sendMailcode(user.getName(), user.getEmail(), thecode);


                return ResponseEntity.created(URI.create("")).body("code sent to your account");
            }
            var Author = com.example.security.Models.Author.builder()


                    .name(request.getName())
                    .email(request.getEmail())
                    //.password(request.getPassword())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(Roles.AUTHOR)
                    .build();

            Random random = new Random();
            int code = 100000 + random.nextInt(900000);
            String thecode = Integer.toString(code);
            Author.setActive(false);
            var savedUser = authorRepository.save(Author);

            NumberConfirmationToken confirmation = new NumberConfirmationToken();
            confirmation.setUser(Author);
            confirmation.setCode(thecode);
            numberConfRepo.save(confirmation);
            emailService.sendMailcode(Author.getName(), Author.getEmail(), thecode);

        }

        catch (MailSendException exception)
        {

        }
        return ResponseEntity.created(URI.create("")).body("PLEASE CONFIRM YOUR ACCOUNT");

    }


    public void RegenerateToken( TestRequest request) {
        Optional<BaseUser> optional = baseUserRepository.findById(request.getId());
        if (optional.isEmpty())

            throw new IllegalStateException("EMAIL NOT FOUND ,,PLEASE REGISTER");

        var user = optional.get();
          Random random = new Random();
             int code = 100000 + random.nextInt(900000);
             String thecode = Integer.toString(code);

        Optional<NumberConfirmationToken> optional1 = numberConfRepo.findByUserId(request.getId());
        if (optional1.isPresent()) {
            NumberConfirmationToken oldCode = optional1.get();
            numberConfRepo.delete(oldCode);
            NumberConfirmationToken confirmation = new NumberConfirmationToken();
            confirmation.setUser(user);
            confirmation.setCode(thecode);
            numberConfRepo.save(confirmation);
            emailService.sendMailcode(user.getName(), user.getEmail(), thecode);
        } else {

            NumberConfirmationToken confirmation = new NumberConfirmationToken();
            confirmation.setUser(user);
            confirmation.setCode(thecode);
            numberConfRepo.save(confirmation);
            emailService.sendMailcode(user.getName(), user.getEmail(), thecode);

        }
    }

    public ResponseEntity<String> first_Register(Register_Login_Request request)throws MailSendException {
try {


    var Author = com.example.security.Models.Author.builder()


            .name(request.getName())
            .email(request.getEmail())
            //.password(request.getPassword())
            .password(passwordEncoder.encode(request.getPassword()))
            .roles(Roles.AUTHOR)
            .build();


    Author.setActive(false);
    var savedUser = authorRepository.save(Author);

    ConfirmationToken confirmation = new ConfirmationToken(Author);
    confirmationRepository.save(confirmation);
    emailService.sendMailtoken(Author.getName(), Author.getEmail(), confirmation.getToken());
//emailService.sendMimeMessageWithAttachments(Author.getName(), Author.getEmail(), confirmation.getToken());
}
catch (MailSendException exception)
{

}
return ResponseEntity.created(URI.create("")).body("PLEASE CONFIRM YOUR ACCOUNT");
      //  return ResponseEntity.ok().body("PLEASE CONFIRM YOUR ACCOUNT");
    }


    public AuthenticationResponse Register(Register_Login_Request request) {

        //logger.info("LOGGER_INFO");

      //  logger.error("LOGGER_ERROR");
        userValidator.validate(request);
        var Author = com.example.security.Models.Author.builder()


                 .name(request.getName())
                .email(request.getEmail())
                  //.password(request.getPassword())
                 .password(passwordEncoder.encode(request.getPassword()))
                 .roles(Roles.AUTHOR)
                .build();


            Author.setActive(false);
      var savedUser=  authorRepository.save(Author);

      ConfirmationToken confirmation=new ConfirmationToken(Author);
      confirmationRepository.save(confirmation);

      emailService.sendMailtoken(Author.getName(),Author.getEmail(),confirmation.getToken());


    //  Author.setPassword(passwordEncoder.encode(request.getPassword()));
    var jwtToken = jwtService.generateToken(Author);

    saveAuthorToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();

    }

private AuthenticationResponse generateToken(BaseUser user,String token)
{
    var jwtToken= jwtService.generateToken(user);


    baseUserRepository.save(user);

    saveAuthorToken(user,jwtToken);
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
}


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
