package SpringBootStarterProject.HotelsPackage.Models;

import SpringBootStarterProject.City_Place_Package.Models.City;
import SpringBootStarterProject.City_Place_Package.Models.Country;
import SpringBootStarterProject.HotelsPackage.Enum.HotelServiceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hotel {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer num_of_rooms;
    private String description;
    @Min(0)
    @Max(10)
    private Integer stars;
    @ManyToOne(cascade = CascadeType.ALL)
    private City city;
    @ManyToOne(cascade = CascadeType.ALL)
    private Country country;
    @OneToOne(cascade = CascadeType.ALL)
    private HotelDetails hotelDetails;
    private Integer photoId;

}
