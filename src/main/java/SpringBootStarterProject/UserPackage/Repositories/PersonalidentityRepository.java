package SpringBootStarterProject.UserPackage.Repositories;

import SpringBootStarterProject.UserPackage.Models.Personalidenty;
import SpringBootStarterProject.UserPackage.Models.RelationshipType;
import SpringBootStarterProject.UserPackage.Models.Visa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonalidentityRepository extends JpaRepository<Personalidenty,Integer> {
    Optional<Personalidenty> getPersonalidentyByRelationshipIdAndType(Integer id, RelationshipType type);

}
