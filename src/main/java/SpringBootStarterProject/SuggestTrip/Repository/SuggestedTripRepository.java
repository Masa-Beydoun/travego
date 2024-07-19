package SpringBootStarterProject.SuggestTrip.Repository;

import SpringBootStarterProject.SuggestTrip.Model.SuggestedTrip;
import SpringBootStarterProject.UserPackage.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuggestedTripRepository extends JpaRepository<SuggestedTrip, Integer> {

    List<SuggestedTrip> findByUser(Client user);
}
