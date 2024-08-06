package SpringBootStarterProject.Trippackage.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private LocalDateTime start_date;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private LocalDateTime end_date;
}
