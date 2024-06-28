package SpringBootStarterProject.UserPackage.Request;

import SpringBootStarterProject.UserPackage.Models.RelationshipType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
public class PassportRequest {

    @NotBlank(message = "passportfirstName shouldnt be blank")
    private String passportfirstName;

    @NotBlank(message = "passportlastName shouldnt be blank")
    private String passportlastName;

    @NotNull(message = "passportIssueDate shouldnt be Null")
    private LocalDate passportIssueDate;

    @NotNull(message = "passportExpiryDate shouldnt be Null")
    private LocalDate passportExpiryDate;

    @NotBlank(message = "passportNumber shouldnt be blank")
    private String passport_number;


    private RelationshipType type;

    private Integer passengerId;

}
