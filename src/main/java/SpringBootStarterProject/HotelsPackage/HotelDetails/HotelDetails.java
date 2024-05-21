package SpringBootStarterProject.HotelsPackage.HotelDetails;

import SpringBootStarterProject.HotelsPackage.Hotel.Hotel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
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
    @OneToOne
    private Hotel hotel;

    List <Integer> photos;



}
