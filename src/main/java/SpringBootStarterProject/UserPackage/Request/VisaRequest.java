package SpringBootStarterProject.UserPackage.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
public class VisaRequest {

    @NotBlank(message = "visaType shouldnt be blank")
    private String visaType;

    @NotBlank(message = "country shouldnt be blank")
    private String country;

    @NotNull(message = "visaIssueDate shouldnt be Null")
    private LocalDate visaIssueDate;

    @NotNull(message = "visaExpiryDate shouldnt be Null")
    private LocalDate visaExpiryDate;

    private Integer passengerId;
}