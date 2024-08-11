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

    private Integer tripReservationId;
    private ConfirmationStatue confirmation_statue;
    private String description;

}
