package SpringBootStarterProject.Trip_package.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TripPrice {
    @Id
    @SequenceGenerator(
            name = "trip_price_id",
            sequenceName = "trip_price_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "trip_price_id"
    )
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Trip trip;

//    private Location location;
    private Integer hotelPrice;
    private Integer flightPrice;
    private Integer servicesPrice;

}
