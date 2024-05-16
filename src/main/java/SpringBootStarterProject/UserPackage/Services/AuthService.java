package SpringBootStarterProject.UserPackage.Services;

import SpringBootStarterProject.ManagingPackage.Security.Config.JwtService;
import SpringBootStarterProject.ManagingPackage.Security.Config.RateLimiterConfig;
import SpringBootStarterProject.ManagingPackage.Security.Token.*;
import SpringBootStarterProject.ManagingPackage.Security.auth.AuthenticationResponse;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.email.EmailService;
import SpringBootStarterProject.ManagingPackage.exception.TooManyRequestException;
import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import SpringBootStarterProject.UserPackage.Request.EmailConfirmationRequest;
import SpringBootStarterProject.UserPackage.Request.LoginRequest;
import SpringBootStarterProject.UserPackage.Request.ClientRegisterRequest;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ObjectsValidator<ClientRegisterRequest>ClientRegisterValidator;

    private final ObjectsValidator<LoginRequest>LoginRequestValidator;

    //TODO:: TRY   private final ObjectsValidator<Object>validator;


    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final NumberConfirmationTokenRepository numberConfTokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final RateLimiterConfig rateLimiterConfig;
    private final RateLimiterRegistry rateLimiterRegistry;
    private static final String LOGIN_RATE_LIMITER = "loginRateLimiter";
    public ResponseEntity<?> ClientRegister(ClientRegisterRequest request)
    {
        ClientRegisterValidator.validate(request);

        Optional<Client> client_found= clientRepository.findByEmail(request.getEmail());

        if (client_found.isPresent()) {
            var client = client_found.get();
            if (client.getActive() == false) {
                GenreateCode(client);
                return ResponseEntity.created(URI.create("")).body("THE CODE SENT TO YOUR ACCOUNT , PLEASE VIREFY YOUR EMAIL");
            } else {
                return ResponseEntity.badRequest().body("email taken");

            }
        }


        var The_client= Client.builder()
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(false)
                .creationDate(LocalDate.now())
                .phone_number(request.getPhone_number())
                .build();

             var savedClient = clientRepository.save(The_client);
             GenreateCode(savedClient);

        return ResponseEntity.created(URI.create("")).body("THE CODE SENT TO YOUR ACCOUNT , PLEASE VIREFY YOUR EMAIL");
        }

    public AuthenticationResponse ClientLogin(LoginRequest request
    , HttpServletRequest httpServletRequest) {
        LoginRequestValidator.validate(request);
        String userIp = httpServletRequest.getRemoteAddr();
        if (rateLimiterConfig.getBlockedIPs().contains(userIp)) {
            throw new TooManyRequestException("Too many login attempts. Please try again later.");
        }

        String rateLimiterKey = LOGIN_RATE_LIMITER + "-" + userIp;
        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter(rateLimiterKey);

        Optional<Client> theclient = clientRepository.findByEmail(request.getEmail());


        if (theclient.isEmpty())
            throw new UsernameNotFoundException("The email not found, please register");
        if (rateLimiter.acquirePermission()) {
            Authentication authentication;
            try {


                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        ));
            } catch (AuthenticationException exception) {
                throw new BadCredentialsException("invalid email or password");
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Client client = theclient.get();
            if (client.getActive() == true) {
                String jwtToken = jwtService.generateToken(client);
                RevokeAllClientTokens(client);
                SaveClientToken(client, jwtToken);
                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
            } else {
                GenreateCode(client);
                //TODO:: MAKE A CUSTOM EXCEPTION
                throw new UsernameNotFoundException("PLEASE CONFIRM YOUR ACCOUNT, EMAIL SENT TO YOUR ACCOUNT");
            }
        } else {
            rateLimiterConfig.blockIP(userIp);

            throw new TooManyRequestException("Too many login attempts. Please try again later.");
        }
    }

   // public ResponseEntity<?> AdminLogin(LoginRequest request) {

    //}


    private void SaveClientToken(Client client, String jwtToken)
    {

        var token= Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .RelationId(client.getId())
                .type(RelationshipType.CLIENT)
                .expired(false)
                .revoked(false)
                .build();
                tokenRepository.save(token);
    }

    private void RevokeAllClientTokens(Client client)
    {
        var ValidClientTokens= tokenRepository.findAllValidTokensByRelationId(client.getId());
        if(ValidClientTokens.isEmpty())
            return;
        ValidClientTokens.forEach(token ->{
            token.setRevoked(true);
            token.setExpired(true);
                });
        tokenRepository.saveAll(ValidClientTokens);

    }


    private void GenreateCode(Client client)
    {
        if (client.getActive()==true)
            throw new BadCredentialsException("EMAIL TAKEN");
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        String thecode = Integer.toString(code);

        Optional<NumberConfirmationToken> optional1 = numberConfTokenRepository.getNumberConfirmationTokenByClient_email(client.getEmail());
        if (optional1.isPresent()) {
            NumberConfirmationToken oldCode = optional1.get();
            numberConfTokenRepository.delete(oldCode);
            NumberConfirmationToken confirmation = new NumberConfirmationToken();
            confirmation.setValid(true);
            confirmation.setClient(client);
            confirmation.setClient_email(client.getEmail());
            confirmation.setCode(thecode);
            numberConfTokenRepository.save(confirmation);
            emailService.sendMailcode(client.getFirst_name(), client.getEmail(), thecode);

        } else {

            NumberConfirmationToken confirmation = new NumberConfirmationToken();
            confirmation.setValid(true);
            confirmation.setClient(client);
            confirmation.setClient_email(client.getEmail());
            confirmation.setCode(thecode);
            numberConfTokenRepository.save(confirmation);
            emailService.sendMailcode(client.getFirst_name(), client.getEmail(), thecode);

        }
    }

    public ResponseEntity<?> RegenerateConfCode( LoginRequest request)
    {
        Optional<Client> optional = clientRepository.findByEmail(request.getEmail());
        if (optional.isEmpty())

            throw new IllegalStateException("EMAIL NOT FOUND ,,PLEASE REGISTER");

        var user = optional.get();
        GenreateCode(user);
return ResponseEntity.ok().body("CODE SENT TO YOUR ACCOUNT SUCCSESSFULLY");
    }


    public AuthenticationResponse checkCodeNumber(EmailConfirmationRequest request) {

        Optional <NumberConfirmationToken> NumberConfCode = numberConfTokenRepository.findByCode(request.getCodeNumber());
        if (NumberConfCode.isEmpty())
            throw new UsernameNotFoundException("THE CODE NOT CORRECT");

        var checker=NumberConfCode.get();

        if(checker.getExpirationDate().isBefore(LocalDateTime.now()))
        {
            throw new IllegalStateException("TOKEN EXPIRED");
        }
        if (checker!=null && checker.getClient_email().equals(request.getUser_email())) {
            if (!checker.getValid())
                throw new BadCredentialsException("CODE NOT VALID ANYMORE ,PLEASE REGENERATE ANOTHER CODE");
            Optional<Client> optionalUser = clientRepository.findByEmail(request.getUser_email());
            if (optionalUser.isPresent()) {
                Client client = optionalUser.get();
                checker.setValid(false);
                client.setActive(true);
                Client savedUser = clientRepository.save(client);
                String jwtToken = jwtService.generateToken(savedUser);
                SaveClientToken(savedUser, jwtToken);
                numberConfTokenRepository.save(checker);
                return AuthenticationResponse
                        .builder().token(jwtToken).build();
            }

        }
        throw new UsernameNotFoundException("the code not correct");
    }
}
