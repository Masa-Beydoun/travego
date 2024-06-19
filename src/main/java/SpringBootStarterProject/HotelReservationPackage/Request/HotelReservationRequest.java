package SpringBootStarterProject.HotelReservationPackage.Request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class HotelReservationRequest {
    private Integer hotelId;
    private LocalDate startDate;
    private LocalDate endDate;
//    List<Passenger> passengers;
}
