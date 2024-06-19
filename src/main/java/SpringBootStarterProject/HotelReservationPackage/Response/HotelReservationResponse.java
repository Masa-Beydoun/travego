package SpringBootStarterProject.HotelReservationPackage.Response;

import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.UserPackage.Models.Client;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class HotelReservationResponse {

    private Integer id;

    private Client client;

    private Hotel hotel;

    private LocalDate startDate;
    private LocalDate endDate;

    private Integer totalPrice;

}
