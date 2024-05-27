package SpringBootStarterProject.HotelsPackage.Response;

import SpringBootStarterProject.City_Place_Package.Models.City;
import SpringBootStarterProject.City_Place_Package.Models.Country;
import SpringBootStarterProject.HotelsPackage.Enum.HotelServiceType;
import SpringBootStarterProject.HotelsPackage.Models.*;
import SpringBootStarterProject.ResourcesPackage.FileEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class HotelResponse {
    private Integer hotelId;
    private String hotelName;
    private Integer num_of_rooms;
    private Integer stars;
    private City city;
    private Country country;
    private String description;
    private FileEntity photo;
}
