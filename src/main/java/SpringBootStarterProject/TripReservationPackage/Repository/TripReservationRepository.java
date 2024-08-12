package SpringBootStarterProject.TripReservationPackage.Repository;

import SpringBootStarterProject.TripReservationPackage.Models.TripReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.lang.Integer;

import java.util.List;

@Repository
public interface TripReservationRepository extends JpaRepository<TripReservation,Integer > {
}
