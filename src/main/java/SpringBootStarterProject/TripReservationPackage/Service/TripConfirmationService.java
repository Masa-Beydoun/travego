package SpringBootStarterProject.TripReservationPackage.Service;

import SpringBootStarterProject.HotelReservationPackage.Repository.HotelConfirmationPassengersDetailsRepository;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import SpringBootStarterProject.ManagingPackage.Utils.UtilsService;
import SpringBootStarterProject.TripReservationPackage.Models.ConfirmationPassengersDetails;
import SpringBootStarterProject.TripReservationPackage.Repository.ConfirmationPassengerDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripConfirmationService {

    private  final UtilsService utilsService;
    private  final ConfirmationPassengerDetailsRepository confirmationPassengersDetailsRepository;

    public ApiResponseClass GetAllMyConfirmation() {

        var client = utilsService.extractCurrentUser();

        List<ConfirmationPassengersDetails> confrimation = confirmationPassengersDetailsRepository.getConfirmationPassengersDetailsByUserEmail(client.getEmail());

        return new ApiResponseClass("All Trip Confirmations Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), confrimation);

    }

    public ApiResponseClass GetOneMyConfirmation(Integer confirmationID) {

         ConfirmationPassengersDetails confrimation = confirmationPassengersDetailsRepository.getReferenceById(confirmationID);

        return new ApiResponseClass(" Trip Confirmation Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), confrimation);
    }
}
