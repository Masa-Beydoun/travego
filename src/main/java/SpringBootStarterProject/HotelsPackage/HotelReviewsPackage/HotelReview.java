package SpringBootStarterProject.HotelsPackage.HotelReviewsPackage;

//import SpringBootStarterProject.HotelsPackage.HotelPackage.Hotel;
import SpringBootStarterProject.HotelsPackage.Hotel.Hotel;
import SpringBootStarterProject.UserPackage.Models.Client;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
    private Integer averageRating;


    @ManyToOne
    private Client client;


    HotelReview(NewHotelReviewRequest newHotelReviewRequest) {
        hotel = newHotelReviewRequest.getHotel();
        reviewDate = LocalDate.now();
        if(security>10)throw new IllegalArgumentException("security must be at most 10");
        if(location>10)throw new IllegalArgumentException("location must be at most 10");
        if(facilities>10)throw new IllegalArgumentException("facilities must be at most 10");
        if(cleanliness>10)throw new IllegalArgumentException("cleanliness must be at most 10");


        security = newHotelReviewRequest.getSecurity();
        location = newHotelReviewRequest.getLocation();
        facilities = newHotelReviewRequest.getFacilities();
        cleanliness = newHotelReviewRequest.getCleanliness();
        averageRating = (security+location+facilities+cleanliness)/4;
        client = newHotelReviewRequest.getClient();

    }






}
