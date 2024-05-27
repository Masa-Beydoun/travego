package SpringBootStarterProject.UserPackage.Request;

import SpringBootStarterProject.ManagingPackage.annotation.ValidPassword;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordRequest
{
    private String oldPassword;
    @ValidPassword
    private String newPassword;

    private String confirmationPassword;

}
