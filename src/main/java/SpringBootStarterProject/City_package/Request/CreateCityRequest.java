package SpringBootStarterProject.City_package.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCityRequest {
private String name;
private String country;
}
