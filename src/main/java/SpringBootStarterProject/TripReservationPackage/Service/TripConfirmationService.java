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
import java.util.*;

@Service
@RequiredArgsConstructor
public class TripConfirmationService {

    private final UtilsService utilsService;
    private final ConfirmationPassengerDetailsRepository confirmationPassengersDetailsRepository;

    public ApiResponseClass GetAllMyConfirmation() {

        var client = utilsService.extractCurrentUser();
        List<Map<String, Object>> result = new ArrayList<>();

        List<ConfirmationPassengersDetails> confrimation = confirmationPassengersDetailsRepository.getAllByUserEmail(client.getEmail());

        for (ConfirmationPassengersDetails conf : confrimation) {
            if(conf.getTripReservation().getTrip().getName().isEmpty())
                throw new NoSuchElementException("Trip Name Not Found ");

            Map<String,Object> map =new HashMap<>();
            map.put("TripReservationId", conf.getTripReservation().getId());
            map.put("confirmationId", conf.getId());
            map.put("TripName", conf.getTripReservation().getTrip().getName());
            map.put("userEmail", conf.getUserEmail());
            map.put("confirmationStatus", conf.getConfirmation_statue());
            map.put("Description", conf.getDescription());
            result.add(map);
        }


        return new ApiResponseClass("All Trip Confirmations Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), result);

    }

    public ApiResponseClass GetOneMyConfirmation(Integer confirmationID) {
//todo :: add more info
        Optional<ConfirmationPassengersDetails> confrimation = confirmationPassengersDetailsRepository.findById(confirmationID);
        if (confrimation.isEmpty())
            throw new NoSuchElementException("No Confirmation found with ID " + confirmationID);

        var conf =confrimation.get();
        Map<String,Object> map =new HashMap<>();
        map.put("TripReservationId", conf.getTripReservation().getId());
        map.put("confirmationId", conf.getId());
        map.put("TripName", conf.getTripReservation().getTrip().getName());
        map.put("userEmail", conf.getUserEmail());
        map.put("confirmationStatus", conf.getConfirmation_statue());
        map.put("Description", conf.getDescription());


        return new ApiResponseClass(" Trip Confirmation Returned Successfully", HttpStatus.ACCEPTED, LocalDateTime.now(), map);
    }
}
