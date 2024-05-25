package SpringBootStarterProject.UserPackage.Repositories;

import SpringBootStarterProject.UserPackage.Models.Personalidenty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalidentityRepository extends JpaRepository<Personalidenty,Integer> {
}
