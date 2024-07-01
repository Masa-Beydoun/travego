package SpringBootStarterProject.ReservationConfirmPackage.Request;

import SpringBootStarterProject.ReservationConfirmPackage.Enum.ReservationType;
import SpringBootStarterProject.UserPackage.Models.Manager;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmReservationRequest {

    @NotNull
    private Integer reservationId;
    @NotNull
    private String description;

}
