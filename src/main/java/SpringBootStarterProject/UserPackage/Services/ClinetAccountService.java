package SpringBootStarterProject.UserPackage.Services;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Security.Config.JwtService;
import SpringBootStarterProject.ManagingPackage.Security.Config.RateLimiterConfig;
import SpringBootStarterProject.ManagingPackage.Security.Token.NumberConfirmationTokenRepository;
import SpringBootStarterProject.ManagingPackage.Security.Token.TokenRepository;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.email.EmailService;
import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import SpringBootStarterProject.UserPackage.Repositories.ManagerRepository;
import SpringBootStarterProject.UserPackage.Request.ClientRegisterRequest;
import SpringBootStarterProject.UserPackage.Request.LoginRequest;
import SpringBootStarterProject.UserPackage.Request.ManagerRegisterRequest;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClinetAccountService {
    private final ObjectsValidator<ClientRegisterRequest> ClientRegisterValidator;

    private final ObjectsValidator<LoginRequest>LoginRequestValidator;

    private final ObjectsValidator<ManagerRegisterRequest>ManagerRequestValidator;

    //TODO:: TRY   private final ObjectsValidator<Object>validator;


    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final NumberConfirmationTokenRepository numberConfTokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final ManagerRepository managerRepository;
    private final JwtService jwtService;
    private final RateLimiterConfig rateLimiterConfig;
    private final RateLimiterRegistry rateLimiterRegistry;
    private static final String LOGIN_RATE_LIMITER = "loginRateLimiter";
    public ApiResponseClass GetMyAccount()
    {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());



        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        return new ApiResponseClass("PROFILE RETURED SUCCESSFULLY", HttpStatus.ACCEPTED, LocalDateTime.now(),client);
    }

//    public Object EditMyAccoutn() {
//    }
}
