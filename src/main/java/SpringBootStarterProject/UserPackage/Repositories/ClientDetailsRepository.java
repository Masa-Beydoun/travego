package SpringBootStarterProject.UserPackage.Repositories;

import SpringBootStarterProject.UserPackage.Models.ClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDetailsRepository extends JpaRepository<ClientDetails,Integer> {
}
