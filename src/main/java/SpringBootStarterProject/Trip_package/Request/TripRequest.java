package SpringBootStarterProject.Trip_package.Request;

import SpringBootStarterProject.Trip_package.Enum.FlightCompany;
import SpringBootStarterProject.Trip_package.Enum.TripCategory;
import SpringBootStarterProject.Trip_package.Enum.TripStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TripRequest {
    @NotEmpty(message = "name must not be empty")
    private String tripName;
    @NotEmpty(message = "Description must not be empty")
    private String tripDescription;
    @NotEmpty(message = "Category must not be empty")
    private TripCategory tripCategory;
    @NotEmpty(message = "start date must not be empty")
    private LocalDateTime tripStartDate;
    @NotEmpty(message = "end date must not be empty")
    private LocalDateTime tripEndDate;
    @NotEmpty(message = "country must not be empty")
    private String country;
    @NotEmpty(message = "cities must not be empty")
    private List<String> cities;
    @NotEmpty(message = "flight company must not be empty")
    private FlightCompany flightCompany;
    @NotEmpty(message = "min number of passengers must not be empty")
    private Integer min_passengers;
    @NotEmpty(message = "max number of passengers must not be empty")
    private Integer max_passengers;
    @NotEmpty(message = "state of trip must not be empty")
    private TripStatus status;
//    @NotEmpty(message = "services must not be empty")
    private List<String> tripServices;
    @NotEmpty(message = "price must not be empty")
    private Integer price;
    private String isPrivate;


}
