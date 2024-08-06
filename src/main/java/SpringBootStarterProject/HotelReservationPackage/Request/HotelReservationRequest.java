package SpringBootStarterProject.HotelReservationPackage.Request;

import SpringBootStarterProject.HotelReservationPackage.Model.RoomReservation;
import SpringBootStarterProject.Trip_ReservationPackage.Request.PassengerDetailsRequest;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class HotelReservationRequest {
    private Integer hotelId;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<RoomReservationRequest> roomReservations;
    private List<PassengerDetailsRequest> PassengerRequest;
}
