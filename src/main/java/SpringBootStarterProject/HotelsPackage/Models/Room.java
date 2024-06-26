package SpringBootStarterProject.HotelsPackage.Models;


import SpringBootStarterProject.HotelsPackage.Enum.RoomServicesType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Locale;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @SequenceGenerator(
            name = "room_id",
            sequenceName = "room_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "room_id"
    )
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private HotelDetails hotelDetails;

    private Integer num_of_bed;

    private Integer space;
    private Integer maxNumOfPeople;
    private Integer price;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "room_service_room",
            joinColumns =@JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "room_services_id")
    )
    private List<RoomServices> roomServices;









}
