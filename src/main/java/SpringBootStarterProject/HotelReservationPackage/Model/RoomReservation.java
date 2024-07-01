package SpringBootStarterProject.HotelReservationPackage.Model;

import SpringBootStarterProject.HotelsPackage.Models.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomReservation {
    @Id
    @SequenceGenerator(
            name = "room_reservation_id",
            sequenceName = "room_reservation_id",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "room_reservation_id",
            strategy = GenerationType.SEQUENCE
    )
    private Integer id;

    @ManyToOne
    private Room room;
    private Integer numberOfRooms;

    private Integer hotelReservationId;
    private Integer totalExtraBed;
    private String notes;
    private Double priceForOneRoom;
    private Double totalPrice;
}
