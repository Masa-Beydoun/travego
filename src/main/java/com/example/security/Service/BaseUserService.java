package com.example.security.Service;

import com.example.security.Models.Author;
import com.example.security.Models.BaseUser;
import com.example.security.Repository.AuthorRepository;
import com.example.security.Repository.BaseUserRepository;
import com.example.security.Request.EditRequest;
import com.example.security.Request.Register_Login_Request;
import com.example.security.Request.ChangePasswordRequest;
import com.example.security.Response.BaseUserProfile;
import com.example.security.RolesAndPermission.Roles;
import com.example.security.Security.Config.JwtService;
import com.example.security.Security.Token.*;
import com.example.security.Security.auth.AuthenticationResponse;
import com.example.security.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    private final ConfirmationRepository confirmationRepository;
    private final NumberConfirmationTokenRepository numberConfRepo;

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


    public AuthenticationResponse Login(Register_Login_Request request) {

        //see if the user active first

        BaseUser baseUser = baseUserRepository.findByEmailIgnoreCase(request.getEmail());

        if (baseUser != null && baseUser.isActive() != false) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    ));
            var user = baseUserRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("the email not found "));
            var jwtToken = jwtService.generateToken(user);
            revokedAllUserTokens(user);
            saveToken(user, jwtToken);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            var user = baseUserRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("the email not found "));
           var Old_code_Req= numberConfRepo.findByUserId(user.getId());
           NumberConfirmationToken Old_code=Old_code_Req.get();
            numberConfRepo.delete(Old_code);
            Random random = new Random();
            int code = 100000 + random.nextInt(900000);
            String thecode = Integer.toString(code);

      //      ConfirmationToken confirmation = new ConfirmationToken(baseUser);
            NumberConfirmationToken confirmation = new NumberConfirmationToken();
            confirmation.setUser(user);
            confirmation.setCode(thecode);
            numberConfRepo.save(confirmation);
            emailService.sendMailcode(user.getName(), user.getEmail(), thecode);

        //    confirmationRepository.save(confirmation);
            emailService.sendMailcode(baseUser.getName(), baseUser.getEmail(), confirmation.getCode());
            //      emailService.sendMailtoken(baseUser.getName(), baseUser.getEmail(), confirmation.getToken());
            throw new IllegalStateException("please confirm your email first.. confirmation send to your email");
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

    public AuthenticationResponse checkCodeNumber(String codeNumber, Integer user_id) {

     Optional <NumberConfirmationToken> optional = numberConfRepo.findByCode(codeNumber);
        var checker=optional.get();
if(checker.equals(null))
    throw new UsernameNotFoundException("THE CODE NOT CORRECT");

if(checker.getExpirationDate().isBefore(LocalDateTime.now()))
{
    throw new IllegalStateException("TOKEN EXPIRED");
}
        if (checker!=null && checker.user.getId().equals(user_id)) {
            Optional<BaseUser> optionalUser = baseUserRepository.findById(user_id);
            if (optionalUser.isPresent()) {
                BaseUser user = optionalUser.get();
                user.setActive(true);
                BaseUser savedUser = baseUserRepository.save(user);
                String jwtToken = jwtService.generateToken(savedUser);
                saveToken(savedUser, jwtToken);

                return AuthenticationResponse
                        .builder()
                        .token(jwtToken).build();
            }

        }
        throw new UsernameNotFoundException("the code not correct");
    }
}
