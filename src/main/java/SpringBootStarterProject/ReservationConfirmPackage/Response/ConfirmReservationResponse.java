package SpringBootStarterProject.ReservationConfirmPackage.Response;

import SpringBootStarterProject.ReservationConfirmPackage.Enum.ReservationType;
import SpringBootStarterProject.UserPackage.Models.Manager;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ConfirmReservationResponse {
    private Integer id;
    private String type;
    private Integer reservationId;
    private String description;
    private Integer managerId;
    private String status;
}
