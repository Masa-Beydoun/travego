package SpringBootStarterProject.ResourcesPackage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultipartResponse {

    private FileMetaDataResponse json;
    private Resource resource;
}
