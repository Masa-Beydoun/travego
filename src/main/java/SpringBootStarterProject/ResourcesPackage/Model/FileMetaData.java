package SpringBootStarterProject.ResourcesPackage.Model;

import SpringBootStarterProject.ResourcesPackage.Enum.ResourceType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileMetaData {

    @Id
    @SequenceGenerator(
            name = "file_id",
            sequenceName = "file_id",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "file_id"
    )
    private Integer id;
    private String fileName;
    private String filePath;
    private String fileType;
    private long fileSize;

    private ResourceType relationType;
    private Integer relationId;


}
