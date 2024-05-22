package SpringBootStarterProject.HotelsPackage.Repository;

import SpringBootStarterProject.HotelsPackage.Models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByHotelDetailsId(Integer hotelDetailsId);
    Room findAllByHotelDetailsIdAndId(Integer hotelDetailsId, Integer roomId);

}
