package SpringBootStarterProject.UserPackage.Request;

import SpringBootStarterProject.TripReservationPackage.Enum.ConfirmationStatue;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class ConfirmationPassengerInTripRequest {

    private Integer ConfirmationId;
    private ConfirmationStatue confirmation_statue;
    private String description;

}
