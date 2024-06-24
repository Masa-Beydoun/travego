package SpringBootStarterProject.HotelReservationPackage.Model;

import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.UserPackage.Models.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelReservation {
    @Id
    @SequenceGenerator(
            name = "hotel_reservation_id",
            sequenceName = "hotel_reservation_id",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "hotel_reservation_id",
            strategy = GenerationType.SEQUENCE
    )
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Client client;

    @ManyToOne
    private Hotel hotel;

    private LocalDate startDate;
    private LocalDate endDate;

    private Integer totalPrice;
    private String status;



}
