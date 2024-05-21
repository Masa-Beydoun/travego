package SpringBootStarterProject.HotelsPackage.HotelPackage;

import SpringBootStarterProject.City_Place_Package.Models.City;
import SpringBootStarterProject.City_Place_Package.Models.Country;
import SpringBootStarterProject.HotelsPackage.HotelCommentReview.HotelCommentReview;
import SpringBootStarterProject.HotelsPackage.HotelDetailsPackage.HotelDetails;
import SpringBootStarterProject.HotelsPackage.HotelServicesPackage.HotelServices;
import SpringBootStarterProject.HotelsPackage.RoomPackage.Room;
import SpringBootStarterProject.HotelsPackage.HotelReviewsPackage.HotelReview;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hotel {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer num_of_rooms;
    @Min(0)
    @Max(10)
    private Integer stars;
    @ManyToOne
    private City city;
    @ManyToOne
    private Country country;

    @OneToMany
    private List<HotelReview> reviews;
    @OneToMany
    private List<HotelCommentReview> commentReviews;

    private String description;


    @OneToOne
    private HotelDetails hotelDetails;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "hotel_service_hotel",
            joinColumns =@JoinColumn(name = "hote;_id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_services_id")
    )
    private List<HotelServices> hotelServices;


    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> room;



    private Integer photos;


}
