package SpringBootStarterProject.TripReservationPackage.Models;

import SpringBootStarterProject.TripReservationPackage.Models.TripReservation;
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
     public String userEmail;
    public TripReservation tripReservation;
    public String Confirmation_statue;
    public String Descriprtion;



}