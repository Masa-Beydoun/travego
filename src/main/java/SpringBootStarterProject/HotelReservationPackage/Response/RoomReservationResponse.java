package SpringBootStarterProject.HotelReservationPackage.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomReservationResponse {
    private Integer id;

    private Integer roomId;
    private Integer numberOfRooms;

    private Integer hotelReservationId;
    private Integer totalExtraBed;
    private String notes;
    private Double priceForOneRoom;
    private Double totalPrice;
}
