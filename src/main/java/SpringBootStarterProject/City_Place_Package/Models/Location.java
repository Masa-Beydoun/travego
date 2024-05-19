package SpringBootStarterProject.City_Place_Package.Models;

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
public class Location {
    @Id
    @SequenceGenerator(
            sequenceName = "location_id",
            name = "location_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "location_id"
    )
    private Integer id;
    private  Double latitude;
    private  Double longitude;
}
