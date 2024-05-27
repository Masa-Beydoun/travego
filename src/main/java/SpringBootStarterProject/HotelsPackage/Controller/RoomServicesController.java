package SpringBootStarterProject.HotelsPackage.Controller;


import SpringBootStarterProject.HotelsPackage.Request.RoomServicesRequest;
import SpringBootStarterProject.HotelsPackage.Response.RoomServicesResponse;
import SpringBootStarterProject.HotelsPackage.Service.RoomServicesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/room-services")
@RequiredArgsConstructor
@Tag(name = "Room Services")
public class RoomServicesController {

    public final RoomServicesService roomServicesService;

    @GetMapping
    @Operation(
            description = "This endpoint build to Get All room services which is in our system",
            summary = "Get All room services",
            responses = {
                    @ApiResponse(
                            description = "Get all done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<List<RoomServicesResponse>> getAllRoomServices() {
        return ResponseEntity.ok().body(roomServicesService.getAllRoomServices());
    }

    @GetMapping("/{id}")
    @Operation(
            description = "This endpoint build to Get room service by id which is in our system",
            summary = "Get room service by id",
            responses = {
                    @ApiResponse(
                            description = "Get room-service done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Service not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<RoomServicesResponse> getRooServiceById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(roomServicesService.getRoomServiceById(id));
    }
    @PostMapping
    @Operation(
            description = "This endpoint build to create new room service which is in our system",
            summary = "Create new room service",
            responses = {
                    @ApiResponse(
                            description = "Create room service done successfully",
                            responseCode = "200"
                    ),
            }
    )
    public ResponseEntity<RoomServicesResponse> createTripService(@RequestBody RoomServicesRequest request) {
        return ResponseEntity.ok().body(roomServicesService.createRoomService(request));
    }
    @PutMapping("/{id}")
    @Operation(
            description = "This endpoint build to edit room service by id which is in our system",
            summary = "Edit room service by id",
            responses = {
                    @ApiResponse(
                            description = "Edit room service done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<RoomServicesResponse> updateRoomService(@PathVariable Integer id, @RequestBody RoomServicesRequest request) {
        return ResponseEntity.ok().body(roomServicesService.updateRoomService(request, id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            description = "This endpoint build to delete room service by id which is in our system",
            summary = "Delete room service by id",
            responses = {
                    @ApiResponse(
                            description = "Delete room service done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Service not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<String> deleteRoomService(@PathVariable Integer id) {
        roomServicesService.deleteRoomService(id);
        return ResponseEntity.ok().body("Deleted room service successfully");
    }
}
