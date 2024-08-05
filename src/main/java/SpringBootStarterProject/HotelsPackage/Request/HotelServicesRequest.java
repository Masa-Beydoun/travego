package SpringBootStarterProject.HotelsPackage.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class HotelServicesRequest {
    @NotBlank(message = "name must not be null")
    private String HotelServiceName;
}
