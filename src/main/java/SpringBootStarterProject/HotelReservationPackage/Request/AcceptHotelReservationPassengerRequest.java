package SpringBootStarterProject.HotelReservationPackage.Request;

import SpringBootStarterProject.HotelReservationPackage.Enum.AcceptingStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class AcceptHotelReservationPassengerRequest {

    private Integer hotelReservationId;

    private Integer hotelReservationPassengerDetailsId;

    private String description;

    @Enumerated(EnumType.STRING)
    private AcceptingStatus status;
}
