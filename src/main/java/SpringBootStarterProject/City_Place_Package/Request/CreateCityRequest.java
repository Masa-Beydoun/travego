package SpringBootStarterProject.City_Place_Package.Request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
@Builder
public class CreateCityRequest {

 @NotBlank(message = "city name must not be null")
 @NotEmpty(message = "city name must not be null")
private String name;
    @NotBlank(message = "country name must not be null")
    private String country;
}
