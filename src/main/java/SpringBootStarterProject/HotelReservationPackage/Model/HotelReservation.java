package SpringBootStarterProject.HotelReservationPackage.Model;

import SpringBootStarterProject.HotelReservationPackage.Enum.AcceptingStatus;
import SpringBootStarterProject.HotelReservationPackage.Enum.HotelReservationStatus;
import SpringBootStarterProject.HotelReservationPackage.Response.RoomReservationResponse;
import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.UserPackage.Models.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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

    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    private HotelReservationStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonBackReference
    public List<RoomReservation> roomReservations;


    @Setter
    @OneToMany(mappedBy = "hotelReservation" ,cascade = CascadeType.ALL,orphanRemoval = true)
    // @JsonIgnore
    private List<HotelReservationPassengerDetails> passengerDetails;


}
