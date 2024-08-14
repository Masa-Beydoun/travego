package SpringBootStarterProject.Trippackage.Request;

import SpringBootStarterProject.ManagingPackage.annotation.ValidEnum;
import SpringBootStarterProject.Trippackage.Enum.TripStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FilterTripByStatusRequest {

    @JsonProperty
    @ValidEnum(enumClass = TripStatus.class)
    @NotBlank(message = "category can't be null")
    private String status;
}
