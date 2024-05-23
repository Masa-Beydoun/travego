package SpringBootStarterProject.HotelsPackage.Models;

import SpringBootStarterProject.HotelsPackage.Enum.HotelServiceType;
import SpringBootStarterProject.Trip_package.Models.HotelServices;
import SpringBootStarterProject.Trip_package.Models.TripServices;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelDetails {

    @Id
    @SequenceGenerator(
            name = "hotel_details_id",
            sequenceName = "hotel_details_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "hotel_details_id"
    )
    private Integer id;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer priceForExtraBed;
    private Double distanceFromCity;
    private Double breakfastPrice;
    private List <Integer> photos;
    @OneToOne
    private Hotel hotel;
    @OneToMany(mappedBy = "hotelDetails", cascade = CascadeType.ALL)
    private List<Room> room;
    @OneToMany(cascade = CascadeType.ALL)
    private List<HotelReview> reviews;
    @OneToMany(cascade = CascadeType.ALL)
    private List<HotelCommentReview> commentReviews;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "hotel_services_hotel",
            joinColumns =@JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "hotel_services_id")
    )
    private List<HotelServices> hotelServices = new ArrayList<>();
}
