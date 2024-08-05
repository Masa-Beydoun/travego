package SpringBootStarterProject.ReviewsPackage.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripReviewResponse {
    private Integer id;
    private Integer tripId;
    private LocalDate reviewDate;
    private Double places;
    private Double services;
    private Double generalRating;
}
