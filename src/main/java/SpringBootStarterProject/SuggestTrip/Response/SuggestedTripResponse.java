package SpringBootStarterProject.SuggestTrip.Response;

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

    private Integer user_id;
    private List<String> trip_service;

    private List<String> places;




}
