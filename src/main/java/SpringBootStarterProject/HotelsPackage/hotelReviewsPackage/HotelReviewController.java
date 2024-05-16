package SpringBootStarterProject.HotelsPackage.hotelReviewsPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/hotel_reviews")
public class HotelReviewController {


    @Autowired
    private HotelReviewService hotelReviewService;

    @GetMapping("/{id}")
    public ResponseEntity<List<HotelReview>> getHotelReview(@PathVariable Integer id) {
        return ResponseEntity.ok(hotelReviewService.findHotelReviewsById(id));
    }

    @PostMapping()
    public ResponseEntity<HotelReview> createHotelReview(@RequestBody NewHotelReviewRequest hotelReview) {
        return ResponseEntity.ok(hotelReviewService.save(hotelReview));
    }


    @DeleteMapping()
    public ResponseEntity deleteHotelReview(@RequestBody HotelReview hotelReview) {
        hotelReviewService.delete(hotelReview);
        return ResponseEntity.ok().build();
    }


}
