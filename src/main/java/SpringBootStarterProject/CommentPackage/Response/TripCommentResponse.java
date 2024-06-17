package SpringBootStarterProject.CommentPackage.Response;

import SpringBootStarterProject.Trip_package.Models.Trip;
import SpringBootStarterProject.UserPackage.Models.Client;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TripCommentResponse {
    private Integer id;
    private String comment;
    private Client client;
    private LocalDateTime createdAt;
    private Trip trip;
}
