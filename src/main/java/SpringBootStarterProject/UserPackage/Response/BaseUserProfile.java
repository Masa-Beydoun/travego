package SpringBootStarterProject.UserPackage.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseUserProfile {

    private String email;

    private String password;

}
