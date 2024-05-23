package SpringBootStarterProject.HotelsPackage.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomServicesRequest {
    @NotBlank(message = "name must not be null")
    private String tripName;
}
