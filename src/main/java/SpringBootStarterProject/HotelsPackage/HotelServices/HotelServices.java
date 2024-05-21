package SpringBootStarterProject.HotelsPackage.HotelServices;

import SpringBootStarterProject.HotelsPackage.Hotel.Hotel;
import SpringBootStarterProject.Trip_package.Models.Trip;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class HotelServices {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "hotel_services_id"
    )
    @SequenceGenerator(
            name = "hotel_services_id",
            sequenceName = "hotel_services_id",
            allocationSize = 1
    )
    private Long id;
    private String name;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Trip.class)
    private List<Hotel> hotels;




}
