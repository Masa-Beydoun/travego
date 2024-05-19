package SpringBootStarterProject.City_package.Controller;

import SpringBootStarterProject.City_package.Response.CityResponse;
import SpringBootStarterProject.City_package.Request.CreateCityRequest;
import SpringBootStarterProject.City_package.Request.GetCityByIdRequest;
import SpringBootStarterProject.City_package.Service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;


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
    public ResponseEntity<List<CityResponse>>  getAllCities() {
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
    public ResponseEntity<CityResponse> getCityById(@PathVariable Integer id) {
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
    public ResponseEntity<List<CityResponse>> getAllCitiesByCountry(@PathVariable String country) {
        return ResponseEntity.ok().body(cityService.getCitiesByCountry(country));
    }


    @PostMapping
    @Operation(
            description = "This endpoint build to add new city to our system by admins",
            summary = "Get city by id",
            responses = {
                    @ApiResponse(
                            description = "Get City done successfully",
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
    public ResponseEntity<CityResponse> createCity(@RequestBody CreateCityRequest request) {
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
    public ResponseEntity<CityResponse> updateCity(CreateCityRequest request, @PathVariable Integer id) {
        return ResponseEntity.ok().body(cityService.EditCity(request,id));
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
    public ResponseEntity<String> deleteCity(@PathVariable Integer id) {
        return ResponseEntity.ok().body(cityService.DeleteCity(id));
    }





}
