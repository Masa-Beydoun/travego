package SpringBootStarterProject.UserPackage.Repositories;

import SpringBootStarterProject.UserPackage.Models.Personalidenty;
import SpringBootStarterProject.UserPackage.Models.Visa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisaRepository extends JpaRepository<Visa,Integer> {
}
