package SpringBootStarterProject.Trippackage.Response;

import SpringBootStarterProject.Trippackage.Enum.TripStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TripArchivedResponse {
    private int tripId;
    private String tripName;
    private TripStatus status;
}
