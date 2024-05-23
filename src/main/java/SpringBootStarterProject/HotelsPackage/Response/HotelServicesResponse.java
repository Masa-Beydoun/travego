package SpringBootStarterProject.HotelsPackage.Response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HotelServicesResponse {
    private Integer id;
    private String name;
}
