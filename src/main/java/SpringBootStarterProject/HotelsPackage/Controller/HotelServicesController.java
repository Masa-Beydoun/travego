package SpringBootStarterProject.HotelsPackage.Controller;


import SpringBootStarterProject.HotelsPackage.Request.HotelServicesRequest;
import SpringBootStarterProject.HotelsPackage.Response.HotelServicesResponse;
import SpringBootStarterProject.HotelsPackage.Service.HotelServicesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/hotel-services")
@RequiredArgsConstructor
@Tag(name = "Hotel Services")
public class HotelServicesController {

    @Autowired
    public final HotelServicesService hotelServicesService;

    @GetMapping
    @Operation(
            description = "This endpoint build to Get All hotel services which is in our system",
            summary = "Get All hotel services",
            responses = {
                    @ApiResponse(
                            description = "Get all done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<List<HotelServicesResponse>> getAllTripServices() {
        return ResponseEntity.ok().body(hotelServicesService.getAllHotelServices());
    }

    @GetMapping("/{id}")
    @Operation(
            description = "This endpoint build to Get hotel service by id which is in our system",
            summary = "Get hotel service by id",
            responses = {
                    @ApiResponse(
                            description = "Get hotel service done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Hotel Service not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<HotelServicesResponse> getTripServiceById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(hotelServicesService.getHotelServiceById(id));
    }

    @PostMapping
    @Operation(
            description = "This endpoint build to create new hotel service which is in our system",
            summary = "Create new hotel service",
            responses = {
                    @ApiResponse(
                            description = "Create hotel service done successfully",
                            responseCode = "200"
                    ),
            }
    )
    public ResponseEntity<HotelServicesResponse> createTripService(@RequestBody HotelServicesRequest request) {
        return ResponseEntity.ok().body(hotelServicesService.createHotelService(request));
    }
    @PutMapping("/{id}")
    @Operation(
            description = "This endpoint build to edit hotel service by id which is in our system",
            summary = "Edit hotel service by id",
            responses = {
                    @ApiResponse(
                            description = "Edit hotel service done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<HotelServicesResponse> updateTripService(@PathVariable Integer id, @RequestBody HotelServicesRequest request) {
        return ResponseEntity.ok().body(hotelServicesService.updateHotelService(request, id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            description = "This endpoint build to delete hotel service by id which is in our system",
            summary = "Delete hotel service by id",
            responses = {
                    @ApiResponse(
                            description = "Delete hotel service done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Service not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<String> deleteHotelService(@PathVariable Integer id) {
        hotelServicesService.deleteHotelService(id);
        return ResponseEntity.ok().body("Hotel service Deleted successfully");
    }
}
