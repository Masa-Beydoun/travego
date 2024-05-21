package SpringBootStarterProject.City_Place_Package.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Place {
    @Id
    @SequenceGenerator(
            sequenceName = "place_id",
            name = "place_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "place_id"
    )
    private Integer id;
    private String name;
    private String description;
    @ManyToOne
    private Country country;
    @ManyToOne
    private City city;
    private LocalTime openingTime;
    private LocalTime closingTime;

    @OneToOne
    private Location location;
}
