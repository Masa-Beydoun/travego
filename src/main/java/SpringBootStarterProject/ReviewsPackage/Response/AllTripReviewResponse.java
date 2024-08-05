package SpringBootStarterProject.ReviewsPackage.Response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AllTripReviewResponse {

    private Integer tripId;
    private Double places;
    private Double services;
    private Double generalRating;
}
