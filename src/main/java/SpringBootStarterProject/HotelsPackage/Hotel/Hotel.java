package SpringBootStarterProject.HotelsPackage.Hotel;

import SpringBootStarterProject.City_package.Models.City;
import SpringBootStarterProject.City_package.Models.Country;
import SpringBootStarterProject.HotelsPackage.HotelCommentReview.HotelCommentReview;
import SpringBootStarterProject.HotelsPackage.HotelDetails.HotelDetails;
import SpringBootStarterProject.HotelsPackage.HotelServices.HotelServices;
import SpringBootStarterProject.HotelsPackage.RoomPackage.Room;
import SpringBootStarterProject.HotelsPackage.HotelReviewsPackage.HotelReview;
import SpringBootStarterProject.ResourcesPackage.FileEntity;
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
    @SequenceGenerator(
            name = "hotel_id",
            sequenceName = "hotel_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "hotel_id"
    )
    private Integer id;
    private String name;
    private Integer num_of_rooms;
    @Min(0)
    @Max(10)
    private Integer stars;
    @ManyToOne
    private City cityId;
    @ManyToOne(cascade = CascadeType.ALL)
    private Country countryId;
    @OneToMany(cascade = CascadeType.ALL)
    private List<HotelReview> reviews;
    @OneToMany(cascade = CascadeType.ALL)
    private List<HotelCommentReview> commentReviews;
    private String description;
    @OneToOne(cascade = CascadeType.ALL)
    private HotelDetails hotelDetails;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "hotel_service_hotel",
            joinColumns =@JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_service_id")
    )
    List<HotelServices> hotelServices;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> room;
    @OneToOne(cascade = CascadeType.ALL)
    private FileEntity photos;
}
