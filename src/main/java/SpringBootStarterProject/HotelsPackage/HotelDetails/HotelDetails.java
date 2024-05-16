package SpringBootStarterProject.HotelsPackage.HotelDetails;

import SpringBootStarterProject.HotelsPackage.hotelPackage.Hotel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Setter
@Getter

public class HotelDetails {

    @Id
    @GeneratedValue
    private Integer id;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer priceForExtraBed;
    private Double distanceFromCity;
    private Double breakfastPrice;
    @OneToOne
    private Hotel hotel;



}
