package com.example.security.Service;

import ai.djl.Model;
import com.example.security.Models.BaseUser;
import com.example.security.Repository.AuthorRepository;
import com.example.security.Repository.BaseUserRepository;
import com.example.security.Repository.StudentRepository;
import com.example.security.Request.ChangePasswordRequest;
import com.example.security.Request.EditRequest;
import com.example.security.Request.Register_Login_Request;
import com.example.security.Request.TestRequest;
import com.example.security.Response.BaseUserProfile;
import com.example.security.RolesAndPermission.Roles;
import com.example.security.Security.Config.JwtService;
import com.example.security.Security.Token.*;
import com.example.security.Security.auth.AuthenticationResponse;
import com.example.security.Validator.ObjectsValidator;
import com.example.security.email.EmailService;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
//import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BaseUserService {
    private final PasswordEncoder passwordEncoder;
    private final BaseUserRepository baseUserRepository;
    private final AuthorRepository authorRepository;
    private final StudentRepository studentRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    private final ConfirmationRepository confirmationRepository;
    private final NumberConfirmationTokenRepository numberConfRepo;
    private final ObjectsValidator<Register_Login_Request> userValidator;
    private final RateLimiterRegistry rateLimiterRegistry;
    private final com.example.security.Security.Config.RateLimiterConfig rateLimiterConfig;
    private static final String LOGIN_RATE_LIMITER = "loginRateLimiter";


    public ResponseEntity<String> Author_Register_with_ConfCode(Register_Login_Request request)throws MailSendException {
        try {
            userValidator.validate(request);
            Optional<BaseUser> user_found = baseUserRepository.findByEmail(request.getEmail());

            if (user_found.isPresent()) {


                var user = user_found.get();
                if (user.isActive()==false) {
                    GenreateCode(user);
                    return ResponseEntity.created(URI.create("")).body("code sent to your account");
                }
                else
                {
                    return ResponseEntity.badRequest().body("email taken");
                }
            }
            var Author = com.example.security.Models.Author.builder()


                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(Roles.AUTHOR)
                    .build();
            Author.setActive(false);
            var savedUser = baseUserRepository.save(Author);
            GenreateCode(savedUser);

        }

        catch (MailSendException exception)
        {

        }
        return ResponseEntity.created(URI.create("")).body("PLEASE CONFIRM YOUR ACCOUNT");

    }

    public ResponseEntity<String> Student_Register_with_ConfCode(Register_Login_Request request)throws MailSendException {
        try {
            userValidator.validate(request);
            Optional<BaseUser> user_found = baseUserRepository.findByEmail(request.getEmail());

            if (user_found.isPresent()) {


                var user = user_found.get();
                if (user.isActive()==false) {
                    GenreateCode(user);
                    return ResponseEntity.created(URI.create("")).body("code sent to your account");
                }
                else
                {
                    return ResponseEntity.badRequest().body("email taken");
                }
            }
            var Studnet = com.example.security.Models.Student.builder()


                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .roles(Roles.AUTHOR)
                    .build();
            Studnet.setActive(false);
            var savedUser = studentRepository.save(Studnet);
            GenreateCode(savedUser);

        }

        catch (MailSendException exception)
        {

        }
        return ResponseEntity.created(URI.create("")).body("PLEASE CONFIRM YOUR ACCOUNT");

    }
    private void GenreateCode(BaseUser user)
    {
if (user.isActive()==true)
    throw new BadCredentialsException("EMAIL TAKEN");
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        String thecode = Integer.toString(code);

        Optional<NumberConfirmationToken> optional1 = numberConfRepo.getNumberConfirmationTokenByUserEmail(user.getEmail());
        if (optional1.isPresent()) {
            NumberConfirmationToken oldCode = optional1.get();
            numberConfRepo.delete(oldCode);
            NumberConfirmationToken confirmation = new NumberConfirmationToken();
            confirmation.setValid(true);
            confirmation.setUser(user);
            confirmation.setUser_email(user.getEmail());
            confirmation.setCode(thecode);
            numberConfRepo.save(confirmation);
            emailService.sendMailcode(user.getName(), user.getEmail(), thecode);

        } else {

            NumberConfirmationToken confirmation = new NumberConfirmationToken();
            confirmation.setValid(true);
            confirmation.setUser(user);
            confirmation.setUser_email(user.getEmail());
            confirmation.setCode(thecode);
            numberConfRepo.save(confirmation);
            emailService.sendMailcode(user.getName(), user.getEmail(), thecode);

        }
    }
    public void RegenerateToken( TestRequest request) {
        Optional<BaseUser> optional = baseUserRepository.findByEmail(request.getEmail());
        if (optional.isEmpty())

            throw new IllegalStateException("EMAIL NOT FOUND ,,PLEASE REGISTER");

        var user = optional.get();
        GenreateCode(user);

    }
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (BaseUser) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");

        }
        if (!request.getNewPassword().equals(request.getRepeatPassword())) {
            throw new IllegalStateException("Password are not the same");

        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        baseUserRepository.save(user);

    }

    public ResponseEntity<?> MyProfile() {

        var user_Principal = (BaseUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<BaseUser> baseUser = baseUserRepository.findBaseUserById(user_Principal.getId());

        if (baseUser.isPresent()) {

            BaseUserProfile response = new BaseUserProfile(baseUser.get().getName(), baseUser.get().getEmail());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }


    public void Edit_Profile(EditRequest request) {


        BaseUser user = (BaseUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setName(request.getName());
        baseUserRepository.save(user);

    }






        public AuthenticationResponse login(Register_Login_Request request
            , HttpServletRequest httpServletRequest) throws Exception {

        String userIp = httpServletRequest.getRemoteAddr(); // Get user IP

    System.out.println(rateLimiterConfig.getBlockedIPs());
    if (rateLimiterConfig.getBlockedIPs().contains(userIp)) {
        throw new BadCredentialsException("IP address blocked. Too many login attempts.");
    }
    // Create a unique key for the rate limiter based on the combination of LOGIN_RATE_LIMITER and user IP
    String rateLimiterKey = LOGIN_RATE_LIMITER + "-" + userIp;

    RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter(rateLimiterKey);
    Optional<BaseUser> optional = baseUserRepository.findByEmail(request.getEmail());
    if (optional.isEmpty())
        throw new UsernameNotFoundException("The email not found, please register");
    if (rateLimiter.acquirePermission()) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    ));
        } catch (AuthenticationException ex) {
            System.out.println("Invalid email or password");
            throw new BadCredentialsException("Invalid email or password");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);



        BaseUser baseUser = optional.get();
        if (baseUser.isActive()) {
            String jwtToken = jwtService.generateToken(baseUser);
            revokedAllUserTokens(baseUser);
            saveToken(baseUser, jwtToken);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            GenreateCode(baseUser);
            throw new IllegalStateException("Please confirm your email first. Confirmation sent to your email");
        }
    } else {
        rateLimiterConfig.blockIP(userIp);
        throw new BadCredentialsException("Too many login attempts. Please try again later.");
    }
}
    private void saveToken(BaseUser user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokedAllUserTokens(BaseUser user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public AuthenticationResponse checkCodeNumber(String codeNumber, String user_email) {

     Optional <NumberConfirmationToken> optional = numberConfRepo.findByCode(codeNumber);
     if (optional.isEmpty())
         throw new UsernameNotFoundException("THE CODE NOT CORRECT");

        var checker=optional.get();

        if(checker.getExpirationDate().isBefore(LocalDateTime.now()))
            {
    throw new IllegalStateException("TOKEN EXPIRED");
            }
        if (checker!=null && checker.getUser_email().equals(user_email)) {
            if (!checker.getValid())
                throw new BadCredentialsException("CODE NOT VALID");
            Optional<BaseUser> optionalUser = baseUserRepository.findByEmail(user_email);
            if (optionalUser.isPresent()) {
                BaseUser user = optionalUser.get();
                checker.setValid(false);
                user.setActive(true);
                BaseUser savedUser = baseUserRepository.save(user);
                String jwtToken = jwtService.generateToken(savedUser);
                saveToken(savedUser, jwtToken);
                numberConfRepo.save(checker);
                return AuthenticationResponse
                        .builder().token(jwtToken).build();
            }

        }
        throw new UsernameNotFoundException("the code not correct");
    }



}
