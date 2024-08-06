package SpringBootStarterProject.ReviewsPackage.Models;

import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.UserPackage.Models.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;

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
    @JsonBackReference
    private HotelDetails hotelDetails;

    private LocalDate reviewDate;
    //add user
    @Min(0)
    @Max(10)
    private Double security;
    @Min(0)
    @Max(10)
    private Double location;
    @Min(0)
    @Max(10)
    private Double facilities;
    @Min(0)
    @Max(10)
    private Double cleanliness;
    @Min(0)
    @Max(10)
    private double averageRating;
    @ManyToOne
    private Client client;

}
