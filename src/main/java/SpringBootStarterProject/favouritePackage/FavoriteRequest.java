package SpringBootStarterProject.favouritePackage;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FavoriteRequest {

    @NotNull
    private Integer clientId;

    @NotNull
    private Integer favouriteId;

}
