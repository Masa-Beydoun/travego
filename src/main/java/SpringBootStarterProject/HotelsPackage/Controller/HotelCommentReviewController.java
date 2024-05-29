package SpringBootStarterProject.HotelsPackage.Controller;

import SpringBootStarterProject.HotelsPackage.Models.HotelCommentReview;
import SpringBootStarterProject.HotelsPackage.Response.HotelCommentReviewResponse;
import SpringBootStarterProject.HotelsPackage.Service.HotelCommentReviewService;
import SpringBootStarterProject.HotelsPackage.Request.HotelCommentReviewRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comment")
@Tag(name = "Hotel Comment")
public class HotelCommentReviewController {

    @Autowired
    private HotelCommentReviewService hotelCommentReviewService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelCommentReviewByHotelDetailsId(@PathVariable Integer id) {
        return ResponseEntity.ok(hotelCommentReviewService.getHotelCommentReviewByHotelDetailsId(id));
    }


    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody HotelCommentReviewRequest request) {
        return ResponseEntity.ok(hotelCommentReviewService.addComment(request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id) {
        return ResponseEntity.ok(hotelCommentReviewService.deleteComment(id));
    }

}
