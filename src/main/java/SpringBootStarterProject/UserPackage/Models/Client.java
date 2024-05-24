package SpringBootStarterProject.UserPackage.Models;

import SpringBootStarterProject.ManagingPackage.Security.Token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Client extends BaseUser  implements UserDetails {


    private String phone_number;

    private Boolean active;

    @OneToOne(mappedBy = "clientId")
    private ClientDetails clientDetails;


    @OneToMany
    private List<Passenger> passengers;


    @OneToOne(mappedBy = "client")
    private Wallet wallet;




}
