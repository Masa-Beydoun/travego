package SpringBootStarterProject.HotelsPackage.Request;

import SpringBootStarterProject.HotelsPackage.Hotel.Hotel;
import SpringBootStarterProject.UserPackage.Models.Client;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.time.LocalDateTime;

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
