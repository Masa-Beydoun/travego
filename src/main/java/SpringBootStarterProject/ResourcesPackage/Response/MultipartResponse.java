package SpringBootStarterProject.ResourcesPackage.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultipartResponse {

    private FileMetaDataResponse json;
//    private Resource resource;
    private String resource;

}

