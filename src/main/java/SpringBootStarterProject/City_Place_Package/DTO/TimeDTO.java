package SpringBootStarterProject.City_Place_Package.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeDTO {
    private Integer hour;
    private Integer minute;
    private Integer second;
    private Integer millisecond;
}
