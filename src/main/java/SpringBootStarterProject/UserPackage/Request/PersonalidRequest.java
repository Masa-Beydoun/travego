package SpringBootStarterProject.UserPackage.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PersonalidRequest {

    @NotBlank(message = "idfirstName shouldnt be blank")
    private String idfirstName;

    @NotBlank(message = "idlastName shouldnt be blank")
    private String idlastName;

    @NotNull(message = "idBirthDate shouldnt be Null")
    private Date idBirthDate;

    @NotBlank(message = "nationality shouldnt be blank")
    private String nationality;
}
