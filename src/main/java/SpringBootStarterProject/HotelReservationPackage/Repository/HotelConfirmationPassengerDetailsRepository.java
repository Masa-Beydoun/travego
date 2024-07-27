package SpringBootStarterProject.HotelReservationPackage.Repository;

import SpringBootStarterProject.HotelReservationPackage.Model.HotelConfirmationPassengersDetails;
import SpringBootStarterProject.Trip_ReservationPackage.Models.ConfirmationPassengersDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelConfirmationPassengerDetailsRepository extends JpaRepository<HotelConfirmationPassengersDetails,Integer> {

    @Query("""
SELECT c FROM HotelConfirmationPassengersDetails c
WHERE c.HotelReservation.hotel.id =: hotelId
""")
    Page<ConfirmationPassengersDetails> getAllPassengerDetailsRequestByHotelId(Integer hotelId, Pageable pageable);
}
