package SpringBootStarterProject.HotelsPackage.Request;

import SpringBootStarterProject.City_Place_Package.Models.City;
import SpringBootStarterProject.City_Place_Package.Models.Country;
//import SpringBootStarterProject.City_package.Models.City;
//import SpringBootStarterProject.City_package.Models.Country;
//import SpringBootStarterProject.HotelsPackage.HotelServices.HotelServiceType;
//import SpringBootStarterProject.HotelsPackage.HotelServices.HotelServices;
//import SpringBootStarterProject.Resources.Resource;
import SpringBootStarterProject.HotelsPackage.Enum.HotelServiceType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Data
public class HotelRequest {

    @NotBlank(message = "name is null")
    private String name;
    @NotBlank(message = "description is null")
    private String description;
    private Integer num_of_rooms;
    @Min(0)
    @Max(10)
    private Integer stars;
    @NotBlank(message = "city id is null")
    private String city;
    @NotBlank(message = "country id is null")
    private String country;

    private MultipartFile file;

}
