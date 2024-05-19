package SpringBootStarterProject.UserPackage.Repositories;

import SpringBootStarterProject.UserPackage.Models.BaseUser;
import SpringBootStarterProject.UserPackage.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClinetRepository extends JpaRepository<Client,Integer> {
   Optional<BaseUser> findByEmail(String username);
}
