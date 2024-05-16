package SpringBootStarterProject.HotelsPackage.hotelPackage;

import SpringBootStarterProject.HotelsPackage.HotelCommentreview.HotelCommentReview;
import SpringBootStarterProject.HotelsPackage.HotelDetails.HotelDetails;
import SpringBootStarterProject.HotelsPackage.hotelReviewsPackage.HotelReview;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Hotel {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer num_of_rooms;
    @Min(0)
    @Max(10)
    private Integer stars;
    //edit to relationship with city and country
    private Integer city;
    private Integer country;
    //edit photo

    @OneToMany
    private List<HotelReview> reviews;
    @OneToMany
    private List<HotelCommentReview> commentReviews;

    private String description;


    @OneToOne
    private HotelDetails hotelDetails;





}
