package SpringBootStarterProject.Trip_package.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TripPlanRequest {
//    private String name;
    @NotBlank(message = "description must not be blank")
    private String description;
    @NotNull(message = "trip-id must not be null")
    private Integer trip_id;
    @NotNull(message = "place-id must not be null")
    private Integer place_id;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
}
