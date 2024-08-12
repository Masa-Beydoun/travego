package SpringBootStarterProject.TripReservationPackage.Repository;

import SpringBootStarterProject.TripReservationPackage.Models.ConfirmationPassengersDetails;
import SpringBootStarterProject.TripReservationPackage.Models.ConfirmationPassengersDetails;
import SpringBootStarterProject.TripReservationPackage.Models.ConfirmationPassengersDetailsDto;
import SpringBootStarterProject.TripReservationPackage.Models.PassengerDetails;
import SpringBootStarterProject.TripReservationPackage.Models.TripReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfirmationPassengerDetailsRepository extends JpaRepository<ConfirmationPassengersDetails,Integer> {

    @Query("""
SELECT c FROM ConfirmationPassengersDetails c
JOIN c.tripReservation tr
JOIN tr.trip t
WHERE t.id=:tripId
""")
   List <ConfirmationPassengersDetails> GetTripReservationRelatedToTripId(@Param("tripId") Integer tripId);


}
