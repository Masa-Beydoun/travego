package SpringBootStarterProject.Trip_package.Repository;

import SpringBootStarterProject.Trip_package.Models.TripPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripPlanRepository extends JpaRepository<TripPlan, Integer> {

    public List<TripPlan> findByTripIdOrderByStartDate(Integer tripId);
}
