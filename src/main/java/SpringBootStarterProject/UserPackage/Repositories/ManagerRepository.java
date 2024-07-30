package SpringBootStarterProject.UserPackage.Repositories;

import SpringBootStarterProject.UserPackage.Models.Manager;
import SpringBootStarterProject.UserPackage.RolesAndPermission.Roles;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager,Integer>
{

    Optional<Manager> findByEmail(String email);

    List<Manager> findByRole(Roles role, Pageable pageable);
}
