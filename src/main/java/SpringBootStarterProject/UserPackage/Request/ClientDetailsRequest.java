package SpringBootStarterProject.UserPackage.Request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
public class ClientDetailsRequest {


    private String gender;

    private Date birthDate;

}
