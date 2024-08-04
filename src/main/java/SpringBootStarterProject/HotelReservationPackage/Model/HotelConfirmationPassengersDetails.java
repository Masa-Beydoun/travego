package SpringBootStarterProject.HotelReservationPackage.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HotelConfirmationPassengersDetails {

    @Id
    @GeneratedValue
    private Integer id;
    private String User_email;
    @ManyToOne(cascade = CascadeType.REMOVE)
    private HotelReservation hotelReservation;

    @OneToOne(cascade =CascadeType.REMOVE,orphanRemoval = true )
    private HotelReservationPassengerDetails passenger_details_id;

    private String confirmation_statue;

    private String description;



}
