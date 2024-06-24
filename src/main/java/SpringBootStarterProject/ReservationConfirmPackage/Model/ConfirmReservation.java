package SpringBootStarterProject.ReservationConfirmPackage.Model;

import SpringBootStarterProject.ReservationConfirmPackage.Enum.ReservationType;
import SpringBootStarterProject.UserPackage.Models.Manager;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmReservation {

    @Id
    @SequenceGenerator(
            name = "confirm_reservation_id",
            sequenceName = "confirm_reservation_id",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "confirm_reservation_id",
            strategy = GenerationType.SEQUENCE
    )
    private Integer id;
    @Enumerated(EnumType.STRING)
    private ReservationType type;
    private Integer reservationId;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Manager manager;

}
