package SpringBootStarterProject.ReviewsPackage.Controller;

import SpringBootStarterProject.ReviewsPackage.Service.HotelReviewService;
import SpringBootStarterProject.ReviewsPackage.Request.HotelReviewRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/hotel_reviews")
@RequiredArgsConstructor
@Tag(name = "Hotel Review",description = "This Controller to add a review to a Hotel")
public class HotelReviewController {


    private final HotelReviewService hotelReviewService;

    @GetMapping("/byHotelDetailsId/{id}")
    @Operation(
            description = "This endpoint build to Get All Review by Hotel-Details-Id in our system",
            summary = "Get All Review by Hotel-Reviews-Id",
            responses = {
                    @ApiResponse(
                            description = "Get all done successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Hotel-Details-Id not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> getHotelReview(@PathVariable Integer id) {
        return ResponseEntity.ok(hotelReviewService.findHotelReviewsByHotelDetailsId(id));
    }

    @PostMapping
    @Operation(
            description = "This endpoint build to Save a Review in a Hotel in our system",
            summary = "Save a Review in a Hotel",
            responses = {
                    @ApiResponse(
                            description = "saved successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Hotel id not found",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Client id not found",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Client already submitted a review. ",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> createHotelReview(@RequestBody HotelReviewRequest hotelReview) {
        return ResponseEntity.ok(hotelReviewService.save(hotelReview));
    }

//    @PutMapping
//    @Operation(
//            description = "This endpoint build to Update a Review in a Hotel in our system",
//            summary = "Update a Review in a Hotel",
//            responses = {
//                    @ApiResponse(
//                            description = "saved successfully",
//                            responseCode = "200"
//                    ),
//                    @ApiResponse(
//                            description = "Hotel id not found",
//                            responseCode = "400"
//                    ),
//                    @ApiResponse(
//                            description = "Client id not found",
//                            responseCode = "400"
//                    )
//            }
//    )
//    public ResponseEntity<HotelReviewResponse> updateHotelReview(@RequestBody HotelReviewRequest hotelReview) {
//        return ResponseEntity.ok(hotelReviewService.save(hotelReview));
//    }



    @DeleteMapping("/{id}")
    @Operation(
            description = "This endpoint build to Delete a Review in our system",
            summary = "Delete Review by id",
            responses = {
                    @ApiResponse(
                            description = "Deleted successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Hotel-Review id not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> deleteHotelReview(@PathVariable Integer id) {
        return ResponseEntity.ok(hotelReviewService.delete(id));
    }


//    @PutMapping
//    @Operation(
//            description = "This endpoint build to Update a Review in our system",
//            summary = "Update Review by id",
//            responses = {
//                    @ApiResponse(
//                            description = "Updated successfully",
//                            responseCode = "200"
//                    ),
//                    @ApiResponse(
//                            description = "Hotel-Review id not found",
//                            responseCode = "400"
//                    )
//            }
//    )
//    public ResponseEntity<HotelReviewResponse> deleteHotelReview(@RequestBody HotelReviewRequest request) {
//        return ResponseEntity.ok(hotelReviewService.update(request));
//    }



}
