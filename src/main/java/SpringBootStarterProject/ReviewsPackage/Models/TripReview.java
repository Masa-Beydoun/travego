package SpringBootStarterProject.ReviewsPackage.Models;


import SpringBootStarterProject.Trippackage.Models.Trip;
import SpringBootStarterProject.UserPackage.Models.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripReview {
    @Id
    @SequenceGenerator(
            name = "trip_review_id",
            sequenceName = "trip_review_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "trip_review_id"
    )
    private Integer id;

    @ManyToOne
    @JsonBackReference
    private Trip trip;

    private LocalDate reviewDate;

    @ManyToOne
    private Client client;

    @Min(0)
    @Max(10)
    private Double places;
    @Min(0)
    @Max(10)
    private Double services;
    @Min(0)
    @Max(10)
    private Double generalRating;


}
