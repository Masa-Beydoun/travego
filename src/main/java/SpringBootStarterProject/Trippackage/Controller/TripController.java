package SpringBootStarterProject.Trippackage.Controller;

import SpringBootStarterProject.Trippackage.Request.FilterTripByCategoryRequest;
import SpringBootStarterProject.Trippackage.Request.TripRequest;
import SpringBootStarterProject.Trippackage.Service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/trips")
@CrossOrigin("*")
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
    public ResponseEntity<?> getAllTrips() {
        return ResponseEntity.ok(tripService.getTripsForClient());
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
    public ResponseEntity<?> getTripById(@PathVariable int id) {
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
    public ResponseEntity<?> createTrip(@RequestBody TripRequest trip) {
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
    public ResponseEntity<?> updateTrip(
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
    public ResponseEntity<?> deleteTrip(@PathVariable Integer id) {
        return ResponseEntity.ok(tripService.deleteTrip(id));
    }

    @Operation(
            description = "This endpoint build to Get All trips by category which is in our system",
            summary = "Get All trips by category",
            responses = {
                    @ApiResponse(
                            description = "Get all done successfully",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/by-category")
    public ResponseEntity<?> getTripsByCategory(FilterTripByCategoryRequest request) {
        return ResponseEntity.ok(tripService.GetByCategory(request));

    }



}
