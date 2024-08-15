package SpringBootStarterProject.TripReservationPackage.Models;

import SpringBootStarterProject.TripReservationPackage.Models.PassengerDetails;
import SpringBootStarterProject.Trippackage.Models.Trip;
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
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_id",nullable = true)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;


    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference
    private List<PassengerDetails> passengerDetails;

    private LocalDate reserveDate;

    private Boolean Paid = Boolean.FALSE;
}
