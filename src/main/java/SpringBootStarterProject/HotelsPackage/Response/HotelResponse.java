package SpringBootStarterProject.HotelsPackage.Response;

import SpringBootStarterProject.City_Place_Package.Models.Country;
import SpringBootStarterProject.ResourcesPackage.FileMetaData;
import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.Resource;

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
    private Resource resource;
    private FileMetaData photo;
}
