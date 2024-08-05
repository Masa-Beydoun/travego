package SpringBootStarterProject.ReviewsPackage.Request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TripReviewRequest {

    @NotNull(message = "trip id should not be null")
    private Integer tripId;
    @Min(value = 0,message = "security must be more than 0")
    @Max(value = 10,message = "security must be less than 10")
    private Double places;
    @Min(value = 0,message = "location must be more than 0")
    @Max(value = 10,message = "location must be less than 10")
    private Double services;
    @Min(value = 0,message = "facilities must be more than 0")
    @Max(value = 10,message = "facilities must be less than 10")
    private Double generalRating;

}
