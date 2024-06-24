package SpringBootStarterProject.ResourcesPackage.Repository;

import SpringBootStarterProject.ResourcesPackage.Model.FileMetaData;
import SpringBootStarterProject.ResourcesPackage.Enum.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileMetaDataRepository extends JpaRepository<FileMetaData, Integer> {

    public Optional<List<FileMetaDataRepository>> findAllByRelationTypeAndRelationId(ResourceType resourceType, Integer relationId);

    public Optional<FileMetaData> findByFileName(String name);


}
