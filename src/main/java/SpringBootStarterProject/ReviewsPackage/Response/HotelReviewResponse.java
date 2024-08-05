package SpringBootStarterProject.ReviewsPackage.Response;

import SpringBootStarterProject.UserPackage.Models.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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
