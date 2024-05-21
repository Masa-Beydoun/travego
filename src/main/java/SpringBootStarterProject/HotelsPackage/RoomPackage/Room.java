package SpringBootStarterProject.HotelsPackage.RoomPackage;


import SpringBootStarterProject.HotelsPackage.HotelPackage.Hotel;
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

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    private Integer num_of_bed;

    private Integer space;
    private Integer maxNumOfPeople;
    private Integer price;

    @Enumerated
    private RoomType roomType;

    @Enumerated
    private List<RoomServiceType> roomServiceTypes;





}
