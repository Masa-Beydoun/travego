package SpringBootStarterProject.Trip_package.Repository;

import SpringBootStarterProject.Trip_package.Models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Integer> {
}
