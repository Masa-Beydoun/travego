package SpringBootStarterProject.City_Place_Package.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryResponse {

    private Integer id;
    private String name;
    private String iso;
    private String iso3;
    private String dial;
    private String currency;
    private String currency_name;
}
