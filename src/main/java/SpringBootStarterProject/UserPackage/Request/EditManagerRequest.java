package SpringBootStarterProject.UserPackage.Request;

import SpringBootStarterProject.UserPackage.RolesAndPermission.Roles;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditManagerRequest {
    private Integer id;

    private String first_name;


    private String last_name;


    @Column(unique = true)
    private String username;

    @Enumerated
    private Roles role;
}
