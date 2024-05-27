package SpringBootStarterProject.HotelsPackage.Response;

import SpringBootStarterProject.HotelsPackage.Models.Hotel;
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
    private Hotel hotel;
    private LocalDate reviewDate;
    private Integer security;
    private Integer location;
    private Integer facilities;
    private Integer cleanliness;
    private double averageRating;
    private Client client;

}
