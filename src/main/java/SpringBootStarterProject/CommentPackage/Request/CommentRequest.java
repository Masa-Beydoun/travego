package SpringBootStarterProject.CommentPackage.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommentRequest {
    @NotNull(message = "Comment must not be null")
    private String comment;
    @NotNull(message = "hotel details id must not be null")
    private Integer hotelDetailsId;



}
