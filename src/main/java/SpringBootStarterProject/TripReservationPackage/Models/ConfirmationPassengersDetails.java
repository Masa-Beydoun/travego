package SpringBootStarterProject.TripReservationPackage.Models;

import SpringBootStarterProject.TripReservationPackage.Enum.ConfirmationStatue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ConfirmationPassengersDetails {

    @Id
    @GeneratedValue
    private Integer id;
    private String userEmail;

    @OneToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @JsonIgnore
    private TripReservation tripReservation;

    @Enumerated(EnumType.STRING)
    private ConfirmationStatue confirmation_statue;

    private String description;


}
