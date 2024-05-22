package SpringBootStarterProject.UserPackage.Request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailRequest {

    public Integer id;
    public String email;


}
