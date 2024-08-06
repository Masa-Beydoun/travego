package SpringBootStarterProject.Trippackage.Repository;

import SpringBootStarterProject.Trippackage.Models.TripServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TripServicesRepository extends JpaRepository<TripServices, Integer> {

    public Optional<TripServices> findByName(String name);
}
