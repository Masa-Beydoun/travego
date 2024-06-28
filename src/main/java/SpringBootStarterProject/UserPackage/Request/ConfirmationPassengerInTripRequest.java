package SpringBootStarterProject.UserPackage.Request;

import SpringBootStarterProject.Trip_ReservationPackage.Enum.ConfirmationStatue;
import SpringBootStarterProject.Trip_ReservationPackage.Models.Passenger_Details;
import SpringBootStarterProject.Trip_ReservationPackage.Models.TripReservation;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ConfirmationPassengerInTripRequest {

    private Integer id;
    private Integer Trip_Id;
    private String User_email;
    private TripReservation tripReservation;
    private Passenger_Details passenger_details_id;
    private ConfirmationStatue confirmation_statue;
    private String description;

}
