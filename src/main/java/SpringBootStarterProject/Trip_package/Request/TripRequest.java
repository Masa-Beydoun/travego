package SpringBootStarterProject.Trip_package.Request;

import SpringBootStarterProject.Trip_package.Enum.FlightCompany;
import SpringBootStarterProject.Trip_package.Enum.TripCategory;
import SpringBootStarterProject.Trip_package.Enum.TripStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TripRequest {
    @NotBlank(message = "name must not be empty")
    private String tripName;
    @NotBlank(message = "Description must not be empty")
    private String tripDescription;
    @NotBlank(message = "Category must not be empty")
    private String tripCategory;
//    @NotBlank(message = "start date must not be empty")
    private LocalDateTime tripStartDate;
//    @NotBlank(message = "end date must not be empty")
    private LocalDateTime tripEndDate;
    @NotBlank(message = "country must not be empty")
    private String country;
//    @NotBlank(message = "cities must not be empty")
    @NotEmpty(message = "cities must not be empty")
    private List<String> cities;
    @NotEmpty(message = "hotels must not be empty")
    private List<String> hotels;
    @NotEmpty(message = "flight company must not be empty")
    private String flightCompany;
//    @NotBlank(message = "min number of passengers must not be empty")
    private Integer min_passengers;
//    @NotBlank(message = "max number of passengers must not be empty")
    private Integer max_passengers;
    @NotEmpty(message = "state of trip must not be empty")
    private String  status;
//    @NotEmpty(message = "services must not be empty")
    private List<String> tripServices;
//    @NotEmpty(")
//    @NotBlank(message = "price must not be empty")
    @NotNull(message = "hotel-price must not be null" )
    private Integer hotelPrice;
    @NotNull(message = "flight-price must not be null")
    private Integer flightPrice;
    @NotNull(message = "service-price must not be null")
    private Integer servicesPrice;
    private Boolean isPrivate;


}
