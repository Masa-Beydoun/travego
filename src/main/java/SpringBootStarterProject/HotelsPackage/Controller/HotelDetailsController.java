package SpringBootStarterProject.HotelsPackage.Controller;

import SpringBootStarterProject.HotelsPackage.Service.HotelDetailsService;
import SpringBootStarterProject.HotelsPackage.Request.HotelDetailsRequest;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/hotel_details")
@RequiredArgsConstructor
@Tag(name = "Hotel Details",description = "This controller to get more detailed information about one hotel ")
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
    public ApiResponseClass createHotelDetails(@RequestPart("request") HotelDetailsRequest request,
                                               @RequestPart("file") List<MultipartFile> file) {
        request.setPhotos(file);
        return hotelDetailsService.save(request);
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
    public ApiResponseClass updateHotelDetails(@RequestBody HotelDetailsRequest request) {
        return hotelDetailsService.save(request);
    }

    @GetMapping("by-hotel-id/{hotelId}")
    @Operation(
            description = "This endpoint build to Get a Hotel-Details by Hotel Id in system",
            summary = "Get Hotel-Details",
            responses = {
                    @ApiResponse(
                            description = "Get Hotel-Details successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "hotel details not found",
                            responseCode = "400"
                    )
            }
    )
    public ApiResponseClass getHotelDetails(@PathVariable Integer hotelId) {
        return hotelDetailsService.getHotelDetailsByHotelId(hotelId);
    }


    @GetMapping("{id}")
    @Operation(
            description = "This endpoint build to Get a Hotel-Details by its Id system",
            summary = "Get Hotel-Details",
            responses = {
                    @ApiResponse(
                            description = "Get Hotel-Details successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "hotel details not found",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                    description = "hotel Id not found",
                    responseCode = "400"
            )
            }
    )
    public ApiResponseClass getHotelDetailsById(@PathVariable Integer id) {
        return hotelDetailsService.getHotelDetailsById(id);
    }


    @DeleteMapping("{id}")
    @Operation(
            description = "This endpoint build to Delete a Hotel-Details by Id system",
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
    public ResponseEntity<?> deleteHotelDetails(@PathVariable Integer id) {
        return ResponseEntity.ok(hotelDetailsService.delete(id));
    }


}
