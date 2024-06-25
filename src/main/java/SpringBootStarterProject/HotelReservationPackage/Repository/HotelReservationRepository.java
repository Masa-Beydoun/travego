package SpringBootStarterProject.HotelReservationPackage.Repository;

import SpringBootStarterProject.HotelReservationPackage.Enum.HotelReservationStatus;
import SpringBootStarterProject.HotelReservationPackage.Model.HotelReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelReservationRepository extends JpaRepository<HotelReservation, Integer> {

    public List<HotelReservation> findAllByHotelIdAndStatus(Integer hotelId, String status);
}

