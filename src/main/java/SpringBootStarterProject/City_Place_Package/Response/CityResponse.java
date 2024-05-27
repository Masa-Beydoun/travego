package SpringBootStarterProject.City_Place_Package.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityResponse {
    private Integer id;
    private String cityName;
    private String countryName;

}
