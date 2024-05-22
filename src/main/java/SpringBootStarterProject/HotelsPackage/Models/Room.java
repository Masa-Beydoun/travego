package SpringBootStarterProject.HotelsPackage.Models;


import SpringBootStarterProject.HotelsPackage.Service.RoomServices;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue
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
