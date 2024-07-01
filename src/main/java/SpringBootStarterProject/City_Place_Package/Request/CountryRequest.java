package SpringBootStarterProject.City_Place_Package.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountryRequest {

    @NotBlank(message = "name can't be empty")
    private String name;
    private String iso;
    @NotBlank(message = "iso3 can't be empty")
    private String iso3;
    @NotBlank(message = "dial can't be empty")
    private String dial;
    private String currency;
    @NotBlank(message = "currency name can't be empty")
    private String currency_name;
}
