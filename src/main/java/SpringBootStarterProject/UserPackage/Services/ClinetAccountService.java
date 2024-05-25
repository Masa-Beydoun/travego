package SpringBootStarterProject.UserPackage.Services;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Security.Config.JwtService;
import SpringBootStarterProject.ManagingPackage.Security.Config.RateLimiterConfig;
import SpringBootStarterProject.ManagingPackage.Security.Token.NumberConfirmationTokenRepository;
import SpringBootStarterProject.ManagingPackage.Security.Token.TokenRepository;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.email.EmailService;
import SpringBootStarterProject.UserPackage.Models.*;
import SpringBootStarterProject.UserPackage.Repositories.*;
import SpringBootStarterProject.UserPackage.Request.*;
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
    private  final ObjectsValidator<Client> EditClientValidator;
    private final ObjectsValidator<ClientRegisterRequest> ClientRegisterValidator;

    private final ObjectsValidator<LoginRequest>LoginRequestValidator;

    private final ObjectsValidator<ManagerRegisterRequest>ManagerRequestValidator;

    //TODO:: TRY   private final ObjectsValidator<Object>validator;
    private final VisaRepository visaRepository;
    private final PersonalidentityRepository personalidentityRepository;
    private final PassportRepository passportRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final ClientDetailsRepository clientDetailsRepository;
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

    public ApiResponseClass EditMyAccount(EditClientRequest request) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        var client= clientRepository.findByEmail(authentication.getName()).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        if(request.getFirst_name()!=null )
            client.setFirst_name(request.getFirst_name());

        if(request.getLast_name()!=null )
            client.setLast_name(request.getLast_name());
        if(request.getUsername()!=null )
            client.setUsername(request.getUsername());
        if(request.getPhone_number()!=null )
            client.setPhone_number(request.getPhone_number());
        EditClientValidator.validate(client);
        clientRepository.save(client);
        return new ApiResponseClass("Profile Updated Successfully",HttpStatus.ACCEPTED,LocalDateTime.now(),client);
    }


    public ApiResponseClass AddMyDetails(ClientDetailsRequest request) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        var client= clientRepository.findByEmail(authentication.getName()).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        var clientDetails = client.getClientDetails();

        if(request.getGender()!=null )
            clientDetails.setGender(request.getGender());

        if(request.getBirthDate()!=null )
            clientDetails.setBirthdate(request.getBirthDate());
        clientDetailsRepository.save(clientDetails);

        Passport passport=new Passport();

        passport.setRelationshipId(client.getId());
        passport.setType(RelationshipType.USERDETAILS);

        if(request.getPassportfirstName()!=null )
            passport.setFirstName(request.getPassportfirstName());

        if(request.getPassportlastName()!=null )
            passport.setLastName(request.getPassportlastName());

        if(request.getPassportIssueDate()!=null )
            passport.setIssueDate(request.getPassportIssueDate());

        if(request.getPassportExpiryDate()!=null )
            passport.setExpiryDate(request.getPassportExpiryDate());

        if(request.getPassportNumber()!=null )
            passport.setPassportNumber(request.getPassportNumber());

        passportRepository.save(passport);

        Personalidenty personalidenty=new Personalidenty();

        personalidenty.setRelationshipId(client.getId());
        personalidenty.setType(RelationshipType.USERDETAILS);


        if(request.getIdfirstName()!=null )
            personalidenty.setFirstName(request.getIdfirstName());

        if(request.getIdlastName()!=null )
            personalidenty.setLastName(request.getIdlastName());

        if(request.getNationality()!=null )
            personalidenty.setNationality(request.getNationality());

        if(request.getNationality()!=null )
            personalidenty.setNationality(request.getNationality());

        if(request.getIdBirthDate()!=null )
            personalidenty.setBirthDate(request.getIdBirthDate());

        personalidentityRepository.save(personalidenty);


        Visa visa=new Visa();
        visa.setRelationshipId(client.getId());
        visa.setType(RelationshipType.USERDETAILS);

        if(request.getVisaType()!=null )
            visa.setVisaType(request.getVisaType());

        if(request.getVisaIssueDate()!=null )
            visa.setIssueDate(request.getVisaIssueDate());

        if(request.getVisaExpiryDate()!=null )
            visa.setExpiryDate(request.getVisaExpiryDate());

        if(request.getCountry()!=null )
            visa.setCountry(request.getCountry());

        visaRepository.save(visa);


        return new ApiResponseClass("Profile Updated Successfully",HttpStatus.ACCEPTED,LocalDateTime.now(),client);

    }
}
