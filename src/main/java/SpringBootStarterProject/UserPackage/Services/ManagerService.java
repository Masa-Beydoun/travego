package SpringBootStarterProject.UserPackage.Services;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Security.Config.JwtService;
import SpringBootStarterProject.ManagingPackage.Security.Config.RateLimiterConfig;
import SpringBootStarterProject.ManagingPackage.Security.Token.*;
import SpringBootStarterProject.ManagingPackage.Security.auth.AuthenticationResponse;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.email.EmailService;
import SpringBootStarterProject.ManagingPackage.exception.EmailTakenException;
import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Models.Manager;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import SpringBootStarterProject.UserPackage.Repositories.ManagerRepository;
import SpringBootStarterProject.UserPackage.Request.*;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ObjectsValidator<LoginRequest>LoginRequestValidator;
    private final ObjectsValidator<ManagerRegisterRequest> ManagerRegisterValidator;
    private final ObjectsValidator<ClientRegisterRequest> ClientRegisterRequest;

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



    public ApiResponseClass AddAdminToSystem(ManagerRegisterRequest request) {
        ManagerRegisterValidator.validate(request);

        String Company_Email = request.getFirst_name() + request.getLast_name()+managerRepository.count()+"@TRAVEGO.com";
        var manager= Manager.builder()
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .email(Company_Email)
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .creationDate(LocalDateTime.now())
                .active(true)
                .build();

        managerRepository.save(manager);
        emailService.sendManagerMail(request.getFirst_name(),request.getEmail(),Company_Email);
        if (request.getRole().name().equals("ADMIN"))

            return  new ApiResponseClass("ADMIN ADDEDD TO SYSTEM SUCCSESSFULLY", HttpStatus.ACCEPTED, LocalDateTime.now());
        //  return ResponseEntity.ok().body("ADMIN ADDEDD TO SYSTEM SUCCSESSFULLY");

        return  new ApiResponseClass("SUPERADMIN ADDEDD TO SYSTEM SUCCSESSFULLY",HttpStatus.ACCEPTED,LocalDateTime.now());
        // return ResponseEntity.ok().body("SuperVisor ADDEDD TO SYSTEM SUCCSESSFULLY");

    }

    public ApiResponseClass ManagerLogin(LoginRequest request) {
        LoginRequestValidator.validate(request);

        Optional<Manager> theManager= managerRepository.findByEmail(request.getEmail());
        if(theManager.isEmpty())
            throw new UsernameNotFoundException("Email not found , please Contact The Admin");
        Authentication authentication;
        try {

            authentication=  authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    ));
        } catch (AuthenticationException exception) {
            throw new BadCredentialsException("invalid email or password");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Map<String, Object> extraClaims = new HashMap<>();
        Object Type= "Manager";
        extraClaims.put("UserType", Type);

        Manager manager=theManager.get();
      var  jwtToken= jwtService.generateToken(extraClaims,manager); ;
        RevokeAllManagerTokens(manager);
        SaveManagerToken(manager,jwtToken);

        Map<String,Object> response = new HashMap<>();
        response.put("Token",jwtToken);
        response.put("User",manager);




        return  new ApiResponseClass("LOGIN SUCCSESSFULLY",HttpStatus.ACCEPTED,LocalDateTime.now(), response);


        //  return ResponseEntity.ok().body(authResponse);
    }

    private void RevokeAllManagerTokens(Manager manager)
    {
        var ValidClientTokens= tokenRepository.findAllValidManagerTokensByRelationId(manager.getId());
        if(ValidClientTokens.isEmpty())
            return;
        ValidClientTokens.forEach(token ->{
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(ValidClientTokens);

    }

    private void SaveManagerToken(Manager manager, String jwtToken)
    {

        var token= Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .RelationId(manager.getId())
                .type(RelationshipType.MANAGER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public ApiResponseClass GetAllAdmins() {
        if(managerRepository.count()==0)
            throw new NoSuchElementException(" NO ADMIN ADDED YET");

        var managers= managerRepository.findAll();
        return new ApiResponseClass("ADMINS RETURNED SUCCESSFULLY",HttpStatus.ACCEPTED,LocalDateTime.now(),managers);


    }

    public ApiResponseClass GetAllClient() {
        if(clientRepository.count()==0)
            throw new NoSuchElementException(" NO Client ADDED YET");

        return new ApiResponseClass("Client RETURNED SUCCESSFULLY",HttpStatus.ACCEPTED,LocalDateTime.now(),clientRepository.findAll());


    }


    public ApiResponseClass CreateClinet(ClientRegisterRequest request)
    {
        ClientRegisterRequest.validate(request);

        if(!clientRepository.findClientByUsername(request.getUsername()).isEmpty())
            throw new EmailTakenException("User name Taken");

        var client = Client.builder()
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .username(request.getUsername())
                .email(request.getEmail())
                .phone_number(request.getPhone_number())
                .creationDate(LocalDateTime.now())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .build();
        clientRepository.save(client);
        return new ApiResponseClass("CLIENT CREATED SUCCESSFULLY",HttpStatus.CREATED,LocalDateTime.now(),client);
    }

    public ApiResponseClass DeleteClient(EmailRequest request) {
        var foundClient = clientRepository.findById(request.getId());
        if(foundClient.isEmpty())
            throw new UsernameNotFoundException("Client Not Found");
        clientRepository.deleteById(request.getId());
        return new ApiResponseClass("Client Deleted Successfully",HttpStatus.ACCEPTED,LocalDateTime.now(),null);
    }

    public ApiResponseClass DeleteManager(EmailRequest request) {
        var foundmanager = managerRepository.findById(request.getId());
        if(foundmanager.isEmpty())
            throw new UsernameNotFoundException("Manager Not Found");
        managerRepository.deleteById(request.getId());
        return new ApiResponseClass("Manager Deleted Successfully",HttpStatus.ACCEPTED,LocalDateTime.now(),null);
    }

    public void ManagerActivation(EmailRequest request) {

        var foundManager = managerRepository.findById(request.getId());
        if(foundManager.isEmpty())
            throw new UsernameNotFoundException("Manager Not Found");
       var manager = foundManager.get();
       manager.setActive(!manager.getActive());
       managerRepository.save(manager);

    }

    public void ClientActivation(EmailRequest request) {

        var foundClient = clientRepository.findById(request.getId());
        if(foundClient.isEmpty())
            throw new UsernameNotFoundException("Client Not Found");
        var client = foundClient.get();
        client.setActive(!client.getActive());
        clientRepository.save(client);

    }

    //TODO :: EDIT CLIENT DETAILS
    public ApiResponseClass EditClient( EditClientRequest request) {
       var client = clientRepository.findById(request.getId()).orElseThrow(()->new NoSuchElementException("Client Not Found"));

        if (request.getFirst_name() != null && !request.getFirst_name().isEmpty()) {
            client.setFirst_name(request.getFirst_name());
        }

        if (request.getLast_name() != null && !request.getLast_name().isEmpty()) {
            client.setLast_name(request.getLast_name());
        }

        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
            client.setUsername(request.getUsername());
        }

       var updatedClient= clientRepository.save(client);
return new ApiResponseClass("Client Updated Successfully",HttpStatus.ACCEPTED,LocalDateTime.now(),updatedClient);
    }

    public ApiResponseClass EditManager(EditManagerRequest request) {
        var manager = managerRepository.findById(request.getId()).orElseThrow(()->new NoSuchElementException("Client Not Found"));

        if (request.getFirst_name() != null && !request.getFirst_name().isEmpty()) {
            manager.setFirst_name(request.getFirst_name());
        }

        if (request.getLast_name() != null && !request.getLast_name().isEmpty()) {
            manager.setLast_name(request.getLast_name());
        }

        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
            manager.setUsername(request.getUsername());
        }

        if (request.getRole() != null ) {
            manager.setRole(request.getRole());
        }

        var updatedManger= managerRepository.save(manager);
        return new ApiResponseClass("Manager Updated Successfully",HttpStatus.ACCEPTED,LocalDateTime.now(),updatedManger);
    }


    public ApiResponseClass ManagerChangePassword(ChangePasswordRequest request, Principal connectedUser) {
//        // Step 1: Extract the principal's identifier (e.g., username)
//        String username = connectedUser.getName(); // Adjust based on how your Principal is structured
//        System.out.println(username);
//        // Step 2: Fetch the Client object using the username
//        Optional<Manager> optionalManager = managerRepository.findByEmail(username);
//
//        if (!optionalManager.isPresent()) {
//            throw new BadCredentialsException("Manager not found");
//        }

      //  Manager manager = optionalManager.get();
        Manager manager = (Manager) ((UsernamePasswordAuthenticationToken) connectedUser).getCredentials();
        if(!passwordEncoder.matches(request.getOldPassword(),manager.getPassword()))
            throw new BadCredentialsException("Password not Correct");

        if (request.getNewPassword() .equals( request.getConfirmationPassword()))
            throw new BadCredentialsException("Password Does Not Match ");
        manager.setPassword(passwordEncoder.encode(request.getNewPassword()));

        managerRepository.save(manager);

        return new ApiResponseClass("Password Changed Successfully ",HttpStatus.ACCEPTED,LocalDateTime.now(),manager);
    }


}

