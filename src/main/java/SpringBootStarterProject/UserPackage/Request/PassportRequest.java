package SpringBootStarterProject.UserPackage.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PassportRequest {

    @NotBlank(message = "passportfirstName shouldnt be blank")
    private String passportfirstName;

    @NotBlank(message = "passportlastName shouldnt be blank")
    private String passportlastName;

    @NotNull(message = "passportIssueDate shouldnt be Null")
    private Date passportIssueDate;

    @NotNull(message = "passportExpiryDate shouldnt be Null")
    private Date passportExpiryDate;

    @NotBlank(message = "passportNumber shouldnt be blank")
    private String passportNumber;
}
