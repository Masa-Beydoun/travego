package SpringBootStarterProject.ReviewsPackage.Request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelReviewRequest {

    @NotNull
    private Integer hotelDetailsId;
    @Min(value = 0,message = "security must be more than 0")
    @Max(value = 10,message = "security must be less than 10")
    private Double security;
    @Min(value = 0,message = "location must be more than 0")
    @Max(value = 10,message = "location must be less than 10")
    private Double location;
    @Min(value = 0,message = "facilities must be more than 0")
    @Max(value = 10,message = "facilities must be less than 10")
    private Double facilities;
    @Min(value = 0,message = "cleanliness must be more than 0")
    @Max(value = 10,message = "loccleanlinessation must be less than 10")
    private Double cleanliness;



}
