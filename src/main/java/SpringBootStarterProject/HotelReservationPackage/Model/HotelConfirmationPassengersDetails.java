package SpringBootStarterProject.HotelReservationPackage.Model;

import SpringBootStarterProject.Trip_ReservationPackage.Models.Passenger_Details;
import SpringBootStarterProject.Trip_ReservationPackage.Models.TripReservation;
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
    private HotelReservation HotelReservation;

    @OneToOne(cascade =CascadeType.REMOVE,orphanRemoval = true )
    private HotelReservationPassengerDetails passenger_details_id;

    private String confirmation_statue;

    private String description;



}
