package SpringBootStarterProject.HotelReservationPackage.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RoomReservationRequest {
    @NotNull
    private Integer roomId;
    @NotNull
    private Integer hotelReservationId;
    @NotNull
    @Min(1)
    private Integer numberOfRooms;
    @Min(0)
    private Integer numberOfExtraBed;
    private String notes;


}
