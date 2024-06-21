package SpringBootStarterProject.SuggestTrip.Model;

import SpringBootStarterProject.City_Place_Package.Models.Place;
import SpringBootStarterProject.Trip_package.Models.TripServices;
import SpringBootStarterProject.UserPackage.Models.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuggestedTrip {
    @Id
    @SequenceGenerator(
            sequenceName = "suggested_trip_id",
            name = "suggested_trip_id",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "suggested_trip_id",
            strategy = GenerationType.SEQUENCE
    )
    private Integer id;
    private Integer num_of_passenger;
    private LocalDate date_of_departure;

    private String transportation_type;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Client user_id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "suggested_trip_service",
            joinColumns = @JoinColumn(name = "suggested_trip_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_services_id")
    )
    private List<TripServices> trip_service;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "suggested_trip_place",
            joinColumns = @JoinColumn(name = "suggested_trip_id"),
            inverseJoinColumns = @JoinColumn(name = "place_id")
    )
    private List<Place> places;
}
