package SpringBootStarterProject.City_Place_Package.Controller;

import SpringBootStarterProject.City_Place_Package.Models.Place;
import SpringBootStarterProject.City_Place_Package.Request.PlaceRequest;
import SpringBootStarterProject.City_Place_Package.Response.PlaceResponse;
import SpringBootStarterProject.City_Place_Package.Service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/places")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "Place")
public class PlaceController {


    private final PlaceService placeService;

    @GetMapping
    @Operation(
            description = "This endpoint build to get all places in our system",
            summary = "Get all places",
            responses = {
                    @ApiResponse(
                            description = "Get All places done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> getAllPlaces() {
        return ResponseEntity.ok().body(placeService.getAllPlaces());
    }

    @GetMapping("{id}")
    @Operation(
            description = "This endpoint build to get place by id in our system by admins",
            summary = "Get places by id",
            responses = {
                    @ApiResponse(
                            description = "get place by id done successfully",
                            responseCode = "200",
                            content = @Content()
                    ),
                    @ApiResponse(
                            description = "city not exist",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> getPlaceById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(placeService.getPlaceById(id));
    }

    @GetMapping("places-by-city/{id}")
    @Operation(
            description = "This endpoint build to add get places in some city to our system by admins",
            summary = "Get places by city id",
            responses = {
                    @ApiResponse(
                            description = "Add new place done successfully",
                            responseCode = "200",
                            content = @Content()
                    ),
                    @ApiResponse(
                            description = "city not exist",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> getPlacesByCity(@PathVariable Integer id) {
        return ResponseEntity.ok().body(placeService.getPlacesByCity(id));
    }

    @PostMapping
    @Operation(
            description = "This endpoint build to Add new place to our system",
            summary = "Add new place",
//            hidden = true,
            responses = {
                    @ApiResponse(
                            description = "Add new place done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "country not found",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "city not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> addPlace(@RequestBody PlaceRequest place) {
        return ResponseEntity.ok().body(placeService.createPlace(place));
    }

    @PutMapping("{id}")
    @Operation(
            description = "This endpoint build to edit place details by id",
            summary = "Edit place by id",
            responses = {
                    @ApiResponse(
                            description = "Edit City details done successfully",
                            responseCode = "200",
                            content = @Content()
                    ),
                    @ApiResponse(
                            description = "Id not found",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "city name must not be null",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "country not exist",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "location not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> updatePlace(@PathVariable Integer id, @RequestBody PlaceRequest place) {
        return ResponseEntity.ok().body(placeService.updatePlace(id, place));
    }

    @DeleteMapping("{id}")
    @Operation(
            description = "This endpoint build to delete place by id",
            summary = "Delete place by id",
            responses = {
                    @ApiResponse(
                            description = "Delete done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "place not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> deletePlace(@PathVariable Integer id) {
        return ResponseEntity.ok().body(placeService.deletePlace(id));
    }
}
