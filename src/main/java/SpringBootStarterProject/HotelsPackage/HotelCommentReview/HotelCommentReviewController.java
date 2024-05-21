package SpringBootStarterProject.HotelsPackage.HotelCommentReview;

import SpringBootStarterProject.HotelsPackage.Request.CommentReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comment")
public class HotelCommentReviewController {

    @Autowired
    private HotelCommentReviewService hotelCommentReviewService;


    @GetMapping("/{id}")
    public ResponseEntity<List<HotelCommentReview>> getHotelCommentReviewByHotelId(@PathVariable Integer id) {
        return ResponseEntity.ok(hotelCommentReviewService.getHotelCommentReviewByHotelId(id));
    }


    @PostMapping
    public ResponseEntity<HotelCommentReview> createComment(@RequestBody CommentReviewRequest request) {
        return ResponseEntity.ok(hotelCommentReviewService.addComment(request));
    }


    @DeleteMapping
    public ResponseEntity deleteComment(@RequestBody HotelCommentReview hotelCommentReview) {
        hotelCommentReviewService.deleteComment(hotelCommentReview);
        return ResponseEntity.ok().build();
    }

}
