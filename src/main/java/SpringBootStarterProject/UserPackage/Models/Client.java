package SpringBootStarterProject.UserPackage.Models;

import SpringBootStarterProject.ManagingPackage.Security.Token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Date;
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




}
