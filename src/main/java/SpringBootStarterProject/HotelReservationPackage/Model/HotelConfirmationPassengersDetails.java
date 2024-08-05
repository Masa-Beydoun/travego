package SpringBootStarterProject.HotelReservationPackage.Model;

import SpringBootStarterProject.HotelReservationPackage.Enum.AcceptingStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private HotelReservation hotelReservation;

    @OneToOne(cascade =CascadeType.REMOVE,orphanRemoval = true )
    @JsonBackReference
    private HotelReservationPassengerDetails passenger_details_id;


    private String description;

    @Enumerated(EnumType.STRING)
    private AcceptingStatus status;



}
