package SpringBootStarterProject.City_Place_Package.Request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Data
//@Builder
@NoArgsConstructor
public class GetCityByIdRequest {

    @NotBlank(message = "Id must not be null")
    private Integer id;
}
