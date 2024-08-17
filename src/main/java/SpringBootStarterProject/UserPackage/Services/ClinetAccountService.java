package SpringBootStarterProject.UserPackage.Services;

import SpringBootStarterProject.ManagingPackage.Paypal.PaypalService;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Security.Config.JwtService;
import SpringBootStarterProject.ManagingPackage.Security.Config.RateLimiterConfig;
import SpringBootStarterProject.ManagingPackage.Security.Token.NumberConfirmationToken;
import SpringBootStarterProject.ManagingPackage.Security.Token.NumberConfirmationTokenRepository;
import SpringBootStarterProject.ManagingPackage.Security.Token.TokenRepository;
import SpringBootStarterProject.ManagingPackage.Utils.UtilsService;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.email.EmailService;
import SpringBootStarterProject.ManagingPackage.email.EmailStructure;
import SpringBootStarterProject.ReviewsPackage.Models.TripReview;
import SpringBootStarterProject.ReviewsPackage.Repository.TripReviewRepository;
import SpringBootStarterProject.TripReservationPackage.Enum.ConfirmationStatue;
import SpringBootStarterProject.TripReservationPackage.Models.ConfirmationPassengersDetails;
import SpringBootStarterProject.TripReservationPackage.Models.TripReservation;
import SpringBootStarterProject.TripReservationPackage.Repository.ConfirmationPassengerDetailsRepository;
import SpringBootStarterProject.TripReservationPackage.Repository.TripReservationRepository;
import SpringBootStarterProject.Trippackage.Repository.TripRepository;
import SpringBootStarterProject.Trippackage.Service.TripService;
import SpringBootStarterProject.UserPackage.Models.*;
import SpringBootStarterProject.UserPackage.Repositories.*;
import SpringBootStarterProject.UserPackage.Request.*;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ClinetAccountService {
    private static final String LOGIN_RATE_LIMITER = "loginRateLimiter";
    private final ObjectsValidator<AddPassengerRequest> addPassengerRequestValidator;
    private final ObjectsValidator<Client> EditClientValidator;
    private final ObjectsValidator<ClientRegisterRequest> ClientRegisterValidator;
    private final ClientAuthService clientAuthService;
    private final ObjectsValidator<LoginRequest> LoginRequestValidator;
    private final ObjectsValidator<ManagerRegisterRequest> ManagerRequestValidator;
    private final ObjectsValidator<PassportRequest> PassportRequestValidator;
    private final ObjectsValidator<PersonalidRequest> PersonalIdRequestValidator;
    private final ObjectsValidator<VisaRequest> VisaRequestValidator;
    private final ObjectsValidator<CreateWalletRequest> CreateWalletRequestValidator;
    //TODO:: TRY   private final ObjectsValidator<Object>validator;
    private final MoneyCodeRepository moneyCodeRepository;
    private final PassengerRepository passengerRepository;
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
    private final WalletRepository walletRepository;
    private final ManagerRepository managerRepository;
    private final JwtService jwtService;
    private final RateLimiterConfig rateLimiterConfig;
    private final RateLimiterRegistry rateLimiterRegistry;
    private final TripRepository tripRepository;
    private final PaypalService paypalService;
    private final TransactionRepository transactionRepository;
    private final TripReservationRepository tripReservationRepository;
    private final ConfirmationPassengerDetailsRepository confirmationPassengerDetailsRepository;
    private final UtilsService utilsService;
    private final TripService tripService;
    private final TripReviewRepository tripReviewRepository;

    private final ObjectsValidator<ChangePasswordRequest> ChangePasswordRequest;

    public ApiResponseClass GetMyAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());


        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return new ApiResponseClass("PROFILE RETURED SUCCESSFULLY", HttpStatus.ACCEPTED, LocalDateTime.now(), client);
    }

    public ApiResponseClass EditMyAccount(EditClientRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        if (request.getFirst_name() != null) client.setFirst_name(request.getFirst_name());

        if (request.getLast_name() != null) client.setLast_name(request.getLast_name());
        if (request.getUsername() != null) client.setTheusername(request.getUsername());
        if (request.getPhone_number() != null) client.setPhone_number(request.getPhone_number());

        EditClientValidator.validate(client);

        var User = clientRepository.save(client);
        Map<String, Object> response = new HashMap<>();
        response.put("User", User);
        return new ApiResponseClass("Profile Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), response);
    }


    public ApiResponseClass AddMyDetails(ClientDetailsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        var foundclientDetails = clientDetailsRepository.findClientDetailsByClient(client);

        if (foundclientDetails == null) {
            ClientDetails clientDetails = ClientDetails.builder().client(client).gender(request.getGender()).birthdate(request.getBirthDate()).father_name(request.getFather_name()).mother_name(request.getMother_name()).build();
            clientDetailsRepository.save(clientDetails);

            Map<String, Object> response = new HashMap<>();
            response.put("gender", client.getClientDetails().getGender());
            response.put("birthDate", client.getClientDetails().getBirthdate());
            response.put("father_name", client.getClientDetails().getFather_name());
            response.put("mother_name", client.getClientDetails().getMother_name());

//            AddClientDetailsResponse response = AddClientDetailsResponse.builder()
//                    .gender(clientDetails.getGender())
//                    .birthDate(clientDetails.getBirthdate())
//                    .father_name(clientDetails.getFather_name())
//                    .mother_name(clientDetails.getMother_name())
//                    .build();

            return new ApiResponseClass("Details Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), response);

        } else {


            if (request.getGender() != null) foundclientDetails.setGender(request.getGender());

            if (request.getBirthDate() != null) foundclientDetails.setBirthdate(request.getBirthDate());

            if (request.getFather_name() != null) foundclientDetails.setFather_name(request.getFather_name());

            if (request.getMother_name() != null) foundclientDetails.setMother_name(request.getMother_name());
            clientDetailsRepository.save(foundclientDetails);

//            AddClientDetailsResponse response = AddClientDetailsResponse.builder()
//                    .gender(foundclientDetails.getGender())
//                    .birthDate(foundclientDetails.getBirthdate())
//                    .father_name(foundclientDetails.getFather_name())
//                    .mother_name(foundclientDetails.getMother_name())
//                    .build();
            Map<String, Object> response = new HashMap<>();
            response.put("gender", client.getClientDetails().getGender());
            response.put("birthDate", client.getClientDetails().getBirthdate());
            response.put("father_name", client.getClientDetails().getFather_name());
            response.put("mother_name", client.getClientDetails().getMother_name());
            return new ApiResponseClass("Details Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), response);

        }

    }

    public ApiResponseClass GetMyDetails(ClientDetailsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        var foundclientDetails = clientDetailsRepository.findClientDetailsByClient(client);

        return new ApiResponseClass("Details Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundclientDetails);

    }

    public ApiResponseClass AddMyPassport(PassportRequest request) {

        PassportRequestValidator.validate(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Optional<Passport> foundPassport = passportRepository.getPassportByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT);

        if (foundPassport.isEmpty()) {
            Passport passport = Passport.builder().
                    relationshipId(client.getId()).
                    type(RelationshipType.CLIENT).
                    firstName(request.getPassportfirstName()).
                    lastName(request.getPassportlastName()).
                    passport_issue_date(request.getPassportIssueDate())
                    .passport_expires_date(request.getPassportExpiryDate())
                    .passport_number(request.getPassport_number())
                    .passport_PHOTO(request.getPassport_PHOTO())
                    .build();
            passportRepository.save(passport);
            updateFullDocsStatus(client.getId(), RelationshipType.CLIENT, null);
            return new ApiResponseClass(" CLIENT Passport Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), passport);
        } else {
            var passport = foundPassport.get();
            if (request.getPassportfirstName() != null) passport.setFirstName(request.getPassportfirstName());

            if (request.getPassportlastName() != null) passport.setLastName(request.getPassportlastName());

            if (request.getPassportIssueDate() != null) passport.setPassport_issue_date(request.getPassportIssueDate());

            if (request.getPassportExpiryDate() != null)
                passport.setPassport_expires_date(request.getPassportExpiryDate());

            if (request.getPassport_number() != null) passport.setPassport_number(request.getPassport_number());

            if (request.getPassport_PHOTO() != null) passport.setPassport_PHOTO(request.getPassport_PHOTO());
            passportRepository.save(passport);
            return new ApiResponseClass("CLIENT Passport Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPassport);

        }
    }

    // @Transactional
    public ApiResponseClass AddMyPassengersPassport(PassportRequest request) {
        Optional<Passport> foundPassport = passportRepository.getPassportByRelationshipIdAndType(request.getPassengerId(), RelationshipType.PASSENGER);

        if (foundPassport.isEmpty()) {

            Passport passport = Passport.builder().
                    relationshipId(request.getPassengerId()).
                    type(RelationshipType.PASSENGER).
                    firstName(request.getPassportfirstName()).
                    lastName(request.getPassportlastName()).
                    passport_issue_date(request.getPassportIssueDate()).
                    passport_expires_date(request.getPassportExpiryDate()).
                    passport_number(request.getPassport_number()).
                    passport_PHOTO(request.getPassport_PHOTO()).
                    build();
            passportRepository.save(passport);

            String uniqueName = passengerRepository.getReferenceById(request.getPassengerId()).getUniqueName();
            updateFullDocsStatus(null, RelationshipType.PASSENGER, uniqueName);
            return new ApiResponseClass("Passenger Passport Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), passport);
        } else {
            var passport = foundPassport.get();
            if (request.getPassportfirstName() != null) passport.setFirstName(request.getPassportfirstName());

            if (request.getPassportlastName() != null) passport.setLastName(request.getPassportlastName());

            if (request.getPassportIssueDate() != null) passport.setPassport_issue_date(request.getPassportIssueDate());

            if (request.getPassportExpiryDate() != null)
                passport.setPassport_expires_date(request.getPassportExpiryDate());

            if (request.getPassport_number() != null) passport.setPassport_number(request.getPassport_number());

            if (request.getPassport_PHOTO() != null) passport.setPassport_PHOTO(request.getPassport_PHOTO());
            passportRepository.save(passport);
            return new ApiResponseClass("Passenger Passport Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPassport);

        }
    }


    public ApiResponseClass AddMyPersonalid(PersonalidRequest request) {
        PersonalIdRequestValidator.validate(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Optional<Personalidenty> foundPersonalId = personalidentityRepository.getPersonalidentyByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT);
        if (foundPersonalId.isEmpty()) {

            Personalidenty personalidenty = Personalidenty.builder().
                    relationshipId(client.getId()).
                    type(RelationshipType.CLIENT).
                    firstName(request.getIdfirstName()).
                    lastName(request.getIdlastName()).
                    birthdate(request.getIdBirthDate()).
                    nationality(request.getNationality()).
                    PersonalIdentity_PHOTO(request.getPersonalIdentity_PHOTO())
                    .build();
            personalidentityRepository.save(personalidenty);
            updateFullDocsStatus(client.getId(), RelationshipType.CLIENT, null);
            return new ApiResponseClass("CLIENT Personal ID Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), personalidenty);
        } else {
            var personalId = foundPersonalId.get();
            if (request.getIdfirstName() != null) personalId.setFirstName(request.getIdfirstName());

            if (request.getIdlastName() != null) personalId.setLastName(request.getIdlastName());

            if (request.getNationality() != null) personalId.setNationality(request.getNationality());

            if (request.getIdBirthDate() != null) personalId.setBirthdate(request.getIdBirthDate());

            if (request.getPersonalIdentity_PHOTO() != null)
                personalId.setPersonalIdentity_PHOTO(request.getPersonalIdentity_PHOTO());
            personalidentityRepository.save(personalId);
            return new ApiResponseClass("CLIENT Personal ID Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPersonalId);


        }
    }

    public ApiResponseClass AddPassengerPersonalid(PersonalidRequest request) {

        Optional<Personalidenty> foundPersonalId = personalidentityRepository.getPersonalidentyByRelationshipIdAndType(request.getPassengerId(), RelationshipType.PASSENGER);
        if (foundPersonalId.isEmpty()) {

            Personalidenty personalidenty = Personalidenty.builder().
                    relationshipId(request.getPassengerId()).
                    type(RelationshipType.PASSENGER).
                    firstName(request.getIdfirstName()).
                    lastName(request.getIdlastName()).
                    birthdate(request.getIdBirthDate()).
                    nationality(request.getNationality()).
                    PersonalIdentity_PHOTO(request.getPersonalIdentity_PHOTO()).
                    build();
            personalidentityRepository.save(personalidenty);
            String uniqueName = passengerRepository.getReferenceById(request.getPassengerId()).getUniqueName();
            updateFullDocsStatus(null, RelationshipType.PASSENGER, uniqueName);
            return new ApiResponseClass("Passenger Personal ID Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), personalidenty);
        } else {
            var personalId = foundPersonalId.get();
            if (request.getIdfirstName() != null) personalId.setFirstName(request.getIdfirstName());

            if (request.getIdlastName() != null) personalId.setLastName(request.getIdlastName());

            if (request.getNationality() != null) personalId.setNationality(request.getNationality());

            if (request.getIdBirthDate() != null) personalId.setBirthdate(request.getIdBirthDate());

            if (request.getPersonalIdentity_PHOTO() != null)
                personalId.setPersonalIdentity_PHOTO(request.getPersonalIdentity_PHOTO());
            personalidentityRepository.save(personalId);
            return new ApiResponseClass("Passenger Personal ID Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPersonalId);


        }
    }

    public ApiResponseClass AddMyVisa(VisaRequest request) {

        VisaRequestValidator.validate(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        Optional<Visa> foundvisa = visaRepository.getVisaByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT);
        if (foundvisa.isEmpty()) {


            Visa visa = Visa.builder().
                    relationshipId(client.getId()).
                    type(RelationshipType.CLIENT).
                    visa_Type(request.getVisa_Type()).
                    visa_issue_date(request.getVisaIssueDate()).
                    visa_expires_date(request.getVisaExpiryDate()).
                    visa_Country(request.getVisa_Country()).
                    visa_PHOTO(request.getVisa_PHOTO()).
                    build();

            visaRepository.save(visa);
            updateFullDocsStatus(client.getId(), RelationshipType.CLIENT, null);
            return new ApiResponseClass("CLIENT Visa Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), visa);
        } else {
            var visa = foundvisa.get();
            if (request.getVisa_Type() != null) visa.setVisa_Type(request.getVisa_Type());

            if (request.getVisaIssueDate() != null) visa.setVisa_issue_date(request.getVisaIssueDate());

            if (request.getVisaExpiryDate() != null) visa.setVisa_expires_date(request.getVisaExpiryDate());

            if (request.getVisa_Country() != null) visa.setVisa_Country(request.getVisa_Country());

            if (request.getVisa_PHOTO() != null) visa.setVisa_PHOTO(request.getVisa_PHOTO());
            visaRepository.save(visa);
            return new ApiResponseClass("CLIENT Visa Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundvisa);

        }

    }

    public ApiResponseClass AddPassengerVisa(VisaRequest request) {

        Optional<Visa> foundvisa = visaRepository.getVisaByRelationshipIdAndType(request.getPassengerId(), RelationshipType.PASSENGER);
        if (foundvisa.isEmpty()) {


            Visa visa = Visa.builder().
                    relationshipId(request.getPassengerId()).
                    type(RelationshipType.PASSENGER).
                    visa_Type(request.getVisa_Type()).
                    visa_issue_date(request.getVisaIssueDate()).
                    visa_expires_date(request.getVisaExpiryDate()).
                    visa_Country(request.getVisa_Country()).
                    visa_PHOTO(request.getVisa_PHOTO()).
                    build();

            visaRepository.save(visa);
            String uniqueName = passengerRepository.getReferenceById(request.getPassengerId()).getUniqueName();
            updateFullDocsStatus(null, RelationshipType.PASSENGER, uniqueName);
            return new ApiResponseClass("PASSENGER Visa Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), visa);
        } else {
            var visa = foundvisa.get();
            if (request.getVisa_Type() != null) visa.setVisa_Type(request.getVisa_Type());

            if (request.getVisaIssueDate() != null) visa.setVisa_issue_date(request.getVisaIssueDate());

            if (request.getVisaExpiryDate() != null) visa.setVisa_expires_date(request.getVisaExpiryDate());

            if (request.getVisa_Country() != null) visa.setVisa_Country(request.getVisa_Country());

            if (request.getVisa_PHOTO() != null) visa.setVisa_PHOTO(request.getVisa_PHOTO());

            visaRepository.save(visa);
            return new ApiResponseClass("PASSENGER Visa Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundvisa);

        }
    }

    public ApiResponseClass GetMyPassport() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Optional<Passport> foundPassport = Optional.ofNullable(passportRepository.getPassportByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT).orElseThrow(() -> new NoSuchElementException("No Passport ADDED yet")));
        return new ApiResponseClass("Passport Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPassport.get());

    }

    public ApiResponseClass GetPassengerPassport(Integer id) {
        Optional<Passport> foundPassport = Optional.ofNullable(passportRepository.getPassportByRelationshipIdAndType(id, RelationshipType.PASSENGER).orElseThrow(() -> new NoSuchElementException("No Passport ADDED yet")));
        return new ApiResponseClass("PASSENGER Passport Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPassport.get());
    }

    public ApiResponseClass GetMyPersonalId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Optional<Personalidenty> foundPersonalId = Optional.ofNullable(personalidentityRepository.getPersonalidentyByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT).orElseThrow(() -> new NoSuchElementException("Personal ID Not ADDED yet")));
        return new ApiResponseClass("Personal ID Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPersonalId.get());

    }

    public ApiResponseClass GetPassengerPersonalId(Integer passengerId) {
        Optional<Personalidenty> foundPersonalId = Optional.ofNullable(personalidentityRepository.getPersonalidentyByRelationshipIdAndType(passengerId, RelationshipType.PASSENGER).orElseThrow(() -> new NoSuchElementException("PASSENGER Personal ID Not ADDED yet")));
        return new ApiResponseClass("PASSENGER Personal ID Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPersonalId.get());
    }

    public ApiResponseClass GetMyVisa() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Optional<Visa> foundVisa = Optional.ofNullable(visaRepository.getVisaByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT).orElseThrow(() -> new NoSuchElementException("Visa  Not ADDED yet")));
        return new ApiResponseClass("Visa Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundVisa.get());

    }

    public ApiResponseClass GetPassengerVisa(Integer passengerId) {
        Optional<Visa> foundVisa = Optional.ofNullable(visaRepository.getVisaByRelationshipIdAndType(passengerId, RelationshipType.PASSENGER).orElseThrow(() -> new NoSuchElementException("PASSENGER Visa  Not ADDED yet")));
        return new ApiResponseClass("PASSENGER Visa Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundVisa.get());
    }

    @Transactional
    public ApiResponseClass DeleteMyAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Optional<Wallet> balance = walletRepository.getWalletByClientId(client.getId());
        System.out.println("balance");
        if (!balance.isEmpty()) {
            if (balance.get().getBalance() != 0) {
                EmailStructure emailStructure = EmailStructure.builder().subject(" Money Added To Your Bank Account").message("Mr. " + client.getFirst_name() + ",Your Money In The Wallet Added to Your Bank Account After You Deletes Your Wallet  , " + client.getWallet().getBalance() + "$ Added To Your Account").build();
                // walletRepository.delete(client.getWallet());
                emailService.sendMail(client.getEmail(), emailStructure);
            }
        }
        var foundclientDetails = clientDetailsRepository.findClientDetailsByClient(client);
        System.out.println("clientDetails");
        if (foundclientDetails != null) clientDetailsRepository.delete(client.getClientDetails());
        //   clientDetailsRepository.delete(foundclientDetails);

        var passport = passportRepository.getPassportByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT);
        if (passport.isPresent()) passportRepository.delete(passport.get());
        System.out.println("passport");
        var visa = visaRepository.getVisaByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT);
        if (visa.isPresent()) visaRepository.delete(visa.get());

        var personalId = personalidentityRepository.getPersonalidentyByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT);

        if (personalId.isPresent()) personalidentityRepository.delete(personalId.get());

        //  var passengers =  passengerRepository.findPassengersByClientId(client.getId());

        //  if(!passengers.isEmpty())
        passengerRepository.deleteAll(client.getPassengers());
        //passengerRepository.deleteAll(passengers);

        clientAuthService.RevokeAllClientTokens(client);

        var Reservation = tripReservationRepository.findByClient(client);

        for (TripReservation reservation : Reservation) {
            ConfirmationPassengersDetails confirmationPassenger
                    = confirmationPassengerDetailsRepository.findConfirmationPassengersDetailsByTripReservationId(reservation.getId());

            confirmationPassengerDetailsRepository.delete(confirmationPassenger);
        }

        List<NumberConfirmationToken> tokens = numberConfTokenRepository.getAllByClientId(client.getId());
        if (!tokens.isEmpty())
            numberConfTokenRepository.deleteAll(tokens);

        Optional<List<TripReview>> trip = tripReviewRepository.findTripReviewsByClientId(client.getId());
        if(trip.isPresent())
        tripReviewRepository.deleteAll(trip.get());

        clientRepository.delete(client);


        return new ApiResponseClass("Account Deleted Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());

    }

    public ApiResponseClass DeleteMyPassport() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Optional<Passport> foundPassport = Optional.ofNullable(passportRepository.getPassportByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT).orElseThrow(() -> new NoSuchElementException("No Passport ADDED yet")));
        passportRepository.delete(foundPassport.get());
        return new ApiResponseClass("Passport Deleted Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());

    }

    public ApiResponseClass DeletePassenegrPassport(Integer id) {
        Optional<Passport> foundPassport = Optional.ofNullable(passportRepository.getPassportByRelationshipIdAndType(id, RelationshipType.PASSENGER).orElseThrow(() -> new NoSuchElementException("PASSENGER No Passport ADDED yet")));
        passportRepository.delete(foundPassport.get());
        return new ApiResponseClass("PASSENGER Passport Deleted Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());
    }

    public ApiResponseClass DeleteMyPersonalId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Optional<Personalidenty> foundPersonalId = Optional.ofNullable(personalidentityRepository.getPersonalidentyByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT).orElseThrow(() -> new NoSuchElementException("Personal ID Not ADDED yet")));
        personalidentityRepository.delete(foundPersonalId.get());
        return new ApiResponseClass("Personal ID Deleted Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());
    }

    public ApiResponseClass DeletePassenegrPersonalId(Integer id) {
        Optional<Personalidenty> foundPersonalId = Optional.ofNullable(personalidentityRepository.getPersonalidentyByRelationshipIdAndType(id, RelationshipType.PASSENGER).orElseThrow(() -> new NoSuchElementException("PASSENGER Personal ID Not ADDED yet")));
        personalidentityRepository.delete(foundPersonalId.get());
        return new ApiResponseClass("PASSENGER Personal ID Deleted Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());
    }

    public ApiResponseClass DeleteMyVisa() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Optional<Visa> foundVisa = Optional.ofNullable(visaRepository.getVisaByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT).orElseThrow(() -> new NoSuchElementException("Visa  Not ADDED yet")));
        visaRepository.delete(foundVisa.get());
        return new ApiResponseClass("Visa Deleted Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());
    }

    public ApiResponseClass DeletePassenegrVisa(Integer id) {
        Optional<Visa> foundVisa = Optional.ofNullable(visaRepository.getVisaByRelationshipIdAndType(id, RelationshipType.PASSENGER).orElseThrow(() -> new NoSuchElementException("PASSENGER Visa  Not ADDED yet")));
        visaRepository.delete(foundVisa.get());
        return new ApiResponseClass("PASSENGER Visa Deleted Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());
    }

    public ApiResponseClass AddMyPassengers(AddPassengerRequest request) {

        addPassengerRequestValidator.validate(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        var foundPassenger = passengerRepository.findPassengerByUniqueName(request.getFirst_name() + request.getFather_name() + request.getLast_name() + request.getBirthdate());
        if (foundPassenger.isEmpty()) {
            Passenger passenger = Passenger.builder().first_name(request.getFirst_name()).last_name(request.getLast_name()).client(client).gender(request.getGender()).birthdate(request.getBirthdate()).father_name(request.getFather_name()).mother_name(request.getMother_name()).uniqueName(request.getFirst_name() + request.getFather_name() + request.getLast_name() + request.getBirthdate()).build();
            passengerRepository.save(passenger);
            return new ApiResponseClass("Passenger Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), passenger);

        }
        return new ApiResponseClass("Passenger Already Exist", HttpStatus.ACCEPTED, LocalDateTime.now());


    }

    public ApiResponseClass EditMyPassenger(AddPassengerRequest request) {
        addPassengerRequestValidator.validate(request);
        Optional<Passenger> foundPassenger = Optional.ofNullable(passengerRepository.findById(request.getId()).orElseThrow(() -> new NoSuchElementException("passenger Not found")));

        var passenger = foundPassenger.get();
        if (request.getFirst_name() != null) passenger.setFirst_name(request.getFirst_name());

        if (request.getLast_name() != null) passenger.setLast_name(request.getLast_name());

        if (request.getFather_name() != null) passenger.setFather_name(request.getFather_name());

        if (request.getMother_name() != null) passenger.setMother_name(request.getMother_name());

        if (request.getBirthdate() != null) passenger.setBirthdate(request.getBirthdate());

        passenger.setUniqueName(request.getFirst_name() + request.getFather_name() + request.getLast_name() + request.getBirthdate());
        passengerRepository.save(passenger);

        return new ApiResponseClass("Passenger updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), passenger);
    }

    public ApiResponseClass DeleteMyPassenger(Integer id) {

        Optional<Passenger> foundPassenger = Optional.ofNullable(passengerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No passengers added yet")));

        passengerRepository.delete(foundPassenger.get());
        return new ApiResponseClass("Passenger Deleted Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());

    }

    public ApiResponseClass GetOnePassenger(Integer id) {

        var foundPassenger = passengerRepository.findById(id).orElseThrow(() -> new NoSuchElementException("passenger Not found"));
        return new ApiResponseClass("Passenger Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPassenger);

    }

    public ApiResponseClass GetMyAllPassengers() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        List<Passenger> allPassengers = passengerRepository.findPassengersByClientId(client.getId());
        if (allPassengers.isEmpty()) throw new NoSuchElementException("No passengers added yet ");

        return new ApiResponseClass("Passengers Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), allPassengers);

    }


    public ApiResponseClass CreateMyWallet(CreateWalletRequest request) {
        CreateWalletRequestValidator.validate(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        var clientOptional = clientRepository.findByEmail(email);
        if (clientOptional.isEmpty()) {
            throw new IllegalArgumentException("Client not found");
        }
        var client = clientOptional.get();
        if (client.getWallet() == null) {
            var wallet = Wallet.builder().client(client).balance(0).bankAccount(request.getBankAccount()).securityCode(passwordEncoder.encode(request.getSecurityCode())).build();

            walletRepository.save(wallet);

            EmailStructure emailStructure = EmailStructure.builder().subject("CREATING WALLET").message("Mr. " + client.getFirst_name() + " Your Wallet Added Successfully To Your Account").build();

            // Asynchronous email sending
            emailService.sendMail(email, emailStructure);


            return new ApiResponseClass("Wallet Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), wallet);
        } else {
            if (request.getBankAccount() != null) client.getWallet().setBankAccount(request.getBankAccount());
            clientRepository.save(client);
            return new ApiResponseClass("Wallet Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());

        }

    }


    public ApiResponseClass AddMoneyToWallet(MoneyCodeRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).get();

        if (client.getWallet() == null) throw new IllegalStateException("PLEASE CREATE WALLET FIRST");

        var foundcode = moneyCodeRepository.findMoneyCodeByCode(request.getCode());
        if (foundcode == null) throw new IllegalStateException("CODE NOT CORRECT");
        if (foundcode.isValid()) {

            client.getWallet().setBalance(foundcode.getBalance() + client.getWallet().getBalance());
            clientRepository.save(client);
            foundcode.setValid(false);
            moneyCodeRepository.save(foundcode);

            EmailStructure emailStructure = EmailStructure.builder().subject("Add Money To Wallet").message("Mr. " + client.getFirst_name() + ", Money Added to Your Account , Your Current Balance is " + client.getWallet().getBalance()).build();
            emailService.sendMail(client.getEmail(), emailStructure);


            return new ApiResponseClass("Money Added To Wallet Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());

        }

        throw new IllegalStateException("CODE NOT VALID");
    }

    public ApiResponseClass GetMyWallet() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).get();

        if (client.getWallet() == null) throw new IllegalStateException("PLEASE CREATE WALLET FIRST");

        return new ApiResponseClass("Wallet Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), client.getWallet());

    }


    public ApiResponseClass DeleteMyWallet() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).get();

        if (client.getWallet() == null) throw new IllegalStateException("PLEASE CREATE WALLET FIRST");


        if (client.getWallet().getBalance() != 0) {
            EmailStructure emailStructure = EmailStructure.builder().subject(" Money Added To Your Bank Account").message("Mr. " + client.getFirst_name() + ",Your Money In The Wallet Added to Your Bank Account After You Delets Your Wallet  , " + client.getWallet().getBalance() + "$ Added To Your Account").build();
            emailService.sendMail(client.getEmail(), emailStructure);
        }
        walletRepository.delete(client.getWallet());

        return new ApiResponseClass("Wallet Deleted Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());

    }


    private void updateFullDocsStatus(Integer id, RelationshipType type, String UniqueName) {
        if (UniqueName != null) {
            if (!passportRepository.getPassportByRelationshipIdAndType(id, type).isEmpty() &&
                    !personalidentityRepository.getPersonalidentyByRelationshipIdAndType(id, type).isEmpty() &&
                    !visaRepository.getVisaByRelationshipIdAndType(id, type).isEmpty()) {

                Client client = clientRepository.findById(id).get();
                client.setAddAllDocs(true);
                clientRepository.save(client);

            }

        } else {
            Passenger passenger = passengerRepository.findPassengerByUniqueName(UniqueName).get();
            int passengerId = passenger.getId();
            if (!passportRepository.getPassportByRelationshipIdAndType(passengerId, type).isEmpty() &&
                    !personalidentityRepository.getPersonalidentyByRelationshipIdAndType(passengerId, type).isEmpty() &&
                    !visaRepository.getVisaByRelationshipIdAndType(passengerId, type).isEmpty()) {
                passenger.setAddAllDocs(true);
                passengerRepository.save(passenger);
            }
        }
    }


    public ApiResponseClass GetAllPassengerDetails(Integer passenger_Id) {
        Map<String, Object> result = new HashMap<>();
        var passenger = passengerRepository.findById(passenger_Id)
                .orElseThrow(() -> new NoSuchElementException("Passenger with ID " + passenger_Id + " Not Found "));

        var passport = passportRepository.getPassportByRelationshipIdAndType(passenger.getId(), RelationshipType.PASSENGER)
                .orElseThrow(() -> new NoSuchElementException("Passport for Passenger with ID " + passenger_Id + " Not Found "));
        ;

        var visa = visaRepository.getVisaByRelationshipIdAndType(passenger.getId(), RelationshipType.PASSENGER)
                .orElseThrow(() -> new NoSuchElementException("Vesa for Passenger with ID " + passenger_Id + " Not Found "));
        ;

        var personalID = personalidentityRepository.getPersonalidentyByRelationshipIdAndType(passenger.getId(), RelationshipType.PASSENGER)
                .orElseThrow(() -> new NoSuchElementException("PersonalID for Passenger with ID " + passenger_Id + " Not Found "));
        ;

        result.put("firstname", passenger.getFirst_name());
        result.put("lastname", passenger.getLast_name());
        result.put("fathername", passenger.getFather_name());
        result.put("mothername", passenger.getMother_name());
        result.put("birthdate", passenger.getBirthdate());
        result.put("personalIdentity_PHOTO", personalID.getPersonalIdentity_PHOTO());
        result.put("passport_Issue_date", String.valueOf(passport.getPassport_issue_date()));
        result.put("passport_Expires_date", String.valueOf(passport.getPassport_expires_date()));
        result.put("passport_Number", passport.getPassport_number());
        result.put("passport_PHOTO", passport.getPassport_PHOTO());
        result.put("visa_Type", visa.getVisa_Type());
        result.put("visa_Country", visa.getVisa_Country());
        result.put("visa_Issue_date", String.valueOf(visa.getVisa_issue_date()));
        result.put("visa_Expires_date", String.valueOf(visa.getVisa_expires_date()));
        result.put("visa_PHOTO", visa.getVisa_PHOTO());

        return new ApiResponseClass("Passenger Details Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), result);

    }


    public ApiResponseClass GetAllMyDetails() {
        Map<String, Object> result = new HashMap<>();

        var clientEmail = utilsService.extractCurrentUser();

        var client = clientRepository.findByEmail(clientEmail.getEmail()).get();

        var clientDetails = clientDetailsRepository.findClientDetailsByClient(client);

        if (clientDetails == null)
            throw new NoSuchElementException("Client Details for Client " + client.getFirst_name() + " Not Found ");

        var passport = passportRepository.getPassportByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT)
                .orElseThrow(() -> new NoSuchElementException("Passport for Client " + client.getFirst_name() + " Not Found "));

        var visa = visaRepository.getVisaByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT)
                .orElseThrow(() -> new NoSuchElementException("Vesa for Client " + client.getFirst_name() + " Not Found "));

        var personalID = personalidentityRepository.getPersonalidentyByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT)
                .orElseThrow(() -> new NoSuchElementException("PersonalID for Client " + client.getFirst_name() + " Not Found "));

        result.put("firstname", client.getFirst_name());
        result.put("lastname", client.getLast_name());
        result.put("fathername", clientDetails.getFather_name());
        result.put("mothername", clientDetails.getMother_name());
        result.put("birthdate", clientDetails.getBirthdate());
        result.put("personalIdentity_PHOTO", personalID.getPersonalIdentity_PHOTO());
        result.put("passport_Issue_date", String.valueOf(passport.getPassport_issue_date()));
        result.put("passport_Expires_date", String.valueOf(passport.getPassport_expires_date()));
        result.put("passport_Number", passport.getPassport_number());
        result.put("passport_PHOTO", passport.getPassport_PHOTO());
        result.put("visa_Type", visa.getVisa_Type());
        result.put("visa_Country", visa.getVisa_Country());
        result.put("visa_Issue_date", String.valueOf(visa.getVisa_issue_date()));
        result.put("visa_Expires_date", String.valueOf(visa.getVisa_expires_date()));
        result.put("visa_PHOTO", visa.getVisa_PHOTO());

        return new ApiResponseClass("payment succeded", HttpStatus.ACCEPTED, LocalDateTime.now(), result);

    }


    public ApiResponseClass ClientChangePassword(ChangePasswordRequest request, Principal connectedUser) {


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

        Client updatedUser = clientRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Client with email " + userDetails.getUsername() + " not found"));

        // Update password securely (use setter or dedicated method)
        updatedUser.setPassword(passwordEncoder.encode(request.getNewPassword()));

        clientRepository.save(updatedUser);


        return new ApiResponseClass("Password Changed Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());
    }

    public ApiResponseClass PaypalPaymentSucceded(TransactionHistoryDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new NoSuchElementException("EMAIL NOT FOUND"));

      var trip = tripRepository.findById(request.getTripId());
      if(trip.isEmpty())
          throw new IllegalStateException("Trip Id Not Correct");
        var transaction = TransactionHistory.builder()
                .transactionAmmount(request.getTransactionAmmount())
                .type(request.getType())
                .date(request.getDate())
                .description("payment succeded")
                .status(request.getStatus())
                .wallet(client.getWallet())
                .tripName(trip.get().getName())
                .build();
        transactionRepository.save(transaction);
        EmailStructure emailStructure = EmailStructure.builder()
                .subject("Payment Throw PayPal")
                .message("Mr. " + client.getFirst_name() + ", Payment Throw PayPal Done Successfully ")
                .build();
        emailService.sendMail(client.getEmail(), emailStructure);
        return new ApiResponseClass("payment succeded", HttpStatus.ACCEPTED, LocalDateTime.now(), transaction);

    }


    @Transactional
    public ApiResponseClass WalletPayment(Integer ReservationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new NoSuchElementException("EMAIL NOT FOUND"));
        var tripReservation = tripReservationRepository.findById(ReservationId).orElseThrow(() -> new NoSuchElementException("Trip Reservation NOT FOUND "));

        var conf = confirmationPassengerDetailsRepository.findConfirmationPassengersDetailsByTripReservationId(ReservationId);
        if (!conf.getConfirmation_statue().name().equals(ConfirmationStatue.APPROVED.name()))
            throw new IllegalStateException("You Cannot Pay For This Trip Because Its Not Approved From The System Yet");

        if (tripReservation.getPaid().equals(true))
            throw new IllegalStateException("this Reservation already paid ");

        var fullPrice = tripService.totalPriceCalculator(0,
                tripReservation.getTrip().getPrice().getFlightPrice(),
                Optional.ofNullable(tripReservation.getTrip().getPrice().getHotelPrice()));

        fullPrice = fullPrice * tripReservation.getPassengerDetails().size();
        Optional<Wallet> wallet = walletRepository.getWalletByClientId(client.getId());

        if (wallet.isPresent()) {
            if (wallet.get().getBalance() >= fullPrice) {
                var transaction = TransactionHistory.builder()
                        .transactionAmmount(fullPrice)
                        .type(ServiceType.FullTrip)
                        .date(LocalDate.now())
                        .description("Payment Succeded")
                        .status(TransactionStatus.PAY)
                        .wallet(client.getWallet())
                        .tripName(tripReservation.getTrip().getName())
                        .build();
                client.getWallet().setBalance(client.getWallet().getBalance() - fullPrice);
                transactionRepository.save(transaction);
                EmailStructure emailStructure = EmailStructure.builder().
                        subject("Payment Done Successfully").
                        message("Mr." + client.getFirst_name() + ", Payment Throw Wallet Done Successfully,Your Ammount is " + client.getWallet().getBalance() + "$").
                        build();
                emailService.sendMail(client.getEmail(), emailStructure);
                tripReservation.setPaid(Boolean.TRUE);

                return new ApiResponseClass("payment succeded", HttpStatus.ACCEPTED, LocalDateTime.now(), transaction);
            } else
                throw new IllegalStateException("The full price of the Trip for the whole Reservation is " + fullPrice + " and your wallet have " + wallet.get().getBalance() + "$");
        } else throw new IllegalStateException("Please Create Wallet First ");

    }


    public ApiResponseClass GetAllTransactionHistory() {

        var clientEmail = utilsService.extractCurrentUser();
        var transactions = transactionRepository.getAllByClientId(clientEmail.getId());
        if (transactions.isEmpty())
            throw new NoSuchElementException("No Transaction Found");

        return new ApiResponseClass("Transactions History Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), transactions.get());

    }

    public ApiResponseClass GetOneTransactionHistory(Integer transactionId) {

        var transactions = transactionRepository.findById(transactionId);
        if (transactions.isEmpty())
            throw new NoSuchElementException("No Transaction Found");

        return new ApiResponseClass("One Transaction History Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), transactions);
    }

    public ApiResponseClass GetMyDetail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new NoSuchElementException("EMAIL NOT FOUND"));
        Optional<ClientDetails> clientDetails = Optional.ofNullable(clientDetailsRepository.findClientDetailsByClient(client));
        if (clientDetails.isEmpty())
            throw new NoSuchElementException("No Details Added Yet");

        return new ApiResponseClass("Client Details Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), clientDetails);

    }
}
