package SpringBootStarterProject.favouritePackage;

import SpringBootStarterProject.UserPackage.Models.Client;
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
public class Favorite {
    @Id
    @SequenceGenerator(
            name = "favourite_id",
            sequenceName = "favourite_id",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "favourite_id",
            strategy = GenerationType.SEQUENCE
    )
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Client client;

    @Enumerated(EnumType.STRING)
    private FavoriteType favoriteType;

    private Integer favouriteId;

}
