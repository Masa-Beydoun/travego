package SpringBootStarterProject.Trip_ReservationPackage.Service;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.Trip_ReservationPackage.Enum.ConfirmationStatue;
import SpringBootStarterProject.Trip_ReservationPackage.Models.ConfirmationPassengersDetails;
import SpringBootStarterProject.Trip_ReservationPackage.Models.Passenger_Details;
import SpringBootStarterProject.Trip_ReservationPackage.Models.TripReservation;
import SpringBootStarterProject.Trip_ReservationPackage.Repository.ConfirmationPassengerDetailsRepository;
import SpringBootStarterProject.Trip_ReservationPackage.Repository.Passenger_Details_Repository;
import SpringBootStarterProject.Trip_ReservationPackage.Repository.TripReservationRepository;
import SpringBootStarterProject.Trip_ReservationPackage.Request.PassengerDetailsRequest;
import SpringBootStarterProject.Trip_package.Models.Trip;
import SpringBootStarterProject.Trip_package.Repository.TripRepository;
import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor

public class Reserve_In_Trip_Service {

    private final ObjectsValidator<PassengerDetailsRequest> passengerDetailsValidator;

    private final ClientRepository clientRepository;

    private final Passenger_Details_Repository passenger_Details_Repository;

    private final TripReservationRepository tripReservationRepository;

    private final ConfirmationPassengerDetailsRepository confirmationPassengerDetailsRepository;

    private final TripRepository tripRepository;

    @org.springframework.transaction.annotation.Transactional
    public ApiResponseClass ReserveInTrip(Integer trip_Id, List<PassengerDetailsRequest> PassengerRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return new ApiResponseClass("Authentication error", HttpStatus.UNAUTHORIZED, LocalDateTime.now(), null);
        }

        var client = clientRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Client not found with email: " + authentication.getName()));


        Trip trip= tripRepository.findById(trip_Id).orElseThrow(()-> new RuntimeException("Trip with Id "+ trip_Id +" not found "));
        for (PassengerDetailsRequest passengerRequest : PassengerRequest) {
            passengerDetailsValidator.validate(passengerRequest);
        }


        Map<String, Object> map = new HashMap<>();
        var reserveTrip = TripReservation.builder()
                .client(client)
                .trip(trip)
                .reserveDate(LocalDate.now())
                .build();



        tripReservationRepository.save(reserveTrip);
        List<Passenger_Details> information = new ArrayList<>();
        for (PassengerDetailsRequest passengerRequest : PassengerRequest) {
            if (!passenger_Details_Repository.existsByDetailsAndTripId(
                    passengerRequest.getFirstname(), passengerRequest.getLastname(), passengerRequest.getFathername(), passengerRequest.getMothername(), passengerRequest.getBirthdate(),trip_Id)) {
                var passenger = Passenger_Details.builder()
                        .clientId(client.getId())
                        .tripReservation(reserveTrip)
                        .firstname(passengerRequest.getFirstname())
                        .lastname(passengerRequest.getLastname())
                        .fathername(passengerRequest.getFathername())
                        .mothername(passengerRequest.getMothername())
                        .birthdate(passengerRequest.getBirthdate())
                        .nationality(passengerRequest.getNationality())
                        .personalIdentity_PHOTO(passengerRequest.getPersonalIdentity_PHOTO())
                        .passport_issue_date(passengerRequest.getPassport_Issue_date())
                        .passport_expires_date(passengerRequest.getPassport_Expires_date())
                        .passport_number(passengerRequest.getPassport_Number())
                        .passport_PHOTO(passengerRequest.getPassport_PHOTO())
                        .visa_Type(passengerRequest.getVisa_Type())
                        .visa_Country(passengerRequest.getVisa_Country())
                        .visa_issue_date(passengerRequest.getVisa_Issue_date())
                        .visa_expires_date(passengerRequest.getVisa_Expires_date())
                        .visa_PHOTO(passengerRequest.getVisa_PHOTO())
                        .build();

                passenger_Details_Repository.save(passenger);
                information.add(passenger);

                map.put("information", information);
                var confPassengerDetails = ConfirmationPassengersDetails.builder()
                        .User_email(client.getEmail())
                        .confirmation_statue(ConfirmationStatue.PENDING.name())
                        .description("PENDING DESC")
                        .tripReservation(reserveTrip)
                        .build();
                passenger.setConfirmationPassengersDetails(confPassengerDetails);

                confirmationPassengerDetailsRepository.save(confPassengerDetails);
            } else
                throw new IllegalStateException("Passenger With Name " + passengerRequest.getFirstname() + " " + passengerRequest.getFathername() + " " + passengerRequest.getLastname() + " Already Reserved In The Trip With id " + trip_Id);
        }
        reserveTrip.setPassengerDetails(information);

        return new ApiResponseClass("Reservation in trip done Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), map);
    }

