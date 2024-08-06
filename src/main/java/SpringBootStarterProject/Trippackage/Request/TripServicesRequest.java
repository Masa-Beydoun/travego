package SpringBootStarterProject.Trippackage.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TripServicesRequest {
    @NotEmpty(message = "name must not be null")
    @NotBlank(message = "name must not be null")
    @JsonProperty
    private String tripName;
}
