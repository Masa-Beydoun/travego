package SpringBootStarterProject.Trippackage.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TripPlanResponse {

    private Integer id;
//    private String name;
    private String description;
    private Integer trip_id;
    private Integer place_id;
    private LocalDateTime start_time;
    private LocalDateTime end_time;

}
