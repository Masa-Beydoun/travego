package SpringBootStarterProject.TripReservationPackage.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import SpringBootStarterProject.TripReservationPackage.Models.TripReservation;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ConfirmationPassengersDetails {

    @Id
    @GeneratedValue
    private Integer id;
    private String User_email;

    @OneToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @JsonIgnore
    private TripReservation tripReservation;


    private String confirmation_statue;

    private String description;


}
