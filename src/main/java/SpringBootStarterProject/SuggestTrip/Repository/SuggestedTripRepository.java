package SpringBootStarterProject.SuggestTrip.Repository;

import SpringBootStarterProject.SuggestTrip.Model.SuggestedTrip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestedTripRepository extends JpaRepository<SuggestedTrip, Integer> {
}
