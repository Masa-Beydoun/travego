package SpringBootStarterProject.Trip_ReservationPackage.Models;

import SpringBootStarterProject.Trip_ReservationPackage.Models.Passenger_Details;
import SpringBootStarterProject.Trip_package.Models.Trip;
import SpringBootStarterProject.UserPackage.Models.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TripReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;


    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
   // @JsonManagedReference
    private List<Passenger_Details> passengerDetails;

    private LocalDate reserveDate;
}
