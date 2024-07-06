package SpringBootStarterProject.HotelsPackage.Controller;

import SpringBootStarterProject.HotelsPackage.Models.Room;
import SpringBootStarterProject.HotelsPackage.Request.RoomRequest;
import SpringBootStarterProject.HotelsPackage.Response.RoomResponse;
import SpringBootStarterProject.HotelsPackage.Service.RoomService;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rooms")
@RequiredArgsConstructor
@Tag(name = "Room")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/{hotel_details_id}")
    @Operation(
            description = "This endpoint build to Get All Rooms by Hotel details Id in our system",
            summary = "Get All Rooms by Hotel Id by city id",
            responses = {
                    @ApiResponse(
                            description = "Get all done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "hotel details id not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> getAllRoomsByHotelId(@PathVariable Integer hotel_details_id) {
        return ResponseEntity.ok(roomService.getAllRoomsByHotelId(hotel_details_id));
    }

//    @GetMapping("/{room_id}")
//    public ResponseEntity<Room> getRoomById(@PathVariable Integer room_id) {
//        return ResponseEntity.ok(roomService.getRoomById(,room_id));
//    }


    @PostMapping
    @Operation(
            description = "This endpoint build to Save a type of Rooms in a Hotel-details in our system",
            summary = "Save a Room in a Hotel ",
            responses = {
                    @ApiResponse(
                            description = "Get all done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Hotel id not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> createRoom(@RequestBody RoomRequest roomRequest) {
        return ResponseEntity.ok(roomService.save(roomRequest));
    }


    @GetMapping("{id}")
    public ResponseEntity<?> getRoomById(@PathVariable Integer id) {
        return ResponseEntity.ok(roomService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(
            description = "This endpoint build to Update a type of Rooms in a Hotel-details in a City in our system",
            summary = "Save All hotels by city id",
            responses = {
                    @ApiResponse(
                            description = "Updated successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Room id not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> updateRoom(@PathVariable Integer id,@RequestBody RoomRequest roomRequest) {
        return ResponseEntity.ok(roomService.update(id,roomRequest));
    }


    @DeleteMapping("/{id}")
    @Operation(
            description = "This endpoint build to Delete a type of Rooms in a Hotel-details in a City in our system",
            summary = "Save All hotels by city id",
            responses = {
                    @ApiResponse(
                            description = "Deleted successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Room id not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> deleteRoom(@PathVariable Integer id) {
        return ResponseEntity.ok(roomService.delete(id));
    }





}
