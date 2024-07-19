package SpringBootStarterProject.SuggestTrip.Request;

import SpringBootStarterProject.City_Place_Package.Models.Place;
import SpringBootStarterProject.Trip_package.Service.TripService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class SuggestTripRequest {

    @NotNull(message = "number of passengers can't be null")
    @Min(value = 2,message = "number of passengers should be more than 1")
    private Integer num_of_passengers;

    @NotNull(message = "date of departure can't be null")
    private LocalDate date_of_departure;

    private List<String> tripService;
//    @NotBlank(message = "places can't be blank")
    @NotEmpty(message = "places can't be blank")
    private List<Integer> places;
    private String transportations;
}
