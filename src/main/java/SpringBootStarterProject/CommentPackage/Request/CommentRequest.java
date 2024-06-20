package SpringBootStarterProject.CommentPackage.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Builder
@Data
public class CommentRequest {
    @NotBlank(message = "Comment is null")
    @NotNull
    private String comment;
    @NotNull
    private Integer hotelDetailsId;
    @NotNull
    private Integer clientId;


}
