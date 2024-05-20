package SpringBootStarterProject.City_Place_Package.Response;

import SpringBootStarterProject.City_Place_Package.Models.Location;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Builder
@Data
public class PlaceResponse {

    private Integer id;
    private String name;
    private String description;
    private String country;
    private String city;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Location location;

}
