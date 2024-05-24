package SpringBootStarterProject.UserPackage.Request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditClientRequest {

    private Integer id;

    private String first_name;


    private String last_name;


    @Column(unique = true)
    private String username;

}
