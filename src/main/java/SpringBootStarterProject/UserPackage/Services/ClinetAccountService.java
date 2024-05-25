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
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClinetAccountService {
    private  final ObjectsValidator<Client> EditClientValidator;
    private final ObjectsValidator<ClientRegisterRequest> ClientRegisterValidator;

    private final ObjectsValidator<LoginRequest>LoginRequestValidator;

    private final ObjectsValidator<ManagerRegisterRequest>ManagerRequestValidator;

    private final ObjectsValidator<PassportRequest>PassportRequestValidator;

    private final ObjectsValidator<PersonalidRequest>PersonalIdRequestValidator;

    private final ObjectsValidator<VisaRequest>VisaRequestValidator;

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

    var foundclientDetails= clientDetailsRepository.findClientDetailsByClient(client);
        if(foundclientDetails==null)
        {
            ClientDetails clientDetails= ClientDetails.builder()
                    .client(client)
                    .gender(request.getGender())
                    .birthdate(request.getBirthDate())
                    .build();
            clientDetailsRepository.save(clientDetails);
            return new ApiResponseClass("Details Added Successfully",HttpStatus.ACCEPTED,LocalDateTime.now(),client);

        }
        else {


            if (request.getGender() != null)
                foundclientDetails.setGender(request.getGender());

            if (request.getBirthDate() != null)
                foundclientDetails.setBirthdate(request.getBirthDate());
            clientDetailsRepository.save(foundclientDetails);

            return new ApiResponseClass("Details Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), client);

        }

    }

    public ApiResponseClass AddMyPassport(PassportRequest request) {

        PassportRequestValidator.validate(request);
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        var client= clientRepository.findByEmail(authentication.getName()).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
       Optional< Passport> foundPassport= passportRepository.getPassportByRelationshipIdAndType(client.getId(),RelationshipType.USERDETAILS);

        if(foundPassport.isEmpty()) {
            Passport passport = Passport.builder()
                    .relationshipId(client.getId())
                    .type(RelationshipType.USERDETAILS)
                    .firstName(request.getPassportfirstName())
                    .lastName(request.getPassportlastName())
                    .issueDate(request.getPassportIssueDate())
                    .expiryDate(request.getPassportExpiryDate())
                    .passportNumber(request.getPassportNumber())
                    .build();
            passportRepository.save(passport);
            return new ApiResponseClass("Passport Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), passport);
        }
        else
        {
            var passport= foundPassport.get();
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

            return new ApiResponseClass("Passport Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPassport);

        }




    }


    public ApiResponseClass AddMyPersonalid(PersonalidRequest request) {
        PersonalIdRequestValidator.validate(request);
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        var client= clientRepository.findByEmail(authentication.getName()).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
     Optional< Personalidenty> foundPersonalId= Optional.ofNullable(personalidentityRepository.getPersonalidentyByRelationshipIdAndType(client.getId(), RelationshipType.USERDETAILS)
             .orElseThrow(() -> new NoSuchElementException("Personal ID Not Added Yet")));
        if(foundPersonalId.isEmpty()) {

        Personalidenty personalidenty=Personalidenty.builder()
                .relationshipId(client.getId())
                .type(RelationshipType.USERDETAILS)
                .firstName(request.getIdfirstName())
                .lastName(request.getIdlastName())
                .birthDate(request.getIdBirthDate())
                .nationality(request.getNationality())
                .build();
                 personalidentityRepository.save(personalidenty);
            return new ApiResponseClass("Personalidenty Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), personalidenty);
        }
        else {
            var personalId= foundPersonalId.get();
        if(request.getIdfirstName()!=null )
            personalId.setFirstName(request.getIdfirstName());

        if(request.getIdlastName()!=null )
            personalId.setLastName(request.getIdlastName());

        if(request.getNationality()!=null )
            personalId.setNationality(request.getNationality());

        if(request.getIdBirthDate()!=null )
            personalId.setBirthDate(request.getIdBirthDate());
            return new ApiResponseClass("Personal ID Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPersonalId);


        }
    }

    public ApiResponseClass AddMyVisa(VisaRequest request) {

        VisaRequestValidator.validate(request);
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        var client= clientRepository.findByEmail(authentication.getName()).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));

      Optional <Visa> foundvisa = Optional.ofNullable(visaRepository.getVisaByRelationshipIdAndType(client.getId(), RelationshipType.USERDETAILS)
              .orElseThrow(() -> new NoSuchElementException("Visa Not Added Yet")));
        if(foundvisa.isEmpty()) {


            Visa visa = Visa.builder()
                    .relationshipId(client.getId())
                    .type(RelationshipType.USERDETAILS)
                    .visaType(request.getVisaType())
                    .issueDate(request.getVisaIssueDate())
                    .expiryDate(request.getVisaExpiryDate())
                    .country(request.getCountry())
                    .build();

            visaRepository.save(visa);
            return new ApiResponseClass("Visa Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), visa);
        }
        else
        {
        var visa= foundvisa.get();
        if(request.getVisaType()!=null )
            visa.setVisaType(request.getVisaType());

        if(request.getVisaIssueDate()!=null )
            visa.setIssueDate(request.getVisaIssueDate());

        if(request.getVisaExpiryDate()!=null )
            visa.setExpiryDate(request.getVisaExpiryDate());

        if(request.getCountry()!=null )
            visa.setCountry(request.getCountry());
            return new ApiResponseClass("Visa Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundvisa);

        }

    }

    public ApiResponseClass GetMyPassport() {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        var client= clientRepository.findByEmail(authentication.getName()).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        Optional<Passport> foundPassport= Optional.ofNullable(passportRepository.getPassportByRelationshipIdAndType(client.getId(), RelationshipType.USERDETAILS)
                .orElseThrow(() -> new NoSuchElementException("No Passport ADDED yet")));
        return new ApiResponseClass("Passport Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPassport.get());

    }

    public ApiResponseClass GetMyPersonalId() {

        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        var client= clientRepository.findByEmail(authentication.getName()).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        Optional<Personalidenty> foundPersonalId= Optional.ofNullable(personalidentityRepository.getPersonalidentyByRelationshipIdAndType(client.getId(), RelationshipType.USERDETAILS)
                .orElseThrow(() -> new NoSuchElementException("Personal ID Not ADDED yet")));
        return new ApiResponseClass("Personal ID Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPersonalId.get());

    }

    public ApiResponseClass GetMyVisa() {

        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        var client= clientRepository.findByEmail(authentication.getName()).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        Optional<Visa> foundVisa= Optional.ofNullable(visaRepository.getVisaByRelationshipIdAndType(client.getId(), RelationshipType.USERDETAILS)
                .orElseThrow(() -> new NoSuchElementException("Visa  Not ADDED yet")));
        return new ApiResponseClass("Visa Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundVisa.get());

    }
}
