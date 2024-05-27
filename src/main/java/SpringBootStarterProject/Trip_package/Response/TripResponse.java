package SpringBootStarterProject.Trip_package.Response;

import SpringBootStarterProject.Trip_package.Enum.FlightCompany;
import SpringBootStarterProject.Trip_package.Enum.TripCategory;
import SpringBootStarterProject.Trip_package.Enum.TripStatus;
import SpringBootStarterProject.Trip_package.Models.TripServices;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class TripResponse {
    private Integer tripId;
    private String tripName;
    private String tripDescription;
    private TripCategory tripCategory;
    private LocalDateTime tripStartDate;
    private LocalDateTime tripEndDate;
    private String country;
    private List<String> cities;
    private List<String> hotels;
    private FlightCompany flightCompany;
    private Integer min_passengers;
    private Integer max_passengers;
    private TripStatus status;
    private List<String> tripServices;
    private Integer price;
    private String isPrivate;


}
