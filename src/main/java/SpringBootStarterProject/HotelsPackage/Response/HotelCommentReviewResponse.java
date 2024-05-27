package SpringBootStarterProject.HotelsPackage.Response;

import SpringBootStarterProject.HotelsPackage.Models.Hotel;
import SpringBootStarterProject.HotelsPackage.Models.HotelDetails;
import SpringBootStarterProject.UserPackage.Models.Client;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class HotelCommentReviewResponse {
    private Integer id;
    private String comment;
    private HotelDetails hotelDetails;
    private Client client;
    private LocalDateTime createdAt;
}
