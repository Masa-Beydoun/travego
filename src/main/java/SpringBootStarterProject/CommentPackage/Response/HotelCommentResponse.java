package SpringBootStarterProject.CommentPackage.Response;

import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.UserPackage.Models.Client;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class HotelCommentResponse {
    private Integer id;
    private String comment;
    private Client client;
    private LocalDateTime createdAt;
    private HotelDetails hotelDetails;
}
