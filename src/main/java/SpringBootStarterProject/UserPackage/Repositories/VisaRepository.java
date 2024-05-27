package SpringBootStarterProject.UserPackage.Repositories;

import SpringBootStarterProject.UserPackage.Models.Personalidenty;
import SpringBootStarterProject.UserPackage.Models.RelationshipType;
import SpringBootStarterProject.UserPackage.Models.Visa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisaRepository extends JpaRepository<Visa,Integer> {
   Optional<Visa> getVisaByRelationshipIdAndType(Integer id, RelationshipType type);
}
