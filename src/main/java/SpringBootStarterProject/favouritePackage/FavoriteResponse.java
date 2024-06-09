package SpringBootStarterProject.favouritePackage;

import SpringBootStarterProject.UserPackage.Models.Client;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FavoriteResponse {
    private Integer id;
    private Client client;
    private String favouriteType;
    private Integer favouriteId;
}
