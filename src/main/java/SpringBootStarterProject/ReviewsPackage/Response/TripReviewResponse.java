package SpringBootStarterProject.ReviewsPackage.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class TripReviewResponse {
    private Integer id;
    private Integer tripId;
    private LocalDate reviewDate;
    private Double places;
    private Double services;
    private Double generalRating;
}
