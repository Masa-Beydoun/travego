package SpringBootStarterProject.CommentPackage.controller;

import SpringBootStarterProject.CommentPackage.Service.CommentService;
import SpringBootStarterProject.CommentPackage.Request.CommentRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comment")
@Tag(name = "Comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelCommentReviewByHotelDetailsId(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.getHotelCommentReviewByHotelDetailsId(id));
    }


    @PostMapping("/add-hotel-comment")
    @Operation(
            description = "This endpoint build to add a comment to a hotel in a City in our system",
            summary = "save a hotel",
            responses = {
                    @ApiResponse(
                            description = "Comment added successfully",
                            responseCode = "200"
                    )
            }
    )
    public ResponseEntity<?> createHotelComment(@RequestBody CommentRequest request) {
        return ResponseEntity.ok(commentService.addHotelComment(request));
    }

    @PostMapping("/add-trip-comment")
    @Operation(
            description = "This endpoint build to add a comment to a hotel in a City in our system",
            summary = "save a hotel",
            responses = {
                    @ApiResponse(
                            description = "Comment added successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "trip id not found",
                            responseCode = "400"
                    )
            }
    )
    public ResponseEntity<?> createTripComment(@RequestBody CommentRequest request) {
        return ResponseEntity.ok(commentService.addTripComment(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.deleteComment(id));
    }

}
