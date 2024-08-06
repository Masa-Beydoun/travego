package SpringBootStarterProject.Trippackage.Repository;

import SpringBootStarterProject.Trippackage.Models.TripPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripPriceRepository extends JpaRepository<TripPrice, Integer> {
}
