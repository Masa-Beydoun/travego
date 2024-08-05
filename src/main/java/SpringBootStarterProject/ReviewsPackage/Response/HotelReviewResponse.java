package SpringBootStarterProject.ReviewsPackage.Response;

import SpringBootStarterProject.UserPackage.Models.Client;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Builder
@Data
public class HotelReviewResponse {

    private Integer id;
    private Integer hotelDetailsId;
    private LocalDate reviewDate;
    private Double security;
    private Double location;
    private Double facilities;
    private Double cleanliness;
    private Double averageRating;

}
