package SpringBootStarterProject.Trip_package.Models;


import SpringBootStarterProject.City_Place_Package.Models.Place;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TripPlan {
    @Id
    @SequenceGenerator(
            name = "trip_plan_id",
            sequenceName = "trip_plan_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "trip_plan_id"
    )
    private Integer id;
//    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

    private LocalDateTime startDate;
    private LocalDateTime endDate;


}
