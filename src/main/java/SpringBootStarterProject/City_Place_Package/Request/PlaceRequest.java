package SpringBootStarterProject.City_Place_Package.Request;

import SpringBootStarterProject.City_Place_Package.DTO.TimeDTO;
import SpringBootStarterProject.City_Place_Package.Models.Location;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class PlaceRequest {
    private String name;
    private String description;
    private String country;
    private String city;
//    @NotBlank(message = "Opening time must not be null")
    private TimeDTO openingTime;
//    @NotBlank(message = "Closing time time must not be null")
    private TimeDTO closingTime;
    private  Double latitude;
    private  Double longitude;

}
