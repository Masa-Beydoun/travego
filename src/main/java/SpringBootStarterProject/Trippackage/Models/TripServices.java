package SpringBootStarterProject.Trippackage.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TripServices {
@Id
    @SequenceGenerator(
            name = "trip_services_id",
            sequenceName = "trip_services_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "trip_services_id"
    )
    private Integer id;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Trip.class)
    private List<Trip>  trip;

}
