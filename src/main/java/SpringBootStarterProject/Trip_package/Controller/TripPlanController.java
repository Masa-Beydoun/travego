package SpringBootStarterProject.Trip_package.Controller;

import SpringBootStarterProject.Trip_package.Models.TripPlan;
import SpringBootStarterProject.Trip_package.Request.TripPlanRequest;
import SpringBootStarterProject.Trip_package.Response.TripPlanResponse;
import SpringBootStarterProject.Trip_package.Service.TripPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/trip-plans")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "Trip-plan")
public class TripPlanController {

    private final TripPlanService tripPlanService;

    @GetMapping
    @Operation(
            description = "This endpoint build to Get All trip-plans which is in our system",
            summary = "Get All trip-plans",
            responses = {
                    @ApiResponse(
                            description = "Get all done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> getAllTripPlans(){
        return ResponseEntity.ok().body(tripPlanService.getAllTripPlans());
    }
    @GetMapping("{id}")
    @Operation(
            description = "This endpoint build to Get trip-plan by id which is in our system",
            summary = "Get trip-plan by id",
            responses = {
                    @ApiResponse(
                            description = "Get trip-plan done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Service not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> getTripPlanById(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(tripPlanService.getTripPlanById(id));
    }


    @GetMapping("plans-of-trip/{trip_id}")
    @Operation(
            description = "This endpoint build to Get trip-plan by trip-id which is in our system",
            summary = "Get trip-plan by trip-id",
            responses = {
                    @ApiResponse(
                            description = "Get trip-plans done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "trip not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> getTripPlanByTripId(
            @PathVariable("trip_id") Integer trip_id
    ){
        return ResponseEntity.ok().body(tripPlanService.getTripPlanByTripId(trip_id));
    }

    @PostMapping
    @Operation(
            description = "This endpoint build to create new trip-plan which is in our system",
            summary = "Create new trip-plan",
            responses = {
                    @ApiResponse(
                            description = "Create trip-service done successfully",
                            responseCode = "200"
                    ),
            }
    )
    public ResponseEntity<?> createTripPlan(@RequestBody TripPlanRequest tripPlanRequest){
        return ResponseEntity.ok().body(tripPlanService.createTripPlan(tripPlanRequest));
    }

    @PutMapping("{id}")
    @Operation(
            description = "This endpoint build to edit trip-plan by id which is in our system",
            summary = "Edit trip-plan by id",
            responses = {
                    @ApiResponse(
                            description = "Edit trip-service done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> updateTripPlan(@RequestBody TripPlanRequest tripPlanRequest , @PathVariable("id") Integer id){
        return ResponseEntity.ok().body(tripPlanService.updateTripPlan(tripPlanRequest , id));
    }
    @DeleteMapping("{id}")
    @Operation(
            description = "This endpoint build to delete trip-plan by id",
            summary = "Delete trip-plan by id",
            responses = {
                    @ApiResponse(
                            description = "Delete done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "trip-plan not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> deleteTripPlan(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(tripPlanService.deleteTripPlan(id));
    }
}
