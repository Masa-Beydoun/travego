package SpringBootStarterProject.HotelReservationPackage.Repository;

import SpringBootStarterProject.HotelReservationPackage.Model.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Integer> {


    public Optional<List<RoomReservation>> findAllByHotelReservationId(Integer reservationId);
}
