package SpringBootStarterProject.HotelsPackage.RoomPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;



    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getAllRoomsByHotelId(Integer hotelId) {
        return roomRepository.findAllByHotelId(hotelId);

    }

    public Room getRoomById(Integer hotelId, Integer roomId) {
        return roomRepository.findAllByHotelIdAndRoomId(hotelId,roomId);
    }
}
