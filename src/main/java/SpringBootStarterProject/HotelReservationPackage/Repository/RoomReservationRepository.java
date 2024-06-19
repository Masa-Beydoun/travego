package SpringBootStarterProject.HotelReservationPackage.Repository;

import SpringBootStarterProject.HotelReservationPackage.Model.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Integer> {
}
