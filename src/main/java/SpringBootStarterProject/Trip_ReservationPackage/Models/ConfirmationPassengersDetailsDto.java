package SpringBootStarterProject.Trip_ReservationPackage.Models;

import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link ConfirmationPassengersDetails}
 */
@Data
@Builder
@Setter
@Getter
public class ConfirmationPassengersDetailsDto implements Serializable {
     public String User_email;
    public TripReservation tripReservation;
    public String Confirmation_statue;
    public String Descriprtion;



}