package SpringBootStarterProject.UserPackage.Models;

import SpringBootStarterProject.UserPackage.RolesAndPermission.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@SuperBuilder
//@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Getter
public class Users implements UserDetails {

    @Id
    @GeneratedValue
    private int id;

    //  @NotBlank(message = "name is null")
    private String first_name;

    //  @NotBlank(message = "name is null")
    private String last_name;

    private String phone_number;

    private String username;


    // @NotBlank
    //@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",flags = Pattern.Flag.CASE_INSENSITIVE)


    @Column(unique = true)
    private String email;

    //@NotBlank
    //  @ValidPassword
    @JsonIgnore
    //@Size(min = 6,max = 61)
    private String password;

    private LocalDate creationDate;

    @OneToOne(mappedBy = "")
    private Wallet wallet;

  //  @JsonIgnore
   // @Enumerated(EnumType.STRING)
   // private Roles roles;

    private boolean active;
    @OneToMany
    private List<Passengers> passengers;
    public boolean isActive() {
        return active;
    }
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.getAuthorities();
    }
    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }
    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }


}
