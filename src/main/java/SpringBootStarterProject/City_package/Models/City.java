package SpringBootStarterProject.City_package.Models;

import SpringBootStarterProject.Trip_package.Models.Trip;
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
public class City {
    @Id
    @SequenceGenerator(
            sequenceName = "city_id",
            name = "city_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "city_id"
    )
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn()
    private Country country;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Trip.class,mappedBy = "cities")
    private List<Trip> trips;

}

