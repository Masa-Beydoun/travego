package SpringBootStarterProject.Trip_package.Repository;

import SpringBootStarterProject.Trip_package.Models.TripServices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripServiceRepository extends JpaRepository<TripServices, Integer> {
}
