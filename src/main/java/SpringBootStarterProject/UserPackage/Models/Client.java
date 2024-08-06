package SpringBootStarterProject.UserPackage.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table
public class Client extends BaseUser  implements UserDetails {


    private String phone_number;


    @JsonIgnore
    private Boolean active;

    @OneToOne(mappedBy = "client",cascade = CascadeType.ALL)
    @JsonIgnore
  //  @JoinColumn(name = "client_details_id")
    private ClientDetails clientDetails;


    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Passenger> passengers;


    @OneToOne(mappedBy = "client",cascade = CascadeType.ALL)
    @JsonIgnore
    private Wallet wallet;

    private Boolean AddAllDocs  = false;


}
