package SpringBootStarterProject.HotelsPackage.Controller;

import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.HotelsPackage.Request.HotelRequest;
import SpringBootStarterProject.HotelsPackage.Response.HotelDetailsResponse;
import SpringBootStarterProject.HotelsPackage.Service.HotelDetailsService;
import SpringBootStarterProject.HotelsPackage.Request.HotelDetailsRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/hotel_details")
@RequiredArgsConstructor
@Tag(name = "Hotel-Details")
public class HotelDetailsController {
    private final HotelDetailsService hotelDetailsService;



    @PostMapping(consumes = "multipart/form-data")
    @Operation(
            description = "This endpoint build to Save Hotel-Details in our system",
            summary = "Get All hotels by city id",
            responses = {
                    @ApiResponse(
                            description = "Get all done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "hotel id not found",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "hotel-service not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<HotelDetailsResponse> createHotelDetails(@RequestPart("request") HotelDetailsRequest request,
                                                                   @RequestPart("file") List<MultipartFile> file) {
        request.setPhotos(file);
        return ResponseEntity.ok(hotelDetailsService.save(request));
    }

    @PutMapping
    @Operation(
            description = "This endpoint build to Update a Hotel-Details in our system",
            summary = "Update Hotel-Details",
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
    public ResponseEntity<HotelDetailsResponse> updateHotelDetails(@RequestBody HotelDetailsRequest request) {
        return ResponseEntity.ok(hotelDetailsService.save(request));
    }

    @GetMapping("{id}")
    @Operation(
            description = "This endpoint build to Get a Hotel-Details by Id system",
            summary = "Delete Hotel-Details",
            responses = {
                    @ApiResponse(
                            description = "deleted successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "hotel details not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<HotelDetailsResponse> getHotelDetails(@PathVariable Integer id) {
        return ResponseEntity.ok(hotelDetailsService.getHotelDetailsById(id));
    }



}
