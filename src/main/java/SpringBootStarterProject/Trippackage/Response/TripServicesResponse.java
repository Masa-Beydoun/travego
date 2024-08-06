package SpringBootStarterProject.Trippackage.Response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TripServicesResponse {
    private Integer id;
    private String name;
}
