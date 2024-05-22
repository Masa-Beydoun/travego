package SpringBootStarterProject.HotelsPackage.Controller;


import SpringBootStarterProject.HotelsPackage.Response.HotelResponse;
import SpringBootStarterProject.HotelsPackage.Service.HotelService;
import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Request.HotelRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/hotel")
@RequiredArgsConstructor
public class HotelController {

    @Autowired
    private final HotelService hotelService;

    @GetMapping("/byCityId/{city_id}")
    @Operation(
            description = "This endpoint build to Get All hotels in a City in our system",
            summary = "Get All hotels by city id",
            responses = {
                    @ApiResponse(
                            description = "Get all done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "city id not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<List<HotelResponse>> getAllHotelsByCityId(@PathVariable Integer city_id) {
        return ResponseEntity.ok(hotelService.findHotelByCityId(city_id));
    }

    @GetMapping("/byCountryId/{country_id}")
    @Operation(
            description = "This endpoint build to Get All hotels in a Country in our system",
            summary = "Get All hotels by country id",
            responses = {
                    @ApiResponse(
                            description = "Get all done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "country id not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<List<HotelResponse>> getAllHotelsByCountryId(@PathVariable Integer country_id) {
        return ResponseEntity.ok(hotelService.findHotelByCountryId(country_id));
    }


    @GetMapping
    @Operation(
            description = "This endpoint build to Get All hotels in our system",
            summary = "Get All hotels",
            responses = {
                    @ApiResponse(
                            description = "Get all done successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<List<HotelResponse>> getAllHotels() {
        return ResponseEntity.ok(hotelService.findAllHotels());
    }

    @GetMapping("/{id}")
    @Operation(
            description = "This endpoint build to Get one hotel in our system",
            summary = "Get one hotel by its id",
            responses = {
                    @ApiResponse(
                            description = "Get one hotel done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Hotel Id not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<HotelResponse> getHotelById(@PathVariable Integer id) {
        return ResponseEntity.ok(hotelService.findHotelById(id));
    }

    @PostMapping
    @Operation(
            description = "This endpoint build to save a hotel in a City in our system",
            summary = "save a hotel",
            responses = {
                    @ApiResponse(
                            description = "Get all done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "city id not found",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "country id not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<HotelResponse> createHotel(@RequestBody HotelRequest request) {
        return ResponseEntity.ok(hotelService.save(request));
    }


    @PutMapping
    @Operation(
            description = "This endpoint build to update a hotel in our system",
            summary = "update a hotel",
            responses = {
                    @ApiResponse(
                            description = "Updated successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "city id not found",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "country id not found",
                            responseCode = "400"
                    )
                    ,
                    @ApiResponse(
                            description = "hotel not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<HotelResponse> updateHotel(@RequestBody HotelRequest hotel) {
        return ResponseEntity.ok(hotelService.save(hotel));
    }

    @DeleteMapping
    @Operation(
            description = "This endpoint build to delete one hotel in our system",
            summary = "delete one hotel",
            responses = {
                    @ApiResponse(
                            description = "deleted successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "hotel id not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<Void> deleteHotel(@RequestBody Hotel hotel) {
        hotelService.delete(hotel);
        return ResponseEntity.noContent().build();
    }



}
