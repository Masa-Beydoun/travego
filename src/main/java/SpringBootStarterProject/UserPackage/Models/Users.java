package com.example.security.Models;

import com.example.security.RolesAndPermission.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@SuperBuilder
//@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Getter
public class Users implements UserDetails {

    @Id
    private int id;
    //  @NotBlank(message = "name is null")
    private String name;
    // @NotBlank
    //@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(unique = true)
    private String email;

    //@NotBlank

    //  @ValidPassword
    @JsonIgnore
    //@Size(min = 6,max = 61)
    private String password;


    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Roles roles;

    private boolean active;

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
