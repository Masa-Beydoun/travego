package SpringBootStarterProject.HotelsPackage.Controller;


import SpringBootStarterProject.HotelsPackage.Service.HotelService;
import SpringBootStarterProject.HotelsPackage.Request.HotelRequest;
import SpringBootStarterProject.ResourcesPackage.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/hotel")
@RequiredArgsConstructor
@Tag(name = "Hotel")
public class HotelController {

    private final HotelService hotelService;
    private final FileStorageService fileStorageService;

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
    public ResponseEntity<?> getAllHotelsByCityId(@PathVariable Integer city_id) {
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
    public ResponseEntity<?> getAllHotelsByCountryId(@PathVariable Integer country_id) {
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
    public ResponseEntity<?> getAllHotels() {
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
    public ResponseEntity<?> getHotelById(@PathVariable Integer id) {
        return ResponseEntity.ok(hotelService.findHotelById(id));
    }

    @PostMapping(consumes = "multipart/form-data")
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
    public ResponseEntity<?> createHotel(@RequestPart("request") HotelRequest request,
                                                     @RequestPart("file") MultipartFile file) {
        request.setFile(file);
        return ResponseEntity.ok(hotelService.save(request));
    }

    @PutMapping("{id}")
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
    public ResponseEntity<?> updateHotel(@PathVariable Integer id,@RequestBody HotelRequest request) {
        return ResponseEntity.ok(hotelService.update(id,request));
    }

    @DeleteMapping("{id}")
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
    public ResponseEntity<?> deleteHotel(@PathVariable Integer id,@RequestBody HotelRequest request) {
        return ResponseEntity.ok(hotelService.delete(id,request));
    }

    @GetMapping("/minAvg/{minAvg}/maxAvg/{maxAvg}")
    @Operation(
            description = "This endpoint build to Get Hotels between two average rating in our system",
            summary = "Get Hotels between two average rating",
            responses = {
                    @ApiResponse(
                            description = "deleted successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> findHotelsBetweenTwoAvg(@PathVariable Double minAvg, @PathVariable Double maxAvg ) {
        return ResponseEntity.ok(hotelService.findHotelsBetweenTwoAvgRating(minAvg,maxAvg));
    }



/////////////////////////////////////////////////////////////////////////////////////////////////////
//
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadHotelPhoto(@PathVariable Integer id) {
        return hotelService.findHotelById(id);
    }
//        // Load hotel and file metadata
//        HotelResponse hotelResponse = hotelService.findHotelById(id); // Implement this method to fetch hotel response
//
//
//        MultipartResponse photo = fileService.loadFileAsResourceByIdForHotel(hotelResponse.getPhotoId());
//
//        if (photo == null) {
//            return new ResponseEntity<>("Photo not found", HttpStatus.NOT_FOUND);
//        }
//
//        // Construct multipart response
//        Resource resource = fileService.loadAsResource(photo.getJson().getId());
//
//        if (resource.exists()) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            headers.setContentDispositionFormData("attachment", resource.getFilename());
//
//            ApiResponseClass response = ApiResponseClass.builder()
//                    .message("Hotel Created successfully")
//                    .status(HttpStatus.OK)
//                    .localDateTime(LocalDateTime.now())
//                    .body(hotelResponse)
//                    .build();
//
//            // Return a ResponseEntity with a multipart response
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .contentType(MediaType.MULTIPART_MIXED)
//                    .body(resource);
//        } else {
//            return new ResponseEntity<>("Could not find file", HttpStatus.NOT_FOUND);
//        }
//    }

}
