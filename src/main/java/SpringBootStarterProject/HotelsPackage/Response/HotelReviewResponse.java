package SpringBootStarterProject.HotelsPackage.Response;

import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.UserPackage.Models.Client;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    private Client client;

}