    public ApiResponseClass Add_Passengers_To_Existing_Reservation(Integer trip_Id, List<PassengerDetailsRequest> PassengerRequest) {
        try {


            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication.getName() == null) {
                return new ApiResponseClass("Authentication error", HttpStatus.UNAUTHORIZED, LocalDateTime.now(), null);
            }

            var client = clientRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Client not found with email: " + authentication.getName()));

            for (PassengerDetailsRequest passengerRequest : PassengerRequest) {
                passengerDetailsValidator.validate(passengerRequest);
            }


            TripReservation reserveTrip = tripReservationRepository.findById(PassengerRequest.get(0).getTripReservation()).get();
            List<Passenger_Details> PassengerArray = new ArrayList<>();
            for (PassengerDetailsRequest passengerRequest : PassengerRequest) {
                if (!passenger_Details_Repository.existsByDetailsAndTripId
                        (passengerRequest.getFirstname(), passengerRequest.getLastname(), passengerRequest.getFathername(), passengerRequest.getMothername(), passengerRequest.getBirthdate(),trip_Id)) {
                    var passenger = Passenger_Details.builder()
                            .clientId(client.getId())
                            .tripReservation(reserveTrip)
                            .firstname(passengerRequest.getFirstname())
                            .lastname(passengerRequest.getLastname())
                            .fathername(passengerRequest.getFathername())
                            .mothername(passengerRequest.getMothername())
                            .birthdate(passengerRequest.getBirthdate())
                            .nationality(passengerRequest.getNationality())
                            .personalIdentity_PHOTO(passengerRequest.getPersonalIdentity_PHOTO())
                            .passport_issue_date(passengerRequest.getPassport_Issue_date())
                            .passport_expires_date(passengerRequest.getPassport_Expires_date())
                            .passport_number(passengerRequest.getPassport_Number())
                            .passport_PHOTO(passengerRequest.getPassport_PHOTO())
                            .visa_Type(passengerRequest.getVisa_Type())
                            .visa_Country(passengerRequest.getVisa_Country())
                            .visa_issue_date(passengerRequest.getVisa_Issue_date())
                            .visa_expires_date(passengerRequest.getVisa_Expires_date())
                            .visa_PHOTO(passengerRequest.getVisa_PHOTO())
                            .build();

                    passenger_Details_Repository.save(passenger);
                    PassengerArray.add(passenger);

                    var confPassengerDetails = ConfirmationPassengersDetails.builder()
                            .User_email(client.getEmail())
                            .confirmation_statue(ConfirmationStatue.PENDING.name())
                            .description("PENDING DESC")
                            .tripReservation(reserveTrip)
                            .build();
                    passenger.setConfirmationPassengersDetails(confPassengerDetails);

                    confirmationPassengerDetailsRepository.save(confPassengerDetails);
                } else
                    throw new IllegalStateException("Passenger With Name " + passengerRequest.getFirstname() + " " + passengerRequest.getFathername() + " " + passengerRequest.getLastname() + " Already Reserved In The Trip With id " + trip_Id + " Please Remove This Reservation And try Again");
            }
            reserveTrip.setPassengerDetails(PassengerArray);

            return new ApiResponseClass("Reservation in trip done Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), reserveTrip);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
    public ApiResponseClass Updated_Reserved_Passengers(Integer reservation_Id, Integer passenger_Id, PassengerDetailsRequest request) {
     try {

         Optional<Passenger_Details> optionalPerson = passenger_Details_Repository.findByTripReservation_IdAndId(reservation_Id, passenger_Id);
         if (optionalPerson.isPresent()) {
             Passenger_Details existingPerson = optionalPerson.get();

             existingPerson.setFirstname(request.getFirstname());
             existingPerson.setLastname(request.getLastname());
             existingPerson.setFathername(request.getFathername());
             existingPerson.setMothername(request.getMothername());
             existingPerson.setBirthdate(request.getBirthdate());
             existingPerson.setNationality(request.getNationality());
             existingPerson.setPersonalIdentity_PHOTO(request.getPersonalIdentity_PHOTO());
             existingPerson.setPassport_issue_date(request.getPassport_Issue_date());
             existingPerson.setPassport_expires_date(request.getPassport_Expires_date());
             existingPerson.setPassport_number(request.getPassport_Number());
             existingPerson.setPassport_PHOTO(request.getPassport_PHOTO());
             existingPerson.setVisa_Type(request.getVisa_Type());
             existingPerson.setVisa_Country(request.getVisa_Country());
             existingPerson.setVisa_issue_date(request.getVisa_Issue_date());
             existingPerson.setVisa_expires_date(request.getVisa_Expires_date());
             existingPerson.setVisa_PHOTO(request.getVisa_PHOTO());

             passenger_Details_Repository.save(existingPerson);
             return new ApiResponseClass("Passneger with name " + request.getFirstname() + " " + request.getLastname() + " , in the ID Number " + passenger_Id + " Updated Successfully",
                     HttpStatus.ACCEPTED, LocalDateTime.now(), existingPerson);

         } else {
             throw new RuntimeException("Passenger not found with tripReservation: " + request.getTripReservation());
         }
     }
     catch (Exception e)
     {
         throw new IllegalStateException(e.getMessage());
     }
    }

    public ApiResponseClass GetMyPassenger(Integer Trip_id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<Client> client = clientRepository.findByEmail(authentication.getName());
        Pageable pageable = PageRequest.of(0, 20);
        Page<Passenger_Details> AllPassengersDetails = passenger_Details_Repository.findPassengersAddedByMeToThisTrip(Trip_id, client.get().getId(), pageable);
        if (!AllPassengersDetails.isEmpty())
            return new ApiResponseClass("All Passenger Details Which Added by User " + client.get().getFirst_name() + " Returned Successfully ",
                    HttpStatus.ACCEPTED, LocalDateTime.now(), AllPassengersDetails.getContent());

        throw new NoSuchElementException(" You Didnt Add Any Passenger Yet ");
    }

    public ApiResponseClass DeletePassengerReservation(Integer passengerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ;
        Optional<Client> client = clientRepository.findByEmail(authentication.getName());

        Passenger_Details Passenger = passenger_Details_Repository.findById(passengerId).get();
        if (Passenger != null) {
            passenger_Details_Repository.deleteById(Passenger.getId());
            return new ApiResponseClass(" Passenger Removed from TripReservation ",
                    HttpStatus.ACCEPTED, LocalDateTime.now());
        }
        throw new NoSuchElementException(" No Passenger Found ");
    }


}

