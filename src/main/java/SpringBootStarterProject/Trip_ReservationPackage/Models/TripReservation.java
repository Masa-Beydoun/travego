package SpringBootStarterProject.Trip_ReservationPackage.Models;

import SpringBootStarterProject.Trip_ReservationPackage.Models.Passenger_Details;
import SpringBootStarterProject.Trip_package.Models.Trip;
import SpringBootStarterProject.UserPackage.Models.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

   // @OneToOne
    private Integer client_id;

   // @OneToOne
    private Integer trip_id;

    //  private Integer passengerCount;
    @Setter
    @OneToMany(mappedBy = "tripReservation" ,cascade = CascadeType.ALL,orphanRemoval = true)
   // @JsonIgnore
    private List<Passenger_Details> passenger_details;

    private LocalDate reserve_date;

}
