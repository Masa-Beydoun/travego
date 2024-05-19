package SpringBootStarterProject.Trip_package.Models;

import SpringBootStarterProject.City_package.Models.City;
import SpringBootStarterProject.City_package.Models.Country;
import SpringBootStarterProject.Trip_package.Enum.FlightCompany;
import SpringBootStarterProject.Trip_package.Enum.TripCategory;
import SpringBootStarterProject.Trip_package.Enum.TripStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Trip {
    @Id
    @SequenceGenerator(
            name = "trip_id",
            sequenceName = "trip_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "trip_id"
    )
    private Integer id;
    private String name;
    private String description;
    private TripCategory tripCategory;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @ManyToOne(cascade = CascadeType.ALL)
    private Country country;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "city_trip",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "city_id")
    )
    private List<City> cities;
    private FlightCompany flightCompany;
    private Integer min_passengers;
    private Integer max_passengers;
    private TripStatus status;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "trip_services_trip",
            joinColumns =@JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_service_id")
    )
    private List<TripServices> tripServices = new ArrayList<>();
    @OneToOne
    private TripPrice price;
    private Boolean isPrivate;

}
