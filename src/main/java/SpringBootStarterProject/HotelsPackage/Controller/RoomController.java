package SpringBootStarterProject.HotelsPackage.Controller;

import SpringBootStarterProject.HotelsPackage.Models.Room;
import SpringBootStarterProject.HotelsPackage.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/{hotel_id}")
    public ResponseEntity<List<Room>> getAllRoomsByHotelId(@PathVariable Integer hotel_id) {
        return ResponseEntity.ok(roomService.getAllRoomsByHotelId(hotel_id));
    }

    @GetMapping("/{hotel_id}/{room_id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Integer hotel_id, @PathVariable Integer room_id) {
        return ResponseEntity.ok(roomService.getRoomById(hotel_id,room_id));
    }

}
