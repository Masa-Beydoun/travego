package SpringBootStarterProject.HotelsPackage.Response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RoomServicesResponse {
    private Integer id;
    private String name;
}
