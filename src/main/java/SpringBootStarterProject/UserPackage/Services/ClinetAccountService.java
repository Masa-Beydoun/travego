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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClinetAccountService {
    private final ObjectsValidator<AddPassengerRequest> addPassengerRequestValidator;
    private final ObjectsValidator<Client> EditClientValidator;
    private final ObjectsValidator<ClientRegisterRequest> ClientRegisterValidator;

    private final ObjectsValidator<LoginRequest> LoginRequestValidator;

    private final ObjectsValidator<ManagerRegisterRequest> ManagerRequestValidator;

    private final ObjectsValidator<PassportRequest> PassportRequestValidator;

    private final ObjectsValidator<PersonalidRequest> PersonalIdRequestValidator;

    private final ObjectsValidator<VisaRequest> VisaRequestValidator;


    //TODO:: TRY   private final ObjectsValidator<Object>validator;
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
    private final ManagerRepository managerRepository;
    private final JwtService jwtService;
    private final RateLimiterConfig rateLimiterConfig;
    private final RateLimiterRegistry rateLimiterRegistry;
    private static final String LOGIN_RATE_LIMITER = "loginRateLimiter";

    public ApiResponseClass GetMyAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());


        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return new ApiResponseClass("PROFILE RETURED SUCCESSFULLY", HttpStatus.ACCEPTED, LocalDateTime.now(), client);
    }

    public ApiResponseClass EditMyAccount(EditClientRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        if (request.getFirst_name() != null)
            client.setFirst_name(request.getFirst_name());

        if (request.getLast_name() != null)
            client.setLast_name(request.getLast_name());
        if (request.getUsername() != null)
            client.setUsername(request.getUsername());
        if (request.getPhone_number() != null)
            client.setPhone_number(request.getPhone_number());
        EditClientValidator.validate(client);
        clientRepository.save(client);
        return new ApiResponseClass("Profile Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), client);
    }


    public ApiResponseClass AddMyDetails(ClientDetailsRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        var foundclientDetails = clientDetailsRepository.findClientDetailsByClient(client);
        if (foundclientDetails == null) {
            ClientDetails clientDetails = ClientDetails.builder()
                    .client(client)
                    .gender(request.getGender())
                    .birthdate(request.getBirthDate())
                    .father_name(request.getFather_name())
                    .mother_name(request.getMother_name())
                    .build();
            clientDetailsRepository.save(clientDetails);
            return new ApiResponseClass("Details Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), client);

        } else {


            if (request.getGender() != null)
                foundclientDetails.setGender(request.getGender());

            if (request.getBirthDate() != null)
                foundclientDetails.setBirthdate(request.getBirthDate());

            if (request.getFather_name() != null)
                foundclientDetails.setFather_name(request.getFather_name());

            if (request.getMother_name() != null)
                foundclientDetails.setMother_name(request.getMother_name());
            clientDetailsRepository.save(foundclientDetails);

            return new ApiResponseClass("Details Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), client);

        }

    }

    public ApiResponseClass AddMyPassport(PassportRequest request) {

        PassportRequestValidator.validate(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
            Optional<Passport> foundPassport = passportRepository.getPassportByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT);

            if (foundPassport.isEmpty()) {
                Passport passport = Passport.builder()
                        .relationshipId(client.getId())
                        .type(RelationshipType.CLIENT)
                        .firstName(request.getPassportfirstName())
                        .lastName(request.getPassportlastName())
                        .issueDate(request.getPassportIssueDate())
                        .expiryDate(request.getPassportExpiryDate())
                        .passportNumber(request.getPassportNumber())
                        .build();
                passportRepository.save(passport);
                return new ApiResponseClass(" CLIENT Passport Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), passport);
            } else {
                var passport = foundPassport.get();
                if (request.getPassportfirstName() != null)
                    passport.setFirstName(request.getPassportfirstName());

                if (request.getPassportlastName() != null)
                    passport.setLastName(request.getPassportlastName());

                if (request.getPassportIssueDate() != null)
                    passport.setIssueDate(request.getPassportIssueDate());

                if (request.getPassportExpiryDate() != null)
                    passport.setExpiryDate(request.getPassportExpiryDate());

                if (request.getPassportNumber() != null)
                    passport.setPassportNumber(request.getPassportNumber());
                passportRepository.save(passport);
                return new ApiResponseClass("CLIENT Passport Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPassport);

            }
        }

   // @Transactional
    public ApiResponseClass AddMyPassengersPassport(PassportRequest request) {
        Optional<Passport> foundPassport = passportRepository.getPassportByRelationshipIdAndType(request.getPassengerId(), RelationshipType.PASSENGER);

        if (foundPassport.isEmpty()) {
            Passport passport = Passport.builder()
                    .relationshipId(request.getPassengerId())
                    .type(RelationshipType.PASSENGER)
                    .firstName(request.getPassportfirstName())
                    .lastName(request.getPassportlastName())
                    .issueDate(request.getPassportIssueDate())
                    .expiryDate(request.getPassportExpiryDate())
                    .passportNumber(request.getPassportNumber())
                    .build();
            passportRepository.save(passport);
            return new ApiResponseClass("Passenger Passport Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), passport);
        } else {
            var passport = foundPassport.get();
            if (request.getPassportfirstName() != null)
                passport.setFirstName(request.getPassportfirstName());

            if (request.getPassportlastName() != null)
                passport.setLastName(request.getPassportlastName());

            if (request.getPassportIssueDate() != null)
                passport.setIssueDate(request.getPassportIssueDate());

            if (request.getPassportExpiryDate() != null)
                passport.setExpiryDate(request.getPassportExpiryDate());

            if (request.getPassportNumber() != null)
                passport.setPassportNumber(request.getPassportNumber());
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

            Personalidenty personalidenty = Personalidenty.builder()
                    .relationshipId(client.getId())
                    .type(RelationshipType.CLIENT)
                    .firstName(request.getIdfirstName())
                    .lastName(request.getIdlastName())
                    .birthDate(request.getIdBirthDate())
                    .nationality(request.getNationality())
                    .build();
            personalidentityRepository.save(personalidenty);
            return new ApiResponseClass("CLIENT Personal ID Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), personalidenty);
        } else {
            var personalId = foundPersonalId.get();
            if (request.getIdfirstName() != null)
                personalId.setFirstName(request.getIdfirstName());

            if (request.getIdlastName() != null)
                personalId.setLastName(request.getIdlastName());

            if (request.getNationality() != null)
                personalId.setNationality(request.getNationality());

            if (request.getIdBirthDate() != null)
                personalId.setBirthDate(request.getIdBirthDate());
            personalidentityRepository.save(personalId);
            return new ApiResponseClass("CLIENT Personal ID Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPersonalId);


        }
    }

    public ApiResponseClass AddPassengerPersonalid(PersonalidRequest request) {

        Optional<Personalidenty> foundPersonalId = personalidentityRepository.getPersonalidentyByRelationshipIdAndType(request.getPassengerId(), RelationshipType.PASSENGER);
        if (foundPersonalId.isEmpty()) {

            Personalidenty personalidenty = Personalidenty.builder()
                    .relationshipId(request.getPassengerId())
                    .type(RelationshipType.PASSENGER)
                    .firstName(request.getIdfirstName())
                    .lastName(request.getIdlastName())
                    .birthDate(request.getIdBirthDate())
                    .nationality(request.getNationality())
                    .build();
            personalidentityRepository.save(personalidenty);
            return new ApiResponseClass("Passenger Personal ID Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), personalidenty);
        } else {
            var personalId = foundPersonalId.get();
            if (request.getIdfirstName() != null)
                personalId.setFirstName(request.getIdfirstName());

            if (request.getIdlastName() != null)
                personalId.setLastName(request.getIdlastName());

            if (request.getNationality() != null)
                personalId.setNationality(request.getNationality());

            if (request.getIdBirthDate() != null)
                personalId.setBirthDate(request.getIdBirthDate());

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


            Visa visa = Visa.builder()
                    .relationshipId(client.getId())
                    .type(RelationshipType.CLIENT)
                    .visaType(request.getVisaType())
                    .issueDate(request.getVisaIssueDate())
                    .expiryDate(request.getVisaExpiryDate())
                    .country(request.getCountry())
                    .build();

            visaRepository.save(visa);
            return new ApiResponseClass("CLIENT Visa Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), visa);
        } else {
            var visa = foundvisa.get();
            if (request.getVisaType() != null)
                visa.setVisaType(request.getVisaType());

            if (request.getVisaIssueDate() != null)
                visa.setIssueDate(request.getVisaIssueDate());

            if (request.getVisaExpiryDate() != null)
                visa.setExpiryDate(request.getVisaExpiryDate());

            if (request.getCountry() != null)
                visa.setCountry(request.getCountry());
            visaRepository.save(visa);
            return new ApiResponseClass("CLIENT Visa Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundvisa);

        }

    }

    public ApiResponseClass AddPassengerVisa(VisaRequest request) {

        Optional<Visa> foundvisa = visaRepository.getVisaByRelationshipIdAndType(request.getPassengerId(), RelationshipType.PASSENGER);
        if (foundvisa.isEmpty()) {


            Visa visa = Visa.builder()
                    .relationshipId(request.getPassengerId())
                    .type(RelationshipType.PASSENGER)
                    .visaType(request.getVisaType())
                    .issueDate(request.getVisaIssueDate())
                    .expiryDate(request.getVisaExpiryDate())
                    .country(request.getCountry())
                    .build();

            visaRepository.save(visa);
            return new ApiResponseClass("PASSENGER Visa Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), visa);
        } else {
            var visa = foundvisa.get();
            if (request.getVisaType() != null)
                visa.setVisaType(request.getVisaType());

            if (request.getVisaIssueDate() != null)
                visa.setIssueDate(request.getVisaIssueDate());

            if (request.getVisaExpiryDate() != null)
                visa.setExpiryDate(request.getVisaExpiryDate());

            if (request.getCountry() != null)
                visa.setCountry(request.getCountry());
            visaRepository.save(visa);
            return new ApiResponseClass("PASSENGER Visa Updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundvisa);

        }
    }

    public ApiResponseClass GetMyPassport() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Optional<Passport> foundPassport = Optional.ofNullable(passportRepository.getPassportByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT)
                .orElseThrow(() -> new NoSuchElementException("No Passport ADDED yet")));
        return new ApiResponseClass("Passport Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPassport.get());

    }

    public ApiResponseClass GetPassengerPassport(Integer id) {
        Optional<Passport> foundPassport = Optional.ofNullable(passportRepository.getPassportByRelationshipIdAndType(id, RelationshipType.PASSENGER)
                .orElseThrow(() -> new NoSuchElementException("No Passport ADDED yet")));
        return new ApiResponseClass("PASSENGER Passport Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPassport.get());
    }

    public ApiResponseClass GetMyPersonalId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Optional<Personalidenty> foundPersonalId = Optional.ofNullable(personalidentityRepository.getPersonalidentyByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT)
                .orElseThrow(() -> new NoSuchElementException("Personal ID Not ADDED yet")));
        return new ApiResponseClass("Personal ID Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPersonalId.get());

    }

    public ApiResponseClass GetPassengerPersonalId(Integer passengerId) {
        Optional<Personalidenty> foundPersonalId = Optional.ofNullable(personalidentityRepository.getPersonalidentyByRelationshipIdAndType(passengerId, RelationshipType.PASSENGER)
                .orElseThrow(() -> new NoSuchElementException("PASSENGER Personal ID Not ADDED yet")));
        return new ApiResponseClass("PASSENGER Personal ID Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundPersonalId.get());
    }

    public ApiResponseClass GetMyVisa() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Optional<Visa> foundVisa = Optional.ofNullable(visaRepository.getVisaByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT)
                .orElseThrow(() -> new NoSuchElementException("Visa  Not ADDED yet")));
        return new ApiResponseClass("Visa Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundVisa.get());

    }

    public ApiResponseClass GetPassengerVisa(Integer passengerId) {
        Optional<Visa> foundVisa = Optional.ofNullable(visaRepository.getVisaByRelationshipIdAndType(passengerId, RelationshipType.PASSENGER)
                .orElseThrow(() -> new NoSuchElementException("PASSENGER Visa  Not ADDED yet")));
        return new ApiResponseClass("PASSENGER Visa Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), foundVisa.get());
    }

    public ApiResponseClass DeleteMyPassport() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Optional<Passport> foundPassport = Optional.ofNullable(passportRepository.getPassportByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT)
                .orElseThrow(() -> new NoSuchElementException("No Passport ADDED yet")));
        passportRepository.delete(foundPassport.get());
        return new ApiResponseClass("Passport Deleted Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());

    }

    public ApiResponseClass DeletePassenegrPassport(Integer id) {
        Optional<Passport> foundPassport = Optional.ofNullable(passportRepository.getPassportByRelationshipIdAndType(id, RelationshipType.PASSENGER)
                .orElseThrow(() -> new NoSuchElementException("PASSENGER No Passport ADDED yet")));
        passportRepository.delete(foundPassport.get());
        return new ApiResponseClass("PASSENGER Passport Deleted Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());
    }

    public ApiResponseClass DeleteMyPersonalId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Optional<Personalidenty> foundPersonalId = Optional.ofNullable(personalidentityRepository.getPersonalidentyByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT)
                .orElseThrow(() -> new NoSuchElementException("Personal ID Not ADDED yet")));
        personalidentityRepository.delete(foundPersonalId.get());
        return new ApiResponseClass("Personal ID Deleted Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());
    }

    public ApiResponseClass DeletePassenegrPersonalId(Integer id) {
        Optional<Personalidenty> foundPersonalId = Optional.ofNullable(personalidentityRepository.getPersonalidentyByRelationshipIdAndType(id, RelationshipType.PASSENGER)
                .orElseThrow(() -> new NoSuchElementException("PASSENGER Personal ID Not ADDED yet")));
        personalidentityRepository.delete(foundPersonalId.get());
        return new ApiResponseClass("PASSENGER Personal ID Deleted Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());
    }
    public ApiResponseClass DeleteMyVisa() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        Optional<Visa> foundVisa = Optional.ofNullable(visaRepository.getVisaByRelationshipIdAndType(client.getId(), RelationshipType.CLIENT)
                .orElseThrow(() -> new NoSuchElementException("Visa  Not ADDED yet")));
        visaRepository.delete(foundVisa.get());
        return new ApiResponseClass("Visa Deleted Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());
    }

    public ApiResponseClass DeletePassenegrVisa(Integer id) {
        Optional<Visa> foundVisa = Optional.ofNullable(visaRepository.getVisaByRelationshipIdAndType(id, RelationshipType.PASSENGER)
                .orElseThrow(() -> new NoSuchElementException("PASSENGER Visa  Not ADDED yet")));
        visaRepository.delete(foundVisa.get());
        return new ApiResponseClass("PASSENGER Visa Deleted Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());
    }

    public ApiResponseClass AddMyPassengers(AddPassengerRequest request) {

        addPassengerRequestValidator.validate(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        var foundPassenger = passengerRepository.findPassengerByUniqueName(request.getFirst_name() + request.getFather_name() + request.getLast_name() + request.getBirthdate());
        if (foundPassenger.isEmpty()) {
            Passenger passenger = Passenger.builder()
                    .first_name(request.getFirst_name())
                    .last_name(request.getLast_name())
                    .client(client)
                    .gender(request.getGender())
                    .birthdate(request.getBirthdate())
                    .father_name(request.getFather_name())
                    .mother_name(request.getMother_name())
                    .uniqueName(request.getFirst_name() + request.getFather_name() + request.getLast_name() + request.getBirthdate())
                    .build();
            passengerRepository.save(passenger);
            return new ApiResponseClass("Passenger Added Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), passenger);

        }
        return new ApiResponseClass("Passenger Already Exist", HttpStatus.ACCEPTED, LocalDateTime.now());


    }

    public ApiResponseClass EditMyPassenger(AddPassengerRequest request) {
        addPassengerRequestValidator.validate(request);
        Optional<Passenger> foundPassenger = Optional.ofNullable(passengerRepository.findById(request.getId()).orElseThrow(() -> new NoSuchElementException("passenger Not found")));

        var passenger = foundPassenger.get();
        if (request.getFirst_name() != null)
            passenger.setFirst_name(request.getFirst_name());

        if (request.getLast_name() != null)
            passenger.setLast_name(request.getLast_name());

        if (request.getFather_name() != null)
            passenger.setFather_name(request.getFather_name());

        if (request.getMother_name() != null)
            passenger.setMother_name(request.getMother_name());

        if (request.getBirthdate() != null)
            passenger.setBirthdate(request.getBirthdate());

        passenger.setUniqueName(request.getFirst_name() + request.getFather_name() + request.getLast_name() + request.getBirthdate());
        passengerRepository.save(passenger);

        return new ApiResponseClass("Passenger updated Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), passenger);
    }
    public ApiResponseClass DeleteMyPassenger(Integer id) {

        Optional<Passenger> foundPassenger = Optional.ofNullable( passengerRepository.findById(id).orElseThrow(()->new NoSuchElementException("No passengers added yet")));

        passengerRepository.delete(foundPassenger.get());
        return new ApiResponseClass("Passenger Deleted Successfully",HttpStatus.ACCEPTED,LocalDateTime.now());

    }

    public ApiResponseClass GetOnePassenger(Integer id) {

       var foundPassenger = passengerRepository.findById(id).orElseThrow(()->new NoSuchElementException("passenger Not found"));
        return new ApiResponseClass("Passenger Returned Successfully",HttpStatus.ACCEPTED,LocalDateTime.now(),foundPassenger);

    }

    public ApiResponseClass GetMyAllPassengers() {

        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        var client = clientRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
       List<Passenger> allPassengers = passengerRepository.findPassengerByClientId(client.getId()) ;
       if(allPassengers.isEmpty())
          throw  new NoSuchElementException("No passengers added yet ");

        return new ApiResponseClass("Passengers Returned Successfully",HttpStatus.ACCEPTED,LocalDateTime.now(),allPassengers);

    }



}
