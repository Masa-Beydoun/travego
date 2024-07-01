package SpringBootStarterProject.Trip_ReservationPackage.Models;

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
    private String User_email;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private TripReservation tripReservation;

    @OneToOne(cascade =CascadeType.REMOVE,orphanRemoval = true )
    private Passenger_Details passenger_details_id;

    private String confirmation_statue;

    private String description;



}
