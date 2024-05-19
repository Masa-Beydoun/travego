package SpringBootStarterProject.City_package.Models;

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
@Table(name = "country")
public class Country {


    @Id
    @SequenceGenerator(
            name = "country_id",
            sequenceName = "country_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "country_id"
    )
    private Integer id;
    //    `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
    private String name;
    private String iso;
    private String iso3;
    private String dial;
    private String currency;
    private String currency_name;

}
