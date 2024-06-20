package SpringBootStarterProject.SuggestTrip.Response;

import SpringBootStarterProject.City_Place_Package.Models.Place;
import SpringBootStarterProject.Trip_package.Models.TripServices;
import SpringBootStarterProject.UserPackage.Models.Client;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class SuggestedTripResponse {

    private Integer suggestedTripId;
    private Integer num_of_passenger;

    private LocalDate date_of_departure;
    private String transportation_type;

    private Client user_id;
    private List<TripServices> trip_service;

    private List<Place> places;




}
