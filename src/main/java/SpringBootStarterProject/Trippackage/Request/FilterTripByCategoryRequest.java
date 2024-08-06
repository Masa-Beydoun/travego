package SpringBootStarterProject.Trippackage.Request;

import SpringBootStarterProject.ManagingPackage.annotation.ValidEnum;
import SpringBootStarterProject.Trippackage.Enum.TripCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FilterTripByCategoryRequest {
    @JsonProperty
    @ValidEnum(enumClass = TripCategory.class)
    @NotBlank(message = "category can't be null")
    private String category;
}
