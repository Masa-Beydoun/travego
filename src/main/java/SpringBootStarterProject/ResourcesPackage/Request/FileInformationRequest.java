package SpringBootStarterProject.ResourcesPackage.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class FileInformationRequest {

    private Integer relationId;
    private String relationType;
}
