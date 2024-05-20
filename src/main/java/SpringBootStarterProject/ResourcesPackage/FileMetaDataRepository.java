package SpringBootStarterProject.ResourcesPackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileMetaDataRepository extends JpaRepository<FileEntity, Integer> {

    public List<FileMetaDataRepository> findAllByRelationTypeAndRelationId(ResourceType resourceType,Integer relationId);
}
