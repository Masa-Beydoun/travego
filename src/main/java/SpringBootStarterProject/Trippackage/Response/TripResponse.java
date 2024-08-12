package SpringBootStarterProject.Trippackage.Response;

import SpringBootStarterProject.Trippackage.Enum.FlightCompany;
import SpringBootStarterProject.Trippackage.Enum.TripCategory;
import SpringBootStarterProject.Trippackage.Enum.TripStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    private Optional<List<String>> hotels;
    private FlightCompany flightCompany;
    private Integer min_passengers;
    private Integer max_passengers;
    private TripStatus status;
    private List<String> tripServices;
    private Integer price;
    private String isPrivate;

    private Boolean isFavourite;


}
