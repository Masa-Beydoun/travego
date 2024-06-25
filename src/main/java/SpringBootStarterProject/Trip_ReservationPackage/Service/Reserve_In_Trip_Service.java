package SpringBootStarterProject.Trip_ReservationPackage.Service;

import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.Trip_ReservationPackage.Models.ConfirmationPassengersDetails;
import SpringBootStarterProject.Trip_ReservationPackage.Models.Passenger_Details;
import SpringBootStarterProject.Trip_ReservationPackage.Models.TripReservation;
import SpringBootStarterProject.Trip_ReservationPackage.Repository.ConfirmationPassengerDetailsRepository;
import SpringBootStarterProject.Trip_ReservationPackage.Repository.Passenger_Details_Repository;
import SpringBootStarterProject.Trip_ReservationPackage.Repository.TripReservationRepository;
import SpringBootStarterProject.Trip_ReservationPackage.Request.PassengerDetailsRequest;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class Reserve_In_Trip_Service {

    private  final ObjectsValidator<PassengerDetailsRequest> passengerDetailsValidator;

    private  final ClientRepository clientRepository;

    private final Passenger_Details_Repository passenger_Details_Repository;

    private  final TripReservationRepository tripReservationRepository;

    private  final ConfirmationPassengerDetailsRepository confirmationPassengerDetailsRepository;

    @org.springframework.transaction.annotation.Transactional
    public ApiResponseClass ReserveInTrip(List<PassengerDetailsRequest> PassengerRequest)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return new ApiResponseClass("Authentication error", HttpStatus.UNAUTHORIZED, LocalDateTime.now(), null);
        }

        var client = clientRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Client not found with email: " + authentication.getName()));

        for (PassengerDetailsRequest passengerRequest : PassengerRequest) {
            passengerDetailsValidator.validate(passengerRequest);
        }


        var reserveTrip = TripReservation.builder()
                .client_id(client.getId())
                .trip_id(PassengerRequest.get(0).getTrip_Id())
                .reserve_date(LocalDate.now())
                .build();

        tripReservationRepository.save(reserveTrip);
        List<Passenger_Details> PassengerArray = new ArrayList<>();
        for (PassengerDetailsRequest passengerRequest : PassengerRequest) {
            if (!passenger_Details_Repository.existsByFisrtnameAndLastnameAndFathernameAndMothernameAndBitrhdate
                    (passengerRequest.getFisrtname(), passengerRequest.getLastname(), passengerRequest.getFathername(), passengerRequest.getMothername(), passengerRequest.getBitrhdate())) {
                var passenger = Passenger_Details.builder()
                        .user_id(client.getId())
                        .tripReservation(reserveTrip)
                        .fisrtname(passengerRequest.getFisrtname())
                        .lastname(passengerRequest.getLastname())
                        .fathername(passengerRequest.getFathername())
                        .mothername(passengerRequest.getMothername())
                        .bitrhdate(passengerRequest.getBitrhdate())
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
                        .passenger_details_id(passenger)
                        .User_email(client.getEmail())
                        .confirmation_statue("PENDING TEST")
                        .description("PENDING DESC")
                        .tripReservation(reserveTrip)
                        .build();
                confirmationPassengerDetailsRepository.save(confPassengerDetails);
            }
            else
               throw new IllegalStateException("Passenger With Name " + passengerRequest.getFisrtname()+" "+passengerRequest.getFathername()+" "+passengerRequest.getLastname()+ " Already Reserved In The Trip With id " + passengerRequest.getTrip_Id());
        }
        reserveTrip.setPassenger_details(PassengerArray);

        return new ApiResponseClass("Reservation in trip done Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), reserveTrip);
    }
}

