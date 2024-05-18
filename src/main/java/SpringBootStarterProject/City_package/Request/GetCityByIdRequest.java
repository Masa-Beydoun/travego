package SpringBootStarterProject.City_package.Request;

import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
@Builder
public class GetCityByIdRequest {

    @NotBlank(message = "Id must not be null")
    private Integer id;
}
