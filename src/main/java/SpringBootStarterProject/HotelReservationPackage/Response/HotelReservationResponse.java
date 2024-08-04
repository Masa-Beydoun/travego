package SpringBootStarterProject.HotelReservationPackage.Response;

import SpringBootStarterProject.HotelReservationPackage.Model.HotelReservationPassengerDetails;
import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.UserPackage.Models.Client;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class HotelReservationResponse {

    private Integer id;

    private Integer clientId;

    private String hotelName;
    private Integer hotelId;

    private LocalDate startDate;
    private LocalDate endDate;

    private Double totalPrice;
    private String status;

    private List<RoomReservationResponse> roomReservationResponses;

    private List<HotelReservationPassengerDetails> passengerDetails;
}
