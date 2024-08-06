package SpringBootStarterProject.UserPackage.Request;

import SpringBootStarterProject.TripReservationPackage.Enum.ConfirmationStatue;
import SpringBootStarterProject.TripReservationPackage.Models.PassengerDetails;
import SpringBootStarterProject.TripReservationPackage.Models.TripReservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ConfirmationPassengerInTripRequest {

    private Integer id;
    private Integer Trip_Id;
    private String User_email;
    private TripReservation tripReservation;
    private PassengerDetails passenger_details_id;
    private ConfirmationStatue confirmation_statue;
    private String description;

}
