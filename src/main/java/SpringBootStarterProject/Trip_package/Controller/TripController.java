package SpringBootStarterProject.Trip_package.Controller;

import SpringBootStarterProject.Trip_package.Models.Trip;
import SpringBootStarterProject.Trip_package.Repository.TripRepository;
import SpringBootStarterProject.Trip_package.Request.TripRequest;
import SpringBootStarterProject.Trip_package.Response.TripResponse;
import SpringBootStarterProject.Trip_package.Service.TripService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/trips")
@RequiredArgsConstructor
@Tag(name = "Trip")
public class TripController {

    private final TripService tripService;

    @GetMapping
    @Operation(
            description = "This endpoint build to Get All trip which is in our system",
            summary = "Get All trips",
            responses = {
                    @ApiResponse(
                            description = "Get all done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<List<TripResponse>> getAllTrips() {
        return ResponseEntity.ok(tripService.getTrips());
    }
    @GetMapping("{id}")
    @Operation(
            description = "This endpoint build to Get trip by id which is in our system",
            summary = "Get trip by id",
            responses = {
                    @ApiResponse(
                            description = "Get trip done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Service not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<TripResponse> getTripById(@PathVariable int id) {
        return ResponseEntity.ok(tripService.getTripById(id));
    }

    @PostMapping
    @Operation(
            description = "This endpoint build to create new trip which is in our system",
            summary = "Create new trip",
            responses = {
                    @ApiResponse(
                            description = "Create trip done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "City not found",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Service not found",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "country not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<TripResponse> createTrip(@RequestBody TripRequest trip) {
        return ResponseEntity.ok(tripService.createTrip(trip));
    }

    @PutMapping("{id}")
    @Operation(
            description = "This endpoint build to edit trip by id which is in our system",
            summary = "Edit trip by id",
            responses = {
                    @ApiResponse(
                            description = "Edit trip done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Id not found",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "City not found",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Service not found",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Price not found",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "country not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<TripResponse> updateTrip(
            @PathVariable Integer id,
            @RequestBody TripRequest trip
    ) {
        return ResponseEntity.ok(tripService.updateTrip(id, trip));
    }

@DeleteMapping("{id}")
@Operation(
        description = "This endpoint build to delete trip by id",
        summary = "Delete trip by id",
        responses = {
                @ApiResponse(
                        description = "Delete done successfully",
                        responseCode = "200"
                ),
                @ApiResponse(
                        description = "trip not found",
                        responseCode = "400"
                )
        }
)
    public ResponseEntity<String> deleteTrip(@PathVariable Integer id) {
        tripService.deleteTrip(id);
        return ResponseEntity.ok("Deleted trip successfully");
    }



}
