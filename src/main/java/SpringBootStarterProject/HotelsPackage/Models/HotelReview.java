package SpringBootStarterProject.HotelsPackage.Models;

import SpringBootStarterProject.HotelsPackage.Request.HotelReviewRequest;
import SpringBootStarterProject.UserPackage.Models.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;
import java.util.Locale;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelReview {
    @Id
    @SequenceGenerator(
            name = "hotel_review_id",
            sequenceName = "hotel_review_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "hotel_review_id"
    )
    private Integer id;

    @ManyToOne
    private HotelDetails hotelDetails;

    private LocalDate reviewDate;
    //add user
    @Min(0)
    @Max(10)
    private Integer security;
    @Min(0)
    @Max(10)
    private Integer location;
    @Min(0)
    @Max(10)
    private Integer facilities;
    @Min(0)
    @Max(10)
    private Integer cleanliness;
    @Min(0)
    @Max(10)
    private double averageRating;
    @ManyToOne
    private Client client;

}
