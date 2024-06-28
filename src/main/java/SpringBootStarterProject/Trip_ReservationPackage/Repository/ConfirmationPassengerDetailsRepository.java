package SpringBootStarterProject.Trip_ReservationPackage.Repository;

import SpringBootStarterProject.Trip_ReservationPackage.Models.ConfirmationPassengersDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationPassengerDetailsRepository extends JpaRepository<ConfirmationPassengersDetails,Integer> {

    @Query("""
SELECT c FROM ConfirmationPassengersDetails c
WHERE c.tripReservation.trip_id =: Trip_Id
""")
    Page<ConfirmationPassengersDetails> getAllPassengerDetailsRequestByTripId(Integer Trip_Id, Pageable pageable);
}
