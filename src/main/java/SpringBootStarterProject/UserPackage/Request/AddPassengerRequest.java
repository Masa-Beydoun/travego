package SpringBootStarterProject.UserPackage.Request;

import SpringBootStarterProject.UserPackage.Models.Client;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data
public class AddPassengerRequest {

    private Integer id;

    @NotBlank(message = "first_name required")
    private String first_name;

    @NotBlank(message = "last_name required")
    private String last_name;

    @NotBlank(message = "father_name required")
    private String father_name;

    @NotBlank(message = "mother_name required")
    private String mother_name;

    @NotBlank(message = "gender required")
    private String gender;

    @NotNull(message = "birthdate required")
    private LocalDate birthdate;

}
