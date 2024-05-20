package SpringBootStarterProject.Trip_package.Request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TripPlanRequest {
//    private String name;
    private String description;
    private Integer trip_id;
    private Integer place_id;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
}
