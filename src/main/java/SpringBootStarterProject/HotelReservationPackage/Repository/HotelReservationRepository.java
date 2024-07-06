package SpringBootStarterProject.HotelReservationPackage.Repository;

import SpringBootStarterProject.HotelReservationPackage.Enum.HotelReservationStatus;
import SpringBootStarterProject.HotelReservationPackage.Model.HotelReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelReservationRepository extends JpaRepository<HotelReservation, Integer> {

    Optional<List<HotelReservation>> findAllByHotelIdAndStatusAndClientId(Integer hotelId, String status, Integer userId);

    Optional<List<HotelReservation>> findAllByClientId(Integer userId);


    Optional<List<HotelReservation>> findAllByHotelIdAndStartDateAfterAndEndDateBefore(Integer hotelId, LocalDate startDate, LocalDate endDate);

    Optional<List<HotelReservation>> findByHotelId(Integer id);
}

