package SpringBootStarterProject.HotelsPackage.Request;

import SpringBootStarterProject.HotelsPackage.HotelPackage.Hotel;
import SpringBootStarterProject.UserPackage.Models.Client;
import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Builder
@Data
public class CommentReviewRequest {
    @NotBlank(message = "Comment is null")
    private String comment;
    @NotBlank(message = "Comment is null")
    private Hotel hotel;
    @NotBlank(message = "Comment is null")
    private Client client;


}
