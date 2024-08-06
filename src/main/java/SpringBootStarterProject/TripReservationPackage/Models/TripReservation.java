package SpringBootStarterProject.TripReservationPackage.Models;

import SpringBootStarterProject.Trippackage.Models.Trip;
import SpringBootStarterProject.UserPackage.Models.Client;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @OneToOne
    private Client client_id;

    @OneToOne
    private Trip trip_id;

    //  private Integer passengerCount;
    @Setter
    @OneToMany(mappedBy = "tripReservation", cascade = CascadeType.ALL, orphanRemoval = true)
    // @JsonIgnore
    private List<PassengerDetails> passenger_details;

    private LocalDate reserve_date;

}
