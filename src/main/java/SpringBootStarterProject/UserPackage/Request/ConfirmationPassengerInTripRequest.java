package SpringBootStarterProject.UserPackage.Request;

import SpringBootStarterProject.TripReservationPackage.Enum.ConfirmationStatue;
import SpringBootStarterProject.TripReservationPackage.Models.PassengerDetails;
import SpringBootStarterProject.TripReservationPackage.Models.TripReservation;
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
