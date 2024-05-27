package SpringBootStarterProject.UserPackage.Repositories;

import SpringBootStarterProject.UserPackage.Models.Passport;
import SpringBootStarterProject.UserPackage.Models.RelationshipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassportRepository extends JpaRepository<Passport,Integer> {
  Optional< Passport> getPassportByRelationshipIdAndType(Integer id, RelationshipType type);
}
