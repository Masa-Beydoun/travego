package SpringBootStarterProject.SuggestTrip.Request;

import SpringBootStarterProject.City_Place_Package.Models.Place;
import SpringBootStarterProject.Trip_package.Service.TripService;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class SuggestTripRequest {

    private Integer num_of_passengers;
    private LocalDate date_of_departure;

    private List<TripService> tripService;
    private List<Place> places;
    private List<String> transportations;
}
