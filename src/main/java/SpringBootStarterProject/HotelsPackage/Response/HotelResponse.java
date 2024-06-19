package SpringBootStarterProject.HotelsPackage.Response;

import SpringBootStarterProject.City_Place_Package.Models.Country;
import SpringBootStarterProject.ResourcesPackage.Response.MultipartResponse;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HotelResponse {
    private Integer hotelId;
    private String hotelName;
    private Integer num_of_rooms;
    private Integer stars;
    private Integer cityId;
    private String cityName;
    private Country country;
    private String description;
    private MultipartResponse photo;
}
