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
    public ResponseEntity<List<HotelCommentReviewResponse>> getHotelCommentReviewByHotelId(@PathVariable Integer id) {
        return ResponseEntity.ok(hotelCommentReviewService.getHotelCommentReviewByHotelId(id));
    }


    @PostMapping
    public ResponseEntity<HotelCommentReviewResponse> createComment(@RequestBody HotelCommentReviewRequest request) {
        return ResponseEntity.ok(hotelCommentReviewService.addComment(request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Integer id) {
        hotelCommentReviewService.deleteComment(id);
        return ResponseEntity.ok("Deleted successfully");
    }

}
