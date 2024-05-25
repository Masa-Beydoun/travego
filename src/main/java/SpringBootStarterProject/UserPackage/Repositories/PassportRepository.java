package SpringBootStarterProject.UserPackage.Repositories;

import SpringBootStarterProject.UserPackage.Models.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassportRepository extends JpaRepository<Passport,Integer> {
}
