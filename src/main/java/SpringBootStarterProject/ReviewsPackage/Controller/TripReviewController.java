package SpringBootStarterProject.ReviewsPackage.Controller;

import SpringBootStarterProject.ReviewsPackage.Request.HotelReviewRequest;
import SpringBootStarterProject.ReviewsPackage.Request.TripReviewRequest;
import SpringBootStarterProject.ReviewsPackage.Service.HotelReviewService;
import SpringBootStarterProject.ReviewsPackage.Service.TripReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/trip_reviews")
@RequiredArgsConstructor
@Tag(name = "Trip Review",description = "This Controller to add a review to a Trip")
public class TripReviewController {

    private final TripReviewService tripReviewService;
    @GetMapping("{id}")
    public ResponseEntity<?> getTripReview(@PathVariable Integer id) {

        return ResponseEntity.ok(tripReviewService.findTripReviewsId(id));
    }

    @PostMapping
    public ResponseEntity<?> createTripReview(@RequestBody TripReviewRequest request) {
        return ResponseEntity.ok(tripReviewService.save(request));
    }



}
