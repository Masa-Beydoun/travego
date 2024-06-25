package SpringBootStarterProject.Trip_ReservationPackage.Repository;

import SpringBootStarterProject.Trip_ReservationPackage.Models.TripReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripReservationRepository extends JpaRepository<TripReservation,Integer > {
}
