package com.example.security.Models;

import com.example.security.RolesAndPermission.Roles;
import com.example.security.Security.Token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

public class Student extends BaseUser implements UserDetails {

private double my_Money=0;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
  private List<Course>courses;
//WTF????????/
   // @Nullable
  //  @OneToMany(mappedBy = "student")
   // private List<Token> tokens;
    @Nullable
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;


}
