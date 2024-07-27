package SpringBootStarterProject.Trip_package.Controller;

import SpringBootStarterProject.Trip_package.Models.TripServices;
//import SpringBootStarterProject.Trip_package.Request.GetTripServicesByIdRequest;
import SpringBootStarterProject.Trip_package.Request.TripServicesRequest;
import SpringBootStarterProject.Trip_package.Response.TripServicesResponse;
import SpringBootStarterProject.Trip_package.Service.TripServicesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/trip-services")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "Trip-Services")
public class TripServicesController {

    @Autowired
    public final TripServicesService tripServicesService;

    @GetMapping
    @Operation(
            description = "This endpoint build to Get All trip-services which is in our system",
            summary = "Get All trip-services",
            responses = {
                    @ApiResponse(
                            description = "Get all done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> getAllTripServices() {
        return ResponseEntity.ok().body(tripServicesService.getAllTripServices());
    }

    @GetMapping("{id}")
    @Operation(
            description = "This endpoint build to Get trip-service by id which is in our system",
            summary = "Get trip-service by id",
            responses = {
                    @ApiResponse(
                            description = "Get trip-service done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Service not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> getTripServiceById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(tripServicesService.getTripServiceById(id));
    }
    @PostMapping
    @Operation(
            description = "This endpoint build to create new trip-service which is in our system",
            summary = "Create new trip-service",
            responses = {
                    @ApiResponse(
                            description = "Create trip-service done successfully",
                            responseCode = "200"
                    ),
            }
    )
    public ResponseEntity<?> createTripService(@RequestBody TripServicesRequest request) {
        return ResponseEntity.ok().body(tripServicesService.createTripService(request));
    }
    @PutMapping("{id}")
    @Operation(
            description = "This endpoint build to edit trip-service by id which is in our system",
            summary = "Edit trip-service by id",
            responses = {
                    @ApiResponse(
                            description = "Edit trip-service done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> updateTripService(@PathVariable Integer id, @RequestBody TripServicesRequest request) {
        return ResponseEntity.ok().body(tripServicesService.updateTripService(request, id));
    }

    @DeleteMapping("{id}")
    @Operation(
            description = "This endpoint build to delete trip-service by id which is in our system",
            summary = "Delete trip-service by id",
            responses = {
                    @ApiResponse(
                            description = "Delete trip-service done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Service not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> deleteTripService(@PathVariable Integer id) {

        return ResponseEntity.ok().body(tripServicesService.deleteTripService(id));
    }
}
