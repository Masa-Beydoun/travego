package SpringBootStarterProject.Resources;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {

    public List<Resource> findAllByResourceTypeAndRelationId(ResourceType resourceType,Integer relationId);
}
