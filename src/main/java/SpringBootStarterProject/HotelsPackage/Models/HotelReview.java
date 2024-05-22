package SpringBootStarterProject.HotelsPackage.Models;

import SpringBootStarterProject.HotelsPackage.Request.HotelReviewRequest;
import SpringBootStarterProject.UserPackage.Models.Client;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Hotel hotel;

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


    HotelReview(HotelReviewRequest hotelReviewRequest) {
        hotel = hotelReviewRequest.getHotel();
        reviewDate = LocalDate.now();
        if(security>10)throw new IllegalArgumentException("security must be at most 10");
        if(location>10)throw new IllegalArgumentException("location must be at most 10");
        if(facilities>10)throw new IllegalArgumentException("facilities must be at most 10");
        if(cleanliness>10)throw new IllegalArgumentException("cleanliness must be at most 10");


        security = hotelReviewRequest.getSecurity();
        location = hotelReviewRequest.getLocation();
        facilities = hotelReviewRequest.getFacilities();
        cleanliness = hotelReviewRequest.getCleanliness();
        averageRating = (security+location+facilities+cleanliness)/4;
        client = hotelReviewRequest.getClient();

    }






}
