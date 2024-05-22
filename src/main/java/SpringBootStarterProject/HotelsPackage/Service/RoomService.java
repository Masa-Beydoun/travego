package SpringBootStarterProject.HotelsPackage.Service;

import SpringBootStarterProject.HotelsPackage.Models.Room;
import SpringBootStarterProject.HotelsPackage.Repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;



    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getAllRoomsByHotelId(Integer hotelDetailsId) {
        return roomRepository.findAllByHotelDetailsId(hotelDetailsId);

    }

    //TODO : hotel id or hotel details id
    public Room getRoomById(Integer hotelId, Integer roomId) {
        return roomRepository.findAllByHotelDetailsIdAndId(hotelId,roomId);
    }

//    public Room save(RoomRequest room) {}
}
