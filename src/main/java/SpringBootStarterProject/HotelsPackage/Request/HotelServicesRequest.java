package SpringBootStarterProject.HotelsPackage.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
public class HotelServicesRequest {
    @NotBlank(message = "name must not be null")
    private String tripName;
}
