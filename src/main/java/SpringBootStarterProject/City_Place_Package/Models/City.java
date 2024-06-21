package SpringBootStarterProject.City_Place_Package.Models;

import SpringBootStarterProject.Trip_package.Models.Trip;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn()
    private Country country;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Trip.class,mappedBy = "cities")
    private List<Trip> trips;

//    @CreatedDate
//    @Column(
//            nullable = false,
//            updatable = false
//    )
//    private LocalDateTime createDate;
//
//    @LastModifiedDate
//    @Column(insertable = false)
//    private LocalDateTime lastModified;
//
//    @CreatedBy
//    @Column(
//            nullable = false,
//            updatable = false
//    )
//    private Integer createdBy;
//
//    @LastModifiedBy
//    @Column(insertable = false)
//    private Integer lastModifiedBy;

}

