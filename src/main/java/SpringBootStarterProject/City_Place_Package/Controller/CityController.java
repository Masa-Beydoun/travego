package SpringBootStarterProject.City_Place_Package.Controller;

import SpringBootStarterProject.City_Place_Package.Response.CityResponse;
import SpringBootStarterProject.City_Place_Package.Request.CreateCityRequest;
import SpringBootStarterProject.City_Place_Package.Service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cities")
@CrossOrigin(origins ="http://localhost:61225")

@RequiredArgsConstructor
@Tag(name = "City")
public class CityController {

    private final CityService cityService;

    @CrossOrigin(origins = "http://localhost:61225")
    @GetMapping
    @Operation(
            description = "This endpoint build to get all cities which is in our system",
            summary = "Get All cities",
            responses = {
                    @ApiResponse(
                            description = "Get all cities done successfully",
                            responseCode = "200",
                            content = @Content()
                    ),
            }
    )
    public ResponseEntity<?>  getAllCities() {
        return ResponseEntity.ok().body(cityService.GetALlCities());
    }

    @GetMapping("{id}")
    @Operation(
            description = "This endpoint build to get city details by id",
            summary = "Get city by id",
            responses = {
                    @ApiResponse(
                            description = "Get City done successfully",
                            responseCode = "200",
                            content = @Content()
                    ),
                    @ApiResponse(
                            description = "Id not found",
                            responseCode = "400"

                    )
            }
    )
    public ResponseEntity<?> getCityById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(cityService.findById(id));
    }

    @GetMapping("cities-by-country/{country}")
    @Operation(
            description = "This endpoint build to get city details by country name",
            summary = "Get city by country name",
            responses = {
                    @ApiResponse(
                            description = "Get City done successfully",
                            responseCode = "200",
                            content = @Content()
                    ),
                    @ApiResponse(
                            description = "country not found",
                            responseCode = "400"

                    )
            }
    )
    public ResponseEntity<?> getAllCitiesByCountry(@PathVariable String country) {
        return ResponseEntity.ok().body(cityService.getCitiesByCountry(country));
    }


    @PostMapping
    @Operation(
            description = "This endpoint build to add new city to our system by admins",
            summary = "Add new city to our system",
            responses = {
                    @ApiResponse(
                            description = "Add new City done successfully",
                            responseCode = "200",
                            content = @Content()
                    ),
                    @ApiResponse(
                            description = "country not exist",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "city name must not be null",
                            responseCode = "400"
                    ),
            }
    )
    public ResponseEntity<?> createCity(@RequestBody CreateCityRequest request) {
        return ResponseEntity.ok().body(cityService.createCity(request));
    }

    @PutMapping("{id}")
        @Operation(
                description = "This endpoint build to edit city details by id",
                summary = "Edit city by id",
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
                        )
                }
        )
    public ResponseEntity<?> updateCity(CreateCityRequest request, @PathVariable Integer id) {
        return ResponseEntity.ok().body(cityService.updateCity(request,id));
    }

    @DeleteMapping("{id}")
    @Operation(
            description = "This endpoint build to delete city details by id",
            summary = "Delete city by id",
            responses = {
                    @ApiResponse(
                            description = "Delete City done successfully",
                            responseCode = "200",
                            content = @Content()
                    ),
                    @ApiResponse(
                            description = "city not exist   ",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Id not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> deleteCity(@PathVariable Integer id) {
        return ResponseEntity.ok().body(cityService.DeleteCity(id));
    }





}
