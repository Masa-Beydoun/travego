package SpringBootStarterProject.UserPackage.Request;

import SpringBootStarterProject.ManagingPackage.annotation.ValidPassword;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ForgetPasswordRequest
{

    private  Integer clientID;
    @ValidPassword
    private String newPassword;

    private String confirmationPassword;

}