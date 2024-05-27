package SpringBootStarterProject.HotelsPackage.Models;

import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelServices {
    @Id
    @SequenceGenerator(
            name = "hotel_services_id",
            sequenceName = "hotel_services_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "hotel_services_id"
    )
    private Integer id;
    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = HotelDetails.class)
    private List<HotelDetails>  details;

}
