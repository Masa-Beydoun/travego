package SpringBootStarterProject.HotelsPackage.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelServicesResponse {
    private Integer id;
    private String name;
}
