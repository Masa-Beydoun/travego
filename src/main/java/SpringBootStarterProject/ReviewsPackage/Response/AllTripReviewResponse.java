package SpringBootStarterProject.ReviewsPackage.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllTripReviewResponse {

    private Integer tripId;
    private Double places;
    private Double services;
    private Double generalRating;
}
