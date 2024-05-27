package SpringBootStarterProject.HotelsPackage.Request;

import SpringBootStarterProject.UserPackage.Models.Client;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Builder
@Data
public class HotelCommentReviewRequest {
    @NotBlank(message = "Comment is null")
    @NotNull
    private String comment;
    @NotNull
    private Integer hotelId;
    @NotNull
    private Integer clientId;


}
