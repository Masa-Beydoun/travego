package SpringBootStarterProject.UserPackage.Services;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Security.Config.JwtService;
import SpringBootStarterProject.ManagingPackage.Security.Config.RateLimiterConfig;
import SpringBootStarterProject.ManagingPackage.Security.Token.*;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.email.EmailService;
import SpringBootStarterProject.ManagingPackage.email.EmailStructure;
import SpringBootStarterProject.ManagingPackage.exception.EmailTakenException;
import SpringBootStarterProject.TripReservationPackage.Enum.ConfirmationStatue;
import SpringBootStarterProject.TripReservationPackage.Models.ConfirmationPassengersDetails;
import SpringBootStarterProject.TripReservationPackage.Repository.ConfirmationPassengerDetailsRepository;
import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Models.Manager;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import SpringBootStarterProject.UserPackage.Repositories.ManagerRepository;
import SpringBootStarterProject.UserPackage.Request.*;
import SpringBootStarterProject.UserPackage.RolesAndPermission.Roles;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private static final String LOGIN_RATE_LIMITER = "loginRateLimiter";
    private final ObjectsValidator<LoginRequest> LoginRequestValidator;
    private final ObjectsValidator<ManagerRegisterRequest> ManagerRegisterValidator;
    private final ObjectsValidator<ClientRegisterRequest> ClientRegisterRequest;
    private final ObjectsValidator<ChangePasswordRequest> ChangePasswordRequest;
    //TODO:: TRY   private final ObjectsValidator<Object>validator;
    private final ConfirmationPassengerDetailsRepository confirmationPassengerDetailsRepository;
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
    private final ClientRepository clinetRepository;


    public ApiResponseClass AddAdminToSystem(ManagerRegisterRequest request) {
        ManagerRegisterValidator.validate(request);

        String Company_Email = request.getFirst_name() + request.getLast_name() + managerRepository.count() + "@TRAVEGO.com";
        var manager = Manager.builder()
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .email(Company_Email)
                .theusername(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .creationDate(LocalDateTime.now())
                .active(true)
                .build();

        managerRepository.save(manager);
        emailService.sendManagerMail(request.getFirst_name(), request.getEmail(), Company_Email);
        if (request.getRole().name().equals("ADMIN"))

            return new ApiResponseClass("ADMIN ADDED TO SYSTEM SUCCESSFULLY", HttpStatus.ACCEPTED, LocalDateTime.now());
        //  return ResponseEntity.ok().body("ADMIN ADDED TO SYSTEM SUCCESSFULLY");

        return new ApiResponseClass("SUPERADMIN ADDED TO SYSTEM SUCCESSFULLY", HttpStatus.ACCEPTED, LocalDateTime.now());
        // return ResponseEntity.ok().body("SuperVisor ADDED TO SYSTEM SUCCESSFULLY");

    }

    public ApiResponseClass ManagerLogin(LoginRequest request) {
        LoginRequestValidator.validate(request);

        Optional<Manager> theManager = managerRepository.findByEmail(request.getEmail());
        if (theManager.isEmpty())
            throw new UsernameNotFoundException("Email not found , please Contact The Admin");
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
        Map<String, Object> extraClaims = new HashMap<>();
        Object Type = "Manager";
        extraClaims.put("UserType", Type);

        Manager manager = theManager.get();
        var jwtToken = jwtService.generateToken(extraClaims, manager);
        ;
        RevokeAllManagerTokens(manager);
        SaveManagerToken(manager, jwtToken);

        Map<String, Object> response = new HashMap<>();
        response.put("Token", jwtToken);
        response.put("User", manager);


        return new ApiResponseClass("LOGIN SUCCSESSFULLY", HttpStatus.ACCEPTED, LocalDateTime.now(), response);


        //  return ResponseEntity.ok().body(authResponse);
    }

    private void RevokeAllManagerTokens(Manager manager) {
        var ValidClientTokens = tokenRepository.findAllValidManagerTokensByRelationId(manager.getId());
        if (ValidClientTokens.isEmpty())
            return;
        ValidClientTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(ValidClientTokens);

    }

    private void SaveManagerToken(Manager manager, String jwtToken) {

        var token = Token.builder()
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
        if (managerRepository.count() == 0)
            throw new NoSuchElementException(" NO ADMIN ADDED YET");
        Pageable pageable= PageRequest.of(0,20);
        var managers = managerRepository.findByRole(Roles.ADMIN,pageable);
        return new ApiResponseClass("ADMINS RETURNED SUCCESSFULLY", HttpStatus.ACCEPTED, LocalDateTime.now(), managers);


    }

    public ApiResponseClass GetAllClient() {
        if (clientRepository.count() == 0)
            throw new NoSuchElementException(" NO Client ADDED YET");
        clientRepository.findAll(Pageable.ofSize(20));
        return new ApiResponseClass("Client RETURNED SUCCESSFULLY", HttpStatus.ACCEPTED, LocalDateTime.now(),  clientRepository.findAll(Pageable.ofSize(20)));


    }


    public ApiResponseClass CreateClinet(ClientRegisterRequest request) {
        ClientRegisterRequest.validate(request);

        if (!clientRepository.findClientByTheusername(request.getUsername()).isEmpty())
            throw new EmailTakenException("User name Taken");

        var client = Client.builder()
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .theusername(request.getUsername())
                .email(request.getEmail())
                .phone_number(request.getPhone_number())
                .creationDate(LocalDateTime.now())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .build();
        clientRepository.save(client);
        return new ApiResponseClass("CLIENT CREATED SUCCESSFULLY", HttpStatus.CREATED, LocalDateTime.now(), client);
    }

    public ApiResponseClass DeleteClient(EmailRequest request) {
        var foundClient = clientRepository.findById(request.getId());
        if (foundClient.isEmpty())
            throw new UsernameNotFoundException("Client Not Found");
        clientRepository.deleteById(request.getId());
        return new ApiResponseClass("Client Deleted Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), null);
    }

    public ApiResponseClass DeleteManager(EmailRequest request) {
        var foundmanager = managerRepository.findById(request.getId());
        if (foundmanager.isEmpty())
            throw new UsernameNotFoundException("Manager Not Found");
        managerRepository.deleteById(request.getId());
        return new ApiResponseClass("Manager Deleted Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), null);
    }

    public void ManagerActivation(EmailRequest request) {

        var foundManager = managerRepository.findById(request.getId());
        if (foundManager.isEmpty())
            throw new UsernameNotFoundException("Manager Not Found");
        var manager = foundManager.get();
        manager.setActive(!manager.getActive());
        managerRepository.save(manager);

    }

    public void ClientActivation(EmailRequest request) {

        var foundClient = clientRepository.findById(request.getId());
        if (foundClient.isEmpty())
            throw new UsernameNotFoundException("Client Not Found");
        var client = foundClient.get();
        client.setActive(!client.getActive());
        clientRepository.save(client);

    }

    //TODO :: EDIT CLIENT DETAILS
    public ApiResponseClass EditClient(EditClientRequest request) {
        var client = clientRepository.findById(request.getId()).orElseThrow(() -> new NoSuchElementException("Client Not Found"));

        if (request.getFirst_name() != null && !request.getFirst_name().isEmpty()) {
            client.setFirst_name(request.getFirst_name());
        }

        if (request.getLast_name() != null && !request.getLast_name().isEmpty()) {
            client.setLast_name(request.getLast_name());
        }

        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
            client.setTheusername(request.getUsername());
        }

        var updatedClient = clientRepository.save(client);
        return new ApiResponseClass("Client Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), updatedClient);
    }

    public ApiResponseClass EditManager(EditManagerRequest request) {
        var manager = managerRepository.findById(request.getId()).orElseThrow(() -> new NoSuchElementException("Client Not Found"));

        if (request.getFirst_name() != null && !request.getFirst_name().isEmpty()) {
            manager.setFirst_name(request.getFirst_name());
        }

        if (request.getLast_name() != null && !request.getLast_name().isEmpty()) {
            manager.setLast_name(request.getLast_name());
        }

        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
            manager.setTheusername(request.getUsername());
        }

        if (request.getRole() != null) {
            manager.setRole(request.getRole());
        }

        var updatedManger = managerRepository.save(manager);
        return new ApiResponseClass("Manager Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), updatedManger);
    }


    public ApiResponseClass ManagerChangePassword(ChangePasswordRequest request, Principal connectedUser) {


        UserDetails userDetails = (UserDetails) (((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());


        if (!passwordEncoder.matches(request.getOldPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Password not Correct");
        }
        ChangePasswordRequest.validate(request);

        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new BadCredentialsException("New Password Does Not Match Confirmation Password ");
        }

        if (request.getNewPassword().equals(request.getOldPassword())) {
            throw new BadCredentialsException("The Password Same As The Old one , Please Change it ");
        }
        Manager updatedUser = managerRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Client with email " + userDetails.getUsername() + " not found"));

        // Update password securely (use setter or dedicated method)
        updatedUser.setPassword(passwordEncoder.encode(request.getNewPassword()));

        managerRepository.save(updatedUser);


        return new ApiResponseClass("Password Changed Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());

    }

    public ApiResponseClass GetAllReservationRequestForTrip(Integer Trip_Id) {

        Pageable pageable = PageRequest.of(0, 20);
        Page<ConfirmationPassengersDetails> Reservation = confirmationPassengerDetailsRepository.getAllPassengerDetailsRequestByTripId(Trip_Id, pageable);
        if (!Reservation.isEmpty())
            return new ApiResponseClass("All Reservation for Trip With Id " + Trip_Id + " Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), Reservation);

        throw new NoSuchElementException("There Is No Reservation For THis Trip Yet");
    }

    public ApiResponseClass EditReservationRequestStatueForTrip(ConfirmationPassengerInTripRequest request) {


        ConfirmationPassengersDetails Reservation = confirmationPassengerDetailsRepository.getById(request.getTrip_Id());
        if (Reservation != null) {

            Reservation.setConfirmation_statue(request.getConfirmation_statue().name());
            Reservation.setDescription(request.getDescription());
            if (request.getConfirmation_statue().name() == ConfirmationStatue.APPROVED.name()) {
                var client = clinetRepository.findByEmail(Reservation.getUser_email()).get();
                EmailStructure emailStructure = EmailStructure.builder()
                        .subject("Resevrvation In Trip ")
                        .message("Mr. " + client.getFirst_name() + " Your Reservation" + Reservation.getId() + " For Trip With Id " + Reservation.getTripReservation().getTrip_id() + " Approved Successfully  ")
                        .build();

                emailService.sendMail(Reservation.getUser_email(), emailStructure);
            } else if (request.getConfirmation_statue().name() == ConfirmationStatue.REJECTED.name()) {
                var client = clinetRepository.findByEmail(Reservation.getUser_email()).get();
                EmailStructure emailStructure = EmailStructure.builder()
                        .subject("Resevrvation In Trip ")
                        .message("Mr. " + client.getFirst_name() + " Your Reservation" + Reservation.getId() + " Your Reservation For Trip With Id " + Reservation.getTripReservation().getTrip_id() + " has been Rejectd , You Can See The Discription In the Application For more Informaion ")
                        .build();

                emailService.sendMail(Reservation.getUser_email(), emailStructure);
            }
            return new ApiResponseClass("Reservation Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), Reservation);

        }
        throw new NoSuchElementException("There Is No Reservation For THis Trip Yet");
    }
}

