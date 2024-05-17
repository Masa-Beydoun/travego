package SpringBootStarterProject.UserPackage.Request;

import SpringBootStarterProject.ManagingPackage.annotation.ValidPassword;
import SpringBootStarterProject.UserPackage.RolesAndPermission.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ManagerRegisterRequest
{
     @NotBlank(message = "name is null")
    private String first_name;

      @NotBlank(message = "name is null")
    private String last_name;


    @Column(unique = true)
    private String username;


    @NotBlank
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",flags = Pattern.Flag.CASE_INSENSITIVE)


    @Column(unique = true)
    private String email;

    @NotBlank
    @ValidPassword
    private String password;

    @Enumerated
    private Roles role;

}
