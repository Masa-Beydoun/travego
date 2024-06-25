package SpringBootStarterProject.Trip_ReservationPackage.Repository;

import SpringBootStarterProject.Trip_ReservationPackage.Models.ConfirmationPassengersDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationPassengerDetailsRepository extends JpaRepository<ConfirmationPassengersDetails,Integer> {

}
