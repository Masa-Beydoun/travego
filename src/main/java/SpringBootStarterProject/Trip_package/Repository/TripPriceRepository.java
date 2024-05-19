package SpringBootStarterProject.Trip_package.Repository;

import SpringBootStarterProject.Trip_package.Models.TripPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripPriceRepository extends JpaRepository<TripPrice, Integer> {
}
