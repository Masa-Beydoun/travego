package SpringBootStarterProject.UserPackage.Request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmailConfirmationRequest {

    private String codeNumber;
    private String user_email;
}
