package SpringBootStarterProject.UserPackage.Models;

import SpringBootStarterProject.ManagingPackage.Security.Token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client extends BaseUser {

    private String phone_number;

    private Boolean active;

    @OneToOne(mappedBy = "clientId")
    private ClientDetails clientDetails;


    @OneToMany
    private List<Passenger> passengers;


    @OneToOne(mappedBy = "")
    private Wallet wallet;

    @OneToMany(mappedBy = "client")
    private List<Token> tokens;


}
