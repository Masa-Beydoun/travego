package SpringBootStarterProject.Trip_package.Repository;

import SpringBootStarterProject.Trip_package.Models.TripPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripPlanRepository extends JpaRepository<TripPlan, Integer> {
}
