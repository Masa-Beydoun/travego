package SpringBootStarterProject.HotelsPackage.Request;

import SpringBootStarterProject.City_Place_Package.Models.City;
import SpringBootStarterProject.City_Place_Package.Models.Country;
//import SpringBootStarterProject.City_package.Models.City;
//import SpringBootStarterProject.City_package.Models.Country;
//import SpringBootStarterProject.HotelsPackage.HotelServices.HotelServiceType;
//import SpringBootStarterProject.HotelsPackage.HotelServices.HotelServices;
import SpringBootStarterProject.HotelsPackage.HotelServicesPackage.HotelServices;
import SpringBootStarterProject.HotelsPackage.RoomPackage.Room;
//import SpringBootStarterProject.Resources.Resource;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Data
public class NewHotelRequest {

    @NotBlank(message = "name is null")
    private String name;
    @NotBlank(message = "description is null")
    private String description;
    @NotBlank(message = "num of rooms is null")
    private Integer num_of_rooms;
    @NotBlank(message = "stars is null")
    @Min(0)
    @Max(10)
    private Integer stars;
    @NotBlank(message = "city id is null")
    private City city;
    @NotBlank(message = "country id is null")
    private Country country;
    private List<HotelServices> hotelServices;
    private List<Room> room;
    private MultipartFile photoId;


}
