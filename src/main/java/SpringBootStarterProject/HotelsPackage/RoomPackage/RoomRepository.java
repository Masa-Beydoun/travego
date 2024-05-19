package SpringBootStarterProject.HotelsPackage.RoomPackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findAllByHotelId(Integer hotelId);
    Room findAllByHotelIdAndId(Integer hotelId, Integer roomId);

}
